<%@ page language="java"  contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<%
	Object obj = session.getAttribute("SESSION_USER");
	String uid = (String)session.getAttribute("sessionUId");
	
	String ulv = null;
	String com = null;
	String mUrl = "https://"+(String)request.getServerName()+":"+request.getServerPort();
	boolean pass = obj==null ? false : true;
	boolean asiana = false;
	
	if(pass){
		ulv = (String)session.getAttribute("sessionULv");
		com = (String)session.getAttribute("sessionCom");
		asiana = mUrl.matches(".*asiana.*");
		asiana = com.equals("05") ? true : false;
		//out.println("*****com = "+ com);
	}
	
	if (ulv == null) ulv = "00";
	String uri = (String)request.getRequestURI();
	//out.println("*****asiana = "+ asiana);
 
%>
<c:set var="uri"><%=request.getRequestURI()%></c:set>
<script type="text/javascript">
$(document).ready(function(){
	
	
	$('input[name="userId"]').focus();
	$('input[name="userId"]').keyup(function(e){
		if(e.keyCode == 13) $('input[name="userPw"]').focus();
	});
	$('input[name="userPw"]').keyup(function(e){
		if(e.keyCode == 13) fnLogin();
	});
	
	var param = null; 
	function getParameters(){ 
		var uri = location.href;
        var param = null; 
        var params = new Object; 
        param = uri.split("/");  
        if(param.length > 1){  
        	//alert(param.length);
        	//alert(param);
			params["topmenu"] = param[3];  
			params["submenu"] = param[4];
			
			params.getString = function(str){  
			if(this[str]) return this[str];  
	            else return null;
			}  
            params.getNumber = function(str){  
			if(this[str]) return Number(this[str]);  
				else return null;  
			}  
			return params;  
		}  
		return null;  
	} 
	
	param = getParameters();  
    var topmenu = ""; 
    var submenu = ""; 
    
    $('#leftums').hide();
    $('#leftsmart').hide();
	$('#leftpush').hide();
	$('#lefttrs').hide();
	$('#leftmgr').hide();
	$('#leftsrv').hide();
	$('#directlink1').hide();
	$('#directlink2').hide();
	
	
    if(param != null){  
    	topmenu = param.getString("topmenu");  
        submenu = param.getString("submenu");  
	}
    
    if(topmenu != null){
    	$('#directlink1').hide();
    	$('#directlink2').show();
    }else{
    	$('#directlink1').show();
    	$('#directlink2').hide();
    }
		
	$('#left'+topmenu).show();
	
	$('#1left'+topmenu+submenu).addClass( "on"); 
	$('#2left'+topmenu+submenu).switchClass("evn","on"); 
      
	//alert(topmenu);
	//alert(submenu);
	
	var saveIdchk = $.cookie("saveIdchk");  
	var userId = $.cookie("userId");
	//alert(saveIdchk+'-'+userId);
	
    if(saveIdchk != null && saveIdchk != -1){
     $("#userId").val(userId);
     $("#saveid").attr("checked","checked"); 
    }

	$("#saveid").bind('click', function (){  
	     var ischecked = $("#saveid").is(":checked");
	        
	     if(ischecked){
	      $.cookie("userId",$('#userId').val(), { expires:365, path: '/' });
	      $.cookie("saveIdchk",$('#userId').val(), { expires:365, path: '/' });
	     }else{
	      $.cookie('userId', '', { expires: -1, path: '/' });
	      $.cookie('saveIdchk', '', { expires: -1, path: '/' });
	     }
	   
	    var userId = $.cookie("userId");  
	    if(userId != null && userId != -1){
	     $("#userId").val(userId);
	     $("#saveid").attr("checked","checked"); 
	    }
	 });
	
});



function fnLogin() {
	if ($('#userId').val() == '' || $('#userId').val() == null) {
		alert('아이디를 입력해주세요');
		$('#userId').focus();
		return;
	}
	
	if ($('#userPw').val() == '' || $('#userPw').val() == null) {
		alert('비밀번호를 입력해주세요');
		$('#userPw').focus();
		return;
	}
	
	$("#loginForm").attr({
        action : "/loginRegist",
        method : "post"
    }).submit();
}

function fnLogOut() {
	
	$("#loginForm").attr({
        action : "/logout",
        method : "post"
    }).submit();
}

function fnModUsrOut() {
	
	$("#loginForm").attr({
        action : "/getModUsr",
        method : "post"
    }).submit();
}


