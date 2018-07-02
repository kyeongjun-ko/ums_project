

function jMsg(title, message, ext) {
	if (ext != undefined) {
		if (typeof(ext) == 'function') {
			$( "#message-form" ).dialog({
				buttons: {
					알림: function() {
						ext();
						$(this).dialog('close');
					}
				}
			});
		} else {
			$( "#message-form" ).dialog({
				buttons: {
					알림: function() {
						location.href = ext;
					}
				}
			});
		}
	} else {
		$( "#message-form" ).dialog({
			buttons: {
				알림: function() {
					$(this).dialog('close');
				}
			}
		});
	}
	$("#message-form").dialog("option", 'title', title);
	$("#message-form").html(message);
	$("#message-form").dialog('open');
}

 
$.postJSON = function(url, data, callback) {
	$.ajax({
		'url': url,
		'type': 'post',
		'dataType' : 'json',
		'processData': false,
		'data': JSON.stringify(data),
		contentType: 'application/json',
		success: callback
	});
};

$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};

/**
 * 메시지 팝업창을 연다.
 *
 * @param messageTitle
 * @param messageCode
 * @param bindArgs '|' 로 구분되는 메시지에 바인드될 argument.
 * @returns {Boolean}
 */
function open_message(messageTitle, messageCode, bindArgs) {
	if (messageTitle == null) {
		alert("ERROR : message title is null.");
		return false;
	}
	if (messageCode == null) {
		alert("ERROR : message code is null.");
		return false;
	}

	var uri = $contextRoot + '/common/common.messagePopup?messageTitle=' + messageTitle + '&messageCode=' + messageCode;
	if (bindArgs !== null) {
		uri = uri + "&bindArgs=" + bindArgs;
	}

	Shadowbox.open({
		content: uri,
		player: "iframe",
		width: 302,
		height: 185
	});

	return false;
}

/**
 * 메시지 팝업을 보여준다. 창을 닫을 때 입력받은 callback 함수를 수행한다.
 *
 * @param messageTitle
 * @param messageCode
 * @param callback
 * @returns {Boolean}
 */
function open_messageWithCallback(messageTitle, messageCode, callback) {
	if (messageTitle == null) {
		alert("ERROR : message title is null.");
		return false;
	}
	if (messageCode == null) {
		alert("ERROR : message code is null.");
		return false;
	}

	var uri = $contextRoot + '/common/common.messagePopup?messageTitle=' + messageTitle + '&messageCode=' + messageCode;
	if (callback !== null) {
		uri = uri + '&callback=' + callback;
	}

	Shadowbox.open({
		content: uri,
		player: "iframe",
		width: 302,
		height: 185
	});

	return false;
}

/**
 * 에러 메시지를 보여주기 위한 메소드. 공통팀에서 사용됨.
 *
 * @param messageTitle
 * @param callback
 * @returns {Boolean}
 */
function errorMessageWithCallback(messageTitle, callback) {
	if (messageTitle == null) {
		messageTitle = "ErrorMessage";
	}

	var uri = $contextRoot + '/common/common.errMessagePopup?messageTitle=' + messageTitle + '&callback=' + callback;

	Shadowbox.open({
		content: uri,
		player: "iframe",
		width: 302,
		height: 185
	});

	return false;
}

function open_confirm(messageTitle, messageCode, callback) {
	if (messageTitle == null) {
		alert("ERROR : message title is null.");
		return false;
	}
	if (messageCode == null) {
		alert("ERROR : message code is null.");
		return false;
	}

	var uri = $contextRoot + '/common/common.messagePopup?messageTitle=' + messageTitle + '&messageCode=' + messageCode;
	if (callback !== null) {
		uri = uri + '&callback=' + callback;
	}

	Shadowbox.open({
		content: uri,
		player: "iframe",
		width: 302,
		height: 185
	});

	return false;
}

