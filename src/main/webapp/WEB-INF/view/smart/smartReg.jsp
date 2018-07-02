<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>  
<%@ page import = "java.util.Date"%>
<%@ page import = "java.text.SimpleDateFormat"%>
<%@ page import = "java.util.ArrayList"%> 
 
<script type="text/javascript">
var SendLimit = true;
var msgBoxList = new Array();
var clickCount = 0;
$(document).ready(function() {
	//default search
	linkPage(1);
	$("#smartdmarea").show();
	$("#smartfaxarea").hide();
	
	$("#msgSav").on("click", function(){
        if( $(this).is(":checked") ){
            $("#MSGSAVE").val("Y");
        } else {
            $("#MSGSAVE").val("N");
        }            
    });
  
    $("#btnSearch").click(function() {
    	//page setting
        $("#currentPageNo").val(1);
        var param = $("#msgBoxListForm").serialize();
        $.ajax({
            type : "POST",
            url : "/ums/msgBoxListPagingAjax",
            data : param,
            success: function(json) {
                renderTableList(json.totalCount, json.msgBoxList, json.pagingHtml);
            },
            error: function(request, status, error) {
            	alert("list search fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
            }
        });
        
    });
    
    var select = "<option>그룹</option>"; 
    $("#grpNo").change(function() {

        var selgrp = $("#grpNo option:selected").val();
        //alert(selgrp);
        $("#MSGGRPNO").val(selgrp);
        //$("select[name=selectbox]").val();
     });
    $("#grpCd").change(function() {

        //$("select[name=selectbox]").val();
		if($("#grpCd").val() == "") { // select의 value가 ""이면, "선택" 메뉴만 보여줌.
			$("#grpNo").find("option").remove().end().append(select);
		} else {
			comboChange($(this).val());
		}
     });
    
	function comboChange() {
		$.ajax({
			type:"post",
			url:"/ums/grpNoListAjax",
			datatype: "json",
			data: $("#msgBoxListForm").serialize(),
			success: function(data) {
				if(data.usrGrpList != "") {
					$("#grpNo").find("option").remove().end().append(select);
					 var html = "";
			        $.each(data.usrGrpList, function(index, item) {
			            html += '<option value="'+ item.grpNo +'" <c:if test="${'+ item.grpNo +'== searchMsgBoxVO.grpNo}">selected="selected" </c:if> >'+item.grpNm  +'</option>' ;
			        });
				    $("#grpNo").find("option").remove().end().append(html);
				    
				    $("#mGrpNo").find("option").remove().end().append(select);
					 var html = "";
			        $.each(data.usrGrpList, function(index, item) {
			            html += '<option value="'+ item.grpNo +'" >'+item.grpNm  +'</option>' ;
			        });
				    $("#mGrpNo").find("option").remove().end().append(html);
				} else {
					$("#grpNo").find("option").remove().end().append("<option value=''>그룹</option>");
					$("#mGrpNo").find("option").remove().end().append("<option value=''>그룹</option>");
					return;
				}
			},
			error: function(x, o, e) {
				var msg = "페이지 호출 중 에러 발생 \n" + x.status + " : " + o + " : " + e; 
				alert(msg);
			}				
		});
	}
	
	// 페이지가 로딩될 때 'Loading 이미지'를 숨긴다.
	$('#viewLoading').hide();

	// ajax 실행 및 완료시 'Loading 이미지'의 동작을 컨트롤하자.
	$('#viewLoading')
	.ajaxStart(function()
	{
		// 로딩이미지의 위치 및 크기조절	
		$('#viewLoading').css('position', 'absolute');
		$('#viewLoading').css('left', 800);
		$('#viewLoading').css('top', 300);
		$('#viewLoading').css('width', 100);
		$('#viewLoading').css('height', 100);

		//$(this).show();
		$(this).fadeIn(500);
	})
	.ajaxStop(function()
	{
		//$(this).hide();
		$(this).fadeOut(500);
	});
	
	$(function(){
		$(":radio").click(function(){
			$("label").css("font-weight","");
			$("label[for='"+$(this).attr("id")+"']").css("font-weight","bold");
		});
		$("label").click(function(){
			$("label").css("font-weight","");
			$(this).css("font-weight","bold");	
			$("radio[for='"+$(this).attr("id")+"']").attr("checked", true);
		});
	});
	
	$(function() {
        $("input[name='smartdmfax']").click(function() {
            if ($(this).val() == 'Y') {
            	$("#smartdmarea").show();
            	$("#smartfaxarea").hide();
            	$("#grpCd").val("07").attr("selected", "selected");
            	comboChange($(this).val());
            	$("#MSGTYPE").val("SMARTDM");
            } else {
            	$("#smartdmarea").hide();
            	$("#smartfaxarea").show();
            	$("#grpCd").val("08").attr("selected", "selected");
            	comboChange($(this).val());
            	$("#MSGTYPE").val("SMARTFAX");
            }
        });
    });
	
	$("#imageselect").change(function() {
		if($(this).val() == "imageselect1") { 
			$('#img-canvas1').css('background-color','#888888');
			$('#img-canvas1').css('border-left','1px solid #333333');
			$('#img-canvas1').css('border-right','1px solid #333333');
		} else if($(this).val() == 'imageselect2') {
			$('#img-canvas2').css('background-color','#888888');
			$('#img-canvas2').css('border-left','1px solid #333333');
			$('#img-canvas2').css('border-right','1px solid #333333');
		} else if($(this).val() == 'imageselect3') {
			$('#img-canvas3').css('background-color','#888888');
			$('#img-canvas3').css('border-left','1px solid #333333');
			$('#img-canvas3').css('border-right','1px solid #333333');
		}
    });

});

//search
function linkPage(pageNo) {   
	//page setting
    $("#currentPageNo").val(pageNo);
    var param = $("#msgBoxListForm").serialize();
    $.ajax({
        type : "POST",
        url : "/ums/msgBoxListPagingAjax",
        data : param,
        success: function(json) {
            renderTableList(json.totalCount, json.msgBoxList, json.pagingHtml);
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
        var html = '<li style="padding:11px 0 7px 10px; height:30px; font-size:15px;" >  NO DATA	</li>';
        $("#tableList").html(html);
        //list check all clear
        //$("#availListCheckAll").attr("checked", false);
    } else {
        var html = "";
        var unCheckedCount = 0;
        $.each(list, function(index, item) {
            html += '<li title=""><a onclick="" href="javascript:;">' + item.subject + '</a></li>';
            html += '<input type="hidden" id="MSG'+ item.msgNo +'" value="' +item.contents1 + '" /> </li>';
        });
        $("#tableList").html(html);
    }
    $("#paginate").html(pagingHtml);
} 

//image upload popup 
function goImageUploadPopup(str) {
  if(IsLoggedIn() == "invalid") return false;
  var title = "RegImageUplodPopup";
  var status = "width=500, height=230, scrollbars=1, toolbar=no, menubar=no resizable=yes";
  poptarget = window.open("/smart/getRegImagePop?type=" + str  , title, status);
}

function goFileUploadPopup() {
	  
	  if(IsLoggedIn() == "invalid") return false;
	  var title = "goFileUploadPopup";
	  var status = "width=500, height=230, scrollbars=1, toolbar=no, menubar=no resizable=yes";
	  poptarget = window.open("/smart/getRegDocPop"  , title, status);
	}



function fn_fileUploadComplete(file_type, data){
	
}


function fn_fileUploadComplete(file_type, data,type){
	if(file_type == 'IMAGE'){
		$('#add'+type).hide(); 
		var divSource = "";
		divSource += "<div id='attach_file'>";
		divSource += "  <img id='upImg"+type+"' src=''/>";
		divSource += "  <input type='hidden' name='file_name' value='"+data.file_name+"'>";
		divSource += "  <input type='hidden' name='file_size' value='"+data.file_size+"'>";
		divSource += "  <input type='hidden' name='file_path' value='"+data.file_svr_path+"'>";
		divSource += "  <input type='hidden' name='file_servername' value='"+data.file_svr_name+"'>";
		divSource += "  <input type='hidden' name='file_type' value='"+file_type+"'>";
		divSource += "  <input type='hidden' name='file_contenttype' value='"+data.content_type+"'>";
		divSource += "  <input type='hidden' name='trans_mode' value='I'>";
		divSource += "</div>";
	
		var contents;
		contents = document.getElementById(type);
		
		contents.innerHTML = divSource;
		//contents.style.display = 'hidden';
	
	  	$('#upImg'+type).attr("src", data.file_full_path + data.file_svr_name).height(160).width(210);
	}else if(file_type == 'FILE'){
	  var divSource = "";
		divSource += "<ul class='flist list-unstyled' id='attach_file'>";
		divSource += "  <li id='arr_file_name'>"+data.file_name+"</li>";
		divSource += "  <li id='arr_file_size'>"+data.file_size+"</li>";
		divSource += "  <li id='arr_content_type'>"+data.content_type+"</li>";
		divSource += "  <input type='hidden' name='file_name' value='"+data.file_name+"'>";
		divSource += "  <input type='hidden' name='file_size' value='"+data.file_size+"'>";
		divSource += "  <input type='hidden' name='file_path' value='"+data.file_svr_path+"'>";
		divSource += "  <input type='hidden' name='file_servername' value='"+data.file_svr_name+"'>";
		divSource += "  <input type='hidden' name='file_type' value='"+file_type+"'>";
		divSource += "  <input type='hidden' name='file_contenttype' value='"+data.content_type+"'>";
		divSource += "  <input type='hidden' name='trans_mode' value='I'>";
		divSource += "</ul>";
		
		//alert(divSource);
		var contents;
		contents = document.getElementById('file-canvas');
		
		contents.innerHTML = contents.innerHTML + divSource;
		contents.style.display='inline';
	}
}


function CheckSendForm() {
	if(IsLoggedIn() == "invalid") return false;
	
	var frm = document.frmSMS;
	
	if( frm.subject.value.trim() == "" ) {
		alert("제목을 입력해 주세요.");
		frm.subject.focus();
		return;
	}  
	
	//if( $("#MSGTYPE").val() == "SMARTDM" && $("#imageselect").val() != ""  && $("#imagelink").val() == "" ) {
	//	alert("SMART DM 링크를 입력해 주세요.");
	//	frm.subject.focus();
	//	return;
	//}  
	
	if ($("#attach_file").length == 0) {
		alert("파일을 첨부해 주세요.");
		return;
    }
	
	
	//if ($("#rightDept").length == 0) {
	//	alert("권한을 등록해 주세요.");
	//	return;
    //}
	
	
	var param = $("#smsCreateForm").serialize();
    //alert(param);
     //if($("#targetSystem").val() != null && $("#targetSystem").val() != '') {
         //send post
     $.ajax({
         type : "POST",
         url : "/smart/sendSmartRegProc",                
         data : param,
         success : function(json) {
             if(json.result) {
                 alert(json.sucMsg);
                 if($("#mGrpNo").val() == ""){
                 	document.location.reload();
                 }else {
                 	linkPage(1);
                 }
             } else {
                 alert(json.errMsg);
             }
         },
         error : function(request, status, error) {
             alert("order list send fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
         }
     });
}
function defaultMsgFocus(control, defaultVar) {
	if(control.value == defaultVar) {
		control.value = '';
	}
}

function defaultMsgBlur(control, defaultVar) {
	if(control.value == '') {
		control.value = defaultVar;
	}
}

//$('input[name="MSG"+seq]') val($('#MSG'+seq)
function Add2MSG(seq) {
	this.Flag = true;
	$('#CONTENT').val($('#MSG'+seq).val().rtrim());
	checkMessageLength(document.getElementById('CONTENT'), 2000, 'SMS', 'spanMessageLength');
} 


function addRight(){
	addFormRight(document.forms["smsCreateForm"]);  
}

function selectRightdept(){
	
}


//권한 목록에서 모두 제거
function delRightAll() {
	arrRecieverList = new Array();
	document.getElementById("divRightList").innerHTML = '';
}	

//받는 사람 직접 입력
function addFormRight(frm) {
	if(frm != null) {
		strDept = $("#rightSDept option:selected").val();
		var strDeptvl = strDept.split(",");
		strSdt = $("#rightSSdt").val();
		strEdt = $("#rightSEdt").val();
		addRightProc(strDeptvl[0],strDeptvl[1], strSdt, strEdt);
	}
	return false;
}


function addRightProc(strDeptCd, strDept, strSdt, strEdt) {
	//alert(strDept+" : "+strSdt+" : "+strEdt);
	if ( strDept == "" ) {
		alert("권한 적용 부서를 선택해주세요.");
		return;
	}
	
	if ( strSdt == "" ) {
		alert("권한 적용 시작일자를 선택해주세요.");
		return;
	}
	
	if ( strEdt == "" ) {
		alert("권한 적용 종료일자를 선택해주세요.");
		return;
	}
	
	var html = "";		
	
	html  = '<li id="SubRightContent" >';
	//html += '<c:set var="strDept" value="'+ strDept + '" /> ${strDept}';
	//html += '<label>';
	//html += '<c:forEach var="item" items="${deptCdList}">';
	//html += '${item.code}  ${strDept} ';
	//html += '<c:if test="${strDept eq item.code}"> ${item.codeName} </c:if> </c:forEach> ';
	html += strDept +' / ' + strSdt + ' ~ ' + strEdt + '</label>';
	html += '<input type="hidden" id="rightSdt" name="rightSdt" value="' + strSdt + '" >';
	html += '<input type="hidden" id="rightEdt" name="rightEdt" value="' + strEdt + '" >';
	html += '<input type="hidden" id="rightDept" name="rightDept" value="' + strDeptCd + '" >';
	html += '</li>';
	
	document.getElementById("divRightList").innerHTML += html;
	return true;
}

function move() {
 $("#oridata input[type=checkbox]:checked").filter(function() {
  $("#movedata").append("<tr>" + $(this).parent().parent().html() + "</tr>");
  $(this).parent().parent().remove();
 });
}
function back() {
 $("#movedata input[type=checkbox]:checked").filter(function() {
  $("#oridata").append("<tr>" + $(this).parent().parent().html() + "</tr>");
  $(this).parent().parent().remove();
 });
}
</script> 

<div class="col-md-9 dmfax" >
 
<form id="smsCreateForm" name="frmSMS" method="POST" onsubmit="return false;"  autocomplete="off" style="display:inline;"> <!-- onsubmit="return false;" -->
<input type="hidden" name="destCount" id="destCount" value="0" />
<input type="hidden" name="destType" id="destType" value="0" />
<input type="hidden" name="MSGTYPE" id="MSGTYPE" value="SMARTDM" />
<input type="hidden" name="RECEIVER_CNT" id="RECEIVER_CNT" value="0" />
<input type="hidden" name="SEND_COUNT" id="SEND_COUNT" value="1000" />
<input type="hidden" name="CONVERT_TYPE" id="CONVERT_TYPE" value="FALSE" />
<input type="hidden" name="MSGSAVE" id="MSGSAVE" value="Y" />
<input type="hidden" name="MSGGRPNO" id="MSGGRPNO" value="0" />
<!-- //shorcuts -->
	<div class="col-md-12 tits">
		<h1>SMART DM/FAX 등록</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-home"></i></a></li>
			<li><a href="#">스마트 발송</a></li>
			<li class="active">SMMART DM/FAX 등록</li>
		</ol>
		<div class="hbg">
			<i class="fa fa-envelope odd"></i>
			<i class="fa fa-globe "></i>
		</div>
	</div>
	<div class="col-md-4 mbox">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">SMART DM/FAX 선택</h3>
			</div>
			<div class="panel-body">
				<ul class="list-inline radio">
					<li>
						<label for="smartdm">
							<input type="radio" name="smartdmfax" id="smartdm" tabon="tab01" value="Y" checked="1" >
							SMART DM
						</label>
					</li>
					<!--
					<li>
						<label for="smartfax">
							<input type="radio" name="smartdmfax" id="smartfax" tabon="tab02" value="N">
							SMART FAX
						</label>
					</li>
					-->		
				</ul>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">그룹선택</h3>
			</div>
			<div class="panel-body">
				<select class="form-control" name="mGrpNo" id="mGrpNo" >
			        <option value="" disabled selected>그룹선택</option>
			        <c:forEach var="usrGrpVO" items="${usrGrpList}">
		                 <option value="${usrGrpVO.grpNo}">${usrGrpVO.grpNm} </option>
		            </c:forEach>	
		        </select>
			</div>
		</div>					
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">제목</h3>
			</div>
			<div class="panel-body">
				<input type="text" name="subject" class="form-control" placeholder="TEXT 등록">
			</div>
		</div>					
			
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">SMART DM 링크 설정</h3>
			</div>
			<div class="panel-body larea">
				<select id="imageselect" name="imageselect" class="form-control">
					<option value="" disabled selected>영역선택</option>
					<option value="imagearea1">이미지 영역1</option>
					<option value="imagearea2">이미지 영역2</option>
					<option value="imagearea3">이미지 영역3</option>
					<option value="imagearea3">이미지 영역4</option>
					<option value="imagearea3">이미지 영역5</option>
				</select>
				<div class="form-group form-inline col-md-12">
					<label for="imagelink">http://</label>
					<input type="text" name="imagelink" id="imagelink" class="form-control" style="width: 70%;">
				</div>
			</div>
			
		</div>
		<div class="btns" align="center" >
			<button onclick="CheckSendForm();"  class="btn btn-primary btn-lg">저장</button>
			<button onclick="document.location.reload();" class="btn btn-primary btn-lg">재입력</button>   
		</div>
		<br>
	</div>
	<div class="col-md-4 mbox">	
					
		<div class="panel panel-default">
			<div class="panel panel-default fp01" id="smartdmarea" align="center">
				<div class="panel-heading">
					<h3 class="panel-title">SMART DM 이미지영역</h3>
				</div>
				<div class="panel-body" id="addimg-canvas1"  >
					<a onclick="goImageUploadPopup('img-canvas1');" class="btn btn-primary pop-img">이미지1 추가</a>
				</div>
				<div id="img-canvas1" ></div>
				<div class="panel-body" id="addimg-canvas2" >
					<a onclick="goImageUploadPopup('img-canvas2');" class="btn btn-primary pop-img">이미지2 추가</a>
				</div>
				<div id="img-canvas2"  ></div>
				<div class="panel-body" id="addimg-canvas3" >
					<a onclick="goImageUploadPopup('img-canvas3');" class="btn btn-primary pop-img">이미지3 추가</a>
				</div>
				<div id="img-canvas3"  ></div>
				<div class="panel-body" id="addimg-canvas4" >
					<a onclick="goImageUploadPopup('img-canvas4');" class="btn btn-primary pop-img">이미지4 추가</a>
				</div>
				<div id="img-canvas4"  ></div>
				
				<div class="panel-body" id="addimg-canvas5" >
					<a onclick="goImageUploadPopup('img-canvas5');" class="btn btn-primary pop-img">이미지5 추가</a>
				</div>
				<div id="img-canvas5"  ></div>
			</div>
			
			<div class="panel panel-default fp01" id="smartfaxarea">
				<div class="panel-heading">
					<h3 class="panel-title">SMART FAX 파일첨부</h3>
				</div>
				<div class="panel-body">
					<a onclick="goFileUploadPopup();" class="btn btn-primary pop-img">파일 등록</a>
				</div>
				<div id="file-canvas" class="panel-body odd"></div>
			</div>
			
			<ul class="flist list-unstyled">
				<li>SMART DM : JPG 파일 등록</li>
				<li>SMART FAX : PDF 파일 등록</li>
			</ul>	
			
		</div>
		
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">DM 발송 권한</h3>
			</div>
			<div class="panel-body">
				<div class="form-inline phn">
					<div class="form-group">
						<select class="form-control textinput-selector" id="rightSDept" name="rightSDept"  >
							 <c:if test="${! empty deptCdList}">
		                         <c:forEach var="item" items="${deptCdList}">
		                            <c:if test="${! empty item.codeName}">
		                            <option value="${item.code},${item.codeName}">${item.codeName}</option>
		                            </c:if>
		                         </c:forEach>
		                     </c:if>
						</select>  
							
					</div>
					<button class="btn btn-second"  onclick="javascript:addRight();">+</button>
					<div class="form-group">
						<input type="text" class="form-control datepicker" name="rightSSdt" id="rightSSdt" >
						<em class="tilde">~</em>
						<input type="text" class="form-control datepicker" name="rightSEdt" id="rightSEdt" >
					</div>
				</div>
			</div> 
			<div class="panel-body divider">
				 <ul class="list-unstyled" id="divRightList" > </ul>
				<div id="divRightInfo" style="display:none;"> </div>
			</div>
			<div class="panel-body">
				<div class="btns" style="margin-bottom:5px;" >
					<a class="btn btn-second" onclick="delRightAll();" >전체삭제</a> 	
				</div>
			</div>
		</div>	
	</div>
	
</form> 
		<div class="col-md-4">
			<div class="panel panel-default msg-stored">
				<div class="panel-heading">
					<h3 class="panel-title">SMART DM/FAX 모음함</h3>
				</div>
			<form:form method="post" modelAttribute="searchMsgBoxVO" id="msgBoxListForm" onsubmit="return false;"> 
			<form:hidden path="currentPageNo"/>
				<div class="panel-body">
					<div class="input-group">
						<div class="input-group-btn">
							<select class="form-control" name=grpCd id="grpCd" value="${searchMsgBoxVO.grpCd}" style="width: 70%;" > 
						        <option value="07" <c:if test="${searchMsgBoxVO.grpCd == '07'}">selected="selected" </c:if>>DM</option>
						        <option value="08" <c:if test="${searchMsgBoxVO.grpCd == '08'}">selected="selected" </c:if>>FAX</option>
					        </select>
					        
					    </div> 
					    <div class="input-group-btn odd">
						    <select class="form-control" name="grpNo" id="grpNo"  value="${searchMsgBoxVO.grpNo}" style="width: 130%;padding: 6px 0;margin-right: 0px;margin-left: -30px;" >
						        <option value="">그룹</option>
						        <c:forEach var="usrGrpVO" items="${usrGrpList}">
					                 <option value="${usrGrpVO.grpNo}"<c:if test="${usrGrpVO.grpNo == searchMsgBoxVO.grpNo}">selected="selected" </c:if> >${usrGrpVO.grpNm} </option>
					            </c:forEach>	
					        </select>
				        </div> 
					    <input type="text" class="form-control" name="subject" placeholder="검색할 제목"  >
					    <button  id="btnSearch"  class="btn btn-second">검색</button>
				    </div><!-- /input-group -->
					
					<ul class="list-unstyled" id="tableList">
					</ul>
					 
					<div class="panel-body text-center" >
						<ul class="pagination" id="paginate">
						<ui:pagination paginationInfo="${searchMsgBoxVO}" type="image"
				                   jsFunction="linkPage" /> 
						</ul>	
					</div>
					
					  
				</div>
				
				</form:form>
			</div>
		</div>
</div>
<!-- //site-content -->

 
 <!-- 로딩 이미지 -->
<div id="viewLoading">
	<img src="/images/loading52.gif" />
</div>

    