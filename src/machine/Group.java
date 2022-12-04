package machine;

import instructions.Tools;

/**
 * A group is a set of NB_OCTADS_PER_GROUP octads
 */
public class Group {

	public static final int NB_OCTADS_PER_GROUP = Constants.NB_OCTADS_PER_GROUP;
	public static final int NB_MEMORIES_PER_OCTAD = Constants.NB_MEMORIES_PER_OCTAD;
	public static final int NB_CHRS_PER_WORD = Constants.NB_CHRS_PER_WORD;
	public static final int NB_HEX_VALUES_PER_GROUP = Constants.NB_HEX_VALUES_PER_GROUP;

	int id;
	BullGamma bullGamma;
	public Octad[] octads;

	/**
	 * constructs a new instance of Group
	 * @param id the Group's ID
	 * @param bullGamma the machine attached to this Group
	 */
	public Group(int id, BullGamma bullGamma) {
		assert (id >= 0) : "id should not be negative";
		this.id = id;
		this.bullGamma = bullGamma;
		this.octads = new Octad[NB_OCTADS_PER_GROUP];
		for (int i = 0; i < NB_OCTADS_PER_GROUP; ++i) {
			this.octads[i] = new Octad(i + id*NB_OCTADS_PER_GROUP, bullGamma);
		}
	}

	/**
	 * Set the Word's content with hex values
	 * @param hexCode a String that represents the new hex values of this Word
	 * @throws Exception 
	 */
	public void setContent(String hexCode) throws Exception { // TODO check exception
		hexCode = Tools.parse_hex_code(hexCode, NB_HEX_VALUES_PER_GROUP);
		for (int i = 0; i < NB_OCTADS_PER_GROUP; ++i) {
			this.octads[i].setContent(hexCode.substring(
					i * (NB_MEMORIES_PER_OCTAD * NB_CHRS_PER_WORD),
					(i + 1) * (NB_MEMORIES_PER_OCTAD * NB_CHRS_PER_WORD)
					));
		}
	}

	/**
	 * Returns the word corresponding to the id
	 * @param id from 0 to 15
	 */
	public Word getWord(int id) {
		for (int o = 0; o < NB_OCTADS_PER_GROUP; o++) {
			if (id < (o + 1) * NB_MEMORIES_PER_OCTAD) {
				return this.octads[o].getMemory(id - o * NB_MEMORIES_PER_OCTAD);
			}
		}
		throw new Error("Not a valid id for word in group: " + id);
	}

	public String toString() {
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < NB_OCTADS_PER_GROUP/2; ++i) {
			for (int j = 0; j < NB_MEMORIES_PER_OCTAD; ++j) {
				sb.append(this.octads[i].getMemory(j).toString() + "\t");
				sb.append(this.octads[i + NB_OCTADS_PER_GROUP/2].getMemory(j).toString() + "\n");
			}
		}
		return sb.toString();
	}
}