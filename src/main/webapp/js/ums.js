// 메인 배너 
$(function () {
	$('.main-landing > .el').hover(function() {
		var $t = $(this);
		// $t.css({width:'40%'});
		$t.addClass('on');
		// $('.main-landing > .el').not($t).delay(250).css({width:'15%'});
		$('.main-landing > .el').not($t).removeClass('on');
	}, function() {
		// $('.main-landing > .el').css({width:'20%'});
		// $('.main-landing > .el').removeClass('on');
	});
});

$(function () {
	$('.send-date .radio input, .tform .schbar .radio input').change(function() {
		var thistab = $(this).attr('tabon');
		var tabon = "#"+thistab;
		$('.tabpn').addClass('off');
		$(tabon).removeClass('off');

	});
});
/**
$(function() {	
	$('.pop-img').popupWindow({ 
	height:260,  
	width:400, 
	top:50, 
	left:50 
	});
});
$(function() {	
	$('.pop-add').popupWindow({ 
	height:478, 
	width:550, 
	top:50, 
	left:50 
	});
});
$(function() {	
	$('.pop-excel').popupWindow({ 
	height:500, 
	width:400, 
	top:50, 
	left:50 
	});
});
$(function() {	
	$('.pop-qinfo').popupWindow({ 
	height:650, 
	width:710, 
	top:50, 
	left:50 
	});
});
$(function() {	
	$('.pop-fax').popupWindow({ 
	height:490, 
	width:720, 
	top:50, 
	left:50 
	});
});
$(function() {	
	$('.pop-notice').popupWindow({ 
	height:400, 
	width:400, 
	top:50, 
	left:50 
	});
});
$(function() {	
	$('.pop-modify').popupWindow({ 
	height:400, 
	width:400, 
	top:50, 
	left:50 
	});
});
$(function() {	
	$('.pop-adb').popupWindow({ 
	height:450, 
	width:400, 
	top:50, 
	left:50 
	});
});
*/
$(function () {
	$('.textinput-selector').change(function() {
		var val = $(this).val();
		if (val == 'textinput') {
			$('.textinput').fadeIn();
			$(this).fadeOut();
		}
	});
});

$(function() {
	$( ".datepicker" ).datepicker({ dateFormat: "yy/mm/dd" });

});
