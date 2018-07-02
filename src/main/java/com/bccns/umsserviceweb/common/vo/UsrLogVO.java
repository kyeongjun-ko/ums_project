package com.bccns.umsserviceweb.common.vo;

public class UsrLogVO { 

	private String logIp;
	private String logId;
	private String logType;
	private String logDate;
	public String getLogIp() {
		return logIp;
	}
	public void setLogIp(String logIp) {
		this.logIp = logIp;
	}
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public String getLogDate() {
		return logDate;
	}
	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}
	@Override
	public String toString() {
		return "UsrLogVO [logIp=" + logIp + ", logId=" + logId + ", logType="
				+ logType + ", logDate=" + logDate + "]";
	}
  
	
	
}
