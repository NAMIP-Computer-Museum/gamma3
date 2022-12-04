package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import machine.Word;

public class TestMemory {
	
	@Test
	public void testWord() {
		Word w=new Word();
		String s1="ABCDEF012345";
		w.setContent(s1);
		String s2=w.toString();
		assertEquals(s1,s2);
	}

}
