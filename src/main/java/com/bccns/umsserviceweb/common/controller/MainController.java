package com.bccns.umsserviceweb.common.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bccns.umsserviceweb.common.service.CommonService;
import com.bccns.umsserviceweb.common.util.UmsServiceWebSession;
import com.bccns.umsserviceweb.common.vo.UmsUserVO;
import com.bccns.umsserviceweb.common.vo.UsrGrpVO;
import com.bccns.umsserviceweb.notice.service.NoticeService;
import com.bccns.umsserviceweb.notice.vo.NoticeVO;

@Controller
public class MainController extends DefaultAPIController {
	

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	@Autowired
    CommonService commonService;
     
    @Value("${SSO.serviceID}")     String serviceID;
    @Value("${SSO.domain}")        String domain;
    @Value("${SSO.registURL}")     String registURL;
    
	@Autowired
	NoticeService noticeService; 
	
	/**
	 * 메인화면
	 * @param umsUserVO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/" , "/main"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String getMain(//@RequestParam("returnURL") String returnURL,
	        @ModelAttribute("umsUserVO") UmsUserVO umsUserVO,
	        Model model, HttpServletRequest request) {
        
        logger.debug("-------------start main");
        
        try {
//            String jsonStr = client.getData(Constants.OA_GET_DASHBOARD);
//            
//            ObjectMapper om = new ObjectMapper();
//            DashBoardResult result;
//        
//            result = om.readValue(jsonStr, DashBoardResult.class);
//            
//            // 결과 리스트 set
//            model.addAttribute("result", result.getBody());
//        	if(logger.isDebugEnabled()){
//        		logger.debug("returnURL : " + returnURL);
//    	    }
    	    
            model.addAttribute("serviceID",     serviceID);
            model.addAttribute("domain",        domain);
            model.addAttribute("registURL",     registURL);
            model.addAttribute("userId",        UmsServiceWebSession.getSessionUserId(request));
//            model.addAttribute("returnURL",     returnURL);
            
            List<NoticeVO> noticeList = noticeService.getNoticeListMain();
        	
        	logger.debug("noticeList = "+ noticeList.toString() );
            // 게시판 리스트
            model.addAttribute("noticeList", noticeList);
             
        }catch (Exception e ) {
            logger.error(e.getMessage());
        }
        
        logger.debug("----------------end main");
                

        return "/common/main";

    }
}
