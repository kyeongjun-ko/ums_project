<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>

<head>
<script>

$(document).ready(function () {
	
	byteCheckTextarea();
	
	$(".RItemClass").click(function() {
        if($(this).is(":checked")) {
            $(".qaResultClass").attr("checked", false);
        } else {
            
        }
    });
     
    $(".qaResultClass").click(function() {
        if($(this).is(":checked")) {
            $(".RItemClass").attr("checked", false);
            $(".MClass").attr("disabled", true);
        } else {
            
        }
    });
    
    /* 
    $(".MALLClass").click(function() {
        if($(this).is(":checked")) {
            $(".MClass").attr("checked", false);
        } else {
            
        }
    });
    $(".MClass").click(function() {
        if($(this).is(":checked")) {
            $(".MALLClass").attr("checked", false);
        } else {
            
        }
    }); 
    */
    $(".MALLClass").click(function() {
        if($(this).is(":checked")) {
        	$(".MClass").attr("disabled", false);
        } else {
        	$(".MClass").attr("checked", false);
            $(".MClass").attr("disabled", true);
        }
    });
    
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
        
    if($(".qaResultClass").is(":checked") == false) {
        if($(".RItemClass").is(":checked") == false) {
            if($(".MClass").is(":checked") == false) {
                alert('you must select Result. check Pass or check Fail Item.');
                return false;
            } 
        } 
    }
    
    if(!confirm('Are you sure you want to save?')) return false;
    
    /*
    var f = document.forms["createForm"];
    f.action = "/qa/qaResultWrite";
    f.method = "post";
    f.submit();
    
    self.close();
    */
    var param = $("#createForm").serialize();
    $.ajax({
        type : "POST",
        url : "/qa/qaResultWriteAjax",
        data : param,
        success: function(json) {
            if(json.result) {
                alert("Qa result save success.");
                self.close();
            } else {
                alert(json.errMsg);
            }
        },
        error: function(request, status, error) {
            alert("Qa result creation fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
        }
    });
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
        <h1>Result</h1>
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
        <h2 class="PgTitle1 MgTop20">Video Verification Items</h2>        
        <div class="BoardList bd_none">
            <table cellpadding="0" cellspacing="0">
                <colgroup>
                    <col />
                </colgroup>
                <!-- 2013-12-24 수정 -->
                <thead>
                <tr>
                    <th scope="col" rowspan="4" class="first bd_color1">&nbsp;</th>
                    <th scope="col" rowspan="4" class="bd_color1">Trailer</th>
                    <th scope="col" colspan="9" class="bd_color1">Mobile</th>
                    <th scope="col" rowspan="4" class="bd_color2">Home Sync</th>
                    <th scope="col" rowspan="3" colspan="3" class="bd_color3">TV Device</th>
                    <th scope="col" rowspan="3" colspan="3" class="bd_color4">PC</th>
                </tr>
                <tr>
                    <th scope="col" colspan="3" rowspan="2">H.264</th>
                    <th scope="col" colspan="6">HEVC</th>
                </tr>
                <tr>
                    <th scope="col" colspan="3">Stream</th>
                    <th scope="col" colspan="3">Download</th>
                </tr>
                <tr>
                    <th scope="col">SD</th>
                    <th scope="col">HD</th>
                    <th scope="col">FHD</th>
                    <th scope="col">SD</th>
                    <th scope="col">HD</th>
                    <th scope="col">FHD</th>
                    <th scope="col">SD</th>
                    <th scope="col">HD</th>
                    <th scope="col">FHD</th>
                    <th scope="col">SD</th>
                    <th scope="col">HD</th>
                    <th scope="col">FHD</th>
                    <th scope="col">SD</th>
                    <th scope="col">HD</th>
                    <th scope="col">FHD</th>
                </tr>
                </thead>
                <!-- //2013-12-24 수정 -->
                <tbody>
                <tr class="min_h">
                    <td colspan="18" class="Ltext">
                    
                    <form:radiobutton path="qaResult" value="PASS" cssClass="input_radio qaResultClass" />
                    
                    <label for="r_video1"><span class="fc_0083ca">pass</span></label>                   
                    </td>
                </tr>
                <tr class="min_h">
                    <td colspan="18" class="Ltext">
                    
                    <form:radiobutton path="qaResult" value="PASS_REVIS" cssClass="input_radio qaResultClass" />
                    
                    <label for="r_video2"><span class="fc_0083ca">pass (Revised)</span></label>                 
                    </td>
                </tr>
                <tr class="min_h">
                    
                    <td> &nbsp;  </td>
                 
                    <td><form:checkbox path="arrRItem" value="R_TRAILER" cssClass="input_check RItemClass" /> <span class="db_type02">V-Fail</span></td>
                                <td><form:checkbox path="arrRItem" value="R_H264_SD" cssClass="input_check RItemClass" />  <span class="db_type02">V-Fail</span></td>
                                <td><form:checkbox path="arrRItem" value="R_H264_HD" cssClass="input_check RItemClass" />   <span class="db_type02">V-Fail</span></td>
                                <td><form:checkbox path="arrRItem" value="R_H264_FHD" cssClass="input_check RItemClass" />  <span class="db_type02">V-Fail</span></td>
                                <td><form:checkbox path="arrRItem" value="R_HEVC_ST_SD" cssClass="input_check RItemClass" /> <span class="db_type02">V-Fail</span></td>
                                <td><form:checkbox path="arrRItem" value="R_HEVC_ST_HD" cssClass="input_check RItemClass" /> <span class="db_type02">V-Fail</span></td>
                                <td><form:checkbox path="arrRItem" value="R_HEVC_ST_FHD" cssClass="input_check RItemClass" /> <span class="db_type02">V-Fail</span></td>
                                <td><form:checkbox path="arrRItem" value="R_HEVC_DL_SD" cssClass="input_check RItemClass" /> <span class="db_type02">V-Fail</span></td>
                                <td><form:checkbox path="arrRItem" value="R_HEVC_DL_HD" cssClass="input_check RItemClass" /> <span class="db_type02">V-Fail</span></td>
                                <td><form:checkbox path="arrRItem" value="R_HEVC_DL_FHD" cssClass="input_check RItemClass" /> <span class="db_type02">V-Fail</span></td>
                                <td><form:checkbox path="arrRItem" value="R_HOME_SYNC" cssClass="input_check RItemClass" /> <span class="db_type02">V-Fail</span></td>
                                <td><form:checkbox path="arrRItem" value="R_TV_SD" cssClass="input_check RItemClass" /> <span class="db_type02">V-Fail</span></td>
                                <td><form:checkbox path="arrRItem" value="R_TV_HD" cssClass="input_check RItemClass" /> <span class="db_type02">V-Fail</span></td>
                                <td><form:checkbox path="arrRItem" value="R_TV_FHD" cssClass="input_check RItemClass" /> <span class="db_type02">V-Fail</span></td>
                                <td><form:checkbox path="arrRItem" value="R_PC_SD" cssClass="input_check RItemClass" /> <span class="db_type02">V-Fail</span></td>
                                <td><form:checkbox path="arrRItem" value="R_PC_HD" cssClass="input_check RItemClass" /> <span class="db_type02">V-Fail</span></td>
                                <td><form:checkbox path="arrRItem" value="R_PC_FHD" cssClass="input_check RItemClass" /> <span class="db_type02">V-Fail</span></td>
                   
                </tr>
                </tbody>
            </table>
        </div>
        <h2 class="PgTitle1 MgTop20">Metadata Verification Items</h2>       
        <div class="BoardList bd_none">
            <table cellpadding="0" cellspacing="0">
                <colgroup>
                    <col />
                </colgroup>
                <thead>
                <tr>
                    <th scope="col" class="first bd_color1">Metadata</th>
                    <th scope="col" rowspan="2" class="bd_color1">Title</th>
                    <th scope="col" rowspan="2" class="bd_color1">Poster</th>
                    <th scope="col" rowspan="2" class="bd_color1">Genre</th>
                    <th scope="col" rowspan="2" class="bd_color1">Directors</th>
                    <th scope="col" rowspan="2" class="bd_color1">Actors</th>
                    <th scope="col" rowspan="2" class="bd_color1">Synopsis</th>
                    <th scope="col" rowspan="2" class="bd_color1">Audio Language</th>
                    <th scope="col" rowspan="2" class="bd_color1">Subtitles</th>
                    <th scope="col" rowspan="2" class="bd_color1">Running Time</th>
                    <th scope="col" rowspan="2" class="bd_color1">Release Year/Date</th>
                    <th scope="col" rowspan="2" class="bd_color1">Rating</th>
                    <th scope="col" class="bd_color4">Price</th>
                    <!-- 
                    <th scope="col" class="bd_color4">Other</th>
                     -->
                </tr>
                <tr>
                    <th scope="col" class="first">&nbsp;</th>
                    <th scope="col">&nbsp;</th>
                    <!-- 
                    <th scope="col">&nbsp;</th>
                     -->
                </tr>
                </thead>
                <tbody>
                <tr class="min_h">
                    
                    <td>
                         <form:checkbox path="arrRMeta" value="M_ALL" cssClass="input_check RItemClass MALLClass"/>
                         <span class="db_type02">M-Fail</span>
                     </td>
                     <td><form:checkbox path="arrRMeta" value="M_Title" cssClass="input_check RItemClass MClass" disabled="true" /></td>
                     <td><form:checkbox path="arrRMeta" value="M_Poster" cssClass="input_check RItemClass MClass" disabled="true" /></td>
                     <td><form:checkbox path="arrRMeta" value="M_Genre" cssClass="input_check RItemClass MClass" disabled="true" /></td>
                     <td><form:checkbox path="arrRMeta" value="M_Directors" cssClass="input_check RItemClass MClass" disabled="true" /></td>
                     <td><form:checkbox path="arrRMeta" value="M_Actors" cssClass="input_check RItemClass MClass" disabled="true" /></td>
                     <td><form:checkbox path="arrRMeta" value="M_Synopsis" cssClass="input_check RItemClass MClass" disabled="true" /></td>
                     <td><form:checkbox path="arrRMeta" value="M_AudioLang" cssClass="input_check RItemClass MClass" disabled="true" /></td>
                     <td><form:checkbox path="arrRMeta" value="M_SubTitles" cssClass="input_check RItemClass MClass" disabled="true" /></td>
                     <td><form:checkbox path="arrRMeta" value="M_RunTime" cssClass="input_check RItemClass MClass" disabled="true" /></td>
                     <td><form:checkbox path="arrRMeta" value="M_ReleaseYD" cssClass="input_check RItemClass MClass" disabled="true" /></td>
                     <td><form:checkbox path="arrRMeta" value="M_Rating" cssClass="input_check RItemClass MClass" disabled="true" /></td>
                     <td>
                         <form:checkbox path="arrRMeta" value="P_Price" cssClass="input_check RItemClass PriceClass" />
                         <span class="db_type02">Fail</span>  <!-- M_Price - P_Price 로 바꿀것. -->
                     </td>
                     <!-- 
                     <td>
                         <form:checkbox path="arrRMeta" value="O_Other" cssClass="input_check RItemClass OtherClass" />
                         <span class="db_type02">OF</span>
                     </td>
                      -->
                </tr>
                </tbody>
            </table>
        </div>
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