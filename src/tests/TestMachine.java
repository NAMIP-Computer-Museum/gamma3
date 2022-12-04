package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import machine.BullGamma;
import machine.Word;

public class TestMachine {

	@Test
	public void testGetMemory() {
		//should get from the current octad'
		BullGamma bullGamma = new BullGamma();
		bullGamma.getMemory(13, 3).blocks[2] = 12;
		bullGamma.setCommutedOctad(3);
		assertEquals(bullGamma.getMemory(13).blocks[2], 12);
		assertEquals(bullGamma.getMemory(13, -1).blocks[2], 12);
		System.out.println("DONE");
	}

	@Test
	public void getExtractors() {
		// should return extractors values
		BullGamma bullGamma = new BullGamma();
		Word[] extractors = bullGamma.getExtractors();
        assertEquals(extractors.length, 4);
	}
	
	@Test
	public void getIntroductors() {
		// should return introductors values
		BullGamma bullGamma = new BullGamma();
		Word[] introductors = bullGamma.getIntroductors();
		assertEquals(introductors.length, 4);
	}
}