package machine;

/**
 * A DrumTrackGroup is a set of 16 (NB_TRACKS_PER_DRUM_TRACK_GROUP) DrumTracks attached to a Drum
 * Note: I was unable to find a proper translation for the word "Seizaine" in English which is equivalent to "dozen"
 * or "decade" but with 16.
 */
public class DrumTrackGroup {

	public static final int NB_TRACKS_PER_DRUM_TRACK_GROUP = Constants.NB_TRACKS_PER_DRUM_TRACK_GROUP;
	public static final int NB_HEX_VALUES_PER_DRUM_TRACK = Constants.NB_HEX_VALUES_PER_DRUM_TRACK;
	public static final int NB_HEX_VALUES_PER_DRUM_TRACK_GROUP = Constants.NB_HEX_VALUES_PER_DRUM_TRACK_GROUP;

	int id;
	MagneticDrum drum;
	DrumTrack[] tracks;

	/**
	 * constructs a new instance of DrumTrackGroup
	 * @param id the id for this DrumTrackGroup
	 * @param drum the Drum to which this track group belongs
	 */
	public DrumTrackGroup(int id, MagneticDrum drum) {
		this.id = id;
		this.drum = drum;
		this.tracks = new DrumTrack[NB_TRACKS_PER_DRUM_TRACK_GROUP];
		for (int i = 0; i < NB_TRACKS_PER_DRUM_TRACK_GROUP; ++i) {
			this.tracks[i] = new DrumTrack(i, this);
		}
	}


	/**
	 * Set the Word's content with hex values
	 * @param hexCode a String that represents the new hex values of this Word
	 */
	public void setContent(String hexCode) {
		// TODO
		/*
		hexCode = parse_hex_code(hexCode, NB_HEX_VALUES_PER_DRUM_TRACK_GROUP);
		for (let i = 0; i < NB_TRACKS_PER_DRUM_TRACK_GROUP; ++i) {
			this.tracks[i].setContent(hexCode.substr(i*NB_HEX_VALUES_PER_DRUM_TRACK, NB_HEX_VALUES_PER_DRUM_TRACK));
		} */
	}

	public String toString() {
		StringBuffer sb=new StringBuffer();
		for(DrumTrack track:tracks) {
			sb.append(track + "\n");
		}
		return sb.toString();
	}

	public DrumTrack getTrack(int i) {
		return tracks[i];
	}
	
}