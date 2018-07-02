package com.bccns.umsserviceweb.smart.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bccns.umsserviceweb.common.controller.DefaultAPIController;
import com.bccns.umsserviceweb.common.service.CommonService;
import com.bccns.umsserviceweb.common.util.DateUtils;
import com.bccns.umsserviceweb.common.util.UmsServiceWebSession;
import com.bccns.umsserviceweb.mgr.service.AddrService;
import com.bccns.umsserviceweb.mgr.service.AttachFileService;
import com.bccns.umsserviceweb.mgr.service.GrpService;
import com.bccns.umsserviceweb.mgr.service.MsgBoxService;
import com.bccns.umsserviceweb.mgr.vo.MsgBoxVO;
import com.bccns.umsserviceweb.mgr.vo.SearchMsgBoxVO;
import com.bccns.umsserviceweb.notice.service.NoticeService;
import com.bccns.umsserviceweb.smart.vo.SmartViewVO;
import com.bccns.umsserviceweb.ums.service.UmsService;
import com.bccns.umsserviceweb.ums.vo.MmsVO;
import com.bccns.umsserviceweb.ums.vo.SmsVO;

@Controller
public class ContWebController extends DefaultAPIController {
	private static final Logger logger = LoggerFactory.getLogger(ContWebController.class);
	
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
     * 스마트 DM 컨텐츠 조회(폰에서 오픈하는 링크페이지)
     * 대상 : 모바일 안내장에서 송신한 스마트DM 등록한 컨텐츠
     * @param model
     * @param request
     * @param response
     * @return
     */
	@RequestMapping(value = "/SD", method = { RequestMethod.GET, RequestMethod.POST })
    public String getSmartDmWeb(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        
		logger.debug("-------------start main");
        
    	SearchMsgBoxVO searchMsgBoxVO = new SearchMsgBoxVO();
    	userId = UmsServiceWebSession.getSessionUserId(request);
    	searchMsgBoxVO.setGrpCd(codeGrpcdsdm);
    	//https://airbusan2.sendall.co.kr:444/SD?SN=0;175;2014102910420001067323629
    	//0;175;2014102910420001067323629
    	//0 1   2 
    	String smartContId[] = request.getParameter("SN").split(";");
    	searchMsgBoxVO.setGrpCd(codeGrpcdsdm);
    	searchMsgBoxVO.setGrpNo(smartContId[0]);
    	searchMsgBoxVO.setMsgNo(smartContId[1]);
    	
    	logger.debug("-------------searchMsgBoxVO = ",searchMsgBoxVO.toString());
    	MsgBoxVO msgBoxVO = msgBoxService.getMsgBoxDetail(searchMsgBoxVO);
    	
    	String sFileServername[] = msgBoxVO.getContents2().split(";");
    	String stFilePathTemp = msgBoxVO.getContents7();
    	SmartViewVO smartViewVO = new SmartViewVO();
    	smartViewVO.setSmartContId(request.getParameter("SN"));
    	
    	for(int i=0; i<sFileServername.length; ++i){
    		if(i == 0)	smartViewVO.setFileNm1(stFilePathTemp+sFileServername[i]);
    		if(i == 1)	smartViewVO.setFileNm2(stFilePathTemp+sFileServername[i]);
    		if(i == 2)	smartViewVO.setFileNm3(stFilePathTemp+sFileServername[i]);
    	}
    	
    	if(msgBoxVO.getContents9().toUpperCase().equals("IMAGEAREA1")){
			smartViewVO.setFileLink1(msgBoxVO.getContents10());
    	}else if(msgBoxVO.getContents9().toUpperCase().equals("IMAGEAREA2")){
    		smartViewVO.setFileLink2(msgBoxVO.getContents10());
    	}else if(msgBoxVO.getContents9().toUpperCase().equals("IMAGEAREA3")){
    		smartViewVO.setFileLink3(msgBoxVO.getContents10());
		}
    	
    	smartViewVO.setSubject(msgBoxVO.getSubject());        	
    	smartViewVO.setImageselect(msgBoxVO.getContents9());
    	smartViewVO.setImagelink(msgBoxVO.getContents10());
    	
    	model.addAttribute("smartViewVO", smartViewVO);
        	
    	System.out.println("-------------smartViewVO = "+smartViewVO.toString());
        logger.debug("----------------end main");
                
        return "/smart/smartDmWeb";
    }
	
