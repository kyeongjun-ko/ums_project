<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>  
<%@ page import = "java.util.Date"%>
<%@ page import = "java.text.SimpleDateFormat"%>
<%@ page import = "java.util.ArrayList"%> 
 
<script type="text/javascript">
var SendLimit = true;
var msgBoxList = new Array();
var clickCount = 0;
var firstPage = 1;
$(document).ready(function() {
	//default search
	linkPage(firstPage);

	$("#msgSav").on("click", function(){
        if( $(this).is(":checked") ){
            $("#MSGSAVE").val("Y");
        } else {
            $("#MSGSAVE").val("N");
        }            
    });
	
	$("#pdsChk").on("click", function(){
        if( $(this).is(":checked") ){
            $("#pdsMode").val("Y");
            alert('[폰구분모드] 대량발송시 지연될수 있으며 폰구분서비스 사용료가 부가됩니다.');
        } else {
            $("#pdsMode").val("N");
        }            
    });
	
	$("#smssplitchk").on("click", function(){
        if( $(this).is(":checked") ){
            $("#SMSSPLIT").val("Y");
            alert('[장문SMS분리] 장문SMS발송시 SMS를 80바이트단위로 분리하여 다건의 SMS로 전송됩니다.');
        } else {
            $("#SMSSPLIT").val("N");
        }            
    });
		    
    $("#btnSearch").click(function() {
    	//page setting
        $("#currentPageNo").val(firstPage);
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
					 html += '<option value="">그룹</option>';
			        $.each(data.usrGrpList, function(index, item) {
			            html += '<option value="'+ item.grpNo +'" <c:if test="${'+ item.grpNo +'== searchMsgBoxVO.grpNo}">selected="selected" </c:if> >'+item.grpNm  +'</option>' ;
			        });
				    $("#grpNo").find("option").remove().end().append(html);
				} else {
					$("#grpNo").find("option").remove().end().append("<option value=''>그룹</option>");
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
	
	var defaultText = "<spring:message code='message'/>";
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

function addPhoneNum(){
	if( NumberCheck((frmSMS.AddReceiveNumber.value).trim().replace(/\-/g,'')) ) 
	{
		if($("#pdsMode").val() == "Y") { 
			jsonpPds(frmSMS.AddReceiveNumber.value);	
		}
		addFormReceiver(document.forms["smsCreateForm"]);
		
	} 
	else { alert('숫자만 입력 가능합니다.'); }
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
    //windowOpenPost(f, url, null, "yes", title, 500, 500, 50, 50, "yes");
    poptarget = window.open("/ums/addrXlsPopup"  , title, status);
	
}

//address excel upload popup 
function goAddrListPopup() {
	if(IsLoggedIn() == "invalid") return false;
	var f = document.forms["smsCreateForm"];
    var url = "/ums/addrListPopup";  
    var title = "AddrListPopup";
    
    //windowOpenPost(f, url, null, "yes", title, 500, 500, 50, 50, "yes");
    //var style =" dialogWidth:500px; dialogHeight:500px"; 
    var status = "width=478, height=700, scrollbars=1, toolbar=no, menubar=no resizable=yes";
    poptarget = window.open("/ums/addrListPopup"  , title, status);
}

//address excel upload popup 
function goAddrGrpListPopup() {
	if(IsLoggedIn() == "invalid") return false;
	var f = document.forms["smsCreateForm"];
    var url = "/ums/addrGrpListPopup";  
    var title = "AddrGrpListPopup";
    
    //windowOpenPost(f, url, null, "yes", title, 500, 500, 50, 50, "yes");
    //var style =" dialogWidth:500px; dialogHeight:500px"; 
    var status = "width=478, height=700, scrollbars=1, toolbar=no, menubar=no resizable=yes";
    poptarget = window.open("/ums/addrGrpListPopup"  , title, status);
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
            html += '<li title=""><a onclick="Add2MSG('+ item.msgNo + ');" href="javascript:;">' + item.contents1Temp + '</a></li>';
            html += '<input type="hidden" id="MSG'+ item.msgNo +'" value="' +item.contents1 + '" /> </li>';
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
			alert("보내는 사람을 입력해 주세요.");
			frm.SENDER.focus();
			return;
		} else {
			frm.SENDER.value = SelSender;
		}
	}
	
	if( frm.CONTENT.value.trim() == "" || frm.CONTENT.value.trim() == Util.DefaultKeyword ) {
		alert("메시지 내용을 입력해 주세요.");
		frm.CONTENT.focus();
		return;
	} //else if(!CheckMessage()) return;
	
	if( frm.RECEIVER_CNT.value == 0 ) {
		alert("받는 사람을 입력해 주세요");
		frm.AddReceiveName.focus();
		return;
	}
	if( document.getElementsByName("scheduleType")[2].checked == true && frm.REPEAT_TYPE.options[frm.REPEAT_TYPE.selectedIndex].value == "" ) {
		alert("반복유형을 선택해주세요.");
		frm.REPEAT_TYPE.focus();
		return;
	}
	if( document.getElementsByName("scheduleType")[1].checked == true ) {
		var strNowDate = new Date();
		var strNowDateTime =  new Date(strNowDate.getFullYear(), strNowDate.getMonth(), strNowDate.getDate(), strNowDate.getHours(), strNowDate.getMinutes(), "00");
		var strReserveDate = new Date($("#reserveYear").val(), $("#reserveMonth").val() - 1, $("#reserveDay").val(), $("#reserveHour").val(), $("#reserveMin").val(), "00");

		if( strReserveDate.getTime() <= strNowDateTime.getTime() ) {
			alert("예약 시간을 확인해 주세요.");
			return;
		}
	}
	if( getByteLength(frm.CONTENT.value.replace(/\r/g, "")) > 80 ) {
		frm.MSGTYPE.value = "MMS";
	} else {
		frm.MSGTYPE.value = "SMS";
	}

	// 누적발송량 체크 공용 자바스크립트
	if(SendLimit) { 
		var rcnt = parseInt(frm.RECEIVER_CNT.value);
		var acc_send_add = parseInt(5000);
		var acc_send_limit = parseInt(5000);
		var acc_send_count = parseInt(6);
		var acc_send_auth = true;
		
		if(acc_send_limit != 0 && (rcnt + acc_send_count) >= acc_send_limit) { 
			alert('발송할 수 있는 메시지 발송량이 초과되어 있습니다.\n고객센터나 영업 담당에게 연락해주세요.');
			return;
		}
	}
	if( sendCntChk() == false ) {
		return;
	}
	
	if(Check2Replace() == true) {
		var param = $("#smsCreateForm").serialize();
		var param1 = $("#smsCreateForm1").serialize();
		
		var strUrl = "";
		if($("#pdsMode").val() == "Y" && $("#MSGTYPE").val() == "MMS"){
			strUrl = "/ums/sendPdsProc";
		}else {
			strUrl = "/ums/sendProc";
		}
		
		//alert(param);
		//alert(param+param1);
        //if($("#targetSystem").val() != null && $("#targetSystem").val() != '') {
            //send post
        $.ajax({
            type : "POST",
            url : strUrl,             
            data : param+"&"+param1,
            beforeSend : function() {
		    	$("#sendBtn").attr("disabled", "disabled");
		    },
            success : function(json) {
                if(json.result) {
                    if (json.sucMsg.indexOf("SMS") != -1) {
                    	alert('<spring:message code="successfullySent"/>');
                    } else {
                    	alert(json.sucMsg);                    	
                    }
                    if($("#MSGSAVE").val() == "Y"){
                    	document.location.reload();
                    }
                } else {
                    alert(json.errMsg);
                }
                $("#sendBtn").removeAttr("disabled");
            },
            error : function(request, status, error) {
                alert("order list send fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
                $("#sendBtn").removeAttr("disabled");
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

function jsonpPdsAll() {
	var cnt = $('input[name="receiverInfo"]').length;
	var strt,str,strtmp = "";
	for ( var i = 1; i < cnt+1 ; ++i ) {
		str = $("#SubReceiverValue"+i).val();
		strtmp = strtmp + str + ",";
	}
	alert (strtmp);

	$.ajax({
        url : "https://211.216.53.38:8443/admin/pdsweb.jsp?format=json&service=PDS01&cnt="+cnt + "&pl=" + strtmp,
        //url : 'https://211.216.53.38:8443/admin/pdsweb.jsp?format=json&service=PDS01&req={"header":{"clientId":"bluechip","clientPw":"bluechip"},"body":{"requestId":"11111","telNo":"01067323629"}}',
        crossDomain: true,
        dataType : "jsonp",
        jsonp : "callback",
        async : false,
        success : function(data) {
        	$("#smartPhnYnlist").val(data.smartPhnYn);
        	
        },
        error : function(request, status, error) {
        	alert("order list send fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
        }
    });
	alert($("#smartPhnYnlist").val());
}

function jsonpPds(str) {
	var strt = str.replace("-","").replace("-","");
	$.ajax({
        url : "https://211.216.53.38:8443/admin/pdsweb.jsp?format=json&service=PDS01&req={'header':{'clientId':'bluechip','clientPw':'bluechip'},'body':{'requestId':'11111','telNo':'"+strt+"'}}",
        //url : 'https://211.216.53.38:8443/admin/pdsweb.jsp?format=json&service=PDS01&req={"header":{"clientId":"bluechip","clientPw":"bluechip"},"body":{"requestId":"11111","telNo":"01067323629"}}',
        dataType : "jsonp",
        jsonp : "callback",
        async : false,
        success : function(data) {
	        $("#AddSmartPhnYn").val(data.smartPhnYn);
        },
        error : function(request, status, error) {
        	alert("order list send fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
        }
    });
}

function callback(data){
	$("#AddSmartPhnYn").val(data.smartPhnYn);
}
function ajaxPds() {
	var cnt = $('input[name="receiverInfo"]').length;
	var strt,str,strtmp = "";
	for ( var i = 1; i < cnt+1 ; ++i ) {
		str = $("#SubReceiverValue"+i).val();
		strtmp = strtmp + str + ",";
	}
	$.ajax({
        type : "POST",
        url : "/ums/getPdsListAjax",                
        data : "cnt="+cnt + "&pl=" + strtmp,
        success : function(json) {
        	alert(json);
        },
        error : function(request, status, error) {
            alert("order list send fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
        }
    });
	
}

</script> 

<!-- //shorcuts -->
<div class="col-md-9">
 
<form id="smsCreateForm" name="frmSMS" method="POST" onsubmit="return false;"  autocomplete="off" style="display:inline;"> <!-- onsubmit="return false;" -->
<input type="hidden" name="destCount" id="destCount" value="0" />
<input type="hidden" name="destType" id="destType" value="0" />
<input type="hidden" name="MSGTYPE" id="MSGTYPE" value="SMS" />
<input type="hidden" name="RECEIVER_TYPE" id="RECEIVER_TYPE" value="A" />
<input type="hidden" name="RECEIVER_CNT" id="RECEIVER_CNT" value="0" />
<input type="hidden" name="SEND_COUNT" id="SEND_COUNT" value="500" />
<input type="hidden" name="CONVERT_TYPE" id="CONVERT_TYPE" value="FALSE" />
<input type="hidden" name="MSGSAVE" id="MSGSAVE" value="N" />
<input type="hidden" name="MSGGRPNO" id="MSGGRPNO" value="0" />
<input type="hidden" name="pdsMode" id="pdsMode" value="N" />
<input type="hidden" name="smartPhnYnlist" id="smartPhnYnlist" />
<input type="hidden" name="SMSSPLIT" id="SMSSPLIT" value="N" />


	<div class="col-md-12 tits">
		<h1>SMS/LMS</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-home"></i></a></li>
			<li><a href="#">메시지발송</a></li>
			<li class="active">SMS/LMS</li>
		</ol>
		<div class="hbg">
			<i class="fa fa-envelope odd"></i>
			<i class="fa fa-globe "></i>
		</div>
	</div>
	
	<div class="col-md-4 mbox">	
					
		<div class="mob">
			<div id="CONTENT_BG" class="mbody">
				<textarea name="smsMsg" id="CONTENT"  onkeydown="checkMessageLength(this, 2000, 'SMS', 'spanMessageLength')" onkeyup="checkMessageLength(this, 2000, 'SMS', 'spanMessageLength')"  ></textarea>
				<div class="mfoot">
					<em class="mtype sms" id="mtype_sms" >SMS</em>
					<em class="mtype lms" id="mtype_lms" style="display:none;">LMS</em>
					<em class="mtype lms" id="mtype_mms" style="display:none;">MMS</em>
					<span id="spanMessageLength" class="counting" ><Strong>0</Strong>/80byte</span>
				</div>
			</div>
			<img src="/images/mob.png" alt="모바일 배경" class="mimg">
		</div>
		
		
		
		
		<div class="checkbox">
		    <label>
			    <input type="checkbox" name="msgSav"  id="msgSav" value="N"  ><spring:message code="saveMessage"/>
		    </label>
		    
		    <label>
			    <input type="checkbox" name="smssplitchk"  id="smssplitchk" value="N"  >장문SMS분리
		    </label>
		    
		    <label>
			    <input type="checkbox" name="pdsChk"  id="pdsChk" value="N"  >폰구분 모드
		    </label>
		</div>
		<div class="btns">
			<button onclick="CheckSendForm();" id="sendBtn" class="btn btn-primary btn-lg"><spring:message code="send"/></button>
			<button onclick="document.location.reload();" class="btn btn-primary btn-lg">재입력</button>   
		</div>
	</div>
	
				
	<div class="col-md-4">		
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title"><spring:message code="callback"/></h3>
			</div>
			<div class="panel-body">
				<input type="text" name="callback" id="SENDER" value="" class="form-control textinput" style="display:none; width:75%; float:left;" onkeydown="OnlyNumPhoneNumScript(this,event);" />
				<select class="form-control textinput-selector"   id="SELECT_SENDER" onchange="SenderSelect(this.value, this.form)">
					<option value=""><spring:message code="callback"/></option>
					<c:if test="${! empty usrCallBackList}">
                        <c:forEach var="item" items="${usrCallBackList}">
                           <c:if test="${! empty item.codeName}">
                           <option value="${item.codeName}">${item.codeName}</option>
                           </c:if>
                        </c:forEach>
                       	<!-- <option value="DIRECT">직접입력</option> -->
                    </c:if>
				</select>	
				<span id="spanSelectBox" onclick="SenderSelect('', document.getElementById('SELECT_SENDER').form)" style="display:none;">
					<button class="btn btn-second" style="float:right;" >선택</button>
				</span>
			</div>
		</div>	
						
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title"><spring:message code="subject"/></h3>
			</div>
			<div class="panel-body">
				<textarea class="form-control" name="subject" id="" rows="2" placeholder="<spring:message code="subjectContent"/>"></textarea>
			</div>
		</div>	
						
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title"><spring:message code="receivers"/></h3>
				<div class="rcvsum">
				총 <span id="spanReceiverCount"><strong>0</strong></span> 명(그룹)
				</div>	
			</div>
			<div class="panel-body">
				<div class="form-inline phn">
					<div class="form-group">
						<input type="text" id="AddReceiveName" name="AddReceiveName" onfocus="defaultMsgFocus(this, '이름');this.select()" onblur="defaultMsgBlur(this, '이름')" class="form-control name" placeholder="이름" />
						<input type="text" id="AddReceiveNumber" name="AddReceiveNumber"  onfocus="defaultMsgFocus(this, '전화번호(지역번호 포함)');this.select()" onblur="defaultMsgBlur(this, '전화번호(지역번호 포함)')" onkeydown="OnlyNumPhoneNumScript(this,event);if(13==event.keyCode){addFormReceiver(this.form);}" class="form-control number" placeholder="휴대폰번호" />
						<input type="hidden" name="AddSmartPhnYn" id="AddSmartPhnYn" />
					</div>
					<button class="btn btn-second"  onclick="javascript:addPhoneNum();">+</button>
				</div>
			</div> 
			<div class="panel-body divider">
				 <ul class="list-unstyled" id="divReceiverList" > </ul>
				<!--<div class="form-control" id="divReceiverList" style="width:200px; height:80px;overflow:auto; color:#333;overflow-y:scroll;overflow-x:hidden;"></div>
					 -->
				<div id="divReceiverInfo" style="display:none;"> </div>
			</div>
			<div class="panel-body">
				<div class="btns" style="margin-bottom:5px;" >
					<a class="btn btn-second" onclick="delReceiverAll();" >전체삭제</a>
					<a class="btn btn-second pop-add" onclick="javascript:delReceiverAll();goAddrGrpListPopup();return false;" ><spring:message code="contactList"/></a>
					 	
				</div>
				<div class="btns"> 
					<a class="btn btn-second pop-add" onclick="javascript:delReceiverAll();goAddrListPopup();return false;" >주소록 불러오기</a>
					<a class="btn btn-second pop-excel" onclick="javascript:delReceiverAll();goAddrExcelUplodPopup();return false;" >엑셀발송</a>		
				</div>
			</div>
		</div>	
</form>		 
	</div>

		<div class="col-md-4">
			<div class="panel panel-default msg-stored">
				<div class="panel-heading">
					<h3 class="panel-title">SMS/LMS 메시지 모음함</h3>
				</div>
			<form:form method="post" modelAttribute="searchMsgBoxVO" id="msgBoxListForm" onsubmit="return false;" > 
			<form:hidden path="currentPageNo"/>
				<div class="panel-body">
					<div class="input-group-btn">
						<select class="form-control" name=grpCd id="grpCd" value="${searchMsgBoxVO.grpCd}" > 
					        <option value="01" <c:if test="${searchMsgBoxVO.grpCd == '01'}">selected="selected" </c:if>>SMS</option>
					        <option value="02" <c:if test="${searchMsgBoxVO.grpCd == '02'}">selected="selected" </c:if>>LMS</option>
				        </select>
				        
				    </div> 
				    <div class="input-group-btn odd">
					    <select class="form-control" name="grpNo" id="grpNo"  value="${searchMsgBoxVO.grpNo}"  >
					        <option value="">그룹</option>
					        <c:forEach var="usrGrpVO" items="${usrGrpList}">
				                 <option value="${usrGrpVO.grpNo}"<c:if test="${usrGrpVO.grpNo == searchMsgBoxVO.grpNo}">selected="selected" </c:if> >${usrGrpVO.grpNm} </option>
				            </c:forEach>	
				        </select>
			        </div> 
					<div class="input-group">
				      <input type="text" class="form-control" name="subject" placeholder="검색할 제목" >
				      <button  id="btnSearch"  class="btn btn-second" >검색</button>
				    </div><!-- /input-group -->
					
				
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
								지금보내기
							</label>
						</li>
						<li>
							<label for="pl02">
								<input type="radio" name="scheduleType" value="RESERVE"  id="pl02" tabon="tab02">
								보내기예약
							</label>
						</li>
						 		
						<li style="display:none;">
							<label for="pl03">
								<input type="radio" name="scheduleType" value="REPEAT" id="pl03" tabon="tab03">
								반복메시지
							</label>
						</li>
						 
					</ul>
					<div class="tabpn" id="tab01">
						<p>메시지를 보낼 시간을 설정하지 않고 즉시 발송합니다.</p>
					</div>
					<div class="tabpn off" id="tab02">
						<span id="spanNow" ></span>
						<span id="spanReserve" >
							<script>Period.SelectBoxFormWrite("reserve");</script>
							<script>Period.SelectBoxTimeFormWrite("reserve");</script>
						</span>
					</div>
					<div class="tabpn off" id="tab03">
						<select name="REPEAT_TYPE" class="form-control unstyled" onchange="RepeatType(this.value)">
							<option value="">반복유형 선택</option>
							<option value="DAY">매일 반복</option>
							<option value="WEEK">주간 반복</option>
							<option value="MONTH">월간 반복</option>
							<option value="5DAY">월~금 반복</option>
						</select>
						<span id="spanRepeatType" style="padding-left:10px;"></span>
						<script>Period.SelectBoxTimeFormWrite("repeat");</script>
					</div>
				</div>
			</div>
			</form> 
		</div>
		 
<!-- //site-content -->
</div>
<!-- 로딩 이미지 -->
<div id="viewLoading">
	<img src="/images/loading52.gif" />
</div>

    