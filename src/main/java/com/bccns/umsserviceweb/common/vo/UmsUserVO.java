package com.bccns.umsserviceweb.common.vo;

import java.util.List;

import com.bccns.umsserviceweb.mgr.vo.MsgBoxVO;

public class UmsUserVO { 
	private String userId;
	private String userNm;
	private String userPw;
	private String userLv;
	private String dept;
	private String company;
	private String deptNm;
	private String companyNm;
	private String email;
	private String telNo1;
	private String telNo2;
	private String callbackNo1;
	private String callbackNo2;
	private String callbackNo3;
	private String callbackNo4;
	private String que;
	private String ans;
	private String instDt;
	private String instId;
	private String updtDt;
	private String updtId;
	private String loginDt;
	private String userLvNm;
	private String loginYn;
	private String confYn;
	
	private String updateDay;
	
	private List<CommCodeVO> deptCdList;
	private List<CommCodeVO> comCdList;
	private List<CommCodeVO> posCdList;
	private List<CallBackVO> callbackList;
	
	
	public String getDeptNm() {
		return deptNm;
	}
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}
	public String getCompanyNm() {
		return companyNm;
	}
	public void setCompanyNm(String companyNm) {
		this.companyNm = companyNm;
	}
	public String getConfYn() {
		return confYn;
	}
	public void setConfYn(String confYn) {
		this.confYn = confYn;
	}
	public List<CommCodeVO> getDeptCdList() {
		return deptCdList;
	}
	public void setDeptCdList(List<CommCodeVO> deptCdList) {
		this.deptCdList = deptCdList;
	}
	public List<CommCodeVO> getComCdList() {
		return comCdList;
	}
	public void setComCdList(List<CommCodeVO> comCdList) {
		this.comCdList = comCdList;
	}
	public List<CommCodeVO> getPosCdList() {
		return posCdList;
	}
	public void setPosCdList(List<CommCodeVO> posCdList) {
		this.posCdList = posCdList;
	}
	public List<CallBackVO> getCallbackList() {
		return callbackList;
	}
	public void setCallbackList(List<CallBackVO> callbackList) {
		this.callbackList = callbackList;
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
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getUserLv() {
		return userLv;
	}
	public void setUserLv(String userLv) {
		this.userLv = userLv;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelNo1() {
		return telNo1;
	}
	public void setTelNo1(String telNo1) {
		this.telNo1 = telNo1;
	}
	public String getTelNo2() {
		return telNo2;
	}
	public void setTelNo2(String telNo2) {
		this.telNo2 = telNo2;
	}
	public String getCallbackNo1() {
		return callbackNo1;
	}
	public void setCallbackNo1(String callbackNo1) {
		this.callbackNo1 = callbackNo1;
	}
	public String getCallbackNo2() {
		return callbackNo2;
	}
	public void setCallbackNo2(String callbackNo2) {
		this.callbackNo2 = callbackNo2;
	}
	public String getCallbackNo3() {
		return callbackNo3;
	}
	public void setCallbackNo3(String callbackNo3) {
		this.callbackNo3 = callbackNo3;
	}
	public String getCallbackNo4() {
		return callbackNo4;
	}
	public void setCallbackNo4(String callbackNo4) {
		this.callbackNo4 = callbackNo4;
	}
	public String getQue() {
		return que;
	}
	public void setQue(String que) {
		this.que = que;
	}
	public String getAns() {
		return ans;
	}
	public void setAns(String ans) {
		this.ans = ans;
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
	public String getLoginDt() {
		return loginDt;
	}
	public void setLoginDt(String loginDt) {
		this.loginDt = loginDt;
	}
	public String getUserLvNm() {
		return userLvNm;
	}
	public void setUserLvNm(String userLvNm) {
		this.userLvNm = userLvNm;
	}
	public String getLoginYn() {
		return loginYn;
	}
	public void setLoginYn(String loginYn) {
		this.loginYn = loginYn;
	}
	public String getUpdateDay() {
		return updateDay;
	}
	public void setUpdateDay(String updateDay) {
		this.updateDay = updateDay;
	}
	@Override
	public String toString() {
		return "UmsUserVO [userId=" + userId + ", userNm=" + userNm
				+ ", userPw=" + userPw + ", userLv=" + userLv + ", dept="
				+ dept + ", company=" + company + ", deptNm=" + deptNm
				+ ", companyNm=" + companyNm + ", email=" + email + ", telNo1="
				+ telNo1 + ", telNo2=" + telNo2 + ", callbackNo1="
				+ callbackNo1 + ", callbackNo2=" + callbackNo2
				+ ", callbackNo3=" + callbackNo3 + ", callbackNo4="
				+ callbackNo4 + ", que=" + que + ", ans=" + ans + ", instDt="
				+ instDt + ", instId=" + instId + ", updtDt=" + updtDt
				+ ", updtId=" + updtId + ", loginDt=" + loginDt + ", userLvNm="
				+ userLvNm + ", loginYn=" + loginYn + ", confYn=" + confYn
				+ ", updateDay=" + updateDay + ", deptCdList=" + deptCdList
				+ ", comCdList=" + comCdList + ", posCdList=" + posCdList + "]";
	}
	
	
	
}
