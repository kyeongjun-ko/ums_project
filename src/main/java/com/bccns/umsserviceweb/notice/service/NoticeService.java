package com.bccns.umsserviceweb.notice.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.bccns.umsserviceweb.notice.vo.NoticeVO;
import com.bccns.umsserviceweb.notice.vo.SearchNoticeVO;

@Repository
public interface NoticeService {
	public Integer createNotice(NoticeVO noticeVO);
    
	public List<NoticeVO> getNoticeListMain();
	
    public List<NoticeVO> getNoticeList(SearchNoticeVO searchNoticeVO);
    
    public List<NoticeVO> getNoticeListExcel(SearchNoticeVO searchNoticeVO);
    
    public Integer getNoticeListCount(SearchNoticeVO searchNoticeVO);
    
    public NoticeVO getNoticeDetail(SearchNoticeVO searchNoticeVO);
    
    public Integer modifyNotice(SearchNoticeVO searchNoticeVO);
    
    public Integer modifyDetailNotice(NoticeVO noticeVO);

    public Integer removeNotice(NoticeVO noticeVO);
     
 
}
