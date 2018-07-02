package com.bccns.umsserviceweb.mgr.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bccns.umsserviceweb.common.controller.DefaultAPIController;
import com.bccns.umsserviceweb.common.service.CommonService;
import com.bccns.umsserviceweb.common.util.DateUtils;
import com.bccns.umsserviceweb.common.util.UmsServiceWebSession;
import com.bccns.umsserviceweb.common.vo.CommCodeVO;
import com.bccns.umsserviceweb.common.vo.JsonVO;
import com.bccns.umsserviceweb.common.vo.UmsUserVO;
import com.bccns.umsserviceweb.common.vo.UsrGrpVO;
import com.bccns.umsserviceweb.mgr.service.GrpService;
import com.bccns.umsserviceweb.mgr.service.MsgBoxService;
import com.bccns.umsserviceweb.mgr.service.TransHisService;
import com.bccns.umsserviceweb.mgr.vo.GrpVO;
import com.bccns.umsserviceweb.mgr.vo.MsgBoxVO;
import com.bccns.umsserviceweb.mgr.vo.ResvDetBottomVO;
import com.bccns.umsserviceweb.mgr.vo.ResvDetTopVO;
import com.bccns.umsserviceweb.mgr.vo.RsltDetBottomVO;
import com.bccns.umsserviceweb.mgr.vo.RsltDetMiddleVO;
import com.bccns.umsserviceweb.mgr.vo.RsltDetTopVO;
import com.bccns.umsserviceweb.mgr.vo.SearchAddrVO;
import com.bccns.umsserviceweb.mgr.vo.SearchMsgBoxVO;
import com.bccns.umsserviceweb.mgr.vo.SearchResvDetVO;
import com.bccns.umsserviceweb.mgr.vo.SearchRsltDetVO;
import com.bccns.umsserviceweb.mgr.vo.SearchTransHisVO;
import com.bccns.umsserviceweb.mgr.vo.SearchTransResvVO;
import com.bccns.umsserviceweb.mgr.vo.SearchTransRsltVO;
import com.bccns.umsserviceweb.mgr.vo.SearchTransStatVO;
import com.bccns.umsserviceweb.mgr.vo.TransHisVO;
import com.bccns.umsserviceweb.mgr.vo.TransResvVO;
import com.bccns.umsserviceweb.mgr.vo.TransRsltVO;
import com.bccns.umsserviceweb.mgr.vo.TransStatVO;
import com.bccns.umsserviceweb.notice.service.NoticeService;
import com.bccns.umsserviceweb.notice.vo.NoticeVO;
import com.bccns.umsserviceweb.notice.vo.SearchNoticeVO;

@Controller
@RequestMapping(value = "/trs")
public class TransMgrWebController extends DefaultAPIController{
	private static final Logger log = LoggerFactory.getLogger(TransMgrWebController.class);
	

	@Autowired
	TransHisService transHisService;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	NoticeService noticeService;
	
	@Autowired
	MsgBoxService msgBoxService;
	
	@Autowired
	GrpService grpService;
	
	@Value("${property.pagination.recordcountperpage}")
    private int recordCountPerPage;
    
