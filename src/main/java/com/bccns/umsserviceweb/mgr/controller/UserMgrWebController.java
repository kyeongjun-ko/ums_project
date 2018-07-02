package com.bccns.umsserviceweb.mgr.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bccns.umsserviceweb.common.controller.DefaultAPIController;
import com.bccns.umsserviceweb.common.pagination.ImagePaginationRenderer;
import com.bccns.umsserviceweb.common.service.CommonService;
import com.bccns.umsserviceweb.common.util.CryptUtils;
import com.bccns.umsserviceweb.common.util.DateUtils;
import com.bccns.umsserviceweb.common.util.FileTools;
import com.bccns.umsserviceweb.common.util.UmsServiceWebSession;
import com.bccns.umsserviceweb.common.vo.CallBackVO;
import com.bccns.umsserviceweb.common.vo.CommCodeVO;
import com.bccns.umsserviceweb.common.vo.JsonVO;
import com.bccns.umsserviceweb.common.vo.SearchUmsUserVO;
import com.bccns.umsserviceweb.common.vo.UmsUserVO;
import com.bccns.umsserviceweb.common.vo.UsrGrpVO;
import com.bccns.umsserviceweb.mgr.service.AddrService;
import com.bccns.umsserviceweb.mgr.service.GrpService;
import com.bccns.umsserviceweb.mgr.service.MsgBoxService;
import com.bccns.umsserviceweb.mgr.service.MySvcService;
import com.bccns.umsserviceweb.mgr.vo.AddrVO;
import com.bccns.umsserviceweb.mgr.vo.GrpVO;
import com.bccns.umsserviceweb.mgr.vo.MySvcVO;
import com.bccns.umsserviceweb.mgr.vo.SearchAddrVO;
import com.bccns.umsserviceweb.mgr.vo.SearchGrpVO;
import com.bccns.umsserviceweb.mgr.vo.SearchMySvcVO;
import com.bccns.umsserviceweb.mgr.vo.SearchTransUseListVO;
import com.bccns.umsserviceweb.mgr.vo.TransUseListVO;
import com.bccns.umsserviceweb.notice.service.NoticeService;
import com.bccns.umsserviceweb.notice.vo.NoticeVO;
import com.bccns.umsserviceweb.notice.vo.SearchNoticeVO;
import com.bccns.umsserviceweb.ums.service.UmsService;
import com.bccns.umsserviceweb.ums.vo.UmsImportResultVO;

@Controller
@RequestMapping(value = "/mgr")
public class UserMgrWebController extends DefaultAPIController{
	private static final Logger log = LoggerFactory.getLogger(UserMgrWebController.class);
	
	@Autowired
	UmsService umsService;
	
	@Autowired
	NoticeService noticeService;
	
	@Autowired
	AddrService addrService;
	
	@Autowired
	MsgBoxService msgBoxService;
	
	@Autowired
	MySvcService mySvcService;
	
	@Autowired
	GrpService grpService;
	
	@Autowired
	CommonService commonService;
	
	@Value("${property.pagination.recordcountperpage}")
    private int recordCountPerPage;
    
    @Value("${property.pagination.pagesize}")
    private int pageSize;
    
    @Value("${property.code.grpcd.adr}") 			String codeGrpcdadr;
    
    private String userId;
    
    /**
     * 마이페이지
     * @param mySvcVO
     * @param umsUserVO
     * @param result
     * @param status
     * @param redirectAttrs
     * @param model
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping(value = "/transMySvc")
    public String getTransMySvc(
            @ModelAttribute("mySvcVO") MySvcVO mySvcVO
            ,@ModelAttribute("umsUserVO") UmsUserVO umsUserVO
            , BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs,
            ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        
    	log.debug("-------------start main");
        
        userId = UmsServiceWebSession.getSessionUserId(request);
        	
		/**
		 * 세션User정보 Get/Set  
		 */
		SearchMySvcVO searchMySvcVO = new SearchMySvcVO();
		String yyyymm = DateUtils.getSysDate("yyyyMM");
		searchMySvcVO.setUserId(userId);
		searchMySvcVO.setYyyymm(yyyymm);
		
		mySvcVO = new MySvcVO();   
		mySvcVO = mySvcService.getMySvcListMain(searchMySvcVO);
		
	    umsUserVO.setUserId(userId);
	    umsUserVO = commonService.getUserInfoD2(umsUserVO);
	  
