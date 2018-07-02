<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>    
 <script type="text/javascript">
 $(document).ready(function(){
	  //date search
       
       $("#btnSearch").click(function() {
       	$("#currentPageNo").val("1");
           
           $("#msgBoxListForm").attr({
               action : "/trs/msgBoxList",
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
            action : "/trs/msgBoxList",
            method : "post"
        }).submit();
    }
    
    
    function goModifyPopup(grpCd, grpNo, msgNo){
		var title = "MsgModifyPopUp";
        var status = "width=478, height=480, scrollbars=1, toolbar=no, menubar=no resizable=yes";
        poptarget = window.open("/trs/msgBoxUpdatePopup?grpCd=" + grpCd +"&grpNo=" + grpNo +"&msgNo=" + msgNo , title, status);
    }
    
    function goDelMsg(grpCd, grpNo, msgNo){
    	if(confirm("정말 삭제하시겠습니까?")){
            $.ajax({
                type : "POST",
                url : "/trs/deleteMsgBoxProc",                
                data : "grpCd="+ grpCd + "&grpNo=" + grpNo + "&msgNo=" + msgNo,
                success : function(json) {
                    if(json.result) {
                        alert(json.sucMsg);
                        document.location.reload();
                    } else {
                        alert(json.errMsg);
                    }
                },
                error : function(request, status, error) {
                    alert("delete Message fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
                }
            });
    	}
    }
    
    
 </script>

        <form:form method="post" modelAttribute="searchMsgBoxVO" id="msgBoxListForm">
        <form:hidden path="currentPageNo"/>

	<div class="col-md-9 tform sresult">
				<div class="col-md-12 tits">
					<h1>메시지 모음함</h1>
					<ol class="breadcrumb">
						<li><a href="#"><i class="fa fa-home"></i></a></li>
						<li><a href="#">전송관리</a></li>
						<li class="active">메시지 모음함</li> 
					</ol>
					<div class="hbg">
						<i class="fa fa-envelope odd"></i>
						<i class="fa fa-globe "></i>
					</div>
				</div>
				<div class="col-md-12 tcon">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">유형별 메시지 모음함</h3>
						</div>
						<div class="panel-body schbar">
							<div class="form-inline">
								
								<div class="form-group msgtype">
									<select name="grpCd" id="grpCd" value="${searchMsgBoxVO.grpCd}" class="form-control" style="width:110px;">
									<option value="">유형</option>
							        <c:forEach var="grpMgrVO" items="${grpCdList}">
						                 <option value="${grpMgrVO.code}"  <c:if test="${grpMgrVO.code == searchMsgBoxVO.grpCd}">selected="selected" </c:if>>${grpMgrVO.codeName} </option>
						            </c:forEach>
									</select>
								</div>
								<div class="pull-right">
									<div class="form-group pname">
										<select name="searchType" id="searchType" class="form-control" value="${searchMsgBoxVO.searchType}">
											<option value=""  <c:if test="${searchMsgBoxVO.searchType == ''}">selected="selected" </c:if> >전체</option>
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
						<table class="table dtable">
							<tr>
								<th style="width: 50px;">No.</th>
								<th style="width: 150px;" class="odd">메세지 유형</th>
								<th style="width: 150px;" >그룹명</th>
								<th style="width: 250px;" class="odd">제목</th>
								<th style="width: 250px;">내용</th>
								<th style="width: 200px;" class="odd">등록일</th>
								<th style="width: 70px;">수정</th>
								<th style="width: 70px;" class="odd">삭제</th>
							</tr>
							<c:choose>
								<c:when test="${! empty msgBoxList }">
									<c:forEach var="MsgBoxVO" items="${msgBoxList }">
										<tr>
											<td>${MsgBoxVO.rowNumber}.</td >
											<td class="odd">${MsgBoxVO.codeName }</td>
											<td >${MsgBoxVO.grpNm }</td>
											<td class="odd" style="text-align:left">${MsgBoxVO.subject }</td>
											<td style="text-align:left">${MsgBoxVO.contents1Temp }</td>
											<td class="odd">${MsgBoxVO.instDt }</td>
											<td ><a onclick="goModifyPopup( '${MsgBoxVO.grpCd}', '${MsgBoxVO.grpNo}', '${MsgBoxVO.msgNo}');" class="btn btn-primary btn-sm pop-modify">수정</a></td>
											<td class="odd"><a onclick="goDelMsg( '${MsgBoxVO.grpCd}', '${MsgBoxVO.grpNo}', '${MsgBoxVO.msgNo}');" class="btn btn-primary btn-sm">삭제</a></td>
										<tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="2">NODATA</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</table>
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
