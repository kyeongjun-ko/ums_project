package com.bccns.umsserviceweb.smart.vo;

import java.util.Arrays;

import com.bccns.umsserviceweb.common.pagination.PaginationInfo;

public class SmartDmRightVO   {
	
	private String grpCd    ;
	private String grpNo    ;
	private String msgNo    ;
	private String rightNo  ;
	private String rtStrtDt ;
	private String rtEndDt  ;
	private String dept     ;
	private String company  ;
	private String userId   ;
	private String instDt   ;
	private String instId   ;
	private String updtDt   ;
	private String updtId   ;
	
	
	public String getGrpCd() {
		return grpCd;
	}
	public void setGrpCd(String grpCd) {
		this.grpCd = grpCd;
	}
	public String getGrpNo() {
		return grpNo;
	}
	public void setGrpNo(String grpNo) {
		this.grpNo = grpNo;
	}
	public String getMsgNo() {
		return msgNo;
	}
	public void setMsgNo(String msgNo) {
		this.msgNo = msgNo;
	}
	public String getRightNo() {
		return rightNo;
	}
	public void setRightNo(String rightNo) {
		this.rightNo = rightNo;
	}
	public String getRtStrtDt() {
		return rtStrtDt;
	}
	public void setRtStrtDt(String rtStrtDt) {
		this.rtStrtDt = rtStrtDt;
	}
	public String getRtEndDt() {
		return rtEndDt;
	}
	public void setRtEndDt(String rtEndDt) {
		this.rtEndDt = rtEndDt;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	@Override
	public String toString() {
		return "SmartDmRightVO [grpCd=" + grpCd + ", grpNo=" + grpNo
				+ ", msgNo=" + msgNo + ", rightNo=" + rightNo + ", rtStrtDt="
				+ rtStrtDt + ", rtEndDt=" + rtEndDt + ", dept=" + dept
				+ ", company=" + company + ", userId=" + userId + ", instDt="
				+ instDt + ", instId=" + instId + ", updtDt=" + updtDt
				+ ", updtId=" + updtId + "]";
	}

	
}
