package com.bccns.umsserviceweb.push.vo.PUSH0200;

import java.io.Serializable;

public class ReqBodyPUSH0200VO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String apiKey	   ;
	private String sender	   ;
	private String title	   ;
	private String message	 ;
	private String phoneNo	 ;
	private String reserveDt	;
	private String imageYn	 ;
	private String imageUrl	 ;
	private String docType	 ;
	
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getReserveDt() {
		return reserveDt;
	}
	public void setReserveDt(String reserveDt) {
		this.reserveDt = reserveDt;
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
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "ReqBodyPUSH0200VO [apiKey=" + apiKey + ", sender=" + sender
				+ ", title=" + title + ", message=" + message + ", phoneNo="
				+ phoneNo + ", reserveDt=" + reserveDt + ", imageYn=" + imageYn
				+ ", imageUrl=" + imageUrl + ", docType=" + docType + "]";
	}

	

}
