package com.bccns.umsserviceweb.common.util;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * String <-> JSON
 *
 */
public class JSONParser {
	private static final Logger LOGGER = LoggerFactory.getLogger(JSONParser.class);
	
//	public static void main(String[] a) {
//		LBSpRequestVO vo = new LBSpRequestVO();
//		
//		vo.setMachineId("111");
//		vo.setRequestId("0390239802");
//		vo.setHpNo("01011112222");
//		vo.setCompanyName("���Ĩ");
//		vo.setAgreeYn("Y");
//		
//		System.out.println(toString(vo));
//	}
	
	
	/**
	 * String�� Object�� ��ȯ
	 * @param src
	 * @param clazz
	 * @return
	 */
	public static <T> T toObject(String src, Class<T> clazz) {
		JSONObject jsonObject = JSONObject.fromObject(src);
		
		@SuppressWarnings("unchecked")
		T bean = (T) JSONObject.toBean(jsonObject, clazz);
		
		LOGGER.debug("toObject - [{}]", bean);
		
		return bean;
	}
	
	/**
	 * Object�� String���� ��ȯ
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		String str = JSONObject.fromObject(obj).toString(); 
		LOGGER.debug("toString - [{}]", str);
		return str;
	}
}
