package com.bccns.umsserviceweb.ums.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bccns.umsserviceweb.common.service.CommonService;
import com.bccns.umsserviceweb.common.util.StringUtil;
import com.bccns.umsserviceweb.common.vo.CommCodeVO;
import com.bccns.umsserviceweb.common.vo.UmsUserVO;
import com.bccns.umsserviceweb.mgr.service.AddrService;
import com.bccns.umsserviceweb.mgr.service.MsgBoxService;
import com.bccns.umsserviceweb.mgr.vo.AddrVO;
import com.bccns.umsserviceweb.mgr.vo.MsgBoxVO;
import com.bccns.umsserviceweb.notice.dao.NoticeDAO;
import com.bccns.umsserviceweb.notice.vo.NoticeVO;
import com.bccns.umsserviceweb.notice.vo.SearchNoticeVO;
import com.bccns.umsserviceweb.push.conf.PushConst;
import com.bccns.umsserviceweb.ums.dao.UmsDAO;
import com.bccns.umsserviceweb.ums.service.util.umsExcelParsingUtil;
import com.bccns.umsserviceweb.ums.service.util.umsExcelParsingUtil2;
import com.bccns.umsserviceweb.ums.vo.AddrExcelInsertVO;
import com.bccns.umsserviceweb.ums.vo.AddrExcelVO;
import com.bccns.umsserviceweb.ums.vo.AddrExcelVO2;
import com.bccns.umsserviceweb.ums.vo.SmsVO;
import com.bccns.umsserviceweb.ums.vo.MmsVO;
import com.bccns.umsserviceweb.ums.vo.FmsVO;
import com.bccns.umsserviceweb.ums.vo.UmsExcelVO;
import com.bccns.umsserviceweb.ums.vo.UmsImportResultVO;
import com.bccns.umsserviceweb.ums.vo.VmsVO;

@Service
public class UmsServiceImpl implements UmsService{
	private static final Logger log = LoggerFactory.getLogger(UmsServiceImpl.class);
	@Autowired
	CommonService commonService;
	
	@Autowired
	MsgBoxService msgBoxService;
	
	@Autowired
	AddrService addrService;
	
	@Autowired
	private UmsDAO umsDAO;

	@Override
	public Integer createSms(SmsVO smsVO) {
		// TODO Auto-generated method stub
		return umsDAO.createSms(smsVO);
	}
	
	@Override
	public Integer createLms(SmsVO smsVO) {
		// TODO Auto-generated method stub
		return umsDAO.createLms(smsVO);
	}


	@Override
	public Integer createMms(MmsVO mmsVO) {
		// TODO Auto-generated method stub
		return umsDAO.createMms(mmsVO);
	}

	@Override
	public Integer createVms(VmsVO vmsVO) {
		// TODO Auto-generated method stub
		return umsDAO.createVms(vmsVO);
	}

	@Override
	public Integer createVmsQr(VmsVO vmsVO) {
		// TODO Auto-generated method stub
		return umsDAO.createVmsQr(vmsVO);
	}

	@Override
	public Integer createFms(FmsVO fmsVO) {
		// TODO Auto-generated method stub
		return umsDAO.createFms(fmsVO);
	}

	@Override
	public List<NoticeVO> getAddrList(SearchNoticeVO searchNoticeVO) {
		// TODO Auto-generated method stub
		return umsDAO.getAddrList(searchNoticeVO);
	}

	@Override
	public List<NoticeVO> getMsgList(SearchNoticeVO searchNoticeVO) {
		// TODO Auto-generated method stub
		return umsDAO.getMsgList(searchNoticeVO);
	}

