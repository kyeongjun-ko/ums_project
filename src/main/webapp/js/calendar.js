/**
 * @author Richie (richie@uzen.net)
 *
 * 달력 팝업 관련
 *
 */

var calendar = null;
/**
 * 달력 선택 팝업을 띄운다
 *
 * @param target
 *            값을 돌려 받을 input text, 팝업을 띄울때 target의 좌측 하단에 맞추기때문에 반드시 not null
 *            이어야한다
 * @param dateString
 *            팝업의 설정 날짜
 * @param callback
 *            날짜를 선택한 뒤 특별한 처리를 하고자 할 때 callback을 설정하면 호출된다 func(target,
 *            dateString, year, month, day) 형태로 인자를 받을 수 있다
 * @param option
 *            달력 구성 옵션값
 *            isPast : boolean 값(default = false), 과거일자 선택가능 = true
 *            timeoffset : int 값, UTC 를 기준으로 보여주고 싶은 시간대를 선택한다. 미리초단위(milliseconds)로 입력.
 *            beforeYears : int 값, 현재년도기준으로 입력한 년수만큼의 년도를 선택할 수 있게 한다.
 *            selectStartDate / selectEndDate : yyyymmdd, yyyy-mm-dd, yyyy/mm/dd, yyyy.mm.dd 형식의 string 값.
 *            									달력 선택가능 날짜 구간을 정해준다.
 *
 *
 */
function openCalendar(target, dateString, callback, option) {
	if(!option) {
		option = {
				beforeYears: 10
				, isPast: false
		};
	}
	if(target) {
		if (calendar != null) {
			calendar.hide();
			calendar = null;
		}
		else {
			calendar = new Calendar(target, dateString, callback, option);
			calendar.show();
		}
	} else {
		alert('target이 지정되지 않았습니다!\n input text를 첫번째 인자로 넣어주세요.');
	}

}

/**
 * 달력 객체 생성자
 */
function Calendar(target, dateString, callback, option) {

	this.init(target, dateString, callback, option);

}

/**
 * 달력 객체
 */
