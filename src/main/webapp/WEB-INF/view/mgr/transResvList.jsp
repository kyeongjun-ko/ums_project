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
          
          $("#transResvFrom").attr({
              action : "/trs/transResvListProc",
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
        
        $("#transResvFrom").attr({
        	action : "/trs/transResvListProc",
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
 
    function goDetailResvPopup(user,msg,type) {
        var title = "DetailResvPopup";
        var userId = "";
        var msgId = "";
        var msgType = "";
        this.userId = user;
        this.msgId = msg;
        this.msgType = type;
        var status = "width=800, height=600, scrollbars=1, toolbar=no, menubar=no resizable=yes";
        //alert("userId=" + this.userId +"&jobId" + this.jobId + "&msgId=" + this.msgId + "&msgType=" + this.msgType);
        poptarget = window.open("/trs/transResvDetPopup?userId=" + this.userId + "&msgId=" + this.msgId + "&msgType=" + this.msgType, title, status); 
    }
    
 </script>

        <form:form method="post" action="/trs/transResvListProc" modelAttribute="searchTransResvVO" id="transResvFrom">
        <form:hidden path="currentPageNo"/>
        <input type="hidden" id="noticeID" name="noticeID" />

        <input type="hidden" id="catevalue" value="" />         
         
       

	<div class="col-md-9 tform sresult">
		<div class="col-md-12 tits">
			<h1>????????????</h1>
			<ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-home"></i></a></li>
				<li><a href="#">????????????</a></li>
				<li class="active">????????????</li>
			</ol>
			<div class="hbg">
				<i class="fa fa-envelope odd"></i>
				<i class="fa fa-globe "></i>
			</div>
		</div>
		<div class="col-md-12 tcon">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">?????? ?????? ??????</h3>
				</div>
				<div class="panel-body schbar">
					<div class="form-inline">
						<div class="form-group msgtype">
							<select name="msgType" id="msgType" value="${searchTransResvVO.msgType}" class="form-control" style="width:100px;">
									<option value="">??????</option>
							        <c:forEach var="grpMgrVO" items="${grpCdList}">
							        	<c:if test="${grpMgrVO.code != '10'}">
						                 <option value="${grpMgrVO.code}"  <c:if test="${grpMgrVO.code == searchTransResvVO.msgType}">selected="selected" </c:if>>${grpMgrVO.codeName} </option>
						           		</c:if>
						            </c:forEach>
							</select>
						</div>		
						<div class="form-group has-feedback">
							<label for="">????????????</label>
							<input type="text" class="form-control datepicker" name="srchRegDateStart" id="srchRegDateStart" value="${searchTransResvVO.srchRegDateStart}">
							<i class="fa fa-calendar form-control-feedback"></i>
						</div>
						<em class="tilde">~</em>
						<div class="form-group has-feedback">
							<input type="text" class="form-control datepicker" name="srchRegDateEnd" id="srchRegDateEnd" value="${searchTransResvVO.srchRegDateEnd}">
							<i class="fa fa-calendar form-control-feedback"></i>
						</div>
						<div class="form-group uselect">
							<select name="suserId" id="suserId" class="form-control" value="${searchTransResvVO.userId}" style="width:100px;">
								<c:forEach var="commCodeVO" items="${userIdList}">
									<option value="${commCodeVO.code}" <c:if test="${commCodeVO.code == searchTransResvVO.userId}"> selected="selected" </c:if>>${commCodeVO.codeName} </option>
								</c:forEach>
							</select>
						</div>	
						<div class="form-group pname">
							<select name="searchType" id="searchType" class="form-control" value="${searchTransResvVO.searchType}">
								<option value=""  <c:if test="${searchTransResvVO.searchType == ''}">selected="selected" </c:if> >??????</option>
								<option value="01" <c:if test="${searchTransResvVO.searchType == '01'}">selected="selected" </c:if> >??????</option>
							</select>
						</div>			
						<div class="form-group searchtit">	
							<input type="text" class="form-control" placeholder="????????? ??????" name="searchName" value="${searchTransResvVO.searchName}">			
						</div>
						<div class="pull-right">
						<button id="btnSearch"  class="btn btn-second" title="??????" type="submit">??????</button>
						</div>
					</div>
				</div>	
				<table class="table dtable">
					<tr>
						<th style="width: 50px;">No.</th>
						<th style="width: 250px;" class="odd">??????</th>
						<th style="width: 200px;">????????????</th>
						<th style="width: 250px;" class="odd">??????</th>
						<th style="width: 100px;">?????????</th>
						<th style="width: 120px;" class="odd">??????/??????</th>
						<th style="width: 100px;">?????????</th>
					</tr>
					
					<c:choose>
						<c:when test="${! empty transResvList}">
							<c:forEach var="TransResvVO" items="${transResvList}">
						 <tr>
						 	<fmt:parseDate var="sendDate" value="${TransResvVO.sendDate}" pattern="yyyyMMddHHmmss" />
			                <td >${TransResvVO.rowNumber   }</td>
							<!-- 
							<td class="odd" onclick="goDetailResvPopup('${searchTransResvVO.userId}','${TransResvVO.msgId}','${searchTransResvVO.msgType }')">${TransResvVO.subject    }</td>
							 -->
							<td class="odd" style="text-align:left">${TransResvVO.subject    }</td>
							<td ><fmt:formatDate value="${sendDate}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
							<td class="odd">${TransResvVO.msgType    }</td>
							<td >${TransResvVO.destCount    } ??? </td>
							<td class="odd">0 / 0</td>
							<td >0%</td>
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
				<div class="panel-body text-center" align=center>
	           	 <ul class="pagination">
	               <ui:pagination paginationInfo="${searchTransResvVO}" type="image" jsFunction="linkPage" />
	             </ul>
	           </div>
			</div>
		</div>
	</div>
 </form:form>  
