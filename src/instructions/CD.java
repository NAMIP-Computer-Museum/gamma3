package instructions;

import machine.BullGamma;
import machine.MemoryMode;

/**
 * switch to decimal mode
 */
public class CD extends Instruction {
	
  public CD(byte OD, byte OF, BullGamma bullGamma) {
    super((byte)1, (byte)10, OD, OF, bullGamma);
  }

  public void execute() {
    this.bullGamma.setMemoryMode(MemoryMode.DECIMAL);
  }

  public String getDescription() {
    return "Selectionne le mode de calcul décimal";
  }

  public String getShortType() {
    return "CD";
  }

  public String getLongType() {
    return "Calcul Décimal";
  }

}
