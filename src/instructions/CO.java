package instructions;

import machine.BullGamma;

/**
 * octad selection
 */
public class CO extends Instruction {
	
  public CO(byte OD, byte OF, BullGamma bullGamma) {
	super((byte)1, (byte)12, OD, OF, bullGamma);
    if (OF > 7) {
      throw new InvalidInstructionError("1c0" + this.getChar(OF));
    }
  }

  public void execute() {
    if (this.OF < 8) {
      this.bullGamma.setCommutedOctad(this.OF);
      return;
    }
    throw new InvalidInstructionExecutionError();
  }

  public String getDescription() {
    if (this.OF < 8) {
      return "Selectionne l'octade commutée " + this.OF;
    }
    throw new InvalidInstructionDescriptionError();
  }

  public String getShortType() {
    return "CO";
  }

  public String getLongType() {
    return "Commutation d'Octade";
  }

}