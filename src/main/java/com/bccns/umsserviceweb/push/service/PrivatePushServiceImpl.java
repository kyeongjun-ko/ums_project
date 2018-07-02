package com.bccns.umsserviceweb.push.service;

import java.util.ArrayList;
import java.util.List;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bccns.umsserviceweb.mgr.vo.RsltDetBottomVO;
import com.bccns.umsserviceweb.mgr.vo.RsltDetMiddleVO;
import com.bccns.umsserviceweb.mgr.vo.RsltDetTopVO;
import com.bccns.umsserviceweb.mgr.vo.SearchRsltDetVO;
import com.bccns.umsserviceweb.mgr.vo.SearchTransRsltVO;
import com.bccns.umsserviceweb.mgr.vo.SearchTransStatVO;
import com.bccns.umsserviceweb.mgr.vo.TransRsltVO;
import com.bccns.umsserviceweb.mgr.vo.TransStatVO;
import com.bccns.umsserviceweb.notice.dao.NoticeDAO;
import com.bccns.umsserviceweb.notice.vo.NoticeVO;
import com.bccns.umsserviceweb.notice.vo.SearchNoticeVO;
import com.bccns.umsserviceweb.push.dao.PrivatePushDAO;
import com.bccns.umsserviceweb.push.dao.PushDAO;
import com.bccns.umsserviceweb.push.vo.PmsSendVO;
import com.bccns.umsserviceweb.push.vo.PUSH0100.ReqBodyPUSH0100VO;
import com.bccns.umsserviceweb.push.vo.PUSH0200.ReqBodyPUSH0200VO;
@Service
public class PrivatePushServiceImpl implements PrivatePushService{
	private static final Logger log = LoggerFactory.getLogger(PrivatePushServiceImpl.class);
    
	@Autowired
	private PrivatePushDAO privatePushDAO;
	
	@Override
	public Integer createPushMsg(PmsSendVO pmsSendVO) {
		// TODO Auto-generated method stub
		return privatePushDAO.createPrivatePushMsg(pmsSendVO);
	}

	@Override
	public Integer createPushSendMsg(ReqBodyPUSH0200VO reqBodyPUSH0200VO) {
		// TODO Auto-generated method stub
		return null;
	} 
	
	@Override
	public List<TransStatVO> getTransStatList(SearchTransStatVO searchTransStatVO) {
		// TODO Auto-generated method stub
		return privatePushDAO.getTransStatList(searchTransStatVO);
	}

	@Override
	public Integer getTransStatListCount(SearchTransStatVO searchTransStatVO) {
		// TODO Auto-generated method stub
		return privatePushDAO.getTransStatListCount(searchTransStatVO);
	}

	@Override
	public List<TransRsltVO> getTransRsltList(SearchTransRsltVO searchTransRsltVO) {
		// TODO Auto-generated method stub
		return privatePushDAO.getTransRsltList(searchTransRsltVO);
	}

	@Override
	public Integer getTransRsltListCount(SearchTransRsltVO searchTransResultVO) {
		// TODO Auto-generated method stub
		return privatePushDAO.getTransRsltListCount(searchTransResultVO);
	}

	@Override
	public RsltDetTopVO getRsltDetTop(SearchRsltDetVO searchRsltDetVO) {
		// TODO Auto-generated method stub
		return privatePushDAO.getRsltDetTop(searchRsltDetVO);
	}

	@Override
	public RsltDetMiddleVO getRsltDetMiddle(SearchRsltDetVO searchRsltDetVO) {
		return privatePushDAO.getRsltDetMiddle(searchRsltDetVO);
	} 

	@Override
	public List<RsltDetBottomVO> getRsltDetBottomList(SearchRsltDetVO searchRsltDetVO) {
		// TODO Auto-generated method stub
		return privatePushDAO.getRsltDetBottomList(searchRsltDetVO);
	}
 
}
