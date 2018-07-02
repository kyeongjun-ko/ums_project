package com.bccns.umsserviceweb.notice.controller.web;


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
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bccns.umsserviceweb.common.controller.DefaultAPIController;
import com.bccns.umsserviceweb.notice.service.NoticeService;
import com.bccns.umsserviceweb.notice.vo.NoticeVO;
import com.bccns.umsserviceweb.notice.vo.SearchNoticeVO;

@Controller
@RequestMapping(value = "/srv")
public class NoticeWebController extends DefaultAPIController {
	private static final Logger log = LoggerFactory.getLogger(NoticeWebController.class);
	

	@Autowired
	NoticeService noticeService;
	
	@Value("${property.pagination.recordcountperpage}")
    private int recordCountPerPage;
    
    @Value("${property.pagination.pagesize}")
    private int pageSize;
    
    @RequestMapping(value = "/noticeList")
    public String getNoticeList(
            @ModelAttribute("searchNoticeVO") SearchNoticeVO searchNoticeVO
            , ModelMap model, HttpServletRequest request) {
        log.debug(searchNoticeVO.toString());
        // 한 페이지에 게시되는 게시물 건수
    	searchNoticeVO.setRecordCountPerPage(this.recordCountPerPage);
        // 페이징 리스트의 사이즈
    	searchNoticeVO.setPageSize(this.pageSize);
        
        model.put("firstIndex",         searchNoticeVO.getFirstRecordIndex());
        model.put("lastRecordIndex",    searchNoticeVO.getLastRecordIndex());
        model.put("recordCountPerPage", searchNoticeVO.getRecordCountPerPage());
        
        int totCount = 0;
        try{
            totCount = noticeService.getNoticeListCount(searchNoticeVO);
        }catch(Exception e){
            log.debug("getNoticeListCount failure = "+e.getMessage());
        }
        
        // 전체 게시물 건 수
        searchNoticeVO.setTotalRecordCount(totCount);

        List<NoticeVO> noticeList = null;
        try{
        	noticeList = noticeService.getNoticeList(searchNoticeVO);
        }catch(Exception e){
            log.debug("getNoticeList failure = "+e.getMessage());
        }
          
        model.put("noticeList", noticeList);
        model.put("searchNoticeVO", searchNoticeVO);
        log.debug(searchNoticeVO.toString());
        return "/notice/noticeList";
    }    
    
    @RequestMapping(value = "/noticeDetailPopup", method = RequestMethod.GET)
    public String getNoticeDetail(
            @ModelAttribute("searchNoticeVO") SearchNoticeVO searchNoticeVO
            , ModelMap model, String noticeNo) {
        
    	log.debug("searchNoticeVO=== "+searchNoticeVO.toString());
    	
        //searchNoticeVO.setNoticeNo(noticeNo);
        NoticeVO noticeVO = null;
        try{
        	noticeVO = noticeService.getNoticeDetail(searchNoticeVO);
        }catch(Exception e){
            log.debug("getNoticeDetail failure = "+e.getMessage());
        }
        
        model.put("noticeVO", noticeVO);
        model.put("searchNoticeVO", searchNoticeVO);
        log.debug("noticeVO=== "+noticeVO.toString());
        return "/notice/noticeDetailPop";
    }
    
    @RequestMapping(value = "/noticeDetailPopup2", method = RequestMethod.GET)
    public String getNoticeDetail2(
            @ModelAttribute("searchNoticeVO") SearchNoticeVO searchNoticeVO
            , ModelMap model, String noticeNo) {
        
    	log.debug("searchNoticeVO=== "+searchNoticeVO.toString());
    	
        //searchNoticeVO.setNoticeNo(noticeNo);
        NoticeVO noticeVO = null;
        try{
        	noticeVO = noticeService.getNoticeDetail(searchNoticeVO);
        }catch(Exception e){
            log.debug("getNoticeDetail failure = "+e.getMessage());
        }
        
        model.put("noticeVO", noticeVO);
        model.put("searchNoticeVO", searchNoticeVO);
        log.debug("noticeVO=== "+noticeVO.toString());
        return "/notice/noticeDetailPop2";
    }
    
