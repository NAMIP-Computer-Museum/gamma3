package machine;

import java.util.ArrayList;
import java.util.List;

import instructions.Instruction;
import instructions.InstructionsParser;

/*
InstructionsParser = require("../assembly/hexParser").InstructionsParser;
Memory = require("./innerComponents/memory").Memory;
Group = require("./innerComponents/group").Group;
Octad = require("./innerComponents/octad").Octad;
CmpMemory = require("./innerComponents/cmpMemory").CmpMemory;
Series3 = require("./innerComponents/series3").Series3;
Serie = require("./innerComponents/serie").Serie;
MagneticDrum = require("./magneticDrum/magneticDrum").MagneticDrum;

const MEMORY_MODE = require("./constants").MEMORY_MODE;

const NB_BANAL_MEMORIES = require("./constants").NB_BANAL_MEMORIES;
const NB_GROUPS = require("./constants").NB_GROUPS;
const NB_SERIES = require("./constants").NB_SERIES;
const NB_GENERAL_SERIES = require("./constants").NB_GENERAL_SERIES;
const NB_OCTADS_PER_GROUP = require("./constants").NB_OCTADS_PER_GROUP;
const NB_COMMUTED_OCTADS = require("./constants").NB_COMMUTED_OCTADS;
const NB_MEMORIES_PER_OCTAD = require("./constants").NB_MEMORIES_PER_OCTAD;
NB_MEMORIES_PER_HALF_OCTAD = require('./constants').NB_MEMORIES_PER_HALF_OCTAD;
 */

/**
 * Central class meant to represent the whole machine
 */
public class BullGamma {
	
	public static final int NB_BANAL_MEMORIES = Constants.NB_BANAL_MEMORIES;
	public static final int NB_GROUPS=Constants.NB_GROUPS;
	public static final int NB_SERIES=Constants.NB_SERIES;
	public static final int NB_GENERAL_SERIES=Constants.NB_GENERAL_SERIES;
	public static final int NB_OCTADS_PER_GROUP=Constants.NB_OCTADS_PER_GROUP;
	public static final int NB_COMMUTED_OCTADS=Constants.NB_COMMUTED_OCTADS;
	public static final int NB_MEMORIES_PER_OCTAD=Constants.NB_MEMORIES_PER_OCTAD;
	public static final int NB_MEMORIES_PER_HALF_OCTAD=Constants.NB_MEMORIES_PER_HALF_OCTAD;	
	
	public byte ms1;
	public int md;
	public CmpMemory mc; 
	public int nl; // line number
	public int ns; // series number
	public int rnl1;
	public int rnl2;
	
	InstructionsParser parser;
	MemoryMode _memoryMode;
	Memory[] _generalMemories;
	public Series3 series3;
	Group ioGroup;
	Serie[] series;
	Group[] groups;
	Octad currentOctad;

	List<ConnectedMachine> connectedMachines;
	MagneticDrum magneticDrum;
	
	/**
	 * Constructs a new instance of BullGamma
	 */
	public BullGamma() {		
		this.parser = new InstructionsParser(this);

		// Memories
		this._generalMemories = new Memory[NB_BANAL_MEMORIES];
		for (int i = 1; i < NB_BANAL_MEMORIES; ++i) {
			this._generalMemories[i] = new Memory(i + 1, this);
		}
		// M0 == M1
		this._generalMemories[0] = this._generalMemories[1];

		this.series3 = new Series3(NB_GENERAL_SERIES, this);
		this.ioGroup = new Group(NB_GENERAL_SERIES, this);

		// Series and groups
		this.series = new Serie[NB_SERIES];
		this.groups = new Group[NB_GROUPS];
		for (int i = 0; i < NB_GENERAL_SERIES; ++i) {
			this.groups[i] = new Group(i, this);
			this.series[i] = new Serie(i, this, this.groups[i]);
		}
		this.series[NB_GENERAL_SERIES] = this.series3;
		this.groups[NB_GENERAL_SERIES] = this.ioGroup;

		this.currentOctad = this.groups[0].octads[0];

		// Other
		this.magneticDrum = new MagneticDrum(this);  // TODO add drum
		this.connectedMachines = new ArrayList<ConnectedMachine>();
		this._memoryMode = MemoryMode.DECIMAL;

		this.ms1 = 0;
		this.md = 0;
		this.mc = new CmpMemory();
		this.nl = 0; // line number
		this.ns = 3; // series number
		this.rnl1 = 0;
		this.rnl2 = 0;
	}

	/**
	 * Given an ID, return the corresponding serie
	 * @param id the serie to return, should be between 0 and 3 included
	 */
	public Serie getSerie(int id) {
		assert id >= 0 && id < NB_SERIES : "id should not be negative or superior to " + (NB_SERIES - 1);
		return this.series[id];
	}

