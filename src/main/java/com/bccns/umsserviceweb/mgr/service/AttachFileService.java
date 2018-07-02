package com.bccns.umsserviceweb.mgr.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.bccns.umsserviceweb.mgr.vo.AttachFileVO;
import com.bccns.umsserviceweb.mgr.vo.SearchAttachFileVO;

@Repository
public interface AttachFileService {
	public Integer createAttachFile(AttachFileVO attachFileVO);

    public Integer removeAttachFile(AttachFileVO attachFileVO);
 
}
