package instructions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import machine.BullGamma;
import machine.Memory;

public abstract class Tools {

	private static final Pattern pattern = Pattern.compile("^[0-9a-fA-F]*$");

	// Computation mode
	// see in MemoryMode

	public static String zeros(int n) {
		StringBuffer sb=new StringBuffer();
		for(int i=0; i<n; i++) sb.append("0");
		return sb.toString();
	}

	public static long pow(long a, int b) {
		if ( b == 0) return 1;
		if ( b == 1) return a;
		if ( b%2==0) return pow ( a * a, b/2); //even a=(a^2)^b/2
		else         return a * pow ( a * a, b/2); //odd  a=a*(a^2)^b/2
	}

	public static long toLong(Memory m) {
		long  total = 0;
		for (int i = 0; i < 12; ++i) {
			total += m.blocks[i]*Tools.pow(10,i);
		}
		return total;
	}

	public static String parse_hex_code(String entry, int size) throws Exception {
		assert (size>=0) : "Number of expected hex chunks must be positive.";
		String hexCode = entry.replaceAll("--[^\n\r]*(\n\r?|$)", ""); // remove comments
		hexCode = hexCode.replaceAll("[\s\n\r\t]", ""); // remove white space and line breaks
		hexCode = hexCode+zeros(size-hexCode.length()); // padding redone
		System.out.println("=====================================");
		System.out.println(hexCode);
		System.out.println(hexCode.length());
		System.out.println("=====================================");

		Matcher matcher = pattern.matcher(hexCode);
		if (!matcher.matches()){
			throw new Exception("Invalid hex code."); // TODO specific exception
		}
		if (hexCode.length() != size) {
			throw new Exception("Hex entry too long. Expected length '" + size + "', got '" + hexCode.length() + "'.");
		}
		return hexCode;
	}

	public static byte parseHex(char c) {
		return (byte)Integer.parseInt(c+"", 16); 
	}

	/**
	 * Given hexadecimal code for Bull Gamma 3, returns a set of instructions for the machine.
	 * @param entry the string representing the code to be parsed. code may include single line comments starting with --.
	 * @param size the number of expected instruction, completes with NOP
	 * @param bullGamma the machine to which the returned instructions should be attached
	 * @returns {Array} the array of parsed instructions
	 * @throws Exception 
	 */
	public static Instruction[] parse_hex_str_to_instructions(String entry, int size, BullGamma bullGamma) throws Exception {
		assert (size>0) : "Number of expected instructions must be positive.";
		String hexCode = parse_hex_code(entry, size * 4);
		Instruction[] instructions = new Instruction[size];
		int i = 0;
		String four_hex_chunk;
		for(int pos=0; pos<size*4; pos+=4) {
			//	  hexCode.match(/.{4}/g).forEach(function (four_hex_chunk) { // break the string into chunks of 4 chars
			four_hex_chunk=hexCode.substring(pos, pos+4);
			System.out.println((i+1)+" "+" "+four_hex_chunk);
			try {
				instructions[i]=parseInstruction(
						parseHex(four_hex_chunk.charAt(0)),
						parseHex(four_hex_chunk.charAt(1)),
						parseHex(four_hex_chunk.charAt(2)),
						parseHex(four_hex_chunk.charAt(3)),
						bullGamma);
				i++;
			} catch (Exception e) {
				throw new Error("Parsing error at instruction #" + (i+1) + ": " + e.getMessage());
			}
		}
		return instructions;
	}

	public static Instruction parseInstruction(byte TO, byte AD, byte OD, byte OF, BullGamma bullGamma) {
		switch (TO) {
		case 0:
			if (OF%4 < 2) {
				switch (AD) {
				case 0: case 1: case 2: case 3: case 4:
					return new SL(AD, OD, OF, bullGamma);
				case 8: case 9: case 10: case 11: case 12: case 13: case 14: case 15:
					return new EL(AD, OD, OF, bullGamma);
				default:
					throw new InvalidInstructionError("0" + Instruction.getChar(AD) + "x" + Instruction.getChar(OF));
				}
			} else {
				switch (AD) {
				case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7:
					return new Vn(AD, OD, OF, bullGamma);
				case 9: case 10: case 11: case 12: case 13: case 14: case 15:
					return new Vac(AD, OD, OF, bullGamma);
				default:
					throw new InvalidInstructionError("0" + Instruction.getChar(AD) + "x" + Instruction.getChar(OF));
				}
			}
		case 1:
			switch (AD) {
			case 0: case 1: case 2: case 3:
				return new VCS(AD, OD, OF, bullGamma);
			case 5: case 6: case 7:
				return new VRS(AD, OD, OF, bullGamma);
			case 8: case 9:
				return new ES(AD, OD, OF, bullGamma);
			case 10:
				return new CD(OD, OF, bullGamma);
			case 12:
				return new CO(OD, OF, bullGamma);
			case 13:
				return new CSz(OD, OF, bullGamma);
			case 15:
				return new CB(OD, OF, bullGamma);
			default:
				throw new InvalidInstructionError("1" + Instruction.getChar(AD) + "xx");
			}
		case 2:
			if ((OF & 0x1) == 0x1) { // OF and 0001 to select last bit
				return new TB(AD, OD, OF, bullGamma);
			} else {
				return new BT(AD, OD, OF, bullGamma);
			}
		case 3:
			return new ZB(AD, OD, OF, bullGamma);
		case 4:
			return new KB(AD, OD, OF, bullGamma);
		case 5:
			if (AD != 0) {
				throw new InvalidInstructionError("5" + Instruction.getChar(AD) + "xx");
			}
			return new GG(OD, OF, bullGamma);
		case 6:
			return new BO(AD, OD, OF, bullGamma);
		case 7:
			switch (AD) {
			case 0:
				return new AMD(OD, OF, bullGamma);
			case 2:
				return new BD(OD, OF, bullGamma);
			case 10:
			case 12:
				return new IL(AD, OD, OF, bullGamma);
			default:
				throw new InvalidInstructionError("7" + Instruction.getChar(AD) + "xx");
			}
		case 8:
			return new OB(AD, OD, OF, bullGamma);
		case 9:
			return new CN(AD, OD, OF, bullGamma);
		case 10:
			return new AN(AD, OD, OF, bullGamma);
		case 11:
			return new SN(AD, OD, OF, bullGamma);
		case 12:
			return new MR(AD, OD, OF, bullGamma);
		case 13:
			return new DR(AD, OD, OF, bullGamma);
		case 14:
			return new MC(AD, OD, OF, bullGamma);
		case 15:
			return new DC(AD, OD, OF, bullGamma);
		default:
			throw new Error("Fell in default case when it shouldn't have happened");
		}
	}

}
