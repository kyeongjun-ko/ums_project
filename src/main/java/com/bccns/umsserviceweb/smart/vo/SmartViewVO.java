package com.bccns.umsserviceweb.smart.vo;

import java.util.Arrays;

import com.bccns.umsserviceweb.common.pagination.PaginationInfo;

public class SmartViewVO {

	private String rowNumber;
	private String userId;
	private String codeName;
	private String grpCd;
	private String grpNm;
	private String grpNo;
	private String msgNo;
	private String subject;
	private String fileNm1;
	private String fileLink1;
	private String[] content1;
	private String fileNm2;
	private String fileLink2;
	private String[] content2;
	private String fileNm3;
	private String fileLink3;
	private String[] content3;
	private String fileNm4;
	private String fileLink4;
	private String[] content4;
	private String fileNm5;
	private String fileLink5;
	private String[] content5;

	private String smartContId;
	private String SD;
	private String SN;
	private String MSGTYPE;
	private String mGrpNo;
	private String imageselect;
	private String imagelink;

	public String getSD() {
		return SD;
	}

	public void setSD(String sD) {
		SD = sD;
	}

	public String getSN() {
		return SN;
	}

	public void setSN(String sN) {
		SN = sN;
	}

	public String getSmartContId() {
		return smartContId;
	}

	public void setSmartContId(String smartContId) {
		this.smartContId = smartContId;
	}

	public String getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getGrpCd() {
		return grpCd;
	}

	public void setGrpCd(String grpCd) {
		this.grpCd = grpCd;
	}

	public String getGrpNm() {
		return grpNm;
	}

	public void setGrpNm(String grpNm) {
		this.grpNm = grpNm;
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

	public String getFileNm1() {
		return fileNm1;
	}

	public void setFileNm1(String fileNm1) {
		this.fileNm1 = fileNm1;
	}

	public String getFileLink1() {
		return fileLink1;
	}

	public void setFileLink1(String fileLink1) {
		this.fileLink1 = fileLink1;
	}

	public String[] getContent1() {
		return content1;
	}

	public void setContent1(String[] content1) {
		this.content1 = content1;
	}

	public String getFileNm2() {
		return fileNm2;
	}

	public void setFileNm2(String fileNm2) {
		this.fileNm2 = fileNm2;
	}

	public String getFileLink2() {
		return fileLink2;
	}

	public void setFileLink2(String fileLink2) {
		this.fileLink2 = fileLink2;
	}

	public String[] getContent2() {
		return content2;
	}

	public void setContent2(String[] content2) {
		this.content2 = content2;
	}

	public String getFileNm3() {
		return fileNm3;
	}

	public void setFileNm3(String fileNm3) {
		this.fileNm3 = fileNm3;
	}

	public String getFileLink3() {
		return fileLink3;
	}

	public void setFileLink3(String fileLink3) {
		this.fileLink3 = fileLink3;
	}

	public String[] getContent3() {
		return content3;
	}

	public void setContent3(String[] content3) {
		this.content3 = content3;
	}

	public String getFileNm4() {
		return fileNm4;
	}

	public void setFileNm4(String fileNm4) {
		this.fileNm4 = fileNm4;
	}

	public String getFileLink4() {
		return fileLink4;
	}

	public void setFileLink4(String fileLink4) {
		this.fileLink4 = fileLink4;
	}

	public String[] getContent4() {
		return content4;
	}

	public void setContent4(String[] content4) {
		this.content4 = content4;
	}

	public String getFileNm5() {
		return fileNm5;
	}

	public void setFileNm5(String fileNm5) {
		this.fileNm5 = fileNm5;
	}

	public String getFileLink5() {
		return fileLink5;
	}

	public void setFileLink5(String fileLink5) {
		this.fileLink5 = fileLink5;
	}

	public String[] getContent5() {
		return content5;
	}

	public void setContent5(String[] content5) {
		this.content5 = content5;
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

	@Override
	public String toString() {
		return "SmartViewVO [rowNumber=" + rowNumber + ", userId=" + userId
				+ ", codeName=" + codeName + ", grpCd=" + grpCd + ", grpNm="
				+ grpNm + ", grpNo=" + grpNo + ", msgNo=" + msgNo
				+ ", subject=" + subject + ", fileNm1=" + fileNm1
				+ ", fileLink1=" + fileLink1 + "contents1=" + content1
				+ ", fileNm2=" + fileNm2 + ", fileLink2=" + fileLink2
				+ "contents2=" + content2 + ", fileNm3=" + fileNm3
				+ ", fileLink3=" + fileLink3 + "contents3=" + content3
				+ ", fileNm4=" + fileNm4 + ", fileLink4=" + fileLink4
				+ "contents4=" + content4 + ", fileNm5=" + fileNm5
				+ ", fileLink5=" + fileLink5 + "contents5=" + content5
				+ ", smartContId=" + smartContId + ", SD=" + SD + ", SN=" + SN
				+ ", MSGTYPE=" + MSGTYPE + ", mGrpNo=" + mGrpNo
				+ ", imageselect=" + imageselect + ", imagelink=" + imagelink
				+ "]";
	}

}
