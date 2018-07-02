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
	if( frm.userPw.value.trim() == "" ) {
		alert("비밀번호를 입력하세요.");
		frm.userPw.focus();
		return;
	}
	
	if( frm.userNm.value.trim() == "" ) {
		alert("담당자명을 입력하세요.");
		frm.userNm.focus();
		return;
	}
	if( frm.dept.value.trim() == "" ) {
		alert("담당부서를 입력하세요.");
		frm.dept.focus();
		return;
	}
	if( frm.company.value.trim() == "" ) {
		alert("회사명을 입력하세요.");
		frm.company.focus();
		return;
	}
	if( frm.email.value.trim() == "" ) {
		alert("담당자 이메일을 입력하세요.");
		frm.email.focus();
		return;
	}
	if( frm.telNo1.value.trim() == "" ) {
		alert("담당자전화(유선)을 입력하세요.");
		frm.telNo1.focus();
		return;
	}
	if( frm.telNo2.value.trim() == "" ) {
		alert("담당자전화(무선)을 입력하세요.");
		frm.telNo2.focus();
		return;
	}
	if( frm.callbackNo1.value.trim() == "" ) {
		alert("대표발신번호를 입력하세요.");
		frm.callbackNo1.focus();
		return;
	}
	 
	if( frm.ans.value.trim() == "" ) {
		alert("답변을 입력하세요.");
		frm.ans.focus();
		return;
	}
	
	var param = $("#modUserForm").serialize();
	
	$("#modUserForm").attr({
        action : "/modUsrProc",
        method : "post"
    }).submit();
	 
}

function callbackCheck(strSelect) {
	if(strSelect != "NULL") {
		var title = "callbackPopup";
		var status = "width=478, height=300, scrollbars=1, toolbar=no, menubar=no resizable=yes";
		window.open("/user/callbackPopup?callbackNo=" + strSelect, title, status);		
	} 
}

