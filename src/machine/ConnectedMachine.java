package machine;

public abstract class ConnectedMachine {

	BullGamma bullGamma;
	
	/**
	 * Defines the Bull Gamma the machine is connected to
	 * @param bullGamma instance of BullGamma
	 */
	public void setBullGamma(BullGamma bullGamma) {
		this.bullGamma = bullGamma;
	}

	/**
	 * Function triggered by the instruction KB with AD=0
	 */
	public void on48V() {
		// to be overridden
		throw new Error("You have to implement the method on48V.");
	}

	/**
	 * Function triggered by the instruction ES1
	 */
	public void onStaticExtraction1(byte OD, byte OF) {
		// to be overridden
		throw new Error("You have to implement the method onStaticExtraction1.");
	}

	/**
	 * Function triggered by the instruction ES2
	 */
	public void onStaticExtraction2(byte OD, byte OF) {
		// to be overridden
		throw new Error("You have to implement the method onStaticExtraction2.");
	}
}
