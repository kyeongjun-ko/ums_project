package com.bccns.umsserviceweb.notice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bccns.umsserviceweb.notice.dao.NoticeDAO;
import com.bccns.umsserviceweb.notice.vo.NoticeVO;
import com.bccns.umsserviceweb.notice.vo.SearchNoticeVO;
@Service
public class NoticeServiceImpl implements NoticeService{
	private static final Logger log = LoggerFactory.getLogger(NoticeServiceImpl.class);
    
	@Autowired
	private NoticeDAO noticeDAO;
	
	@Override
	public Integer createNotice(NoticeVO noticeVO) {
		// TODO Auto-generated method stub
		return noticeDAO.createNotice(noticeVO);
	}
	
	@Override
	public List<NoticeVO> getNoticeListMain() {
		// TODO Auto-generated method stub
		return noticeDAO.getNoticeListMain();
	}
	
	@Override
	public List<NoticeVO> getNoticeList(SearchNoticeVO searchNoticeVO) {
		// TODO Auto-generated method stub
		return noticeDAO.getNoticeList(searchNoticeVO);
	}

	@Override
	public List<NoticeVO> getNoticeListExcel(
			SearchNoticeVO searchNoticeVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getNoticeListCount(SearchNoticeVO searchNoticeVO) {
		// TODO Auto-generated method stub
		return noticeDAO.getNoticeListCount(searchNoticeVO);
	}

	@Override
	public NoticeVO getNoticeDetail(SearchNoticeVO searchNoticeVO) {
		// TODO Auto-generated method stub
		return noticeDAO.getNoticeDetail(searchNoticeVO);
	}

	@Override
	public Integer modifyNotice(SearchNoticeVO searchNoticeVO) {
		// TODO Auto-generated method stub
		return noticeDAO.modifyNotice(searchNoticeVO);
	}

	@Override
	public Integer modifyDetailNotice(NoticeVO noticeVO) {
		// TODO Auto-generated method stub
		return noticeDAO.modifyDetailNotice(noticeVO);
	}

	@Override
	public Integer removeNotice(NoticeVO noticeVO) {
		// TODO Auto-generated method stub
		return noticeDAO.removeNotice(noticeVO);
	}
 
}
