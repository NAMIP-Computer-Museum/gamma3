package instructions;

import machine.BullGamma;
import machine.MemoryMode;

/**
 * switch to binary mode
 */
public class CB extends Instruction {
	
  public CB(byte OD, byte OF, BullGamma bullGamma) {
    super((byte)1, (byte)15, OD, OF, bullGamma);
  }

  public void execute() {
    this.bullGamma.setMemoryMode(MemoryMode.BINARY);
  }

  public String getDescription() {
    return "Selectionne le mode de calcul binaire";
  }

  public String getShortType() {
    return "CB";
  }

  public String getLongType() {
    return "Calcul Binaire";
  }

}
