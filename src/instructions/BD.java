package instructions;

import machine.BullGamma;

/**
 * M2 to MD transfer
 */
public class BD extends Instruction {
	
  public BD(byte OD, byte OF, BullGamma bullGamma) {
    super((byte)7, (byte)2, OD, OF, bullGamma);
  }

  public void execute() {
    this.bullGamma.md = this.bullGamma.getMemory(2).blocks[this.OD];
  }

  public String getDescription() {
    return "Met la valeur en position " + this.OD + " de M2 en mémoire de décalage";
  }

  public String getShortType() {
    return "BD";
  }

  public String getLongType() {
    return "Transfert de mémoire Banale en mémoire de Décalage";
  }

}
