package instructions;

import machine.BullGamma;
import machine.Constants;
import machine.DrumTrackGroup;

/**
 * drum to group transfer
 */
public class TB extends DrumTransfer {

	public static final int NB_WORD_PER_DRUM_BLOCK = Constants.NB_WORD_PER_DRUM_BLOCK;

	public TB(byte AD, byte OD, byte OF, BullGamma bullGamma) {
		super((byte)2, AD, OD, OF, bullGamma);
	}

	void _transfer(byte nbGroup, DrumTrackGroup trackGroup, byte nbTrack, byte nbBlock) {
		for (int w=0; w<NB_WORD_PER_DRUM_BLOCK; w++) {
			this.bullGamma.getGroup(nbGroup).getWord(w).copy(
					trackGroup.getTrack(nbTrack).getBlock(nbBlock).getWord(w));
		}
	}

	public String getDescription() {
		return "Transfère le contenu du bloc " + (this.OF >> 1)
				+ " de la piste " + this.OD
				+ " de la seizaine " + (this.AD % 2 == 1 ? "commutée" : "0")
				+ " du tambour dans les octades " + (this.AD & 0xE) + " et " + (this.AD | 0x1);
	}

	public String getShortType() {
		return "TB";
	}

	public String getLongType() {
		return "Transfert du Tambour en mémoire Banale";
	}

}