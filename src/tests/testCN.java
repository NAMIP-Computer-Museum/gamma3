package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import instructions.CN;
import machine.BullGamma;
import machine.Memory;

public class testCN {

	@Test
	public void testGreaterM1MB() {
		System.out.println("Should set MC to greater if M1 is greater than MB");

		BullGamma bullGamma = new BullGamma();
		Memory m1 = bullGamma.getMemory(1);
		Memory m2 = bullGamma.getMemory(2);
		byte od = 0, of = 8;
		bullGamma.md = od;
		for (int i = od; i < of; ++i){
			m1.blocks[i] = 9;
			m2.blocks[i] = 8;
		}
		System.out.println(m1);
		System.out.println(m2);
		CN cn = new CN((byte)2, od, of, bullGamma);
		cn.execute();
		assertTrue(bullGamma.mc.isGreater());
		assertTrue(bullGamma.mc.isGreaterOrEqual());
		assertFalse(bullGamma.mc.isEqual());
		assertFalse(bullGamma.mc.isLowerOrEqual());
		assertFalse(bullGamma.mc.isLower());
		assertTrue(bullGamma.mc.isNotEqual());
	}

	@Test
	public void testLowerM1MB() {
		System.out.println("Should set MC to greater if M1 is lower than MB");

		BullGamma bullGamma = new BullGamma();
		Memory m1 = bullGamma.getMemory(1);
		Memory m2 = bullGamma.getMemory(2);
		byte od = 0, of = 8;
		bullGamma.md = od;
		for (int i = od; i < of; ++i){
			m1.blocks[i] = 8;
			m2.blocks[i] = 9;
		}
		System.out.println(m1);
		System.out.println(m2);
		CN cn = new CN((byte)2, od, of, bullGamma);
		cn.execute();
		assertFalse(bullGamma.mc.isGreater());
		assertFalse(bullGamma.mc.isGreaterOrEqual());
		assertFalse(bullGamma.mc.isEqual());
		assertTrue(bullGamma.mc.isLowerOrEqual());
		assertTrue(bullGamma.mc.isLower());
		assertTrue(bullGamma.mc.isNotEqual());
	}

	@Test
	public void testEqualM1MB() {
		System.out.println("Should set MC to greater if M1 is greater than MB");

		BullGamma bullGamma = new BullGamma();
		Memory m1 = bullGamma.getMemory(1);
		Memory m2 = bullGamma.getMemory(2);
		byte od = 0, of = 8;
		bullGamma.md = od;
		for (int i = od; i < of; ++i){
			m1.blocks[i] = 9;
			m2.blocks[i] = 9;
		}
		System.out.println(m1);
		System.out.println(m2);
		CN cn = new CN((byte)2, od, of, bullGamma);
		cn.execute();
		assertFalse(bullGamma.mc.isGreater());
		assertTrue(bullGamma.mc.isGreaterOrEqual());
		assertTrue(bullGamma.mc.isEqual());
		assertTrue(bullGamma.mc.isLowerOrEqual());
		assertFalse(bullGamma.mc.isLower());
		assertFalse(bullGamma.mc.isNotEqual());
	}

	@Test
	public void testAD1() {
		// TODO check AD1 means checking again itself on a portion
		System.out.println("when used with AD = 1, should detect remaining numbers in blocks outside of the OD->OF portion");
		BullGamma bullGamma = new BullGamma();
		Memory m1 = bullGamma.getMemory(1);
		byte od = 2, of = 8;
		bullGamma.md = od;
		m1.blocks[of] = 1;
		System.out.println(m1);
		System.out.println("FULL: "+m1.getDecimalValue((byte)0,(byte)m1.blocks.length));
		System.out.println("PART: "+m1.getDecimalValue(od,of)); // note ending block is excluded
		
		CN cn = new CN((byte)1, od, of, bullGamma); // TODO check
		cn.execute();
		assertTrue(bullGamma.mc.isGreater());
		assertTrue(bullGamma.mc.isGreaterOrEqual());
		assertFalse(bullGamma.mc.isEqual());
		assertFalse(bullGamma.mc.isLowerOrEqual());
		assertFalse(bullGamma.mc.isLower());
		assertTrue(bullGamma.mc.isNotEqual());
	}

