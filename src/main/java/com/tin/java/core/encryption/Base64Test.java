package com.tin.java.core.encryption;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * Base64加密，16进制加密
 * @author Administrator
 *
 */
public class Base64Test {
	
	public static void main(String[] args) {
		try {
			base64TestJdk8();
			hexTest();
			md5Test();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (DecoderException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 需引入commons-codec.jar
	 * jar包下载：http://commons.apache.org/proper/commons-codec/download_codec.cgi
	 */
	public static void base64Test() {
		String str = "hello";
		org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();
		String base64Encode = base64.encodeToString(str.getBytes());
		System.out.println(base64Encode);
		
		String base64Decode = new String(base64.decode(base64Encode));
		System.out.println(base64Decode);
	}
	
	/**
	 * Java 8的java.util套件中，新增了Base64的类，可以用来处理Base64的编码与解码
	 * @throws UnsupportedEncodingException 
	 */
	public static void base64TestJdk8() throws UnsupportedEncodingException {
		Base64.Decoder decoder = Base64.getDecoder();
		Base64.Encoder encoder = Base64.getEncoder();
		String text = "字串文字";
		byte[] textByte = text.getBytes("UTF-8");
		//编码
		String encodedText = encoder.encodeToString(textByte);
		System.out.println(encodedText);
		
		String decodeText = new String(decoder.decode(encodedText));
		System.out.println(decodeText);
	}
	
	/**
	 * 16进制加解密
	 * @throws DecoderException 
	 */
	public static void hexTest() throws DecoderException {
		String str = "hello";
		String encodeStr = Hex.encodeHexString(str.getBytes());
		System.out.println(encodeStr);
		
		String decodeStr = new String(Hex.decodeHex(encodeStr.toCharArray()));
		System.out.println(decodeStr);
		
	}
	
	public static void md5Test() {
		String str = "hello";
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			
	        //加密后的字符串
	        String newstr = new String(Base64.getEncoder().encode(md5.digest(str.getBytes("utf-8"))));
	        System.out.println(newstr);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * md5加密
	 * 
	 * @param str
	 * @return
	 */
	public static String MD5(String str) {
		char hexDigits[] = { '0', 'a', '1', 'b', '2', 'c', '3', 'd', '4', 'e',
				'5', 'f', '6', 'g', '7', 'h' };
		try {
			byte[] strTemp = str.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char schar[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				schar[k++] = hexDigits[byte0 >>> 4 & 0xf];
				schar[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(schar);
		} catch (Exception e) {
			return null;
		}
	}

}
