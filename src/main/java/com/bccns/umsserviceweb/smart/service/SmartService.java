package com.bccns.umsserviceweb.smart.service;

import java.util.List;

import com.bccns.umsserviceweb.smart.vo.SmartDmRightVO;
import com.bccns.umsserviceweb.ums.exception.UmsSendProcException;
import com.bccns.umsserviceweb.ums.vo.SmsVO;
import com.bccns.umsserviceweb.common.vo.CommCodeVO;
import com.bccns.umsserviceweb.mgr.vo.SearchMsgBoxVO;
import com.bccns.umsserviceweb.smart.vo.SmartModListVO;


public interface SmartService {
	public Integer createSmartDm(SmsVO smsVO) throws UmsSendProcException;
	
	public Integer createSmartFax(SmsVO smsVO) throws UmsSendProcException;
	
	public Integer createSmartDmRight(SmartDmRightVO smartDmRightVO) throws UmsSendProcException;
	
	public Integer getSmartDmModListCount(SearchMsgBoxVO smartVO);
	
	public List<SmartModListVO> getSmartDmModList(SearchMsgBoxVO smartVO);
	
	public SmartModListVO getSmartDmModOne(SmartModListVO smartVO);
	
	public Integer getSmartDmModOneCount(SmartModListVO smartVO);
	
	public List<CommCodeVO> getSmartDmDeptList(SmartModListVO smartVO);
	
	public Integer modifySmartDmDept(SmartModListVO smartVO);
	
	public Integer delSmartDmList(SmartModListVO smartVO);
	
}
