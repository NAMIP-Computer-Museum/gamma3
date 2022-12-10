package machine;

/**
 * A Memory is Word directly connected to the Bullgamma (not through an Octad or a DrumBlock). It possess some
 * computation methods that makes it easier to manipulate for instructions.
 */
public class Memory extends Word {

	public static final int NB_CHRS_PER_WORD = Constants.NB_CHRS_PER_WORD;
	//	public static final int MEMORY_MODE = Constants.MEMORY_MODE; // TODO

	int id;
	BullGamma bullGamma;

	/**
	 * constructs a new instance of Memory
	 * @param id the ID of this memory
	 * @param bullGamma the machine attached to this memory
	 * @param nb_blocks the number of blocks for this memory (same as Word)
	 */
	public Memory(int id, BullGamma bullGamma, int nb_blocks) {
		super(nb_blocks);
		this.id = id;
		this.bullGamma = bullGamma;
	}

	public Memory(int id, BullGamma bullGamma) {
		this(id,bullGamma, NB_CHRS_PER_WORD);
	}

	/**
	 * @returns {MEMORY_MODE} the computation mode for this Memory
	 */
	public MemoryMode getMode() {
		return this.bullGamma.getMemoryMode();
	}

	/**
	 * Set every memory block in range to 0
	 * @param from start index of the selected memory blocks, should be positive or zero
	 * @param to end index (excluded) of the selected memory blocks, should be inferior to NB_CHRS_PER_WORD
	 */
	public void setToZero(int from, int to) {
		assert from >= 0 : "from parameter should be superior to 0";
		assert to <= this.blocks.length : "to parameter should be inferior to " + this.blocks.length;

		for (int i = from; i < to; i++) {
			this.blocks[i] = 0;
		}
	}

	/**
	 * Set the selected memory block to the given value.
	 * If in decimal mode and value is > 9, the value's digits are split then the lower one goes to blocks[idx] while
	 * blocks[idx + 1] gets the higher one.
	 * @param idx the idx of the the block that should be set, must be positive or zero but inferior to
	 * NB_CHRS_PER_WORD
	 * @param value the value to which the block should be set, must be positive or zero and inferior to 16.
	 */
	public void setBlockValue(int idx, byte value) { // TODO check byte value
		assert idx < this.blocks.length : "idx should be inferior to " + this.blocks.length;
		assert idx >= 0 : "idx should be not be negative";
		assert value >= 0 : "value should not be negative";
		assert value < 16 :"value should be inferior to 16";

		if (value >= this.getMode().base) {
			this.blocks[(idx + 1) % this.blocks.length] = 1; // FIXME : += 1 ?
		}
		this.blocks[idx] = (byte)(value % this.getMode().base);
		byte a,b,c;
	}

	/**
	 * Copy the selected values from an other memory
	 * If the calculator is in decimal mode, only ten's complement values will be copied
	 * @param other the other memory from which values will be copied
	 * @param from which block index should the copy start from, should be positive and inferior to 12
	 * @param to where should the copy end (excluded), should be inferior or equal to 12
	 */
	public void copyBlockValues(Memory other, int from, int to) { // int from =0, int to=this.blocks.length
		assert from >= 0 : "from should be positive";
		assert to <= this.blocks.length : "to should be inferior or equal to " + this.blocks.length;

		for (int i = from; i < to; i++) {
			this.blocks[i] = (byte)(other.blocks[i] % this.getMode().base);
		}
	}

	/**
	 * Every block in the memory gets the value of its right neighbour (index 0 gets value of index 11)
	 */
	public void shiftLeft() {
		byte buff = this.blocks[this.blocks.length - 1];
		for (int i = this.blocks.length - 1; i > 0; --i) {
			this.blocks[i] = this.blocks[i - 1];
		}
		this.blocks[0] = buff;
	}

	/**
	 * Every block in the memory gets the value of its left neighbour (index 11 gets value of index 0)
	 */
	public void shiftRight() {
		byte buff = this.blocks[0];
		for (int i = 0 ; i < this.blocks.length - 1; ++i) {
			this.blocks[i] = this.blocks[i + 1];
		}
		this.blocks[this.blocks.length- 1] = buff;
	}

