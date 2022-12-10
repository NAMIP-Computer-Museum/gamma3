package instructions;

import machine.BullGamma;
import machine.Constants;
import machine.Memory;

//MethodNotImplementedError = require("./instruction").MethodNotImplementedError;
//InvalidInstructionExecutionError = require("./instruction").InvalidInstructionExecutionError;

class BigDivOrMult extends Operation {

	public static final int NB_CHRS_PER_WORD = Constants.NB_CHRS_PER_WORD;


	public BigDivOrMult(byte TO, byte AD, byte OD, byte OF, BullGamma bullGamma) {
		super(TO, AD, OD, OF, bullGamma);
	}

	/**
	 * Abstract method, should compute M1 = M1 OP MB
	 * @param mb
	 * @protected
	 */
	void _compute(Memory m1m2, Memory mb) {
		throw new MethodNotImplementedError("_compute");
	}

	/**
	 * Abstract method, should compute M1 = M1 OP OF
	 * @protected
	 */
	void _computeValue(Memory m1m2) {
		throw new MethodNotImplementedError("_computeValue");
	}

	public void execute() {
		if (this.AD == 1 || this.AD == 2) {
			throw new InvalidInstructionExecutionError();
		}

		Memory m1m2 = new Memory(0, this.bullGamma, NB_CHRS_PER_WORD*2);
		Memory m1 = this.bullGamma.getMemory(1);
		Memory m2 = this.bullGamma.getMemory(2);
		m1m2.copyBlockValues(m1, 0, NB_CHRS_PER_WORD);
		for (int i = 0; i < NB_CHRS_PER_WORD; ++i) {
			m1m2.shiftLeft();
		}
		m1m2.copyBlockValues(m2, 0, NB_CHRS_PER_WORD);
		if (this.bullGamma.md == 0) {
			this.bullGamma.md = 12;
		}

		if (this.AD == 0) {
			this._computeValue(m1m2);
		}
		if (this.AD > 2) {
			Memory mb = this.bullGamma.getMemory(this.AD);
			this._compute(m1m2, mb);
		}

		for (int i = 0; i < NB_CHRS_PER_WORD; ++i) {
			m1.blocks[i] = m1m2.blocks[i + NB_CHRS_PER_WORD];
			m2.blocks[i] = m1m2.blocks[i];
		}
	}
}
