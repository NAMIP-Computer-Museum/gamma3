package instructions;

import machine.BullGamma;

/**
 * Ecriture de Lettre
 */
public class Vac extends V {
  Vac(byte AD, byte OD, byte OF, BullGamma bullGamma) {
	super(AD, OD, OF, bullGamma); // TODO check super moved first
	
    if (AD < 9 || OF%4 < 2) {
      throw new InvalidInstructionError("0" + Instruction.getChar(AD) + "x" + Instruction.getChar(OF));
    }
 
  }

}
