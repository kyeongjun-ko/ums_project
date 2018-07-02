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

<form:form method="post" modelAttribute="grpVO" id="grpListForm" autocomplete="off"  action="/mgr/grpMgrWriteProcessPopup">
<form:hidden path="regFg" />
<form:hidden path="grpNo" />
<input type="hidden" id="Tab" value="0"/>
<input type="hidden" id="catevalue" value="" />  
<div class="panel-default popup notice">
		<div class="panel-heading">
			<h3 class="panel-title">그룹 추가 수정</h3>
		</div>
		<div class="panel-body inputs"> 
				<div class="form-group">
					<label for="tysel">유형선택</label>
					<select name="grpCd" value="${grpVO.grpCd}" id="grpCd" class="form-control">
				        <c:forEach var="grpMgrVO" items="${grpCdList}">
			                 <option value="${grpMgrVO.code}"  <c:if test="${grpMgrVO.code == grpVO.grpCd}">selected="selected" </c:if>>${grpMgrVO.codeName} </option>
			            </c:forEach>
					</select>
				</div>			
				<div class="form-group">
					<label for="gnum">그룹번호</label>
					<input type="number" name="grpNo" id="grpNo" value="${grpVO.grpNo}" class="form-control" disabled>
				</div>	
				<div class="form-group">
					<label for="gtit">그룹명</label>
					<input type="text" class="form-control" name="grpNm" id="grpNm" value="${grpVO.grpNm}" placeholder="그룹명" required>
				</div>
				<div class="form-group">
					<label for="gesp">비고</label>
					<input type="text" class="form-control" name="note" id="note" value="${grpVO.note}" placeholder="비고">
				</div> 
		</div>
		<div class="panel-body btns text-center">
			<button class="btn btn-primary">등록/수정</button>
			<button class="btn btn-second" onclick="window.close();">취소</button>
		</div>
	</div>
</form:form>
