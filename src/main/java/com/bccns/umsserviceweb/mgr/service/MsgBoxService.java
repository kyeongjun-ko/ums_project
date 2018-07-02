package com.bccns.umsserviceweb.mgr.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.bccns.umsserviceweb.mgr.vo.MsgBoxVO;
import com.bccns.umsserviceweb.mgr.vo.SearchMsgBoxVO;

@Repository
public interface MsgBoxService {
	public Integer createMsgBox(MsgBoxVO msgBoxVO);
    
	public List<MsgBoxVO> getMsgBoxListMain();
	
    public List<MsgBoxVO> getMsgBoxList(SearchMsgBoxVO searchMsgBoxVO);
    
    public List<MsgBoxVO> getMsgBoxListExcel(SearchMsgBoxVO searchMsgBoxVO);
    
    public List<MsgBoxVO> getMsgBoxDmList(SearchMsgBoxVO searchMsgBoxVO);
    
    public Integer getMsgBoxDmListCount(SearchMsgBoxVO searchMsgBoxVO);
    
    public List<MsgBoxVO> getChngMsgBoxList(SearchMsgBoxVO searchMsgBoxVO);
    
    public Integer getChngMsgBoxListCount(SearchMsgBoxVO searchMsgBoxVO);
    
    public Integer getMsgBoxListCount(SearchMsgBoxVO searchMsgBoxVO);
    
    public MsgBoxVO getMsgBoxDetail(SearchMsgBoxVO searchMsgBoxVO);
    
    public Integer modifyMsgBox(MsgBoxVO msgBoxVO);
    
    public Integer modifyMsgBoxSimple(MsgBoxVO msgBoxVO);
    
    public Integer modifyDetailMsgBox(MsgBoxVO msgBoxVO);

    public Integer removeMsgBox(MsgBoxVO msgBoxVO);
    
    public MsgBoxVO getDmContents(SearchMsgBoxVO searchMsgBoxVO);
    
     
 
}
