package com.bccns.umsserviceweb.common.vo;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;



/**
 * HashMap을 사용하기 편하도록 만든 Param 클래스. <br>
 */
@SuppressWarnings("unchecked")
public class Param extends HashMap {
	private static final long serialVersionUID = 1L;

	private boolean isEncoded = false;
	
	public Param() {
		super();
	}

	public Param(Object o) {
		this((HashMap) o);
	}

	/**
	 * HashtMap객체를 인자로 받아 Param에 담는 생성자.
	 * 
	 * @param row
	 *            Hasgtable객체.
	 */
	public Param(HashMap map) {
		super();

		if (map != null) this.putAll(map);
	}

	/**
	 * Map 객체를 인자로 받아 Param에 담는 생성자
	 * @param map
	 */
	public Param(Map map) {
		super();
		if (map != null) {
		    this.putAll(map);
		}
	}

	
	/**
	 * 인자로 나열된 key-value 쌍을 Param에 담는 생성자
	 * 
	 * 홀수번째 인자는 키, 짝수번째 인자는 값
	 * 
	 * @param units
	 */
	public Param(Object... units) {
		setArgs(units);
	}
	

	/**
	 * 맵정보를 담은 Param instance를 반환한다.
	 * 
	 * 생성자가 있는데도 getInstance가 쓰이는 용도는, map이 null일때 Param 자체도 null을 반환시키기 위해서다.
	 * 
	 * @param map
	 * @return
	 */
	public static Param getInstance(Object map) {
		return map == null ? null : new Param(map);
	}

	public String getQueryString(String name) {
		String query = "";
		if (query == null) return null;

		if (query.startsWith(name + "=")) {
			int pos = query.indexOf("&");

			if (pos > -1) return query.substring(name.length() + 1, pos);
			else return query.substring(name.length() + 1);
		}
		else {
			int pos1 = query.indexOf("&" + name + "=");

			if (pos1 > -1) {
				int pos2 = query.indexOf("&", pos1 + 1);

				if (pos2 > -1) return query.substring(pos1 + name.length() + 2,
						pos2);
				else return query.substring(pos1 + name.length() + 2);
			}
		}
		return null;
	}

	public int getQueryStringCount(String name) {
		String query = "";
		if (query == null) return 0;

		int cnt = 0;
		int last = 0;
		while (true) {
			int start = query.indexOf(name + "=", last);
			if (start > -1) {
				cnt++;
				last = start + name.length() + 1;
			}
			else break;
		}
		return cnt;
	}

	/**
	 * Param객체를 복사하여 다른 객체를 생성한다. <br>
	 * 
	 * Call by Reference가 아닌 Call by Value의 용도로 사용할때 쓴다.
	 * 
	 * @return 복사된 Param객체.
	 * @see java.util.HashMap
	 */
	public Param copy() {
		return (Param) super.clone();
	}
	
	/**
	 * Param에 key-value 값을 추가/갱신 한다.
	 * 
	 * Param은 HashMap의 put이란 명칭을 쓰지 않고, set이란 이름으로 쓴다.
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value) {
		super.put(key, value);
	}

	/**
	 * 인자로 나열된 key-value 쌍을 Param에 추가한다.
	 * 
	 * 홀수번째 인자는 키, 짝수번째 인자는 값
	 * 
	 * @param units
	 */
	public void setArgs(Object... units) {
		for (int i=0; i<units.length; i+= 2) {
			super.put((String)units[i], (i+1 < units.length) ? units[i + 1] : null);
		}
	}
	
	/**
	 * Param객체로 부터 key에 해당하는 value를 int형으로 가지고 온다.
	 * 
	 * @param key
	 * @return value (key에 해당하는 값이 없으면 디폴트 0).
	 */
	public int getInt(String key) {
		return getInt(key, 0);
	}

	/**
	 * Param객체로 부터 key에 해당하는 value를 int형으로 가지고 온다. key에 해당하는 값이 없으면 2번째 int형 인자가
	 * 리턴된다.
	 * 
	 * @param  key
	 * @param defaultValue key에 해당하는 값이 없거나 value가 null일 경우 리턴해야 할 값.
	 * @return value
	 */
	public int getInt(String key, int defaultValue) {
		return _getInt(key, defaultValue, false);
	}
	