	// /**
	//  * compare the whole memory to another one between selected blocks
	//  * @param other the other memory to which it should be compared
	//  * @param from the starting block from which the comparison should start
	//  * @param to the end block of the comparison (excluded)
	//  * @return an array of two booleans, index 0 is true if this is greater than other, index 1 is true if they are equal
	//  */
	// compareTo(other, from, to) {
	//   assert.equal(from >= 0, true, "from should not be negative");
	//   assert.equal(from < to, true, "from should be inferior to to");
	//   assert.equal(to <= this.blocks.length, true, "to should be inferior to the number of blocks per memory");
	//   let nbDigitsThis = this.blocks.length;
	//   while (this.blocks[nbDigitsThis - 1] === 0 && nbDigitsThis > 0) {
	//     --nbDigitsThis;
	//   }
	//   let nbDigitsOther = to - from;
	//   while (this.blocks[from + nbDigitsOther - 1] === 0 && nbDigitsOther > 0) {
	//     --nbDigitsOther;
	//   }
	//   if (nbDigitsThis > nbDigitsOther) {
	//     return [true, false];
	//   }
	//   if (nbDigitsThis < nbDigitsOther) {
	//     return [false, false];
	//   }
	//   for (let i = 0; i < nbDigitsThis; ++i) {
	//     if (this.blocks[nbDigitsThis - i - 1] > other.blocks[to - i - 1]) {
	//       return [true, false];
	//     }
	//     if (this.blocks[nbDigitsThis - i - 1] < other.blocks[to - i - 1]) {
	//       return [false, false];
	//     }
	//   }
	//   return [false, true];
	// }

	// TODO add operations HERE

	/**
	 * add the given memory to this one
	 * @param other the memory that should be added
	 * @param from index of the block from which the addition should start
	 * @param to index of the block to which the addition should end (excluded)
	 * @param overriding_carry if true, at the end of the addition, the resulting carry out will override the next
	 * memory block if it is not null. Otherwise it will be added to the next memory block.
	 */
	public void add(Memory other, byte from, byte to, boolean overriding_carry) { 
		assert (from >= 0) : "from should not be negative";
		assert (from < to):  "from should be inferior to "+ to;
		assert (to <= this.blocks.length) : "to should be inferior to the number of blocks per memory";
		int carry = 0;
		for (int i = from; (i < to) || ((carry == 1) && !overriding_carry); i++) { // TODO check precedence
			int other_val = i < to ? other.blocks[i] : 0;
			int res = this.blocks[i%this.blocks.length + (this.blocks.length - NB_CHRS_PER_WORD)] + other_val + carry;
			if (res >= this.getMode().base) {
				carry = 1;
				res -= this.getMode().base;
			} else {
				carry = 0;
			}
			this.blocks[i%this.blocks.length + (this.blocks.length - NB_CHRS_PER_WORD)] = (byte)res;
		}
		if (overriding_carry && (carry!=0)) {
			this.blocks[to%this.blocks.length] += carry;
		}
	}

	public void add(Memory other, byte from, byte to) {  // TODO check why true (seems ok)
		add(other,from,to,true);
	}

	/**
	 * Add a value to any block of the memory, carrying out the result if in decimal mode.
	 * @param value the value to add, must be between -16 and 16
	 * @param at the block index to which the value should be added
	 */
	public void addValue(byte value, int at) {
		assert (value > -16) : "value should be superior to -16";
		assert (value < 16) : "value should be inferior to 16";
		assert (at < this.blocks.length) : "at should be inferior to the number of blocks per memory";
		this.blocks[at] = (byte)Math.abs(this.blocks[at] + value);
		for (int i = at; this.blocks[i] >= this.getMode().base; i = (i + 1)%this.blocks.length) {
			this.blocks[(i + 1) % this.blocks.length]++;
			this.blocks[i] -= this.getMode().base;
		}
	}

	/**
	 * return the unsigned decimal value of this memory between the selected blocks (decimal mode only)
	 * @param from the starting block from which the value should be computed
	 * @param to the ending block (excluded)
	 * @return {number} the computed value
	 */
	public long getDecimalValue(byte from, byte to) {
		assert (from >= 0) : "from should not be negative";
		assert (from < to) : "from should be smaller than to";
		assert (to <= this.blocks.length) : "to should not be greater than the number of blocks per memory";
		long val = 0;
		long mult = 1;
		for (int i = from; i < to; ++i) {
			val += this.blocks[i]*mult;
			mult *= this.getMode().base;
		}
		return val;
	}

