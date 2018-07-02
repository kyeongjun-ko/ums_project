<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>    
 <script type="text/javascript">
 $(document).ready(function(){
	  //date search

        $("#btnSearch").click(function() {
        	$("#currentPageNo").val("1");
            
            $("#applCallbackListForm").attr({
                action : "/mgr/getApplCallback",
                method : "post"
            }).submit();
            
        });
    });
    
    function linkPage(pageNo) {
        $("#currentPageNo").val(pageNo);
        
        $("#applCallbackListForm").attr({
            action : "/mgr/getApplCallback",
            method : "post"
        }).submit();
    }
 
    function goUsrApplProc(status, callbackNo, userId) {
    	var text = status == 'A' ? '등록 승인하시겠습니까?':'반려 처리하시겠습니까?';
    	if(confirm("[" + callbackNo + "] " + text)) {
    		$.ajax({
                type : "POST",
                url : "/mgr/updateApplCallback",                
                data : {
                	status 		: status,
                	callbackNo 	: callbackNo,
                	userId		: userId
                },
                success : function(rs) {
                	if(rs) {
                		alert("작업 완료하였습니다.");
                        document.location.reload(); 	 
                        /* document.location.href = document.location.href; */  
                	} else {
                		alert("작업 실패하였습니다.");
                	}
                },
                error : function(request, status, error) {
                    alert("order list update fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
                }
            });
    	}
    }
 </script>

        <form:form method="post" modelAttribute="searchUmsUserVO" id="applCallbackListForm" >
        <form:hidden path="currentPageNo"/>
        <input type="hidden" name="applId" id="applId"  />
	<div class="col-md-9 tform">
		<div class="col-md-12 tits">
			<h1>발신번호승인</h1>
			<ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-home"></i></a></li>
				<li><a href="#">마이페이지</a></li>
				<li class="active">발신번호승인</li>
			</ol>
			<div class="hbg">
				<i class="fa fa-envelope odd"></i>
				<i class="fa fa-globe "></i>
			</div>
		</div>
		<div class="col-md-12 tcon">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">발신번호승인 처리리스트</h3> 
				</div>
				<div class="panel-body schbar">
					<div  class="form-inline">
					
							
							<select class="form-control textinput-selector" id="company" name="company" value="${searchUmsUserVO.company}" >
								 
								 <c:if test="${! empty comCdList}">
								 	 <option value="">전체</option>
								 	 <c:forEach var="item" items="${comCdList}">
			                            <c:if test="${! empty item.codeName}">
			                            <option value="${item.code}" <c:if test="${searchUmsUserVO.company == item.code}"> selected="selected" </c:if> >${item.codeName}</option>
			                            </c:if>
			                         </c:forEach>
			                     </c:if>
							</select>
							
							
							<div class="form-group pname">
								<select name="searchType" id="searchType" class="form-control" value="${searchUmsUserVO.searchType}">
									<option value=""  <c:if test="${searchUmsUserVO.searchType == ''}">selected="selected" </c:if> >전체</option>
									<option value="01" <c:if test="${searchUmsUserVO.searchType == '01'}">selected="selected" </c:if> >이름</option>
									<option value="02" <c:if test="${searchUmsUserVO.searchType == '02'}">selected="selected" </c:if> >ID</option>
								</select>
							</div>
							<div class="form-group searchtit">	
								<input type="text" name="searchName" id="searchName" class="form-control" value="${searchUmsUserVO.searchName }" placeholder="검색할 제목">			
							</div>
							<button id="btnSearch" href="" class="btn btn-second" title="검색">검색</button>
					</div>
				</div>
				
                <div style="overflow-x: scroll;">
				<table class="table dtable"   style="min-width:1500px; margin-bottom:0;">
					<tr  style="overflow:scroll;">
						<th style="width: 50px;">No.</th>
						<th style="width: 120px;" class="odd">사용자ID</th>
						<th style="width: 120px;">사용자명</th>
						<th style="width: 120px;"  class="odd">회사명</th>
						<th style="width: 120px;">부서명</th>						
						<th style="width: 200px;" class="odd">발신번호</th>
						<th style="width: 100px;">승인여부</th>
						<th style="width: 100px;" class="odd"> 승인</th>	
						<th style="width: 250px;">등록일시</th>
					</tr>
					
					<c:if test="${empty applCallbackList}">
					<tr>
						<td colspan="2">NO DATA</td>
					</tr>
					</c:if>
					<c:if test="${! empty applCallbackList}">
						
	                    <c:forEach var="CallBackVO" items="${applCallbackList}">
	                    <tr  style="overflow:scroll;">	
	                    	<fmt:parseDate var="instDt" value="${CallBackVO.instDt}" pattern="yyyyMMddHHmmss" />
	                        <td>${CallBackVO.rowNumber} </td>
							<td class="odd">${CallBackVO.userId} </td>
							<td>${CallBackVO.userNm} </td>
							<td  class="odd">${CallBackVO.company}</td>
							<td>${CallBackVO.dept}</td>
							<td class="odd">${CallBackVO.callbackNo}</td>
							<td>${CallBackVO.status}</td>
							<td class="odd">
								<a href="javascript:goUsrApplProc('A','${CallBackVO.callbackNo}','${CallBackVO.userId}');" class="btn btn-primary btn-sm pop-modify">승인</a>
								<a href="javascript:goUsrApplProc('C','${CallBackVO.callbackNo}','${CallBackVO.userId}');" class="btn btn-primary btn-sm pop-modify">반려</a>
							</td>
							<td><fmt:formatDate value="${instDt}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
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
		
