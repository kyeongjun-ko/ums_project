package com.bccns.umsserviceweb.ums.vo.PDS;
import com.bccns.umsserviceweb.ums.vo.PDS.Code;

public class BCServiceResultVO {
	private String transactionId;
	private String serviceId;
	private String code;
	private String message;
	
	public BCServiceResultVO() {
	}
	
	public BCServiceResultVO(String transactionId, String serviceId, String code, String message) {
		this.transactionId = transactionId;
		this.serviceId = serviceId;
		this.code = code;
		this.message = message;
	}
	
	public BCServiceResultVO(String transactionId, String serviceId, Code code) {
		this.transactionId = transactionId;
		this.serviceId = serviceId;
		this.code = code.getCode();
		this.message = code.getMessage();
	}
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BCServiceResultVO [transactionId=");
		builder.append(transactionId);
		builder.append(", serviceId=");
		builder.append(serviceId);
		builder.append(", code=");
		builder.append(code);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
}
