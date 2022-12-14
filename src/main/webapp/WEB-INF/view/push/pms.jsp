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
	
	$('#uploader').hide();
	$('#CONTENT').css('height', 340);
	$('#imgarea').hide();
	$('#image-canvas').empty();
	
	
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
    
    $("#msgGrpNo").change(function() {

        var selgrp = $("#msgGrpNo option:selected").val();
        //alert(selgrp);
        $("#MSGGRPNO").val(selgrp);
        //$("select[name=selectbox]").val();

     });
    
    
    $("input[name='docType']").change(function() {
    

        //var chkDocType = $("#docType").val();
        var chkDocType = $("input[name='docType']:checked").val();
        //alert(chkDocType);
        if( chkDocType == "01" ){
        	
        	$('#uploader').hide();
        	$('#imgarea').hide();
        	$('#CONTENT').css('height', 340);
        	$('#image-canvas').empty();
        	
        }else if( chkDocType == "02"){
        
        	$('#uploader').hide();
        	$('#imgarea').hide();
        	$('#CONTENT').css('height', 340);
        	$('#image-canvas').empty();
        }else if( chkDocType == "03"){
        	
        	$('#uploader').show();
        	$('#imgarea').show();
        	$('#CONTENT').css('height', 155);
        	$('#image-canvas').empty();
        }
        //$("#MSGGRPNO").val(selgrp);
        //$("select[name=selectbox]").val();

     });
    
 // ???????????? ????????? ??? 'Loading ?????????'??? ?????????.
	$('#viewLoading').hide();

	// ajax ?????? ??? ????????? 'Loading ?????????'??? ????????? ???????????????.
	$('#viewLoading')
	.ajaxStart(function()
	{
		// ?????????????????? ?????? ??? ????????????	
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
	
	var defaultText = "????????? ????????? ????????? ?????????.";
	$("#CONTENT")[0].value = defaultText;
	$("#CONTENT").addClass("blank");

	$("#CONTENT").bind("keyup", function(){
		if(this.value == ""){
			$(this).addClass("blank");
		}
	});

	$("#CONTENT").bind("keydown", function(){
		if($(this).hasClass("blank")){
			$(this).removeClass("blank");
		}
	});

	$("#CONTENT").bind("click", function(){
		if($(this).hasClass("blank")){
			this.value = "";
		}
	});

	$("#CONTENT").bind("blur", function(){
		if($(this).hasClass("blank")){
			this.value = defaultText;
		}
	});
	
});


function fn_fileUploadComplete(file_type, data){

	$('#imgarea').hide();

	var divSource = "";
	divSource += "<div id='attach_file' style='margin-top:10px;'>";
	divSource += "  <img id='upImg' src=''/>";
	divSource += "  <input type='hidden' name='file_name' value='"+data.file_name+"'>";
	divSource += "  <input type='hidden' name='file_size' value='"+data.file_size+"'>";
	divSource += "  <input type='hidden' name='file_path' value='"+data.file_svr_path+"'>";
	divSource += "  <input type='hidden' name='file_servername' value='"+data.file_svr_name+"'>";
	divSource += "  <input type='hidden' name='file_type' value='"+file_type+"'>";
	divSource += "  <input type='hidden' name='file_contenttype' value='"+data.content_type+"'>";
	divSource += "  <input type='hidden' name='trans_mode' value='I'>";
	divSource += "</div>";

	var contents;
	contents = document.getElementById('image-canvas');
	
	contents.innerHTML = divSource;
	//contents.style.display = 'hidden';
//	$('#upImg').attr("src", 'http://img.bluechipcns.com/upload/image/' + data.file_svr_name).height(160).width(210);
//  $('#upImg').attr("src", 'http://10.10.1.21/upload/image/' + data.file_svr_name).height(160).width(210);
  $('#upImg').attr("src", 'https://ums.sendall.co.kr:444'+ data.file_full_path + data.file_svr_name).height(160).width(210);
}

