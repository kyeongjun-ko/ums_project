package com.bccns.umsserviceweb.push.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.bccns.umsserviceweb.mgr.vo.RsltDetBottomVO;
import com.bccns.umsserviceweb.mgr.vo.RsltDetMiddleVO;
import com.bccns.umsserviceweb.mgr.vo.RsltDetTopVO;
import com.bccns.umsserviceweb.mgr.vo.SearchRsltDetVO;
import com.bccns.umsserviceweb.mgr.vo.SearchTransRsltVO;
import com.bccns.umsserviceweb.mgr.vo.SearchTransStatVO;
import com.bccns.umsserviceweb.mgr.vo.TransRsltVO;
import com.bccns.umsserviceweb.mgr.vo.TransStatVO;
import com.bccns.umsserviceweb.push.vo.PmsSendVO;
import com.bccns.umsserviceweb.push.vo.PUSH0100.ReqBodyPUSH0100VO;
import com.bccns.umsserviceweb.push.vo.PUSH0200.ReqBodyPUSH0200VO;

@Repository
public interface PrivatePushService {
	public Integer createPushMsg(PmsSendVO pmsSendVO);
	
	public Integer createPushSendMsg(ReqBodyPUSH0200VO reqBodyPUSH0200VO);
	
	public List<TransStatVO> getTransStatList(SearchTransStatVO searchTransStatVO);
	    
    public Integer getTransStatListCount(SearchTransStatVO searchTransStatVO);
    
    public List<TransRsltVO> getTransRsltList(SearchTransRsltVO searchTransRsltVO);
    
    public Integer getTransRsltListCount(SearchTransRsltVO searchTransRsltVO);
    
    public RsltDetTopVO getRsltDetTop(SearchRsltDetVO searchRsltDetVO);
    
    public RsltDetMiddleVO getRsltDetMiddle(SearchRsltDetVO searchRsltDetVO);
    
    public List<RsltDetBottomVO> getRsltDetBottomList(SearchRsltDetVO searchRsltDetVO);
    
}