Calendar.prototype = {

	/**
	 * 초기화
	 */
	init: function(target, dateString, callback, option) {

		this.target = target;
		this.calendarOption = option;
		if (!this.calendarOption.timeoffset) {	// 2011.07.11 added. 시간대 표현.
			this.calendarOption.timeoffset = SHOP_TIMEOFFSET;
		}

		this.selectedDate = stringToDate(dateString);

		if(!this.selectedDate) {
			this.selectedDate = stringToDate(target.value);
		}
		if(!this.selectedDate) {
			this.selectedDate = createDateWithTimezone(new Date(), this.calendarOption.timeoffset);
		}
		this.today = createDateWithTimezone(new Date(), this.calendarOption.timeoffset);
		this.today.setHours(0);
		this.today.setMinutes(0);
		this.today.setSeconds(0);
		this.today.setMilliseconds(0);
		this.callback = callback;

		/* Constants */
		this.WIDTH = 205;
		this.HEIGHT_BASE = 47;
		this.HEIGHT_DELTA = 25;
	},

	/**
	 * 달력 팝업 띄우기
	 */
	show: function() {
		this.popup =  $(this.makeBodyHTML());
		this.popup.css("position", "absolute");
		this.popup.appendTo("body");
		this.locatePopup();
	},

	/**
	 * 달력 팝업의 BODY의 innerHTML 생성하기
	 */
	makeBodyHTML: function() {

		var _CALENDAR_PREV_BTN_ = "../images/common/btn_prev.gif";
		var _CALENDAR_NEXT_BTN_ = "../images/common/btn_next.gif";
		var _CALENDAR_CLOSE_BTN_ = "../images/common/btn_pop_close2.gif";
		var year = this.selectedDate.getFullYear();
		var month = this.selectedDate.getMonth() + 1; // Date.getMonth()는 0 ~
														// 11 까지의 값을 리턴하기 때문에
														// ...

		var yearAsString = "" + year;
		var monthAsString = ((month < 10) ? "0" : "") + month;
		var beforeYear = 10;
		if (this.calendarOption && this.calendarOption.beforeYears){
			beforeYear = this.calendarOption.beforeYears;
		}
		var selectStartDate;
		var selectEndDate;
		// option isPast 가 false 인경우, 과거 날짜 선택 안됨.
		if (this.calendarOption && this.calendarOption.isPast === false) {
			selectStartDate = getToday(createDateWithTimezone(new Date(), this.calendarOption.timeoffset));
		}
		if (this.calendarOption && this.calendarOption.selectStartDate){
			selectStartDate = this.calendarOption.selectStartDate;

		}
		if (this.calendarOption && this.calendarOption.selectEndDate){
			selectEndDate = this.calendarOption.selectEndDate;
		}
		else {
			selectEndDate = "9999-12-31";
		}

		html = "<div class='layerpop' id='calendar1' style='display:block; overflow:visible;'>\
					<div class='shadow'>\
						<div class='LayerPopCalendar'>\
						<!-- START -->\
						<div class='ThisMonth'>\
							<span class='Year'><a style='cursor:pointer;' onclick='javascript:calendar.goPrevYear()'><img src='" + _CALENDAR_PREV_BTN_ + "' alt='Prev' /></a>";
		html +=				"<em>"+yearAsString+ "</em>\
							<a style='cursor:pointer;' onclick='javascript:calendar.goNextYear()'><img src='" + _CALENDAR_NEXT_BTN_ + "' alt='Next' /></a></span>\
							<span class='Month'><a style='cursor:pointer;' onclick='javascript:calendar.goPrevMonth()'><img src='" + _CALENDAR_PREV_BTN_ + "' alt='Prev' /></a>";
		html +=				"<em>"+monthAsString+"</em>\
							<a style='cursor:pointer;' onclick='javascript:calendar.goNextMonth()'><img src='" + _CALENDAR_NEXT_BTN_ + "' alt='Next' /></a></span>\
						</div>\
						<table cellpadding='0' cellspacing='1'>\
							<thead>\
							<tr>\
								<th scope='col' width='22'>S</th>\
								<th scope='col' width='22'>M</th>\
								<th scope='col' width='22'>T</th>\
								<th scope='col' width='22'>W</th>\
								<th scope='col' width='22'>T</th>\
								<th scope='col' width='22'>F</th>\
								<th scope='col' width='22'>S</th>\
							</tr>\
							</thead>\
							<tbody>";


		var firstDate = getFirstDateOfMonth(this.selectedDate);
		var lastDate = getLastDateOfMonth(this.selectedDate);

		var firstDatePos = firstDate.getDay();
		var lastDatePos = firstDatePos + lastDate.getDate();

		cellCount = lastDatePos + (6 - lastDate.getDay());

		this.rowCount = cellCount / 7; // 달력의 줄수

		var realDate;
		var startDate = removeDateDelimeter(selectStartDate);
		var endDate = removeDateDelimeter(selectEndDate);
		for(var d = 0; d < cellCount; ++d) {

			realDate = d - firstDatePos + 1;

			if(d % 7 == 0) {
				if(d == 0) {
					html += "<tr class='first'>";
				} else {
					html += "<tr>";
				}
			}

			// 선택가능 날짜 값이 있을 경우에만.
			//if (startDate != null && endDate != null) {
			if (startDate !== null || endDate !== null) {
				if (endDate == null) {
					endDate = "99991231";
				}

				html += "<td";
				if(d < firstDatePos || lastDatePos <= d) {
					html += ">";
				} else {
					var dateStr = (realDate < 10) ? "0"+realDate : realDate;
					var realYMD = removeDateDelimeter(yearAsString+monthAsString+dateStr);
					if(startDate <= realYMD && realYMD <= endDate) {
						if(realDate == this.selectedDate.getDate())
							html += " class='on selective'>";
						else if(d % 7 == 0)
							html += " class='sun selective'>";
						else if(d % 7 == 6)
							html += " class='sat selective'>";
						else
							html += " class='selective'>";
						html += "<a href='javascript:calendar.selectDate("+ realDate +")'>" + realDate + "</a>";
					}
					else {
						if(realDate == this.selectedDate.getDate())
							html += " class='on'>";
						else if(d % 7 == 0)
							html += " class='sun'>";
						else if(d % 7 == 6)
							html += " class='sat'>";
						else
							html += ">";
						html += "<span>" + realDate + "</span>";
					}

				}
				html +=  "</td>";

				if(d % 7 == 6) {
					html += "</tr>";
				}
			}
			// 전체 선택 가능 달력.
			else {
				html += "<td";
				if(d < firstDatePos || lastDatePos <= d) {
					html += ">";
				} else {
					if(realDate == this.selectedDate.getDate())
						html += " class='on'>";
					else if(d % 7 == 0)
						html += " class='sun'>";
					else if(d % 7 == 6)
						html += " class='sat'>";
					else
					html += ">";

					html += "<a href='javascript:calendar.selectDate("+ realDate +")'>" + realDate + "</a>";
				}
				html +=  "</td>";

				if(d % 7 == 6) {
					html += "</tr>";
				}
			}
		}

		html +=	"		</tbody>\
					</table>\
					<a style='cursor:pointer;' onclick='javascript:calendar.close();'><img src='" + _CALENDAR_CLOSE_BTN_ + "' class='BtnClose' alt='Close' /></a>\
					<!-- // END -->\
				</div>\
			</div>\
		</div>";

		return html;
	},

	/**
	 * 날짜 선택 이벤트 핸들러
	 */
	selectDate: function(date) {
		var y = this.selectedDate.getFullYear();
		var m = (((this.selectedDate.getMonth() + 1) < 10) ? "0" : "") + (this.selectedDate.getMonth() + 1);
		var d = ((date < 10) ? "0" : "") + date;

		var dateString = y + "-" + m + "-" + d;
		//var dateString = y + "" + m + "" + d;

		// 과거 금지
		if (this.calendarOption.isPast == false) {
			this.selectedDate = stringToDate(dateString);
			if (this.selectedDate < this.today) {
				return;
			}
		}

		this.hide();

		calendar = null;

		if(this.target) {
			$("#"+this.target).val(dateString);
			// this.target.value = dateString;
		}

		if(this.callback) {
			if (typeof this.callback == "function") {
				var res =this.callback(this.target, dateString, y, m, d);
				if (false == res) {
					return;
				}
			}
		}

	},

	/**
	 * 달력 숨기기
	 */
	hide: function() {
		this.popup.hide();
		this.popup.remove();
	},

	/**
	 * 다음 달 보기
	 */
	goNextMonth: function() {
		if(!this.popup) {
			return;
		}

		this.selectedDate = getNextMonth(this.selectedDate);

		this.hide();
		this.show();

	},

	/**
	 * 지난 달 보기
	 */
	goPrevMonth: function() {
		if(!this.popup) {
			return;
		}

		this.selectedDate = getPrevMonth(this.selectedDate);

		this.hide();
		this.show();
	},

	/**
	 * 익년 보기
	 */
	goNextYear: function() {
		if(!this.popup) {
			return;
		}

		this.selectedDate = getNextYear(this.selectedDate);

		this.hide();
		this.show();

	},

	/**
	 * 작년 보기
	 */
	goPrevYear: function() {
		if(!this.popup) {
			return;
		}

		this.selectedDate = getPrevYear(this.selectedDate);

		this.hide();
		this.show();
	},

	locatePopup :function (){
		var pos = $("#"+this.target).offset();
		this.popup.css('top', pos.top+$("#"+this.target).height());
		this.popup.css('left', pos.left);
		this.popup.css('z-index', 9999999);
	},

	close : function() {
		calendar.hide();
		calendar = null;
	}
};

