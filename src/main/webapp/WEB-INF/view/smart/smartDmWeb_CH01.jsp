<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<title>${smartViewVO.subject}</title>
<script>
$(document).ready(function() {
	
	RequestConfirm1();
	$('td').ccs('text-align','center');	
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
			
		
		<c:if test="${! empty smartViewVO.fileNm1}">
			<div  style="width: 1199px; height: 609px; background: url(${smartViewVO.fileNm1});">
				<div style="position:relative; left:80px; top:90px; width: 200px;" align="center">
					<font size="5px">${smartViewVO.content2[0]}</font>
				</div>
				<div style="position:relative; left:280px; top:165px; width: 170px;" align="center">
					<font size="5px">${smartViewVO.content2[1]}</font>
				</div>
				<div style="position:relative; left:480px; top:210px; width:330px;">
					<table width="100%">
						<tr>
							<td width="13%" style="text-align: center;">
								<font size="5px">${smartViewVO.content2[2]}</font>
							</td>
							<td width="33%" style="text-align: center;"></td>
							<td width="19%" style="text-align: center;">
								<font size="5px">${smartViewVO.content2[3]}</font>
							</td>
							<td width="5%"  style="text-align: center;" ></td>
							<td width="10%" style="text-align: center;">
								<font size="5px">${smartViewVO.content2[4]}</font>
							</td>
						</tr>
					</table>
				</div>	
			</div>
		</c:if>

	
		<c:if test="${! empty smartViewVO.fileNm2}">
			<div  style="width: 1181px; height: 604px; background: url(${smartViewVO.fileNm2});">
				<table style="position: relative; left: 30px;top: 220px; width: 700px;">
					<tr height="60px">
						<td width="265px" style="text-align: center;">
						<font size="5px">${smartViewVO.content3[0]}</font>
						</td>
						<td align="center" style="text-align: center;">
						<font size="5px">${smartViewVO.content3[1]}</font>
						</td>
					</tr>
				</table>
				
				<table style="position: relative; left: 200px;top: 355px; width: 940px;">
					<tr height="40px">
						<td width="18%" style="text-align: center;"><font size="5px">${smartViewVO.content3[2]}</font></td>
						<td width="24%" style="text-align: center;"><font size="5px">${smartViewVO.content3[3]}</font></td>
						<td width="18%" style="text-align: center;"><font size="5px">${smartViewVO.content3[4]}</font></td>
						<td width="18%" style="text-align: center;"><font size="5px">${smartViewVO.content3[5]}</font></td>
						<td width="22%" style="text-align: center;"><font size="5px">${smartViewVO.content3[6]}</font></td>
					</tr>
				</table>
				
				<table style="position: relative; left: 200px;top: 365px; width: 940px">
					<tr height="40px">
						<td width="18%" style="text-align: center;"><font size="5px">${smartViewVO.content3[7]}</font></td>
						<td width="24%" style="text-align: center;"><font size="5px">${smartViewVO.content3[8]}</font></td>
						<td width="18%" style="text-align: center;"><font size="5px">${smartViewVO.content3[9]}</font></td>
						<td width="18%" style="text-align: center;"><font size="5px">${smartViewVO.content3[10]}</font></td>
						<td width="22%" style="text-align: center;"><font size="5px">${smartViewVO.content3[11]}</font></td>
					</tr>
				</table>
			</div>
		</c:if>
	</div> 
<%-- <div id="contents" style="line-height: 0px;" >
<c:if test="${! empty smartViewVO.fileLink1}">
	<a href="${smartViewVO.fileLink1}"><img id = "img1_1" onclick = "RequestResponse()" width = "100%" src="../images/149cc7a2e61.JPG" /></a>
</c:if>
<c:if test="${ empty smartViewVO.fileLink1}">
	<img id = "img1_1" onclick = "RequestResponse()" width = "100%" src="../images/149cc7a2e61.JPG" />
	<div style="position:relative; left:80px; top:90px; width: 200px;" align="center">
					<font size="5px">asdsadasdsadsadsadsds</font>
				</div>
</c:if>

<c:if test="${! empty smartViewVO.fileLink2}">
	<a href="${smartViewVO.fileLink2}"><img id = "img1_2" onclick = "RequestResponse()" width = "100%" src="../images/149cc7a2e62.JPG" /></a>
</c:if>
<c:if test="${ empty smartViewVO.fileLink2}">
	<img id = "img1_2" onclick = "RequestResponse()" width = "100%" src="../images/149cc7a2e62.JPG" />	
</c:if>

<c:if test="${! empty smartViewVO.fileLink3}">
	<a href="${smartViewVO.fileLink3}"><img id = "img1_3" onclick = "RequestResponse()" width = "100%" src="../images/149cc7a2e63.JPG" /></a>
</c:if>
<c:if test="${ empty smartViewVO.fileLink3}">
	<img id = "img1_3" onclick = "RequestResponse()" width = "100%" src="../images/149cc7a2e63.JPG" />	
</c:if> 
</div> --%>
	<!-- <button onclick="SucAlert();"  class="btn btn-primary btn-lg">상담신청</button>-->  
	<div  align="right"> 
		<button onclick="FailAlert();" style="color:red; width: 200px; height:70px; font-size:40px;">상담거부</button>   
	</div>
</form:form>
<iframe id="request" name="request" style="display: none;"></iframe>



