<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>    
 <script type="text/javascript">
 $(document).ready(function(){
	  //
    });
    
    function linkPage(pageNo) {
        $("#currentPageNo").val(pageNo);
        
        $("#transRsltDetForm").attr({
            action : "/trs/transRsltDetPopUp",
            method : "get",
        }).submit();
    }
    
    
    function excelDownload() {
    	//var viewFileName = $("#viewFileName").val();
		//if(viewFileName == '') {
		//	alert("Please enter CSV file name.");
		//	return;
		//}
		//
		//$("#viewFileName", window.opener.document).val(viewFileName);
		$("#downForm").attr({
            action : "/trs/rsltDetBottomListDownload",
            method : "get",
        }).submit();
		
    }
 
 </script>
        <form:form method="post" modelAttribute="searchRsltDetVO" id="transRsltDetForm">
        <form:hidden path="currentPageNo"/>
        <input type="hidden" id="userId" name="userId" 	value='${searchRsltDetVO.userId}'/>
        <input type="hidden" id="jobId"	 name="jobId" 	value='${searchRsltDetVO.jobId}'/>
        <input type="hidden" id="msgId"	 name="msgId" 	value='${searchRsltDetVO.msgId}'/>
        <input type="hidden" id="msgType" name="msgType" value='${searchRsltDetVO.msgType}'/>
        
	<div class="panel-default popup notice">
		<div class="col-md-12 tits">
			<h1>전송결과</h1>
			<ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-home"></i></a></li>
				<li><a href="#">전송관리</a></li>
				<li class="active">전송결과</li>
			</ol>
			<div class="hbg">
				<i class="fa fa-envelope odd"></i>
				<i class="fa fa-globe "></i>
			</div>
		</div>
		<div class="col-md-12 tcon">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">전송 정보</h3>
				</div>
				<div class="panel-body scon">
					<div class="panel-body col-md-4">
						<div class="mob2">
							<img src="/images/mob2.png" alt="모바일 배경" class="responsive-img">
						</div>
						<div id="mobtxt" class="mobtxt" style="overflow:auto; height:150px;" >
							<textarea style="border: 0; background-color: transparent; height:120px; width: 100%;">${rsltDetTop.contents}</textarea>
						</div>
					</div>							
					<div class="panel-body col-md-8 dlist">
							<div class="dl-horizontal">
							<fmt:parseDate var="sendDate" value="${rsltDetTop.sendDate    }" pattern="yyyyMMddHHmmss" />
							<dl>
							<dt>제목</dt><dd><c:if test="${rsltDetTop.subject != ''}">${rsltDetTop.subject}</c:if>
											 <c:if test="${rsltDetTop.subject == ''}">제목없음</c:if></dd>
							</dl>
							<dl>
							<dt>전송일시</dt><dd><fmt:formatDate value="${sendDate}" pattern="yyyy-MM-dd  HH:mm:ss" /></dd>
							</dl>
							<dl>
							<dt>메시지 유형</dt><dd>${rsltDetTop.msgType    }</dd>
							</dl>
							<dl>
							<dt>수신처 수</dt><dd>${rsltDetTop.destCount    }명</dd>
							</dl>
							<dl>
							<dt>회신번호</dt><dd>${rsltDetTop.callback    }</dd>
							</dl>
							</div>
					</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">전송결과</h3>
				</div>
				<div class="panel panel-body scon">
					<div class="panel-body col-md-4 sgraph">
						<dl>
							<dt>성공</dt>
							<dd>
								<div class="progress">
								  <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="${rsltDetMiddle.perSucc}" aria-valuemin="0" aria-valuemax="100" style="width: ${rsltDetMiddle.perSucc}%;">
								  	<span>${rsltDetMiddle.perSucc}%</span>
								  </div>
								</div>
							</dd>									
							<dt>전송중</dt>
							<dd>
								<div class="progress">
								  <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="${rsltDetMiddle.perIng}" aria-valuemin="0" aria-valuemax="100" style="width: ${rsltDetMiddle.perIng}%;">
								  	<span>${rsltDetMiddle.perIng}%</span>
								  </div>
								</div>
							</dd>
							<dt>실패</dt>									
							<dd>
								<div class="progress">
								  <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="${rsltDetMiddle.perFail}" aria-valuemin="0" aria-valuemax="100" style="width: ${rsltDetMiddle.perFail}%;">
								  	<span>${rsltDetMiddle.perFail}%</span>
								  </div>
								</div>
							</dd>
						</dl>
					</div>
					<table class="table dtable col-md-8">
						<tr>
							<th>분류</th>
							<th>건수</th>
							<th>설명</th>
						</tr>
						<tr>
							<td class="odd">성공</td>
							<td>${rsltDetMiddle.sucCount}</td>
							<td class="odd">메시지의 발송 절차가 성공적으로 완료 되었습니다.</td>
						</tr>		
						<tr>
							<td class="odd">전송중</td>
							<td>${rsltDetMiddle.ingCount}</td>
							<td class="odd">전송 후 결과값에 대한 응답을 기다리고 있습니다.</td>
						</tr>							
						<c:if test="${searchRsltDetVO.msgType == '04' || searchRsltDetVO.msgType == '05' || searchRsltDetVO.msgType == '06' || searchRsltDetVO.msgType == '09'}">
							<tr>
								<td class="odd">통화중</td>
								<td>${rsltDetMiddle.busyLineCount}</td>
								<td class="odd">수신자가 통화중이어서 메시지 발송이 실패했습니다.</td>
							</tr>
							<tr>
								<td class="odd">무응답</td>
								<td>${rsltDetMiddle.noAnsCount}</td>
								<td class="odd">수신자가 응답하지 않아 메시지 발송이 실패했습니다.</td>
							</tr>
						</c:if>
						<tr>
							<td class="odd">전송실패</td>
							<td>${rsltDetMiddle.totFailCount}</td>
							<td class="odd">메시지 발송에 실패하였습니다.</td>
						</tr>							
						<tr>
							<td class="odd">결번</td>
							<td>${rsltDetMiddle.noNumCount}</td>
							<td class="odd">가입된 번호가 없거나 메시지를 발송할 수 없는 번호입니다.</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">전송 상세</h3>
				</div>
				<table class="table dtable">
					<tr>
						<th>No.</th>
						<th>수신자명</th>
						<th>수신번호</th>
						<th>전송일시</th>
						<th>결과</th>
					</tr>
					<c:choose>
						<c:when test="${! empty rsltDetBottomlist}">
							<c:forEach var="RsltDetBottomVO" items="${rsltDetBottomlist}">
						 <tr>	
							<fmt:parseDate var="sendDate" value="${RsltDetBottomVO.sendDate}" pattern="yyyyMMddHHmmss" />
			                <td>${RsltDetBottomVO.rowNumber   }</td>
			                <td class="odd">${RsltDetBottomVO.destName   }</td>
			                <td>${RsltDetBottomVO.phoneNumber   }</td>
							<td class="odd"><fmt:formatDate value="${sendDate}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
							<td>${RsltDetBottomVO.resultDesc    }</td>
						</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td>NO DATA</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</div>
			<div class="panel-body text-center" align=center>
				<ul class="pagination">
					<ui:pagination paginationInfo="${searchRsltDetVO}" type="image"	jsFunction="linkPage" />
				</ul>
			</div>
			</form:form>  
			
			<form:form method="get" modelAttribute="searchRsltDetVO" id="downForm">
			<input type="hidden" id="userId" name="userId" 	value='${searchRsltDetVO.userId}'/>
	        <input type="hidden" id="jobId"	 name="jobId" 	value='${searchRsltDetVO.jobId}'/>
	        <input type="hidden" id="msgId"	 name="msgId" 	value='${searchRsltDetVO.msgId}'/>
	        <input type="hidden" id="msgType" name="msgType" value='${searchRsltDetVO.msgType}'/>
			<div class="panel-body btns text-center">
				<button class="btn btn-primary" onclick="javascript:excelDownload();">다운로드</button>
			</div>
			</form:form>			
		</div>
	</div>
 