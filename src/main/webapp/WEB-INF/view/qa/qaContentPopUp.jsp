<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>

<head>
<script>

$(document).ready(function () {
    byteCheckTextarea();
});


	function checkSubmit() {
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
		
	function excelDownload() {
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
	    
	    var f = document.forms["listForm"];
	    f.action = "/emailmng/emailReceiverListExcel";
	    f.method = "post";
	    f.submit();
	}

	function downloadView() {
		$("#csvName").val("");
	    $("#downloadPopUp").toggle();
	}
	
</script>
</head>

<!-- 850 // QA 요청 -->
<div class="LayerPop" style="width:850px;">
    <div class="Wrap">
        
        <form:form  method="post" modelAttribute="qaVO" id="createForm">
        
        <h1>QA request</h1>
        <div class="PgTitle_wrap">
            <h2 class="PgTitle1">QA Contents</h2>
        </div>
        <div class="BoardList">
            <table cellpadding="0" cellspacing="0">
                <colgroup>
                    <col width="23%" /><col width="15%" /><col width="15%" />
                    <col width="10%" /><col width="15%" /><col width="10%" /><col width="12%" />
                </colgroup>
                <thead>
                <tr>
                    <th scope="col" class="first">Title</th>
                    <th scope="col">Integration ID</th>
                    <th scope="col">CID</th>
                    <th scope="col">Type</th>
                    <th scope="col">CP</th>
                    <th scope="col">Encoding House</th>
                    <th scope="col">Territory</th>
                </tr>
                </thead>
                <tbody>
                
           <c:if test="${! empty qaContList}">
              <c:forEach var="item" items="${qaContList}" varStatus="status">
                 <tr>
                    <td scope="row">${item.contTitle}<input type=hidden name="arrProductId" value="${item.productId}!@${item.cid}!@${item.territory}!@${item.prodCurState}!@${item.orderId}"></td>
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
                    <th rowspan="2">Classification</th>
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
                    <td colspan="3" class="Ltext">
                        <form:radiobutton path="reqFlag" value="01" cssClass="input_radio" checked="checked" /> <label for="p_qa2_5">New Registration</label>
                        <form:radiobutton path="reqFlag" value="02" cssClass="input_radio"  /> <label for="p_qa2_6">Add new country</label>
                        <form:radiobutton path="reqFlag" value="03" cssClass="input_radio"  /> <label for="p_qa2_7">Modifying Meta</label>
                        <form:radiobutton path="reqFlag" value="04" cssClass="input_radio"  /> <label for="p_qa2_8">Video Modifying</label>
                        <form:radiobutton path="reqFlag" value="05" cssClass="input_radio"  /> <label for="p_qa2_9">License</label>
                        <form:radiobutton path="reqFlag" value="06" cssClass="input_radio"  /> <label for="p_qa2_10">Modifying Device</label>
                    </td>
                </tr>
                <tr>
                    <th><label for="p_qa2_11">Verification Requests</label></th>
                    <td colspan="3" class="Ltext">
                        <textarea cols="0" rows="0" title="검증 요청사항 입력" id="p_qa2_11" name="reqCont" class="textarea1" byteCheck="true" maxlength="500" style="min-width:98%;min-height:120px;max-width:98%;max-height:120px"></textarea>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <h2 class="PgTitle1 MgTop20">Verification Items</h2>        
        <div class="BoardList bd_none">
            <table cellpadding="0" cellspacing="0">
                <colgroup>
                    <col />
                </colgroup>
                <thead>
                <tr>
                    <th scope="col" rowspan="4" class="first bd_color1">Trailer</th>
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
                <tbody>
                <tr class="min_h">
                    <td><form:checkbox path="arrItem" value="A_TRAILER" cssClass="input_check" /></td>
                    <td><form:checkbox path="arrItem" value="A_H264_SD" cssClass="input_check" /></td>
                    <td><form:checkbox path="arrItem" value="A_H264_HD" cssClass="input_check" /></td>
                    <td><form:checkbox path="arrItem" value="A_H264_FHD" cssClass="input_check" /></td>
                    <td><form:checkbox path="arrItem" value="A_HEVC_ST_SD" cssClass="input_check" /></td>
                    <td><form:checkbox path="arrItem" value="A_HEVC_ST_HD" cssClass="input_check" /></td>
                    <td><form:checkbox path="arrItem" value="A_HEVC_ST_FHD" cssClass="input_check" /></td>
                    <td><form:checkbox path="arrItem" value="A_HEVC_DL_SD" cssClass="input_check" /></td>
                    <td><form:checkbox path="arrItem" value="A_HEVC_DL_HD" cssClass="input_check" /></td>
                    <td><form:checkbox path="arrItem" value="A_HEVC_DL_FHD" cssClass="input_check" /></td>
                    <td><form:checkbox path="arrItem" value="A_HOME_SYNC" cssClass="input_check" /></td>
                    <td><form:checkbox path="arrItem" value="A_TV_SD" cssClass="input_check" /></td>
                    <td><form:checkbox path="arrItem" value="A_TV_HD" cssClass="input_check" /></td>
                    <td><form:checkbox path="arrItem" value="A_TV_FHD" cssClass="input_check" /></td>
                    <td><form:checkbox path="arrItem" value="A_PC_SD" cssClass="input_check" /></td>
                    <td><form:checkbox path="arrItem" value="A_PC_HD" cssClass="input_check" /></td>
                    <td><form:checkbox path="arrItem" value="A_PC_FHD" cssClass="input_check" /></td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="BtnArea">
            <a class="btnTypeK" href="#"><span onclick="checkSubmit();">request</span></a>
            <a class="btnTypeK" href="javascript:self.close();"><span>Cancel</span></a>
        </div>
        <a href="javascript:self.close();"><img src="/images/common/btn_pop_close.gif" class="BtnClose" alt="Close" /></a>
        </form:form>
    </div>
</div>

    
    
</body>
</html>