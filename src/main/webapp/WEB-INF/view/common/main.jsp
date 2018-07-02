<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>    
<script type="text/javascript">
 $(document).ready(function() {
	//showDetail(2);
});
     function showDetail(noticeNo) {
        var title = "noticePopup";
        var status = "width=478,height=500, scrollbars=1, toolbar=no, menubar=no resizable=yes";
        poptarget = window.open("/srv/noticeDetailPopup?noticeNo=" +noticeNo , title, status);
    }  
    
 </script>
			<div class="col-md-9">
				<div class="main-landing">
					<div class="el el01 on">
						<img src="images/main/p01.png" alt="">
						<h1>문자</h1>
						<h2>빠르고 간편한<br>SMS/MMS<br>전송서비스</h2>
						<i class="fa fa-comments"></i>
					</div>
					<div class="el el02">
						<img src="images/main/p02.png" alt="">
						<h1>음성</h1>
						<h2>음성으로<br>녹음된 메시지<br>전송서비스</h2>
						<i class="fa fa-volume-up"></i>
					</div>
					<div class="el el03">
						<img src="images/main/p03.png" alt="">
						<h1>SMART<br>DM</h1>
						<h2>모바일 우편서비스와<br>모바일 문서전달<br> 서비스</h2>
						<i class="fa fa-desktop"></i>
					</div>
					<div class="el el04">
						<img src="images/main/p04.png" alt="">
						<h1>설문</h1>
						<h2>복잡한<br>설문조사를<br>간편하게</h2>
						<i class="fa fa-pie-chart"></i>
					</div>
					<div class="el el05">
						<img src="images/main/p05.png" alt="">
						<h1>팩스</h1>
						<h2>장비없이<br>팩스전송이<br>가능한 서비스</h2>
						<i class="fa fa-fax"></i>
					</div>
				</div>
				<div class="row">
					<div class="col-md-8">
						<div class="main-notice panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">
									<i class="fa fa-bullhorn"></i>
									공지사항
								</h3>
							</div>
							<form:form method="post" id="noticeListForm"> 
							
							<c:if test="${empty noticeList}">
							<ul class="list-group">
								<li class="list-group-item">
									<a href="#" title="">
										NO DATA
									</a>
								</li>
							</ul>
							
							</c:if>
							<c:if test="${! empty noticeList}">
								<ul class="list-group">
			                    <c:forEach var="noticeVO" items="${noticeList}">
			                    	<fmt:parseDate var="instDt" value="${noticeVO.instDt}" pattern="yyyyMMddHHmmss" />
			                        <li class="list-group-item">
										<a href="javascript:showDetail('${ noticeVO.noticeNo }');" title="">
											${noticeVO.noticeTitle}
											<span class="badge"><fmt:formatDate value="${instDt}" pattern="yyyy-MM-dd  HH:mm:ss" /></span>
										</a>
									</li>			                        
			                    </c:forEach>    
			                    </ul>
			                </c:if>    
							
							</form:form>
							
						</div>
					</div>
					<div class="col-md-4 main-banner">
						<a href="#" title="main banner">
							<img src="images/main_banner.png" alt="main banner">
						</a>
					</div>
				</div>
			</div>
