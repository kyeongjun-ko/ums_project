<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../common/common.jsp"%>
<!DOCTYPE html>
<html xmlns="https://www.w3.org/1999/xhtml" xml:lang="en" lang="ko-KR">
<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<link rel="stylesheet" href="/css/bootstrap.min.css"/>
<link rel="stylesheet" href="/fa/css/font-awesome.min.css"/>
<link rel="stylesheet" href="/css/style.css" media="all" />
<link rel="stylesheet" href="/css/custom.css" media="all" />
<link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.min.js"></script>
	<!--[if lt IE 9]>
		<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script> 
	<script type="text/javascript" src="/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/date.format.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/json2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/validator.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/autoNumeric_1.6.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/shadowbox.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/common2.js"></script>  
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/ums.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/addressbook.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/period.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/message.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/util.js"></script>
<c:if test="${! empty msg}">
	<script>
		alert("${msg}");
		self.close();
	</script>
</c:if> 
</head>
<body>

	<decorator:body />

</body>
</html>