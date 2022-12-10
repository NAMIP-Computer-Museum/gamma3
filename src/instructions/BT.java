package instructions;

import machine.BullGamma;
import machine.Constants;
import machine.DrumTrackGroup;

/**
 * Group to drum transfer
 */
public class BT extends DrumTransfer {


	public static final int NB_WORD_PER_DRUM_BLOCK = Constants.NB_WORD_PER_DRUM_BLOCK;


	public BT(byte AD, byte OD, byte OF, BullGamma bullGamma) {
		super((byte)2, AD, OD, OF, bullGamma);
	}

	public void _transfer(int nbGroup, DrumTrackGroup trackGroup, int nbTrack, int nbBlock) {
		for (int  w=0; w<NB_WORD_PER_DRUM_BLOCK; w++) {
			trackGroup.getTrack(nbTrack).getBlock(nbBlock).getWord(w).copy(
					this.bullGamma.getGroup(nbGroup).getWord(w)
					);
		}
	}

	public String getDescription() {
		return "Transfère le contenu des octades " + (this.AD & 0xE) + " et " + (this.AD | 0x1)
				+ " dans le bloc " + (this.OF >> 1) + " de la piste " + this.OD
				+ " de la seizaine " + (this.AD % 2 == 1 ? "commutée" : "0")
				+ " du tambour";
	}

	public String getShortType() {
		return "BT";
	}

	public String getLongType() {
		return "Transfert de mémoire Banal au Tambour";
	}

}
