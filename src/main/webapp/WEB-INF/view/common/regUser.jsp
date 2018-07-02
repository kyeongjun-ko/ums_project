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
	
	$("#valIdProc").click(function() {
    	//page setting
        var param = $("#regUserForm").serialize();
        $.ajax({
            type : "POST",
            url : "/valIdProcAjax",
            data : param,
            success: function(json) {
            	if(json.result) {
                    //
            		alert("사용가능한 ID입니다.");
                } else {
                    alert("중복된 ID입니다. 다른아이디를 사용해 주세요");
                }
            },
            error: function(request, msg, error) {
            	alert("id search fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
            }
        });
        
    });
	 
});
function fnRegUser() {
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
	
	if(  frm.userPwC.value.trim() == "") {
		alert("비밀번호를 입력하세요.");
		frm.userPwC.focus();
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
/* 	if( frm.callbackNo1.value.trim() == "" ) {
		alert("대표회신번호를 입력하세요.");
		frm.callbackNo1.focus();
		return;
	} */
	 
	if( frm.ans.value.trim() == "" ) {
		alert("답변을 입력하세요.");
		frm.ans.focus();
		return;
	}
	
	var param = $("#regUserForm").serialize();
	$.ajax({
        type : "POST",
        url : "/regUsrProcAjax",                
        data : param,
        success : function(json) {
            if(json.result) {
                alert(json.sucMsg);
                document.location.reload();
            } else {
                alert(json.errMsg);
            }
        },
        error : function(request, status, error) {
            alert("order fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
        }
    });
 
}   

//회사명 입력(selectbox)
function CompanySelect(strSelect, frm) { 
	if( strSelect == "DIRECT" ) {
		frm.company.style.display = "none";
		frm.companyNm.style.display = "";
		frm.companyNm.value = "";
		frm.companyNm.focus();
		if(frm.company.options.length > 1)
			document.getElementById("spanSelectBox").style.display = "";
	} else {
		frm.company.style.display = "inline";
		frm.companyNm.style.display = "none";
		frm.companyNm.value = strSelect;
		if( frm.company.options[frm.company.selectedIndex].value == "DIRECT" ) {
			frm.company.options[0].selected = true;
		}
		document.getElementById("spanSelectBox").style.display = "none";
	}
}

//부서명 입력(selectbox)
function DeptSelect(strSelect, frm) { 
	if( strSelect == "DIRECT" ) {
		frm.dept.style.display = "none";
		frm.deptNm.style.display = "";
		frm.deptNm.value = "";
		frm.deptNm.focus();
		if(frm.dept.options.length > 1)
			document.getElementById("spanSelectBoxDept").style.display = "";
	} else {
		frm.dept.style.display = "inline";
		frm.deptNm.style.display = "none";
		frm.deptNm.value = strSelect;
		if( frm.dept.options[frm.dept.selectedIndex].value == "DIRECT" ) {
			frm.dept.options[0].selected = true;
		}
		document.getElementById("spanSelectBoxDept").style.display = "none";
	}
}
</script>
<div class="col-md-9">
	<div class="col-md-12 tits">					
			<h1>회원가입</h1>
		</div>
		<div class="panel panel-default pform col-md-12 np">
			<div class="panel-heading">
				<h3 class="panel-title">
					항목 입력
				</h3>
			</div>
			<div class="panel-body">
				<form method="post" id="regUserForm" name="frmRegUser" action="/regUsrProcAjax"   onsubmit="return false;" > 
					<div class="su-input">
						<div class="form-group form-inline col-md-12">
							<label for="su01">아이디<em>*</em></label>
							<input type="text" class="form-control"  id="userId" name="userId" value="${umsUserVO.userId}" >
							<button class="btn btn-primary" id="valIdProc"  >중복확인</button>
						</div>							
						<div class="form-group form-inline col-md-12">
							<label for="su02">비밀번호<em>*</em></label>
							<input type="password" class="form-control"  id="userPw" name="userPw" >
							<span><em>*</em> 비밀번호는 4-12자리, 영문 이나 영문 숫자 조합.</span>
						</div>							
						<div class="form-group form-inline col-md-12">
							<label for="su03">비밀번호 확인<em>*</em></label>
							<input type="password" class="form-control"  id="userPwC" name="userPwC" >
						</div>		
						<div class="form-group form-inline panel-body">
							<label for="su04">회사명<em>*</em></label>
							
							<input type="text" id="companyNm" name="companyNm" value="${umsUserVO.company}"  class="form-control" style="display:none; "  />
							<select class="form-control textinput-selector" id="company" name="company" value="${umsUserVO.company}" onchange="CompanySelect(this.value, this.form)">
								 <c:if test="${! empty comCdList}">
			                         
			                         <c:forEach var="item" items="${comCdList}">
			                            <c:if test="${! empty item.codeName}">
			                            <option value="${item.code}" <c:if test="${umsUserVO.company == item.code}"> selected="selected" </c:if> >${item.codeName}</option>
			                            </c:if>
			                         </c:forEach>
			                         <option value="DIRECT" style="color:red;"> 직접입력 </option>
			                         
			                     </c:if>
							</select>
							<span id="spanSelectBox" onclick="CompanySelect('', document.getElementById('company').form)" style="display:none;">
								<button class="btn btn-second"  >선택</button>
							</span>
						</div>						
							
						<div class="form-group form-inline col-md-12">
							<label for="su05">담당부서<em>*</em></label>
							<input type="text" id="deptNm" name="deptNm"  value="${umsUserVO.dept}"  class="form-control" style="display:none; "  />
							<select class="form-control textinput-selector" id="dept" name="dept" value="${umsUserVO.dept}" onchange="DeptSelect(this.value, this.form)">
								 <c:if test="${! empty deptCdList}">
			                         <c:forEach var="item" items="${deptCdList}">
			                            <c:if test="${! empty item.codeName}">
			                            <option value="${item.code}" <c:if test="${umsUserVO.dept == item.code}"> selected="selected" </c:if> >${item.codeName}</option>
			                            </c:if>
			                         </c:forEach>
			                         <option value="DIRECT" style="color:red;"> 직접입력 </option>
			                     </c:if>
							</select>	 
							<span id="spanSelectBoxDept" onclick="DeptSelect('', document.getElementById('dept').form)" style="display:none;">
								<button class="btn btn-second"  >선택</button>
							</span>
						</div>	
						 
						<div class="col-md-6">
							<div class="form-group form-inline col-md-12">
								<label for="su06">담당자명<em>*</em></label>
								<input type="text" class="form-control"  id="userNm" name="userNm" value="${umsUserVO.userNm}" >
							</div>				
							<div class="form-group form-inline">
									<label for="su07">담당자 이메일<em>*</em></label>
									<input type="email" class="form-control"  id="email" name="email" value="${umsUserVO.email}" >
							</div>							
							<div class="form-group form-inline">
									<label for="su08">담당자전화(유선)<em>*</em></label>
									<input type="tel" class="form-control" id="telNo1" name="telNo1" value="${umsUserVO.telNo1}" >
							</div>
							<div class="form-group form-inline" style="width:550px;">
									<label for="su09" style="width:120px;">담당자전화(무선)<em>*</em></label>
									<input type="tel" class="form-control" id="telNo2" name="telNo2" value="${umsUserVO.telNo1}" >
									<label style="width:180px;">&nbsp;푸쉬 수신 전화번호(인증대상)</label>
							</div>
						</div>
						<%-- <div class="col-md-6">
							<div class="form-group form-inline">
								<label for="su10">대표회신번호<em>*</em></label>
								<input type="tel" class="form-control" id="callbackNo1" name="callbackNo1" value="${umsUserVO.callbackNo1}" >
							</div>							
 							<div class="form-group form-inline">
								<label for="su11">회신번호1</label>
								<input type="tel" class="form-control" id="callbackNo2" name="callbackNo2" value="${umsUserVO.callbackNo2}" >
							</div>					
							<div class="form-group form-inline">
								<label for="su12">회신번호2</label>
								<input type="tel" class="form-control" id="callbackNo3" name="callbackNo3" value="${umsUserVO.callbackNo3}" >
							</div>							
							<div class="form-group form-inline">
								<label for="su13">회신번호3</label>
								<input type="tel" class="form-control" id="callbackNo4" name="callbackNo4" value="${umsUserVO.callbackNo4}" >
							</div> 	
						</div> --%> 
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
							<input type="text" class="form-control"  id="ans" name="ans"  >
						</div>
					</div>
					<div class="btns">
						<button type="submit" onclick="fnRegUser();"  class="btn btn-primary btn-lg">회원가입</button>
						<button type="submit" onclick="document.location.reload();" class="btn btn-second btn-lg">취소하기</button>
					</div>
				</form>
				
			</div>
		</div>
	</div>
</div>	