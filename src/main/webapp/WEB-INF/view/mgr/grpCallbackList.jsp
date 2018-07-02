<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>    
 <script type="text/javascript">
 $(document).ready(function(){
	  //date search

      $("#btnSearch").click(function() {
      	$("#currentPageNo").val("1");
          
          $("#applCallbackListForm").attr({
              action : "/mgr/getGrpCallback",
              method : "post"
          }).submit();
            
      });
	  
      var $searchName = $("#searchName");
      var $searchType = $("#searchType");
      $searchName.keyup(function(key){
    	  $searchType.val("01");
	  });
      $searchType.change(function(){
    	  $searchName.val("");
      });
	  
    });
    
    function linkPage(pageNo) {
        $("#currentPageNo").val(pageNo);
        
        $("#applCallbackListForm").attr({
            action : "/mgr/getGrpCallback",
            method : "post"
        }).submit();
    }
 
    function callbackCheck(type) {
    	if(type != "NULL") {
    		var title = "callbackPopup";
    		var status;
    		
    		if(type == 'I') {
    			status = "width=600, height=580, scrollbars=1, toolbar=no, menubar=no resizable=yes";	
    		}
    		
    		if(type == 'D') {
    			status = "width=478, height=300, scrollbars=1, toolbar=no, menubar=no resizable=yes";
    		}
    		
    		window.open("/user/callbackPopup?type=" + type, title, status);		
    	} 
    }
 </script>

        <form:form method="post" modelAttribute="searchUmsUserVO" id="applCallbackListForm" >
        <form:hidden path="currentPageNo"/>
        <input type="hidden" name="applId" id="applId"  />
	<div class="col-md-9 tform">
		<div class="col-md-12 tits">
			<h1>발신번호관리</h1>
			<ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-home"></i></a></li>
				<li><a href="#">마이페이지</a></li>
				<li class="active">발신번호관리</li>
			</ol>
			<div class="hbg">
				<i class="fa fa-envelope odd"></i>
				<i class="fa fa-globe "></i>
			</div>
		</div>
		<div class="col-md-12 tcon">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">발신번호관리 리스트</h3> 
				</div>
				<div class="panel-body schbar">
					<div  class="form-inline">
						<a href="javascript:callbackCheck('I');" class="btn btn-primary pop-modify">추가</a>
						<a href="javascript:callbackCheck('D');" class="btn btn-second">삭제</a>
						<div class="pull-right">
							<div class="form-group pname">
								<select name="searchType" id="searchType" class="form-control" value="${searchUmsUserVO.searchType}">
									<option value=""  <c:if test="${searchUmsUserVO.searchType == ''}">selected="selected" </c:if> >전체</option>
									<option value="01" <c:if test="${searchUmsUserVO.searchType == '01'}">selected="selected" </c:if> >발신번호</option>
								</select>
							</div>
							<div class="form-group searchtit">	
								<input type="text" name="searchName" id="searchName" class="form-control" value="${searchUmsUserVO.searchName }" placeholder="검색할 발신번호">			
							</div>
							<button id="btnSearch" href="" class="btn btn-second" title="검색">검색</button>
						</div>
					</div>
				</div>
				
                <div style="overflow-x: scroll;">
				<table class="table dtable">
					<tr  style="overflow:scroll;">
						<th>No.</th>
						<th class="odd">발신번호</th>
						<th>승인여부</th>
						<th class="odd">등록일시</th>
						<th>수정일시</th>
					</tr>
					
					<c:if test="${empty grpCallbackList}">
					<tr>
						<td colspan="2">NO DATA</td>
					</tr>
					</c:if>
					<c:if test="${! empty grpCallbackList}">
						
	                    <c:forEach var="CallBackVO" items="${grpCallbackList}">
	                    <tr style="overflow:scroll;">	
	                    	<fmt:parseDate var="instDt" value="${CallBackVO.instDt}" pattern="yyyyMMddHHmmss" />
	                    	<fmt:parseDate var="updtDt" value="${CallBackVO.updtDt}" pattern="yyyyMMddHHmmss" />
	                        <td>${CallBackVO.rowNumber} </td>
							<td class="odd">${CallBackVO.callbackNo}</td>
							<td>${CallBackVO.status} </td>
							<td class="odd"><fmt:formatDate value="${instDt}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
							<td><fmt:formatDate value="${updtDt}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
						</tr>		                        
	                    </c:forEach>    
	                </c:if>
				</table>
				</div>
				<!-- Paging -->
	            <div class="panel-body text-center" align=center>
	           	 <ul class="pagination">
	               <ui:pagination paginationInfo="${searchUmsUserVO}" type="image"
	                   jsFunction="linkPage" />
	             </ul>
	           </div>
	    	</div>
	    </div>
</div>

        </form:form>
		
