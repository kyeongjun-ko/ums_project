package com.bccns.umsserviceweb.push.controller.api;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bccns.umsserviceweb.common.controller.DefaultAPIController;
import com.bccns.umsserviceweb.common.util.DateUtils;
import com.bccns.umsserviceweb.common.util.FileTools;
import com.bccns.umsserviceweb.common.util.JSONParser;
import com.bccns.umsserviceweb.common.util.StringUtil;
import com.bccns.umsserviceweb.common.util.UmsServiceWebSession;
import com.bccns.umsserviceweb.common.util.XSSDomainFilter;
import com.bccns.umsserviceweb.common.util.XSSFilter;
import com.bccns.umsserviceweb.push.conf.PushConst;
import com.bccns.umsserviceweb.push.service.PrivatePushService;
import com.bccns.umsserviceweb.push.service.PushService;
import com.bccns.umsserviceweb.push.service.PrivatePushServiceImpl;
import com.bccns.umsserviceweb.push.util.PushUtil;
import com.bccns.umsserviceweb.push.vo.PmsPointMsgVO;
import com.bccns.umsserviceweb.push.vo.PmsSendVO;
import com.bccns.umsserviceweb.push.vo.ResBodyResultVO;
import com.bccns.umsserviceweb.push.vo.RexHeaderVO;
import com.bccns.umsserviceweb.push.vo.PUSH0100.ReqBodyPUSH0100VO;
import com.bccns.umsserviceweb.push.vo.PUSH0100.ReqPUSH0100VO;
import com.bccns.umsserviceweb.push.vo.PUSH0100.ResBodyDataPUSH0110VO;
import com.bccns.umsserviceweb.push.vo.PUSH0100.ResBodyPUSH0110VO;
import com.bccns.umsserviceweb.push.vo.PUSH0100.ResPUSH0110VO;
import com.bccns.umsserviceweb.push.vo.PUSH0200.ReqBodyPUSH0200VO;
import com.bccns.umsserviceweb.push.vo.PUSH0200.ReqPUSH0200VO;
import com.bccns.umsserviceweb.push.vo.PUSH0200.ResBodyDataPUSH0210VO;
import com.bccns.umsserviceweb.push.vo.PUSH0200.ResBodyPUSH0210VO;
import com.bccns.umsserviceweb.push.vo.PUSH0200.ResPUSH0210VO;
import com.bccns.umsserviceweb.push.vo.PUSH0300.ReqBodyPUSH0300VO;
import com.bccns.umsserviceweb.push.vo.PUSH0300.ReqPUSH0300VO;
import com.bccns.umsserviceweb.push.vo.PUSH0300.ResBodyDataPUSH0310VO;
import com.bccns.umsserviceweb.push.vo.PUSH0300.ResBodyPUSH0310VO;
import com.bccns.umsserviceweb.push.vo.PUSH0300.ResPUSH0310VO;
import com.bccns.umsserviceweb.ums.vo.UmsImportResultVO;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/api")
public class PulbicPushAPIController extends DefaultAPIController {
	private static final Logger log = LoggerFactory.getLogger(PulbicPushAPIController.class);

	
	@Value("${property.file.upload.file}") 			String fileUploadFile;
	@Value("${property.file.view.image}") 			String fileViewFile;
	@Value("${property.file.upload.image}") 		String fileUploadImage;

	@Value("${property.file.upload.image.size}") 		Integer fileUploadImageSize;
	@Value("${property.file.upload.file.size}") 		Integer fileUploadFileSize;
	
	@Value("${property.push.view.image}") 		String pushViewImage;
	
	@Value("${property.push.API_KEY}")
    private static String API_KEY;
    
//    @Resource
//    @Qualifier("pushService_71")
//    PrivatePushService pushService_71;
//    
//    @Autowired
//    @Qualifier("pushService")
//    PushService pushService;
	
