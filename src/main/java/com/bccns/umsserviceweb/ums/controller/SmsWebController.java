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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bccns.umsserviceweb.common.controller.DefaultAPIController;
import com.bccns.umsserviceweb.common.pagination.ImagePaginationRenderer;
import com.bccns.umsserviceweb.common.service.CommonService;
import com.bccns.umsserviceweb.common.util.DateUtils;
import com.bccns.umsserviceweb.common.util.FileTools;
import com.bccns.umsserviceweb.common.util.StringUtil;
import com.bccns.umsserviceweb.common.util.UmsServiceWebSession;
import com.bccns.umsserviceweb.ums.api.TestClient;
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
import com.bccns.umsserviceweb.ums.vo.PDS.PDSResponseVO;
import com.bccns.umsserviceweb.common.vo.UmsUserVO; 
import com.bccns.umsserviceweb.mgr.vo.AttachFileVO;



@Controller
@RequestMapping(value = "/ums")
public class SmsWebController extends DefaultAPIController{
	private static final Logger logger = LoggerFactory.getLogger(SmsWebController.class);
	    
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
     * SMS 메인
     * @param smsVO
     * @param searchMsgBoxVO
     * @param model
     * @param request
     * @return
     */
    
	@RequestMapping(value = "/sms", method = { RequestMethod.GET, RequestMethod.POST })
    public String getSmsMain(@ModelAttribute("smsVO") SmsVO smsVO
    		, @ModelAttribute("searchMsgBoxVO") SearchMsgBoxVO searchMsgBoxVO
    	    , ModelMap model, HttpServletRequest request) {
        
        logger.debug("-------------start main");
        
        try {
        	userId = UmsServiceWebSession.getSessionUserId(request);
        	
        	/**
        	 * 세션User정보 Get/Set  
        	 */
        	try{
	        	UmsUserVO umsUserVO = UmsServiceWebSession.getSessionUserVO(request);
	        	
	
	        	UsrGrpVO usrGrpVO =  new UsrGrpVO(); 
	        	
	        	usrGrpVO.setUserId(userId);
	        	usrGrpVO.setGrpCd(codeGrpcdsms);

	        	List<UsrGrpVO> usrGrpList = commonService.getUsrGrpList(usrGrpVO);
	        	
	        	model.addAttribute("usrGrpList",usrGrpList);
	        	
	        	model.addAttribute("usrCallBackList",umsService.getUsrCallBackList(umsUserVO));
	        	
        	}catch(Exception e){
                logger.debug("getUsrGrpList failure = "+e.getMessage());
            }
        	
        	/**
             *  메시지 모음함 리스트
             */
        	// 한 페이지에 게시되는 게시물 건수
        	searchMsgBoxVO.setRecordCountPerPage(this.recordCountPerPage);
            // 페이징 리스트의 사이즈
        	searchMsgBoxVO.setPageSize(this.pageSize);
        	searchMsgBoxVO.setGrpCd(codeGrpcdsms);
        	searchMsgBoxVO.setUserId(userId);
            
        	model.addAttribute("searchMsgBoxVO", searchMsgBoxVO);
            
        }catch (Exception e ) {
            logger.error(e.getMessage());
        }
        
        logger.debug("----------------end main");
                

        return "/ums/sms";
    }
	/**
     * SMS 메인
     * @param smsVO
     * @param searchMsgBoxVO
     * @param model
     * @param request
     * @return
     */
    
	@RequestMapping(value = "/smsExc", method = { RequestMethod.GET, RequestMethod.POST })
    public String getExcelMain(
    		@ModelAttribute("smsVO") SmsVO smsVO,
    		 @ModelAttribute("searchMsgBoxVO") SearchMsgBoxVO searchMsgBoxVO
    	    , ModelMap model, HttpServletRequest request) {
        
        logger.debug("-------------start main");
        
        try {
        	userId = UmsServiceWebSession.getSessionUserId(request);
        	
        	/**
        	 * 세션User정보 Get/Set  
        	 */
        	try{
	        	UmsUserVO umsUserVO = UmsServiceWebSession.getSessionUserVO(request);
	        	
	
	        	UsrGrpVO usrGrpVO =  new UsrGrpVO(); 
	        	
	        	usrGrpVO.setUserId(userId);
	        	usrGrpVO.setGrpCd(codeGrpcdsms);

	        	List<UsrGrpVO> usrGrpList = commonService.getUsrGrpList(usrGrpVO);
	        	
	        	model.addAttribute("usrGrpList",usrGrpList);
	        	
	        	model.addAttribute("usrCallBackList",umsService.getUsrCallBackList(umsUserVO));
	        	
        	}catch(Exception e){
                logger.debug("getUsrGrpList failure = "+e.getMessage());
            }
        	
        	/**
             *  메시지 모음함 리스트
             */
        	// 한 페이지에 게시되는 게시물 건수
        	searchMsgBoxVO.setRecordCountPerPage(this.recordCountPerPage);
            // 페이징 리스트의 사이즈
        	searchMsgBoxVO.setPageSize(this.pageSize);
        	searchMsgBoxVO.setGrpCd(codeGrpcdsms);
        	searchMsgBoxVO.setUserId(userId);
            
        	model.addAttribute("searchMsgBoxVO", searchMsgBoxVO);
            
        }catch (Exception e ) {
            logger.error(e.getMessage());
        }
        
        logger.debug("----------------end main");
                

        return "/ums/smsExc";
    }
	
	/**
	 * SMS 간편팝업
	 * @param smsVO
	 * @param searchMsgBoxVO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/smsPopUp", method = { RequestMethod.GET, RequestMethod.POST })
    public String getSmsPop(@ModelAttribute("smsVO") SmsVO smsVO
    		, @ModelAttribute("searchMsgBoxVO") SearchMsgBoxVO searchMsgBoxVO
    	    , ModelMap model, HttpServletRequest request) {
        
        logger.debug("-------------start main");
        
        try {
        	userId = UmsServiceWebSession.getSessionUserId(request);
        	
        	/**
        	 * 세션User정보 Get/Set  
        	 */
        	try{
	        	UmsUserVO umsUserVO = UmsServiceWebSession.getSessionUserVO(request);
	
	        	UsrGrpVO usrGrpVO =  new UsrGrpVO(); 
	        	
	        	usrGrpVO.setUserId(userId);
	        	usrGrpVO.setGrpCd(codeGrpcdsms);

	        	List<UsrGrpVO> usrGrpList = commonService.getUsrGrpList(usrGrpVO);
	        	
	        	model.addAttribute("usrGrpList",usrGrpList);
	        	
	        	model.addAttribute("usrCallBackList",umsService.getUsrCallBackList(umsUserVO));
	        	
        	}catch(Exception e){
                logger.debug("getUsrGrpList failure = "+e.getMessage());
            }
        	
