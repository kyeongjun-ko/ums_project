package com.bccns.umsserviceweb.common.vo;

public class CallBackVO {
	private String rowNumber;
	private String userId;
	private String userNm;
	private String callbackNo;
	private String dept;
	private String company;
	private String method;
	private String status;
	private String statusCd;
	private String instDt;
	private String updtDt;

	private String authCode;

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

	public String getCallbackNo() {
		return callbackNo;
	}

	public void setCallbackNo(String callbackNo) {
		this.callbackNo = callbackNo;
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

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	public String getInstDt() {
		return instDt;
	}

	public void setInstDt(String instDt) {
		this.instDt = instDt;
	}

	public String getUpdtDt() {
		return updtDt;
	}

	public void setUpdtDt(String updtDt) {
		this.updtDt = updtDt;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	@Override
	public String toString() {
		return "CallBackVO [rowNumber=" + rowNumber + ", userId=" + userId
				+ ", userNm=" + userNm + ", callbackNo=" + callbackNo
				+ ", dept=" + dept + ", company=" + company + ", method="
				+ method + ", status=" + status + ", statusCd=" + statusCd
				+ ", instDt=" + instDt + ", updtDt=" + updtDt + ", authCode="
				+ authCode + "]";
	}

}
