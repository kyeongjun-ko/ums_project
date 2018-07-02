package com.bccns.umsserviceweb.mgr.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bccns.umsserviceweb.common.vo.CommCodeVO;
import com.bccns.umsserviceweb.common.vo.UmsUserVO;
import com.bccns.umsserviceweb.mgr.dao.MySvcDAO;
import com.bccns.umsserviceweb.mgr.vo.MySvcVO;
import com.bccns.umsserviceweb.mgr.vo.SearchMySvcVO;
import com.bccns.umsserviceweb.mgr.vo.SearchTransUseListVO;
import com.bccns.umsserviceweb.mgr.vo.TransUseListVO;
import com.bccns.umsserviceweb.mgr.vo.YyyyMmVO;
@Service
public class MySvcServiceImpl implements MySvcService{
	private static final Logger log = LoggerFactory.getLogger(MySvcServiceImpl.class);
    
	@Autowired
	private MySvcDAO MySvcDAO;
	
	@Override
	public MySvcVO getMySvcListMain(SearchMySvcVO searchMySvcVO) {
		// TODO Auto-generated method stub
		return MySvcDAO.getMySvcListMain(searchMySvcVO);
	}
	
	@Override
	public List<TransUseListVO> getTransUseList(SearchTransUseListVO searchTransUseListVO) {
		// TODO Auto-generated method stub
		return MySvcDAO.getTransUseList(searchTransUseListVO);
	}

	@Override
	public Integer getTransUseListCount(SearchTransUseListVO searchTransUseListVO) {
		// TODO Auto-generated method stub
		return MySvcDAO.getTransUseListCount(searchTransUseListVO);
	}

	@Override
	public List<YyyyMmVO> getYyyyMmList() {
        
        List<YyyyMmVO> YyyyMmList = new ArrayList<YyyyMmVO>();
        YyyyMmVO yyyyMmVO = new YyyyMmVO();
        Map<String, String> inputMap = new HashMap<String, String>();
        try{
        	yyyyMmVO = MySvcDAO.selectYyyyMmList();
        }catch(Exception e){
        	log.debug("getUserInfoD failure = "+e.getMessage());
        }
        YyyyMmList.add(new YyyyMmVO(yyyyMmVO.getYyyymm()));
        YyyyMmList.add(new YyyyMmVO(yyyyMmVO.getYyyymm1()));
        YyyyMmList.add(new YyyyMmVO(yyyyMmVO.getYyyymm2()));
        YyyyMmList.add(new YyyyMmVO(yyyyMmVO.getYyyymm3()));
        YyyyMmList.add(new YyyyMmVO(yyyyMmVO.getYyyymm4()));
        YyyyMmList.add(new YyyyMmVO(yyyyMmVO.getYyyymm5()));
        log.debug("YyyyMmList = "+YyyyMmList.toString());
        return YyyyMmList;
    }

}
