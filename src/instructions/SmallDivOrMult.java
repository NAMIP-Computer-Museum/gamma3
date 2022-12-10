package instructions;

import machine.BullGamma;
import machine.Memory;
import machine.MemoryMode;

/**
 * Abstract class for reduced multiplication or division instructions
 */
public abstract class SmallDivOrMult extends Operation {
	
  public SmallDivOrMult(byte TO, byte AD, byte OD, byte OF, BullGamma bullGamma) {
    super(TO, AD, OD, OF, bullGamma);
  }

  /**
   * Abstract method, should compute M1 = M1 OP MB
   * @param mb
   * @protected
   */
  void _compute(Memory mb) {
    throw new MethodNotImplementedError("_compute");
  }

  /**
   * Abstract method, should compute M1 = M1 OP OF
   * @protected
   */
  void _computeValue() {
    throw new MethodNotImplementedError("_computeValue");
  }

  public void execute() {
    if (this.AD == 1) {
      throw new InvalidInstructionExecutionError();
    }

    this.bullGamma.md = this.OD;
    int nb_neg_signs = 0;
    if (this.bullGamma.ms1 == 10) {
      nb_neg_signs++;
    }

    if (this.AD == 0) {
      this._computeValue();
    }
    if (this.AD > 1) {
      Memory mb = this.bullGamma.getMemory(this.AD);
      if (this.bullGamma.getMemoryMode() == MemoryMode.DECIMAL && mb.blocks[this.OF - 1] == 10) {
        mb.blocks[this.OF - 1] = 0;
        this._compute(mb);
        mb.blocks[this.OF - 1] = 10;
        nb_neg_signs++;
      } else {
        this._compute(mb);
      }
    }

    if (this.bullGamma.getMemoryMode() == MemoryMode.DECIMAL) {
      if (nb_neg_signs % 2 == 0) {
        this.bullGamma.ms1 = 0;
      } else {
        this.bullGamma.ms1 = 10;
      }
    }
  }

}