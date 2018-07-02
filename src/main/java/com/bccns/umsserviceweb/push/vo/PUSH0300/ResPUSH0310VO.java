package com.bccns.umsserviceweb.push.vo.PUSH0300;

import java.io.IOException;
import java.io.Serializable;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.bccns.umsserviceweb.push.vo.RexHeaderVO;

public class ResPUSH0310VO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RexHeaderVO header;
	private ResBodyPUSH0310VO body;
	
	
	
	public RexHeaderVO getHeader() {
		return header;
	}



	public void setHeader(RexHeaderVO header) {
		this.header = header;
	}



	public ResBodyPUSH0310VO getBody() {
		return body;
	}



	public void setBody(ResBodyPUSH0310VO body) {
		this.body = body;
	}



	@Override
	public String toString() {
		return "ResPUSH0110VO [header=" + header + ", body=" + body + "]";
	}



	public String toJsonStr() throws JsonGenerationException, JsonMappingException, IOException {
        
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(this);       
    }
}