    @Value("${property.pagination.pagesize}")
    private int pageSize;
    
    
    /**
     * 전송이력
     * @param searchTransHisVO
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/transHisList")
    public String getTransHisList(
            @ModelAttribute("searchTransHisVO") SearchTransHisVO searchTransHisVO
            , ModelMap model, HttpServletRequest request) {
        
    	String userId = UmsServiceWebSession.getSessionUserId(request); 
    	
    	UmsUserVO umsUserVO = new UmsUserVO();
    	
    	
    	umsUserVO.setUserId(userId);
	    umsUserVO = commonService.getUserInfoD(umsUserVO);
    	
	    searchTransHisVO.setCompany(umsUserVO.getCompany());
	    searchTransHisVO.setDept(umsUserVO.getDept());
	    
	    searchTransHisVO.setUserlv(umsUserVO.getUserLv());
	    
    	if(searchTransHisVO.getSuserId() == null || searchTransHisVO.getSuserId().length() == 0){
    		searchTransHisVO.setUserId(userId);
    	}else{
    		
    		if(searchTransHisVO.getSuserId().equals("ALL") && umsUserVO.getUserLv().equals("10")){
    			searchTransHisVO.setUserId(userId);
    		}else{
    			searchTransHisVO.setUserId(searchTransHisVO.getSuserId());
    		}
    	}
    	
    	
    	String regDateStartDate = "";
		if(searchTransHisVO.getSrchRegDateStart() != null && searchTransHisVO.getSrchRegDateStart() != "") {
			  regDateStartDate = searchTransHisVO.getSrchRegDateStart().replaceAll("/", "");
			  regDateStartDate = searchTransHisVO.getSrchRegDateStart().replaceAll("-", "");

			  searchTransHisVO.setSrchRegDateStart(regDateStartDate);
		}
		String regDateEndDate = "";
		if(searchTransHisVO.getSrchRegDateEnd() != null && searchTransHisVO.getSrchRegDateEnd() != "") {
			  regDateEndDate = searchTransHisVO.getSrchRegDateEnd().replaceAll("/", "");
			  regDateEndDate = searchTransHisVO.getSrchRegDateEnd().replaceAll("-", "");

			  searchTransHisVO.setSrchRegDateEnd(regDateEndDate);
		}
        // 한 페이지에 게시되는 게시물 건수
    	searchTransHisVO.setRecordCountPerPage(this.recordCountPerPage);
        // 페이징 리스트의 사이즈
    	searchTransHisVO.setPageSize(this.pageSize);
        
        model.put("firstIndex",         searchTransHisVO.getFirstRecordIndex());
        model.put("lastRecordIndex",    searchTransHisVO.getLastRecordIndex());
        model.put("recordCountPerPage", searchTransHisVO.getRecordCountPerPage());
//        log.debug(searchTransHisVO.toString());
//        int totCount = 0;
//        try{
//            totCount = transHisService.getTransHisListCount(searchTransHisVO);
//        }catch(Exception e){
//            log.debug("getTransHisListCount failure = "+e.getMessage());
//        }
//        
//        // 전체 게시물 건 수 
//        searchTransHisVO.setTotalRecordCount(totCount);
//
//        List<TransHisVO> transHisList = null;
//        try{
//        	transHisList = transHisService.getTransHisList(searchTransHisVO);
//        }catch(Exception e){
//            log.debug("getTransHisList failure = "+e.getMessage());
//        }
          
        List<CommCodeVO > userIdList = new ArrayList<CommCodeVO >();
		umsUserVO.setUserId(userId);
		try{
			userIdList = commonService.getUserIdCodeList(umsUserVO);
		}catch(Exception e){
			log.debug("userIdList failure = "+e.getMessage());
		}
		log.debug("userIdList = "+userIdList.toString());
		
//        model.put("transHisList", transHisList);
        model.put("userIdList",userIdList );

    	model.put("searchTransHisVO", searchTransHisVO);
        
        log.debug("searchTransHisVO = "+searchTransHisVO.toString());
        
        return "/mgr/transHisList";
    }  
    
    
    /**
     * 전송이력
     * @param searchTransHisVO
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/transHisListProc")
    public String getTransHisListProc(
            @ModelAttribute("searchTransHisVO") SearchTransHisVO searchTransHisVO
            , ModelMap model, HttpServletRequest request) {
        
    	String userId = UmsServiceWebSession.getSessionUserId(request); 
    	
    	UmsUserVO umsUserVO = new UmsUserVO();
    	
    	
    	umsUserVO.setUserId(userId);
	    umsUserVO = commonService.getUserInfoD(umsUserVO);
    	
	    searchTransHisVO.setCompany(umsUserVO.getCompany());
	    searchTransHisVO.setDept(umsUserVO.getDept());
	    
	    searchTransHisVO.setUserlv(umsUserVO.getUserLv());
	    
    	if(searchTransHisVO.getSuserId() == null || searchTransHisVO.getSuserId().length() == 0){
    		searchTransHisVO.setUserId(userId);
    	}else{
    		
    		if(searchTransHisVO.getSuserId().equals("ALL") && umsUserVO.getUserLv().equals("10")){
    			searchTransHisVO.setUserId(userId);
    		}else{
    			searchTransHisVO.setUserId(searchTransHisVO.getSuserId());
    		}
    	}
    	
    	
    	String regDateStartDate = "";
		if(searchTransHisVO.getSrchRegDateStart() != null && searchTransHisVO.getSrchRegDateStart() != "") {
			  regDateStartDate = searchTransHisVO.getSrchRegDateStart().replaceAll("/", "");
			  regDateStartDate = searchTransHisVO.getSrchRegDateStart().replaceAll("-", "");

			  searchTransHisVO.setSrchRegDateStart(regDateStartDate);
		}
		String regDateEndDate = "";
		if(searchTransHisVO.getSrchRegDateEnd() != null && searchTransHisVO.getSrchRegDateEnd() != "") {
			  regDateEndDate = searchTransHisVO.getSrchRegDateEnd().replaceAll("/", "");
			  regDateEndDate = searchTransHisVO.getSrchRegDateEnd().replaceAll("-", "");

			  searchTransHisVO.setSrchRegDateEnd(regDateEndDate);
		}
        // 한 페이지에 게시되는 게시물 건수
    	searchTransHisVO.setRecordCountPerPage(this.recordCountPerPage);
        // 페이징 리스트의 사이즈
    	searchTransHisVO.setPageSize(this.pageSize);
        
        model.put("firstIndex",         searchTransHisVO.getFirstRecordIndex());
        model.put("lastRecordIndex",    searchTransHisVO.getLastRecordIndex());
        model.put("recordCountPerPage", searchTransHisVO.getRecordCountPerPage());
        log.debug(searchTransHisVO.toString());
        int totCount = 0;
        try{
            totCount = transHisService.getTransHisListCount(searchTransHisVO);
        }catch(Exception e){
            log.debug("getTransHisListCount failure = "+e.getMessage());
        }
        
        // 전체 게시물 건 수 
        searchTransHisVO.setTotalRecordCount(totCount);

        List<TransHisVO> transHisList = null;
        try{
        	transHisList = transHisService.getTransHisList(searchTransHisVO);
        }catch(Exception e){
            log.debug("getTransHisList failure = "+e.getMessage());
        }
          
        List<CommCodeVO > userIdList = new ArrayList<CommCodeVO >();
		umsUserVO.setUserId(userId);
		try{
			userIdList = commonService.getUserIdCodeList(umsUserVO);
		}catch(Exception e){
			log.debug("userIdList failure = "+e.getMessage());
		}
		log.debug("userIdList = "+userIdList.toString());
		
        model.put("transHisList", transHisList);
        model.put("userIdList",userIdList );

    	model.put("searchTransHisVO", searchTransHisVO);
        
        log.debug("searchTransHisVO = "+searchTransHisVO.toString());
        
        return "/mgr/transHisList";
    } 
    
    /**
     * 사용내역
     * @param umsUserVO
     * @return
     */
    @RequestMapping(value = "/transUsrList")
    private List<CommCodeVO> getSearchUsrList(UmsUserVO umsUserVO) {
        
        List<CommCodeVO> searchUsrList = new ArrayList<CommCodeVO>();
        UmsUserVO userVO = null;
        try{
        	userVO = commonService.getUserInfoD(umsUserVO);
        }catch(Exception e){
            log.debug("getUserInfoD failure = "+e.getMessage());
        }
        searchUsrList.add(new CommCodeVO("callbackNo1", userVO.getCallbackNo1()));
        searchUsrList.add(new CommCodeVO("callbackNo2", userVO.getCallbackNo2()));
        searchUsrList.add(new CommCodeVO("callbackNo3", userVO.getCallbackNo3()));
        searchUsrList.add(new CommCodeVO("callbackNo4", userVO.getCallbackNo4()));
        log.debug("getCallbackNo1 = "+userVO.getCallbackNo1());
        log.debug("usrCallBackList = "+searchUsrList.toString());
        return searchUsrList;
    }
    