</script> 
<script> 
function getCookie(Name) {
    var search = Name + "=";
    if (document.cookie.length > 0) { // 쿠키가 설정되어 있다면
      offset = document.cookie.indexOf(search);
      if (offset != -1) { // 쿠키가 존재하면
        offset += search.length;
        // set index of beginning of value
        end = document.cookie.indexOf(";", offset);
        // 쿠키 값의 마지막 위치 인덱스 번호 설정
        if (end == -1)
          end = document.cookie.length;
        return unescape(document.cookie.substring(offset, end));
      }
    }
    return "";
}
  
<%if(asiana && uri.equals("/")){%>

 /* if(getCookie("Notice4") != "done"){	
	noticeWindow = window.open('/srv/noticeDetailPopup2?noticeNo=4','_blank','width=478,height=450,scrollbar=no');
	noticeWindow.opener.self;
}  */

<%}%>
 
 /* if(getCookie("Notice5") != "done"){	
	noticeWindow = window.open('/srv/noticeDetailPopup2?noticeNo=5','_blank','width=478,height=500,scrollbar=no');
	noticeWindow.opener.self;
} 
  */

  /*   if(getCookie("Notice3") != "done"){	
    noticeWindow = window.open('/srv/noticeDetailPopup2?noticeNo=3','_blank','width=478,height=500,scrollbar=no');
	noticeWindow.opener.self;
}  */ 
 
