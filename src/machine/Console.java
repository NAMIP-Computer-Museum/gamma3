package machine;

import java.util.ArrayList;
import java.util.List;

import instructions.Instruction;
import instructions.Tools;

/**
 * NB: This connected machine is not an actual device af the Bull Gamma
 * It is used for debug purposes only
 */
public class Console extends ConnectedMachine {
	
  List<String> lines;	

  public Console() {
    this.lines = new ArrayList<String>();
  }

  /**
   * Function triggered by an instruction of the Bull Gamma
   */
  public void on48V() {
    int i = Constants.NB_MEMORIES_PER_HALF_OCTAD;
    for (Word ex:this.bullGamma.getExtractors()) {
      this.lines.add(
        "Sortie "+ (i - Constants.NB_MEMORIES_PER_HALF_OCTAD) + " : " + ex.toString()
      );
      i++;
    }
  }

  public double convertToDouble(String value) {
    long exponent = (Tools.parseHex(value.charAt(0)) & 0x7) * Tools.pow(2,5)
      + Tools.parseHex(value.charAt(1)) * 2
      + (Tools.parseHex(value.charAt(2)) >> 3);
    exponent -= 7 * 16 + 15;  // TODO to understand - décalage de 50 en principe ??

    int sign = ((Tools.parseHex(value.charAt(0)) & 0x8) >> 3)!=0 ? -1: 1;

    long mantissa = (Tools.parseHex(value.charAt(2)) & 0x7) / 4;
    for (int i = 3; i < value.length(); i++) {
      mantissa += Tools.parseHex(value.charAt(i)) / (4 * Tools.pow(16,(i-2)));
    }

    return sign * (mantissa * Tools.pow(2,(int)exponent));  // TODO check cast exponent should be OK
  }

  public long convertToLong(String value) { // TODO test !
    int sign = ((Tools.parseHex(value.charAt(0)) & 0x8) >> 3)!=0 ? -1: 1;

    long mantissa = (Tools.parseHex(value.charAt(2)) & 0x7) * Tools.pow(16,11);   // TODO check looks strange
    for (int i = 1, j = value.length() - 2; i < value.length(); i++, j--) {
      mantissa += Tools.parseHex(value.charAt(i)) * Tools.pow(16,j);
    }

    return sign * mantissa;
  }

  /**
   * Function triggered by the instruction ES1
   */
  public void onStaticExtraction1(byte OD, byte OF) {
    Word[] extractors = this.bullGamma.getExtractors();
    // first extractor is used for error
    // other for standard outputs
    int nb_errors = 1;

    switch (OD) {
      case 0:
        // display outputs
        if (OF < extractors.length - nb_errors) {
          push(
            "Sortie "+ OF + " : " + extractors[OF + nb_errors].toString()
          );
        }
        break;
      case 1:
        // display errors
        if (OF < nb_errors) {
          push(
            "Erreur: " + extractors[OF].toString()
          );
        }
        break;
      case 2:
        // display outputs
        if (OF < extractors.length - nb_errors) {
          push(
            "Sortie "+ OF + " : " + this.convertToDouble(extractors[OF + nb_errors].toString())
          );
        }
        break;
      case 3:
        // display outputs
        if (OF < extractors.length - nb_errors) {
          push(
            "Sortie "+ OF + " : " + this.convertToLong(extractors[OF + nb_errors].toString())
          );
        }
        break;
      default:
        break;
    }
  }

  /**
   * Function triggered by the instruction ES1
   */
  public void onStaticExtraction2(byte OD, byte OF) {
    // nothing yet
  }

  /**
   * Returns the list of lines printed to the console
   * @returns String[]
   */
  public String[] getLines() {
    return this.lines.toArray(new String[0]);
  }

  /**
   * Adds a line to the console
   * @param line string to be added
   */
  public void push(String line) {
    this.lines.add(line);
  }

}