// 확인/취소 버튼 클릭 후 YES/NO 반환하는 confirm widnow
function open_common_confirm(messageTitle, messageCode, callback) {
	if (messageTitle == null) {
		alert("ERROR : message title is null.");
		return false;
	}
	if (messageCode == null) {
		alert("ERROR : message code is null.");
		return false;
	}

	var uri = $contextRoot + '/common/common.confirmPopup?messageTitle=' + messageTitle + '&messageCode=' + messageCode;
	if (callback !== null) {
		uri = uri + '&callback=' + callback;
	}

	Shadowbox.open({
		content: uri,
		player: "iframe",
		width: 304,
		height: 150
	});

	return false;
}

//확인/취소 버튼 클릭 후 YES/NO 반환하는 confirm widnow
function open_common_confirm_live(messageTitle, messageCode, callback) {
	if (messageTitle == null) {
		alert("ERROR : message title is null.");
		return false;
	}
	if (messageCode == null) {
		alert("ERROR : message code is null.");
		return false;
	}

	var uri = "https://admin.samsungmediahub.net/" + $contextRoot + '/common/common.confirmPopup?messageTitle=' + messageTitle + '&messageCode=' + messageCode;
	if (callback !== null) {
		uri = uri + '&callback=' + callback;
	}

	Shadowbox.open({
		content: uri,
		player: "iframe",
		width: 304,
		height: 150
	});

	return false;
}

//확인/취소 버튼 클릭 후 YES/NO 반환하는 confirm widnow(Warning Title노출안함)
function open_common_confirm_noSubTitle(messageTitle, messageCode, callback) {
	if (messageTitle == null) {
		alert("ERROR : message title is null.");
		return false;
	}
	if (messageCode == null) {
		alert("ERROR : message code is null.");
		return false;
	}

	var uri = $contextRoot + '/common/common.confirmPopup?subTitleYN=false&messageTitle=' + messageTitle + '&messageCode=' + messageCode;
	if (callback !== null) {
		uri = uri + '&callback=' + callback;
	}

	Shadowbox.open({
		content: uri,
		player: "iframe",
		width: 304,
		height: 150
	});

	return false;
}

//확인/취소 버튼 클릭 후 YES/NO 반환하는 confirm widnow(Warning Title노출안함)
function open_common_confirm_noSubTitle_withHtml(messageTitle, htmlContents, callback) {
	if (messageTitle == null) {
		alert("ERROR : message title is null.");
		return false;
	}
	if (htmlContents == null) {
		alert("ERROR : htmlContents is null.");
		return false;
	}

	var uri = $contextRoot + '/common/common.confirmHtmlPopup?subTitleYN=false&messageTitle=' + messageTitle + '&htmlContents=' + htmlContents;
	if (callback !== null) {
		uri = uri + '&callback=' + callback;
	}

	Shadowbox.open({
		content: uri,
		player: "iframe",
		width: 304,
		height: 150
	});

	return false;
}

/**
 * JavaScript 전용 message popup 입니다.
 * @param ti_tle
 * @param con_tent
 * @returns {Boolean}
 */
function open_alert(ti_tle, con_tent, obj_id) {
	$("#alert_popup_title").text(ti_tle);
	$("#alert_popup_content").text(con_tent);
	try {
		resizeAlertPopup($("#alert_popup_content").height());
	}
	catch (e) {
	}

	if (obj_id) {
		$("#alert_obj_id").val(obj_id);
	}
	Shadowbox.open({
		content: $("#alert_popup").html(),
		player: "html",
		width: 252,
		height: 114 //95
	});
	return false;
}

function open_alert_close() {
	//alert( $("#alert_obj_id").val() );
	Shadowbox.close();
	$(this).focus(); // added. 2011.07.06. IE7,8 에서 fucus out 되는것을 수정.
	$("#" + $("#alert_obj_id").val() ).focus();
}

function set_menu_on(obj_id) {
	$("#"+obj_id).addClass("on");
}

function set_menu_reon(obj_id) {
	$("[id^=sub_]:.on" ).removeClass("on");
	$("#"+obj_id).addClass("on");
}

