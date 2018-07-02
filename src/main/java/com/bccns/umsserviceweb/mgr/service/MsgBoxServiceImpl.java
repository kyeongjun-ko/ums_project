package com.bccns.umsserviceweb.mgr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bccns.umsserviceweb.mgr.dao.MsgBoxDAO;
import com.bccns.umsserviceweb.mgr.vo.MsgBoxVO;
import com.bccns.umsserviceweb.mgr.vo.SearchMsgBoxVO;
@Service
public class MsgBoxServiceImpl implements MsgBoxService{
	private static final Logger log = LoggerFactory.getLogger(MsgBoxServiceImpl.class);
    
	@Autowired
	private MsgBoxDAO msgBoxDAO;
	
	@Override
	public Integer createMsgBox(MsgBoxVO msgBoxVO) {
		// TODO Auto-generated method stub
		return msgBoxDAO.createMsgBox(msgBoxVO);
	}
	
	@Override
	public List<MsgBoxVO> getMsgBoxListMain() {
		// TODO Auto-generated method stub
		return msgBoxDAO.getMsgBoxListMain();
	}
	
	@Override
	public List<MsgBoxVO> getMsgBoxList(SearchMsgBoxVO searchMsgBoxVO) {
		// TODO Auto-generated method stub
		return msgBoxDAO.getMsgBoxList(searchMsgBoxVO);
	}

	@Override
	public List<MsgBoxVO> getMsgBoxListExcel(
			SearchMsgBoxVO searchMsgBoxVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getMsgBoxListCount(SearchMsgBoxVO searchMsgBoxVO) {
		// TODO Auto-generated method stub
		return msgBoxDAO.getMsgBoxListCount(searchMsgBoxVO);
	}

	@Override
	public MsgBoxVO getMsgBoxDetail(SearchMsgBoxVO searchMsgBoxVO) {
		// TODO Auto-generated method stub
		return msgBoxDAO.getMsgBoxDetail(searchMsgBoxVO);
	}

	@Override
	public Integer modifyMsgBox(MsgBoxVO msgBoxVO) {
		// TODO Auto-generated method stub
		return msgBoxDAO.modifyMsgBox(msgBoxVO);
	}
	
	@Override
	public Integer modifyMsgBoxSimple(MsgBoxVO msgBoxVO) {
		// TODO Auto-generated method stub
		return msgBoxDAO.modifyMsgBoxSimple(msgBoxVO);
	}

	@Override
	public Integer modifyDetailMsgBox(MsgBoxVO msgBoxVO) {
		// TODO Auto-generated method stub
		return msgBoxDAO.modifyDetailMsgBox(msgBoxVO);
	}

	@Override
	public Integer removeMsgBox(MsgBoxVO msgBoxVO) {
		// TODO Auto-generated method stub
		return msgBoxDAO.removeMsgBox(msgBoxVO);
	}

	@Override
	public List<MsgBoxVO> getChngMsgBoxList(SearchMsgBoxVO searchMsgBoxVO) {
		// TODO Auto-generated method stub
		return msgBoxDAO.getChngMsgBoxList(searchMsgBoxVO);
	}

	@Override
	public Integer getChngMsgBoxListCount(SearchMsgBoxVO searchMsgBoxVO) {
		// TODO Auto-generated method stub
		return msgBoxDAO.getChngMsgBoxListCount(searchMsgBoxVO);
	}
	
	@Override
	public List<MsgBoxVO> getMsgBoxDmList(SearchMsgBoxVO searchMsgBoxVO) {
		// TODO Auto-generated method stub
		return msgBoxDAO.getMsgBoxDmList(searchMsgBoxVO);
	}

	@Override
	public Integer getMsgBoxDmListCount(SearchMsgBoxVO searchMsgBoxVO) {
		// TODO Auto-generated method stub
		return msgBoxDAO.getMsgBoxDmListCount(searchMsgBoxVO);
	}

	@Override
	public MsgBoxVO getDmContents(SearchMsgBoxVO searchMsgBoxVO) {
		// TODO Auto-generated method stub
		return msgBoxDAO.getDmContents(searchMsgBoxVO);
	}
 
}
