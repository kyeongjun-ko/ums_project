package com.bccns.umsserviceweb.ums.vo.PDS;

public class PDSBodyVO {
	private PDSResultVO result;
	private PDSDataVO data;
	public PDSResultVO getResult() {
		return result;
	}
	public void setResult(PDSResultVO result) {
		this.result = result;
	}
	public PDSDataVO getData() {
		return data;
	}
	public void setData(PDSDataVO data) {
		this.data = data;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[result=");
		builder.append(result);
		builder.append(", data=");
		builder.append(data);
		builder.append("]");
		return builder.toString();
	}
}