function resize_popup_height(div_id) {
	var height = ($("#" + div_id).height() + 23 + 4) > 100 ? ($("#" + div_id).height() + 23 + 4) : 100;
	parent.$("#sb-wrapper-inner").height(height);
	parent.$("#sb-wrapper").width( $("#" + div_id).width() + 4); // 2011.07.15 팝업내용 드래그시에 움직는 것 조정.
	parent.$("#sb-wrapper").height(height);
}

function on_date_blur(obj) {
    if (obj.value == "") {
    	return true;
    }

    // yyyymmdd → yyyy-mm-dd
    if (obj.value.length == 8) {
    	var y = obj.value.substring(0, 4);
    	var m = obj.value.substring(4, 6);
    	var d = obj.value.substring(6, 8);
    	obj.value = y + "-" + m + "-" + d;
    }

	if ( !check_date(obj) ) {
        // focus if validation failed
		obj.value = "";
        obj.focus();
    }
}

function check_date(obj) {
	var mo, day, yr;
    var entry = obj.value;
    var re = /\b\d{4}[\/-]\d{1,2}[\/-]\d{1,2}\b/;

    if (re.test(entry) ) {
        var delimChar = (entry.indexOf("/") != -1) ? "/" : "-";
        var delim1 = entry.indexOf(delimChar);
        var delim2 = entry.lastIndexOf(delimChar);
        yr = parseInt(entry.substring(0, delim1), 10);
        mo = parseInt(entry.substring(delim1+1, delim2), 10);
        day = parseInt(entry.substring(delim2+1), 10);
        var testDate = new Date(yr, mo-1, day);
        //alert(testDate);

        if (testDate.getDate() == day) {
            if (testDate.getMonth() + 1 == mo) {
                if (testDate.getFullYear() == yr) {
                    return true;
                } else {
                    open_alert("Alert", "There is a problem with the year entry.");
                }
            } else {
                open_alert("Alert", "There is a problem with the month entry.");
            }
        } else {
            open_alert("Alert", "There is a problem with the date entry.");
        }
    } else {
        open_alert("Alert", "Incorrect date format. Enter as yyyyMMdd.");
    }

    return false;
}

function check_xss(obj) {
	var val = $(obj).val();
	var re = /\<|\>|\"|\'|\%|\;|\(|\)|\&|\+|alert+|script+|javascript+|document\.+|\.cookie+|xss\:+|\:expression|style\=+|background\:+/g;
	if (val.match(re) ) {
	    return false;
	} else {
		return true;
	}
}

function showModal(divID) {
    window.onscroll = function () { document.getElementById(divID).style.top = document.body.scrollTop; };
    document.getElementById(divID).style.display = "block";
    document.getElementById(divID).style.top = document.body.scrollTop;
}

function hideModal(divID) {
    document.getElementById(divID).style.display = "none";
}

/*
 * 선택된 행을 히든 처리하고 플래그를 삭제처리한다.
 *
 *            srcFormId
 *            radio Id
 */
var deleteRow = function(srcFormId, radioId){
	  var colCnt = 0;
	  var radioSelector = '#'+ srcFormId + ' :input[id='+radioId+']:checked';

	  if(recordStatus != 'undefined'){
		  // 기존 행은 업데이트 플래그 처리한다.
		  $(radioSelector).parents("tr").attr("deleteYn","1");
		  $(radioSelector).parents("tr").attr("recordStatus","U");
		  $(radioSelector).parents("tr").hide(); // 숨김처리한다.
	  } else {
		  // 신규 추가된 행은 뷰테이블에서 실제 remove 처리한다.
		  $(radioSelector).parents("tr").remove();
	  }
	  // 이 시점에서 renumbering을 처리한다.
	  //sortOrderedNumber();

      return ;
};
// 행삭제에 따른 정렬순서 재처리
var sortOrderedNumber = function(tableId, boolAscDesc){
	return ;
};

/*
 * 선택된 행의 상태값을 hide(02)/exporsure(01) 처리한다.
 *
 *            srcFormId üũ¹ڽº°¡ ǷȔµƠForm id ¹®Z¿­
 *            checkboxId üũ¹ڽºG id ¹®Z¿­
 */
