package com.bccns.umsserviceweb.push.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
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

@Repository
public class PrivatePushDAO {
	private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession)
    {
        this.sqlSession = sqlSession;
    }
	public Integer createPrivatePushMsg(PmsSendVO pmsSendVO) {
		// TODO Auto-generated method stub
		return sqlSession.insert("com.bccns.umsserviceweb.push.dao.PrivatePushDAO.insertPushTestData", pmsSendVO);
	}
	public List<TransStatVO> getTransStatList(SearchTransStatVO searchTransStatVO) {

        return sqlSession.selectList("com.bccns.umsserviceweb.push.dao.PrivatePushDAO.selectTransStatList", searchTransStatVO);
    }

	public Integer getTransStatListCount(SearchTransStatVO searchTransStatVO) {
		return sqlSession.selectOne("com.bccns.umsserviceweb.push.dao.PrivatePushDAO.selectTransStatListCount", searchTransStatVO);
	}
	
	public List<TransRsltVO> getTransRsltList(SearchTransRsltVO searchTransRsltVO) {

        return sqlSession.selectList("com.bccns.umsserviceweb.push.dao.PrivatePushDAO.selectTransRsltList", searchTransRsltVO);
    }

	public Integer getTransRsltListCount(SearchTransRsltVO searchTransRsltVO) {
		return sqlSession.selectOne("com.bccns.umsserviceweb.push.dao.PrivatePushDAO.selectTransRsltListCount", searchTransRsltVO);
	}
	
	public RsltDetTopVO getRsltDetTop(SearchRsltDetVO searchRsltDetVO) {
		return sqlSession.selectOne("com.bccns.umsserviceweb.push.dao.PrivatePushDAO.selectRsltDetTop", searchRsltDetVO);
	}
	
	public RsltDetMiddleVO getRsltDetMiddle(SearchRsltDetVO searchRsltDetVO) {
		return sqlSession.selectOne("com.bccns.umsserviceweb.push.dao.PrivatePushDAO.selectRsltDetMiddle", searchRsltDetVO);
	}
	
	public List<RsltDetBottomVO> getRsltDetBottomList(SearchRsltDetVO searchRsltDetVO) {
		return sqlSession.selectList("com.bccns.umsserviceweb.push.dao.PrivatePushDAO.selectRsltDetBottomList", searchRsltDetVO);
	}
}
