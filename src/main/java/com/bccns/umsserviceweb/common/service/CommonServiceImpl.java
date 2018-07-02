package com.bccns.umsserviceweb.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bccns.umsserviceweb.common.vo.CallBackVO;
import com.bccns.umsserviceweb.common.vo.CommCodeVO;
import com.bccns.umsserviceweb.common.vo.MobileVO;
import com.bccns.umsserviceweb.common.vo.SearchUmsUserVO;
import com.bccns.umsserviceweb.common.vo.UmsUserVO;
import com.bccns.umsserviceweb.common.vo.UsrGrpVO;
import com.bccns.umsserviceweb.common.vo.UsrLogVO;
import com.bccns.umsserviceweb.common.dao.UmsUserDAO; 

@Service
public class CommonServiceImpl implements CommonService{
	private static final Logger log = LoggerFactory.getLogger(CommonServiceImpl.class);
    
	@Autowired
	private UmsUserDAO umsUserDAO;
		
	@Override
	public UmsUserVO getUserInfo(UmsUserVO umsUserVO){
		// TODO Auto-generated method stub
		return umsUserDAO.getUserInfo(umsUserVO);
	}
	@Override
	public UmsUserVO getUserInfoD(UmsUserVO umsUserVO){
		// TODO Auto-generated method stub
		return umsUserDAO.getUserInfoD(umsUserVO);
	}
	
	@Override
	public UmsUserVO getUserInfoD2(UmsUserVO umsUserVO){
		// TODO Auto-generated method stub
		return umsUserDAO.getUserInfoD2(umsUserVO);
	}
	@Override
	public UmsUserVO getUserId(UmsUserVO umsUserVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.getUserId(umsUserVO);
	}

	@Override
	public UmsUserVO getUserPw(UmsUserVO umsUserVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.getUserPw(umsUserVO);
	}

	@Override
	public Integer createUser(UmsUserVO umsUserVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.createUser(umsUserVO);
	}

	@Override
	public Integer modifyUser(UmsUserVO umsUserVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.modifyUser(umsUserVO);
	}

	@Override
	public Integer modifyPassword(UmsUserVO umsUserVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.modifyPassword(umsUserVO);
	}

	@Override
	public Integer createLog(UsrLogVO usrLogVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.createLog(usrLogVO);
	}

	@Override
	public Integer modifyLoginTime(UmsUserVO umsUserVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.modifyLoginTime(umsUserVO);
	}

	@Override
	public Integer getIdValid(UmsUserVO umsUserVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.getIdValid(umsUserVO);
	}

	@Override
	public List<UsrGrpVO> getUsrGrpList(UsrGrpVO usrGrpVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.selectUsrGrpList(usrGrpVO);
	}
	
	@Override
	public List<UsrGrpVO> getUsrGrpDmList(UsrGrpVO usrGrpVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.selectUsrGrpDmList(usrGrpVO);
	}
	
	@Override
	public List<CommCodeVO> getCodeList(CommCodeVO commCodeVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.selectCodeList(commCodeVO);
	}

	@Override
	public Integer createCode(CommCodeVO commCodeVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.createCode(commCodeVO);
	}
	
	@Override
	public List<CommCodeVO> getUserIdCodeList(UmsUserVO umsUserVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.selectUserIdCodeList(umsUserVO);
	}
	@Override
	public CommCodeVO getCodeValAddCom(CommCodeVO commCodeVO){
		// TODO Auto-generated method stub
		return umsUserDAO.selectCodeValAddCom(commCodeVO);
	}
	@Override
	public CommCodeVO getCodeValAddDept(CommCodeVO commCodeVO){
		// TODO Auto-generated method stub
		return umsUserDAO.selectCodeValAddDept(commCodeVO);
	}
	
	@Override
	public List<SearchUmsUserVO> getApplUsrList(SearchUmsUserVO searchUmsUserVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.selectApplUsrList(searchUmsUserVO);
	}
	
	public Integer getApplUsrListCount(SearchUmsUserVO searchUmsUserVO){
		return umsUserDAO.selectApplUsrListCount(searchUmsUserVO);
	}
	
	@Override
	public Integer modifyUmsUserAppl(UmsUserVO umsUserVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.modifyUmsUserAppl(umsUserVO);
	}

	public UmsUserVO getSeekId(UmsUserVO umsUserVO){
		// TODO Auto-generated method stub
		return umsUserDAO.selectSeekId(umsUserVO);
	}
	
	public UmsUserVO getSeekPw(UmsUserVO umsUserVO){
		// TODO Auto-generated method stub
		return umsUserDAO.selectSeekPw(umsUserVO);
	}
	@Override
	public List<CallBackVO> getCallBackInfo(CallBackVO callBackVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.selectCallbackInfo(callBackVO);
	}
	
	@Override
	public Integer createAuthCode(CallBackVO callBackVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.insertAuthInfo(callBackVO);
	}
	
	@Override
	public Integer delAuthCode(CallBackVO callBackVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.deleteAuthInfo(callBackVO);
	}
	
	@Override
	public Integer createAuthSms(CallBackVO callBackVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.insertAuthSMS(callBackVO);
	}
	@Override
	public Integer createCallbackInfo(CallBackVO callBackVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.insertCallbackInfo(callBackVO);
	}
	
	@Override
	public Integer delCallbackInfo(CallBackVO callBackVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.deleteCallbackInfo(callBackVO);
	}
	
	@Override
	public Integer createCallbackInfoHis(CallBackVO callBackVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.insertCallbackInfoHis(callBackVO);
	}
	@Override
	public List<CallBackVO> getGrpCallbackList(SearchUmsUserVO searchUmsUserVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.selectGrpCallbackList(searchUmsUserVO);
	}
	@Override
	public Integer getGrpCallbackListCount(SearchUmsUserVO searchUmsUserVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.selectGrpCallbackListCount(searchUmsUserVO);
	}
	
	@Override
	public List<CallBackVO> getApplCallbackList(SearchUmsUserVO searchUmsUserVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.selectApplCallbackList(searchUmsUserVO);
	}
	@Override
	public Integer getApplCallbackListCount(SearchUmsUserVO searchUmsUserVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.selectApplCallbackListCount(searchUmsUserVO);
	}
	@Override
	public Integer modifyCallbackInfo(CallBackVO callBackVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.updateCallbackInfo(callBackVO);
	}
	@Override
	public List<CommCodeVO> getCallbackList(String userId) {
		// TODO Auto-generated method stub
		return umsUserDAO.selectCallbackList(userId);
	}
	@Override
	public MobileVO getMobileApi(MobileVO mobileVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.getMobileApi(mobileVO);
	}
	
	@Override
	public Integer modifyMobileApi(MobileVO mobileVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.modifyMobileApi(mobileVO);
	}
	@Override
	public Integer modifyMobileApi2(MobileVO mobileVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.modifyMobileApi2(mobileVO);
	}
	@Override
	public Integer modifyMobileUpdateApi(MobileVO mobileVO) {
		// TODO Auto-generated method stub
		return umsUserDAO.modifyMobileUpdateApi(mobileVO);
	}

}
