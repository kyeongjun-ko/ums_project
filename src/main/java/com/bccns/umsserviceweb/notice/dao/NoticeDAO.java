package com.bccns.umsserviceweb.notice.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;








import com.bccns.umsserviceweb.notice.vo.NoticeVO;
import com.bccns.umsserviceweb.notice.vo.SearchNoticeVO;

@Repository
public class NoticeDAO  {
	private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession)
    {
        this.sqlSession = sqlSession;
    }
    
	public List<NoticeVO> getNoticeList(SearchNoticeVO searchNoticeVO) {

        return sqlSession.selectList("com.bccns.umsserviceweb.notice.dao.NoticeDAO.selectNoticeList", searchNoticeVO);
    }
	
	public List<NoticeVO> getNoticeListMain() {

        return sqlSession.selectList("com.bccns.umsserviceweb.notice.dao.NoticeDAO.selectNoticeListMain", null);
    }

	public Integer createNotice(NoticeVO noticeVO) {
		// TODO Auto-generated method stub
		return sqlSession.insert("com.bccns.umsserviceweb.notice.dao.NoticeDAO.insertNotice", noticeVO);
	}

	public Integer getNoticeListCount(SearchNoticeVO searchNoticeVO) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("com.bccns.umsserviceweb.notice.dao.NoticeDAO.selectNoticeListCount", searchNoticeVO);
	}

	public NoticeVO getNoticeDetail(SearchNoticeVO searchNoticeVO) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("com.bccns.umsserviceweb.notice.dao.NoticeDAO.selectNotice", searchNoticeVO);
	}

	public Integer modifyNotice(SearchNoticeVO searchNoticeVO) {
		// TODO Auto-generated method stub
		return sqlSession.update("com.bccns.umsserviceweb.notice.dao.NoticeDAO.updateNotice", searchNoticeVO);
	}

	public Integer modifyDetailNotice(NoticeVO noticeVO) {
		// TODO Auto-generated method stub
		return sqlSession.update("com.bccns.umsserviceweb.notice.dao.NoticeDAO.updateDetailNotice", noticeVO);
	}

	public Integer removeNotice(NoticeVO noticeVO) {
		// TODO Auto-generated method stub
		return sqlSession.delete("com.bccns.umsserviceweb.notice.dao.NoticeDAO.deleteNotice", noticeVO);
	}
}
