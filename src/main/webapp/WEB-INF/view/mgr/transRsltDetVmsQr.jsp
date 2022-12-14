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
					<h3 class="panel-title">?????? ??????</h3>
				</div>
				<div class="panel-body scon">
					<div class="panel-body col-md-4">
						<div class="mob2">
							<img src="./images/mob2.png" alt="????????? ??????" class="responsive-img">
						</div>
						<div class="mobtxt">
							????????? ????????? ????????? ?????????.
							80Byte (?????? 1?????? 2Byte ) ????????? 
							????????? LMS??? ?????? ??? ?????? ?????????.
						</div>
					</div>							
					<div class="panel-body col-md-8 dlist">
						<dl>
							<dt>??????</dt><dd>???????????? ????????? ?????????1</dd>
							<dt>????????????</dt><dd>2014 08.26 11:36</dd>
							<dt>????????? ??????</dt><dd>????????????</dd>
							<dt>????????? ???</dt><dd>10???</dd>
							<dt>????????????</dt><dd>010xxxxxxxx</dd>
						</dl>
					</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">????????????</h3>
				</div>
				<div class="panel panel-body scon">
					<div class="panel-body col-md-4 sgraph">
						<dl>
							<dt>??????</dt>
							<dd>
								<div class="progress">
								  <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%;">
								  	<span>100%</span>
								  </div>
								</div>
							</dd>									
							<dt>?????????</dt>
							<dd>
								<div class="progress">
								  <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width: 50%;">
								  	<span>50%</span>
								  </div>
								</div>
							</dd>
							<dt>??????</dt>									
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
							<th>??????</th>
							<th>??????</th>
							<th>??????</th>
						</tr>
						<tr>
							<td class="odd">??????(?????? ??????)</td>
							<td>1</td>
							<td class="odd">???????????? ?????? ????????? ??????????????? ?????? ???????????????.</td>
						</tr>							
						<tr>
							<td class="odd">??????(?????? ??????)</td>
							<td>1</td>
							<td class="odd">???????????? ?????? ????????? ??????????????? ?????? ???????????????.</td>
						</tr>							
						<tr>
							<td class="odd">?????????</td>
							<td>0</td>
							<td class="odd">?????? ??? ???????????? ?????? ????????? ???????????? ????????????.</td>
						</tr>							
						<tr>
							<td class="odd">?????????</td>
							<td>0</td>
							<td class="odd">???????????? ?????????????????? ????????? ????????? ??????????????????.</td>
						</tr>							
						<tr>
							<td class="odd">?????????</td>
							<td>0</td>
							<td class="odd">???????????? ???????????? ?????? ????????? ????????? ??????????????????.</td>
						</tr>							
						<tr>
							<td class="odd">????????????</td>
							<td>0</td>
							<td class="odd">????????? ????????? ?????????????????????.</td>
						</tr>							
						<tr>
							<td class="odd">??????</td>
							<td>0</td>
							<td class="odd">????????? ????????? ????????? ???????????? ????????? ??? ?????? ???????????????.</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">?????? ??????</h3>
				</div>
				<table class="table dtable">
					<tr>
						<th>????????????</th>
						<th>????????????</th>
						<th>??????1</th>
						<th>??????2</th>
						<th>??????3</th>
						<th>??????4</th>
						<th>??????5</th>
						<th>??????6</th>
						<th>??????7</th>
						<th>??????8</th>
						<th>??????9</th>
					</tr>
					<tr>
						<td>1???</td>
						<td class="odd">?????????</td>
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
						<td>2???</td>
						<td class="odd">?????????</td>
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
					<h3 class="panel-title">?????? ??????</h3>
				</div>
				<table class="table dtable">
					<tr>
						<th>No.</th>
						<th>????????????</th>
						<th>????????????</th>
						<th>????????????</th>
						<th>??????</th>
					</tr>
					<tr>
						<td>1</td>
						<td class="odd">?????????</td>
						<td>010xxxxxxxx</td>
						<td class="odd">2014/08/24 08:55</td>
						<td>??????</td>
					</tr>								
					<tr>
						<td>2</td>
						<td class="odd">?????????</td>
						<td>010xxxxxxxx</td>
						<td class="odd">2014/08/24 08:55</td>
						<td>??????</td>
					</tr>								
					<tr>
						<td>3</td>
						<td class="odd">?????????</td>
						<td>010xxxxxxxx</td>
						<td class="odd">2014/08/24 08:55</td>
						<td>??????</td>
					</tr>								
					<tr>
						<td>4</td>
						<td class="odd">?????????</td>
						<td>010xxxxxxxx</td>
						<td class="odd">2014/08/24 08:55</td>
						<td>??????</td>
					</tr>							
					<tr>
						<td>5</td>
						<td class="odd">?????????</td>
						<td>010xxxxxxxx</td>
						<td class="odd">2014/08/24 08:55</td>
						<td>??????</td>
					</tr>				
				</table>
			</div>
		</div>
	</div>
 </form:form>  
