package com.bccns.umsserviceweb.api.dao;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.bccns.umsserviceweb.api.vo.SimpleVO;

@Repository
public class SimpleDAO{
	private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession)
    {
        this.sqlSession = sqlSession;
    }
    public SimpleVO getSimple(String id) {
        return sqlSession.selectOne("com.samsung.mediahub.api.dao.SimpleDAO.getSimple", id);
    }

}
