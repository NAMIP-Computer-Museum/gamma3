package instructions;

import machine.BullGamma;
import machine.Memory;

/**
 * reduced multiplication
 */
public class MR extends SmallDivOrMult {  // TODO check wrong name in javascript

	public MR(byte AD, byte OD, byte OF, BullGamma bullGamma) {
		super((byte)12, AD, OD, OF, bullGamma); // TODO check pos

		if (AD == 1) {
			throw new InvalidInstructionError("c1xx");
		}
	}
	
	void _compute(Memory mb) {
		this.bullGamma.getMemory(1).multiply(mb, this.OD, this.OF);
	}

	void _computeValue() {
		this.bullGamma.getMemory(1).multiplyValue(this.OF, this.OD);
	}

	public String getDescription() {
		if (this.AD == 0) {
			return "Multipplie le nombre contenu dans M1 par " + this.OF
					+ " en position " + this.AD
					+ ", le résultat est en M1";
		}
		if (this.AD > 1) {
			return "Multipplie le nombre contenu dans M1 par M" + this.AD
					+ " entre les positions " + this.OD + " et " + this.OF
					+ ", le résultat est en M1";
		}
		throw new InvalidInstructionDescriptionError();
	}

	public String getShortType() {
		return "MR";
	}

	public String getLongType() {
		return "Multiplication Réduite";
	}

}
