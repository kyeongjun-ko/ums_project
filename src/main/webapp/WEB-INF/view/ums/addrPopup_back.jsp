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

function linkPage(pageNo) {
    $("#currentPageNo").val(pageNo);
    
    $("#addrListForm").attr({
        action : "/ums/addrListPopup",
        method : "post"
    }).submit();
}
//
</script> 
<form:form method="post" modelAttribute="searchAddrVO" id="addrListForm">
<form:hidden path="currentPageNo"/>
<input type="hidden" id="noticeID" name="noticeID" />
<input type="hidden" id="Tab" value="0"/>
<input type="hidden" id="catevalue" value="" />  
<div class="panel-default popup addpop">
	<div class="panel-heading">
		<h3 class="panel-title">주소록</h3>
	</div>
	<div class="panel-body inputs">
      	<div class="input-group">
      		<div class="input-group-btn">
      				        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">그룹<span class="caret"></span></button>
      				        <ul class="dropdown-menu">
      				          <li><a href="#">1</a></li>
      				          <li><a href="#">2</a></li>
      				          <li><a href="#">3</a></li>
      				        </ul>
      				     </div>
      		<input type="text" class="form-control" name="" placeholder="검색할 이름">
      	</div>
    	<button class="btn btn-second">검색</button>
	</div>
	<div class="panel-body">
		<div class="table-overflow">
			<table class="table">
				<tr>
					<th class="col01">
					    <label>
					      <input type="checkbox"  class="addrListCheckAll">  전체
					    </label>
					     
					    <!-- <input type="checkbox" class="checkbox"  onclick="CheckBox.All(this);" />-->
					</th>
					<th class="col02">순번</th>
					<th class="col03">성명</th>
					<th class="col04">문자번호</th>
					<th class="col05">음성번호</th>
					<th class="col06">팩스번호</th>
				</tr>
				
				<c:if test="${empty addrList}">
                    <tr>
                        <td colspan="12">No data</td>
                    </tr>
                </c:if>
				<c:if test="${! empty addrList}">
                	<c:forEach var="addrVO" items="${addrList}">
				<tr>
					<td><label for=""><input type="checkbox" name="checkbox"  value="${addrVO.addrNo}"  class="checkbox"></label></td>
					<td>${addrVO.addrNo}</td>
					<td>${addrVO.name}</td>
					<td>${addrVO.smsNo}</td>
					<td>${addrVO.vmsNo}</td>
					<td>${addrVO.fmsNo}</td>
				</tr>
					</c:forEach>     
                </c:if> 	
			</table>
		</div>
	</div>
	
	<div class="text-center">
		<ul class="pagination">
		<ui:pagination paginationInfo="${searchAddrVO}" type="image"
                   jsFunction="linkPage" /> 
		</ul>	
	</div>
	<div class="panel-body btns text-center">
		<button class="btn btn-primary"  onclick="AddressBook.Insert2SMS();">선택</button>
		<button class="btn btn-second">취소</button>
	</div>
</div>
</form:form>
