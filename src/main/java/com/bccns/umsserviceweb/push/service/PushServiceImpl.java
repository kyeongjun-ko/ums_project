package com.bccns.umsserviceweb.push.service;

import java.util.ArrayList;
import java.util.List;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bccns.umsserviceweb.notice.dao.NoticeDAO;
import com.bccns.umsserviceweb.notice.vo.NoticeVO;
import com.bccns.umsserviceweb.notice.vo.SearchNoticeVO;
import com.bccns.umsserviceweb.push.dao.PushDAO;
import com.bccns.umsserviceweb.push.vo.PmsPointMsgVO;
import com.bccns.umsserviceweb.push.vo.PmsSendVO;
import com.bccns.umsserviceweb.push.vo.PUSH0100.ReqBodyPUSH0100VO;
@Service
public class PushServiceImpl implements PushService{
	private static final Logger log = LoggerFactory.getLogger(PushServiceImpl.class);
    
	@Autowired
	private PushDAO pushDAO;
	
	@Override
	public Integer createPushRegId(ReqBodyPUSH0100VO reqBodyPUSH0100VO) {
		// TODO Auto-generated method stub
		return pushDAO.createPushRegId(reqBodyPUSH0100VO);
	}
	
	@Override
	public PmsSendVO getPushRegId(PmsSendVO pmsSendVO){
		return pushDAO.getPushRegId(pmsSendVO);
	}
 
	@Override
	public PmsPointMsgVO getPushPointMsg(PmsPointMsgVO pmsPointMsgVO){
		return pushDAO.getPushPointMsg(pmsPointMsgVO);
	}
}
