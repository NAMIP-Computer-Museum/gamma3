package instructions;

import machine.BullGamma;
import machine.DrumTrackGroup;

/**
 * Abstract class for drum transfer instructions
 */
public class DrumTransfer extends Instruction {

  public DrumTransfer(byte TO, byte AD, byte OD, byte OF, BullGamma bullGamma) {
    super(TO, AD, OD, OF, bullGamma);
  }

  public void _transfer(int nbGroup, DrumTrackGroup trackGroup, int nbTrack, int nbBlock) {
    throw new MethodNotImplementedError("_transfer");
  }

  public void execute() {
    DrumTrackGroup trackGroup = this.bullGamma.getMagneticDrum().getTrackGroup(0);
    if (this.AD % 2 == 1) {
      trackGroup = this.bullGamma.getMagneticDrum().getCommutedTrackGroup();
    }
    this._transfer(this.AD >> 1, trackGroup, this.OD, this.OF >> 1);
  }

}