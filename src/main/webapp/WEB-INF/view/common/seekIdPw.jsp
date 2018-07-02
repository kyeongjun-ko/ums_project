<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./common.jsp"%>

<script type="text/javascript">
$(document).ready(function(){
	$('input[name="userNm1"]').focus();
	
	$("#seeKIdProc").click(function() {
    	//page setting
        var param = $("#seekIdForm").serialize();
        $.ajax({
            type : "POST",
            url : "/seeKIdProcAjax",
            data : param,
            success: function(json) {
            	if(json.result) {
            		alert("해당하는 아이디는 [ "+json.msg+" ] 입니다.");
                } else {
                    alert("응답내용이 없습니다. 다시 시도해주세요");
                }
            },
            error: function(request, msg, error) {
            	alert("id search fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
            }
        });
        
    });
	
	
	$("#seeKPwProc").click(function() {
    	//page setting
        var param = $("#seekPwForm").serialize();
        $.ajax({
            type : "POST",
            url : "/seeKPwProcAjax",
            data : param,
            success: function(json) {
            	if(json.result) {
            		alert("해당하는 비밀번호는 [ "+json.msg+" ] 입니다.");
                } else {
                	alert("응답내용이 없습니다. 다시 시도해주세요");
                }
            },
            error: function(request, msg, error) {
            	alert("id search fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
            }
        });
        
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
		<h1>아이디/비밀번호 찾기</h1>
	</div>
	<div class="panel panel-default col-md-12 np pform pform02">
		<div class="panel-heading">
			<h3 class="panel-title">
				아이디 찾기
			</h3>
		</div>
		<div class="panel-body">
			<form method="post" id="seekIdForm" name="seekIdForm"  onsubmit="return false;" >
				<div class="su-input">
					<div class="form-group form-inline">
						<label for="iu01">담당자명</label>
						<input type="text" class="form-control" id="userNm1" name="userNm1" >
					</div>							
					<div class="form-group form-inline">
						<label for="iu02">담당자명 이메일</label>
						<input type="email" class="form-control" id="email1" name="email1" >
					</div>
				</div>
				<div class="btns">
					<button type="submit" id="seeKIdProc" class="btn btn-primary btn-lg">확인</button>
				</div>
			</form>
		</div>
	</div>				
	<div class="panel panel-default col-md-12 np pform pform02">
		<div class="panel-heading">
			<h3 class="panel-title">
				비밀번호 찾기
			</h3>
		</div>
		<div class="panel-body">
			<form method="post" id="seekPwForm" name="seekPwForm"  onsubmit="return false;" >
				<div class="su-input">
					<div class="form-group form-inline">
						<label for="pu01">아이디</label>
						<input type="text" class="form-control" id="userId2" name="userId2">
					</div>							
					<div class="form-group form-inline">
						<label for="pu02">담당자명</label>
						<input type="text" class="form-control" id="userNm2" name="userNm2">
					</div>								
					<div class="form-group form-inline">
						<label for="pu03">담당자 이메일</label>
						<input type="email" class="form-control" id="email2" name="email2" >
					</div>
					<div class="form-group form-inline col-md-6">
						<label for="pu04">비밀번호 질문<em>*</em></label>
								<select id="que" name="que" class="form-control">
									<option value="01">초등학교 첫사랑 이름은?</option>
									<option value="02">나만의 추억의 장소는?</option>
									<option value="03">나의 좌우명은?</option>
									<option value="04">내가 가장 좋아하는 색깔은?</option>
									<option value="05">내가 가장 소중히 여기는 것은?</option>
									<option value="06">내가 꼭 가보고 싶은 곳은?</option>
									<option value="07">가장 인상깊게 본 영화는?</option>
									<option value="08">나의 꿈은?</option>
									<option value="09">나와 가장 많이 닮은 연예인은?</option>
									<option value="10">나의 가장 친한 친구는?</option>
									<option value="11">내가 가장 좋아하는 노래는?</option>
									<option value="12">출신 초등학교 이름은?</option>
									<option value="13">내 생애 잊지 못할 날은?</option>
								</select>
					</div>
					<div class="form-group form-inline col-md-6">
						<label for="pu05">답변</label>
						<input type="text" class="form-control"  id="ans" name="ans" >
					</div>
				</div>
				<div class="btns">
					<button type="submit" id="seeKPwProc" class="btn btn-primary btn-lg">확인</button>
				</div>
			</form>
		</div>
	</div>
</div>
