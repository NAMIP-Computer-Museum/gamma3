package tests;

import instructions.SN;
import instructions.Tools;
import machine.BullGamma;
import machine.Memory;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestSN {

	@Test
	public void testSubM1posMBpos() {
		// should subtract when M1 and MB are positive
		BullGamma bullGamma = new BullGamma();
		Memory m1 = bullGamma.getMemory(1);
		Memory m2 = bullGamma.getMemory(2);
		byte od = 0, of = 8;
		for (int i = od; i < of; ++i) {
			m1.blocks[i] = 9;
			m2.blocks[i] = 9;
		}
		new SN((byte)2, (byte)0, (byte)8, bullGamma).execute();
		assertEquals(Tools.toLong(m1), 0);
	}

	@Test
	public void testSubM1negM2neg() {
		// should subtract when M1 and MB are negative
		BullGamma bullGamma = new BullGamma();
		Memory m1 = bullGamma.getMemory(1);
		Memory m2 = bullGamma.getMemory(2);
		byte od = 0, of = 8;
		for (int i = od; i < of; ++i) {
			m1.blocks[i] = 9;
			m2.blocks[i] = 9;
		}
		m2.blocks[of - 1] = 10;
		bullGamma.ms1 = 10;
		new SN((byte)2, (byte)0, (byte)8, bullGamma).execute();
		assertEquals(Tools.toLong(m1), 90000000);
		assertEquals(bullGamma.ms1, 10);
	}

	@Test
	public void testM1posMBneg() {
		// should subtract when M1 is positive and MB is negative
		BullGamma bullGamma = new BullGamma();
		Memory m1 = bullGamma.getMemory(1);
		Memory m2 = bullGamma.getMemory(2);
		byte od = 0, of = 8;
		for (int i = od; i < of; ++i) {
			m1.blocks[i] = 9;
			m2.blocks[i] = 9;
		}
		m2.blocks[of - 1] = 10;
		new SN((byte)2, (byte)0, (byte)8, bullGamma).execute();
		assertEquals(Tools.toLong(m1), 109999998);
	}

}