        	/**
             *  메시지 모음함 리스트
             */
        	// 한 페이지에 게시되는 게시물 건수
        	searchMsgBoxVO.setRecordCountPerPage(this.recordCountPerPage);
            // 페이징 리스트의 사이즈
        	searchMsgBoxVO.setPageSize(this.pageSize);
        	searchMsgBoxVO.setGrpCd(codeGrpcdsms);
        	searchMsgBoxVO.setUserId(userId);
            
        	model.addAttribute("searchMsgBoxVO", searchMsgBoxVO);
        }catch (Exception e ) {
            logger.error(e.getMessage());
        }
        
        logger.debug("----------------end main");

        return "/ums/smsPop";
    }
	
	/**
	 * 일반 SMS Send
	 * @param smsVO
	 * @param receiverInfo
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/sendProc", method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody JsonVO doSendProcAjax( @ModelAttribute(value="smsVO")  SmsVO smsVO
    		, @RequestParam("receiverInfo") String[] receiverInfo
    		, ModelMap model , HttpServletRequest request) {
  
		
        JsonVO result = new JsonVO();
        List<Map<String,Object>> createReceivers1 = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> pdsmap = new HashMap<String,Object>();
    	String receiverInfoTemp = "";
    	String reserveDate = "";
    	String repeatDate = "";
    	String msgGrpNo = "";
    	String subject = "";
    	String receiveType = request.getParameter("RECEIVER_TYPE"); 

    	int rowCnt = 1;
    	int rowSubCnt = 0;
    	int rowAddCnt = 0;
    	userId = UmsServiceWebSession.getSessionUserId(request);
    	msgGrpNo = request.getParameter("MSGGRPNO"); 
    	
        try {
        	logger.debug("sendProc info = " + smsVO.toString());
        	subject = smsVO.getSubject();
        	subject = StringUtil.getHanEngNumber(subject);
        	if(StringUtil.lengthB(subject) > 50) subject = StringUtil.substrB(subject, 1, 50);
        	smsVO.setSubject(subject);
        	
        	//메시지저장 처리
        	if( request.getParameter("MSGSAVE").equals("Y")){
        		
        		//default group create
        		GrpVO grpVO =  new GrpVO();
        		SearchGrpVO searchGrpVO =  new SearchGrpVO();
        		searchGrpVO.setUserId(userId);
        		if(request.getParameter("MSGTYPE").equals("SMS")){
        			searchGrpVO.setGrpCd(codeGrpcdsms);
	        	}else if(request.getParameter("MSGTYPE").equals("MMS")){
	        		searchGrpVO.setGrpCd(codeGrpcdlms);
	        	}
        		
        		searchGrpVO.setGrpNo("0");//defalut group
        		grpVO.setInstDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
        		grpService.createDefaultGrp(grpVO, searchGrpVO);
        		
        		//save msg create
	        	MsgBoxVO msgBoxVO =  new MsgBoxVO();
	        	msgBoxVO.setUserId(userId);
	        	if(request.getParameter("MSGTYPE").equals("SMS")){
	        		msgBoxVO.setGrpCd(codeGrpcdsms);
	        	}else if(request.getParameter("MSGTYPE").equals("MMS")){
	        		msgBoxVO.setGrpCd(codeGrpcdlms);
	        	}
	        	
	        	if(msgGrpNo.equals("") || msgGrpNo == null)
	        		msgBoxVO.setGrpNo("0");//defalut group No
	        	else
	        		msgBoxVO.setGrpNo(msgGrpNo);//msg group No
	        	
	        	msgBoxVO.setSubject(smsVO.getSubject());
	        	msgBoxVO.setContents1(smsVO.getSmsMsg());
	        	umsService.createmsgBox(msgBoxVO);
	        	
        	}
        	

        	//80바이트 문자 분리을 위한 메시지 80바이트 분리
        	int msgLen = StringUtil.lengthB(smsVO.getSmsMsg());

        	List<String> msgArr = new ArrayList<String>();
        	int sPt = 1;
        	int mCnt = 1;
        	String oriMsg = smsVO.getSmsMsg();
        	String tmpMsg = smsVO.getSmsMsg();
        	while (msgLen >= 80) {
        		System.out.println("msgLen = "+msgLen);
        		System.out.println("sPt = "+sPt);
        		System.out.println("mCnt = "+mCnt);
        		msgArr.add(StringUtil.substrB(tmpMsg, sPt, 80));
        		System.out.println("reqStr = "+StringUtil.substrB(tmpMsg, sPt, 80));
        		msgLen = msgLen - 80;
        		sPt = sPt + 80;
        		tmpMsg = smsVO.getSmsMsg();
        		mCnt ++;
            }
        	if(msgLen <= 80 ){
        		msgArr.add(StringUtil.substrB(tmpMsg, sPt, 80));
        	}
        	
        	//리시버타입이 A이면 일반주소록 전송
        	if(receiveType.equals("A")){
	        	//SMS 발신처리
	        	logger.debug("receiverInfo info = " + receiverInfo.toString());
	        	logger.debug("receiverInfo length = " + receiverInfo.length);
	        	for(int i=0; i<receiverInfo.length; i++){
	        	//for(String temp : receiverInfo ) {
	        		rowAddCnt ++;
					receiverInfoTemp = receiverInfoTemp.concat(receiverInfo[i]);
					if(i == ((sendmaxcnt*rowCnt)-1)){
						map = new HashMap();
						map.put("receiverInfoTemp", receiverInfoTemp);
						map.put("rowSubCnt", sendmaxcnt);
						createReceivers1.add(map);
						receiverInfoTemp = "";
						//logger.debug("createReceiver :" + createReceivers1.get(rowCnt));
						rowCnt++;
						rowAddCnt = 0;
					} 
				}
	        	if( rowCnt== 1 ){
		    		map = new HashMap();
		    		map.put("receiverInfoTemp", receiverInfoTemp);
					map.put("rowSubCnt", rowAddCnt);
					createReceivers1.add(map);
	        	}
	        	else if( rowCnt > 1){
	        		if(receiverInfo.length % ((rowCnt-1)*sendmaxcnt) > 0 ){
	        			map = new HashMap();
	    	    		map.put("receiverInfoTemp", receiverInfoTemp);
	    				map.put("rowSubCnt", rowAddCnt);
	    				createReceivers1.add(map);
	        		}
	        	}
        	}
        	//리시버타입이 G이면 그룹주소록 전송
        	else if(receiveType.equals("G")){
        		
        		for(int i=0; i<receiverInfo.length; i++){
		        	logger.debug("receiverInfo info = " + receiverInfo.toString());
		        	logger.debug("receiverInfo length = " + receiverInfo.length);
		        	rowCnt = 1;
		        	rowAddCnt = 0;
		        	receiverInfoTemp = "";
					SearchAddrVO searchAddrVO = new SearchAddrVO();
					userId = UmsServiceWebSession.getSessionUserId(request);
					searchAddrVO.setUserId(userId);
					searchAddrVO.setGrpNo(receiverInfo[i]);
					
					//주소록 리스트
					List<AddrVO> addrList = null;
				    addrList = addrService.getAddrListMain(searchAddrVO);
				    
					for(int l=0; l<addrList.size(); l++ ){
						rowAddCnt ++;
						//addrList.get(l).getSmsNo();
						//addrList.get(l).getName();
						receiverInfoTemp = receiverInfoTemp.concat(addrList.get(l).getName())
								.concat("^").concat(addrList.get(l).getSmsNo()).concat("|");
//			        	logger.debug("receiverInfoTemp = " + receiverInfoTemp);
						//logger.debug("rowAddCnt = " + rowAddCnt);
						if(l == ((sendmaxcnt*rowCnt)-1)){
							map = new HashMap();
							map.put("receiverInfoTemp", receiverInfoTemp);
							map.put("rowSubCnt", sendmaxcnt);
							//logger.debug("receiverInfoTemp :" + receiverInfoTemp);

							createReceivers1.add(map);
							receiverInfoTemp = "";
							//logger.debug("createReceiver :" + createReceivers1.get(rowCnt));
							rowCnt++;
							rowAddCnt = 0;
						}
        			}
					if( rowCnt== 1 ){
			    		map = new HashMap();
			    		map.put("receiverInfoTemp", receiverInfoTemp);
						map.put("rowSubCnt", rowAddCnt);
						createReceivers1.add(map);
		        	}
		        	else if( rowCnt > 1){
		        		if(receiverInfo.length % ((rowCnt-1)*sendmaxcnt) > 0 ){
		        			map = new HashMap();
		    	    		map.put("receiverInfoTemp", receiverInfoTemp);
		    				map.put("rowSubCnt", rowAddCnt);
		    				createReceivers1.add(map);
		        		}
		        	}
				}
        		
        	}
	        	
        	for(int i=0; i<createReceivers1.size(); i++){
	        	
	        	smsVO.setUserId(userId);
	        	
	        	if(smsVO.getScheduleType().equals("NOW")){
	        		smsVO.setScheduleType(smsScheduletypeNew);  
	        		smsVO.setSendDate(DateUtils.getSysDate("yyyyMMddHHmmss"));
	        		
	        		//smsVO.setSendDate("99991231000000");
	        	}else if(smsVO.getScheduleType().equals("RESERVE")){	        		
	        		smsVO.setScheduleType(smsScheduletypeReserve);
	        		reserveDate = reserveDate.concat(smsVO.getReserveYear());
	        		reserveDate = reserveDate.concat(smsVO.getReserveMonth());
	        		reserveDate = reserveDate.concat(smsVO.getReserveDay());
	        		reserveDate = reserveDate.concat(smsVO.getReserveHour());
	        		reserveDate = reserveDate.concat(smsVO.getReserveMin()); 
	        		reserveDate = reserveDate.concat("00");
	        		smsVO.setSendDate(reserveDate);
	        		
	        	}else if(smsVO.getScheduleType().equals("REPEAT")){
	        		smsVO.setScheduleType(smsScheduletypeReserve);
	        		repeatDate.concat(smsVO.getRepeatHour());
	        		repeatDate.concat(smsVO.getRepeatMin());	        		
	        		smsVO.setSendDate(DateUtils.getSysDate("yyyyMMddHHmmss"));
	        	}
	        	smsVO.setDestType(smsDesttypeText);
	        	smsVO.setSendStatus(smsSendstatus);
	        	smsVO.setSendCount(smsSendcount); 
	        	smsVO.setSendResult(smsSendresult);
	        	Map imap = (HashMap)createReceivers1.get(i);
	        	smsVO.setDestCount(imap.get("rowSubCnt").toString());	   
	        	
	        	String destInfo = imap.get("receiverInfoTemp").toString();
	        	destInfo = StringUtil.replaceString(destInfo).trim();
	        	
	        	smsVO.setDestInfo(destInfo);	        	
	        	logger.debug(this.getClass() + " : toString  = "+smsVO.toString());
	        	if(request.getParameter("MSGTYPE").equals("SMS") || request.getParameter("SMSSPLIT").equals("Y")){
	        		//SMS분리 구분이 Y이면 80바이트씩분리하여 발송	        		
	        		if(request.getParameter("SMSSPLIT").equals("Y")){
			        	for(int j = 0; j< msgArr.size(); j++){
			        		smsVO.setSmsMsg(msgArr.get(j));
			        		logger.debug("SmsMsg = "+msgArr.get(j));
			        		umsService.createSms(smsVO);
			        	}
	        		}else{
	        		
	        			
	        			
		        		umsService.createSms(smsVO);
	        		}
	        		result.setSucMsg("SMS 메시지 보내기가 성공하였습니다.");
	        	}else if(request.getParameter("MSGTYPE").equals("MMS") && request.getParameter("SMSSPLIT").equals("N")){
	        		smsVO.setNowDate(DateUtils.getSysDate("yyyyMMddHHmmss"));
	        		umsService.createLms(smsVO);
	        		result.setSucMsg("LMS 메시지 보내기가 성공하였습니다.");
	        	}
			}        	
        } catch (UmsSendProcException e) {
            logger.error("create sms data validation check fail :: " + e.getMessage());
            result.setResult(false);
            result.setErrMsg(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setResult(false);
            //result.setErrMsg(e.getMessage());
            result.setErrMsg("System error :: ums content info create fail.");
        }
        return result;
    }
	
	/**
	 * 대량 SMS Send
	 * @param smsVO
	 * @param receiverInfo
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/sendProc2", method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody JsonVO doSendProcAjax2( @ModelAttribute(value="smsVO")  SmsVO smsVO
    		, @RequestParam("receiverInfo") String[] receiverInfo
    		, ModelMap model , HttpServletRequest request) {
		
		
        JsonVO result = new JsonVO();
        List<Map<String,Object>> createReceivers1 = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> pdsmap = new HashMap<String,Object>();
    	String receiverInfoTemp = "";
    	String reserveDate = "";
    	String repeatDate = "";
    	String msgGrpNo = "";
    	String subject = "";
    	String receiveType = request.getParameter("RECEIVER_TYPE"); 

    	int rowCnt = 1;
    	int rowSubCnt = 0;
    	int rowAddCnt = 0;
    	userId = UmsServiceWebSession.getSessionUserId(request);
    	msgGrpNo = request.getParameter("MSGGRPNO"); 
  
        try {
        	logger.debug("sendProc info = " + smsVO.toString());
        	subject = smsVO.getSubject();
        	subject = StringUtil.getHanEngNumber(subject);
        	if(StringUtil.lengthB(subject) > 50) subject = StringUtil.substrB(subject, 1, 50);
        	smsVO.setSubject(subject);
        	
        	//메시지저장 처리
        	if( request.getParameter("MSGSAVE").equals("Y")){
        		
        		//default group create
        		GrpVO grpVO =  new GrpVO();
        		SearchGrpVO searchGrpVO =  new SearchGrpVO();
        		searchGrpVO.setUserId(userId);
        		if(request.getParameter("MSGTYPE").equals("SMS")){
        			searchGrpVO.setGrpCd(codeGrpcdsms);
	        	}else if(request.getParameter("MSGTYPE").equals("MMS")){
	        		searchGrpVO.setGrpCd(codeGrpcdlms);
	        	}
        		
        		searchGrpVO.setGrpNo("0");//defalut group
        		grpVO.setInstDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
        		grpService.createDefaultGrp(grpVO, searchGrpVO);
        		
        		//save msg create
	        	MsgBoxVO msgBoxVO =  new MsgBoxVO();
	        	msgBoxVO.setUserId(userId);
	        	if(request.getParameter("MSGTYPE").equals("SMS")){
	        		msgBoxVO.setGrpCd(codeGrpcdsms);
	        	}else if(request.getParameter("MSGTYPE").equals("MMS")){
	        		msgBoxVO.setGrpCd(codeGrpcdlms);
	        	}
	        	
	        	if(msgGrpNo.equals("") || msgGrpNo == null)
	        		msgBoxVO.setGrpNo("0");//defalut group No
	        	else
	        		msgBoxVO.setGrpNo(msgGrpNo);//msg group No
	        	
	        	msgBoxVO.setSubject(smsVO.getSubject());
	        	msgBoxVO.setContents1(smsVO.getSmsMsg());
	        	umsService.createmsgBox(msgBoxVO);
        	}
        	

        	//80바이트 문자 분리을 위한 메시지 80바이트 분리
        	int msgLen = StringUtil.lengthB(smsVO.getSmsMsg());

        	List<String> msgArr = new ArrayList<String>();
        	int sPt = 1;
        	int mCnt = 1;
        	String oriMsg = smsVO.getSmsMsg();
        	String tmpMsg = smsVO.getSmsMsg();
        	while (msgLen >= 80) {
        		System.out.println("msgLen = "+msgLen);
        		System.out.println("sPt = "+sPt);
        		System.out.println("mCnt = "+mCnt);
        		msgArr.add(StringUtil.substrB(tmpMsg, sPt, 80));
        		System.out.println("reqStr = "+StringUtil.substrB(tmpMsg, sPt, 80));
        		msgLen = msgLen - 80;
        		sPt = sPt + 80;
        		tmpMsg = smsVO.getSmsMsg();
        		mCnt ++;
            }
        	if(msgLen <= 80 ){
        		msgArr.add(StringUtil.substrB(tmpMsg, sPt, 80));
        	}
        	
        	//리시버타입이 A이면 일반주소록 전송
        	if(receiveType.equals("A")){
	        	//SMS 발신처리
        		
	        	logger.debug("receiverInfo info = " + receiverInfo.toString());
	        	logger.debug("receiverInfo length = " + receiverInfo.length);
	        	for(int i=0; i<receiverInfo.length; i++){
	        	//for(String temp : receiverInfo ) {
	        		rowAddCnt ++;
					receiverInfoTemp = receiverInfoTemp.concat(receiverInfo[i]);
					if(i == ((sendmaxcnt*rowCnt)-1)){
						map = new HashMap();
						map.put("receiverInfoTemp", receiverInfoTemp);
						map.put("rowSubCnt", sendmaxcnt);
						createReceivers1.add(map);
						receiverInfoTemp = "";
						//logger.debug("createReceiver :" + createReceivers1.get(rowCnt));
						rowCnt++;
						rowAddCnt = 0;
					} 
				}
	        	if( rowCnt== 1 ){
		    		map = new HashMap();
		    		map.put("receiverInfoTemp", receiverInfoTemp);
					map.put("rowSubCnt", rowAddCnt);
					createReceivers1.add(map);
	        	}
	        	else if( rowCnt > 1){
	        		if(receiverInfo.length % ((rowCnt-1)*sendmaxcnt) > 0 ){
	        			map = new HashMap();
	    	    		map.put("receiverInfoTemp", receiverInfoTemp);
	    				map.put("rowSubCnt", rowAddCnt);
	    				createReceivers1.add(map);
	        		}
	        	}
        	}
        	//리시버타입이 E이면 대량 전송
        	else if(receiveType.equals("E")){
	        	//SMS 발신처리
        		
	        	logger.debug("receiverInfo info = " + receiverInfo.toString());
	        	logger.debug("receiverInfo length = " + receiverInfo.length);
	        	for(int i=0; i<receiverInfo.length; i++){
	        	//for(String temp : receiverInfo ) {
	        		rowAddCnt ++;
					receiverInfoTemp = receiverInfoTemp.concat(receiverInfo[i]);
					/*if(i == ((sendmaxcnt*rowCnt)-1)){
						map = new HashMap();
						map.put("receiverInfoTemp", receiverInfoTemp);
						map.put("rowSubCnt", sendmaxcnt);
						createReceivers1.add(map);
						receiverInfoTemp = "";
						//logger.debug("createReceiver :" + createReceivers1.get(rowCnt));
						rowCnt++;
						rowAddCnt = 0;
					} */
				}
	        	if(rowAddCnt > 500 | receiverInfo.length >500){
	        		logger.error("Data has exceeded 500.");
	                result.setResult(false);
	                result.setErrMsg("최대 500건까지 발송 가능 합니다. 작업이 취소되었습니다.");
	                return result;
	        	}
	        	if( rowCnt== 1 ){
	        		
		    		map = new HashMap();
		    		map.put("receiverInfoTemp", receiverInfoTemp);
					map.put("rowSubCnt", rowAddCnt);
					createReceivers1.add(map);
	        	}
	        	else if( rowCnt > 1){
	        		if(receiverInfo.length % ((rowCnt-1)*sendmaxcnt) > 0 ){
	        			
	        			map = new HashMap();
	    	    		map.put("receiverInfoTemp", receiverInfoTemp);
	    				map.put("rowSubCnt", rowAddCnt);
	    				createReceivers1.add(map);
	        		}
	        	}
	        	
        	}
        	//리시버타입이 G이면 그룹주소록 전송
        	else if(receiveType.equals("G")){
        		
        		for(int i=0; i<receiverInfo.length; i++){
		        	logger.debug("receiverInfo info = " + receiverInfo.toString());
		        	logger.debug("receiverInfo length = " + receiverInfo.length);
		        	rowCnt = 1;
		        	rowAddCnt = 0;
		        	receiverInfoTemp = "";
					SearchAddrVO searchAddrVO = new SearchAddrVO();
					userId = UmsServiceWebSession.getSessionUserId(request);
					searchAddrVO.setUserId(userId);
					searchAddrVO.setGrpNo(receiverInfo[i]);
					
					//주소록 리스트
					List<AddrVO> addrList = null;
				    addrList = addrService.getAddrListMain(searchAddrVO);
				    
					for(int l=0; l<addrList.size(); l++ ){
						rowAddCnt ++;
						//addrList.get(l).getSmsNo();
						//addrList.get(l).getName();
						receiverInfoTemp = receiverInfoTemp.concat(addrList.get(l).getName())
								.concat("^").concat(addrList.get(l).getSmsNo()).concat("|");
//			        	logger.debug("receiverInfoTemp = " + receiverInfoTemp);
						//logger.debug("rowAddCnt = " + rowAddCnt);
						if(l == ((sendmaxcnt*rowCnt)-1)){
							map = new HashMap();
							map.put("receiverInfoTemp", receiverInfoTemp);
							map.put("rowSubCnt", sendmaxcnt);
							//logger.debug("receiverInfoTemp :" + receiverInfoTemp);

							createReceivers1.add(map);
							receiverInfoTemp = "";
							//logger.debug("createReceiver :" + createReceivers1.get(rowCnt));
							rowCnt++;
							rowAddCnt = 0;
						}
        			}
					if( rowCnt== 1 ){
			    		map = new HashMap();
			    		map.put("receiverInfoTemp", receiverInfoTemp);
						map.put("rowSubCnt", rowAddCnt);
						createReceivers1.add(map);
		        	}
		        	else if( rowCnt > 1){
		        		if(receiverInfo.length % ((rowCnt-1)*sendmaxcnt) > 0 ){
		        			map = new HashMap();
		    	    		map.put("receiverInfoTemp", receiverInfoTemp);
		    				map.put("rowSubCnt", rowAddCnt);
		    				createReceivers1.add(map);
		        		}
		        	}
				}
        		
        	}
	        	
        	for(int i=0; i<createReceivers1.size(); i++){
	        	
	        	smsVO.setUserId(userId);
	        	
	        	if(smsVO.getScheduleType().equals("NOW")){
	        		smsVO.setScheduleType(smsScheduletypeNew);  
	        		smsVO.setSendDate(DateUtils.getSysDate("yyyyMMddHHmmss"));
	        		
	        		//smsVO.setSendDate("99991231000000");
	        	}else if(smsVO.getScheduleType().equals("RESERVE")){	        		
	        		smsVO.setScheduleType(smsScheduletypeReserve);
	        		reserveDate = reserveDate.concat(smsVO.getReserveYear());
	        		reserveDate = reserveDate.concat(smsVO.getReserveMonth());
	        		reserveDate = reserveDate.concat(smsVO.getReserveDay());
	        		reserveDate = reserveDate.concat(smsVO.getReserveHour());
	        		reserveDate = reserveDate.concat(smsVO.getReserveMin()); 
	        		reserveDate = reserveDate.concat("00");
	        		smsVO.setSendDate(reserveDate);
	        		
	        	}else if(smsVO.getScheduleType().equals("REPEAT")){
	        		smsVO.setScheduleType(smsScheduletypeReserve);
	        		repeatDate.concat(smsVO.getRepeatHour());
	        		repeatDate.concat(smsVO.getRepeatMin());	        		
	        		smsVO.setSendDate(DateUtils.getSysDate("yyyyMMddHHmmss"));
	        	}
	        	smsVO.setDestType(smsDesttypeText);
	        	smsVO.setSendStatus(smsSendstatus);
	        	smsVO.setSendCount(smsSendcount); 
	        	smsVO.setSendResult(smsSendresult);
	        	Map imap = (HashMap)createReceivers1.get(i);
	        	smsVO.setDestCount(imap.get("rowSubCnt").toString());	   
	        	
	        	String destInfo = imap.get("receiverInfoTemp").toString();
	        	
	        	
	        	
	        	destInfo = StringUtil.replaceString(destInfo).trim();
	        	
	        	smsVO.setDestInfo(destInfo);
	        	
	        	
	        	
	        	logger.debug(this.getClass() + " : toString  = "+smsVO.toString());
	        	if(request.getParameter("MSGTYPE").equals("SMS") || request.getParameter("SMSSPLIT").equals("Y")){
	        		//SMS분리 구분이 Y이면 80바이트씩분리하여 발송	        		
	        		if(request.getParameter("SMSSPLIT").equals("Y")){
			        	for(int j = 0; j< msgArr.size(); j++){
			        		smsVO.setSmsMsg(msgArr.get(j));
			        		logger.debug("SmsMsg = "+msgArr.get(j));
			        		umsService.createSms(smsVO);
			        		
			        	}
	        		}else{
	        			String userid = smsVO.getUserId();
	        			String sche = smsVO.getScheduleType();
        				String sub = smsVO.getSubject();
        				/*String smsmsg = smsVO.getSmsMsg();*/
        				String senddate = smsVO.getSendDate();
        				String callback = smsVO.getCallback();
        				String destType = smsVO.getDestType();
        				/*String destcount = smsVO.getDestCount();*/
        		        String destinfo = smsVO.getDestInfo();
        		        String sendstatus = smsVO.getSendStatus();
        		        String sendcount = smsVO.getSendCount();
        		        String sendresult = smsVO.getSendResult();
       			        
        		        String [] smsm = request.getParameterValues("smsMsg");
        		
        		     /*   String [] sms = smsmsg.split(",");*/
        		     	String [] di = destinfo.split(",");
       			    	
        		     	for(int k=0; k<smsm.length;k++){
        		  
        		     		SmsVO sv = new SmsVO();
        		     		sv.setUserId(userid);
        		     		sv.setScheduleType(sche);
        		     		sv.setSubject(sub);
        		     		sv.setSmsMsg(smsm[k]);
        		     		sv.setSendDate(senddate);
        		     		sv.setCallback(callback);
        		     		sv.setDestType(destType);
        		     		sv.setDestCount("1");
        		     		sv.setDestInfo(di[k]);
        		     		sv.setSendStatus(sendstatus);
        		     		sv.setSendCount(sendcount);
        		     		sv.setSendResult(sendresult);
        		     		
        		     	umsService.createSms(sv);
    		   		}
	        	
	        		}
	        		result.setSucMsg("SMS 메시지 보내기가 성공하였습니다.");
	        	}else if(request.getParameter("MSGTYPE").equals("MMS") && request.getParameter("SMSSPLIT").equals("N")){
	        	
	        		
	        		String userid = smsVO.getUserId();
        			String sche = smsVO.getScheduleType();
    				String sub = smsVO.getSubject();    		
    				String senddate = smsVO.getSendDate();
    				String callback = smsVO.getCallback();  			
    				/*String destcount = smsVO.getDestCount();*/
    		        String destinfo = smsVO.getDestInfo();
    		    	/*String smsmsg = smsVO.getSmsMsg();*/
    				String destType = smsVO.getDestType();
    		        String sendstatus = smsVO.getSendStatus();
    		        String sendcount = smsVO.getSendCount();
    		        String sendresult = smsVO.getSendResult();
    		     
    		        String [] smsm = request.getParameterValues("smsMsg");
    		       /* String [] sms = smsmsg.split(",");*/
    		     	String [] di = destinfo.split(",");
   			    	
    		     	for(int p=0; p<smsm.length;p++){
    		     		
    		     		SmsVO sv = new SmsVO();
    		     		sv.setUserId(userid);
    		     		sv.setScheduleType(sche);
    		     		sv.setSubject(sub);
    		     		sv.setNowDate(DateUtils.getSysDate("yyyyMMddHHmmss"));
    		     		sv.setDestType(destType);
    		     		sv.setSendDate(senddate);
    		     		sv.setCallback(callback);
    		     		sv.setDestCount("1");
    		     		sv.setDestInfo(di[p]);
    		     		sv.setSmsMsg(smsm[p]);
    		     		sv.setSendStatus(sendstatus);
    		     		sv.setSendCount(sendcount);
    		     		sv.setSendResult(sendresult);
    		   
    		     		
    		     		umsService.createLms(sv);
    		     	}	   
    		     	result.setSucMsg("LMS 메시지 보내기가 성공하였습니다.");
	        	}
	        	
			}        	
        } catch (UmsSendProcException e) {
            logger.error("create sms data validation check fail :: " + e.getMessage());
            result.setResult(false);
            result.setErrMsg(e.getMessage());
        } catch (Exception e) {
        
            logger.error(e.getMessage());
            result.setResult(false);
            //result.setErrMsg(e.getMessage());
            result.setErrMsg("System error :: ums content info create fail.");
        }
        return result;
    }
	
	/**
	 * 폰구분 SMS Send
	 * @param smsVO
	 * @param receiverInfo
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/sendPdsProc", method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody JsonVO doSendPdsProcAjax( @ModelAttribute(value="smsVO")  SmsVO smsVO
    		, @RequestParam("receiverInfo") String[] receiverInfo
    		, ModelMap model , HttpServletRequest request) {
    	
        JsonVO result = new JsonVO();
        MsgBoxVO msgBoxVO =  new MsgBoxVO();        
        List<Map<String,Object>> createReceiversSms = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> createReceiversMms = new ArrayList<Map<String,Object>>();
        Map<String,Object> mapSms = new HashMap<String,Object>();
        Map<String,Object> mapMms = new HashMap<String,Object>();
    	String receiverInfoTempSms = "";//폰구분N
    	String receiverInfoTempMms = "";//폰구분Y
    	String reserveDate = "";
    	String repeatDate = "";
    	String msgGrpNo = "";
    	String pdsMode = "";
    	String subject = "";
    	int rowCntSms = 1;
    	int rowCntMms = 1;
    	int rowSubCnt = 0;
    	int rowAddCntSms = 0;
    	int rowAddCntMms = 0;
    	
    	int rowTotCntSms = 0;
    	int rowTotCntMms = 0;
    	
    	userId = UmsServiceWebSession.getSessionUserId(request);
    	msgGrpNo = request.getParameter("MSGGRPNO"); 
    	pdsMode = request.getParameter("pdsMode");
    	
        try {
        	logger.debug("sendProc info = " + smsVO.toString());
        	subject = smsVO.getSubject();
        	subject = StringUtil.getHanEngNumber(subject);
        	if(StringUtil.lengthB(subject) > 50) subject = StringUtil.substrB(subject, 1, 50);
        	smsVO.setSubject(subject);
        	
        	//메시지저장 처리
        	if( request.getParameter("MSGSAVE").equals("Y")){
        		
        		//default group create
        		GrpVO grpVO =  new GrpVO();
        		SearchGrpVO searchGrpVO =  new SearchGrpVO();
        		searchGrpVO.setUserId(userId);
        		if(request.getParameter("MSGTYPE").equals("SMS")){
        			searchGrpVO.setGrpCd(codeGrpcdsms);
	        	}else if(request.getParameter("MSGTYPE").equals("MMS")){
	        		searchGrpVO.setGrpCd(codeGrpcdlms);
	        	}
        		//디폴트 메시지 그룹생성
        		searchGrpVO.setGrpNo("0");//defalut group
        		grpVO.setInstDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
        		grpService.createDefaultGrp(grpVO, searchGrpVO);
        		
        		//save msg create
	        	msgBoxVO.setUserId(userId);
	        	if(request.getParameter("MSGTYPE").equals("SMS")){
	        		msgBoxVO.setGrpCd(codeGrpcdsms);
	        	}else if(request.getParameter("MSGTYPE").equals("MMS")){
	        		msgBoxVO.setGrpCd(codeGrpcdlms);
	        	}
	        	
	        	//그룹번호가 없으면 디폴트그룹으로 메시지저장
	        	if(msgGrpNo.equals("") || msgGrpNo == null)
	        		msgBoxVO.setGrpNo("0");//defalut group No
	        	else
	        		msgBoxVO.setGrpNo(msgGrpNo);//msg group No
	        	
	        	msgBoxVO.setSubject(smsVO.getSubject());
	        	msgBoxVO.setContents1(smsVO.getSmsMsg());
	        	umsService.createmsgBox(msgBoxVO);
        	}
        	
        	//폰구분 서비스를 위한 메시지 80바이트 분리
        	int msgLen = StringUtil.lengthB(smsVO.getSmsMsg());

        	List<String> msgArr = new ArrayList<String>();
        	int sPt = 1;
        	int mCnt = 1;
        	
        	String oriMsg = smsVO.getSmsMsg();
        	String tmpMsg = smsVO.getSmsMsg();
        	while (msgLen >= 80) {
        		System.out.println("msgLen = "+msgLen);
        		System.out.println("sPt = "+sPt);
        		System.out.println("mCnt = "+mCnt);
        		msgArr.add(StringUtil.substrB(tmpMsg, sPt, 80));
        		System.out.println("reqStr = "+StringUtil.substrB(tmpMsg, sPt, 80));
        		msgLen = msgLen - 80;
        		sPt = sPt + 80;
        		tmpMsg = smsVO.getSmsMsg();
        		mCnt ++;
            }
        	if(msgLen <= 80 ){
        		msgArr.add(StringUtil.substrB(tmpMsg, sPt, 80));
        	}
        	
        	//SMS 발신처리
        	logger.debug("receiverInfo info = " + receiverInfo.toString());
        	logger.debug("receiverInfo length = " + receiverInfo.length);
        	//SMS수신자수 만큼 LOOP
        	for(int i=0; i<receiverInfo.length; i++){
        		receiverInfo[i] = StringUtil.replaceString(receiverInfo[i].toString());
				
        		//폰구분을 위한 문자스플릿
        		String[] phntmp = StringUtil.splitData(receiverInfo[i].toString(),"^");
				String pdsPhn = "";
				if(phntmp.length>0){
					pdsPhn = phntmp[1].toString();
					pdsPhn = pdsPhn.replace("|", "").trim();	
				}				
				
				//폰구분 메시지요청 생성
				String reqStr = "{'header':{'clientId':'bluechip','clientPw':'bluechip'},'body':{'requestId':'11111','telNo':'"+pdsPhn+"'}}";
				//폰구분 실행
				PDSResponseVO vo = new TestClient(request).request(reqStr);
				
				//폰구분응답값이 정상인경우
				if(vo.getBody().getResult().getCode().equals("S00000")){
					//스마트폰인경우
					if(vo.getBody().getData().getSmartPhnYn().equals("Y")){
						rowAddCntMms ++;
						rowTotCntMms ++;
						receiverInfoTempMms = receiverInfoTempMms.concat(receiverInfo[i]);
					}
					//스마트폰이아닌경우
					else{
						rowAddCntSms ++;
						rowTotCntSms ++;
						receiverInfoTempSms = receiverInfoTempSms.concat(receiverInfo[i]);

					}					
				}
				//오류인경우 스마트폰으로 강제설정
				else{					
					//pdsmap.put("pdsYn", "Y");
					rowAddCntMms ++;
					rowTotCntMms ++;
					receiverInfoTempMms = receiverInfoTempMms.concat(receiverInfo[i]);
				}
				
				//SMS건수 100건마다 리스트Add후 초기화
				if(rowTotCntSms == ((sendmaxcnt*rowCntSms))){
					mapSms = new HashMap();
					mapSms.put("receiverInfoTemp", receiverInfoTempSms);
					mapSms.put("rowSubCnt", sendmaxcnt);
					createReceiversSms.add(mapSms);
					receiverInfoTempSms = "";
					rowCntSms++;
					rowAddCntSms = 0;
				} 
				
				//MMS건수 100건마다 리스트Add후 초기화
				if(rowTotCntMms == ((sendmaxcnt*rowCntMms))){
					mapMms = new HashMap();
					mapMms.put("receiverInfoTemp", receiverInfoTempMms);
					mapMms.put("rowSubCnt", sendmaxcnt);
					createReceiversMms.add(mapMms);
					receiverInfoTempMms = "";
					rowCntMms++;
					rowAddCntMms = 0;
				} 
			}
        	
        	if( rowCntSms== 1 ){
	    		mapSms = new HashMap();
	    		mapSms.put("receiverInfoTemp", receiverInfoTempSms);
				mapSms.put("rowSubCnt", rowAddCntSms);
				createReceiversSms.add(mapSms);
        	}
        	else if( rowCntSms > 1){
        		if(rowAddCntSms % ((rowCntSms-1)*sendmaxcnt) > 0 ){
        			mapSms = new HashMap();
    	    		mapSms.put("receiverInfoTemp", receiverInfoTempSms);
    				mapSms.put("rowSubCnt", rowAddCntSms);
    				createReceiversSms.add(mapSms);
        		}
        	}
        	
        	if( rowCntMms== 1 ){
	    		mapMms = new HashMap();
	    		mapMms.put("receiverInfoTemp", receiverInfoTempMms);
				mapMms.put("rowSubCnt", rowAddCntMms);
				createReceiversMms.add(mapMms);
        	}
        	else if( rowCntMms > 1){
        		if(rowAddCntMms % ((rowCntMms-1)*sendmaxcnt) > 0 ){
        			mapMms = new HashMap();
    	    		mapMms.put("receiverInfoTemp", receiverInfoTempMms);
    				mapMms.put("rowSubCnt", rowAddCntMms);
    				createReceiversMms.add(mapMms);
        		}
        	}
        	
        	logger.debug("rowCntSms= " + rowCntSms);
        	logger.debug("rowCntMms= " + rowCntMms);
        	
        	logger.debug("rowAddCntSms= " + rowAddCntSms);
        	logger.debug("rowAddCntMms= " + rowAddCntMms);
        	
        	logger.debug("rowTotCntSms= " + rowTotCntSms);
        	logger.debug("rowTotCntMms= " + rowTotCntMms);
        	
        	//SMS인경우 레코드수 > 0 || 
        	if(rowTotCntSms > 0){
	        	for(int i=0; i<createReceiversSms.size(); i++){
		        	
		        	smsVO.setUserId(userId);
		        	
		        	if(smsVO.getScheduleType().equals("NOW")){
		        		smsVO.setScheduleType(smsScheduletypeNew);  
		        		smsVO.setSendDate(DateUtils.getSysDate("yyyyMMddHHmmss"));
		        	}else if(smsVO.getScheduleType().equals("RESERVE")){	        		
		        		smsVO.setScheduleType(smsScheduletypeReserve);
		        		reserveDate = reserveDate.concat(smsVO.getReserveYear());
		        		reserveDate = reserveDate.concat(smsVO.getReserveMonth());
		        		reserveDate = reserveDate.concat(smsVO.getReserveDay());
		        		reserveDate = reserveDate.concat(smsVO.getReserveHour());
		        		reserveDate = reserveDate.concat(smsVO.getReserveMin()); 
		        		reserveDate = reserveDate.concat("00");
		        		smsVO.setSendDate(reserveDate);
		        		
		        	}else if(smsVO.getScheduleType().equals("REPEAT")){
		        		smsVO.setScheduleType(smsScheduletypeReserve);
		        		repeatDate.concat(smsVO.getRepeatHour());
		        		repeatDate.concat(smsVO.getRepeatMin());	        		
		        		smsVO.setSendDate(DateUtils.getSysDate("yyyyMMddHHmmss"));
		        	}
		        	smsVO.setDestType(smsDesttypeText);
		        	smsVO.setSendStatus(smsSendstatus);
		        	smsVO.setSendCount(smsSendcount); 
		        	smsVO.setSendResult(smsSendresult);
		        	Map imap = (HashMap)createReceiversSms.get(i);
		        	smsVO.setDestCount(imap.get("rowSubCnt").toString());	   
		        	
		        	String destInfo = imap.get("receiverInfoTemp").toString();
		        	destInfo = StringUtil.replaceString(destInfo).trim();
		        	
		        	smsVO.setDestInfo(destInfo);	        	
		        	logger.debug(this.getClass() + " : toString  = "+smsVO.toString());
		        	
		        	for(int j = 0; j< msgArr.size(); j++){
		        		smsVO.setSmsMsg(msgArr.get(j));
		        		logger.debug("SmsMsg = "+msgArr.get(j));
		        		umsService.createSms(smsVO);
		        	}
	        		
	        		result.setSucMsg("SMS 메시지 보내기가 성공하였습니다.");
					
				}     
        	}
        	
        	if(rowTotCntMms > 0){
	        	//MMS 인경우
	        	for(int i=0; i<createReceiversMms.size(); i++){
		        	smsVO.setUserId(userId);
		        	if(smsVO.getScheduleType().equals("NOW")){
		        		smsVO.setScheduleType(smsScheduletypeNew);  
		        		smsVO.setSendDate(DateUtils.getSysDate("yyyyMMddHHmmss"));
		        	}else if(smsVO.getScheduleType().equals("RESERVE")){	        		
		        		smsVO.setScheduleType(smsScheduletypeReserve);
		        		reserveDate = reserveDate.concat(smsVO.getReserveYear());
		        		reserveDate = reserveDate.concat(smsVO.getReserveMonth());
		        		reserveDate = reserveDate.concat(smsVO.getReserveDay());
		        		reserveDate = reserveDate.concat(smsVO.getReserveHour());
		        		reserveDate = reserveDate.concat(smsVO.getReserveMin()); 
		        		reserveDate = reserveDate.concat("00");
		        		smsVO.setSendDate(reserveDate);
		        		
		        	}else if(smsVO.getScheduleType().equals("REPEAT")){
		        		smsVO.setScheduleType(smsScheduletypeReserve);
		        		repeatDate.concat(smsVO.getRepeatHour());
		        		repeatDate.concat(smsVO.getRepeatMin());	        		
		        		smsVO.setSendDate(DateUtils.getSysDate("yyyyMMddHHmmss"));
		        	}
		        	smsVO.setDestType(smsDesttypeText);
		        	smsVO.setSendStatus(smsSendstatus);
		        	smsVO.setSendCount(smsSendcount); 
		        	smsVO.setSendResult(smsSendresult); 
		        	Map imap = (HashMap)createReceiversMms.get(i);
		        	smsVO.setDestCount(imap.get("rowSubCnt").toString());	   
		        	
		        	String destInfo = imap.get("receiverInfoTemp").toString();
		        	destInfo = StringUtil.replaceString(destInfo).trim();
		        	smsVO.setDestInfo(destInfo);
		        	smsVO.setSmsMsg(oriMsg);
		        	logger.debug(this.getClass() + " : toString  = "+smsVO.toString());
	        		smsVO.setNowDate(DateUtils.getSysDate("yyyyMMddHHmmss"));
	        		umsService.createLms(smsVO);
	        		result.setSucMsg("LMS 메시지 보내기가 성공하였습니다.");
				}       
        	}
        } catch (UmsSendProcException e) {
            logger.error("create sms data validation check fail :: " + e.getMessage());
            result.setResult(false);
            result.setErrMsg(e.getMessage());
        } catch (Exception e) {
        	
            logger.error(e.toString());
            result.setResult(false);
            //result.setErrMsg(e.getMessage());
            result.setErrMsg("System error :: ums content info create fail.");
	    }
        return result;
    }
	
}
