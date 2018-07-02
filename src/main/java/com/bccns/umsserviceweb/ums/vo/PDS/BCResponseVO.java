package com.bccns.umsserviceweb.ums.vo.PDS;

public class BCResponseVO {
	private BCServiceResultVO service;
	public BCServiceResultVO getService() {
		return service;
	}
	public void setService(BCServiceResultVO service) {
		this.service = service;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BCResponseVO [service=");
		builder.append(service);
		builder.append("]");
		return builder.toString();
	}
}
