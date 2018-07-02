<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>    
 <script type="text/javascript">
 $(document).ready(function(){
	  //date search
       
       $("#btnSearch").click(function() {
       	$("#currentPageNo").val("1");
           
           $("#msgBoxListForm").attr({
               action : "/smart/smartRightModList",
               method : "post"
           }).submit();
           
       });
       $(function() {
       	$( ".datepicker" ).datepicker();
       });
   });

    
    function linkPage(pageNo) {
        $("#currentPageNo").val(pageNo);
        
        $("#msgBoxListForm").attr({
            action : "/smart/smartMod",
            method : "post"
        }).submit();
    }
    
    
    function showDetail(suspArID) {
    	$("#noticeID").val(suspArID);
        $("#noticeListForm").attr({
            action : "/notice/noticeDetail?suspArID="+suspArID,
            method : "get"
        }).submit();
    }
    function goRightAddPopup() {
	     var title = "RighAddPopup";
	     
	     var status = "width=478, height=480, scrollbars=1, toolbar=no, menubar=no resizable=yes";
	     poptarget = window.open("/smart/smartRightAddPopUp"  , title, status);
	 }
	
    
    function goModifyPopup(msgNo, rightNo, dept){
		var title = "smartRightModifyPopUp";
        
        var status = "width=478, height=480, scrollbars=1, toolbar=no, menubar=no resizable=yes";
        
        var frm = document.createElement("form");
        var msgno = document.createElement("input");
        msgno.setAttribute("name", "msgNo");
        msgno.setAttribute("value", msgNo);
        frm.appendChild(msgno);
        
        var rightno = document.createElement("input");
        rightno.setAttribute("name", "rightNo");
        rightno.setAttribute("value", rightNo);
        frm.appendChild(rightno);
        
        var deptIn = document.createElement("input");
        deptIn.setAttribute("name", "dept");
        deptIn.setAttribute("value", dept);
        frm.appendChild(deptIn);
        
        
        //var status = "width=478, height=430, scrollbars=1, toolbar=no, menubar=no resizable=yes";
        poptarget = window.open("/smart/smartRightModifyPopUp?msgNo=" + msgNo +"&rightNo=" + rightNo +"&dept=" + dept , title, status);
        
        //window.open('', 'viewer', status);
        //frm.action = "/smart/smartRightModifyPopUp";
        //frm.target = "viewer";
        //frm.method = "post";
        //frm.submit();
    }
    
    function goDeleteDm(grpNo, rightNo, dept){
    	if(confirm("정말 삭제하시겠습니까?")){
            $.ajax({
                type : "POST",
                url : "/smart/deleteSmartDmRightProc",                
                data : "grpNo="+ grpNo + "&rightNo=" + rightNo + "&dept=" + dept,
                success : function(json) {
                    if(json.result) {
                        alert(json.sucMsg);
                        document.location.reload();
                    } else {
                        alert(json.errMsg);
                    }
                },
                error : function(request, status, error) {
                    alert("delete SmartDM fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
                }
            });
    	}
    }
    
 </script>

        <form:form method="post" modelAttribute="searchMsgBoxVO" id="msgBoxListForm">
        <form:hidden path="currentPageNo"/>

	<div class="col-md-9 tform sresult">
		<div class="col-md-12 tits">
			<h1>SMART DM 사용자 권한 관리</h1>
			<ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-home"></i></a></li>
				<li><a href="#">스마트 발송</a></li>
				<li class="active">SMART DM 사용자 권한 관리</li> 
			</ol>
			<div class="hbg">
				<i class="fa fa-envelope odd"></i>
				<i class="fa fa-globe "></i>
			</div>
		</div>
		<div class="col-md-12 tcon">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">SMART DM 사용자 권한 목록</h3>
				</div>
				<div class="panel-body schbar">
					<div class="form-inline">
						<div class="form-group msgtype">
							<select name="grpNo" id="grpNo" value="${searchMsgBoxVO.grpCd}" class="form-control" style="width:110px;">
							<option value="">유형</option>
					        <c:forEach var="usrGrpVO" items="${usrGrpList}">
				                 <option value="${usrGrpVO.grpNo}"  <c:if test="${usrGrpVO.grpNo == searchMsgBoxVO.grpNo}">selected="selected" </c:if>>${usrGrpVO.grpNm} </option>
				            </c:forEach>
							</select>
						</div>
						<div class="pull-right">
							<div class="form-group pname">
								<select name="searchType" id="searchType" class="form-control" value="${searchMsgBoxVO.searchType}">
									<%-- <option value=""  <c:if test="${searchMsgBoxVO.searchType == ''}">selected="selected" </c:if> >전체</option> --%>
									<option value="01" <c:if test="${searchMsgBoxVO.searchType == '01'}">selected="selected" </c:if> >제목</option>
								</select>
							</div>	
							<div class="form-group searchtit">	
								<input type="text" class="form-control" placeholder="검색할 제목" name="searchName" value="${searchMsgBoxVO.searchName}">			
							</div>
							<button id="btnSearch" class="btn btn-second" title="검색" type="button">검색</button>
						</div>
					</div>
				</div>
				<div style="overflow-x: scroll;">
					<table class="table dtable" style="min-width:800px; margin-bottom:0;">
						<tr  style="overflow:scroll;">
							<th style="width: 50px;">No.</th>
							<th style="width: 100px;" class="odd">그룹명</th>
							<th style="width: 150px;" >메세지 번호</th>
							<th style="width: 250px;" class="odd">제목</th>
							<th style="width: 100px;" >권한</th>
							<th style="width: 150px;" class="odd">권한시작</th>
							<th style="width: 150px;" >권한종료</th>
							<th style="width: 150px;" class="odd">등록일</th>
							<th style="width: 70px;" >등록/수정</th>
							<th style="width: 70px;" class="odd">삭제</th>
						</tr>
						<c:choose>
							<c:when test="${! empty smartRightModList }">
								<c:forEach var="SmartModListVO" items="${smartRightModList }">
									<tr style="overflow:scroll;">
										<fmt:parseDate var="rtStrtDt" value="${SmartModListVO.rtStrtDt}" pattern="yyyyMMdd" />
										<fmt:parseDate var="rtEndDt" value="${SmartModListVO.rtEndDt}" pattern="yyyyMMdd" />
										<fmt:parseDate var="instDt" value="${SmartModListVO.instDt}" pattern="yyyyMMdd" />
										<td>${SmartModListVO.rowNumber}.</td >
										<td class="odd">${SmartModListVO.grpNm }</td>
										<td >${SmartModListVO.msgNo }</td>
										<td class="odd" style="text-align:left">${SmartModListVO.subject }</td>
										<td >${SmartModListVO.deptNm }</td>
										<td class="odd"> <fmt:formatDate value="${rtStrtDt}" pattern="yyyy-MM-dd" /></td>
										<td ><fmt:formatDate value="${rtEndDt}" pattern="yyyy-MM-dd" /></td>
										<td class="odd"><fmt:formatDate value="${instDt}" pattern="yyyy-MM-dd" /></td>
										<td ><a onclick="goModifyPopup('${SmartModListVO.msgNo}', '${SmartModListVO.rightNo}', '${SmartModListVO.dept}');" class="btn btn-primary btn-sm pop-modify">등록/수정</a></td>
										<td class="odd"><a onclick="goDeleteDm('${SmartModListVO.grpNo}', '${SmartModListVO.rightNo}', '${SmartModListVO.dept}');" class="btn btn-primary btn-sm">삭제</a></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="2">NODATA</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</table>
				</div>
				<div class="panel-body text-center" align=center>
	           	 <ul class="pagination">
	               <ui:pagination paginationInfo="${searchMsgBoxVO}" type="image"
	                   jsFunction="linkPage" />
	             </ul>
	           </div>
			</div>
		</div>
	</div>
 </form:form>  
