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
	        action : "/ums/addrListPopup",
	        method : "post"
	    }).submit();
        
    });

	$("#tab01").css("display","block");
	$("#tab01-page").css("display","block");


	$("#selGrp").change(function(){
		if($("#selGrp").val() == '1'){
			$("#tab01").css("display","block");
			$("#tab01-page").css("display","block");
			$("#tab02").css("display","none");
			$("#tab02-page").css("display","none");
			$("#Tab").val("0");
			$("#MSGTYPE").val("SMS");
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

</script> 


<form:form method="post" modelAttribute="searchAddrVO" id="addrListForm" autocomplete="off">
<form:hidden path="currentPageNo"/>
<input type="hidden" id="Tab" value="0"/>
<input type="hidden" id="MSGTYPE" value="SMS"/>
<div class="panel-default popup addpop">
	<div class="panel-heading">
		<h3 class="panel-title">주소록</h3>
	</div>
	<div class="panel-body inputs">
	
      	<div class="input-group">
            
      		<select class="form-control" name="selCnt" value="${searchAddrVO.selCnt}" style="width: 20%;padding: 6px 0;margin-right: -1px;">
	        <option value="10" <c:if test="${10 == searchAddrVO.selCnt}">selected="selected" </c:if>>10건</option>
	        <option value="20" <c:if test="${20 == searchAddrVO.selCnt}">selected="selected" </c:if>>20건</option>
	        <option value="30" <c:if test="${30 == searchAddrVO.selCnt}">selected="selected" </c:if>>30건</option>
	        <option value="50" <c:if test="${50 == searchAddrVO.selCnt}">selected="selected" </c:if>>50건</option>
            </select>	
			            
	        <select class="form-control" name="grpNo" value="${searchAddrVO.grpNo}" style="width: 30%;padding: 6px 0;margin-right: -1px;">
	        <option value="">전체</option>
	        <c:forEach var="usrGrpVO" items="${usrGrpList}">
                 <option value="${usrGrpVO.grpNo}" <c:if test="${usrGrpVO.grpNo == searchAddrVO.grpNo}">selected="selected" </c:if>>${usrGrpVO.grpNm} </option>
            </c:forEach>	
            </select>	          
				      
      		<input type="text" class="form-control" name="name" placeholder="검색할 이름" value="${searchAddrVO.name}" style="width: 30%;">
      	</div>
    	<button class="btn btn-second" id="btnSearch" >검색</button>
	</div>
	<div class="panel-body">
		<div class="table-overflow"  style="overflow-y: scroll;">
			<table class="table" id="tab01">
				<tr class="odd">
					<th >
					    <label>
					      <input type="checkbox"   id="CheckAll1" onclick="CheckBox.All(this);  Util.CheckAll2(this);">  전체
					    </label>
					</th>
				<!-- 	<th class="odd">순번</th> -->
					<th >성명</th>
					<th class="odd">문자번호</th>
					<th >음성번호</th>
					<th class="odd">팩스번호</th>
				</tr>
				
				<c:if test="${empty addrList}">
                    <tr>
                        <td colspan="12">No data</td>
                    </tr>
                </c:if>
				<c:if test="${! empty addrList}">
                	<c:forEach var="addrVO" items="${addrList}">
				<tr class="odd">
					<td><label for=""><input type="checkbox" id="CHK-${addrVO.addrNo}" name="checkbox"  value="${addrVO.addrNo}"   onclick="Util.CheckObject(${addrVO.addrNo});" ></label></td>
				<!--	<td class="odd">${addrVO.addrNo}</td>  -->
					<td>${addrVO.name}</td>
					<td class="odd">${addrVO.smsNo}</td>
					<td>${addrVO.vmsNo}</td>
					<td class="odd">${addrVO.fmsNo}</td>					
					<input type="hidden" id="NAME-${addrVO.addrNo}" value="${addrVO.name}"/>
					<input type="hidden" id="SMS-${addrVO.addrNo}" value="${addrVO.smsNo}"/>
					<input type="hidden" id="VMS-${addrVO.addrNo}" value="${addrVO.vmsNo}"/>
					<input type="hidden" id="FMS-${addrVO.addrNo}" value="${addrVO.fmsNo}"/>
				</tr>
					</c:forEach>     
                </c:if> 	
			</table>
			
		</div>
	</div>
	
	<div class="panel-body text-center" id="tab01-page">
		<ul class="pagination">
		<ui:pagination paginationInfo="${searchAddrVO}" type="image"
                   jsFunction="linkPage" /> 
		</ul>	
	</div>
	<div class="panel-body btns text-center">
		<button class="btn btn-primary"  onclick="AddressBook.Insert2SMS2(); return false;">선택</button>
		<button class="btn btn-second" onclick="window.close();">취소</button>
	</div>
	
</div>
</form:form>

