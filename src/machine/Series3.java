package machine;

import instructions.Instruction;

/**
 * Class which represents the connexion array of the Bull Gamma where plots would be connected by wires to hard code
 * programs. It contains 64 instructions and as such can be considered as a Series
 */
public class Series3 extends Serie {

	public static int NB_INST_SERIES_3 = Constants.NB_INST_SERIES_3;
	Instruction[] instructions;

	/**
	 * Constructs a new instance of ConnexioArray
	 * @param id the id for the extended Serie, should always be 3 to respect the physical architecture
	 * @param bullGamma the bull gamma to which this array is connected
	 */
	public Series3(int id, BullGamma bullGamma) {
		super(id, bullGamma);
		this.maxNbInst = NB_INST_SERIES_3;
		try {
			this.setInstructions("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set the Series3's content with hex values
	 * @param hexCode a String that represents the new hex values of the array
	 * @throws Exception 
	 */
	public void setInstructions(String hexCode) throws Exception {
		this.instructions = this.bullGamma.parser.parseInstructions(hexCode, NB_INST_SERIES_3);
	}

	/**
	 * @param line the position of the desired instructions
	 * @returns {Instruction} the fetched instructions
	 */
	public Instruction getInstruction(int line) {
		return this.instructions[line];
	}

	/**
	 * @returns {Array|Instruction} all the instructions of the Series3
	 */
	public Instruction[] getInstructions() {
		return this.instructions;
	}

}
