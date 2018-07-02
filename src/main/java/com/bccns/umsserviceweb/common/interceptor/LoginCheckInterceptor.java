package com.bccns.umsserviceweb.common.interceptor;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bccns.umsserviceweb.common.util.UmsServiceWebSession;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
    
    private static final Logger log = LoggerFactory.getLogger(LoginCheckInterceptor.class);
    
    @Value("${login.excludeUrls}") String excludeUrls;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object hadler) throws Exception{
        
        //Http response splitting
        String reqUri = request.getRequestURI();
        if(!isValidUri(reqUri)){
            return false;
        }
        
        //예외주소처리
        for(String excludeUrl : excludeUrls.split(",")){
            if(reqUri.startsWith(excludeUrl) || reqUri.contentEquals("/")){
                return true;
            }
        }
        
        //SSO Return
        if("Y".equals(request.getParameter("isReturn"))){
            response.sendRedirect(request.getContextPath() + "/loginRegist?isReturn=Y");
            return false;
        }
        //세션검사
        HttpSession session = request.getSession(false);
        if(null == session){
            response.sendRedirect(request.getContextPath() + "/login?returnURL=" + reqUri);
            return false;
        }
        UmsServiceWebSession sessionUSER = (UmsServiceWebSession) session
				.getAttribute("SESSION_USER");
        //UmsUserVo sessionUSERc = (String)session.getAttribute("SESSION_USER");
        if(null == sessionUSER){
            response.sendRedirect(request.getContextPath() + "/login?returnURL=" + reqUri);
            return false;
        }
        
        return true;
    }
    
    /**
     * Http response splitting
     * Http response splitting
     *
     * @param uri
     * @return
     */
    public static boolean isValidUri(String uri) {
        List<String> filters = Arrays.asList("%0d", "%0a", "\\r", "\\n");
        
        for(String filter : filters){
            if(uri.toLowerCase().indexOf(filter) > -1){
                return false;
            }
        }
        return true;
    }
}