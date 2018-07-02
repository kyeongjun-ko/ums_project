<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<script type="text/javascript">
var callbackCheckFlag = false;
var authCodeCheckFlag = false;

$(document).ready(function(){
	var $callbackNo = $("#callbackNo");
	var $authCode = $("#authCode");
	$callbackNo.keyup(function(e){
		callbackCheckFlag = false;
		authCodeCheckFlag = false;
		$authCode.val("");
	});
	
	$authCode.keyup(function(e){
		if(!authCodeCheckFlag) {
			$authCode.val("");
			alert("인증번호 SMS요청하세요.");
			return;
		}
	});
});

function checkCallback() {
	$callbackNo = $("#callbackNo");
	
	if($callbackNo.val() == "") {
		alert("발신번호를 입력하세요.");
		$callbackNo.focus();
		return;
	}
	
	$.ajax({
        type : "POST",
        url : "/checkCallbackAjax",
        data : 
        	{
        		callbackNo : $callbackNo.val()
        	},
        success: function(rs) {
        	if(rs) {
        		callbackCheckFlag = true;
        		alert("사용 가능한 번호입니다.");        		
        	} else {
        		callbackCheckFlag = false;
        		alert("이미 등록한 번호입니다.");
        		$callbackNo.focus();
        	}
        },
        error: function(request, status, error) {
        	alert("list search fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
        }
    });
}

function auth(type) {
	$callbackNo = $("#callbackNo");
	$authCode = $("#authCode");
	
	if($callbackNo.val() == "") {
		if(type == 'I') alert("발신번호를 입력하세요.");
		if(type == 'D') alert("발신번호를 선택하세요.");
		$callbackNo.focus();
		return;
	}
	
	if(!callbackCheckFlag && type == 'I') {
		alert("발신번호 중복 확인해주세요.");
		return;
	}
	
    $.ajax({
        type : "POST",
        url : "/authProcAjax",
        data : 
        	{
        		callbackNo : $callbackNo.val()
        	},
        success: function(rs) {
        	if(rs) {
        		$authCode.val("");
        		authCodeCheckFlag = true;
        		alert("등록하신 휴대폰으로 인증번호를 발송했습니다.");
        		$authCode.focus();
        	} else {
        		authCodeCheckFlag = false;
        		alert("인증번호 발송 실패");
        	}
        },
        error: function(request, status, error) {
        	alert("list search fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
        }
    });
}

function regCallback() {
	$callbackNo = $("#callbackNo");
	$authCode = $("#authCode");
	
	if($callbackNo.val() == "") {
		alert("발신번호를 입력하세요.");
		$callbackNo.focus();
		return;
	}

	if(!callbackCheckFlag) {
		alert("발신번호 중복 확인해주세요.");
		return;
	}
	
	if($authCode.val() == "") {
		alert("인증번호를 입력하세요.");
		$authCode.focus();
		return;
	}
	
	$.ajax({
        type : "POST",
        url : "/regCallbackAjax",
        data : 
        	{
        		callbackNo 	: $callbackNo.val(),
        		authCode	: $authCode.val()
        	},
        success: function(rs) {
        	if(rs) {
        		alert("발신번호를 추가하였습니다. 관리자 승인 후 사용하실 수 있습니다.");
        		/* opener.location.reload(); */
        		opener.location.href = opener.location.href; 
        		window.close();        		
        	} else {
        		alert("인증번호를 확인 해주시기 바랍니다.");
        		$("#authCode").focus();
        	}
        },
        error: function(request, status, error) {
        	alert("list search fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
        }
    });
}

function delCallback() {
	$callbackNo = $("#callbackNo");
	$authCode = $("#authCode");
	
	if($callbackNo.val() == "") {
		alert("발신번호를 선택하세요.");
		$callbackNo.focus();
		return;
	}
	
	if($authCode.val() == "") {
		alert("인증번호를 입력하세요.");
		$authCode.focus();
		return;
	}
	
	$.ajax({
        type : "POST",
        url : "/delCallbackAjax",
        data : 
        	{
        		callbackNo 	: $callbackNo.val(),
        		authCode	: $authCode.val()
        	},
        success: function(rs) {
        	if(rs) {
        		alert("발신번호를 삭제하였습니다.");
        		/* opener.location.reload(); */
        		opener.location.href = opener.location.href;
        		window.close(); 
        	} else {
        		alert("인증번호를 확인 해주시기 바랍니다.");
        		$authCode.focus();
        	}
        },
        error: function(request, status, error) {
        	alert("list search fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
        }
    });
}
</script>
<div class="panel-default popup addpop">
	<c:set var="type" value="${param.type}"/>
	<c:if test="${type == 'I'}">
		<div class="panel-heading">
				<h3 class="panel-title">발신번호 등록</h3>
		</div>
		<div class="panel-body inputs">
		   	<div class="input-group">
		   		<input type="text" class="form-control" id="callbackNo" name="callbackNo" placeholder="발신번호">
		   	</div>
		   	<button class="btn btn-second" onclick="checkCallback()">중복확인</button>
		</div>
		<div class="panel-body inputs">
		   	<div class="input-group">
		   		<input type="text" class="form-control" id="authCode" name="authCode" placeholder="인증번호 ">
		   	</div>
		   	<button class="btn btn-second" onclick="auth('I')" title="인증번호 발송">SMS요청</button>
		</div>
		<div class="panel-body">
			※ 발신번호 입력 >> 중복확인 >> SMS요청 >> 인증번호 입력 >> 등록<br><br> 
			SMS 요청을 하시면 등록하신 담당자전화(무선) 번호로 인증번호가 발송됩니다.<br><br>
			<img src="/images/callback.png" alt="">
		</div>
		<div class="panel-body btns text-center">
			<button class="btn btn-primary" onclick="regCallback()">등록</button>
			<button class="btn btn-second"  onclick="window.close()">취소</button>
		</div>
	</c:if>
	<c:if test="${type == 'D'}">
		<div class="panel-heading">
			<h3 class="panel-title">발신번호 삭제</h3>
		</div>
		<div class="panel-body inputs">
		   	<div class="input-group">
		   		<select id="callbackNo" name="callbackNo" class="form-control">
		   			<option value="">발신번호</option>
			   		<c:choose>
			   			<c:when test="${! empty callbackList }">
			   				<c:forEach var="item" items="${callbackList }">
			   				<option value="${item.callbackNo }">${item.callbackNo }</option>
			   				</c:forEach>
			   			</c:when>
			   			<c:otherwise>
			   				<option>등록번호없음</option>
			   			</c:otherwise>
			   		</c:choose>
		   		</select>
		   	</div>
		</div>
		<div class="panel-body inputs">
		   	<div class="input-group">
		   		<input type="text" class="form-control" id="authCode" name="authCode" placeholder="인증번호" >
		   	</div>
		 	<button class="btn btn-second" onclick="auth('D')" title="인증번호 발송">SMS요청</button>
		</div>
		<div class="panel-body">
			※ 발신번호 선택 >> SMS요청 >> 인증번호 입력 >> 삭제<br><br>
			SMS 요청을 하시면 등록하신 담당자전화(무선) 번호로 인증번호가 발송됩니다.<br>
		</div>
		<div class="panel-body btns text-center">
			<button class="btn btn-primary" onclick="delCallback()">삭제</button>
			<button class="btn btn-second"  onclick="window.close()">취소</button>
		</div>
	</c:if>
</div>

