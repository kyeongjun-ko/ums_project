package com.bccns.umsserviceweb.mgr.vo;

import java.util.Arrays;

import com.bccns.umsserviceweb.common.pagination.PaginationInfo;

public class TransStatVO extends PaginationInfo  {
	
	private String rowNumber   ;
	private String userId      ;
	private String userNm      ;
	private String sendDate	   ;
	private String sendTryCnt  ;
	private String sendFailCnt ;
	private String sendWaitCnt ;
	private String sendSucCnt  ;
	private String sendSmsCnt  ;
	private String sendLmsCnt  ;
	private String sendMmsCnt  ;
	private String sendFmsCnt  ;
	private String sendVmsCnt  ;
	private String sendVqrcnt  ;
	private String dmSmsCnt    ;
	private String dmLmsCnt    ;
	private String dmConCnt    ;
	private String dmConPer    ;
	private String dmResCnt    ;
	private String dmResPer    ;
	private String faSmsCnt    ;
	private String faLmsCnt    ;
	private String faConCnt    ;
	private String faConPer    ;
	private String faResCnt    ;
	private String faResPer    ;
	
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
	public String getStatType() {
		return statType;
	}
	public void setStatType(String statType) {
		this.statType = statType;
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
	public String getSendSmsCnt() {
		return sendSmsCnt;
	}
	public void setSendSmsCnt(String sendSmsCnt) {
		this.sendSmsCnt = sendSmsCnt;
	}
	public String getSendLmsCnt() {
		return sendLmsCnt;
	}
	public void setSendLmsCnt(String sendLmsCnt) {
		this.sendLmsCnt = sendLmsCnt;
	}
	public String getSendMmsCnt() {
		return sendMmsCnt;
	}
	public void setSendMmsCnt(String sendMmsCnt) {
		this.sendMmsCnt = sendMmsCnt;
	}
	public String getSendFmsCnt() {
		return sendFmsCnt;
	}
	public void setSendFmsCnt(String sendFmsCnt) {
		this.sendFmsCnt = sendFmsCnt;
	}
	public String getSendVmsCnt() {
		return sendVmsCnt;
	}
	public void setSendVmsCnt(String sendVmsCnt) {
		this.sendVmsCnt = sendVmsCnt;
	}
	public String getSendVqrcnt() {
		return sendVqrcnt;
	}
	public void setSendVqrcnt(String sendVqrcnt) {
		this.sendVqrcnt = sendVqrcnt;
	}
	public String getDmSmsCnt() {
		return dmSmsCnt;
	}
	public void setDmSmsCnt(String dmSmsCnt) {
		this.dmSmsCnt = dmSmsCnt;
	}
	public String getDmLmsCnt() {
		return dmLmsCnt;
	}
	public void setDmLmsCnt(String dmLmsCnt) {
		this.dmLmsCnt = dmLmsCnt;
	}
	public String getDmConCnt() {
		return dmConCnt;
	}
	public void setDmConCnt(String dmConCnt) {
		this.dmConCnt = dmConCnt;
	}
	public String getDmConPer() {
		return dmConPer;
	}
	public void setDmConPer(String dmConPer) {
		this.dmConPer = dmConPer;
	}
	public String getDmResCnt() {
		return dmResCnt;
	}
	public void setDmResCnt(String dmResCnt) {
		this.dmResCnt = dmResCnt;
	}
	public String getDmResPer() {
		return dmResPer;
	}
	public void setDmResPer(String dmResPer) {
		this.dmResPer = dmResPer;
	}
	public String getFaSmsCnt() {
		return faSmsCnt;
	}
	public void setFaSmsCnt(String faSmsCnt) {
		this.faSmsCnt = faSmsCnt;
	}
	public String getFaLmsCnt() {
		return faLmsCnt;
	}
	public void setFaLmsCnt(String faLmsCnt) {
		this.faLmsCnt = faLmsCnt;
	}
	public String getFaConCnt() {
		return faConCnt;
	}
	public void setFaConCnt(String faConCnt) {
		this.faConCnt = faConCnt;
	}
	public String getFaConPer() {
		return faConPer;
	}
	public void setFaConPer(String faConPer) {
		this.faConPer = faConPer;
	}
	public String getFaResCnt() {
		return faResCnt;
	}
	public void setFaResCnt(String faResCnt) {
		this.faResCnt = faResCnt;
	}
	public String getFaResPer() {
		return faResPer;
	}
	public void setFaResPer(String faResPer) {
		this.faResPer = faResPer;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	@Override
	public String toString() {
		return "TransStatVO [rowNumber=" + rowNumber + ", userId=" + userId
				+ ", userNm=" + userNm + ", sendDate=" + sendDate
				+ ", sendTryCnt=" + sendTryCnt + ", sendFailCnt=" + sendFailCnt
				+ ", sendWaitCnt=" + sendWaitCnt + ", sendSucCnt=" + sendSucCnt
				+ ", sendSmsCnt=" + sendSmsCnt + ", sendLmsCnt=" + sendLmsCnt
				+ ", sendMmsCnt=" + sendMmsCnt + ", sendFmsCnt=" + sendFmsCnt
				+ ", sendVmsCnt=" + sendVmsCnt + ", sendVqrcnt=" + sendVqrcnt
				+ ", dmSmsCnt=" + dmSmsCnt + ", dmLmsCnt=" + dmLmsCnt
				+ ", dmConCnt=" + dmConCnt + ", dmConPer=" + dmConPer
				+ ", dmResCnt=" + dmResCnt + ", dmResPer=" + dmResPer
				+ ", faSmsCnt=" + faSmsCnt + ", faLmsCnt=" + faLmsCnt
				+ ", faConCnt=" + faConCnt + ", faConPer=" + faConPer
				+ ", faResCnt=" + faResCnt + ", faResPer=" + faResPer
				+ ", srchRegDateStart=" + srchRegDateStart
				+ ", srchRegDateEnd=" + srchRegDateEnd + ", searchType="
				+ searchType + ", searchName=" + searchName + ", status="
				+ status + ", phoneNumber=" + phoneNumber + ", result="
				+ result + ", tcsResult=" + tcsResult + ", statType="
				+ statType + ", userlv=" + userlv + ", dept=" + dept
				+ ", company=" + company + "]";
	}
	
}
