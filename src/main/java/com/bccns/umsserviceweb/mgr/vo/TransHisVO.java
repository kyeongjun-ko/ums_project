package com.bccns.umsserviceweb.mgr.vo;

import java.util.Arrays;

import com.bccns.umsserviceweb.common.pagination.PaginationInfo;

public class TransHisVO extends PaginationInfo  {
	
	private String rowNumber;
	private String msgId;
	private String userId;
	private String userNm;
	private String phoneNumber;
	private String stype;
	private String sendDate;
	private String confDate;
	private String resDate;
	private String name;
	private String contents;
	private String srchRegDateStart;
	private String srchRegDateEnd;
	private String searchType;
	private String searchName;
	private String result;
	private String tcsResult;
	
	private String userlv;
	private String dept;
	private String company;
	private String suserId;
	
	
	public String getSuserId() {
		return suserId;
	}
	public void setSuserId(String suserId) {
		this.suserId = suserId;
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
	public String getResDate() {
		return resDate;
	}
	public void setResDate(String resDate) {
		this.resDate = resDate;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getStype() {
		return stype;
	}
	public void setStype(String stype) {
		this.stype = stype;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getConfDate() {
		return confDate;
	}
	public void setConfDate(String confDate) {
		this.confDate = confDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
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
	@Override
	public String toString() {
		return "TransHisVO [rowNumber=" + rowNumber + ", msgId=" + msgId
				+ ", userId=" + userId + ", userNm=" + userNm
				+ ", phoneNumber=" + phoneNumber + ", stype=" + stype
				+ ", sendDate=" + sendDate + ", confDate=" + confDate
				+ ", resDate=" + resDate + ", name=" + name + ", contents="
				+ contents + ", srchRegDateStart=" + srchRegDateStart
				+ ", srchRegDateEnd=" + srchRegDateEnd + ", searchType="
				+ searchType + ", searchName=" + searchName + ", result="
				+ result + ", tcsResult=" + tcsResult + ", userlv=" + userlv
				+ ", dept=" + dept + ", company=" + company + "]";
	}
	
	
	
}
