<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>


<head>

<DChain:codeList var="cmbContType" group="MH_CM002" subGroup="" /> <!-- content type -->
<DChain:codeList var="cmbRegion" group="MH_SC009" subGroup="" />  <!-- region code -->
<DChain:codeList var="cmbTerritory" group="TERRITORY" subGroup="" />
<DChain:codeList var="cmbProdState" group="SP_PT003" subGroup="" /> <!-- product state -->

<DChain:codeList var="cmbRegion01" group="REGION01" subGroup="" />  <!-- region code 01 -->
<DChain:codeList var="cmbRegion02" group="REGION02" subGroup="" />  <!-- region code 02 -->
<DChain:codeList var="cmbRegion03" group="REGION03" subGroup="" />  <!-- region code 03 -->
<DChain:codeList var="cmbRegion04" group="REGION04" subGroup="" />  <!-- region code 04 -->
<DChain:codeList var="cmbRegion05" group="REGION05" subGroup="" />  <!-- region code 05 -->

<script>

$(document).ready(function () {
	   
	   $("#trSaleCountry").hide();
       
	   $(".btnResolMinus").hide();
       $(".resolList").hide();
       
       setDatepicker("srchEstStartDate", "srchEstEndDate", "estStartDate", "estEndDate");
       setDatepicker("srchVodStartDate", "srchVodEndDate", "vodStartDate", "vodEndDate");
       setDatepicker("srchReqStartDate", "srchReqEndDate", "reqStartDate", "reqEndDate");
       setDatepicker("srchComplStartDate", "srchComplEndDate", "complStartDate", "complEndDate");
       
       $('table tr:even').addClass("bg1");
        
       
       $("#arrProdStateAll").click(function() {
           if($(this).is(":checked")) {
               $(".checkProdState").attr("checked", true);
           } else {
               $(".checkProdState").attr("checked", false);
           }
       });
       //territory check 
       $(".checkProdState").click(function() {
           if($(this).is(":checked")) {
        	   checkOne('arrProdState');
           } else {
               $("#arrProdStateAll").attr("checked", false);
           }
       });
       
       $("#arrRegionAll").click(function() {
           if($(this).is(":checked")) {
               $(".checkRegion").attr("checked", true);
               dispSaleCountry();
           } else {
               $(".checkRegion").attr("checked", false);
               hideCountry();
           }
       });
       $(".checkRegion").click(function() {
           if($(this).is(":checked")) {
               checkOne('arrRegion');
               dispSaleCountry();
           } else {
               $("#arrRegionAll").attr("checked", false);
               
               if(!checkAnyRegion()) {
                   hideCountry();
               } else {
                   dispSaleCountry();      
               }
           }
       });
       
       
       $("#arrVendorNmAll").click(function() {
           if($(this).is(":checked")) {
               $(".checkVendorNm").attr("checked", true);
           } else {
               $(".checkVendorNm").attr("checked", false);
           }
       });
       //territory check 
       $(".checkVendorNm").click(function() {
           if($(this).is(":checked")) {
        	   checkOne('arrVendorNm');
           } else {
               $("#arrVendorNmAll").attr("checked", false);
           }
       });
       
       
       $("#arrQaIdAll").click(function() {
           if($(this).is(":checked")) {
               $(".checkQaId").attr("checked", true);
           } else {
               $(".checkQaId").attr("checked", false);
           }
       });
       $(".checkQaId").click(function() {
           if($(this).is(":checked")) {
               checkOne('arrQaId');
           } else {
               $("#arrQaIdAll").attr("checked", false);
           }
       });
       
       
       
       checkOne('arrTerritory');
       checkOne('arrProdState');
       checkOne('arrRegion');
       checkOne('arrVendorNm');
      
       
       /*
       <c:if test="${searchQaVO.arrVendorNm == null}">
       $("#checkVendorAll").attr("checked", true);
       $(".checkVendor").attr("checked", true);
       </c:if>
       */
       /*
      <c:if test="${searchQaVO.arrTerritory == null}">
      $("#checkTerritoryAll").attr("checked", true);
      $(".checkTerritory").attr("checked", true);
      </c:if>
      */
      
      /*
      <c:if test="${searchQaVO.arrProdState == null}">
      $("#checkProdStateAll").attr("checked", true);
      $(".checkProdState").attr("checked", true);
      </c:if>
      
      <c:if test="${searchQaVO.arrRegion == null}">
      $("#checkRegionAll").attr("checked", true);
      $(".checkRegion").attr("checked", true);
      </c:if>
      */
      
      <c:if test="${searchQaVO.srchContType == null}">
      $("#srchContTypeAll").attr("checked", true);
      </c:if>
      
      
      <c:if test="${searchQaVO.qaReqComplFlag eq 'compl'}">
          $("#arrQaResultAll").click(function() {
              if($(this).is(":checked")) {
                  $(".checkQaResult").attr("checked", true);
              } else {
                  $(".checkQaResult").attr("checked", false);
              }
          });
          //territory check 
          $(".checkQaResult").click(function() {
              if($(this).is(":checked")) {
            	  checkOne('arrQaResult');
              } else {
                  $("#arrQaResultAll").attr("checked", false);
              }
          });
          
          <c:if test="${searchQaVO.arrQaResult == null or fn:length(searchQaVO.arrQaResult)==0}">
          //$("#checkQaResultAll").attr("checked", true);
          //$(".checkQaResult").attr("checked", true);
          </c:if>
          
          checkOne('arrQaResult');
          
      </c:if>
      
      dispSaleCountry();
      territoryEventInit();
      
});


