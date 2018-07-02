package com.bccns.umsserviceweb.mgr.service;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bccns.umsserviceweb.mgr.vo.ResvDetBottomVO;
import com.bccns.umsserviceweb.mgr.vo.ResvDetTopVO;
import com.bccns.umsserviceweb.mgr.vo.RsltDetBottomVO;
import com.bccns.umsserviceweb.mgr.vo.RsltDetMiddleVO;
import com.bccns.umsserviceweb.mgr.vo.RsltDetTopVO;
import com.bccns.umsserviceweb.mgr.vo.SearchResvDetVO;
import com.bccns.umsserviceweb.mgr.vo.SearchRsltDetVO;
import com.bccns.umsserviceweb.mgr.vo.SearchTransHisVO;
import com.bccns.umsserviceweb.mgr.vo.SearchTransResvVO;
import com.bccns.umsserviceweb.mgr.vo.SearchTransRsltVO;
import com.bccns.umsserviceweb.mgr.vo.SearchTransStatVO;
import com.bccns.umsserviceweb.mgr.vo.TransHisVO;
import com.bccns.umsserviceweb.mgr.vo.TransResvVO;
import com.bccns.umsserviceweb.mgr.vo.TransRsltVO;
import com.bccns.umsserviceweb.mgr.vo.TransStatVO;

@Repository
public interface TransHisService {
    
    public List<TransHisVO> getTransHisList(SearchTransHisVO searchTransHisVO);
    
    public Integer getTransHisListCount(SearchTransHisVO searchTransHisVO);
    
    public List<TransStatVO> getTransStatList(SearchTransStatVO searchTransStatVO);
    
    public Integer getTransStatListCount(SearchTransStatVO searchTransStatVO);
    
    public List<TransRsltVO> getTransRsltList(SearchTransRsltVO searchTransRsltVO);
    
    public Integer getTransRsltListCount(SearchTransRsltVO searchTransRsltVO);
    
    public RsltDetTopVO getRsltDetTop(SearchRsltDetVO searchRsltDetVO);
    
    public List<RsltDetBottomVO> getRsltDetBottomList(SearchRsltDetVO searchRsltDetVO);
    
    public List<RsltDetBottomVO> getRsltDetBottomListExcel(SearchRsltDetVO searchRsltDetVO);

    public RsltDetMiddleVO getRsltDetMiddle(SearchRsltDetVO searchRsltDetVO);
    
    public List<TransResvVO> getTransResvList(SearchTransResvVO searchTransResvVO);
    
    public Integer getTransResvListCount(SearchTransResvVO searchTransResvVO);
    
    public ResvDetTopVO getResvDetTop(SearchResvDetVO searchResvDetVO);
    
    public List<ResvDetBottomVO> getResvDetBottomList(SearchResvDetVO searchResvDetVO);
    
    public Integer getResvDetBottomListCount(SearchResvDetVO searchResvDetVO);
}
