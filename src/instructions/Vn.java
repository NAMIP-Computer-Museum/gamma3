package instructions;

import machine.BullGamma;

/**
 * Ecriture de Lettre
 */
public class Vn extends V {

	public Vn(byte AD, byte OD, byte OF, BullGamma bullGamma) {
		super(AD, OD, OF, bullGamma); // TOCO check move

		if (AD > 7 || OF%4 < 2) {
			throw new InvalidInstructionError("0" + Instruction.getChar(AD) + "x" + Instruction.getChar(OF));
		}
	}

	public void execute() {
		throw new MethodNotImplementedError("execute");
	}

	public String getDescription() {
		throw new MethodNotImplementedError("getDescription");
	}

	// getShortType() {
	//   return "Vn";
	// }
	//
	// getLongType() {
	//   return "Variante";
	// }

}
