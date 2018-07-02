package com.bccns.umsserviceweb.mgr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bccns.umsserviceweb.mgr.dao.AttachFileDAO;
import com.bccns.umsserviceweb.mgr.vo.AttachFileVO;
import com.bccns.umsserviceweb.mgr.vo.SearchAttachFileVO;
@Service
public class AttachFileServiceImpl implements AttachFileService{
	private static final Logger log = LoggerFactory.getLogger(AttachFileServiceImpl.class);
    
	@Autowired
	private AttachFileDAO addrDAO;
	
	@Override
	public Integer createAttachFile(AttachFileVO attachFileVO) {
		// TODO Auto-generated method stub
		return addrDAO.createAttachFile(attachFileVO);
	}

	@Override
	public Integer removeAttachFile(AttachFileVO attachFileVO) {
		// TODO Auto-generated method stub
		return addrDAO.removeAttachFile(attachFileVO);
	}
 
}
