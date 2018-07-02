<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>

<head>
<script>

$(document).ready(function () {
	
	byteCheckTextarea();
    
});

function checkAll(){ 
    $("input[name=arrQaId]").prop("checked", $("input[name=allChk]").is(":checked"));
}


function checkAny(){
    var bChecked = false;
    $("input[name=arrQaId]").each(function() {
        if(this.checked == true) bChecked = true;  
    });
    
    if(!bChecked) {
        alert("please select first.");
        return false;
    } 
    return true;
}

function checkDel() {
    if(!checkAny()) return false;
    
    if(confirm('Are you sure you want to delete?')) {
        
        $("#flagDel").val("Y");
        var f = document.forms["listForm"];
        f.action = "/emailmng/emailGroupWrite";
        f.method = "post";
        f.submit();
    }
}

function checkSubmit() {
	if(!checkAny()) return false;
    
    if(!confirm('Are you sure you want to save?')) return false;
    
    var f = document.forms["createForm"];
    f.action = "/qa/qaResultWrite";
    f.method = "post";
    f.submit();
    
    self.close();
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
    f.action = "/qa/qaResultsWritePopUpExcel";
    f.method = "post";
    f.submit();
}

function downloadView() {
	$("#csvName").val("");
    $("#downloadPopUp").toggle();
}


</script>
</head>


<!-- 850 // 결과 -->
<div class="LayerPop" style="width:900px;"><!-- 2013-12-24 크기 850에서 900px로 수정 -->
    <div class="Wrap">
        <h1>Result Change</h1>
        <h2 class="PgTitle1">Content Resulting Input <!--선택한 QA 대기 중인 컨텐츠의 결과를 일괄입력합니다. --></h2>
        
         <form:form  method="post" modelAttribute="qaVO" id="createForm">
            <!--  search 영역 -->
           <input type=hidden name="srchTerritory" value="${searchQaVO.srchTerritory}">
           <input type=hidden name="currentPageNo" value="${searchQaVO.currentPageNo}">
           <!--  search 영역 end -->
           
        <form:hidden path="qaId" />   
        <input type=hidden name="arrProductId" value="${qaVO.productId}_${qaVO.territory}">
        
        <div class="PgTitle_wrap MgTop20">
            <h2 class="PgTitle1">Contents (<em id="emCount">${fn:length(qaContList)}</em>)</h2>
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
                            <a class="btnTypeK" href="#"><span onclick="downloadView();">NO</span></a>
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
                    <th scope="col" class="first"><input type=checkbox name="allChk" id="allChk" onclick="checkAll();"></input></th><!--  Ltext -->
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
                    <td><input class="input_check" type=checkbox name="arrQaId" value="${item.qaId}"></input> ${status.index + 1}</td>
                    <td>${item.contTitle}</td>
                    <td>${item.orderId}</td>
                    <td>${item.cid}</td>
                    <td>${item.contTypeNm}</td>
                    <td>${item.cpNm}</td>
                    <c:set var="encodingHouse" value="" />
                    <c:if test="${ item.encodeVendor eq '01' }"><c:set var="encodingHouse" value="QPM" /></c:if>
                    <c:if test="${ item.encodeVendor eq '02' }"><c:set var="encodingHouse" value="Deluxe" /></c:if>
                    <c:if test="${ item.encodeVendor eq '03' }"><c:set var="encodingHouse" value="Samsung Encoder" /></c:if>
                    <td scope="row">${encodingHouse}</td>
                    <td>${item.territoryNm} </td>
                </tr>
            </c:forEach>     
           </c:if>  
         
            </tbody>
        </table>
        </div>
        
        <h2 class="PgTitle1 MgTop20">Result Change</h2> 
        Pass(Part) <input type=hidden name="qaResult" value="PASS_PART">
        
        <h2 class="PgTitle1 MgTop20">Detail Content</h2> 
        <textarea name="description" cols="0" rows="0" title="상세 내용 입력" class="textarea1" byteCheck="true" maxlength="500" style="min-width:98%;min-height:120px;max-width:98%;max-height:120px"></textarea>
        <div class="BtnArea">
            <a class="btnTypeK" href="#"><span onclick="checkSubmit();">Save<!--결과 입력 --></span></a>
            <a class="btnTypeK" href="javascript:self.close();"><span>Cancel</span></a>
        </div>
        <a href="javascript:self.close();"><img src="/images/common/btn_pop_close.gif" class="BtnClose" alt="Close" /></a>
        
        </form:form>
        
    </div>
</div>

    
    
</body>
</html>