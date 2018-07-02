package com.bccns.umsserviceweb.push.vo.PUSH0100;

import java.io.Serializable;

public class ReqBodyPUSH0100VO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String phoneNo;	
	private String regId;
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	@Override
	public String toString() {
		return "ReqBodyPUSH0100VO [phoneNo=" + phoneNo + ", regId=" + regId
				+ "]";
	}
	

}
