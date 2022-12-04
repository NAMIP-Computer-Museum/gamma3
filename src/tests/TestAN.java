package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import instructions.AN;
import machine.BullGamma;
import machine.Memory;

public class TestAN {

	@Test
	public void testPositive() {
		//should add when M1 and MB are positive
		BullGamma bullGamma = new BullGamma();
		Memory m1 = bullGamma.getMemory(1);
		Memory m2 = bullGamma.getMemory(2);
		byte od = 0, of = 8;
		for (int i = od; i < of; ++i) {
			m1.blocks[i] = 9;
			m2.blocks[i] = 9;
		}
		new AN((byte)2, (byte)0, (byte)8, bullGamma).execute();
		int total = 0;
		for (int  i = 0; i < 12; ++i) {
			total += m1.blocks[i]*Math.pow(10,i);
		}
		assertEquals(total, 199999998);
	}

	@Test
	public void testNegative() {
		// should add when M1 and MB are negative
		BullGamma bullGamma = new BullGamma();
		Memory m1 = bullGamma.getMemory(1);
		Memory m2 = bullGamma.getMemory(2);
        byte od = 0, of = 8;
        for (int i = od; i < of; ++i) {
          m1.blocks[i] = 9;
          m2.blocks[i] = 9;
        }
        m2.blocks[of - 1] = 10; // sign of m2 ?
        bullGamma.ms1 = 10;
        System.out.println("M1: "+m1);
        System.out.println("M2: "+m2);       
        new AN((byte)2, (byte)0, (byte)8, bullGamma).execute();
        int total = 0;
        for (int i = 0; i < 12; ++i) {
          total += m1.blocks[i]*Math.pow(10,i);
        }
        assertEquals(total, 109999998);
        assertEquals(bullGamma.ms1, 10); // sign
	}

}

/*
    it('should add when M1 is positive and MB is negative', function () {
      let bullGamma = new BullGamma();
      let m1 = bullGamma.getMemory(1);
      let m2 = bullGamma.getMemory(2);
      let od = 0, of = 8;
      for (let i = od; i < of; ++i) {
        m1.blocks[i] = 9;
        m2.blocks[i] = 9;
      }
      m2.blocks[of - 1] = 10;
      new AN(2, 0, 8, bullGamma).execute();
      let total = 0;
      for (let i = 0; i < 12; ++i) {
        total += m1.blocks[i]*10**i;
      }
      assert.equal(total, 90000000, "wrong result");
    });
    it('should add when M1 is negative and MB is positive', function () {
      let bullGamma = new BullGamma();
      let m1 = bullGamma.getMemory(1);
      let m2 = bullGamma.getMemory(2);
      let od = 0, of = 8;
      for (let i = od; i < of; ++i) {
        m1.blocks[i] = 9;
        m2.blocks[i] = 9;
      }
      bullGamma.ms1 = 10;
      new AN(2, 0, 8, bullGamma).execute();
      let total = 0;
      for (let i = 0; i < 12; ++i) {
        total += m1.blocks[i]*10**i;
      }
      assert.equal(total, 0, "wrong result");
    });
    it('when AD = 1, should double the absolute value of M1', function () {
      let bullGamma = new BullGamma();
      let m1 = bullGamma.getMemory(1);
      let od = 0, of = 8;
      for (let i = od; i < of; ++i) {
        m1.blocks[i] = 9;
      }
      new AN(1, 0, 8, bullGamma).execute();
      let total = 0;
      for (let i = 0; i < 12; ++i) {
        total += m1.blocks[i]*10**i;
      }
      assert.equal(total, 199999998, "wrong result");
    });
    it('when AD = 0, should add OF to M1[OD] (M1 positive)', function () {
      let bullGamma = new BullGamma();
      let m1 = bullGamma.getMemory(1);
      m1.blocks[2] = 8;
      new AN(0, 2, 8, bullGamma).execute();
      assert.equal(m1.blocks[3]*10 + m1.blocks[2], 16, "wrong result");
    });
    it('when AD = 0, should ignore M1 sign', function () {
      let bullGamma = new BullGamma();
      let m1 = bullGamma.getMemory(1);
      m1.blocks[2] = 8;
      bullGamma.ms1 = 10;
      new AN(0, 2, 8, bullGamma).execute();
      assert.equal(m1.blocks[3]*10 + m1.blocks[2], 16, "wrong result");
    });
    it('should report carry to M1[0] in case of overflow on M1[11]', function () {
      let bullGamma = new BullGamma();
      let m1 = bullGamma.getMemory(1);
      let m2 = bullGamma.getMemory(2);
      m1.blocks[11] = 9;
      m2.blocks[11] = 9;
      new AN(2, 0, 12, bullGamma).execute();
      assert.equal(m1.blocks[0]*10 + m1.blocks[11], 18, "wrong result");
    });
    it('[Binary mode] should add two binary numbers', function () {
      let bullGamma = new BullGamma();
      let m1 = bullGamma.getMemory(1);
      let m2 = bullGamma.getMemory(2);
      bullGamma.setMemoryMode(MEMORY_MODE.BINARY);
      bullGamma.ms1 = 10;
      m1.setContent("0FEDCBA98765");
      m2.setContent("0AAAAAAAAAAA");
      new AN(2, 0, 12, bullGamma).execute();
      assert.equal(m1.toString(), "1A987654320F", "wrong value");
    });
    it('[Binary mode] overflow test', function () {
      let bullGamma = new BullGamma();
      let m1 = bullGamma.getMemory(1);
      let m2 = bullGamma.getMemory(2);
      bullGamma.setMemoryMode(MEMORY_MODE.BINARY);
      m1.setContent("AAAAAAAAAAAA");
      m2.setContent("AAAAAAAAAAAA");
      new AN(2, 0, 12, bullGamma).execute();
      assert.equal(m1.toString(), "555555555555", "wrong value");
    });
    it('[Binary mode] should add OF to M1', function () {
      let bullGamma = new BullGamma();
      let m1 = bullGamma.getMemory(1);
      bullGamma.setMemoryMode(MEMORY_MODE.BINARY);
      m1.setContent("0FAAAAAAAAAA");
      new AN(0, 10, 1, bullGamma).execute();
      assert.equal(m1.toString(), "10AAAAAAAAAA", "wrong value");
    });
  });
  describe('#getDescription()', function () {
    it("should print the instruction's description", function () {
      let bullGamma =  new BullGamma();
      let instr = new AN(4, 5, 7, bullGamma);
      console.debug(instr.getDescription());
      console.debug(instr.getShortType());
      console.debug(instr.getLongType());
    });
    it("should print the instruction's description", function () {
      let bullGamma =  new BullGamma();
      let instr = new AN(0, 5, 7, bullGamma);
      console.debug(instr.getDescription());
    });
  });
});
 */