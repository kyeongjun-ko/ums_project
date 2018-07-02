package com.bccns.umsserviceweb.ums.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson; 

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bccns.umsserviceweb.common.controller.DefaultAPIController;
import com.bccns.umsserviceweb.common.pagination.ImagePaginationRenderer;
import com.bccns.umsserviceweb.common.service.CommonService;
import com.bccns.umsserviceweb.common.util.DateUtils;
import com.bccns.umsserviceweb.common.util.FileTools;
import com.bccns.umsserviceweb.common.util.StringUtil;
import com.bccns.umsserviceweb.common.util.UmsServiceWebSession;
import com.bccns.umsserviceweb.ums.exception.UmsSendProcException;
import com.bccns.umsserviceweb.mgr.service.AddrService;
import com.bccns.umsserviceweb.mgr.service.AttachFileService;
import com.bccns.umsserviceweb.mgr.service.GrpService;
import com.bccns.umsserviceweb.mgr.service.MsgBoxService;
import com.bccns.umsserviceweb.mgr.vo.AddrVO;
import com.bccns.umsserviceweb.mgr.vo.GrpVO;
import com.bccns.umsserviceweb.mgr.vo.MsgBoxVO;
import com.bccns.umsserviceweb.mgr.vo.SearchAddrVO;
import com.bccns.umsserviceweb.mgr.vo.SearchGrpVO;
import com.bccns.umsserviceweb.mgr.vo.SearchMsgBoxVO;
import com.bccns.umsserviceweb.notice.service.NoticeService;
import com.bccns.umsserviceweb.ums.service.UmsService;
import com.bccns.umsserviceweb.common.vo.CommCodeVO;
import com.bccns.umsserviceweb.common.vo.JsonVO;
import com.bccns.umsserviceweb.common.vo.UsrGrpVO;
import com.bccns.umsserviceweb.notice.vo.NoticeVO;
import com.bccns.umsserviceweb.notice.vo.SearchNoticeVO;
import com.bccns.umsserviceweb.ums.vo.FmsVO;
import com.bccns.umsserviceweb.ums.vo.MmsVO;
import com.bccns.umsserviceweb.ums.vo.SmsVO;
import com.bccns.umsserviceweb.ums.vo.UmsImportResultVO;
import com.bccns.umsserviceweb.ums.vo.VmsVO;
import com.bccns.umsserviceweb.common.vo.UmsUserVO; 
import com.bccns.umsserviceweb.mgr.vo.AttachFileVO;



@Controller
@RequestMapping(value = "/ums")
public class UmsWebController extends DefaultAPIController{
	private static final Logger logger = LoggerFactory.getLogger(UmsWebController.class);
	    
	@Value("${property.sms.scheduletype.new}") 		String smsScheduletypeNew;
	@Value("${property.sms.scheduletype.reserve}") 	String smsScheduletypeReserve;
	@Value("${property.sms.desttype.text}") 		String smsDesttypeText;
	@Value("${property.sms.destcount}") 			String smsDestcount;
	@Value("${property.sms.sendstatus}") 			String smsSendstatus;
	@Value("${property.sms.sendcount}") 			String smsSendcount;
	@Value("${property.sms.sendresult}") 			String smsSendresult;
	@Value("${property.mms.contentcount}") 			String contentcount;
	@Value("${property.mms.msgtype}") 				String msgtype;
	@Value("${property.sms.sendmaxcnt}") 			int sendmaxcnt;
	
	@Value("${property.code.grpcd.sms}") 			String codeGrpcdsms;
	@Value("${property.code.grpcd.lms}") 			String codeGrpcdlms;
	@Value("${property.code.grpcd.mms}") 			String codeGrpcdmms;
	@Value("${property.code.grpcd.fms}") 			String codeGrpcdfms;
	@Value("${property.code.grpcd.vms}") 			String codeGrpcdvms;
	@Value("${property.code.grpcd.vqr}") 			String codeGrpcdvqr;
	@Value("${property.code.grpcd.sdm}") 			String codeGrpcdsdm;
	@Value("${property.code.grpcd.sfx}") 			String codeGrpcdsfx;
	@Value("${property.code.grpcd.adr}") 			String codeGrpcdadr;
	
	@Value("${property.file.upload.file}") 			String fileUploadFile;
	@Value("${property.file.view.image}") 			String fileViewFile;
	@Value("${property.file.upload.image}") 		String fileUploadImage;
	
