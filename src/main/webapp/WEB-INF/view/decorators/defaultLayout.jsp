<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../common/common.jsp"%>
<!DOCTYPE html>
<html xmlns="https://www.w3.org/1999/xhtml" xml:lang="en" lang="ko-KR">
<head>
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type"/>
    
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Cache-Control" content="no-cache" />
    <meta http-equiv="Expires" content="-1" /> 
 	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
 	<meta name="viewport" content="width=device-width, initial-scale=1"/>
 	
 	<link rel="stylesheet" href="/css/bootstrap.min.css"/>
	<link rel="stylesheet" href="/fa/css/font-awesome.min.css"/>
	<link rel="stylesheet" href="/css/style.css" media="all" />
	<link rel="stylesheet" href="/css/custom.css" media="all" />
	<link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">
	
    <script type="text/javascript">SHOP_TIMEOFFSET = "-28800000";</script>
    <script type="text/javascript">_TIMEOFFSET_ = "-28800000";</script>
    <script type="text/javascript">_DAYLIGHT_TIMEOFFSET_ = "-28800000";</script>
    <script type="text/javascript">IMAGE_DOMAIN = "http://";</script>
    <script type="text/javascript" src="/js/jquery.min.js"></script>
	<script type="text/javascript" src="/js/jquery-ui.min.js"></script>
	
    <!--[if lt IE 9]>
		<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script> 
    <![endif]-->
    
	<script type="text/javascript" src="/js/jquery.form.js"></script>  
	<script type="text/javascript" src="/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/js/date.format.js"></script>
    <script type="text/javascript" src="/js/calendar.js"></script>
    <script type="text/javascript" src="/js/json2.js"></script>
    <script type="text/javascript" src="/js/validator.js"></script>
    <script type="text/javascript" src="/js/autoNumeric_1.6.2.js"></script>
    <script type="text/javascript" src="/js/shadowbox.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    <script type="text/javascript" src="/js/common2.js"></script>  
    <script type="text/javascript" src="/js/bootstrap.min.js"></script> 
     
  <script type="text/javascript" src="/js/ums.js"></script>
  
    <script type="text/javascript" src="/js/addressbook.js"></script>
    <script type="text/javascript" src="/js/period.js"></script>
    <script type="text/javascript" src="/js/message.js"></script>
    <script type="text/javascript" src="/js/util.js"></script>

<c:if test="${! empty msg}">
	<script>
		alert("${msg}");
	</script>
</c:if> 
    

  <title>umsserviceweb</title>

<decorator:head />

</head>

 

<body onload="<decorator:getProperty property="body.onload"/>" class="bg_wrap">
<div class="container-fluid site-wrap">
<!-- Header -->
 <page:applyDecorator name="_header" />

<!-- //Header -->

<!-- Container -->
<div class="site-content row">
    <!-- Left menu -->
      <page:applyDecorator name="_left" />
        
        
        <!-- START:MAIN Area -->
                <decorator:body />
        <!-- END:MAIN Area -->
    
<!-- // Container -->
</div>
<!-- Footer -->

<!-- START:BOTTOM Area -->
      <page:applyDecorator name="_footer" />
<!-- END:BOTTOM Area -->


</div>
</body>
</html>