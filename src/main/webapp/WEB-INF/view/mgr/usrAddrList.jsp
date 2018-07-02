<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>    
 <script type="text/javascript">
 $(document).ready(function(){
	//addr list check all
		$(".addrListCheckAll").bind('click', function() {
	        if($(this).is(":checked")) {
	            $(".checkbox").attr("checked", true);
	        } else {
	            $(".checkbox").attr("checked", false);
	        }
	    });
		
		$("#btnSearch").click(function() {
			$("#currentPageNo").val(1);
		    
		    $("#addrListForm").attr({
		        action : "/mgr/usrAddrList",
		        method : "post"
		    }).submit();
	        
	    });
		
	});
 	function linkPage(pageNo) {
	    $("#currentPageNo").val(pageNo);
	    
	    $("#addrListForm").attr({
	        action : "/mgr/usrAddrList",
	        method : "post"
	    }).submit();
	}
    
    function showDetail(addrNo) {
    	$("#addrNo").val(addrNo);
        $("#addrListForm").attr({
            action : "/mgr/usrAddrDet?addrNo="+addrNo,
            method : "get"
        }).submit();
    }
    
    function goUsrAddrPopup() {
        var title = "UsrAddrPopup";
        
        var status = "width=478, height=430, scrollbars=1, toolbar=no, menubar=no resizable=yes";
        poptarget = window.open("/mgr/usrAddrWritePopUp"  , title, status);
    }

    
    function goUsrAddrExcelPopup() {
        var title = "UsrAddrXlsPopup";
        
        var status = "width=478, height=430, scrollbars=1, toolbar=no, menubar=no resizable=yes";
        poptarget = window.open("/mgr/usrAddrXlsPop"  , title, status);
    }


    function checkAny(type){
        var bChecked = false;
        var strVar = "";
        
        $("input[name=addrArNo]").each(function() {
            if(this.checked == true) {
            	bChecked = true;
            	strVar ++;  
            }
        });
         
        
        if(!bChecked) {
            alert("데이터를 선택하세요.");
            return false;
        } 
        
        if(strVar>1  && type == "update") {
        	alert("복수 데이터를 선택할 수 없습니다.");
            return false;
        }
        
        return true;
    }

        
    function goUsrAddrUptPopup(addrNo) {
    	
    	if(checkAny('update')) {
    	
	        var title = "UsrAddrPopup";
	        
	        var strVar = ""; 
	        
	        $("input[name=addrArNo]").each(function() {
	            if(this.checked == true) {
	                strVar = this.value;  
	            }
	        });
	        
	        var addrNo = $("#currentPageNo").val();
	        var status = "width=478, height=430, scrollbars=1, toolbar=no, menubar=no resizable=yes";
	        poptarget = window.open("/mgr/usrAddrUpdatePopup?addrArNo=" + strVar , title, status);
    	}
    }
    
    function goUsrAddrDetPopup() {
    	if(checkAny('delete')){
    	
	        if(confirm('선택한 데이터를 삭제하시겠습니까?')) {
				var param = $("#addrListForm").serialize();
	            
	            //if($("#targetSystem").val() != null && $("#targetSystem").val() != '') {
	                //send post
	            $.ajax({
	                type : "POST",
	                url : "/mgr/removeUsrAddr",                
	                data : param,
	                success : function(json) {
	                    if(json.result) {
	                        alert(json.sucMsg);
	                        document.location.reload();
	                    } else {
	                        alert(json.errMsg);
	                    }
	                },
	                error : function(request, status, error) {
	                    alert("order list delete fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
	                }
	            });
	        }
    	}
    }

 
 </script>

        <form:form method="post" modelAttribute="searchAddrVO" id="addrListForm" autocomplete="off">
        <form:hidden path="currentPageNo"/>
        <input type="hidden" id="addrNo" name="addrNo" />
       

	<div class="col-md-9 tform adb">
		<div class="col-md-12 tits">
			<h1>주소록</h1>
			<ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-home"></i></a></li>
				<li><a href="#">마이페이지</a></li>
				<li class="active">주소록</li>
			</ol>
			<div class="hbg">
				<i class="fa fa-envelope odd"></i>
				<i class="fa fa-globe "></i>
			</div>
		</div>
		<div class="col-md-12 tcon">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">주소록 관리</h3>
				</div>
				<div class="panel-body schbar odd">
					<div   class="form-inline">
						<a href="javascript:goUsrAddrPopup();" class="btn btn-primary pop-adb">주소 추가</a>
						<a href="javascript:goUsrAddrExcelPopup();" class="btn btn-primary pop-adb">엑셀 업로드</a>
						
						<a href="javascript:goUsrAddrUptPopup();" class="btn btn-second">주소 수정</a>
						<a href="javascript:goUsrAddrDetPopup();" class="btn btn-second">주소 삭제</a>
						<div class="pull-right">
							<div class="form-group uselect">
								<select name="grpNo" value="${searchAddrVO.grpNo}" id="" class="form-control">
									<option value="">그룹</option>
							        <c:forEach var="usrGrpVO" items="${usrGrpList}">
						                 <option value="${usrGrpVO.grpNo}"  <c:if test="${usrGrpVO.grpNo == searchAddrVO.grpNo}">selected="selected" </c:if>>${usrGrpVO.grpNm} </option>
						            </c:forEach>
								</select>
							</div>			
							<div class="form-group pname">
								<select name="searchType" id="pname" class="form-control" value="${searchAddrVO.searchType}">
									<option value="">전체</option>
									<option value="00" <c:if test="${searchAddrVO.searchType == '00'}">selected="selected" </c:if>>문자번호</option>
									<option value="01" <c:if test="${searchAddrVO.searchType == '01'}">selected="selected" </c:if>>음성번호</option>
									<option value="02" <c:if test="${searchAddrVO.searchType == '02'}">selected="selected" </c:if>>팩스번호</option>
									<option value="03" <c:if test="${searchAddrVO.searchType == '03'}">selected="selected" </c:if>>이름</option>
								</select>
							</div>
							<div class="form-group searchtit">	
								<input type="text" class="form-control" name="searchName" placeholder="검색할 제목" value="${searchAddrVO.searchName}">			
							</div>
							<button href="" id="btnSearch" class="btn btn-second" title="검색" type="submit">검색</button>
						</div>
					</div>
				</div>	
				<table class="table dtable">
					<tr>
						<th class="chb">
						<label for="CheckAll">
					      <input type="checkbox"   id="CheckAll" onclick="CheckBox.All(this);  Util.CheckAll2(this);">  전체
					    </label>
						</th>
						<th class="odd">그룹명</th>
						<th >이름</th>
						<th class="odd">문자번호</th>
						<th>음성번호</th>
						<th class="odd">팩스번호</th>
						<th>비고</th>
					</tr>
					<c:if test="${empty addrList}">
                    <tr>
                        <td colspan="12">No data</td>
                    </tr>
	                </c:if>
					<c:if test="${! empty addrList}">
	                	<c:forEach var="addrVO" items="${addrList}">
					<tr>
						<td><label for="CHK-${addrVO.addrNo}"><input type="checkbox" id="CHK-${addrVO.addrNo}" name="addrArNo"  value="${addrVO.addrNo}"   onclick="Util.CheckObject(${addrVO.addrNo});" ></label></td>
						<td class="odd">${addrVO.grpNm}</td>
						<td>${addrVO.name}</td>
						<td class="odd">${addrVO.smsNo}</td>
						<td>${addrVO.vmsNo}</td>
						<td class="odd">${addrVO.fmsNo}</td>
						<td>${addrVO.note}</td>					
						<input type="hidden" id="NAME-${addrVO.addrNo}" value="${addrVO.name}"/>
						<input type="hidden" id="SMS-${addrVO.addrNo}" value="${addrVO.smsNo}"/>
						<input type="hidden" id="VMS-${addrVO.addrNo}" value="${addrVO.vmsNo}"/>
						<input type="hidden" id="FMS-${addrVO.addrNo}" value="${addrVO.fmsNo}"/>
					</tr>
						</c:forEach>     
	                </c:if> 	
				</table>
				
				<div class="panel-body text-center">
					<!-- 
					<a href="./pop-excel2.html" class="btn btn-second pull-left pop-excel">주소록 엑셀 업로드</a>
					 -->
					<ul class="pagination">
					<ui:pagination paginationInfo="${searchAddrVO}" type="image"
			                   jsFunction="linkPage" /> 
					</ul>	
				</div>
			</div>
		</div>
	</div>
 </form:form>  
