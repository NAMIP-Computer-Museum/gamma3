package instructions;

import machine.BullGamma;
import machine.ConnectedMachine;

public class ES extends Instruction {
	
  public ES(byte AD, byte OD, byte OF, BullGamma bullGamma) {
	super((byte)1, AD, OD, OF, bullGamma); // TODO check pos
	    
    if (AD != 8 && AD != 9) {
      throw new InvalidInstructionError("1" + Instruction.getChar(AD) + "xx");
    }

  }

  public void execute() {
    switch (this.AD) {
      case 8:
        for (ConnectedMachine machine:this.bullGamma.getConnectedMachines()) {
          machine.onStaticExtraction1(this.OD, this.OF);
        }
        break;
      case 9:
        for (ConnectedMachine machine:this.bullGamma.getConnectedMachines()) {
          machine.onStaticExtraction2(this.OD, this.OF);
        }
        break;
      default:
        throw new InvalidInstructionExecutionError();
    }
  }

  public String getDescription() {
    if (this.AD == 8 || this.AD == 9) {
      return "Envoie des données à une machine connectée";
    }
    throw new InvalidInstructionDescriptionError();
  }

  public String getShortType() {
    return "ES" + (this.AD - 7);
  }

  public String getLongType() {
    return "Extraction Statique";
  }

}