	@Override
	public UmsImportResultVO processAddrListExcelUpload(InputStream inputStream, String uploadUserId) throws InvalidFormatException, IOException {
		// TODO Auto-generated method stub
		log.debug("######################## Addr excel file upload start!!");
         
		int cntReceiver = 0;
		UmsImportResultVO resultVo = new UmsImportResultVO();
		StringBuilder html = new StringBuilder();
        //excel inputStream parsing
        List<AddrExcelVO> addrUploadList = umsExcelParsingUtil.parsingAddrExcelFile(inputStream);
        
        log.debug("processAddrListExcelUpload : addrUploadList = " + addrUploadList.toString());
        //validation addrlist db 등록
        for(AddrExcelVO content : addrUploadList) {
            try {
            	UmsExcelVO umsExcelVO = new UmsExcelVO();
            	
            	// input validadtion
            	if(!StringUtil.isNumberic(content.getPhoneNo())){
            		resultVo.setResult(false);
            		resultVo.setErrMsg("Upload process error :: Invalid data format exception.");
            		return resultVo;
                }
            	
            	umsExcelVO.setUserId(uploadUserId);
            	umsExcelVO.setExcelCd("01");//01:sender excel, 02:addr excel,
            	umsExcelVO.setContents1(content.getAddrName());
            	umsExcelVO.setContents2(content.getPhoneNo());
            	umsExcelVO.setInstId(uploadUserId);
            	umsExcelVO.setUpdtDt(uploadUserId);
            	  
            	String str1 = content.getAddrName();
            	String str2 = content.getPhoneNo();
            	str1.replaceFirst("^[\\x00-\\x200\\xA0]+", "").replaceFirst("[\\x00-\\x20\\xA0]+$", "");
            	str1 = StringUtil.getHanEngNumber(str1);
            	str1 = StringUtil.getSTRFilter(str1).trim();
            	str1.replace(" ", "").replace("&nbsp;", ""); 
            	if(StringUtil.lengthB(str1) > 16) str1 = StringUtil.substrB(str1, 1, 16);
            	
            	str2.replaceFirst("^[\\x00-\\x200\\xA0]+", "").replaceFirst("[\\x00-\\x20\\xA0]+$", "");
            	str2 = StringUtil.getNumber(str2);
            	if(StringUtil.lengthB(str2) > 20) str2 = StringUtil.substrB(str2, 1, 20);
            	
        		cntReceiver++;
            	html.append("<li id='SubReceiveContent").append(cntReceiver).append("' >");
            	html.append("<input type='text' id='SubReceiverName").append(cntReceiver).append("' class='input-sm name' value='").append(str1).append("'>");
            	html.append("<input type='text' id='SubReceiverValue").append(cntReceiver).append("' class='input-sm number' value='").append(str2).append("'>");
            	html.append("<button class='btn btn-link btn-xs' onclick='delReceiver(").append(cntReceiver).append(")' >삭제</button>");
            	html.append("<input type='hidden' name='receiverInfo' value='").append(str1).append("^").append(str2).append("|'/>");
            	html.append("<input type='hidden' name='receiverPhone' value='").append(str2).append("' />");
            	html.append("</li>");
            	
                resultVo.addAddrExcelList(content);                
            } catch (Exception e) {
                log.error("addr content insert fail :: " + e.getMessage());
            }            
        }
        resultVo.setHtml(html.toString());
        resultVo.setResult(true);
        log.debug("######################## res ={  "+html.toString()+"  }");
        log.debug("######################## addr excel file upload end!!");
        log.debug("######################## resultVo toJsonStr !!"+resultVo.toJsonStr());
        log.debug("######################## resultVo toString !!"+resultVo.toString());
        
        return resultVo;
	}

	
	@Override
	public UmsImportResultVO processAddrListExcelUploadInsert(InputStream inputStream, String uploadUserId, String grpNo) throws InvalidFormatException, IOException {
		// TODO Auto-generated method stub
		log.debug("######################## Addr excel file upload start!!");
		Integer res = 0;
		int cntReceiver = 0;
		UmsImportResultVO resultVo = new UmsImportResultVO();
		StringBuilder html = new StringBuilder();
        //excel inputStream parsing
        List<AddrExcelInsertVO> addrUploadList = umsExcelParsingUtil.parsingAddrExcelInsertFile(inputStream, uploadUserId, grpNo);
         
        log.debug("processAddrListExcelUpload : addrUploadList = " + addrUploadList.toString());
        //validation addrlist db 등록
        for(AddrExcelInsertVO content : addrUploadList) {
            try {
            	AddrVO addrVO = new AddrVO();
            	addrVO.setUserId(uploadUserId);
            	addrVO.setGrpNo(grpNo);
            	addrVO.setName(content.getName());
            	
            	String str1 = content.getSmsNo();
            	String str2 = content.getVmsNo();
            	String str3 = content.getFmsNo();
            	
            	str1.replaceFirst("^[\\x00-\\x200\\xA0]+", "").replaceFirst("[\\x00-\\x20\\xA0]+$", "");
            	str1 = StringUtil.getNumber(str1);
            	if(StringUtil.lengthB(str1) > 20) str1 = StringUtil.substrB(str1, 1, 20);
            	
//            	str1.replaceFirst("^[\\x00-\\x200\\xA0]+", "").replaceFirst("[\\x00-\\x20\\xA0]+$", "");
//            	str1 = StringUtil.getNumber(str1).trim();
//            	str1 = StringUtil.getHanEngNumber(str1).trim();
//            	str1.replace(" ", "").replace("&nbsp;", ""); 
//            	if(StringUtil.lengthB(str1) > 16) str1 = StringUtil.substrB(str1, 1, 16);
            	str2.replaceFirst("^[\\x00-\\x200\\xA0]+", "").replaceFirst("[\\x00-\\x20\\xA0]+$", "");
            	str2 = StringUtil.getNumber(str2);
            	if(StringUtil.lengthB(str2) > 20) str2 = StringUtil.substrB(str2, 1, 20);
            	
            	str3.replaceFirst("^[\\x00-\\x200\\xA0]+", "").replaceFirst("[\\x00-\\x20\\xA0]+$", "");
            	str3 = StringUtil.getNumber(str3);
            	if(StringUtil.lengthB(str3) > 20) str3 = StringUtil.substrB(str3, 1, 20);
            	
            	addrVO.setSmsNo(str1);
            	addrVO.setVmsNo(str2);
            	addrVO.setFmsNo(str3);
            	addrVO.setNote(content.getNote());
            	res = addrService.createAddr(addrVO);
            	if(res == 0 ){
                    resultVo.setHtml("<script> alert('주소록 파일업로드 입력 성공');</script>");
            	}else {
            		resultVo.setHtml("<script> alert('주소록 파일업로드 입력 실패');</script>");            		
            	}
            } catch (Exception e) {
                log.error("addr content insert fail :: " + e.getMessage());
            }            
        }
        
        log.debug("######################## res ={  "+res+"  }");
        log.debug("######################## addr excel file upload end!!");
        log.debug("######################## resultVo toJsonStr !!"+resultVo.toJsonStr());
        log.debug("######################## resultVo toString !!"+resultVo.toString());
        
        return resultVo;
	}
	private Integer insertAddrContent(UmsExcelVO umsExcelVO) {
		// TODO Auto-generated method stub
		log.debug("######################## start insertAddrContent!! " + umsExcelVO.toString());
		return umsDAO.createAddrExcel(umsExcelVO);
	}

