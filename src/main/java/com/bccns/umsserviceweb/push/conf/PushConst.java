package com.bccns.umsserviceweb.push.conf;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class PushConst {
	
	public static final String RESULT_SUC_00000 = "S00000";
	public static final String RESULT_SUC_00001 = "S00001";
	public static final String RESULT_ERR_00000 = "E00000";
	public static final String RESULT_ERR_00001 = "E00001";
	public static final String RESULT_ERR_00002 = "E00002";
	public static final String RESULT_ERR_00003 = "E00003";
	public static final String RESULT_ERR_00100 = "E00100";
	public static final String RESULT_ERR_00101 = "E00101";
	public static final String RESULT_ERR_00300 = "E00300";
	public static final String RESULT_ERR_09999 = "E09999";
	
	public static Map<String, String> resultMap = new HashMap<String, String>();
	static {
//			try {
//				resultMap.put("S00000",	new String("요청 성공".getBytes("UTF-8")));
//				resultMap.put("S00001",	new String("발송 성공 ".getBytes("UTF-8")));
//				resultMap.put("E00000", new String("요청중".getBytes("UTF-8")));
//				resultMap.put("E00001",	new String("요청전문 헤더값 오류".getBytes("UTF-8")));
//				resultMap.put("E00002",	new String("요청전문 필수값 오류".getBytes("UTF-8")));
//				resultMap.put("E00003",	new String("요청전문 SVCID 오류".getBytes("UTF-8")));
//				resultMap.put("E00100",	new String("DB처리 오류(입력오류)".getBytes("UTF-8")));
//				resultMap.put("E00101",	new String("DB처리 오류(중복입력)".getBytes("UTF-8")));
//				resultMap.put("E09999",	new String("기타 시스템장애".getBytes("UTF-8")));
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			resultMap.put("S00000",	"요청 성공");
			resultMap.put("S00001",	"발송 성공 ");
			resultMap.put("E00000", "요청중");
			resultMap.put("E00001",	"요청전문 헤더값 오류");
			resultMap.put("E00002",	"요청전문 필수값 오류");
			resultMap.put("E00003",	"요청전문 SVCID 오류");
			resultMap.put("E00100",	"DB처리 오류(입력오류)");
			resultMap.put("E00101",	"DB처리 오류(중복입력)");
			resultMap.put("E00300",	"비콘메시지가 없음");
			resultMap.put("E09999",	"기타 시스템장애");
	}

}
