package instructions;

import machine.BullGamma;
import machine.Constants;
import machine.Group;

/**
 * Group to Group transfer
 */
public class GG extends Instruction {

	public static final int NB_OCTADS_PER_GROUP = Constants.NB_OCTADS_PER_GROUP;
	public static final int NB_MEMORIES_PER_OCTAD = Constants.NB_MEMORIES_PER_OCTAD;

	public GG(byte OD, byte OF, BullGamma bullGamma) {
		super((byte)5, (byte)0, OD, OF, bullGamma); // TODO check move
		if ((OD > 3 && OD < 8) || OF > 3) {
			throw new InvalidInstructionError("50" + Instruction.getChar(OD) + Instruction.getChar(OF));
		}
	}

	public void execute() {
		if (this.OD >= 8 && this.OF < 4) {
			try {
				this.bullGamma.getGroup(this.OF).setContent(""); // TODO check exception handling
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		if (this.OD < 4 && this.OF < 4) {
			Group gsrc = this.bullGamma.getGroup(this.OD);
			Group gdest = this.bullGamma.getGroup(this.OF);
			for (int i=0; i<NB_OCTADS_PER_GROUP*NB_MEMORIES_PER_OCTAD; i++) {
				gdest.getWord(i).copy(gsrc.getWord(i));
			}
			return;
		}
		throw new InvalidInstructionExecutionError();
	}

	public String getDescription() {
		if (this.OD >= 8 && this.OF < 4) {
			return "Met à 0 les octades " + (this.OF << 1) + " et " + ((this.OF << 1) | 0x1);
		}
		if (this.OD < 4 && this.OF < 4) {
			return "Copie le contenu des octades " + ((this.OD & 0x7) << 1) + " et " + (((this.OD & 0x7) << 1) | 0x1)
					+ " dans les octades " + ((this.OF & 0x7) << 1) + " et " + (((this.OF & 0x7) << 1) | 0x1);
		}
		throw new InvalidInstructionDescriptionError();
	}

	public String getShortType() {
		return "GG";
	}

	public String getLongType() {
		return "Groupe - Groupe";
	}
}