package com.bccns.umsserviceweb.mgr.vo;

public class RsltDetMiddleVO {
	private String totCount;
	private String sucCount;
	private String ingCount;
	private String noAnsCount;
	private String busyLineCount;
	private String noNumCount;
	private String totFailCount;
	private String perSucc;
	private String perIng;
	private String perFail;
	
	
	
	public String getTotCount() {
		return totCount;
	}
	public void setTotCount(String totCount) {
		this.totCount = totCount;
	}
	public String getSucCount() {
		return sucCount;
	}
	public void setSucCount(String sucCount) {
		this.sucCount = sucCount;
	}
	public String getIngCount() {
		return ingCount;
	}
	public void setIngCount(String ingCount) {
		this.ingCount = ingCount;
	}
	public String getNoAnsCount() {
		return noAnsCount;
	}
	public void setNoAnsCount(String noAnsCount) {
		this.noAnsCount = noAnsCount;
	}
	public String getBusyLineCount() {
		return busyLineCount;
	}
	public void setBusyLineCount(String busyLineCount) {
		this.busyLineCount = busyLineCount;
	}
	public String getNoNumCount() {
		return noNumCount;
	}
	public void setNoNumCount(String noNumCount) {
		this.noNumCount = noNumCount;
	}
	public String getTotFailCount() {
		return totFailCount;
	}
	public void setTotFailCount(String totFailCount) {
		this.totFailCount = totFailCount;
	}
	public String getPerSucc() {
		return perSucc;
	}
	public void setPerSucc(String perSucc) {
		this.perSucc = perSucc;
	}
	public String getPerIng() {
		return perIng;
	}
	public void setPerIng(String perIng) {
		this.perIng = perIng;
	}
	public String getPerFail() {
		return perFail;
	}
	public void setPerFail(String perFail) {
		this.perFail = perFail;
	}
	@Override
	public String toString() {
		return "RsltDetMiddleVO [totCount=" + totCount + "sucCount=" + sucCount + ", ingCount=" + ingCount 
				+ ", noAnsCount="	+ noAnsCount + ", busyLineCount=" + busyLineCount +", noNumCount=" + noNumCount
				+", totFailCount=" + totFailCount +"]";
	}
}
