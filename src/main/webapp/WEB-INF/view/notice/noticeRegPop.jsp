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
 
</script> 
<form:form method="post" modelAttribute="noticeVO" id="noticeListForm" autocomplete="off"  action="/mgr/grpMgrWriteProcess">
<form:hidden path="regFg" />
<form:hidden path="grpNo" />
<input type="hidden" id="Tab" value="0"/>
<input type="hidden" id="catevalue" value="" />  
<div class="panel-default popup notice">
		<div class="panel-heading">
			<h3 class="panel-title">공지사항 추가 수정</h3>
		</div>
		<div class="panel-body inputs">
			<form action="">
				<div class="form-group">
					<label for="nonum">번호</label>
					<input type="number" class="form-control" id="noticeNo" name="noticeNo" value="${noticeVO.noticeNo}">
				</div>			
				<div class="form-group">
					<label for="notit">제목</label>
					<input type="text" class="form-control" id="notit" name="grpCd" value="${noticeVO.noticeTitle}">
				</div>
				<div class="form-group">
					<label for="notarea">내용</label>
					<textarea name="" id="notarea" cols="30" rows="3" name="grpCd" value="${noticeVO.noticeCont}" class="form-control"></textarea>
				</div>
				<div class="form-group">
					<label for="notit">등록일시</label>
					<input type="date" class="form-control" id="notit" name="grpCd" value="${noticeVO.instDt}">
				</div>
			</form>
		</div>
		<div class="panel-body btns text-center">
			<button class="btn btn-primary">등록/수정</button>
			<button class="btn btn-second" onclick="window.close();">취소</button>
		</div>
	</div>
</form:form>
