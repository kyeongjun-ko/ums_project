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
public class MmsWebController extends DefaultAPIController{
	private static final Logger logger = LoggerFactory.getLogger(MmsWebController.class);
	    
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
     * MMS메인
     * @param smsVO
     * @param searchMsgBoxVO
     * @param model
     * @param request
     * @return
     */
	@RequestMapping(value = "/mms", method = { RequestMethod.GET, RequestMethod.POST })
    public String getMmsMain(@ModelAttribute("smsVO") SmsVO smsVO
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
	        	usrGrpVO.setGrpCd(codeGrpcdmms);

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
                

        return "/ums/mms";
    }
	
	/**
	 * MMS 간편 팝업
	 * @param smsVO
	 * @param searchMsgBoxVO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/mmsPopUp", method = { RequestMethod.GET, RequestMethod.POST })
    public String getMmsPop(@ModelAttribute("smsVO") SmsVO smsVO
    		, @ModelAttribute("searchMsgBoxVO") SearchMsgBoxVO searchMsgBoxVO
    	    , ModelMap model, HttpServletRequest request) {
        
        logger.debug("-------------start main");
        
        try {
        	
        	/**
        	 * 세션User정보 Get/Set  
        	 */
        	try{
	        	UmsUserVO umsUserVO = UmsServiceWebSession.getSessionUserVO(request);
	        	
	        	UsrGrpVO usrGrpVO =  new UsrGrpVO(); 
	        	
	        	userId = UmsServiceWebSession.getSessionUserId(request);
	        	usrGrpVO.setUserId(userId);
	        	usrGrpVO.setGrpCd(codeGrpcdmms);

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
                

        return "/ums/mmsPop";
    }
	 
