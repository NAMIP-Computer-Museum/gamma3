package instructions;

import machine.BullGamma;
import machine.Memory;
import machine.MemoryMode;

/**
 * subtraction
 */
public class SN extends OperationWithPreShift {
	
  public SN(byte AD, byte OD, byte OF, BullGamma bullGamma) {
    super((byte)11, AD, OD, OF, bullGamma);
  }

  void _exeInstructionLogic() {
    Memory m1 = this.bullGamma.getMemory(1);

    switch (this.AD) {
      case 0:
        Memory mTmp = new Memory(1, this.bullGamma);
        mTmp.blocks[this.OD] = this.OF;
        this.bullGamma.getMemory(1).subtract(mTmp);
        return;

      case 1:
        m1.setToZero(this.OD, this.OF);

        // change sign if complete reset in decimal mode
        if (this.bullGamma.getMemoryMode() == MemoryMode.DECIMAL) {
          if (this.bullGamma.ms1 == 10) {
            this.bullGamma.ms1 = 0;
          } else {
            this.bullGamma.ms1 = 10;
          }
        }
        return;

      default: // use of MB
        Memory mb = this.bullGamma.getMemory(this.AD);
        if (this.bullGamma.getMemoryMode() == MemoryMode.DECIMAL) {
          boolean m1positive = this.bullGamma.ms1 != 10; // Whether M1 positive
          boolean mBpositive = mb.blocks[this.OF - 1] != 10; // whether MB positive

          // Add or subtract positive integers depending on operand signs
          if (m1positive) {
            if (mBpositive) {
              m1.subtract(mb, this.OD, this.OF);
            } else {
              mb.blocks[this.OF - 1] = 0;
              m1.add(mb, this.OD, this.OF);
              mb.blocks[this.OF - 1] = 10;
            }
          } else {
            if (mBpositive) {
              m1.add(mb, this.OD, this.OF);
              this.bullGamma.ms1 = 10;
            } else {
              // -M1 + MB <=> MB - M1, so MB gets copied to M1 and a buffer for M1 is used
              Memory cpM1 = new Memory(1, this.bullGamma);
              cpM1.copyBlockValues(m1, this.OD, this.OF);
              m1.copyBlockValues(mb, this.OD, this.OF);
              m1.blocks[this.OF - 1] = 0;
              m1.subtract(cpM1, this.OD, this.OF);
            }
          }
        } else {
          m1.subtract(mb, this.OD, this.OF);
          return;
        }
    }
  }

  public String getDescription() {
    if (this.AD == 0) {
      return "Effectue le décalage de M1, soustrait M1 à " + this.OF
      + " en position " + this.OD
      + ", puis met le résultat dans M1";
    } else {
      return "Effectue le décalage de M1, soustrait M1 à M" + this.AD
      + " entre les positions" + this.OD + " et " + this.OF
      + ", puis met le résultat dans M1";
    }
  }

  public String getShortType() {
    return "SN";
  }

  public String getLongType() {
    return "Soustraction";
  }

}
