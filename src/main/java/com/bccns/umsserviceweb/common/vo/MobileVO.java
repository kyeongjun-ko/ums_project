package com.bccns.umsserviceweb.common.vo;

public class MobileVO {

	private String userNm;
	private String userId;
	private String userPw;
	private String telNo2;
	private String osGb;
	private String deviceToken;
	private String result_cd;
	private String result_msg;

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getTelNo2() {
		return telNo2;
	}

	public void setTelNo2(String telNo2) {
		this.telNo2 = telNo2;
	}

	public String getOsGb() {
		return osGb;
	}

	public void setOsGb(String osGb) {
		this.osGb = osGb;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getResult_cd() {
		return result_cd;
	}

	public void setResult_cd(String result_cd) {
		this.result_cd = result_cd;
	}

	public String getResult_msg() {
		return result_msg;
	}

	public void setResult_msg(String result_msg) {
		this.result_msg = result_msg;
	}

	@Override
	public String toString() {
		return "MobileVO [userNm=" + userNm + ", userId=" + userId
				+ ", userPw=" + userPw + ", telNo2=" + telNo2 + ", osGb="
				+ osGb + ", deviceToken=" + deviceToken + ", result_cd="
				+ result_cd + ", result_msg=" + result_msg + "]";
	}

}