	@Value("${property.file.upload.image.size}") 		Integer fileUploadImageSize;
	@Value("${property.file.upload.file.size}") 		Integer fileUploadFileSize;
	
	
	@Autowired
	UmsService umsService;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	AddrService addrService;

	@Autowired
	MsgBoxService msgBoxService;
	
	@Autowired
	NoticeService noticeService;
	
	@Autowired
	GrpService grpService;
	
	@Autowired
	AttachFileService attachFileService;
	

	@Value("${property.pagination.recordcountperpage}")
    private int recordCountPerPage;
    
    @Value("${property.pagination.pagesize}")
    private int pageSize;
    
    private String userId;
	 
    /**
     * 전송수신자 추가용 주소록 팝업
     * @param searchAddrVO
     * @param model
     * @param request
     * @param response
     * @return
     */
	@RequestMapping(value="/addrListPopup" , method={RequestMethod.GET, RequestMethod.POST})
	public String getAddrListPopup(@ModelAttribute(value="searchAddrVO") SearchAddrVO searchAddrVO
			, ModelMap model, HttpServletRequest request, HttpServletResponse response ) {
	    
    	logger.debug("searchAddrVO = "+ searchAddrVO.toString() );

		UmsUserVO umsUserVO = UmsServiceWebSession.getSessionUserVO(request);
    	
    	//model.addAttribute("userVO",umsUserVO);

    	UsrGrpVO usrGrpVO =  new UsrGrpVO(); 
    	
    	userId = UmsServiceWebSession.getSessionUserId(request);
    	usrGrpVO.setUserId(userId);
    	usrGrpVO.setGrpCd(codeGrpcdadr);

    	List<UsrGrpVO> usrGrpList = commonService.getUsrGrpList(usrGrpVO);
    	
    	model.put("usrGrpList",usrGrpList);
    	int selcnt = 0;
		if(searchAddrVO.getSelCnt() == null){
			selcnt = 10; 
		}else{
			selcnt = Integer.parseInt(searchAddrVO.getSelCnt());
		}
		
	    // 한 페이지에 게시되는 게시물 건수
		//searchAddrVO.setRecordCountPerPage(this.recordCountPerPage);
		searchAddrVO.setRecordCountPerPage(selcnt);
		
	    // 페이징 리스트의 사이즈
		searchAddrVO.setPageSize(this.pageSize);
		searchAddrVO.setUserId(userId);
		searchAddrVO.setGrpNo(request.getParameter("grpNo"));
		searchAddrVO.setName(request.getParameter("name"));
		
	    model.put("firstIndex",         searchAddrVO.getFirstRecordIndex());
	    model.put("lastRecordIndex",    searchAddrVO.getLastRecordIndex());
	    model.put("recordCountPerPage", searchAddrVO.getRecordCountPerPage());
	    
	    int totCount = 0;
	    try{
	        totCount = addrService.getAddrListCount(searchAddrVO);
	    }catch(Exception e){
	    	logger.error("getAaddrListCount failure = "+e.getMessage());
	    }
	    
	    // 전체 게시물 건 수
	    searchAddrVO.setTotalRecordCount(totCount);
	
	    List<AddrVO> addrList = null;
	    try{
	    	addrList = addrService.getAddrList(searchAddrVO);
	    }catch(Exception e){
	    	logger.error("getaddrList failure = "+e.getMessage());
	    }
         
	    model.put("addrList", addrList);
	    model.put("searchAddrVO", searchAddrVO);
	    logger.debug("searchAddrVO = "+ searchAddrVO.toString() );
	    return "/ums/addrPopup";
	}  
	
	
	/**
     * 전송수신자 추가용 주소록 팝업
     * @param searchAddrVO
     * @param model
     * @param request
     * @param response
     * @return
     */
	@RequestMapping(value="/addrGrpListPopup" , method={RequestMethod.GET, RequestMethod.POST})
	public String getAddrGrpListPopup(@ModelAttribute(value="searchAddrVO") SearchGrpVO searchGrpVO
			, ModelMap model, HttpServletRequest request, HttpServletResponse response ) {
	    
    	logger.debug("searchGrpVO = "+ searchGrpVO.toString() );

		UmsUserVO umsUserVO = UmsServiceWebSession.getSessionUserVO(request);
    	
    	//model.addAttribute("userVO",umsUserVO);

    	UsrGrpVO usrGrpVO =  new UsrGrpVO(); 
    	
    	userId = UmsServiceWebSession.getSessionUserId(request);
    	usrGrpVO.setUserId(userId);
    	usrGrpVO.setGrpCd(codeGrpcdadr);

    	List<UsrGrpVO> usrGrpList = commonService.getUsrGrpList(usrGrpVO);
    	
    	model.put("usrGrpList",usrGrpList);
	    
	    searchGrpVO.setUserId(userId);
	    searchGrpVO.setGrpCd(codeGrpcdadr);
	    int selcnt = 0;
		if(searchGrpVO.getSelCnt() == null){
			selcnt = 10; 
		}else{
			selcnt = Integer.parseInt(searchGrpVO.getSelCnt());
		}
	 // 한 페이지에 게시되는 게시물 건수
 		//searchAddrVO.setRecordCountPerPage(this.recordCountPerPage);
 		searchGrpVO.setRecordCountPerPage(selcnt);
 		
         
 	    // 페이징 리스트의 사이즈
         searchGrpVO.setPageSize(this.pageSize);
	    model.put("firstIndex",         searchGrpVO.getFirstRecordIndex());
	    model.put("lastRecordIndex",    searchGrpVO.getLastRecordIndex());
	    model.put("recordCountPerPage", searchGrpVO.getRecordCountPerPage());
	    int totCount = 0;
        try{
            totCount = grpService.getGrpListCount(searchGrpVO);
        }catch(Exception e){
        	logger.debug("getGrpListCount failure = "+e.getMessage());
        }
        
		
     // 전체 게시물 건 수
        searchGrpVO.setTotalRecordCount(totCount);
        
        List<GrpVO> grpList = null;
        try{
        	grpList = grpService.getGrpList(searchGrpVO);
        }catch(Exception e){
        	logger.debug("getGrpList failure = "+e.getMessage());
        }
        
        model.put("grpList", grpList);
         
	    model.put("searchGrpVO", searchGrpVO);
	    logger.debug("searchGrpVO = "+ searchGrpVO.toString() );
	    return "/ums/addrGrpPopup";
	}  
	
