package ui;

import java.awt.Container;
import java.awt.GridLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import machine.BullGamma;
import machine.Execution;

public class UIPane extends JFrame {
	
	JLabel[] banals;
	BullGamma bullGamma;

	public UIPane(BullGamma bullGamma) {
		this.bullGamma=bullGamma;
		Container pane=getContentPane();
		pane.setLayout(new GridLayout(0,2));
		banals=new JLabel[7];
		for (int i=1; i<8; i++) {
			if (i==1) add(new JLabel("M0/M1:")); else add(new JLabel("M"+i+":"));
			banals[i-1]=new JLabel("xxx");
			add(banals[i-1]);
		}
		setTitle("GAMMA 3 console");
		pack();
		setVisible(true);	}
	
	public void update() {
		System.out.println("UPDATE called");
		for(int i=1; i<8; i++) {
			banals[i-1].setText(bullGamma.getMemory(i).toString());
		}
	}
	
	public static void main(String[] args) throws Exception {
		BullGamma bullGamma = new BullGamma();
		String content = new String(Files.readAllBytes(Paths.get("programs/fibo.txt")));
		System.out.println(content);
		bullGamma.series3.setInstructions(content);
		Execution exec=new Execution(bullGamma);
		UIPane app=new UIPane(bullGamma);
		exec.setUIPane(app);
		System.out.println("CURRENT SERIE: "+exec.getCurrentSeries());
		System.out.println("CURRENT INSTR: "+exec.getCurrentLine());
		for(int i=0; i<200; i++) {
			exec.executeNextInstruction();
			Thread.sleep(100);
		}

	}
}
