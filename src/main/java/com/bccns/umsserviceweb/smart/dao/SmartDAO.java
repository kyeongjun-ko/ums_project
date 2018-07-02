package com.bccns.umsserviceweb.smart.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository; 

import com.bccns.umsserviceweb.smart.vo.SmartDmRightVO;
import com.bccns.umsserviceweb.ums.vo.SmsVO;
import com.bccns.umsserviceweb.common.vo.CommCodeVO;
import com.bccns.umsserviceweb.mgr.vo.SearchMsgBoxVO;
import com.bccns.umsserviceweb.smart.vo.SmartModListVO;

@Repository
public class SmartDAO {
	private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession)
    {
        this.sqlSession = sqlSession;
    }
    
	public Integer createSmartDm(SmsVO smsVO) {
		// TODO Auto-generated method stub
		return sqlSession.insert("com.bccns.umsserviceweb.smart.dao.UmsDAO.insertSmartDm", smsVO);
	}
	
	public Integer createSmartFax(SmsVO smsVO) {
		// TODO Auto-generated method stub
		return sqlSession.insert("com.bccns.umsserviceweb.ums.dao.UmsDAO.insertSmartFax", smsVO);
	}
	
	public Integer createSmartDmMsgRight(SmartDmRightVO smartDmRightVO) {
		// TODO Auto-generated method stub
		return sqlSession.insert("com.bccns.umsserviceweb.ums.dao.UmsDAO.insertSmartDmMsgRight", smartDmRightVO);
	}

	
	public Integer getSmartDmModListCount(SearchMsgBoxVO searchMsgBoxVO) {
		return sqlSession.selectOne("com.bccns.umsserviceweb.smart.dao.SmartDAO.selectSmartDmListCount", searchMsgBoxVO);
	}
	
	public List<SmartModListVO> getSmartDmModList(SearchMsgBoxVO searchMsgBoxVO) {

        return sqlSession.selectList("com.bccns.umsserviceweb.smart.dao.SmartDAO.selectSmartDmList", searchMsgBoxVO);
    }
	
	public SmartModListVO getSmartDmModOne(SmartModListVO smartVO){
		return sqlSession.selectOne("com.bccns.umsserviceweb.smart.dao.SmartDAO.selectSmartDmMod", smartVO);
	}
	
	public Integer getSmartDmModOneCount(SmartModListVO smartVO){
		return sqlSession.selectOne("com.bccns.umsserviceweb.smart.dao.SmartDAO.selectSmartDmModCount", smartVO);
	}
	
	public List<CommCodeVO> getSmartDmDeptList(SmartModListVO smartVO){
		return sqlSession.selectList("com.bccns.umsserviceweb.smart.dao.SmartDAO.selectSmartDmDeptList", smartVO);
	}
	
	public Integer modifySmartDm(SmartModListVO smartModVO){
		return sqlSession.update("com.bccns.umsserviceweb.smart.dao.SmartDAO.updateSmartDmList", smartModVO);
	}
	
	public Integer delSmartDmList(SmartModListVO smartModVO){
		return sqlSession.delete("com.bccns.umsserviceweb.smart.dao.SmartDAO.deleteSmartDmList", smartModVO);
	}
}