	private int _getInt(String key, int defaultValue, boolean upper) {
		Object o = super.get(key);
		
		if (o == null) {
			if (upper) {
				return defaultValue;
			}
			else {
				return  _getInt(key.toUpperCase(), defaultValue, true);
			}
		}
		else {
			if (o instanceof Integer) {
				return (Integer)o;
			}
			else if (o instanceof Long) {
				return ((Integer)o).intValue();
			}
			else if (o instanceof Float) {
				return ((Float)o).intValue();
			}
			else if (o instanceof Double) {
				return ((Double)o).intValue();
			}
			else {
				if (o.equals("")) {
					return defaultValue;
				}
				else {
					return (int)Double.parseDouble(o.toString());
				}
			}
		}
	}

	/**
	 * Param객체로 부터 key에 해당하는 value를 long형으로 가지고 온다.
	 * 
	 * @param  key
	 * @return value(key에 해당하는 값이 없으면 디폴트 0).
	 */
	public long getLong(String column) {
		return getLong(column, 0);
	}

	/**
	 * Param객체로 부터 key에 해당하는 value를 long형으로 가지고 온다. key에 해당하는 값이 없으면 2번째 long형
	 * 인자가 리턴된다.
	 * 
	 * @param key
	 * @param defaultValue key에 해당하는 값이 없거나 value가 null일 경우 리턴해야 할 값.
	 * @return value
	 */
	public long getLong(String key, long defaultValue) {
		return _getLong(key, defaultValue, false);
	}
	
	private long _getLong(String key, long defaultValue, boolean upper) {
		Object o = super.get(key);
		
		if (o == null) {
			if (upper) {
				return defaultValue;
			}
			else {
				return  _getLong(key.toUpperCase(), defaultValue, true);
			}
		}
		else {
			if (o instanceof Integer) {
				return ((Integer)o).longValue();
			}
			else if (o instanceof Long) {
				return (Long)o;
			}
			else if (o instanceof Float) {
				return ((Float)o).longValue();
			}
			else if (o instanceof Double) {
				return ((Double)o).longValue();
			}
			else {
				if (o.equals("")) {
					return defaultValue;
				}
				else {
					return (long)Double.parseDouble(o.toString());
				}
			}
		}
	}

	/**
	 * Param객체로 부터 key에 해당하는 value를 float형으로 가지고 온다.
	 * 
	 * @param  key
	 * @return value (key에 해당하는 값이 없으면 디폴트 0).
	 */
	public float getFloat(String key) {
		return getFloat(key, 0);
	}

	/**
	 * Param객체로 부터 key에 해당하는 value를 float형으로 가지고 온다. key에 해당하는 값이 없으면 2번째 float형
	 * 인자가 리턴된다.
	 * 
	 * @param key
	 * @param defaultValue key에 해당하는 값이 없거나 value가 null일 경우 리턴해야 할 값.
	 * @return value
	 */
	public float getFloat(String key, float defaultValue) {
		return _getFloat(key, defaultValue, false);
	}
	
	private float _getFloat(String key, float defaultValue, boolean upper) {
		Object o = super.get(key);
		
		if (o == null) {
			if (upper) {
				return defaultValue;
			}
			else {
				return  _getFloat(key.toUpperCase(), defaultValue, true);
			}
		}
		else {
			if (o instanceof Integer) {
				return ((Integer)o).floatValue();
			}
			else if (o instanceof Long) {
				return ((Long)o).floatValue();
			}
			else if (o instanceof Float) {
				return (Float)o;
			}
			else if (o instanceof Double) {
				return ((Double)o).floatValue();
			}
			else {
				if (o.equals("")) {
					return defaultValue;
				}
				else {
					return (float)Double.parseDouble(o.toString());
				}
			}
		}
	}

	/**
	 * Param객체로 부터 key에 해당하는 value를 double형으로 가지고 온다.
	 * 
	 * @param  key
	 * @return value (key에 해당하는 값이 없으면 디폴트 0).
	 */
	public double getDouble(String key) {
		return getDouble(key, 0);
	}

	/**
	 * Param객체로 부터 key에 해당하는 value를 double형으로 가지고 온다. key에 해당하는 값이 없으면 2번째
	 * double형 인자가 리턴된다.
	 * 
	 * @param key
	 * @param defaultValue key에 해당하는 값이 없거나 value가 null일 경우 리턴해야 할 값.
	 * @return value
	 */
	public double getDouble(String key, double defaultValue) {
		return _getDouble(key, defaultValue, false);
	}
	
