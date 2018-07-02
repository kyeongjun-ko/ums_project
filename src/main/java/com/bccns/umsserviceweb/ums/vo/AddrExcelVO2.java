package com.bccns.umsserviceweb.ums.vo;

public class AddrExcelVO2 {
	private int rowIndex;
    private String addrMetaSeq;
	private String phoneNo;
	private String addrName;
    private String content;
    
    
	public String getContent() {
		return content;
	}
	public String getAddrName() {
		return addrName;
	}
	public void setAddrName(String addrName) {
		this.addrName = addrName;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	public String getAddrMetaSeq() {
		return addrMetaSeq;
	}
	public void setAddrMetaSeq(String addrMetaSeq) {
		this.addrMetaSeq = addrMetaSeq;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	@Override
	public String toString() {
		return "AddrExcelVO [rowIndex=" + rowIndex + ", addrMetaSeq="
				+ addrMetaSeq + ", phoneNo=" + phoneNo + 
				 "addrName="	+ addrName+ "addrContent=" + content + "]";
	}
    
    
}
