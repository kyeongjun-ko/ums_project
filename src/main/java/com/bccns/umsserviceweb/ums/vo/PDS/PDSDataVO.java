package com.bccns.umsserviceweb.ums.vo.PDS;

public class PDSDataVO {
	private String requestId;
	private String telNo;
	private String telCom;
	private String modelId;
	private String modelName;
	private String osName;
	private String osVender;
	private String osVersion;
	private String smartPhnYn;
	
	public String getRequestId() {
		return requestId;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		if (requestId != null) {
			builder.append("requestId=");
			builder.append(requestId);
			builder.append(", ");
		}
		if (telNo != null) {
			builder.append("telNo=");
			builder.append(telNo);
			builder.append(", ");
		}
		if (telCom != null) {
			builder.append("telCom=");
			builder.append(telCom);
			builder.append(", ");
		}
		if (modelId != null) {
			builder.append("modelId=");
			builder.append(modelId);
			builder.append(", ");
		}
		if (modelName != null) {
			builder.append("modelName=");
			builder.append(modelName);
			builder.append(", ");
		}
		if (osName != null) {
			builder.append("osName=");
			builder.append(osName);
			builder.append(", ");
		}
		if (osVender != null) {
			builder.append("osVender=");
			builder.append(osVender);
			builder.append(", ");
		}
		if (osVersion != null) {
			builder.append("osVersion=");
			builder.append(osVersion);
			builder.append(", ");
		}
		if (smartPhnYn != null) {
			builder.append("smartPhnYn=");
			builder.append(smartPhnYn);
		}
		builder.append("]");
		return builder.toString();
	}
	public String getTelCom() {
		return telCom;
	}
	public String getModelId() {
		return modelId;
	}
	public String getModelName() {
		return modelName;
	}
	public String getOsName() {
		return osName;
	}
	public String getOsVender() {
		return osVender;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public String getSmartPhnYn() {
		return smartPhnYn;
	}
	public void setTelCom(String telCom) {
		this.telCom = telCom;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public void setOsName(String osName) {
		this.osName = osName;
	}
	public void setOsVender(String osVender) {
		this.osVender = osVender;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public void setSmartPhnYn(String smartPhnYn) {
		this.smartPhnYn = smartPhnYn;
	}
}