	@Autowired
	PrivatePushService privatePushService;
	@Autowired
	PushService pushService;
	
    
    @RequestMapping(value = "/set_PushRegID_regist.api", headers="Accept=application/xml, application/json")
    public @ResponseBody ResponseEntity<String> registPushRegID(@RequestBody ReqPUSH0100VO reqPUSH0100VO
            , HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
        
    	/**
    	 * process
    	 * : get -> check -> set -> action
    	 */
    	
        //1. get

        Integer res = 0;
        XSSFilter xssFilter = new XSSFilter();
        //common
        RexHeaderVO header = new RexHeaderVO();
        RexHeaderVO reqHeader = reqPUSH0100VO.getHeader();
        
        //req
        ReqBodyPUSH0100VO reqBody = reqPUSH0100VO.getBody();
        
        //res
        ResPUSH0110VO resVO = new ResPUSH0110VO();
        ResBodyResultVO resBodyResult = new ResBodyResultVO();
        ResBodyDataPUSH0110VO resBodyData = new ResBodyDataPUSH0110VO();
        ResBodyPUSH0110VO resBody = new ResBodyPUSH0110VO();
        
        log.debug("reqBody = "+reqBody.toString());

        //2. check (validation)
        
        if(reqHeader.getSvcId() == null || reqHeader.getSvcId() == "" ) {
        	resBodyResult.setCode(PushConst.RESULT_ERR_00001);
            resBodyResult.setMessage(PushConst.resultMap.get(PushConst.RESULT_ERR_00001));
        }
        
        if(!reqHeader.getSvcId().equals("PUSH0100")) {
        	resBodyResult.setCode(PushConst.RESULT_ERR_00002);
            resBodyResult.setMessage(PushConst.resultMap.get(PushConst.RESULT_ERR_00002));
        }
        
        if(reqBody.getPhoneNo() == null || reqBody.getPhoneNo() == "" ||
           reqBody.getRegId() == null || reqBody.getRegId() == "") {
        	resBodyResult.setCode(PushConst.RESULT_ERR_00003);
            resBodyResult.setMessage(PushConst.resultMap.get(PushConst.RESULT_ERR_00003));
        }
        
        //3. set
        resBodyData.setPhoneNo(reqBody.getPhoneNo());
        resBodyData.setRegId(reqBody.getRegId());
        
        try{
        	res = pushService.createPushRegId(reqBody);
            log.debug("res ================== "+res);
            if(res == 0){
            	resBodyResult.setCode(PushConst.RESULT_ERR_00100);
                resBodyResult.setMessage(PushConst.resultMap.get(PushConst.RESULT_ERR_00100));
            }
        }catch(Exception e){
            log.debug("createPushRegId Fail = "+e.getMessage());
            resBodyResult.setCode(PushConst.RESULT_ERR_00101);
            resBodyResult.setMessage(PushConst.resultMap.get(PushConst.RESULT_ERR_00101));
        }
        
        if(resBodyResult.getCode() == null || resBodyResult.getMessage() == ""){
	        
	        header.setSvcId("PUSH0110");
	        resVO.setHeader(header);
	        resBodyResult.setCode(PushConst.RESULT_SUC_00000);
            resBodyResult.setMessage(PushConst.resultMap.get(PushConst.RESULT_SUC_00000));
        }
        
        resBody.setData(resBodyData);        
        resBody.setResult(resBodyResult);
        
        resVO.setBody(resBody);
        log.debug("resVO : " +resVO.toJsonStr());
        //resVO.setBody(new XSSDomainFilter<ResBodyPUSH0110VO>().getFilterdObject(resBody));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<String>(resVO.toJsonStr(), responseHeaders, HttpStatus.OK);
        //return resVO.toJsonStr();
    }
    