var setHideExposure = function(srcFormId, radioId, boolTF){
	  var radioSelector = '#'+ srcFormId + ' :input[id='+radioId+']:checked';

	  if(true){  // 노출
		  $(radioSelector).parents("tr").attr("#statusCode",'01');
		  $(radioSelector).parents("tr").find("#statusCodeName").text("Exposure");
	  } else { // 숨김
		  $(radioSelector).parents("tr").attr("#statusCode",'02');
		  $(radioSelector).parents("tr").find("#statusCodeName").text("Hide");
	  }

      return ;
};


/************************************************************************/
/**     checkbox, radio 컨트롤 관련 공통 함수                           */
/************************************************************************/
/**
 *  Checkbox를 Enable/Disable 처리하는 공통함수
 *  idOrName : "id"/"name" 중 하나를 지정할 것[반드시 소문자로 기재할 것].
 *  checkboxNameId : checkbox의 id/name 속성명
 *  boolEnable     : true(Enable)/false(Disable)
 *  boolKeepData   : 기존 체크된 데이터를 보존할 것인지 여부 true(보존) / false(체크해제처리)
 *  Usage 1 : enableCheckbox("id", "realChannel", false, true);  // Disable처리 예제(데이터 보존)
 *  Usage 2 : enableCheckbox("id", "realChannel", true, true);  // enable처리 예제(데이터 보존)
 */
function manageCheckedControll(idOrName, checkboxNameId, boolEnable, boolKeepData){

	var selector = "input["+idOrName + "=" + checkboxNameId +"]"; // selector 구성

	if(!boolKeepData)
		$(selector).removeAttr('checked'); // 데이터 보존하지 않음

    if(boolEnable) {
		$(selector).removeAttr('disabled');     // 기존 disabled 삭제
	   	   $(selector).attr('enabled', 'enabled'); // Enable
	} else {
		$(selector).removeAttr('enabled');        // 기존 enabled 삭제
	   	   $(selector).attr('disabled', 'disabled'); // Disable 처리
	}
}
// 해당 id/name에 속한 checkbox/radio의 개수 또는 체크된 개수 반환
function getCountChecked(idOrName, checkboxNameId, boolChecked){
	var selector = "input["+ idOrName + "=" + checkboxNameId + "]";
	if(boolChecked)
		selector = selector + ":checked";
    return $(selector).length;
}
// checkbox/radio를 체크 선택/해제 시키는 wrapper 함수
function controllChecked(idOrName, checkboxNameId, boolChecked){
	var selector = "input["+ idOrName + "=" + checkboxNameId + "]";
	if(boolChecked){
	    $(selector).attr('checked','checked');
	} else {
		$(selector).removeAttr('checked');
	}
}
/**
 *  Checkbox를 제외한 일반 form element들을 Enable/Disable 처리하는 공통함수
 *  formId : form element가 속해있는 form의 id(페이지내에서 유일하다면 "" 문자열도 가능함)
 *  controllId : form element의 id명
 *  boolEnabled  : true(Enable)/false(Disable)
 *  boolKeepData   : 기존 체크된 데이터를 보존할 것인지 여부 true(보존) / false(비보존)
 */
function controlEnabled(formId, controllId, boolEnabled, boolKeepData){
	var selector = '';
	if(formId != 'undefined' && formId != null && formId != ""){
	    selector = "#"+ formId + " #" +  controllId;
	} else {
		selector = "#"+ controllId;
	}

    if(!boolKeepData){
		if($(selector).is("select")){
			var selectedSelector = selector + " option:selected";  // 현재 선택 option의 selector
			$(selectedSelector).removeAttr('selected'); // 데이터 보존하지 않음
		} else {
			$(selector).val("");
		}
	}

	if(boolEnabled){

		$(selector).removeAttr('disabled');
		$(selector).attr('enabled', 'enabled');
	} else {
		$(selector).removeAttr('enabled');
		$(selector).attr('disabled', 'disabled');
	}
}

/**
 * channel All 선택
 */