/**
 * 문자열을 날짜로 변환한다 문자열 형식은 "2008/05/21", "2008-05-21", "2008.05.21"만 지원한다
 *
 */
function stringToDate(str) {
	if(str) {
		var matches = str.match(/(\d{4})[-.\/](\d{1,2})[-.\/](\d{1,2})/);
		if(matches != null) {
			return createDate(parseInt(matches[1], 10), parseInt(matches[2], 10), parseInt(matches[3], 10));
		}
	}
	return null;
}

/**
 * 문자열을 날짜로 변환한다.
 * <h4>입력가능 시간 포멧</h4>
 * <ul>
 * <li>yyyy-MM-dd HH:mm:ss</li>
 * <li>yyyy-MM-dd HH:mm</li>
 * <li>yyyy-MM-dd HH</li>
 * <li>yyyy-MM-dd</li>
 * <li>yyyy.MM.dd HH.mm.ss</li>
 * <li>yyyy.MM.dd HH.mm</li>
 * <li>yyyy.MM.dd HH</li>
 * <li>yyyy.MM.dd</li>
 * <li>yyyyMMddHHmmss</li>
 * <li>yyyyMMddHHmm</li>
 * <li>yyyyMMddHH</li>
 * <li>yyyyMMdd</li>
 * </ul>
 *
 * @param str date string
 * @returns {Date}
 */
function dateStringToDateObj(str) {
	var dateStr = str.split(":").join("");
	dateStr = dateStr.split("-").join("");
	dateStr = dateStr.split(" ").join("");
	dateStr = dateStr.split(".").join("");
	if (dateStr.length !== 14) {
		dateStr = rpad(dateStr, 14, "0");
	}

	var year = dateStr.substr(0, 4);
	var mon = dateStr.substr(4, 2);
	var day = dateStr.substr(6, 2);
	var hour = dateStr.substr(8, 2);
	var min = dateStr.substr(10, 2);
	var sec = dateStr.substr(12, 2);
	return new Date(year, mon - 1, day, hour, min, sec, 0);
}

/**
 * 오늘 날짜를 구한다.
 * @returns yyyy-mm-dd
 */
var getToday = function(dateObj) {
	var todayDate = null;
	if (dateObj != null) {
		todayDate = dateObj;
	}
	else {
		todayDate = createDateWithTimezone(new Date(), SHOP_TIMEOFFSET);
	}
	var month = todayDate.getMonth() + 1;
	var monthAsString = ((month < 10) ? "0" : "") + month;
	var date = todayDate.getDate();
	var dateAsString = ((date < 10) ? "0" : "") + date;
	return todayDate.getFullYear() + "-" + monthAsString + "-" + dateAsString;
};

/**
 * 오늘 날짜를 구한다.
 * @returns yyyy-mm-dd HH:MM:SS
 */
var getTodayFull = function(dateObj) {
	var todayDate = null;
	if (dateObj != null) {
		todayDate = dateObj;
	}
	else {
		todayDate = createDateWithTimezone(new Date(), SHOP_TIMEOFFSET);
	}
	var sec = todayDate.getSeconds();
	var secAsString = ((sec < 10) ? "0" : "") + sec;
	var min = todayDate.getMinutes();
	var minAsString = ((min < 10) ? "0" : "") + min;
	var hour = todayDate.getHours();
	var hourAsString = ((hour < 10) ? "0" : "") + hour;
	var month = todayDate.getMonth() + 1;
	var monthAsString = ((month < 10) ? "0" : "") + month;
	var date = todayDate.getDate();
	var dateAsString = ((date < 10) ? "0" : "") + date;
	return todayDate.getFullYear() + "-" + monthAsString + "-" + dateAsString + " " + hourAsString + ":" + minAsString + ":" + secAsString;
};

/**
 * 오늘 날짜를 구한다.
 * @returns yyyymmdd
 */
var getTodayNodele = function(dateObj) {
	return getToday(dateObj).replace(/[-]/gi, "");
};

/**
 * 오늘 날짜를 구한다.
 * @returns yyyymmddHHMMSS
 */
var getTodayFullNodele = function(dateObj) {
	return getTodayFull(dateObj).replace(/[-:\s]/gi, "");
};

