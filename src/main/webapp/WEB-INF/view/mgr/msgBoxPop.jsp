<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>

<script type="text/javascript">
var availSelectList = new Array();
var clickCount = 0;
 
$(document).ready(function() {
});

function linkPage(pageNo) {
    $("#currentPageNo").val(pageNo);
    
    $("#addrListForm").attr({
        action : "/ums/addrListPopup",
        method : "post"
    }).submit();
}

var select = "<option>그룹</option>"; 
//$("#grpNo").change(function() {
//    var selgrp = $("#grpNo option:selected").val();
    //alert(selgrp);
//    $("#MSGGRPNO").val(selgrp);
    //$("select[name=selectbox]").val();
// });

$("#grpCd").change(function() {
    //$("select[name=selectbox]").val();
	if($("#grpCd").val() == "") { // select의 value가 ""이면, "선택" 메뉴만 보여줌.
		$("#grpNo").find("option").remove().end().append(select);
	} else {
		comboChange($(this).val());
	}
 });

function comboChange() {
	$.ajax({
		type:"post",
		url:"/ums/grpNoListAjax",
		datatype: "json",
		data: $("#msgBoxForm").serialize(),
		success: function(data) {
			if(data.usrGrpList != "") {
				$("#grpNo").find("option").remove().end().append(select);
				 var html = "";
				 html += '<option value="">그룹</option>';
		        $.each(data.usrGrpList, function(index, item) {
		            html += '<option value="'+ item.grpNo +'" <c:if test="${'+ item.grpNo +'== searchMsgBoxVO.grpNo}">selected="selected" </c:if> >'+item.grpNm  +'</option>' ;
		        });
			    $("#grpNo").find("option").remove().end().append(html);
			} else {
				$("#grpNo").find("option").remove().end().append("<option value=''>그룹</option>");
				return;
			}
		},
		error: function(x, o, e) {
			var msg = "페이지 호출 중 에러 발생 \n" + x.status + " : " + o + " : " + e; 
			alert(msg);
		}				
	});
}

</script> 
<form:form method="post" modelAttribute="msgBox" id="msgBoxForm" autocomplete="off" action="/trs/msgBoxUpdateProcPopup">
<form:hidden path="msgNo"/>

<div class="panel-default popup notice">
	<div class="panel-heading">
		<h3 class="panel-title">메시지 추가 수정</h3>
	</div>
	<div class="panel-body inputs">
		
			<div class="form-group">
				<label for="tysel">메시지유형</label>
				<select name="grpCd" value="${msgBox.grpCd}" id="grpCd" class="form-control">
			        <c:forEach var="grpMgrVO" items="${grpCdList}">
		                 <option value="${grpMgrVO.code}"  <c:if test="${grpMgrVO.code == msgBox.grpCd}">selected="selected" </c:if>>${grpMgrVO.codeName} </option>
		            </c:forEach>
				</select>
			</div>	
	        
			<div class="form-group">
				<label for="gname">그룹명</label>
				<select class="form-control" name="grpNo" id="grpNo"  value="${msgBox.grpNo}"  >
			        <option value="">그룹</option>
			        <c:forEach var="usrGrpVO" items="${usrGrpList}">
		                 <option value="${usrGrpVO.grpNo}"<c:if test="${usrGrpVO.grpNo == msgBox.grpNo}">selected="selected" </c:if> >${usrGrpVO.grpNm} </option>
		            </c:forEach>	
		        </select>
			</div>	
			<div class="form-group">
				<label for="notit">제목</label>
				<input type="text" class="form-control" id="subject" name="subject" value="${msgBox.subject}">
			</div>
			<div class="form-group">
				<label for="notarea">내용</label>
				<textarea id="contents1" name="contents1" cols="30" rows="3" value="${msgBox.contents1}" class="form-control">${msgBox.contents1}</textarea>
			</div>
	</div>
	<div class="panel-body btns text-center">
		<button class="btn btn-primary">등록/수정</button>
		<button class="btn btn-second" onclick="window.close();">취소</button>
	</div>
</div>
</form:form>
