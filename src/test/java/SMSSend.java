import java.io.*;
import java.net.*;

class SMSSend 
{
	String host;
	int port;
	
	Socket s;
	OutputStream out;
	InputStream in;
	
	SMSSend()
	{	  	
	}

	public String SendSMS(String ip, int port, String CompanyCode, String Member_ID, String Dest_Phone,
		String CallBack, String Message) throws IOException
	{
		s = new Socket(ip,port);
		String result= "";
		String Source_Phone = CallBack;

		byte[] size_test = Message.getBytes();
		int count = 0;
		for(int i = 0; i < size_test.length; i++) {
			String temp_string = size_test[i] + "";
			count++;
		}

		if(CompanyCode.length()>20 || Member_ID.length()>12 || Dest_Phone.length()>12 ||
			CallBack.length()>12 || count>100)
		{
			result = "99";
			return result;
		}

		try {
			//header
			byte[] head1 = new byte[2];
			head1[0] = 77;
			byte[] head2 = new byte[5];
			head2[0] = 49;
			head2[1] = 56;
			head2[2] = 51;

			//messages_temp
			byte[] b_CompanyCode_temp= new byte[20];
			byte[] b_Member_ID_temp= new byte[20];
			byte[] b_Dest_Phone_temp= new byte[12];
			byte[] b_Source_Phone_temp= new byte[12];
			byte[] b_CallBack_temp= new byte[12];
			byte[] b_Message_temp= new byte[100];

			CompanyCode = modifyString(CompanyCode,20);
			Member_ID = modifyString(Member_ID,20);
			Dest_Phone = modifyString(Dest_Phone,12);
			Source_Phone = modifyString(Source_Phone,12);
			CallBack = modifyString(CallBack,12);
			Message = modifyString(Message,100);
			
			out = s.getOutputStream();
			in = s.getInputStream();

			b_CompanyCode_temp = CompanyCode.getBytes();
			b_Member_ID_temp = Member_ID.getBytes();
			b_Dest_Phone_temp = Dest_Phone.getBytes();
			b_Source_Phone_temp = Source_Phone.getBytes();
			b_CallBack_temp = CallBack.getBytes();
			b_Message_temp = Message.getBytes();

			//message
			byte[] b_CompanyCode= new byte[20];
			byte[] b_Member_ID= new byte[20];
			byte[] b_Dest_Phone= new byte[12];
			byte[] b_Source_Phone= new byte[12];
			byte[] b_CallBack= new byte[12];
			byte[] b_Message= new byte[100];

			System.arraycopy(b_CompanyCode_temp,0,b_CompanyCode,0,20);
			System.arraycopy(b_Member_ID_temp,0,b_Member_ID,0,20);
			System.arraycopy(b_Dest_Phone_temp,0,b_Dest_Phone,0,12);
			System.arraycopy(b_Source_Phone_temp,0,b_Source_Phone,0,12);
			System.arraycopy(b_CallBack_temp,0,b_CallBack,0,12);
			System.arraycopy(b_Message_temp,0,b_Message,0,100);

			int sizea = b_Message_temp.length;

			out.write(head1);
			out.write(head2);
			out.write(b_CompanyCode);
			out.write(b_Member_ID);
			out.write(b_Dest_Phone);
			out.write(b_Source_Phone);
			out.write(b_CallBack);
			out.write(b_Message);
			out.flush();

			byte[] return_val = new byte[2];
			return_val[0] = (byte)in.read();
			return_val[1] = (byte)in.read();
			
			result = new String(return_val,0,2);
		
	    } catch (IOException ex) {
      out.close();
			result = "95";
			return result;
    }
		return result;
	}

	public String modifyString(String str, int size)
	{
		if(str.length()>size)
		{
			str = str.substring(0,size);
		}
		
		for(int i=str.length(); i<size; i++)
		{
			str += "\0";
		}
		return str;
	}
}
