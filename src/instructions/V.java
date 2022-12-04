package instructions;

import machine.BullGamma;

/**
 * V Operation
 */
public abstract class V extends Instruction {
  V(byte AD, byte OD, byte OF, BullGamma bullGamma) {
    super((byte)0, AD, OD, OF, bullGamma);
  }

  public String getShortType() {
    return "V";
  }

  public String getLongType() {
    return "Variante";
  }
}