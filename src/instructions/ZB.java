package instructions;

import machine.BullGamma;

/**
 * Memory reset
 */
public class ZB extends Operation {

	public ZB(byte AD, byte OD, byte OF, BullGamma bullGamma) {
		super((byte)3, AD, OD, OF, bullGamma); // TODOD check moved
		if (AD == 0) {
			throw new InvalidInstructionError("30xx");
		}
	}

	public void execute() {
		if (this.AD != 0) {
			this.bullGamma.getMemory(this.AD).setToZero(this.OD, this.OF);
			return;
		}
		throw new InvalidInstructionExecutionError();
	}

	public String getDescription() {
		if (this.AD != 0) {
			return "Met à zéro M" + this.AD + " entre les positions "
					+ this.OD + " et " + this.OF;
		}
		throw new InvalidInstructionDescriptionError();
	}

	public String getShortType() {
		return "ZB";
	}

	public String getLongType() {
		return "Mise à Zéro de mémoire Banale";
	}

}