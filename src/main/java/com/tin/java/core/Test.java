package com.tin.java.core;

import java.text.DecimalFormat;

public class Test {

	public static void main(String[] args) {
//		System.out.println("3".compareTo("20"));
		DecimalFormat df = new DecimalFormat("00000");
		for (int i = 0; i < 200; i+=5) {
			System.out.println(df.format(i));
		}
	}
	
}
