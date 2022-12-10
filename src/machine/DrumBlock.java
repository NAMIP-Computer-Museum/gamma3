package machine;

/**
 * A DrumBlock is a set of NB_WORD_PER_DRUM_BLOCK that belongs to a DrumTrack
 */
public class DrumBlock {

	public static final int NB_HEX_VALUES_PER_DRUM_BLOCK = Constants.NB_HEX_VALUES_PER_DRUM_BLOCK;
	public static final int NB_WORD_PER_DRUM_BLOCK = Constants.NB_WORD_PER_DRUM_BLOCK;

	int id;
	DrumTrack track;
	Word[] words;

	/**
	 * constructs a new DrumBlock
	 * @param id the ID of this block
	 * @param track the track to which this block is attached
	 */
	public DrumBlock(int id, DrumTrack track) {
		this.id = id;
		this.track = track;
		this.words = new Word[NB_WORD_PER_DRUM_BLOCK];
		for (int w=0; w<NB_WORD_PER_DRUM_BLOCK; w++) {
			this.words[w] = new Word();
		}
	}

	/**
	 * Set the Word's content with hex values
	 * @param hexCode a String that represents the new hex values of this Word
	 */
	public void setContent(String hexCode) {
		// TODO
		/*
		hexCode = parse_hex_code(hexCode, NB_HEX_VALUES_PER_DRUM_BLOCK);
		for (let w=0; w<NB_WORD_PER_DRUM_BLOCK; w++) {
			this.words[w].setContent(hexCode.substr(w*NB_CHRS_PER_WORD, NB_CHRS_PER_WORD));
		} */
	}

	/**
	 * Returns the word corresponding to the id
	 * @param id from 0 to 15
	 */
	public Word getWord(int id) {
		return this.words[id];
	}

	public String toString() {
		StringBuffer sb=new StringBuffer();
		for (int w=0; w<NB_WORD_PER_DRUM_BLOCK; w++) {
			sb.append(this.words[w]);
			if (w % 4 == 3) {
				sb.append("\n");
			} else {
				sb.append("\t");
			}
		}
		return sb.toString();
	}

}
