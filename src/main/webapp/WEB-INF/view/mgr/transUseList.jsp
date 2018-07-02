<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>    
 <script type="text/javascript">
 $(document).ready(function(){
	//default search
		linkPage(1);
		$("#yyyymmList").change(function() {
			$("#currentPageNo").val(1);
	        var selym = $("#yyyymmList option:selected").val();
	        //alert(selym);
	        $("#yyyymm").val(selym);
	        $("#yyyymm1").val(selym);
	        //$("select[name=selectbox]").val();
            
            $("#mySvcForm").attr({
                action : "/mgr/transUseList",
                method : "post"
            }).submit();
	     });
    });
 
//search
 function linkPage(pageNo) {   
 	//page setting
     $("#currentPageNo").val(pageNo);
     var selym = $("#yyyymmList option:selected").val();
     //alert(selym);
     $("#yyyymm1").val(selym);
     var param = $("#transUseListForm").serialize();
     //alert(param);
     $.ajax({
         type : "POST",
         url : "/mgr/transUseListAjax",
         data : param,
         success: function(json) {
             renderTableList(json.totalCount, json.transUseList, json.pagingHtml);
         },
         error: function(request, status, error) {
         	alert("list search fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
         }
     });
 }

//table render
function renderTableList(count, list, pagingHtml) {
   //
  // $("#totalCount").text(count);
   if(list.length == 0) {
       var html = "";
       
       html += '<tr><th style="width: 150px;">전송일시</th>	<th style="width: 150px;">유형</th><th style="width: 150px;">제목</th><th style="width: 80px;">총건수</th><th style="width: 150px;">성공/실패</th></tr>';
       html += '<li >NO DATA	</li>';
       $("#tableList").html(html);
       //list check all clear
       //$("#availListCheckAll").attr("checked", false);
   } else {
       var html = "";
       var unCheckedCount = 0;
       html += '<tr><th style="width: 150px;">전송일시</th>	<th style="width: 150px;">유형</th><th style="width: 150px;">제목</th><th style="width: 80px;">총건수</th><th style="width: 150px;">성공/실패</th></tr>';
       $.each(list, function(index, item) {
    	   html += '<tr>';
    	   html += '<td>'+item.sendDate+'</td>';
    	   html += '<td class="text-left odd">'+item.msgType+'</td>';
    	   html += '<td class="text-left">'+item.subject+'</td>';
    	   html += '<td class="odd">'+item.totalCnt+'</td>';
    	   html += '<td>'+item.succCnt+'/'+item.failCnt+'</td>';
       });
       $("#tableList").html(html);
   }
   $("#paginate").html(pagingHtml);
}     
 
 </script>
<form:form method="post" modelAttribute="mySvcVO" id="mySvcForm">
	<div class="col-md-9 tform sresult">
		<div class="col-md-12 tits">
			<h1>사용내역</h1>
			<ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-home"></i></a></li>
				<li><a href="#">마이페이지</a></li>
				<li class="active">사용내역</li>
			</ol>
			<div class="hbg">
				<i class="fa fa-envelope odd"></i>
				<i class="fa fa-globe "></i>
			</div>
		</div>
		<div class="col-md-12 tcon">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">사용내역</h3>
				</div>
				<div class="panel-body schbar odd">
					<div class="form-inline">
						<div class="form-group">
							<select name="yyyymm" id="yyyymmList" class="form-control" value="${searchTransUseListVO.yyyymm}">
								<c:if test="${! empty yyyyMmList}">
		                         <c:forEach var="item" items="${yyyyMmList}">
		                            <c:if test="${! empty item.yyyymm}">
		                            <fmt:parseDate var="instDt" value="${item.yyyymm}" pattern="yyyyMM" />
		                            <option value="${item.yyyymm}" <c:if test="${searchTransUseListVO.yyyymm == item.yyyymm}"> selected="selected" </c:if> ><fmt:formatDate value="${instDt}" pattern="yyyy년 MM월" /></option>
		                            </c:if>
		                         </c:forEach>
		                    	</c:if>
							</select>
						</div>
					</div>
				</div>	
				<table class="table dtable">
					<tr>
						<th>발송유형</th>
						<th>발송건수</th>
						<th>성공건수</th>
						<th>실패건수</th>
					</tr>
					<tr>
						<td class="text-left">문자메시지(SMS)</td>
						<td class="odd">${mySvcVO.smsSendCnt}</td>
						<td>${mySvcVO.smsSuccCnt}</td>
						<td class="odd">${mySvcVO.smsfailCnt}</td>
					</tr>							
					<tr>
						<td class="text-left">장문메시지(LMS)</td>
						<td class="odd">${mySvcVO.lmsSendCnt}</td>
						<td>${mySvcVO.lmsSuccCnt}</td>
						<td class="odd">${mySvcVO.lmsfailCnt}</td>
					</tr>
					<tr>
						<td class="text-left">멀티미디어메시지(MMS)</td>
						<td class="odd">${mySvcVO.mmsSendCnt}</td>
						<td>${mySvcVO.mmsSuccCnt}</td>
						<td class="odd">${mySvcVO.mmsfailCnt}</td>
					</tr>	
					<tr>
						<td class="text-left">FAX메시지(FMS)</td>
						<td class="odd">${mySvcVO.fmsSendCnt}</td>
						<td>${mySvcVO.fmsSuccCnt}</td>
						<td class="odd">${mySvcVO.fmsfailCnt}</td>
					</tr>							
					<tr>
						<td class="text-left">음성메시지(VMS)</td>
						<td class="odd">${mySvcVO.vmsSendCnt}</td>
						<td>${mySvcVO.vmsSuccCnt}</td>
						<td class="odd">${mySvcVO.vmsfailCnt}</td>
					</tr>							
					<tr>
						<td class="text-left">설문조사</td>
						<td class="odd">${mySvcVO.vmsQrSendCnt}</td>
						<td>${mySvcVO.vmsQrSuccCnt}</td>
						<td class="odd">${mySvcVO.vmsQrfailCnt}</td>
					</tr>							
					<tr>
						<td class="text-left">SMART DM</td>
						<td class="odd">${mySvcVO.smartDmSendCnt}</td>
						<td>${mySvcVO.smartDmSuccCnt}</td>
						<td class="odd">${mySvcVO.smartDmfailCnt}</td>
					</tr>							
					<tr>
						<td class="text-left">SMART FAX</td>
						<td class="odd">${mySvcVO.smartFaxSendCnt}</td>
						<td>${mySvcVO.smartFaxSuccCnt}</td>
						<td class="odd">${mySvcVO.smartFaxfailCnt}</td>
					</tr>
					
					<tr>
						<td class="panel-footer">합계</td>
						<td class="odd">${mySvcVO.totSendCnt}</td>
						<td>${mySvcVO.totSuccCnt}</td>
						<td class="odd">${mySvcVO.totfailCnt}</td>
					</tr>
				</table>
			</div>
 </form:form>
			<form:form method="post" modelAttribute="searchTransUseListVO" id="transUseListForm"> 
			<form:hidden path="currentPageNo"/>
			<input type="hidden" name="yyyymm1" id="yyyymm1"/>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">전송내역</h3>
				</div>
				
				<table class="table dtable" id="tableList">
				</table>	
				
				<div class="panel-body text-center" >
					<ul class="pagination" id="paginate">
					<ui:pagination paginationInfo="${searchTransUseListVO}" type="image"
			                   jsFunction="linkPage" /> 
					</ul>	
				</div>
				 
			</div>
			</form:form>
			
		</div>
	</div>
  
