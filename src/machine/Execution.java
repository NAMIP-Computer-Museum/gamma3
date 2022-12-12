package machine;

import instructions.Instruction;
import ui.UIPane;

public class Execution {

	BullGamma bullGamma;
	Console console;
	UIPane uipane;

	public Execution(BullGamma bullGamma) {
		assert bullGamma!=null :  "A BullGamma instance must be provided.";
		this.bullGamma = bullGamma;
		this.console = new Console();
		this.bullGamma.connectMachine(this.console);
	}

	/**
	 * Executes the next instruction in the program
	 */
	public void executeNextInstruction() {
		this.bullGamma.executeNextInstruction();
		updateUIPane();
	}
	

	/**
	 * Executes instructions until the given line in the given series is reached
	 * Don't execute the instruction at the given line
	 * @param line the line number the instruction ot stop before
	 * @param seriesId the series id of the instruction
	 */
	public void executeUntil(int line, int seriesId) {
		// test if the series and the instruction exists
		this.bullGamma.getSerie(seriesId).getInstruction(line);

		do {
			this.executeNextInstruction();
		} while (this.getCurrentLine() != line || this.getCurrentSeries() != seriesId);
	}

	/**
	 * Returns the current instruction line in the current series
	 * @returns the line number
	 */
	public int getCurrentLine() {
		return this.bullGamma.nl;
	}

	/**
	 * Returns the current series where the instructions read from
	 * @returns the series id
	 */
	public int getCurrentSeries() {
		return this.bullGamma.ns;
	}

	/**
	 * Returns the list of instructions of the given series
	 * @param seriesId the id of the series
	 * @returns Instruction[]
	 */
	public Instruction[] getInstructions(int seriesId) {
		return this.bullGamma.getSerie(seriesId).getInstructions();
	}

	/**
	 * Returns the number of instructions of the given series
	 * @param seriesId the id of the series
	 * @returns the number of instructions
	 */
	public int getNumberOfInstructions(int seriesId) {
		return this.bullGamma.getSerie(seriesId).maxNbInst;
	}

	/**
	 * Writes a line in the execution console
	 * @param line the string to be written
	 */
	public void writeConsoleLine(String line) {
		this.console.push(line);
	}
	
	public void setUIPane(UIPane uipane) {
		this.uipane=uipane;
	}
	
	public void updateUIPane() {
		if (uipane==null) return;
		
		uipane.update();
	}
}
