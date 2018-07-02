package com.bccns.umsserviceweb.common.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
  
public class CryptUtils {
	private final static int ITERATION_NUMBER = 1000;
	final static String secretKey   = "12345678901234567890123456789012"; //32bit

	 //암호화
	  public static String AES_Encode(String str) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
	      byte[] keyData = secretKey.getBytes();
	  
	   String IV                = secretKey.substring(0,16); //16bit
	   SecretKey secureKey = new SecretKeySpec(keyData, "AES");
	   
	   Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
	   c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes()));
	   
	   byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
	   String enStr = new String(Base64.encodeBase64(encrypted));
	   
	   return enStr;
	  }
	 
	  //복호화
	  public static String AES_Decode(String str) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
    	String IV                = secretKey.substring(0,16); //16bit
	    byte[] keyData = secretKey.getBytes();
	    System.out.println(new String(keyData) );
	    SecretKey secureKey = new SecretKeySpec(keyData, "AES");
	    Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes("UTF-8")));
	   
	    byte[] byteStr = Base64.decodeBase64(str.getBytes());
	   
	    return new String(c.doFinal(byteStr),"UTF-8");
	  }
	  
	  //키생서
	  public static byte[] generationAES256_KEY() throws NoSuchAlgorithmException{
	   KeyGenerator kgen = KeyGenerator.getInstance("AES");
	   kgen.init(256);
	   SecretKey key = kgen.generateKey();
	   
	   return key.getEncoded();
	   
	  }


	/**
     * SHA256
     * 
     * @param src
     * @return
     */
    public static String encryptSHA256Hash(String src) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if(src == null || "".equals(src)) {
            return null;
        }
        byte[] bDigest = getHash(ITERATION_NUMBER,src);
        String sDigest = encryptBase64String(bDigest);
        
        return sDigest;
    }
 
    private static byte[] getHash(int iterationNb, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();
        //digest.update(salt);
        byte[] input = digest.digest(password.getBytes("UTF-8"));
        for (int i = 0; i < iterationNb; i++) {
            digest.reset();
            input = digest.digest(input);
        }
        return input;
    }
    /**
     * Base64
     * @param data
     * @return
     */
    public static String encryptBase64String(byte[] data){
        return Base64.encodeBase64String(data);
    }
    /**
     * Base64
     * @param str
     * @return
     */
    public static byte[] decryptBase64(String str) {
    	return Base64.decodeBase64(str);
    }
    
    /**
     * Base64
     * @param str
     * @return
     */
    public static String decryptBase64String(String str) {
    	return new String(Base64.decodeBase64(str));
    }
    /**
     * AES256
     * @param str
     * @param key
     * @param isPoolable default:true
     * @return
     * @throws java.io.UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
	public static String encryptAES256String(String str, String key)
			throws java.io.UnsupportedEncodingException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException {
		return encryptAES256String(str, key, true);
	}
	/**
	 * AES256 
	 * @param str
	 * @param key
	 * @return
	 * @throws java.io.UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String encryptAES256String(String str, String key, boolean isPoolable)
			throws java.io.UnsupportedEncodingException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException {
		
		byte[] textBytes = str.getBytes("UTF-8");
		return encryptBase64String(AES256.cipher(Cipher.ENCRYPT_MODE, key, isPoolable).doFinal(textBytes));
	}
	/**
	 * AES256
	 * @param str
	 * @param key
	 * @param isPoolable default:true
	 * @return
	 * @throws java.io.UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String decryptAES256String(String str, String key, boolean isPoolable)
			throws java.io.UnsupportedEncodingException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException {
	
		byte[] textBytes = decryptBase64(str);
		
	    return new String(AES256.cipher(Cipher.DECRYPT_MODE, key, isPoolable).doFinal(textBytes), "UTF-8");
	}
	/**
	 * AES256
	 * @param str
	 * @param key
	 * @param isPoolable default:true
	 * @return
	 * @throws java.io.UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String decryptAES256String(String str, String key)
			throws java.io.UnsupportedEncodingException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException {
		return decryptAES256String(str, key, true); 
	}
	/**
	 * Ǯ AES256 cipher
	 * @param key
	 */
	public static void clearAES256Cipher(String key) {
		AES256.removeCipher(key);
	}
	
	
	
	
	
	private static class AES256 {
		private static final byte[] IV_BYTES = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
		private static final Map<String, AES256> pool;
		
		static {
			pool = new HashMap<String, AES256>();
		}
		
		private Cipher encryptCipher;
		private Cipher decryptCipher;
		
		private AES256(String key) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
			this.encryptCipher = createCipher(Cipher.ENCRYPT_MODE, key);
			this.decryptCipher = createCipher(Cipher.DECRYPT_MODE, key);
		}
		
		/**
		 * AES256 cipher
		 * @param mode
		 * @param key
		 * @return
		 * @throws InvalidKeyException
		 * @throws UnsupportedEncodingException
		 * @throws NoSuchAlgorithmException
		 * @throws NoSuchPaddingException
		 * @throws InvalidAlgorithmParameterException
		 */
		private synchronized static Cipher cipher(int mode, String key, boolean isPoolable) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
			AES256 aes256;
			
			if (isPoolable == true)	{
				aes256 = pool.get(key);
				if (aes256 == null) {
					aes256 = new AES256(key);
					pool.put(key, aes256);
				}
			} else {
				aes256 = new AES256(key);
			}
			
			
			return aes256.cipher(mode);
		}
		
		private synchronized static void removeCipher(String key) {
			pool.remove(key);
		}
		
		/**
		 * cipher 
		 * @param mode
		 * @param key
		 * @throws UnsupportedEncodingException
		 * @throws NoSuchAlgorithmException
		 * @throws NoSuchPaddingException
		 * @throws InvalidKeyException
		 * @throws InvalidAlgorithmParameterException
		 */
		private Cipher createCipher(int mode, String key) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
			AlgorithmParameterSpec ivSpec = new IvParameterSpec(IV_BYTES);
			SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        cipher.init(mode, newKey, ivSpec);
	        
	        return cipher;
		}
		/**
		 * AES256 cipher
		 * @param mode	Cipher.ENCRYPT_MODE or Cipher.DECRYPT_MODE
		 * @return
		 */
		private Cipher cipher(int mode) {
			switch(mode) {
			case Cipher.ENCRYPT_MODE:	return this.encryptCipher;
			case Cipher.DECRYPT_MODE:	return this.decryptCipher;
			default:					return null;
			}
		}
	}
}
