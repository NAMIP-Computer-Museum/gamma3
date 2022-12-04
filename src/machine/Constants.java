package machine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constants {
	
	// Octads constants
	public static final int NB_COMMUTED_OCTADS = 8; // Number of commuted octads
	public static final int NB_MEMORIES_PER_OCTAD = 8; // Number of memories per octad
	public static final int NB_MEMORIES_PER_HALF_OCTAD = NB_MEMORIES_PER_OCTAD / 2; // Number of memories per half octad

	// Groups constants
	public static final int NB_GROUPS = 4; // Number of octads groups
	public static final int NB_OCTADS_PER_GROUP = 2; // Number of octads per group

	// Series constants
	public static final int NB_GENERAL_SERIES = 3; // Number of general series (series 3 excluded)
	public static final int NB_SERIES = NB_GENERAL_SERIES + 1; // Number of series (series 3 included)
	public static final int NB_INST_SERIES_3 = 64; // Number of instructions on the connections array (series 3)
	public static final int NB_INST_PER_MEM = 4; // Number of instructions per memory (3 + 1 blank)
	public static final int NB_INST_PER_SERIE = NB_OCTADS_PER_GROUP * NB_MEMORIES_PER_OCTAD * NB_INST_PER_MEM; // Number of instructions per series (64 with 1/4 blank)
	public static final int NB_HEX_VALUES_PER_INST = 4; // Number of hex digits per instruction

	// Memories constants
	public static final int NB_BANAL_MEMORIES = 8; // Number of banal memories
	public static final int NB_OTHER_MEMORIES = NB_MEMORIES_PER_OCTAD * NB_COMMUTED_OCTADS; // Number of other memories (total in octads = 64)

	// Computer sizes constants
	public static final int NB_CHRS_PROGRAM_COUNTER = 2; // Number of hex digits for program counter
	public static final int NB_CHRS_PER_WORD = 12; // Number of hex digits per memory / word -- assumed less than 156 (byte)
	public static final int NB_HEX_VALUES_PER_OCTAD = NB_CHRS_PER_WORD * NB_MEMORIES_PER_OCTAD; // Number of hex digits per octad

	// Drum constants
	public static final int NB_WORD_PER_DRUM_BLOCK = 16; // Number of memories / words per drum block
	public static final int NB_BLOCKS_PER_DRUM_TRACK = 8; // Number of blocks per drum track
	public static final int NB_TRACKS_PER_DRUM_TRACK_GROUP = 16; // Number of tracks per tracks group
	public static final int NB_TRACK_GROUPS = 8; // Number of tracks groups

	// Drum sizestatic s constants
	public static final int NB_HEX_VALUES_PER_DRUM_BLOCK = NB_CHRS_PER_WORD * NB_WORD_PER_DRUM_BLOCK;
	public static final int NB_HEX_VALUES_PER_GROUP = NB_HEX_VALUES_PER_DRUM_BLOCK;
	public static final int NB_HEX_VALUES_PER_DRUM_TRACK = NB_HEX_VALUES_PER_DRUM_BLOCK * NB_BLOCKS_PER_DRUM_TRACK;
	public static final int NB_HEX_VALUES_PER_DRUM_TRACK_GROUP = NB_HEX_VALUES_PER_DRUM_TRACK * NB_TRACKS_PER_DRUM_TRACK_GROUP;
	public static final int NB_HEX_VALUES_PER_MAGNETIC_DRUM = NB_HEX_VALUES_PER_DRUM_TRACK_GROUP * NB_TRACK_GROUPS;
}