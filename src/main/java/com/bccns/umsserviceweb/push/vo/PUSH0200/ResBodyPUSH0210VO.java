package com.bccns.umsserviceweb.push.vo.PUSH0200;

import java.io.Serializable;

import com.bccns.umsserviceweb.push.vo.ResBodyResultVO;

public class ResBodyPUSH0210VO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ResBodyDataPUSH0210VO data;
	private ResBodyResultVO result;
	public ResBodyDataPUSH0210VO getData() {
		return data;
	}
	public void setData(ResBodyDataPUSH0210VO data) {
		this.data = data;
	}
	public ResBodyResultVO getResult() {
		return result;
	}
	public void setResult(ResBodyResultVO result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "ResBodyPUSH0110VO [data=" + data + ", result=" + result + "]";
	}
	
	
}
