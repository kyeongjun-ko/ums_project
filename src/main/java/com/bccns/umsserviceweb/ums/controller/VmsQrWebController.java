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
public class VmsQrWebController extends DefaultAPIController{
	private static final Logger logger = LoggerFactory.getLogger(VmsQrWebController.class);
	    
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
	

	@RequestMapping(value = "/vmsQr", method = { RequestMethod.GET, RequestMethod.POST })
    public String getVmsQrMain(@ModelAttribute("vmsVO") VmsVO vmsVO
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
	        	logger.debug("session umsUserVO == ",umsUserVO.toString());
	
	        	UsrGrpVO usrGrpVO =  new UsrGrpVO(); 
	        	
	        	userId = UmsServiceWebSession.getSessionUserId(request);
	        	usrGrpVO.setUserId(userId);
	        	usrGrpVO.setGrpCd(codeGrpcdvms);
	        	logger.debug("session usrGrpVO == ",usrGrpVO.toString());

	        	List<UsrGrpVO> usrGrpList = commonService.getUsrGrpList(usrGrpVO);
	        	
	        	logger.debug("usrGrpList = "+ usrGrpList.toString() );
	        	
	        	model.addAttribute("usrGrpList",usrGrpList);
	        	
	        	model.addAttribute("usrCallBackList",getUsrCallBackList(umsUserVO));
	        	
	        	
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
        	searchMsgBoxVO.setGrpCd(codeGrpcdvms);
        	searchMsgBoxVO.setUserId(userId);
            
        	model.addAttribute("searchMsgBoxVO", searchMsgBoxVO);
        }catch (Exception e ) {
            logger.error(e.getMessage());
        }
        
        logger.debug("----------------end main");
                

