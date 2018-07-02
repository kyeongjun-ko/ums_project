<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>

<head>
<script>

function goDetailInfo(qaId) {

    var f = document.forms["createForm"];

    //f.reset();

    f.action = "/qa/qaDetailInfo?qaId="+qaId;
    f.method = "post";
    f.submit();
}

function goDetail(qaId) {

    var f = document.forms["createForm"];

    //f.reset();

    f.action = "/qa/qaDetailView?qaId="+qaId;
    f.method = "post";
    f.submit();
}

function gotoList() {
    var f = document.forms["searchForm"];
    f.action = "/qa/qaRequestList";
    f.method = "get";
    f.submit();
}

function checkSubmit() {
    
    if(!confirm('Are you sure you want to save?')) return false;
    
    var f = document.forms["createForm"];
    f.action = "/qa/qaResultWrite";
    f.method = "post";
    f.submit();
}

function popup(productID) {
    var url = "http://eg-pvt.samsungvideohub.com:8080/portal/product/product.newProductBaseOverview?productBaseId=" + productID;
    window.open(url); 
}

</script>

</head>
    
    
        <!-- Navi -->
        <div class="Navi">
            <a href="#none">Home </a> &gt; <a href="#none">QA</a> &gt; <a href="#none"> QA 대기</a> &gt; <strong>Details </strong>
        </div>
        
        
        <form:form  method="post" modelAttribute="searchQaVO" id="searchForm">
           <!--  search 영역 -->
           <input type=hidden name="srchTerritory" value="${searchQaVO.srchTerritory}">
           <input type=hidden name="currentPageNo" value="${searchQaVO.currentPageNo}">
           <!--  search 영역 end -->
        </form:form>    
      
           
        <form:form  method="post" modelAttribute="qaVO" id="createForm">
            <!--  search 영역 -->
           <input type=hidden name="srchTerritory" value="${searchQaVO.srchTerritory}">
           <input type=hidden name="currentPageNo" value="${searchQaVO.currentPageNo}">
           <!--  search 영역 end -->
           
        <form:hidden path="qaId" />
        <form:hidden path="orderId" />
        <input type=hidden name="arrProductId" value="${qaVO.productId}_${qaVO.territory}">
        <input type=hidden name="arrQaId" value="${qaVO.qaId}">
        
        
        <!-- 상단Title -->
        <h2 class="HeadTitle">QA 대기 Details</h2>
        <ul class="TabStyleA">
            <li><a href="javascript:goDetail('${qaVO.qaId}');">QA 요청서 & 결과</a></li>
            <li class="on"><a href="javascript:goDetailInfo('${qaVO.qaId}');">Contents 정보</a></li>
        </ul>
        <!-- 이미지 상세정보 -->
        <div class="ProductsDetail">
            <table cellpadding="0" cellspacing="0" class="BoardDetail MgBtm10">
                <colgroup>
                    <col width="18%" /><col />
                </colgroup>
                <tbody>
                    <tr>
                        <th>Country</th>
                        <td class="Ltext">${contentVO.country}</td>
                    </tr>
                </tbody>
            </table>
            <div class="ImgDetailBox">
                <!-- 이미지 사이즈 w:118px h:148px -->
                <span class="ImgDetail">
                    <img src="http://eg-pvt.samsungvideohub.com:8080${contentVO.posterUrl}" alt="" />
                </span>
            </div>
            <div class="DetailInfo">
                <div class="tit">
                    <em>${contentVO.title}</em>
                    <a class="btnTypeE" href="javascript:popup(${contentVO.productID});"><span>수정</span></a>                
                </div>
                <p><strong class="txt_info2">${contentVO.subTitle}</strong>
                    ${contentVO.releaseYear} &#47; ${contentVO.genre} &#47; ${contentVO.cpName}
                </p>
                <ul>
                    <li>Directors: ${contentVO.director}</li>
                    <li>Actors: ${contentVO.actors}</li>
                    <li>Age Restriction: ${contentVO.age}</li>
                    <li>Running Time (mins): ${contentVO.runningTime}</li>
                    
                    
                    <c:forEach var="fileSizeList" items="${fileSizeList}">
	                    <c:set var="filYn" value="N" />
	                    <c:if test="${ fileSizeList.fileSize ne '0'}">
	                       <c:set var="filYn" value="Y" />
	                    </c:if>
                    </c:forEach>
                    <c:if test="${ fileYn eq 'Y' }">
                    </c:if>
                    <li>
                        File Size : 
                        <c:set var="android" value="N" />
                        <c:set var="windows" value="N" />
                        <c:set var="tv" value="N" />
                        <c:forEach var="fileSizeList" items="${fileSizeList}">
                            <c:if test="${ fileSizeList.videoTypeCode eq '01' }"><c:set var="videoType" value="HD" /></c:if>
                            <c:if test="${ fileSizeList.videoTypeCode eq '02' }"><c:set var="videoType" value="SD" /></c:if>
                        
                            <c:if test="${ fileSizeList.osTpyeCode eq '01' and fileSizeList.fileSize ne '0'}">
                                <c:choose>
                                    <c:when test="${ android eq 'N' }">[Android]</c:when>
                                    <c:otherwise>, </c:otherwise>
                                </c:choose>
                                ${ videoType } ${ fileSizeList.fileSize } 
                                <c:set var="android" value="Y" />
                            </c:if>
                            <c:if test="${ android eq 'Y' and (windows eq 'Y' or tv eq 'Y')}">|</c:if>
                            
                            <c:if test="${ fileSizeList.osTpyeCode eq '02' and fileSizeList.fileSize ne '0'}">
                                <c:choose>
                                    <c:when test="${ windows eq 'N' }">[Windows]</c:when>
                                    <c:otherwise>, </c:otherwise>
                                </c:choose>
                                ${ videoType } ${ fileSizeList.fileSize }
                                <c:set var="windows" value="Y" />
                            </c:if>
                            <c:if test="${ windows eq 'Y' and tv eq 'Y' }">|</c:if>

                            <c:if test="${ fileSizeList.osTpyeCode eq '03' and fileSizeList.fileSize ne '0'}">
                                <c:choose>
                                    <c:when test="${ tv eq 'N' }">[TV]</c:when>
                                    <c:otherwise>, </c:otherwise>
                                </c:choose>
                                <c:set var="tv" value="Y" />
                                ${ videoType } ${ fileSizeList.fileSize }
                            </c:if>
                        </c:forEach>
                    </li>
                    <li>${contentVO.hdmi}</li>
                </ul>
            </div>
            <dl>
                <dt>Synopsis</dt>
                <dd>${contentVO.synopsis}</dd>
                <dt>Short Synopsis</dt>
                <dd>${contentVO.shortSynopsis}</dd>
            </dl>
        </div>
        <table cellpadding="0" cellspacing="0" class="BoardDetail">
            <colgroup>
                <col width="18%" /><col />
            </colgroup>
            <tbody>
            <tr>
                <th>Price</th>
                <td class="Ltext">
                    <table cellpadding="0" cellspacing="0" class="DataTbl3">
                        <colgroup>
                            <col width="20%" /><col width="30%" /><col width="30%" /><col /><col />
                        </colgroup>
                        <thead>
                        <tr>
                            <th class="first">Quality</th>
                            <th>Transaction Type</th>
                            <th>Playback Limit</th>
                            <th>Price</th>
                            <th>Availability</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="first" rowspan="3">SD</td>
                            <td>Own</td>
                            <td>-</td>
                            <td class="Rtext">$3.99</td>
                            <td>Mobile, TV</td>
                        </tr>
                        <tr>
                            <td rowspan="2">Rent</td>
                            <td>24 hours</td>
                            <td class="Rtext">$1.99</td>
                            <td>Mobile</td>
                        </tr>
                        <tr>
                            <td>48 hours</td>
                            <td class="Rtext">$2.99</td>
                            <td>TV</td>
                        </tr>
                        <tr>
                            <td class="first" rowspan="2">HD</td>
                            <td>Own</td>
                            <td>-</td>
                            <td class="Rtext">$9.99</td>
                            <td>Mobile, TV</td>
                        </tr>
                        <tr>
                            <td>Rent</td>
                            <td>24 hours</td>
                            <td class="Rtext">$5.99</td>
                            <td>Mobile</td>
                        </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            </tbody>
        </table>
        <table cellpadding="0" cellspacing="0" class="BoardDetail MgTop20">
            <colgroup>
                <col width="18%" /><col />
            </colgroup>
            <tbody>
            <tr>
                <th>Status</th>
                <td class="Ltext">${contentVO.status}</td>
            </tr>
            <tr>
                <th>Product ID</th>
                <td class="Ltext">${contentVO.productID}</td>
            </tr>
            <tr>
                <th>Content ID</th>
                <td class="Ltext">${contentVO.contentID}</td>
            </tr>
            <tr>
                <th>Content Type</th>
                <td class="Ltext">${contentVO.contentType}</td>
            </tr>
            <tr>
                <th>Caption(Subtitle) Language</th>
                <td class="Ltext">${contentVO.captionLang}</td>
            </tr>
            <tr>
                <th>Audio Language</th>
                <td class="Ltext">Dubbing in ${contentVO.audioLang}</td>
            </tr>
            <tr>
                <th>Channel</th>
                <td class="Ltext">
                    <table cellpadding="0" cellspacing="0" class="DataTbl3">
                        <colgroup>
                            <col width="25%" /><col />
                        </colgroup>
                        <thead>
                        <tr>
                            <th scope="col" class="first">Channel</th>
                            <th scope="col">Channel Category</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td scope="row" class="first">AT&amp;T</td>
                            <td class="Ltext">
                                <ul>
                                    <li>TV &gt; Drama</li>
                                    <li>TV &gt; Comedy</li>
                                </ul>
                            </td>
                        </tr>
                        <tr>
                            <td scope="row" class="first">Verizon</td>
                            <td class="Ltext">
                                <ul>
                                    <li>TV &gt; Drama</li>
                                    <li>TV &gt; Comedy</li>
                                </ul>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            </tbody>
        </table>
        <div class="BtnArea">
            <div class="Left"><a class="btnTypeC" href="#none"><span onclick="gotoList();"><strong class="sty1">List</strong></span></a></div>
            <div class="Right">
                <a class="btnTypeA" href="#none"><span onclick="checkSubmit();"><strong class="sty1">Save</strong></span></a>            
            </div>
        </div>
    
   
   

</form:form>
    
    
