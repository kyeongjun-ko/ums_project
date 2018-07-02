package com.bccns.umsserviceweb.ums.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository; 






import com.bccns.umsserviceweb.mgr.vo.AddrVO;
import com.bccns.umsserviceweb.notice.vo.NoticeVO;
import com.bccns.umsserviceweb.notice.vo.SearchNoticeVO;
import com.bccns.umsserviceweb.ums.vo.AddrExcelVO;
import com.bccns.umsserviceweb.ums.vo.SmsVO;
import com.bccns.umsserviceweb.ums.vo.MmsVO;
import com.bccns.umsserviceweb.ums.vo.UmsExcelVO;
import com.bccns.umsserviceweb.ums.vo.VmsVO;
import com.bccns.umsserviceweb.ums.vo.FmsVO;


@Repository
public class UmsDAO {
	private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession)
    {
        this.sqlSession = sqlSession;
    }
    
	public Integer createSms(SmsVO smsVO) {
		// TODO Auto-generated method stub
		return sqlSession.insert("com.bccns.umsserviceweb.ums.dao.UmsDAO.insertSms", smsVO);
	}
	
	public Integer createLms(SmsVO smsVO) {
		// TODO Auto-generated method stub
		return sqlSession.insert("com.bccns.umsserviceweb.ums.dao.UmsDAO.insertLms", smsVO);
	}

	public Integer createMms(MmsVO mmsVO) {
		// TODO Auto-generated method stub
		return sqlSession.insert("com.bccns.umsserviceweb.ums.dao.UmsDAO.insertMms", mmsVO);
	}

	public Integer createFms(FmsVO fmsVO) {
		// TODO Auto-generated method stub
		return sqlSession.insert("com.bccns.umsserviceweb.ums.dao.UmsDAO.insertFms", fmsVO);
	}

	public Integer createVmsQr(VmsVO vmsVO) {
		// TODO Auto-generated method stub
		return sqlSession.insert("com.bccns.umsserviceweb.ums.dao.UmsDAO.insertVmsQr", vmsVO);
	}

	public Integer createVms(VmsVO vmsVO) {
		// TODO Auto-generated method stub
		return sqlSession.insert("com.bccns.umsserviceweb.ums.dao.UmsDAO.insertVms", vmsVO);
	}
	
	public List<NoticeVO> getMsgList(SearchNoticeVO searchNoticeVO) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("com.bccns.umsserviceweb.ums.dao.UmsDAO.selectMsgList", searchNoticeVO);
	}

	public List<NoticeVO> getAddrList(SearchNoticeVO searchNoticeVO) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("com.bccns.umsserviceweb.ums.dao.UmsDAO.selectAddrList", searchNoticeVO);
	}

	public Integer createAddrExcel(UmsExcelVO umsExcelVO) {
		// TODO Auto-generated method stub
		return sqlSession.insert("com.bccns.umsserviceweb.ums.dao.UmsDAO.insertAddrExcel", umsExcelVO);
	}
	public Integer modifySmsSmartDM(SmsVO smsVO) {
		// TODO Auto-generated method stub
		return sqlSession.update("com.bccns.umsserviceweb.ums.dao.UmsDAO.updateSmsSmartDM", smsVO);
	}
	
	public Integer modifyMmsSmartDM(MmsVO mmsVO) {
		// TODO Auto-generated method stub
		return sqlSession.update("com.bccns.umsserviceweb.ums.dao.UmsDAO.updateMmsSmartDM", mmsVO);
	}
	
}
