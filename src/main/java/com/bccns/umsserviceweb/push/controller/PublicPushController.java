package com.bccns.umsserviceweb.push.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import com.bccns.umsserviceweb.mgr.vo.RsltDetBottomVO;
import com.bccns.umsserviceweb.mgr.vo.RsltDetMiddleVO;
import com.bccns.umsserviceweb.mgr.vo.RsltDetTopVO;
import com.bccns.umsserviceweb.mgr.vo.SearchGrpVO;
import com.bccns.umsserviceweb.mgr.vo.SearchMsgBoxVO;
import com.bccns.umsserviceweb.mgr.vo.SearchRsltDetVO;
import com.bccns.umsserviceweb.mgr.vo.SearchTransRsltVO;
import com.bccns.umsserviceweb.mgr.vo.SearchTransStatVO;
import com.bccns.umsserviceweb.mgr.vo.TransRsltVO;
import com.bccns.umsserviceweb.mgr.vo.TransStatVO;
import com.bccns.umsserviceweb.notice.service.NoticeService;
import com.bccns.umsserviceweb.push.service.PrivatePushService;
import com.bccns.umsserviceweb.push.service.PushService;
import com.bccns.umsserviceweb.push.util.PushUtil;
import com.bccns.umsserviceweb.push.vo.PmsSendVO;
import com.bccns.umsserviceweb.push.vo.PmsVO;
import com.bccns.umsserviceweb.ums.controller.MmsWebController;
import com.bccns.umsserviceweb.ums.exception.UmsSendProcException;
import com.bccns.umsserviceweb.ums.service.UmsService;
import com.bccns.umsserviceweb.push.vo.PmsVO;
import com.bccns.umsserviceweb.ums.vo.SmsVO;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

@Controller
@RequestMapping(value = "/push")
public class PublicPushController {
	
	private static final Logger logger = LoggerFactory.getLogger(PublicPushController.class);

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
	@Value("${property.code.grpcd.pms}") 			String codeGrpcdpms;

	
	@Value("${property.file.upload.file}") 			String fileUploadFile;
	@Value("${property.file.view.image}") 			String fileViewFile;
	@Value("${property.file.upload.image}") 		String fileUploadImage;
	
	
	@Autowired
	UmsService umsService;
	
	@Autowired
	PushService pushService;
	
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
    PrivatePushService privatePushService;
    
	
	@Value("${property.pagination.recordcountperpage}")
    private int recordCountPerPage;
    
    @Value("${property.pagination.pagesize}")
    private int pageSize;
    
    private String userId;
    
