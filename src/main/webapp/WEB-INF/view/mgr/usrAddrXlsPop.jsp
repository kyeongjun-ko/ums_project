<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<script type="text/javascript">
var clickCount = 0;
 
$(document).ready(function() {
	$("#uploadFile").change(function() {
        //upload file check
        var fileName = $(this).val().toLowerCase();
        var fileExt = fileName.substring(fileName.lastIndexOf(".")+1);
        switch(fileExt) {
            case "xls":
            case "xlsx":
            case "txt":
               break;
            default:
               //alert("Please check your file.");
               $(this).val("");
               break;
        }
    });
});
//
function excelUpload() {
	if(clickCount == 1) { 
        return;
    }
	var fileName = $("#uploadFile").val();
	if(fileName == '') {
		alert("Please check upload file.");
        return;
	}  
	clickCount = 1; 
	
	$("#addrGrpNo").val($("#grpNo").val());

	$("#uploadAjaxForm").ajaxSubmit({
        url : "/mgr/addrExcelUploadProcessAjax",
        type : "POST",
        dataType : "html",
    	beforeSubmit: function() {
	    },
	    success: function(res) {    
	    	alert("정상 입력");
	    	window.close();
	    },
	    error: function(request, status, error) {
            alert("excel upload fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
	    }
    });   
}
 
</script> 

<div class="panel-default popup imgpop excelpop">
	<div class="panel-heading">
		<h3 class="panel-title">주소록 엑셀 업로드</h3>
	</div>
	
	<br>
	<form:form method="post" modelAttribute="addrVO" id="addrListForm" autocomplete="off" action="/mgr/usrAddrXlsPop">
	<div class="form-group">
			<label for="msgsel">그룹선택</label>
			<select name="grpNo" id="grpNo" class="form-control" value="${addrVO.grpNo}" >
				<c:forEach var="usrGrpVO" items="${usrGrpList}">
	                 <option value="${usrGrpVO.grpNo}" <c:if test="${usrGrpVO.grpNo == addrVO.grpNo}">selected="selected" </c:if>>${usrGrpVO.grpNm} </option>
	            </c:forEach>
			</select>
	</div>	
	</form:form>
	

	<form id="uploadAjaxForm" name="uploadAjaxForm" method="post" action="/ums/addrExcelUploadProcessAjax" enctype="multipart/form-data"> 
	<input type="hidden" name="uploadUserId" value=""/>
	<input type="hidden" name="addrGrpNo"  id="addrGrpNo"  value=""/>
	
	<div class="panel-body inputs">
		<ul class="list-unstyled">
			<li>주소록 저장하기
				<small>엑셀 파일로 직접 작성한 목록을 불러 저장할 수 있습니다. 저장하실 목록 파일을 선택해 주십시오.</small>
			</li>
		</ul>
		
		<div class="form-group">
			<input type="hidden"   id="uploadFileText" name="uploadFileText" readonly="readonly" style="width:200px;" value=""/>
			<input type="file" id="uploadFile" name="uploadFile" onChange="$('#uploadFileText').val(this.value);"  style="width:100%;"/>
            <label for="uploadFile" class="sr-only">찾아보기...</label>
		</div>
		<h4>선택한 주소록 목록 파일을 저장하시겠습니까?</h4>
	</div>
	</form>
	<div class="panel-body btns text-center">
		<button class="btn btn-primary" onclick="javascript:excelUpload();">확인</button>
		<button class="btn btn-second" onclick="window.close();">취소</button>
	</div>
	<div class="panel-body text-center">
		<div class="example">
				<p>* 엑셀발송 파일 문서종류는 xls이어야 하며 <br>
				<strong>이름| 문자번호|음성번호|팩스번호|비고</strong> 의 형식으로 작성하십시오.
				</p>
			<img src="/images/excel2.jpg" width="400" height="200" alt="엑셀예시">
		</div>
	</div>
</div>