/*
	    it('should subtract when M1 is negative and MB is positive', function () {
	      let bullGamma = new BullGamma();
	      let m1 = bullGamma.getMemory(1);
	      let m2 = bullGamma.getMemory(2);
	      let od = 0, of = 8;
	      for (let i = od; i < of; ++i) {
	        m1.blocks[i] = 9;
	        m2.blocks[i] = 9;
	      }
	      bullGamma.ms1 = 10;
	      new SN(2, 0, 8, bullGamma).execute();
	      let total = 0;
	      for (let i = 0; i < 12; ++i) {
	        total += m1.blocks[i]*10**i;
	      }
	      assert.equal(total, 199999998, "wrong result");
	      assert.equal(bullGamma.ms1, 10, "wrong sign");
	    });
	    it('when AD = 1, should reset M1 and invert sign (M1 positive)', function () {
	      let bullGamma = new BullGamma();
	      let m1 = bullGamma.getMemory(1);
	      let od = 0, of = 8;
	      for (let i = od; i < of; ++i) {
	        m1.blocks[i] = 9;
	      }
	      new SN(1, 0, 8, bullGamma).execute();
	      let total = 0;
	      for (let i = 0; i < 12; ++i) {
	        total += m1.blocks[i]*10**i;
	      }
	      assert.equal(total, 0, "wrong result");
	      assert.equal(bullGamma.ms1, 10, "wrong sign");
	    });
	    it('when AD = 1, should reset M1 and invert sign (M1 negative)', function () {
	      let bullGamma = new BullGamma();
	      let m1 = bullGamma.getMemory(1);
	      let od = 0, of = 8;
	      for (let i = od; i < of; ++i) {
	        m1.blocks[i] = 9;
	      }
	      bullGamma.ms1 = 10;
	      new SN(1, 0, 8, bullGamma).execute();
	      let total = 0;
	      for (let i = 0; i < 12; ++i) {
	        total += m1.blocks[i]*10**i;
	      }
	      assert.equal(total, 0, "wrong result");
	      assert.equal(bullGamma.ms1, 0, "wrong sign");
	    });
	    it('when AD = 0, should subtract OF to M1[OD]', function () {
	      let bullGamma = new BullGamma();
	      let m1 = bullGamma.getMemory(1);
	      m1.blocks[2] = 8;
	      new SN(0, 2, 8, bullGamma).execute();
	      assert.equal(m1.blocks[2], 0, "wrong result");
	    });
	    it('when AD = 0, should subtract OF to M1[OD] (overflow test) ', function () {
	      let bullGamma = new BullGamma();
	      let m1 = bullGamma.getMemory(1);
	      m1.blocks[2] = 8;
	      new SN(0, 2, 9, bullGamma).execute();
	      assert.equal(m1.blocks[2], 1, "wrong result");
	    });
	    it('[Binary mode] should subtract MB to M1', function () {
	      let bullGamma = new BullGamma();
	      let m1 = bullGamma.getMemory(1);
	      let m2 = bullGamma.getMemory(2);
	      bullGamma.setMemoryMode(MEMORY_MODE.BINARY);
	      bullGamma.ms1 = 10;
	      m1.setContent("FEDCBA987654");
	      m2.setContent("AAAAAAAAAAAA");
	      new SN(2, 0, 12, bullGamma).execute();
	      assert.equal(m1.toString(), "54320FEDCBAA", "wrong value");
	    });
	    it('[Binary mode] should keep absolute value when MB > M1', function () {
	      let bullGamma = new BullGamma();
	      let m1 = bullGamma.getMemory(1);
	      let m2 = bullGamma.getMemory(2);
	      bullGamma.setMemoryMode(MEMORY_MODE.BINARY);
	      bullGamma.ms1 = 10;
	      m2.setContent("FEDCBA987654");
	      m1.setContent("AAAAAAAAAAAA");
	      new SN(2, 0, 12, bullGamma).execute();
	      assert.equal(m1.toString(), "54320FEDCBAA", "wrong value");
	    });
	    it('[Binary mode] should subtract OF to M1', function () {
	      let bullGamma = new BullGamma();
	      let m1 = bullGamma.getMemory(1);
	      bullGamma.setMemoryMode(MEMORY_MODE.BINARY);
	      m1.setContent("EAAAAAAAAAAA");
	      new SN(0, 11, 0xf, bullGamma).execute();
	      assert.equal(m1.toString(), "055555555556", "wrong value");
	    });
	    it('[Binary mode] when AD = 1, should reset M1', function () {
	      let bullGamma = new BullGamma();
	      let m1 = bullGamma.getMemory(1);
	      bullGamma.setMemoryMode(MEMORY_MODE.BINARY);
	      let od = 0, of = 8;
	      for (let i = od; i < of; ++i) {
	        m1.blocks[i] = 9;
	      }
	      bullGamma.ms1 = 10;
	      new SN(1, 0, 8, bullGamma).execute();
	      let total = 0;
	      for (let i = 0; i < 12; ++i) {
	        total += m1.blocks[i]*10**i;
	      }
	      assert.equal(total, 0, "wrong result");
	      assert.equal(bullGamma.ms1, 10, "wrong sign");
	    });
	  });
	  describe('#getDescription()', function () {
	    it("should print the instruction's description", function () {
	      let bullGamma =  new BullGamma();
	      let instr = new SN(4, 5, 7, bullGamma);
	      console.debug(instr.getDescription());
	      console.debug(instr.getShortType());
	      console.debug(instr.getLongType());
	    });
	    it("should print the instruction's description", function () {
	      let bullGamma =  new BullGamma();
	      let instr = new SN(0, 5, 7, bullGamma);
	      console.debug(instr.getDescription());
	      console.debug(instr.getShortType());
	      console.debug(instr.getLongType());
	    });
	  });
	});
 */