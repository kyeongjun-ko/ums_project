package com.bccns.umsserviceweb.push.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.bccns.umsserviceweb.push.vo.PmsPointMsgVO;
import com.bccns.umsserviceweb.push.vo.PmsSendVO;
import com.bccns.umsserviceweb.push.vo.PUSH0100.ReqBodyPUSH0100VO;
import com.bccns.umsserviceweb.push.vo.PUSH0200.ReqBodyPUSH0200VO;

@Repository
public interface PushService {
	public Integer createPushRegId(ReqBodyPUSH0100VO reqBodyPUSH0100VO);
	
	public PmsSendVO getPushRegId(PmsSendVO pmsSendVO);
	
	public PmsPointMsgVO getPushPointMsg(PmsPointMsgVO pmsPointMsgVO);
}
