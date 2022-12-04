package tests;

import org.junit.Test;
import static org.junit.Assert.*;
import instructions.IL;
import machine.BullGamma;
import machine.Constants;
import machine.Memory;

public class TestIL {

	@Test
	public void testInvalid() {
		BullGamma bullGamma=new BullGamma();
		try {
			IL instr = new IL((byte)5, (byte)2, (byte)9, bullGamma);
		} catch (Error e) {
			System.out.println(e.getMessage());
			return;
		}
		fail("Bad instruction not detected");
	}

	@Test
	public void testNotAffectM1() {
		System.out.println("should not affect M1");
		BullGamma bullGamma = new BullGamma();
		Memory m1 = bullGamma.getMemory(1);
		Memory m2 = bullGamma.getMemory(2);
		for (int i = 0; i < Constants.NB_CHRS_PER_WORD; ++i) {
			m1.blocks[i] = (byte)(i + 2);
			m2.blocks[i] = (byte)(i + 2);
		}
		System.out.println(m1);
		System.out.println(m2);
		IL il = new IL((byte)12, (byte)0, (byte)0, bullGamma);
		il.execute();

		for (int i = 0; i < Constants.NB_CHRS_PER_WORD; ++i) {
			assertEquals(m1.blocks[i],i + 2); // "block " + i + " has changed"
		}
	}
}


/*
describe('IL M2', function() {
  describe('#constructor()', function () {
    it('constructor should raise error', function () {
      let bullGamma = new BullGamma();
      try {
        let instr = new IL(5, 2, 9, bullGamma);
      } catch (e) {
        return;
      }
      assert(false);
    });
  });
  describe('#execute()', function () {
    it('should not affect M1', function () {
      let bullGamma = new BullGamma();
      let m1 = bullGamma.getMemory(1);
      let m2 = bullGamma.getMemory(2);
      for (let i = 0; i < NB_CHRS_PER_WORD; ++i) {
        m1.blocks[i] = i + 2;
        m2.blocks[i] = i + 2;
      }
      let il = new IL(12, 0, 0, bullGamma);
      il.execute();

      for (let i = 0; i < NB_CHRS_PER_WORD; ++i) {
        assert(m1.blocks[i] == i + 2, "block " + i + " has changed");
      }
    });
    it('should M1 & M2', function () {
      let bullGamma = new BullGamma();
      let m1 = bullGamma.getMemory(1);
      let m2 = bullGamma.getMemory(2);
      m1vals  = "123456789ABC".split("").map(v => parseInt(v, 16));
      m2vals  = "987654322222".split("").map(v => parseInt(v, 16));
      resvals = "103454300220".split("").map(v => parseInt(v, 16));
      for (let i = 0; i < NB_CHRS_PER_WORD; ++i) {
        m1.blocks[i] = m1vals[i];
        m2.blocks[i] = m2vals[i];
      }
      let il = new IL(12, 0, 0, bullGamma);
      il.execute();

      for (let i = 0; i < NB_CHRS_PER_WORD; ++i) {
        assert(m1.blocks[i] == resvals[i], "block " + i + " is incorrect");
      }
    });
    it('should raise error', function () {
      let bullGamma = new BullGamma();
      try {
        let instr = new IL(10, 2, 9, bullGamma);
        instr.AD = 6;
        instr.execute();
      } catch (e) {
        return;
      }
      assert(false);
    });
  });
  describe('#getDescription()', function () {
    it("should print the instruction's description", function () {
      let bullGamma =  new BullGamma();
      let instr = new IL(10, 5, 7, bullGamma);
      console.debug(instr.getDescription());
      console.debug(instr.getShortType());
      console.debug(instr.getLongType());
    });
    it('should raise error', function () {
      let bullGamma = new BullGamma();
      try {
        let instr = new IL(10, 2, 9, bullGamma);
        instr.AD = 6;
        instr.getDescription();
      } catch (e) {
        return;
      }
      assert(false);
    });
  });
});
describe('IL OF', function() {
  describe('#execute()', function () {
    it('should not affect M1', function () {
      let bullGamma = new BullGamma();
      let m1 = bullGamma.getMemory(1);
      for (let i = 0; i < NB_CHRS_PER_WORD; ++i) {
        m1.blocks[i] = i + 2;
      }
      let il = new IL(10, 0, 15, bullGamma);
      il.execute();

      for (let i = 0; i < NB_CHRS_PER_WORD; ++i) {
        assert(m1.blocks[i] == i + 2, "block " + i + " has changed");
      }
    });
    it('should M1 & M2', function () {
      let bullGamma = new BullGamma();
      let m1 = bullGamma.getMemory(1);
      m1vals  = "123456789ABC".split("").map(v => parseInt(v, 16));
      resvals = "101454501014".split("").map(v => parseInt(v, 16));
      for (let i = 0; i < NB_CHRS_PER_WORD; ++i) {
        m1.blocks[i] = m1vals[i];
      }
      let il = new IL(10, 0, 5, bullGamma);
      il.execute();

      for (let i = 0; i < NB_CHRS_PER_WORD; ++i) {
        assert(m1.blocks[i] == resvals[i], "block " + i + " is incorrect");
      }
    });
  });
  describe('#getDescription()', function () {
    it("should print the instruction's description", function () {
      let bullGamma =  new BullGamma();
      let instr = new IL(12, 5, 7, bullGamma);
      console.debug(instr.getDescription());
      console.debug(instr.getShortType());
      console.debug(instr.getLongType());
    });
  });
});
 */