function checkBoxSelectAll(obj, chkListName) {

	var chkCannel = $('input[name=' + chkListName+']');

	if(obj.checked){
		for(var i = 0; i < chkCannel.length; i++ ){
			//$(chkCannel)[i].checked = true;
			chkCannel[i].checked = true;
		}
	}
	else { // all unchecked
		for (var i = 0; i < chkCannel.length; i++) {
			chkCannel[i].checked = false;
			//$(chkCannel)[i].checked = false;
		}
	}
}

/**
 * channel 개별 선택.
 */
function checkBoxSelect(checkAllId, checkListName){
	var channels = document.getElementsByName(checkListName);
	var isCheckAll = true;
	for (var i = 0; i < channels.length; i++) {
		if (channels[i].checked === false) {
			isCheckAll = false;
			break;
		}
	}
	if (isCheckAll) {
		document.getElementById(checkAllId).checked = true;
	}
	else {
		document.getElementById(checkAllId).checked = false;
	}
}

/**
 * Test Channel 선택/해제
 */
function chkTestChannel(chkObj, channelAllId, channelListName){
	var isChecked = chkObj.checked;
	var channels = document.getElementsByName(channelListName);
	if (isChecked) {
		// Channel 선택된 것을 해제.
		if(channels.length > 1){
			document.getElementById(channelAllId).checked = false;
		}
		$("input[name="+channelListName+"]:checkbox:checked").each(function() {
			this.checked = false;
		});
	}
}
/**
 * Channel 의 선택/해제
 */
function chkChannel(chkObj, testChannel) {
	var isChecked = chkObj.checked;
	if (isChecked) {
		// Test Channel 선택된 것을 해제.
		document.getElementById(testChannel).checked = false;
	}
}

/*****************checkbox, radio 컨트롤 관련 공통 함수 끝 **********************/


/***************** 시작종료일 체크 함수 시작**********************/

// 모든 공백제거(양끝, 중간)
function eraseSpace(val) {
	if(val == null) {
		val = "";
	}
	var space = /\s+/g;
	val = val.replace(space,"");
	return val;
}

// 공백여부
function isEmpty(val) {
	if (val == null || eraseSpace(val) == "") {
		return true;
	}
	return false;
}

// 좌측공백제거
function lTrim(val) {
	var space = /^\s*/;
	val = val.replace(space,"");
	return val;
}

// 우측공백제거
function rTrim(val) {
	var space = /\s*$/;
	val = val.replace(space,"");
	return val;
}

// 양쪽 끝 공백제거
function trim(val) {
	val = rTrim(lTrim(val));
	return val;
}

// String 함수에 trim 추가.
if (!String.prototype.trim) {
	String.prototype.trim = function() {
		return this.replace(/^\s*(\S*(?:\s+\S+)*)\s*$/, "$1");
	};
}

// 윤년여부
function isLeapYear(iYear) {
	if (((iYear % 4) == 0) && ((iYear % 100) != 0) || ((iYear % 400) == 0)) {
		return false;
	} else {
		return true;
	}
}

// 년월별 마지막 날
function getDaysInMonth(iYear, iMonth) {
	var tmpByte=0;

	if (iMonth == 1 || iMonth == 3 || iMonth == 5 || iMonth == 7 || iMonth == 8 || iMonth == 10 || iMonth == 12) {
		tmpByte = 31;
	} else if (iMonth == 4 || iMonth == 6 || iMonth == 9 || iMonth == 11) {
		tmpByte = 30;
	} else if (iMonth == 2) {
		if (!isLeapYear(iYear)) {
			tmpByte = 29;
		} else {
			tmpByte = 28;
		}
	}

	return tmpByte;
}

// "0" 추가
function addZero(n) {
	  return n < 10 ? "0" + n : n;
}

// 날짜 정규식
var regDate = /^([1-9]\d{3})[\-\/\.]?(0[1-9]|1[012])[\-\/\.]?(0[1-9]|[12][0-9]|3[0-1])$/;

