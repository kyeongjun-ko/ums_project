package com.bccns.umsserviceweb.mgr.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.bccns.umsserviceweb.common.vo.CommCodeVO;
import com.bccns.umsserviceweb.mgr.vo.MySvcVO;
import com.bccns.umsserviceweb.mgr.vo.SearchMySvcVO;
import com.bccns.umsserviceweb.mgr.vo.SearchTransUseListVO;
import com.bccns.umsserviceweb.mgr.vo.TransUseListVO;
import com.bccns.umsserviceweb.mgr.vo.YyyyMmVO;

@Repository
public interface MySvcService {
	
    
	public MySvcVO getMySvcListMain(SearchMySvcVO searchMySvcVO);
	
    public List<TransUseListVO> getTransUseList(SearchTransUseListVO searchTransUseListVO);
    
    public Integer getTransUseListCount(SearchTransUseListVO searchTransUseListVO);

    public List<YyyyMmVO> getYyyyMmList();
    
}
