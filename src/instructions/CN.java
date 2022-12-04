package instructions;

import machine.BullGamma;
import machine.Memory;

/**
 * comparison
 */
public class CN extends OperationWithPreShift {
	
  public CN(byte AD, byte OD, byte OF, BullGamma bullGamma) {
    super((byte)9, AD, OD, OF, bullGamma);
  }

  void _exeInstructionLogic() {
    Memory m1 = this.bullGamma.getMemory(1);
    byte base = this.bullGamma.getMemoryMode().getBase();
    long  vm1 = m1.getDecimalValue((byte)0, (byte)m1.blocks.length);
    if (this.AD == 0) {
      this.bullGamma.mc.setCompare(vm1,this.OF * (long)Math.pow(base,this.OD));  // TODO Math pow int
    } else {
      long valMb = this.bullGamma.getMemory(this.AD).getDecimalValue(this.OD, this.OF);
      valMb *= (int)Math.pow(base,this.OD);
      this.bullGamma.mc.setCompare(vm1,valMb);
    }
  }

  public String getDescription() {
    if (this.AD == 0) {
      return "Effectue le décalage de M1 puis compare le contenu de M1 à "
      + this.OF + " en position " + this.OD
      + ", met le résultat en mémoire de comparaison";
    } else {
      return "Effectue le décalage de M1 puis compare le contenu de M1 à celui de M"
      + this.AD + " entre les positions " + this.OD + " et " + this.OF
      + ", met le résultat en mémoire de comparaison";
    }
  }

  public String getShortType() {
    return "CN";
  }

  public String getLongType() {
    return "Comparaison";
  }

}
