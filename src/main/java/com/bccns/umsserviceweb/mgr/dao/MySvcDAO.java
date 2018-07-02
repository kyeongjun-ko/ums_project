package com.bccns.umsserviceweb.mgr.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;








import com.bccns.umsserviceweb.common.vo.CommCodeVO;
import com.bccns.umsserviceweb.mgr.vo.MySvcVO;
import com.bccns.umsserviceweb.mgr.vo.SearchMySvcVO;
import com.bccns.umsserviceweb.mgr.vo.SearchTransUseListVO;
import com.bccns.umsserviceweb.mgr.vo.TransUseListVO;
import com.bccns.umsserviceweb.mgr.vo.YyyyMmVO;

@Repository
public class MySvcDAO {
	private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession)
    {
        this.sqlSession = sqlSession;
    }
    
    
	public List<TransUseListVO> getTransUseList(SearchTransUseListVO searchTransUseListVO) {

        return sqlSession.selectList("com.bccns.umsserviceweb.mgr.dao.MySvcDAO.selectTransUseList", searchTransUseListVO);
    }
	
	public MySvcVO getMySvcListMain(SearchMySvcVO searchMySvcVO) {

        return sqlSession.selectOne("com.bccns.umsserviceweb.mgr.dao.MySvcDAO.selectMySvcListMain", searchMySvcVO);
    }

	public Integer getTransUseListCount(SearchTransUseListVO searchTransUseListVO) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("com.bccns.umsserviceweb.mgr.dao.MySvcDAO.selectTransUseListCount", searchTransUseListVO);
	}

	/**
     * 년월리스트 조회 
     */
    public YyyyMmVO selectYyyyMmList() {
        return sqlSession.selectOne("com.bccns.umsserviceweb.mgr.dao.MySvcDAO.selectYyyyMmList", null);
    }
}
