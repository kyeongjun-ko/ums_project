<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./common.jsp"%>

<script type="text/javascript">
$(document).ready(function(){
	$('input[name="userId"]').focus();
	$('input[name="userId"]').keyup(function(e){
		if(e.keyCode == 13) $('input[name="userPw"]').focus();
	});
	$('input[name="userPw"]').keyup(function(e){
		if(e.keyCode == 13) fnLogin();
	});
	
});

function fnModUser() {
var frm = document.frmRegUser;

	
	if( frm.userId.value.trim() == "" ) {
		alert("사용자 ID를 입력하세요.");
		frm.userId.focus();
		return;
	}  
	
	if( frm.userPwB.value.trim() == "" ) {
		alert("현재 비밀번호를 입력하세요.");
		frm.userPwB.focus();
		return;
	}
	
	if( frm.userPw.value.trim() == "" ) {
		alert("비밀번호를 입력하세요.");
		frm.userPw.focus();
		return;
	}
	if( frm.userPwC.value.trim() == "" ) {
		alert("비밀번호를 입력하세요.");
		frm.userPwC.focus();
		return;
	}
	
	var param = $("#modUserForm").serialize();
	
	$("#modUserForm").attr({
        action : "/modUsrPwProc",
        method : "post"
    }).submit();
	 
}  

</script>
<div class="col-md-9">
	<div class="col-md-12 tits">
		<h1>비밀번호 변경</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-home"></i></a></li>
			<li><a href="#">마이페이지</a></li>
			<li class="active">비밀번호 변경</li>
		</ol>
	</div>
	<div class="panel panel-default pform col-md-12 np">
		<div class="panel-heading">
			<h3 class="panel-title">
				항목 입력
			</h3>
		</div>
		<div class="panel-body">
				
			<form:form method="post"  name="frmRegUser" modelAttribute="umsUserVO" id="modUserForm"  action="/modUsrPwProc">
			<input type="hidden" name="userLv"          value="${umsUserVO.userLv}" /> 
			<input type="hidden" name="userId"          value="${umsUserVO.userId}" />
				<div class="su-input">
					<div class="form-group form-inline col-md-12">
						<label for="su01">아이디<em>*</em></label>
						<input type="text" class="form-control" disabled="" value="${umsUserVO.userId}">
					</div>							
					<div class="form-group form-inline col-md-12">
						<label for="su02">현재 비밀번호<em>*</em></label>
						<input type="password" class="form-control" id="userPwB" name="userPwB"  required>
						<span><em>*</em> 비밀번호 변경시 현재 비밀번호 입력하세요</span>
					</div>	
					<div class="form-group form-inline col-md-12">
						<label for="su02">변경 비밀번호<em>*</em></label>
						<input type="password" class="form-control"  id="userPw" name="userPw" >
						<span><em>*</em> 비밀번호는 4-12자리, 영문 이나 영문 숫자 조합.</span>
					</div>							
					<div class="form-group form-inline col-md-12">
						<label for="su03">변경 비밀번호 확인<em>*</em></label>
						<input type="password" class="form-control"  id="userPwC" name="userPwC" >
					</div>								
				</div>
				<div class="btns">
					<button type="submit" onclick="fnModUser();" class="btn btn-primary btn-lg">변경하기</button>
					<button type="submit" onclick="document.location.reload();" class="btn btn-second btn-lg">취소하기</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