/**
 * 오늘 날짜에 delta 만큼을 더한 날짜를 구한다.
 * @param delta 일단위
 * @returns yyyy-MM-dd
 */
var getAddDay = function(delta) {
	var deltaMillisec = delta * 1000 * 60 * 60 * 24;
	var todayDate = createDateWithTimezone(new Date(), SHOP_TIMEOFFSET);
	var addDate = new Date(todayDate.getTime() + deltaMillisec);
	var month = addDate.getMonth() + 1;
	var monthAsString = ((month < 10) ? "0" : "") + month;
	var date = addDate.getDate();
	var dateAsString = ((date < 10) ? "0" : "") + date;
	return addDate.getFullYear() + "-" + monthAsString + "-" + dateAsString;
};

/**
 * 문자열 형식의 date string(yyyy/mm/dd, yyyy-mm-dd, yyyy.mm.dd) 을 int 형식으로 변환
 * @param strDate
 * @return
 */
function removeDateDelimeter(strDate) {
	if (strDate == null) {
		return null;
	}
	else {
		var retDateNumber = strDate.replace(/[-.\/]/gi, "");
		return parseInt(retDateNumber);
	}
}

/**
 *
 */
function createDate(year, month, date) {
	return doCreateDate(year, month - 1, date);
}

function doCreateDate(year, month, date) {
	return new Date(year, month, date, 0, 0, 0, 0);
}

/**
 * 타임존offset 을 가지는 dateString 으로 Date 객체 생성.
 * 2011.07.11 추가.
 * @param dateObj
 * @param timezoneoffset 미리초단위.
 */
function createDateWithTimezone(dateObj, timezoneoffset) {
	var utcDate = getUTCDate(dateObj);
	var utcDateLongTime = utcDate.getTime();
	return new Date(utcDateLongTime +  parseInt(timezoneoffset));
}

/**
 * CST기준의 시간값을 받아서 UTC 0 기준 시간으로 표현.<br/>
 * default --> UTC : yyyy-mm-dd HH ~ yyyy-mm-dd HH
 * <h4>입력가능 시간 포멧</h4>
 * <ul>
 * <li>yyyy-MM-dd HH:mm:ss</li>
 * <li>yyyy-MM-dd HH:mm</li>
 * <li>yyyy-MM-dd HH</li>
 * <li>yyyy-MM-dd</li>
 * <li>yyyy.MM.dd HH.mm.ss</li>
 * <li>yyyy.MM.dd HH.mm</li>
 * <li>yyyy.MM.dd HH</li>
 * <li>yyyy.MM.dd</li>
 * <li>yyyyMMddHHmmss</li>
 * <li>yyyyMMddHHmm</li>
 * <li>yyyyMMddHH</li>
 * <li>yyyyMMdd</li>
 * </ul>
 * 2012.02.17 추가.
 * @param fromCSTDateTime
 * @param toCSTDateTime
 * @param targetId 변경된 시간 표현을 보여줄
 * @param timeformat date.format.js 의 형식을 따른다. default 'yyyy-mm-dd HH'
 */
function convertCSTDateTimeToUTC(fromCSTDateTime, toCSTDateTime, targetId, timeformat) {
	//var timeoffset = Math.abs(parseInt(SHOP_TIMEOFFSET));
	var cstTimeOffset = parseInt(_TIMEOFFSET_); //1000 * 60 * 60 * 6;
	var cdtTimeOffset = parseInt(_DAYLIGHT_TIMEOFFSET_); //1000 * 60 * 60 * 5;
	if (fromCSTDateTime === null) {
		fromCSTDateTime = "";
	}
	if (toCSTDateTime === null) {
		toCSTDateTime = "";
	}

	var fromDate = dateStringToDateObj(fromCSTDateTime);
	if (isDST(fromCSTDateTime)) {
		fromDate.setTime(fromDate.getTime() - cdtTimeOffset);
	}
	else {
		fromDate.setTime(fromDate.getTime() - cstTimeOffset);
	}
	var toDate = dateStringToDateObj(toCSTDateTime);
	if (isDST(toCSTDateTime)) {
		toDate.setTime(toDate.getTime() - cdtTimeOffset);
	}
	else {
		toDate.setTime(toDate.getTime() - cstTimeOffset);
	}

	var result = "";

	if ((fromCSTDateTime === "" || fromCSTDateTime === null) && (toCSTDateTime === "" || toCSTDateTime === null)) {
		result = "";
	} else {
		if (fromCSTDateTime === "" || fromCSTDateTime === null) {
			if (timeformat) {
				result = "(UTC :  ~ " + toDate.format(timeformat) + ")";
			}
			else {
				result = "(UTC :  ~ " + toDate.format("yyyy-mm-dd HH") + ")";
			}
		}
		else if (toCSTDateTime === "" || toCSTDateTime === null) {
			if (timeformat) {
				result = "(UTC : " + fromDate.format(timeformat) + " ~ )";
			}
			else {
				result = "(UTC : " + fromDate.format("yyyy-mm-dd HH") + " ~ )";
			}
		}
		else {
			if (timeformat) {
				result = "(UTC : " + fromDate.format(timeformat) + " ~ " + toDate.format(timeformat) + ")";
			}
			else {
				result = "(UTC : " + fromDate.format("yyyy-mm-dd HH") + " ~ " + toDate.format("yyyy-mm-dd HH") + ")";
			}
		}
	}

	//alert(result);
	$("#"+targetId).html(result);
}

