package com.bccns.umsserviceweb.mgr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bccns.umsserviceweb.mgr.dao.AddrDAO;
import com.bccns.umsserviceweb.mgr.vo.AddrVO;
import com.bccns.umsserviceweb.mgr.vo.SearchAddrVO;
@Service
public class AddrServiceImpl implements AddrService{
	private static final Logger log = LoggerFactory.getLogger(AddrServiceImpl.class);
    
	@Autowired
	private AddrDAO addrDAO;
	
	@Override
	public Integer createAddr(AddrVO addrVO) {
		// TODO Auto-generated method stub
		return addrDAO.createAddr(addrVO);
	}
	
	@Override
	public List<AddrVO> getAddrListMain(SearchAddrVO searchAddrVO) {
		// TODO Auto-generated method stub
		return addrDAO.getAddrListMain(searchAddrVO);
	}
	
	@Override
	public List<AddrVO> getAddrList(SearchAddrVO searchAddrVO) {
		// TODO Auto-generated method stub
		return addrDAO.getAddrList(searchAddrVO);
	}

	@Override
	public List<AddrVO> getAddrListExcel(
			SearchAddrVO searchAddrVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getAddrListCount(SearchAddrVO searchAddrVO) {
		// TODO Auto-generated method stub
		return addrDAO.getAddrListCount(searchAddrVO);
	}

	@Override
	public AddrVO getAddrDetail(SearchAddrVO searchAddrVO) {
		// TODO Auto-generated method stub
		return addrDAO.getAddrDetail(searchAddrVO);
	}

	@Override
	public Integer modifyAddr(AddrVO addrVO) {
		// TODO Auto-generated method stub
		return addrDAO.modifyAddr(addrVO);
	}

	@Override
	public Integer modifyDetailAddr(AddrVO addrVO) {
		// TODO Auto-generated method stub
		return addrDAO.modifyDetailAddr(addrVO);
	}

	@Override
	public Integer removeAddr(SearchAddrVO searchAddrVO) {
		// TODO Auto-generated method stub
		return addrDAO.removeAddr(searchAddrVO);
	}
 
}
