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
public class FmsWebController extends DefaultAPIController{
	private static final Logger logger = LoggerFactory.getLogger(FmsWebController.class);
	    
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
     * FMS 전송 처리
     * 
     * 수신자목록 100건당 1Record생성
     * 
     * @param fmsVO
     * @param receiverInfo
     * @param model
     * @param request
     * @return
     * @throws UmsSendProcException
     */
	@RequestMapping(value="/sendFmsProc", method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody JsonVO doSendFmsProcAjax( @ModelAttribute(value="fmsVO")  FmsVO fmsVO
    		, @RequestParam("receiverInfo") String[] receiverInfo
    		, ModelMap model , HttpServletRequest request) throws UmsSendProcException {
    	
        JsonVO result = new JsonVO();
        List<Map<String,Object>> createReceivers1 = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = new HashMap<String,Object>();
        MsgBoxVO msgBoxVO =  new MsgBoxVO();
    	String receiverInfoTemp = "";
    	String reserveDate = "";
    	String repeatDate = "";
    	String msgGrpNo = "";
    	String subject = "";
    	String fileServernameTemp = "";
    	String fileNameTemp = "";
    	String fileTypeTemp = "";
    	String fileSizeTemp = "";
    	String filePathTemp = "";
    	String fileContenttypeTemp = "";
    	int rowCnt = 1;
    	int rowAddCnt = 0;
    	
        try {
        	logger.debug("sendProc info = " + fmsVO.toString());
        	
        	userId = UmsServiceWebSession.getSessionUserId(request);
        	msgGrpNo = request.getParameter("MSGGRPNO");        	
        	fmsVO.setFile_servername(request.getParameterValues("file_servername"));
        	fmsVO.setFile_type(request.getParameterValues("file_type"));
        	fmsVO.setFile_size(request.getParameterValues("file_size"));
        	fmsVO.setFile_path(request.getParameterValues("file_path"));
        	String[] tempFileName = fmsVO.getFile_name();
        	String[] tempFileType = fmsVO.getFile_type();
        	String[] tempFileSize = fmsVO.getFile_size();
        	String[] tempFilePath = fmsVO.getFile_path();
        	String[] tempFileContenttype = fmsVO.getFile_contenttype();
        	Integer cntFile = 0; 

        	subject = fmsVO.getSubject();
        	subject = StringUtil.getHanEngNumber(subject);
        	if(StringUtil.lengthB(subject) > 50) subject = StringUtil.substrB(subject, 1, 50);
        	fmsVO.setSubject(subject);
			
        	//파일정보 
        	for(String temp : fmsVO.getFile_servername()  ) {
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
        	logger.debug("fileServernameTemp= " + fileServernameTemp);
        	logger.debug("fileNameTemp= " + fileNameTemp);
        	logger.debug("fileTypeTemp= " + fileTypeTemp);
        	logger.debug("fileSizeTemp= " + fileSizeTemp);
        	logger.debug("filePathTemp= " + filePathTemp);
        	logger.debug("fileContenttypeTemp= " + fileContenttypeTemp);
        	
        	if(msgGrpNo.equals("") || msgGrpNo == null)
        		msgBoxVO.setGrpNo("0");//defalut group No
        	else
        		msgBoxVO.setGrpNo(msgGrpNo);//msg group No
        	logger.debug("getGrpNo = " + msgBoxVO.getGrpNo());
        	
        	//메시지저장처리
        	if( request.getParameter("MSGSAVE").equals("Y")){ 
        		
        		//default group create
        		GrpVO grpVO =  new GrpVO();
        		SearchGrpVO searchGrpVO =  new SearchGrpVO();
        		searchGrpVO.setUserId(userId);
        		searchGrpVO.setGrpCd(codeGrpcdfms);
        		searchGrpVO.setGrpNo("0");//defalut group
        		grpVO.setInstDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
        		Integer dGNo = grpService.createDefaultGrp(grpVO, searchGrpVO);
        		
        		//save msg create
	        	msgBoxVO.setUserId(userId);
	        	msgBoxVO.setGrpCd(codeGrpcdfms); 
	        	msgBoxVO.setSubject(fmsVO.getSubject());
	        	msgBoxVO.setContents1(fileNameTemp);
	        	msgBoxVO.setContents2(fileServernameTemp);
	        	msgBoxVO.setContents3(fileNameTemp);
	        	msgBoxVO.setContents4(fileSizeTemp);
	        	msgBoxVO.setContents5(filePathTemp);
	        	msgBoxVO.setContents6(fileTypeTemp);
	        	msgBoxVO.setContents7(filePathTemp);
	        	msgBoxVO.setContents8(fileContenttypeTemp);
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

			logger.debug("rowCnt= " + rowCnt);
			for(int i=0; i<createReceivers1.size(); i++){
	        	
	        	fmsVO.setUserId(userId);
	        	
	        	if(fmsVO.getScheduleType().equals("NOW")){
	        		fmsVO.setScheduleType(smsScheduletypeNew);  
	        		fmsVO.setSendDate(DateUtils.getSysDate("yyyyMMddHHmmss"));
	        	}else if(fmsVO.getScheduleType().equals("RESERVE")){	        		
	        		fmsVO.setScheduleType(smsScheduletypeReserve);
	        		reserveDate = reserveDate.concat(fmsVO.getReserveYear());
	        		reserveDate = reserveDate.concat(fmsVO.getReserveMonth());
	        		reserveDate = reserveDate.concat(fmsVO.getReserveDay());
	        		reserveDate = reserveDate.concat(fmsVO.getReserveHour());
	        		reserveDate = reserveDate.concat(fmsVO.getReserveMin()); 
	        		reserveDate = reserveDate.concat("00");
	        		fmsVO.setSendDate(reserveDate);
	        		
	        	}else if(fmsVO.getScheduleType().equals("REPEAT")){
	        		fmsVO.setScheduleType(smsScheduletypeReserve);
	        		repeatDate.concat(fmsVO.getRepeatHour());
	        		repeatDate.concat(fmsVO.getRepeatMin());	        		
	        		fmsVO.setSendDate(DateUtils.getSysDate("yyyyMMddHHmmss"));
	        	}
	        	fmsVO.setSendStatus(smsSendstatus);
	        	fmsVO.setSendCount(smsSendcount); 
	        	fmsVO.setSendResult(smsSendresult);
	        	
	        	Map imap = (HashMap)createReceivers1.get(i);
	        	fmsVO.setDestCount(imap.get("rowSubCnt").toString());	       
	        	fmsVO.setDestInfo(imap.get("receiverInfoTemp").toString());	
	        	
        		fmsVO.setNowDate(DateUtils.getSysDate("yyyyMMddHHmmss"));
	        	if(request.getParameter("file_servername") != null){
		        	fmsVO.setAttachFile(fileServernameTemp);
	        	}
	        	fmsVO.setMsgSubtype("20");//팩스 메시지 분류	(20: Local, 21: 서버 업로드)
	        	logger.debug(this.getClass() + " : toString  = "+fmsVO.toString());
	            umsService.createFms(fmsVO);
				result.setSucMsg("FMS 메시지 보내기가 성공하였습니다.");
			}        	
			cntFile = 0;
			for(String temp : fmsVO.getFile_servername()  ) {
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
			}
        	
        } catch (UmsSendProcException e) {
            logger.error("create Fms data validation check fail :: " + e.getMessage());
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
	 * FMS 메인
	 * @param fmsVO
	 * @param searchMsgBoxVO
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/fms", method = { RequestMethod.GET, RequestMethod.POST })
    public String getFmsMain(@ModelAttribute("fmsVO") FmsVO fmsVO
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
	        	usrGrpVO.setGrpCd(codeGrpcdfms);

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
        	searchMsgBoxVO.setGrpCd(codeGrpcdfms);
        	searchMsgBoxVO.setUserId(userId);
            
        	model.addAttribute("searchMsgBoxVO", searchMsgBoxVO);
             
        }catch (Exception e ) {
            logger.error(e.getMessage());
        }
        
        logger.debug("----------------end main");

        return "/ums/fms";
    }
	
}
