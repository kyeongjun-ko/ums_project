<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>    
 <script type="text/javascript">
 $(document).ready(function(){
	  //date search
    });
 
 </script>

<form:form method="post" modelAttribute="mySvcVO" id="mySvcForm"> 
<input type="hidden" name="userId"          value="${mySvcVO.umsUserVO.userId}" />
	<div class="col-md-9 tform rsvdetail">
		<div class="col-md-12 tits">
			<h1>My 서비스</h1>
			<ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-home"></i></a></li>
				<li><a href="#">마이페이지</a></li>
				<li class="active">My 서비스</li>
			</ol>
			<div class="hbg">
				<i class="fa fa-envelope odd"></i>
				<i class="fa fa-globe "></i>
			</div>
		</div>
		<div class="col-md-12 tcon">
			<div class="panel panel-default dlist">
				<div class="panel-heading">
					<h3 class="panel-title">개인정보</h3>
				</div>
				<div class="panel-body">
					<div class="dl-horizontal">
						<dl>
						<dt>담당자명</dt><dd>${mySvcVO.umsUserVO.userNm}</dd>
						</dl>
						<dl>
						<dt>회사명</dt><dd>${mySvcVO.umsUserVO.company}</dd>
						</dl>
						<dl>
						<dt>담당부서</dt><dd>${mySvcVO.umsUserVO.dept}</dd>
						</dl>
					</div>							
					<div class="dl-horizontal">
						<dl>
						<dt>이메일</dt><dd>${mySvcVO.umsUserVO.email}</dd>
						</dl>
						<dl>
						<dt>담당자전화(유선)</dt><dd>${mySvcVO.umsUserVO.telNo1}</dd>
						</dl>
						<dl>
						<dt>담당자전화(무선)</dt><dd>${mySvcVO.umsUserVO.telNo2}</dd>
						</dl>
					</div>							
					<div class="dl-horizontal">
						<dl>
						<dt>발신번호</dt>
						<dd>
							<select class="form-control textinput-selector" >
								<option value="NULL">발신번호목록</option>
								<c:if test="${! empty callbackList}">
									<c:forEach var="item" items="${callbackList}">
										<c:if test="${! empty item.callbackNo && item.statusCd eq 'A'}">
											<option value="${item.callbackNo}" >${item.callbackNo}</option>
										</c:if>
									</c:forEach>
								</c:if>
							</select>	
						</dd>
						</dl>
					</div>
					<a href="/mgr/getModUsr" class="btn btn-second pull-right">정보 변경</a>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">사용내역</h3>
				</div>
				<div class="panel-body schbar">
				<fmt:parseDate var="instDt" value="${mySvcVO.yyyymm}" pattern="yyyyMM" />
					<h4 class="pull-left"><fmt:formatDate value="${instDt}" pattern="yyyy년 MM월" /></h4>
					<a href="/mgr/transUseList" class="btn btn-primary pull-right">사용내역조회</a>
				</div>	
				<table class="table dtable">
					<tr>
						<th>발송유형</th>
						<th>발송건수</th>
						<th>성공건수</th>
						<th>실패건수</th>
					</tr>
					<tr>
						<td class="text-left">문자메시지(SMS)</td>
						<td class="odd">${mySvcVO.smsSendCnt}</td>
						<td>${mySvcVO.smsSuccCnt}</td>
						<td class="odd">${mySvcVO.smsfailCnt}</td>
					</tr>							
					<tr>
						<td class="text-left">장문메시지(LMS)</td>
						<td class="odd">${mySvcVO.lmsSendCnt}</td>
						<td>${mySvcVO.lmsSuccCnt}</td>
						<td class="odd">${mySvcVO.lmsfailCnt}</td>
					</tr>
					<tr>
						<td class="text-left">멀티미디어메시지(MMS)</td>
						<td class="odd">${mySvcVO.mmsSendCnt}</td>
						<td>${mySvcVO.mmsSuccCnt}</td>
						<td class="odd">${mySvcVO.mmsfailCnt}</td>
					</tr>	
					<tr>
						<td class="text-left">FAX메시지(FMS)</td>
						<td class="odd">${mySvcVO.fmsSendCnt}</td>
						<td>${mySvcVO.fmsSendCnt}</td>
						<td class="odd">${mySvcVO.fmsSendCnt}</td>
					</tr>							
					<tr>
						<td class="text-left">음성메시지(VMS)</td>
						<td class="odd">${mySvcVO.vmsSendCnt}</td>
						<td>${mySvcVO.vmsSuccCnt}</td>
						<td class="odd">${mySvcVO.vmsfailCnt}</td>
					</tr>							
					<tr>
						<td class="text-left">설문조사</td>
						<td class="odd">${mySvcVO.vmsQrSendCnt}</td>
						<td>${mySvcVO.vmsQrSuccCnt}</td>
						<td class="odd">${mySvcVO.vmsQrfailCnt}</td>
					</tr>							
					<tr>
						<td class="text-left">SMART DM</td>
						<td class="odd">${mySvcVO.smartDmSendCnt}</td>
						<td>${mySvcVO.smartDmSuccCnt}</td>
						<td class="odd">${mySvcVO.smartDmfailCnt}</td>
					</tr>							
					<tr>
						<td class="text-left">SMART FAX</td>
						<td class="odd">${mySvcVO.smartFaxSendCnt}</td>
						<td>${mySvcVO.smartFaxSuccCnt}</td>
						<td class="odd">${mySvcVO.smartFaxfailCnt}</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
 </form:form>  
