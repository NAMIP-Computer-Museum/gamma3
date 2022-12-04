package machine;

import instructions.Tools;

/**
 * An octad is a set of 8 memories
 */
public class Octad {

	public static final int  NB_MEMORIES_PER_OCTAD = Constants.NB_MEMORIES_PER_OCTAD;
	public static final int  NB_BANAL_MEMORIES = Constants.NB_BANAL_MEMORIES;
	public static final int  NB_HEX_VALUES_PER_OCTAD = Constants.NB_HEX_VALUES_PER_OCTAD;
	public static final int  NB_CHRS_PER_WORD = Constants.NB_CHRS_PER_WORD;

	int id;
	BullGamma bullGamma;
	Memory[] memories;
	
	/**
	 * construct a new instance of Octad
	 * @param id the id of this octad
	 * @param bullGamma the machine to which the octad is attached
	 */
	public Octad(int id, BullGamma bullGamma) {
		this.id = id;
		this.memories = new Memory[NB_MEMORIES_PER_OCTAD];
		for (int i = 0; i < NB_MEMORIES_PER_OCTAD; ++i) {
			this.memories[i] = new Memory(NB_BANAL_MEMORIES + i, bullGamma);
		}
	}

	/**
	 * Returns the memory corresponding to the id
	 * @param id from 0 to 7
	 */
	public Memory getMemory(int idx) {
		return this.memories[idx];
	}

	/**
	 * Set the Word's content with hex values
	 * @param hexCode a String that represents the new hex values of this Word
	 * @throws Exception 
	 */
	public void setContent(String hexCode) throws Exception { // TODO throws
		hexCode = Tools.parse_hex_code(hexCode, NB_HEX_VALUES_PER_OCTAD);
		for (int i = 0; i < NB_MEMORIES_PER_OCTAD; ++i) {
			this.getMemory(i).setContent(hexCode.substring(i*NB_CHRS_PER_WORD, NB_CHRS_PER_WORD));
		}
	}

	public String toString() {
		StringBuffer sb=new StringBuffer();
		for(Memory mem:memories)
			sb.append(mem.toString() + "\n");
		return sb.toString();
	}

}