	    List<CallBackVO> callbackList = new ArrayList<CallBackVO>();
	    CallBackVO callBackVO = new CallBackVO();
		callBackVO.setUserId(userId);
		callbackList = commonService.getCallBackInfo(callBackVO);
	    
		     
        mySvcVO.setUmsUserVO(umsUserVO);
	    model.put("umsUserVO",umsUserVO);  
	    model.put("mySvcVO",mySvcVO);
	    model.put("callbackList",callbackList);
            
    	
        log.debug("----------------end main");
        return "/mgr/transMySvc";
    }    
    
    /**
     * 사용내역조회
     * 
     * @param mySvcVO
     * @param searchTransUseListVO
     * @param result
     * @param status
     * @param redirectAttrs
     * @param model
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping(value = "/transUseList")
    public String getTransUseList(
              @ModelAttribute("mySvcVO") MySvcVO mySvcVO
            , @ModelAttribute("searchTransUseListVO") SearchTransUseListVO searchTransUseListVO
            , BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs,
       		Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        
    	log.debug("-------------start main");
        
    	userId = UmsServiceWebSession.getSessionUserId(request);
        	
		/**
		 * 세션User정보 Get/Set  
		 */
		log.debug("mySvcVO == ",mySvcVO.toString());
		SearchMySvcVO searchMySvcVO = new SearchMySvcVO();
		String yyyymm = DateUtils.getSysDate("yyyyMM");
		searchMySvcVO.setUserId(userId);
		if(request.getParameter("yyyymm") == "" || request.getParameter("yyyymm") == null  ){
			searchMySvcVO.setYyyymm(yyyymm);
			searchTransUseListVO.setYyyymm(yyyymm);
		}
		else{ 
			searchMySvcVO.setYyyymm(request.getParameter("yyyymm"));
			searchTransUseListVO.setYyyymm(request.getParameter("yyyymm"));
		}
    	
		log.debug("searchMySvcVO == ",searchMySvcVO.toString());

	    mySvcVO = mySvcService.getMySvcListMain(searchMySvcVO);
	        	
    	// 한 페이지에 게시되는 게시물 건수
    	searchTransUseListVO.setRecordCountPerPage(this.recordCountPerPage);
        // 페이징 리스트의 사이즈
    	
    	searchTransUseListVO.setPageSize(this.pageSize);
    	model.addAttribute("yyyyMmList",mySvcService.getYyyyMmList());
    	
    	model.addAttribute("searchTransUseListVO", searchTransUseListVO);
    	//log.debug("mySvcVO == ",mySvcVO.toString());
    	model.addAttribute("mySvcVO",mySvcVO);
            
            
        log.debug("----------------end main");
        
        return "/mgr/transUseList";
    }    
    @RequestMapping(value = "/transUseListAjax"  , method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody MySvcVO getTransUseListAjax(
            @ModelAttribute("searchTransUseListVO") SearchTransUseListVO searchTransUseListVO
            , BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs, ModelMap model, HttpServletRequest request) {
    	
    	// 한 페이지에 게시되는 게시물 건수
    	searchTransUseListVO.setRecordCountPerPage(this.recordCountPerPage);
        // 페이징 리스트의 사이즈
    	searchTransUseListVO.setPageSize(this.pageSize);
    	userId = UmsServiceWebSession.getSessionUserId(request);        
    	searchTransUseListVO.setUserId(userId);
    	String yyyymm = DateUtils.getSysDate("yyyyMM");
    	
    	log.debug("yyyymm = "+yyyymm);
    	log.debug("yyyymm1 = "+searchTransUseListVO.getYyyymm1());
    	
    	if(searchTransUseListVO.getYyyymm1() == "" || searchTransUseListVO.getYyyymm1() == null  )
    		searchTransUseListVO.setYyyymm(yyyymm);
    	else
    		searchTransUseListVO.setYyyymm(searchTransUseListVO.getYyyymm1());
    	
        int totCount = 0;
        try{
            totCount = mySvcService.getTransUseListCount(searchTransUseListVO);
        }catch(Exception e){
        	log.debug("getTransUseListCount failure = "+e.getMessage());
        }
        MySvcVO mySvcVO = new MySvcVO();
        // 전체 게시물 건 수
        searchTransUseListVO.setTotalRecordCount(totCount);
        
        List<TransUseListVO> transUseList = null;
        try{
        	transUseList = mySvcService.getTransUseList(searchTransUseListVO);
        }catch(Exception e){
        	log.debug("getMsgBoxList failure = "+e.getMessage());
        }
        mySvcVO.setTotalCount(totCount);
        mySvcVO.setTransUseList(transUseList);
        
        ImagePaginationRenderer paging = new ImagePaginationRenderer();
        String pagingHtml = paging.renderPagination(searchTransUseListVO, "linkPage");
        mySvcVO.setPagingHtml(pagingHtml);
        
    	log.debug("mySvcVO = "+mySvcVO.toString());
        return mySvcVO;
    }
    
    /**
     * 주소록조회
     *    
     * @param searchAddrVO
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/usrAddrList")
    public String getUsrAddrList(
            @ModelAttribute("searchAddrVO") SearchAddrVO searchAddrVO
            , ModelMap model, HttpServletRequest request) {
    	
    	log.debug("Addr searchAddrVO = "+searchAddrVO.toString());
        // 한 페이지에 게시되는 게시물 건수
    	searchAddrVO.setRecordCountPerPage(this.recordCountPerPage);
        // 페이징 리스트의 사이즈
    	searchAddrVO.setPageSize(this.pageSize);
        
        model.put("firstIndex",         searchAddrVO.getFirstRecordIndex());
        model.put("lastRecordIndex",    searchAddrVO.getLastRecordIndex());
        model.put("recordCountPerPage", searchAddrVO.getRecordCountPerPage());
        
        
        userId = UmsServiceWebSession.getSessionUserId(request);
        searchAddrVO.setUserId(userId);
        
        int totCount = 0;
        try{
            totCount = addrService.getAddrListCount(searchAddrVO);
        }catch(Exception e){
            log.debug("getNoticeListCount failure = "+e.getMessage());
        }
        
        // 전체 게시물 건 수
        searchAddrVO.setTotalRecordCount(totCount);

        List<AddrVO> addrList = null;
        try{
        	addrList = addrService.getAddrList(searchAddrVO);
        }catch(Exception e){
            log.debug("getAddrList failure = "+e.getMessage());
        }
        
        UsrGrpVO usrGrpVO =  new UsrGrpVO();

    	usrGrpVO.setUserId(userId);
    	usrGrpVO.setGrpCd(codeGrpcdadr);
    	log.debug("session usrGrpVO == ",usrGrpVO.toString());

    	List<UsrGrpVO> usrGrpList = commonService.getUsrGrpList(usrGrpVO);
    	
    	log.debug("usrGrpList = "+ usrGrpList.toString() );
    	
    	model.put("usrGrpList",usrGrpList);
          
        model.put("addrList", addrList);
        model.put("searchAddrVO", searchAddrVO);
        
        return "/mgr/usrAddrList";
    }
    /**
     * 주소록 상세조회
     * @param searchAddrVO
     * @param model
     * @param addrNo
     * @param request
     * @return
     */
    @RequestMapping(value = "/usrAddrDet", method = RequestMethod.GET)
    public String getUsrAddrDetail(
            @ModelAttribute("SearchAddrVO") SearchAddrVO searchAddrVO
            , ModelMap model, String addrNo, HttpServletRequest request) {
    	
    	log.debug("Addr searchAddrVO = "+searchAddrVO.toString());
    	userId = UmsServiceWebSession.getSessionUserId(request);
        searchAddrVO.setAddrNo(addrNo);
        AddrVO addrVO = null;
        try{
        	addrVO = addrService.getAddrDetail(searchAddrVO);
        }catch(Exception e){
            log.debug("getNoticeDetail failure = "+e.getMessage());
        }
        
        model.put("addrVO", addrVO);
        model.put("searchAddrVO", searchAddrVO);
        
        return "/mgr/usrAddrDet";
    }
    
    /**
     * 주소록 등록
     * @param addrVO
     * @param result
     * @param status
     * @param redirectAttrs
     * @param model
     * @param response
     * @param request
     * @return
     */
    
    @RequestMapping(value = "/usrAddrWritePopUp", method = RequestMethod.GET)
    public String createUsrAddr(
            @ModelAttribute("addrVO") AddrVO addrVO,
            BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        String mav = "/mgr/usrAddrPop";
        
        UsrGrpVO usrGrpVO =  new UsrGrpVO();

    	String userId = UmsServiceWebSession.getSessionUserId(request);
    	usrGrpVO.setUserId(userId);
    	usrGrpVO.setGrpCd(codeGrpcdadr);
    	log.debug("session usrGrpVO == ",usrGrpVO.toString());

    	List<UsrGrpVO> usrGrpList = commonService.getUsrGrpList(usrGrpVO);
    	log.debug("usrGrpList = "+ usrGrpList.toString() );
    	addrVO.setRegFg("insert");
		model.addAttribute(addrVO);
    	model.put("usrGrpList",usrGrpList);
    	
        return mav;
    }
    
    /**
     * 주소록 엑셀등록
     * @param addrVO
     * @param result
     * @param status
     * @param redirectAttrs
     * @param model
     * @param response
     * @param request
     * @return
     */
    
    @RequestMapping(value = "/usrAddrXlsPop", method = RequestMethod.GET)
    public String createUsrAddrExcel(
    		 @ModelAttribute("addrVO") AddrVO addrVO,
             BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        String mav = "/mgr/usrAddrXlsPop";
         
        UsrGrpVO usrGrpVO =  new UsrGrpVO();

     	String userId = UmsServiceWebSession.getSessionUserId(request);
     	usrGrpVO.setUserId(userId);
     	usrGrpVO.setGrpCd(codeGrpcdadr);
     	log.debug("session usrGrpVO == ",usrGrpVO.toString());

     	List<UsrGrpVO> usrGrpList = commonService.getUsrGrpList(usrGrpVO);
     	log.debug("usrGrpList = "+ usrGrpList.toString() );
     	addrVO.setRegFg("insert");
 		model.addAttribute(addrVO);
     	model.put("usrGrpList",usrGrpList);
     	
        return mav;
         
    }
    
    
    /**
     * 주소록 등록처리
     * @param addrVO
     * @param result
     * @param status
     * @param redirectAttrs
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/usrAddrWriteProcessPopup", method = { RequestMethod.GET, RequestMethod.POST })
    public String createUsrAddrProcess(
            @ModelAttribute("addrVO") AddrVO addrVO
            , BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
    	log.debug("Addr addrVO = "+addrVO.toString());

        String mav = "/mgr/usrAddrPop";
        userId = UmsServiceWebSession.getSessionUserId(request);
        addrVO.setUserId(userId);
        Integer res = 0;
        String sMsg = null;
        String fMsg = null;
        String str1 = "";
        try{
        	if(addrVO.getRegFg().equals("insert")){
        		
        		//default group create
        		if(addrVO.getGrpNo() == null || addrVO.getGrpNo().trim().length() == 0 )
        		{
        			GrpVO grpVO =  new GrpVO();
            		SearchGrpVO searchGrpVO =  new SearchGrpVO();
            		searchGrpVO.setUserId(userId);
            		searchGrpVO.setGrpCd("10");
            		searchGrpVO.setGrpNo("0");//defalut group
            		grpVO.setInstDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
            		grpService.createDefaultGrp(grpVO, searchGrpVO);
            		addrVO.setGrpNo("0");
        		}
        		res = addrService.createAddr(addrVO);
        		sMsg = "alert('주소데이터 입력이 성공하였습니다.');";
        		fMsg = "alert('주소데이터 입력이 실패하였습니다.');";
        	}else if(addrVO.getRegFg().equals("update")){
        		res = addrService.modifyAddr(addrVO);
        		sMsg = "alert('주소데이터 변경이 성공하였습니다.');";
        		fMsg = "alert('주소데이터 변경이 실패하였습니다.');";
        	}
            model.addAttribute(addrVO);
            if(res == 0){
                response.setContentType("text/html;charset=euc-kr");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println(fMsg);
                out.println("self.close();");
                out.println("</script>");
                out.flush();
                out.close();
            }else if(res == 1){
                response.setContentType("text/html;charset=euc-kr");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println(sMsg);
                out.println("self.close();");
                out.println("window.opener.location.reload();");
                out.println("</script>");
                out.flush();
                out.close();
            } 
        }catch(Exception e){
            log.debug("Addr registration failure = "+e.getMessage());
        }
        return mav;
    }
    
    /**
     * 주소록 수정
     * @param searchAddrVO
     * @param result
     * @param status
     * @param redirectAttrs
     * @param model
     * @param request
     * @param response
     * @return
     */
    
    @RequestMapping(value = "/usrAddrUpdatePopup",  method = { RequestMethod.GET, RequestMethod.POST })
    public String updateUsrAddr(
            @ModelAttribute("searchAddrVO") SearchAddrVO searchAddrVO
            , BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
    	
    	log.debug("Addr searchAddrVO = "+searchAddrVO.toString());
    	
        String msg = "success!";
        String mav = "/mgr/usrAddrPop";
        
        
        UsrGrpVO usrGrpVO =  new UsrGrpVO();

    	userId = UmsServiceWebSession.getSessionUserId(request);
    	usrGrpVO.setUserId(userId);
    	usrGrpVO.setGrpCd(codeGrpcdadr);
    	log.debug("session usrGrpVO == ",usrGrpVO.toString());

    	List<UsrGrpVO> usrGrpList = commonService.getUsrGrpList(usrGrpVO);
    	
    	log.debug("usrGrpList = "+ usrGrpList.toString() );
    	
    	model.put("usrGrpList",usrGrpList);
        
        searchAddrVO.setUserId(userId);
        String[] tempArNo = searchAddrVO.getAddrArNo();
        searchAddrVO.setAddrNo(tempArNo[0]);
        
        try{
        	log.debug("Addr searchAddrVO = "+searchAddrVO.toString());
        	AddrVO addrVO = addrService.getAddrDetail(searchAddrVO);
        	addrVO.setRegFg("update");
 		    model.addAttribute(addrVO);
 	    } catch(Exception e) {
            log.error("getAddrDetail error", e);
        }
        return mav;
    }
    
    /**
     * 주소록 삭제처리
     * @param searchAddrVO
     * @param status
     * @param redirectAttrs
     * @param model
     * @param request
     * @param response
     * @return
     */
    
    @RequestMapping(value = "/removeUsrAddr",  method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody JsonVO removeUsrAddr(@ModelAttribute("searchAddrVO") SearchAddrVO searchAddrVO
    		, SessionStatus status, RedirectAttributes redirectAttrs, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        
    	log.debug("Addr searchAddrVO = "+searchAddrVO.toString());
    	JsonVO result = new JsonVO();
    	userId = UmsServiceWebSession.getSessionUserId(request);
        searchAddrVO.setUserId(userId);
        String[] tempAddrArNo = searchAddrVO.getAddrArNo();
        
        try{
        	Integer res = 0;
        	Integer cntGrp = 0; 
        	
        	for(String temp : tempAddrArNo  ) {
        		searchAddrVO.setAddrNo(temp);
        		res = addrService.removeAddr(searchAddrVO);
        		cntGrp++;
        	}
        	if(cntGrp>0)
        		result.setSucMsg("선택한 주소 데이터가 삭제되었습니다.");
        	
 	    } catch(Exception e) {
            log.error("getAddrDetail error", e);
            result.setErrMsg("선택한 주소 데이터 삭제가 실패하였습니다.");
        }
        return result;
    }   
    
	
    /**
     * 그룹관리 조회
     * @param searchGrpVO
     * @param model
     * @param request
     * @return
     */
    
    @RequestMapping(value = "/grpMgrList",  method = { RequestMethod.GET, RequestMethod.POST })
    public String getGrpMgrList(
            @ModelAttribute("searchGrpVO") SearchGrpVO searchGrpVO
            , ModelMap model, HttpServletRequest request) {
        
    	log.debug("Grp searchGrpVO = "+searchGrpVO.toString());
        // 한 페이지에 게시되는 게시물 건수
    	searchGrpVO.setRecordCountPerPage(this.recordCountPerPage);
        // 페이징 리스트의 사이즈
    	searchGrpVO.setPageSize(this.pageSize);
        
        model.put("firstIndex",         searchGrpVO.getFirstRecordIndex());
        model.put("lastRecordIndex",    searchGrpVO.getLastRecordIndex());
        model.put("recordCountPerPage", searchGrpVO.getRecordCountPerPage());
        
        
        userId = UmsServiceWebSession.getSessionUserId(request);
        searchGrpVO.setUserId(userId);
        
        int totCount = 0;
        try{
            totCount = grpService.getGrpListCount(searchGrpVO);
        }catch(Exception e){
            log.debug("getNoticeListCount failure = "+e.getMessage());
        }
        
        // 전체 게시물 건 수
        searchGrpVO.setTotalRecordCount(totCount);

        List<GrpVO> grpList = null;
        try{
        	grpList = grpService.getGrpList(searchGrpVO);
        }catch(Exception e){
            log.debug("getMsgBoxList failure = "+e.getMessage());
        }
        
    	
        model.put("grpList", grpList);
        model.put("searchGrpVO", searchGrpVO);
        

        CommCodeVO commCodeVO =  new CommCodeVO();

        commCodeVO.setCodeGroup("GRP_CD");
        
    	log.debug("session CommCodeVO == ",commCodeVO.toString());

    	List<CommCodeVO> grpCdList = commonService.getCodeList(commCodeVO);
    	
    	log.debug("grpCdList = "+ grpCdList.toString() );
    	
    	model.put("grpCdList",grpCdList);
    	
    	List<UsrGrpVO> usrGrpList = new ArrayList<UsrGrpVO>();
    	UsrGrpVO usrGrpVO =  new UsrGrpVO();
    	
    	if(searchGrpVO.getGrpCd() == null || searchGrpVO.getGrpCd().trim().length() == 0 )
    		searchGrpVO.setGrpCd(" ");
    	
    	usrGrpVO.setUserId(userId);
     	usrGrpVO.setGrpCd(searchGrpVO.getGrpCd());
     	log.debug("session usrGrpVO == ",usrGrpVO.toString());
     	usrGrpList = commonService.getUsrGrpList(usrGrpVO);
     	log.debug("usrGrpList = "+ usrGrpList.toString() );
     	model.put("usrGrpList",usrGrpList);
     	
        return "/mgr/grpMgrList";
    }
    
    /**
     * 그룸관리 상세
     * @param searchNoticeVO
     * @param model
     * @param noticeNo
     * @param request
     * @return
     */
    
    @RequestMapping(value = "/grpMgrDet", method = RequestMethod.GET)
    public String getGrpMgrDetail(
            @ModelAttribute("searchNoticeVO") SearchNoticeVO searchNoticeVO
            , ModelMap model, String noticeNo, HttpServletRequest request) {
    	userId = UmsServiceWebSession.getSessionUserId(request);
        searchNoticeVO.setNoticeNo(noticeNo);
        NoticeVO noticeVO = null;
        try{
        	noticeVO = noticeService.getNoticeDetail(searchNoticeVO);
        }catch(Exception e){
            log.debug("getNoticeDetail failure = "+e.getMessage());
        }
        
        model.put("noticeVO", noticeVO);
        model.put("searchNoticeVO", searchNoticeVO);
        
        return "/mgr/grpMgrDet";
    }
    
    /**
     * 그룹관리 팝업
     * @param grpVO
     * @param result
     * @param status
     * @param redirectAttrs
     * @param model
     * @param response
     * @param request
     * @return
     */
    
    @RequestMapping(value = "/grpMgrWritePopUp", method = RequestMethod.GET)
    public String createGrpMgr(
            @ModelAttribute("grpVO") GrpVO grpVO,
            BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        
        String mav = "/mgr/grpMgrPop";
                
        String msg = "success!";
        
        CommCodeVO commCodeVO =  new CommCodeVO();

        commCodeVO.setCodeGroup("GRP_CD");
        
    	log.debug("session CommCodeVO == ",commCodeVO.toString());

    	List<CommCodeVO> grpCdList = commonService.getCodeList(commCodeVO);
    	
    	log.debug("grpCdList = "+ grpCdList.toString() );
    	
    	model.put("grpCdList",grpCdList);
    	grpVO.setRegFg("insert");
		model.addAttribute(grpVO);
		
        return mav;
    }
    /**
     * 그룹관리 등록처리
     * @param grpVO
     * @param result
     * @param status
     * @param redirectAttrs
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/grpMgrWriteProcessPopup", method = RequestMethod.POST)
    public String createGrpMgrProcess(
            @ModelAttribute("grpVO") GrpVO grpVO
            , BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
    	
    	log.debug("grp grpVO = "+grpVO.toString());

        String mav = "/mgr/grpMgrPop";
        userId = UmsServiceWebSession.getSessionUserId(request);
        grpVO.setUserId(userId);
        Integer res = 0;
        String sMsg = null;
        String fMsg = null;
        try{
        	if(grpVO.getRegFg().equals("insert")){
        		res = grpService.createGrp(grpVO);
        		sMsg = "alert('그룹데이터 입력이 성공하였습니다.');";
        		fMsg = "alert('그룹데이터 입력이 실패하였습니다.');";
        	}else if(grpVO.getRegFg().equals("update")){
        		res = grpService.modifyGrp(grpVO);
        		sMsg = "alert('그룹데이터 변경이 성공하였습니다.');";
        		fMsg = "alert('그룹데이터 변경이 실패하였습니다.');";
        	}
            model.addAttribute(grpVO);
            if(res == 0){
                response.setContentType("text/html;charset=euc-kr");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println(fMsg);
                out.println("self.close();");
                out.println("</script>");
                out.flush();
                out.close();
            }else if(res == 1){
                response.setContentType("text/html;charset=euc-kr");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println(sMsg);
                out.println("self.close();");
                out.println("window.opener.location.reload();");
                out.println("</script>");
                out.flush();
                out.close();
            } 
        }catch(Exception e){
            log.debug("Grp registration failure = "+e.getMessage());
        }
        return mav;
    }
    /**
     * 그룹관리 수정 팝업
     * @param searchGrpVO
     * @param result
     * @param status
     * @param redirectAttrs
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/grpMgrUpdatePopup",  method = { RequestMethod.GET, RequestMethod.POST })
    public String updateGrpMgr(
            @ModelAttribute("searchGrpVO") SearchGrpVO searchGrpVO
            , BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
    	log.debug("Addr searchGrpVO = "+searchGrpVO.toString());
    	
        String msg = "success!";
        String mav = "/mgr/grpMgrPop";
        
        CommCodeVO commCodeVO =  new CommCodeVO();

        commCodeVO.setCodeGroup("GRP_CD");
        
    	log.debug("session CommCodeVO == ",commCodeVO.toString());

    	List<CommCodeVO> grpCdList = commonService.getCodeList(commCodeVO);
    	
    	log.debug("grpCdList = "+ grpCdList.toString() );
    	
    	model.put("grpCdList",grpCdList);
        
        searchGrpVO.setUserId(userId);
        
        try{
        	log.debug("grp searchGrpVO = "+searchGrpVO.toString());
        	GrpVO grpVO = grpService.getGrpDetail(searchGrpVO);
        	grpVO.setRegFg("update");
 		    model.addAttribute(grpVO);
 	    } catch(Exception e) {
            log.error("getGrpDetail error", e);
        }
        return mav;
    }
    
    /**
     * 그룹관리 삭제
     * @param searchGrpVO
     * @param status
     * @param redirectAttrs
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/removeGrpMgr",  method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody JsonVO removeGrpMgr(@ModelAttribute("searchGrpVO") SearchGrpVO searchGrpVO
    		, SessionStatus status, RedirectAttributes redirectAttrs, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        
    	log.debug("Addr searchGrpVO = "+searchGrpVO.toString());
    	JsonVO result = new JsonVO();
    	userId = UmsServiceWebSession.getSessionUserId(request);
        searchGrpVO.setUserId(userId);
        
        String[] tempGrpArNo = searchGrpVO.getGrpArNo();
        String[] tempGrpArCd = searchGrpVO.getGrpArCd();
        Integer cntGrp = 0; 
        try{
        	Integer res = 0;
        	
        	for(String temp : tempGrpArNo  ) {
        		
        		String tmpParam[] = temp.split("-");
        		
        		searchGrpVO.setGrpNo(tmpParam[1]);
        		searchGrpVO.setGrpCd(tmpParam[0]);
        		
        		log.debug("searchGrpVO ", searchGrpVO.toString());
        		res = grpService.removeGrp(searchGrpVO);
        		cntGrp++;
        	}
        	if(cntGrp>0)
        		result.setSucMsg("선택한 그룹 데이터가 삭제되었습니다.");
		    
 	    } catch(Exception e) {
            log.error("getGrpDetail error", e);
            result.setErrMsg("선택한 그룹 데이터 삭제가 실패하였습니다.");
        }
        return result;
    }   
    
    /**
     * 회원가입승인 조회
     * @param searchUmsUserVO
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/getApplUsr",  method = { RequestMethod.GET, RequestMethod.POST })
    public String getApplUsrList(
            @ModelAttribute("searchUmsUserVO") SearchUmsUserVO searchUmsUserVO
            , ModelMap model, HttpServletRequest request) {
        
    	log.debug(" searchUmsUserVO = "+searchUmsUserVO.toString());
        // 한 페이지에 게시되는 게시물 건수
    	searchUmsUserVO.setRecordCountPerPage(this.recordCountPerPage);
        // 페이징 리스트의 사이즈
    	searchUmsUserVO.setPageSize(this.pageSize);
        
        model.put("firstIndex",         searchUmsUserVO.getFirstRecordIndex());
        model.put("lastRecordIndex",    searchUmsUserVO.getLastRecordIndex());
        model.put("recordCountPerPage", searchUmsUserVO.getRecordCountPerPage());
        
        
        userId = UmsServiceWebSession.getSessionUserId(request);
        searchUmsUserVO.setUserId(userId);
        
        UmsUserVO umsUserVO = new UmsUserVO();
    	
    	umsUserVO.setUserId(userId);
	    umsUserVO = commonService.getUserInfoD(umsUserVO);
    	
	    searchUmsUserVO.setUserLv(umsUserVO.getUserLv());
        
        int totCount = 0;
        try{
            totCount = commonService.getApplUsrListCount(searchUmsUserVO);
        }catch(Exception e){
            log.debug("getNoticeListCount failure = "+e.getMessage());
        }
        
        // 전체 게시물 건 수
        searchUmsUserVO.setTotalRecordCount(totCount);

        List<SearchUmsUserVO> applUsrList = null;
        try{
        	applUsrList = commonService.getApplUsrList(searchUmsUserVO);
        }catch(Exception e){
            log.debug("getMsgBoxList failure = "+e.getMessage());
        }
    	
    	CommCodeVO commCodeVO = new CommCodeVO();

        List<CommCodeVO> comCdList = new ArrayList<CommCodeVO>();
        
        if (umsUserVO.getCompany().equals("99")){
	    	commCodeVO.setCodeGroup("COM_CD");
        }
        else{
        	commCodeVO.setCodeGroup("COM_CD");
        	commCodeVO.setCode(umsUserVO.getCompany());
        }
        try{
        	comCdList = commonService.getCodeList(commCodeVO);
        }catch(Exception e){
        	log.debug("getCodeList failure = "+e.getMessage());
        }
        log.debug("comCdList = "+comCdList.toString());
        model.addAttribute("comCdList",comCdList);
        
        model.put("applUsrList", applUsrList);
        model.put("searchUmsUserVO", searchUmsUserVO);
        
        
        commCodeVO = new CommCodeVO();        
        
        List<CommCodeVO> userLvList = new ArrayList<CommCodeVO>();
    	commCodeVO.setCodeGroup("USER_LV");
        try{
        	userLvList = commonService.getCodeList(commCodeVO);
        }catch(Exception e){
        	log.debug("getCodeList failure = "+e.getMessage());
        }
        log.debug("userLvList = "+userLvList.toString());
        model.put("userLvList", userLvList);

        return "/mgr/applUsrList";
    }
    
    /**
     * 회원가입승인 처리
     * @param searchGrpVO
     * @param status
     * @param redirectAttrs
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/updateApplUsr",  method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody JsonVO updateApplUsr(@ModelAttribute("searchGrpVO") SearchGrpVO searchGrpVO
    		, SessionStatus status, RedirectAttributes redirectAttrs, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        
    	log.debug("Addr searchGrpVO = "+searchGrpVO.toString());
    	JsonVO result = new JsonVO();
    	String applId = request.getParameter("applId");
    	Integer res = 0;
    	userId = UmsServiceWebSession.getSessionUserId(request);
        searchGrpVO.setUserId(userId);
        
        UmsUserVO umsUserVO = new UmsUserVO();
        UmsUserVO umsUserVOpw = new UmsUserVO();

    	umsUserVO.setUserId(userId);
	    umsUserVO = commonService.getUserInfoD(umsUserVO);
        
	    if(Integer.valueOf(umsUserVO.getUserLv()) <= Integer.valueOf(request.getParameter("userLv"))){
            result.setSucMsg("가입승인 실패하였습니다.(사용자권한오류)");
            return result;
	    }
	    try{
		    /*
	    	if(Integer.valueOf(umsUserVO.getUserLv()) == 99){
				umsUserVOpw.setUserId(applId);
				umsUserVOpw = commonService.getUserInfoD(umsUserVOpw);
				umsUserVOpw.setUserPw(CryptUtils.AES_Encode(umsUserVOpw.getUserPw()));
				umsUserVOpw.setUpdtDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
				commonService.modifyPassword(umsUserVOpw);
				log.debug("set descrypt pw ", umsUserVOpw.toString());
			}
		    */
	    	
        	umsUserVO.setUserId(applId);
        	umsUserVO.setConfYn("Y");
        	umsUserVO.setUserLv(request.getParameter("userLv"));
        	umsUserVO.setUpdtDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
        	umsUserVO.setUpdtId(userId);
    		res = commonService.modifyUmsUserAppl(umsUserVO);
        	result.setSucMsg("가입승인 처리되었습니다.");
        	log.debug("umsUserVO ", umsUserVO.toString());
        	log.debug("umsUserVO.getUserLv ", umsUserVO.getUserLv());
        	
 	    } catch(Exception e) {
            log.error("getGrpDetail error", e);
            result.setErrMsg("가입승인 실패하였습니다.");
        }
        return result;
    }   
    
    /**
     * 회원가입비밀번호변경 처리
     * @param searchGrpVO
     * @param status
     * @param redirectAttrs
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/updateApplUsrPw",  method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody JsonVO updateApplUsrPw(@ModelAttribute("searchGrpVO") SearchGrpVO searchGrpVO
    		, SessionStatus status, RedirectAttributes redirectAttrs, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        
    	log.debug("Addr searchGrpVO = "+searchGrpVO.toString());
    	JsonVO result = new JsonVO();
    	userId = UmsServiceWebSession.getSessionUserId(request);
        searchGrpVO.setUserId(userId);
        
        UmsUserVO umsUserVO = new UmsUserVO();
    	
    	umsUserVO.setUserId(userId);
	    umsUserVO = commonService.getUserInfoD(umsUserVO);
	    //패스워드 암호화
        try {
			umsUserVO.setUserPw(CryptUtils.AES_Encode(umsUserVO.getUserPw()));
		} catch (InvalidKeyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidAlgorithmParameterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalBlockSizeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (BadPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
	    if(Integer.valueOf(umsUserVO.getUserLv()) <= Integer.valueOf(request.getParameter("userLv"))){
            result.setSucMsg("가입승인 실패하였습니다.(사용자권한오류)");
            return result;
	    }
	    
        try{
        	String applId = request.getParameter("applId");
        	Integer res = 0;
        	
        	umsUserVO.setUserId(applId);
        	umsUserVO.setUpdtDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
    		res = commonService.modifyPassword(umsUserVO);
        	result.setSucMsg("비밀번호업데이트 처리되었습니다.");
        	log.debug("umsUserVO ", umsUserVO.toString());

		    
 	    } catch(Exception e) {
            log.error("getGrpDetail error", e);
            result.setSucMsg("비밀번호업데이트 실패하였습니다.");
        }
        return result;
    }

	/**
	 * 주소록 엑셀 업로드 등록처리
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/addrExcelUploadProcessAjax", method=RequestMethod.POST)
	public void addrListUploadProcessAjax(ModelMap model,
	        final HttpServletRequest request, final HttpServletResponse response) {
	    
	    MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest) request;
	  //
	    
	    String grpNo = request.getParameter("addrGrpNo");

	    String uploadUserId = request.getParameter("uploadUserId");
	    if(uploadUserId == null || uploadUserId.equals("")) {
	        uploadUserId = UmsServiceWebSession.getSessionUserId(request);;
	    }
	    List<MultipartFile> files = multipartRequest.getFiles("uploadFile");
	    MultipartFile file = files.get(0);
	    
	    log.debug(" upload userId = " + uploadUserId);
	    log.debug(" upload grpNo = " + grpNo);

	    log.debug(" upload file name = " + file.getOriginalFilename());
	    
	    UmsImportResultVO resultVO = new UmsImportResultVO();
	    try {
	        //파일 확장자 체크
	        int fileExt = FileTools.checkUploadFileExtension(file.getOriginalFilename());
	        if(fileExt == -1) {
	            resultVO.setResult(false);
	            resultVO.setErrMsg("Invalid file format. Please Check file name.");
	        } else {
	            if(fileExt == 1) {
	                //excel file upload
	                resultVO = umsService.processAddrListExcelUploadInsert(file.getInputStream(), uploadUserId, grpNo);
	            } else {
	                //text file upload (구분자 ;)
	                resultVO = umsService.processAddrListTextUpload(file.getInputStream(), uploadUserId);
	            }
	            
	            resultVO.setResult(true);
	        }
	    } catch (InvalidFormatException e) {
	    	log.error("Upload file parsing error :: InvalidFormatException --> " + e.getMessage());
	        resultVO.setResult(false);
	        resultVO.setErrMsg("Upload process error :: Invalid file format exception.");
	    } catch (IOException e) {
	    	log.error("Upload file parsing error :: IOException --> " + e.getMessage());
	        resultVO.setResult(false);
	        resultVO.setErrMsg("Upload process error :: file io exception.");
	    } catch (Exception e) {
	    	log.error("Upload file parsing error :: Exception --> " + e.getMessage());
	        resultVO.setResult(false);
	        resultVO.setErrMsg("Upload process error :: System exception occured.");
	    }
	    
	    //response
	    try {
	        response.setContentType("text/html; charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        out.print(resultVO.getHtml());
	        out.flush();
	        out.close();
	    } catch (IOException e) {
	    	log.error("response write IOException :: " + e.getMessage());
	    }
	}
	
	/**
	 * 사용자 발신번호 리스트 조회
	 * @param searchUmsUserVO
	 * @param model
	 * @param request
	 */
	@RequestMapping(value = "/getGrpCallback",  method = { RequestMethod.GET, RequestMethod.POST })
	public String getGrpCallback(@ModelAttribute("searchUmsUserVO") SearchUmsUserVO searchUmsUserVO
    , ModelMap model, HttpServletRequest request) {
		log.debug(" searchUmsUserVO = "+searchUmsUserVO.toString());
        // 한 페이지에 게시되는 게시물 건수
    	searchUmsUserVO.setRecordCountPerPage(this.recordCountPerPage);
        // 페이징 리스트의 사이즈
    	searchUmsUserVO.setPageSize(this.pageSize);
        
        model.put("firstIndex",         searchUmsUserVO.getFirstRecordIndex());
        model.put("lastRecordIndex",    searchUmsUserVO.getLastRecordIndex());
        model.put("recordCountPerPage", searchUmsUserVO.getRecordCountPerPage());
        
        
        userId = UmsServiceWebSession.getSessionUserId(request);
        searchUmsUserVO.setUserId(userId);
	    searchUmsUserVO.setStatus(new String[]{"R","I","A","C"});
        
        int totCount = 0;
        try{
            totCount = commonService.getGrpCallbackListCount(searchUmsUserVO);
        }catch(Exception e){
            log.debug("getNoticeListCount failure = "+e.getMessage());
        }
        
        // 전체 게시물 건 수
        searchUmsUserVO.setTotalRecordCount(totCount);

        List<CallBackVO> grpCallbackList = null;
        
        try{
        	grpCallbackList = commonService.getGrpCallbackList(searchUmsUserVO);
        }catch(Exception e){
            log.debug("getMsgBoxList failure = "+e.getMessage());
        }

        model.put("grpCallbackList", grpCallbackList);
        model.put("searchUmsUserVO", searchUmsUserVO);
        
		return "/mgr/grpCallbackList";
	}
	
	/**
	 * 발신번호 사전등록 요청 리스트 조회
	 * @param searchUmsUserVO
	 * @param model
	 * @param request
	 */
	@RequestMapping(value = "/getApplCallback",  method = { RequestMethod.GET, RequestMethod.POST })
	public String getApplCallbackList(
			@ModelAttribute("searchUmsUserVO") SearchUmsUserVO searchUmsUserVO
            , ModelMap model, HttpServletRequest request) {
        
    	log.debug(" searchUmsUserVO = "+searchUmsUserVO.toString());
        // 한 페이지에 게시되는 게시물 건수
    	searchUmsUserVO.setRecordCountPerPage(this.recordCountPerPage);
        // 페이징 리스트의 사이즈
    	searchUmsUserVO.setPageSize(this.pageSize);
        
        model.put("firstIndex",         searchUmsUserVO.getFirstRecordIndex());
        model.put("lastRecordIndex",    searchUmsUserVO.getLastRecordIndex());
        model.put("recordCountPerPage", searchUmsUserVO.getRecordCountPerPage());
        
        
        userId = UmsServiceWebSession.getSessionUserId(request);
        searchUmsUserVO.setUserId(userId);
        
        UmsUserVO umsUserVO = new UmsUserVO();
    	
    	umsUserVO.setUserId(userId);
	    umsUserVO = commonService.getUserInfoD(umsUserVO);
    	
	    searchUmsUserVO.setUserLv(umsUserVO.getUserLv());
	    searchUmsUserVO.setStatus(new String[]{"R","I","A","C"});
        
        int totCount = 0;
        try{
            totCount = commonService.getApplCallbackListCount(searchUmsUserVO);
        }catch(Exception e){
            log.debug("getNoticeListCount failure = "+e.getMessage());
        }
        
        // 전체 게시물 건 수
        searchUmsUserVO.setTotalRecordCount(totCount);

        List<CallBackVO> applCallbackList = null;
        
        try{
        	applCallbackList = commonService.getApplCallbackList(searchUmsUserVO);
        	
        	boolean flag = false;
        	for (CallBackVO vo : applCallbackList) {
        		if ("R".equals(vo.getStatusCd())) {
        			vo.setStatusCd("I");
        			int rs = commonService.modifyCallbackInfo(vo);
        			if(rs != 0) commonService.createCallbackInfoHis(vo);
        			flag = true;
        		}
        	}
        	
        	if (flag) applCallbackList = commonService.getApplCallbackList(searchUmsUserVO); 
        }catch(Exception e){
            log.debug("getMsgBoxList failure = "+e.getMessage());
        }
    	
    	CommCodeVO commCodeVO = new CommCodeVO();

        List<CommCodeVO> comCdList = new ArrayList<CommCodeVO>();
        
        if (umsUserVO.getCompany().equals("99")){
	    	commCodeVO.setCodeGroup("COM_CD");
        }
        else{
        	commCodeVO.setCodeGroup("COM_CD");
        	commCodeVO.setCode(umsUserVO.getCompany());
        }
        try{
        	comCdList = commonService.getCodeList(commCodeVO);
        }catch(Exception e){
        	log.debug("getCodeList failure = "+e.getMessage());
        }
        log.debug("comCdList = "+comCdList.toString());
        model.addAttribute("comCdList",comCdList);
        
        model.put("applCallbackList", applCallbackList);
        model.put("searchUmsUserVO", searchUmsUserVO);
        
        
        commCodeVO = new CommCodeVO();        
        
        List<CommCodeVO> userLvList = new ArrayList<CommCodeVO>();
    	commCodeVO.setCodeGroup("USER_LV");
        try{
        	userLvList = commonService.getCodeList(commCodeVO);
        }catch(Exception e){
        	log.debug("getCodeList failure = "+e.getMessage());
        }
        log.debug("userLvList = "+userLvList.toString());
        model.put("userLvList", userLvList);

		return "/mgr/applCallbackList";
	}
	
	/**
	 * 발신번호 등록/반려 처리
	 * @param request
	 */
	@RequestMapping(value = "/updateApplCallback",  method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody boolean updateApplCallback(HttpServletRequest request) {
		
		CallBackVO callBackVO = new CallBackVO();
		
		String status = request.getParameter("status");
		callBackVO.setStatusCd(status);
		
		String callbackNo = request.getParameter("callbackNo");
		callBackVO.setCallbackNo(callbackNo.replace("-", ""));
		
		String userId = request.getParameter("userId");
		callBackVO.setUserId(userId);
		
		try{
        	int rs = commonService.modifyCallbackInfo(callBackVO);
        	if (rs != 0) {
        		commonService.createCallbackInfoHis(callBackVO);
        		return true;
        	}
        }catch(Exception e){
        	log.debug("upCallbackInfo failure = "+e.getMessage());
        }
		return false;
	}

}
