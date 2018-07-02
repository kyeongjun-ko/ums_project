package com.bccns.umsserviceweb.mgr.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.bccns.umsserviceweb.mgr.vo.AddrVO;
import com.bccns.umsserviceweb.mgr.vo.SearchAddrVO;

@Repository
public interface AddrService {
	public Integer createAddr(AddrVO addrVO);
    
	public List<AddrVO> getAddrListMain(SearchAddrVO searchAddrVO);
	
    public List<AddrVO> getAddrList(SearchAddrVO searchAddrVO);
    
    public List<AddrVO> getAddrListExcel(SearchAddrVO searchAddrVO);
    
    public Integer getAddrListCount(SearchAddrVO searchAddrVO);
    
    public AddrVO getAddrDetail(SearchAddrVO searchAddrVO);
    
    public Integer modifyAddr(AddrVO addrVO);
    
    public Integer modifyDetailAddr(AddrVO addrVO);

    public Integer removeAddr(SearchAddrVO searchAddrVO);
     
 
}
