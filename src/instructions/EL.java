package instructions;

import machine.BullGamma;

/**
 * Ecriture de Lettre
 */
public class EL extends V {

	public EL(byte AD, byte OD, byte OF, BullGamma bullGamma) {
		super(AD, OD, OF, bullGamma); // TODO check mode
		if (AD < 8 || OF%4 > 1) {
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
	//   return "EL";
	// }
	//
	// getLongType() {
	//   return "Variante";
	// }

}