/**
 * 입력된 Date 객체를 UTC 0 기준의 Date 객체로 변환.<br/>
 * 입력된 Date 객체는 Local time 으로 간주 함.<br/>
 * 2012.02.17 추가.<br/>
 *
 * dependency : date.foramt.js
 *
 * @param dateObj javascript Date Object
 * @returns {Date}
 */
function getUTCDate(dateObj) {
	var dateStr = dateObj.format("yyyymmddHHMMss", true); // 입력된 Date 객체를 지정된 형식의 String 으로 변환, boolean 값은 UTC 여부.
	var year = dateStr.substring(0, 4);
	var mon = dateStr.substring(4, 6);
	var day = dateStr.substring(6, 8);
	var hour = dateStr.substring(8, 10);
	var min = dateStr.substring(10, 12);
	var sec = dateStr.substring(12, 14);
	return new Date(year, mon - 1, day, hour, min, sec, 0);
}

/**
 * CST dateTime 값의 DST 적용 여부 확인.
 *
 * @param dateTime yyyyMMddHHmmss
 * @returns {Boolean}
 */
function isDST(dateTime) {
	var year = dateTime.substr(0, 4);
	// DST Begins 2 a.m. Second Sunday in March.
	var marchFisrtDate = new Date(year, 2, 1, 2, 0, 0, 0);
	//alert(beginDate);
	var dayOfWeek = marchFisrtDate.getDay();
	var secondSunday = 1 + 7;
	if (dayOfWeek > 0) {
		var firstSunday = 1 + (7 - dayOfWeek);
		secondSunday = firstSunday + 7;
	}
	//alert(secondSunday);
	var beginDate = new Date(year, 2, secondSunday, 2, 0, 0, 0);
	//alert(beginDate);

	// DST Ends 2 a.m. First Sunday in November.
	var novemberFirstDate = new Date(year, 10, 1, 2, 0, 0, 0);
	var dayOfWeek = novemberFirstDate.getDay();
	var firstSunday = 1;
	if (dayOfWeek > 0) {
		firstSunday = 1 + (7 - dayOfWeek);
	}
	//alert(secondSunday);
	var endDate = new Date(year, 10, firstSunday, 2, 0, 0, 0);
	//alert(endDate);

	var beginDateTime = beginDate.getTime();
	var endDateTime = endDate.getTime();
	var inputDateTime = dateStringToDateObj(dateTime).getTime();
	if (inputDateTime >= beginDateTime && inputDateTime <= endDateTime) {
		return true;
	}
	else {
		return false;
	}
}



/**
 *
 */
function copyDate(date) {
	return doCreateDate(date.getFullYear(), date.getMonth(), date.getDate());
}

/**
 * 해당 월의 첫날 날짜 구하기
 */
function getFirstDateOfMonth(date) {
	return doCreateDate(date.getFullYear(), date.getMonth(), 1);
}

/**
 * 해당 월의 마직막 날짜 구하기
 */
function getLastDateOfMonth(date) {
	return doCreateDate(date.getFullYear(), date.getMonth() + 1, 0);
}

/**
 * 한달 전 날짜 구하기
 */
function getPrevMonth(date) {

	// 지난 달 마지막 날짜를 구한다
	var newDate = doCreateDate(date.getFullYear(), date.getMonth(), 0);

	// 1. 현재 '일'이 지난 달 마지막 '일'보다 작으면, '월'만 감소시키고
	// 2. 아니면 지난 달 마지막 날짜를 리턴한다
	return (date.getDate() < newDate.getDate()) ? doCreateDate(date.getFullYear(), date.getMonth() - 1, date.getDate()) : newDate;
}

/**
 * 한달 후 날짜 구하기
 */
function getNextMonth(date) {

	// 다음 달 마지막 날짜를 구한다
	var newDate = doCreateDate(date.getFullYear(), date.getMonth() + 2, 0);

	// 1. 현재 '일'이 다음 달 마지막 '일'보다 작으면, '월'만 증가시키고
	// 2. 아니면 다음 달 마지막 날짜를 리턴한다
	return (date.getDate() < newDate.getDate()) ? doCreateDate(date.getFullYear(), date.getMonth() + 1, date.getDate()) : newDate;
}

/**
 * 1년 전 날짜 구하기
 */
function getPrevYear(date) {

	// 지난 달 마지막 날짜를 구한다
	//var newDate = doCreateDate(date.getFullYear(), date.getMonth(), 0);

	// 1. 현재 '일'이 지난 달 마지막 '일'보다 작으면, '월'만 감소시키고
	// 2. 아니면 지난 달 마지막 날짜를 리턴한다
	//return (date.getDate() < newDate.getDate()) ? doCreateDate(date.getFullYear(), date.getMonth() - 1, date.getDate()) : newDate;
	return doCreateDate(date.getFullYear() - 1, date.getMonth(), date.getDate());
}

/**
 * 1년 후 날짜 구하기
 */
