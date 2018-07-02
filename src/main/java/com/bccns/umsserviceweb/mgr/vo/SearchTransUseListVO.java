package com.bccns.umsserviceweb.mgr.vo;

import java.util.Arrays;

import com.bccns.umsserviceweb.common.pagination.PaginationInfo;

public class SearchTransUseListVO extends PaginationInfo  {
	private String userId;
	private String sendDate;
	private String msgType;
	private String subject;
	private String sendCnt;
	private String succCnt;
	private String failCnt;
	private String totalCnt;
	private String instDt;
	private String instId;
	private String updtDt;
	private String updtId;
	private String searchType;
	private String searchName;
	private String regFg;
	private String yyyymm;
	private String yyyymm1;
	
	public String getYyyymm1() {
		return yyyymm1;
	}
	public void setYyyymm1(String yyyymm1) {
		this.yyyymm1 = yyyymm1;
	}
	public String getYyyymm() {
		return yyyymm;
	}
	public void setYyyymm(String yyyymm) {
		this.yyyymm = yyyymm;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSendCnt() {
		return sendCnt;
	}
	public void setSendCnt(String sendCnt) {
		this.sendCnt = sendCnt;
	}
	public String getSuccCnt() {
		return succCnt;
	}
	public void setSuccCnt(String succCnt) {
		this.succCnt = succCnt;
	}
	public String getFailCnt() {
		return failCnt;
	}
	public void setFailCnt(String failCnt) {
		this.failCnt = failCnt;
	}
	public String getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(String totalCnt) {
		this.totalCnt = totalCnt;
	}
	public String getInstDt() {
		return instDt;
	}
	public void setInstDt(String instDt) {
		this.instDt = instDt;
	}
	public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
	public String getUpdtDt() {
		return updtDt;
	}
	public void setUpdtDt(String updtDt) {
		this.updtDt = updtDt;
	}
	public String getUpdtId() {
		return updtId;
	}
	public void setUpdtId(String updtId) {
		this.updtId = updtId;
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
	public String getRegFg() {
		return regFg;
	}
	public void setRegFg(String regFg) {
		this.regFg = regFg;
	}
	@Override
	public String toString() {
		return "SearchTransUseListVO [userId=" + userId + ", sendDate="
				+ sendDate + ", msgType=" + msgType + ", subject=" + subject
				+ ", sendCnt=" + sendCnt + ", succCnt=" + succCnt
				+ ", failCnt=" + failCnt + ", totalCnt=" + totalCnt
				+ ", instDt=" + instDt + ", instId=" + instId + ", updtDt="
				+ updtDt + ", updtId=" + updtId + ", searchType=" + searchType
				+ ", searchName=" + searchName + ", regFg=" + regFg
				+ ", yyyymm=" + yyyymm + ", yyyymm1=" + yyyymm1 + "]";
	}
	 
	
}