    /**
     * 전송통계
     * 
     * @param searchTransStatVO
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/transStatList")
    public String getTransStatList(
            @ModelAttribute("searchTransStatVO") SearchTransStatVO searchTransStatVO
            , ModelMap model, HttpServletRequest request) {
        log.debug(searchTransStatVO.toString());
        String userId = UmsServiceWebSession.getSessionUserId(request); 
    	
    	UmsUserVO umsUserVO = new UmsUserVO();
    	SearchTransStatVO searchTransStatVOView = new SearchTransStatVO();
    	searchTransStatVOView = searchTransStatVO;
    	
    	umsUserVO.setUserId(userId);
	    umsUserVO = commonService.getUserInfoD(umsUserVO);
    	
	    searchTransStatVO.setCompany(umsUserVO.getCompany());
	    searchTransStatVO.setDept(umsUserVO.getDept());
	    searchTransStatVO.setUserlv(umsUserVO.getUserLv());
	    
	    
	    if(searchTransStatVO.getSuserId() == null || searchTransStatVO.getSuserId().length() == 0){
	    	searchTransStatVO.setUserId(userId);
    	}else{
    		if(searchTransStatVO.getSuserId().equals("ALL") && umsUserVO.getUserLv().equals("10")){
    			searchTransStatVO.setUserId(userId);
    		}else{
    			searchTransStatVO.setUserId(searchTransStatVO.getSuserId());
    		}
    	}
    	
        // 한 페이지에 게시되는 게시물 건수
    	searchTransStatVO.setRecordCountPerPage(this.recordCountPerPage);
        // 페이징 리스트의 사이즈
    	searchTransStatVO.setPageSize(this.pageSize);

    	if(searchTransStatVO.getUserId() == null)
    		searchTransStatVO.setUserId(userId);
		
    	if(searchTransStatVO.getStatType() == null)
    		searchTransStatVO.setStatType("01");
    	
        model.put("firstIndex",         searchTransStatVO.getFirstRecordIndex());
        model.put("lastRecordIndex",    searchTransStatVO.getLastRecordIndex());
        model.put("recordCountPerPage", searchTransStatVO.getRecordCountPerPage());
        
        String regDateStartDate = "";
		if(searchTransStatVO.getSrchRegDateStart() != null && searchTransStatVO.getSrchRegDateStart() != "") {
			  regDateStartDate = searchTransStatVO.getSrchRegDateStart().replaceAll("/", "");
			  regDateStartDate = searchTransStatVO.getSrchRegDateStart().replaceAll("-", "");

			  searchTransStatVO.setSrchRegDateStart(regDateStartDate);
		}
		String regDateEndDate = "";
		if(searchTransStatVO.getSrchRegDateEnd() != null && searchTransStatVO.getSrchRegDateEnd() != "") {
			  regDateEndDate = searchTransStatVO.getSrchRegDateEnd().replaceAll("/", "");
			  regDateEndDate = searchTransStatVO.getSrchRegDateEnd().replaceAll("-", "");

			  searchTransStatVO.setSrchRegDateEnd(regDateEndDate);
		}
		
		System.out.println(searchTransStatVO.toString());
		
        int totCount = 0;
//        try{
//            totCount = transHisService.getTransStatListCount(searchTransStatVO);
//        }catch(Exception e){
//            log.debug("getNoticeListCount failure = "+e.getMessage());
//        }
        
        // 전체 게시물 건 수
//        searchTransStatVO.setTotalRecordCount(totCount);
//        
//        List<TransStatVO> transStatList = null;
//        try{
//        	transStatList = transHisService.getTransStatList(searchTransStatVO);
//        }catch(Exception e){
//            log.debug("getTransStatList failure = "+e.getMessage());
//            System.out.println(e.getMessage());
//        }
//        model.put("transStatList", transStatList);
        
        List<CommCodeVO > userIdList = new ArrayList<CommCodeVO >();
		umsUserVO = new UmsUserVO ();
		umsUserVO.setUserId(userId);
		try{
			userIdList = commonService.getUserIdCodeList(umsUserVO);
		}catch(Exception e){
			log.debug("userIdList failure = "+e.getMessage());
		}
		log.debug("userIdList = "+userIdList.toString());

		model.addAttribute("userIdList",userIdList );
        
        model.addAttribute("searchTransStatVO", searchTransStatVOView);
        log.debug(searchTransStatVO.toString());

        return "/mgr/transStatList";
    }
    
    @RequestMapping(value = "/transStatListProc")
    public String getTransStatListProc(
            @ModelAttribute("searchTransStatVO") SearchTransStatVO searchTransStatVO
            , ModelMap model, HttpServletRequest request) {
        log.debug(searchTransStatVO.toString());
        String userId = UmsServiceWebSession.getSessionUserId(request); 
    	
    	UmsUserVO umsUserVO = new UmsUserVO();
    	SearchTransStatVO searchTransStatVOView = new SearchTransStatVO();
    	searchTransStatVOView = searchTransStatVO;
    	
    	umsUserVO.setUserId(userId);
	    umsUserVO = commonService.getUserInfoD(umsUserVO);
    	
	    searchTransStatVO.setCompany(umsUserVO.getCompany());
	    searchTransStatVO.setDept(umsUserVO.getDept());
	    searchTransStatVO.setUserlv(umsUserVO.getUserLv());
	    
	    
	    if(searchTransStatVO.getSuserId() == null || searchTransStatVO.getSuserId().length() == 0){
	    	searchTransStatVO.setUserId(userId);
    	}else{
    		if(searchTransStatVO.getSuserId().equals("ALL") && umsUserVO.getUserLv().equals("10")){
    			searchTransStatVO.setUserId(userId);
    		}else{
    			searchTransStatVO.setUserId(searchTransStatVO.getSuserId());
    		}
    	}
    	
        // 한 페이지에 게시되는 게시물 건수
    	searchTransStatVO.setRecordCountPerPage(this.recordCountPerPage);
        // 페이징 리스트의 사이즈
    	searchTransStatVO.setPageSize(this.pageSize);

    	if(searchTransStatVO.getUserId() == null)
    		searchTransStatVO.setUserId(userId);
		
    	if(searchTransStatVO.getStatType() == null)
    		searchTransStatVO.setStatType("01");
    	
        model.put("firstIndex",         searchTransStatVO.getFirstRecordIndex());
        model.put("lastRecordIndex",    searchTransStatVO.getLastRecordIndex());
        model.put("recordCountPerPage", searchTransStatVO.getRecordCountPerPage());
        
        String regDateStartDate = "";
		if(searchTransStatVO.getSrchRegDateStart() != null && searchTransStatVO.getSrchRegDateStart() != "") {
			  regDateStartDate = searchTransStatVO.getSrchRegDateStart().replaceAll("/", "");
			  regDateStartDate = searchTransStatVO.getSrchRegDateStart().replaceAll("-", "");

			  searchTransStatVO.setSrchRegDateStart(regDateStartDate);
		}
		String regDateEndDate = "";
		if(searchTransStatVO.getSrchRegDateEnd() != null && searchTransStatVO.getSrchRegDateEnd() != "") {
			  regDateEndDate = searchTransStatVO.getSrchRegDateEnd().replaceAll("/", "");
			  regDateEndDate = searchTransStatVO.getSrchRegDateEnd().replaceAll("-", "");

			  searchTransStatVO.setSrchRegDateEnd(regDateEndDate);
		}
		
		System.out.println(searchTransStatVO.toString());
		
        int totCount = 0;
        try{
            totCount = transHisService.getTransStatListCount(searchTransStatVO);
        }catch(Exception e){
            log.debug("getNoticeListCount failure = "+e.getMessage());
        }
        
        // 전체 게시물 건 수
        searchTransStatVO.setTotalRecordCount(totCount);
        
        List<TransStatVO> transStatList = null;
        try{
        	transStatList = transHisService.getTransStatList(searchTransStatVO);
        }catch(Exception e){
            log.debug("getTransStatList failure = "+e.getMessage());
            System.out.println(e.getMessage());
        }
        model.put("transStatList", transStatList);
        
        List<CommCodeVO > userIdList = new ArrayList<CommCodeVO >();
		umsUserVO = new UmsUserVO ();
		umsUserVO.setUserId(userId);
		try{
			userIdList = commonService.getUserIdCodeList(umsUserVO);
		}catch(Exception e){
			log.debug("userIdList failure = "+e.getMessage());
		}
		log.debug("userIdList = "+userIdList.toString());

		model.addAttribute("userIdList",userIdList );
        
        model.addAttribute("searchTransStatVO", searchTransStatVOView);
        log.debug(searchTransStatVO.toString());

        return "/mgr/transStatList";
    }
    
    /**
     * 전송결과내역
     * @param searchTransRsltVO
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/transRsltList")
    public String getTransRsltList(
            @ModelAttribute("searchTransRsltVO") SearchTransRsltVO searchTransRsltVO
            , ModelMap model, HttpServletRequest request) {
    	
    	String userId = UmsServiceWebSession.getSessionUserId(request); 
    	
    	UmsUserVO umsUserVO = new UmsUserVO();
    	
    	model.put("searchTransHisVO", searchTransRsltVO);
    	
    	umsUserVO.setUserId(userId);
	    umsUserVO = commonService.getUserInfoD(umsUserVO);
    	
	    searchTransRsltVO.setCompany(umsUserVO.getCompany());
	    searchTransRsltVO.setDept(umsUserVO.getDept());
	    
	    searchTransRsltVO.setUserlv(umsUserVO.getUserLv());
    	
	    
	    if(searchTransRsltVO.getSuserId() == null || searchTransRsltVO.getSuserId().length() == 0){
	    	searchTransRsltVO.setUserId(userId);
    	}else{
    		
    		if(searchTransRsltVO.getSuserId().equals("ALL") && umsUserVO.getUserLv().equals("10")){
    			searchTransRsltVO.setUserId(userId);
    		}else{
    			searchTransRsltVO.setUserId(searchTransRsltVO.getSuserId());
    		}
    	}
        
        // 한 페이지에 게시되는 게시물 건수
    	searchTransRsltVO.setRecordCountPerPage(this.recordCountPerPage);
        // 페이징 리스트의 사이즈
    	searchTransRsltVO.setPageSize(this.pageSize);
        
        model.put("firstIndex",         searchTransRsltVO.getFirstRecordIndex());
        model.put("lastRecordIndex",    searchTransRsltVO.getLastRecordIndex());
        model.put("recordCountPerPage", searchTransRsltVO.getRecordCountPerPage());
        
        //배포시작 및 종료일자를 DB조회를 위해 -를 제거
        String regDateStartDate = "";
		if(searchTransRsltVO.getSrchRegDateStart() != null && searchTransRsltVO.getSrchRegDateStart() != "") {
			  regDateStartDate = searchTransRsltVO.getSrchRegDateStart().replaceAll("/", "");
			  regDateStartDate = searchTransRsltVO.getSrchRegDateStart().replaceAll("-", "");

			  searchTransRsltVO.setSrchRegDateStart(regDateStartDate);
		}
		String regDateEndDate = "";
		if(searchTransRsltVO.getSrchRegDateEnd() != null && searchTransRsltVO.getSrchRegDateEnd() != "") {
			  regDateEndDate = searchTransRsltVO.getSrchRegDateEnd().replaceAll("/", "");
			  regDateEndDate = searchTransRsltVO.getSrchRegDateEnd().replaceAll("-", "");

			  searchTransRsltVO.setSrchRegDateEnd(regDateEndDate);
		}
        
        int totCount = 0;
//        try{
//            totCount = transHisService.getTransRsltListCount(searchTransRsltVO);
//            System.out.println("##totCount : "+totCount);
//        }catch(Exception e){
//            log.debug("getTransRsltListCount failure = "+e.getMessage());
//        }
        
        CommCodeVO commCodeVO =  new CommCodeVO();

        commCodeVO.setCodeGroup("GRP_CD");
        
    	log.debug("session CommCodeVO == ",commCodeVO.toString());

    	List<CommCodeVO> grpCdList = commonService.getCodeList(commCodeVO);
        
        // 전체 게시물 건 수
    	searchTransRsltVO.setTotalRecordCount(totCount);

//        List<TransRsltVO> transRsltList = null;
//        try{
//        	transRsltList = transHisService.getTransRsltList(searchTransRsltVO);
//        }catch(Exception e){
//            log.debug("getTransRsltList failure = "+e.getMessage());
//            System.out.println("totCount : " + totCount);
//            System.out.println("msgType : "+searchTransRsltVO.getMsgType());
//            System.out.println("에러 msg : " + e.getMessage());
//        }

        
        List<CommCodeVO > userIdList = new ArrayList<CommCodeVO >();
		 umsUserVO.setUserId(userId);
		try{
			userIdList = commonService.getUserIdCodeList(umsUserVO);
		}catch(Exception e){
			log.debug("getTransRsltList failure = "+e.getMessage());
		}
		log.debug("userIdList = "+userIdList.toString());
		model.addAttribute("userIdList",userIdList );
        
        model.put("grpCdList", grpCdList);
//        model.put("transRsltList", transRsltList);
        model.put("searchTransRsltVO", searchTransRsltVO);
        
        return "/mgr/transRsltList";
    }
    
    @RequestMapping(value = "/transRsltListProc")
    public String getTransRsltListProc(
            @ModelAttribute("searchTransRsltVO") SearchTransRsltVO searchTransRsltVO
            , ModelMap model, HttpServletRequest request) {
    	
    	String userId = UmsServiceWebSession.getSessionUserId(request); 
    	
    	UmsUserVO umsUserVO = new UmsUserVO();
    	
    	model.put("searchTransHisVO", searchTransRsltVO);
    	
    	umsUserVO.setUserId(userId);
	    umsUserVO = commonService.getUserInfoD(umsUserVO);
    	
	    searchTransRsltVO.setCompany(umsUserVO.getCompany());
	    searchTransRsltVO.setDept(umsUserVO.getDept());
	    
	    searchTransRsltVO.setUserlv(umsUserVO.getUserLv());
    	
	    
	    if(searchTransRsltVO.getSuserId() == null || searchTransRsltVO.getSuserId().length() == 0){
	    	searchTransRsltVO.setUserId(userId);
    	}else{
    		
    		if(searchTransRsltVO.getSuserId().equals("ALL") && umsUserVO.getUserLv().equals("10")){
    			searchTransRsltVO.setUserId(userId);
    		}else{
    			searchTransRsltVO.setUserId(searchTransRsltVO.getSuserId());
    		}
    	}
        
        // 한 페이지에 게시되는 게시물 건수
    	searchTransRsltVO.setRecordCountPerPage(this.recordCountPerPage);
        // 페이징 리스트의 사이즈
    	searchTransRsltVO.setPageSize(this.pageSize);
        
        model.put("firstIndex",         searchTransRsltVO.getFirstRecordIndex());
        model.put("lastRecordIndex",    searchTransRsltVO.getLastRecordIndex());
        model.put("recordCountPerPage", searchTransRsltVO.getRecordCountPerPage());
        
        //배포시작 및 종료일자를 DB조회를 위해 -를 제거
        String regDateStartDate = "";
		if(searchTransRsltVO.getSrchRegDateStart() != null && searchTransRsltVO.getSrchRegDateStart() != "") {
			  regDateStartDate = searchTransRsltVO.getSrchRegDateStart().replaceAll("/", "");
			  regDateStartDate = searchTransRsltVO.getSrchRegDateStart().replaceAll("-", "");

			  searchTransRsltVO.setSrchRegDateStart(regDateStartDate);
		}
		String regDateEndDate = "";
		if(searchTransRsltVO.getSrchRegDateEnd() != null && searchTransRsltVO.getSrchRegDateEnd() != "") {
			  regDateEndDate = searchTransRsltVO.getSrchRegDateEnd().replaceAll("/", "");
			  regDateEndDate = searchTransRsltVO.getSrchRegDateEnd().replaceAll("-", "");

			  searchTransRsltVO.setSrchRegDateEnd(regDateEndDate);
		}
        
        int totCount = 0;
        try{
            totCount = transHisService.getTransRsltListCount(searchTransRsltVO);
            System.out.println("##totCount : "+totCount);
        }catch(Exception e){
            log.debug("getTransRsltListCount failure = "+e.getMessage());
        }
        
        CommCodeVO commCodeVO =  new CommCodeVO();

        commCodeVO.setCodeGroup("GRP_CD");
        
    	log.debug("session CommCodeVO == ",commCodeVO.toString());

    	List<CommCodeVO> grpCdList = commonService.getCodeList(commCodeVO);
        
        // 전체 게시물 건 수
    	searchTransRsltVO.setTotalRecordCount(totCount);

        List<TransRsltVO> transRsltList = null;
        try{
        	transRsltList = transHisService.getTransRsltList(searchTransRsltVO);
        }catch(Exception e){
            log.debug("getTransRsltList failure = "+e.getMessage());
            System.out.println("totCount : " + totCount);
            System.out.println("msgType : "+searchTransRsltVO.getMsgType());
            System.out.println("에러 msg : " + e.getMessage());
        }

        
        List<CommCodeVO > userIdList = new ArrayList<CommCodeVO >();
		 umsUserVO.setUserId(userId);
		try{
			userIdList = commonService.getUserIdCodeList(umsUserVO);
		}catch(Exception e){
			log.debug("getTransRsltList failure = "+e.getMessage());
		}
		log.debug("userIdList = "+userIdList.toString());
		model.addAttribute("userIdList",userIdList );
        
        model.put("grpCdList", grpCdList);
        model.put("transRsltList", transRsltList);
        model.put("searchTransRsltVO", searchTransRsltVO);
        
        return "/mgr/transRsltList";
    }
    
    /**
     * 전송결과 상세조회
     * @param searchRsltDetVO
     * @param result
     * @param status
     * @param redirectAttrs
     * @param model
     * @param response
     * @param request
     * @return
     */
    
