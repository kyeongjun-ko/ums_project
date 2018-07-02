package com.bccns.umsserviceweb.push.vo.PUSH0100;

import com.bccns.umsserviceweb.push.vo.RexHeaderVO;

import java.io.IOException;
import java.io.Serializable;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class ReqPUSH0100VO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RexHeaderVO header;
	private ReqBodyPUSH0100VO body;

	
	public RexHeaderVO getHeader() {
		return header;
	}


	public void setHeader(RexHeaderVO header) {
		this.header = header;
	}


	public ReqBodyPUSH0100VO getBody() {
		return body;
	}


	public void setBody(ReqBodyPUSH0100VO body) {
		this.body = body;
	}


	@Override
	public String toString() {
		return "ReqPUSH0100VO [header=" + header + ", body=" + body + "]";
	}


	public String toJsonStr() throws JsonGenerationException, JsonMappingException, IOException {
        
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(this);       
    }
	
}
