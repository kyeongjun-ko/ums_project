package com.bccns.umsserviceweb.common.util;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;
 
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.bccns.umsserviceweb.common.vo.UmsUserVO;

/**
 * @version 1.0.0
 * 
 */
public class UmsServiceWebSession implements Serializable {
	
	private static final Logger logger = LoggerFactory.getLogger(UmsServiceWebSession.class);
	
	private static final long serialVersionUID = -2675264526842048291L;

	private final UmsUserVO user;

	private UmsServiceWebSession(UmsUserVO user) {
		super();
		this.user = user;
	}
 
	public static void make(HttpServletRequest request, UmsUserVO user) {
		HttpSession session = request.getSession(true);
		session.setMaxInactiveInterval(30 * 60 );

		session.setAttribute("SESSION_USER", new UmsServiceWebSession(
				user));
		session.setAttribute("sessionDate", DateUtils.getSysDate("yyyyMMddHHmmss"));
		session.setAttribute("sessionUId", user.getUserId());
		session.setAttribute("sessionULv", user.getUserLv());
		session.setAttribute("sessionCom", user.getCompany());


	}
 
	public static void makeDate(HttpServletRequest request, String date) {

		if (check(request)) {
			HttpSession session = request.getSession(false);
			session.setAttribute("sessionDate", date);
		}
	}
 
	public static boolean check(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session == null)
			return false;

		UmsServiceWebSession Session = (UmsServiceWebSession) session
				.getAttribute("SESSION_USER");

		if (Session == null)
			return false;
		if (Session.getUser() == null)
			return false;
		if (StringUtils.isEmpty(Session.getUser().getUserId()))
			return false;

		return true;
	}

	public static void reset(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session != null) {
			session.invalidate();
		}
	}
 
	public static UmsServiceWebSession get(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session == null)
			return null;

		UmsServiceWebSession Session = (UmsServiceWebSession) session
				.getAttribute("SESSION_USER");

		return Session;
	}
 
	public static String getDate(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session == null)
			return null;

		String sessionDate = (String) session.getAttribute("sessionDate");

		return sessionDate;
	}

	public UmsUserVO getUser() {
		return user;
	}
	
	/**
     * session userid check
     */
	public static String getSessionUserId(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session == null)
			return null;
		
		UmsServiceWebSession Session = (UmsServiceWebSession) session
				.getAttribute("SESSION_USER");
		
    	UmsUserVO umsUserVO = null;
    	String userId = null;
    	
        try {
        	//umsUserVO = (UmsUserVO) session.getAttribute("SESSION_USER");      
        	//userId = (String) RequestContextHolder.currentRequestAttributes().getAttribute("SESSION_USER", RequestAttributes.SCOPE_SESSION);
            
        	userId = Session.getUser().getUserId();
        	logger.debug("getuserId = " + userId);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        
        return userId;
    }
	/**
     * session UmsUserVO 
     */
	public static UmsUserVO getSessionUserVO2(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session == null)
			return null;
		
		UmsServiceWebSession Session = (UmsServiceWebSession) session
				.getAttribute("SESSION_USER");
		
    	UmsUserVO umsUserVO = null;
    	String userId = null;
    	
        try {
        	umsUserVO = (UmsUserVO) RequestContextHolder.currentRequestAttributes().getAttribute("SESSION_USER", RequestAttributes.SCOPE_SESSION);
            
        	//userId = Session.getUser().getUserId();
        	logger.debug("getuserId = " + userId);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        
        return umsUserVO;
    }
	/**
     * session umsUserVO get
     */
	public static UmsUserVO getSessionUserVO(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session == null)
			return null;
		
		UmsServiceWebSession Session = (UmsServiceWebSession) session
				.getAttribute("SESSION_USER");
		
    	UmsUserVO umsUserVO = null;
    	
        try {
            
        	umsUserVO = (UmsUserVO) Session.getUser();
        	logger.debug("umsUserVO = " + umsUserVO.toString());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        
        return umsUserVO;
    }
}
