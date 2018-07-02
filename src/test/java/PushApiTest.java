import static org.junit.Assert.*;

import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bccns.umsserviceweb.common.util.XSSDomainFilter;
import com.bccns.umsserviceweb.push.vo.ResBodyResultVO;
import com.bccns.umsserviceweb.push.vo.RexHeaderVO;
import com.bccns.umsserviceweb.push.vo.PUSH0100.ReqBodyPUSH0100VO;
import com.bccns.umsserviceweb.push.vo.PUSH0100.ReqPUSH0100VO;
import com.bccns.umsserviceweb.push.vo.PUSH0100.ResBodyDataPUSH0110VO;
import com.bccns.umsserviceweb.push.vo.PUSH0100.ResBodyPUSH0110VO;
import com.bccns.umsserviceweb.push.vo.PUSH0100.ResPUSH0110VO;


public class PushApiTest {
	private static final Logger logger = LoggerFactory.getLogger(PushApiTest.class);

	private String serverUrl ;
	private String apiName;

	@Test
	public void Push0100ClientTest() throws Exception {
        
        HttpClient httpClient = null;
        PostMethod method = null;
        InputStream responseStream = null;
        //common
        RexHeaderVO header = new RexHeaderVO();
        RexHeaderVO reqHeader = new RexHeaderVO();
        
        //req
        ReqPUSH0100VO reqVO = new ReqPUSH0100VO();
        ReqBodyPUSH0100VO reqBody = new ReqBodyPUSH0100VO();
        
        //res
        ResPUSH0110VO resVO = new ResPUSH0110VO();
        ResBodyResultVO resBodyResult = new ResBodyResultVO();
        ResBodyDataPUSH0110VO resBodyData = new ResBodyDataPUSH0110VO();
        ResBodyPUSH0110VO resBody = new ResBodyPUSH0110VO();
         
        try {
            HttpConnectionManager connMgr = new SimpleHttpConnectionManager();
            HttpConnectionManagerParams httpConnMgrParams = new HttpConnectionManagerParams();
            httpConnMgrParams.setConnectionTimeout(30000);
            httpConnMgrParams.setSoTimeout(30000);
            connMgr.setParams(httpConnMgrParams);
            
            serverUrl = "http://127.0.0.1:8080";
            //serverUrl = "http://10.10.1.38:8082";
            apiName ="/api/set_PushRegID_regist.api";
            
            httpClient = new HttpClient(connMgr);
            method = new PostMethod(this.serverUrl + this.apiName);
            
            
            reqHeader.setSvcId("PUSH0100");
            reqBody.setPhoneNo("01067323629");
            reqBody.setRegId("parksanghyun");
            
            reqVO.setHeader(reqHeader);
            //reqVO.setReqBodyPUSH0100VO(reqBody);
            reqVO.setBody(new XSSDomainFilter<ReqBodyPUSH0100VO>().getFilterdObject(reqBody));

            StringRequestEntity request = new StringRequestEntity(reqVO.toJsonStr(), "application/json", "UTF-8");
            method.setRequestEntity(request);
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
            resVO = om.readValue(IOUtils.toByteArray(responseStream), ResPUSH0110VO.class);
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
