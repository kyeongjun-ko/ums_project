package com.bccns.umsserviceweb.push.vo;

import com.bccns.umsserviceweb.common.pagination.PaginationInfo;

public class SearchPmsTransStatVO extends PaginationInfo  {
	
	private String rowNumber   ;
	private String userId      ;
	private String userNm      ;
	private String sendDate	   ;
	private String sendTryCnt  ;
	private String sendFailCnt ;
	private String sendWaitCnt ;
	private String sendSucCnt  ;
	private String sendTextCnt ;
	private String sendHtmlCnt ;
	private String sendImageCnt; 
	
	private String srchRegDateStart;
	private String srchRegDateEnd;
	private String searchType;
	private String searchName;
	private String status;
	private String phoneNumber;
	private String result;
	private String tcsResult;
	
	private String statType;
	
	private String userlv;
	private String dept;
	private String company;
	
	private String suserId;

	public String getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getSendTryCnt() {
		return sendTryCnt;
	}

	public void setSendTryCnt(String sendTryCnt) {
		this.sendTryCnt = sendTryCnt;
	}

	public String getSendFailCnt() {
		return sendFailCnt;
	}

	public void setSendFailCnt(String sendFailCnt) {
		this.sendFailCnt = sendFailCnt;
	}

	public String getSendWaitCnt() {
		return sendWaitCnt;
	}

	public void setSendWaitCnt(String sendWaitCnt) {
		this.sendWaitCnt = sendWaitCnt;
	}

	public String getSendSucCnt() {
		return sendSucCnt;
	}

	public void setSendSucCnt(String sendSucCnt) {
		this.sendSucCnt = sendSucCnt;
	}

	public String getSendTextCnt() {
		return sendTextCnt;
	}

	public void setSendTextCnt(String sendTextCnt) {
		this.sendTextCnt = sendTextCnt;
	}

	public String getSendHtmlCnt() {
		return sendHtmlCnt;
	}

	public void setSendHtmlCnt(String sendHtmlCnt) {
		this.sendHtmlCnt = sendHtmlCnt;
	}

	public String getSendImageCnt() {
		return sendImageCnt;
	}

	public void setSendImageCnt(String sendImageCnt) {
		this.sendImageCnt = sendImageCnt;
	}

	public String getSrchRegDateStart() {
		return srchRegDateStart;
	}

	public void setSrchRegDateStart(String srchRegDateStart) {
		this.srchRegDateStart = srchRegDateStart;
	}

	public String getSrchRegDateEnd() {
		return srchRegDateEnd;
	}

	public void setSrchRegDateEnd(String srchRegDateEnd) {
		this.srchRegDateEnd = srchRegDateEnd;
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public String getStatType() {
		return statType;
	}

	public void setStatType(String statType) {
		this.statType = statType;
	}

	public String getUserlv() {
		return userlv;
	}

	public void setUserlv(String userlv) {
		this.userlv = userlv;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getSuserId() {
		return suserId;
	}

	public void setSuserId(String suserId) {
		this.suserId = suserId;
	}

	@Override
	public String toString() {
		return "TransStatVO [rowNumber=" + rowNumber + ", userId=" + userId
				+ ", userNm=" + userNm + ", sendDate=" + sendDate
				+ ", sendTryCnt=" + sendTryCnt + ", sendFailCnt=" + sendFailCnt
				+ ", sendWaitCnt=" + sendWaitCnt + ", sendSucCnt=" + sendSucCnt
				+ ", sendTextCnt=" + sendTextCnt + ", sendHtmlCnt="
				+ sendHtmlCnt + ", sendImageCnt=" + sendImageCnt
				+ ", srchRegDateStart=" + srchRegDateStart
				+ ", srchRegDateEnd=" + srchRegDateEnd + ", searchType="
				+ searchType + ", searchName=" + searchName + ", status="
				+ status + ", phoneNumber=" + phoneNumber + ", result="
				+ result + ", tcsResult=" + tcsResult + ", statType="
				+ statType + ", userlv=" + userlv + ", dept=" + dept
				+ ", company=" + company + ", suserId=" + suserId + "]";
	}
	
}