	@Override
	public UmsImportResultVO processAddrListTextUpload(InputStream inputStream,	String uploadUserId) throws  IOException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Integer processImageUpload(InputStream inputStream) throws Exception {
		return null;
	}
	
	@Override
	public List<CommCodeVO> getUsrCallBackList(UmsUserVO umsUserVO) {
        
        List<CommCodeVO> usrCallBackList = new ArrayList<CommCodeVO>();
        UmsUserVO userVO = null;
        try{
//        	userVO = commonService.getUserInfoD(umsUserVO);
        	usrCallBackList = commonService.getCallbackList(umsUserVO.getUserId());
        }catch(Exception e){
        	log.debug("getUserInfoD failure = "+e.getMessage());
        }
//        usrCallBackList.add(new CommCodeVO("callbackNo1", userVO.getCallbackNo1()));
//        usrCallBackList.add(new CommCodeVO("callbackNo2", userVO.getCallbackNo2()));
//        usrCallBackList.add(new CommCodeVO("callbackNo3", userVO.getCallbackNo3()));
//        usrCallBackList.add(new CommCodeVO("callbackNo4", userVO.getCallbackNo4()));
//        log.debug("getCallbackNo1 = "+userVO.getCallbackNo1());
//        log.debug("usrCallBackList = "+usrCallBackList.toString());
        return usrCallBackList;
    }
	
	@Override
	public Integer createmsgBox(MsgBoxVO msgBoxVO) {
		// TODO Auto-generated method stub
		return msgBoxService.createMsgBox(msgBoxVO);
	}
	 
	@Override
	public Integer modifySmsSmartDM(SmsVO smsVO) {
		// TODO Auto-generated method stub
		return umsDAO.modifySmsSmartDM(smsVO);
	}
	
	@Override
	public Integer modifyMmsSmartDM(MmsVO mmsVO) {
		// TODO Auto-generated method stub
		return umsDAO.modifyMmsSmartDM(mmsVO);
	}

