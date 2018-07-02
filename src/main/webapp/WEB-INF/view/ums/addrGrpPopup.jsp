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
	
	$("#btnSearch").click(function() {
		$("#currentPageNo").val(1);
	    
	    $("#addrListForm").attr({
	        action : "/ums/addrGrpListPopup",
	        method : "post"
	    }).submit();
        
    });
});

function linkPage(pageNo) {
    $("#currentPageNo").val(pageNo);
    
    $("#addrListForm").attr({
        action : "/ums/addrGrpListPopup",
        method : "post"
    }).submit();
}
</script> 
<form:form method="post" modelAttribute="searchGrpVO" id="addrListForm" autocomplete="off">
<form:hidden path="currentPageNo"/>
<input type="hidden" id="Tab" value="0"/>
<input type="hidden" id="MSGTYPE" value="GRP"/>
<div class="panel-default popup addpop">
	<div class="panel-heading">
		<h3 class="panel-title">그룹 주소록</h3>
	</div>
	<div class="panel-body inputs">
      	<div class="input-group">
      		<select class="form-control" name="selCnt" value="${searchGrpVO.selCnt}" style="width: 20%;padding: 6px 0;margin-right: -1px;">
	        <option value="10" <c:if test="${10 == searchGrpVO.selCnt}">selected="selected" </c:if>>10건</option>
	        <option value="20" <c:if test="${20 == searchGrpVO.selCnt}">selected="selected" </c:if>>20건</option>
	        <option value="30" <c:if test="${30 == searchGrpVO.selCnt}">selected="selected" </c:if>>30건</option>
	        <option value="50" <c:if test="${50 == searchGrpVO.selCnt}">selected="selected" </c:if>>50건</option>
            </select>	
	        <select class="form-control" name="grpNo" value="${searchGrpVO.grpNo}" style="width: 30%;padding: 6px 0;margin-right: -1px;">
	        <option value="">전체</option>
	        <c:forEach var="usrGrpVO" items="${usrGrpList}">
                 <option value="${usrGrpVO.grpNo}" <c:if test="${usrGrpVO.grpNo == searchGrpVO.grpNo}">selected="selected" </c:if>>${usrGrpVO.grpNm} </option>
            </c:forEach>	
            </select>	          
      		<input type="text" class="form-control" name="searchName" placeholder="검색할 이름" value="${searchGrpVO.searchName}" style="width: 30%;">
      	</div>
    	<button class="btn btn-second" id="btnSearch" >검색</button>
	</div>
	<div class="panel-body">
		<div class="table-overflow"  style="overflow-y: scroll;">
			
			<table class="table" >
				<tr class="odd">
					<th >
					     <label>
					      <input type="checkbox"   id="CheckAll1" onclick="CheckBox.All(this);  Util.CheckAll2(this);">  전체
					    </label>
					</th>
				<!-- 	<th class="odd">순번</th> -->
					<th style="width: 130px;"><spring:message code="groupName"/></th>
					<th class="odd" style="width: 230px;"><spring:message code="groupCount"/></th>
				</tr>
				<c:if test="${empty grpList}">
                    <tr>
                        <td colspan="3">No data</td>
                    </tr>
                </c:if>
				<c:if test="${! empty grpList}">
                	<c:forEach var="grpVO" items="${grpList}">
				<tr class="odd">
					<td><label for=""><input type="checkbox" id="CHK-${grpVO.grpNo}" name="checkbox"  value="${grpVO.grpNo}"   onclick="Util.CheckObject(${grpVO.grpNo});" ></label></td>
					<td>${grpVO.grpNm}</td>
					<td class="odd">${grpVO.grpCnt}</td>
					 				
					<input type="hidden" id="NAME-${grpVO.grpNo}" value="${grpVO.grpNm}"/>
					<input type="hidden" id="GRPNO-${grpVO.grpNo}" value="${grpVO.grpNo}"/>
					<input type="hidden" id="GRPCNT-${grpVO.grpNo}" value="${grpVO.grpCnt}"/>
					
				</tr>
					</c:forEach>     
                </c:if> 	
			</table>
		</div>
	</div>
	
	<div class="panel-body text-center" >
		<ul class="pagination">
		<ui:pagination paginationInfo="${searchGrpVO}" type="image"
                   jsFunction="linkPage" /> 
		</ul>	
	</div>
	<div class="panel-body btns text-center">
		<button class="btn btn-primary"  onclick="AddressBook.Insert2SMS2(); return false;"><spring:message code="select"/></button>
		<button class="btn btn-second" onclick="window.close();">취소</button>
	</div>
	
</div>
</form:form>

