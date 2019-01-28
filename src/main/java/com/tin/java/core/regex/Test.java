package com.tin.java.core.regex;

public class Test {
	
	public static void main(String[] args) {
		String regex = "^[a-zA-Z0-9]*$";
		String value = "sdf23是";
		System.out.println(value.matches(regex));
	}

}
