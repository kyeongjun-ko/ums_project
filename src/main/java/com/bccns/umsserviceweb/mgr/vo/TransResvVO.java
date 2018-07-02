package com.bccns.umsserviceweb.mgr.vo;

import com.bccns.umsserviceweb.common.pagination.PaginationInfo;

public class TransResvVO extends PaginationInfo{

	private String rowNumber;
	private String msgId;
	private String msgType;
	private String subject;
	private String sendDate;
	private String destCount;
	private String succCount;
	private String failCount;
	private String progCount;
	private String suserId;
	private String searchType;
	private String searchName;
	
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
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
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
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
	public String getDestCount() {
		return destCount;
	}
	public void setDestCount(String destCount) {
		this.destCount = destCount;
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
	public String getProgCount() {
		return progCount;
	}
	public void setProgCount(String progCount) {
		this.progCount = progCount;
	}
	@Override
	public String toString() {
		return "TransResvVO [rowNumber=" + rowNumber + ", msgId=" + msgId
				+ ", msgType=" + msgType + ", subject=" + subject
				+ ", sendDate=" + sendDate + ", destCount=" + destCount
				+ ", succCount=" + succCount + ", failCount=" + failCount
				+ ", progCount=" + progCount + ", suserId=" + suserId
				+ ", searchType=" + searchType + ", searchName=" + searchName
				+ "]";
	}
}