function getNextYear(date) {

	// 다음 달 마지막 날짜를 구한다
	//var newDate = doCreateDate(date.getFullYear(), date.getMonth() + 2, 0);

	// 1. 현재 '일'이 다음 달 마지막 '일'보다 작으면, '월'만 증가시키고
	// 2. 아니면 다음 달 마지막 날짜를 리턴한다
	//return (date.getDate() < newDate.getDate()) ? doCreateDate(date.getFullYear(), date.getMonth() + 1, date.getDate()) : newDate;
	return doCreateDate(date.getFullYear() + 1, date.getMonth(), date.getDate());
}

/** ****************************************** */
// 2008/06/04 charles추가
/**
 * 유효한(존재하는) 월(月)인지 체크
 */
function isValidMonth(mm) {
    var m = parseInt(mm,10);
    return (m >= 1 && m <= 12);
}

/**
 * 유효한(존재하는) 일(日)인지 체크
 */
function isValidDay(yyyy, mm, dd) {
    var m = parseInt(mm,10) - 1;
    var d = parseInt(dd,10);

    var end = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
    if ((yyyy % 4 == 0 && yyyy % 100 != 0) || yyyy % 400 == 0) {
        end[1] = 29;
    }

    return (d >= 1 && d <= end[m]);
}

/**
 * 유효한(존재하는) 시(時)인지 체크
 */
function isValidHour(hh) {
    var h = parseInt(hh,10);
    return (h >= 1 && h <= 24);
}

/**
 * 유효한(존재하는) 분(分)인지 체크
 */
function isValidMin(mi) {
    var m = parseInt(mi,10);
    return (m >= 1 && m <= 60);
}

/**
 * Time 형식인지 체크(느슨한 체크)
 */
function isValidTimeFormat(time) {
    return (!isNaN(time) && time.length == 12);
}

/**
 * 유효하는(존재하는) Time 인지 체크
 *
 * ex) var time = form.time.value; //'200102310000' if (!isValidTime(time)) {
 * alert("올바른 날짜가 아닙니다."); }
 */
function isValidTime(time) {
    var year  = time.substring(0,4);
    var month = time.substring(4,6);
    var day   = time.substring(6,8);
    var hour  = time.substring(8,10);
    var min   = time.substring(10,12);

    if (parseInt(year,10) >= 1900  && isValidMonth(month) &&
        isValidDay(year,month,day) && isValidHour(hour)   &&
        isValidMin(min)) {
        return true;
    }
    return false;
}

/**
 * Time 스트링을 자바스크립트 Date 객체로 변환 parameter time: Time 형식의 String
 */
function toTimeObject(time) { // parseTime(time)
    var year  = time.substr(0,4);
    var month = time.substr(4,2) - 1; // 1월=0,12월=11
    var day   = time.substr(6,2);
    var hour  = time.substr(8,2);
    var min   = time.substr(10,2);

    return new Date(year,month,day,hour,min);
}

/**
 * Time 스트링을 자바스크립트 Date 객체로 변환 parameter time: Time 형식의 String
 */
function toDateObject(time) { // parseTime(time)
    var year  = time.substr(0,4);
    var month = time.substr(4,2) - 1; // 1월=0,12월=11
    var day   = time.substr(6,2);

    return new Date(year,month,day);
}

/**
 * Time 스트링을 자바스크립트 Date 객체로 변환 parameter time: Time 형식의 String
 */
function toFormatString(time, dele) { // parseTime(time)
    var year  = time.substr(0,4);
    var month = time.substr(4,2); // 1월=0,12월=11
    var day   = time.substr(6,2);

    return ("" + year + dele + month + dele + day);
}

function toFormatStringNodele(time){
	var year  = time.substr(0,4);
	var month = time.substr(4,2); // 1월=0,12월=11
	var day   = time.substr(6,2);

	return ("" + year +  month + day);
}

/**
 * 자바스크립트 Date 객체를 DateTime 스트링으로 변환
 *
 * @param date javascript dateObj
 * @returns {String} yyyyMMddHHmmss
 */
function toTimeString(date) { // formatTime(date)
    var year  = date.getFullYear();
    var month = date.getMonth() + 1; // 1월=0,12월=11이므로 1 더함
    var day   = date.getDate();
    var hour  = date.getHours();
    var min   = date.getMinutes();
    var sec   = date.getSeconds();

    if (("" + month).length == 1) { month = "0" + month; }
    if (("" + day).length   == 1) { day   = "0" + day;   }
    if (("" + hour).length  == 1) { hour  = "0" + hour;  }
    if (("" + min).length   == 1) { min   = "0" + min;   }
    if (("" + sec).length   == 1) { sec   = "0" + sec;   }

    return ("" + year + month + day + hour + min + sec);
}


/**
 * 자바스크립트 Date 객체를 Time 스트링으로 변환 parameter date: JavaScript Date Object
 */
function toDateString(date, dele) { // formatTime(date)
    var year  = date.getFullYear();
    var month = date.getMonth() + 1; // 1월=0,12월=11이므로 1 더함
    var day   = date.getDate();

    if (("" + month).length == 1) { month = "0" + month; }
    if (("" + day).length   == 1) { day   = "0" + day;   }

    return ("" + year + dele + month + dele + day);
}

function toDateStringNodele(date){
	var year  = date.getFullYear();
	var month = date.getMonth() + 1; // 1월=0,12월=11이므로 1 더함
	var day   = date.getDate();

	if (("" + month).length == 1) { month = "0" + month; }
	if (("" + day).length   == 1) { day   = "0" + day;   }

	return ("" + year + month + day);
}

