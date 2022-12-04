package tests;

import org.junit.Test;

import instructions.Instruction;
import machine.BullGamma;
import machine.Execution;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class testRun {

	
	@Test
	public void testParse() throws Exception {
		BullGamma bullGamma = new BullGamma();
		String content = new String(Files.readAllBytes(Paths.get("programs/fibo.txt")));
		System.out.println(content);
		bullGamma.series3.setInstructions(content);
		Execution exec=new Execution(bullGamma);
		System.out.println("CURRENT SERIE: "+exec.getCurrentSeries());
		System.out.println("CURRENT INSTR: "+exec.getCurrentLine());
		for(int i=0; i<200; i++)
			exec.executeNextInstruction();
	}
}