    @RequestMapping(value = "/req_PushMsgSend.api", headers="Accept=application/xml, application/json")
    public @ResponseBody ResponseEntity<String> reqPushMsgSend(@RequestBody ReqPUSH0200VO reqPUSH0200VO
            , HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
        
    	/**
    	 * process
    	 * : get -> check -> set -> action
    	 */
    	
        //1. get
    	String pushResult = "";
        Integer res = 0;
        XSSFilter xssFilter = new XSSFilter();
        //common
        RexHeaderVO header = new RexHeaderVO();
        RexHeaderVO reqHeader = reqPUSH0200VO.getHeader();
        
        //req
        ReqBodyPUSH0200VO reqBody = reqPUSH0200VO.getBody();
        
        //res
        ResPUSH0210VO resVO = new ResPUSH0210VO();
        ResBodyResultVO resBodyResult = new ResBodyResultVO();
        ResBodyDataPUSH0210VO resBodyData = new ResBodyDataPUSH0210VO();
        ResBodyPUSH0210VO resBody = new ResBodyPUSH0210VO();
        
        log.debug("reqBody = "+reqBody.toString());

        //2. check (validation)
        
        if(reqHeader.getSvcId() == null || reqHeader.getSvcId() == "" ) {
        	resBodyResult.setCode(PushConst.RESULT_ERR_00001);
            resBodyResult.setMessage(PushConst.resultMap.get(PushConst.RESULT_ERR_00001));
        }
        
        if(!reqHeader.getSvcId().equals("PUSH0200")) {
        	resBodyResult.setCode(PushConst.RESULT_ERR_00002);
            resBodyResult.setMessage(PushConst.resultMap.get(PushConst.RESULT_ERR_00002));
        }
        
        if(reqBody.getPhoneNo() == null || reqBody.getPhoneNo() == "" ) {
        	resBodyResult.setCode(PushConst.RESULT_ERR_00003);
            resBodyResult.setMessage(PushConst.resultMap.get(PushConst.RESULT_ERR_00003));
        }
        
        //3. set
        resBodyData.setPhoneNo(reqBody.getPhoneNo());
        resBodyData.setApiKey(reqBody.getApiKey());
        resBodyData.setSendDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
        
        try{
        	//res = pushService_71.createPushSendMsg(reqBody);
        	
        	PmsSendVO pmsSendVO = new PmsSendVO();
        	PmsSendVO getPmsSendVO = new PmsSendVO();
        	pmsSendVO.setApiKey(reqBody.getApiKey());
        	pmsSendVO.setMessage(reqBody.getMessage());
        	pmsSendVO.setNum(""); 

        	pmsSendVO.setDocType(reqBody.getDocType());
        	pmsSendVO.setPhoneNo(reqBody.getPhoneNo());
        	log.debug("pmsSendVO : "+pmsSendVO.toString());

        	getPmsSendVO = pushService.getPushRegId(pmsSendVO);
        	log.debug("getPmsSendVO : "+getPmsSendVO.toString());

        	pmsSendVO.setRegId(getPmsSendVO.getRegId());
        	pmsSendVO.setSendDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
        	pmsSendVO.setSender(reqBody.getSender());
        	pmsSendVO.setTitle(reqBody.getTitle());
        	
        	log.debug("pmsSendVO : "+pmsSendVO.toString());

//        	if(request.getParameter("file_servername") != null){
//	        	pmsSendVO.setImageUrl(pushViewImage + fileViewFile + request.getParameter("file_servername"));
//	        	pmsSendVO.setImageYn("Y");
//        	}else{
//        		pmsSendVO.setImageYn("N");
//        		//pmsSendVO.setImageUrl("");
//        	}
//        	if(pmsVO.getScheduleType().equals("NOW")){
//        		pmsSendVO.setReserveDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
//        	}else{
//        		pmsSendVO.setReserveDt(reserveDate);
//        	}
        	pmsSendVO.setReserveDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
        	
        	log.debug("pmsSendVO : "+pmsSendVO.toString());
        	
        	log.debug("pmsSendVOJson : "+pmsSendVO.toJsonStr());

        	pushResult = PushUtil.pushSend(pmsSendVO,getPmsSendVO.getRegId());
        	
        	log.debug("res ================== "+res);
//            if(res == 0){
//            	resBodyResult.setCode(PushConst.RESULT_ERR_00100);
//                resBodyResult.setMessage(PushConst.resultMap.get(PushConst.RESULT_ERR_00100));
//            }
        }catch(Exception e){
            log.debug("createPushRegId Fail = "+e.getMessage());
            resBodyResult.setCode(PushConst.RESULT_ERR_00101);
            resBodyResult.setMessage(PushConst.resultMap.get(PushConst.RESULT_ERR_00101));
        }
        
        if(resBodyResult.getCode() == null || resBodyResult.getMessage() == ""){
	        
	        header.setSvcId("PUSH0210");
	        resVO.setHeader(header);
	        resBodyResult.setCode(PushConst.RESULT_SUC_00001);
            resBodyResult.setMessage(PushConst.resultMap.get(PushConst.RESULT_SUC_00001));
        }
        
        resBody.setData(resBodyData);        
        resBody.setResult(resBodyResult);
        
        resVO.setBody(resBody);
        log.debug("resVO : " +resVO.toJsonStr());
        //resVO.setBody(new XSSDomainFilter<ResBodyPUSH0110VO>().getFilterdObject(resBody));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<String>(resVO.toJsonStr(), responseHeaders, HttpStatus.OK);
        //return resVO.toJsonStr();
    }
    
