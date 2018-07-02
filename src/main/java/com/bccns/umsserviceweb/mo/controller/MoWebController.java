package com.bccns.umsserviceweb.mo.controller;


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
@RequestMapping(value = "/mo")
public class MoWebController extends DefaultAPIController {
	private static final Logger log = LoggerFactory.getLogger(MoWebController.class);
	

	@Autowired
	NoticeService noticeService;
	
	@Value("${property.pagination.recordcountperpage}")
    private int recordCountPerPage;
    
    @Value("${property.pagination.pagesize}")
    private int pageSize;
    
    @RequestMapping(value = "/moList")
    public String getMoList(
            @ModelAttribute("searchNoticeVO") SearchNoticeVO searchNoticeVO
            , ModelMap model, HttpServletRequest request) {
        
        // 한 페이지에 게시되는 게시물 건수
    	searchNoticeVO.setRecordCountPerPage(this.recordCountPerPage);
        // 페이징 리스트의 사이즈
    	searchNoticeVO.setPageSize(this.pageSize);
        
        model.put("firstIndex",         searchNoticeVO.getFirstRecordIndex());
        model.put("lastRecordIndex",    searchNoticeVO.getLastRecordIndex());
        model.put("recordCountPerPage", searchNoticeVO.getRecordCountPerPage());
        
        //배포시작 및 종료일자를 DB조회를 위해 -를 제거
//        String releaseStartDate = "";
//        if(searchNoticeVO.getRelease_start_date() != null && searchNoticeVO.getRelease_start_date() != "") {
//            releaseStartDate = searchNoticeVO.getRelease_start_date().replaceAll("-", "");
//            searchNoticeVO.setRelease_start_date(releaseStartDate);
//        }
//        String releaseEndDate = "";
//        if(searchNoticeVO.getRelease_end_date() != null && searchNoticeVO.getRelease_end_date() != "") {
//            releaseEndDate = searchNoticeVO.getRelease_end_date().replaceAll("-", "");
//            searchNoticeVO.setRelease_end_date(releaseEndDate);
//        }
        
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
        
        return "/notice/noticeList";
    }    
    
    @RequestMapping(value = "/moDetail", method = RequestMethod.GET)
    public String getMoDetail(
            @ModelAttribute("searchNoticeVO") SearchNoticeVO searchNoticeVO
            , ModelMap model, String noticeNo) {
        
        searchNoticeVO.setNoticeNo(noticeNo);
        NoticeVO noticeVO = null;
        try{
        	noticeVO = noticeService.getNoticeDetail(searchNoticeVO);
        }catch(Exception e){
            log.debug("getNoticeDetail failure = "+e.getMessage());
        }
        
        model.put("noticeVO", noticeVO);
        model.put("searchNoticeVO", searchNoticeVO);
        
        return "/notice/noticeDetail";
    }
    
    
    
}