	/**
	 * 스마트팩스 (준비중)
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/SF", method = { RequestMethod.GET, RequestMethod.POST })
    public String getSmartFaxWeb( ModelMap model, HttpServletRequest request) {
        
		logger.debug("-------------start main");
        
		logger.debug("-------------start main");
        String grpNo = "";
        String msgNo = "";
        
    	SearchMsgBoxVO searchMsgBoxVO = new SearchMsgBoxVO();
    	userId = UmsServiceWebSession.getSessionUserId(request);
    	searchMsgBoxVO.setGrpCd(codeGrpcdsfx);
    	//SMARTDM 0  userId 1 company 2 dept 3 grpcd 4 grpno 5 msgno 6
    	//SMARTDM;   hd02;  01;           01;   07;     0;      150
    	//SMARTDM;hd02;01;01;07;0;150
    	String smartContId[] = request.getParameter("smartContId").split(";");
    	searchMsgBoxVO.setUserId(smartContId[1]);
    	searchMsgBoxVO.setGrpNo(smartContId[5]);
    	searchMsgBoxVO.setMsgNo(smartContId[6]);
    	
    	logger.debug("-------------searchMsgBoxVO = ",searchMsgBoxVO.toString());
    	MsgBoxVO msgBoxVO = msgBoxService.getMsgBoxDetail(searchMsgBoxVO);
    	
    	
    	String sFileServername[] = msgBoxVO.getContents2().split(";");
    	String stFilePathTemp = msgBoxVO.getContents7();
    	SmartViewVO smartViewVO = new SmartViewVO();
    	
    	for(int i=0; i<sFileServername.length; ++i){
    		if(i == 0)	smartViewVO.setFileNm1(stFilePathTemp+sFileServername[i]);
    		if(i == 1)	smartViewVO.setFileNm2(stFilePathTemp+sFileServername[i]);
    		if(i == 2)	smartViewVO.setFileNm3(stFilePathTemp+sFileServername[i]);
    	}
    	
    	smartViewVO.setSubject(msgBoxVO.getSubject());        	
    	smartViewVO.setImageselect(msgBoxVO.getContents9());
    	smartViewVO.setImagelink(msgBoxVO.getContents10());
    	
    	model.addAttribute("smartViewVO", smartViewVO);
        logger.debug("----------------end main");
        return "/smart/smartFaxWeb";
    }
	
	/**
	 * 스마트 DM 확인/응답처리
	 * @param model
	 * @param request
	 * @param smartViewVO
	 */
	@RequestMapping(value = "/smartDmWeb/corfirm", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody void updateSmartDmWeb( ModelMap model, HttpServletRequest request,
			 @ModelAttribute(value="smartViewVO")  SmartViewVO smartViewVO) {
        
		logger.debug("-------------start main");
		//https://airbusan2.sendall.co.kr:444/SD?SN=0;175;2014102910420001067323629
    	//0;175;2014102910420001067323629
    	//0 1   2 
        String smartContId = smartViewVO.getSmartContId();
        
        String smartContIdTemp[] = smartContId.split(";");
        String grpCd = request.getParameter("MSGTYPE");
        String grpNo = smartContIdTemp[0];
        String msgNo = smartContIdTemp[1];
        String senDt = smartContIdTemp[2].substring(0,14);
        String phoneNo = smartContIdTemp[2].substring(14);
        String nowDate = DateUtils.getSysDate("yyyyMMddHHmmss"); 
        
        if(grpCd.equals("SMS")){
        	SmsVO smsVO = new SmsVO();
        	smsVO.setReserved2(smartContId);
        	if(request.getParameter("smartResCd").equals("CON")){
        		smsVO.setReserved3("1");
        		smsVO.setReserved4(nowDate);
        	}else if(request.getParameter("smartResCd").equals("RES")){
        		smsVO.setReserved3("2");
        		smsVO.setReserved5(nowDate);
        	}
        	logger.debug("smsVO info = ", smsVO.toString());
        	umsService.modifySmsSmartDM(smsVO);
        }else if(grpCd.equals("MMS")){
        	MmsVO mmsVO = new MmsVO();
        	mmsVO.setReserved2(smartContId);
        	if(request.getParameter("smartResCd").equals("CON")){
        		mmsVO.setReserved3("1");
        		mmsVO.setReserved4(nowDate);
        	}else if(request.getParameter("smartResCd").equals("RES")){
        		mmsVO.setReserved3("2");
        		mmsVO.setReserved5(nowDate);
        	}
        	logger.debug("mmsVO info = ", mmsVO.toString());
        	umsService.modifyMmsSmartDM(mmsVO);
        }
        logger.debug("----------------end main");
	}
	
	/**
	 * 스마트DM 컨텐츠 조회(외부업체용)청호나이스등등
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/SDO", method = { RequestMethod.GET, RequestMethod.POST })
    public String getSmartDmWeb_CH(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        
		logger.debug("-------------start main");
        
    	SearchMsgBoxVO searchMsgBoxVO = new SearchMsgBoxVO();
    	userId = UmsServiceWebSession.getSessionUserId(request);
    	searchMsgBoxVO.setGrpCd(codeGrpcdsdm);
    	//https://airbusan2.sendall.co.kr:444/SD?SN=0;175;2014102910420001067323629
    	//0;175;2014102910420001067323629
    	//0 1   2 
    	String smartContId[] = request.getParameter("SN").split(";");
    	searchMsgBoxVO.setGrpCd(codeGrpcdsdm);
    	searchMsgBoxVO.setGrpNo(smartContId[0]);
    	searchMsgBoxVO.setMsgNo(smartContId[1]);
    	searchMsgBoxVO.setMsgId(smartContId[2]);
    	
    	System.out.println("-------------searchMsgBoxVO = "+searchMsgBoxVO.toString());
    	logger.debug("-------------searchMsgBoxVO = ",searchMsgBoxVO.toString());
    	MsgBoxVO msgBoxVO = msgBoxService.getMsgBoxDetail(searchMsgBoxVO);
    	
    	String sFileServername[] = msgBoxVO.getContents2().split(";");
    	String stFilePathTemp = msgBoxVO.getContents7();
    	SmartViewVO smartViewVO = new SmartViewVO();
    	smartViewVO.setSmartContId(request.getParameter("SN"));
    	smartViewVO.setGrpNm(searchMsgBoxVO.getGrpNo());
    	smartViewVO.setMsgNo(searchMsgBoxVO.getMsgNo());
    	
    	for(int i=0; i<sFileServername.length; ++i){
    		if(i == 0)	smartViewVO.setFileNm1(stFilePathTemp+sFileServername[i]);
    		if(i == 1)	smartViewVO.setFileNm2(stFilePathTemp+sFileServername[i]);
    		if(i == 2)	smartViewVO.setFileNm3(stFilePathTemp+sFileServername[i]);
    	}
    	
    	if(msgBoxVO.getContents9().toUpperCase().equals("IMAGEAREA1")){
			smartViewVO.setFileLink1(msgBoxVO.getContents10());
    	}else if(msgBoxVO.getContents9().toUpperCase().equals("IMAGEAREA2")){
    		smartViewVO.setFileLink2(msgBoxVO.getContents10());
    	}else if(msgBoxVO.getContents9().toUpperCase().equals("IMAGEAREA3")){
    		smartViewVO.setFileLink3(msgBoxVO.getContents10());
		}
    	
    	smartViewVO.setSubject(msgBoxVO.getSubject());        	
    	smartViewVO.setImageselect(msgBoxVO.getContents9());
    	smartViewVO.setImagelink(msgBoxVO.getContents10());
    	
    	msgBoxVO = msgBoxService.getDmContents(searchMsgBoxVO);
    	
    	if(msgBoxVO.getContents1() != null) smartViewVO.setContent1(msgBoxVO.getContents1().split(";"));
    	if(msgBoxVO.getContents2() != null) smartViewVO.setContent2(msgBoxVO.getContents2().split(";"));
    	if(msgBoxVO.getContents3() != null) smartViewVO.setContent3(msgBoxVO.getContents3().split(";"));
    	if(msgBoxVO.getContents4() != null) smartViewVO.setContent4(msgBoxVO.getContents4().split(";"));
    	
    	model.addAttribute("smartViewVO", smartViewVO);
        	
    	System.out.println("-------------smartViewVO = "+smartViewVO.toString());
        logger.debug("----------------end main");
        
        return "/smart/smartDmWeb_CH01";
    }
}
