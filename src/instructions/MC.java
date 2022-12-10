package instructions;

import machine.BullGamma;
import machine.Memory;

/**
 * complete multiplication
 */
public class MC extends BigDivOrMult {

	public MC(byte AD, byte OD, byte OF, BullGamma bullGamma) {
		super((byte)14, AD, OD, OF, bullGamma); // TODO check location
		if (AD == 1 || AD == 2) {
			throw new InvalidInstructionError("e" + Instruction.getChar(AD) + "xx");
		}
	}

	void _compute(Memory m1m2, Memory mb) {
		m1m2.multiply(mb, this.OD, this.OF);
	}

	void _computeValue(Memory m1m2) {
		if (this.OF != 0) {
			this.bullGamma.md = NB_CHRS_PER_WORD;
		}
		m1m2.multiplyValue(this.OF, (byte)(this.OD + NB_CHRS_PER_WORD));
	}

	public String getDescription() {
		if (this.AD == 0) {
			return "Multipplie le nombre contenu dans M1 par " + this.OF
					+ " en position " + this.AD
					+ ", le résultat est en M1-M2";
		}
		if (this.AD > 2) {
			return "Multipplie le nombre contenu dans M1 par  M" + this.AD
					+ " entre les positions " + this.OD + " et " + this.OF
					+ ", le résultat est en M1-M2";
		}
		throw new InvalidInstructionDescriptionError();
	}

	public String getShortType() {
		return "MC";
	}

	public String getLongType() {
		return "Multiplication Complète";
	}
}
