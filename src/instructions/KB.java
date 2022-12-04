package instructions;

import machine.BullGamma;

/**
 * Constant to memory
 */
public class KB extends Instruction {

	public KB(byte AD, byte OD, byte OF, BullGamma bullGamma) {
		super((byte)4, AD, OD, OF, bullGamma);
	}

	public void execute() {
		if (this.AD == 0) {
			/*      for (let machine of this.bullGamma.connectedMachines) {
        machine.on48V();
      } */
			throw new MethodNotImplementedError("_exeInstructionLogic"); // TODO
		} else {
			this.bullGamma.getMemory(this.AD).setBlockValue(this.OD, this.OF);
		}
	}

	public String getDescription() {
		if (this.AD == 0) {
			return "Emission de 48V";
		}
		return "Ecrit la constante " + this.OF + " en position " + this.OD
				+ " de M" + this.AD;
	}

	public String getShortType() {
		if (this.AD == 0) {
			return "48V";
		}
		return "KB";
	}

	public String getLongType() {
		if (this.AD == 0) {
			return "Emission de 48V";
		}
		return "Transfert d'une Constante en mémoire Banale";
	}

}
