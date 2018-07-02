package com.bccns.umsserviceweb.mgr.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;








import com.bccns.umsserviceweb.mgr.vo.AddrVO;
import com.bccns.umsserviceweb.mgr.vo.SearchAddrVO;

@Repository
public class AddrDAO  {
	private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession)
    {
        this.sqlSession = sqlSession;
    }
    
	public List<AddrVO> getAddrList(SearchAddrVO searchAddrVO) {

        return sqlSession.selectList("com.bccns.umsserviceweb.mgr.dao.AddrDAO.selectAddrList", searchAddrVO);
    }
	
	public List<AddrVO> getAddrListMain(SearchAddrVO searchAddrVO) {

        return sqlSession.selectList("com.bccns.umsserviceweb.mgr.dao.AddrDAO.selectAddr", searchAddrVO);
    }

	public Integer createAddr(AddrVO addrVO) {
		// TODO Auto-generated method stub
		return sqlSession.insert("com.bccns.umsserviceweb.mgr.dao.AddrDAO.insertAddr", addrVO);
	}

	public Integer getAddrListCount(SearchAddrVO searchAddrVO) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("com.bccns.umsserviceweb.mgr.dao.AddrDAO.selectAddrListCount", searchAddrVO);
	}

	public AddrVO getAddrDetail(SearchAddrVO searchAddrVO) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("com.bccns.umsserviceweb.mgr.dao.AddrDAO.selectAddr", searchAddrVO);
	}

	public Integer modifyAddr(AddrVO addrVO) {
		// TODO Auto-generated method stub
		return sqlSession.update("com.bccns.umsserviceweb.mgr.dao.AddrDAO.updateAddr", addrVO);
	}

	public Integer modifyDetailAddr(AddrVO addrVO) {
		// TODO Auto-generated method stub
		return sqlSession.update("com.bccns.umsserviceweb.mgr.dao.AddrDAO.updateDetailAddr", addrVO);
	}

	public Integer removeAddr(SearchAddrVO searchAddrVO) {
		// TODO Auto-generated method stub
		return sqlSession.delete("com.bccns.umsserviceweb.mgr.dao.AddrDAO.deleteAddr", searchAddrVO);
	}
}
