package instructions;

import machine.BullGamma;

/**
 * jump to another Series
 */
public class VCS extends Instruction {
  VCS(byte AD, byte OD, byte OF, BullGamma bullGamma) {
	 super((byte)1, AD, OD, OF, bullGamma);

	  if (AD > 3) {
      throw new InvalidInstructionError("1" + Instruction.getChar(AD) + "xx");
    }
  }

  public void execute() {
    switch (this.AD) {
      case 0:
        // wait systematicaly for a drum transfer to finish
        // Not implemented
        break;
      case 1:
        // wait for a drum transfer to finish if jumping to the same series
        // not implemented
        bullGamma.rnl1 = (bullGamma.ns << 6) + bullGamma.nextLine();
        break;
      case 2:
        // wait for a drum transfer to finish if jumping to the same series
        // not implemented
        bullGamma.rnl2 = (bullGamma.ns << 6) + bullGamma.nextLine();
        break;
      case 3:
        // wait for a drum transfer to finish if jumping to the same series
        // not implemented
        break;
      default:
        throw new InvalidInstructionExecutionError();
    }
    bullGamma.ns = this.OF % 4;
    bullGamma.nl = (this.OD << 2) + (this.OF >> 2);
  }

  public String getDescription() {
    String action = "Saute à la ligne " + ((this.OD << 2) + (this.OF >> 2)) + " de la série " + (this.OF % 4);

    switch (this.AD) {
      case 0:
        return action;
      case 1:
        return action + " et retient la ligne de laquelle on saute + 1 en RNL1";
      case 2:
        return action + " et retient la ligne de laquelle on saute + 1 en RNL2";
      case 3:
        return action;
      default:
        throw new InvalidInstructionDescriptionError();
    }
  }

  public String getShortType() {
    return "VCS";
  }

  public String getLongType() {
    return "Variante Changement de Serie";
  }

}
