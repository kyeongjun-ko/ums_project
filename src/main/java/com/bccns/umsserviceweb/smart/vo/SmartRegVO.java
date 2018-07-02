package com.bccns.umsserviceweb.smart.vo;

import java.util.Arrays;

import com.bccns.umsserviceweb.common.pagination.PaginationInfo;

public class SmartRegVO   {
	
	private String[] msgArNo;	
	private String[] RightDept;
	private String[] RightSdt;
	private String[] RightEdt;
	private String rowNumber;
	private String userId;
	private String codeName;
	private String grpCd;
	private String grpNm;
	private String grpNo;
	private String msgNo;
	private String subject;
	private String contents1;
	private String contents1Temp;
	private String contents2;
	private String contents3;
	private String contents4;
	private String contents5;
	private String contents6;
	private String contents7;
	private String contents8;
	private String contents9;
	private String contents10;
	private String instDt;
	private String instId;
	private String updtDt;
	private String updtId;
	private String searchType;
	private String searchName;
	
	private String MSGTYPE;
	private String mGrpNo;
	private String imageselect;
	private String imagelink;
	
	
	
	
	public String[] getRightDept() {
		return RightDept;
	}
	public void setRightDept(String[] rightDept) {
		RightDept = rightDept;
	}
	public String[] getRightSdt() {
		return RightSdt;
	}
	public void setRightSdt(String[] rightSdt) {
		RightSdt = rightSdt;
	}
	public String[] getRightEdt() {
		return RightEdt;
	}
	public void setRightEdt(String[] rightEdt) {
		RightEdt = rightEdt;
	}
	public String getMSGTYPE() {
		return MSGTYPE;
	}
	public void setMSGTYPE(String mSGTYPE) {
		MSGTYPE = mSGTYPE;
	}
	public String getmGrpNo() {
		return mGrpNo;
	}
	public void setmGrpNo(String mGrpNo) {
		this.mGrpNo = mGrpNo;
	}
	public String getImageselect() {
		return imageselect;
	}
	public void setImageselect(String imageselect) {
		this.imageselect = imageselect;
	}
	public String getImagelink() {
		return imagelink;
	}
	public void setImagelink(String imagelink) {
		this.imagelink = imagelink;
	}
	public String[] getFile_name() {
		return file_name;
	}
	public void setFile_name(String[] file_name) {
		this.file_name = file_name;
	}
	public String[] getFile_size() {
		return file_size;
	}
	public void setFile_size(String[] file_size) {
		this.file_size = file_size;
	}
	public String[] getFile_type() {
		return file_type;
	}
	public void setFile_type(String[] file_type) {
		this.file_type = file_type;
	}
	public String[] getFile_path() {
		return file_path;
	}
	public void setFile_path(String[] file_path) {
		this.file_path = file_path;
	}
	public String[] getFile_servername() {
		return file_servername;
	}
	public void setFile_servername(String[] file_servername) {
		this.file_servername = file_servername;
	}
	public String[] getFile_contenttype() {
		return file_contenttype;
	}
	public void setFile_contenttype(String[] file_contenttype) {
		this.file_contenttype = file_contenttype;
	}
	/*
     * file_name 배열
     */
    public String[] file_name;
    /*
     * file_size 배열
     */
    public String[] file_size;
    
    /*
     * file_type 배열
     */
    public String[] file_type;
    
    /*
     * file_path 배열
     */
    public String[] file_path;
    /*
     * file_servername 배열
     */
    public String[] file_servername;
    /*
     * file_contenttype 배열
     */
    public String[] file_contenttype;
	
