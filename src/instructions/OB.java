package instructions;

import machine.BullGamma;
import machine.Memory;

/**
 * M1 to MB transfer
 */
public class OB extends OperationWithPreShift {

	public OB(byte AD, byte OD, byte OF, BullGamma bullGamma) {
		super((byte)8, AD, OD, OF, bullGamma);
		if (AD == 0) {
			throw new InvalidInstructionError("80xx");
		}
	}

	void _exeInstructionLogic() {
		Memory m1 = this.bullGamma.getMemory(1);
		if (this.AD == 1) {
			m1.setToZero(this.OD, this.OF);
			return;
		}
		if (this.AD > 1){
			Memory mb = this.bullGamma.getMemory(this.AD);
			mb.copyBlockValues(m1, this.OD, this.OF);
			if (mb.blocks[this.OF - 1] == 0) {
				mb.blocks[this.OF - 1] = this.bullGamma.ms1;  // TODO check
			}
			return;
		}
		throw new InvalidInstructionExecutionError();
	}

	public String getDescription() {
		if (this.AD == 1) {
			return "Met M1 à zéro entre les position " + this.OD + " et " + this.OF;
		}
		if (this.AD > 1) {
			return "Effectue le décalage de M1 puis copie M1 entre les positions "
					+ this.OD + " et " + this.OF + " en M" + this.AD + " aux mêmes positions";
		}
		throw new InvalidInstructionDescriptionError();
	}

	public String getShortType() {
		return "OB";
	}

	public String getLongType() {
		return "Transfert de mémoire Opérateur en mémoire Banale";
	}
}
