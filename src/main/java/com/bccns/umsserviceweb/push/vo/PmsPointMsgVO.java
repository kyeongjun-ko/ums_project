package com.bccns.umsserviceweb.push.vo;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class PmsPointMsgVO {
	
	private String macAddr;
	private String msgNo;
	private String subject;
	private String contents1;
	private String contents2;
	private String contents3;
    private String contents4;
    private String contents5;
    private String contents6;
    private String contents7;
    private String contents8;
    private String contents9;
    private String contents10;
    


	@Override
	public String toString() {
		return "PmsPointMsgVO [macAddr=" + macAddr + ", msgNo=" + msgNo
				+ ", subject=" + subject + ", contents1=" + contents1
				+ ", contents2=" + contents2 + ", contents3=" + contents3
				+ ", contents4=" + contents4 + ", contents5=" + contents5
				+ ", contents6=" + contents6 + ", contents7=" + contents7
				+ ", contents8=" + contents8 + ", contents9=" + contents9
				+ ", contents10=" + contents10 + "]";
	}



	public String getMacAddr() {
		return macAddr;
	}



	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
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



	public String toJsonStr() throws JsonGenerationException, JsonMappingException, IOException {
        
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(this);       
    }
}
