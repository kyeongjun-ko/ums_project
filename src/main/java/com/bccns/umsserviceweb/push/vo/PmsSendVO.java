package com.bccns.umsserviceweb.push.vo;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class PmsSendVO {
	
	private String userId;

	private String apiKey;
	private String regId;
	private String num;
	private String sendDt;
	private String sender;
	private String title;
	private String message;
	private String phoneNo;
	private String regDt;
	private String reserveDt;
	private String imageYn;
	private String imageUrl;
	private String docType;
	private String msgType;
	private String fileSize;
	
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
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
	public String getReserveDt() {
		return reserveDt;
	}
	public void setReserveDt(String reserveDt) {
		this.reserveDt = reserveDt;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getSendDt() {
		return sendDt;
	}
	public void setSendDt(String sendDt) {
		this.sendDt = sendDt;
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
	
	@Override
	public String toString() {
		return "PmsSendVO [userId=" + userId + ", apiKey=" + apiKey
				+ ", regId=" + regId + ", num=" + num + ", sendDt=" + sendDt
				+ ", sender=" + sender + ", title=" + title + ", message="
				+ message + ", phoneNo=" + phoneNo + ", regDt=" + regDt
				+ ", reserveDt=" + reserveDt + ", imageYn=" + imageYn
				+ ", imageUrl=" + imageUrl + ", docType=" + docType
				+ ", msgType=" + msgType + ", fileSize=" + fileSize + "]";
	}
	public String toJsonStr() throws JsonGenerationException, JsonMappingException, IOException {
        
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(this);       
    }
}
