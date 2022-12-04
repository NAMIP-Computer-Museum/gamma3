package instructions;

import machine.BullGamma;

/**
 * Abstract class for instructions with pre-shift
 */
public abstract class OperationWithPreShift extends Operation {

	public OperationWithPreShift(byte TO, byte AD, byte OD, byte OF, BullGamma bullGamma) {
		super(TO,AD,OD,OF,bullGamma);
	}
	
	public void execute() {
		if (this.AD != 0) {
			if (this.bullGamma.md - this.OD > 0){
				int shift_nb = this.bullGamma.md - this.OD;
				for (int i = 0; i < shift_nb; ++i) {
					this.bullGamma.md = (this.bullGamma.md - 1);
					this.bullGamma.getMemory(1).shiftRight();
				}
			} else {
				int shift_nb = this.OD - this.bullGamma.md;
				for (int i = 0; i < shift_nb; ++i) {
					this.bullGamma.getMemory(1).shiftLeft();
				}
			}
			this.bullGamma.md = this.OD;
		}
		this._exeInstructionLogic();
	}

	/**
	 * Abstract method, called by #execute() after the shift is done
	 * @protected
	 */
	void _exeInstructionLogic() {
		throw new MethodNotImplementedError("_exeInstructionLogic");
	}

}
