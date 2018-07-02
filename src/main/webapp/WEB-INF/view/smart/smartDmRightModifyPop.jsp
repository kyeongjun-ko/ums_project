<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>

<script type="text/javascript">
var availSelectList = new Array();
var clickCount = 0;
 
$(document).ready(function() {
	$("#rtStrtDt, #rtEndDt").datepicker({
	    dateFormat: 'yy-mm-dd',
	    prevText: '이전 달',
	    nextText: '다음 달',
	    monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
	    monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
	    dayNames: ['일','월','화','수','목','금','토'],
	    dayNamesShort: ['일','월','화','수','목','금','토'],
	    dayNamesMin: ['일','월','화','수','목','금','토'],
	    showMonthAfterYear: true,
	    yearSuffix: '년'
	});
	
	$("#rtStrtDt").datepicker("setDate", converFormat('${result.rtStrtDt}'));
	$("#rtEndDt").datepicker("setDate", converFormat('${result.rtEndDt}'));
	
	$("#btnModify").click(function(e) {
		e.preventDefault();
		var stdt = $('#rtStrtDt').val();
		var eddt = $('#rtEndDt').val();
		
		$('#rtStrtDt').val(stdt.replace(/-/gi, ""));
		$('#rtEndDt').val(eddt.replace(/-/gi, ""));
	    var param = $("#smartModForm").serialize();
	    $('#rtStrtDt').val(stdt);
    	$('#rtEndDt').val(eddt);
    	
	    $.ajax({
	        type : "POST",
	        url : "/smart/modifySmartDmRightProc",
	        data : param,
	        success: function(json) {
	            if(json.result) {
	                alert(json.sucMsg);
	                opener.location.reload();
	                window.close();
	            } else {
	                alert(json.errMsg);
	            }
	        },
	        error: function(request, status, error) {
	        	alert("list search fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
	        	window.close();
	        }
	    });
	    
	});
});

function converFormat(yyyymmdd){
	var year        = yyyymmdd.substring(0,4);
	var month       = yyyymmdd.substring(4,6);
	var day         = yyyymmdd.substring(6,8);

	return year+'-'+month+'-'+day;
}

</script>  
<form:form method="post" modelAttribute="smartModListVO" id="smartModForm" autocomplete="off">
<form:hidden path="msgNo" value="${result.msgNo}"/>
<form:hidden path="rightNo" value="${result.rightNo}"/>
<form:hidden path="grpNo" value="${result.grpNo}"/>
<form:hidden path="grpCd" value="${result.grpCd}"/>
<form:hidden path="company" value="${result.company}"/>

<div class="panel-default popup notice">
		<div class="panel-heading">
			<h3 class="panel-title">SMART DM 권한 수정</h3>
		</div>
		<div class="panel-body inputs">
				<div class="form-group">
					<label for="dmType">메시지 유형</label>
					<input type="text" class="form-control" name="dmType"  id="dmType" value="${result.dmType}" disabled>
				</div>	
				<div class="form-group">
					<label for="grpNm">그룹명</label>
					<input type="tel" class="form-control" name="grpNm" id="grpNm" value="${result.grpNm}" disabled>
				</div>				
				<div class="form-group">
					<label for="subject">제목</label>
					<input type="tel" class="form-control" name="subject" id="subject" value="${result.subject}" disabled>
				</div>				
				<div class="form-group">
					<label for="contents1">내용</label>
					<input type="tel" class="form-control" name="contents1" id=contents1 value="${result.contents1}" disabled>
				</div>
				<div class="form-group">
					<label for="dept">권한</label>
					<select name="dept" id="dept" class="form-control">
						<c:forEach var="CommCodeVO" items="${deptList}">
			                 <option value="${CommCodeVO.code}" <c:if test="${CommCodeVO.code == result.dept}">selected="selected" </c:if>>${CommCodeVO.codeName} </option>
			            </c:forEach>
					</select>
				</div>
				<div class="form-group has-feedback">
					<label for="rtStrtDt">권한시작</label>
					<%-- <fmt:parseDate var="rtStrtDt" value="${result.rtStrtDt}" pattern="yyyyMMdd" /> --%>
					<input type="text" class="form-control" name="rtStrtDt" id="rtStrtDt" <%-- value="<fmt:formatDate value="${rtStrtDt}" pattern="yyyy-MM-dd" />" --%>>
					<!-- <i class="fa fa-calendar form-control-feedback" style="top: 11px;"></i> -->
				</div>
				<div class="form-group has-feedback">
					<label for="rtEndDt">권한종료</label>
					<%-- <fmt:parseDate var="rtEndDt" value="${result.rtEndDt}" pattern="yyyyMMdd" /> --%>
					<input type="text" class="form-control" name="rtEndDt" id="rtEndDt" <%-- value="<fmt:formatDate value="${rtEndDt}" pattern="yyyy-MM-dd" />" --%>>
					<!-- <i class="fa fa-calendar form-control-feedback" style="top: 11px;"></i> -->
				</div>
			</div>
		<div class="panel-body btns text-center">
			<button id="btnModify" class="btn btn-primary">수정</button>
			<button class="btn btn-second" onclick="window.close();">취소</button>
		</div>
	</div>
</form:form>
