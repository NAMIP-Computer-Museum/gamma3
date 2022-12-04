package instructions;

import machine.BullGamma;

/**
 * jump
 */
public class SL extends V {
  
  public SL(byte AD, byte OD, byte OF, BullGamma bullGamma) {
	super(AD, OD, OF, bullGamma); // TODO check order (was after)
	    
    if (AD > 4 || OF%4 > 1) {
      throw new InvalidInstructionError("0" + Instruction.getChar(AD) + "x" + Instruction.getChar(OF));
    }
  }

  public void execute() {
    boolean[][] jump_cond_matrix = {{
      false,
      this.bullGamma.mc.isGreater(),
      this.bullGamma.mc.isEqual(),
      this.bullGamma.mc.isGreaterOrEqual(),
      this.bullGamma.ms1 == 10
    }, {
      true,
      this.bullGamma.mc.isLowerOrEqual(),
      this.bullGamma.mc.isNotEqual(),
      this.bullGamma.mc.isLower(),
      this.bullGamma.ms1 == 0
    }};
/*    if (jump_cond_matrix[this.OF % 4][this.AD] == null) {
      throw new InvalidInstructionExecutionError();
    } */
    // TODO check out of bound
    if (jump_cond_matrix[this.OF%4][this.AD]) {
      this.bullGamma.nl = (this.OD << 2) + (this.OF >> 2);
    }
  }

  public String getDescription() {
    if (this.AD < 5 && this.OF % 4 < 2) {
      String action = "Saute à ligne " + ((this.OD << 2) + (this.OF >> 2))
      + " de la série courante";

      if (this.AD == 0 ) {
        if (this.OF % 4 == 0) {
          return "Ne fait rien";
        } else {
          return action;
        }
      } else if (this.AD == 4) {
        if (this.OF % 4 == 0) {
          return action + " si la mémoire de signe est négative";
        } else {
          return action + " si la mémoire de signe est positive";
        }
      } else {
        String[][] jump_cond_matrix = {
          {"'supérieur'", "'égal'", "'supérieur ou égal'"},
		  {"'inférieur ou égal'", "'différent'", "'inférieur'"}};
        return action + " si la mémoire de décalage contient le resultat "
        + jump_cond_matrix[this.OF % 4][this.AD - 1];
      }
    }
    throw new InvalidInstructionDescriptionError();
  }

  // getShortType() {
  //   return "V";
  // }
  //
  // getLongType() {
  //   return "Variante";
  // }

}