function territoryEventInit() {
    $("#arrTerritoryAll").click(function() {
        if($(this).is(":checked")) {
            $(".checkTerritory").attr("checked", true);
        } else {
            $(".checkTerritory").attr("checked", false);
        }
    });
    //territory check 
    $(".checkTerritory").click(function() {
        if($(this).is(":checked")) {
            checkOne('arrTerritory');
        } else {
            $("#arrTerritoryAll").attr("checked", false);
        }
    });
    
    checkOne('arrTerritory');
}


function checkAnyRegion() {
    var bChecked = false;
    $("input[name=arrRegion]").each(function() {
        if(this.checked == true) bChecked = true;  
    });
    
    return bChecked;
}

function checkAnyTerritory() {
    var bChecked = false;
    $("input[name=arrTerritory]").each(function() {
        if(this.checked == true) bChecked = true;  
    });
    
    return bChecked;
}

function hideCountry() {
    $(".checkTerritory").attr("checked", false);
    $(".checkTerritory").attr("disabled", true);
    $("#arrTerritoryAll").attr("checked", false);
    $("#trSaleCountry").hide();
}

function checkOne(id){
    var allcheck = true;
    $("input[name=" + id + "]").each(function() {
        if(!$(this).is(":checked")) {
            allcheck = false;
        }
        if(allcheck) {
             $("input[id=" + id + "All]").prop("checked", true);
        }else {
             $("input[id=" + id + "All]").prop("checked", false);
        }
    });
}


function goWriteForm() {
    var f = document.forms["listForm"];
    f.action = "/emailmng/emailGroupWriteForm";
    f.method = "post";
    f.submit();
}

function goDetail(qaId) {

    var f = document.forms["listForm"];

    //f.reset();
    
    <c:choose>
    <c:when test="${searchQaVO.qaReqComplFlag eq 'compl'}">
         var strAction = "/qa/qaComplDetailView";
    </c:when>
    <c:otherwise>
         var strAction = "/qa/qaDetailView";
    </c:otherwise>
    </c:choose>

    f.action = strAction + "?qaId="+qaId;
    f.method = "post";
    f.submit();
}

function linkPage(pageNo) {
	
	if(checkAnyRegion()) {
        if(!checkAnyTerritory()) {
            alert("You must check Territory. If you checked Region.");
            return;
        }
    }
	
    $("#currentPageNo").val(pageNo);
    
    $("input[name=arrQaId]").prop("checked", false); //페이지 이동시 상관없는 항목. 
    
    <c:choose>
    <c:when test="${searchQaVO.qaReqComplFlag eq 'compl'}">
         var strAction = "/qa/qaCompleteList";
    </c:when>
    <c:otherwise>
         var strAction = "/qa/qaRequestList";
    </c:otherwise>
    </c:choose>
    
    $("#listForm").attr({
        action : strAction,
        method : "post"
    }).submit();
}


function checkAll(){ 
    $("input[name=arrQaId]").prop("checked", $("input[name=allChk]").is(":checked"));
}


