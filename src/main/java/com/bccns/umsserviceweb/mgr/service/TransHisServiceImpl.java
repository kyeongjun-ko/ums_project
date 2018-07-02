package com.bccns.umsserviceweb.mgr.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bccns.umsserviceweb.mgr.dao.TransHisDAO;
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

@Service
public class TransHisServiceImpl implements TransHisService{
	private static final Logger log = LoggerFactory.getLogger(TransHisServiceImpl.class);
    
	@Autowired
	private TransHisDAO transHisDAO;

	@Override
	public List<TransHisVO> getTransHisList(SearchTransHisVO searchTransHisVO) {
		// TODO Auto-generated method stub
		return transHisDAO.getTransHisList(searchTransHisVO);
	}

	@Override
	public Integer getTransHisListCount(SearchTransHisVO searchTransHisVO) {
		// TODO Auto-generated method stub
		return transHisDAO.getTransHisListCount(searchTransHisVO);
	}

	@Override
	public List<TransStatVO> getTransStatList(SearchTransStatVO searchTransStatVO) {
		// TODO Auto-generated method stub
		return transHisDAO.getTransStatList(searchTransStatVO);
	}

	@Override
	public Integer getTransStatListCount(SearchTransStatVO searchTransStatVO) {
		// TODO Auto-generated method stub
		return transHisDAO.getTransStatListCount(searchTransStatVO);
	}

	@Override
	public List<TransRsltVO> getTransRsltList(SearchTransRsltVO searchTransRsltVO) {
		// TODO Auto-generated method stub
		return transHisDAO.getTransRsltList(searchTransRsltVO);
	}

	@Override
	public Integer getTransRsltListCount(SearchTransRsltVO searchTransResultVO) {
		// TODO Auto-generated method stub
		return transHisDAO.getTransRsltListCount(searchTransResultVO);
	}

	@Override
	public RsltDetTopVO getRsltDetTop(SearchRsltDetVO searchRsltDetVO) {
		// TODO Auto-generated method stub
		return transHisDAO.getRsltDetTop(searchRsltDetVO);
	}

	@Override
	public RsltDetMiddleVO getRsltDetMiddle(SearchRsltDetVO searchRsltDetVO) {
		return transHisDAO.getRsltDetMiddle(searchRsltDetVO);
	} 

	@Override
	public List<RsltDetBottomVO> getRsltDetBottomList(SearchRsltDetVO searchRsltDetVO) {
		// TODO Auto-generated method stub
		return transHisDAO.getRsltDetBottomList(searchRsltDetVO);
	}
	
	@Override
	public List<RsltDetBottomVO> getRsltDetBottomListExcel(SearchRsltDetVO searchRsltDetVO) {
		// TODO Auto-generated method stub
		return transHisDAO.getRsltDetBottomListExcel(searchRsltDetVO);
	}

	@Override
	public List<TransResvVO> getTransResvList(SearchTransResvVO searchTransResvVO) {
		// TODO Auto-generated method stub
		return transHisDAO.getTransResvList(searchTransResvVO);
	}

	@Override
	public Integer getTransResvListCount(SearchTransResvVO searchTransResvVO) {
		// TODO Auto-generated method stub
		return transHisDAO.getTransResvListCount(searchTransResvVO);
	}
	
	@Override
	public ResvDetTopVO getResvDetTop(SearchResvDetVO searchResvDetVO){
		return transHisDAO.getResvDetTop(searchResvDetVO);
	}

	@Override
	public List<ResvDetBottomVO> getResvDetBottomList(SearchResvDetVO searchResvDetVO) {
		return transHisDAO.getResvDetBottomList(searchResvDetVO);
	}

	@Override
	public Integer getResvDetBottomListCount(SearchResvDetVO searchResvDetVO) {
		return transHisDAO.getResvDetBottomListCount(searchResvDetVO);
	}
}
