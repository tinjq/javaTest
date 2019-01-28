package com.tin.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public class CriptUtil {
	
	public static void main(String[] args) {
		System.out.println(Hex.encodeHexString(getMd5bytes("hello")));
	}

	/**
	 * md5加密
	 * 
	 * @param str
	 * @return
	 */
	public static String MD5(String str) {
			return Hex.encodeHexString(getMd5bytes(str));
	}
	
	public static byte[] getMd5bytes(String str) {
		try {
			byte[] bytes = str.getBytes();
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(bytes);
			return md5.digest();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
	
}
