package tests;

import org.junit.Test;

import machine.BullGamma;
import machine.Execution;
import ui.UIPane;

import static org.junit.Assert.*;

import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JLabel;

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
		for(int i=0; i<10; i++)
			exec.executeNextInstruction();		
	}
}
