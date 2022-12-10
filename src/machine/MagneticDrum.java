package machine;



/**
 * A magnetic drum is an ancient storage device that was connected to the Bull Gamma
 */
public class MagneticDrum {

	public static final int NB_TRACK_GROUPS = Constants.NB_TRACK_GROUPS;
	public static final int NB_HEX_VALUES_PER_MAGNETIC_DRUM = Constants.NB_HEX_VALUES_PER_MAGNETIC_DRUM;
	public static final int NB_HEX_VALUES_PER_TRACK_GROUP = Constants.NB_HEX_VALUES_PER_DRUM_TRACK_GROUP;

	BullGamma bullGamma;
	DrumTrackGroup[] trackGroups;
	DrumTrackGroup commutedTrackGroup;

	/**
	 * Constructs a new instance of MagneticDrum
	 * @param bullGamma the machine to which this drum is attached
	 */
	public MagneticDrum(BullGamma bullGamma) {
		assert bullGamma!=null : "bullGamma must not be null or undefined";
		this.bullGamma = bullGamma;
		this.trackGroups = new DrumTrackGroup[NB_TRACK_GROUPS];
		for (int  i = 0; i < NB_TRACK_GROUPS; ++i) {
			this.trackGroups[i] = new DrumTrackGroup(i, this);
		}
		this.commutedTrackGroup = this.trackGroups[0];
	}

	public DrumTrackGroup getTrackGroup(int i) {
		return trackGroups[i];
	}

	public DrumTrackGroup getCommutedTrackGroup() {
		return commutedTrackGroup;
	}
	
	/**
	 * Set the Word's content with hex values
	 * @param hexCode a String that represents the new hex values of this Word
	 */
	public void setContent(String hexCode) {
		// TODO
		/*
		hexCode = parse_hex_code(hexCode, NB_HEX_VALUES_PER_MAGNETIC_DRUM);
		for (let i = 0; i < NB_TRACK_GROUPS; ++i) {
			this.trackGroups[i].setContent(hexCode.substr(i*NB_HEX_VALUES_PER_TRACK_GROUP, NB_HEX_VALUES_PER_TRACK_GROUP));
		}
		*/
	}

	public String toString() {
		StringBuffer sb=new StringBuffer();
		for(DrumTrackGroup trackGroup:trackGroups) {
			sb.append(trackGroup+ "\n");
		}
		return sb.toString();
	}

	public void setCommutedGroup(int id) {
		assert (id >= 0) && (id < NB_TRACK_GROUPS) : "id should not be negative or superior to " + NB_TRACK_GROUPS;
		this.commutedTrackGroup = this.trackGroups[id];
	}

}