</script>
<div class="col-md-9">
	<div class="col-md-12 tits">
		<h1>정보변경</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-home"></i></a></li>
			<li><a href="#">마이페이지</a></li>
			<li class="active">정보변경</li>
		</ol>
	</div>
	<div class="panel panel-default pform col-md-12 np">
		<div class="panel-heading">
			<h3 class="panel-title">
				항목 입력
			</h3>
		</div>
		<div class="panel-body">
				
			<form:form method="post"  name="frmRegUser" modelAttribute="umsUserVO" id="modUserForm"  action="/modUsrProc">
			<input type="hidden" name="userLv"          value="${umsUserVO.userLv}" /> 
			<input type="hidden" name="userId"          value="${umsUserVO.userId}" />
				<div class="su-input">
					<div class="form-group form-inline col-md-12">
						<label for="su01">아이디<em>*</em></label>
						<input type="text" class="form-control" disabled="" value="${umsUserVO.userId}">
					</div>							
					<div class="form-group form-inline col-md-12">
						<label for="su02">비밀번호<em>*</em></label>
						<input type="password" class="form-control" id="userPw" name="userPw"  required>
						<span><em>*</em> 정보 변경시 현재 비밀번호 입력하세요</span>
					</div>							
					<!-- 
					<div class="form-group form-inline col-md-12">
						<label for="su03">비밀번호 확인<em>*</em></label>
						<input type="password" class="form-control" id="userPwC" name="userPwC"  required>
					</div>
					 -->							
					<div class="form-group form-inline col-md-12">
						<label for="su04">담당자명<em>*</em></label>
						<input type="text" class="form-control"  id="userNm" name="userNm" value="${umsUserVO.userNm}" required>
					</div>
					
					<div class="form-group form-inline col-md-12">
						<label for="su05">담당부서<em>*</em></label>
						
						<input type="text" id="SENDER" value="${umsUserVO.dept}"  class="form-control textinput" style="display:none; width:100%; float:left;"  />
						<select class="form-control textinput-selector" id="dept" name="dept" value="${umsUserVO.dept}">
							 <c:if test="${! empty umsUserVO.deptCdList}">
		                         <c:forEach var="item" items="${umsUserVO.deptCdList}">
		                            <c:if test="${! empty item.codeName}">
		                            <option value="${item.code}" <c:if test="${umsUserVO.dept == item.code}"> selected="selected" </c:if> >${item.codeName}</option>
		                            </c:if>
		                         </c:forEach>
		                         	<!-- <option value="DIRECT">직접입력</option> -->
		                     </c:if>
						</select>	
						<span id="spanSelectBox" onclick="SenderSelect('', document.getElementById('SELECT_SENDER').form)" style="display:none;">
							<button class="btn btn-second" style="float:right;" >선택</button>
						</span>
						
					</div>	
												
									
					<div class="col-md-6">
						<div class="form-group form-inline">
							<label for="su06">회사명<em>*</em></label>
							
							<input type="text" id="SENDER" value="${umsUserVO.company}"  class="form-control textinput" style="display:none; width:100%; float:left;"  />
							<select class="form-control textinput-selector" id="company" name="company"  value="${umsUserVO.company}">
								 <c:if test="${! empty umsUserVO.comCdList}">
			                         <c:forEach var="item" items="${umsUserVO.comCdList}">
			                            <c:if test="${! empty item.codeName}">
			                            <option value="${item.code}" <c:if test="${umsUserVO.company == item.code}"> selected="selected" </c:if> >${item.codeName}</option>
			                            </c:if>
			                         </c:forEach>
			                         	<!-- <option value="DIRECT">직접입력</option> -->
			                     </c:if>
							</select>
							<!-- 
							<input type="text" class="form-control" id="company" name="company" value="${umsUserVO.company}" required>
							 -->
						</div>
						
						<div class="form-group form-inline">
								<label for="su07">담당자 이메일<em>*</em></label>
								<input type="email" class="form-control" id="email" name="email" value="${umsUserVO.email}" required>
						</div>							
						<div class="form-group form-inline">
								<label for="su08">담당자전화(유선)<em>*</em></label>
								<input type="tel" class="form-control"  id="telNo1" name="telNo1" value="${umsUserVO.telNo1}" required>
						</div>
						<div class="form-group form-inline" style="width:550px;">
								<label for="su09" style="width:120px;">담당자전화(무선)<em>*</em></label>
								<input type="tel" class="form-control"  id="telNo2" name="telNo2"  value="${umsUserVO.telNo2}" required>
						        <label style="width:180px;">&nbsp;푸쉬 수신 전화번호(인증대상)</label>
						</div>
					</div>
					<div class="col-md-6">
						<%-- <div class="form-group form-inline">
							<label for="su10">대표발신번호<em>*</em></label>
							<input type="tel" class="form-control"  id="callbackNo1" name="callbackNo1" value="${umsUserVO.callbackNo1}" required>
						</div> --%>
						<div class="form-group form-inline">
							<label for="su10">발신번호</label>
							<select class="form-control textinput-selector" id="callbackSubNo" name="callbackSubNo"> <!-- onchange="callbackCheck(this.value)" -->
								<option value="NULL">발신번호목록</option>
								<c:if test="${! empty umsUserVO.callbackList}">
									<c:forEach var="item" items="${umsUserVO.callbackList}">
										<c:if test="${! empty item.callbackNo && item.statusCd eq 'A'}">
											<option value="${item.callbackNo}" >${item.callbackNo}</option>
										</c:if>
									</c:forEach>
								</c:if>
								<!-- <option value="DIRECT">번호 등록</option> -->
							</select>
						</div>							
						<%-- <div class="form-group form-inline">
							<label for="su11">발신번호1</label>
							<input type="tel" class="form-control"  id="callbackNo2" name="callbackNo2" value="${umsUserVO.callbackNo2}" >
						</div>					
						<div class="form-group form-inline">
							<label for="su12">발신번호2</label>
							<input type="tel" class="form-control"  id="callbackNo3" name="callbackNo3" value="${umsUserVO.callbackNo3}"  >
						</div>							
						<div class="form-group form-inline">
							<label for="su13">발신번호3</label>
							<input type="tel" class="form-control" id="callbackNo4" name="callbackNo4" value="${umsUserVO.callbackNo4}" >
						</div>	 --%>
					</div>
					<div class="form-group form-inline col-md-6">
							<label for="su11">비밀번호 질문<em>*</em></label>
							<select   id="que" name="que"  value="${umsUserVO.que}"  class="form-control">
							<option value="01" <c:if test="${umsUserVO.que == '01'}"> selected="selected" </c:if>>초등학교 첫사랑 이름은?</option>
							<option value="02" <c:if test="${umsUserVO.que == '02'}"> selected="selected" </c:if>>나만의 추억의 장소는?</option>
							<option value="03" <c:if test="${umsUserVO.que == '03'}"> selected="selected" </c:if>>나의 좌우명은?</option>
							<option value="04" <c:if test="${umsUserVO.que == '04'}"> selected="selected" </c:if>>내가 가장 좋아하는 색깔은?</option>
							<option value="05" <c:if test="${umsUserVO.que == '05'}"> selected="selected" </c:if>>내가 가장 소중히 여기는 것은?</option>
							<option value="06" <c:if test="${umsUserVO.que == '06'}"> selected="selected" </c:if>>내가 꼭 가보고 싶은 곳은?</option>
							<option value="07" <c:if test="${umsUserVO.que == '07'}"> selected="selected" </c:if>>가장 인상깊게 본 영화는?</option>
							<option value="08" <c:if test="${umsUserVO.que == '08'}"> selected="selected" </c:if>>나의 꿈은?</option>
							<option value="09" <c:if test="${umsUserVO.que == '09'}"> selected="selected" </c:if>>나와 가장 많이 닮은 연예인은?</option>
							<option value="10" <c:if test="${umsUserVO.que == '10'}"> selected="selected" </c:if>>나의 가장 친한 친구는?</option>
							<option value="11" <c:if test="${umsUserVO.que == '11'}"> selected="selected" </c:if>>내가 가장 좋아하는 노래는?</option>
							<option value="12" <c:if test="${umsUserVO.que == '12'}"> selected="selected" </c:if>>출신 초등학교 이름은?</option>
							<option value="13" <c:if test="${umsUserVO.que == '13'}"> selected="selected" </c:if>>내 생애 잊지 못할 날은?</option>
							</select>
					</div>
					<div class="form-group form-inline col-md-6">
						<label for="su12">답변<em>*</em></label>
						<input type="text" class="form-control" id="ans" name="ans" value="${umsUserVO.ans}" required >
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