// 시작종료일 관련 함수
function checkStaEndDt(staObj, endObj) {

	var d = new Date();
	var staDt;
	var endDt;

	var toDay =
	    d.getFullYear() + '-' +
	    addZero(d.getMonth() + 1, 2) + '-' +
	    addZero(d.getDate(), 2);

	if (!isEmpty(eraseSpace(staObj.val()))) {
		if (staObj.val().match(regDate) == null) {
			open_message('Alert', 'admin.common.date.invalidFormat', "");
			return false;
		} else {
			if (parseInt(RegExp.$3, 10) < 0 || parseInt(RegExp.$3, 10) > getDaysInMonth(parseInt(RegExp.$1, 10), parseInt(RegExp.$2, 10))) {
				open_message('Alert', 'admin.common.date.invalidDate', "");
				return false;
			} else {
				staDt = RegExp.$1 + "-" + RegExp.$2 + "-" + RegExp.$3;

				if (staDt > toDay) {
					open_message('Alert',  'admin.common.date.todayEarly', "");
					return false;
				}
			}
		}
	}

	if (!isEmpty(eraseSpace(endObj.val()))) {
		if (endObj.val().match(regDate) == null) {
			open_message('Alert', 'admin.common.date.invalidFormat', "");
			return false;
		} else {
			if (parseInt(RegExp.$3, 10) < 0 || parseInt(RegExp.$3, 10) > getDaysInMonth(parseInt(RegExp.$1, 10), parseInt(RegExp.$2, 10))) {
				open_message('Alert', 'admin.common.date.invalidDate', "");
				return false;
			} else {
				endDt = RegExp.$1 + "-" + RegExp.$2 + "-" + RegExp.$3;
			}
		}
	}

	if (!isEmpty(eraseSpace(staObj.val())) && !isEmpty(eraseSpace(endObj.val()))) {
		if (staDt > endDt) {
			open_message('Alert', 'admin.common.date.todayEarly', "");
			return false;
		}
	}

	staObj.val(staDt);
	endObj.val(endDt);
	return true;

}
/***************** 시작종료일 체크 함수 끝**********************/


/**
 * sleep
 */
function sleep(msecs){
    var start = new Date().getTime();
    var cur = start;
    while (cur - start < msecs){
        cur = new Date().getTime();
    }
}


function buttonResourceInit(buttonResourceListAll, buttonResourceListByUserId) {
	var buttonResource_Y = "";
	for(var index=0; index<buttonResourceListAll.length; index++) {
		buttonResource_Y = "N";
		for(var index2=0; index2<buttonResourceListByUserId.length; index2++) {
				//alert(buttonResourceListAll[index] + " " + buttonResourceListByUserId[index2]);
				if(buttonResourceListAll[index] == buttonResourceListByUserId[index2]){
					buttonResource_Y = "Y";
				}
		}
		if(buttonResource_Y == "N"){
			$("*[buttonResourceId='" + buttonResourceListAll[index] + "']").attr("href", "#none");
			$("*[buttonResourceId='" + buttonResourceListAll[index] + "']").attr("onclick", "");
			$("*[buttonResourceId='" + buttonResourceListAll[index] + "']").click("onclick", function () {open_message('Alert','admin.MH_CM_09.buttonResource.notPermitted');});

		}
	}
}

/**
 * Left padding.
 *
 * @param val
 * @param len
 * @param char
 */
lpad = function (val, len, char) {
	val = String(val);
	len = len || 2;
	while (val.length < len) val = char + val;
	return val;
};

/**
 * Right padding.
 *
 * @param val
 * @param len
 * @param char
 */
rpad = function (val, len, char) {
	val = String(val);
	len = len || 2;
	while (val.length < len) val = val + char;
	return val;
};

/**
 * Javascript 'typeof' 기능 재정의.
 * @param value
 * @returns
 */
function typeOf(value) {
    var s = typeof value;
    if (s === 'object') {
        if (value) {
            if (Object.prototype.toString.call(value) == '[object Array]') {
                s = 'array';
            }
        } else {
            s = 'null';
        }
    }
    return s;
}


