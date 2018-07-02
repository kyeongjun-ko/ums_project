<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ page import="java.io.PrintWriter" %>				 
<%
	response.setStatus(200);
	exception.printStackTrace();	

//	if(exception instanceof FileUploaderException){
//		FileUploaderException e = (FileUploaderException)exception;
//		if (e.getCode() == FileUploaderException.TOO_LARGE_SIZE) {
//			Utils.sendMessage(out, e.getMessage());
//		}
//		else {
//			Utils.sendMessage(out, e.getMessage());
//		}
//	}
	/*
	else if (exception instanceof TransactionError) {
		TransactionError e = (TransactionError)exception;
		System.out.println(exception.getMessage());
		//Utils.sendMessage(out, e.getMessage());
	}
	else if (exception instanceof SystemError) {
		SystemError e = (SystemError)exception;
		System.out.println(exception.getMessage());
		Utils.sendMessage(out, e.getMessage());
	}
	*/
//	else { 
		System.out.println(exception.getMessage());
//	}
%>
<!DOCTYPE html>  
<html>
<style type="text/css">
*	{ padding:0; margin:0; }
img	{ border:none; vertical-align:top; }
</style>
</head>
<body>
	<div style="position:absolute; left:50%; top:40%; margin:-155px 0 0 -250px">
		<a href="javascript:history.back()" alt="이전페이지로 이동">
			<img src="/images/img_error.gif">
		</a>
	</div>
	<div style="display:none">
<%
exception.printStackTrace(new PrintWriter(out));
%>
	</div>
</body>
</html>
<%
	//throw new Exception(exception);
%>