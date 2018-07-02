package com.bccns.umsserviceweb.push.vo;

import com.bccns.umsserviceweb.common.pagination.PaginationInfo;

public class SearchPmsTransRsltVO extends PaginationInfo{
	
	private String rowNumber;
	private String userId;
	
	private String userType;
	private String msgType;
	private String searchType;
	private String srchRegDateStart;
	private String srchRegDateEnd;
	private String srchContent;
	private String phoneNumber;
	private String userNm;
	
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
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	
	public String getSrchContent() {
		return srchContent;
	}
	public void setSrchContent(String srchContent) {
		this.srchContent = srchContent;
	}
	@Override
	public String toString() {
		return "SearchTransRsltVO [rowNumber=" + rowNumber + ", userId="
				+ userId + ", userType=" + userType + ", msgType=" + msgType
				+ ", searchType=" + searchType + ", srchRegDateStart="
				+ srchRegDateStart + ", srchRegDateEnd=" + srchRegDateEnd
				+ ", srchContent=" + srchContent + ", phoneNumber="
				+ phoneNumber + ", userNm=" + userNm + ", userlv=" + userlv
				+ ", dept=" + dept + ", company=" + company + "]";
	}
}