/**
 * Time이 현재시각 이후(미래)인지 체크
 */
function isFutureTime(time) {
    return (toTimeObject(time) > new Date());
}

/**
 * Time이 현재시각 이전(과거)인지 체크
 */
function isPastTime(time) {
    return (toTimeObject(time) < new Date());
}

/**
 * 주어진 Time 과 y년 m월 d일 차이나는 Time을 리턴
 *
 * ex) var time = form.time.value; //'20000101' alert(shiftTime(time,0,0,-100)); =>
 * 2000/01/01 00:00 으로부터 100일 전 Time
 */
function shiftDate(time,y,m,d, dele) { // moveTime(time,y,m,d)
    var date = toDateObject(time);

    date.setFullYear(date.getFullYear() + y); // y년을 더함
    date.setMonth(date.getMonth() + m);       // m월을 더함
    date.setDate(date.getDate() + d);         // d일을 더함

    return toDateString(date, dele);
}

function shiftDateNodele(time,y,m,d){
	var date = toDateObject(time);

	date.setFullYear(date.getFullYear() + y); // y년을 더함
	date.setMonth(date.getMonth() + m);       // m월을 더함
	date.setDate(date.getDate() + d);         // d일을 더함

	return toDateStringNodele(date);

}

/**
 * 주어진 Time 과 y년 m월 d일 h시 차이나는 Time을 리턴
 *
 * ex) var time = form.time.value; //'20000101000'
 * alert(shiftTime(time,0,0,-100,0)); => 2000/01/01 00:00 으로부터 100일 전 Time
 */
function shiftTime(time,y,m,d,h) { // moveTime(time,y,m,d,h)
    var date = toTimeObject(time);

    date.setFullYear(date.getFullYear() + y); // y년을 더함
    date.setMonth(date.getMonth() + m);       // m월을 더함
    date.setDate(date.getDate() + d);         // d일을 더함
    date.setHours(date.getHours() + h);       // h시를 더함

    return toTimeString(date);
}

/**
 * 두 Time이 몇 개월 차이나는지 구함
 *
 * time1이 time2보다 크면(미래면) minus(-)
 */
function getMonthInterval(time1,time2) { // measureMonthInterval(time1,time2)
    var date1 = toTimeObject(time1);
    var date2 = toTimeObject(time2);

    var years  = date2.getFullYear() - date1.getFullYear();
    var months = date2.getMonth() - date1.getMonth();
    var days   = date2.getDate() - date1.getDate();

    return (years * 12 + months + (days >= 0 ? 0 : -1) );
}

/**
 * 두 Time이 며칠 차이나는지 구함 time1이 time2보다 크면(미래면) minus(-)
 */
function getDayInterval(time1,time2) {
    var date1 = toTimeObject(time1);
    var date2 = toTimeObject(time2);
    var day   = 1000 * 3600 * 24; // 24시간

    return parseInt((date2 - date1) / day, 10);
}

/**
 * 두 Time이 몇 시간 차이나는지 구함
 *
 * time1이 time2보다 크면(미래면) minus(-)
 */
function getHourInterval(time1,time2) {
    var date1 = toTimeObject(time1);
    var date2 = toTimeObject(time2);
    var hour  = 1000 * 3600; // 1시간

    return parseInt((date2 - date1) / hour, 10);
}

/**
 * 현재 시각을 Date 형식으로 리턴
 *
 */
function getCurrentDate(dele) {
    return toFormatString(toTimeString(new Date()), "-");
}

function getCurrentDateNodele(){
	return toFormatStringNodele(toTimeString(new Date()));
}

/**
 * 현재 시각을 Time 형식으로 리턴
 *
 */
function getCurrentTime() {
    return toTimeString(new Date());
}

/**
 * 현재 시각과 y년 m월 d일 h시 차이나는 Time을 리턴
 */
function getRelativeTime(y,m,d,h) {
/*
 * var date = new Date();
 *
 * date.setFullYear(date.getFullYear() + y); //y년을 더함
 * date.setMonth(date.getMonth() + m); //m월을 더함 date.setDate(date.getDate() +
 * d); //d일을 더함 date.setHours(date.getHours() + h); //h시를 더함
 *
 * return toTimeString(date);
 */
    return shiftTime(getCurrentTime(),y,m,d,h);
}

/**
 * 현재 年을 YYYY형식으로 리턴
 */
function getYear() {
/*
 * var now = new Date(); return now.getFullYear();
 */
    return getCurrentTime().substr(0,4);
}

/**
 * 현재 月을 MM형식으로 리턴
 */
function getMonth() {
/*
 * var now = new Date();
 *
 * var month = now.getMonth() + 1; // 1월=0,12월=11이므로 1 더함 if (("" +
 * month).length == 1) { month = "0" + month; }
 *
 * return month;
 */
    return getCurrentTime().substr(4,2);
}

/**
 * 현재 日을 DD형식으로 리턴
 *
 */
function getDay() {
/*
 * var now = new Date();
 *
 * var day = now.getDate(); if (("" + day).length == 1) { day = "0" + day; }
 *
 * return day;
 */
    return getCurrentTime().substr(6,2);
}

/**
 * 현재 時를 HH형식으로 리턴
 */
