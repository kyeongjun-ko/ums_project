<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>    
 <script type="text/javascript">
 $(document).ready(function(){
	  //date search
	  if($("#srchRegDateStart").val() == "") {
		  $('#srchRegDateStart').val($.datepicker.formatDate('yy/mm/dd', new Date()));
      }
	  if($("#srchRegDateEnd").val() == "") {
		  $('#srchRegDateEnd').val($.datepicker.formatDate('yy/mm/dd', new Date()));
      }
      

    });
 

	 $("#btnSearch").click(function() {
		$("#currentPageNo").val("1");
	    
	    $("#transHisListForm").attr({
	        action : "/trs/transHisListProc",
	        method : "post"
	    }).submit();
	    
	});
	 
    function linkPage
    (pageNo) {
        $("#currentPageNo").val(pageNo);
        
        $("#transHisListForm").attr({
            action : "/trs/transHisListProc",
            method : "post"
        }).submit();
    }
 
 </script>

        <form:form method="post" action="/trs/transHisListProc" modelAttribute="searchTransHisVO" id="transHisListForm" >
        <form:hidden path="currentPageNo"/>
        <input type="hidden" id="msgId" name="msgId" />

	<div class="col-md-9 tform">
		<div class="col-md-12 tits">
			<h1>전송이력</h1>
			<ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-home"></i></a></li>
				<li><a href="#">전송관리</a></li>
				<li class="active">전송이력</li>
			</ol>
			<div class="hbg">
				<i class="fa fa-envelope odd"></i>
				<i class="fa fa-globe "></i>
			</div>
		</div>
		<div class="col-md-12 tcon">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">발송 이력 / 응답 / 확인 리스트</h3> 
				</div>
				<div class="panel-body schbar">
					<div  class="form-inline">
							
							<div class="form-group has-feedback">
								<label for="">전송일자</label>
								<input type="text" class="form-control datepicker" name="srchRegDateStart" id="srchRegDateStart" value="${searchTransHisVO.srchRegDateStart}">
								<i class="fa fa-calendar form-control-feedback"></i>
							</div>
							<em class="tilde">~</em>
							<div class="form-group has-feedback">
								<input type="text" class="form-control datepicker" name="srchRegDateEnd" id="srchRegDateEnd" value="${searchTransHisVO.srchRegDateEnd}">
								<i class="fa fa-calendar form-control-feedback"></i>
							</div>
							<div class="form-group uselect">
							<select name="suserId" id="suserId" class="form-control" value="${searchTransHisVO.userId}" style="width:100px;">
								<c:forEach var="commCodeVO" items="${userIdList}">
									<option value="${commCodeVO.code}" <c:if test="${commCodeVO.code == searchTransHisVO.userId}"> selected="selected" </c:if>>${commCodeVO.codeName} </option>
								</c:forEach>
							</select>
							</div>		
							<!-- <div class="form-group msgtype">
								<select name="msgType" id="msgType" value="${searchTransRsltVO.msgType}" class="form-control" style="width:110px;">
										<option value="">유형</option>
								        <c:forEach var="grpMgrVO" items="${grpCdList}">
								        	<c:if test="${grpMgrVO.code != '10'}">
							                 <option value="${grpMgrVO.code}"  <c:if test="${grpMgrVO.code == searchTransRsltVO.msgType}">selected="selected" </c:if>>${grpMgrVO.codeName} </option>
							           		</c:if>
							            </c:forEach>
								</select>
							</div>
							 -->
							<div class="form-group pname">
								<select name="searchType" id="searchType" class="form-control" value="${searchTransHisVO.searchType}">
									<option value=""  <c:if test="${searchTransHisVO.searchType == ''}">selected="selected" </c:if> >전체</option>
									<option value="01" <c:if test="${searchTransHisVO.searchType == '01'}">selected="selected" </c:if> >발신번호</option>
									<option value="02" <c:if test="${searchTransHisVO.searchType == '02'}">selected="selected" </c:if> >이름</option>
								</select>
							</div>
							<div class="form-group searchtit">	
								<input type="text" name="searchName" id="searchName" class="form-control" value="${searchTransHisVO.searchName }" placeholder="검색할 제목" >			
							</div>
							<div class="pull-right">
							<button id="btnSearch" href="" class="btn btn-second" title="검색">검색</button>
							</div>
					</div>
							
				</div>
				
                <div style="overflow-x: scroll;">
				<table class="table dtable"   style="min-width:2000px; margin-bottom:0;">
					<tr  style="overflow:scroll;">
						<th style="width: 50px;">No.</th>
						<th style="width: 130px;" class="odd">담당자명</th>
						<th style="width: 120px;">발신자번호</th>
						<th style="width: 80px;" class="odd">분류</th>
						<th style="width: 150px;" >발송일시</th>
						<th style="width: 200px;" class="odd">상품명</th>						
						<th style="width: 500px;">내용</th>
						<th style="width: 150px;" class="odd">확인일시(DM/FAX)</th>
						<th style="width: 150px;" >응답일시(DM/FAX)</th>
					</tr>
					
					<c:if test="${empty transHisList}">
					<tr>
						<td colspan="2">NO DATA</td>
					</tr>
					</c:if>
					<c:if test="${! empty transHisList}">
						
	                    <c:forEach var="transHisVO" items="${transHisList}">
	                    <tr  style="overflow:scroll;">	
	                    	<fmt:parseDate var="sendDate" value="${transHisVO.sendDate}" pattern="yyyyMMddHHmmss" />
	                        <fmt:parseDate var="confDate" value="${transHisVO.confDate}" pattern="yyyyMMddHHmmss" />
	                        <fmt:parseDate var="resDate" value="${transHisVO.resDate}" pattern="yyyyMMddHHmmss" />
	                        <td>${transHisVO.rowNumber} </td>
							<td class="odd">${transHisVO.userNm} </td>
							<td>${transHisVO.phoneNumber} </td>
							<td class="odd">${transHisVO.stype}</td>
							<td><fmt:formatDate value="${sendDate}" pattern="yyyy-MM-dd  HH:mm:ss" /> </td>
							<td style="text-align:left" class="odd">${transHisVO.name}</td>
							<td style="text-align:left">${transHisVO.contents}</td>
							<td class="odd"><fmt:formatDate value="${confDate}" pattern="yyyy-MM-dd  HH:mm:ss" /> </td>
							<td><fmt:formatDate value="${resDate}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
						</tr>		                        
	                    </c:forEach>    
	                </c:if>
				</table>
				</div>
				<!-- Paging -->
	            <div class="panel-body text-center" align=center>
	           	 <ul class="pagination">
	               <ui:pagination paginationInfo="${searchTransHisVO}" type="image"
	                   jsFunction="linkPage" />
	             </ul>
	           </div>
	    	</div>
	    </div>
</div>

        </form:form>
		
