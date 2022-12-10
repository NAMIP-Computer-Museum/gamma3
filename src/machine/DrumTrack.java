package machine;

/**
 * A DrumTrack is a set of NB_BLOCKS_PER_DRUM_TRACK DrumBlock that is part of a DrumTrackGroup
 */
public class DrumTrack {

	public static final int NB_BLOCKS_PER_DRUM_TRACK = Constants.NB_BLOCKS_PER_DRUM_TRACK;
	public static final int NB_HEX_VALUES_PER_DRUM_BLOCK = Constants.NB_HEX_VALUES_PER_DRUM_BLOCK;
	public static final int NB_HEX_VALUES_PER_DRUM_TRACK = Constants.NB_HEX_VALUES_PER_DRUM_TRACK;

	int id;
	DrumTrackGroup trackGroup;
	DrumBlock[] blocks;
	
	/**
	 * constructs a new instance of DrumTrack
	 * @param id the ID of this Track
	 * @param trackGroup the TrackGroup to which this track belongs
	 */
	public DrumTrack(int id, DrumTrackGroup trackGroup) {
		this.id = id;
		this.trackGroup = trackGroup;
		this.blocks = new DrumBlock[NB_BLOCKS_PER_DRUM_TRACK];
		for (int i = 0; i < NB_BLOCKS_PER_DRUM_TRACK; ++i) {
			this.blocks[i] = new DrumBlock(i, this);
		}
	}

	/**
	 * Set the Word's content with hex values
	 * @param hexCode a String that represents the new hex values of this Word
	 */
	public void setContent(String hexCode) {
		// TODO
		/*
		hexCode = parse_hex_code(hexCode, NB_HEX_VALUES_PER_DRUM_TRACK);
		for (let i = 0; i < NB_BLOCKS_PER_DRUM_TRACK; ++i) {
			this.blocks[i].setContent(hexCode.substr(i*NB_HEX_VALUES_PER_DRUM_BLOCK, NB_HEX_VALUES_PER_DRUM_BLOCK));
		} */
	}

	public String toString() {
		StringBuffer sb=new StringBuffer();
		for (int t = 0; t < NB_BLOCKS_PER_DRUM_TRACK; ++t) {
			sb.append(this.blocks[t].toString() + "\n");
		}
		return sb.toString();
	}

	public DrumBlock getBlock(int i) {
		return blocks[i];
	}
}
