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
<form:form method="post" modelAttribute="addrVO" id="addrListForm" autocomplete="off"  action="/mgr/usrAddrWriteProcessPopup">
<form:hidden path="currentPageNo"/>
<form:hidden path="regFg" />
<form:hidden path="addrNo" />

<input type="hidden" id="noticeID" name="noticeID" />
<input type="hidden" id="Tab" value="0"/>
<input type="hidden" id="catevalue" value="" />  
<div class="panel-default popup notice">
		<div class="panel-heading">
			<h3 class="panel-title">주소 추가 수정</h3>
		</div>
		<div class="panel-body inputs">
			
				<div class="form-group">
					<label for="msgsel">그룹선택</label>
					<select name="grpNo" id="grpNo" class="form-control" value="${addrVO.grpNo}" >
						<c:forEach var="usrGrpVO" items="${usrGrpList}">
			                 <option value="${usrGrpVO.grpNo}" <c:if test="${usrGrpVO.grpNo == addrVO.grpNo}">selected="selected" </c:if>>${usrGrpVO.grpNm} </option>
			            </c:forEach>
					</select>
				</div>			
				<div class="form-group">
					<label for="aname">이름</label>
					<input type="text" class="form-control" name="name"  id="name" value="${addrVO.name}"  placeholder="tester" required>
				</div>	
				<div class="form-group">
					<label for="txtnum">문자번호</label>
					<input type="tel" class="form-control" name="smsNo" id="smsNo" value="${addrVO.smsNo}"  placeholder="0100000000" required>
				</div>				
				<div class="form-group">
					<label for="vcnum">음성번호</label>
					<input type="tel" class="form-control" name="vmsNo" id="vmsNo" value="${addrVO.vmsNo}"  placeholder="0100000000" required>
				</div>				
				<div class="form-group">
					<label for="fxnum">팩스번호</label>
					<input type="tel" class="form-control" name="fmsNo" id="fmsNo" value="${addrVO.fmsNo}"  placeholder="0100000000" required>
				</div>
				<div class="form-group">
					<label for="esp">비고</label>
					<input type="text" class="form-control" name="note" id="note" value="${addrVO.note}"  placeholder="관심고객">
				</div>	
		</div>
		<div class="panel-body btns text-center">
			<button class="btn btn-primary">등록/수정</button>
			<button class="btn btn-second" onclick="window.close();">취소</button>
		</div>
	</div>
</form:form>
