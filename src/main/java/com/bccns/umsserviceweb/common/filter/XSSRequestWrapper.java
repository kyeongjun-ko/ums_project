package com.bccns.umsserviceweb.common.filter;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
/**
 * XSS Request Wrapper
 * @version 1.0
 */
public final class XSSRequestWrapper extends HttpServletRequestWrapper {
	/**
	 * The Constructor
	 * 
	 * @param servletRequest HttpServletRequest
	 */
	public XSSRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		
		if (values == null) {
			return null;
		}
		
		int count = values.length;
		String[] encodedValues = new String[count];
		
		for (int i = 0; i < count; i++) {
			encodedValues[i] = convert(values[i]);
		}
		
		return encodedValues;
	}
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		
		if (value == null) {
			return null;
		}
		
		return convert(value);
	}
	public String getHeader(String name) {
		String value = super.getHeader(name);
		
		if (value == null) {
			return null;
		}
		
		return convert(value);
	}
	private String convert(String value) {
		value = value.replaceAll("&",	"&amp;");
		value = value.replaceAll("<",	"&lt;");
		value = value.replaceAll(">",	"&gt;");
		value = value.replaceAll("%00",	null);
		value = value.replaceAll("\"",	"&#34;");
		value = value.replaceAll("\'",	"&#39;");
		value = value.replaceAll("%",	"&#37;");
		value = value.replaceAll("'", 	"&#39;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		
		return value;
	}
}