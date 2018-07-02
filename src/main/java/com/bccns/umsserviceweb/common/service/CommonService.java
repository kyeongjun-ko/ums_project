package com.bccns.umsserviceweb.common.service;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.callback.Callback;

import org.springframework.stereotype.Repository;

import com.bccns.umsserviceweb.common.vo.*;

@Repository
public interface CommonService {
	
	public MobileVO getMobileApi(MobileVO movileVO);
	
	public Integer modifyMobileApi(MobileVO mobileVO);
	
	public Integer modifyMobileApi2(MobileVO mobileVO);
	
	public Integer modifyMobileUpdateApi(MobileVO mobileVO);
	
	public UmsUserVO getUserInfo(UmsUserVO umsUserVO);
	
	public UmsUserVO getUserInfoD(UmsUserVO umsUserVO);
	
	public UmsUserVO getUserInfoD2(UmsUserVO umsUserVO);
	
	public UmsUserVO getUserId(UmsUserVO umsUserVO);
	
	public UmsUserVO getUserPw(UmsUserVO umsUserVO);
	
	public Integer createUser(UmsUserVO umsUserVO);
	
	public Integer modifyUser(UmsUserVO umsUserVO);
	
	public Integer modifyPassword(UmsUserVO umsUserVO);
	
	public Integer createLog(UsrLogVO usrLogVO);
	
	public Integer modifyLoginTime(UmsUserVO umsUserVO);
	
	public Integer getIdValid(UmsUserVO umsUserVO);
	
	public List<UsrGrpVO> getUsrGrpList(UsrGrpVO usrGrpVO);
	
	public List<UsrGrpVO> getUsrGrpDmList(UsrGrpVO usrGrpVO);
	
	public List<CommCodeVO> getCodeList(CommCodeVO commCodeVO);
	
	public Integer createCode(CommCodeVO commCodeVO);
	
	public CommCodeVO getCodeValAddCom(CommCodeVO commCodeVO);
	
	public CommCodeVO getCodeValAddDept(CommCodeVO commCodeVO);
	
	public List<CommCodeVO> getUserIdCodeList(UmsUserVO umsUserVO);
	
	public List<SearchUmsUserVO> getApplUsrList(SearchUmsUserVO searchUmsUserVO);
	
	public Integer getApplUsrListCount(SearchUmsUserVO searchUmsUserVO);
	
	public Integer modifyUmsUserAppl(UmsUserVO umsUserVO);
	
	public UmsUserVO getSeekId(UmsUserVO umsUserVO);
	
	public UmsUserVO getSeekPw(UmsUserVO umsUserVO);
	
	public List<CallBackVO> getCallBackInfo(CallBackVO callBackVO);
	
	public Integer createAuthCode(CallBackVO callBackVO);
	
	public Integer delAuthCode(CallBackVO callBackVO);
	
	public Integer createAuthSms(CallBackVO callBackVO);
	
	public Integer createCallbackInfo(CallBackVO callBackVO);
	
	public Integer modifyCallbackInfo(CallBackVO callBackVO);
	
	public Integer delCallbackInfo(CallBackVO callBackVO);
	
	public Integer createCallbackInfoHis(CallBackVO callBackVO);
	
	public List<CallBackVO> getGrpCallbackList(SearchUmsUserVO searchUmsUserVO);
	
	public Integer getGrpCallbackListCount(SearchUmsUserVO searchUmsUserVO);
	
	public List<CallBackVO> getApplCallbackList(SearchUmsUserVO searchUmsUserVO);
	
	public Integer getApplCallbackListCount(SearchUmsUserVO searchUmsUserVO);

	public List<CommCodeVO> getCallbackList(String userId);

}
