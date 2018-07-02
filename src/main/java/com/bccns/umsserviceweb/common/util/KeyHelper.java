package com.bccns.umsserviceweb.common.util;

import java.util.Random;
 
public abstract class KeyHelper {
	
	/** 키 생성기 */
	private static KeyGenerator keyGenerator = new KeyGenerator();
	
	/** String 으로 생성된 13 자리 문자열을 반환한다. */
	public static String getStringKey() {
		return keyGenerator.getStringKey();
	}
	
	/** 키 생성 클래스 */
	public static class KeyGenerator {
		
		public String getStringKey() {
			Random ran = new Random();
			String key = Long.toHexString(System.currentTimeMillis()) + "" + ran.nextInt(99);
			return key;	
		}
	}

}