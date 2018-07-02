<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    

<body class="body">
        <table id="alert" style="position:absolute; width:300px; height:150px; min-height:150px; left:50%; margin-left:-150px; margin-top:150px;  background:#b2c6ee;visibility:hidden;">
        <tr><td>
		<table style="width: 100%; height: 80px; text-align: center; background: white;">
		<tr>
		<td id="alert_msg" style="font-weight: bold; text-align: center; color:#676767;">
		</td>
		</tr>
		</table>
		<table style="width: 100%; height: 40px; vertical-align: bottom; background: white;">
		<tr align="center">
		<td><img src="/img/alertimg/bt_ok.gif" onclick="exit('alert')"/>
		</td>
		</tr>
		</table></td></tr>
		</table>
</body>


<!DOCTYPE html>
<html>
<head>
<meta name="viewport"
 content="width=device-width, initial-scale=1, maximum-scale=4, user-scalable=yes">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="http://code.jquery.com/mobile/1.2.0/jquery.mobile-1.2.0.min.css" />
<script src="http://code.jquery.com/jquery-1.8.2.min.js"></script>
<script src="http://code.jquery.com/mobile/1.2.0/jquery.mobile-1.2.0.min.js"></script>
<script src="js/alert.js"></script>

<form:form method="post" modelAttribute=smartViewVO id="msgBoxForm" >
<input type="hidden" name="MSGTYPE" id="MSGTYPE" value="SFX" />
<title>${smartViewVO.subject}</title>
<style>
body{line-height:0px;}
#contents{line-height:0px;}
</style>
<script>
	var seq = '';

	function SucAlert(){
		JAlert('alert', '상담신청에 성공하였습니다.');	
	}
	function FailAlert(){
		JAlert('alert', '상담신청에 실패하였습니다.');		
	}
	//상담요청 전송  
	function RequestConsult(){
		document.getElementById("request").src = "RequestConsult.do?seq="+seq;
	}
</script>
</head>
<!-- 수정완료 /images/smartdm/DM_fire1_20130225_006.jpg -->

<div id="contents" style="line-height: 0px;">
	<a href="${smartViewVO.fileLink1}"><img id = "img1_1" onclick = "RequestConsult()" width = "100%" src="${smartViewVO.fileNm1}" />
	<a href="${smartViewVO.fileLink2}"><img id = "img1_2" onclick = "RequestConsult()" width = "100%" src="${smartViewVO.fileNm2}" /></a>
	<a href="${smartViewVO.fileLink3}"><img id = "img1_3" onclick = "RequestConsult()" width = "100%" src="${smartViewVO.fileNm3}" />
	
<iframe id="request" name="request" style="display: none;"></iframe>
</div>

</form:form>
</html>