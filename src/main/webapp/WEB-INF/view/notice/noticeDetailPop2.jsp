<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>

<script type="text/javascript">
var availSelectList = new Array();
var clickCount = 0;
 
$(document).ready(function() {
	//addr list check all
	$(".addrListCheckAll").bind('click', function() {
        if($(this).is(":checked")) {
            $(".checkbox").attr("checked", true);
        } else {
            $(".checkbox").attr("checked", false);
        }
    });
	
});

function setCookie(name, value, expiredays){
	var todayDate = new Date();
	todayDate.setDate(todayDate.getDate()+expiredays);
	document.cookie = name + "=" + escape(value) + "; path=/; expires=" + todayDate.toGMTString() + ";"	;
}
function closeWin(val){
	setCookie("Notice"+val,"done",1);
	window.close();
}

</script> 
<form:form method="post" modelAttribute="noticeVO" id="noticeListForm" autocomplete="off"  action="/srv/noticeDetail">
<fmt:parseDate var="instDt" value="${noticeVO.instDt}" pattern="yyyyMMddHHmmss" />
<input type="hidden" id="Tab" value="0"/>
<input type="hidden" id="catevalue" value="" />  
	<div class="panel-default popup notice">
		<div class="panel-heading">
			<h3 class="panel-title">공지사항 상세 보기</h3>
		</div>
		<div class="panel-body inputs">
				<div class="form-group">
					<label for="nonum">번호</label>
					<input type="number" class="form-control" id="noticeNo" name="noticeNo" value="${noticeVO.noticeNo}" readonly>
				</div>			
				<div class="form-group">
					<label for="notit">제목</label>
					<input type="text" class="form-control" id="noticeTitle" name="noticeTitle" value="${noticeVO.noticeTitle}" readonly> 
				</div>
				<div class="form-group">
					<label for="notarea">내용</label>
					<textarea id="noticeCont" cols="30" rows="10" name="noticeCont" value="${noticeVO.noticeCont}" class="form-control" readonly>${noticeVO.noticeCont}</textarea>
				</div>
				<div class="form-group">
					<label for="notit">등록일시</label>
					<input type="text" class="form-control" id="instDt" name="instDt" value="<fmt:formatDate value="${instDt}" pattern="yyyy-MM-dd  HH:mm:ss" />" readonly>
				</div>
		</div>
		<button class="btn btn-primary" onclick="javascript:closeWin(${noticeVO.noticeNo});">[닫기]</button>
		
	</div>
</form:form>
