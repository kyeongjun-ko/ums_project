<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>

<head>
<script>

$(document).ready(function () {
    byteCheckTextarea();
});

function checkSubmit() {
        
    if(!checkAny()) return false;
    if(!confirm('do you really want to submit?')) return false;
    
    /*
    var f = document.forms["createForm"];
    f.action = "/qa/qaContentsWrite";
    f.method = "post";
    f.submit();
    
    self.close();
    */
    var param = $("#createForm").serialize();
    $.ajax({
        type : "POST",
        url : "/qa/qaContentsWriteAjax",
        data : param,
        success: function(json) {
            if(json.result) {
            	alert("QA request success.");
            	self.close();
            } else {
            	alert(json.errMsg);
            }
        },
        error: function(request, status, error) {
            alert("qa creation fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
        }
    });
}


function checkAll(){ 
    $("input[name=arrProductId]").prop("checked", $("input[name=allChk]").is(":checked"));
}


function checkAny(){
    var bChecked = false;
    $("input[name=arrProductId]").each(function() {
        if(this.checked == true) bChecked = true;  
    });
    
    if(!bChecked) {
        alert("please check first.");
        return false;
    } 
    return true;
}



function excelDownload() {
	
	if(!checkAny()) return false;
	
	$("#csvName").val(trim($("#csvName").val()));
	var csvName = $("#csvName").val();
    if(!csvName) {
        alert("You must enter \"CSV Name\".");
        return;
    }
    
    var special = /['~!@#$%^&*|\\\'\";:\/?]/gi;
    if(special.test($("#csvName").val()) == true) {
        alert("A special character can not be input csv name.");
        return;
    } 
    
    if(csvName.lastIndexOf(".xls") < 0) {
        csvName += ".xls";
        $("#csvName").val(csvName);
    }
    
    $("#downloadPopUp").toggle();
    
    var f = document.forms["createForm"];
    f.action = "/qa/qaContentsListPopUpExcel";
    f.method = "post";
    f.submit();
}

function downloadView() {
	$("#csvName").val("");
    $("#downloadPopUp").toggle();
}



</script>
</head>

<!-- 650 // QA request -->
<div class="LayerPop" style="width:650px;">
    <div class="Wrap">
        <h1>QA Request</h1>
        
        <form:form  method="post" modelAttribute="qaVO" id="createForm">
        
        <div class="PgTitle_wrap">
            <h2 class="PgTitle1">QA Contents <em>(${fn:length(qaContList)})</em></h2>
            <span class="sel">
                <a class="btnTypeI" href="javascript:downloadView();"><span><strong class="sty2">CSV</strong></span></a>
                <!-- w:274 // CSV Download -->
                <div id="downloadPopUp" class="LayerPop" style="width:270px;top:0;right:170px;display:none;">
                    <div class="Wrap">
                        <h1>CSV Download</h1>
                        <h2 class="PgTitle1">Warning</h2>
                        <p>Please enter CSV file name.</p>
                        <div class="CategorySec">
                            <span>CSV Name</span>&nbsp;&nbsp;<input id="csvName" name="csvName" class="input_text" maxlength="30" style="width:160px;" />
                        </div>
                        <p>Would you like to request CSV file download?</p>
                        <div class="BtnArea">
                            <a class="btnTypeK" href="#"><span onclick="excelDownload();">Yes</span></a>
                            <a class="btnTypeK" href="#"><span onclick="downloadView();" >NO</span></a>
                        </div>
                        <a href="javascript:downloadView();"><img src="/images/common/btn_pop_close.gif" class="BtnClose" alt="Close" /></a>
                    </div>
                </div>
                
            </span>
        </div>
        <div class="DataTbl_head3">
            <table cellpadding="0" cellspacing="0">
                <colgroup>
                    <col width="8%" /><col width="20%" /><col width="10%" /><col width="15%" />
                    <col width="10%" /><col width="15%" /><col width="8%" /><col width="14%" />
                </colgroup>
                <thead>
                <tr>
                    <th scope="col" class="first"><input class="input_check" type="checkbox" name="allChk" id="allChk" onclick="checkAll();"/></th>
                    <th scope="col">Title</th>
                    <th scope="col">Integration ID</th>
                    <th scope="col">CID</th>
                    <th scope="col">Type</th>
                    <th scope="col">CP</th>
                    <th scope="col">Encoding House</th>
                    <th scope="col">Territory</th>
                </tr>
                </thead>
            </table>
        </div>
        <div class="TblScroll lineType1" style="height:129px;">
            <table cellpadding="0" cellspacing="0" class="DataTbl3">
            <colgroup>
                    <col width="8%" /><col width="20%" /><col width="12%" /><col width="15%" />
                    <col width="10%" /><col width="15%" /><col width="10%" /><col width="10%" />
            </colgroup>
            <tbody>
            <c:if test="${! empty qaContList}">
              <c:forEach var="item" items="${qaContList}" varStatus="status">
                <tr>
	                <td><input class="input_check" type=checkbox name="arrProductId" value="${item.productId}!@${item.cid}!@${item.territory}!@${item.prodCurState}!@${item.orderId}"></input> ${status.index + 1}</td>
	                <td>${item.contTitle}</td>
	                <td>${item.orderId}</td>
	                <td>${item.cid}</td>
	                <td>${item.contTypeNm}</td>
	                <td>${item.cpNm}</td>
	                
	                <c:set var="encodingHouse" value="" />
                    <c:if test="${ item.encodeVendor eq '01' }"><c:set var="encodingHouse" value="QPM" /></c:if>
                    <c:if test="${ item.encodeVendor eq '02' }"><c:set var="encodingHouse" value="Deluxe" /></c:if>
                    <c:if test="${ item.encodeVendor eq '03' }"><c:set var="encodingHouse" value="Samsung Encoder" /></c:if>
                    <td>${encodingHouse}</td>
	                <td>${item.territoryNm} </td>
	            </tr>
            </c:forEach>     
           </c:if>  
              
            </tbody>
        </table>
        </div>
        <div class="BoardDetail MgTop20">
            <table cellpadding="0" cellspacing="0">
                <colgroup>
                    <col width="20%" /><col width="30%" /><col width="22%" /><col width="28%" />
                </colgroup>
                <tbody>
                <tr>
                    <th>Classification</th>
                    <td colspan="3" class="Ltext">
                        <form:radiobutton path="urgentFlag" value="01" cssClass="input_radio" checked="checked" /> <label for="p_qa2_1">General</label>
                        <form:radiobutton path="urgentFlag" value="02" cssClass="input_radio"  /> <label for="p_qa2_2">First</label>
                        <form:radiobutton path="urgentFlag" value="03" cssClass="input_radio"  /> <label for="p_qa2_3">Urgent</label>
                        <span class="db_type01">
                            <form:checkbox path="massYn" value="Y" cssClass="input_check" /> <label for="p_qa2_4"> bulk changes</label>
                        </span>
                    </td>
                </tr>
                <tr>
                    <th><label for="p_qa5">Verification Requests</label></th>
                    <td colspan="3" class="Ltext">
                        <textarea cols="0" rows="0" title="검증 요청사항 입력" id="p_qa5" name="reqCont" class="textarea1" byteCheck="true" maxlength="500" style="min-width:98%;min-height:120px;max-width:98%;max-height:120px"></textarea>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="BtnArea">
            <a class="btnTypeK" href="#"><span onclick="checkSubmit();">request</span></a>
            <a class="btnTypeK" href="javascript:self.close();"><span>Cancel</span></a>
        </div>
        <a href="javascript:self.close();"><img src="../images/common/btn_pop_close.gif" class="BtnClose" alt="Close" /></a>
        
        </form:form>
    </div>
</div>


    
    
</body>
</html>