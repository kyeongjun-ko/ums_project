package com.bccns.umsserviceweb.ums.vo;

public class AddrExcelVO {
	private int rowIndex;
    private String addrMetaSeq;
	private String phoneNo;
    private String addrName;
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
	public String getAddrName() {
		return addrName;
	}
	public void setAddrName(String addrName) {
		this.addrName = addrName;
	}
	@Override
	public String toString() {
		return "AddrExcelVO [rowIndex=" + rowIndex + ", addrMetaSeq="
				+ addrMetaSeq + ", phoneNo=" + phoneNo + ", addrName="
				+ addrName + "]";
	}
    
    
}
