package com.bccns.umsserviceweb.push.util;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bccns.umsserviceweb.push.controller.api.PulbicPushAPIController;
import com.bccns.umsserviceweb.push.vo.PmsSendVO;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class PushUtil {
	
	private static final Logger log = LoggerFactory.getLogger(PushUtil.class);

	public static String pushSend(PmsSendVO pmsSendVO, String regid) {
		
		try {
			Sender send = new Sender(pmsSendVO.getApiKey());
			JSONObject msg = new JSONObject();
			
			log.debug("pmsSendVO"+ pmsSendVO.toString());

			Message.Builder builder = new  Message.Builder();
			
			builder.addData("num", pmsSendVO.getNum());
			builder.addData("send_dt", pmsSendVO.getSendDt());
			
			if(pmsSendVO.getSender() !=  null)				
				builder.addData("sender", pmsSendVO.getSender());
			
			builder.addData("title", pmsSendVO.getTitle());
			builder.addData("message", pmsSendVO.getMessage());
			if(pmsSendVO.getImageYn() !=  null)
				builder.addData("imageYn", pmsSendVO.getImageYn());
			
			if(pmsSendVO.getImageUrl() !=  null) 
				builder.addData("imageUrl", pmsSendVO.getImageUrl());
			
			if(pmsSendVO.getDocType() !=  null)
				builder.addData("docType", pmsSendVO.getDocType());
			
			Message push_msg = builder.build(); 
			Result result = send.send(push_msg, regid, 3);
			
			log.debug("result :"+ result.toString());
			
			return result.toString();
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return "SERVER_ERROR";
		}
		
		
	}
}