    @RequestMapping(value = "/noticeWritePopUp", method = RequestMethod.GET)
    public String createNotice(
            @ModelAttribute("noticeVO") NoticeVO noticeVO,
            BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs, ModelMap model, HttpServletResponse response) {
        
        String mav = "/notice/noticeWrite";
                
        String msg = "success!";
                                        
//        status.setComplete();
//        
//        String orderID = noticeVO.getOrderID();
//        String serviceID = noticeVO.getServiceID();
//        log.debug("noticeWritePopUp ================= orderID = " + orderID + ", serviceID = " + serviceID );
//        
//        if(orderID == null || "".equals(orderID)) {
//            response.setContentType("text/html;charset=euc-kr");
//            PrintWriter out = null;
//            try {
//                out = response.getWriter();
//                out.println("<script>");
//                out.println("alert('Integration ID does not exist. Can not be registered.');");
//                out.println("self.close();");
//                out.println("</script>");
//                out.flush();
//                out.close();
//            }
//            catch ( IOException e ) {
//                log.debug("Notice registration failure = "+e.getMessage());
//            }
//        }else{
//            noticeVO.setOrderID(orderID);
//        }
//        
//        noticeVO.setServiceID(serviceID);
//        
//        Map<String, Object> forwardMap = UtilStatic.getMap(noticeVO);
//        redirectAttrs.addAllAttributes(forwardMap);
//        redirectAttrs.addAttribute("currentPageNo", noticeVO.getCurrentPageNo());
//        redirectAttrs.addFlashAttribute("msg", msg);
//        
//        model.put("noticeVO", noticeVO);
        
        return mav;
    }
    
    @RequestMapping(value = "/noticeWriteProcess", method = RequestMethod.POST)
    public String createNoticeProcess(
            @ModelAttribute("noticeVO") NoticeVO noticeVO
            , BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        
//        HttpSession session = request.getSession(false);
//        String sessionGUID = (String)session.getAttribute("sessionGUID");
//        noticeVO.setCreateUser(sessionGUID);
//        
//        String sessionEmailId = (String)session.getAttribute("sessionEmailId");
//        log.debug("sessionEmailId ================= "+sessionEmailId);
//        
        String mav = "/notice/noticeWrite";
//        modelPrint(noticeVO);
//                
//        status.setComplete();
//        
//        Integer res = 0;
//        try{
//            res = noticeService.createNotice(noticeVO);
//            
//            if(res == 0){
//                response.setContentType("text/html;charset=euc-kr");
//                PrintWriter out = response.getWriter();
//                out.println("<script>");
//                out.println("alert('Registration Fail.');");
//                out.println("self.close();");
//                out.println("</script>");
//                out.flush();
//                out.close();
//            }else if(res == 1){
//                response.setContentType("text/html;charset=euc-kr");
//                PrintWriter out = response.getWriter();
//                out.println("<script>");
//                out.println("alert('Registration Success.');");
//                out.println("self.close();");
//                out.println("</script>");
//                out.flush();
//                out.close();
//            }else if(res == 2){
//                response.setContentType("text/html;charset=euc-kr");
//                PrintWriter out = response.getWriter();
//                out.println("<script>");
//                out.println("alert('Content for orderID information that does not exist.');");
//                out.println("self.close();");
//                out.println("</script>");
//                out.flush();
//                out.close();
//            }else if(res == 3){
//                response.setContentType("text/html;charset=euc-kr");
//                PrintWriter out = response.getWriter();
//                out.println("<script>");
//                out.println("alert('Content for country information that does not exist.');");
//                out.println("self.close();");
//                out.println("</script>");
//                out.flush();
//                out.close();
//            }
//        }catch(Exception e){
//            log.debug("Notice registration failure = "+e.getMessage());
//        }
//        
//        Map<String, Object> forwardMap = UtilStatic.getMap(noticeVO);
//        redirectAttrs.addAllAttributes(forwardMap);
//        redirectAttrs.addAttribute("currentPageNo", noticeVO.getCurrentPageNo());
//        
        return mav;
    }
    
