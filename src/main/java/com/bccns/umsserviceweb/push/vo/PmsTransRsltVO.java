package com.bccns.umsserviceweb.push.vo;

import com.bccns.umsserviceweb.common.pagination.PaginationInfo;

public class PmsTransRsltVO extends PaginationInfo{
	private String rowNumber;
	private String msgId;
	private String msgType;
	private String phoneNo;
	private String senderNo;
	private String sendDate;
	private String msgTitle;
	private String msgContent;
	private String regDt;
	private String updDt;
	private String sendDt;
	private String reserveDt;
	private String stateCd;
	private String fileSize;
	private String userId      ;

	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getSenderNo() {
		return senderNo;
	}
	public void setSenderNo(String senderNo) {
		this.senderNo = senderNo;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getMsgTitle() {
		return msgTitle;
	}
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getUpdDt() {
		return updDt;
	}
	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}
	public String getSendDt() {
		return sendDt;
	}
	public void setSendDt(String sendDt) {
		this.sendDt = sendDt;
	}
	public String getReserveDt() {
		return reserveDt;
	}
	public void setReserveDt(String reserveDt) {
		this.reserveDt = reserveDt;
	}
	public String getStateCd() {
		return stateCd;
	}
	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	@Override
	public String toString() {
		return "PmsTransRsltVO [rowNumber=" + rowNumber + ", msgId=" + msgId
				+ ", msgType=" + msgType + ", phoneNo=" + phoneNo
				+ ", senderNo=" + senderNo + ", sendDate=" + sendDate
				+ ", msgTitle=" + msgTitle + ", msgContent=" + msgContent
				+ ", regDt=" + regDt + ", updDt=" + updDt + ", sendDt="
				+ sendDt + ", reserveDt=" + reserveDt + ", stateCd=" + stateCd
				+ ", fileSize=" + fileSize + ", userId=" + userId + "]";
	}
	
}
