package machine;

//const parse_hex_code = require('../tools/parseHex');

/**
 * A Word is a memory unit which contains, by default, NB_CHRS_PER_WORD hexadecimal values
 */
public class Word {

	public static final int NB_CHRS_PER_WORD=Constants.NB_CHRS_PER_WORD;
	public int nb_blocks;
	public byte[] blocks;

	/**
	 * constructs a new instance of Word
	 * @param nb_blocks the number of hexadecimal values held by this Word
	 */
	public Word(int nb_blocks) {
		this.nb_blocks = nb_blocks; // default value
		this.blocks = new byte[nb_blocks];
		this.resetWord();
	}
	
	public Word() {
		this(NB_CHRS_PER_WORD);
	}

	/**
	 * Set the Word's content with hex values
	 * @param hexCode a String that represents the new hex values of this Word
	 */
	public void setContent(String hexCode) { 
		// check TODO max length !
		for (int i = hexCode.length() - 1, j = 0; j < hexCode.length(); i--, j++) {
			this.blocks[i] = (byte)Integer.parseInt(hexCode.charAt(j)+"", 16); // TODO test
		}
	}

	public String toString() {
		StringBuffer sb=new StringBuffer(blocks.length);
		String s;
		for(int i=0, j=blocks.length-1 ; i<blocks.length; i++, j--) {
			sb.append(Integer.toHexString(blocks[j]).charAt(0)); // TODO test
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * Set every memory block in range to 0
	 * @param from start index of the selected memory blocks, should be positive or zero
	 * @param to end index (excluded) of the selected memory blocks, should be inferior to NB_CHRS_PER_WORD
	 */
	public void resetWord() {
		for (int i = 0; i < blocks.length; i++) {
			this.blocks[i] = 0;
		}
	}

	/**
	 * Copy the selected values from an other memory
	 * If the calculator is in decimal mode, only ten's complement values will be copied
	 * @param other the other memory from which values will be copied
	 * @param from which block index should the copy start from, should be positive and inferior to 12
	 * @param to where should the copy end (excluded), should be inferior or equal to 12
	 */
	public void copy(Word other) {
		for (int i = 0; i < this.blocks.length; i++) {
			this.blocks[i] = other.blocks[i];
		}
	}

}
