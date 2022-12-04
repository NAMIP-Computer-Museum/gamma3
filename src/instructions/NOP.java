package instructions;

import machine.BullGamma;

/**
 * NO Operation
 */
public class NOP extends SL {
	
  public NOP(BullGamma bullGamma) {
    super((byte)0, (byte)0, (byte)0, bullGamma);
  }

  public void execute() {
    // do nothing
  }

  public String getDescription() {
    return "Ne fait rien";
  }

  // getShortType() {
  //   return "V";
  // }
  //
  // getLongType() {
  //   return "Variante";
  // }
}
