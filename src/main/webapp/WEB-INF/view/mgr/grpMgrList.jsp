<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>    
 <script type="text/javascript">
 $(document).ready(function(){
	 //comboChange();
	$(".addrListCheckAll").bind('click', function() {
	       if($(this).is(":checked")) {
	           $(".checkbox").attr("checked", true);
	       } else {
	           $(".checkbox").attr("checked", false);
	       }
   	});

	$("#btnSearch").click(function() {
		$("#currentPageNo").val(1);
	    
	    $("#grpMgrListForm").attr({
	        action : "/mgr/grpMgrList",
	        method : "post"
	    }).submit();
   	});
	var select = "<option value=''>그룹번호</option>"; 
	$("#grpCd").change(function() {			
		if($("#grpCd").val() == "") { // select의 value가 ""이면, "선택" 메뉴만 보여줌.
			$("#grpNo").find("option").remove().end().append(select);
			$("#grpNo").val() == "";
			
		} else {
			comboChange($(this).val());
		}
	});

	function comboChange() {
		$.ajax({
			type:"post",
			url:"/ums/grpNoListAjax",
			datatype: "json",
			data: $("#grpMgrListForm").serialize(),
			success: function(data) {
				if(data.usrGrpList != "") {
					$("#grpNo").find("option").remove().end().append(select);
					 var html = "";
					 html += select;
			        $.each(data.usrGrpList, function(index, item) {
			            html += '<option value="'+ item.grpNo +'" <c:if test="${'+ item.grpNo +'== searchGrpVO.grpNo}">selected="selected" </c:if> >'+item.grpNm  +'</option>' ;
			        });
				    $("#grpNo").find("option").remove().end().append(html);
				} else {
					$("#grpNo").find("option").remove().end().append("<option>-- No sub --</option>");
					return;
				}
			},
			error: function(x, o, e) {
				var msg = "페이지 호출 중 에러 발생 \n" + x.status + " : " + o + " : " + e; 
				alert(msg);
			}				
		});
	}

});
	function linkPage(pageNo) {
	    $("#currentPageNo").val(pageNo);
	    
	    $("#grpMgrListForm").attr({
	        action : "/mgr/grpMgrList",
	        method : "post"
	    }).submit();
	}
 
	 function showDetail(grpNo) {
	 	$("#grpNo").val(grpNo);
	     $("#grpMgrListForm").attr({
	         action : "/mgr/grpMgrDet?grpNo="+grpNo,
	         method : "get"
	     }).submit();
	 }
	 
	 function goGrpMgrPopup() {
	     var title = "grpMgrPopup";
	     
	     var status = "width=478, height=430, scrollbars=1, toolbar=no, menubar=no resizable=yes";
	     poptarget = window.open("/mgr/grpMgrWritePopUp"  , title, status);
	 }
	
	
	 function checkAny(type){
	     var bChecked = false;
	     var strVar = "";
	     
	     $("input[name=grpArNo]").each(function() {
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
	
	     
	 function goGrpMgrUptPopup(addrNo) {
	 	
	 	if(checkAny('update')) {
	 	
		        var title = "grpMgrPopup";
		        
		        var strVar = ""; 
		        var strVar1 = ""; 

		        var param = null; 
		        var params = new Object; 
		        
		        $("input[name=grpArNo]").each(function() {
		            if(this.checked == true) {
		                strVar = this.value;  
		            }
		        });
		        
		        param = strVar.split("-");  
		        if(param.length > 1){  
					params["grpCd"] = param[0];  
					params["grpNo"] = param[1];
		        }
		        
		        var status = "width=478, height=430, scrollbars=1, toolbar=no, menubar=no resizable=yes";
		        poptarget = window.open("/mgr/grpMgrUpdatePopup?grpNo=" + params["grpNo"] +"&grpCd=" + params["grpCd"] , title, status);
	 	}
	 }
	 
	 function goGrpMgrDetPopup() {
	 	if(checkAny('delete')){
	 	
	        if(confirm('선택한 데이터를 삭제하시겠습니까?')) {
	            
	            var param = $("#grpMgrListForm").serialize();
	            
	            //if($("#targetSystem").val() != null && $("#targetSystem").val() != '') {
	                //send post
	            $.ajax({
	                type : "POST",
	                url : "/mgr/removeGrpMgr",                
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

        <form:form method="post" modelAttribute="searchGrpVO" id="grpMgrListForm">
        <form:hidden path="currentPageNo"/>

	<div class="col-md-9 tform adb">
		<div class="col-md-12 tits">
			<h1>그룹관리</h1>
			<ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-home"></i></a></li>
				<li><a href="#">마이페이지</a></li>
				<li class="active">그룹관리</li>
			</ol>
			<div class="hbg">
				<i class="fa fa-envelope odd"></i>
				<i class="fa fa-globe "></i>
			</div>
		</div>
		<div class="col-md-12 tcon">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">그룹 관리</h3>
				</div>
				<div class="panel-body schbar odd">
					<div class="form-inline">
						<a href="javascript:goGrpMgrPopup();" class="btn btn-primary pop-modify">그룹 추가</a>
						<a href="javascript:goGrpMgrUptPopup();" class="btn btn-second">그룹 수정</a>
						<a href="javascript:goGrpMgrDetPopup();" class="btn btn-second">그룹 삭제</a>
						<div class="pull-right">
						
							<div class="form-group uselect">
								<select name="grpCd" value="${searchGrpVO.grpCd}" id="grpCd" class="form-control">
									<option value="">그룹유형</option>
							        <c:forEach var="grpMgrVO" items="${grpCdList}">
						                 <option value="${grpMgrVO.code}"  <c:if test="${grpMgrVO.code == searchGrpVO.grpCd}">selected="selected" </c:if>>${grpMgrVO.codeName} </option>
						            </c:forEach>
								</select>
							</div>
							
							<div class="form-group pname">
								<select name="grpNo" id="grpNo" class="form-control" value="${searchGrpVO.grpNo}" >
									<option value="">그룹번호</option>
									<c:forEach var="grpVO" items="${usrGrpList}">
						                 <option value="${grpVO.grpNo}"  <c:if test="${grpVO.grpNo == searchGrpVO.grpNo}">selected="selected" </c:if>>${grpVO.grpNm} </option>
						            </c:forEach>
								</select>
							</div>
							
							<div class="form-group searchtit">	
								<input type="text"name="grpNm" id="grpNm" class="form-control" placeholder="검색할 그룹명" value="${searchGrpVO.grpNm}">			
							</div>
							<button id="btnSearch" href="" class="btn btn-second" title="검색">검색</button>
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
						<th class="odd">유형</th>
						<th>그룹번호</th>
						<th class="odd">그룹명</th>
						<th>등록 갯수</th>
						<th class="odd">비고</th>
					</tr>
					
					<c:if test="${empty grpList}">
                    <tr>
                        <td colspan="12">No data</td>
                    </tr>
	                </c:if>
	                
	                <c:if test="${! empty grpList}">
	                	<c:forEach var="GrpVO" items="${grpList}">
					<tr>
						<td><label for="CHK-${GrpVO.grpNo}"><input type="checkbox" id="CHK-${GrpVO.grpNo}" name="grpArNo"  value="${GrpVO.grpCd}-${GrpVO.grpNo}"   onclick="Util.CheckObject(${GrpVO.grpNo});" ></label></td>
						<td class="odd">${GrpVO.grpCdNm}</td>
						<td>${GrpVO.grpNo}</td>
						<td class="odd">${GrpVO.grpNm}</td>
						<td>${GrpVO.grpCnt}</td>
						<td class="odd">${GrpVO.note}</td>
					</tr>
						</c:forEach>     
	                </c:if>
	                
				</table>
				<div class="panel-body text-center">
					<ul class="pagination">
					<ui:pagination paginationInfo="${searchGrpVO}" type="image"
			                   jsFunction="linkPage" /> 
					</ul>	
				</div>
			</div>
		</div>
	</div>
 </form:form>  