	/**
	 * 주소록 엑셀 팝업
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/addrXlsPopup"  , method={RequestMethod.GET, RequestMethod.POST})
	public String getAddrExcelUplodPopupView(
	        ModelMap model) {
	    
	    return "/ums/addrXlsPopup";
	}	
	/**
	 * 주소록 엑셀 팝업
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/addrXlsPopup2"  , method={RequestMethod.GET, RequestMethod.POST})
	public String getAddrExcelUplodPopup2View(
	        ModelMap model) {
	    
	    return "/ums/addrXlsPopup2";
	}
	 
	/**
	 * 주소록 엑셀 업로드 등록처리
	 * @param model
	 * @param request
	 * @param response
	 */
	/**
	 * 주소록 엑셀 업로드 등록처리
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/addrExcelUploadProcessAjax2", method=RequestMethod.POST)
    public void addrListUploadProcessAjax2(ModelMap model,
            final HttpServletRequest request, final HttpServletResponse response) {
        
        MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest) request;
      //
        
        String uploadUserId = request.getParameter("uploadUserId");
        if(uploadUserId == null || uploadUserId.equals("")) {
            uploadUserId = UmsServiceWebSession.getSessionUserId(request);;
        }
        List<MultipartFile> files = multipartRequest.getFiles("uploadFile");
        MultipartFile file = files.get(0);
        
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
                    resultVO = umsService.processAddrListExcelUpload2(file.getInputStream(), uploadUserId);
                } else {
                    //text file upload (구분자 ;)
                    resultVO = umsService.processAddrListTextUpload(file.getInputStream(), uploadUserId);
                }
            }
        } catch (InvalidFormatException e) {
            logger.error("Upload file parsing error :: InvalidFormatException --> " + e.getMessage());
            resultVO.setResult(false);
            resultVO.setErrMsg("Upload process error :: Invalid file format exception.");
        } catch (IOException e) {
            logger.error("Upload file parsing error :: IOException --> " + e.getMessage());
            resultVO.setResult(false);
            resultVO.setErrMsg("Upload process error :: file io exception.");
        } catch (Exception e) {
            logger.error("Upload file parsing error :: Exception --> " + e.getMessage());
            resultVO.setResult(false);
            resultVO.setErrMsg("Upload process error :: System exception occured.");
        }
        logger.error("resultVO --> " + resultVO.toString());

        //response
        try {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            if(resultVO.isResult()){
            	out.print(resultVO.getHtml());
            } 
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.error("response write IOException :: " + e.getMessage());
        }
    }
	@RequestMapping(value="/addrExcelUploadProcessAjax", method=RequestMethod.POST)
    public void addrListUploadProcessAjax(ModelMap model,
            final HttpServletRequest request, final HttpServletResponse response) {
        
        MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest) request;
      //
        
        String uploadUserId = request.getParameter("uploadUserId");
        if(uploadUserId == null || uploadUserId.equals("")) {
            uploadUserId = UmsServiceWebSession.getSessionUserId(request);;
        }
        List<MultipartFile> files = multipartRequest.getFiles("uploadFile");
        MultipartFile file = files.get(0);
        
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
                    resultVO = umsService.processAddrListExcelUpload(file.getInputStream(), uploadUserId);
                } else {
                    //text file upload (구분자 ;)
                    resultVO = umsService.processAddrListTextUpload(file.getInputStream(), uploadUserId);
                }
            }
        } catch (InvalidFormatException e) {
            logger.error("Upload file parsing error :: InvalidFormatException --> " + e.getMessage());
            resultVO.setResult(false);
            resultVO.setErrMsg("Upload process error :: Invalid file format exception.");
        } catch (IOException e) {
            logger.error("Upload file parsing error :: IOException --> " + e.getMessage());
            resultVO.setResult(false);
            resultVO.setErrMsg("Upload process error :: file io exception.");
        } catch (Exception e) {
            logger.error("Upload file parsing error :: Exception --> " + e.getMessage());
            resultVO.setResult(false);
            resultVO.setErrMsg("Upload process error :: System exception occured.");
        }
        logger.error("resultVO --> " + resultVO.toString());

        //response
        try {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            if(resultVO.isResult()){
            	out.print(resultVO.getHtml());
            } 
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.error("response write IOException :: " + e.getMessage());
        }
    }
	 
	/**
	 * 메시지모음함 조회
	 * @param searchMsgBoxVO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/msgBoxListPagingAjax" , method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody SmsVO msgBoxListPagingAjax(
            @ModelAttribute("searchMsgBoxVO") SearchMsgBoxVO searchMsgBoxVO
            , ModelMap model, HttpServletRequest request) {
		
		logger.debug("searchMsgBoxVO = "+searchMsgBoxVO.toString());
		
		userId = UmsServiceWebSession.getSessionUserId(request);
		UmsUserVO umsUserVO = new UmsUserVO();
    	umsUserVO.setUserId(userId);
	    umsUserVO = commonService.getUserInfoD(umsUserVO);
		
	    searchMsgBoxVO.setUserlv(umsUserVO.getUserLv());
	    
        // 한 페이지에 게시되는 게시물 건수
    	searchMsgBoxVO.setRecordCountPerPage(this.recordCountPerPage);
        // 페이징 리스트의 사이즈
    	searchMsgBoxVO.setPageSize(this.pageSize);
        searchMsgBoxVO.setUserId(userId);
        searchMsgBoxVO.setSubject(request.getParameter("subject"));
        searchMsgBoxVO.setYyyymmdd(DateUtils.getSysDate("yyyyMMdd"));
        
        int totCount = 0;
        try{
        	if(searchMsgBoxVO.getGrpCd().equals(codeGrpcdsdm) && !searchMsgBoxVO.getUserlv().equals("99")){
        		totCount = msgBoxService.getMsgBoxDmListCount(searchMsgBoxVO);
        	}else{
        		totCount = msgBoxService.getMsgBoxListCount(searchMsgBoxVO);
        	}
            
        }catch(Exception e){
            logger.error("getMsgBoxListCount failure = "+e.getMessage());
        }
        SmsVO smsVO = new SmsVO();
        
        //그룹콤보
        UsrGrpVO usrGrpVO =  new UsrGrpVO(); 
    	
    	usrGrpVO.setUserId(userId);
    	usrGrpVO.setGrpCd(searchMsgBoxVO.getGrpCd());
    	//logger.debug("session usrGrpVO == ",usrGrpVO.toString());

    	List<UsrGrpVO> usrGrpList = null; 
        
        // 전체 게시물 건 수
        searchMsgBoxVO.setTotalRecordCount(totCount);
        
        
        List<MsgBoxVO> msgBoxList = null;
        try{
        	if(searchMsgBoxVO.getGrpCd().equals(codeGrpcdsdm) && !searchMsgBoxVO.getUserlv().equals("99")){
        		msgBoxList = msgBoxService.getMsgBoxDmList(searchMsgBoxVO);
        		usrGrpList = commonService.getUsrGrpDmList(usrGrpVO);
        		
        	}else{
        		msgBoxList = msgBoxService.getMsgBoxList(searchMsgBoxVO);
        		usrGrpList = commonService.getUsrGrpList(usrGrpVO);
        	}
        	
        }catch(Exception e){
            logger.error("getMsgBoxList failure = "+e.getMessage());
        }
        smsVO.setTotalCount(totCount);
        smsVO.setMsgBoxList(msgBoxList);
        
        ImagePaginationRenderer paging = new ImagePaginationRenderer();
        String pagingHtml = paging.renderPagination(searchMsgBoxVO, "linkPage");
        model.addAttribute("searchMsgBoxVO",searchMsgBoxVO);
        smsVO.setPagingHtml(pagingHtml);
        
    	smsVO.setUsrGrpList(usrGrpList);
    	
    	/*logger.debug("usrGrpList = "+ usrGrpList.toString() );*/
        
