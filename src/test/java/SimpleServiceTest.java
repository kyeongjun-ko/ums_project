

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.jasper.tagplugins.jstl.core.Out;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;






import com.bccns.umsserviceweb.common.util.CryptUtils;
import com.bccns.umsserviceweb.common.util.StringUtil;
import com.bccns.umsserviceweb.ums.vo.PDS.PDSResponseVO;
import com.bccns.umsserviceweb.ums.api.TestClient;
import com.bccns.umsserviceweb.common.util.*;

/**
 * EmailMngService 단위 테스트.
 * 
 * @author  
 */
public class SimpleServiceTest {

	@Before
	public void setUp() throws Exception {
	}
	@Test
	public void StringTest() throws Exception {
		
		StringUtil stringUtil = new StringUtil();
		String str = "park>01067323629?";
		String str1 = "";
		str1 = stringUtil.replaceString(str);
		//System.out.println(str1);
	}
	@Test
	public void StringTest1() throws Exception {
		HttpServletRequest request = null;
		String strttmp = "0010-4940-2371";
		String reqStr = "{'header':{'clientId':'bluechip','clientPw':'Jesusdfm!2'},'body':{'requestId':'11111','telNo':'"+strttmp+"'}}";
		//PDSResponseVO vo = new TestClient(request).request(reqStr);
		//System.out.println(str1);
		
		int msgLen = StringUtil.lengthB(reqStr);
    	System.out.println("msgLen"+msgLen);
    	List<String> msgArr = new ArrayList<String>();
    	int sPt = 0;
    	int mCnt = 0;
    	System.out.println("reqStr"+reqStr);
    	while (msgLen >= 80) {
    		msgArr.add(StringUtil.substrB(reqStr, sPt, 80 + sPt));
    		System.out.println("reqStr = "+StringUtil.substrB(reqStr, sPt, 80 + sPt));
    		msgLen = msgLen - 80;
    		sPt = sPt + 80;
    		mCnt ++;
        }
    	msgArr.add(StringUtil.substrB(reqStr, sPt, 80 + sPt));
    	
    	for(int i = 0; i< msgArr.size(); i++){
    		System.out.println("msgArr = "+msgArr.get(i));
    	}
    	
    	//String str1 = strttmp.replace("|", "X");
    	
    	String str1 = StringUtil.getHanEngNumber(strttmp);
    	//str1.substring(0,16);
    	
    	
    	if(StringUtil.lengthB(str1) > 16) str1 = StringUtil.substrB(str1, 1, 16);
    	
    	System.out.println(str1);

	}
	
	@Test
	public void StringTest2() throws Exception {
		String ip = "211.216.53.15";
		//현재 “211.174.83.153” 입니다.
		int port = 37020;
		//현재 “37020” 입니다.
		String CompanyCode ="CP7923002KRA";
		//마사회는 "CP7923002KRA" 입니다.
		String Member_ID = "JAVA";
		//마사회 자바 모듈은 "JAVA" 입니다.
		String Dest_Phone = "01067323629"; 
		//: 받는 사람 전화번호를 넣습니다.
		String CallBack = "01067323629";
		//: 보내는 사람 전화번호를 넣습니다.
		String Message = "test"; 

		SMSSend sms = new SMSSend();
		//String result = sms.SendSMS(ip,port,CompanyCode,Member_ID,Dest_Phone,CallBack,Message);
		
		//System.out.println("reseult = "+result);
	}

	@Test
	public void StringTest3() throws Exception {
		 String secretKey   = "12345678901234567890123456789012"; //32bit

		 /**
		  * byte[] bDigest = getHash(ITERATION_NUMBER,src);
          * String sDigest = encryptBase64String(bDigest);
		  */
		//String encrypass = CryptUtils.encryptSHA256Hash("bleuchip!23");
		 
		String encrypass = CryptUtils.AES_Encode("bleuchip!23");
				//encryptAES256String("bleuchip!23",secretKey);
		System.out.println(encrypass);
		//byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));

		String decrypass = CryptUtils.AES_Decode(encrypass);
		System.out.println(decrypass.toString());
	}
	
	@Test
	public void StringTest4() throws Exception {
		 String secretKey   = "12345678901234567890123456789012"; //32bit

		 /**
		  * byte[] bDigest = getHash(ITERATION_NUMBER,src);
          * String sDigest = encryptBase64String(bDigest);
		  */
		//String encrypass = CryptUtils.encryptSHA256Hash("bleuchip!23");

		String encrypass = CryptUtils.AES_Encode("bleuchip!23");
				//encryptAES256String("bleuchip!23",secretKey);
		System.out.println(encrypass);
		//byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
		encrypass = "iEMoR/X0OCwNvAMSMUb45Q==";
		String decrypass = CryptUtils.AES_Decode(encrypass);
		System.out.println(decrypass.toString());
	}
	
	
	@Test
	public void StringTest5() throws Exception {
		
		StringUtil stringUtil = new StringUtil();
		String str = "https://airbusan2.sendall.co.kr:443";
		boolean str1 = str.matches(".*airbusan2.*");
		System.out.println(str1);

		//str1 = stringUtil.replaceString(str);
		//System.out.println(str1);
	}
	
}