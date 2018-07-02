var defaultText = "보내실 내용을 입력해 주세요. 80Byte (한글 1글자 2Byte ) 이상의 문자는 LMS로 처리 및 과금 됩니다.";
$(".mbox .mob .mbody textarea")[0].value = defaultText;
$(".mbox .mob .mbody textarea").addClass("blank");

$(".mbox .mob .mbody textarea").bind("keyup", function(){
	if(this.value == ""){
		$(this).addClass("blank");
	}
});

$(".mbox .mob .mbody textarea").bind("keydown", function(){
	if($(this).hasClass("blank")){
		$(this).removeClass("blank");
	}
});

$(".mbox .mob .mbody textarea").bind("click", function(){
	if($(this).hasClass("blank")){
		this.value = "";
	}
});

$(".mbox .mob .mbody textarea").bind("blur", function(){
	if($(this).hasClass("blank")){
		this.value = defaultText;
	}
});