    @Value("${property.push.API_KEY}") String API_KEY;
	
	
    /**
     * MMS메인
     * @param smsVO
     * @param searchMsgBoxVO
     * @param model
     * @param request
     * @return
     */
	@RequestMapping(value = "/pms", method = { RequestMethod.GET, RequestMethod.POST })
    public String getPmsMain(@ModelAttribute("pmsVO") PmsVO pmsVO
    		, @ModelAttribute("searchMsgBoxVO") SearchMsgBoxVO searchMsgBoxVO
    	    , ModelMap model, HttpServletRequest request) {
        
        logger.debug("-------------start main");
        
        try {
        	
        	/**
        	 * 세션User정보 Get/Set  
        	 */
        	try{
	        	UmsUserVO umsUserVO = UmsServiceWebSession.getSessionUserVO(request);
	        	
	        	//model.addAttribute("userVO",umsUserVO);
	        	UsrGrpVO usrGrpVO =  new UsrGrpVO(); 
	        	
	        	userId = UmsServiceWebSession.getSessionUserId(request);
	        	usrGrpVO.setUserId(userId);
	        	usrGrpVO.setGrpCd(codeGrpcdpms);

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
        	searchMsgBoxVO.setGrpCd(codeGrpcdmms);
        	searchMsgBoxVO.setUserId(userId);
            
        	model.addAttribute("searchMsgBoxVO", searchMsgBoxVO);
            
        }catch (Exception e ) {
            logger.error(e.getMessage());
        }
        
        logger.debug("----------------end main");
                

        return "/push/pms";
    }
	
	 
	/**
	 * PMS 전송 처리
	 * 
	 * @param pmsVO
	 * @param receiverInfo
	 * @param model
	 * @param request
	 * @return
	 * @throws UmsSendProcException
	 */
	@RequestMapping(value="/sendPmsProc", method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody JsonVO doSendPmsProcAjax( @ModelAttribute(value="pmsVO")  PmsVO pmsVO
    		, @RequestParam("receiverInfo") String[] receiverInfo
    		, ModelMap model , HttpServletRequest request) throws UmsSendProcException {
		
        JsonVO result = new JsonVO();
        List<Map<String,Object>> createReceivers1 = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = new HashMap<String,Object>();
    	String receiverInfoTemp = "";
    	String reserveDate = "";
    	String repeatDate = "";
    	String msgGrpNo = "";
    	String subject = "";
    	
    	String pushResult = "";

    	int rowCnt = 1;
    	int rowAddCnt = 0;
    	int msgBoxISeq = 0;
    	userId = UmsServiceWebSession.getSessionUserId(request);
    	msgGrpNo = request.getParameter("MSGGRPNO"); 
    	MsgBoxVO msgBoxVO =  new MsgBoxVO();
        try {
        	logger.debug("sendProc info = " + pmsVO.toString());
            //타이틀
        	subject = pmsVO.getSubject();
        	//subject = StringUtil.getHanEngNumber(subject);
        	pmsVO.setSubject(subject);
        	
        	
        	if(msgGrpNo.equals("") || msgGrpNo == null)
        		msgBoxVO.setGrpNo("0");//defalut group No
        	else
        		msgBoxVO.setGrpNo(msgGrpNo);//msg group No
        	
        	if( request.getParameter("MSGSAVE").equals("Y")){ 
        		
        		//default group create
        		GrpVO grpVO =  new GrpVO();
        		SearchGrpVO searchGrpVO =  new SearchGrpVO();
        		searchGrpVO.setUserId(userId);
        		searchGrpVO.setGrpCd(codeGrpcdpms);
        		searchGrpVO.setGrpNo("0");//defalut group
        		grpVO.setInstDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
        		grpService.createDefaultGrp(grpVO, searchGrpVO);
        		
        		//save msg create
	        	
	        	msgBoxVO.setUserId(userId);
	        	msgBoxVO.setGrpCd(codeGrpcdmms); 
	        	msgBoxVO.setSubject(pmsVO.getSubject());
	        	msgBoxVO.setContents1(pmsVO.getMmsMsg());
	        	msgBoxVO.setContents2(request.getParameter("file_servername"));//이미지서버파일명
	        	msgBoxVO.setContents3(request.getParameter("file_name"));//이미지파일명
	        	msgBoxVO.setContents4(request.getParameter("file_size"));//이미지사이즈
	        	msgBoxVO.setContents5(request.getParameter("file_path"));//이미지경로명
	        	msgBoxVO.setContents6(request.getParameter("file_type"));//이미지타입명
	        	msgBoxVO.setContents7(fileViewFile);//이미지경로명
	        	msgBoxVO.setContents8(request.getParameter("file_contenttype"));//이미지경로명
	        	msgBoxISeq =umsService.createmsgBox(msgBoxVO);

        	}
        	logger.debug("receiverInfo info = " + receiverInfo.toString());
        	logger.debug("receiverInfo length = " + receiverInfo.length);
        	for(int i=0; i<receiverInfo.length; i++){
        		rowAddCnt ++;
        		logger.debug("rowSubCnt= " + i);
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
	        	
	        	pmsVO.setUserId(userId);
	        	
	        	if(pmsVO.getScheduleType().equals("NOW")){
	        		pmsVO.setScheduleType(smsScheduletypeNew);  
	        		pmsVO.setSendDate(DateUtils.getSysDate("yyyyMMddHHmmss"));
	        	}else if(pmsVO.getScheduleType().equals("RESERVE")){	        		
	        		pmsVO.setScheduleType(smsScheduletypeReserve);
	        		reserveDate = reserveDate.concat(pmsVO.getReserveYear());
	        		reserveDate = reserveDate.concat(pmsVO.getReserveMonth());
	        		reserveDate = reserveDate.concat(pmsVO.getReserveDay());
	        		reserveDate = reserveDate.concat(pmsVO.getReserveHour());
	        		reserveDate = reserveDate.concat(pmsVO.getReserveMin()); 
	        		reserveDate = reserveDate.concat("00");
	        		pmsVO.setSendDate(reserveDate);
	        		
	        	}else if(pmsVO.getScheduleType().equals("REPEAT")){
	        		pmsVO.setScheduleType(smsScheduletypeReserve);
	        		repeatDate.concat(pmsVO.getRepeatHour());
	        		repeatDate.concat(pmsVO.getRepeatMin());	        		
	        		pmsVO.setSendDate(DateUtils.getSysDate("yyyyMMddHHmmss"));
	        	}
	        	
	        	pmsVO.setSendStatus(smsSendstatus);
	        	pmsVO.setSendCount(smsSendcount); 
	        	pmsVO.setSendResult(smsSendresult);
	        	Map imap = (HashMap)createReceivers1.get(i);
	        	pmsVO.setDestCount(imap.get("rowSubCnt").toString());	
	        	
	        	String destInfo = imap.get("receiverInfoTemp").toString();
	        	destInfo = StringUtil.replaceString(destInfo);
	        	
	        	pmsVO.setDestInfo(destInfo);
	        	
        		pmsVO.setNowDate(DateUtils.getSysDate("yyyyMMddHHmmss"));
        		logger.debug("file_servername info = " + request.getParameter("file_servername"));
        		pmsVO.setContentCount("0");
	        	if(request.getParameter("file_servername") != null){
		        	pmsVO.setContentCount("1");
		        	pmsVO.setContentData(request.getParameter("file_servername")+"^1^0");
	        	}
	        	//pmsVO.setMsgType("0");//일반 TEXT : 0, HTML 지원: 1
	        	
	        	logger.debug(this.getClass() + " : toString  = "+pmsVO.toString());
	            //umsService.createMms(pmsVO);
	        	
	        	//destInfo
	        	
	        	PmsSendVO pmsSendVO = new PmsSendVO();
	        	PmsSendVO getPmsSendVO = new PmsSendVO();
	        	logger.debug("API_KEY : "+API_KEY);
	        	
	        	//ID 등록확인
	        	for(int k=0; k < receiverInfo.length ; k++){
	        	pmsSendVO.setUserId(userId);
	        	pmsSendVO.setApiKey(API_KEY);
	        	pmsSendVO.setMessage(pmsVO.getMmsMsg());
	        	pmsSendVO.setNum("2"); 
	        	pmsSendVO.setPhoneNo(StringUtil.getNumber(receiverInfo[k]));
	        	getPmsSendVO = pushService.getPushRegId(pmsSendVO);
	        	
	        	logger.debug("receiverInfo[k] : "+StringUtil.getNumber(receiverInfo[k]));
	        	if(getPmsSendVO == null){
	        		result.setResult(false);
	                result.setErrMsg("받는 사람의 정보가 없습니다.");
	                return result;
	        	}
	        	logger.debug("getPmsSendVO : "+getPmsSendVO.toString());

	        	//public 푸시 처리시
	        	pmsSendVO.setRegId(getPmsSendVO.getRegId());
	        	pmsSendVO.setSendDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
	        	pmsSendVO.setSender(pmsVO.getCallback());	        	
	        	pmsSendVO.setTitle(pmsVO.getSubject());
	        	if(request.getParameter("file_servername") != null){
		        	pmsSendVO.setImageUrl("https://ums.sendall.co.kr:444" + fileViewFile + request.getParameter("file_servername"));
		        	pmsSendVO.setImageYn("Y");
	        		pmsSendVO.setFileSize(request.getParameter("file_size"));
	        		pmsSendVO.setMessage(pmsSendVO.getImageUrl());
	        	}else{
	        		pmsSendVO.setImageYn("N");
	        	}
	        	
	        	if(pmsVO.getScheduleType().equals("NOW")){
	        		pmsSendVO.setReserveDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
	        	}else{
	        		pmsSendVO.setReserveDt(reserveDate);
	        	}
	        	pmsSendVO.setDocType(request.getParameter("docType"));
	        	
	        	
	        	logger.debug("pmsSendVO : "+pmsSendVO.toString());
	        	logger.debug("pmsSendVOJson : "+pmsSendVO.toJsonStr());

	        	//pushType = 01 이면 publicPush
	        	
	        	if(request.getParameter("pushType").equals("01")){
	        		pushResult = PushUtil.pushSend(pmsSendVO,getPmsSendVO.getRegId());
	        		logger.debug("pushResult : "+ pushResult);	        	
	        	}	        	
	        	//pushType = 02 이면 privatePush
	        	else if(request.getParameter("pushType").equals("02")){
	        		pmsSendVO.setMsgType(request.getParameter("docType"));
	        		privatePushService.createPushMsg(pmsSendVO);      
	        	}
	        	}
	        	//파일
//	            AttachFileVO attachFileVO = new AttachFileVO();
//	            attachFileVO.setUserId(userId);
//	            attachFileVO.setGrpCd(codeGrpcdpms);
//	            attachFileVO.setFileType(request.getParameter("file_type"));
//	            attachFileVO.setFileSize(request.getParameter("file_size"));
//	            attachFileVO.setFileDir(request.getParameter("file_path"));
//	            attachFileVO.setFileNm(request.getParameter("file_servername"));
//	            attachFileVO.setInstDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
//	            attachFileVO.setInstId(userId);
//	            attachFileVO.setMsgNo(msgBoxVO.getMsgNo());
//	            attachFileVO.setGrpNo(msgBoxVO.getGrpNo());
//	        	attachFileService.createAttachFile(attachFileVO);
	        	result.setSucMsg("PUSH 메시지 보내기가 성공하였습니다.");
			}        	
            
        } catch (UmsSendProcException e) {
            logger.error("create Pms data validation check fail :: " + e.getMessage());          
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
     * 전송통계
     * 
     * @param searchTransStatVO
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/pmsTransStatList")
    public String getTransStatList(
            @ModelAttribute("searchTransStatVO") SearchTransStatVO searchTransStatVO
            , ModelMap model, HttpServletRequest request) {
    	logger.debug(searchTransStatVO.toString());
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
        
        List<CommCodeVO > userIdList = new ArrayList<CommCodeVO >();
		umsUserVO = new UmsUserVO ();
		umsUserVO.setUserId(userId);
		try{
			userIdList = commonService.getUserIdCodeList(umsUserVO);
		}catch(Exception e){
			logger.debug("userIdList failure = "+e.getMessage());
		}
		logger.debug("userIdList = "+userIdList.toString());

		model.addAttribute("userIdList",userIdList );
        
        model.addAttribute("searchTransStatVO", searchTransStatVOView);
        logger.debug(searchTransStatVO.toString());

        return "/push/pmsTransStatList";
    }
    
    @RequestMapping(value = "/pmsTransStatListProc")
    public String getTransStatListProc(
            @ModelAttribute("searchTransStatVO") SearchTransStatVO searchTransStatVO
            , ModelMap model, HttpServletRequest request) {
    	logger.debug(searchTransStatVO.toString());
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
            totCount = privatePushService.getTransStatListCount(searchTransStatVO);
        }catch(Exception e){
        	logger.debug("getNoticeListCount failure = "+e.getMessage());
        }
        
        // 전체 게시물 건 수
        searchTransStatVO.setTotalRecordCount(totCount);
        
        List<TransStatVO> transStatList = null;
        try{
        	transStatList = privatePushService.getTransStatList(searchTransStatVO);
        }catch(Exception e){
        	logger.debug("getTransStatList failure = "+e.getMessage());
            System.out.println(e.getMessage());
        }
        model.put("transStatList", transStatList);
        
        List<CommCodeVO > userIdList = new ArrayList<CommCodeVO >();
		umsUserVO = new UmsUserVO ();
		umsUserVO.setUserId(userId);
		try{
			userIdList = commonService.getUserIdCodeList(umsUserVO);
		}catch(Exception e){
			logger.debug("userIdList failure = "+e.getMessage());
		}
		logger.debug("userIdList = "+userIdList.toString());

		model.addAttribute("userIdList",userIdList );
        
        model.addAttribute("searchTransStatVO", searchTransStatVOView);
        logger.debug(searchTransStatVO.toString());

        return "/push/pmsTransStatList";
    }
	
    
    /**
     * 전송결과내역
     * @param searchTransRsltVO
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/pmsTransRsltList")
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
        
        logger.debug("session CommCodeVO == ",commCodeVO.toString());

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
			logger.debug("getTransRsltList failure = "+e.getMessage());
		}
		logger.debug("userIdList = "+userIdList.toString());
		model.addAttribute("userIdList",userIdList );
        
        model.put("grpCdList", grpCdList);
//        model.put("transRsltList", transRsltList);
        model.put("searchTransRsltVO", searchTransRsltVO);
        
        return "/push/pmsTransRsltList";
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
            totCount = privatePushService.getTransRsltListCount(searchTransRsltVO);
            System.out.println("##totCount : "+totCount);
        }catch(Exception e){
        	logger.debug("getTransRsltListCount failure = "+e.getMessage());
        }
        
        CommCodeVO commCodeVO =  new CommCodeVO();

        commCodeVO.setCodeGroup("GRP_CD");
        
        logger.debug("session CommCodeVO == ",commCodeVO.toString());

    	List<CommCodeVO> grpCdList = commonService.getCodeList(commCodeVO);
        
        // 전체 게시물 건 수
    	searchTransRsltVO.setTotalRecordCount(totCount);

        List<TransRsltVO> transRsltList = null;
        try{
        	transRsltList = privatePushService.getTransRsltList(searchTransRsltVO);
        }catch(Exception e){
        	logger.debug("getTransRsltList failure = "+e.getMessage());
            System.out.println("totCount : " + totCount);
            System.out.println("msgType : "+searchTransRsltVO.getMsgType());
            System.out.println("에러 msg : " + e.getMessage());
        }

        
        List<CommCodeVO > userIdList = new ArrayList<CommCodeVO >();
		 umsUserVO.setUserId(userId);
		try{
			userIdList = commonService.getUserIdCodeList(umsUserVO);
		}catch(Exception e){
			logger.debug("getTransRsltList failure = "+e.getMessage());
		}
		logger.debug("userIdList = "+userIdList.toString());
		model.addAttribute("userIdList",userIdList );
        
        model.put("grpCdList", grpCdList);
        model.put("transRsltList", transRsltList);
        model.put("searchTransRsltVO", searchTransRsltVO);
        
        return "/push/pmsTransRsltList";
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
        	rsltDetMiddle = privatePushService.getRsltDetMiddle(searchRsltDetVO);
            totCount = Integer.valueOf(rsltDetMiddle.getTotCount());
            searchRsltDetVO.setTotalRecordCount(totCount);
        }catch(Exception e){
        	logger.error("getRsltDetListCount failure = "+e.getMessage());
        }
        
        model.put("firstIndex",         searchRsltDetVO.getFirstRecordIndex());
        model.put("lastRecordIndex",    searchRsltDetVO.getLastRecordIndex());
        model.put("recordCountPerPage", searchRsltDetVO.getRecordCountPerPage());
        
        RsltDetTopVO rsltDetTop = null;
        List<RsltDetBottomVO> rsltDetBottomlist = null;
        try{
        	rsltDetTop = privatePushService.getRsltDetTop(searchRsltDetVO);
        }catch(Exception e){
        	logger.debug("getRsltDetTop failure = "+e.getMessage());
        }
        try{
        	rsltDetBottomlist = privatePushService.getRsltDetBottomList(searchRsltDetVO);
        }catch(Exception e){
        	logger.debug("getRsltDetBottomList failure = "+e.getMessage());
        }
        model.addAttribute(searchRsltDetVO);
        model.put("rsltDetTop", rsltDetTop);
        model.put("rsltDetMiddle", rsltDetMiddle);
        model.put("rsltDetBottomlist", rsltDetBottomlist);
        return "/push/pmsTransRsltDet"; 
    }
}
