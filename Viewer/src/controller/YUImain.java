package controller;

import javax.swing.JFrame;



public class YUImain {
	public static void main(String[] args) {
	
	ControlFrame frame = new ControlFrame("YUIFrame");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	frame.setSize(565,185);

	frame.setVisible(true);
	
	System.out.println("Ready!");
	
	}
}
