package instructions;

import machine.BullGamma;

/**
 * Magnetic drum track group selection
 */
public class CSz extends Instruction {
	
  public CSz(byte OD, byte OF, BullGamma bullGamma) {
    super((byte)1, (byte)13, OD, OF, bullGamma);
  }

  public void execute() {
    this.bullGamma.getMagneticDrum().setCommutedGroup(this.OF & 0x7);
  }

  public String getDescription() {
    return "Selectionne la seizaine commutée " + (this.OF & 0x7) + " du tambour";
  }

  public String getShortType() {
    return "CSz";
  }

  public String getLongType() {
    return "Commutation de Seizaine";
  }

}
