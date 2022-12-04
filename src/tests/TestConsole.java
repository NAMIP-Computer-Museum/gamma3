package tests;

import org.junit.Test;
import static org.junit.Assert.*;
import instructions.Tools;
import machine.Console;


public class TestConsole {
	
	@Test 
	public void testPow() {  // TODO move test
		System.out.println("POW");
		assertEquals(Tools.pow(10,0),1);		
		assertEquals(Tools.pow(10,2),100);
		assertEquals(Tools.pow(10,10),10000000000L);
		assertEquals(Tools.pow(2,0),1);		
		assertEquals(Tools.pow(2,10),1024);
		assertEquals(Tools.pow(16,0),1);		
		assertEquals(Tools.pow(16,8),4294967296L);
	}
	
	void compare(double a, double b) {
		double diff=Math.abs(a-b);
		System.out.println(a+" "+b+" "+diff);
		assertTrue((diff<0.0000001));
	}

	@Test
	public void testConvertToDouble() {
		System.out.println("should convert to readable double");
		Console machine = new Console();
		compare(machine.convertToDouble("3FC000000000"), 1);
		compare(machine.convertToDouble("BFC000000000"), -1);
		compare(machine.convertToDouble("404000000000"), 2);
		compare(machine.convertToDouble("415000000000"), 10);
	}

	@Test
	public void testConvertToLong() {
		System.out.println("should convert to readable long");
		Console machine = new Console();
		assertEquals(machine.convertToLong("000000000001"), 1);
		assertEquals(machine.convertToLong("800000000144"), -324);
		assertEquals(machine.convertToLong("123456789ABC"), 55200370432700L);
	}

}
