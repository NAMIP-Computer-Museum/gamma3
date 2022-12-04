package instructions;

import machine.BullGamma;
import machine.Memory;

/**
 * Logical intersection instruction  (AND)
 */
public class IL extends Operation {

	public IL(byte AD, byte OD, byte OF, BullGamma bullGamma) {
		super((byte)7, AD, OD, OF, bullGamma); // TODO check move

		if (AD != 10 && AD != 12) {
			throw new InvalidInstructionError("7" + Instruction.getChar(AD) + "xx");
		}
	}

	public void execute() {
		Memory  m1 = this.bullGamma.getMemory(1);
		if (this.AD == 10) {
			for (int i=0; i<m1.blocks.length; i++) {
				m1.blocks[i] = (byte)(m1.blocks[i] & this.OF);  // TODO check
			}
			return;
		}
		if (this.AD == 12) {
			Memory m2 = this.bullGamma.getMemory(2);
			assert (m1.blocks.length == m2.blocks.length) : "M1 and M2 should be of same length";
			for (int i=0; i<m1.blocks.length; i++) {
				m1.blocks[i] = (byte)(m1.blocks[i] & m2.blocks[i]);
			}
			return;
		}
		throw new InvalidInstructionExecutionError();
	}

	public String getDescription() {
		if (this.AD == 10) {
			return "Calcule un 'et' logique entre chaque position de M1 et " + this.OF;
		}
		if (this.AD == 12) {
			return "Calcule un 'et' logique entre M1 et M2";
		}
		throw new InvalidInstructionDescriptionError();
	}

	public String getShortType() {
		return "IL";
	}

	public String getLongType() {
		return "Intersection Logique";
	}
}
