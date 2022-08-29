<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
	<hr />
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">	
<style>
@import url('https://fonts.googleapis.com/css2?family=DynaPuff:wght@500&display=swap'); font-family: 'DynaPuff', cursive;
* { font-family: 'Noto Sans KR', sans-serif; }   
#footer {min-width:1124px; padding-top:30px;padding-bottom:20px;border-top:1px solid #red; background-color:#f5f5f5;}
#footer .inner{width:1124px;margin:0 auto;position:relative;}
#footer .inner .footLogo {position:absolute;left:0;top:0px;}
#footer .inner .footWaMark{position:absolute;right:0;top:0;}
#footer .inner .foot_txt{margin-left:218px;}
#footer .inner .foot_txt p{line-height:20px;}
#footer .inner .foot_txt .blue{color:#black;}
#footer .inner .foot_txt a{ }
#footer .inner .foot_txt span{}
#footer .inner .foot_txt ul{margin-bottom:10px;}
#footer .inner .foot_txt ul li{display:inline;padding-right:10px;}

/* 20210421 추가 */
#footer.new {position:relative;padding:0;background-color:#f5f5f5;}
#footer.new:before{display:block;content:'';position:absolute; top:55px;left:0;right:0;height:1px; background-color:#black;}
#footer.new .inner{padding:10px 0 60px 0;}
#footer.new .foot_link{margin-bottom:20px;}
#footer.new .foot_link li{display:inline; padding-right:30px; line-height:54px;}
#footer.new .foot_link li a{font-color:black; font-size:14px;}
#footer.new .footLogo{top:87px; margin-left:30px;}
#footer.new .foot_txt{margin-left:320px;font-size:15px;color:#black;}
#footer.new .foot_txt p{line-height:130%;}
#footer.new .footWaMark{right:20px;top:87px;}
.footerLogo{width: auto; height: auto; max-width: 230px; max-height: 100px;}
.foot_txt address {font-color:white;}
</style>	
	
<div id="footer" class="new">
	<div class="inner">
		<!-- 푸터 영역 -->
		<ul class="foot_link">
			<li><a href="/m4/privacy.asp">개인정보처리방침   |</a></li>
			<li><a href="/m4/email.asp">이메일무단수집거부   |</a></li>
			<li><a href="/m4/provision.asp">이용약관</a></li>
		</ul>

		<!-- footLogo -->
		<p class="footLogo">
		<a href="http://www.blue-chip.co.kr/"></q><img class="footerLogo" src="/images/bluechip.png" alt="블루칩씨엔에스 로고" /></p></a>
		<!-- //footLogo -->

		<div class="foot_txt">
			<address>
				<p>
					<span><i class="fa fa-map-marker"> 서울 용산구 한강대로 273 용산빌딩 2층 블루칩씨앤에스      |      </i></span>
					<span><i class="fa-solid fa-user-tie">대표이사 : 임홍진</i></span>
				</p>
				<p>
					<span>사업자등록번호 : 107-82-16788   |   </span>
					<span>통신판매번호 : 2016-서울용산-00470</span>
				</p>
				<p>
					<span><i class="fa fa-phone-square"> TEL: 070-7730-3517      |      </i></span>
					<span><i class="fa fa-fax"> Fax : 02-4170-4087      |      </i></span>
					<span><i class="fa fa-envelope"> 담당자 : gj.go@bluechipcns.com</i></span>
				</p>
				<p style="font-size:16px; color:#5F5F5F;">
					<strong><span>All rights reserved by <strong>블루칩씨앤에스</strong> Since 1999</span></strong>
				</p>
			</address>
		</div>

	</div>
</div>
		
		<!-- <div class="site-footer row" style="padding-top:10px; padding-bottom:10px;">
			<div class="col-md-2">
				<div class="flogo">
					<img src="/images/bluechip.png" alt="">
				</div>
			</div>
			

			<div class="col-md-10">
				<div class="site-info">
					<ul style="list-style:none; text-align:center;" style="margin-bottom:0;">
						<li style="display:inline-block;"><a href="#" style="color:black; margin-right:50px;">회사소개  |</a></li>
						<li style="display:inline-block;"><a href="#" style="color:black; margin-right:50px;">이용약관  |</a></li>
						<li style="display:inline-block;"><a href="#" style="color:black; margin-right:50px;">개인정보처리방침  |</a></li>
						<li style="display:inline-block;"><a href="#" style="color:black; margin-right:50px;">스팸정책</a></li>
					</ul>
				</div>
			
				<div class="site-info" >
					<i class="fa fa-map-marker" style="margin-left:10px;">[04321] 서울특별시 용산구 한강대로 273 블루칩씨앤에스 2층</i> 
					<i class="fa fa-phone-square">대표전화 : 02-2057-2678</i> 
					<i class="fa fa-fax">FAX :070-4170-4087</i> 
					<i class="fa fa-fax">담당자 : 070-7730-3517</i> 
					<i class="fa fa-envelope" style="margin-right:10px;">gj.go@bluechipcns.com</i> 
					                                                                                                
					본 사이트의 모든 내용에 대하여 bluechipcns.의 허락없는 무단 복제를 불허합니다.
				</div>
				<em class="site-copy">Copyright (c) 2014 bluechipcns. All Rights Reserved. </em>
			</div>
		</div> -->