    @RequestMapping(value = "/transRsltDetPopUp", method = RequestMethod.GET)
    public String getTransRsltDetail(
            @ModelAttribute("searchRsltDetVO") SearchRsltDetVO searchRsltDetVO 
            ,BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs,
            ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        
    	int totCount = 0;
    	searchRsltDetVO.setPageSize(this.pageSize);
    	searchRsltDetVO.setRecordCountPerPage(this.recordCountPerPage);
    	RsltDetMiddleVO rsltDetMiddle = null;
        try{
        	rsltDetMiddle = transHisService.getRsltDetMiddle(searchRsltDetVO);
            totCount = Integer.valueOf(rsltDetMiddle.getTotCount());
            searchRsltDetVO.setTotalRecordCount(totCount);
        }catch(Exception e){
            log.error("getRsltDetListCount failure = "+e.getMessage());
        }
        
        model.put("firstIndex",         searchRsltDetVO.getFirstRecordIndex());
        model.put("lastRecordIndex",    searchRsltDetVO.getLastRecordIndex());
        model.put("recordCountPerPage", searchRsltDetVO.getRecordCountPerPage());
        
        RsltDetTopVO rsltDetTop = null;
        List<RsltDetBottomVO> rsltDetBottomlist = null;
        try{
        	rsltDetTop = transHisService.getRsltDetTop(searchRsltDetVO);
        }catch(Exception e){
            log.debug("getRsltDetTop failure = "+e.getMessage());
        }
        try{
        	rsltDetBottomlist = transHisService.getRsltDetBottomList(searchRsltDetVO);
        }catch(Exception e){
            log.debug("getRsltDetBottomList failure = "+e.getMessage());
        }
        model.addAttribute(searchRsltDetVO);
        model.put("rsltDetTop", rsltDetTop);
        model.put("rsltDetMiddle", rsltDetMiddle);
        model.put("rsltDetBottomlist", rsltDetBottomlist);
        return "/mgr/transRsltDet";
    }
    
    
    @RequestMapping(value="/rsltDetBottomListDownload", method={RequestMethod.GET, RequestMethod.POST})
    public String rsltDetBottomListDownloadView(@ModelAttribute(value="searchRsltDetVO") SearchRsltDetVO searchRsltDetVO ,
            Map<String, Object> model) {
        List<RsltDetBottomVO> rsltDetBottomlist = null;
        String viewName = "rsltDetBottomlist";
        if(viewName.lastIndexOf(".xls") < 0) {
            viewName += ".xls";
        }
        log.debug("download file name = " + viewName);
        try{
        	rsltDetBottomlist = transHisService.getRsltDetBottomListExcel(searchRsltDetVO);
        }catch(Exception e){
            log.debug("getRsltDetBottomList failure = "+e.getMessage());
        }
        
        model.put("viewName", viewName);
        model.put("rsltDetBottomlist", rsltDetBottomlist);
        
        return "rsltDetBottomListExcelDownloadView";
    }
    
    
    /**
     * 예약내역조회
     * @param searchTransResvVO
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/transResvList")
    public String getTransResvList(
            @ModelAttribute("searchTransResvVO") SearchTransResvVO searchTransResvVO
            , ModelMap model, HttpServletRequest request) {
        
    	String userId = UmsServiceWebSession.getSessionUserId(request);        
    	
    	UmsUserVO umsUserVO = new UmsUserVO();
    	
    	model.put("searchTransResvVO", searchTransResvVO);
    	
    	umsUserVO.setUserId(userId);
	    umsUserVO = commonService.getUserInfoD(umsUserVO);
    	
	    searchTransResvVO.setCompany(umsUserVO.getCompany());
	    searchTransResvVO.setDept(umsUserVO.getDept());
	    
	    searchTransResvVO.setUserlv(umsUserVO.getUserLv());
    	
	    
	    if(searchTransResvVO.getSuserId() == null || searchTransResvVO.getSuserId().length() == 0){
	    	searchTransResvVO.setUserId(userId);
    	}else{
    		if(searchTransResvVO.getSuserId().equals("ALL") && umsUserVO.getUserLv().equals("10")){
    			searchTransResvVO.setUserId(userId);
    		}else{
    			searchTransResvVO.setUserId(searchTransResvVO.getSuserId());
    		}
    	}

    	
        // 한 페이지에 게시되는 게시물 건수
    	searchTransResvVO.setRecordCountPerPage(this.recordCountPerPage);
        // 페이징 리스트의 사이즈
    	searchTransResvVO.setPageSize(this.pageSize);
        
        model.put("firstIndex",         searchTransResvVO.getFirstRecordIndex());
        model.put("lastRecordIndex",    searchTransResvVO.getLastRecordIndex());
        model.put("recordCountPerPage", searchTransResvVO.getRecordCountPerPage());
        
        String regDateStartDate = "";
		if(searchTransResvVO.getSrchRegDateStart() != null && searchTransResvVO.getSrchRegDateStart() != "") {
			  regDateStartDate = searchTransResvVO.getSrchRegDateStart().replaceAll("/", "");
			  regDateStartDate = searchTransResvVO.getSrchRegDateStart().replaceAll("-", "");

			  searchTransResvVO.setSrchRegDateStart(regDateStartDate);
		}
		String regDateEndDate = "";
		if(searchTransResvVO.getSrchRegDateEnd() != null && searchTransResvVO.getSrchRegDateEnd() != "") {
			  regDateEndDate = searchTransResvVO.getSrchRegDateEnd().replaceAll("/", "");
			  regDateEndDate = searchTransResvVO.getSrchRegDateEnd().replaceAll("-", "");

			  searchTransResvVO.setSrchRegDateEnd(regDateEndDate);
		}
        
        
        CommCodeVO commCodeVO =  new CommCodeVO();

        commCodeVO.setCodeGroup("GRP_CD");
        
    	log.debug("session CommCodeVO == ",commCodeVO.toString());

    	List<CommCodeVO> grpCdList = commonService.getCodeList(commCodeVO);
        
    	System.out.println(grpCdList.toString());
    	
//        int totCount = 0;
//        try{
//            totCount = transHisService.getTransResvListCount(searchTransResvVO);
//        }catch(Exception e){
//            log.debug("getNoticeListCount failure = "+e.getMessage());
//        }
//        
        // 전체 게시물 건 수
//        searchTransResvVO.setTotalRecordCount(totCount);
//        List<TransResvVO> transResvList = null;
//        try{
//        	transResvList = transHisService.getTransResvList(searchTransResvVO);
//        }catch(Exception e){
//            log.debug("getNoticeList failure = "+e.getMessage());
//            System.out.println("에러 메시지"+e.getMessage());
//        }
        
        
        List<CommCodeVO > userIdList = new ArrayList<CommCodeVO >();
		umsUserVO = new UmsUserVO ();
		umsUserVO.setUserId(userId);
		try{
			userIdList = commonService.getUserIdCodeList(umsUserVO);
		}catch(Exception e){
			log.debug("userIdList failure = "+e.getMessage());
		}
		log.debug("userIdList = "+userIdList.toString());

		model.addAttribute("userIdList",userIdList );
        model.put("grpCdList", grpCdList);
//        model.put("transResvList", transResvList);
        
        return "/mgr/transResvList";
    }
    
    @RequestMapping(value = "/transResvListProc")
    public String getTransResvListProc(
            @ModelAttribute("searchTransResvVO") SearchTransResvVO searchTransResvVO
            , ModelMap model, HttpServletRequest request) {
        
    	String userId = UmsServiceWebSession.getSessionUserId(request);        
    	
    	UmsUserVO umsUserVO = new UmsUserVO();
    	
    	model.put("searchTransResvVO", searchTransResvVO);
    	
    	umsUserVO.setUserId(userId);
	    umsUserVO = commonService.getUserInfoD(umsUserVO);
    	
	    searchTransResvVO.setCompany(umsUserVO.getCompany());
	    searchTransResvVO.setDept(umsUserVO.getDept());
	    
	    searchTransResvVO.setUserlv(umsUserVO.getUserLv());
    	
	    
	    if(searchTransResvVO.getSuserId() == null || searchTransResvVO.getSuserId().length() == 0){
	    	searchTransResvVO.setUserId(userId);
    	}else{
    		if(searchTransResvVO.getSuserId().equals("ALL") && umsUserVO.getUserLv().equals("10")){
    			searchTransResvVO.setUserId(userId);
    		}else{
    			searchTransResvVO.setUserId(searchTransResvVO.getSuserId());
    		}
    	}

    	
        // 한 페이지에 게시되는 게시물 건수
    	searchTransResvVO.setRecordCountPerPage(this.recordCountPerPage);
        // 페이징 리스트의 사이즈
    	searchTransResvVO.setPageSize(this.pageSize);
        
        model.put("firstIndex",         searchTransResvVO.getFirstRecordIndex());
        model.put("lastRecordIndex",    searchTransResvVO.getLastRecordIndex());
        model.put("recordCountPerPage", searchTransResvVO.getRecordCountPerPage());
        
        String regDateStartDate = "";
		if(searchTransResvVO.getSrchRegDateStart() != null && searchTransResvVO.getSrchRegDateStart() != "") {
			  regDateStartDate = searchTransResvVO.getSrchRegDateStart().replaceAll("/", "");
			  regDateStartDate = searchTransResvVO.getSrchRegDateStart().replaceAll("-", "");

			  searchTransResvVO.setSrchRegDateStart(regDateStartDate);
		}
		String regDateEndDate = "";
		if(searchTransResvVO.getSrchRegDateEnd() != null && searchTransResvVO.getSrchRegDateEnd() != "") {
			  regDateEndDate = searchTransResvVO.getSrchRegDateEnd().replaceAll("/", "");
			  regDateEndDate = searchTransResvVO.getSrchRegDateEnd().replaceAll("-", "");

			  searchTransResvVO.setSrchRegDateEnd(regDateEndDate);
		}
        
        
        CommCodeVO commCodeVO =  new CommCodeVO();

        commCodeVO.setCodeGroup("GRP_CD");
        
    	log.debug("session CommCodeVO == ",commCodeVO.toString());

    	List<CommCodeVO> grpCdList = commonService.getCodeList(commCodeVO);
        
    	System.out.println(grpCdList.toString());
    	
        int totCount = 0;
        try{
            totCount = transHisService.getTransResvListCount(searchTransResvVO);
        }catch(Exception e){
            log.debug("getNoticeListCount failure = "+e.getMessage());
        }
        
        // 전체 게시물 건 수
        searchTransResvVO.setTotalRecordCount(totCount);
        List<TransResvVO> transResvList = null;
        try{
        	transResvList = transHisService.getTransResvList(searchTransResvVO);
        }catch(Exception e){
            log.debug("getNoticeList failure = "+e.getMessage());
            System.out.println("에러 메시지"+e.getMessage());
        }
        
        
        List<CommCodeVO > userIdList = new ArrayList<CommCodeVO >();
		umsUserVO = new UmsUserVO ();
		umsUserVO.setUserId(userId);
		try{
			userIdList = commonService.getUserIdCodeList(umsUserVO);
		}catch(Exception e){
			log.debug("userIdList failure = "+e.getMessage());
		}
		log.debug("userIdList = "+userIdList.toString());

		model.addAttribute("userIdList",userIdList );
        model.put("grpCdList", grpCdList);
        model.put("transResvList", transResvList);
        
        return "/mgr/transResvList";
    }
    
    @RequestMapping(value = "/transResvDetPopup", method = RequestMethod.GET)
    public String getTransResvDetail(
    		@ModelAttribute("searchResvDetVO") SearchResvDetVO searchResvDetVO 
            ,BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs,
            ModelMap model, HttpServletResponse response, HttpServletRequest request) {
    	
    	int totCount = 0;
    	searchResvDetVO.setPageSize(this.pageSize);
    	searchResvDetVO.setRecordCountPerPage(this.recordCountPerPage);
        try{
        	totCount = transHisService.getResvDetBottomListCount(searchResvDetVO);
            searchResvDetVO.setTotalRecordCount(totCount);
        }catch(Exception e){
            log.error("getResvDetBottomCount failure = "+e.getMessage());
        }
        
        model.put("firstIndex",         searchResvDetVO.getFirstRecordIndex());
        model.put("lastRecordIndex",    searchResvDetVO.getLastRecordIndex());
        model.put("recordCountPerPage", searchResvDetVO.getRecordCountPerPage());
        
        ResvDetTopVO resvDetTop = null;
        List<ResvDetBottomVO> resvDetBottomlist = null;
        try{
        	resvDetTop = transHisService.getResvDetTop(searchResvDetVO);
        }catch(Exception e){
            log.debug("getResvDetTop failure = "+e.getMessage());
        }
        try{
        	resvDetBottomlist = transHisService.getResvDetBottomList(searchResvDetVO);
        }catch(Exception e){
            log.debug("getResvDetBottomList failure = "+e.getMessage());
        }
        model.addAttribute(searchResvDetVO);
        model.put("resvDetTop", resvDetTop);
        model.put("resvDetBottomlist", resvDetBottomlist);
        
        return "/mgr/transResvDet";
    }
    
    /**
     * 메시지 모음함
     * @param searchMsgBoxVO
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/msgBoxList")
    public String getMsgBoxList(
            @ModelAttribute("searchMsgBoxVO") SearchMsgBoxVO searchMsgBoxVO
            , ModelMap model, HttpServletRequest request) {
        
    	String userId = UmsServiceWebSession.getSessionUserId(request);        
    	
    	
    	model.put("searchMsgBoxVO", searchMsgBoxVO);
    	
	    searchMsgBoxVO.setUserId(userId);
    	
        // 한 페이지에 게시되는 게시물 건수
    	searchMsgBoxVO.setRecordCountPerPage(this.recordCountPerPage);
        // 페이징 리스트의 사이즈
    	searchMsgBoxVO.setPageSize(this.pageSize);
        
        model.put("firstIndex",         searchMsgBoxVO.getFirstRecordIndex());
        model.put("lastRecordIndex",    searchMsgBoxVO.getLastRecordIndex());
        model.put("recordCountPerPage", searchMsgBoxVO.getRecordCountPerPage());
        
        CommCodeVO commCodeVO =  new CommCodeVO();

        commCodeVO.setCodeGroup("GRP_CD");
        
    	log.debug("session CommCodeVO == ",commCodeVO.toString());

    	List<CommCodeVO> grpCdList = commonService.getCodeList(commCodeVO);
        
        int totCount = 0;
        try{
            totCount = msgBoxService.getChngMsgBoxListCount(searchMsgBoxVO);
        }catch(Exception e){
            log.debug("getNoticeListCount failure = "+e.getMessage());
        }
        
        // 전체 게시물 건 수
        searchMsgBoxVO.setTotalRecordCount(totCount);

        List<MsgBoxVO> msgBoxList = null;
        try{
        	msgBoxList = msgBoxService.getChngMsgBoxList(searchMsgBoxVO);
        }catch(Exception e){
            log.debug("MsgBoxList failure = "+e.getMessage());
            System.out.println("totCount : "+totCount);
            System.out.println("getMessage() : "+e.getMessage());
        }
        
        List<UsrGrpVO> usrGrpList = new ArrayList<UsrGrpVO>();
        UsrGrpVO usrGrpVO =  new UsrGrpVO();
        
        usrGrpVO.setUserId(userId);
     	usrGrpVO.setGrpCd(searchMsgBoxVO.getGrpCd());
     	usrGrpList = commonService.getUsrGrpList(usrGrpVO);
     	
        model.put("usrGrpList",usrGrpList);
        model.put("grpCdList",grpCdList);
        model.put("msgBoxList", msgBoxList);
        model.put("searchMsgBoxVO", searchMsgBoxVO);
        
        return "/mgr/msgBoxList";
    }
    
    @RequestMapping(value = "/msgBoxDet", method = RequestMethod.GET)
    public String getMsgBoxDetail(
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
        
        return "/mgr/msgBoxDet";
    }
    
    @RequestMapping(value = "/msgBoxWritePopUp", method = RequestMethod.GET)
    public String createMsgBox(
            @ModelAttribute("noticeVO") NoticeVO noticeVO,
            BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs, ModelMap model, HttpServletResponse response) {
        
        String mav = "/mgr/msgBoxWritePopUp";
                
        String msg = "success!";
        return mav;
    }
    
    @RequestMapping(value = "/msgBoxWriteProcess", method = RequestMethod.POST)
    public String createMsgBoxProcess(
    		@ModelAttribute("searchMsgBoxVO") SearchMsgBoxVO searchMsgBoxVO
            , BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        
        String mav = "";
        return mav;
    }
    
    @RequestMapping(value = "/msgBoxUpdatePopup", method={RequestMethod.GET, RequestMethod.POST})
    public String updateMsgBox(
    		@ModelAttribute("searchMsgBoxVO") SearchMsgBoxVO searchMsgBoxVO
            , BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        
        String msg = "success!";
        String mav = "/mgr/msgBoxPop";
    	String userId = UmsServiceWebSession.getSessionUserId(request); 
                
        log.debug("searchMsgBoxVO = "+searchMsgBoxVO.toString());
		
        try {
                        
        	CommCodeVO commCodeVO =  new CommCodeVO();

            commCodeVO.setCodeGroup("GRP_CD");
            
        	log.debug("session CommCodeVO == ",commCodeVO.toString());

        	List<CommCodeVO> grpCdList = commonService.getCodeList(commCodeVO);
        	
        	log.debug("grpCdList = "+ grpCdList.toString() );
        	
        	model.put("grpCdList",grpCdList);
        	
        	UsrGrpVO usrGrpVO =  new UsrGrpVO(); 
        	
        	usrGrpVO.setUserId(userId);
        	usrGrpVO.setGrpCd(searchMsgBoxVO.getGrpCd());
        	log.debug("session usrGrpVO == ",usrGrpVO.toString());

        	List<UsrGrpVO> usrGrpList = commonService.getUsrGrpList(usrGrpVO);
        	
        	log.debug("usrGrpList = "+ usrGrpList.toString() );
        	
        	model.put("usrGrpList", usrGrpList); 
        	
        	MsgBoxVO msgBox = msgBoxService.getMsgBoxDetail(searchMsgBoxVO);
        	
        	model.put("msgBox", msgBox);
            
        	log.debug("msgBox = "+msgBox.toString());
        	
        }catch (Exception e ) {
        	e.printStackTrace();
        	log.error(e.getMessage());
        }
        
        return mav;
    }
    
    @RequestMapping(value="/msgBoxUpdateProcPopup", method={RequestMethod.GET, RequestMethod.POST})
    public String doModifyMsgBoxProc(@ModelAttribute("searchMsgBoxVO") SearchMsgBoxVO searchMsgBoxVO,
    		ModelMap model , HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String mav = "/mgr/msgBoxPop";
		log.debug("searchMsgBoxVO=" + searchMsgBoxVO.toString());
		
		String userId = UmsServiceWebSession.getSessionUserId(request);
		
		//수정할 데이터가 있는지 체크
		MsgBoxVO msgBoxVO = new MsgBoxVO();
		msgBoxVO.setUserId(userId);
		
		msgBoxVO.setGrpCd(searchMsgBoxVO.getGrpCd());
		msgBoxVO.setGrpNo(searchMsgBoxVO.getGrpNo());
		msgBoxVO.setMsgNo(searchMsgBoxVO.getMsgNo());
		
		msgBoxVO.setSubject(searchMsgBoxVO.getSubject());
		msgBoxVO.setContents1(searchMsgBoxVO.getContents1());
		msgBoxVO.setUpdtId(userId);
		msgBoxVO.setUpdtDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
    	log.debug("msgBoxVO = "+msgBoxVO.toString());
    	msgBoxService.modifyMsgBoxSimple(msgBoxVO);
    	model.put("msgBox", msgBoxVO); 
    	
    	String sMsg = "alert('메시지 모음함 수정 성공하였습니다.');";
		
//    	
//    	CommCodeVO commCodeVO =  new CommCodeVO();
//
//        commCodeVO.setCodeGroup("GRP_CD");
//        
//    	log.debug("session CommCodeVO == ",commCodeVO.toString());
//
//    	List<CommCodeVO> grpCdList = commonService.getCodeList(commCodeVO);
//    	
//    	log.debug("grpCdList = "+ grpCdList.toString() );
//    	
//    	model.put("grpCdList",grpCdList);
//    	
//    	UsrGrpVO usrGrpVO =  new UsrGrpVO(); 
//    	
//    	usrGrpVO.setUserId(userId);
//    	usrGrpVO.setGrpCd(searchMsgBoxVO.getGrpCd());
//    	log.debug("session usrGrpVO == ",usrGrpVO.toString());
//
//    	List<UsrGrpVO> usrGrpList = commonService.getUsrGrpList(usrGrpVO);
//    	
//    	log.debug("usrGrpList = "+ usrGrpList.toString() );
//    	
//    	model.put("usrGrpList", usrGrpList); 
    	
    	response.setContentType("text/html;charset=euc-kr");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println(sMsg);
        out.println("self.close();");
        out.println("window.opener.location.reload();");
        out.println("</script>");
        out.flush();
        out.close();
    	
		return mav;
    }
    @RequestMapping(value = "/deleteMsgBoxProc", method = RequestMethod.POST)
    public @ResponseBody JsonVO doDeleteMsgBoxProcAjax(@ModelAttribute("searchMsgBoxVO") SearchMsgBoxVO searchMsgBoxVO, 
    		ModelMap model , HttpServletRequest request) {
		
		JsonVO result = new JsonVO();
		
		log.debug("searchMsgBoxVO = "+searchMsgBoxVO.toString());
		
		try{
			String userId = UmsServiceWebSession.getSessionUserId(request);
			MsgBoxVO msgBoxVO = new MsgBoxVO();
			msgBoxVO.setUserId(userId);
			
			msgBoxVO.setGrpCd(searchMsgBoxVO.getGrpCd());
			msgBoxVO.setGrpNo(searchMsgBoxVO.getGrpNo());
			msgBoxVO.setMsgNo(searchMsgBoxVO.getMsgNo());
			
			if(msgBoxService.removeMsgBox(msgBoxVO) <= 0){
				result.setResult(false);
				result.setErrMsg("DB 에러");
			} else {
				result.setResult(true);
				result.setSucMsg("삭제 되었습니다.");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		
		return result;
    }
    
}
