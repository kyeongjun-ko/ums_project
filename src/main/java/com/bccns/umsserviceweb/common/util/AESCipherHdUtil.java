package com.bccns.umsserviceweb.common.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidAlgorithmParameterException;

import org.apache.commons.codec.binary.Base64;

public class AESCipherHdUtil {

	private static volatile AESCipherHdUtil INSTANCE;

	final static String secretKey = "@l$3o!tt&eegy#Np"; // 16byte(128bit) 키는 미정.
	static String IV = secretKey; // 16byte 키는 미정.

	public static AESCipherHdUtil getInstance() {
		if (INSTANCE == null) {
			synchronized (AESCipherHdUtil.class) {
				if (INSTANCE == null)
					INSTANCE = new AESCipherHdUtil();
			}
		}
		return INSTANCE;
	}

	private AESCipherHdUtil() {
		//IV = secretKey.substring(0, 16);
	}

	// 암호화
	public String AES_Encode(String str)
			throws java.io.UnsupportedEncodingException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException {
		byte[] keyData = secretKey.getBytes();

		SecretKey secureKey = new SecretKeySpec(keyData, "AES");

		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, secureKey,
				new IvParameterSpec(IV.getBytes("UTF-8")));

		byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
		String enStr = new String(Base64.encodeBase64(encrypted), "UTF-8");

		return enStr;
	}
	
	
	// 암호화
	public byte[] AES_Encode(byte[] str)
			throws java.io.UnsupportedEncodingException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException {
		byte[] keyData = secretKey.getBytes();

		SecretKey secureKey = new SecretKeySpec(keyData, "AES");

		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, secureKey,
				new IvParameterSpec(IV.getBytes("UTF-8")));

		byte[] encrypted = c.doFinal(str);
		return Base64.encodeBase64(encrypted);
	}
		

	// 복호화
	public String AES_Decode(String str)
			throws java.io.UnsupportedEncodingException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException {
		byte[] keyData = secretKey.getBytes();
		SecretKey secureKey = new SecretKeySpec(keyData, "AES");
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, secureKey,
				new IvParameterSpec(IV.getBytes("UTF-8")));

		byte[] byteStr = Base64.decodeBase64(str.getBytes("UTF-8"));

		return new String(c.doFinal(byteStr), "UTF-8");
	}
	
	// 복호화
	public byte[] AES_Decode(byte[] str)
				throws java.io.UnsupportedEncodingException,
				NoSuchAlgorithmException, NoSuchPaddingException,
				InvalidKeyException, InvalidAlgorithmParameterException,
				IllegalBlockSizeException, BadPaddingException {
		byte[] keyData = secretKey.getBytes();
		SecretKey secureKey = new SecretKeySpec(keyData, "AES");
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, secureKey,
				new IvParameterSpec(IV.getBytes("UTF-8")));

		byte[] byteStr = Base64.decodeBase64(str);
		return c.doFinal(byteStr);
	}
}
