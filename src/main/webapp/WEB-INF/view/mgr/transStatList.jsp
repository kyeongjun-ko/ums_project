<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>    
 <script type="text/javascript">
 $(document).ready(function(){
	  //date search
	 	//$("input[name='statType']:radio[value='01']").attr('checked',true);
	 	//$("#tab01").removeClass("table dtable tabpn off");
	    //$("#tab01").addClass("table dtable tabpn");
//date search
	  if($("#srchRegDateStart").val() == "") {
		  $('#srchRegDateStart').val($.datepicker.formatDate('yy/mm/dd', new Date()));
      }
	  if($("#srchRegDateEnd").val() == "") {
		  $('#srchRegDateEnd').val($.datepicker.formatDate('yy/mm/dd', new Date()));
      }
	  
        $("#btnSearch").click(function() {
        	$("#currentPageNo").val("1");
            
            $("#transStatListForm").attr({
                action : "/trs/transStatListProc",
                method : "post"
            }).submit();
            
        });
    });

    function linkPage(pageNo) {
        $("#currentPageNo").val(pageNo);
        
        $("#transStatListForm").attr({
            action : "/trs/transStatListProc",
            method : "post"
        }).submit();
    }
    
    function downloadView() {
    	$("#csvName").val("");
        $("#downloadPopUp").toggle();
    }    
 
 </script>
        <form:form method="post" action="/trs/transStatListProc" modelAttribute="searchTransStatVO" id="transStatListForm" >
        <form:hidden path="currentPageNo"/>
        <input type="hidden" id="msgId" name="msgId" />

        <input type="hidden" id="catevalue" value="" />        
         
       

	<div class="col-md-9 tform sresult">
		<div class="col-md-12 tits">
			<h1>전송통계</h1>
			<ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-home"></i></a></li>
				<li><a href="#">전송관리</a></li>
				<li class="active">전송통계</li>
			</ol>
			<div class="hbg">
				<i class="fa fa-envelope odd"></i>
				<i class="fa fa-globe "></i>
			</div>
		</div>
		<div class="col-md-12 tcon">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">상담원별 / 날짜별 전송 통계 리스트</h3>
				</div>
				<div class="panel-body schbar">
					<div class="form-inline">
						<div class="form-group radio">
							<label class="radio-inline">
							  <input type="radio" name="statType" id="inlineRadio1" value="01" tabon="tab01" <c:if test="${searchTransStatVO.statType eq '01'}">checked="1" </c:if> > 상담원별
							</label>
							<label class="radio-inline">
							  <input type="radio" name="statType" id="inlineRadio2" value="02" tabon="tab02" <c:if test="${searchTransStatVO.statType eq '02'}">checked="1" </c:if> > 날짜별
							</label>
						</div>		
						<div class="form-group has-feedback">
							<label for="">전송일자</label>
							<input type="text" class="form-control datepicker" name="srchRegDateStart" id="srchRegDateStart" value="${searchTransStatVO.srchRegDateStart}">
							<i class="fa fa-calendar form-control-feedback"></i>
						</div>
						<em class="tilde">~</em>
						<div class="form-group has-feedback">
							<input type="text" class="form-control datepicker" name="srchRegDateEnd" id="srchRegDateEnd" value="${searchTransStatVO.srchRegDateEnd}">
							<i class="fa fa-calendar form-control-feedback"></i>
						</div>
						
						<div class="form-group uselect">
						
						<select name="suserId" id="suserId" class="form-control" value="${searchTransStatVO.userId}" style="width: 100%;padding: 6px 0;margin-right: -1px;">
							<c:forEach var="commCodeVO" items="${userIdList}">
								<option value="${commCodeVO.code}"  
								<c:if test="${commCodeVO.code == searchTransStatVO.userId}">selected="selected" </c:if>
								>${commCodeVO.codeName} </option>
							</c:forEach>
						</select>
						</div>			
						<!-- 
						<div class="form-group searchtit">	
							<input type="text" name="searchName" id="searchName" class="form-control" placeholder="검색할 제목" value="${searchTransStatVO.searchName}">			
						</div>
						-->
						<div class="pull-right">
						<button id="btnSearch"  class="btn btn-second" title="검색" type="submit">검색</button>
						</div> 
					</div>
					
				</div>	
				<div style="overflow-x: scroll;">
				<table id="tab01" class="<c:choose><c:when test = "${searchTransStatVO.statType == '01'}">table dtable tabpn</c:when>
										<c:otherwise>table dtable tabpn off</c:otherwise></c:choose>" 
 					style="min-width:1500px;">
					<tr>
						<th style="width: 80px;" rowspan="2">No.</th>
						<th style="width: 220px;" class="odd" rowspan="2">담당자명</th>
						<th style="width: 135px;" rowspan="2">발송시도</th>
						<th style="width: 135px;" class="odd" rowspan="2">발송실패</th>
						<th style="width: 135px;" rowspan="2">발송대기</th>
						<th style="width: 135px;" class="odd" rowspan="2">발송성공</th>
						<th colspan="6">UMS</th>
						<th class="odd" colspan="6">SmartDM발송</th>
						<th colspan="4">SmartFAX발송</th>
					</tr>
					<tr>
						<th style="width: 100px;" class="td">SMS</th>
						<th style="width: 100px;" class="td odd">LMS</th>
						<th style="width: 100px;" class="td">MMS</th>
						<th style="width: 100px;" class="td odd">FMS</th>
						<th style="width: 100px;" class="td ">VMS</th>
						<th style="width: 100px;" class="td odd">설문<br>조사</th>
						<!--//UMS-->								
						<th style="width: 100px;" class="td">SMS</th>
						<th style="width: 100px;" class="td odd">LMS</th>
						<th style="width: 100px;" class="td">확인<br>건수</th>
						<th style="width: 100px;" class="td odd">확인률</th>
						<th style="width: 100px;" class="td">응답<br>건수</th>
						<th style="width: 100px;" class="td odd">응답률</th>
						<!--//UMS-->								
						<th style="width: 100px;" class="td">SMS</th>
						<th style="width: 100px;" class="td odd">LMS</th>
						<th style="width: 100px;" class="td">응답<br>건수</th>
						<th style="width: 100px;" class="td odd">응답률</th>
						<!--//UMS-->
					</tr>
					
					<c:choose>
						<c:when test="${!empty transStatList && searchTransStatVO.statType eq '01'}">
							<c:forEach var="transStatVO" items="${transStatList}">
		                    <tr>	
		                        <td>${transStatVO.rowNumber} </td>
								<td class="odd">${transStatVO.userNm} </td>
								<td>${transStatVO.sendTryCnt} </td>
								<td class="odd">${transStatVO.sendFailCnt }</td>
								<td>${transStatVO.sendWaitCnt }</td>
								<td class="odd">${transStatVO.sendSucCnt  }</td>
								<td>${transStatVO.sendSmsCnt  }</td>
								<td class="odd">${transStatVO.sendLmsCnt  }</td>
								<td>${transStatVO.sendMmsCnt  }</td>
								<td class="odd">${transStatVO.sendFmsCnt  }</td>
								<td>${transStatVO.sendVmsCnt  }</td>
								<td class="odd">${transStatVO.sendVqrcnt  }</td>
								<td>${transStatVO.dmSmsCnt    }</td>
								<td class="odd">${transStatVO.dmLmsCnt    }</td>
								<td>${transStatVO.dmConCnt    }</td>
								<td class="odd">${transStatVO.dmConPer    } %</td>
								<td>${transStatVO.dmResCnt    }</td>
								<td class="odd">${transStatVO.dmResPer    } %</td>
								<td>${transStatVO.faSmsCnt    }</td>
								<td class="odd">${transStatVO.faLmsCnt    }</td>
								<!-- <td>${transStatVO.faConCnt    }</td>
								<td class="odd">${transStatVO.faConPer        }</td>
								 -->
								<td>${transStatVO.faResCnt        }</td>
								<td class="odd">${transStatVO.faResPer    } %</td>
							</tr>		                        
		                    </c:forEach>    
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="2">NO DATA</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
				<table id="tab02" class="<c:choose><c:when test = "${searchTransStatVO.statType == '02'}">table dtable tabpn</c:when>
										<c:otherwise>table dtable tabpn off</c:otherwise></c:choose>" 
					style="min-width:1500px;">
					<tr>
						<th style="width: 80px;" rowspan="2">No.</th>
						<th style="width: 220px;" class="odd" rowspan="2">날짜</th>
						<th style="width: 135px;" rowspan="2">발송시도</th>
						<th style="width: 135px;" class="odd" rowspan="2">발송실패</th>
						<th style="width: 135px;" rowspan="2">발송대기</th>
						<th style="width: 135px;" class="odd" rowspan="2">발송성공</th>
						<th colspan="6">UMS</th>
						<th class="odd" colspan="6">SmartDM발송</th>
						<th colspan="4">SmartFAX발송</th>
					</tr>
					<tr>
						<th style="width: 100px;" class="td">SMS</th>
						<th style="width: 100px;" class="td odd">LMS</th>
						<th style="width: 100px;" class="td">MMS</th>
						<th style="width: 100px;" class="td odd">FMS</th>
						<th style="width: 100px;" class="td">VMS</th>
						<th style="width: 100px;" class="td odd" >설문<br>조사</th>
						<!--//UMS-->								
						<th style="width: 100px;" class="td">SMS</th>
						<th style="width: 100px;" class="td odd">LMS</th>
						<th style="width: 100px;" class="td">확인<br>건수</th>
						<th style="width: 100px;" class="td odd">확인률</th>
						<th style="width: 100px;" class="td">응답<br>건수</th>
						<th style="width: 100px;" class="td odd">응답률</th>
						<!--//UMS-->								
						<th style="width: 100px;" class="td">SMS</th>
						<th style="width: 100px;" class="td odd">LMS</th>
						<th style="width: 100px;" class="td">응답<br>건수</th>
						<th style="width: 100px;" class="td odd">응답률</th>
						<!--//UMS-->
					</tr>
					
					<c:choose>
						<c:when test="${! empty transStatList && searchTransStatVO.statType eq '02'}">
							<c:forEach var="transStatVO" items="${transStatList}">
			                    <tr>	
			                    	<fmt:parseDate var="sendDate" value="${transStatVO.sendDate}" pattern="yyyyMMdd" />
			                        <td>${transStatVO.rowNumber   }</td>
									<td class="odd">
									<fmt:formatDate value="${sendDate}" pattern="yyyy-MM-dd" />
									</td>
									<td>${transStatVO.sendTryCnt  }</td>
									<td class="odd">${transStatVO.sendFailCnt }</td>
									<td>${transStatVO.sendWaitCnt }</td>
									<td class="odd">${transStatVO.sendSucCnt  }</td>
									<td>${transStatVO.sendSmsCnt  }</td>
									<td class="odd">${transStatVO.sendLmsCnt  }</td>
									<td>${transStatVO.sendMmsCnt  }</td>
									<td class="odd">${transStatVO.sendFmsCnt  }</td>
									<td>${transStatVO.sendVmsCnt  }</td>
									<td class="odd">${transStatVO.sendVqrcnt  }</td>
									<td>${transStatVO.dmSmsCnt    }</td>
									<td class="odd">${transStatVO.dmLmsCnt    }</td>
									<td>${transStatVO.dmConCnt    }</td>
									<td class="odd">${transStatVO.dmConPer    }</td>
									<td>${transStatVO.dmResCnt    }</td>
									<td class="odd">${transStatVO.dmResPer    }</td>
									<td>${transStatVO.faSmsCnt    }</td>
									<td class="odd">${transStatVO.faLmsCnt    }</td>
									<!--<td>${transStatVO.faConCnt    }</td>
									<td class="odd">${transStatVO.faConPer    }</td>
									-->
									<td>${transStatVO.faResCnt    }</td>
									<td class="odd">${transStatVO.faResPer    }</td>
								</tr>		                        
	                   		 </c:forEach>   						
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="2">NO DATA</td>
							</tr>
						</c:otherwise>
					</c:choose>						
				</table>
			</div>
				<!-- Paging -->
				<div class="panel-body text-center" align=center>
	           	 <ul class="pagination">
	               <ui:pagination paginationInfo="${searchTransStatVO}" type="image"
	                   jsFunction="linkPage" />
	             </ul>
	           </div>
			</div>
		</div>
	</div>
 </form:form>  
