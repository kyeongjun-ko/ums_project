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

function fnLogin1() {
 
 
	$("#loginForm1").attr({
        action : "/loginRegist",
        method : "post"
    }).submit();
	 
}  

</script>
<div class="col-md-9">

	<div class="col-md-12 tits">					
		<h1>로그인 하기 </h1>
	</div>
	<div class="panel panel-default col-md-12 np pform pform02">
		<div class="panel-heading">
			<h3 class="panel-title">
				로그인
			</h3>
		</div>
		
		    <input type="hidden" name="isReturn"           value="N" />
		<div class="panel-body">
			<form:form method="post" modelAttribute="umsUserVO" id="loginForm1"> 
		    <input type="hidden" name="serviceID"          value="<c:out value="${serviceID}"/>" />
		    <input type="hidden" name="domain"             value="<c:out value="${domain}"/>" />
		    <input type="hidden" name="registURL"          value="<c:out value="${registURL}"/>" />
		    <input type="hidden" name="returnURL"          value="<c:out value="${returnURL}"/>" />
				<div class="su-input">
					<div class="form-group form-inline">
						<label for="iu01">아이디</label>
						<input type="text" class="form-control"  id="userId" name="userId">
					</div>							
					<div class="form-group form-inline">
						<label for="iu02">패스워드</label>
						<input type="password" class="form-control" id="userPw" name="userPw">
					</div>
				</div>
				<div class="btns">
					<button type="submit" class="btn btn-primary btn-lg" onclick="fnLogin1();">로그인</button>					 
				</div>
			</form:form>
		</div>
	</div>	 
</div>
