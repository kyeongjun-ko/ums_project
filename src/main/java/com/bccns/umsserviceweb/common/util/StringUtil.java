package com.bccns.umsserviceweb.common.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * String 관련 기능을 제공하는 클래스
 */
public class StringUtil extends StringUtils {

    // public static final String ARRAY_DELIMETER = "|";
    // public static final String KEYVALUE_DELIMETER = "=";
    //
    public static String convertHtml(String str) {
        String resultStr = "";
        if ( str == null || str.equals("") ) {
            return str;
        }

        resultStr = str.replaceAll("<", "&lt;");
        resultStr = str.replaceAll(">", "&gt;");
        resultStr = str.replaceAll("\"", "&quot;");
        return resultStr;
    }

    // public static Map convertHtml(Map map) {
    // Set set = map.entrySet();
    // Iterator it = set.iterator();
    // while(it.hasNext()) {
    // Map.Entry e = (Map.Entry)it.next();
    // Object value = e.getValue();
    // if(value instanceof String) {
    // String convertedValue = convertHtml((String)value);
    // map.put(e.getKey(), convertedValue);
    // }
    // }
    // return map;
    // }
    //
    // public static List convertHtml(List list) {
    // List resultList = new ArrayList();
    // Iterator<Map> it = list.iterator();
    // while(it.hasNext()) {
    // Map map = it.next();
    // resultList.add(convertHtml(map));
    // }
    // return resultList;
    // }

    public static String reconvertHtml(String str) {
        String resultStr = "";
        if ( str == null || str.equals("") ) {
            return str;
        }

        resultStr = str.replaceAll("&lt;", "<");
        resultStr = str.replaceAll("&gt;", ">");
        resultStr = str.replaceAll("&quot;", "\"");
        return resultStr;
    }

    // public static Map reconvertHtml(Map map) {
    // Set set = map.entrySet();
    // Iterator it = set.iterator();
    // while(it.hasNext()) {
    // Map.Entry e = (Map.Entry)it.next();
    // Object value = e.getValue();
    // if(value instanceof String) {
    // String reconvertedValue = reconvertHtml((String)value);
    // map.put(e.getKey(), reconvertedValue);
    // }
    // }
    // return map;
    // }
    //
    // public static List reconvertHtml(List list) {
    // List resultList = new ArrayList();
    // Iterator<Map> it = list.iterator();
    // while(it.hasNext()) {
    // Map map = it.next();
    // resultList.add(reconvertHtml(map));
    // }
    // return resultList;
    // }

    // public static String toString(Object value){
    // return value==null?"":value.toString();
    // }

    public static String byteToHex(byte[] src) {
        if ( src == null )
            return null;

        StringBuilder sb = new StringBuilder();
        String str = null;
        for ( int i = 0 ; i < src.length ; i++ ) {
            str = Integer.toHexString(src[i] & 0xff);
            sb.append(str.length() == 1 ? "0" + str : str);
        }
        return sb.toString();
    }

    public static byte[] hexToByte(String src) {
        if ( src == null )
            return null;

        if ( src.length() % 2 != 0 )
            src = "0" + src;

        byte[] result = new byte[src.length() / 2];
        for ( int i = 0 ; i < result.length ; i++ ) {
            result[i] = (byte) Integer.parseInt(src.substring(2 * i, 2 * i + 2), 16);
        }
        return result;
    }

    public static HashMap<String, Object> strToMap(String str, String arrayDelim,
            String keyValueDelim) {
        HashMap<String, Object> paramMap = new HashMap<String, Object>();

        if ( str != null ) {
            String[] params = str.split("\\" + arrayDelim);
            for ( int i = 0 ; i < params.length ; i++ ) {
                String[] strs = params[i].split(keyValueDelim);
                if ( strs.length == 2 ) {
                    String key = strs[0].trim();
                    String value = strs[1].trim();
                    paramMap.put(key, value);
                }
            }
        }

        return paramMap;
    }

    @SuppressWarnings("rawtypes")
    public static String mapToStr(Map paramMap, String arrayDelim, String keyValueDelim) {
        if ( paramMap == null )
            return null;

        Iterator iter = paramMap.entrySet().iterator();
        StringBuilder strBuf = new StringBuilder();

        while ( iter.hasNext() ) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            strBuf.append(key);
            strBuf.append(keyValueDelim);
            strBuf.append(valueOf(value));
            strBuf.append(arrayDelim);
        }

