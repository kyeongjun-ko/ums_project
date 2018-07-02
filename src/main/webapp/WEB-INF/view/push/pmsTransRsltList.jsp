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
       
       $("#btnSearch").click(function() {
       	$("#currentPageNo").val("1");
           
           $("#TransRsltListForm").attr({
               action : "/push/transRsltListProc",
               method : "post"
           }).submit();
           
       });
   });

    function checkAll(id){ 
        $("input[name=" + id + "]").prop("checked", $("input[name=" + id + "All]").is(":checked"));
    }

    function checkOne(id){
        var allcheck = true;
        $("input[name=" + id + "]").each(function() {
            if(!$(this).is(":checked")) {
                allcheck = false;
            }
            
            if(allcheck) {
                 $("input[name=" + id + "All]").prop("checked", true);
            }else {
                 $("input[name=" + id + "All]").prop("checked", false);
            }
        });
    }
    
    function checkOne2(id){
        var allcheck = true;
        $("input[name=" + id + "]").each(function() {
            if(!$(this).is(":checked")) {
                allcheck = false;
            }
            
            if(allcheck) {
                 $("input[name=allChk]").prop("checked", true);
            }else {
                 $("input[name=allChk]").prop("checked", false);
            }
        });
    }    
    
    function checkAll2(){ 
        $("input[name=suspArID]").prop("checked", $("input[name=allChk]").is(":checked"));
    }

    function checkAny(){
        var bChecked = false;
        $("input[name=suspArID]").each(function() {
            if(this.checked == true) bChecked = true;  
        });
        
        if(!bChecked) {
            alert("Please select a checkbox");
            return false;
        } 
        return true;
    }

    function linkPage(pageNo) {
        $("#currentPageNo").val(pageNo);
        
        $("#TransRsltListForm").attr({
            action : "/push/transRsltListProc",
            method : "post"
        }).submit();
    }
    
    function excelDownload() {
    	$("#csvName").val(trim($("#csvName").val()));
        if(!$("#csvName").val()) {
            alert("You must enter \"CSV Name\".");
            return;
        }
        
        var special = /['~!@#$%^&*|\\\'\";:\/?]/gi;
        if(special.test($("#csvName").val()) == true) {
            alert("Special characters can not be used.");
            return;
        }        
        
        var f = document.forms["noticeListForm"];
        f.action = "/notice/excelDownload";
        f.method = "post";
        f.submit();
        downloadView();
    }
    
    function downloadView() {
    	$("#csvName").val("");
        $("#downloadPopUp").toggle();
    }    
	function goDetailRsltPopup(user,job,msg,type) {
        var title = "DetailRsltPopup";
        var userId = "";
        var jobId = "";
        var msgId = ""; 
        var msgType = "";
        this.userId = user;
        this.jobId = job;
        this.msgId = msg;
        this.msgType = type;
        var status = "width=800, height=600, scrollbars=1, toolbar=no, menubar=no resizable=yes";
        //alert("userId=" + this.userId +"&jobId" + this.jobId + "&msgId=" + this.msgId + "&msgType=" + this.msgType);
        poptarget = window.open("/push/transRsltDetPopUp?userId=" + this.userId +"&jobId=" + this.jobId + "&msgId=" + this.msgId + "&msgType=" + this.msgType, title, status); 
    }
 
 </script>

        <form:form method="post" action="/push/transRsltListProc" modelAttribute="searchTransRsltVO" id="TransRsltListForm">
        <form:hidden path="currentPageNo"/>
        <input type="hidden" id="noticeID" name="noticeID" />

        <input type="hidden" id="catevalue" value="" />         
         
       

	<div class="col-md-9 tform sresult">
		<div class="col-md-12 tits">
			<h1>전송결과</h1>
			<ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-home"></i></a></li>
				<li><a href="#">PUSH발송</a></li>
				<li class="active">전송결과</li>
			</ol>
			<div class="hbg">
				<i class="fa fa-envelope odd"></i>
				<i class="fa fa-globe "></i>
			</div>
		</div>
		<div class="col-md-12 tcon">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">전송 결과 리스트</h3>  
				</div>
				<div class="panel-body schbar">
					<div class="form-inline">
						<div class="form-group msgtype">
							<select name="msgType" id="msgType" value="${searchTransRsltVO.msgType}" class="form-control" style="width:110px;">
									<option value="">유형</option>
									<option value="01"
									<c:if test="${'01' == searchTransRsltVO.msgType}"> selected="selected" </c:if>
									>TEXT</option>
									<option value="02"
									<c:if test="${'02' == searchTransRsltVO.msgType}"> selected="selected" </c:if>
									>HTML</option>
									<option value="03"
									<c:if test="${'03' == searchTransRsltVO.msgType}"> selected="selected" </c:if>
									>IMAGE</option>
							</select>
						</div>		
						<div class="form-group has-feedback">
							<input type="text" class="form-control datepicker" name="srchRegDateStart" id="srchRegDateStart" value="${searchTransRsltVO.srchRegDateStart}">
							<i class="fa fa-calendar form-control-feedback"></i>
						</div>
						<em class="tilde">~</em>
						<div class="form-group has-feedback">
							<input type="text" class="form-control datepicker" name="srchRegDateEnd" id="srchRegDateEnd" value="${searchTransRsltVO.srchRegDateEnd}">
							<i class="fa fa-calendar form-control-feedback"></i>
						</div>
						
						
						<div class="form-group uselect">
						
							<select name="suserId" id="suserId" class="form-control" value="${searchTransRsltVO.userId}" style="width:100px;">
								<c:forEach var="commCodeVO" items="${userIdList}">
									<option value="${commCodeVO.code}" 
										<c:if test="${commCodeVO.code == searchTransRsltVO.userId}"> selected="selected" </c:if>
										>${commCodeVO.codeName} 
									</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group pname">
							<select name="searchType" id="searchType" class="form-control" value="${searchTransRsltVO.searchType}">
								<option value=""  <c:if test="${searchTransRsltVO.searchType == ''}">selected="selected" </c:if> >전체</option>
								<option value="01" <c:if test="${searchTransRsltVO.searchType == '01'}">selected="selected" </c:if> >제목</option>
							</select>
						</div>			
						<div class="form-group searchtit">	
							<input type="text" class="form-control" placeholder="검색할 제목" name="srchContent" value="${searchTransRsltVO.srchContent}">			
						</div>
						<div class="pull-right">
						<button id="btnSearch"  class="btn btn-second" title="검색" type="submit">검색</button>
						</div>
					</div>
				</div>	
				<div style="overflow-x: scroll;">

				<table class="table dtable"  style="min-width:1500px;">  
					<tr>
						<th style="width: 30px;">No.</th>
						<th style="width: 50px;" class="odd">유형</th>
						<th style="width: 100px;">수신처</th>
						<th style="width: 100px;" class="odd">발신처</th>
						<th style="width: 100px;">제목</th>
						<th style="width: 250px;" class="odd">내용</th>
						<th style="width: 130px;">전송일시</th>				
						<th style="width: 130px;" class="odd">수신일시</th>
						<th style="width: 100px;">사용자ID</th>
						<th style="width: 70px;" class="odd">성공/실패</th>
						<th style="width: 50px;">파일크기</th>
					</tr>		
					
					<c:choose> 
						<c:when test="${! empty transRsltList}">
							<c:forEach var="TransRsltVO" items="${transRsltList}">
						 <tr>	
						 	<td >${TransRsltVO.rowNumber   }</td>
						 	<td  class="odd">${TransRsltVO.msgType    }</td>
						 	<td >${TransRsltVO.phoneNo   }</td>
						 	<td  class="odd">${TransRsltVO.senderNo    }</td>
						 	<td style="text-align:left" onclick="goDetailRsltPopup('${searchTransRsltVO.userId}','${TransRsltVO.msgId}','${TransRsltVO.msgId}','${TransRsltVO.msgType }')">${TransRsltVO.msgTitle    }</td>
						 	<td  class="odd">${TransRsltVO.msgContent    }</td>
						 	
							<fmt:parseDate var="reserveDt" value="${TransRsltVO.reserveDt}" pattern="yyyyMMddHHmmss" />
							<td ><fmt:formatDate value="${reserveDt}" pattern="yyyy-MM-dd  HH:mm:ss" /> </td>
							<fmt:parseDate var="updDt" value="${TransRsltVO.updDt}" pattern="yyyyMMddHHmmss" />
							<td class="odd"><fmt:formatDate value="${updDt}" pattern="yyyy-MM-dd  HH:mm:ss" /> </td>
							<td >${TransRsltVO.userId    }</td>

							<td class="odd">${TransRsltVO.stateCd    }</td>
							<td >${TransRsltVO.fileSize } </td>
						</tr>
					</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="2">NO DATA</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>  
				</div>
				<div class="panel-body text-center" align=center>
	           	 <ul class="pagination">
	               <ui:pagination paginationInfo="${searchTransRsltVO}" type="image" jsFunction="linkPage" />
	             </ul>
	           </div>
			</div>
		</div>
	</div>
 </form:form>  
