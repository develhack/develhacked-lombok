package com.develhack.lombok;


public class Version {

	private static final String VERSION = "0.1.0";

	public static void main(String[] args) {
		System.out.println(VERSION);
	}

	public static String getVersion() {
		return VERSION;
	}
}
