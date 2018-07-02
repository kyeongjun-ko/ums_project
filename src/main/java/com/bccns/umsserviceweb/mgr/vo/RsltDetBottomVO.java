package com.bccns.umsserviceweb.mgr.vo;

import com.bccns.umsserviceweb.common.pagination.PaginationInfo;

public class RsltDetBottomVO extends PaginationInfo {
	private String rowNumber;
	private String msgId;
	private String jobId;
	private String subJobId;
	private String userId;
	private String destName;
	private String phoneNumber;
	private String sendDate;
	private String result;
	private String tcsResult;
	private String resultDesc;
	
	


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





	public String getSubJobId() {
		return subJobId;
	}





	public void setSubJobId(String subJobId) {
		this.subJobId = subJobId;
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





	public String getResult() {
		return result;
	}





	public void setResult(String result) {
		this.result = result;
	}





	public String getTcsResult() {
		return tcsResult;
	}





	public void setTcsResult(String tcsResult) {
		this.tcsResult = tcsResult;
	}





	public String getResultDesc() {
		return resultDesc;
	}





	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}





	@Override
	public String toString() {
		return "RsltDetTopVO [rowNumber=" + rowNumber + "msgId=" + msgId + ", jobId=" + jobId 
				+ ", subJobId="	+ subJobId + ", userId=" + userId +", destName=" + destName
				+", phoneNumber=" + phoneNumber +", sendDate=" + sendDate 
				+ "result=" + result + ", tcsResult=" + tcsResult + "resultDesc=" + resultDesc +"]";
	}
}