	@Test
	public void testAD0() {
		System.out.println("when AD = 0, should set MC to greater if M1 is greater than OF.base^OD");  // TODO check base
		BullGamma bullGamma = new BullGamma();
		Memory m1 = bullGamma.getMemory(1);
		m1.blocks[6] = 1;
		byte of=5, od=5;
		System.out.println(m1);
		System.out.println(of*Math.pow(10, od));
		CN cn = new CN((byte)0, od, of, bullGamma);
		cn.execute();
		assertTrue(bullGamma.mc.isGreater());
		assertTrue(bullGamma.mc.isGreaterOrEqual());
		assertFalse(bullGamma.mc.isEqual());
		assertFalse(bullGamma.mc.isLowerOrEqual());
		assertFalse(bullGamma.mc.isLower());
		assertTrue(bullGamma.mc.isNotEqual());
	}

}

/*	    

	    it('when AD = 0, should set MC to greater if M1 is greater than OF^OD', function () {
	      let bullGamma = new BullGamma();
	      let m1 = bullGamma.getMemory(1);
	      m1.blocks[11] = 1;
	      let cn = new CN(0, 5, 5, bullGamma);
	      cn.execute();
	      assert.equal(bullGamma.mc.isGreater(), true);
	      assert.equal(bullGamma.mc.isGreaterOrEqual(), true);
	      assert.equal(bullGamma.mc.isEqual(), false);
	      assert.equal(bullGamma.mc.isLowerOrEqual(), false);
	      assert.equal(bullGamma.mc.isLower(), false);
	      assert.equal(bullGamma.mc.isNotEqual(), true);
	    });
	    it('when AD = 0, should set MC to equal if M1 is equal to OF^OD', function () {
	      let bullGamma = new BullGamma();
	      let m1 = bullGamma.getMemory(1);
	      m1.blocks[5] = 4;
	      let cn = new CN(0, 5, 4, bullGamma);
	      cn.execute();
	      assert.equal(bullGamma.mc.isGreater(), false);
	      assert.equal(bullGamma.mc.isGreaterOrEqual(), true);
	      assert.equal(bullGamma.mc.isEqual(), true);
	      assert.equal(bullGamma.mc.isLowerOrEqual(), true);
	      assert.equal(bullGamma.mc.isLower(), false);
	      assert.equal(bullGamma.mc.isNotEqual(), false);
	    });
	    it('when AD = 0, should set MC to lower if M1 is lower than OF^OD', function () {
	      let bullGamma = new BullGamma();
	      let m1 = bullGamma.getMemory(1);
	      m1.blocks[4] = 6;
	      let cn = new CN(0, 5, 3, bullGamma);
	      cn.execute();
	      assert.equal(bullGamma.mc.isGreater(), false);
	      assert.equal(bullGamma.mc.isGreaterOrEqual(), false);
	      assert.equal(bullGamma.mc.isEqual(), false);
	      assert.equal(bullGamma.mc.isLowerOrEqual(), true);
	      assert.equal(bullGamma.mc.isLower(), true);
	      assert.equal(bullGamma.mc.isNotEqual(), true);
	    });
	    it('should work with le test de José', function () {
	      let bullGamma = new BullGamma();
	      let m1 = bullGamma.getMemory(1);
	      let m3 = bullGamma.getMemory(3);
	      for (let i = 0; i < 6; ++i) {
	        m1.blocks[i] = 2;
	      }
	      for (let i = 0; i < 5; ++i) {
	        m3.blocks[i] = 1;
	      }
	      let cn = new CN(3, 0, 0, bullGamma);
	      cn.execute();
	      assert.equal(bullGamma.mc.equal, false);
	      assert.equal(bullGamma.mc.greater, true);
	    });
	    it('should be equal to a constant', function () {
	      let bullGamma = new BullGamma();
	      let m1 = bullGamma.getMemory(1);
	      bullGamma.setMemoryMode(MEMORY_MODE.BINARY);
	      m1.setContent("800000000000");
	      let cn = new CN(0, 11, 8, bullGamma);
	      cn.execute();
	      assert.equal(bullGamma.mc.equal, true);
	      assert.equal(bullGamma.mc.greater, false);
	    });
	  });
	  describe('#getDescription()', function () {
	    it("should print the instruction's description", function () {
	      let bullGamma =  new BullGamma();
	      let instr = new CN(4, 5, 7, bullGamma);
	      console.debug(instr.getDescription());
	      console.debug(instr.getShortType());
	      console.debug(instr.getLongType());
	    });
	    it("should print the instruction's description", function () {
	      let bullGamma =  new BullGamma();
	      let instr = new CN(0, 5, 7, bullGamma);
	      console.debug(instr.getDescription());
	      console.debug(instr.getShortType());
	      console.debug(instr.getLongType());
	    });
	  });
	});
 */