        return "/ums/vmsQr";
    }
	
	@RequestMapping(value="/sendVmsQrProc", method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody JsonVO doSendVmsQrProcAjax( @ModelAttribute(value="vmsVO")  VmsVO vmsVO
    		, @RequestParam("receiverInfo") String[] receiverInfo
    		, ModelMap model , HttpServletRequest request) throws UmsSendProcException {
    	
        JsonVO result = new JsonVO();
        List<Object> createReceivers1 = new ArrayList<Object>();
    	String receiverInfoTemp = "";
    	String reserveDate = "";
    	String repeatDate = "";
    	String msgGrpNo = "";
    	int rowCnt = 0;
    	int rowSubCnt = 1;
    	userId = UmsServiceWebSession.getSessionUserId(request);
    	msgGrpNo = request.getParameter("MSGGRPNO"); 
    	
        try {
        	logger.debug("sendProc info = " + vmsVO.toString());
            
        	logger.debug("MSGSAVE info = " + request.getParameter("MSGSAVE"));
          	 
        	if( request.getParameter("MSGSAVE").equals("Y")){ 
        		//default group create
        		GrpVO grpVO =  new GrpVO();
        		SearchGrpVO searchGrpVO =  new SearchGrpVO();
        		searchGrpVO.setUserId(userId);
        		searchGrpVO.setGrpCd(codeGrpcdvms);
        		searchGrpVO.setGrpNo("0");//defalut group
        		grpVO.setInstDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
        		grpService.createDefaultGrp(grpVO, searchGrpVO);
        		
        		//save msg create
	        	MsgBoxVO msgBoxVO =  new MsgBoxVO();
	        	msgBoxVO.setUserId(userId);
	        	msgBoxVO.setGrpCd(codeGrpcdvms); 
	        	logger.debug("msgGrpNo = " + msgGrpNo);
	        	if(msgGrpNo.equals("") || msgGrpNo == null)
	        		msgBoxVO.setGrpNo("0");//defalut group No
	        	else
	        		msgBoxVO.setGrpNo(msgGrpNo);//msg group No
	        	
	        	msgBoxVO.setSubject(vmsVO.getSubject());
	        	msgBoxVO.setContents1(vmsVO.getTtsMsg());
	        	this.createmsgBox(msgBoxVO);
        	}
        	logger.debug("receiverInfo info = " + receiverInfo.toString());
        	logger.debug("receiverInfo length = " + receiverInfo.length);
        	for(String temp : receiverInfo ) {
				receiverInfoTemp = receiverInfoTemp.concat(temp);
				if(rowSubCnt == sendmaxcnt*rowCnt){
					createReceivers1.add(receiverInfoTemp);
					receiverInfoTemp = null;
					logger.debug("createReceiver :" + createReceivers1.get(rowCnt));
					rowCnt++;
				} 
				logger.debug("rowSubCnt= " + rowSubCnt);
				rowSubCnt ++;
			}
        	if(rowCnt== 0){
        		logger.debug("receiverInfoTemp= " + receiverInfoTemp);
        		createReceivers1.add(receiverInfoTemp);
        		logger.debug("createReceiver :" + createReceivers1.get(rowCnt));

        	}
        	logger.debug("rowCnt= " + rowCnt);
			for(Object createReceiver : createReceivers1){
				logger.debug("createReceiver= " + createReceiver.toString());
	        	
	        	vmsVO.setUserId(userId);
	        	
	        	if(vmsVO.getScheduleType().equals("NOW")){
	        		vmsVO.setScheduleType(smsScheduletypeNew);  
	        		vmsVO.setSendDate(DateUtils.getSysDate("yyyyMMddHHmmss"));
	        	}else if(vmsVO.getScheduleType().equals("RESERVE")){	        		
	        		vmsVO.setScheduleType(smsScheduletypeReserve);
	        		reserveDate = reserveDate.concat(vmsVO.getReserveYear());
	        		reserveDate = reserveDate.concat(vmsVO.getReserveMonth());
	        		reserveDate = reserveDate.concat(vmsVO.getReserveDay());
	        		reserveDate = reserveDate.concat(vmsVO.getReserveHour());
	        		reserveDate = reserveDate.concat(vmsVO.getReserveMin()); 
	        		reserveDate = reserveDate.concat("00");
	        		logger.debug("reserveDate= " + reserveDate.toString());
	        		vmsVO.setSendDate(reserveDate);
	        		
	        	}else if(vmsVO.getScheduleType().equals("REPEAT")){
	        		vmsVO.setScheduleType(smsScheduletypeReserve);
	        		repeatDate.concat(vmsVO.getRepeatHour());
	        		repeatDate.concat(vmsVO.getRepeatMin());	        		
	        		vmsVO.setSendDate(DateUtils.getSysDate("yyyyMMddHHmmss"));
	        	}
	        	vmsVO.setDestCount(String.valueOf(receiverInfo.length));
	        	vmsVO.setSendStatus(smsSendstatus);
	        	vmsVO.setSendCount(smsSendcount); 
	        	vmsVO.setSendResult(smsSendresult);
	        	vmsVO.setDestInfo(createReceiver.toString());	
        		vmsVO.setNowDate(DateUtils.getSysDate("yyyyMMddHHmmss"));

        		vmsVO.setMsgSubtype("30");//30:TTS , 32:설문조사
        		vmsVO.setMentType("0"); //0 : 본문만 사용하며, 머리말과 맺음말을 사용하지 않음
        		vmsVO.setVoiceType("0"); //0:여성
        		vmsVO.setReplyType("0");//0:답변 받기 사용 안 함 1:답변받기사용
        		vmsVO.setReplyCount("0");//0:답변받기사용안함
        		vmsVO.setCounselorDtmf("0");
        		vmsVO.setRelistenCount("0");        		
        		
	        	logger.debug(this.getClass() + " : toString  = "+vmsVO.toString());
	            umsService.createVms(vmsVO);
	            result.setSucMsg("VMS 메시지 보내기가 성공하였습니다.");
			}        	
            
        } catch (UmsSendProcException e) {
            logger.error("create Mms data validation check fail :: " + e.getMessage());
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
	private List<CommCodeVO> getUsrCallBackList(UmsUserVO umsUserVO) {
        
        List<CommCodeVO> usrCallBackList = new ArrayList<CommCodeVO>();
        UmsUserVO userVO = null;
        try{
        	userVO = commonService.getUserInfoD(umsUserVO);
        }catch(Exception e){
            logger.debug("getUserInfoD failure = "+e.getMessage());
        }
        usrCallBackList.add(new CommCodeVO("callbackNo1", userVO.getCallbackNo1()));
        usrCallBackList.add(new CommCodeVO("callbackNo2", userVO.getCallbackNo2()));
        usrCallBackList.add(new CommCodeVO("callbackNo3", userVO.getCallbackNo3()));
        usrCallBackList.add(new CommCodeVO("callbackNo4", userVO.getCallbackNo4()));
        logger.debug("getCallbackNo1 = "+userVO.getCallbackNo1());
        logger.debug("usrCallBackList = "+usrCallBackList.toString());
        return usrCallBackList;
    }
	public Integer createmsgBox(MsgBoxVO msgBoxVO) {
		// TODO Auto-generated method stub
		return msgBoxService.createMsgBox(msgBoxVO);
	}
}