    @RequestMapping(value = "/noticeUpdatePopup", method = RequestMethod.POST)
    public String updateNotice(
            @ModelAttribute("searchNoticeVO") SearchNoticeVO searchNoticeVO
            , BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        
//        HttpSession session = request.getSession(false);
//        String sessionGUID = (String)session.getAttribute("sessionGUID");
//        searchNoticeVO.setUpdateUser(sessionGUID);
//        
//        String sessionEmailId = (String)session.getAttribute("sessionEmailId");
//        log.debug("sessionEmailId ================= "+sessionEmailId);
//        
//        modelPrint(searchNoticeVO);
//                
        String msg = "success!";
        String mav = "";
                                        
        status.setComplete();
        
        Integer res = 0;
        try{
            res = noticeService.modifyNotice(searchNoticeVO);
            log.debug("res ================== "+res);
            if(res != 0){
                msg = "Change Comlete";
            }
        }catch(Exception e){
            log.debug("updateNotice Fail = "+e.getMessage());
            msg = "Change Fail";
        }
        
//        if("1".equals(searchNoticeVO.getDetailView())){
//            mav = "redirect:/notice/noticeDetail";
//        }else{
//            mav = "redirect:/notice/noticeList";
//        }
//        
//        Map<String, Object> forwardMap = UtilStatic.getMap(searchNoticeVO);
//        redirectAttrs.addAllAttributes(forwardMap);
//        redirectAttrs.addFlashAttribute("msg", msg);
//        redirectAttrs.addAttribute("currentPageNo", searchNoticeVO.getCurrentPageNo());
//        
//        //model.put("searchNoticeVO", searchNoticeVO);
        
        return mav;
    }
    
    @RequestMapping(value = "/noticeDetailUpdate", method = RequestMethod.POST)
    public @ResponseBody String updateDetailNotice(@ModelAttribute("suspArID") String suspArID, HttpServletRequest request) {
        
//        XSSFilter xssFilter = new XSSFilter();
//        
//        HttpSession session = request.getSession(false);
//        String sessionGUID = (String)session.getAttribute("sessionGUID");
//        
//        modelPrint(suspArID);
//        
//        SearchNoticeVO searchNoticeVO = new SearchNoticeVO();
//        
//        try{
//            noticeService.updateDetailNotice(suspArID, sessionGUID);
//        }catch(Exception e){
//            log.debug("updateDetailNotice Fail = "+e.getMessage());
//        }
//        
//        searchNoticeVO.setNoticeID(suspArID);
//        NoticeVO susVO = noticeService.getNoticeDetail(searchNoticeVO);
//        
//        String procDate = xssFilter.filter(susVO.getProc_date());
//        
//        return procDate;
//        
        return null;
    }   
    
    @RequestMapping(value = "/removeNotice", method = RequestMethod.POST)
    public @ResponseBody String removeNotice(@ModelAttribute("suspArID") String suspArID, HttpServletRequest request) {
        
//        XSSFilter xssFilter = new XSSFilter();
//        
//        HttpSession session = request.getSession(false);
//        String sessionGUID = (String)session.getAttribute("sessionGUID");
//        
//        modelPrint(suspArID);
//        
//        SearchNoticeVO searchNoticeVO = new SearchNoticeVO();
//        
//        try{
//            noticeService.updateDetailNotice(suspArID, sessionGUID);
//        }catch(Exception e){
//            log.debug("updateDetailNotice Fail = "+e.getMessage());
//        }
//        
//        searchNoticeVO.setNoticeID(suspArID);
//        NoticeVO susVO = noticeService.getNoticeDetail(searchNoticeVO);
//        
//        String procDate = xssFilter.filter(susVO.getProc_date());
//        
//        return procDate;
//        
        return null;
    }   
    
}