	/**
	 * Given an ID, return the corresponding group
	 * @param id the group to return, should be between 0 and 3 included
	 */
	public Group getGroup(int id) {
		assert id >= 0 && id < NB_GROUPS : "id should not be negative or superior to " + (NB_GROUPS - 1);
		return this.groups[id];
	}

	/**
	 * Given an ID, return the corresponding octad
	 * @param id the octad to return, should be between 0 and 7 included
	 */
	public Octad getOctad(int id) {
		assert id >= 0 : "octad id should be positive";
		assert id < NB_COMMUTED_OCTADS : "octad id should be inferior to " + NB_COMMUTED_OCTADS;
		return this.groups[id/NB_OCTADS_PER_GROUP].octads[id % NB_OCTADS_PER_GROUP]; // TODO check floor
	}

	/**
	 * Changes the current octad the Bull Gamma is working with
	 * @param id id of the desired octad
	 */
	public void setCommutedOctad(int id) {
		this.currentOctad = this.getOctad(id);
	}

	/**
	 * @param id the memory to be returned, if superior to 7, then the memory is selected from the octad
	 * @param octadId if given, the memory will be selected from this octad, else from the current octad // TODO -1 for undefined
	 * @returns {*} the memory with the desired id
	 */
	public Memory getMemory(int id, int octadId) {
		assert id >= 0 : "memory id should not be negative";
		assert 
				id < NB_BANAL_MEMORIES + NB_MEMORIES_PER_OCTAD :
				"memory id should be inferior to " + NB_BANAL_MEMORIES + NB_MEMORIES_PER_OCTAD;

		if (id < NB_BANAL_MEMORIES) {
			return this._generalMemories[id];
		} else {
			if (octadId >=0) {
				return this.getOctad(octadId).getMemory(id - NB_BANAL_MEMORIES);
			} else {
				return this.currentOctad.getMemory(id - NB_BANAL_MEMORIES);
			}
		}
	}
	
	public Memory getMemory(int id) {
		return getMemory(id,-1);  // TODO -1 used for undefined
	}
	

	/**
	 * @returns {*} the current memory mode for the machine, either MEMORY_MODE.BINARY or MEMORY_MODE.DECIMAL
	 */
	public MemoryMode getMemoryMode() {
		return this._memoryMode;
	}

	/**
	 * set the value for the machine's memory mode
	 * @param newMode the new value, either MEMORY_MODE.BINARY or MEMORY_MODE.DECIMAL
	 */
	public void setMemoryMode(MemoryMode newMode) {
		assert (newMode == MemoryMode.BINARY || newMode == MemoryMode.DECIMAL) : "invalid memory mode";
		this._memoryMode = newMode;
	}

	/**
	 * Compute the next line to be executed if no jump
	 * @returns next line
	 */
	public int nextLine() {
		return (this.nl + 1) % (this.getSerie(this.ns).nbInst);
	}

	/**
	 * Executes the coming instruction if the current Series
	 */
	public void executeNextInstruction() {
		int old_cp = this.nl;
		this.nl = this.nextLine();

		// execute instruction
		Instruction instr=this.getSerie(this.ns).getInstruction(old_cp);
		Memory m1=getMemory(1);
		Memory m4=getMemory(4);
		System.out.println(instr+"\t"+instr.getShortType()+"\t"+m1+"\t"+m4); // TODO console ?
		instr.execute();
	}

	public Word[] getExtractors() {
		Word[] extractors = new Word[NB_MEMORIES_PER_HALF_OCTAD];
		for (int  i=3*NB_MEMORIES_PER_HALF_OCTAD, j=0; i<4*NB_MEMORIES_PER_HALF_OCTAD; i++, j++) {
			extractors[j]=this.ioGroup.getWord(i);
		}
		return extractors;
	}

	public Word[] getIntroductors() {
		Word[]  introductors = new Word[NB_MEMORIES_PER_HALF_OCTAD];
		for (int i=1*NB_MEMORIES_PER_HALF_OCTAD, j=0; i<2*NB_MEMORIES_PER_HALF_OCTAD; i++, j++) {
			introductors[j]=this.ioGroup.getWord(i);
		}
		return introductors;
	}

	public void connectMachine(ConnectedMachine machine) {
	  this.connectedMachines.add(machine);
	  machine.setBullGamma(this);
	}
	
	public ConnectedMachine[] getConnectedMachines() { // TODO check list to array
		ConnectedMachine[] res = new ConnectedMachine[connectedMachines.size()];
	    connectedMachines.toArray(res);
	    return res;
	}
	
	public MagneticDrum getMagneticDrum() {
		return magneticDrum;
	}

}