	public String getContents1Temp() {
		return contents1Temp;
	}
	public void setContents1Temp(String contents1Temp) {
		this.contents1Temp = contents1Temp;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String[] getMsgArNo() {
		return msgArNo;
	}
	public void setMsgArNo(String[] msgArNo) {
		this.msgArNo = msgArNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGrpCd() {
		return grpCd;
	}
	public void setGrpCd(String grpCd) {
		this.grpCd = grpCd;
	}
	public String getGrpNo() {
		return grpNo;
	}
	public void setGrpNo(String grpNo) {
		this.grpNo = grpNo;
	}
	public String getMsgNo() {
		return msgNo;
	}
	public void setMsgNo(String msgNo) {
		this.msgNo = msgNo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContents1() {
		return contents1;
	}
	public void setContents1(String contents1) {
		this.contents1 = contents1;
	}
	public String getContents2() {
		return contents2;
	}
	public void setContents2(String contents2) {
		this.contents2 = contents2;
	}
	public String getContents3() {
		return contents3;
	}
	public void setContents3(String contents3) {
		this.contents3 = contents3;
	}
	public String getContents4() {
		return contents4;
	}
	public void setContents4(String contents4) {
		this.contents4 = contents4;
	}
	public String getContents5() {
		return contents5;
	}
	public void setContents5(String contents5) {
		this.contents5 = contents5;
	}
	public String getContents6() {
		return contents6;
	}
	public void setContents6(String contents6) {
		this.contents6 = contents6;
	}
	public String getContents7() {
		return contents7;
	}
	public void setContents7(String contents7) {
		this.contents7 = contents7;
	}
	public String getContents8() {
		return contents8;
	}
	public void setContents8(String contents8) {
		this.contents8 = contents8;
	}
	public String getContents9() {
		return contents9;
	}
	public void setContents9(String contents9) {
		this.contents9 = contents9;
	}
	public String getContents10() {
		return contents10;
	}
	public void setContents10(String contents10) {
		this.contents10 = contents10;
	}
	public String getInstDt() {
		return instDt;
	}
	public void setInstDt(String instDt) {
		this.instDt = instDt;
	}
	public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
	public String getUpdtDt() {
		return updtDt;
	}
	public void setUpdtDt(String updtDt) {
		this.updtDt = updtDt;
	}
	public String getUpdtId() {
		return updtId;
	}
	public void setUpdtId(String updtId) {
		this.updtId = updtId;
	}
	public String getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public String getGrpNm() {
		return grpNm;
	}
	public void setGrpNm(String grpNm) {
		this.grpNm = grpNm;
	}
	@Override
	public String toString() {
		return "SmartRegVO [msgArNo=" + Arrays.toString(msgArNo)
				+ ", RightDept=" + Arrays.toString(RightDept) + ", RightSdt="
				+ Arrays.toString(RightSdt) + ", RightEdt="
				+ Arrays.toString(RightEdt) + ", rowNumber=" + rowNumber
				+ ", userId=" + userId + ", codeName=" + codeName + ", grpCd="
				+ grpCd + ", grpNm=" + grpNm + ", grpNo=" + grpNo + ", msgNo="
				+ msgNo + ", subject=" + subject + ", contents1=" + contents1
				+ ", contents1Temp=" + contents1Temp + ", contents2="
				+ contents2 + ", contents3=" + contents3 + ", contents4="
				+ contents4 + ", contents5=" + contents5 + ", contents6="
				+ contents6 + ", contents7=" + contents7 + ", contents8="
				+ contents8 + ", contents9=" + contents9 + ", contents10="
				+ contents10 + ", instDt=" + instDt + ", instId=" + instId
				+ ", updtDt=" + updtDt + ", updtId=" + updtId + ", searchType="
				+ searchType + ", searchName=" + searchName + ", MSGTYPE="
				+ MSGTYPE + ", mGrpNo=" + mGrpNo + ", imageselect="
				+ imageselect + ", imagelink=" + imagelink + ", file_name="
				+ Arrays.toString(file_name) + ", file_size="
				+ Arrays.toString(file_size) + ", file_type="
				+ Arrays.toString(file_type) + ", file_path="
				+ Arrays.toString(file_path) + ", file_servername="
				+ Arrays.toString(file_servername) + ", file_contenttype="
				+ Arrays.toString(file_contenttype) + "]";
	}
}
