<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>

<head>
<script>

$(document).ready(function() {
    
    $(".ViewHistory").hide();
    $(".btnclose").hide();
    
    $("#backTrackType").attr("disabled",true);
    $("#dontChange").attr("disabled",true);
    $("#changeBackTrack").attr("disabled",true);
    
    byteCheckTextarea();
    
    $("#dontChange").click(function() {
        if($(this).is(":checked")) {
            $("#backTrackType option:eq(0)").attr("selected", "selected");
            //$("#backTrackType").attr("disabled","disabled");
        } else {
        }
    });
    
    $("#changeBackTrack").click(function() {
        if($(this).is(":checked")) {
        	//$("#backTrackType").attr("disabled",false);
        	$("#backTrackType option:eq(0)").attr("selected", "selected");
        } else {
        }
    });
      
    
    $(".RItemClass").click(function() {
        if($(this).is(":checked")) {
            $(".qaResultClass").attr("checked", false);
            
            $("#backTrackType").attr("disabled",false);
            $("#dontChange").attr("disabled",false);
            $("#changeBackTrack").attr("disabled",false);
        } else {
            
        }
    });
     
    $(".qaResultClass").click(function() {
        if($(this).is(":checked")) {
            $(".RItemClass").attr("checked", false);
            $(".MClass").attr("disabled", true);
            
            backTrackDispInit();
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
    
    <c:choose>
    <c:when test="${searchQaVO.qaReqComplFlag eq 'compl'}">
         <c:if test="${gaVO.backTrackType == null || gaVO.backTrackType == ''}">
            //$("#backTrackType").attr("disabled","disabled");
         </c:if>
         
         $(".RItemClass").attr("disabled", "disabled");
         $(".CItemClass").attr("disabled", "disabled");
         $("#description").attr("disabled", "disabled");
         $(".qaResultClass").attr("disabled", "disabled");
         $(".backTrackClass").attr("disabled", "disabled");
         $(".backTrackSelectClass").attr("disabled", "disabled");
    </c:when>
    <c:otherwise>
        $(".MClass").attr("disabled", true);
	    $("#dontChange").attr("checked", true);
	    $("#backTrackType option:eq(0)").attr("selected", "selected");
	    //$("#backTrackType").attr("disabled","disabled");
    </c:otherwise>
    </c:choose>
    
    $(".AItemClass").attr("disabled", "disabled");
    $("input[name=reqFlag]").attr("disabled", "disabled");
    
    $(".hisReqFlagClass").attr("disabled", "disabled");
    $(".hisAItemClass").attr("disabled", "disabled");
    $(".hisCItemClass").attr("disabled", "disabled");
    $(".hisRItemClass").attr("disabled", "disabled");
    $(".hisMItemClass").attr("disabled", "disabled");
    $(".hisResultClass").attr("disabled", "disabled");
    $(".hisBackTrackClass").attr("disabled", "disabled");
    $(".hisBackTrackSelectClass").attr("disabled", "disabled");
    
    
});


function backTrackDispInit() {
	$("#backTrackType option:eq(0)").attr("selected", "selected");
    $("#dontChange").attr("checked",true);
    $("#changeBackTrack").attr("checked",false);
    $("#backTrackType").attr("disabled",true);
    $("#dontChange").attr("disabled",true);
    $("#changeBackTrack").attr("disabled",true);
}

function selectChangeBackTrack() {
	$("#dontChange").attr("checked", false);
	$("#changeBackTrack").attr("checked", true);
}


function showDetail(id) {
	$("#ViewHistory_" + id).show();
	$("#btnOpen_" + id).hide();
	$("#btnClose_" + id).show();
}

function closeDetail(id) {
    $("#ViewHistory_" + id).hide();
    $("#btnOpen_" + id).show();
    $("#btnClose_" + id).hide();
}

function gotoList() {
    var f = document.forms["searchForm"];
    
    <c:choose>
    <c:when test="${searchQaVO.qaReqComplFlag eq 'compl'}">
         f.action = "/qa/qaCompleteList";
    </c:when>
    <c:otherwise>
         f.action = "/qa/qaRequestList";
    </c:otherwise>
    </c:choose>
    
    f.method = "post";
    f.submit();
}


function checkAll(){ 
    $("input[name=arrEmailSeq]").prop("checked", $("input[name=allChk]").is(":checked"));
}

function checkAll2(){ 
    if($(".MALLClass").is(":checked")) {
        $(".MClass").attr("disabled", false);
    } else {
        $(".MClass").attr("checked", false);
        $(".MClass").attr("disabled", true);
    }
}

function checkAny(){
    var bChecked = false;
    $("input[name=arrEmailSeq]").each(function() {
    	
        if(this.checked == true) bChecked = true;  
    });
    
    if(!bChecked) {
        alert("please check first.");
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
    
	if($("#changeBackTrack").is(":checked")) {  
	    if($("#backTrackType option:selected").val() == "") {
	        alert("you must select back track type.");
	        return false;
	    }
    }
    
    if($(".qaResultClass").is(":checked") == false) {
        if($(".RItemClass").is(":checked") == false) {
            if($(".MClass").is(":checked") == false) {
                alert('you must select Result. check Pass or check Fail Item.');
                return false;
            } 
        } 
    }
    
    if(!confirm('Are you sure you want to save?')) return false;
    
    var f = document.forms["createForm"];
    f.action = "/qa/qaResultWrite";
    f.method = "post";
    f.submit();
}    

function goDetailInfo(qaId) {

    var f = document.forms["createForm"];

    //f.reset();

    f.action = "/qa/qaDetailInfo?qaId="+qaId;
    f.method = "post";
    f.submit();
}

function popup(productID) {
	    $("#productBaseId").val(productID);
	    //$("#shopId").val(shopID);
	    
	    //var url = $("#currentURL").val() + "/portal/product/product.showProduct?productBaseId="+productID+"&shopId="+shopID;
	    var url = $("#currentURL").val() + "/portal/product/product.newProductBaseOverview?productBaseId="+productID;
	    window.open(url);  
	    
/* 	    window.open('','contFlow');
	    document.popForm.action = $("#currentURL").val() + "/portal/product/product.newProductBaseOverview";
	    document.popForm.target = "contFlow";
	    document.popForm.method = "post";
	    //document.popForm.shopId.value = shopID;
	    document.popForm.productBaseId.value = productID;
	    document.popForm.submit(); */
}

</script>
</head>


<c:set var="arrHtmlRItem">R_TRAILER,R_H264_SD,R_H264_HD,R_H264_FHD,R_HEVC_ST_SD,R_HEVC_ST_HD,R_HEVC_ST_FHD,R_HEVC_DL_SD,R_HEVC_DL_HD,R_HEVC_DL_FHD,R_HOME_SYNC,R_TV_SD,R_TV_HD,R_TV_FHD,R_PC_SD,R_PC_HD,R_PC_FHD</c:set>


	    <form method="post" id="popForm" name="popForm">
	        <input type="hidden" id="productBaseId" name="productBaseId" />
	        <input type="hidden" id="shopId" name="shopId" />
	    </form>
    
    <!-- Navi
        <div class="Navi">
            <a href="#none">Home </a> &gt; <a href="#none">QA</a> &gt; 
            <c:choose>
		    <c:when test="${searchQaVO.qaReqComplFlag eq 'compl'}">
		         <a href="#none"> QA Completion</a> &gt; <strong>Details </strong>
		    </c:when>
		    <c:otherwise>
		         <a href="#none"> QA standby</a> &gt; <strong>Details </strong>
		    </c:otherwise>
		    </c:choose>
            
        </div>
         -->
        
        <input type=hidden id="currentURL" name="currentURL" value="${currentURL}">
        
        
        <form:form  method="post" modelAttribute="searchQaVO" id="searchForm">
        <!--  search 영역 -->
        <c:if test="${searchQaVO.arrTerritory != null && fn:length(searchQaVO.arrTerritory) > 0}">
               <c:forEach var="item" items="${searchQaVO.arrTerritory}">
                 <input type=hidden name="arrTerritory" value="${item}">
               </c:forEach>
           </c:if>
           <c:if test="${searchQaVO.arrVendorNm != null && fn:length(searchQaVO.arrVendorNm) > 0}">
               <c:forEach var="item" items="${searchQaVO.arrVendorNm}">
                 <input type=hidden name="arrVendorNm" value="${item}">
               </c:forEach>
           </c:if>
           <c:if test="${searchQaVO.arrRegion != null && fn:length(searchQaVO.arrRegion) > 0}">
               <c:forEach var="item" items="${searchQaVO.arrRegion}">
                 <input type=hidden name="arrRegion" value="${item}">
               </c:forEach>
           </c:if>
           <c:if test="${searchQaVO.arrProdState != null && fn:length(searchQaVO.arrProdState) > 0}">
               <c:forEach var="item" items="${searchQaVO.arrProdState}">
                 <input type=hidden name="arrProdState" value="${item}">
               </c:forEach>
           </c:if>
           
           
        
        <input type=hidden name="currentPageNo" value="${searchQaVO.currentPageNo}">
        <!--  search 영역 end -->
        </form:form>    
           
        <form:form  method="post" modelAttribute="qaVO" id="createForm">
            <!--  search 영역 -->
            
            <c:if test="${searchQaVO.arrTerritory != null && fn:length(searchQaVO.arrTerritory) > 0}">
               <c:forEach var="item" items="${searchQaVO.arrTerritory}">
                 <input type=hidden name="arrTerritory" value="${item}">
               </c:forEach>
           </c:if>
           <c:if test="${searchQaVO.arrVendorNm != null && fn:length(searchQaVO.arrVendorNm) > 0}">
               <c:forEach var="item" items="${searchQaVO.arrVendorNm}">
                 <input type=hidden name="arrVendorNm" value="${item}">
               </c:forEach>
           </c:if>
           <c:if test="${searchQaVO.arrRegion != null && fn:length(searchQaVO.arrRegion) > 0}">
               <c:forEach var="item" items="${searchQaVO.arrRegion}">
                 <input type=hidden name="arrRegion" value="${item}">
               </c:forEach>
           </c:if>
           <c:if test="${searchQaVO.arrProdState != null && fn:length(searchQaVO.arrProdState) > 0}">
               <c:forEach var="item" items="${searchQaVO.arrProdState}">
                 <input type=hidden name="arrProdState" value="${item}">
               </c:forEach>
           </c:if>
           
           <input type=hidden name="currentPageNo" value="${searchQaVO.currentPageNo}">
           <!--  search 영역 end -->
           
        <form:hidden path="qaId" />
        <form:hidden path="orderId" />
        <form:hidden path="shopId" />
        
        <!-- 
        <input type=hidden name="arrProductId" value="${qaVO.productId}_${qaVO.territory}">
         -->
        <input type=hidden name="arrQaId" value="${qaVO.qaId}">
        
         <c:choose>
            <c:when test="${searchQaVO.qaReqComplFlag eq 'compl'}">
            <!-- <h2 class="HeadTitle">QA Completion Details</h2>  -->     
                 <h3 class="PgTitle1">Details</h3>                                
            </c:when>
            <c:otherwise>
            <!-- <h2 class="HeadTitle">QA standby Details</h2>  -->
                 <h3 class="PgTitle1">Details</h3>
            </c:otherwise>
            </c:choose>
        
        <ul class="TabStyleA">
            <li class="on"><a href="#none">QA request & result</a></li>
            <li><a href="javascript:popup('${qaVO.productId}');">Contents Information</a></li>
        </ul>
        <table cellpadding="0" cellspacing="0" class="BoardWrite2">
            <colgroup>
                <col width="15%" /><col />
            </colgroup>
            <tbody>
            <tr>
                <th>Title</th>
                <td class="Ltext">
                <c:if test="${qaVO.urgentFlag eq '03'}"><span class="fc_f15202">[Urgent]</span></c:if>${qaVO.contTitle}</td>
            </tr>
            <tr>
                <th>Integration ID</th>
                <td class="Ltext">${qaVO.orderId} </td>
            </tr>
            <tr>
                <th>request date</th>
                <td class="Ltext">${qaVO.reqDateAmPm} </td>
            </tr>
            <tr>
                <th>requestor</th>
                <td class="Ltext">${qaVO.reqUserIdEmail}</td>
            </tr>
            
            <c:choose>
            <c:when test="${searchQaVO.qaReqComplFlag eq 'compl'}">
                <tr>
	                <th>Verification date</th>
	                <td class="Ltext">${qaVO.verifyDateAmPm} </td>
	            </tr>
	            <tr>
	                <th>Verifier</th>
	                <td class="Ltext">${qaVO.verifyUserIdEmail}</td>
	            </tr>
	            
	            <tr>
                    <th>For Sale Date <!-- 배포완료일--></th>
                    <td class="Ltext">${qaVO.publishDate}</td>
                </tr>
                    
            </c:when>
            <c:otherwise>
            </c:otherwise>
            </c:choose>
            
            <tr>
             <th>QA Request Status<!--QA 당시 상태 --></th>
                 <td class="Ltext">${qaVO.state} 
                 </td>
             </tr>
            <!-- 
            <tr>
                <th>Current Status</th>
                <td class="Ltext">${qaVO.prodCurState} 
                </td>
            </tr>
             -->
            <tr>
                <th>Classificaton</th>
                <td class="Ltext">
                    <c:if test="${qaVO.massYn eq 'Y'}"><span class="fc_f15202">[bulk changes]</span></c:if>
                    <br />
                    
                     <form:radiobutton path="reqFlag" value="01" cssClass="input_radio"/> <label for="p_qa2_5">New Registration</label>
                        <form:radiobutton path="reqFlag" value="02" cssClass="input_radio"  /> <label for="p_qa2_6">Add new country</label>
                        <form:radiobutton path="reqFlag" value="03" cssClass="input_radio"  /> <label for="p_qa2_7">Modifying Meta</label>
                        <form:radiobutton path="reqFlag" value="04" cssClass="input_radio"  /> <label for="p_qa2_8">Video Modifying</label>
                        <form:radiobutton path="reqFlag" value="05" cssClass="input_radio"  /> <label for="p_qa2_9">License</label>
                        <form:radiobutton path="reqFlag" value="06" cssClass="input_radio"  /> <label for="p_qa2_10">Modifying Device</label>
                </td>
            </tr>
            <tr>
                <th>Verification Items</th>
                <td class="Ltext">
                    <textarea id="lb_synopsis1" class="textarea1 InputBg1" disabled style="width:99%;height:100px;" title="Synopsis" rows="0" cols="0">${qaVO.reqCont}</textarea>
                </td>
            </tr>
            <tr>
                <th>Video Verification Items</th>
                <td class="Ltext">                  
                    <div class="BoardList bd_none">
                        <table cellpadding="0" cellspacing="0">
                            <colgroup>
                                <col />
                            </colgroup>
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
                            <tbody>
                            <tr class="min_h">
                                <td>request</td>
                                <td><form:checkbox path="arrItem" value="A_TRAILER" cssClass="input_check AItemClass" onclick="return false;" /></td>
			                    <td><form:checkbox path="arrItem" value="A_H264_SD" cssClass="input_check AItemClass" onclick="return false;"/></td>
			                    <td><form:checkbox path="arrItem" value="A_H264_HD" cssClass="input_check AItemClass" onclick="return false;"/></td>
			                    <td><form:checkbox path="arrItem" value="A_H264_FHD" cssClass="input_check AItemClass" onclick="return false;"/></td>
			                    <td><form:checkbox path="arrItem" value="A_HEVC_ST_SD" cssClass="input_check AItemClass" onclick="return false;" /></td>
			                    <td><form:checkbox path="arrItem" value="A_HEVC_ST_HD" cssClass="input_check AItemClass" onclick="return false;"/></td>
			                    <td><form:checkbox path="arrItem" value="A_HEVC_ST_FHD" cssClass="input_check AItemClass" onclick="return false;"/></td>
			                    <td><form:checkbox path="arrItem" value="A_HEVC_DL_SD" cssClass="input_check AItemClass" onclick="return false;"/></td>
			                    <td><form:checkbox path="arrItem" value="A_HEVC_DL_HD" cssClass="input_check AItemClass" onclick="return false;"/></td>
			                    <td><form:checkbox path="arrItem" value="A_HEVC_DL_FHD" cssClass="input_check AItemClass" onclick="return false;"/></td>
			                    <td><form:checkbox path="arrItem" value="A_HOME_SYNC" cssClass="input_check AItemClass" onclick="return false;"/></td>
			                    <td><form:checkbox path="arrItem" value="A_TV_SD" cssClass="input_check AItemClass" onclick="return false;"/></td>
			                    <td><form:checkbox path="arrItem" value="A_TV_HD" cssClass="input_check AItemClass" onclick="return false;"/></td>
			                    <td><form:checkbox path="arrItem" value="A_TV_FHD" cssClass="input_check AItemClass" onclick="return false;"/></td>
			                    <td><form:checkbox path="arrItem" value="A_PC_SD" cssClass="input_check AItemClass" onclick="return false;"/></td>
			                    <td><form:checkbox path="arrItem" value="A_PC_HD" cssClass="input_check AItemClass" onclick="return false;"/></td>
			                    <td><form:checkbox path="arrItem" value="A_PC_FHD" cssClass="input_check AItemClass" onclick="return false;"/></td>
                            </tr>
                            <tr class="min_h">
                                <td>verification</td>
                                <td>
                                    <input type="checkbox" name="arrCItem" value="C_TRAILER" class="input_check CItemClass"  
                                    <c:set var="sameYn" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'A_TRAILER'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                    </c:forEach> 
                                    <c:if test="${sameYn eq 'N'}"> disabled </c:if>    
                                    
                                    <c:set var="sameYnCheck" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'C_TRAILER'}"> <c:set var="sameYnCheck" value="Y"/> </c:if>
                                    </c:forEach>     
                                    <c:if test="${sameYnCheck eq 'Y'}"> checked </c:if>
                                    />
                                </td>
                                
                                <td>
                                    <input type="checkbox" name="arrCItem" value="C_H264_SD" class="input_check CItemClass"  
                                    <c:set var="sameYn" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'A_H264_SD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                    </c:forEach> 
                                    <c:if test="${sameYn eq 'N'}"> disabled </c:if>  
                                    
                                    <c:set var="sameYnCheck" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'C_H264_SD'}"> <c:set var="sameYnCheck" value="Y"/> </c:if>
                                    </c:forEach>     
                                    <c:if test="${sameYnCheck eq 'Y'}"> checked </c:if>  
                                    />
                                </td>
                                <td>
                                    <input type="checkbox" name="arrCItem" value="C_H264_HD" class="input_check CItemClass"  
                                    <c:set var="sameYn" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'A_H264_HD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                    </c:forEach> 
                                    <c:if test="${sameYn eq 'N'}"> disabled </c:if>    
                                    
                                    <c:set var="sameYnCheck" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'C_H264_HD'}"> <c:set var="sameYnCheck" value="Y"/> </c:if>
                                    </c:forEach>     
                                    <c:if test="${sameYnCheck eq 'Y'}"> checked </c:if>
                                    />
                                </td>
                                <td>
	                                <input type="checkbox" name="arrCItem" value="C_H264_FHD" class="input_check CItemClass"  
	                                <c:set var="sameYn" value="N"/>
	                                <c:forEach var="item" items="${qaVO.arrItem}">
	                                    <c:if test="${item eq 'A_H264_FHD'}"> <c:set var="sameYn" value="Y"/> </c:if>
	                                </c:forEach> 
	                                <c:if test="${sameYn eq 'N'}"> disabled </c:if>   
	                                
	                                <c:set var="sameYnCheck" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'C_H264_FHD'}"> <c:set var="sameYnCheck" value="Y"/> </c:if>
                                    </c:forEach>     
                                    <c:if test="${sameYnCheck eq 'Y'}"> checked </c:if> 
	                                />
                                </td>
                                <td>
	                                <input type="checkbox" name="arrCItem" value="C_HEVC_ST_SD" class="input_check CItemClass"  
	                                <c:set var="sameYn" value="N"/>
	                                <c:forEach var="item" items="${qaVO.arrItem}">
	                                    <c:if test="${item eq 'A_HEVC_ST_SD'}"> <c:set var="sameYn" value="Y"/> </c:if>
	                                </c:forEach> 
	                                <c:if test="${sameYn eq 'N'}"> disabled </c:if>
	                                
	                                <c:set var="sameYnCheck" value="N"/>
	                                <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'C_HEVC_ST_SD'}"> <c:set var="sameYnCheck" value="Y"/> </c:if>
                                    </c:forEach>     
                                    <c:if test="${sameYnCheck eq 'Y'}"> checked </c:if>
	                                />
                                </td>
                                
                                <td>
                                    <input type="checkbox" name="arrCItem" value="C_HEVC_ST_HD" class="input_check CItemClass"  
                                    <c:set var="sameYn" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'A_HEVC_ST_HD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                    </c:forEach> 
                                    <c:if test="${sameYn eq 'N'}"> disabled </c:if>   
                                    
                                    <c:set var="sameYnCheck" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'C_HEVC_ST_HD'}"> <c:set var="sameYnCheck" value="Y"/> </c:if>
                                    </c:forEach>     
                                    <c:if test="${sameYnCheck eq 'Y'}"> checked </c:if>  
                                    />
                                </td>
                                <td>
                                    <input type="checkbox" name="arrCItem" value="C_HEVC_ST_FHD" class="input_check CItemClass"  
                                    <c:set var="sameYn" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'A_HEVC_ST_FHD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                    </c:forEach> 
                                    <c:if test="${sameYn eq 'N'}"> disabled </c:if>   
                                    
                                    <c:set var="sameYnCheck" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'C_HEVC_ST_FHD'}"> <c:set var="sameYnCheck" value="Y"/> </c:if>
                                    </c:forEach>     
                                    <c:if test="${sameYnCheck eq 'Y'}"> checked </c:if> 
                                    />
                                </td>
                                
                                <td>
                                    <input type="checkbox" name="arrCItem" value="C_HEVC_DL_SD" class="input_check CItemClass"  
                                    <c:set var="sameYn" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'A_HEVC_DL_SD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                    </c:forEach> 
                                    <c:if test="${sameYn eq 'N'}"> disabled </c:if>    
                                    
                                    <c:set var="sameYnCheck" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'C_HEVC_DL_SD'}"> <c:set var="sameYnCheck" value="Y"/> </c:if>
                                    </c:forEach>     
                                    <c:if test="${sameYnCheck eq 'Y'}"> checked </c:if> 
                                    />
                                </td>
                                <td>
                                    <input type="checkbox" name="arrCItem" value="C_HEVC_DL_HD" class="input_check CItemClass"  
                                    <c:set var="sameYn" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'A_HEVC_DL_HD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                    </c:forEach> 
                                    <c:if test="${sameYn eq 'N'}"> disabled </c:if>
                                    
                                    <c:set var="sameYnCheck" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'C_HEVC_DL_HD'}"> <c:set var="sameYnCheck" value="Y"/> </c:if>
                                    </c:forEach>     
                                    <c:if test="${sameYnCheck eq 'Y'}"> checked </c:if>    
                                    />
                                </td>
                                <td>
                                    <input type="checkbox" name="arrCItem" value="C_HEVC_DL_FHD" class="input_check CItemClass"  
                                    <c:set var="sameYn" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'A_HEVC_DL_FHD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                    </c:forEach> 
                                    <c:if test="${sameYn eq 'N'}"> disabled </c:if>  
                                    
                                    <c:set var="sameYnCheck" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'C_HEVC_DL_FHD'}"> <c:set var="sameYnCheck" value="Y"/> </c:if>
                                    </c:forEach>     
                                    <c:if test="${sameYnCheck eq 'Y'}"> checked </c:if>
                                    />
                                </td>
                                <td>
                                    <input type="checkbox" name="arrCItem" value="C_HOME_SYNC" class="input_check CItemClass"  
                                    <c:set var="sameYn" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'A_HOME_SYNC'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                    </c:forEach> 
                                    <c:if test="${sameYn eq 'N'}"> disabled </c:if>   
                                    
                                    <c:set var="sameYnCheck" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'C_HOME_SYNC'}"> <c:set var="sameYnCheck" value="Y"/> </c:if>
                                    </c:forEach>     
                                    <c:if test="${sameYnCheck eq 'Y'}"> checked </c:if> 
                                    />
                                </td>
                                <td>
                                    <input type="checkbox" name="arrCItem" value="C_TV_SD" class="input_check CItemClass"  
                                    <c:set var="sameYn" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'A_TV_SD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                    </c:forEach> 
                                    <c:if test="${sameYn eq 'N'}"> disabled </c:if>  
                                    
                                    <c:set var="sameYnCheck" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'C_TV_SD'}"> <c:set var="sameYnCheck" value="Y"/> </c:if>
                                    </c:forEach>     
                                    <c:if test="${sameYnCheck eq 'Y'}"> checked </c:if>  
                                    />
                                </td>
                                <td>
                                    <input type="checkbox" name="arrCItem" value="C_TV_HD" class="input_check CItemClass"  
                                    <c:set var="sameYn" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'A_TV_HD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                    </c:forEach> 
                                    <c:if test="${sameYn eq 'N'}"> disabled </c:if>    
                                    
                                    <c:set var="sameYnCheck" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'C_TV_HD'}"> <c:set var="sameYnCheck" value="Y"/> </c:if>
                                    </c:forEach>     
                                    <c:if test="${sameYnCheck eq 'Y'}"> checked </c:if>
                                    />
                                </td>
                                <td>
                                    <input type="checkbox" name="arrCItem" value="C_TV_FHD" class="input_check CItemClass"  
                                    <c:set var="sameYn" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'A_TV_FHD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                    </c:forEach> 
                                    <c:if test="${sameYn eq 'N'}"> disabled </c:if>  
                                    
                                    <c:set var="sameYnCheck" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'C_TV_FHD'}"> <c:set var="sameYnCheck" value="Y"/> </c:if>
                                    </c:forEach>     
                                    <c:if test="${sameYnCheck eq 'Y'}"> checked </c:if>  
                                    />
                                </td>
                                <td>
                                    <input type="checkbox" name="arrCItem" value="C_PC_SD" class="input_check CItemClass"  
                                    <c:set var="sameYn" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'A_PC_SD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                    </c:forEach> 
                                    <c:if test="${sameYn eq 'N'}"> disabled </c:if>   
                                    
                                    <c:set var="sameYnCheck" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'C_PC_SD'}"> <c:set var="sameYnCheck" value="Y"/> </c:if>
                                    </c:forEach>     
                                    <c:if test="${sameYnCheck eq 'Y'}"> checked </c:if> 
                                    />
                                </td>
                                <td>
                                    <input type="checkbox" name="arrCItem" value="C_PC_HD" class="input_check CItemClass"  
                                    <c:set var="sameYn" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'A_PC_HD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                    </c:forEach> 
                                    <c:if test="${sameYn eq 'N'}"> disabled </c:if>  
                                    
                                    <c:set var="sameYnCheck" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'C_PC_HD'}"> <c:set var="sameYnCheck" value="Y"/> </c:if>
                                    </c:forEach>     
                                    <c:if test="${sameYnCheck eq 'Y'}"> checked </c:if>  
                                    />
                                </td>
                                <td>
                                    <input type="checkbox" name="arrCItem" value="C_PC_FHD" class="input_check CItemClass"  
                                    <c:set var="sameYn" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'A_PC_FHD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                    </c:forEach> 
                                    <c:if test="${sameYn eq 'N'}"> disabled </c:if>    
                                    
                                    <c:set var="sameYnCheck" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq 'C_PC_FHD'}"> <c:set var="sameYnCheck" value="Y"/> </c:if>
                                    </c:forEach>     
                                    <c:if test="${sameYnCheck eq 'Y'}"> checked </c:if>
                                    />
                                </td>
                            </tr>
                            <tr class="min_h">
                                <td rowspan="3">Result</td>
                                <td colspan="17" class="Ltext">
                                 
                                <form:radiobutton path="qaResult" value="PASS" cssClass="input_radio qaResultClass" />
                                
                                <label for="qa_detail7"><span class="fc_0083ca">pass</span></label>                                     
                                </td>
                            </tr>
                            <tr class="min_h">
                                <td colspan="17" class="Ltext">
                                <form:radiobutton path="qaResult" value="PASS_REVIS" cssClass="input_radio qaResultClass" />
                                
                                <label for="qa_detail8"><span class="fc_0083ca">pass (Revised)</span></label>                                       
                                </td>
                            </tr>
                            <tr class="min_h">
                                
                                <c:forEach var="htmlItem" items="${arrHtmlRItem}">
								   <td>
								    <c:set var="subHtmlItem" value="${fn:substring(htmlItem,2,fn:length(htmlItem))}"/>
                                    <input type="checkbox" name="arrRItem" value="${htmlItem}" class="input_check RItemClass"  
                                    <c:set var="sameYn" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:set var="AItemStr" value="A_${subHtmlItem}" />
                                        <c:if test="${item eq AItemStr}"> <c:set var="sameYn" value="Y"/> </c:if>
                                    </c:forEach> 
                                    <c:if test="${sameYn eq 'N'}"> disabled </c:if>   
                                    
                                    <c:set var="sameYnCheck" value="N"/>
                                    <c:forEach var="item" items="${qaVO.arrItem}">
                                        <c:if test="${item eq htmlItem}"> <c:set var="sameYnCheck" value="Y"/> </c:if>
                                    </c:forEach>     
                                    <c:if test="${sameYnCheck eq 'Y'}"> checked </c:if> 
                                    /> <c:choose><c:when test="${sameYn eq 'N' || searchQaVO.qaReqComplFlag eq 'compl'}"><span class="db_type03"></c:when><c:otherwise><span class="db_type02"></c:otherwise></c:choose>V-Fail</span>
                                  </td>
								</c:forEach>
                               
                                <!--  
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
                                 -->
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </td>
            </tr>
            <tr>
                <th>Metadata Verification Items</th>
                <td class="Ltext">
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
                            </tr>
                            <tr>
                                <th scope="col" class="first">&nbsp;</th>
                                <th scope="col">&nbsp;</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr class="min_h">
                                <td>
                                    <form:checkbox path="arrRMeta" value="M_ALL" cssClass="input_check RItemClass MALLClass" />
                                    <span class="db_type02">M-Fail</span>
                                </td>
                                <td><form:checkbox path="arrRMeta" value="M_Title" cssClass="input_check RItemClass MClass" /></td>
                                <td><form:checkbox path="arrRMeta" value="M_Poster" cssClass="input_check RItemClass MClass"  /></td>
                                <td><form:checkbox path="arrRMeta" value="M_Genre" cssClass="input_check RItemClass MClass"  /></td>
                                <td><form:checkbox path="arrRMeta" value="M_Directors" cssClass="input_check RItemClass MClass"  /></td>
                                <td><form:checkbox path="arrRMeta" value="M_Actors" cssClass="input_check RItemClass MClass"  /></td>
                                <td><form:checkbox path="arrRMeta" value="M_Synopsis" cssClass="input_check RItemClass MClass"  /></td>
                                <td><form:checkbox path="arrRMeta" value="M_AudioLang" cssClass="input_check RItemClass MClass"  /></td>
                                <td><form:checkbox path="arrRMeta" value="M_SubTitles" cssClass="input_check RItemClass MClass"  /></td>
                                <td><form:checkbox path="arrRMeta" value="M_RunTime" cssClass="input_check RItemClass MClass"  /></td>
                                <td><form:checkbox path="arrRMeta" value="M_ReleaseYD" cssClass="input_check RItemClass MClass"  /></td>
                                <td><form:checkbox path="arrRMeta" value="M_Rating" cssClass="input_check RItemClass MClass" /></td>
                                <td>
                                    <form:checkbox path="arrRMeta" value="M_Price" cssClass="input_check RItemClass PriceClass" />
                                    <span class="db_type02">Fail</span>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </td>
            </tr>
            <tr>
                <th>detail content</th>
                <td class="Ltext">
                    <textarea name="description" id="description" cols="0" rows="0" title="상세 내용 입력" class="textarea1" byteCheck="true" maxlength="500" style="min-width:98%;min-height:120px;max-width:98%;max-height:120px">${qaVO.description}</textarea>
                </td>
            </tr>
            <tr>
                <th>Rework <!-- Back 변경--></th>
                <td class="Ltext">
                    <input id="dontChange" name="rdoBackTrackType" class="input_radio backTrackClass" type="radio" <c:if test="${qaVO.backTrackType == null || qaVO.backTrackType == ''}"> checked </c:if> />
                    <label for="dontChange">Do not change</label>
                    <input id="changeBackTrack" name="rdoBackTrackType" class="input_radio backTrackClass" type="radio" <c:if test="${qaVO.backTrackType != null && qaVO.backTrackType != ''}"> checked </c:if> />
                    <label for="changeBackTrack">change (Back-track)</label>
                     &nbsp;&nbsp;
                    <!-- span class="db_type01" -->
                        <form:select path="backTrackType" style="width:150px;" title="Back 변경 선택" onChange="selectChangeBackTrack();" class="backTrackSelectClass">
                            <form:option value="" label="--Please Select"/>
                            <form:options items="${qaVO.cmbBackTrackType}" />
                        </form:select>
                    <!--  /span -->
                    
                </td>
            </tr>
            </tbody>
        </table>

        <h3 class="PgTitle1 MgTop20">Result History</h3>
        <table cellpadding="0" cellspacing="0" class="BoardWrite2 ">
            <colgroup>
                <col width="15%" /><col /><col /><col /><col />
            </colgroup>
            <tbody>
            
            <c:forEach var="item" items="${qaHistoryList}" varStatus="status">
                <tr>
                <th class="Ltext">${status.index + 1}</th>
                <td class="Ltext">Request : ${item.reqUserIdEmail} ${item.reqDateAmPm}</td>
                <td class="Ltext">Verification : ${item.verifyUserIdEmail} ${item.verifyDateAmPm}</td>
                <td class="Ltext"><span class="fc_f15202">${item.qaResult}</span></td>
                <td class="Ltext">
                    
                    <!-- 2013-12-19 수정 -->
                    <a class="btnTypeJ btnopen" id="btnOpen_${item.qaHisSeq}" style="cursor:pointer;cursor:hand;"><span onclick="showDetail('${item.qaHisSeq}');">Open Content</span></a><!-- 상세 내용 열기 버튼 -->
                    <!-- //2013-12-19 수정 -->
                    <a class="btnTypeJ btnclose" id="btnClose_${item.qaHisSeq}" style="cursor:pointer;cursor:hand;"><span onclick="closeDetail('${item.qaHisSeq}');">Close Content</span></a> <!-- 상세 내용 닫기 버튼 -->
                    
                </td>
                </tr>
                
                <!-- 상세 내용 열기 버튼 클릭시 보여짐 --><!-- 2013-12-19 추가 -->
	            <tr class="ViewHistory" id="ViewHistory_${item.qaHisSeq}">
	                <td colspan="5">
	                
	                
	                 <table cellpadding="0" cellspacing="0" class="BoardWrite2 inBW2">
			            <colgroup>
			                <col width="15%" /><col />
			            </colgroup>
			            <tbody>
			            <tr>
			                <th>Title</th>
			                <td class="Ltext">
			                <c:if test="${item.urgentFlag eq '03'}"><span class="fc_f15202">[Urgent]</span></c:if>${item.contTitle}</td>
			            </tr>
			            <tr>
			                <th>Integration ID</th>
			                <td class="Ltext">${item.orderId} </td>
			            </tr>
			            <tr>
			                <th>request date</th>
			                <td class="Ltext">${item.reqDateAmPm} </td>
			            </tr>
			            <tr>
			                <th>requestor</th>
			                <td class="Ltext">${item.reqUserIdEmail}</td>
			            </tr>
			            <!-- 
			            <tr>
			                <th>현재 상태</th>
			                <td class="Ltext">Ready for Change (Updated) 
			                <a class="btnTypeJ" href="#none"><span>상태 변경</span></a>
			                </td>
			            </tr>
			             -->
			            <tr>
			                <th>Classification</th>
			                <td class="Ltext">
			                    <c:if test="${item.massYn eq 'Y'}"><span class="fc_f15202">[bulk changes]</span></c:if>
			                    <br />
			                        <input type=radio name="reqFlag_${item.qaHisSeq}" value="01" class="input_radio hisReqFlagClass" <c:if test="${item.reqFlag eq '01'}"> checked</c:if> /> <label for="p_qa2_5">New Registration</label>
			                        <input type=radio name="reqFlag_${item.qaHisSeq}" value="02" class="input_radio hisReqFlagClass" <c:if test="${item.reqFlag eq '02'}"> checked</c:if> /> <label for="p_qa2_6">Add new country</label>
			                        <input type=radio name="reqFlag_${item.qaHisSeq}" value="03" class="input_radio hisReqFlagClass" <c:if test="${item.reqFlag eq '03'}"> checked</c:if> /> <label for="p_qa2_7">Modifying Meta</label>
			                        <input type=radio name="reqFlag_${item.qaHisSeq}" value="04" class="input_radio hisReqFlagClass" <c:if test="${item.reqFlag eq '04'}"> checked</c:if> /> <label for="p_qa2_8">Video Modifying</label>
			                        <input type=radio name="reqFlag_${item.qaHisSeq}" value="05" class="input_radio hisReqFlagClass" <c:if test="${item.reqFlag eq '05'}"> checked</c:if> /> <label for="p_qa2_9">License</label>
			                        <input type=radio name="reqFlag_${item.qaHisSeq}" value="06" class="input_radio hisReqFlagClass" <c:if test="${item.reqFlag eq '06'}"> checked</c:if> /> <label for="p_qa2_10">Modifying Device</label>
			                </td>
			            </tr>
			            <tr>
			                <th>Verification Items</th>
			                <td class="Ltext">
			                    <textarea id="lb_synopsis1" class="textarea1 InputBg1" disabled style="width:99%;height:100px;" title="Synopsis" rows="0" cols="0">${item.reqCont}</textarea>
			                </td>
			            </tr>
			            <tr>
			                <th>Video Verification Items</th>
			                <td class="Ltext">                  
			                    <div class="BoardList bd_none">
			                        <table cellpadding="0" cellspacing="0">
			                            <colgroup>
			                                <col />
			                            </colgroup>
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
			                            <tbody>
			                            <tr class="min_h">
			                                <td>request</td>
			                                
			                                
			                                <td>
			                                    <input type="checkbox" name="arrItem_${item.qaHisSeq}" value="A_TRAILER" class="input_check hisAItemClass"  
			                                    <c:set var="sameYn" value="N"/>
			                                    <c:forEach var="itemsub" items="${item.arrItem}">
			                                        <c:if test="${itemsub eq 'A_TRAILER'}"> <c:set var="sameYn" value="Y"/> </c:if>
			                                    </c:forEach> 
			                                    <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
			                                    />
			                                </td>
			                                <td>
                                                <input type="checkbox" name="arrItem_${item.qaHisSeq}" value="A_H264_SD" class="input_check hisAItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'A_H264_SD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrItem_${item.qaHisSeq}" value="A_H264_HD" class="input_check hisAItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'A_H264_HD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrItem_${item.qaHisSeq}" value="A_H264_FHD" class="input_check hisAItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'A_H264_FHD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrItem_${item.qaHisSeq}" value="A_HEVC_ST_SD" class="input_check hisAItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'A_HEVC_ST_SD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
                                            <td>
                                                <input type="checkbox" name="arrItem_${item.qaHisSeq}" value="A_HEVC_ST_HD" class="input_check hisAItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'A_HEVC_ST_HD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrItem_${item.qaHisSeq}" value="A_HEVC_ST_FHD" class="input_check hisAItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'A_HEVC_ST_FHD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrItem_${item.qaHisSeq}" value="A_HEVC_DL_SD" class="input_check hisAItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'A_HEVC_DL_SD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
                                            <td>
                                                <input type="checkbox" name="arrItem_${item.qaHisSeq}" value="A_HEVC_DL_HD" class="input_check hisAItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'A_HEVC_DL_HD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrItem_${item.qaHisSeq}" value="A_HEVC_DL_FHD" class="input_check hisAItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'A_HEVC_DL_FHD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrItem_${item.qaHisSeq}" value="A_HOME_SYNC" class="input_check hisAItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'A_HOME_SYNC'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrItem_${item.qaHisSeq}" value="A_TV_SD" class="input_check hisAItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'A_TV_SD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrItem_${item.qaHisSeq}" value="A_TV_HD" class="input_check hisAItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'A_TV_HD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrItem_${item.qaHisSeq}" value="A_TV_FHD" class="input_check hisAItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'A_TV_FHD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrItem_${item.qaHisSeq}" value="A_PC_SD" class="input_check hisAItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'A_PC_SD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrItem_${item.qaHisSeq}" value="A_PC_HD" class="input_check hisAItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'A_PC_HD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrItem_${item.qaHisSeq}" value="A_PC_FHD" class="input_check hisAItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'A_PC_FHD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                
			                            </tr>
			                            <tr class="min_h">
			                                <td>verification</td>
			                                <td>
                                                <input type="checkbox" name="arrCItem_${item.qaHisSeq}" value="C_TRAILER" class="input_check hisCItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'C_TRAILER'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                
			                                <td>
                                                <input type="checkbox" name="arrCItem_${item.qaHisSeq}" value="C_H264_SD" class="input_check hisCItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'C_H264_SD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
                                            <td>
                                                <input type="checkbox" name="arrCItem_${item.qaHisSeq}" value="C_H264_HD" class="input_check hisCItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'C_H264_HD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrCItem_${item.qaHisSeq}" value="C_H264_FHD" class="input_check hisCItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'C_H264_FHD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrCItem_${item.qaHisSeq}" value="C_HEVC_ST_SD" class="input_check hisCItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'C_HEVC_ST_SD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrCItem_${item.qaHisSeq}" value="C_HEVC_ST_HD" class="input_check hisCItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'C_HEVC_ST_HD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrCItem_${item.qaHisSeq}" value="C_HEVC_ST_FHD" class="input_check hisCItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'C_HEVC_ST_FHD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrCItem_${item.qaHisSeq}" value="C_HEVC_DL_SD" class="input_check hisCItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'C_HEVC_DL_SD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrCItem_${item.qaHisSeq}" value="C_HEVC_DL_HD" class="input_check hisCItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'C_HEVC_DL_HD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrCItem_${item.qaHisSeq}" value="C_HEVC_DL_FHD" class="input_check hisCItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'C_HEVC_DL_FHD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrCItem_${item.qaHisSeq}" value="C_HOME_SYNC" class="input_check hisCItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'C_HOME_SYNC'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrCItem_${item.qaHisSeq}" value="C_TV_SD" class="input_check hisCItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'C_TV_SD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrCItem_${item.qaHisSeq}" value="C_TV_HD" class="input_check hisCItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'C_TV_HD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrCItem_${item.qaHisSeq}" value="C_TV_FHD" class="input_check hisCItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'C_TV_FHD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrCItem_${item.qaHisSeq}" value="C_PC_SD" class="input_check hisCItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'C_PC_SD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
                                            <td>
                                                <input type="checkbox" name="arrCItem_${item.qaHisSeq}" value="C_PC_HD" class="input_check hisCItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'C_PC_HD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
                                            <td>
                                                <input type="checkbox" name="arrCItem_${item.qaHisSeq}" value="C_PC_FHD" class="input_check hisCItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'C_PC_FHD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                />
                                            </td>
			                                
			                            </tr>
			                            <tr class="min_h">
			                                <td rowspan="3">Result</td>
			                                <td colspan="17" class="Ltext">
			                                 
			                                <input type=radio name="qaResult_${item.qaHisSeq}" value="PASS" class="input_radio hisResultClass" <c:if test="${item.qaResult eq 'PASS'}"> checked</c:if> />
			                                
			                                <label for="qa_detail7"><span class="fc_0083ca">pass</span></label>                                     
			                                </td>
			                            </tr>
			                            <tr class="min_h">
			                                <td colspan="17" class="Ltext">
			                                
			                                <input type=radio name="qaResult_${item.qaHisSeq}" value="PASS_REVIS" class="input_radio hisResultClass" <c:if test="${item.qaResult eq 'PASS_REVIS'}"> checked</c:if> />
			                                			                                
			                                <label for="qa_detail8"><span class="fc_0083ca">pass (Revised)</span></label>                                       
			                                </td>
			                            </tr>
			                            <tr class="min_h">
			                                 
			                                <td>
                                                <input type="checkbox" name="arrRItem_${item.qaHisSeq}" value="R_TRAILER" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'R_TRAILER'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> <span class="db_type02">V-Fail</span>
                                            </td>
                                            
                                            <td>
                                                <input type="checkbox" name="arrRItem_${item.qaHisSeq}" value="R_H264_SD" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'R_H264_SD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> <span class="db_type02">V-Fail</span>
                                            </td>
                                            <td>
                                                <input type="checkbox" name="arrRItem_${item.qaHisSeq}" value="R_H264_HD" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'R_H264_HD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> <span class="db_type02">V-Fail</span>
                                            </td>
                                            <td>
                                                <input type="checkbox" name="arrRItem_${item.qaHisSeq}" value="R_H264_FHD" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'R_H264_FHD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> <span class="db_type02">V-Fail</span>
                                            </td>
                                            <td>
                                                <input type="checkbox" name="arrRItem_${item.qaHisSeq}" value="R_HEVC_ST_SD" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'R_HEVC_ST_SD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> <span class="db_type02">V-Fail</span>
                                            </td>
                                            <td>
                                                <input type="checkbox" name="arrRItem_${item.qaHisSeq}" value="R_HEVC_ST_HD" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'R_HEVC_ST_HD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> <span class="db_type02">V-Fail</span>
                                            </td>
                                            <td>
                                                <input type="checkbox" name="arrRItem_${item.qaHisSeq}" value="R_HEVC_ST_FHD" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'R_HEVC_ST_FHD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> <span class="db_type02">V-Fail</span>
                                            </td>
                                            <td>
                                                <input type="checkbox" name="arrRItem_${item.qaHisSeq}" value="R_HEVC_DL_SD" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'R_HEVC_DL_SD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> <span class="db_type02">V-Fail</span>
                                            </td>
                                            <td>
                                                <input type="checkbox" name="arrRItem_${item.qaHisSeq}" value="R_HEVC_DL_HD" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'R_HEVC_DL_HD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> <span class="db_type02">V-Fail</span>
                                            </td>
                                            <td>
                                                <input type="checkbox" name="arrRItem_${item.qaHisSeq}" value="R_HEVC_DL_FHD" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'R_HEVC_DL_FHD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> <span class="db_type02">V-Fail</span>
                                            </td>
                                            <td>
                                                <input type="checkbox" name="arrRItem_${item.qaHisSeq}" value="R_HOME_SYNC" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'R_HOME_SYNC'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> <span class="db_type02">V-Fail</span>
                                            </td>
                                            <td>
                                                <input type="checkbox" name="arrRItem_${item.qaHisSeq}" value="R_TV_SD" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'R_TV_SD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> <span class="db_type02">V-Fail</span>
                                            </td>
                                            <td>
                                                <input type="checkbox" name="arrRItem_${item.qaHisSeq}" value="R_TV_HD" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'R_TV_HD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> <span class="db_type02">V-Fail</span>
                                            </td>
                                            <td>
                                                <input type="checkbox" name="arrRItem_${item.qaHisSeq}" value="R_TV_FHD" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'R_TV_FHD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> <span class="db_type02">V-Fail</span>
                                            </td>
                                            <td>
                                                <input type="checkbox" name="arrRItem_${item.qaHisSeq}" value="R_PC_SD" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'R_PC_SD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> <span class="db_type02">V-Fail</span>
                                            </td>
                                            <td>
                                                <input type="checkbox" name="arrRItem_${item.qaHisSeq}" value="R_PC_HD" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'R_PC_HD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> <span class="db_type02">V-Fail</span>
                                            </td>
                                            <td>
                                                <input type="checkbox" name="arrRItem_${item.qaHisSeq}" value="R_PC_FHD" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'R_PC_FHD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> <span class="db_type02">V-Fail</span>
                                            </td>
                                            
			                            </tr>
			                            </tbody>
			                        </table>
			                    </div>
			                </td>
			            </tr>
			            <tr>
			                <th>Metadata Verification Items</th>
			                <td class="Ltext">
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
			                            </tr>
			                            <tr>
			                                <th scope="col" class="first">&nbsp;</th>
			                                <th scope="col">&nbsp;</th>
			                            </tr>
			                            </thead>
			                            <tbody>
			                            <tr class="min_h">
			                            
			                                <td>
                                                <input type="checkbox" name="arrRMeta_${item.qaHisSeq}" value="M_ALL" class="input_check hisMItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'M_ALL'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> <span class="db_type02">M-Fail</span>
                                            </td>
                                            <td>
                                                <input type="checkbox" name="arrRMeta_${item.qaHisSeq}" value="M_Title" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'M_Title'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> 
                                            </td>
                                            <td>
                                                <input type="checkbox" name="arrRMeta_${item.qaHisSeq}" value="M_Poster" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'M_Poster'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> 
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrRMeta_${item.qaHisSeq}" value="M_Genre" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'M_Genre'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> 
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrRMeta_${item.qaHisSeq}" value="M_Directors" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'M_Directors'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> 
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrRMeta_${item.qaHisSeq}" value="M_Actors" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'M_Actors'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> 
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrRMeta_${item.qaHisSeq}" value="M_Synopsis" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'M_Synopsis'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> 
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrRMeta_${item.qaHisSeq}" value="M_AudioLang" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'M_AudioLang'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> 
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrRMeta_${item.qaHisSeq}" value="M_SubTitles" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'M_SubTitles'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> 
                                            </td>
                                            <td>
                                                <input type="checkbox" name="arrRMeta_${item.qaHisSeq}" value="M_RunTime" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'M_RunTime'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> 
                                            </td>
                                            
                                            
                                             <td>
                                                <input type="checkbox" name="arrRMeta_${item.qaHisSeq}" value="M_ReleaseYD" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'M_ReleaseYD'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> 
                                            </td>
                                            <td>
                                                <input type="checkbox" name="arrRMeta_${item.qaHisSeq}" value="M_Rating" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'M_Rating'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> 
                                            </td>
			                                <td>
                                                <input type="checkbox" name="arrRMeta_${item.qaHisSeq}" value="M_Price" class="input_check hisRItemClass"  
                                                <c:set var="sameYn" value="N"/>
                                                <c:forEach var="itemsub" items="${item.arrItem}">
                                                    <c:if test="${itemsub eq 'M_Price'}"> <c:set var="sameYn" value="Y"/> </c:if>
                                                </c:forEach> 
                                                <c:if test="${sameYn eq 'Y'}"> checked </c:if>    
                                                /> <span class="db_type02">Fail</span> 
                                            </td>
			                            </tr>
			                            </tbody>
			                        </table>
			                    </div>
			                </td>
			            </tr>
			            <tr>
			                <th>Detail Content</th>
			                <td class="Ltext">
			                    <textarea name="description_${item.qaHisSeq}" disabled cols="0" rows="0" title="상세 내용 입력" class="textarea1" style="min-width:98%;min-height:120px;max-width:98%;max-height:120px">${item.description}</textarea>
			                </td>
			            </tr>
			            <tr>
			                <th>Back 변경</th>
			                <td class="Ltext">
			                
			                    <input id="dontChange_${item.qaHisSeq}" name="rdoBackTrackType_${item.qaHisSeq}" class="input_radio hisBackTrackClass" type="radio" <c:if test="${item.backTrackType == null || item.backTrackType == ''}"> checked </c:if> />
			                    <label for="dontChange">Do not change</label>
			                    <input id="changeBackTrack_${item.qaHisSeq}" name="rdoBackTrackType_${item.qaHisSeq}" class="input_radio hisBackTrackClass" type="radio" <c:if test="${item.backTrackType != null && item.backTrackType != ''}"> checked </c:if> />
			                    <label for="changeBackTrack">change (Back-track)</label> 
			                     &nbsp;&nbsp;
			                     
			                    <!-- span class="db_type01" -->
			                        <select name="backTrackType_${item.qaHisSeq}" style="width:150px;" title="Back 변경 선택" class="hisBackTrackSelectClass">
			                            <option value="" label="--Please Select"/> 
			                            <c:forEach var="thisBackType" items="${qaVO.cmbBackTrackType}">
                                            <option value="${thisBackType.key}" <c:if test="${thisBackType.key == item.backTrackType}"> selected </c:if> 
                                            />${thisBackType.value}
                                        </c:forEach> 
			                        </select>
			                    <!--  /span -->
			                    
			                </td>
			            </tr>
			            </tbody>
			        </table>
                    
                    </td>
                    </tr>
                
            </c:forEach> 
          
             
            </tbody>
        </table>
        <div class="BtnArea">
            <div class="Left"><a class="btnTypeC" style="cursor:pointer;cursor:hand;"><span onclick="gotoList();"><strong class="sty1">List</strong></span></a></div>
            
            <c:choose>
            <c:when test="${searchQaVO.qaReqComplFlag eq 'compl'}">
                
            </c:when>
            <c:otherwise>
                <div class="Right">
	                <a class="btnTypeA" style="cursor:pointer;cursor:hand;"><span onclick="checkSubmit();"><strong class="sty1">Save</strong></span></a>            
	            </div>
            </c:otherwise>
            </c:choose>
            
        </div>
   
   

</form:form>
    
    
