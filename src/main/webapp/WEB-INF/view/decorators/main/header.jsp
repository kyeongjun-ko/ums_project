<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<%
	Object obj = session.getAttribute("SESSION_USER");
	String ulv = null;
	String com = null;
	String mUrl = "https://"+(String)request.getServerName()+":"+request.getServerPort();
	boolean pass = obj==null ? false : true;
	boolean asiana = mUrl.matches(".*asiana.*");
	
	if(pass){
		ulv = (String)session.getAttribute("sessionULv");
		com = (String)session.getAttribute("sessionCom");
		asiana = com.equals("05") ? true : false;
	}
	if (ulv == null) ulv = "00";
	
%>
<div class="site-header row">
	<div class="col-md-3">
		<%if(mUrl.equals("https://airbusan2.sendall.co.kr:444") || mUrl.equals("https://airbusan.sendall.co.kr:444")
			|| mUrl.equals("https://airbusan2.sendall.co.kr:443") || mUrl.equals("https://airbusan.sendall.co.kr:443")) {%>
		<a href="/" title="모바일 안내 UMS 서비스" class="site-logo" width=130 height=50>
			<span class="symbol">
				<img src="/images/airbusan.jpg" alt="에어부산 모바일 안내 UMS 서비스">
			</span>
			<!-- <span class="type">
				<em><b>UMS</b> SERVICE</em>
				<strong>에어부산</strong>
			</span>
			 -->
		</a>
		<%} else if(mUrl.equals("https://hi.sendall.co.kr:444") || mUrl.equals("https://hi.sendall.co.kr:443")){ %>
		<a href="/" title="모바일 안내 UMS 서비스" class="site-logo" width=130 height=50>
			<span class="symbol">
				<img src="/images/hilogo.jpg" alt="현대해상 모바일 안내 UMS 서비스" >
			</span> 
		</a>
		<%} else if(mUrl.equals("https://asiana2.sendall.co.kr:444") || mUrl.equals("https://asiana.sendall.co.kr:444")
				|| mUrl.equals("https://asiana2.sendall.co.kr:443") || mUrl.equals("https://asiana.sendall.co.kr:443")){ %>
		<a href="/" title="모바일 안내 UMS 서비스" class="site-logo" width=130 height=50>
			<span class="symbol">
				<img src="/images/asiana.gif" alt="아시아나 모바일 안내 UMS 서비스" >
			</span> 
		</a>
		<%} else { %>
		<a href="/" title="모바일 안내 UMS 서비스" class="site-logo">
			<span class="symbol">
				<img src="/images/logo.png" alt="모바일 안내 UMS 서비스" >
			</span>
			<span class="type">
				<em><b>UMS</b> SERVICE</em>
				<strong>모바일안내장</strong>
			</span>
		</a>
		<%} %>
	</div>
	<div class="col-md-9">
		<nav class="site-prn">
			<ul>
			<% if(asiana){ %>
				<li>Language : 
					<a href="<%=request.getContextPath()%>/?lang=<spring:message code="locale"/>" >
						<font color="blue"><spring:message code="language"/></font>
					</a>
				</li>
			<%} %>
			<% if( ! pass ){ %>
				<li><a href="<%=request.getContextPath()%>/common/login" title="로그인">로그인</a></li>
				<li><a href="<%=request.getContextPath()%>/getRegUsr" title="회원가입">회원가입</a></li>
				<li><a href="<%=request.getContextPath()%>/seeKIdPw" title="아이디/비밀번호 찾기">아이디/비밀번호 찾기</a></li>
			<%} else {%>
				<li><a href="<%=request.getContextPath()%>/getRegUsr" title="회원가입">회원가입</a></li>
				<li><a href="<%=request.getContextPath()%>/" title="사이트맵">사이트맵</a></li>
			<%} %>
			</ul>
		</nav>
		
		
		<%if(asiana){ %>
		<nav class="site-gnb" role="navigation">
			<ul class="nav nav-pills nav-justified">
				<li>
					<a href="<%=request.getContextPath()%>/ums/sms"  class="dropdown-toggle" data-toggle="dropdown" title="메시지발송">메시지발송</a>
					<ul class="dropdown-menu" role="menu">
						<li><a href="<%=request.getContextPath()%>/ums/sms" title="SMS/LMS">SMS/LMS</a></li>
					</ul>
				</li>
				<li>
					<a href="#"  class="dropdown-toggle" data-toggle="dropdown" title="스마트발송">스마트발송</a>
					<ul class="dropdown-menu" role="menu">
					</ul>
				</li>
				<li>
					<a href="#"  class="dropdown-toggle" data-toggle="dropdown" title="PUSH메시지">PUSH발송</a>
					<ul class="dropdown-menu" role="menu">
					</ul>
				</li>
				<li>
					<a href="#"  class="dropdown-toggle" data-toggle="dropdown" title="전송관리">전송관리</a>
					<ul class="dropdown-menu" role="menu">
						<li><a href="<%=request.getContextPath()%>/trs/transHisList" title="전송이력">전송이력</a></li>
						<li><a href="<%=request.getContextPath()%>/trs/transStatList" title="전송통계">전송통계</a></li>
						<li><a href="<%=request.getContextPath()%>/trs/transRsltList" title="전송결과">전송결과</a></li>
						<li><a href="<%=request.getContextPath()%>/trs/transResvList" title="예약내역">예약내역</a></li>
						<li><a href="<%=request.getContextPath()%>/trs/msgBoxList" title="메시지 모음함">메시지 모음함</a></li>
						 
					</ul>
				</li>
				<li>
					<a href="#"  class="dropdown-toggle" data-toggle="dropdown" title="마이페이지">마이페이지</a>
					<ul class="dropdown-menu" role="menu">
						
						<li><a href="<%=request.getContextPath()%>/mgr/transMySvc" title="My서비스">My서비스</a></li>
						<li><a href="<%=request.getContextPath()%>/mgr/transUseList" title="사용내역">사용내역</a></li>
						
						<li><a href="<%=request.getContextPath()%>/mgr/usrAddrList" title="주소록">주소록</a></li>
						 
						<li><a href="<%=request.getContextPath()%>/mgr/grpMgrList" title="그룹관리">그룹관리</a></li>
						
						<li><a href="<%=request.getContextPath()%>/mgr/getModUsr" title="정보변경">정보변경</a></li>
						<li><a href="<%=request.getContextPath()%>/mgr/getModUsrPw" title="비밀번호변경">비밀번호</a></li>
						
						<li><a href="<%=request.getContextPath()%>/mgr/getGrpCallback" title="발신번호관리">발신번호관리</a></li>
						
						<%if(Integer.valueOf(ulv) > 10){ %>
						<li><a href="<%=request.getContextPath()%>/mgr/getApplUsr" title="가입승인">가입승인</a></li>
						<%} %>
						
					</ul>
				</li>
				<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown" title="서비스소개">서비스소개</a>
					<ul class="dropdown-menu" role="menu">
						<li><a href="<%=request.getContextPath()%>/srv/simpleView1" title="모바일안내장이란?">모바일안내장이란?</a></li>
						<li><a href="<%=request.getContextPath()%>/srv/simpleView2" title="이용안내">이용안내</a></li>
						<li><a href="<%=request.getContextPath()%>/srv/simpleView3" title="이용신청">이용신청</a></li>
						<li><a href="<%=request.getContextPath()%>/srv/noticeList" title="공지사항">공지사항</a></li>
					</ul>
				</li>
			</ul>
		</nav>
		
		<%}else{ %>
		
		<nav class="site-gnb" role="navigation">
			<ul class="nav nav-pills nav-justified">
				<li>
					<a href="<%=request.getContextPath()%>/ums/sms"  class="dropdown-toggle" data-toggle="dropdown" title="메시지발송">메시지발송</a>
					<ul class="dropdown-menu" role="menu">
						<li><a href="<%=request.getContextPath()%>/ums/sms" title="SMS/LMS">SMS/LMS</a></li>
						<li><a href="<%=request.getContextPath()%>/ums/smsExc" title="SMS/LMS">SMS/LMS 대량발송</a></li>
						<li><a href="<%=request.getContextPath()%>/ums/mms" title="MMS">MMS</a></li>
						<li><a href="<%=request.getContextPath()%>/ums/vms" title="VMS">VMS</a></li>
						<!--
						<li><a href="<%=request.getContextPath()%>/ums/vmsQr" title="설문조사">설문조사</a></li>
						-->
						<li><a href="<%=request.getContextPath()%>/ums/fms" title="FMS">FMS</a></li>
					</ul>
				</li>
				<li>
					<a href="#"  class="dropdown-toggle" data-toggle="dropdown" title="스마트발송">스마트발송</a>
					<ul class="dropdown-menu" role="menu">
						<li><a href="<%=request.getContextPath()%>/smart/smartDm" title="SMART DM">SMART DM</a></li>
						<!--
						<li><a href="<%=request.getContextPath()%>/smart/smartFax" title="SMART FAX">SMART FAX</a></li>
						-->
						<%if(Integer.valueOf(ulv) > 30){ %>
						<li><a href="<%=request.getContextPath()%>/smart/smartReg" title="SMART REG">SMART DM 등록</a></li>
						<li><a href="<%=request.getContextPath()%>/smart/smartRightModList" title="SMART MOD">SMART DM 사용자 권한 관리</a></li>
						<%} %>
					</ul>
				</li>
				<li>
					<a href="#"  class="dropdown-toggle" data-toggle="dropdown" title="PUSH메시지">PUSH발송</a>
					<ul class="dropdown-menu" role="menu">
						<li><a href="<%=request.getContextPath()%>/push/pms" title="PUSH보내기">PUSH보내기</a></li>
						<li><a href="<%=request.getContextPath()%>/push/pmsTransRsltList" title="PUSH결과">PUSH결과</a></li>
						<li><a href="<%=request.getContextPath()%>/push/pmsTransStatList" title="PUSH통계">PUSH통계</a></li>
					</ul>
				</li>
				<li>
					<a href="#"  class="dropdown-toggle" data-toggle="dropdown" title="전송관리">전송관리</a>
					<ul class="dropdown-menu" role="menu">
						<li><a href="<%=request.getContextPath()%>/trs/transHisList" title="전송이력">전송이력</a></li>
						<li><a href="<%=request.getContextPath()%>/trs/transStatList" title="전송통계">전송통계</a></li>
						<li><a href="<%=request.getContextPath()%>/trs/transRsltList" title="전송결과">전송결과</a></li>
						<li><a href="<%=request.getContextPath()%>/trs/transResvList" title="예약내역">예약내역</a></li>
						<li><a href="<%=request.getContextPath()%>/trs/msgBoxList" title="메시지 모음함">메시지 모음함</a></li>
						 
					</ul>
				</li>
				<li>
					<a href="#"  class="dropdown-toggle" data-toggle="dropdown" title="마이페이지">마이페이지</a>
					<ul class="dropdown-menu" role="menu">
						
						<li><a href="<%=request.getContextPath()%>/mgr/transMySvc" title="My서비스">My서비스</a></li>
						<li><a href="<%=request.getContextPath()%>/mgr/transUseList" title="사용내역">사용내역</a></li>
						
						<li><a href="<%=request.getContextPath()%>/mgr/usrAddrList" title="주소록">주소록</a></li>
						 
						<li><a href="<%=request.getContextPath()%>/mgr/grpMgrList" title="그룹관리">그룹관리</a></li>
						
						<li><a href="<%=request.getContextPath()%>/mgr/getModUsr" title="정보변경">정보변경</a></li>
						<li><a href="<%=request.getContextPath()%>/mgr/getModUsrPw" title="비밀번호변경">비밀번호</a></li>
						
						<li><a href="<%=request.getContextPath()%>/mgr/getGrpCallback" title="발신번호관리">발신번호관리</a></li>
						
						<%if(Integer.valueOf(ulv) > 10){ %>
						<li><a href="<%=request.getContextPath()%>/mgr/getApplUsr" title="가입승인">가입승인</a></li>
						<%} %>
						
						<%if (Integer.valueOf(ulv) == 99){ %>
						<li><a href="<%=request.getContextPath()%>/mgr/getApplCallback" title="발신번호승인">발신번호승인</a></li>
						<%} %>
					</ul>
				</li>
				<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown" title="서비스소개">서비스소개</a>
					<ul class="dropdown-menu" role="menu">
						<li><a href="<%=request.getContextPath()%>/srv/simpleView1" title="모바일안내장이란?">모바일안내장이란?</a></li>
						<li><a href="<%=request.getContextPath()%>/srv/simpleView2" title="이용안내">이용안내</a></li>
						<li><a href="<%=request.getContextPath()%>/srv/simpleView3" title="이용신청">이용신청</a></li>
						<li><a href="<%=request.getContextPath()%>/srv/noticeList" title="공지사항">공지사항</a></li>
					</ul>
				</li>
			</ul>
		</nav>
		
		<%} %>
	</div>
<!-- //site-header -->
</div>
<form id="ssoEditForm" name="ssoEditForm" method="post" action="">
  <input type="hidden" id="actionID"           name="actionID"           value="ModifyUserInfo" /><!-- 필수 -->
  <input type="hidden" id="serviceID"          name="serviceID"          value="UmsServiceWeb" /> <!-- 필수, 서비스의 appID -->
  <input type="hidden" id="serviceName"        name="serviceName"        value="UmsServiceWeb" /><!-- 필수 -->
  <input type="hidden" id="domain"             name="domain"             value="http://ums.bluechipcns.com"/>  
</form>
<!-- //Header -->
 