	/**
	 * set the value of the memory between the selected blocks
	 * @param value
	 * @param from
	 * @param to
	 */
	public void setDecimalValue(long value, byte from, byte to) {
		assert (from >= 0) : "from should not be negative";
		assert (from < to) : "from should be smaller than to";
		assert (value >= 0) : "value should be an absolute value";
		assert (to <= this.blocks.length) : "to should not be greater than the number of blocks per memory";
		System.out.println("SDV: "+value+" "+from+" "+to);
		String sval=Long.toString(value, this.getMode().base);
		System.out.println(sval);
		int i = 0;
		for (; i + from < to && i < sval.length(); ++i) {
			System.out.println("i:"+i);
			this.blocks[from + i] = (byte)Integer.parseInt(sval.charAt(sval.length()-i-1)+"", this.getMode().base); // TODO check
		}
		for (; i + from < to ; ++i) {
			this.blocks[from + i] = (byte)0;
		}
	}

	/**
	 * subtract the given memory to this one
	 * @param other the memory that should be subtracted
	 * @param from index of the block from which the subtraction should start
	 * @param to index of the block to which the subtraction should end (excluded)
	 */
	public void subtract(Memory other, byte from, byte to, byte this_from, byte this_to) {
		assert (from >= 0) : "from should not be negative";
		assert (from < to) : "from should be inferior to to";
		assert (to <= this.blocks.length) : "to should be inferior to the number of blocks per memory";
		long valM1 = this.getDecimalValue(this_from, this_to) - other.getDecimalValue(from, to);
		this.setDecimalValue(Math.abs(valM1), this_from, this_to);
		if (valM1 < 0 && this.getMode() == MemoryMode.DECIMAL) {
			this.bullGamma.ms1 = 10;
		}
	}

	public void subtract(Memory other, byte from, byte to) {
		subtract(other,from,to,from,to);
	}

	public void subtract(Memory other) {
		subtract(other, (byte)0, (byte)this.blocks.length, (byte)0, (byte)this.blocks.length);
	}
	
	/**
	 * multiply the given memory to this one
	 * @param other the memory that should be multiplied
	 * @param from index of the block from which the multiplication should start
	 * @param to index of the block to which the multiplication should end (excluded)
	 */
	public void multiply(Memory other, byte  from, byte to) {
		while (this.bullGamma.md != 0) {
			if (this.blocks[0] == 0) {
				this.shiftRight();
				this.bullGamma.md--;
			} else {
				this.blocks[0]--;
				this.add(other, from, to, false);
			}
		}
	}

	/**
	 * multiply the memory with the given value, equivalent to this = this * value * (10 or 2)^at
	 * @param value
	 * @param at
	 */
	public void multiplyValue(byte value, byte at) {
		while (this.bullGamma.md != 0) {
			if (this.blocks[0] == 0) {
				this.shiftRight();
				this.bullGamma.md--;
			} else {
				this.blocks[0]--;
				this.addValue(value, at);
			}
		}
	}

	/**
	 * divide the given memory to this one
	 * @param other the memory that should be divided
	 * @param from index of the block from which the division should start
	 * @param to index of the block to which the division should end (excluded)
	 */
	public void divide(Memory other, byte from, byte to) {
		long vmb = other.getDecimalValue(from, to);
		if (vmb == 0) {
			throw new Error("Division by 0.");
		}
		while (this.bullGamma.md > 0) {
			while (this.getDecimalValue((byte)(from + this.blocks.length - NB_CHRS_PER_WORD), (byte)this.blocks.length) < vmb
					&& this.bullGamma.md > 0) {
				this.shiftLeft();
				this.bullGamma.md--;
			}
			while (this.getDecimalValue((byte)(from + this.blocks.length - NB_CHRS_PER_WORD), (byte)this.blocks.length) >= vmb) {
				this.blocks[0]++;
				this.subtract(other, from, to, (byte)(from + this.blocks.length - NB_CHRS_PER_WORD), (byte)this.blocks.length);
			}
		}
	}


	/**
	 * divide the memory with the given value, equivalent to this = this / (value * (10 or 2)^at)
	 * @param value
	 * @param at
	 */
	public void divideValue(byte value, byte at) {
		Memory mb = new Memory(0, this.bullGamma);
		mb.blocks[at] = value;
		this.divide(mb, (byte)0, (byte)(at + 1));
	}	

}