    /**
	 * 이미지 등록 업로드 처리
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/req_ImageUploadProcess.api", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody ResponseEntity<String> registImagePopupAjax(
		ModelMap model, final HttpServletRequest request, final HttpServletResponse response) {
		
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String result = "";

        String uploadUserId = request.getParameter("uploadUserId");
        if(uploadUserId == null || uploadUserId.equals("")) {
            uploadUserId = UmsServiceWebSession.getSessionUserId(request);;
        }
        List<MultipartFile> files = multipartRequest.getFiles("uploadFile");
        MultipartFile file = files.get(0);
        
        log.debug(" upload userId = " + uploadUserId);
        log.debug(" upload file name = " + file.getOriginalFilename());
        log.debug(" upload file name = ");
        
		Map<String, Object> mapResult = new HashMap<String, Object>();

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
			log.debug("path:"+sRootPath);
			log.debug("fileExt:"+fileExt);
			
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
                	log.debug("sRootPath:"+sRootPath+sSvrFileName);
                	log.debug("fileViewFile:"+fileViewFile);
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
                log.debug("==============>mapResult:"+mapResult.toString());
				
            }
			log.debug("mapResult:"+mapResult.toString());
			
			//업로드결과 푸시전송
	        ReqPUSH0200VO reqPUSH0200VO = JSONParser.toObject(request.getParameter("data"), ReqPUSH0200VO.class);
				
			result = reqPushMsgSend(request, reqPUSH0200VO, mapResult);
			
			log.debug("result:"+result.toString());

        } catch (IOException e) {
            log.error("Upload file parsing error :: IOException --> " + e.getMessage());
            resultVO.setResult(false);
            resultVO.setErrMsg("Upload process error :: file io exception.");
        } catch (Exception e) {
            log.error("Upload file parsing error :: Exception --> " + e.getMessage());
            resultVO.setResult(false);
            resultVO.setErrMsg("Upload process error :: System exception occured.");
        }  
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<String>(result, responseHeaders, HttpStatus.OK);
    }
	
	
	public String reqPushMsgSend(HttpServletRequest request, ReqPUSH0200VO reqPUSH0200VO
			, Map<String, Object> mapResult) throws JsonGenerationException, JsonMappingException, IOException {
		
    	/**
    	 * process
    	 * : get -> check -> set -> action
    	 */
    	
		//1. get
    	String pushResult = "";
        Integer res = 0;
        XSSFilter xssFilter = new XSSFilter();
        //common
        RexHeaderVO header = new RexHeaderVO();
        RexHeaderVO reqHeader = reqPUSH0200VO.getHeader();
        
