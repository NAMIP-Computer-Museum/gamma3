package instructions;

import machine.BullGamma;
import machine.Memory;

/**
 * Reduced Division
 */
public class DR extends SmallDivOrMult {
	
  public DR(byte AD, byte OD, byte OF, BullGamma bullGamma) {
	    super((byte)13, AD, OD, OF, bullGamma);

    if (AD == 1) {
      throw new InvalidInstructionError("d1xx");
    }
  }

  void _compute(Memory mb) {
    this.bullGamma.getMemory(1).divide(mb, this.OD, this.OF);
  }

  void _computeValue() {
    this.bullGamma.getMemory(1).divideValue(this.OF, this.OD);
  }

  public String getDescription() {
    if (this.AD == 0) {
      return "DR - Division Réduite\n"
      + "Divise le nombre contenu dans M1 par " + this.OF
      + " en position " + this.AD
      + ", le quotient est en M1 en position 0 et le reste en position MD";
    }
    if (this.AD > 1) {
      return "DR - Division Réduite\n"
      + "Divise le nombre contenu dans M1 par M" + this.AD
      + ", le quotient est en M1 en position 0 et le reste en position MD";
    }
    throw new InvalidInstructionDescriptionError();
  }

  public String getShortType() {
    return "DR";
  }

  public String getLongType() {
    return "Division Réduite";
  }

}