function getHour() {
/*
 * var now = new Date();
 *
 * var hour = now.getHours(); if (("" + hour).length == 1) { hour = "0" + hour; }
 *
 * return hour;
 */
    return getCurrentTime().substr(8,2);
}

/**
 * 오늘이 무슨 요일이야?
 *
 * ex) alert('오늘은 ' + getDayOfWeek() + '요일입니다.'); 특정 날짜의 요일을 구하려면? => 여러분이 직접
 * 만들어 보세요.
 */
function getDayOfWeek() {
    var now = new Date();

    var day = now.getDay(); // 일요일=0,월요일=1,...,토요일=6
    var week = new Array('일','월','화','수','목','금','토');

    return week[day];
}


/*
 * 윤달 포함 달별 일수 Return
 */
function daysPerMonth()
{
    var DOMonth  = new Array("31","28","31","30","31","30","31","31","30","31","30","31");
    var IDOMonth = new Array("31","29","31","30","31","30","31","31","30","31","30","31");

	if(arguments[1] == 0) arguments[1] = 12;

    if ( (arguments[0]%4) == 0 )
    {
        if ( (arguments[0]%100) == 0 && (arguments[0]%400) != 0 )
            return DOMonth[arguments[1]-1];
        return IDOMonth[arguments[1]-1];
    }
    else
        return DOMonth[arguments[1]-1];
}

/*
 * 화면의 오늘, 어제, 이번주, 지난주, 이번달, 지난달 기간 구하기
 */
function setDuration(obj, start, end) {

	var flag = obj.value;

	// 기간선택이면...
	if(flag == "CM00801") {
		start.value = "";
		end.value = "";
	}
	// 오늘이면...
	if(flag == "CM00802") {
		start.value = getCurrentDate("-");
		end.value = getCurrentDate("-");
	}
	// 어제이면...
	if(flag == "CM00803") {
		var tmpDate = getCurrentTime();
		start.value = shiftDate(tmpDate, 0, 0, -1, "-");
		end.value = shiftDate(tmpDate, 0, 0, -1, "-");
	}
	// 최근한주이면...
	if(flag == "CM00804") {
		start.value = shiftDate(getCurrentTime(), 0, 0, -7, "-");
		end.value = getCurrentDate("-");
	}
	// 이번주이면...
	if(flag == "CM00805") {
	    var now = new Date();
	    var day = now.getDay(); // 일요일=0,월요일=1,화요일=2,수요일=3,목요일=4,금요일=5,토요일=6

		start.value = shiftDate(getCurrentTime(), 0, 0, -1 * day, "-");
		end.value = shiftDate(getCurrentTime(), 0, 0, 6-day, "-");
	}
	// 지난주이면...
	if(flag == "CM00806") {
	    var now = new Date();
	    var day = now.getDay() + 7; // 일요일=0,월요일=1,화요일=2,수요일=3,목요일=4,금요일=5,토요일=6

		start.value = shiftDate(getCurrentTime(), 0, 0, -1 * day, "-");
		end.value = shiftDate(getCurrentTime(), 0, 0, 6-day, "-");
	}
	// 최근한달이면...
	// if(flag == "") {
	// start.value = shiftDate(getCurrentTime(), 0, -1, 0, "/");
	// end.value = getCurrentDate("/");
	// }
	// 이번달이면...
	if(flag == "CM00807") {
	    var now = new Date();
	    var day = now.getDate();
		var last_day = daysPerMonth(now.getYear(), now.getMonth()+1);

		start.value = shiftDate(getCurrentTime(), 0, 0, -1 * (day - 1), "-");
		end.value = shiftDate(getCurrentTime(), 0, 0, last_day-day, "-");
	}
	// 지난달이면...
	if(flag == "CM00808") {
	    var now = new Date();
	    var day = now.getDate();
		var last_day = daysPerMonth(now.getYear(), now.getMonth());

		start.value = shiftDate(getCurrentTime(), 0, -1, -1 * (day - 1), "-");
		end.value = shiftDate(getCurrentTime(), 0, -1, last_day-day, "-");
	}
	/** **************************************************** */
	// charles추가끝
}

/*
 * 기간 조회
 */
function setSearchDate(term) {
	var startDate;
	if(term == "0day"){
		startDate = shiftDate(getCurrentTime(), 0, 0, 0, "");
	}else if(term == "1day"){
		startDate = shiftDate(getCurrentTime(), 0, 0, -1, "");
	}else if(term == "3day"){
		startDate = shiftDate(getCurrentTime(), 0, 0, -2, "");
	}else if(term == "5day"){
		startDate = shiftDate(getCurrentTime(), 0, 0, -5, "");
	}else if(term == "7day"){
		startDate = shiftDate(getCurrentTime(), 0, 0, -6, "");
	}else if(term == "15day"){
		startDate = shiftDate(getCurrentTime(), 0, 0, -14, "");
	}else if(term == "1month"){
		startDate = shiftDate(getCurrentTime(), 0, -1, 0, "");
	}else if(term == "3month"){
		startDate = shiftDate(getCurrentTime(), 0, -3, 0, "");
	}else if(term == "6month"){
		startDate = shiftDate(getCurrentTime(), 0, -6, 0, "");
	}
	var endDate = toFormatString(getCurrentTime(), "");
	$("#startDate").val(startDate);
	$("#endDate").val(endDate);
	$("#term").val(term);
}