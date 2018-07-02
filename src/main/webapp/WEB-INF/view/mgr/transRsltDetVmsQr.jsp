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
        
        $("#noticeListForm").attr({
            action : "/notice/noticeList",
            method : "post"
        }).submit();
    }
    
    function linkPage2() {
        if(!checkAny()) return;

        $("#noticeListForm").attr({
            action : "/notice/noticeUpdate",
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

        <form:form method="post" modelAttribute="searchNoticeVO" id="noticeListForm">
        <form:hidden path="currentPageNo"/>
        <input type="hidden" id="noticeID" name="noticeID" />

        <input type="hidden" id="catevalue" value="" />         
         
       

	<div class="col-md-9 tform">
		<div class="col-md-12 tits">
			<h1>전송결과</h1>
			<ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-home"></i></a></li>
				<li><a href="#">전송관리</a></li>
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
					<h3 class="panel-title">전송 정보</h3>
				</div>
				<div class="panel-body scon">
					<div class="panel-body col-md-4">
						<div class="mob2">
							<img src="./images/mob2.png" alt="모바일 배경" class="responsive-img">
						</div>
						<div class="mobtxt">
							보내실 내용을 입력해 주세요.
							80Byte (한글 1글자 2Byte ) 이상의 
							문자는 LMS로 처리 및 과금 됩니다.
						</div>
					</div>							
					<div class="panel-body col-md-8 dlist">
						<dl>
							<dt>제목</dt><dd>전송결과 리스트 테스트1</dd>
							<dt>전송일시</dt><dd>2014 08.26 11:36</dd>
							<dt>메시지 유형</dt><dd>설문조사</dd>
							<dt>수신처 수</dt><dd>10명</dd>
							<dt>회신번호</dt><dd>010xxxxxxxx</dd>
						</dl>
					</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">전송결과</h3>
				</div>
				<div class="panel panel-body scon">
					<div class="panel-body col-md-4 sgraph">
						<dl>
							<dt>성공</dt>
							<dd>
								<div class="progress">
								  <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%;">
								  	<span>100%</span>
								  </div>
								</div>
							</dd>									
							<dt>전송중</dt>
							<dd>
								<div class="progress">
								  <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width: 50%;">
								  	<span>50%</span>
								  </div>
								</div>
							</dd>
							<dt>실패</dt>									
							<dd>
								<div class="progress">
								  <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: 20%;">
								  	<span>20%</span>
								  </div>
								</div>
							</dd>
						</dl>
					</div>
					<table class="table dtable col-md-8">
						<tr>
							<th>분류</th>
							<th>건수</th>
							<th>설명</th>
						</tr>
						<tr>
							<td class="odd">성공(답변 있음)</td>
							<td>1</td>
							<td class="odd">메시지의 발송 절차가 성공적으로 완료 되었습니다.</td>
						</tr>							
						<tr>
							<td class="odd">성공(답변 있음)</td>
							<td>1</td>
							<td class="odd">메시지의 발송 절차가 성공적으로 완료 되었습니다.</td>
						</tr>							
						<tr>
							<td class="odd">전송중</td>
							<td>0</td>
							<td class="odd">전송 후 결과값에 대한 응답을 기다리고 있습니다.</td>
						</tr>							
						<tr>
							<td class="odd">통화중</td>
							<td>0</td>
							<td class="odd">수신자가 통화중이서서 메시지 발송이 실패했습니다.</td>
						</tr>							
						<tr>
							<td class="odd">무응답</td>
							<td>0</td>
							<td class="odd">수신자가 응답하지 않아 메시지 발송이 실패했습니다.</td>
						</tr>							
						<tr>
							<td class="odd">전송실패</td>
							<td>0</td>
							<td class="odd">메시지 발송에 실패하였습니다.</td>
						</tr>							
						<tr>
							<td class="odd">결번</td>
							<td>0</td>
							<td class="odd">가입된 번호가 없거나 메시지를 발송할 수 없는 번호입니다.</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">답변 통계</h3>
				</div>
				<table class="table dtable">
					<tr>
						<th>설문번호</th>
						<th>답변구분</th>
						<th>예문1</th>
						<th>예문2</th>
						<th>예문3</th>
						<th>예문4</th>
						<th>예문5</th>
						<th>예문6</th>
						<th>예문7</th>
						<th>예문8</th>
						<th>예문9</th>
					</tr>
					<tr>
						<td>1번</td>
						<td class="odd">객관식</td>
						<td>1</td>
						<td class="odd">-</td>
						<td>-</td>
						<td class="odd">-</td>	
						<td>-</td>
						<td class="odd">-</td>	
						<td>-</td>
						<td class="odd">-</td>
						<td>-</td>
					</tr>			
					<tr>
						<td>2번</td>
						<td class="odd">객관식</td>
						<td>1</td>
						<td class="odd">-</td>
						<td>-</td>
						<td class="odd">-</td>	
						<td>-</td>
						<td class="odd">-</td>	
						<td>-</td>
						<td class="odd">-</td>
						<td>-</td>
					</tr>				
				</table>
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
						<th>결과</th>
					</tr>
					<tr>
						<td>1</td>
						<td class="odd">테스터</td>
						<td>010xxxxxxxx</td>
						<td class="odd">2014/08/24 08:55</td>
						<td>성공</td>
					</tr>								
					<tr>
						<td>2</td>
						<td class="odd">테스터</td>
						<td>010xxxxxxxx</td>
						<td class="odd">2014/08/24 08:55</td>
						<td>성공</td>
					</tr>								
					<tr>
						<td>3</td>
						<td class="odd">테스터</td>
						<td>010xxxxxxxx</td>
						<td class="odd">2014/08/24 08:55</td>
						<td>성공</td>
					</tr>								
					<tr>
						<td>4</td>
						<td class="odd">테스터</td>
						<td>010xxxxxxxx</td>
						<td class="odd">2014/08/24 08:55</td>
						<td>성공</td>
					</tr>							
					<tr>
						<td>5</td>
						<td class="odd">테스터</td>
						<td>010xxxxxxxx</td>
						<td class="odd">2014/08/24 08:55</td>
						<td>성공</td>
					</tr>				
				</table>
			</div>
		</div>
	</div>
 </form:form>  
