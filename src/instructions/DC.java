package instructions;

import machine.BullGamma;
import machine.Constants;
import machine.Memory;

//InvalidInstructionError = require("./instruction").InvalidInstructionError;
//InvalidInstructionDescriptionError = require("./instruction").InvalidInstructionDescriptionError;

/**
 * Complete Division
 */
public class DC extends BigDivOrMult {

	public static final int NB_CHRS_PER_WORD = Constants.NB_CHRS_PER_WORD;


	public DC(byte AD, byte OD, byte OF, BullGamma bullGamma) {
		super((byte)15, AD, OD, OF, bullGamma); // TODO check switch here

		if (AD == 1 || AD == 2) {
			throw new InvalidInstructionError("f" + Instruction.getChar(AD) + "xx");
		}
	}

	void _compute(Memory m1m2, Memory mb) {
		m1m2.divide(mb, this.OD, this.OF);
	}

	void _computeValue(Memory m1m2) {
		if (this.OF == 1 ) {
			while (this.bullGamma.md > 0) {
				// OD is ignored as of now
				m1m2.shiftLeft();
				this.bullGamma.md--;
			}
		} else {
			this.bullGamma.md = NB_CHRS_PER_WORD;
			m1m2.divideValue(this.OF, this.OD);
		}
	}

	public String getDescription() {
		if (this.AD == 0) {
			return "DC - Division Complète\n"
					+ "Divise le nombre contenu dans M1-M2 par " + this.OF
					+ " en position " + this.AD
					+ ", le quotient est calculé en M2 et le reste en M1-M2 en position MD ou 12 si MD vaut 0";
		}
		if (this.AD > 2) {
			return "DC - Division Complète\n"
					+ "Divise le nombre contenu dans M1-M2 par M" + this.AD
					+ " entre les positions " + this.OD + " et " + this.OF
					+ ", le quotient est calculé en M2 et le reste en M1-M2 en position MD ou 12 si MD vaut 0";
		}
		throw new InvalidInstructionDescriptionError();
	}

	public String getShortType() {
		return "DC";
	}

	public String getLongType() {
		return "Division Complète";
	}

}
