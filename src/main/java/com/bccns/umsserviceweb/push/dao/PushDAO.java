package com.bccns.umsserviceweb.push.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.bccns.umsserviceweb.push.vo.PmsPointMsgVO;
import com.bccns.umsserviceweb.push.vo.PmsSendVO;
import com.bccns.umsserviceweb.push.vo.PUSH0100.ReqBodyPUSH0100VO;

@Repository
public class PushDAO  {
	private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession)
    {
        this.sqlSession = sqlSession;
    }
	public Integer createPushRegId(ReqBodyPUSH0100VO reqBodyPUSH0100VO) {
		// TODO Auto-generated method stub
		return sqlSession.insert("com.bccns.umsserviceweb.push.dao.PushDAO.insertPushRegId", reqBodyPUSH0100VO);
	}
	
	public PmsSendVO getPushRegId(PmsSendVO pmsSendVO){
		return sqlSession.selectOne("com.bccns.umsserviceweb.push.dao.PushDAO.selectPushRegId", pmsSendVO);		
	}
	
	public PmsPointMsgVO getPushPointMsg(PmsPointMsgVO pmsPointMsgVO){
		return sqlSession.selectOne("com.bccns.umsserviceweb.push.dao.PushDAO.selectPushPointMsg", pmsPointMsgVO);		
	}

}