</script>

	<div class="col-md-3">
	<form:form method="post" modelAttribute="umsUserVO" id="loginForm"> 
				    <input type="hidden" name="serviceID"          value="<c:out value="${serviceID}"/>" />
				    <input type="hidden" name="domain"             value="<c:out value="${domain}"/>" />
				    <input type="hidden" name="registURL"          value="<c:out value="${registURL}"/>" />
				    <input type="hidden" name="returnURL"          value="<c:out value="${returnURL}"/>" />
				    <input type="hidden" name="isReturn"           value="N" />
		<% if( ! pass ){ %>

		<div class="loginbox panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">
					<i class="fa fa-key"></i>
					<spring:message code="login"/>
				</h3>
			</div>
			<div class="panel-body">
					<div class="form-group">
						<label for="exampleInputEmail1">아이디</label>
						<input type="text" class="form-control" id="userId" name="userId" placeholder="<spring:message code="id"/>">
					</div>
					<div class="form-group">
						<label for="exampleInputPassword1">비밀번호</label>
						<input type="password" class="form-control"  id="userPw" name="userPw" placeholder="<spring:message code="password"/>">
					</div>
					<div class="checkbox">
						<label for="checkbox1">
							<input type="checkbox" value="" id="saveid" Name="saveid" data-toggle="checkbox">
							ID 저장
						</label>
					</div>
					<button type="submit" class="btn btn-primary" onclick="javascript:fnLogin()"><spring:message code="login"/></button>
				
				<div class="links">
					<a href="<%=request.getContextPath()%>/getRegUsr" title="회원가입">회원가입</a>
					<a href="<%=request.getContextPath()%>/seeKIdPw" title="아이디/비밀번호 찾기">아이디/비밀번호 찾기</a>
				</div>
			</div>
		</div>
		<% } %>
		<% if( pass ){ %>
		<!-- //로그인후 -->
		<div class="loginbox panel panel-default login">
			<div class="panel-heading">
				<h3 class="panel-title">
					<i class="fa fa-key"></i>
					<spring:message code="login"/>
				</h3>
			</div>
			<div class="panel-body">
				<div class="welcome"><em><b class="userid"> <%=uid %></b>님</em>환영합니다.</div>
				<div class="btns">
					<button type="submit" class="btn btn-second" onclick="javascript:fnLogOut()" >로그아웃</button>
					<button type="submit" class="btn btn-third" onclick="javascript:fnModUsrOut()">회원정보수정</button>
				</div>
			</div>
		</div>
		<% } %>
	</form:form>
		<!-- //loginbox -->
		<%
		if(asiana){
		%> 
		<div class="shortcuts panel panel-default" id="directlink1">
			<div class="panel-heading">
				<h3 class="panel-title">
					<i class="fa fa-star"></i>
					바로가기
				</h3>
			</div>
			<ul>
				
				<li>
					<a href="<%=request.getContextPath()%>/ums/sms" title="문자">
						<i class="fa fa-comments"></i>
						<em>문자</em>
					</a>
				</li>
				
				<li>
					<a href="<%=request.getContextPath()%>/trs/transRsltList" title="전송결과">
						<i class="fa fa-share-square-o"></i>
						<em>전송결과</em>
					</a>
				</li>
				
				<li>
					<a href="<%=request.getContextPath()%>/srv/simpleView2" title="이용안내">
						<i class="fa fa-info-circle"></i>
						<em>이용안내</em>
					</a>
				</li>
				<li>
					<a href="<%=request.getContextPath()%>/mgr/transMySvc" title="마이페이지">
						<i class="fa fa-user"></i>
						<em>마이페이지</em>
					</a>
				</li>
				<% if( pass ){ %>
				<li>
					<a href="https://ums.sendall.co.kr:444/fileupload/image/mobile.pdf" title="메뉴얼">
						<i class="fa fa-file-pdf-o"></i>
						<em>메뉴얼</em>
					</a>
				</li>
				<%} %>
			</ul>
		</div>
		<!-- //shorcuts --> 
		
<!-- //other -->
		<div class="sidemenu" id="leftums">
			<h1>메시지 발송</h1>
			<ul class="list-unstyled">
				<li id="1leftumssms">
				<a href="<%=request.getContextPath()%>/ums/sms">SMS/LMS<i class="fa fa-chevron-right"></i></a>
				</li>
			</ul>
		</div> 
		<div class="sidemenu" id="leftsmart">
			<h1>스마트 발송</h1>
		</div> 
		<div class="sidemenu" id=leftpush>
			<h1>PUSH발송</h1>
		</div> 
		<div class="sidemenu" id="lefttrs">
			<h1>전송관리</h1>
			<ul class="list-unstyled">
				<li  id="1lefttrstransHisList">
				<a href="<%=request.getContextPath()%>/trs/transHisList">전송 이력<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="2lefttrstransStatList" class="evn">
				<a href="<%=request.getContextPath()%>/trs/transStatList">전송 통계<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="1lefttrstransRsltList">
				<a href="<%=request.getContextPath()%>/trs/transRsltList">전송 결과<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="2lefttrstransResvList" class="evn">
				<a href="<%=request.getContextPath()%>/trs/transResvList">예약 내역<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="1lefttrsmsgBoxList">
				<a href="<%=request.getContextPath()%>/trs/msgBoxList">메시지 모음함<i class="fa fa-chevron-right"></i></a>
				</li>
			</ul>
		</div>
		<div class="sidemenu" id="leftmgr">
			<h1>마이 페이지</h1>
			<ul class="list-unstyled">
				<li  id="1leftmgrtransMySvc">
				<a href="<%=request.getContextPath()%>/mgr/transMySvc">My 서비스<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="2leftmgrtransUseList" class="evn">
				<a href="<%=request.getContextPath()%>/mgr/transUseList">사용내역<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="1leftmgrusrAddrList">
				<a href="<%=request.getContextPath()%>/mgr/usrAddrList">주소록<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="2leftmgrgrpMgrList" class="evn">
				<a href="<%=request.getContextPath()%>/mgr/grpMgrList">그룹관리<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="1leftmgrgetModUsr">
				<a href="<%=request.getContextPath()%>/mgr/getModUsr">정보변경<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="2getGrpCallback" class="evn">
				<a href="<%=request.getContextPath()%>/mgr/getGrpCallback">발신번호관리<i class="fa fa-chevron-right"></i></a>
				</li>
				<%if(Integer.valueOf(ulv) > 10){ %>
				<li  id="1leftmgrgetApplUsr">
				<a href="<%=request.getContextPath()%>/mgr/getApplUsr">가입승인<i class="fa fa-chevron-right"></i></a>
				</li>
				<%} %>
			</ul>
		</div> 
		<div class="sidemenu" id="leftsrv">
			<h1>서비스 소개</h1>
			<ul class="list-unstyled">
				<li  id="1leftsrvsimpleView1">
				<a href="<%=request.getContextPath()%>/srv/simpleView1">모바일안내장이란?<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="2leftsrvsimpleView2" class="evn">
				<a href="<%=request.getContextPath()%>/srv/simpleView2">이용안내<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="1leftsrvsimpleView3">
				<a href="<%=request.getContextPath()%>/srv/simpleView3">이용신청<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="2leftsrvnoticeList" class="evn">
				<a href="<%=request.getContextPath()%>/srv/noticeList">공지사항<i class="fa fa-chevron-right"></i></a>
				</li>
			</ul>
		</div> 
		 
		<div class="shortcuts panel panel-default sub" id="directlink2">
			<div class="panel-heading">
				<h3 class="panel-title">
					<i class="fa fa-star"></i>
					바로가기
				</h3>
			</div>
			<ul>
				<li>
					<a href="<%=request.getContextPath()%>/trs/transRsltList" title="전송결과">
						<i class="fa fa-share-square-o"></i>
						<em>전송결과</em>
					</a>
				</li>
				<li>
					<a href="<%=request.getContextPath()%>/mgr/usrAddrList" title="주소록">
						<i class="fa fa-group"></i>
						<em>주소록</em>
					</a>
				</li>
				<li>
					<a href="<%=request.getContextPath()%>/trs/transResvList" title="예약내역">
						<i class="fa fa-calendar"></i>
						<em>예약내역</em>
					</a>
				</li>
				<li>
					<a href="<%=request.getContextPath()%>/srv/simpleView2" title="이용안내">
						<i class="fa fa-info-circle"></i>
						<em>이용안내</em>
					</a>
				</li>
				<!-- <li>
					<a href="<%=request.getContextPath()%>/mgr/transMySvc" title="마이페이지">
						<i class="fa fa-user"></i>
						<em>마이페이지</em>
					</a>
				</li> -->
			</ul>
		</div>
	  	<%} else{ %>
	  	<div class="shortcuts panel panel-default" id="directlink1">
			<div class="panel-heading">
				<h3 class="panel-title">
					<i class="fa fa-star"></i>
					바로가기
				</h3>
			</div>
			<ul>
				<li>
					<a href="<%=request.getContextPath()%>/ums/smsPopUp" onClick="window.open(this.href, '', 'width=600, height=800, scrollbars=1, toolbar=no, menubar=no resizable=yes'); return false;" title="문자">
						<i class="fa fa-comments"></i>
						<em>간편문자</em>
					</a>
				</li>
				<li>
					<a href="<%=request.getContextPath()%>/smart/smartDmPopUp" onClick="window.open(this.href, '', 'width=600, height=800, scrollbars=1, toolbar=no, menubar=no resizable=yes'); return false;" title="SMART DM">
						<i class="fa fa-desktop"></i>
						<em>간편 DM</em>
					</a>
				</li> 
				
				<li>
					<a href="<%=request.getContextPath()%>/ums/vms" title="음성">
						<i class="fa fa-volume-up"></i>
						<em>음성</em>
					</a>
				</li>
				<li>
					<a href="<%=request.getContextPath()%>/ums/fms" title="FAX">
						<i class="fa fa-fax"></i>
						<em>FAX</em>
					</a>
				</li>
				<li>
					<a href="<%=request.getContextPath()%>/ums/sms" title="문자">
						<i class="fa fa-comments"></i>
						<em>문자</em>
					</a>
				</li>
				<li>
					<a href="<%=request.getContextPath()%>/ums/mms" title="MMS">	
						<i class="fa fa-envelope"></i>
						<em>MMS</em>
					</a>
				</li>
				<li>
					<a href="<%=request.getContextPath()%>/smart/smartDm" title="SMART DM">
						<i class="fa fa-desktop"></i>
						<em>SMART DM</em>
					</a>
				</li>
				<li>
					<a href="<%=request.getContextPath()%>/trs/transRsltList" title="전송결과">
						<i class="fa fa-share-square-o"></i>
						<em>전송결과</em>
					</a>
				</li>
				
				<li>
					<a href="<%=request.getContextPath()%>/srv/simpleView2" title="이용안내">
						<i class="fa fa-info-circle"></i>
						<em>이용안내</em>
					</a>
				</li>
				<li>
					<a href="<%=request.getContextPath()%>/mgr/transMySvc" title="마이페이지">
						<i class="fa fa-user"></i>
						<em>마이페이지</em>
					</a>
				</li>
				<% if( pass ){ %>
				<li>
					<a href="https://ums.sendall.co.kr:444/fileupload/image/mobile.pdf"   target="_blank"  title="메뉴얼">
						<i class="fa fa-file-pdf-o"></i>
						<em>메뉴얼</em>
					</a>
				</li>
				<%} %>
			</ul>
		</div>
		<!-- //shorcuts --> 
<!-- //other -->
		<div class="sidemenu" id="leftums">
			<h1>메시지 발송</h1>
			<ul class="list-unstyled">
				<li id="1leftumssms">
				<a href="<%=request.getContextPath()%>/ums/sms">SMS/LMS<i class="fa fa-chevron-right"></i></a>
				</li>				
				<li id="1leftumsexcel">
				<a href="<%=request.getContextPath()%>/ums/smsExc">SMS/LMS 대량발송<i class="fa fa-chevron-right"></i></a>
				</li>
				<li id="2leftumsmms" class="evn">
				<a href="<%=request.getContextPath()%>/ums/mms">MMS<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="1leftumsvms">
				<a href="<%=request.getContextPath()%>/ums/vms">VMS<i class="fa fa-chevron-right"></i></a>
				</li>
				<!-- 
				<li  id="2leftumsvmsQr" class="evn">
				<a href="<%=request.getContextPath()%>/ums/vmsQr">설문조사<i class="fa fa-chevron-right"></i></a>
				</li>
				 -->
				<li  id="1leftumsfms">
				<a href="<%=request.getContextPath()%>/ums/fms">FMS<i class="fa fa-chevron-right"></i></a>
				</li>
			</ul>
		</div> 
		<div class="sidemenu" id="leftsmart">
			<h1>스마트 발송</h1>
			<ul class="list-unstyled">
				<li  id="1leftsmartsmartDm">
				<a href="<%=request.getContextPath()%>/smart/smartDm">SMART DM<i class="fa fa-chevron-right"></i></a>
				</li>
				<!-- 
				<li  id="2leftsmartsmartFax" class="evn">
				<a href="<%=request.getContextPath()%>/smart/smartFax">SMART FAX<i class="fa fa-chevron-right"></i></a>
				</li>
				 -->
				<%if(Integer.valueOf(ulv) > 30){ %>
				<li  id="2leftsmartsmartReg"  class="evn">
				<a href="<%=request.getContextPath()%>/smart/smartReg">SMART DM등록<i class="fa fa-chevron-right"></i>
				</a></li>
				<li  id="1leftsmartsmartRightModList">
				<a href="<%=request.getContextPath()%>/smart/smartRightModList">SMART DM 사용자 권한 관리<i class="fa fa-chevron-right"></i>
				</a></li>
				<%} %>
				 
			</ul>
		</div> 
		<div class="sidemenu" id=leftpush>
			<h1>PUSH발송</h1>
			<ul class="list-unstyled">
				<li  id="1leftpushpms">
				<a href="<%=request.getContextPath()%>/push/pms">PUSH보내기<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="1leftpushpmsTransRsltList">
				<a href="<%=request.getContextPath()%>/push/pmsTransRsltList">PUSH결과<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="1leftpushpmsTransStatList">
				<a href="<%=request.getContextPath()%>/push/pmsTransStatList">PUSH통계<i class="fa fa-chevron-right"></i></a>
				</li>
			</ul>
		</div> 
		<div class="sidemenu" id="lefttrs">
			<h1>전송관리</h1>
			<ul class="list-unstyled">
				<li  id="1lefttrstransHisList">
				<a href="<%=request.getContextPath()%>/trs/transHisList">전송 이력<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="2lefttrstransStatList" class="evn">
				<a href="<%=request.getContextPath()%>/trs/transStatList">전송 통계<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="1lefttrstransRsltList">
				<a href="<%=request.getContextPath()%>/trs/transRsltList">전송 결과<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="2lefttrstransResvList" class="evn">
				<a href="<%=request.getContextPath()%>/trs/transResvList">예약 내역<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="1lefttrsmsgBoxList">
				<a href="<%=request.getContextPath()%>/trs/msgBoxList">메시지 모음함<i class="fa fa-chevron-right"></i></a>
				</li>
			</ul>
		</div>
		<div class="sidemenu" id="leftmgr">
			<h1>마이 페이지</h1>
			<ul class="list-unstyled">
				<li  id="1leftmgrtransMySvc">
				<a href="<%=request.getContextPath()%>/mgr/transMySvc">My 서비스<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="2leftmgrtransUseList" class="evn">
				<a href="<%=request.getContextPath()%>/mgr/transUseList">사용내역<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="1leftmgrusrAddrList">
				<a href="<%=request.getContextPath()%>/mgr/usrAddrList">주소록<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="2leftmgrgrpMgrList" class="evn">
				<a href="<%=request.getContextPath()%>/mgr/grpMgrList">그룹관리<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="1leftmgrgetModUsr">
				<a href="<%=request.getContextPath()%>/mgr/getModUsr">정보변경<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="2getGrpCallback" class="evn">
				<a href="<%=request.getContextPath()%>/mgr/getGrpCallback">발신번호관리<i class="fa fa-chevron-right"></i></a>
				</li>
				<%if(Integer.valueOf(ulv) > 10){ %>
				<li  id="1leftmgrgetApplUsr">
				<a href="<%=request.getContextPath()%>/mgr/getApplUsr">가입승인<i class="fa fa-chevron-right"></i></a>
				</li>
				<%} %>
				<%if(Integer.valueOf(ulv) == 99){ %>
				<li  id="2leftmgrgetApplCallback" class="evn">
				<a href="<%=request.getContextPath()%>/mgr/getApplCallback">발신번호승인<i class="fa fa-chevron-right"></i></a>
				</li>
				<%} %>
				
			</ul>
		</div> 
		<div class="sidemenu" id="leftsrv">
			<h1>서비스 소개</h1>
			<ul class="list-unstyled">
				<li  id="1leftsrvsimpleView1">
				<a href="<%=request.getContextPath()%>/srv/simpleView1">모바일안내장이란?<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="2leftsrvsimpleView2" class="evn">
				<a href="<%=request.getContextPath()%>/srv/simpleView2">이용안내<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="1leftsrvsimpleView3">
				<a href="<%=request.getContextPath()%>/srv/simpleView3">이용신청<i class="fa fa-chevron-right"></i></a>
				</li>
				<li  id="2leftsrvnoticeList" class="evn">
				<a href="<%=request.getContextPath()%>/srv/noticeList">공지사항<i class="fa fa-chevron-right"></i></a>
				</li>
			</ul>
		</div> 
		 
		<div class="shortcuts panel panel-default sub" id="directlink2">
			<div class="panel-heading">
				<h3 class="panel-title">
					<i class="fa fa-star"></i>
					바로가기
				</h3>
			</div>
			<ul>
				<li>
					<a href="<%=request.getContextPath()%>/ums/smsPopUp" onClick="window.open(this.href, '', 'width=600, height=800, scrollbars=1, toolbar=no, menubar=no resizable=yes'); return false;" title="문자">
						<i class="fa fa-comments"></i>
						<em>간편문자</em>
					</a>
				</li>
				<li>
					<a href="<%=request.getContextPath()%>/smart/smartDmPopUp" onClick="window.open(this.href, '', 'width=600, height=800, scrollbars=1, toolbar=no, menubar=no resizable=yes'); return false;" title="SMART DM">
						<i class="fa fa-desktop"></i>
						<em>간편 DM</em>
					</a>
				</li>
				<li>
					<a href="<%=request.getContextPath()%>/ums/mmsPopUp" onClick="window.open(this.href, '', 'width=600, height=800, scrollbars=1, toolbar=no, menubar=no resizable=yes'); return false;" title="MMS">	
						<i class="fa fa-envelope"></i>
						<em>간편MMS</em>
					</a>
				</li>
				<li>
					<a href="<%=request.getContextPath()%>/ums/fms" title="FAX">
						<i class="fa fa-fax"></i>
						<em>FAX</em>
					</a>
				</li>
				<li>
					<a href="<%=request.getContextPath()%>/ums/vms" title="음성">
						<i class="fa fa-volume-up"></i>
						<em>음성</em>
					</a>
				</li>
				<li>
					<a href="<%=request.getContextPath()%>/trs/transRsltList" title="전송결과">
						<i class="fa fa-share-square-o"></i>
						<em>전송결과</em>
					</a>
				</li>
				<li>
					<a href="<%=request.getContextPath()%>/mgr/usrAddrList" title="주소록">
						<i class="fa fa-group"></i>
						<em>주소록</em>
					</a>
				</li>
				<li>
					<a href="<%=request.getContextPath()%>/trs/transResvList" title="예약내역">
						<i class="fa fa-calendar"></i>
						<em>예약내역</em>
					</a>
				</li>
				<li>
					<a href="<%=request.getContextPath()%>/srv/simpleView2" title="이용안내">
						<i class="fa fa-info-circle"></i>
						<em>이용안내</em>
					</a>
				</li>
				<!-- <li>
					<a href="<%=request.getContextPath()%>/mgr/transMySvc" title="마이페이지">
						<i class="fa fa-user"></i>
						<em>마이페이지</em>
					</a>
				</li> -->
			</ul>
		</div>
	  	<%}%>
		
	<!-- //shorcuts -->
	</div>
<!-- LeftMenu -->
