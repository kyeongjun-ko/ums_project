package com.bccns.umsserviceweb.push.vo.PUSH0200;

import java.io.Serializable;

public class ResBodyDataPUSH0210VO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String apiKey	     ;
	private String num	       ;
	private String phoneNo	   ;
	private String sendDt	  	 ;
	private String imageYn	 ;
	private String imageUrl	 ;
	
	
	public String getImageYn() {
		return imageYn;
	}
	public void setImageYn(String imageYn) {
		this.imageYn = imageYn;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getSendDt() {
		return sendDt;
	}
	public void setSendDt(String sendDt) {
		this.sendDt = sendDt;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "ResBodyDataPUSH0210VO [apiKey=" + apiKey + ", num=" + num
				+ ", phoneNo=" + phoneNo + ", sendDt=" + sendDt + ", imageYn="
				+ imageYn + ", imageUrl=" + imageUrl + "]";
	} 
	
	
}
