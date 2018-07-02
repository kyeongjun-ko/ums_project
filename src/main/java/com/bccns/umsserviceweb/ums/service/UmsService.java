package com.bccns.umsserviceweb.ums.service;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.bccns.umsserviceweb.common.vo.CommCodeVO;
import com.bccns.umsserviceweb.common.vo.UmsUserVO;
import com.bccns.umsserviceweb.mgr.vo.MsgBoxVO;
import com.bccns.umsserviceweb.notice.vo.NoticeVO;
import com.bccns.umsserviceweb.notice.vo.SearchNoticeVO;
import com.bccns.umsserviceweb.ums.exception.UmsSendProcException;
import com.bccns.umsserviceweb.ums.vo.SmsVO;
import com.bccns.umsserviceweb.ums.vo.MmsVO;
import com.bccns.umsserviceweb.ums.vo.UmsImportResultVO;
import com.bccns.umsserviceweb.ums.vo.VmsVO;
import com.bccns.umsserviceweb.ums.vo.FmsVO;


public interface UmsService {
	public Integer createSms(SmsVO smsVO) throws UmsSendProcException;
	
	public Integer createLms(SmsVO smsVO) throws UmsSendProcException;
	
	public Integer createMms(MmsVO mmsVO)  throws UmsSendProcException;
	
	public Integer createVms(VmsVO vmsVO)  throws UmsSendProcException;
	
	public Integer createVmsQr(VmsVO vmsVO)  throws UmsSendProcException;
	
	public Integer createFms(FmsVO FmsVO)  throws UmsSendProcException;
    
	/*주소록 조회*/
	public List<NoticeVO> getAddrList(SearchNoticeVO searchNoticeVO);
	
	/*메세지모음함 조회*/
    public List<NoticeVO> getMsgList(SearchNoticeVO searchNoticeVO);

	public UmsImportResultVO processAddrListExcelUpload(InputStream inputStream, String uploadUserId) throws Exception;
	public UmsImportResultVO processAddrListExcelUpload2(InputStream inputStream, String uploadUserId) throws Exception;
	public UmsImportResultVO processAddrListExcelUploadInsert(InputStream inputStream, String uploadUserId, String grpNo) throws Exception;
	public UmsImportResultVO processAddrListTextUpload(InputStream inputStream, String uploadUserId) throws Exception;
	
	public Integer processImageUpload(InputStream inputStream) throws Exception;
      
	public List<CommCodeVO> getUsrCallBackList(UmsUserVO umsUserVO);
	
	public Integer createmsgBox(MsgBoxVO msgBoxVO);
	
	public Integer modifySmsSmartDM(SmsVO smsVO);
	
	public Integer modifyMmsSmartDM(MmsVO mmsVO);
}