function checkAny(){
    var bChecked = false;
    $("input[name=arrQaId]").each(function() {
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

var poptarget;

function checkResultPopUp() {
    if(!checkAny()) return false;
    
    var strVar = ""; 
    $("input[name=arrQaId]").each(function() {
        if(this.checked == true) {
            strVar += this.value + ',';  
        }
    });
    
    var title = "qaResultsWritePopUp";
    var status = "width=900, height=1024, scrollbars=1, toolbar=no, menubar=no resizable=yes";
    poptarget = window.open("/qa/qaResultsWritePopUp?arrQaId=" + strVar, title, status);
    
    /*
    $("#flagDel").val("");
    var f = document.forms["listForm"];
    f.action = "/qa/qaResultsWritePopUp";
    f.method = "post";
    f.target = poptarget; 
    f.submit();
    */
    
    //sbIframe('/qa/qaResultsWritePopUp?arrQaId=' + strVar, '900', '900');
    
}

function resultChange() {
    if(!checkAny()) return false;
    
    var strVar = ""; 
    $("input[name=arrQaId]").each(function() {
        if(this.checked == true) {
            strVar += this.value + ',';  
        }
    });
    
    var title = "qaResultsChangeopUp";
    var status = "width=900, height=640, scrollbars=1, toolbar=no, menubar=no resizable=yes";
    poptarget = window.open("/qa/qaResultsChangePopUp?qaReqComplFlag=compl&arrQaId=" + strVar, title, status);
}

function plusClick(qaId) {
    
    $("#btnResolMinus_" + qaId).show();
    $("#btnResolPlus_" + qaId).hide();
    
    $(".resolList_" + qaId).show();
}

function minusClick(qaId) {
    
    $("#btnResolMinus_" + qaId).hide();
    $("#btnResolPlus_" + qaId).show();
    
    $(".resolList_" + qaId).hide();

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
    
    <c:choose>
    <c:when test="${searchQaVO.qaReqComplFlag eq 'compl'}">
         var strAction = "/qa/qaCompleteListExcel";
    </c:when>
    <c:otherwise>
         var strAction = "/qa/qaRequestListExcel";
    </c:otherwise>
    </c:choose>
    
    
    var f = document.forms["listForm"];
    f.action = strAction;
    f.method = "post";
    f.submit();
}

function downloadView() {
    $("#csvName").val("");
    
    $("#downloadPopUp").toggle();
}


function uploadView() {
    $("#uploadFileText").val("");
    $("#uploadFile").val("");
    //$("#uploadPopUp").toggle(); 
    
    Common.centerLayer($("#uploadPopUp"),"LayerPop");
}



function uploadResultView() {
    $("#uploadResultPopup").hide();
    location.reload();
}

function excelUpload() {
    if(!$("#uploadFile").val()) {
        alert("please file select.");
        return;
    }
    $("#uploadAjaxForm").ajaxSubmit({
        type : "POST",
        dataType: "json",
        beforeSubmit: function() {
        },
        success: function(json) {    
            if(json.result) {
                $.each(json.validFailList, function(index, data) {
                    renderUploadFailList(data);
                });
                $.each(json.dbFailList, function(index, data) {
                    renderUploadFailList(data);
                });
                var successCount = json.validSuccessList.length - json.dbFailList.length;
                $("#successCount").text("(" + successCount + ")");
                var failCount = json.validFailList.length + json.dbFailList.length;
                $("#failCount").text("(" + failCount + ")");
                if(failCount < 8) {
                    var height = 40 * failCount + 40;
                    $("#validFailListDiv").attr("style", "height:" + height);
                }
                uploadView();               
                
                //$("#uploadResultPopup").show();
                Common.centerLayer($("#uploadResultPopup"),"LayerPop");
                
                
            } else {
                alert(json.errMsg); 
            }
        },
        error: function(msg) {
            alert(JSON.stringify(msg));
        }
    });
}

function renderUploadFailList(data) {
    var validFailList;
    var territory = "";
    var title = "";
    var returnMsg = "";
    
    if(data.territory != null) { territory = data.territory; }
    if(data.contTitle != null) { title = data.contTitle; }
    if(data.returnMsg != null) { returnMsg = data.returnMsg;}
    
    validFailList = "<tr>" +
                  "<td scope='row'>" + territory + "</td>" +
                  "<td scope='row'>" + data.orderId + "</td>" +
                  "<td scope='row'>" + data.cid + "</td>" +
                  "<td scope='row'>" + data.productId + "</td>" +
                  "<td scope='row'>" + title + "</td>" +
                  "<td scope='row' class='Ltext'>" + returnMsg + "</td>" +
                  "</tr>";
    $("#validFailList").append(validFailList);
}


function dispSaleCountry() {
    
    var bChecked = false;
    $("input[name=arrRegion]").each(function() {
        if(this.checked == true) bChecked = true;  
    });
    
    if(!bChecked) return;
    
    $("#trSaleCountry").show();
    $("#trSaleCountry td:eq(0)").empty();
    
    var tArray = new Array();
    var tLiArr = new Array();
    var tCnt = 0;
    
    $("input[name=arrRegion]").each(function() {
        if(this.checked == true) {
            var regionCd = this.value;
            if(regionCd == '01') {
                <c:forEach var="item" items="${cmbRegion01}">
                   tArray[tCnt] = "${item.codeNm}".substring(0,2).toLowerCase();
                   tLiArr[tCnt] = "<li><input type='checkbox' name='arrTerritory' class='input_check checkTerritory' id='checkTerritory${item.code}' value='${item.code}'";
                   <c:set var="sameYnCheck" value="N"/>
                   <c:forEach var="subitem" items="${searchQaVO.arrTerritory}">
                       <c:if test="${subitem eq item.code}"> <c:set var="sameYnCheck" value="Y"/> </c:if>
                   </c:forEach>     
                   <c:if test="${sameYnCheck eq 'Y'}"> tLiArr[tCnt] += " checked "; </c:if> 
                   tLiArr[tCnt] += "/><label for='checkTerritory${item.code}'> ${item.codeNm}</label></li>";
                   tCnt++; 
                </c:forEach>
            }
            if(regionCd == '02') {
                <c:forEach var="item" items="${cmbRegion02}">
                   tArray[tCnt] = "${item.codeNm}".substring(0,2).toLowerCase();
                   tLiArr[tCnt] = "<li><input type='checkbox' name='arrTerritory' class='input_check checkTerritory' id='checkTerritory${item.code}' value='${item.code}'";
                   <c:set var="sameYnCheck" value="N"/>
                   <c:forEach var="subitem" items="${searchQaVO.arrTerritory}">
                       <c:if test="${subitem eq item.code}"> <c:set var="sameYnCheck" value="Y"/> </c:if>
                   </c:forEach>     
                   <c:if test="${sameYnCheck eq 'Y'}"> tLiArr[tCnt] += " checked "; </c:if> 
                   tLiArr[tCnt] += "/><label for='checkTerritory${item.code}'> ${item.codeNm}</label></li>";
                   tCnt++; 
                </c:forEach>
            }
            if(regionCd == '03') {
                <c:forEach var="item" items="${cmbRegion03}">
                    tArray[tCnt] = "${item.codeNm}".substring(0,2).toLowerCase();
                    tLiArr[tCnt] = "<li><input type='checkbox' name='arrTerritory' class='input_check checkTerritory' id='checkTerritory${item.code}' value='${item.code}'";
                       <c:set var="sameYnCheck" value="N"/>
                       <c:forEach var="subitem" items="${searchQaVO.arrTerritory}">
                           <c:if test="${subitem eq item.code}"> <c:set var="sameYnCheck" value="Y"/> </c:if>
                       </c:forEach>     
                       <c:if test="${sameYnCheck eq 'Y'}"> tLiArr[tCnt] += " checked "; </c:if> 
                       tLiArr[tCnt] += "/><label for='checkTerritory${item.code}'> ${item.codeNm}</label></li>";
                    tCnt++; 
                </c:forEach>
            }
            if(regionCd == '04') {
                <c:forEach var="item" items="${cmbRegion04}">
                    tArray[tCnt] = "${item.codeNm}".substring(0,2).toLowerCase();   
                    tLiArr[tCnt] = "<li><input type='checkbox' name='arrTerritory' class='input_check checkTerritory' id='checkTerritory${item.code}' value='${item.code}'";
                       <c:set var="sameYnCheck" value="N"/>
                       <c:forEach var="subitem" items="${searchQaVO.arrTerritory}">
                           <c:if test="${subitem eq item.code}"> <c:set var="sameYnCheck" value="Y"/> </c:if>
                       </c:forEach>     
                       <c:if test="${sameYnCheck eq 'Y'}"> tLiArr[tCnt] += " checked "; </c:if> 
                       tLiArr[tCnt] += "/><label for='checkTerritory${item.code}'> ${item.codeNm}</label></li>";
                    tCnt++; 
                </c:forEach>
            }
            if(regionCd == '05') {
                <c:forEach var="item" items="${cmbRegion05}">
                    //tArray[tCnt] = "${item.code}";
                    tArray[tCnt] = "${item.codeNm}".substring(0,2).toLowerCase();
                    tLiArr[tCnt] = "<li><input type='checkbox' name='arrTerritory' class='input_check checkTerritory' id='checkTerritory${item.code}' value='${item.code}'";
                       <c:set var="sameYnCheck" value="N"/>
                       <c:forEach var="subitem" items="${searchQaVO.arrTerritory}">
                           <c:if test="${subitem eq item.code}"> <c:set var="sameYnCheck" value="Y"/> </c:if>
                       </c:forEach>     
                       <c:if test="${sameYnCheck eq 'Y'}"> tLiArr[tCnt] += " checked "; </c:if> 
                       tLiArr[tCnt] += "/><label for='checkTerritory${item.code}'> ${item.codeNm}</label></li>";
                    tCnt++; 
                </c:forEach>
            }
        }  
    });
    
    var tmp = "";
    var tmp2 = "";
    for(var i=0; i < tArray.length ; i++) {
        for(var j=i+1; j < tArray.length ; j++) {
            if(tArray[j] < tArray[i]) {
                tmp = tArray[i];
                tArray[i] = tArray[j];
                tArray[j] = tmp;
                
                tmp2 = tLiArr[i];
                tLiArr[i] = tLiArr[j];
                tLiArr[j] = tmp2;
            }
        }
    }
    
    var strCountry = "<ul class='SalesChannelList InputSec'>" +
    "<li class='block_type1'><input class='input_check' id='arrTerritoryAll' type='checkbox' /><label for='checkTerritoryAll'> All</label></li>";
    
    for(var i=0; i < tArray.length; i++) {
        strCountry += tLiArr[i];
    }
    strCountry += "</ul>";
    
    $("#trSaleCountry td:eq(0)").append(strCountry);
    
    territoryEventInit();
}

function afterUploadExcel() {
	$('#uploadResultPopup').hide();
	location.href = "/qa/qaCompleteList";
}

</script>

</head>
    
    <!-- Navi
        <div class="Navi">
            <a href="#none">Home </a> &gt; <a href="#none"> QA</a> &gt; <strong>QA 대기</strong>
        </div>
        
        <h2 class="HeadTitle">
        <c:choose>
        <c:when test="${searchQaVO.qaReqComplFlag eq 'compl'}">
            QA 완료    
        </c:when>
        <c:otherwise>
            QA 대기
        </c:otherwise>
        </c:choose>
        
        <span>(${searchQaVO.totalRecordCount})</span></h2>
         -->
        
        <form:form  method="post" modelAttribute="searchQaVO" id="listForm">
        <form:hidden path="currentPageNo"/>
        <form:hidden path="flagDel"/>
        <form:hidden path="qaReqComplFlag"/>
        <input type=hidden name="uiSearchFlag" value="Y">
        
        
        <div class="BoardWrite">
            <table cellpadding="0" cellspacing="0">
                <colgroup>
                    <col width="120" /><col />
                </colgroup>
                <tbody>
                <tr>
                    <th>Sales Region</th>
                    <td class="Ltext">
                        <ul class="SalesChannelList InputSec">
                            
                            <li class="block_type1"><input class="input_check" id="arrRegionAll" type="checkbox" /><label for="checkRegionAll"> All</label></li>
                        <c:forEach var="item" items="${cmbRegion}">
                            <li><form:checkbox path="arrRegion" class="input_check checkRegion" id="checkRegion${item.code}" value="${item.code}" /><label for="checkRegion${item.code}"> ${item.codeNm}</label></li>
                        </c:forEach>
                            
                        </ul>
                    </td>
                </tr>
                <tr id="trSaleCountry">
                    <th>Sales Territory</th>
                    <td class="Ltext">
                        <ul class="SalesChannelList InputSec">
                        <li class="block_type1"><input class="input_check" id="arrTerritoryAll" type="checkbox" /><label for="checkTerritoryAll"> All</label></li>
                        <c:forEach var="item" items="${cmbTerritory}">
                            <li><form:checkbox path="arrTerritory" class="input_check checkTerritory" id="checkTerritory${item.code}" value="${item.code}" /><label for="checkTerritory${item.code}"> ${item.codeNm}</label></li>
                        </c:forEach>
                        </ul>
                    </td>
                </tr>

                <tr>
                    <th><label for="qa_cp">CP Name</label></th>
                    <td class="Ltext">
                        <form:input path="srchCpNm" cssClass="input_text" maxlength="50" style="width:220px;"/>
                        <span class="fc_f15202">Ex) CP Name1; CP Name2; CP Name3</span><!-- 2013-12-20 추가 -->
                        </a>
                    </td>
                </tr>
                 
                <tr>
                    <th>Encode Vendor Name</th>
                    <td class="Ltext">
                        <ul class="SalesChannelList InputSec">
                        <li class="block_type1"><input class="input_check" id="arrVendorNmAll" type="checkbox" /><label for="checkVendorAll"> All</label></li>
                        <c:forEach var="item" items="${encodingCodeList}">
                            <li><form:checkbox path="arrVendorNm" class="input_check checkVendorNm" id="checkVendor${item.code}" value="${item.code}" /><label for="checkVendor${item.code}"> ${item.codeNm}</label></li>
                        </c:forEach>
                        </ul>
                    </td>
                </tr>

                <tr>
                    <th>Status</th>
                    <td class="Ltext">
                        <ul class="SalesChannelList InputSec">
                        <li class="block_type1"><input class="input_check" id="arrProdStateAll" type="checkbox" /><label for="checkProdStateAll"> All</label></li>
                        <c:forEach var="item" items="${cmbProdState}">
                            <li><form:checkbox path="arrProdState" class="input_check checkProdState" id="checkProdState${item.code}" value="${item.code}" /><label for="checkProdState${item.code}"> ${item.codeNm}</label></li>
                        </c:forEach>
                        </ul>
                    </td>
                </tr> 
                <tr>
                    <th>Content Type</th>
                    <td class="Ltext">
                        <form:radiobutton path="srchContType" id="srchContTypeAll" value="" cssClass="input_radio" /> <label for="srchContType">All</label>
                        <c:forEach var="item" items="${cmbContType}">
                            <form:radiobutton path="srchContType" value="${item.code}" cssClass="input_radio" /> <label for="srchContType">${item.codeNm}</label>
                        </c:forEach>
                        <!--                         
                        <input id="qa_type2" class="input_radio" type="radio" name="" />
                        <label for="qa_type2">Movie</label>                         
                        <input id="qa_type3" class="input_radio" type="radio" name="" />
                        <label for="qa_type3">TV</label>
                         -->    
                    </td>
                </tr> 
                
        
                <c:if test="${searchQaVO.qaReqComplFlag eq 'compl'}">
                     <tr>
                        <th>Result</th>
                        <td class="Ltext">
                            <ul class="SalesChannelList InputSec">
                                <li class="block_type1"><input class="input_check" id="arrQaResultAll" type="checkbox" checked="checked" /><label for="qa_resultall"> All</label></li>
                                
                                <li><form:checkbox path="arrQaResult" class="input_check checkQaResult" value="PASS" /><label for="srchQaResult"> Pass</label></li>
                                <li><form:checkbox path="arrQaResult" class="input_check checkQaResult" value="PASS_REVIS" /><label for="srchQaResult"> Pass(Revised)</label></li>
                                
                                <!-- 
                                <li><form:checkbox path="arrQaResult" class="input_check checkQaResult" value="PASS_PART" /><label for="srchQaResult"> Pass(Part)</label></li>
                                 -->
                                 
                                <li><form:checkbox path="arrQaResult" class="input_check checkQaResult" value="FAIL_V" /><label for="srchQaResult"> V-Fail</label></li>
                                <li><form:checkbox path="arrQaResult" class="input_check checkQaResult" value="FAIL_M" /><label for="srchQaResult"> M-Fail</label></li>
                                
                                <!-- 
                                <li><form:checkbox path="arrQaResult" class="input_check checkQaResult" value="FAIL_P" /><label for="srchQaResult"> PF</label></li>
                                <li><form:checkbox path="arrQaResult" class="input_check checkQaResult" value="FAIL_O" /><label for="srchQaResult"> OF</label></li>
                                 -->
                            </ul>
                        </td>
                    </tr> 
                </c:if>
        
                
                <tr>
                    <th>EST start date</th>
                    <td class="Ltext">
                        
                        <form:input path="srchEstStartDate" cssClass="input_text" style="width:60px;" title="시작일" maxlength="10"/>
                        <a href="javascript:;" class="estStartDate"><img src="/images/common/ico_calendar.gif" class="ico1" alt="달력" /></a> &nbsp;&#126;
                        
                        <form:input path="srchEstEndDate" cssClass="input_text" style="width:60px;" title="종료일" maxlength="10"/>
                        <a href="javascript:;" class="estEndDate"><img src="/images/common/ico_calendar.gif" class="ico1" alt="달력" /></a>
                    </td>
                </tr> 
                <tr>
                    <th>VOD start date</th>
                    <td class="Ltext">
                        
                        <form:input path="srchVodStartDate" cssClass="input_text" style="width:60px;" title="시작일" maxlength="10"/>
                        <a href="javascript:;" class="vodStartDate"><img src="/images/common/ico_calendar.gif" class="ico1" alt="달력" /></a> &nbsp;&#126;
                        
                        <form:input path="srchVodEndDate" cssClass="input_text" style="width:60px;" title="종료일" maxlength="10"/>
                        <a href="javascript:;" class="vodEndDate"><img src="/images/common/ico_calendar.gif" class="ico1" alt="달력" /></a>
                    </td>
                </tr>   
                <tr>
                    <th>QA request date</th>
                    <td class="Ltext">
                        <form:input path="srchReqStartDate" cssClass="input_text" style="width:60px;" title="시작일" maxlength="10"/>
                        <a href="javascript:;" class="reqStartDate"><img src="/images/common/ico_calendar.gif" class="ico1" alt="달력" /></a> &nbsp;&#126;
                        <form:input path="srchReqEndDate" cssClass="input_text" style="width:60px;" title="종료일" maxlength="10"/>
                        <a href="javascript:;" class="reqEndDate"><img src="/images/common/ico_calendar.gif" class="ico1" alt="달력" /></a>
                    </td>
                </tr> 
                
             <c:if test="${searchQaVO.qaReqComplFlag eq 'compl'}">   
                <tr>
                    <th>QA Verification date</th>
                    <td class="Ltext">
                        <form:input path="srchComplStartDate" cssClass="input_text" style="width:60px;" title="시작일" maxlength="10"/>
                        <a href="javascript:;" class="complStartDate"><img src="/images/common/ico_calendar.gif" class="ico1" alt="달력" /></a> &nbsp;&#126;
                        <form:input path="srchComplEndDate" cssClass="input_text" style="width:60px;" title="종료일" maxlength="10"/>
                        <a href="javascript:;" class="complEndDate"><img src="/images/common/ico_calendar.gif" class="ico1" alt="달력" /></a>
                    </td>
                </tr>       
             </c:if>    
                
                <tr>
                    <th><label for="qa_all_id">Integration ID</label></th>
                    <td class="Ltext">
                        <form:input path="srchMasterId" cssClass="input_text" maxlength="50" style="width:220px;"/>
                    </td>
                </tr>
                <tr>
                    <th><label for="qa_content_id">Content ID</label></th>
                    <td class="Ltext">
                        <form:input path="srchCid" cssClass="input_text" maxlength="50" style="width:220px;"/>
                    </td>
                </tr>
                <tr>
                    <th><label for="qa_title">Title</label></th>
                    <td class="Ltext">
                        <form:input path="srchContTitle" cssClass="input_text" maxlength="50" style="width:220px;"/>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="BtnSearch">
                <a class="btnTypeI" style="cursor:pointer;cursor:hand;"><span onclick="linkPage(1);"><strong class="sty1">Search</strong></span></a>
                
                    <c:choose>
                    <c:when test="${searchQaVO.qaReqComplFlag eq 'compl'}">
                           <a class="btnTypeI" style="cursor:pointer;cursor:hand;"><span onclick="uploadView();"><strong>Upload Result</strong></span></a>
                           <a class="btnTypeI" style="cursor:pointer;cursor:hand;"><span onclick="resultChange();"><strong>Result Change</strong></span></a>  <!-- 임시삭제 -->
                    </c:when>
                    <c:otherwise>
                           <a class="btnTypeI" style="cursor:pointer;cursor:hand;"><span onclick="checkResultPopUp();"><strong>Resulting input</strong></span></a>
                    </c:otherwise>
                    </c:choose>
                    
                
                
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
                            <a class="btnTypeK" href="javascript:excelDownload();"><span>Yes</span></a>
                            <a class="btnTypeK" href="javascript:downloadView();"><span>NO</span></a>
                        </div>
                        <a href="javascript:downloadView();"><img src="/images/common/btn_pop_close.gif" class="BtnClose" alt="Close" /></a>
                    </div>
                </div>
                
            </div>
        </div>
        <p class="TotalSch MgTop20">
            Search Result : ( <strong>${searchQaVO.totalRecordCount}</strong> )
        </p>
        <table cellpadding="0" cellspacing="0" class="BoardList">
            <colgroup>
                <col width="3%" /><col width="7%" /><col width="5%" /><col width="9%" /><col width="10%" />
                <col width="6%" /><col width="6%" /><col width="4%" /><col width="10%" /><col width="7%" /><col width="6%" /><col width="8%" />
                <col width="6%" /><col width="13%" />
            </colgroup>
            <thead>
            <tr>
                <th scope="col" colspan="2" class="first">
                
                <c:choose>
                <c:when test="${searchQaVO.qaReqComplFlag eq 'compl'}">
                    <input type=checkbox id="arrQaIdAll" class="input_check"></input> <!-- 임시삭제 --> Result   
                </c:when>
                <c:otherwise>
                    <input type=checkbox id="arrQaIdAll" class="input_check"></input>
                </c:otherwise>
                </c:choose>
                
                </th>
                <th scope="col">number of requests</th>
                <th scope="col">Status</th>
                <th scope="col">Title</th>             
                <th scope="col">Integration ID</th>
                <th scope="col">CID</th>
                <th scope="col">Type</th>
                <th scope="col">CP</th>
                <th scope="col">Encoding House</th>
                <th scope="col">Territory</th>
                <th scope="col">QA resolution</th>
                <th scope="col">
                <c:choose>
                <c:when test="${searchQaVO.qaReqComplFlag eq 'compl'}">
                    verifier
                </c:when>
                <c:otherwise>
                    requestor
                </c:otherwise>
                </c:choose>  
                </th>
                <th scope="col">
                <c:choose>
                <c:when test="${searchQaVO.qaReqComplFlag eq 'compl'}">
                    verification date    
                </c:when>
                <c:otherwise>
                    request date
                </c:otherwise>
                </c:choose>  
                </th>
            </tr>
            </thead>
            <tbody>
            
                    <!-- 리스트 30줄 뿌려짐 -->
           <c:if test="${empty qaList}">
               <tr>
                   <td colspan="15">No data</td>
               </tr>
           </c:if>
          <c:if test="${! empty qaList}">
              <c:forEach var="item" items="${qaList}" varStatus="status">
                <tr>
                <td scope="row">
                
                <c:choose>
                <c:when test="${searchQaVO.qaReqComplFlag eq 'compl'}">
                       <!--  임시삭제 -->
                     <c:if test="${item.qaResult != 'PASS' && item.qaResult != 'PASS_REVISED' && item.qaResult != 'PASS_PART'}">
                     <input class="input_check" type=checkbox name="arrQaId" value="${item.qaId}"></input>
                     </c:if> 
                        <!--  임시삭제 -->
                     ${searchQaVO.totalRecordCount - (searchQaVO.recordCountPerPage * (searchQaVO.currentPageNo - 1)) - status.index}
                </c:when>
                <c:otherwise>
                    <input class="input_check checkQaId" type=checkbox name="arrQaId" value="${item.qaId}"></input>        
                </c:otherwise>
                </c:choose>
                </td>
                <td scope="row" class="Ltext">
                
                <c:choose>
                <c:when test="${searchQaVO.qaReqComplFlag eq 'compl'}">
                    <c:choose>
                        <c:when test="${item.qaResult eq 'PASS'}">
                            <span class="fc_0083ca">Pass</span>
                        </c:when>
                        <c:when test="${item.qaResult eq 'PASS_REVIS'}">
                            <span class="fc_0083ca">Pass(Revised)</span>
                        </c:when>
                        <c:when test="${item.qaResult eq 'PASS_PART'}">
                            <span class="fc_0083ca">Pass(Part)</span>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${item.qaFailMeta > 0}">
                                <span class="fc_f15202">MF</span>
                            </c:if>
                            <c:if test="${item.qaFailVideo > 0}">
                                &nbsp;<span class="fc_f15202">VF</span>
                            </c:if>
                        </c:otherwise>
                    </c:choose>     
                </c:when>
                <c:otherwise>
                    ${searchQaVO.totalRecordCount - (searchQaVO.recordCountPerPage * (searchQaVO.currentPageNo - 1)) - status.index}
                    <c:if test="${item.urgentFlag eq '03'}"><span class="fc_f15202">[Urgent]</span></c:if>    
                </c:otherwise>
                </c:choose>
                 </td>
                <td scope="row">${item.qaCnt}</td>
                <td scope="row">${item.prodCurState}</td>
                <td scope="row" class="Ltext"><a href="javascript:goDetail('${item.qaId}');">${item.contTitle}</a></td>
                <td scope="row">${item.orderId}</td>
                <td scope="row">${item.cid}</td>
                <td scope="row">${item.contTypeNm}</td>
                <td scope="row">${item.cpNm}</td>
                <c:set var="encodingHouse" value="" />
                <c:if test="${ item.encodeVendor eq '01' }"><c:set var="encodingHouse" value="QPM" /></c:if>
                <c:if test="${ item.encodeVendor eq '02' }"><c:set var="encodingHouse" value="Deluxe" /></c:if>
                <c:if test="${ item.encodeVendor eq '03' }"><c:set var="encodingHouse" value="Samsung Encoder" /></c:if>
                <td scope="row">${encodingHouse}</td>
                <td scope="row">${item.territoryNm}</td>
                <td scope="row">
                    <c:choose>
                    <c:when test="${fn:length(item.itemList) > 1}">
                        <span id="btnResolPlus_${item.qaId}"><a style="cursor:pointer;cursor:hand;"><img onclick="plusClick('${item.qaId}');" src="/images/board/ico_blue_plus2.gif" class="ico1" alt="Unfold" /></a></span>
                        <span class="btnResolMinus" id="btnResolMinus_${item.qaId}"><a style="cursor:pointer;cursor:hand;"><img onclick="minusClick('${item.qaId}');" src="/images/board/ico_blue_minus2.gif" class="ico1" alt="Unfold" /></a></span>
                    </c:when>
                    <c:otherwise></c:otherwise>
                    </c:choose>
                    
                    <c:forEach var="qaItem" items="${item.itemList}" varStatus="status_sub">
                        <span
                         <c:if test="${status_sub.index > 0}"> class="resolList resolList_${item.qaId}" </c:if> >${fn:substring(qaItem.item,2,fn:length(qaItem.item))}</span>
                    </c:forEach>
                                 
                </td>
                <td scope="row">
                <c:choose>
                <c:when test="${searchQaVO.qaReqComplFlag eq 'compl'}">
                     ${item.verifyUserIdEmail}   
                </c:when>
                <c:otherwise>
                     ${item.reqUserIdEmail}
                </c:otherwise>
                </c:choose>  
                
                </td>
                <td scope="row">
                <c:choose>
                <c:when test="${searchQaVO.qaReqComplFlag eq 'compl'}">
                    ${item.verifyDateAmPm}
                </c:when>
                <c:otherwise>
                    ${item.reqDateAmPm}
                </c:otherwise>
                </c:choose>  
                </td>
            </tr>
            </c:forEach>     
           </c:if>
           
          
           
      
            </tbody>
        </table>
        
        <!-- Paging -->
        <div class="Paging">
            <ui:pagination paginationInfo="${searchQaVO}" type="image"
               jsFunction="linkPage" />
        </div>
        <!-- Paging -->

</form:form>

<!-- ==================================================================================== -->
    <!-- avail upload layer popup -->
        <div id="uploadPopUp" class="LayerPop" style="width:420px;top:350px;right:310px;display:none;">
        <form id="uploadAjaxForm" name="uploadAjaxForm" method="post" action="/qa/qaResultUploadAjax" enctype="multipart/form-data">
            <div class="Wrap">
                <h1>Upload Avail List</h1>
                <h2 class="PgTitle1">Upload the CSV file.</h2>
                <div class="BoardWrite2 MgTop20">
                    <table cellpadding="0" cellspacing="0">
                        <colgroup>
                            <col width="20%" /><col />
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>File</th>
                            <td class="Ltext ">
                                <!-- change -->
                                <input type="text" class="file_input_textbox" id="uploadFileText" name="uploadFileText" readonly="readonly" style="width:200px;" value=""/>
                                <div class="file_input_div" >
                                    <a class="btnTypeD"><span>Search</span></a>
                                    <input type="file" class="file_input_hidden" id="uploadFile" name="uploadFile" onChange="$('#uploadFileText').val(this.value);"/>
                                </div>
                                <!-- origin
                                <input type="file" id="uploadFile" name="uploadFile" class="input_file" style="width:250px;"/>
                                -->
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="BtnArea">
                    <a class="btnTypeK" style="cursor:pointer;cursor:hand;"><span onclick="excelUpload();">Upload</span></a>
                    <a class="btnTypeK" style="cursor:pointer;cursor:hand;"><span onclick="$('#uploadPopUp').hide();">Cancel</span></a>
                </div>
                <a style="cursor:pointer;cursor:hand;"><img onclick="$('#uploadPopUp').hide();" src="/images/common/btn_pop_close.gif" class="BtnClose" alt="Close" /></a>
            </div>
            </form>
        </div>   
         
    
     
   <!-- upload result layer popup -->
    <div id="uploadResultPopup" class="LayerPop" style="width:740px;top:250px;right:310px;display:none;">
    
        <div class="Wrap">
            <h1>Validation error Report</h1>
            <h2 class="PgTitle1 MgTop20">Success : <em id="successCount">(0)</em>, Fail : <em id="failCount">(0)</em></h2>
            <div class="BoardList TblScroll" id="validFailListDiv" style="height:362px;">
                <table cellpadding="0" cellspacing="0">
                    <colgroup>
                        <col /><col /><col /><col /><col /><col width="30%" />
                    </colgroup>
                    <thead>
                    <tr>
                        <th scope="col" class="first">Country</th>
                        <th scope="col">Master Id</th>
                        <th scope="col">Content Id</th>
                        <th scope="col">Producet Id</th>
                        <th scope="col">Title</th>
                        <th scope="col">Error message</th>
                    </tr>
                    </thead>
                    <tbody id="validFailList"></tbody>
                </table>
            </div>
            <a style="cursor:pointer;cursor:hand;"><img src="/images/common/btn_pop_close.gif" onclick="afterUploadExcel();" class="BtnClose" alt="Close" /></a>
        </div>
    </div>
    <!-- ==================================================================================== -->  
    
    
    
    