        //req
        ReqBodyPUSH0200VO reqBody = reqPUSH0200VO.getBody();
        
        //res
        ResPUSH0210VO resVO = new ResPUSH0210VO();
        ResBodyResultVO resBodyResult = new ResBodyResultVO();
        ResBodyDataPUSH0210VO resBodyData = new ResBodyDataPUSH0210VO();
        ResBodyPUSH0210VO resBody = new ResBodyPUSH0210VO();
        
        log.debug("reqBody = "+reqBody.toString());

        //2. check (validation)
        
        if(reqHeader.getSvcId() == null || reqHeader.getSvcId() == "" ) {
        	resBodyResult.setCode(PushConst.RESULT_ERR_00001);
            resBodyResult.setMessage(PushConst.resultMap.get(PushConst.RESULT_ERR_00001));
        }
        
        if(!reqHeader.getSvcId().equals("PUSH0200")) {
        	resBodyResult.setCode(PushConst.RESULT_ERR_00002);
            resBodyResult.setMessage(PushConst.resultMap.get(PushConst.RESULT_ERR_00002));
        }
        
        if(reqBody.getPhoneNo() == null || reqBody.getPhoneNo() == "" ) {
        	resBodyResult.setCode(PushConst.RESULT_ERR_00003);
            resBodyResult.setMessage(PushConst.resultMap.get(PushConst.RESULT_ERR_00003));
        }
        
        //3. set
        resBodyData.setPhoneNo(reqBody.getPhoneNo());
        resBodyData.setApiKey(reqBody.getApiKey());
        resBodyData.setSendDt(DateUtils.getSysDate("yyyyMMddHHmmss"));

        try{
        	//res = pushService_71.createPushSendMsg(reqBody);
        	
        	PmsSendVO pmsSendVO = new PmsSendVO();
        	PmsSendVO getPmsSendVO = new PmsSendVO();
        	pmsSendVO.setApiKey(reqBody.getApiKey());
        	pmsSendVO.setMessage(reqBody.getMessage());
        	pmsSendVO.setNum(""); 
        	log.debug("DocType : "+reqBody.getDocType()); 
        	pmsSendVO.setDocType(reqBody.getDocType());
        	pmsSendVO.setPhoneNo(reqBody.getPhoneNo());
        	getPmsSendVO = pushService.getPushRegId(pmsSendVO);
        	log.debug("getPmsSendVO : "+getPmsSendVO.toString());

        	pmsSendVO.setRegId(getPmsSendVO.getRegId());
        	pmsSendVO.setSendDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
        	pmsSendVO.setSender(reqBody.getSender());
        	pmsSendVO.setTitle(reqBody.getTitle());
        	if(mapResult.get("file_svr_name") != null){
	        	pmsSendVO.setImageUrl(pushViewImage + fileViewFile + mapResult.get("file_svr_name"));
	        	resBodyData.setImageUrl(pushViewImage + fileViewFile + mapResult.get("file_svr_name"));
	        	pmsSendVO.setImageYn("Y");
	        	resBodyData.setImageYn("Y");

        	}else{
        		pmsSendVO.setImageYn("N");
        		resBodyData.setImageYn("N");
        		//pmsSendVO.setImageUrl("");
        	}
//        	if(pmsVO.getScheduleType().equals("NOW")){
//        		pmsSendVO.setReserveDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
//        	}else{
//        		pmsSendVO.setReserveDt(reserveDate);
//        	}
        	pmsSendVO.setReserveDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
        	log.debug("pmsSendVOJson : "+pmsSendVO.toJsonStr());
        	pushResult = PushUtil.pushSend(pmsSendVO,getPmsSendVO.getRegId());
        	log.debug("res ================== "+res);
//            if(res == 0){
//            	resBodyResult.setCode(PushConst.RESULT_ERR_00100);
//                resBodyResult.setMessage(PushConst.resultMap.get(PushConst.RESULT_ERR_00100));
//            }
        }catch(Exception e){
            log.debug("createPushRegId Fail = "+e.getMessage());
            resBodyResult.setCode(PushConst.RESULT_ERR_00101);
            resBodyResult.setMessage(PushConst.resultMap.get(PushConst.RESULT_ERR_00101));
        }
        
