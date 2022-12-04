package machine;

import instructions.Instruction;
import instructions.NOP;

/**
 * A Series is Group which content is interpreted as instructions
 */
public class Serie {

	public static final int NB_INST_SERIES_3 = Constants.NB_INST_SERIES_3;
	public static final int NB_INST_PER_SERIE = Constants.NB_INST_PER_SERIE;
	public static final int NB_INST_PER_MEM = Constants.NB_INST_PER_MEM;
	public static final int NB_MEMORIES_PER_OCTAD = Constants.NB_MEMORIES_PER_OCTAD;

	int id;
	BullGamma bullGamma;
	int nbInst;
	int maxNbInst;
	Group group;
	
	/**
	 * constructs a new instance of Series
	 * @param id the id of this Series
	 * @param bullGamma the machine attached to this Serie
	 * @param group the group encapsulated in the Series
	 */
	public Serie(int id, BullGamma bullGamma, Group group) {
		assert (id >= 0) : "id should not be negative";
		assert bullGamma != null: "bullGamma should neither be null nor undefined";
		this.id = id;
		this.bullGamma = bullGamma;
		this.group = group; // TODO check undefined removed
		this.nbInst = NB_INST_PER_SERIE;
		this.maxNbInst = NB_INST_PER_SERIE;
	}
	
	/**
	 * constructor without group
	 * @param id
	 * @param bullGamma
	 */
	public Serie(int id, BullGamma bullGamma) {
		this(id,bullGamma,null);
	}

	/**
	 * Return the instructions list of the series
	 */
	public Instruction getInstruction(int line) {
		assert (this.group!=null) : "group attribute is undefined";
		assert (line >= 0) && (line < this.maxNbInst) : "invalid instruction index";
		int q = line / NB_INST_PER_MEM;  // TODO make sure it is floor
		int r = line % NB_INST_PER_MEM;

		// blank line every 3 instructions
		if (r == NB_INST_PER_MEM - 1) {
			return new NOP(this.bullGamma);
		}

		Memory mem = this.group.octads[q / NB_MEMORIES_PER_OCTAD].getMemory(q % NB_MEMORIES_PER_OCTAD); // TODO check math floor dans q/NB_MEM
		byte TO = mem.blocks[4 * r + 3];
		byte AD = mem.blocks[4 * r + 2];
		byte OD = mem.blocks[4 * r + 1];
		byte OF = mem.blocks[4 * r];

		// try to compute an instruction, else return nop as an instruction
		// that does not have implementation
		try {
			return this.bullGamma.parser.parseInstruction(TO, AD, OD, OF);
		} catch (Exception e) {  // TODO check exception
			return new NOP(this.bullGamma);
		}
	}

	/**
	 * Return the instruction at the given line, or null if not found
	 * @param line program line of the desired instruction
	 */
	public Instruction[] getInstructions() {
		Instruction[] list = new Instruction[maxNbInst];   // TODO check instructions
		for (int i=0; i<this.maxNbInst; i++) {
			list[i]=this.getInstruction(i);
		}
		return list;
	}

}
