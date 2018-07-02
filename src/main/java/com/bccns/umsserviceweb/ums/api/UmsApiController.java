package com.bccns.umsserviceweb.ums.api;


import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.bccns.umsserviceweb.ums.vo.URL;
import com.bccns.umsserviceweb.ums.vo.PDS.PDSResponseVO;

@Controller
@RequestMapping(value = "/ums")
public class UmsApiController extends DefaultAPIController{
	private static final Logger logger = LoggerFactory.getLogger(UmsApiController.class);
	    
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
	
	private static final int TIME_OUT = 60000;
	 
	@RequestMapping(value = "/getPdsListAjax" , method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody SmsVO getPdsListAjax(
            @ModelAttribute("searchGrpVO") SearchGrpVO searchGrpVO
            , ModelMap model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		
		String cnt = request.getParameter("cnt");
		String pl = request.getParameter("pl").replaceAll("-", "");
		String strttmp = "";
		String smartContTemp[] = pl.split(",");
		for(int i = 0; i < smartContTemp.length; i++ ){
			String reqStr = "{'header':{'clientId':'bluechip','clientPw':'bluechip'},'body':{'requestId':'11111','telNo':'"+smartContTemp[i]+"'}}";
			logger.debug("getPdsListAjax start `````````"+pl);
			
			PDSResponseVO vo = new TestClient(request).request(reqStr);
			if(vo.getBody().getData().getSmartPhnYn().length() > 0){
				strttmp.concat(smartContTemp[i]).concat("^").concat(vo.getBody().getData().getSmartPhnYn()).concat(":");
			}
			System.out.println("test"+vo.getBody().getData().toString());
			
			logger.debug("getPdsListAjax end `````````");
		}
		
        return null;//smsVO;
    }    
}
