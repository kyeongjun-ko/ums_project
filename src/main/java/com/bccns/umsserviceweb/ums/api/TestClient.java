package com.bccns.umsserviceweb.ums.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bccns.umsserviceweb.common.util.JSONParser;
import com.bccns.umsserviceweb.ums.vo.URL;
import com.bccns.umsserviceweb.ums.vo.PDS.PDSResponseVO;


public class TestClient {
	private final URL server;
	private static final Logger logger = LoggerFactory.getLogger(TestClient.class);
	private static final int TIME_OUT = 60000;
	
	public TestClient(HttpServletRequest request) {
		server = new URL("http", "10.10.1.38", 9090, request.getContextPath() + "/pds.bc");
	}
	
	public PDSResponseVO request(String req) throws Exception {
		
		logger.debug("start !!!!!@###");
		org.apache.http.client.HttpClient httpclient = new DefaultHttpClient();
		
		try {
			if (server.getScheme().startsWith("https")) {
				try {
					SSLSocketFactory socketFactory = new SSLSocketFactory(new TrustSelfSignedStrategy(), new AllowAllHostnameVerifier());
					
					Scheme scheme = new Scheme(server.getScheme(), server.getPort(), socketFactory);
					httpclient.getConnectionManager().getSchemeRegistry().register(scheme);
				} catch (Exception e) {
					throw e;
				}
			}
			
			httpclient.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT,TIME_OUT);
			httpclient.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT,TIME_OUT);
			
			HttpHost targetHost = new HttpHost(server.getHost(), server.getPort(), server.getScheme());
			
			HttpPost httpPost = new HttpPost(server.getUri());
			
			HttpEntity entity = getEntity(req);
			
			httpPost.setEntity(entity);
			
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = httpclient.execute(targetHost, httpPost, responseHandler);
			
			return JSONParser.toObject(responseBody, PDSResponseVO.class);
					
			
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}
	
	protected static HttpEntity getEntity(String req) throws Exception {
		
//		if (StringUtils.isEmpty(req)) {
//			StringBuffer sb = new StringBuffer();
//			sb.append("Parameter Error");
//			
//			throw new Exception(sb.toString());
//		}
//		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("format", "json"));
		nameValuePairs.add(new BasicNameValuePair("service", "PDS01"));
		nameValuePairs.add(new BasicNameValuePair("req", req));
		
		return new UrlEncodedFormEntity(nameValuePairs, "EUC-KR");
	}
}
