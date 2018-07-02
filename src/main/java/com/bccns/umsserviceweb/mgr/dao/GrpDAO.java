package com.bccns.umsserviceweb.mgr.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;








import com.bccns.umsserviceweb.mgr.vo.GrpVO;
import com.bccns.umsserviceweb.mgr.vo.SearchGrpVO;

@Repository
public class GrpDAO  {
	private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession)
    {
        this.sqlSession = sqlSession;
    }
    
	public List<GrpVO> getGrpList(SearchGrpVO searchGrpVO) {

        return sqlSession.selectList("com.bccns.umsserviceweb.mgr.dao.GrpDAO.selectGrpList", searchGrpVO);
    }
	
	public List<GrpVO> getGrpListMain() {

        return sqlSession.selectList("com.bccns.umsserviceweb.mgr.dao.GrpDAO.selectGrpList", null);
    }
	public Integer createGrp(GrpVO grpVO) {
		// TODO Auto-generated method stub
		return sqlSession.insert("com.bccns.umsserviceweb.mgr.dao.GrpDAO.insertGrp", grpVO);
	}
	
	public Integer createGrpIdOff(GrpVO grpVO) {
		// TODO Auto-generated method stub
		return sqlSession.insert("com.bccns.umsserviceweb.mgr.dao.GrpDAO.insertGrpIdOff", grpVO);
	}

	
	public Integer getGrpListCount(SearchGrpVO searchGrpVO) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("com.bccns.umsserviceweb.mgr.dao.GrpDAO.selectGrpListCount", searchGrpVO);
	}

	public GrpVO getGrpDetail(SearchGrpVO searchGrpVO) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("com.bccns.umsserviceweb.mgr.dao.GrpDAO.selectGrp", searchGrpVO);
	}

	public Integer modifyGrp(GrpVO grpVO) {
		// TODO Auto-generated method stub
		return sqlSession.update("com.bccns.umsserviceweb.mgr.dao.GrpDAO.updateGrp", grpVO);
	}

	public Integer modifyDetailGrp(GrpVO grpVO) {
		// TODO Auto-generated method stub
		return sqlSession.update("com.bccns.umsserviceweb.mgr.dao.GrpDAO.updateDetailGrp", grpVO);
	}

	public Integer removeGrp(SearchGrpVO searchGrpVO) {
		// TODO Auto-generated method stub
		return sqlSession.delete("com.bccns.umsserviceweb.mgr.dao.GrpDAO.deleteGrp", searchGrpVO);
	}
}
