package com.bccns.umsserviceweb.smart.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bccns.umsserviceweb.smart.dao.SmartDAO;
import com.bccns.umsserviceweb.smart.vo.SmartDmRightVO;
import com.bccns.umsserviceweb.ums.vo.SmsVO;
import com.bccns.umsserviceweb.smart.vo.SmartModListVO;
import com.bccns.umsserviceweb.common.vo.CommCodeVO;
import com.bccns.umsserviceweb.mgr.vo.SearchMsgBoxVO;

@Service
public class SmartServiceImpl implements SmartService{
	private static final Logger log = LoggerFactory.getLogger(SmartServiceImpl.class);
    
	@Autowired
	private SmartDAO smartDAO;

	@Override
	public Integer createSmartDm(SmsVO smsVO) {
		// TODO Auto-generated method stub
		return smartDAO.createSmartDm(smsVO);
	}
	
	@Override
	public Integer createSmartFax(SmsVO smsVO) {
		// TODO Auto-generated method stub
		return smartDAO.createSmartFax(smsVO);
	}
	
	@Override
	public Integer createSmartDmRight(SmartDmRightVO smartDmRightVO) {
		// TODO Auto-generated method stub
		return smartDAO.createSmartDmMsgRight(smartDmRightVO);
	}
	

	
	@Override
	public Integer getSmartDmModListCount(SearchMsgBoxVO smartVO) {
		return smartDAO.getSmartDmModListCount(smartVO);
	}

	@Override
	public List<SmartModListVO> getSmartDmModList(SearchMsgBoxVO smartVO) {
		return smartDAO.getSmartDmModList(smartVO);
	}

	@Override
	public SmartModListVO getSmartDmModOne(SmartModListVO smartVO) {
		return smartDAO.getSmartDmModOne(smartVO);
	}
	
	@Override
	public Integer getSmartDmModOneCount(SmartModListVO smartVO) {
		return smartDAO.getSmartDmModOneCount(smartVO);
	}

	
	@Override
	public List<CommCodeVO> getSmartDmDeptList(SmartModListVO smartVO) {
		return smartDAO.getSmartDmDeptList(smartVO);
	}

	@Override
	public Integer modifySmartDmDept(SmartModListVO smartVO) {
		return smartDAO.modifySmartDm(smartVO);
	}

	@Override
	public Integer delSmartDmList(SmartModListVO smartVO) {
		return smartDAO.delSmartDmList(smartVO);		
	}
}
