package com.bccns.umsserviceweb.mgr.vo;

import com.bccns.umsserviceweb.common.pagination.PaginationInfo;

public class SearchTransResvVO extends PaginationInfo{
	
	private String rowNumber;
	private String userId;
	
	private String userType;
	private String msgType;
	private String searchType;
	private String srchRegDateStart;
	private String srchRegDateEnd;
	private String srchContent;
	
	private String userlv;
	private String dept;
	private String company;
	
	private String suserId;
	private String searchName;
	
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
	public String getSrchContent() {
		return srchContent;
	}
	public void setSrchContent(String srchContent) {
		this.srchContent = srchContent;
	}
	
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
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
	@Override
	public String toString() {
		return "SearchTransResvVO [rowNumber=" + rowNumber + ", userId="
				+ userId + ", userType=" + userType + ", msgType=" + msgType
				+ ", searchType=" + searchType + ", srchRegDateStart="
				+ srchRegDateStart + ", srchRegDateEnd=" + srchRegDateEnd
				+ ", srchContent=" + srchContent + ", userlv=" + userlv
				+ ", dept=" + dept + ", company=" + company + ", suserId="
				+ suserId + ", searchName=" + searchName + "]";
	}
}
