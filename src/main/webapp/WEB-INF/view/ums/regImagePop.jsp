<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
var clickCount = 0;
 
$(document).ready(function() {
	
	$("#uploadFile").change(function() {
        //upload file check
        var fileName = $(this).val().toLowerCase();
        var fileExt = fileName.substring(fileName.lastIndexOf(".")+1);
        switch(fileExt) {
            case "jpg":
          //case "jpeg":
          //case "gif":
          //case "png":
               break;
            default:              
               //$(this).val("");
               $(this).replaceWith($(this).clone(true));
               alert(".JPG형식의 파일만 등록 가능합니다.");
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
	
	
	$("#uploadAjaxForm").ajaxSubmit({
        url : "/ums/regImageUploadProcessAjax",
        type : "POST",
        dataType : "json",
    	beforeSubmit: function() {
	    },
	    success: function(data) {    
	    	
	    	if(data != null && data.result != 'failure') {
	    		// HTML 코드 작성
	    		//alert(data.result);
				window.opener.fn_fileUploadComplete('IMAGE',data);
				window.close(); 		
			}
	    	else {
	    		alert(data.msg);
	    		window.close(); 
	    	}
	    },
	    error: function(request, status, error) {

	    	alert(data.errMsg);
            alert("excel upload fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
	    }
    });   
}
</script> 

<div class="panel-default popup imgpop">

<form id="uploadAjaxForm" name="uploadAjaxForm" method="post" action="/ums/regImageUploadProcessAjax" enctype="multipart/form-data">
	<div class="panel-heading">
		<h3 class="panel-title">이미지 등록</h3>
	</div>
	<div class="panel-body inputs">
		<div class="form-group">
		
			<input type="hidden"  id="uploadFileText" name="uploadFileText"    readonly="readonly" style="width:200px;" value=""/>
			<input type="file" id="uploadFile"  name="uploadFile" onChange="$('#uploadFileText').val(this.value);" style="width:100%;"/>
			<label for="uploadFile" class="sr-only">찾아보기...</label>
		</div>
		<ul class="list-unstyled">
			<li>이미지는 jpg 파일만 등록 가능합니다.</li>
			<li>이미지 용량은 최대 300KB입니다.</li>
		</ul>
	</div>
</form>

	<div class="panel-body btns text-center">
		<button class="btn btn-primary" onclick="javascript:excelUpload();">등록</button>
		<button class="btn btn-second" onclick="window.close();">취소</button> 
	</div>
</div>
