package instructions;

import machine.BullGamma;
import machine.Constants;
import machine.Memory;
import machine.MemoryMode;

/**
 * MB to M1 transfer
 */
public class BO extends Operation {
	
  static final int NB_CHRS_PER_WORD = Constants.NB_CHRS_PER_WORD;
	
  public BO(byte AD, byte OD, byte OF, BullGamma bullGamma) {
    super((byte)6, AD, OD, OF, bullGamma);
  }

  public void execute() {
    Memory m1 = this.bullGamma.getMemory(1);

    // save memory value
    byte[] cp_block = new byte[NB_CHRS_PER_WORD];
    for (int i = this.OD; i < this.OF; i++) {
      cp_block[i] = m1.blocks[i];
    }

    // common
    m1.setToZero(0, 12);
    if (this.AD != 1) {
      this.bullGamma.ms1 = 0;
    }
    this.bullGamma.md = this.OD;

    // cases on AD
    switch (this.AD) {
      case 1:
        m1.setToZero(0, 12);
        for (int i = this.OD; i < this.OF; i++) {
          m1.blocks[i] = cp_block[i];
        }
        break;

      case 0:
        m1.blocks[this.OD] = this.OF;
        break;

      default:
        Memory mb = this.bullGamma.getMemory(this.AD);
        m1.copyBlockValues(mb, this.OD, this.OF);
        if (this.bullGamma.getMemoryMode() == MemoryMode.DECIMAL) {
          if (this.AD != 1 && mb.blocks[this.OF - 1] == 10) {
            this.bullGamma.ms1 = 10;
            m1.blocks[this.OF - 1] = 0;
          }
        }
        break;

    }
  }

  public String getDescription() {
    switch (this.AD) {
      case 1:
        return "Efface M1 à l'extérieur des positions " + this.OD + " et " + this.OF
        + ", la mémoire de décalage prend la valeur " + this.OD;
      case 0:
        return "Efface M1 puis met " + this.OF + " en position " + this.OD
        + ", la mémoire de décalage prend la valeur " + this.OD;
      default:
        return "Efface M1 puis met M" + this.AD
        + " entre les positions " + this.OD + " et " + this.OF + " en M1"
        + ", la mémoire de décalage prend la valeur " + this.OD;
    }
  }

  public String getShortType() {
    return "BO";
  }

  public String getLongType() {
    return "Transfert de mémoire Banale en mémoire Opérative";
  }

}