        logger.debug("smsVO = "+smsVO.toString());
        
        return smsVO;
    }    
	
	/**
	 * 이미지 등록 팝업
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getRegImagePop")
	public String getRegImagePopupView(
	        ModelMap model) {
	    
	    return "/ums/regImagePop";
	}	
	
	/**
	 * 문서 등록 팝업
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getRegDocPop")
	public String getRegDocPopupView(
	        ModelMap model) {
	    
	    return "/ums/regDocPop";
	}
	
	/**
	 * 이미지 등록 업로드 처리
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/regImageUploadProcessAjax", method={RequestMethod.GET, RequestMethod.POST})
	public void registImagePopupAjax(
		ModelMap model, final HttpServletRequest request, final HttpServletResponse response) {
		
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        
        String uploadUserId = request.getParameter("uploadUserId");
        if(uploadUserId == null || uploadUserId.equals("")) {
            uploadUserId = UmsServiceWebSession.getSessionUserId(request);;
        }
        List<MultipartFile> files = multipartRequest.getFiles("uploadFile");
        MultipartFile file = files.get(0);
        
        logger.debug(" upload userId = " + uploadUserId);
        logger.debug(" upload file name = " + file.getOriginalFilename());
        logger.debug(" upload file name = ");

        // 파일명
//		String fileName = StringUtil.convertKorToUTF(file.getOriginalFilename());
        String fileName = file.getOriginalFilename();
		// 업로드 경로 설정
		String sRootPath = fileUploadImage;
		//String sRootPath = request.getSession().getServletContext().getRealPath(fileUploadImage);
		//String sSvrFilePath = FileTools.getMkdirPath(sRootPath, "image");
		String sSvrFileName = FileTools.getFileName(fileName);
		
		// 파일 관련 변수 선언 
		InputStream inStream = null;    
		OutputStream outStream = null;
		// AJAX 관련 변수 선언
		PrintWriter outWriter=null; 
		
        UmsImportResultVO resultVO = new UmsImportResultVO();
        try {
        	//파일 확장자 체크
            int fileExt = FileTools.checkUploadMultiFileExtension(file.getOriginalFilename());
            //파일 사이즈 체크 
            if(file.getSize() > fileUploadImageSize) fileExt = -2;
            
        	// 파일 업로드 변수 설정
			inStream = file.getInputStream();  
			logger.debug("path:"+sRootPath);
			logger.debug("fileExt:"+fileExt);
			
			Map<String, Object> mapResult = new HashMap<String, Object>();
            if(fileExt == -1) {
            	mapResult.put("result", "failure");
            	//mapResult.put("msg", "Invalid file format. Please Check file name.");
            	mapResult.put("msg", "파일형식 오류입니다. 파일을 확인하세요.");
                resultVO.setResult(false);
                resultVO.setErrMsg("파일형식 오류입니다. 파일을 확인하세요.");
            } else if(fileExt == -2) {
            	mapResult.put("result", "failure");
            	//mapResult.put("msg", "failure Invalid file size. Please Check file size.");
            	mapResult.put("msg", "파일사이즈 오류입니다. * 허용 파일사이즈(300KB)");
            	resultVO.setResult(false);
            	//resultVO.setErrMsg("Invalid file size. Please Check file size.");
            	resultVO.setErrMsg("파일사이즈 오류입니다. * 허용 파일사이즈(300KB)");
            } else {
                if(fileExt == 1) { 
                	//이미지 : jpg, jpeg
                	logger.debug("sRootPath:"+sRootPath+sSvrFileName);
                	logger.debug("fileViewFile:"+fileViewFile);
                	//File newFile = new File(sRootPath+sSvrFilePath, sSvrFileName);
                	File newFile = new File(sRootPath, sSvrFileName);
    				// 파일 업로드 실행
    				boolean isUploaded = FileTools.uploadFormFile(file, newFile);
    				
    				if(isUploaded){
    					mapResult.put("result", "success");
    					// 업로드 파일 정보 셋팅
    					mapResult.put("file_name" , fileName);
    					mapResult.put("file_svr_name" , sSvrFileName);
    					mapResult.put("file_svr_path" , sRootPath);
    					//mapResult.put("file_full_path" , sRootPath+sSvrFilePath);
    					mapResult.put("file_full_path" , fileViewFile);
    					mapResult.put("file_size" , file.getSize());
    					mapResult.put("content_type" , file.getContentType());
    					// 이미지 파일 추가정보 설정
    					BufferedImage bimg = ImageIO.read(file.getInputStream());
    					mapResult.put("width" , bimg.getWidth());
    					mapResult.put("height" , bimg.getHeight());
    					
    					// 한글 처리를 위한 response 설정
    					response.setContentType("text/plain;charset=UTF-8");
    					response.setCharacterEncoding("UTF-8");
    					response.setHeader("Cache-Control", "no-chche");

    				}else{
    					mapResult.put("result", "failure");
    				}
                }  
                
                resultVO.setResult(true);
                logger.debug("==============>mapResult:"+mapResult.toString());
				
            }
            // 업로드 결과 전송
 			Gson gson = new Gson();
 			outWriter = response.getWriter();
 			outWriter.print(gson.toJson(mapResult));
 			outWriter.flush();
			logger.debug("mapResult:"+mapResult.toString());
        } catch (IOException e) {
            logger.error("Upload file parsing error :: IOException --> " + e.getMessage());
            resultVO.setResult(false);
            resultVO.setErrMsg("Upload process error :: file io exception.");
        } catch (Exception e) {
            logger.error("Upload file parsing error :: Exception --> " + e.getMessage());
            resultVO.setResult(false);
            resultVO.setErrMsg("Upload process error :: System exception occured.");
        } finally{
			try{if(inStream!=null) inStream.close();}catch(Exception ex){}
			try{if(outStream!=null) outStream.close();}catch(Exception ex){}
			try{if(outWriter!=null) outWriter.close();}catch(Exception ex){}
		}
    }
	
	
	/**
	 * 파일 업로드 처리 
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/regFileUploadProcessAjax", method={RequestMethod.GET, RequestMethod.POST})
	public void registFilePopupAjax(
		ModelMap model, final HttpServletRequest request, final HttpServletResponse response) {
		
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        
        String uploadUserId = request.getParameter("uploadUserId");
        if(uploadUserId == null || uploadUserId.equals("")) {
            uploadUserId = UmsServiceWebSession.getSessionUserId(request);;
        }
        List<MultipartFile> files = multipartRequest.getFiles("uploadFile");
        MultipartFile file = files.get(0);
        
        // 파일명
//		String fileName = StringUtil.convertKorToUTF(file.getOriginalFilename());
        String fileName = file.getOriginalFilename();
		// 업로드 경로 설정
		String sRootPath = fileUploadFile;
		//String sRootPath = request.getSession().getServletContext().getRealPath("/");
		//String sRootPath = uploadPathResource.getPath();
		//String sSvrFilePath = FileTools.getMkdirPath(sRootPath, "file");
		String sSvrFileName = FileTools.getFileName(fileName);
		 
		logger.debug(" upload userId = " + uploadUserId);
        logger.debug(" upload file name = " + file.getOriginalFilename());

		// 파일 관련 변수 선언 
		InputStream inStream = null;    
		OutputStream outStream = null;
		// AJAX 관련 변수 선언
		PrintWriter outWriter=null; 
		
        UmsImportResultVO resultVO = new UmsImportResultVO();
        try {
        	//파일 확장자 체크
            int fileExt = FileTools.checkUploadMultiFileExtension(file.getOriginalFilename());
            
            //파일 사이즈 체크 
            if(file.getSize() > fileUploadFileSize) fileExt = -2;
            
        	// 파일 업로드 변수 설정
			inStream = file.getInputStream();  
			logger.debug("path:"+sRootPath);
			Map<String, Object> mapResult = new HashMap<String, Object>();
			if(fileExt == -1) {
            	mapResult.put("result", "failure");
            	mapResult.put("msg", "파일형식 오류입니다. 파일을 확인하세요.");
                resultVO.setResult(false);
                resultVO.setErrMsg("파일형식 오류입니다. 파일을 확인하세요.");
            } else if(fileExt == -2) {
            	mapResult.put("result", "failure");
            	//mapResult.put("msg", "failure Invalid file size. Please Check file size.");
            	mapResult.put("msg", "파일사이즈 오류입니다. * 허용 파일사이즈(300KB)");
            	resultVO.setResult(false);
            	//resultVO.setErrMsg("Invalid file size. Please Check file size.");
            	resultVO.setErrMsg("파일사이즈 오류입니다. * 허용 파일사이즈(300KB)");
            }
            else {
                if(fileExt == 2 || fileExt == 1) { 
                	//이미지 :  
                	logger.debug("sRootPath:"+sRootPath+sSvrFileName);
                	
                	File newFile = new File(sRootPath, sSvrFileName);
    				// 파일 업로드 실행
    				boolean isUploaded = FileTools.uploadFormFile(file, newFile);
    				
    				
    				if(isUploaded){
    					mapResult.put("result", "success");
    					// 업로드 파일 정보 셋팅
    					mapResult.put("file_name" , fileName);
    					mapResult.put("file_svr_name" , sSvrFileName);
    					mapResult.put("file_svr_path" , sRootPath);
    					mapResult.put("file_full_path" , sRootPath);
    					mapResult.put("file_size" , file.getSize());
    					mapResult.put("content_type" , file.getContentType());
    					// 한글 처리를 위한 response 설정
    					response.setContentType("text/plain;charset=UTF-8");
    					response.setCharacterEncoding("UTF-8");
    					response.setHeader("Cache-Control", "no-chche");

    				}else{
    					mapResult.put("result", "failure");
    				}
                }  
                
                resultVO.setResult(true);
                logger.debug("==============>mapResult:"+mapResult.toString());
				
            }
            
			// 업로드 결과 전송
			Gson gson = new Gson();
			outWriter = response.getWriter();
			outWriter.print(gson.toJson(mapResult));
			outWriter.flush();
			
        } catch (IOException e) {
            logger.error("Upload file parsing error :: IOException --> " + e.getMessage());
            resultVO.setResult(false);
            resultVO.setErrMsg("Upload process error :: file io exception.");
        } catch (Exception e) {
            logger.error("Upload file parsing error :: Exception --> " + e.getMessage());
            resultVO.setResult(false);
            resultVO.setErrMsg("Upload process error :: System exception occured.");
        } finally{
			try{if(inStream!=null) inStream.close();}catch(Exception ex){}
			try{if(outStream!=null) outStream.close();}catch(Exception ex){}
			try{if(outWriter!=null) outWriter.close();}catch(Exception ex){}
		}
        
    }
	
	/**
	 * 그룹 콤보 조회
	 * @param searchGrpVO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/grpNoListAjax" , method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody SmsVO grpNoListAjax(
            @ModelAttribute("searchGrpVO") SearchGrpVO searchGrpVO
            , ModelMap model, final HttpServletRequest request, final HttpServletResponse response) {
		
		logger.debug("searchGrpVO = "+searchGrpVO.toString());
    	userId = UmsServiceWebSession.getSessionUserId(request);        
        
    	SmsVO smsVO = new SmsVO();
    	
        UsrGrpVO usrGrpVO =  new UsrGrpVO(); 
    	
    	
    	usrGrpVO.setUserId(userId);
    	usrGrpVO.setGrpCd(searchGrpVO.getGrpCd());
    	logger.debug("session usrGrpVO == ",usrGrpVO.toString());

    	List<UsrGrpVO> usrGrpList = commonService.getUsrGrpList(usrGrpVO);
    	
    	logger.debug("usrGrpList = "+ usrGrpList.toString() );
    	
    	smsVO.setUsrGrpList(usrGrpList);
        
        logger.debug("smsVO = "+smsVO.toString());
        
        return smsVO;
    }    
}
