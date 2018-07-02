package com.bccns.umsserviceweb.mgr.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.bccns.umsserviceweb.mgr.vo.GrpVO;
import com.bccns.umsserviceweb.mgr.vo.SearchGrpVO;

@Repository
public interface GrpService {
	public Integer createGrp(GrpVO grpVO);
	
	public Integer createDefaultGrp(GrpVO grpVO, SearchGrpVO searchGrpVO);
    
	public List<GrpVO> getGrpListMain();
	
    public List<GrpVO> getGrpList(SearchGrpVO searchGrpVO);
    
    public List<GrpVO> getGrpListExcel(SearchGrpVO searchGrpVO);
    
    public Integer getGrpListCount(SearchGrpVO searchGrpVO);
    
    public GrpVO getGrpDetail(SearchGrpVO searchGrpVO);
    
    public Integer modifyGrp(GrpVO grpVO);
    
    public Integer modifyDetailGrp(GrpVO grpVO);

    public Integer removeGrp(SearchGrpVO searchGrpVO);
     
 
}
