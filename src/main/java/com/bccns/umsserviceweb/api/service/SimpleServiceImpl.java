package com.bccns.umsserviceweb.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bccns.umsserviceweb.api.dao.SimpleDAO;
import com.bccns.umsserviceweb.api.vo.SimpleVO;
import com.bccns.umsserviceweb.common.exception.SimpleException;

@Service
public class SimpleServiceImpl implements SimpleService {
	@Autowired
	SimpleDAO simpleDAO;
	
	@Override
	public SimpleVO getSimple(String id) throws SimpleException {
		// TODO Auto-generated method stub
		return simpleDAO.getSimple(id);
	}

}
