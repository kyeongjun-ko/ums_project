<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>    
 <script type="text/javascript">
 $(document).ready(function(){
	  //date search
	    setDatepicker("release_start_date", "release_end_date", "release_start_date", "release_end_date");
	    setDatepicker("create_start_date", "create_end_date", "create_start_date", "create_end_date");
        //selectCate('chkcate2');
        checkOne('arrCountry');
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
        
        $("#noticeListForm").attr({
            action : "/notice/noticeList",
            method : "post"
        }).submit();
    }
    
    function linkPage2() {
        if(!checkAny()) return;

        $("#noticeListForm").attr({
            action : "/srv/noticeUpdate",
            method : "post"
        }).submit();
    }
    
     function showDetail(noticeNo) {
        var title = "noticePopup";
        var status = "width=478, height=300, scrollbars=1, toolbar=no, menubar=no resizable=yes";
        poptarget = window.open("/srv/noticeDetailPopup?noticeNo=" +noticeNo , title, status);
    }
     
    
 
 </script>

        <form:form method="post" modelAttribute="searchNoticeVO" id="noticeListForm">
        <form:hidden path="currentPageNo"/>
        <form:hidden path="noticeCd" value="01"/>
        <div class="col-md-9 tform notice">
			<div class="col-md-12 tits">
				<h1>공지사항</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-home"></i></a></li>
					<li><a href="#">서비스소개</a></li>
					<li class="active">공지사항</li>
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
					<div class="panel-body schbar">
						<div class="form-inline">
							<div class="form-group">
								<select name="searchType" id="searchType" class="form-control" value="${searchNoticeVO.searchType}">
									<option value=""  <c:if test="${searchNoticeVO.searchType == ''}">selected="selected" </c:if> >전체</option>
									<option value="00" <c:if test="${searchNoticeVO.searchType == '00'}">selected="selected" </c:if>>제목+내용</option>
									<option value="01" <c:if test="${searchNoticeVO.searchType == '01'}">selected="selected" </c:if>>제목</option>
									<option value="02" <c:if test="${searchNoticeVO.searchType == '02'}">selected="selected" </c:if>>내용</option>
								</select>
							</div>
							<div class="form-group searchtit">	
								<input type="text" name="searchName" id="searchName"  class="form-control" placeholder="검색할 제목" value="${searchNoticeVO.searchName }">			
							</div>
							<button href="javascript:linkPage(1);" class="btn btn-second" title="검색" type="submit">검색</button>
						</div>
					</div>	
					<table class="table dtable">
						<tr>
							<th>번호</th>
							<th style="width:75%" class="odd">제목</th>
							<th>등록일시</th>
						</tr>
						<c:if test="${empty noticeList}">
	                        <tr>
	                            <td colspan="12">No data</td>
	                        </tr>
	                    </c:if>
						<c:if test="${! empty noticeList}">
	                       <c:forEach var="noticeVO" items="${noticeList}">
	                           <tr>
	                           <%-- <fmt:parseDate var="proc_date" value="${noticeVO.proc_date}" pattern="yyyy-MM-dd HH:mm:ss" /> --%>
	                           <fmt:parseDate var="instDt" value="${noticeVO.instDt}" pattern="yyyyMMddHHmmss" />
	                               <!-- 
	                               <td scope="row">
	                                   <input type=checkbox class="input_check" name="suspArID" value="${noticeVO.noticeNo}" onclick="javascript:checkOne2('suspArID');"></input>
	                               </td>
	                                -->                           
	                               <td scope="row">${noticeVO.noticeNo}</td>
	                               <td scope="row" class="odd"><a href="javascript:showDetail('${ noticeVO.noticeNo }');"  class="pop-notice">${noticeVO.noticeTitle}</a></td>
	                               <td scope="row"><fmt:formatDate value="${instDt}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
	                           </tr>
	                       </c:forEach>     
	                   </c:if>  
	                    	
					</table>
					<div  class="panel-body text-center">
						<ul class="pagination">
					
		               <ui:pagination paginationInfo="${searchNoticeVO}" type="image"
		                   jsFunction="linkPage" />
		                </ul>
		           </div>
					
				</div>
			</div>
		</div>
        
        
        </form:form>           
