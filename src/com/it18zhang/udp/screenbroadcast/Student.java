package com.it18zhang.udp.screenbroadcast;

/**
 * Ω” ‹∑Ω
 * 
 */
public class Student {
	public static void main(String[] args) {
		StudentUI ui = new StudentUI();
		new Receiver(ui).start();
	}
}
