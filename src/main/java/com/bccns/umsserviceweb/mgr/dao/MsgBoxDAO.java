package com.bccns.umsserviceweb.mgr.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;



import com.bccns.umsserviceweb.mgr.vo.MsgBoxVO;
import com.bccns.umsserviceweb.mgr.vo.SearchMsgBoxVO;

@Repository
public class MsgBoxDAO {
	private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession)
    {
        this.sqlSession = sqlSession;
    }
    
	public List<MsgBoxVO> getMsgBoxList(SearchMsgBoxVO searchMsgBoxVO) {

        return sqlSession.selectList("com.bccns.umsserviceweb.mgr.dao.MsgBoxDAO.selectMsgBoxList", searchMsgBoxVO);
    }
	
	public List<MsgBoxVO> getMsgBoxListMain() {

        return sqlSession.selectList("com.bccns.umsserviceweb.mgr.dao.MsgBoxDAO.selectMsgBoxList", null);
    }

	public Integer createMsgBox(MsgBoxVO msgBoxVO) {
		// TODO Auto-generated method stub
		return sqlSession.insert("com.bccns.umsserviceweb.mgr.dao.MsgBoxDAO.insertMsgBox", msgBoxVO);
	}

	public Integer getMsgBoxListCount(SearchMsgBoxVO searchMsgBoxVO) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("com.bccns.umsserviceweb.mgr.dao.MsgBoxDAO.selectMsgBoxListCount", searchMsgBoxVO);
	}

	public MsgBoxVO getMsgBoxDetail(SearchMsgBoxVO searchMsgBoxVO) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("com.bccns.umsserviceweb.mgr.dao.MsgBoxDAO.selectMsgBox", searchMsgBoxVO);
	}

	public Integer modifyMsgBox(MsgBoxVO msgBoxVO) {
		// TODO Auto-generated method stub
		return sqlSession.update("com.bccns.umsserviceweb.mgr.dao.MsgBoxDAO.updateMsgBox", msgBoxVO);
	}
	
	public Integer modifyMsgBoxSimple(MsgBoxVO msgBoxVO) {
		// TODO Auto-generated method stub
		return sqlSession.update("com.bccns.umsserviceweb.mgr.dao.MsgBoxDAO.updateMsgBoxSimple", msgBoxVO);
	}

	public Integer modifyDetailMsgBox(MsgBoxVO msgBoxVO) {
		// TODO Auto-generated method stub
		return sqlSession.update("com.bccns.umsserviceweb.mgr.dao.MsgBoxDAO.updateDetailMsgBox", msgBoxVO);
	}

	public Integer removeMsgBox(MsgBoxVO msgBoxVO) {
		// TODO Auto-generated method stub
		return sqlSession.delete("com.bccns.umsserviceweb.mgr.dao.MsgBoxDAO.deleteMsgBox", msgBoxVO);
	}
	
	public List<MsgBoxVO> getChngMsgBoxList(SearchMsgBoxVO searchMsgBoxVO) {

        return sqlSession.selectList("com.bccns.umsserviceweb.mgr.dao.MsgBoxDAO.selectChngMsgBoxList", searchMsgBoxVO);
    }
	
	public Integer getChngMsgBoxListCount(SearchMsgBoxVO searchMsgBoxVO) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("com.bccns.umsserviceweb.mgr.dao.MsgBoxDAO.selectChngMsgBoxListCount", searchMsgBoxVO);
	}
	
	public List<MsgBoxVO> getMsgBoxDmList(SearchMsgBoxVO searchMsgBoxVO) {

        return sqlSession.selectList("com.bccns.umsserviceweb.mgr.dao.MsgBoxDAO.selectMsgBoxDmList", searchMsgBoxVO);
    }
	
	public Integer getMsgBoxDmListCount(SearchMsgBoxVO searchMsgBoxVO) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("com.bccns.umsserviceweb.mgr.dao.MsgBoxDAO.selectMsgBoxDmListCount", searchMsgBoxVO);
	}	
	
	public MsgBoxVO getDmContents(SearchMsgBoxVO searchMsgBoxVO) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("com.bccns.umsserviceweb.mgr.dao.MsgBoxDAO.selectDmContents", searchMsgBoxVO);
	}	
}
