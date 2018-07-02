package com.bccns.umsserviceweb.mgr.dao;


import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.bccns.umsserviceweb.mgr.vo.ResvDetBottomVO;
import com.bccns.umsserviceweb.mgr.vo.ResvDetTopVO;
import com.bccns.umsserviceweb.mgr.vo.RsltDetBottomVO;
import com.bccns.umsserviceweb.mgr.vo.RsltDetMiddleVO;
import com.bccns.umsserviceweb.mgr.vo.RsltDetTopVO;
import com.bccns.umsserviceweb.mgr.vo.SearchResvDetVO;
import com.bccns.umsserviceweb.mgr.vo.SearchRsltDetVO;
import com.bccns.umsserviceweb.mgr.vo.SearchTransHisVO;
import com.bccns.umsserviceweb.mgr.vo.SearchTransResvVO;
import com.bccns.umsserviceweb.mgr.vo.SearchTransRsltVO;
import com.bccns.umsserviceweb.mgr.vo.SearchTransStatVO;
import com.bccns.umsserviceweb.mgr.vo.TransHisVO;
import com.bccns.umsserviceweb.mgr.vo.TransResvVO;
import com.bccns.umsserviceweb.mgr.vo.TransRsltVO;
import com.bccns.umsserviceweb.mgr.vo.TransStatVO;

@Repository
public class TransHisDAO {
	private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession)
    {
        this.sqlSession = sqlSession;
    }
    
	public List<TransHisVO> getTransHisList(SearchTransHisVO searchTransHisVO) {

        return sqlSession.selectList("com.bccns.umsserviceweb.mgr.dao.TransHisDAO.selectTransHisList", searchTransHisVO);
    }

	public Integer getTransHisListCount(SearchTransHisVO searchTransHisVO) {
		return sqlSession.selectOne("com.bccns.umsserviceweb.mgr.dao.TransHisDAO.selectTransHisListCount", searchTransHisVO);
	}

	public List<TransStatVO> getTransStatList(SearchTransStatVO searchTransStatVO) {

        return sqlSession.selectList("com.bccns.umsserviceweb.mgr.dao.TransHisDAO.selectTransStatList", searchTransStatVO);
    }

	public Integer getTransStatListCount(SearchTransStatVO searchTransStatVO) {
		return sqlSession.selectOne("com.bccns.umsserviceweb.mgr.dao.TransHisDAO.selectTransStatListCount", searchTransStatVO);
	}
	
	public List<TransRsltVO> getTransRsltList(SearchTransRsltVO searchTransRsltVO) {

        return sqlSession.selectList("com.bccns.umsserviceweb.mgr.dao.TransHisDAO.selectTransRsltList", searchTransRsltVO);
    }

	public Integer getTransRsltListCount(SearchTransRsltVO searchTransRsltVO) {
		return sqlSession.selectOne("com.bccns.umsserviceweb.mgr.dao.TransHisDAO.selectTransRsltListCount", searchTransRsltVO);
	}
	
	public RsltDetTopVO getRsltDetTop(SearchRsltDetVO searchRsltDetVO) {
		return sqlSession.selectOne("com.bccns.umsserviceweb.mgr.dao.TransHisDAO.selectRsltDetTop", searchRsltDetVO);
	}
	
	public RsltDetMiddleVO getRsltDetMiddle(SearchRsltDetVO searchRsltDetVO) {
		return sqlSession.selectOne("com.bccns.umsserviceweb.mgr.dao.TransHisDAO.selectRsltDetMiddle", searchRsltDetVO);
	}
	
	public List<RsltDetBottomVO> getRsltDetBottomList(SearchRsltDetVO searchRsltDetVO) {
		return sqlSession.selectList("com.bccns.umsserviceweb.mgr.dao.TransHisDAO.selectRsltDetBottomList", searchRsltDetVO);
	}
	
	public List<RsltDetBottomVO> getRsltDetBottomListExcel(SearchRsltDetVO searchRsltDetVO) {
		return sqlSession.selectList("com.bccns.umsserviceweb.mgr.dao.TransHisDAO.selectRsltDetBottomListExcel", searchRsltDetVO);
	}
	
	public List<TransResvVO> getTransResvList(SearchTransResvVO searchTransResvVO) {
		return sqlSession.selectList("com.bccns.umsserviceweb.mgr.dao.TransHisDAO.selectTransResvList", searchTransResvVO);
	}
	
	public Integer getTransResvListCount(SearchTransResvVO searchTransResvVO) {
		return sqlSession.selectOne("com.bccns.umsserviceweb.mgr.dao.TransHisDAO.selectTransResvListCount", searchTransResvVO);
	}
	
	public ResvDetTopVO getResvDetTop(SearchResvDetVO searchResvDetVO) {
		return sqlSession.selectOne("com.bccns.umsserviceweb.mgr.dao.TransHisDAO.selectResvDetTop", searchResvDetVO);
	}
	
	public List<ResvDetBottomVO> getResvDetBottomList(SearchResvDetVO searchResvDetVO) {
		return sqlSession.selectList("com.bccns.umsserviceweb.mgr.dao.TransHisDAO.selectResvDetBottomList", searchResvDetVO);
	}
	
	public Integer getResvDetBottomListCount(SearchResvDetVO searchResvDetVO) {
		return sqlSession.selectOne("com.bccns.umsserviceweb.mgr.dao.TransHisDAO.selectResvDetBottomListCount", searchResvDetVO);
	}
}
