package com.bccns.umsserviceweb.mgr.vo;

import com.bccns.umsserviceweb.common.pagination.PaginationInfo;

public class ResvDetBottomVO extends PaginationInfo {
	private String rowNumber;
	private String msgId;
	private String userId;
	private String destName;
	private String phoneNumber;
	private String sendDate;
	private String resvDate;
	
	
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDestName() {
		return destName;
	}
	public void setDestName(String destName) {
		this.destName = destName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getResvDate() {
		return resvDate;
	}
	public void setResvDate(String resvDate) {
		this.resvDate = resvDate;
	}
	
	@Override
	public String toString() {
		return "TransResvVO [rowNumber=" + rowNumber + ", msgId=" + msgId
				+ ", userId=" + userId + ", destName=" + destName
				+ ", phoneNumber=" + phoneNumber + ", sendDate=" + sendDate
				+ ", resvDate=" + resvDate + "]";
	}
}
