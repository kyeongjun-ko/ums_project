<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>    
 <script type="text/javascript">
 $(document).ready(function(){
	  //date search

        $("#btnSearch").click(function() {
        	$("#currentPageNo").val("1");
            
            $("#applUsrListForm").attr({
                action : "/mgr/getApplUsr",
                method : "post"
            }).submit();
            
        });
    });
    
    function linkPage(pageNo) {
        $("#currentPageNo").val(pageNo);
        
        $("#applUsrListForm").attr({
            action : "/mgr/getApplUsr",
            method : "post"
        }).submit();
    }
 
    function goUsrApplProc(id) {
	        if(confirm('[ '+id+' ] 가입 승인하시겠습니까?')) {
	        	$("#applId").val(id);
	        	
	        	userLv = $('#userLv-'+id).val();
	        	//userLv-${UmsUserVO.userId}
				var param = $("#applUsrListForm").serialize();
				
	            $.ajax({
	                type : "POST",
	                url : "/mgr/updateApplUsr",                
	                data : param+'&userLv='+userLv,
	                success : function(json) {
	                	alert(json.sucMsg);
                        document.location.reload();
	                },
	                error : function(request, status, error) {
	                    alert("order list update fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
	                }
	            });
	        }
    }
 </script>

        <form:form method="post" modelAttribute="searchUmsUserVO" id="applUsrListForm" >
        <form:hidden path="currentPageNo"/>
        <input type="hidden" name="applId" id="applId"  />
	<div class="col-md-9 tform">
		<div class="col-md-12 tits">
			<h1>가입승인</h1>
			<ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-home"></i></a></li>
				<li><a href="#">마이페이지</a></li>
				<li class="active">가입승인</li>
			</ol>
			<div class="hbg">
				<i class="fa fa-envelope odd"></i>
				<i class="fa fa-globe "></i>
			</div>
		</div>
		<div class="col-md-12 tcon">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">가입승인 처리리스트</h3> 
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
						<th style="width: 100px;" class="odd">승인여부</th>
						<th style="width: 175px;"> 권한</th>
						<th style="width: 100px;" class="odd"> 승인</th>	
						<th style="width: 200px;">사용자권한</th>
						<th style="width: 200px;" class="odd">전화번호</th>
						<th style="width: 250px;">등록일시</th>
						
											
					</tr>
					
					<c:if test="${empty applUsrList}">
					<tr>
						<td colspan="2">NO DATA</td>
					</tr>
					</c:if>
					<c:if test="${! empty applUsrList}">
						
	                    <c:forEach var="UmsUserVO" items="${applUsrList}">
	                    <tr  style="overflow:scroll;">	
	                    	<fmt:parseDate var="instDt" value="${UmsUserVO.instDt}" pattern="yyyyMMddHHmmss" />
	                        <td>${UmsUserVO.rowNumber} </td>
							<td class="odd">${UmsUserVO.userId} </td>
							<td>${UmsUserVO.userNm} </td>
							<td  class="odd">${UmsUserVO.companyNm}</td>
							<td >${UmsUserVO.deptNm}</td>
							<td class="odd">${UmsUserVO.confYn} </td>
							<td>
							<select class="form-control textinput-selector" id="userLv-${UmsUserVO.userId}" name="userLv-${UmsUserVO.userId}"  value="${UmsUserVO.userLv}">
								 <c:if test="${! empty userLvList}">
			                         <c:forEach var="item" items="${userLvList}">
			                            <c:if test="${! empty item.codeName}">
			                            <option value="${item.code}" <c:if test="${UmsUserVO.userLv == item.code}"> selected="selected" </c:if> >${item.codeName}</option>
			                            </c:if>
			                         </c:forEach>
			                         	<!-- <option value="DIRECT">직접입력</option> -->
			                     </c:if>
							</select>
							</td>
							<td class="odd"><a href="javascript:goUsrApplProc('${UmsUserVO.userId}');" class="btn btn-primary btn-sm pop-modify">승인</a></td>
							<td >${UmsUserVO.userLvNm}</td>
							<td class="odd">${UmsUserVO.telNo1}</td>
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
		
