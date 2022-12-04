package instructions;

import machine.BullGamma;

/**
 * Abstract class for all instructions where OD cannot be greater than OF
 */
public class Operation extends Instruction {

	public Operation(byte TO, byte AD, byte OD, byte OF, BullGamma bullGamma) {
		super(TO, AD, OD, process(AD,OD,OF), bullGamma); // TODO check move
		this.hexString = this.getHexCode(TO, AD, OD, OF);
	}
	
	static byte process(byte ad, byte od, byte of) {
		if (od >= of && ad != 0) {
			return 12;
		} else {
			return of;
		}

	}
}
