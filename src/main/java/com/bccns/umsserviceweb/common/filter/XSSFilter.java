package com.bccns.umsserviceweb.common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
/**
 * Cross-Site Script Filter
 */
public class XSSFilter implements Filter {
	@SuppressWarnings("unused")
	private FilterConfig filterConfig;
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }
    public void destroy() {
        this.filterConfig = null;
    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    	throws IOException, ServletException {
    	
    	String uri = ((HttpServletRequest) request).getRequestURI();
    	
    	if (uri.indexOf("/ums") != -1) {
    		chain.doFilter(request, response);
    	} else {
    		chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
    	}
    	
    }
}