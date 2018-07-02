package com.bccns.umsserviceweb.api.service;

import com.bccns.umsserviceweb.api.vo.SimpleVO;
import com.bccns.umsserviceweb.common.exception.SimpleException;

public interface SimpleService {

	public SimpleVO getSimple(String id) throws SimpleException;
	
}
