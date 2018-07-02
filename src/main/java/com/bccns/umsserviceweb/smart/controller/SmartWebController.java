package com.bccns.umsserviceweb.smart.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bccns.umsserviceweb.common.controller.DefaultAPIController;
import com.bccns.umsserviceweb.common.service.CommonService;
import com.bccns.umsserviceweb.common.util.DateUtils;
import com.bccns.umsserviceweb.common.util.StringUtil;
import com.bccns.umsserviceweb.common.util.UmsServiceWebSession;
import com.bccns.umsserviceweb.common.vo.CommCodeVO;
import com.bccns.umsserviceweb.common.vo.JsonVO;
import com.bccns.umsserviceweb.common.vo.UmsUserVO;
import com.bccns.umsserviceweb.common.vo.UsrGrpVO;
import com.bccns.umsserviceweb.mgr.service.AddrService;
import com.bccns.umsserviceweb.mgr.service.AttachFileService;
import com.bccns.umsserviceweb.mgr.service.GrpService;
import com.bccns.umsserviceweb.mgr.service.MsgBoxService;
import com.bccns.umsserviceweb.mgr.vo.AttachFileVO;
import com.bccns.umsserviceweb.mgr.vo.GrpVO;
import com.bccns.umsserviceweb.mgr.vo.MsgBoxVO;
import com.bccns.umsserviceweb.mgr.vo.SearchGrpVO;
import com.bccns.umsserviceweb.mgr.vo.SearchMsgBoxVO;
import com.bccns.umsserviceweb.notice.service.NoticeService;
import com.bccns.umsserviceweb.smart.service.SmartService;
import com.bccns.umsserviceweb.smart.vo.SmartModListVO;
import com.bccns.umsserviceweb.smart.service.SmartService;
import com.bccns.umsserviceweb.smart.vo.SmartDmRightVO;
import com.bccns.umsserviceweb.smart.vo.SmartRegVO;
import com.bccns.umsserviceweb.ums.exception.UmsSendProcException;
import com.bccns.umsserviceweb.ums.service.UmsService;
import com.bccns.umsserviceweb.ums.vo.SmsVO;

@Controller
@RequestMapping(value = "/smart")
public class SmartWebController extends DefaultAPIController {
	private static final Logger logger = LoggerFactory.getLogger(SmartWebController.class);
	
    
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
	@Value("${property.file.view.file}") 			String fileViewFile1;
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
	
	@Autowired
	SmartService smartService;
	

	@Value("${property.pagination.recordcountperpage}")
    private int recordCountPerPage;
    
    @Value("${property.pagination.pagesize}")
    private int pageSize;
    
    private String userId;
	
    /**
     * 스마트DM VIEW
     * @param smsVO
     * @param searchMsgBoxVO
     * @param model
     * @param request
     * @return
     */
	@RequestMapping(value = "/smartDm", method = { RequestMethod.GET, RequestMethod.POST })
    public String getSmartDmMain(@ModelAttribute("smsVO") SmsVO smsVO
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
	        	usrGrpVO.setGrpCd(codeGrpcdsdm);
	        	List<UsrGrpVO> usrGrpList = null;
	            try{
	            	if(usrGrpVO.getGrpCd().equals(codeGrpcdsdm) && !umsUserVO.getUserLv().equals("99")){
	            		usrGrpList = commonService.getUsrGrpDmList(usrGrpVO);
	            		
	            	}else{
	            		usrGrpList = commonService.getUsrGrpList(usrGrpVO);
	            	}
	            	
	            }catch(Exception e){
	                logger.error("getMsgBoxList failure = "+e.getMessage());
	            }
	            
	        	
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
        	searchMsgBoxVO.setGrpCd(codeGrpcdsdm);
        	searchMsgBoxVO.setUserId(userId);
        	
        	model.addAttribute("searchMsgBoxVO", searchMsgBoxVO);
            
        }catch (Exception e ) {
            logger.error(e.getMessage());
        }
        
        logger.debug("----------------end main");
                

