package com.bccns.umsserviceweb.mgr.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;



import com.bccns.umsserviceweb.mgr.vo.AttachFileVO;
import com.bccns.umsserviceweb.mgr.vo.SearchAttachFileVO;

@Repository
public class AttachFileDAO  {
	private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession)
    {
        this.sqlSession = sqlSession;
    }
    
	public Integer createAttachFile(AttachFileVO attachFileVO) {
		// TODO Auto-generated method stub
		return sqlSession.insert("com.bccns.umsserviceweb.mgr.dao.AttachFileDAO.insertAttachFile", attachFileVO);
	}
 
	public Integer removeAttachFile(AttachFileVO attachFileVO) {
		// TODO Auto-generated method stub
		return sqlSession.delete("com.bccns.umsserviceweb.mgr.dao.AttachFileDAO.deleteAttachFile", attachFileVO);
	}
}
