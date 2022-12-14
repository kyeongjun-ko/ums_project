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
public class VmsWebController extends DefaultAPIController{
	private static final Logger logger = LoggerFactory.getLogger(VmsWebController.class);
	    
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
     * VMS ?????? ??????
     * @param vmsVO
     * @param receiverInfo
     * @param model
     * @param request
     * @return
     * @throws UmsSendProcException
     */
	@RequestMapping(value="/sendVmsProc", method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody JsonVO doSendVmsProcAjax( @ModelAttribute(value="vmsVO")  VmsVO vmsVO
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
    	int rowCnt = 1;
    	int rowSubCnt = 1;
    	int rowAddCnt = 0;
    	
    	userId = UmsServiceWebSession.getSessionUserId(request);
    	msgGrpNo = request.getParameter("MSGGRPNO"); 
    	
        try {
        	logger.debug("sendProc info = " + vmsVO.toString());
            
        	subject = vmsVO.getSubject();
        	subject = StringUtil.getHanEngNumber(subject);
        	if(StringUtil.lengthB(subject) > 50) subject = StringUtil.substrB(subject, 1, 50);
        	vmsVO.setSubject(subject);
        	
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
	        	if(msgGrpNo.equals("") || msgGrpNo == null)
	        		msgBoxVO.setGrpNo("0");//defalut group No
	        	else
	        		msgBoxVO.setGrpNo(msgGrpNo);//msg group No
	        	
	        	msgBoxVO.setSubject(vmsVO.getSubject());
	        	msgBoxVO.setContents1(vmsVO.getTtsMsg());
	        	umsService.createmsgBox(msgBoxVO);
        	}
        	logger.debug("receiverInfo info = " + receiverInfo.toString());
        	logger.debug("receiverInfo length = " + receiverInfo.length);
        	for(int i=0; i<receiverInfo.length; i++){
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
				rowSubCnt ++;
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
	        		vmsVO.setSendDate(reserveDate);
	        		
	        	}else if(vmsVO.getScheduleType().equals("REPEAT")){
	        		vmsVO.setScheduleType(smsScheduletypeReserve);
	        		repeatDate.concat(vmsVO.getRepeatHour());
	        		repeatDate.concat(vmsVO.getRepeatMin());	        		
	        		vmsVO.setSendDate(DateUtils.getSysDate("yyyyMMddHHmmss"));
	        	}
	        	//vmsVO.setDestCount(String.valueOf(receiverInfo.length));
	        	vmsVO.setSendStatus(smsSendstatus);
	        	vmsVO.setSendCount(smsSendcount); 
	        	vmsVO.setSendResult(smsSendresult);
	        	//vmsVO.setDestInfo(createReceiver.toString());	
        		vmsVO.setNowDate(DateUtils.getSysDate("yyyyMMddHHmmss"));
        		
        		Map imap = (HashMap)createReceivers1.get(i);
        		vmsVO.setDestCount(imap.get("rowSubCnt").toString());	       
        		vmsVO.setDestInfo(imap.get("receiverInfoTemp").toString());	
	        	
        		vmsVO.setMsgSubtype("30");//30:TTS , 32:????????????
        		vmsVO.setMentType("0"); //0 : ????????? ????????????, ???????????? ???????????? ???????????? ??????
        		vmsVO.setVoiceType("0"); //0:??????
        		vmsVO.setReplyType("0");//0:?????? ?????? ?????? ??? ??? 1:??????????????????
        		vmsVO.setReplyCount("0");//0:????????????????????????
        		vmsVO.setCounselorDtmf("0");
        		vmsVO.setRelistenCount("0");        		
        		
	        	logger.debug(this.getClass() + " : toString  = "+vmsVO.toString());
	            umsService.createVms(vmsVO);
	            result.setSucMsg("VMS ????????? ???????????? ?????????????????????.");
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
	 
	/**
	 * VMS ??????
	 * @param vmsVO
	 * @param searchMsgBoxVO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/vms", method = { RequestMethod.GET, RequestMethod.POST })
    public String getVmsMain(@ModelAttribute("vmsVO") VmsVO vmsVO
    		, @ModelAttribute("searchMsgBoxVO") SearchMsgBoxVO searchMsgBoxVO
    	    , ModelMap model, HttpServletRequest request) {
        
        logger.debug("-------------start main");
        
        try {
        	/**
        	 * ??????User?????? Get/Set  
        	 */
        	try{
	        	UmsUserVO umsUserVO = UmsServiceWebSession.getSessionUserVO(request);
	        	
	
	        	UsrGrpVO usrGrpVO =  new UsrGrpVO(); 
	        	
	        	userId = UmsServiceWebSession.getSessionUserId(request);
	        	usrGrpVO.setUserId(userId);
	        	usrGrpVO.setGrpCd(codeGrpcdvms);

	        	List<UsrGrpVO> usrGrpList = commonService.getUsrGrpList(usrGrpVO);
	        	
	        	model.addAttribute("usrGrpList",usrGrpList);
	        	
	        	model.addAttribute("usrCallBackList",umsService.getUsrCallBackList(umsUserVO));
	        	
	        	
        	}catch(Exception e){
                logger.debug("getUsrGrpList failure = "+e.getMessage());
            }
        	
        	/**
             *  ????????? ????????? ?????????
             */
        	// ??? ???????????? ???????????? ????????? ??????
        	searchMsgBoxVO.setRecordCountPerPage(this.recordCountPerPage);
            // ????????? ???????????? ?????????
        	searchMsgBoxVO.setPageSize(this.pageSize);
        	searchMsgBoxVO.setGrpCd(codeGrpcdvms);
        	searchMsgBoxVO.setUserId(userId);
            
        	model.addAttribute("searchMsgBoxVO", searchMsgBoxVO);
        }catch (Exception e ) {
            logger.error(e.getMessage());
        }
        
        logger.debug("----------------end main");
                

        return "/ums/vms";
    }
	
	 
}
