package instructions;

import machine.BullGamma;

class InvalidInstructionError extends Error {
	InvalidInstructionError(String instruction) {
		super("Invalid instruction " + instruction + ".");
	}
}

class InvalidInstructionExecutionError extends Error {
	InvalidInstructionExecutionError() {
		super("Cannot execute invalid instruction.");
	}
}

class InvalidInstructionDescriptionError extends Error {
	InvalidInstructionDescriptionError() {
		super("Cannot describe invalid instruction.");
	}
}

class MethodNotImplementedError extends Error {
	MethodNotImplementedError(Object method) {
		super("You have to implement the method '" + method + "'.");
	}
}

/**
 * Abstract class meant to represent an Instruction, please refer to
 * http://aconit.org/histoire/Gamma-3/Articles/Gamma-Bolliet.pdf for further documentation about the specific
 * instructions behavior
 */
public abstract class Instruction {
	
	byte TO,AD,OD,OF;
	String hexString;
	BullGamma bullGamma;

	Instruction(byte TO, byte AD, byte OD, byte OF, BullGamma bullGamma) {
		assert TO >= 0 : "TO should not be negative";
		assert AD >= 0 : "AD should not be negative";
		assert OD >= 0 : "OD should not be negative";
		assert OF >= 0 : "OF should not be negative";
		assert TO < 16 : "TO should be inferior to 16";
		assert AD < 16 : "AD should be inferior to 16";
		assert OD < 16 : "OD should be inferior to 16";
		assert OF < 16 : "OF should be inferior to 16";
		assert bullGamma!=null : "a BullGamma instance should be provided";

		this.TO = TO;
		this.AD = AD;
		this.OD = OD;
		this.OF = OF;
		this.bullGamma = bullGamma;
		this.hexString = this.getHexCode(TO, AD, OD, OF);
	}

	/**
	 * Abstract method, execute the instruction logic
	 */
	public void execute() {
		throw new MethodNotImplementedError("execute");
	}

	/**
	 * Abstract method, return the execution time of this instruction
	 */
	public void computeExeTime() {
		throw new MethodNotImplementedError("computeExeTime");
	}

	/**
	 * Abstract method, return the textual description of this instruction
	 */
	public String getDescription() {
		throw new MethodNotImplementedError("getDescription");
	}

	/**
	 * Abstract method, return the short type name of this instruction
	 */
	public String getShortType() {
		throw new MethodNotImplementedError("getShortType");
	}

	/**
	 * Abstract method, return the long type name of this instruction
	 */
	public String getLongType() {
		throw new MethodNotImplementedError("getLongType");
	}

	public String toString() {
		return this.hexString;
	}

	public String toLineString() {
		return this.hexString + " -- " + this.getShortType();
	}

	static String getChar(byte intValue) {
		return Integer.toHexString(intValue & 0xf); // TODO check lower or upper case ? also widening pb?
	}

	public String getHexCode(byte TO, byte AD, byte OD, byte OF) {
		return Instruction.getChar(TO) + Instruction.getChar(AD) + Instruction.getChar(OD) + Instruction.getChar(OF);
	}

}