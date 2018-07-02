package com.bccns.umsserviceweb.common.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
 












import com.bccns.umsserviceweb.common.vo.CallBackVO;
import com.bccns.umsserviceweb.common.vo.CommCodeVO;
import com.bccns.umsserviceweb.common.vo.MobileVO;
import com.bccns.umsserviceweb.common.vo.SearchUmsUserVO;
import com.bccns.umsserviceweb.common.vo.UmsUserVO; 
import com.bccns.umsserviceweb.common.vo.UsrGrpVO;
import com.bccns.umsserviceweb.common.vo.UsrLogVO;
import com.bccns.umsserviceweb.mgr.vo.SearchAddrVO;

@Repository
public class UmsUserDAO {
	private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession)
    {
        this.sqlSession = sqlSession;
    }
    public MobileVO getMobileApi(MobileVO mobileVO){
    	return sqlSession.selectOne("com.bccns.umsserviceweb.common.dao.UmsUserDAO.selectMobileApi", mobileVO);
    }
    
    public Integer modifyMobileApi(MobileVO mobileVO){
    	return sqlSession.update("com.bccns.umsserviceweb.common.dao.UmsUserDAO.updateMobileApi", mobileVO);
    }
    
    public Integer modifyMobileApi2(MobileVO mobileVO){
    	return sqlSession.update("com.bccns.umsserviceweb.common.dao.UmsUserDAO.updateMobileApi2", mobileVO);
    }
    
    public Integer modifyMobileUpdateApi(MobileVO mobileVO){
    	return sqlSession.update("com.bccns.umsserviceweb.common.dao.UmsUserDAO.updateMobileUpdateApi", mobileVO);
    }
    
	public UmsUserVO getUserInfo(UmsUserVO umsUserVO) {
        return sqlSession.selectOne("com.bccns.umsserviceweb.common.dao.UmsUserDAO.selectUmsUserInfo", umsUserVO);
    }
	
	public UmsUserVO getUserInfoD(UmsUserVO umsUserVO) {
        return sqlSession.selectOne("com.bccns.umsserviceweb.common.dao.UmsUserDAO.selectUmsUserInfoD", umsUserVO);
    }
	
	public UmsUserVO getUserInfoD2(UmsUserVO umsUserVO) {
        return sqlSession.selectOne("com.bccns.umsserviceweb.common.dao.UmsUserDAO.selectUmsUserInfoD2", umsUserVO);
    }
	
	public UmsUserVO getUserId(UmsUserVO umsUserVO) {
		return sqlSession.selectOne("com.bccns.umsserviceweb.common.dao.UmsUserDAO.selectUmsUserId", umsUserVO);
	}

	public UmsUserVO getUserPw(UmsUserVO umsUserVO) {
		return sqlSession.selectOne("com.bccns.umsserviceweb.common.dao.UmsUserDAO.selectUmsUserPw", umsUserVO);
	}

	public Integer createUser(UmsUserVO umsUserVO) {
		return sqlSession.insert("com.bccns.umsserviceweb.common.dao.UmsUserDAO.insertUmsUserInfo", umsUserVO);
	}

	public Integer modifyUser(UmsUserVO umsUserVO) {
		return sqlSession.update("com.bccns.umsserviceweb.common.dao.UmsUserDAO.updateUmsUserInfo", umsUserVO);
	}

	public Integer modifyPassword(UmsUserVO umsUserVO) {
		return sqlSession.update("com.bccns.umsserviceweb.common.dao.UmsUserDAO.updateUmsUserPw", umsUserVO);
	}

	public Integer createLog(UsrLogVO usrLogVO) {
		return sqlSession.insert("com.bccns.umsserviceweb.common.dao.UmsUserDAO.insertUmsUserLog", usrLogVO);
	}

	public Integer modifyLoginTime(UmsUserVO umsUserVO) {
		return sqlSession.update("com.bccns.umsserviceweb.common.dao.UmsUserDAO.updateUmsUserLoginTime", umsUserVO);
	}

	public Integer getIdValid(UmsUserVO umsUserVO) {
		return sqlSession.selectOne("com.bccns.umsserviceweb.common.dao.UmsUserDAO.selectUmsUserIdValid", umsUserVO);
	}
	
	/**
     * 사용자 그룹리스트 조회 (주소록/SMS/MMS/VMS/FMS)
     */
    public List<UsrGrpVO> selectUsrGrpList(UsrGrpVO usrGrpVO) {
        return sqlSession.selectList("com.bccns.umsserviceweb.common.dao.UmsUserDAO.selectUsrGrpList", usrGrpVO);
    }
    
    /**
     * 사용자 그룹리스트 조회 (주소록/SMS/MMS/VMS/FMS)
     */
    public List<UsrGrpVO> selectUsrGrpDmList(UsrGrpVO usrGrpVO) {
        return sqlSession.selectList("com.bccns.umsserviceweb.common.dao.UmsUserDAO.selectUsrGrpDmList", usrGrpVO);
    }
    
    
    /**
     * 코드리스트 조회 
     */
    public List<CommCodeVO> selectCodeList(CommCodeVO commCodeVO) {
        return sqlSession.selectList("com.bccns.umsserviceweb.common.dao.UmsUserDAO.selectCodeList", commCodeVO);
    }
    
    public Integer createCode(CommCodeVO commCodeVO) {
		return sqlSession.insert("com.bccns.umsserviceweb.common.dao.UmsUserDAO.insertCodeInfo", commCodeVO);
	}
    
    public CommCodeVO selectCodeValAddCom(CommCodeVO commCodeVO) {
        return sqlSession.selectOne("com.bccns.umsserviceweb.common.dao.UmsUserDAO.selectCodeValAddCom", commCodeVO);
    }
    
    public CommCodeVO selectCodeValAddDept(CommCodeVO commCodeVO) {
        return sqlSession.selectOne("com.bccns.umsserviceweb.common.dao.UmsUserDAO.selectCodeValAddDept", commCodeVO);
    }
    
    /**
     * 사용자리스트  조회 
     */
    public List<CommCodeVO> selectUserIdCodeList(UmsUserVO umsUserVO) {
        return sqlSession.selectList("com.bccns.umsserviceweb.common.dao.UmsUserDAO.selectUserIdCodeList", umsUserVO);
    }
    
    /**
     * 가입승인 리스트  조회 
     */
    public List<SearchUmsUserVO> selectApplUsrList(SearchUmsUserVO searchUmsUserVO) {
        return sqlSession.selectList("com.bccns.umsserviceweb.common.dao.UmsUserDAO.selectApplUsrList", searchUmsUserVO);
    }
    
    public Integer selectApplUsrListCount(SearchUmsUserVO searchUmsUserVO) {
        return sqlSession.selectOne("com.bccns.umsserviceweb.common.dao.UmsUserDAO.selectApplUsrListCount", searchUmsUserVO);
    }
    
    public Integer modifyUmsUserAppl(UmsUserVO umsUserVO) {
		return sqlSession.update("com.bccns.umsserviceweb.common.dao.UmsUserDAO.updateUmsUserAppl", umsUserVO);
	}
    public UmsUserVO selectSeekId(UmsUserVO umsUserVO) {
		return sqlSession.selectOne("com.bccns.umsserviceweb.common.dao.UmsUserDAO.selectSeekId", umsUserVO);
	}
    public UmsUserVO selectSeekPw(UmsUserVO umsUserVO) {
		return sqlSession.selectOne("com.bccns.umsserviceweb.common.dao.UmsUserDAO.selectSeekPw", umsUserVO);
	}
    
    /**
     * callback 관리
     */
    public List<CallBackVO> selectCallbackInfo(CallBackVO callBackVO) {
		return sqlSession.selectList("com.bccns.umsserviceweb.common.dao.UmsUserDAO.selectCallbackInfo", callBackVO);
    }
    
    public Integer insertAuthInfo(CallBackVO callBackVO) {
    	return sqlSession.insert("com.bccns.umsserviceweb.common.dao.UmsUserDAO.insertAuthInfo", callBackVO);
    }
    
    public Integer deleteAuthInfo(CallBackVO callBackVO) {
    	return sqlSession.insert("com.bccns.umsserviceweb.common.dao.UmsUserDAO.deleteAuthInfo", callBackVO);
    }
    
    public Integer insertAuthSMS(CallBackVO callBackVO) {
    	return sqlSession.insert("com.bccns.umsserviceweb.common.dao.UmsUserDAO.insertAuthSMS", callBackVO);
    }
    
    public Integer insertCallbackInfo(CallBackVO callBackVO) {
		return sqlSession.insert("com.bccns.umsserviceweb.common.dao.UmsUserDAO.insertCallbackInfo", callBackVO);
    }
    
    public Integer updateCallbackInfo(CallBackVO callBackVO) {
    	return sqlSession.update("com.bccns.umsserviceweb.common.dao.UmsUserDAO.updateCallbackInfo", callBackVO);
    }
    
    public Integer deleteCallbackInfo(CallBackVO callBackVO) {
    	return sqlSession.insert("com.bccns.umsserviceweb.common.dao.UmsUserDAO.deleteCallbackInfo", callBackVO);
    }
    
    public Integer insertCallbackInfoHis(CallBackVO callBackVO) {
		return sqlSession.insert("com.bccns.umsserviceweb.common.dao.UmsUserDAO.insertCallbackInfoHis", callBackVO);
    }
    
    public List<CallBackVO> selectGrpCallbackList(SearchUmsUserVO searchUmsUserVO) {
		return sqlSession.selectList("com.bccns.umsserviceweb.common.dao.UmsUserDAO.selectGrpCallbackList", searchUmsUserVO);
    }
    
    public Integer selectGrpCallbackListCount(SearchUmsUserVO searchUmsUserVO) {
		return sqlSession.selectOne("com.bccns.umsserviceweb.common.dao.UmsUserDAO.selectGrpCallbackListCount", searchUmsUserVO);
    }
    
    public List<CallBackVO> selectApplCallbackList(SearchUmsUserVO searchUmsUserVO) {
		return sqlSession.selectList("com.bccns.umsserviceweb.common.dao.UmsUserDAO.selectApplCallbackList", searchUmsUserVO);
    }
    
    public Integer selectApplCallbackListCount(SearchUmsUserVO searchUmsUserVO) {
		return sqlSession.selectOne("com.bccns.umsserviceweb.common.dao.UmsUserDAO.selectApplCallbackListCount", searchUmsUserVO);
    }
    
    public List<CommCodeVO> selectCallbackList(@Param("userId") String userId) {
    	return sqlSession.selectList("com.bccns.umsserviceweb.common.dao.UmsUserDAO.selectCallbackList", userId);
    }
    
}