	/**
	 * MMS 전송 처리
	 * 
	 * @param mmsVO
	 * @param receiverInfo
	 * @param model
	 * @param request
	 * @return
	 * @throws UmsSendProcException
	 */
	@RequestMapping(value="/sendMmsProc", method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody JsonVO doSendMmsProcAjax( @ModelAttribute(value="mmsVO")  MmsVO mmsVO
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
    	int rowAddCnt = 0;
    	int msgBoxISeq = 0;
    	userId = UmsServiceWebSession.getSessionUserId(request);
    	msgGrpNo = request.getParameter("MSGGRPNO"); 
    	String receiveType = request.getParameter("RECEIVER_TYPE"); 

    	MsgBoxVO msgBoxVO =  new MsgBoxVO();
        try {
        	logger.debug("sendProc info = " + mmsVO.toString());
            
        	subject = mmsVO.getSubject();
        	subject = StringUtil.getHanEngNumber(subject);
        	if(StringUtil.lengthB(subject) > 50) subject = StringUtil.substrB(subject, 1, 50);
        	mmsVO.setSubject(subject);
        	
        	if(msgGrpNo.equals("") || msgGrpNo == null)
        		msgBoxVO.setGrpNo("0");//defalut group No
        	else
        		msgBoxVO.setGrpNo(msgGrpNo);//msg group No
        	
        	if( request.getParameter("MSGSAVE").equals("Y")){ 
        		
        		//default group create
        		GrpVO grpVO =  new GrpVO();
        		SearchGrpVO searchGrpVO =  new SearchGrpVO();
        		searchGrpVO.setUserId(userId);
        		searchGrpVO.setGrpCd(codeGrpcdmms);
        		searchGrpVO.setGrpNo("0");//defalut group
        		grpVO.setInstDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
        		grpService.createDefaultGrp(grpVO, searchGrpVO);
        		
        		//save msg create
	        	
	        	msgBoxVO.setUserId(userId);
	        	msgBoxVO.setGrpCd(codeGrpcdmms); 
	        	msgBoxVO.setSubject(mmsVO.getSubject());
	        	msgBoxVO.setContents1(mmsVO.getMmsMsg());
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
        	
//        	for(int i=0; i<receiverInfo.length; i++){
//        		rowAddCnt ++;
//        		logger.debug("rowSubCnt= " + i);
//        		receiverInfoTemp = receiverInfoTemp.concat(receiverInfo[i]);
//        		if(i == ((sendmaxcnt*rowCnt)-1)){
//        			map = new HashMap();
//					map.put("receiverInfoTemp", receiverInfoTemp);
//					map.put("rowSubCnt", sendmaxcnt);
//					createReceivers1.add(map);
//					receiverInfoTemp = "";
//					rowCnt++;
//					rowAddCnt = 0;
//				} 
//			}
//        	
//        	if( rowCnt== 1 ){
//	    		map = new HashMap();
//	    		map.put("receiverInfoTemp", receiverInfoTemp);
//				map.put("rowSubCnt", rowAddCnt);
//				createReceivers1.add(map);
//        	}
//        	else if( rowCnt > 1){
//        		if(receiverInfo.length % ((rowCnt-1)*sendmaxcnt) > 0 ){
//        			map = new HashMap();
//    	    		map.put("receiverInfoTemp", receiverInfoTemp);
//    				map.put("rowSubCnt", rowAddCnt);
//    				createReceivers1.add(map);
//        		}
//        	}
			
			for(int i=0; i<createReceivers1.size(); i++){
	        	
	        	mmsVO.setUserId(userId);
	        	
	        	if(mmsVO.getScheduleType().equals("NOW")){
	        		mmsVO.setScheduleType(smsScheduletypeNew);  
	        		mmsVO.setSendDate(DateUtils.getSysDate("yyyyMMddHHmmss"));
	        	}else if(mmsVO.getScheduleType().equals("RESERVE")){	        		
	        		mmsVO.setScheduleType(smsScheduletypeReserve);
	        		reserveDate = reserveDate.concat(mmsVO.getReserveYear());
	        		reserveDate = reserveDate.concat(mmsVO.getReserveMonth());
	        		reserveDate = reserveDate.concat(mmsVO.getReserveDay());
	        		reserveDate = reserveDate.concat(mmsVO.getReserveHour());
	        		reserveDate = reserveDate.concat(mmsVO.getReserveMin()); 
	        		reserveDate = reserveDate.concat("00");
	        		mmsVO.setSendDate(reserveDate);
	        		
	        	}else if(mmsVO.getScheduleType().equals("REPEAT")){
	        		mmsVO.setScheduleType(smsScheduletypeReserve);
	        		repeatDate.concat(mmsVO.getRepeatHour());
	        		repeatDate.concat(mmsVO.getRepeatMin());	        		
	        		mmsVO.setSendDate(DateUtils.getSysDate("yyyyMMddHHmmss"));
	        	}
	        	
	        	mmsVO.setSendStatus(smsSendstatus);
	        	mmsVO.setSendCount(smsSendcount); 
	        	mmsVO.setSendResult(smsSendresult);
	        	Map imap = (HashMap)createReceivers1.get(i);
	        	mmsVO.setDestCount(imap.get("rowSubCnt").toString());	
	        	
	        	String destInfo = imap.get("receiverInfoTemp").toString();
	        	destInfo = StringUtil.replaceString(destInfo);
	        	
	        	mmsVO.setDestInfo(destInfo);
	        	
        		mmsVO.setNowDate(DateUtils.getSysDate("yyyyMMddHHmmss"));
        		logger.debug("file_servername info = " + request.getParameter("file_servername"));
        		mmsVO.setContentCount("0");
	        	if(request.getParameter("file_servername") != null){
		        	mmsVO.setContentCount("1");
		        	mmsVO.setContentData(request.getParameter("file_servername")+"^1^0");
	        	}
	        	mmsVO.setMsgType("0");//일반 TEXT : 0, HTML 지원: 1
	        	
	        	
	        	logger.debug(this.getClass() + " : toString  = "+mmsVO.toString());
	            umsService.createMms(mmsVO);
	        	//파일
	            AttachFileVO attachFileVO = new AttachFileVO();
	            attachFileVO.setUserId(userId);
	            attachFileVO.setGrpCd(codeGrpcdmms);
	            attachFileVO.setFileType(request.getParameter("file_type"));
	            attachFileVO.setFileSize(request.getParameter("file_size"));
	            attachFileVO.setFileDir(request.getParameter("file_path"));
	            attachFileVO.setFileNm(request.getParameter("file_servername"));
	            attachFileVO.setInstDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
	            attachFileVO.setInstId(userId);
	            attachFileVO.setMsgNo(msgBoxVO.getMsgNo());
	            attachFileVO.setGrpNo(msgBoxVO.getGrpNo());
	        	attachFileService.createAttachFile(attachFileVO);
	        	
	        	result.setSucMsg("MMS 메시지 보내기가 성공하였습니다.");
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
	   
}
