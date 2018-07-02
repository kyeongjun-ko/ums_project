package com.bccns.umsserviceweb.mgr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bccns.umsserviceweb.mgr.dao.GrpDAO;
import com.bccns.umsserviceweb.mgr.vo.GrpVO;
import com.bccns.umsserviceweb.mgr.vo.SearchGrpVO;
@Service
public class GrpServiceImpl implements GrpService{
	private static final Logger log = LoggerFactory.getLogger(GrpServiceImpl.class);
    
	@Autowired
	private GrpDAO grpDAO;
	
	@Override
	public Integer createGrp(GrpVO grpVO) {
		// TODO Auto-generated method stub
		return grpDAO.createGrp(grpVO);
	}
	
	@Override
	public List<GrpVO> getGrpListMain() {
		// TODO Auto-generated method stub
		return grpDAO.getGrpListMain();
	}
	
	@Override
	public List<GrpVO> getGrpList(SearchGrpVO searchGrpVO) {
		// TODO Auto-generated method stub
		return grpDAO.getGrpList(searchGrpVO);
	}

	@Override
	public List<GrpVO> getGrpListExcel(
			SearchGrpVO searchGrpVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getGrpListCount(SearchGrpVO searchGrpVO) {
		// TODO Auto-generated method stub
		return grpDAO.getGrpListCount(searchGrpVO);
	}

	@Override
	public GrpVO getGrpDetail(SearchGrpVO searchGrpVO) {
		// TODO Auto-generated method stub
		return grpDAO.getGrpDetail(searchGrpVO);
	}

	@Override
	public Integer modifyGrp(GrpVO grpVO) {
		// TODO Auto-generated method stub
		return grpDAO.modifyGrp(grpVO);
	}

	@Override
	public Integer modifyDetailGrp(GrpVO grpVO) {
		// TODO Auto-generated method stub
		return grpDAO.modifyDetailGrp(grpVO);
	}

	@Override
	public Integer removeGrp(SearchGrpVO searchGrpVO) {
		// TODO Auto-generated method stub
		return grpDAO.removeGrp(searchGrpVO);
	}

	@Override
	public Integer createDefaultGrp(GrpVO grpVO, SearchGrpVO searchGrpVO) {
		// TODO Auto-generated method stub
		log.debug("=========>grpVO :" + grpVO.toString());
		log.debug("=========>searchGrpVO :" + searchGrpVO.toString());
		try {
			Integer cnt = grpDAO.getGrpListCount(searchGrpVO);
			if( cnt == 0){
				grpVO.setUserId(searchGrpVO.getUserId());
				grpVO.setGrpCd(searchGrpVO.getGrpCd());
				grpVO.setGrpNo("0");
				grpVO.setGrpNm("기본그룹");
				grpVO.setInstDt(searchGrpVO.getInstDt());
				grpVO.setInstId(searchGrpVO.getUserId());
				grpDAO.createGrpIdOff(grpVO);
			}
		} catch(Exception e) {
			log.error("createDefaultGrp error", e);
		}
		return null;
	}
 
}