	@Override
	public UmsImportResultVO processAddrListExcelUpload2(InputStream inputStream, String uploadUserId) throws InvalidFormatException, IOException {
		// TODO Auto-generated method stub
		log.debug("######################## Addr excel file upload start!!");
         
		int cntReceiver = 0;
		UmsImportResultVO resultVo = new UmsImportResultVO();
		StringBuilder html = new StringBuilder();
        //excel inputStream parsing
        List<AddrExcelVO2> addrUploadList = umsExcelParsingUtil2.parsingAddrExcelFile(inputStream);
        
        log.debug("processAddrListExcelUpload : addrUploadList = " + addrUploadList.toString());
        //validation addrlist db 등록
        for(AddrExcelVO2 content : addrUploadList) {
            try {
            	UmsExcelVO umsExcelVO = new UmsExcelVO();
            	
            	// input validadtion
            	if(!StringUtil.isNumberic(content.getPhoneNo())){
            		resultVo.setResult(false);
            		resultVo.setErrMsg("Upload process error :: Invalid data format exception.");
            		return resultVo;
                }
            	
            	umsExcelVO.setUserId(uploadUserId);
            	umsExcelVO.setExcelCd("01");//01:sender excel, 02:addr excel,
            	umsExcelVO.setContents1(content.getContent());
            	umsExcelVO.setContents2(content.getPhoneNo());
            	umsExcelVO.setContents3(content.getAddrName());
            	umsExcelVO.setInstId(uploadUserId);
            	umsExcelVO.setUpdtDt(uploadUserId);
            	  
            	String str1 = content.getContent();
            	String str2 = content.getPhoneNo();
            	String str3 = content.getAddrName();
            	
            	str3.replaceFirst("^[\\x00-\\x200\\xA0]+", "").replaceFirst("[\\x00-\\x20\\xA0]+$", "");
            	str3 = StringUtil.getHanEngNumber(str3);
            	str3 = StringUtil.getSTRFilter(str3).trim();
            	str3.replace(" ", "").replace("&nbsp;", ""); 
            	if(StringUtil.lengthB(str3) > 16) str3 = StringUtil.substrB(str3, 1, 16);
            	
            	str2.replaceFirst("^[\\x00-\\x200\\xA0]+", "").replaceFirst("[\\x00-\\x20\\xA0]+$", "");
            	str2 = StringUtil.getNumber(str2);
            	if(StringUtil.lengthB(str2) > 20) str2 = StringUtil.substrB(str2, 1, 20);
            	
        		cntReceiver++;
            	html.append("<li id='SubReceiveContent").append(cntReceiver).append("'>");
            	html.append("<span style='font-size:13px; float:left; margin-top:5px; font-weight:bold; margin-right:5px;'>").append(cntReceiver+". ").append("</span>");
            	html.append("<input type='text' name='SubReceiverValue").append(cntReceiver).append("'id='SubReceiverValue").append(cntReceiver).append("' class='input-sm number' style='width:30%;' value='").append(str2).append("'>");
                html.append("<input type='text' name='SubReceiverName").append(cntReceiver).append("'id='SubReceiverName").append(cntReceiver).append("' class='input-sm name' style='width:30%;' value='").append(str3).append("'>");
                html.append("<br/>");
            	html.append("<input type='text' name='smsMsg' style='border-radius:3px; width:93%; padding: 2px 5px; margin:0 2px 0 0; font-size:12px; border:1px solid #ddd; height:28px;' id='CONTENT'").append("onkeydown='checkMessageLength(this, 2000, 'SMS', 'spanMessageLength')' onkeyup='checkMessageLength(this, 2000, 'SMS', 'spanMessageLength')'  value='").append(str1).append("'>");		            	
            	html.append("<button class='btn btn-link btn-xs' onclick='delReceiver(").append(cntReceiver).append(")' >삭제</button>");
            	html.append("<input type='hidden' name='receiverInfo' value='").append(str3).append("^").append(str2).append("|,'/>");
            	html.append("<input type='hidden' name='receiverPhone' value='").append(str2).append("' />");
            	html.append("</li>");
            	
            
                resultVo.addAddrExcelList2(content);                
            } catch (Exception e) {
                log.error("addr content insert fail :: " + e.getMessage());
            }            
        }
        
        resultVo.setHtml(html.toString());
        resultVo.setResult(true);
        log.debug("######################## res ={  "+html.toString()+"  }");
        log.debug("######################## addr excel file upload end!!");
        log.debug("######################## resultVo toJsonStr !!"+resultVo.toJsonStr());
        log.debug("######################## resultVo toString !!"+resultVo.toString());
        
        return resultVo;
	}
	}
	

