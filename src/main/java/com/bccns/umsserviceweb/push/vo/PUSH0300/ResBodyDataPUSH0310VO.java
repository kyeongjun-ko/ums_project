package com.bccns.umsserviceweb.push.vo.PUSH0300;

import java.io.Serializable;

public class ResBodyDataPUSH0310VO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String apiKey	     ;

	private String macAddr  ;	
	private String uuid	    ;
	private String major	;
	private String minor	;
	
	private String num	       ;
	private String phoneNo	   ;
	private String sendDt	  	 ;
	private String imageYn	 ;
	private String imageUrl	 ;
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getMacAddr() {
		return macAddr;
	}
	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getMinor() {
		return minor;
	}
	public void setMinor(String minor) {
		this.minor = minor;
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
	@Override
	public String toString() {
		return "ResBodyDataPUSH0310VO [apiKey=" + apiKey + ", macAddr="
				+ macAddr + ", uuid=" + uuid + ", major=" + major + ", minor="
				+ minor + ", num=" + num + ", phoneNo=" + phoneNo + ", sendDt="
				+ sendDt + ", imageYn=" + imageYn + ", imageUrl=" + imageUrl
				+ "]";
	}
	
	
}
