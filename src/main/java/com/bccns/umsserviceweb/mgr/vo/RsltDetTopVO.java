package com.bccns.umsserviceweb.mgr.vo;

import com.bccns.umsserviceweb.common.pagination.PaginationInfo;

public class RsltDetTopVO extends PaginationInfo{
	private String jobId;
	private String userId;
	private String msgId;
	private String msgType;
	private String msgCode;
	private String subject;
	private String contents;
	private String sendDate;
	private String destCount;
	private String callback;
	
	
	public String getJobId() {
		return jobId;
	}



	public void setJobId(String jobId) {
		this.jobId = jobId;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getMsgId() {
		return msgId;
	}



	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}



	public String getMsgType() {
		return msgType;
	}



	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}



	public String getMsgCode() {
		return msgCode;
	}



	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}



	public String getSubject() {
		return subject;
	}



	public void setSubject(String subject) {
		this.subject = subject;
	}



	public String getContents() {
		return contents;
	}



	public void setContents(String contents) {
		this.contents = contents;
	}



	public String getSendDate() {
		return sendDate;
	}



	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}



	public String getDestCount() {
		return destCount;
	}



	public void setDestCount(String destCount) {
		this.destCount = destCount;
	}



	public String getCallback() {
		return callback;
	}



	public void setCallback(String callback) {
		this.callback = callback;
	}



	@Override
	public String toString() {
		return "RsltDetTopVO [jobId=" + jobId + ", userId=" + userId + ", msgId="
				+ msgId + ", msgType=" + msgType +", msgCode=" + msgCode +", subject="
				+ subject +", contents=" + contents +", sendDate=" + sendDate +", destCount=" 
				+ destCount +", callback=" + callback +"]";
	}

}
