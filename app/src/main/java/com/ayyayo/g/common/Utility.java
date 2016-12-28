package com.ayyayo.g.common;

public class Utility {
	public String textFormatting (String input) {
		String temp = input;
		temp = temp.replace("\t", "");
		temp = temp.replace("\\t", "");
		return temp;
	}
}