function fn_MsgSelectAfter(msg_no){
	
	var contents;
	contents = document.getElementById('image-canvas');
	contents.innerHTML = "";
	
	var fileservername	= $('#FILESERVERNAME'+msg_no).val();
	var filepath 		= $('#FILEPATH'+msg_no).val();
	var filename 		= $('#FILENAME'+msg_no).val();
	var filesize		= $('#FILESIZE'+msg_no).val();
	var filetype 		= $('#FILETYPE'+msg_no).val();
	var filecontenttype = $('#FILECONTENTTYPE'+msg_no).val();
	var divSource = "";
	divSource += "<div id='attach_file' style='margin-top:10px;'>";
	divSource += "  <img id='upImg' src=''/>";
	divSource += "  <input type='hidden' name='file_name' value='"+filename+"'>";
	divSource += "  <input type='hidden' name='file_size' value='"+filesize+"'>";
	divSource += "  <input type='hidden' name='file_path' value='"+filepath+"'>";
	divSource += "  <input type='hidden' name='file_servername' value='"+fileservername+"'>";
	divSource += "  <input type='hidden' name='file_type' value='"+filetype+"'>";
	divSource += "  <input type='hidden' name='file_contenttype' value='"+filecontenttype+"'>";
	divSource += "  <input type='hidden' name='trans_mode' value='I'>";
	divSource += "</div>";
	
	if($.trim(fileservername) != "null" && fileservername.length > 0){
		$('#imgarea').hide();
		contents.innerHTML = divSource;
  		$('#upImg').attr("src", 'https://ums.sendall.co.kr:444'+ filepath + fileservername).height(160).width(210);
	}
  	
  	$('#CONTENT').val($('#MSG'+msg_no).val().rtrim());
	checkMessageLength(document.getElementById('CONTENT'), 2000, 'MMS', 'spanMessageLength');
}

//address excel upload popup 
function goImageUploadPopup() {
  if(IsLoggedIn() == "invalid") return false;
  var title = "RegImageUplodPopup";
  var status = "width=500, height=230, scrollbars=1, toolbar=no, menubar=no resizable=yes";
  poptarget = window.open("/ums/getRegImagePop"  , title, status); 
}

//address excel upload popup 
function goAddrExcelUplodPopup() {
	if(IsLoggedIn() == "invalid") return false;
	//$("#orderLinkOrderGroupSeq").val(jobOrderGroupSeq);
	//$("#orderLinkUserId").val(userId);
	//$("#orderLinkCallerType").val("avail");
	
	var f = document.forms["smsCreateForm"];
    var url = "/ums/addrXlsPopup";  
    var title = "AddrExcelUplodPopup";
	
    //windowOpenPost(f, url, null, "yes", title, 500, 500, 50, 50, "yes");
    
    var status = "width=500, height=500, scrollbars=1, toolbar=no, menubar=no resizable=yes";
    poptarget = window.open("/ums/addrXlsPopup"  , title, status);
}

