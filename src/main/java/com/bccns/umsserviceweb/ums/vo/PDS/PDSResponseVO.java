package com.bccns.umsserviceweb.ums.vo.PDS;


public class PDSResponseVO extends BCResponseVO {
	private PDSBodyVO body;
	public PDSBodyVO getBody() {
		return body;
	}
	public void setBody(PDSBodyVO body) {
		this.body = body;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[body=");
		builder.append(body);
		builder.append("]");
		return builder.toString();
	}
}
