package instructions;

import machine.BullGamma;
import machine.DrumTrackGroup;

/**
 * Abstract class for drum transfer instructions
 */
public class TT extends Instruction { // TODO check name DrumTransfer

	public TT(byte TO, byte AD, byte OD, byte OF, BullGamma bullGamma) {
		super(TO, AD, OD, OF, bullGamma);
	}

	void _transfer(byte nbGroup, DrumTrackGroup trackGroup, byte nbTrack, byte nbBlock) {
		throw new MethodNotImplementedError("_transfer");
	}

	public void execute() {
		DrumTrackGroup trackGroup = this.bullGamma.getMagneticDrum().getTrackGroup(0);
		if (this.AD % 2 == 1) {
			trackGroup = this.bullGamma.getMagneticDrum().getCommutedTrackGroup();
		}
		this._transfer((byte)(this.AD >> 1), trackGroup, this.OD, (byte)(this.OF >> 1));
	}

}