//address excel upload popup 
function goAddrListPopup() {
	if(IsLoggedIn() == "invalid") return false;
    // modal ?????? window.showModalDialog ?????????!! 

	//$("#orderLinkOrderGroupSeq").val(jobOrderGroupSeq);
	//$("#orderLinkUserId").val(userId);
	//$("#orderLinkCallerType").val("avail");
	
	var f = document.forms["smsCreateForm"];
    var url = "/ums/addrListPopup";  
    var title = "AddrListPopup";
    
    //windowOpenPost(f, url, null, "yes", title, 500, 500, 50, 50, "yes");
    //var style =" dialogWidth:500px; dialogHeight:500px"; 
    var status = "width=478, height=700, scrollbars=1, toolbar=no, menubar=no resizable=yes";
    poptarget = window.open("/ums/addrListPopup"  , title, status);
    //poptarget = window.showModalDialog("/ums/addrListPopup"  , title, style);
}

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
function addPhoneNum(){
	if( NumberCheck((frmSMS.AddReceiveNumber.value).trim().replace(/\-/g,'')) ) 
	{addFormReceiver(document.forms["smsCreateForm"]); } 
	else { alert('????????? ?????? ???????????????.'); }
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
            //html += '<li  title=""><a onclick="Add2MSG('+ item.msgNo + '); fn_MsgSelectAfter(' + item.contents7 + ',' + item.contents2 + '); " href="javascript:;">' + item.contents1Temp + '</a></li>';
            html += '<li  title=""><a onclick="fn_MsgSelectAfter(' + item.msgNo + ');">' + item.contents1Temp + '</a></li>';
            html += '<input type="hidden" id="MSG'+ item.msgNo +'" value="' +item.contents1 + '" /> </li>';
            html += '<input type="hidden" id="FILESERVERNAME'+ item.msgNo +'" value="' +item.contents2 + '" /> </li>';
            html += '<input type="hidden" id="FILEPATH'+ item.msgNo +'" value="' +item.contents7 + '" /> </li>';
            html += '<input type="hidden" id="FILESIZE'+ item.msgNo +'" value="' +item.contents4 + '" /> </li>';
            html += '<input type="hidden" id="FILENAME'+ item.msgNo +'" value="' +item.contents3 + '" /> </li>';
            html += '<input type="hidden" id="FILETYPE'+ item.msgNo +'" value="' +item.contents6 + '" /> </li>';
            html += '<input type="hidden" id="FILECONTENTTYPE'+ item.msgNo +'" value="' +item.contents8 + '" /> </li>';
            
        });
        $("#tableList").html(html);
    }
    $("#paginate").html(pagingHtml);
} 
function CheckSendForm() {
	
	if(IsLoggedIn() == "invalid") return false;
	var frm = document.frmSMS;

	var chkCount = 0;
	frm.RECEIVER_CNT.value = $('input[name="receiverInfo"]').length;
	SelSender = frm.SELECT_SENDER.options[frm.SELECT_SENDER.selectedIndex].value;

	if( frm.SENDER.value.trim() == "" ) {
		if( SelSender == "DIRECT" || SelSender == "") {
			alert("????????? ????????? ????????? ?????????.");
			frm.SENDER.focus();
			return;
		} else {
			frm.SENDER.value = SelSender;
		}
	}
	
	if( frm.CONTENT.value.trim() == "" || frm.CONTENT.value.trim() == Util.DefaultKeyword ) {
		alert("????????? ????????? ????????? ?????????.");
		frm.CONTENT.focus();
		return;
	} //else if(!CheckMessage()) return;
	
	if( frm.RECEIVER_CNT.value == 0 ) {
		alert("?????? ????????? ????????? ?????????");
		frm.AddReceiveName.focus();
		return;
	}
	if( document.getElementsByName("scheduleType")[2].checked == true && frm.REPEAT_TYPE.options[frm.REPEAT_TYPE.selectedIndex].value == "" ) {
		alert("??????????????? ??????????????????.");
		frm.REPEAT_TYPE.focus();
		return;
	}
	if( document.getElementsByName("scheduleType")[1].checked == true ) {
		var strNowDate = new Date();
		var strNowDateTime =  new Date(strNowDate.getFullYear(), strNowDate.getMonth(), strNowDate.getDate(), strNowDate.getHours(), strNowDate.getMinutes(), "00");
		var strReserveDate = new Date($("#reserveYear").val(), $("#reserveMonth").val() - 1, $("#reserveDay").val(), $("#reserveHour").val(), $("#reserveMin").val(), "00");

		if( strReserveDate.getTime() <= strNowDateTime.getTime() ) {
			alert("?????? ????????? ????????? ?????????.");
			return;
		}
	}
	if( getByteLength(frm.CONTENT.value.replace(/\r/g, "")) > 80 ) {
		frm.MSGTYPE.value = "MMS";
	} else {
		frm.MSGTYPE.value = "SMS";
	}

	// ??????????????? ?????? ?????? ??????????????????
	if(SendLimit) { 
		var rcnt = parseInt(frm.RECEIVER_CNT.value);
		var acc_send_add = parseInt(5000);
		var acc_send_limit = parseInt(5000);
		var acc_send_count = parseInt(6);
		var acc_send_auth = true;
		
		if(acc_send_limit != 0 && (rcnt + acc_send_count) >= acc_send_limit) { 
			alert('????????? ??? ?????? ????????? ???????????? ???????????? ????????????.\n??????????????? ?????? ???????????? ??????????????????.');
			return;
		}
	}
	if( sendCntChk() == false ) {
		return;
	}
	if(Check2Replace() == true) {
		var param = $("#smsCreateForm").serialize();
		var param1 = $("#smsCreateForm1").serialize();

        //if($("#targetSystem").val() != null && $("#targetSystem").val() != '') {
            //send post
        $.ajax({
            type : "POST",
            url : "/push/sendPmsProc",                
            data : param+"&"+param1,
            success : function(json) {
            	if(json.result) {
                    alert(json.sucMsg);
                    if($("#MSGSAVE").val() == "Y"){
                    	document.location.reload();
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

function RepeatType(strTypeSelect) {
	var obj = document.getElementById("spanRepeatType")

	if( strTypeSelect == "MONTH" ) {
		obj.innerHTML = Period.SelectBoxDay("REPEAT");
	} else if( strTypeSelect == "WEEK" ) {
		obj.innerHTML = Period.SelectBoxWeek("REPEAT");
	} else {
		obj.innerHTML = "&nbsp;";
	}
}
//$('input[name="MSG"+seq]') val($('#MSG'+seq)
function Add2MSG(seq) {
	this.Flag = true;
	$('#CONTENT').val($('#MSG'+seq).val().rtrim());
	checkMessageLength(document.getElementById('CONTENT'), 2000, 'SMS', 'spanMessageLength');
} 
function ShowBytes() {
	//var msg = $('#CONTENT').val().replace(/[\r\n\t]/gi,'');
	//$('#spanMessageLength').html(Util.GetBytes(msg));
	checkMessageLength(document.getElementById('CONTENT'), 2000, 'SMS', 'spanMessageLength');
}
</script> 

 
<!-- //shorcuts -->
<div class="col-md-9">

<form id="smsCreateForm" name="frmSMS" method="POST" onsubmit="return false;"  autocomplete="off" style="display:inline;"> <!-- onsubmit="return false;" -->
<input type="hidden" name="destCount" id="destCount" value="0" />
<input type="hidden" name="destType" id="destType" value="0" />
<input type="hidden" name="MSGTYPE" id="MSGTYPE" value="MMS" />
<input type="hidden" name="RECEIVER_CNT" id="RECEIVER_CNT" value="0" />
<input type="hidden" name="SEND_COUNT" id="SEND_COUNT" value="500" />
<input type="hidden" name="CONVERT_TYPE" id="CONVERT_TYPE" value="FALSE" />
<input type="hidden" name="MSGSAVE" id="MSGSAVE" value="N" />
<input type="hidden" name="MSGGRPNO" id="MSGGRPNO" value="0" />
	<div class="col-md-12 tits">
		<h1>PUSH ?????????</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-home"></i></a></li>
			<li><a href="#">???????????????</a></li>
			<li class="active">PMS</li>
		</ol>
		<div class="hbg">
			<i class="fa fa-envelope odd"></i>
			<i class="fa fa-globe "></i>
		</div>
	</div>
	
	
	<div class="col-md-4 mbox mms">	
					
		<div class="mob">
			<div id="CONTENT_BG" class="mbody">
				<!-- <textarea name="smsMsg" id="CONTENT"  onkeydown="checkMessageLength(this, 2000, 'SMS', 'spanMessageLength')" onkeyup="checkMessageLength(this, 2000, 'SMS', 'spanMessageLength')"  ></textarea>
				 -->
				 
				 <div class="uploader" id="uploader">
					<div class="imgarea" id="imgarea" style="margin-bottom:20px;">
						<h1>????????? ??????</h1>
						<a onclick="goImageUploadPopup();" class="pop-img" ><img src="/images/img-upload.png"  id="pop-img" alt="??? PC ????????? ??????" ></a>
					</div>
					<div id="image-canvas"></div>
				</div>
				 <textarea name="mmsMsg" id="CONTENT"    ></textarea>
				<div class="mfoot">
					<em class="mtype mms" id="mtype_sms" >PMS</em>
				<!--	<span id="spanMessageLength" class="counting" ><Strong>0</Strong>/2000byte</span>
				-->
				<span id="spanMessageLength" class="counting" ></span>
				</div>
			</div>
			<img src="/images/mob.png" alt="????????? ??????" class="mimg">
		</div>
		
		<!-- 
	<div class="col-md-4 mbox mms">			
					
		<div class="mob">
			<div id="CONTENT_BG" class="mbody">
				<div class="uploader">
					<div class="imgarea" id="imgarea" style="margin-bottom:20px;">
						<h1>????????? ??????</h1>
						<a onclick="goImageUploadPopup();" class="pop-img" ><img src="/images/img-upload.png"  id="pop-img" alt="??? PC ????????? ??????" ></a>
					</div>
					<div id="image-canvas"></div>
				</div>
				<textarea name="mmsMsg" id="CONTENT"  onkeydown="checkMessageLength(this, 2000, 'MMS', 'spanMessageLength')" onkeyup="checkMessageLength(this, 2000, 'MMS', 'spanMessageLength')" ></textarea>
				<div class="mfoot"> 
					<em class="mtype mms" id="mtype_mms" >MMS</em>
					<span id="spanMessageLength" class="counting" ><Strong>0</Strong>/80byte</span>
				</div>
			</div>
			<img src="/images/mob.png" alt="????????? ??????" class="mimg">
		</div>
		 -->
		<div class="checkbox">
		    <label>
			    <input type="checkbox" name="msgSav"  id="msgSav" value="N"  >????????? ??????
		    </label>
			<ul class="list-inline radio">
					<li>
						<label for="pl01">
							<input type="radio"  name="pushType" value="01"  id="pl01" tabon="tab01" checked="1">
							PublicPush
						</label>
					</li>
					<li>
						<label for="pl02">
							<input type="radio" name="pushType" value="02"  id="pl02" tabon="tab02">
							PrivatePush
						</label>
					</li>
			</ul> 
			
			<ul class="list-inline radio">
					<li>
						<label for="pl03">
							<input type="radio" name="docType" value="01"  id="pl03" tabon="tab01" checked="1">
							?????? TEXT
						</label>
					</li>
					<li>
						<label for="pl04">
							<input type="radio" name="docType" value="02"  id="pl04" tabon="tab02">
							HTML ??????
						</label>
					</li>
					<li>
						<label for="pl05">
							<input type="radio" name="docType" value="03"  id="pl05" tabon="tab03">
							?????????
						</label>
					</li>
			</ul>    
		</div>
		
		<div class="btns">
			<button onclick="CheckSendForm();"  class="btn btn-primary btn-lg">?????????</button>
			<button onclick="document.location.reload();" class="btn btn-primary btn-lg">?????????</button>   
		</div>
	</div>
	
		
	
	
	
				
	<div class="col-md-4">		
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">????????????</h3>
			</div>
			<div class="panel-body">
				<input type="text" name="callback" id="SENDER" value="" class="form-control textinput" style="display:none; width:75%; float:left;" onkeydown="OnlyNumPhoneNumScript(this,event);" />
				<select class="form-control textinput-selector"   id="SELECT_SENDER" onchange="SenderSelect(this.value, this.form)">
					<option value="">????????????</option>
					<c:if test="${! empty usrCallBackList}">
                        <c:forEach var="item" items="${usrCallBackList}">
                           <c:if test="${! empty item.codeName}">
                           <option value="${item.codeName}">${item.codeName}</option>
                           </c:if>
                        </c:forEach>
                        	<!-- <option value="DIRECT">????????????</option> -->
                    </c:if>
				</select>	
				<span id="spanSelectBox" onclick="SenderSelect('', document.getElementById('SELECT_SENDER').form)"  style="display:none;">
					<button class="btn btn-second" style="float:right;" >??????</button>
				</span>
			</div>
		</div>	
						
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">??????</h3>
			</div>
			<div class="panel-body">
				<textarea class="form-control" name="subject" id="" rows="2" placeholder="????????? ????????? ?????????.(??????????????? ?????? ?????????.)"></textarea>
			</div>
		</div>	
						
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">????????? ??????</h3>
				<div class="rcvsum">
				??? <span id="spanReceiverCount"><strong>0</strong></span> ???
				</div>	
			</div>
			<div class="panel-body">
				<div class="form-inline phn">
					<div class="form-group">
						<input type="text" id="AddReceiveName" name="AddReceiveName" onfocus="defaultMsgFocus(this, '??????');this.select()" onblur="defaultMsgBlur(this, '??????')" class="form-control name" placeholder="??????" />						 
						<input type="text" id="AddReceiveNumber" name="AddReceiveNumber"  onfocus="defaultMsgFocus(this, '????????????(???????????? ??????)');this.select()" onblur="defaultMsgBlur(this, '????????????(???????????? ??????)')" onkeydown="OnlyNumPhoneNumScript(this,event);if(13==event.keyCode){addFormReceiver(this.form);}" class="form-control number" placeholder="???????????????" />
					</div>
					<button class="btn btn-second"  onclick="javascript:addPhoneNum();">+</button>
				</div> 
			</div>
			<div class="panel-body divider">
				<ul class="list-unstyled" id="divReceiverList" > </ul>
				<div id="divReceiverInfo" style="display:none;"> </div>
			</div>
			<div class="panel-body">
				<div class="btns" style="margin-bottom:5px;" >
					<a class="btn btn-second" onclick="delReceiverAll();" >????????????</a> 	
				</div>
				<div class="btns"> 
					<a class="btn btn-second pop-add" onclick="javascript:goAddrListPopup();return false;" >????????? ????????????</a>
					<a class="btn btn-second pop-excel" onclick="goAddrExcelUplodPopup();" >????????????</a>		
				</div>
			</div>
		</div>	
		
		
						
		
	</div>
</form> 
		<div class="col-md-4">
			<div class="panel panel-default msg-stored">
				<div class="panel-heading">
					<h3 class="panel-title">PMS ????????? ?????????</h3>
				</div>
			<form:form method="post" modelAttribute="searchMsgBoxVO" id="msgBoxListForm" onsubmit="return false;" > 
			<form:hidden path="currentPageNo"/>
			<form:hidden path="grpCd" value="11" />	
				<div class="panel-body">
					<div class="input-group" style="width: 70%; margin-top: 0;">
				        <select class="form-control"  name="grpNo" id="msgGrpNo" style="width: 40%;padding: 6px 0;margin-right: -1px;">
				        <option value="">??????</option>
				        <c:forEach var="usrGrpVO" items="${usrGrpList}">
			                 <option value="${usrGrpVO.grpNo}">${usrGrpVO.grpNm} </option>
			            </c:forEach>	
			            </select>	          
				      	<input type="text" class="form-control" name="subject" placeholder="????????? ??????" style="width: 60%; margin-right: 0;">
				    </div><!-- /input-group -->
					<button  id="btnSearch"  class="btn btn-second" style="width: 28%; margin-left: 2%;">??????</button>
				
					<ul class="list-unstyled" id="tableList">
					
					<!--
					<c:if test="${empty msgBoxList}">
					
						<li class="list-group-item">
							<a href="#" title="">
								NO DATA
							</a>
						</li>
					
					</c:if>
					<c:if test="${! empty msgBoxList}">
						
	                    <c:forEach var="noticeVO" items="${msgBoxList}">
	                    	<fmt:parseDate var="instDt" value="${msgBoxVO.instDt}" pattern="yyyyMMddHHmmss" />
	                        <li class="list-group-item">
								<a href="#" title="">
									${msgBoxVO.subject}
								</a>
							</li>			                        
	                    </c:forEach>    
	                    
	                </c:if>    
					-->
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
				  		
			<form id="smsCreateForm1" name="frmSMS1" method="POST" onsubmit="return false;">	
			
			<div class="panel panel-default send-date">
			<div class="panel-body">
				<ul class="list-inline radio">
					<li>
						<label for="pl01">
							<input type="radio"  name="scheduleType" value="NOW"  id="pl01" tabon="tab01" checked="1">
							???????????????
						</label>
					</li>
					<li>
						<label for="pl02">
							<input type="radio" name="scheduleType" value="RESERVE"  id="pl02" tabon="tab02">
							???????????????
						</label>
					</li>		
					 
					<li style="display:none;">
						<label for="pl03">
							<input type="radio" name="scheduleType" value="REPEAT" id="pl03" tabon="tab03">
							???????????????
						</label>
					</li>
					
				</ul>
				<div class="tabpn" id="tab01">
					<p>???????????? ?????? ????????? ???????????? ?????? ?????? ???????????????.</p>
				</div>
				<div class="tabpn off" id="tab02">
					<span id="spanNow" > </span>
					<span id="spanReserve" >
						<script>Period.SelectBoxFormWrite("reserve");</script>
						<script>Period.SelectBoxTimeFormWrite("reserve");</script>
					</span>
				</div>
				<div class="tabpn off" id="tab03">
					<select name="REPEAT_TYPE" class="form-control unstyled" onchange="RepeatType(this.value)">
						<option value="">???????????? ??????</option>
						<option value="DAY">?????? ??????</option>
						<option value="WEEK">?????? ??????</option>
						<option value="MONTH">?????? ??????</option>
						<option value="5DAY">???~??? ??????</option>
					</select>
					<span id="spanRepeatType" style="padding-left:10px;"></span>
					<script>Period.SelectBoxTimeFormWrite("repeat");</script>
				</div>
			</div>
		</div>
		</form> 
		 
	</div>
		
</div>	
<!-- //site-content -->

 <!-- ?????? ????????? -->
<div id="viewLoading">
	<img src="/images/loading52.gif" />
</div>

    