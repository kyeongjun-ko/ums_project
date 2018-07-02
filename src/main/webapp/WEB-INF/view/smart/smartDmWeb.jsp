<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<title>${smartViewVO.subject}</title>
<script>
$(document).ready(function() {
	
	RequestConfirm1();
	
});
	var smartContId = '';
	var smartResCd = '';
	
	function SucAlert(){
		alert('상담신청에 성공하였습니다.');	
	}
	function FailAlert(){
		alert('상담신청에 실패하였습니다.');		
	}
	//상담요청 전송  
	function RequestResponse(){
		document.getElementById("request").src = "/smartDmWeb/corfirm?smartResCd=RES&MSGTYPE=SMS&smartContId="+$("#smartContId").val();
		SucAlert();
	}
	function RequestConfirm1(){
		document.getElementById("request").src = "/smartDmWeb/corfirm?smartResCd=CON&MSGTYPE=SMS&smartContId="+$("#smartContId").val();
	}
	
</script>
</head>
<form:form method="post" modelAttribute=smartViewVO id="msgBoxForm" >
<input type="hidden" name="MSGTYPE" id="MSGTYPE" value="SMS" />
<input type="hidden" name="smartResCd" id="smartResCd" value="CON" />
<input type="hidden" name="smartContId" id="smartContId" value="${smartViewVO.smartContId}" />
<div id="contents" style="line-height: 0px;" >
<c:if test="${! empty smartViewVO.fileLink1}">
	<a href="${smartViewVO.fileLink1}"><img id = "img1_1" onclick = "RequestResponse()" width = "100%" src="${smartViewVO.fileNm1}" /></a>
</c:if>
<c:if test="${ empty smartViewVO.fileLink1}">
	<img id = "img1_1" onclick = "RequestResponse()" width = "100%" src="${smartViewVO.fileNm1}" />
</c:if>

<c:if test="${! empty smartViewVO.fileLink2}">
	<a href="${smartViewVO.fileLink2}"><img id = "img1_2" onclick = "RequestResponse()" width = "100%" src="${smartViewVO.fileNm2}" /></a>
</c:if>
<c:if test="${ empty smartViewVO.fileLink2}">
	<img id = "img1_2" onclick = "RequestResponse()" width = "100%" src="${smartViewVO.fileNm2}" />	
</c:if>

<c:if test="${! empty smartViewVO.fileLink3}">
	<a href="${smartViewVO.fileLink3}"><img id = "img1_3" onclick = "RequestResponse()" width = "100%" src="${smartViewVO.fileNm3}" /></a>
</c:if>
<c:if test="${ empty smartViewVO.fileLink3}">
	<img id = "img1_3" onclick = "RequestResponse()" width = "100%" src="${smartViewVO.fileNm3}" />	
</c:if>

<c:if test="${! empty smartViewVO.fileLink4}">
	<a href="${smartViewVO.fileLink4}"><img id = "img1_4" onclick = "RequestResponse()" width = "100%" src="${smartViewVO.fileNm4}" /></a>
</c:if>
<c:if test="${ empty smartViewVO.fileLink4}">
	<img id = "img1_4" onclick = "RequestResponse()" width = "100%" src="${smartViewVO.fileNm4}" />	
</c:if>

<c:if test="${! empty smartViewVO.fileLink5}">
	<a href="${smartViewVO.fileLink5}"><img id = "img1_5" onclick = "RequestResponse()" width = "100%" src="${smartViewVO.fileNm5}" /></a>
</c:if>
<c:if test="${ empty smartViewVO.fileLink5}">
	<img id = "img1_5" onclick = "RequestResponse()" width = "100%" src="${smartViewVO.fileNm5}" />	
</c:if>
</div>
	<!-- <button onclick="SucAlert();"  class="btn btn-primary btn-lg">상담신청</button>-->
<div  align="right"> 
	<button onclick="FailAlert();" style="color:red; width: 200px; height:70px; font-size:40px;">상담거부</button>   
</div>   
</form:form>
<iframe id="request" name="request" style="display: none;"></iframe>



