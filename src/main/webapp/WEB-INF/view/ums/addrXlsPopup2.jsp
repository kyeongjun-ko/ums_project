<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
	
	$("#btnUpload").click(function() {
    	
    	var file = $("#uploadFile").val();
    	if(file == '') {
    		alert("Please check upload file.");
    		return;
    	}
    	 
    	$("#uploadAjaxForm").ajaxSubmit({
            url : "/ums/addrExcelUploadProcessAjax",
            type : "POST",
            dataType : "html",
        	beforeSubmit: function() {
    	    },
    	    success: function(res) {    
    	    	if(res != null || res != 'null') {
    				window.opener.document.getElementById("divReceiverList").innerHTML += res;
    				window.opener.setReceiverCount();
    				// 마지막 cntReceiver 설정
    				var temp = window.opener.$('li[name=SubReceiveContent]');
    				if(temp != null && temp.length > 0) {
    					var idx = temp[temp.length-1].id.replace('SubReceiveContent','');
    					window.opener.cntReceiver = idx;
    				}				
    			}
    	    	window.close();
    	    },
    	    error: function(request, status, error) {
                alert("excel upload fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
    	    }
        });    	  	
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
        url : "/ums/addrExcelUploadProcessAjax2",
        type : "POST",
        dataType : "html",
    	beforeSubmit: function() {
	    },
	    success: function(res) {
	    	if(res != null && res.length > 0) {
				window.opener.document.getElementById("divReceiverList").innerHTML += res;
				window.opener.setReceiverCount();
				// 마지막 cntReceiver 설정
				var temp = window.opener.$('li[name=SubReceiveContent]');
				if(temp != null && temp.length > 0) {
					var idx = temp[temp.length-1].id.replace('SubReceiveContent','');
					window.opener.cntReceiver = idx;
				}			
				window.close();
			}else{
				alert("엑셀업로드 양식 오류입니다. 확인후 다시 처리해 주세요  (순서 : 전화번호,이름,내용 )");
				window.close();
			}
	    },
	    error: function(request, status, error) {
            alert("excel upload fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
	    }
    });   
}
</script> 
<div class="panel-default popup imgpop excelpop">
	<div class="panel-heading">
		<h3 class="panel-title">엑셀발송</h3>
	</div>
	<form id="uploadAjaxForm" name="uploadAjaxForm" method="post" action="/ums/addrExcelUploadProcessAjax2" enctype="multipart/form-data"> 
	<input type="hidden" name="uploadUserId" value=""/>
	<div class="panel-body inputs">
		<ul class="list-unstyled">
			<li> 
				<small>엑셀 파일로 직접 작성한 목록을 불러 발송할 수 있습니다. 발송하실 목록 파일을 선택해 주십시오.</small>
			</li>
		</ul>
		<div class="form-group">
			 
			<input type="hidden"   id="uploadFileText" name="uploadFileText" readonly="readonly" style="width:200px;" value=""/>
			<input type="file" id="uploadFile" name="uploadFile" onChange="$('#uploadFileText').val(this.value);"  style="width:100%;"/>
            <label for="uploadFile" class="sr-only">찾아보기...</label>
		</div>
		<h4>선택한 발송 목록 파일을 전송하시겠습니까?</h4>
	</div>
	</form>
	<div class="panel-body btns text-center">
		<button class="btn btn-primary" onclick="javascript:excelUpload();">확인</button>
		<button class="btn btn-second" onclick="window.close();">취소</button>
	</div>
	<div class="panel-body text-center">
		<div class="example">
				<p>* 엑셀발송 파일 문서종류는 xls이어야 하며<br><strong>전화번호 | 이름 | 내용</strong> 의 형식으로 작성하십시오.</p>
			<img src="/images/excel3.PNG" alt="엑셀예시">
		</div>
	</div>
	
	 
</div>