        return "/smart/smartDm";
    }
	
	/**
	 * 스마트 DM 간편팝업
	 * @param smsVO
	 * @param searchMsgBoxVO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/smartDmPopUp", method = { RequestMethod.GET, RequestMethod.POST })
    public String getSmartDmPop(@ModelAttribute("smsVO") SmsVO smsVO
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
	        	usrGrpVO.setGrpCd(codeGrpcdsdm);
	        	
	        	List<UsrGrpVO> usrGrpList = null;
	            try{
	            	if(usrGrpVO.getGrpCd().equals(codeGrpcdsdm) && !umsUserVO.getUserLv().equals("99")){
	            		usrGrpList = commonService.getUsrGrpDmList(usrGrpVO);
	            		
	            	}else{
	            		usrGrpList = commonService.getUsrGrpList(usrGrpVO);
	            	}
	            	
	            }catch(Exception e){
	                logger.error("getMsgBoxList failure = "+e.getMessage());
	            }
	            
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
        	searchMsgBoxVO.setGrpCd(codeGrpcdsdm);
        	searchMsgBoxVO.setUserId(userId);
        	
        	model.addAttribute("searchMsgBoxVO", searchMsgBoxVO);
            
        }catch (Exception e ) {
            logger.error(e.getMessage());
        }
        
        logger.debug("----------------end main");
                

        return "/smart/smartDmPop";
    }
	
	/**
	 * 스마트 DB 발송
	 * @param smsVO
	 * @param receiverInfo
	 * @param model
	 * @param request
	 * @return
	 * 
	 * process : 송신시 키생성 -> 건별DM발송 -> 발송페이지오픈시 확인event -> 페이지이미지클릭시 -> 응답event
	 * smartContSendId = grpCd + grpNo + msgId + 일시 + phoneNo
	 * https://airbusan2.sendall.co.kr:444/SD?SN=0;175;2014102910420001067323629
	 * reserved1 SMART DM/FAX구분
	 * reserved2 SMART 식별키
	 * reserved3 SMART 응답상태 = 0:송신 , 1:확인, 2:응답
	 * reserved4 확인일시
	 * reserved5 응답일시
	 */
	@RequestMapping(value="/sendSmartDmProc", method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody JsonVO doSendSmartDmProcAjax( @ModelAttribute(value="smsVO")  SmsVO smsVO
    		, @RequestParam("receiverInfo") String[] receiverInfo
    		, ModelMap model , HttpServletRequest request) {
    	
        JsonVO result = new JsonVO();
        List<Map<String,Object>> createReceivers1 = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = new HashMap<String,Object>();
    	String receiverInfoTemp = "";
    	String reserveDate = "";
    	String repeatDate = "";
    	String msgGrpNo = "";
    	String nowDate = "";
    	String tmpSmsMsg = "";
    	String subject = "";
    	int rowCnt = 1;
    	int rowSubCnt = 0;
    	int rowAddCnt = 0;
    	userId = UmsServiceWebSession.getSessionUserId(request);
    	msgGrpNo = request.getParameter("MSGGRPNO"); 
    	
        try {
        	logger.debug("sendProc info = " + smsVO.toString());
        	
        	nowDate = DateUtils.getSysDate("yyyyMMddHHmmss"); 
        	
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
        		searchGrpVO.setGrpCd(codeGrpcdsdm);
        		searchGrpVO.setGrpNo("0");//defalut group
        		grpVO.setInstDt(nowDate);
        		grpService.createDefaultGrp(grpVO, searchGrpVO);
        		
        		//save msg create
	        	MsgBoxVO msgBoxVO =  new MsgBoxVO();
	        	msgBoxVO.setUserId(userId);
	        	msgBoxVO.setGrpCd(codeGrpcdsdm);
	        	
	        	if(msgGrpNo.equals("") || msgGrpNo == null)
	        		msgBoxVO.setGrpNo("0");//defalut group No
	        	else
	        		msgBoxVO.setGrpNo(msgGrpNo);//msg group No
	        	
	        	msgBoxVO.setSubject(smsVO.getSubject());
	        	msgBoxVO.setContents1(smsVO.getSmsMsg());
	        	umsService.createmsgBox(msgBoxVO);
        	}
        	
        	//https://localhost/smartFaxWeb?smartContId=SMARTFAX;hd01;01;02;08;0;157 + ;date(yyyymmddhhmmss)
        	String smartContTemp[] = smsVO.getSmsMsg().split("=");
        	if(smartContTemp.length > 1){
        		smsVO.setReserved2(smartContTemp[1].trim().concat(";").concat(nowDate));
        	}
        	
        	//common input attribute
        	smsVO.setUserId(userId);
        	
        	if(smsVO.getScheduleType().equals("NOW")){
        		smsVO.setScheduleType(smsScheduletypeNew);  
        		smsVO.setSendDate(nowDate);
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
        		smsVO.setSendDate(nowDate);
        	}
        	smsVO.setDestType(smsDesttypeText);
        	smsVO.setSendStatus(smsSendstatus);
        	smsVO.setSendCount(smsSendcount); 
        	smsVO.setSendResult(smsSendresult);
        	
        	smsVO.setReserved1("SMARTDM");
        	tmpSmsMsg = smsVO.getSmsMsg().trim();
        	
        	//메시지 응답받기 모드가 아닐경우 통합송신모드( 최대100명 단위 ROW 1건)
        	if(request.getParameter("MSGRES").equals("N")){
        		
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
	        	
	        	
	        	for(int i=0; i<createReceivers1.size(); i++){
	        		
		        	Map imap = (HashMap)createReceivers1.get(i);
		        	//smsVO.setDestCount(String.valueOf(receiverInfo.length));
		        	smsVO.setDestCount(imap.get("rowSubCnt").toString());	       
		        	smsVO.setDestInfo(imap.get("receiverInfoTemp").toString());	 
		        	
		        	logger.debug("sendKey",smsVO.getSmsMsg());
		        	logger.debug(this.getClass() + " : toString  = "+smsVO.toString());
		        	if(request.getParameter("MSGTYPE").equals("SMS")){
		            	smsVO.setSmsMsg(tmpSmsMsg.trim().concat(";").concat(nowDate));
		        		umsService.createSms(smsVO);
		        		result.setSucMsg("SmartDM SMS 메시지 보내기가 성공하였습니다.");
		        	}else if(request.getParameter("MSGTYPE").equals("MMS")){
		            	smsVO.setSmsMsg(tmpSmsMsg.trim().concat(";").concat(nowDate));
		        		smsVO.setNowDate(nowDate);
		        		umsService.createLms(smsVO);
		        		result.setSucMsg("SmartDM LMS 메시지 보내기가 성공하였습니다.");
		        	}
					
				}        	
        	}
        	//메시지 응답받기 모드는 건별 송신 (100명 송신시 100건Row Insert)
        	else if(request.getParameter("MSGRES").equals("Y")){
        		for(int i=0; i<receiverInfo.length; i++){

        			logger.debug("receiverInfo = ",receiverInfo[i].toString());
        			int iP = receiverInfo[i].toString().indexOf("^");
        			String sTemp = receiverInfo[i].toString().substring(iP+1).replace("|", "").trim();
        			
					smsVO.setDestCount("1");	       
		        	smsVO.setDestInfo(receiverInfo[i].toString());	 
		        	
		        	smsVO.setSmsMsg(tmpSmsMsg.trim().concat(";").concat(nowDate).concat(sTemp));
	            	smsVO.setReserved2(smartContTemp[1].trim().concat(";").concat(nowDate).concat(sTemp));
	            	smsVO.setReserved3("0");//reserved3 0:송신 , 1:확인, 2:응답
	            	
		        	logger.debug(this.getClass() + " : toString  = "+smsVO.toString());
		        	if(request.getParameter("MSGTYPE").equals("SMS")){
		        		umsService.createSms(smsVO);
		        		result.setSucMsg("SmartDM SMS 메시지 보내기가 성공하였습니다.");
		        	}else if(request.getParameter("MSGTYPE").equals("MMS")){
		        		smsVO.setNowDate(nowDate);
		        		umsService.createLms(smsVO);
		        		result.setSucMsg("SmartDM LMS 메시지 보내기가 성공하였습니다.");
		        	}
				}
        		
        	}
        } catch (UmsSendProcException e) {
        	e.printStackTrace();
            logger.error("create sms data validation check fail :: " + e.getMessage());
            result.setResult(false);
            result.setErrMsg(e.getMessage());
        } catch (Exception e) {
			e.printStackTrace();
            logger.error(e.getMessage());
            result.setResult(false);
            //result.setErrMsg(e.getMessage());
            result.setErrMsg("System error :: ums content info create fail.");
        }
        return result;
    }
	
	/**
	 * 스마트 팩스 발송
	 * @param smsVO
	 * @param receiverInfo
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/sendSmartFaxProc", method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody JsonVO doSendSmartFaxProcAjax( @ModelAttribute(value="smsVO")  SmsVO smsVO
    		, @RequestParam("receiverInfo") String[] receiverInfo
    		, ModelMap model , HttpServletRequest request) {
    	
        JsonVO result = new JsonVO();
        List<Map<String,Object>> createReceivers1 = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = new HashMap<String,Object>();
    	String receiverInfoTemp = "";
    	String reserveDate = "";
    	String repeatDate = "";
    	String msgGrpNo = "";
    	int rowCnt = 1;
    	int rowSubCnt = 0;
    	int rowAddCnt = 0;
    	userId = UmsServiceWebSession.getSessionUserId(request);
    	msgGrpNo = request.getParameter("MSGGRPNO"); 
    	
        try {
        	logger.debug("sendProc info = " + smsVO.toString());
        	//메시지저장 처리
        	if( request.getParameter("MSGSAVE").equals("Y")){
        		
        		//default group create
        		GrpVO grpVO =  new GrpVO();
        		SearchGrpVO searchGrpVO =  new SearchGrpVO();
        		searchGrpVO.setUserId(userId);
        		searchGrpVO.setGrpCd(codeGrpcdsfx);
        		searchGrpVO.setGrpNo("0");//defalut group
        		grpVO.setInstDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
        		grpService.createDefaultGrp(grpVO, searchGrpVO);
        		
        		//save msg create
	        	MsgBoxVO msgBoxVO =  new MsgBoxVO();
	        	msgBoxVO.setUserId(userId);
	        	msgBoxVO.setGrpCd(codeGrpcdsfx);
	        	
	        	if(msgGrpNo.equals("") || msgGrpNo == null)
	        		msgBoxVO.setGrpNo("0");//defalut group No
	        	else
	        		msgBoxVO.setGrpNo(msgGrpNo);//msg group No
	        	
	        	msgBoxVO.setSubject(smsVO.getSubject());
	        	msgBoxVO.setContents1(smsVO.getSmsMsg());
	        	umsService.createmsgBox(msgBoxVO);
        	}
        	
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
					rowCnt++;
					rowAddCnt = 0;
				} 
			}
    		map = new HashMap();
    		map.put("receiverInfoTemp", receiverInfoTemp);
			map.put("rowSubCnt", rowAddCnt);
			createReceivers1.add(map);
        	logger.debug("rowCnt= " + rowCnt);
        	for(int i=0; i<createReceivers1.size(); i++){
	        	
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
	        		logger.debug("reserveDate= " + reserveDate.toString());
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
	        	//smsVO.setDestCount(String.valueOf(receiverInfo.length));
	        	smsVO.setDestCount(imap.get("rowSubCnt").toString());	       
	        	smsVO.setDestInfo(imap.get("receiverInfoTemp").toString());	 
	        	
	        	logger.debug(this.getClass() + " : toString  = "+smsVO.toString());
	        	if(request.getParameter("MSGTYPE").equals("SMS")){
	        		smsVO.setReserved1("SMARTFAX");
	        		umsService.createSms(smsVO);
	        		result.setSucMsg("SMARTFAX SMS 메시지 보내기가 성공하였습니다.");
	        	}else if(request.getParameter("MSGTYPE").equals("MMS")){
	        		smsVO.setNowDate(DateUtils.getSysDate("yyyyMMddHHmmss"));
	        		smsVO.setReserved1("SMARTFAX");
	        		umsService.createLms(smsVO);
	        		result.setSucMsg("SMARTFAX LMS 메시지 보내기가 성공하였습니다.");
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
	 * 스마트 팩스VIEW
	 * @param smsVO
	 * @param searchMsgBoxVO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/smartFax", method = { RequestMethod.GET, RequestMethod.POST })
    public String getSmartFaxMain(@ModelAttribute("smsVO") SmsVO smsVO
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
	        	usrGrpVO.setGrpCd(codeGrpcdsfx);
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
        	searchMsgBoxVO.setGrpCd(codeGrpcdsfx);
        	searchMsgBoxVO.setUserId(userId);
        	
        	model.addAttribute("searchMsgBoxVO", searchMsgBoxVO);
            
        }catch (Exception e ) {
            logger.error(e.getMessage());
        }
        
        logger.debug("----------------end main");
                

        return "/smart/smartFax";
    }
	
	
	/**
	 * 스마트 DM/FAX등록
	 * @param smsVO
	 * @param searchMsgBoxVO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/smartReg", method = { RequestMethod.GET, RequestMethod.POST })
    public String getSmartRegMain(@ModelAttribute("smsVO") SmsVO smsVO
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
	        	usrGrpVO.setGrpCd(codeGrpcdsdm);

	        	List<UsrGrpVO> usrGrpList = commonService.getUsrGrpList(usrGrpVO);
	        	
	        	model.addAttribute("usrGrpList",usrGrpList);
	        	
	        	model.addAttribute("usrCallBackList",umsService.getUsrCallBackList(umsUserVO));
	        	
	        	List<CommCodeVO> deptCdList = new ArrayList<CommCodeVO>();
	        	CommCodeVO commCodeVO = new CommCodeVO();
	        	commCodeVO.setCodeGroup("DEPT_CD");
	            try{
	            	deptCdList = commonService.getCodeList(commCodeVO);
	            }catch(Exception e){
	            	logger.debug("getCodeList failure = "+e.getMessage());
	            }
	            model.addAttribute("deptCdList",deptCdList);
	            
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
//            model.addAttribute("msgBoxList",msgBoxList);
        }catch (Exception e ) {
            logger.error(e.getMessage());
        }
        
        logger.debug("----------------end main");
                

        return "/smart/smartReg";
    }
	
	/**
	 * 스마트 DM/FAX CONTENTS 등록 처리	
	 * @param smartRegVO
	 * @param model
	 * @param request
	 * @return
	 * @throws UmsSendProcException
	 * https://airbusan2.sendall.co.kr:444/SD?SN=0;175;2014102910420001067323629
	 * smartContId = grpCd + grpNo + msgId
	 * 
	 */
	@RequestMapping(value="/sendSmartRegProc", method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody JsonVO doSendSmartRegProcAjax( @ModelAttribute(value="smartRegVO")  SmartRegVO smartRegVO
    		, ModelMap model , HttpServletRequest request) throws UmsSendProcException {
        
        logger.debug("SmartRegVO info = " + smartRegVO.toString());
        String msgGrpNo = "";
        MsgBoxVO msgBoxVO =  new MsgBoxVO();
        UmsUserVO umsUserVO = new UmsUserVO();
    	String fileServernameTemp = "";
    	String fileNameTemp = "";
    	String fileTypeTemp = "";
    	String fileSizeTemp = "";
    	String filePathTemp = "";
    	String fileContenttypeTemp = "";
    	String requesturl = "";
    	String serverPort = "";
    	String smartContId = "";
        JsonVO result = new JsonVO();
        requesturl = (String)request.getServerName();
        serverPort = String.valueOf(request.getServerPort());
        userId = UmsServiceWebSession.getSessionUserId(request);
        logger.debug("serverPort info = " + serverPort);
        
    	msgGrpNo = request.getParameter("MSGGRPNO");        	
    	smartRegVO.setFile_servername(request.getParameterValues("file_servername"));
    	smartRegVO.setFile_type(request.getParameterValues("file_type"));
    	smartRegVO.setFile_size(request.getParameterValues("file_size"));
    	smartRegVO.setFile_path(request.getParameterValues("file_path"));
    	String[] tempFileName = smartRegVO.getFile_name();
    	String[] tempFileType = smartRegVO.getFile_type();
    	String[] tempFileSize = smartRegVO.getFile_size();
    	String[] tempFilePath = smartRegVO.getFile_path();
    	String[] tempFileContenttype = smartRegVO.getFile_contenttype();
    	
    	String[] tempRightDept = smartRegVO.getRightDept();
    	String[] tempRightSdt = smartRegVO.getRightSdt();
    	String[] tempRightEdt = smartRegVO.getRightEdt();
    	
    	Integer cntFile = 0; 
    	Integer cntRight = 0; 
    	
    	
     	try{
        	umsUserVO.setUserId(userId);
        	UmsUserVO userVO = commonService.getUserInfoD(umsUserVO);
	    	for(String temp : smartRegVO.getFile_servername()  ) {
	    		fileServernameTemp = fileServernameTemp.concat(temp);
	    		fileServernameTemp = fileServernameTemp.concat(";");
	    		
	    		fileNameTemp = fileNameTemp.concat(tempFileName[cntFile]);
	    		fileNameTemp = fileNameTemp.concat(";");
	    		
	    		fileTypeTemp = fileTypeTemp.concat(tempFileType[cntFile]);
	    		fileTypeTemp = fileTypeTemp.concat(";");
	    		
	    		fileSizeTemp = fileSizeTemp.concat(tempFileSize[cntFile]);
	    		fileSizeTemp = fileSizeTemp.concat(";");
	    		
	    		filePathTemp = filePathTemp.concat(tempFilePath[cntFile]);
	    		filePathTemp = filePathTemp.concat(";");
	    		
	    		fileContenttypeTemp = fileContenttypeTemp.concat(tempFileContenttype[cntFile]);
	    		fileContenttypeTemp = fileContenttypeTemp.concat(";");
	        	
	        	cntFile++;
			}
	    	
	    	if( request.getParameter("MSGSAVE").equals("Y")){ 
	    		
	    		//default group create
	    		GrpVO grpVO =  new GrpVO();
	    		SearchGrpVO searchGrpVO =  new SearchGrpVO();
	    		searchGrpVO.setUserId(userId);
	    		if(smartRegVO.getMSGTYPE().equals("SMARTDM"))
	    			searchGrpVO.setGrpCd(codeGrpcdsdm);
    			else 
    				searchGrpVO.setGrpCd(codeGrpcdsfx);
	    		
	    		searchGrpVO.setGrpNo("0");//defalut group
	    		grpVO.setInstDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
	    		Integer dGNo = grpService.createDefaultGrp(grpVO, searchGrpVO);
	    		
	    		//save msg create
	        	
	    		msgBoxVO.setUserId(userId);
	    		
	        	if(msgGrpNo.equals("") || msgGrpNo == null)
	        		msgBoxVO.setGrpNo("0");//defalut group No
	        	else
	        		msgBoxVO.setGrpNo(msgGrpNo);//msg group No
	        	
	        	if(smartRegVO.getMSGTYPE().equals("SMARTDM")){
	    			msgBoxVO.setGrpCd(codeGrpcdsdm);
	    			msgBoxVO.setContents7(fileViewFile);
	    			requesturl = "https://"+requesturl + ":" + serverPort +"/SD?SN=";
	    		}else if(smartRegVO.getMSGTYPE().equals("SMARTFAX")){
	    			msgBoxVO.setGrpCd(codeGrpcdsfx);
	    			msgBoxVO.setContents7(fileViewFile1);
	    			requesturl = "https://"+requesturl + ":" + serverPort +"/SF?SN=";
	    		}
	        	
	        	msgBoxVO.setSubject(smartRegVO.getSubject());
	        	msgBoxVO.setContents1(requesturl);
	        	
	        	msgBoxVO.setContents2(fileServernameTemp);//
	        	msgBoxVO.setContents3(fileNameTemp);
	        	msgBoxVO.setContents4(fileSizeTemp);
	        	msgBoxVO.setContents5(filePathTemp);
	        	msgBoxVO.setContents6(fileTypeTemp);
	        	
	        	msgBoxVO.setContents8(fileContenttypeTemp);
	        	msgBoxVO.setContents9(smartRegVO.getImageselect());
	        	smartRegVO.setImagelink(smartRegVO.getImagelink().replace("http://", "")); 
	        	msgBoxVO.setContents10("http://" + smartRegVO.getImagelink());
	        	umsService.createmsgBox(msgBoxVO);
	        	result.setSucMsg("SMART DM/FAX 메시지 등록이 성공하였습니다.");
	    	}
	    	logger.debug("smartRegVO info = " + smartRegVO.toString());
	    	
	    	//update만들기
	    	
	    	//smartContId = smartContId +";"+ msgBoxVO.getGrpCd() +";"+ msgBoxVO.getGrpNo() +";"+ msgBoxVO.getMsgNo();
	    	smartContId = msgBoxVO.getGrpNo() +";"+ msgBoxVO.getMsgNo();
	    	requesturl = requesturl.concat(smartContId);
	    	msgBoxVO.setContents1(requesturl);
	    	logger.debug("requesturl info = " + msgBoxVO.toString());
	    	msgBoxService.modifyMsgBox(msgBoxVO);
	    	
	    	cntFile = 0;
			for(String temp : smartRegVO.getFile_servername()  ) {
				//파일
	            AttachFileVO attachFileVO = new AttachFileVO();
	            attachFileVO.setUserId(userId);
	            attachFileVO.setGrpCd(codeGrpcdmms);
	            attachFileVO.setFileType(tempFileType[cntFile]);
	            attachFileVO.setFileSize(tempFileSize[cntFile]);
	            attachFileVO.setFileDir(tempFilePath[cntFile]);
	            attachFileVO.setFileNm(temp);
	            	            
	            attachFileVO.setInstDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
	            attachFileVO.setInstId(userId);
	            
	            attachFileVO.setMsgNo(msgBoxVO.getMsgNo());
	            attachFileVO.setGrpNo(msgBoxVO.getGrpNo());
	        	attachFileService.createAttachFile(attachFileVO);
	        	
	        	cntFile++;
	        	
	        	logger.debug("attachFileVO info = " + attachFileVO.toString());
			}
			
			//권한 저장
			cntRight = 0;
			for(String temp : tempRightDept ) {
				
				SmartDmRightVO smartDmRightVO = new SmartDmRightVO();
				smartDmRightVO.setGrpCd(codeGrpcdsdm);
				smartDmRightVO.setGrpNo(msgBoxVO.getGrpNo());
				smartDmRightVO.setMsgNo(msgBoxVO.getMsgNo());
				smartDmRightVO.setCompany(userVO.getCompany());
				smartDmRightVO.setDept(tempRightDept[cntRight]);
				
				String regRightStartDate = "";
				if(tempRightSdt[cntRight] != null && tempRightSdt[cntRight] != "") {
					regRightStartDate = tempRightSdt[cntRight].replaceAll("/", "");
					smartDmRightVO.setRtStrtDt(regRightStartDate);
				}
				String regRightEndDate = "";
				if(tempRightEdt[cntRight] != null && tempRightEdt[cntRight] != "") {
					regRightEndDate = tempRightEdt[cntRight].replaceAll("/", "");
					smartDmRightVO.setRtEndDt(regRightEndDate);
				}
				
				smartDmRightVO.setInstId(userId);
				
				smartService.createSmartDmRight(smartDmRightVO);
				
				cntRight++;
				logger.debug("smartDmRightVO info = " + smartDmRightVO.toString());
				
			}
			
        } catch (Exception e) {
            logger.error("System error :: ums content info create fail." + e.getMessage());
            result.setResult(false);
            result.setErrMsg("System error :: ums content info create fail."+ e.getMessage());
        }
        return result;
    }
	
	/**
	 * 스마트 DM 권한 등록조회
	 * @param searchMsgBoxVO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/smartRightModList", method = { RequestMethod.GET, RequestMethod.POST })
    public String getSmartRightModMain(@ModelAttribute("searchMsgBoxVO") SearchMsgBoxVO searchMsgBoxVO
    	    , ModelMap model, HttpServletRequest request) {
  
		logger.debug("-------------start main");
		logger.debug("searchMsgBoxVO = "+searchMsgBoxVO.toString());
		
		userId = UmsServiceWebSession.getSessionUserId(request);        
    	
    	model.put("searchMsgBoxVO", searchMsgBoxVO);
    	
	    searchMsgBoxVO.setUserId(userId);
	    searchMsgBoxVO.setGrpCd("07");
    	
        // 한 페이지에 게시되는 게시물 건수
    	searchMsgBoxVO.setRecordCountPerPage(this.recordCountPerPage);
        // 페이징 리스트의 사이즈
    	searchMsgBoxVO.setPageSize(this.pageSize);
        
        model.put("firstIndex",         searchMsgBoxVO.getFirstRecordIndex());
        model.put("lastRecordIndex",    searchMsgBoxVO.getLastRecordIndex());
        model.put("recordCountPerPage", searchMsgBoxVO.getRecordCountPerPage());
        
        //searchMsgBoxVO.setSubject(request.getParameter("subject"));
        
        int totCount = 0;
        List<SmartModListVO> smartRightModList = null;
        
        UmsUserVO userInfo = new UmsUserVO();
        userInfo.setUserId(userId);
        userInfo = commonService.getUserInfoD(userInfo);
        int userLv = Integer.valueOf(userInfo.getUserLv());
        searchMsgBoxVO.setCompany(userInfo.getCompany());
        
        // 사용자 레벨이 40 이상일 경우에만 조회
        if(userLv  >= 40){
        	try{
            	totCount = smartService.getSmartDmModListCount(searchMsgBoxVO);
            }catch(Exception e){
            	e.printStackTrace();
                logger.error("getMsgBoxListCount failure = "+e.getMessage());
            }
            
            // 전체 게시물 건 수
            searchMsgBoxVO.setTotalRecordCount(totCount);
            
            try{
            	smartRightModList = smartService.getSmartDmModList(searchMsgBoxVO);
            }catch(Exception e){
            	e.printStackTrace();
                logger.error("getMsgBoxList failure = "+e.getMessage());
            }
        }
        
        //그룹콤보
        UsrGrpVO usrGrpVO =  new UsrGrpVO(); 
    	
    	usrGrpVO.setUserId(userId);
    	usrGrpVO.setGrpCd(searchMsgBoxVO.getGrpCd());
    	//logger.debug("session usrGrpVO == ",usrGrpVO.toString());

    	List<UsrGrpVO> usrGrpList = commonService.getUsrGrpList(usrGrpVO);
    	
    	logger.debug("usrGrpList = "+ usrGrpList.toString() );
    	
        model.put("usrGrpList",usrGrpList);
        model.put("smartRightModList", smartRightModList);
        model.put("searchMsgBoxVO", searchMsgBoxVO);
        
        logger.debug("----------------end main");

        return "/smart/smartRightModList";
    }
	
	/**
	 * 스마트 DM 권한 수정 팝업
	 * @param smartModListVO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/smartRightModifyPopUp", method = { RequestMethod.GET, RequestMethod.POST })
    public String doSmartRightModifyPop(@ModelAttribute("smartModListVO") SmartModListVO smartModListVO,
    		ModelMap model, HttpServletRequest request) {
        
		logger.debug("smartModListVO = "+smartModListVO.toString());
		
        try {
        	userId = UmsServiceWebSession.getSessionUserId(request);
        	
        	List<CommCodeVO> deptList = new ArrayList<CommCodeVO>();
        	CommCodeVO commCodeVO = new CommCodeVO();
        	commCodeVO.setCodeGroup("DEPT_CD");
            try{
            	deptList = commonService.getCodeList(commCodeVO);
            }catch(Exception e){
            	logger.debug("getCodeList failure = "+e.getMessage());
            }
        	
            model.put("deptList",deptList);
                        
        	SmartModListVO result = smartService.getSmartDmModOne(smartModListVO);
        	
        	model.put("result", result);
            
        }catch (Exception e ) {
        	e.printStackTrace();
            logger.error(e.getMessage());
        }
        
        return "/smart/smartDmRightModifyPop";
    }
	
	/**
	 * 스마트 DM 권한 등록 처리
	 * @param smartModListVO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/modifySmartDmRightProc", method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody JsonVO doModifySmartDmRightProcAjax(@ModelAttribute("smartModListVO") SmartModListVO smartModListVO,
    		ModelMap model , HttpServletRequest request) {
		
		JsonVO result = new JsonVO();
		int totCount = 0;
		logger.debug("smartModListVO=" + smartModListVO.toString());
		
		smartModListVO.setRtStrtDt(smartModListVO.getRtStrtDt().replaceAll("/", ""));
		smartModListVO.setRtEndDt(smartModListVO.getRtEndDt().replaceAll("/", ""));
		
		try{
			userId = UmsServiceWebSession.getSessionUserId(request);
			
			smartModListVO.setUserId(userId);
			//수정할 데이터가 있는지 체크
			totCount = smartService.getSmartDmModOneCount(smartModListVO);
			
			if(totCount > 0){
				//수정일경우			
				if(smartService.modifySmartDmDept(smartModListVO) != 1){
					result.setResult(false);
					result.setErrMsg("DB 에러");
				} else {
					result.setResult(true);
					result.setSucMsg("수정 되었습니다.");
				}	
			}else if(totCount == 0){
				//입력일경우
				SmartDmRightVO smartDmRightVO = new SmartDmRightVO();
				smartDmRightVO.setGrpCd(codeGrpcdsdm);
				smartDmRightVO.setGrpNo(smartModListVO.getGrpNo());
				smartDmRightVO.setMsgNo(smartModListVO.getMsgNo());
				smartDmRightVO.setCompany(smartModListVO.getCompany());
				smartDmRightVO.setDept(smartModListVO.getDept());
				
				String regRightStartDate = "";
				regRightStartDate = smartModListVO.getRtStrtDt().replaceAll("/", "");
				smartDmRightVO.setRtStrtDt(regRightStartDate);
				
				String regRightEndDate = "";
				regRightEndDate = smartModListVO.getRtEndDt().replaceAll("/", "");
				smartDmRightVO.setRtEndDt(regRightEndDate);
				
				smartDmRightVO.setInstId(userId);
				
				smartService.createSmartDmRight(smartDmRightVO);
				
				logger.debug("smartDmRightVO info = " + smartDmRightVO.toString());
				
				result.setResult(true);
				result.setSucMsg("신규 권한이 등록되었습니다.");
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return result;
    }
	
	
	/**
	 * 스마트 DM 권한 처리
	 * @param smartModListVO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/deleteSmartDmRightProc", method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody JsonVO doDeleteSmartDmRightProcAjax(@ModelAttribute("smartModListVO") SmartModListVO smartModListVO, 
    		ModelMap model , HttpServletRequest request) {
		
		JsonVO result = new JsonVO();
		
		logger.debug("smartModListVO = "+smartModListVO.toString());
		
		try{
			if(smartService.delSmartDmList(smartModListVO) <= 0){
				result.setResult(false);
				result.setErrMsg("DB 에러");
			} else {
				result.setResult(true);
				result.setSucMsg("삭제 되었습니다.");
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		
		return result;
    }
	
	/**
	 * 스마트 이미지 등록팝업 VIEW
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getRegImagePop")
	public String getRegImagePopupView(
	        ModelMap model) {
	    
	    return "/smart/regImagePop";
	}	
	
	/**
	 * 스마트 파일 등록팝업 VIEW
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getRegDocPop")
	public String getRegDocPopupView(
	        ModelMap model) {
	    
	    return "/smart/regDocPop";
	}
}
