package com.bccns.umsserviceweb.mgr.vo;

import com.bccns.umsserviceweb.common.pagination.PaginationInfo;

public class TransRsltVO extends PaginationInfo{

	private String rowNumber;
	private String msgId;
	private String jobId;
	private String userId;
	private String subject;
	private String sendDate;
	private String msgType;
	private String msgCode;
	private String destCount;
	private String succCount;
	private String failCount;
	private String progCount;
	private String suserId;
	
	
	public String getSuserId() {
		return suserId;
	}
	public void setSuserId(String suserId) {
		this.suserId = suserId;
	}
	public String getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getSuccCount() {
		return succCount;
	}
	public void setSuccCount(String succCount) {
		this.succCount = succCount;
	}
	public String getFailCount() {
		return failCount;
	}
	public void setFailCount(String failCount) {
		this.failCount = failCount;
	}
	
	public String getDestCount() {
		return destCount;
	}
	public void setDestCount(String destCount) {
		this.destCount = destCount;
	}
	public String getProgCount() {
		return progCount;
	}
	public void setProgCount(String progCount) {
		this.progCount = progCount;
	}
	public String getMsgCode() {
		return msgCode;
	}
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
	@Override
	public String toString() {
		return "TransRsltVO [rowNumber=" + rowNumber + ", msgId=" + msgId
				+ ", jobId=" + jobId + ", userId=" + userId + ", subject="
				+ subject + ", sendDate=" + sendDate + ", msgType=" + msgType
				+ ", msgCode=" + msgCode + ", destCount=" + destCount
				+ ", succCount=" + succCount + ", failCount=" + failCount
				+ ", progCount=" + progCount + "]";
	}
}
