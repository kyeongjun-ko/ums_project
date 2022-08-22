import static org.junit.Assert.*;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpPost;import org.apache.http.entity.mime.MultipartEntity;

import org.apache.http.entity.mime.content.FileBody;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bccns.umsserviceweb.common.util.XSSDomainFilter;
import com.bccns.umsserviceweb.push.vo.ResBodyResultVO;
import com.bccns.umsserviceweb.push.vo.RexHeaderVO;
import com.bccns.umsserviceweb.push.vo.PUSH0200.ReqBodyPUSH0200VO;
import com.bccns.umsserviceweb.push.vo.PUSH0200.ReqPUSH0200VO;
import com.bccns.umsserviceweb.push.vo.PUSH0200.ResBodyDataPUSH0210VO;
import com.bccns.umsserviceweb.push.vo.PUSH0200.ResBodyPUSH0210VO;
import com.bccns.umsserviceweb.push.vo.PUSH0200.ResPUSH0210VO;


public class PushSendApiTest {
	private static final Logger logger = LoggerFactory.getLogger(PushSendApiTest.class);

	private String serverUrl ;
	private String apiName;

	@Test
	public void PUSH0200ClientTest() throws Exception {
        
        HttpClient httpClient = null;
        PostMethod method = null;
        InputStream responseStream = null;
        //common
        RexHeaderVO header = new RexHeaderVO();
        RexHeaderVO reqHeader = new RexHeaderVO();
        
        //req
        ReqPUSH0200VO reqVO = new ReqPUSH0200VO();
        ReqBodyPUSH0200VO reqBody = new ReqBodyPUSH0200VO();
        
        //res
        ResPUSH0210VO resVO = new ResPUSH0210VO();
        ResBodyResultVO resBodyResult = new ResBodyResultVO();
        ResBodyDataPUSH0210VO resBodyData = new ResBodyDataPUSH0210VO();
        ResBodyPUSH0210VO resBody = new ResBodyPUSH0210VO();
         
        File file = new File("C:/image/148cb6d4515.jpg");
        try {
            HttpConnectionManager connMgr = new SimpleHttpConnectionManager();
            HttpConnectionManagerParams httpConnMgrParams = new HttpConnectionManagerParams();
            httpConnMgrParams.setConnectionTimeout(30000);
            httpConnMgrParams.setSoTimeout(30000);
            connMgr.setParams(httpConnMgrParams);
            

            serverUrl = "http://127.0.0.1:8080";
            //serverUrl = "http://10.10.1.38:8082";
            apiName ="/api/req_PushMsgSend.api";
           
            //HttpPost post = new HttpPost(serverUrl+apiName);

            
            FileBody bin = new FileBody(file);
            MultipartEntity reqEntity = new MultipartEntity(null);
            
            reqEntity.addPart("myFile", bin);
            
           // post.setEntity(reqEntity);
            
            httpClient = new HttpClient(connMgr);
            method = new PostMethod(this.serverUrl + this.apiName);
//            method..Entity = reqEntity;
            		
            reqHeader.setSvcId("PUSH0200");
            reqBody.setPhoneNo("01049402371");
            reqBody.setApiKey("AIzaSyDz8Eg3LTliZqroIhewxkAlwKeEy4tpx1w");
            reqBody.setMessage("test send msg");
            reqBody.setTitle("test");
            reqBody.setSender("01049402371");
            
            reqVO.setHeader(reqHeader);
            //reqVO.setReqBodyPUSH0200VO(reqBody);
            reqVO.setBody(new XSSDomainFilter<ReqBodyPUSH0200VO>().getFilterdObject(reqBody));

            StringRequestEntity request = new StringRequestEntity(reqVO.toJsonStr(), "application/json", "UTF-8");
            method.setRequestEntity(request);
            
            method.setRequestEntity(reqEntity);
            
            System.out.println("reqVO : "+reqVO.toString());
            System.out.println("req toJsonStr : "+reqVO.toJsonStr());
           
            int statusCode = httpClient.executeMethod(method);
            if(statusCode != HttpStatus.SC_OK) {
            	System.out.println("statusCode : "+statusCode);
                throw new Exception("tacking id api connection fail");
            }
            
            //byte[] resJsonArray = method.getResponseBody();
            responseStream = method.getResponseBodyAsStream();
            
            ObjectMapper om = new ObjectMapper();
            om.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            resVO = om.readValue(IOUtils.toByteArray(responseStream), ResPUSH0210VO.class);
            System.out.println("resVO : "+resVO.toJsonStr());
            
        } catch ( Exception e) {
            throw e;
        } finally {
            if(method != null) {
                method.releaseConnection();
            }
            IOUtils.closeQuietly(responseStream);
        }       
    }	
}
