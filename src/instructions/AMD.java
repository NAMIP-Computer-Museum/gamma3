package instructions;

import machine.BullGamma;

/**
 * Shift memory (MD) affectation MD := OF
 */
public class AMD extends Instruction {
	
  public AMD(byte OD, byte OF, BullGamma bullGamma) {
    super((byte)7, (byte)0, OD, OF, bullGamma);
  }

  public void execute() {
    this.bullGamma.md = this.OF;
  }

  public String getDescription() {
    return "Met la mémoire de décalage à " + this.OF;
  }

  public String getShortType() {
    return "AMD";
  }

  public String getLongType() {
    return "Alteration de la Mémoire de Décalage";
  }

}