        if(resBodyResult.getCode() == null || resBodyResult.getMessage() == ""){
	        
	        header.setSvcId("PUSH0210");
	        resVO.setHeader(header);
	        resBodyResult.setCode(PushConst.RESULT_SUC_00001);
            resBodyResult.setMessage(PushConst.resultMap.get(PushConst.RESULT_SUC_00001));
        }
        
        resBody.setData(resBodyData);        
        resBody.setResult(resBodyResult);
        
        resVO.setBody(resBody);
        log.debug("resVO : " +resVO.toJsonStr());
        //resVO.setBody(new XSSDomainFilter<ResBodyPUSH0110VO>().getFilterdObject(resBody));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
        return resVO.toJsonStr();
    }
    
	
	@RequestMapping(value = "/req_BeaconSigSend.api", headers="Accept=application/xml, application/json")
    public @ResponseBody ResponseEntity<String> BeaconSigSend(@RequestBody ReqPUSH0300VO reqPUSH0300VO
            , HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
        
    	/**
    	 * process
    	 * : get -> check -> set -> action
    	 */
    	
        //1. get
    	String pushResult = "";
        Integer res = 0;
        XSSFilter xssFilter = new XSSFilter();
        //common
        RexHeaderVO header = new RexHeaderVO();
        RexHeaderVO reqHeader = reqPUSH0300VO.getHeader();
        
        //req
        ReqBodyPUSH0300VO reqBody = reqPUSH0300VO.getBody();
        
        //res
        ResPUSH0310VO resVO = new ResPUSH0310VO();
        ResBodyResultVO resBodyResult = new ResBodyResultVO();
        ResBodyDataPUSH0310VO resBodyData = new ResBodyDataPUSH0310VO();
        ResBodyPUSH0310VO resBody = new ResBodyPUSH0310VO();
        
        log.debug("reqBody = "+reqBody.toString());

        //2. check (validation)
        
        if(reqHeader.getSvcId() == null || reqHeader.getSvcId() == "" ) {
        	resBodyResult.setCode(PushConst.RESULT_ERR_00001);
            resBodyResult.setMessage(PushConst.resultMap.get(PushConst.RESULT_ERR_00001));
        }
        
        if(!reqHeader.getSvcId().equals("PUSH0300")) {
        	resBodyResult.setCode(PushConst.RESULT_ERR_00002);
            resBodyResult.setMessage(PushConst.resultMap.get(PushConst.RESULT_ERR_00002));
        }
        
        if(reqBody.getPhoneNo() == null || reqBody.getPhoneNo() == "" ) {
        	resBodyResult.setCode(PushConst.RESULT_ERR_00003);
            resBodyResult.setMessage(PushConst.resultMap.get(PushConst.RESULT_ERR_00003));
        }
        
        //3. set
        resBodyData.setPhoneNo(reqBody.getPhoneNo());
        resBodyData.setApiKey(reqBody.getApiKey());
        resBodyData.setSendDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
        resBodyData.setMacAddr(reqBody.getMacAddr());
        resBodyData.setMajor(reqBody.getMajor());
        resBodyData.setMinor(reqBody.getMinor());
        resBodyData.setUuid(reqBody.getUuid());
        
        try{
        	PmsPointMsgVO reqPmsPointMsgVO = new PmsPointMsgVO();
        	PmsPointMsgVO pmsPointMsgVO = new PmsPointMsgVO();
        	
        	reqPmsPointMsgVO.setMacAddr(reqBody.getMacAddr());
        	
        	pmsPointMsgVO = pushService.getPushPointMsg(reqPmsPointMsgVO);
        	
        	if(pmsPointMsgVO.getMsgNo() == null || pmsPointMsgVO.getMsgNo() == ""){
    	        
    	        header.setSvcId("PUSH0310");
    	        resVO.setHeader(header);
    	        resBodyResult.setCode(PushConst.RESULT_ERR_00300);
                resBodyResult.setMessage(PushConst.resultMap.get(PushConst.RESULT_ERR_00300));
            }else{
        	
	        	PmsSendVO pmsSendVO = new PmsSendVO();
	        	PmsSendVO getPmsSendVO = new PmsSendVO();
	        	pmsSendVO.setApiKey(reqBody.getApiKey());
	        	pmsSendVO.setMessage(pmsPointMsgVO.getContents1());
	        	
	        	pmsSendVO.setNum(""); 
	
	        	pmsSendVO.setDocType("01");
	        	pmsSendVO.setPhoneNo(reqBody.getPhoneNo());
	        	log.debug("pmsSendVO : "+pmsSendVO.toString());
	
	        	getPmsSendVO = pushService.getPushRegId(pmsSendVO);
	        	log.debug("getPmsSendVO : "+getPmsSendVO.toString());
	
	        	pmsSendVO.setRegId(getPmsSendVO.getRegId());
	        	pmsSendVO.setSendDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
	        	pmsSendVO.setSender(reqBody.getUuid());
	        	pmsSendVO.setTitle(pmsPointMsgVO.getSubject());
	        	
	        	log.debug("pmsSendVO : "+pmsSendVO.toString());
	
	        	pmsSendVO.setImageUrl(pushViewImage + fileViewFile + pmsPointMsgVO.getContents2());
	        	pmsSendVO.setImageYn("Y");
	        	
	//        	if(pmsVO.getScheduleType().equals("NOW")){
	//        		pmsSendVO.setReserveDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
	//        	}else{
	//        		pmsSendVO.setReserveDt(reserveDate);
	//        	}
	        	pmsSendVO.setReserveDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
	        	
	        	log.debug("pmsSendVO : "+pmsSendVO.toString());
	        	
	        	log.debug("pmsSendVOJson : "+pmsSendVO.toJsonStr());
	
	        	pushResult = PushUtil.pushSend(pmsSendVO,getPmsSendVO.getRegId());
	        	

	        	resBodyData.setImageUrl(pushViewImage + fileViewFile + pmsPointMsgVO.getContents2());
	        	resBodyData.setImageYn("Y");
	        	resBodyData.setNum("");
	        	
	        	log.debug("pushResult ================== "+pushResult);
	//            if(res == 0){
	//            	resBodyResult.setCode(PushConst.RESULT_ERR_00100);
	//                resBodyResult.setMessage(PushConst.resultMap.get(PushConst.RESULT_ERR_00100));
	//            }
            }
        }catch(Exception e){
            log.debug("BeaconSigSend Fail = "+e.getMessage());
            resBodyResult.setCode(PushConst.RESULT_ERR_00100);
            resBodyResult.setMessage(PushConst.resultMap.get(PushConst.RESULT_ERR_00100));
        }
        
        if(resBodyResult.getCode() == null || resBodyResult.getMessage() == ""){
	        
	        header.setSvcId("PUSH0310");
	        resVO.setHeader(header);
	        
	        resBodyResult.setCode(PushConst.RESULT_SUC_00001);
            resBodyResult.setMessage(PushConst.resultMap.get(PushConst.RESULT_SUC_00001));
        }
        
        resBody.setData(resBodyData);        
        resBody.setResult(resBodyResult);
        
        resVO.setBody(resBody);
        log.debug("resVO : " +resVO.toJsonStr());
        //resVO.setBody(new XSSDomainFilter<ResBodyPUSH0110VO>().getFilterdObject(resBody));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<String>(resVO.toJsonStr(), responseHeaders, HttpStatus.OK);
        //return resVO.toJsonStr();
    }
    
}
