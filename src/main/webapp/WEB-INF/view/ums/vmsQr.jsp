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
		
    //save button
    $("#btnSend").click(function() {
    	
    	//if(!checkAny()) return;
        
        var f = document.forms["smsCreateForm"];
        
       /* var max_byte_yn = max_byte("reason", "500");
           if(!max_byte_yn) {
               return;
           }
        if ($("#orderID").val() == null || $("#orderID").val() == '') {
            alert("orderID is null");
            return;
        }
        
        if ($("#serviceID").val() == null || $("#serviceID").val() == '') {
            alert("serviceID is null");
            return;
        }  
        
        var param = {
        	smsVO : $("#smsCreateForm").serialize()
        	orderID : $("#orderID").val()
        }; 
        */
        var param = $("#smsCreateForm").serialize();
        alert("ready!!");
        //if($("#targetSystem").val() != null && $("#targetSystem").val() != '') {
            //send post
            $.ajax({
                type : "POST",
                url : "/ums/sendProc",                
                data : param,
                success : function(json) {
                    if(json.result) {
                        //emailPreviewViewShow(json.email);
                        alert("success!!");
                    } else {
                        alert("nulll!!");
                    }
                },
                error : function(request, status, error) {
                    alert("order list send fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
                }
            });
       // } else {      
       //     checkSubmit();
       //}
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
});



//address excel upload popup 
function goAddrExcelUplodPopup() {
	
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
    // modal 실행 window.showModalDialog 포인트!! 

	//$("#orderLinkOrderGroupSeq").val(jobOrderGroupSeq);
	//$("#orderLinkUserId").val(userId);
	//$("#orderLinkCallerType").val("avail");
	
	var f = document.forms["smsCreateForm"];
    var url = "/ums/addrListPopup";  
    var title = "AddrListPopup";
    
    //windowOpenPost(f, url, null, "yes", title, 500, 500, 50, 50, "yes");
    //var style =" dialogWidth:500px; dialogHeight:500px"; 
    var status = "width=500, height=500, scrollbars=1, toolbar=no, menubar=no resizable=yes";
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

//table render
function renderTableList(count, list, pagingHtml) {
    //
   // $("#totalCount").text(count);
    if(list.length == 0) {
        var html = '<li class="list-group-item">NO DATA	</li>';
        $("#tableList").html(html);
        //list check all clear
        //$("#availListCheckAll").attr("checked", false);
    } else {
        var html = "";
        var unCheckedCount = 0;
        $.each(list, function(index, item) {
            html += '<ul class="list-unstyled">';           
            html += '<li class="list-group-item" title=""><a onclick="Add2MSG('+ item.msgNo + ');" href="javascript:;">' + item.subject + '</a></li>';
            html += '<input type="hidden" id="MSG'+ item.msgNo +'" value="' +item.contents1 + '" /> </li>';
            html += '</ul>'; 
        });
        $("#tableList").html(html);
    }
    $("#paginate").html(pagingHtml);
} 
function CheckSendForm() {
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
		var strReserveDate = new Date(frm.reserveYear.value, frm.reserveMonth.value - 1, frm.reserveDay.value, frm.reserveHour.value, frm.reserveMin.value, "00");

		if( strReserveDate.getTime() <= strNowDateTime.getTime() ) {
			alert("예약 시간을 확인해 주세요.");
			return;
		}
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
	
	
	 alert("ready!!");
	if(Check2Replace() == true) {
		var param = $("#smsCreateForm").serialize();
       
        //if($("#targetSystem").val() != null && $("#targetSystem").val() != '') {
            //send post
        $.ajax({
            type : "POST",
            url : "/ums/sendProc",                
            data : param,
            success : function(json) {
                if(json.result) {
                    //emailPreviewViewShow(json.email);
                    alert("success!!");
                } else {
                    alert("nulll!!");
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

function CheckSendType(strType) {
	switch(strType) {
		case "Now":
			document.getElementById("spanNow").style.display = "block";
			document.getElementById("spanNowText").style.display = "block";
			document.getElementById("spanReserve").style.display = "none";
			document.getElementById("spanReserveText").style.display = "none";
			document.getElementById("spanRepeat").style.display = "none";
			document.getElementById("spanRepeatText").style.display = "none";
			document.getElementById("trReserve").style.display = "none";
		break;
		case "Reserve":
			document.getElementById("spanNow").style.display = "none";
			document.getElementById("spanNowText").style.display = "none";
			document.getElementById("spanReserve").style.display = "block";
			document.getElementById("spanReserveText").style.display = "block";
			document.getElementById("spanRepeat").style.display = "none";
			document.getElementById("spanRepeatText").style.display = "none";
			document.getElementById("trReserve").style.display = "block";
		break;
		case "Repeat":
			document.getElementById("spanNow").style.display = "none";
			document.getElementById("spanNowText").style.display = "none";
			document.getElementById("spanReserve").style.display = "none";
			document.getElementById("spanReserveText").style.display = "none";
			document.getElementById("spanRepeat").style.display = "block";
			document.getElementById("spanRepeatText").style.display = "block";
			document.getElementById("trReserve").style.display = "block";
		break;
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

 
<form id="smsCreateForm" name="frmSMS" method="POST" onsubmit="return false;"  autocomplete="off" style="display:inline;"> <!-- onsubmit="return false;" -->
<input type="hidden" name="destCount" id="destCount" value="0" />
<input type="hidden" name="destType" id="destType" value="0" />
<input type="hidden" name="MSGTYPE" id="MSGTYPE" value="VMSQR" />
<input type="hidden" name="RECEIVER_CNT" id="RECEIVER_CNT" value="0" />
<input type="hidden" name="SEND_COUNT" id="SEND_COUNT" value="500" />
<input type="hidden" name="CONVERT_TYPE" id="CONVERT_TYPE" value="FALSE" />
<!-- //shorcuts -->
<div class="col-md-9">
	<div class="col-md-12 tits">
		<h1>설문조사</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-home"></i></a></li>
			<li><a href="#">메시지발송</a></li>
			<li class="active">설문조사</li>
		</ol>
		<div class="hbg">
			<i class="fa fa-envelope odd"></i>
			<i class="fa fa-globe "></i>
		</div>
	</div>
	
	<div class="col-md-4">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">음성</h3>
			</div>
			<div class="panel-body">
				<ul class="list-inline radio">
					<li>
						<label for="sl01">
							<input type="radio" name="smartdmfax" id="sl01" tabon="tab01" checked="1">
							남성
						</label>
					</li>
					<li>
						<label for="sl02">
							<input type="radio" name="smartdmfax" id="sl02" tabon="tab02">
							여성
						</label>
					</li>		
				</ul>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">머리말</h3>
			</div>
			<div class="panel-body">
				<textarea class="form-control" name="" id="" rows="3" placeholder="소개말을 작성하여 주십시오. 스팸으로 오해할 수 있으니 본인임을 알리시는 것이 좋습니다.
				"></textarea>
			</div>
		</div>		
		<div class="panel panel-default questions">
			<div class="panel-heading">
				<h3 class="panel-title">설문문항수</h3>
			</div>
			<div class="panel-body pbody01">
				<select name="" id="" class="form-control">
					<option value="">1</option>
					<option value="">2</option>
					<option value="">3</option>
					<option value="">4</option>
					<option value="">5</option>
				</select>
				<small>5개 까지 설정이 가능합니다.</small>
			</div>
			<div class="panel-body pbody02">
				<strong>설문 1</strong> 
				<em>답변항목</em> 
				<select name="" id="" class="form-control">
					<option value="">1</option>
					<option value="">2</option>
					<option value="">3</option>
					<option value="">4</option>
					<option value="">5</option>
					<option value="">6</option>
					<option value="">7</option>
					<option value="">8</option>
					<option value="">9</option>
				</select>
				<small>9개 까지 설정가능</small>
				<textarea name="" id="" cols="30" rows="2" placeholder="설문내용" class="form-control"></textarea>
				<div class="counting">0/1000 Byte</div>
			</div>
			<div class="panel-body pbody02">
				<strong>설문 2</strong> 
				<em>답변항목</em> 
				<select name="" id="" class="form-control">
					<option value="">1</option>
					<option value="">2</option>
					<option value="">3</option>
					<option value="">4</option>
					<option value="">5</option>
					<option value="">6</option>
					<option value="">7</option>
					<option value="">8</option>
					<option value="">9</option>
				</select>
				<small>9개 까지 설정가능</small>
				<textarea name="" id="" cols="30" rows="2" placeholder="설문내용" class="form-control"></textarea>
				<div class="counting">0/1000 Byte</div>
			</div>
		</div>		
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">맺음말</h3>
			</div>
			<div class="panel-body">
				<textarea class="form-control" name="" id="" rows="2" placeholder="끝인사 외 내용에 대한 문의사항이 있을 시 연락처 등을 남깁니다."></textarea>
			</div>
		</div>		
	</div>
		
		 
	
	
	
				
	<div class="col-md-4">		
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">회신번호</h3>
			</div>
			<div class="panel-body">
				<input type="text" name="callback" id="SENDER" value="" class="text" style="display:none;" onkeydown="OnlyNumPhoneNumScript(this,event);" />
				<select class="form-control textinput-selector"   id="SELECT_SENDER" onchange="SenderSelect(this.value, this.form)">
					<option value="">회신번호</option>
					<c:if test="${! empty usrCallBackList}">
                        <c:forEach var="item" items="${usrCallBackList}">
                           <c:if test="${! empty item.codeName}">
                           <option value="${item.codeName}">${item.codeName}</option>
                           </c:if>
                        </c:forEach>
                        	<!-- <option value="DIRECT">직접입력</option> -->
                    </c:if>
				</select>	
				<span id="spanSelectBox" onclick="SenderSelect('', document.getElementById('SELECT_SENDER').form)" class="form-control textinput" style="display:none;">
					<button class="btn btn-second" >선택</button>
				</span>
			</div>
		</div>	
						
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">제목</h3>
			</div>
			<div class="panel-body">
				<textarea class="form-control" name="subject" id="" rows="2" placeholder="제목을 입력해 주세요.(발송이력에 표시 됩니다.)"></textarea>
			</div>
		</div>	
						
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">수신자 목록</h3>
				<div class="rcvsum">
				총 <span id="spanReceiverCount"><strong>0</strong></span> 명
				</div>	
			</div>
			<div class="panel-body">
				<div class="form-group">
					<input type="text" id="AddReceiveName" name="AddReceiveName" onfocus="defaultMsgFocus(this, '이름');this.select()" onblur="defaultMsgBlur(this, '이름')" class="form-control name" placeholder="이름" />
					<input type="text" id="AddReceiveNumber" name="AddReceiveNumber"  onfocus="defaultMsgFocus(this, '전화번호(지역번호 포함)');this.select()" onblur="defaultMsgBlur(this, '전화번호(지역번호 포함)')" onkeydown="OnlyNumPhoneNumScript(this,event);if(13==event.keyCode){addFormReceiver(this.form);}" class="form-control number" placeholder="휴대폰번호" />
				</div>
				<button class="btn btn-second"  onclick="if( NumberCheck((frmSMS.AddReceiveNumber.value).trim().replace(/\-/g,'')) ) {addFormReceiver(document.smsCreateForm); } else { alert('숫자만 입력 가능합니다.'); }">+</button>
			</div> 
			<div class="panel-body divider">
				<ul class="list-unstyled" id="divReceiverList" > </ul>
				<div id="divReceiverInfo" style="display:none;"> </div>
			</div>
			<div class="panel-body">
				<div class="btns">
					<a class="btn btn-second" onclick="delReceiverAll();" >전체삭제</a> 	
				</div>
				<div class="btns"> 
					<a class="btn btn-second pop-add" onclick="javascript:goAddrListPopup();return false;" >주소록 불러오기</a>
					<a class="btn btn-second pop-excel" onclick="goAddrExcelUplodPopup();" >엑셀발송</a>		
				</div>
			</div>
		</div>	
		
		
						
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
					<li>
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
					<span id="spanNow" >&nbsp;</span>
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
			<div class="checkbox">
			    <label>
				    <input type="checkbox">메시지 저장
			    </label>
			</div>
			<div class="btns">
				<button onclick="CheckSendForm();"  class="btn btn-primary btn-lg">보내기</button>
				<button onclick="document.location.reload();" class="btn btn-primary btn-lg">재입력</button>   
			</div>
		</div>
	</div>
</form> 
		<div class="col-md-4">
			<div class="panel panel-default msg-stored">
				<div class="panel-heading">
					<h3 class="panel-title">VMS 메시지 모음함</h3>
				</div>
			<form:form method="post" modelAttribute="searchMsgBoxVO" id="msgBoxListForm"> 
			<form:hidden path="currentPageNo"/>
			<form:hidden path="grpCd" value="01" />	
				<div class="panel-body">
					<div class="input-group">
				      <div class="input-group-btn">
				        <select class="form-control unstyled" name="grpNo" >
				        <option value="">그룹</option>
				        <c:forEach var="usrGrpVO" items="${usrGrpList}">
			                 <option value="${usrGrpVO.grpNo}">${usrGrpVO.grpNm} </option>
			            </c:forEach>	
			            </select>	          
				      </div><!-- /btn-group --> 
				      <input type="text" class="form-control" name="subject" placeholder="검색할 제목">
				    </div><!-- /input-group -->
					<button  id="btnSearch"  class="btn btn-second">검색</button>
				
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
					 
					<div class="text-center" >
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

 

    