        return strBuf.toString();
    }

    /**
     * value를 String으로 변환해 줌.
     * 
     * @param value
     * @return
     */
    public static String valueOf(Object value) {
        return valueOf(value, String.valueOf((Object) null));
    }

    /**
     * value를 String으로 변환해 줌. vaule가 null이면 defaultValue를 리턴함.
     * 
     * @param value
     * @param defaultValue
     * @return
     */
    public static String valueOf(Object value, String defaultValue) {
        return value == null ? defaultValue : String.valueOf(value);
    }

    /**
     * value를 String으로 변환해 줌. vaule가 null이거나 공백이면 defaultValue를 리턴함.
     * 
     * <pre>
     * StringUtil.valueIfEmpty(null, &quot;NULL&quot;)  = &quot;NULL&quot;
     * StringUtil.valueIfEmpty(&quot;&quot;, &quot;NULL&quot;)    = &quot;NULL&quot;
     * StringUtil.valueIfEmpty(&quot;bat&quot;, &quot;NULL&quot;) = &quot;bat&quot;
     * </pre>
     * 
     * @param value
     * @param defaultValue
     * @return
     */
    public static String valueIfEmpty(Object value, String defaultValue) {
        return defaultIfEmpty(valueOf(value, ""), defaultValue);
    }

    /**
     * value값을 boolean형으로 리턴함. value가 유효하지 않은 경우 false를 리턴함.<br>
     * value 값이 대소문자 구분 없는 "true"일 때만 true 리턴함.
     * 
     * @param value
     * @return
     */
    public static boolean parseBoolean(Object value) {
        return Boolean.valueOf(valueOf(value)).booleanValue();
    }

    /**
     * value값을 boolean형으로 리턴함. value가 유효하지 않은 경우 false를 리턴함.<br>
     * value 값이 대소문자 구분 없는 "true"일 때만 true 리턴함.
     * 
     * @param value
     * @param defaultValue
     *            value가 null 일때 기본값.
     * @return
     */
    public static boolean parseBoolean(Object value, boolean defaultValue) {
        return value == null ? defaultValue : Boolean.valueOf(valueOf(value)).booleanValue();
    }

    /**
     * value값을 int형으로 리턴함. value가 유효하지 않은 경우 0을 리턴함.
     * 
     * @param value
     * @return
     */
    public static int parseInt(Object value) {
        return parseInt(value, 0);
    }

    /**
     * value값을 int형으로 리턴함. value가 유효하지 않은 경우 defaultValue를 리턴함.
     * 
     * @param value
     * @return
     */
    public static int parseInt(Object value, int defaultValue) {
        Number number = parseNumber(value);
        return number == null ? defaultValue : number.intValue();
    }

    /**
     * value값을 long형으로 리턴함. value가 유효하지 않은 경우 0을 리턴함.
     * 
     * @param value
     * @return
     */
    public static long parseLong(Object value) {
        return parseLong(value, 0L);
    }

    /**
     * value값을 long형으로 리턴함. value가 유효하지 않은 경우 defaultValue를 리턴함.
     * 
     * @param value
     * @return
     */
    public static long parseLong(Object value, long defaultValue) {
        Number number = parseNumber(value);
        return number == null ? defaultValue : number.longValue();
    }

    /**
     * value값을 float형으로 리턴함. value가 유효하지 않은 경우 0.0을 리턴함.
     * 
     * @param value
     * @return
     */
    public static float parseFloat(Object value) {
        return parseFloat(value, 0.0f);
    }

    /**
     * value값을 float형으로 리턴함. value가 유효하지 않은 경우 defaultValue를 리턴함.
     * 
     * @param value
     * @return
     */
    public static float parseFloat(Object value, float defaultValue) {
        Number number = parseNumber(value);
        return number == null ? defaultValue : number.floatValue();
    }

    /**
     * value값을 double형으로 리턴함. value가 유효하지 않은 경우 0.0을 리턴함.
     * 
     * @param value
     * @return
     */
    public static double parseDouble(Object value) {
        return parseDouble(value, 0.0d);
    }

    /**
     * value값을 double형으로 리턴함. value가 유효하지 않은 경우 defaultValue를 리턴함.
     * 
     * @param value
     * @return
     */
    public static double parseDouble(Object value, double defaultValue) {
        Number number = parseNumber(value);
        return number == null ? defaultValue : number.doubleValue();
    }

    /**
     * value값을 Number형으로 리턴함. value가 유효하지 않은 경우 null를 리턴함.
     * 
     * @param value
     * @return
     */
    public static Number parseNumber(Object value) {
        try {
            return NumberFormat.getInstance().parse(valueOf(value));
        }
        catch ( ParseException e ) {
            return null;
        }
    }

    private static int byteChar(char chr) {
        if ( Character.getType(chr) == 5 )
            return 2;
        else if ( chr > 0x007f )
            return 2;
        else
            return 1;
    }

    public static String leftB(String str, int max) {
        int subLen = 0;
        StringBuilder sf = new StringBuilder();

        for ( int i = 0 ; i < str.length() ; i++ ) {
            subLen += byteChar(str.charAt(i));
            if ( subLen <= max )
                sf.append(str.charAt(i));
        }

        return sf.toString();
    }

    public static String leftB(StringBuffer str, int max) {
        int subLen = 0;
        StringBuilder sf = new StringBuilder();

        for ( int i = 0 ; i < str.length() ; i++ ) {
            subLen += byteChar(str.charAt(i));
            if ( subLen <= max )
                sf.append(str.charAt(i));
        }

        return sf.toString();
    }

    public static String rightB(String str, int max) {
        int subLen = 0;
        StringBuilder sf = new StringBuilder();

        for ( int i = str.length() ; i > 0 ; i-- ) {
            subLen += byteChar(str.charAt(i - 1));
            if ( subLen <= max )
                sf.append(str.charAt(i - 1));
        }

        return sf.reverse().toString();
    }

    public static String rightB(StringBuilder str, int max) {
        int subLen = 0;
        StringBuilder sf = new StringBuilder();

        for ( int i = str.length() ; i > 0 ; i-- ) {
            subLen += byteChar(str.charAt(i - 1));
            if ( subLen <= max )
                sf.append(str.charAt(i - 1));
        }

        return sf.reverse().toString();
    }

    public static String substrB(String str, int start, int len) {
        return leftB(substrB(str, start), len);
    }

    public static String substrB(StringBuffer str, int start, int len) {
        return leftB(substrB(str, start), len);
    }

    public static String substrB(String str, int start) {
        int subLen = 0;
        StringBuilder sf = new StringBuilder();

        for ( int i = 0 ; i < str.length() ; i++ ) {
            subLen += byteChar(str.charAt(i));
            if ( subLen >= start )
                sf.append(str.charAt(i));
        }

        return sf.toString();
    }

    public static String substrB(StringBuffer str, int start) {
        int subLen = 0;
        StringBuilder sf = new StringBuilder();

        for ( int i = 0 ; i < str.length() ; i++ ) {
            subLen += byteChar(str.charAt(i));
            if ( subLen >= start )
                sf.append(str.charAt(i));
        }

        return sf.toString();
    }

    public static int lengthB(String str) {
        int subLen = 0;

        for ( int i = 0 ; i < str.length() ; i++ ) {
            subLen += byteChar(str.charAt(i));
        }

        return subLen;
    }

    public static int lengthB(StringBuffer str) {
        int subLen = 0;

        for ( int i = 0 ; i < str.length() ; i++ ) {
            subLen += byteChar(str.charAt(i));
        }

        return subLen;
    }

    public static String reverse(String str) {
        return new StringBuilder(str).reverse().toString();
    }

    public static String reverse(StringBuffer str) {
        return new StringBuilder(str.toString()).reverse().toString();
    }

    public static String getCommaFormat(String dec) {
        double result = Double.parseDouble(dec + "");
        NumberFormat fmat = NumberFormat.getInstance();

        if ( fmat instanceof DecimalFormat ) {
            ( (DecimalFormat) fmat ).setDecimalSeparatorAlwaysShown(false);
        }

        return fmat.format((int) result);
    }

    public static String getCommaFormat(double dec) {
        double result = dec;
        NumberFormat fmat = NumberFormat.getInstance();

        if ( fmat instanceof DecimalFormat ) {
            ( (DecimalFormat) fmat ).setDecimalSeparatorAlwaysShown(false);
        }

        return fmat.format((int) result);
    }

    public static String getCommaFormat(int dec) {
        double result = (double) dec;
        NumberFormat fmat = NumberFormat.getInstance();

        if ( fmat instanceof DecimalFormat ) {
            ( (DecimalFormat) fmat ).setDecimalSeparatorAlwaysShown(false);
        }

        return fmat.format((int) result);
    }

    public static String makeChar(String str, int cnt) {
        StringBuilder sf = new StringBuilder("");

        for ( int i = 0 ; i < cnt ; i++ ) {
            sf.append(str);
        }

        return sf.toString();
    }

    public static String makeZero(int cnt) {
        return makeChar("0", cnt);
    }

    public static String fillZero(String str, int max) {
        return rightB(makeZero(max) + str, max);
    }

    public static String fillZero(int i, int max) {
        return rightB(makeZero(max) + StringUtil.valueOf(i, ""), max);
    }

    public static String fillZero(long i, int max) {
        return rightB(makeZero(max) + StringUtil.valueOf(i, ""), max);
    }

    public static String fillZeroR(String str, int max) {
        return leftB(str + makeZero(max), max);
    }

    public static String fillZeroR(int i, int max) {
        return leftB(StringUtil.valueOf(i, "") + makeZero(max), max);
    }

    public static String fillZeroR(long i, int max) {
        return leftB(StringUtil.valueOf(i, "") + makeZero(max), max);
    }

    public static String makeSpace(int cnt) {
        return makeChar(" ", cnt);
    }

    public static String fillSpace(String str, int max) {
        return rightB(makeSpace(max) + str, max);
    }

    public static String fillSpaceR(String str, int max) {
        return leftB(str + makeSpace(max), max);
    }

    public static String toBasicDate(String str) {
        String tStr = leftB(StringUtil.replace(
                StringUtil.replace(StringUtil.replace(str, "/", ""), "-", ""), ".", ""), 8);
        if ( lengthB(tStr) == 8 ) {
            int y = StringUtil.parseInt(leftB(tStr, 4));
            int m = StringUtil.parseInt(substrB(tStr, 5, 2));
            int d = StringUtil.parseInt(substrB(tStr, 7, 2));

            if ( y < 1900 || y > 2100 ) {
                return null;
            }
            if ( m < 1 || m > 12 ) {
                return null;
            }
            if ( d < 1 || d > 31 ) {
                return null;
            }

            return tStr;
        }
        else {
            return null;
        }
    }

    /**
     * 문자열에서 숫자만 골라내는 함수
     * 
     * @param str
     * @return
     */
    public static String extractNumber(String str) {
        return extractNumber(str, false, false);
    }

    /**
     * 문자열에서 숫자,+,-만 골라내는 함수
     * 
     * @param str
     * @return
     */
    public static String extractTelNumber(String str) {
        return extractNumber(str, true, true);
    }

    /**
     * 문자열에서 숫자만 골라내는 함수 (음수포함)
     * 
     * @param str
     * @return
     */
    public static String extractNumber(String str, boolean plus, boolean minus) {
        StringBuilder numeral = new StringBuilder("");
        String temp = "";

        if ( str == null ) {
            return null;
        }
        else {
            for ( int i = 0 ; i < str.length() ; i++ ) {
                temp = str.substring(i, i + 1);

                if ( i == 0 ) {// 첫글자 체크
                    if ( plus && temp.equals("+") || minus && temp.equals("-") ) {
                        numeral.append(temp);
                    }
                }

                if ( Character.isDigit(str.charAt(i)) ) {
                    numeral.append(temp);
                }
            }
        }
        return numeral.toString();
    }

    public static String thumbnailExt(String contentType, String ext) {
        String returnExt = "";

        if ( !contentType.equals("video") ) {
            int index = ext.lastIndexOf(".");
            if ( index < 0 ) {
                returnExt = ext;
            }
            else {
                returnExt = ext.substring(index + 1);
            }
        }
        return valueIfEmpty(returnExt, "jpg");
    }

    /**
     * 파일명에서 확장자 조회
     * 
     * @param contentType
     * @param ext
     * @return
     */
    public static String contentExt(String contentType, String ext) {
        String returnExt = "";

        int index = ext.lastIndexOf(".");
        if ( index < 0 ) {
            returnExt = ext;
        }
        else {
            returnExt = ext.substring(index + 1);
        }

        return valueIfEmpty(returnExt, "jpg");
    }

    /**
     * 숫자로만 이루어진 문자인지 확인.
     * 
     * @param str
     * @return
     */
    public static boolean isNumberic(String str) {
        return str.matches("-?\\d+");
    }

    /**
     * 숫자와 (-)로만 이루어진 문자인지 확인.
     * 
     * @param str
     * @return
     */
    public static boolean isPhoneNumber(String str) {
        return str.matches("[-+\\d]+");
    }

    /**
     * 영문 숫자인지 확인.
     * 
     * @param str
     * @return
     */
    public static boolean isAlphaNum(String str) {
        return str.matches("[a-zA-Z\\d]+");
    }

    /**
     * Multiple Value Convert List
     * value1,value2,value3,... -> List<String>
     * 
     * @param value
     * @return
     */
    public static List<String> getListOfMultipleValues(String... values) {
        List<String> resultList = new ArrayList<String>();
        for ( String value : values ) {
            resultList.add(value);
        }
        return resultList;
    }

    /**
     * Multiple Value Convert List
     * value1,value2,value3,... -> List<String>
     * 
     * @param value
     * @return
     */
    public static List<String> getListOfMultipleValues(String value) {
        return getListOfMultipleValues(StringUtil.split(value, ","));
    }

    /**
     * Multiple Value Convert List
     * value1,value2,value3,... -> List<String>
     * 
     * @param value
     * @return
     */
    public static Map<String, String> getMapOfMultipleValues(String... values) {
        if ( values == null || values.length <= 0 ) {
            return Collections.emptyMap();
        }

        Map<String, String> map = new HashMap<String, String>(values.length);
        for ( String value : values ) {
            map.put(value, value);
        }
        return map;
    }

    /**
     * Multiple Value Convert List
     * value1,value2,value3,... -> List<String>
     * 
     * @param value
     * @return
     */
    public static Map<String, String> getMapOfMultipleValues(String value) {
        return getMapOfMultipleValues(StringUtil.split(value, ","));
    }

    /**
     * 여러개의 문자열을 하나로 붙여러 리턴한다.
     * 
     * @param str0
     *            첫번째 기본 파라메터
     * @param str
     *            두번째 옵션 파라메터
     * @return 하나로 만들어진 문자열
     */
    public static String addString(Object str0, Object... str) {

        StringBuilder buf = new StringBuilder(1000);
        buf.append(str0);

        if ( str != null && str.length > 0 ) {
            for ( int k = 0 ; k < str.length ; k++ ) {
                buf.append(str[k]);
            }
        }
        return buf.toString();
    }

    public static Object[] param(Object... str) {
        return str;
    }

    public static String sumInt(String x, String y) {
        int ix = StringUtil.parseInt(x);
        int iy = StringUtil.parseInt(y);
        return String.valueOf(ix + iy);
    }

    /**
     * 대상 Object가 널이면 기본 빈물자열이 리턴이 되며 널이 아닐경우 입력 파라미터를 ToString() 처리 한다.
     * 
     * @param object
     *            : ToString 대상 Object
     * @return parameter objecct.ToString()
     */
    public static String nvl(Object object) {
        return object != null ? object.toString() : "";
    }

    /**
     * 문자열이 null 일때 임의의 문자열을 반환한다.
     * 
     * @param String
     *            value, String defaultValue
     * @return String
     * @throws
     */
    public static String nvl(String value, String defaultValue) {
        return value == null || "".equals(value) ? defaultValue : value.trim();
    }

    public static String nvl(Object o, String defaultValue) {
        return ( o == null ) ? defaultValue : o.toString().trim();
    }

    /**
     * 0 ~ 9,A~F까지 범위 내에서 Random 하게 아스키값을 생성한다.
     */
    public static String getGenKeyNo(int index) throws Exception {
        int count = 0;
        String tmp;

        byte[] randomByte = new byte[index];
        Random rr = new Random();
        for ( int i = 0 ; i < index ; i++ ) {
            count = 48 + rr.nextInt(index);

            if ( 57 < count && count < 65 )
                count += 7;
            randomByte[i] = (byte) count;
        }

        tmp = new String(randomByte);
        return tmp.substring(0, 4) + "-" + tmp.substring(4, 8) + "-" + tmp.substring(8, 12) + "-"
                + tmp.substring(12);
    }

    public static void main(String[] args) {
        String str = "1xv#$%J$^%23K&%^&_+4zx5678sdv2fv90";

        System.out.println("data1 : " + StringUtil.extractNumber(str));

    }
    
    /**
	 * 문자 배열 
	 * @param sData
	 * @param size
	 * @return
	 */
	public static String [] splitData(String sData, String SeParator){
		String [] result = null;
		try{
			ArrayList<String> arrayList = new ArrayList<String>();
			int nCount = 1;
			int idx=0;
			int iPosStart = 0;
			int iPosEnd   = -1;
			// 
			for(idx=0; idx<sData.length(); idx++){
				if( sData.substring(idx, idx+1).equals(SeParator)){
					iPosEnd = idx;
					if(iPosStart<=iPosEnd){
						arrayList.add(sData.substring(iPosStart, iPosEnd));
					}
					iPosStart = idx+1;
				}
			}
			if(iPosStart<idx){
				if(iPosStart<sData.length()){
					arrayList.add(sData.substring(iPosStart, sData.length()));
				}
			}
			result = new String[arrayList.size()];
			for(idx=0; idx<arrayList.size(); idx++){
				result[idx] = arrayList.get(idx);
			}
		}catch(Exception ex){
			//logger.error(ex.getMessage(), ex);
		}
		
		return result;
	}
    // Null 값을 check한다.
    public static String checkNull(String str) {
        if(str == null) {
            str = "";
        } else {
            str = str.trim();
        }
        return str;
    }
    // 파일을 변환한다
    public static String convertKorToUTF(String str) {
    	try {
			return new String(str.getBytes("8859_1"), "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			//logger.error(ex.getMessage(), ex);
		}
    	return "";
    }
    // 파일을 변환한다
    public static String convertUTFToKor(String str) {
    	try {
			return new String(str.getBytes("UTF-8"), "8859_1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "";
    }
    
    public static String replaceString(String str) throws Exception {

        String invalidString ="|\\|:|\"|\\*|\\?|<|>|#";
        		//"|\\|:|\"|\\*|\\?|<|>|#";

        Pattern pattern = Pattern.compile("\\?|<|>|#|:|\\*|\\s");
        
        Matcher m = pattern.matcher(str);
        
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        
        
        str = sb.toString().trim();
		return str;

    }
    
    public static String getNumber(String str) {
    	String patternStr = "\\d"; //숫자를 패턴으로 지정
        Pattern pattern = Pattern.compile(patternStr); 
        Matcher matcher = pattern.matcher(str); 
        String numeral = "";
        while(matcher.find()) { 
        	numeral += matcher.group(0); //지정된 패턴과 매칭되면 numeral 변수에 넣는다. 여기서는 숫자!!
        }
        System.out.println("numeral =" + numeral);
 		return numeral; 
    }
    
    public static String getHanEngNumber(String str) {
    	String patternStr = "[0-9a-zA-Z가-힣]"; //숫자를 패턴으로 지정
        Pattern pattern = Pattern.compile(patternStr); 
        Matcher matcher = pattern.matcher(str); 
        String numeral = "";
        while(matcher.find()) { 
        	numeral += matcher.group(0); //지정된 패턴과 매칭되면 numeral 변수에 넣는다. 여기서는 숫자!!
        }
        System.out.println("numeral =" + numeral);
 		return numeral; 
    }

    
    public static String maskWord(String word) {
        StringBuffer buff = new StringBuffer();
        char[] ch = word.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (i < 1) {
                buff.append(ch[i]);
            } else {
                buff.append("*");
            }
        }
        return buff.toString();
    }
    
    public static String getSTRFilter(String str){ 
		int str_length = str.length(); 
		String strlistchar   = ""; 
		String str_imsi   = "";  
		String [] filter_word = {"","\\.","\\?","\\/","\\~","\\!","\\@","\\#","\\$","\\%","\\^","\\&","\\*","\\(","\\)","\\_","\\+","\\=","\\|","\\\\","\\}","\\]","\\{","\\[","\\\"","\\'","\\:","\\;","\\<","\\,","\\>","\\.","\\?","\\/"}; 
		for(int i=0;i<filter_word.length;i++){ 
		  //while(str.indexOf(filter_word[i]) >= 0){ 
			str_imsi = str.replaceAll(filter_word[i],""); 
		    str = str_imsi; 
		  //} 
		} 
		return str; 
	} 

}