function chkNumberFormatter(obj){

	var filter = '[0-9]';
	if(event.keyCode!=37&&event.keyCode!=38&&event.keyCode!=39&&event.keyCode!=40){
		if(filter){
			var fileNm=$("#"+obj.id).val();
			var fileNmFormatted="";
			var chkStr = true;

			for(var i=0;fileNm.length>i;i++){
				var sKey = fileNm.charAt(i);
				var re = new RegExp(filter);
				if(re.test(sKey)){
					fileNmFormatted+=fileNm.charAt(i);
				}
				if(!re.test(sKey)){
					chkStr = false;
				}
			}
			//alert(fileNmFormatted);
			if(chkStr!=true){
				$("#"+obj.id).val(fileNmFormatted);
			}

		}
	}

}
//function buttonResourceInit(buttonResourceListAll, buttonResourceListByUserId) {
//	for(var index=0; index<buttonResourceListAll.length; index++) {
//		$("*[buttonResourceId='" + buttonResourceListAll[index] + "']").attr("buttonAccessYn", "false");
//	}
//	for(var index=0; index<buttonResourceListByUserId.length; index++) {
//		$("*[buttonResourceId='" + buttonResourceListByUserId[index] + "']").attr("buttonAccessYn", "true");
//	}
//	$("*[buttonResourceId][buttonAccessYn='false']").attr("href", "#none");
//	$("*[buttonResourceId][buttonAccessYn='false']").attr("onclick", "javascript:open_message('Alert','admin.MH_CM_09.buttonResource.notPermitted');");
//}


function downloadCsv(csvId) {
  var form = $("#downloadForm");
  form.attr("action", "/portal/download/"+csvId);
  form.get(0).submit();
}


//p_url        : URL
// p_pos        : c - CenterScreen
// p_resz       : resizable
// p_name       : target
// p_w          : width
// p_h          : height
// p_t          : top
// p_l          : left
// p_s          : scrollbars
function windowOpenPost(p_form, p_url, p_pos, p_resz, p_name, p_w, p_h, p_t, p_l, p_s) {    
    var pxWidth = "1015";
    var pxHeight = "690";
    
    if (p_pos == undefined || p_pos =='') p_pos = "l" ;
    if (p_w == undefined || p_w == 0 || p_w == '') p_w = pxWidth ;
    if (p_h == undefined || p_h == 0 || p_h == '') p_h = pxHeight ;
    if (p_t == undefined || p_t == '') p_t = 0;
    if (p_l == undefined || p_l == '') p_l = 0;
    if (p_name == undefined) p_name = '';
    if (p_resz == undefined) p_resz = '0';
    if (p_s == undefined) p_s = 'no';
    
    if (p_pos == 'c'){
        p_t = (window.screen.availHeight - p_h)/2;
        p_l = (window.screen.availWidth - p_w)/2;
    }
    
    var sProp = '';
    sProp = 'left=' + p_l + ',top=' + p_t + ',width='  + p_w + ',height=' + p_h + ',resizable='+ p_resz +',scrollbars='+p_s+',menubars=no,status=yes';
    
    var popupWindow = window.open( '', p_name, sProp );
    if(popupWindow == null) {
    	alert("차단된 팝업창을 허용해 주십시오.");
    	return;
    }
    //var old_target = p_form.target;
    //var old_action = p_form.action;
    
    p_form.target = p_name;
    p_form.action = p_url;
    p_form.submit();    
    
    //p_form.target = old_target;
    //p_form.action = old_action;
    
    popupWindow.focus();
    
    return popupWindow;
}

/*
 * html escape 
 */
 // Use the browser's built-in functionality to quickly and safely escape the
 // string
 function escapeHtml(str) {
     var div = document.createElement('div');
     div.appendChild(document.createTextNode(str));
     return div.innerHTML;
 };
  
 // UNSAFE with unsafe strings; only use on previously-escaped ones!
 function unescapeHtml(escapedStr) {
     var div = document.createElement('div');
     div.innerHTML = escapedStr;
     var child = div.childNodes[0];
     return child ? child.nodeValue : '';
 };
 
 
 function trim(str) {
	 return str.replace(/^\s+/g,'').replace(/\s+$/g,'');
 }
  