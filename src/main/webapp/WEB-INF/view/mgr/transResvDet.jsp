<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>    
 <script type="text/javascript">
 $(document).ready(function(){
	 // TO DO: 
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

    function selectCate(chkcate) {
        if(!$("#cate_depth1").val()) {
        	$("#cate_depth2").html("");
            $("#cate_depth2").append("<option value='' label='All' />");
            return false;
        }
        $.ajax({  
                type: "GET",  
                url: "/notice/noticeCategoryList",  
                data: "cate_depth1=" + $("#cate_depth1").val(),  
                success: function(response){  
                    $("#cate_depth2").html("");
                    $("#cate_depth2").append("<option value='' label='All' />");
                    for(var i=0; i<response.length; i++) {
                        $("#cate_depth2").append("<option value='" + response[i] + "' label='" + response[i] + "' />");    
                    }
                    
                    if(chkcate == "chkcate2") {
                        $("#cate_depth2").val($("#catevalue").val()).prop("selected", true);
                    }
                 },  
                 error: function(e){  
                     alert('Error: ' + e);  
                 }  
            });  
    }   
    
    function linkPage(pageNo) {
        $("#currentPageNo").val(pageNo);
        
        $("#searchResvDetForm").attr({
            action : "/trs/transResvDetPopup",
            method : "get"
        }).submit();
    }
    
    function showDetail(suspArID) {
    	$("#noticeID").val(suspArID);
        $("#noticeListForm").attr({
            action : "/notice/noticeDetail?suspArID="+suspArID,
            method : "get"
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
 
 </script>
        <form:form method="post" modelAttribute="searchResvDetVO" id="searchResvDetForm">
        <form:hidden path="currentPageNo"/>
        <input type="hidden" id="userId" 	name="userId" 	value='${searchResvDetVO.userId}'/>
        <input type="hidden" id="msgId"	 	name="msgId" 	value='${searchResvDetVO.msgId}'/>
        <input type="hidden" id="msgType" 	name="msgType" 	value='${searchResvDetVO.msgType}'/>

	<div class="panel-default popup notice">
		<div class="col-md-12 tits">
			<h1>예약 내역</h1>
			<ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-home"></i></a></li>
				<li><a href="#">전송관리</a></li>
				<li class="active">예약 내역</li>
			</ol>
			<div class="hbg">
				<i class="fa fa-envelope odd"></i>
				<i class="fa fa-globe "></i>
			</div>
		</div>
		<div class="col-md-12 tcon">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">전송 정보</h3>
				</div>
				<div class="panel-body scon">
					<div class="panel-body col-md-4">
						<div class="mob2">
							<img src="/images/mob2.png" alt="모바일 배경" class="responsive-img">
						</div>
						<div id="mobtxt" class="mobtxt" style="overflow:auto; height:150px;" >
							<textarea style="border: 0; background-color: transparent; height:120px; width: 100%;">${resvDetTop.contents}</textarea>
						</div>
					</div>							
					<div class="panel-body col-md-8 dlist">
						<dl>
							<fmt:parseDate var="sendDate" value="${resvDetTop.sendDate    }" pattern="yyyyMMddHHmmss" />
							<dt>제목</dt><dd><c:if test="${resvDetTop.subject != ''}">${rsltDetTop.subject}</c:if>
							<c:if test="${resvDetTop.subject == ''}">제목없음</c:if></dd>
							<dt>전송일시</dt><dd><fmt:formatDate value="${sendDate}" pattern="yyyy-MM-dd  HH:mm:ss" /></dd>
							<dt>메시지 유형</dt><dd>${resvDetTop.msgType    }</dd>
							<dt>수신처 수</dt><dd>${resvDetTop.destCount    }명</dd>
							<dt>회신번호</dt><dd>${resvDetTop.callback    }</dd>
						</dl>
					</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">전송 상세</h3>
				</div>
				<table class="table dtable">
					<tr>
						<th>No.</th>
						<th>수신자명</th>
						<th>수신번호</th>
						<th>전송일시</th>
						<th>예약일시</th>
					</tr>
					<c:choose>
						<c:when test="${! empty resvDetBottomlist}">
							<c:forEach var="resvDetBottomVO" items="${resvDetBottomlist}">
						 <tr>	
							<fmt:parseDate var="sendDate" value="${resvDetBottomVO.sendDate}" pattern="yyyyMMddHHmmss" />
			                <td>${resvDetBottomVO.rowNumber   }</td>
			                <td class="odd">${resvDetBottomVO.destName   }</td>
			                <td>${resvDetBottomVO.phoneNumber   }</td>
							<td class="odd"><fmt:formatDate value="${sendDate}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
							<td>${resvDetBottomVO.resvDate    }</td>
						</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td>NO DATA</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</div>
			<%-- <div class="panel-body text-center" align=center>
				<ul class="pagination">
					<ui:pagination paginationInfo="${searchResvDetVO}" type="image"	jsFunction="linkPage" />
				</ul>
			</div> --%>
		</div>
	</div>
 </form:form>  
