package instructions;

import machine.BullGamma;

/**
 * jump to another Series
 */
public class VRS extends Instruction {

	public VRS(byte AD, byte OD, byte OF, BullGamma bullGamma) {
		super((byte)1, AD, OD, OF, bullGamma);
		if (AD < 5 || AD > 7) {
			throw new InvalidInstructionError("1" + this.getChar(AD) + "xx");
		}
	}

	public void execute() {
		switch (this.AD) {
		case 5:
			// wait for a drum transfer to finish if jumping to the same series
			// not implemented
			this.bullGamma.nl = this.bullGamma.rnl1 % 64;
			this.bullGamma.ns = this.bullGamma.rnl1 >> 6;
			break;
		case 6:
			// wait for a drum transfer to finish if jumping to the same series
			// not implemented
			this.bullGamma.nl = this.bullGamma.rnl2 % 64;
			this.bullGamma.ns = this.bullGamma.rnl2 >> 6;
			break;
		case 7:
			// complex behavior
			throw new MethodNotImplementedError("excute");
		default:
			throw new InvalidInstructionExecutionError();
		}
	}

	public String getDescription() {
		switch (this.AD) {
		case 5:
			return "VRS - Variante Retour Serie\n"
			+ "Retourne à la ligne enregistrée en RNL1";
		case 6:
			return "VRS - Variante Retour Serie\n"
			+ "Retourne à la ligne enregistrée en RNL2";
		case 7:
			return "Instruction non implémentée";
		default:
			throw new InvalidInstructionDescriptionError();
		}
	}

	public String getShortType() {
		return "VRS";
	}

	public String getLongType() {
		return "Variante Retour Serie";
	}

}
