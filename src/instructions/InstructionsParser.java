package instructions;

import machine.BullGamma;

/**
 * Parser generating instruction for the given bullGamma
 */
public class InstructionsParser {

	BullGamma bullGamma;

	public InstructionsParser(BullGamma bullGamma) {
		this.bullGamma = bullGamma;
	}

	/**
	 * Function that return the hex code without comments, return, tab, spaces
	 * @param entry string with comments, hex code, spaces...
	 * @param size the number of hex chunks expected, completes with 0
	 * @throws Exception 
	 * @returns hex code
	 */
	static String parseHex(String entry, int size) throws Exception {
		return Tools.parse_hex_code(entry, size);
	}

	/**
	 * function that returns a list of instructions from the given code
	 * @param entry code with comments, spaces, returns allowed
	 * @param size the number of expected instruction, completes with NOP
	 * @throws Exception
	 */
	public Instruction[] parseInstructions(String entry, int size) throws Exception {
		return Tools.parse_hex_str_to_instructions(entry, size, this.bullGamma);
	}

	/**
	 * function that returns the instruction corresponding to the params
	 * @param TO string or number // TODO only for number here
	 * @param AD string or number
	 * @param OD string or number
	 * @param OF string or number
	 */
	public Instruction parseInstruction(byte TO, byte AD, byte OD, byte OF) {
/*		TO = parseInt(TO.toString(16), 16);
		AD = parseInt(AD.toString(16), 16);
		OD = parseInt(OD.toString(16), 16);
		OF = parseInt(OF.toString(16), 16); */
		return Tools.parseInstruction(TO, AD, OD, OF, this.bullGamma);
	}
}