package machine;

/**
 * A memory able to retain the result a comparison
 * Internal attributes 'greater' and 'equal' should be set manually
 */
public class CmpMemory {

	boolean greater;
	boolean equal;
	
	/**
	 * Constructs a new instance of CmpMemory
	 */
	public CmpMemory() {
		this.greater = false;
		this.equal = false;
	}

	/**
	 * @returns {boolean} true if the memory is set to lower
	 */
	public boolean isLower() {
		return !this.equal && !this.greater;
	}

	/**
	 * @returns {boolean} true if the memory is set to equal
	 */
	public boolean isEqual() {
		return this.equal;
	}

	/**
	 * @returns {boolean} true if the memory is set to lower or equal
	 */
	public boolean isLowerOrEqual() {
		return this.isLower() || this.isEqual();
	}

	/**
	 * @returns {boolean} true if the memory is set to greater
	 */
	public boolean isGreater() {
		return this.greater;
	}

	/**
	 * @returns {boolean} true if the memory is set to greater or equal
	 */
	public boolean isGreaterOrEqual() {
		return this.isGreater() || this.isEqual();
	}

	/**
	 * @returns {boolean} true if the memory is not set to equal
	 */
	public boolean isNotEqual() {
		return !this.isEqual();
	}

	/**
	 * set comparison
	 * note using long required to reach 10^12
	 * TODO check added by CP
	 */
	public void setCompare(long a, long b) {
		greater=(a>b);
		equal=(a==b);
	}
}