	private double _getDouble(String key, double defaultValue, boolean upper) {
		Object o = super.get(key);
		
		if (o == null) {
			if (upper) {
				return defaultValue;
			}
			else {
				return  _getDouble(key.toUpperCase(), defaultValue, true);
			}
		}
		else {
			if (o instanceof Integer) {
				return ((Integer)o).doubleValue();
			}
			else if (o instanceof Long) {
				return ((Long)o).doubleValue();
			}
			else if (o instanceof Float) {
				return ((Float)o).doubleValue();
			}
			else if (o instanceof Double) {
				return (Double)o;
			}
			else {
				if (o.equals("")) {
					return defaultValue;
				}
				else {
					return Double.parseDouble(o.toString());
				}
			}
		}
	}

	/**
	 * Param객체로 부터 key에 해당하는 value를 String형으로 가지고 온다.
	 * 
	 * @param key
	 * @return value (key에 해당하는 값이 없으면 공백을 리턴).
	 */
	public String getString(String column) {
		return this.get(column, "");
	}

	/**
	 * Param객체로 부터 key에 해당하는 value를 String형으로 가지고 온다.
	 * 
	 * @param key
	 * @param defaultValue key가 Param내에 없거나 key에 해당하는 value가 null일 때 반환될 값.
	 * @return value
	 */
	public String getString(String column, String defaultValue) {
		return this.get(column, defaultValue);
	}


	/**
	 * Param객체로 부터 key에 해당하는 value를 String형으로 가지고 온다.
	 * 
	 * @param key
	 * @return value (key에 해당하는 값이 없으면 공백을 리턴).
	 */
	public String get(String key) {
		return this.get(key, "");
	}
	
	
	public byte[] getBts(String key) {
		return (byte[])super.get(key);
	}
	
	/**
	 * Param객체로 부터 key에 해당하는 value를 String형으로 가지고 온다.
	 * 
	 * @param key
	 * @param defaultValue key가 Param내에 없거나 key에 해당하는 value가 null일 때 반환될 값.
	 * @return value
	 */
	public String get(String key, String defaultValue) {
		return _get(key, defaultValue, false);
	}
	
	private String _get(String key, String defaultValue, boolean upper) {		
		Object o = super.get(key);
		
		if (o == null) {
			if (upper) {
				return defaultValue;
			}
			else {
				return  _get(key.toUpperCase(), defaultValue, true);
			}
		}
		else {
			if (o instanceof String) {
				return o.equals("") ? defaultValue : (String)o;
			}
			else if(o instanceof Integer) {
				return o.toString();
			}
			else if(o instanceof Long) {
				return o.toString();
			}
			else if(o instanceof Float) {
				return o.toString();
			}
			else if(o instanceof Double) {
				return o.toString();
			}
			else if (o instanceof BigDecimal) {
				return ((BigDecimal)o).toString();
			} 
			else if(o instanceof Date) {
				return DateFormatUtils.format((Date)o, "yyyy-MM-dd");
			}
			else {
				return (String)o;
			}
		}
	}

	

	/**
	 * Param객체에서 해당 key & value를 삭제한다.
	 * 
	 * @param key.
	 */
	public void remove(String key) {
		super.remove(key);
	}

	/**
	 * Param객체내의 data를 key1&value1=key2&data2=.....의 형태로 반환한다.
	 * 
	 * @return Param객체내의 data를 key1&value1=key2&data2=.....의 형태로 반환.
	 */
	public String toQueryString() {
		StringBuffer sb = new StringBuffer();

		Set set = this.entrySet();
		Iterator keys = set.iterator();

		boolean isFirst = true;
		while (keys.hasNext()) {
			String sKey = keys.next().toString();
			if (!isFirst) sb.append("&");
			sb.append(sKey);
			isFirst = false;
		}

		return sb.toString();
	}


	/* (non-Javadoc)
	 * @see java.util.AbstractMap#toString()
	 */
	public String toString() {
		String s = "";
		Set set = this.entrySet();
		for (Iterator it = set.iterator(); it.hasNext();) {
			String key = it.next().toString();

			s += "[" + key + "]";
		}

		return s;
	}

	private static String nvl(Object v) {

		if (v == null) return "";
		else return String.valueOf(v);
	}

}