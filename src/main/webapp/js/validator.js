/**
 * @author Richie (richie@uzen.net)
 *
 */
var Validator = {
	validate: function(form, title /* optional */) {
		this.currentForm = form;

		var sucess = true;

		$(form).find(":input[validate]").each(function(i) {
			var j = $(this);
			var label = $("label[for='" + j.attr("id") + "']"); // added (11/05/21)
			var loc = j.attr("locale"); // added (11/05/22)
			// added (11/07/04). loc fix to 'en'.
			loc = "en";
			var vrs = j.attr("validate");
			var rs = Validator.parseInlineVrs(vrs);
			for (var n in rs) {

				if (n != "label") {
					var param = rs[n];
					var result = Validator.rules[n].check.call(Validator, j.val(), this, param);
					if (result != true) {
						//alert("\"" + label.text() + "\" : " + Validator.formatMessage(Validator.rules[n].msg, param));
						//alert("\"" + label.text() + "\" : " + Validator.formatMessage(Validator.locales[loc].messages[n], param)); // modified (11/05/22)
						//open_alert("Alert", Validator.formatMessage(Validator.locales[loc].messages[n], param) + ":" +  "\"" + label.text() + "\""); // modified (11/06/11)
						if (title == null) {	// added. 2011.07.05
							title = "Alert";
						}
						open_alert(title, Validator.formatMessage(Validator.locales[loc].messages[n], param) + ":" +  "\"" + label.text() + "\"", j.attr("id") ); // modified (11/06/29)
						sucess = false;
						//j.focus(); // deleted (11/06/29)
						return false;
					}
				}
			}
			return true;
		});

		return sucess;
	},

	validateField: function(element, vrs /* Optional */) {
	},

	parseInlineVrs: function(vrs) {
		var rs = new Object();

		var ss = vrs.split(";");
		for (var i = 0; i < ss.length; ++i) {
			var r = $.trim(ss[i]);
			if (r) {
				var rr = r.split(":");

				var ruleName = $.trim(rr[0]);
				var ruleValue = $.trim(rr[1]);

				if (Validator.rules[ruleName] && Validator.rules[ruleName].argc > 1) {
					var ruleValueCount = Validator.rules[ruleName].argc;
					var multiRuleValue = ruleValue.split(new RegExp(" +", "g"), ruleValueCount);
					for (var j = 0; j < multiRuleValue.length; ++j) {
						multiRuleValue[j] = eval(multiRuleValue[j]);
					}
					rs[ruleName] = multiRuleValue;
				}
				else {
					try {
						rs[ruleName] = eval(ruleValue);
					}
					catch (e) {
						rs[ruleName] = ruleValue;
					}
				}
			}
		}

		return rs;
	},

	parseVrs: function(vrs) {
	},

	formatMessage: function(format, param) {
		if (arguments.length <= 1) {
			return format;
		}

		var s = format;
		if (param instanceof Array) {
			for (var i = 0; i < param.length; ++i) {
				s = s.replace(new RegExp("\\{" + i + "\\}", "g"), param[i]);
			}
		}
		else {
			for (var i = 1; i < arguments.length; ++i) {
				s = s.replace(new RegExp("\\{" + (i - 1) + "\\}", "g"), arguments[i]);
			}
		}

		return s;
	},

	getLength: function(value, element) {
		switch( element.nodeName.toLowerCase() ) {
		case 'select':
			return $("option:selected", element).length;
		case 'input':
			if( this.checkable( element) )
				return Validator.findByName(element.name).filter(':checked').length;
		}
		return value.length;
	},

	getByteLength: function(value, element) {
		switch( element.nodeName.toLowerCase() ) {
		case 'select':
			return $("option:selected", element).length;
		case 'input':
			if( Validator.checkable( element) )
				return Validator.findByName(element.name).filter(':checked').length;
		}
		return value.length;
	},

	depend: function(param, element) {
//		alert(param + " : " + typeof param);
		return Validator.dependTypes[typeof param]
			? Validator.dependTypes[typeof param](param, element)
			: true;
	},

	dependTypes: {
		"boolean": function(param, element) {
			return param;
		},
		"number": function(param, element) {
			return param;
		},
		"string": function(param, element) {
			return !!$(param, element.form).length;
		},
		"function": function(param, element) {
			return param(element);
		}
	},

	checkable: function( element ) {
		return /radio|checkbox/i.test(element.type);
	},

	findByName: function( name ) {
		// select by name and filter by form for performance over form.find("[name=...]")
		var form = Validator.currentForm;
		return $(document.getElementsByName(name)).map(function(index, element) {
			return element.form == form && element.name == name && element  || null;
		});
	},

	optional: function(element) {
		return !Validator.rules.required.check.call(Validator, $.trim(element.value), element);
	},

	rules: {
		required: {
			argc: 1,
			//msg: "필수항목입니다.", // deleted (11/05/22)
			check: function(value, element, param) {
				// check if dependency is met
				if ( !Validator.depend(param, element) ) {
					return false;
				}

				switch( element.nodeName.toLowerCase() ) {
				case 'select':
					// could be an array for select-multiple or a string, both are fine this way
					var val = $(element).val();
					return val && val.length > 0;
				case 'input':
					if ( Validator.checkable(element) )
						return Validator.getLength(value, element) > 0;
				default:
					return $.trim(value).length > 0;
				}
			}
		},

		email: {
			argc: 1,
			// msg: "올바른 이메일 형식으로 입력하세요.", // deleted (11/05/22)
			check: function(value, element, param) {
				return Validator.optional(element) || /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i.test(value);
			}
		},

		url: {
			argc: 1,
			//msg: "올바른 주소 형식으로 입력하세요.", // deleted (11/05/22)
			check: function(value, element, param) {
				return Validator.optional(element) || /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(value);
			}
		},

		date: {
			argc: 1,
			//msg: "올바른 날짜 형식으로 입력하세요.", // deleted (11/05/22)
			check: function(value, element, param) {
				return Validator.optional(element) || /^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/.test(value);
			}
		},

		specialCharacter: {
			argc: 1,
			//msg: "공백과 '-'와'_' 이외의특수문자는 입력할 수 없습니다.", // deleted (13/04/25)
			check: function(value, element, param) {
				return Validator.optional(element) || /^[a-zA-Z0-9-_]+$/.test(value);
			}
		},

		contact: {
			argc: 1,
			//msg: "올바른 연락처 형식으로 입력하세요.", // deleted (13/04/25)
			check: function(value, element, param) {
				return Validator.optional(element) || /^[0-9-_]+$/.test(value);
			}
		},

		number: {
			argc: 1,
			//msg: "올바른 숫자 형식으로 입력하세요.", // deleted (11/05/22)
			check: function(value, element, param) {
				return Validator.optional(element) || /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(value);
			}
		},

		digit: {
			argc: 1,
			//msg: "숫자만 입력하세요.", // deleted (11/05/22)
			check: function(value, element, param) {
				return Validator.optional(element) || /^\d+$/.test(value);
			}
		},

		equal: {
			argc: 1,
			//msg: "{0}와 같은 값을 입력하세요.", // deleted (11/05/22)
			check: function(value, element, param) {
				return Validator.findByName(param).val() == value;
			}
		},

		min: {
			argc: 1,
			//msg: "{0} 이상의 값을 입력하세요.", // deleted (11/05/22)
			check: function(value, element, param) {
				return Validator.optional(element) || value >= param;
			}
		},

		max: {
			argc: 1,
			//msg: "{0} 이하의 값을 입력하세요.", // deleted (11/05/22)
			check: function(value, element, param) {
				return Validator.optional(element) || value <= param;
			}
		},

		range: {
			argc: 2,
			//msg: "{0} ~ {1} 사이의 값을 입력하세요.", // deleted (11/05/22)
			check: function(value, element, param) {
				return Validator.optional(element) || ( value >= param[0] && value <= param[1] );
			}
		},

		minlength: {
			argc: 1,
			//msg: "{0}자 이상의 값을 입력하세요.", // deleted (11/05/22)
			check: function(value, element, param) {
				return Validator.optional(element) || Validator.getLength($.trim(value), element) >= param;
			}
		},

		maxlength: {
			argc: 1,
			//msg: "{0}자 이하의 값을 입력하세요.", // deleted (11/05/22)
			check: function(value, element, param) {
				return Validator.optional(element) || Validator.getLength($.trim(value), element) <= param;
			}
		},

		rangelength: {
			argc: 2,
			//msg: "{0}자 ~ {1}자 사이의 값을 입력하세요.", // deleted (11/05/22)
			check: function(value, element, param) {
				var length = Validator.getLength($.trim(value), element);
				return Validator.optional(element) || ( length >= param[0] && length <= param[1] );
			}
		},

		minbytes: {
			argc: 1,
			//msg: "{0}바이트 이상의 값을 입력하세요.", // deleted (11/05/22)
			check: function(value, element, param) {
				return Validator.optional(element) || Validator.getByteLength($.trim(value), element) >= param;
			}
		},

		maxbytes: {
			argc: 1,
			//msg: "{0}바이트 이하의 값을 입력하세요.", // deleted (11/05/22)
			check: function(value, element, param) {
				return Validator.optional(element) || Validator.getByteLength($.trim(value), element) <= param;
			}
		},

		maxbytesNewComment: {
			argc: 1,
			//msg: "{0}바이트 이하의 값을 입력하세요.", // deleted (11/05/22)
			check: function(value, element, param) {
				return Validator.optional(element) || Validator.getByteLength($.trim(value), element) <= param;
			}
		},

		rangebytes: {
			argc: 2,
			//msg: "{0}바이트 ~ {1}바이트 사이의 값을 입력하세요.", // deleted (11/05/22)
			check: function(value, element, param) {
				var length = Validator.getByteLength($.trim(value), element);
				return Validator.optional(element) || ( length >= param[0] && length <= param[1] );
			}
		},

		xss: { // added (11/06/28)
			argc: 1,
			check: function(value, element, param) {
				if ( !Validator.checkable(element) ) {
					var val = $(element).val();
					//var re = /\<|\>|\"|\'|\%|\;|\(|\)|\&|\+|alert+|(\<|\<\/)script+|javascript+|document\.+|\.cookie+|xss\:+|\:expression|style\=+|background(\:|\.)+|\.\/+|\.\.\/+/g;
					//var re = /\<|\>|\"|\%|\&|\+|alert+|(\<|\<\/)script+|javascript+|document\.+|\.cookie+|xss\:+|\:expression|style\=+|background(\:|\.)+|\.\/+|\.\.\/+/g;
					//var re = /\<|\>|\%|\+|alert *\(+|(\<|\<\/)script+|javascript+|document\.+|\.cookie+|xss\:+|\:expression|style\=+|background(\:|\.)+|\.\/+|\.\.\/+/g;
					var re = /\<|\>|alert *\(+|(\<|\<\/)script+|javascript+|document\.+|\.cookie+|xss\:+|\:expression|style\=+|background(\:|\.)+|\.\/+|\.\.\/+/g;
					if (val.match(re) ) {
					    return false;
					} else {
						return true;
					}
				}
			}
		},

		restrictChar: { // added (12/07/16)
			argc: 1,
			check: function(value, element, param) {
				if ( !Validator.checkable(element) ) {
					var vals = param.split("|");
					for (var i = 0; i < vals.length; i++) {
						if (value.indexOf(vals[i]) > -1) {
							return false;
						}
					}
					return true;
				}
			}
		},

		restrictSingle: { // added (12/07/16)
			argc: 1,
			check: function(value, element, param) {
				return Validator.rules.restrictChar.check.call(Validator, value, this, "'");
			}
		},

		restrictDouble: { // added (12/07/16)
			argc: 1,
			check: function(value, element, param) {
				return Validator.rules.restrictChar.check.call(Validator, value, this, '"');
			}
		},

		whitelist : { // added (13/01/07)
			argc: 1,
			check : function(value, element, param) {
				if ( !Validator.checkable(element) ) {
					var val = $(element).val();
					var re = new RegExp(param, 'g');
					var matchStr = new String(re.exec(val));
					if (matchStr.length === val.length) {
						//alert(val + " : true");
					    return true;
					} else {
						//alert(val + " : false");
						return false;
					}
				}
			}
		},


		blacklist : { // added (13/01/07)
			argc: 1,
			check : function(value, elements, param) {
				if ( !Validator.checkable(element) ) {
					var val = $(element).val();
					var re = new RegExp(param, 'g');
					if (val.match(re) ) {
						return false;
					} else {
						return true;
					}
				}
			}
		}
	},

	locales: {
		en: {
			messages: {
				required: "You must enter",
				email: "Please input by the right email format",
				url: "Please input by the right url format",
				date: "Please input by the right date format",
				number: "Please input by the right number format",
				digit: "Please input only a number",
				equal: "Please input the same value as {0}",
				min: "Please input the value beyond {0}",
				max: "Please input the value following {0}",
				range: "Please input the value between {0} and {1}",
				minlength: "Please input the characters beyond {0}",
				maxlength: "Please input the characters following {0}",
				rangelength: "Please input the characters between {0} and {1}",
				minbytes: "Please input the bytes beyond {0}",
				maxbytes: "Please input the bytes following {0}",
				maxbytesNewComment: "Maximum number of characters allowed is {0}",
				rangebytes: "Please input the bytes between {0} and {1}",
				xss: "A special character can not be input",
				restrictChar: "A special character({0}) can not be input",
				restrictSingle: "A single or double quotation can not be input",
				restrictDouble: "A single or double quotation can not be input",
				specialCharacter: "A special character or space can not be input except Underline( _ ) and Dash( - ) ",
				whitelist: "A special character or space can not be input except Underline( _ ) and Dash( - ) ",
				contact: "Please input by the right contact number format",
				blacklist: "Invalid value."
			}
		},

		ko: {
			messages: {
				required: "필수항목입니다.",
				email: "올바른 이메일 형식으로 입력하세요.",
				url: "올바른 주소 형식으로 입력하세요.",
				date: "올바른 날짜 형식으로 입력하세요.",
				number: "올바른 숫자 형식으로 입력하세요.",
				digit: "숫자만 입력하세요.",
				equal: "{0}와 같은 값을 입력하세요.",
				min: "{0} 이상의 값을 입력하세요.",
				max: "{0} 이하의 값을 입력하세요.",
				range: "{0} ~ {1} 사이의 값을 입력하세요.",
				minlength: "{0}자 이상의 값을 입력하세요.",
				maxlength: "{0}자 이하의 값을 입력하세요.",
				rangelength: "{0}자 ~ {1}자 사이의 값을 입력하세요.",
				minbytes: "{0}바이트 이상의 값을 입력하세요.",
				maxbytes: "{0}바이트 이하의 값을 입력하세요.",
				maxbytesNewComment: "{0}바이트 이하의 값을 입력하세요.",
				rangebytes: "{0}바이트 ~ {1}바이트 사이의 값을 입력하세요.",
				xss: "특수문자는 입력할 수 없습니다.",
				restrictChar: "특수문자({0})는 입력할 수 없습니다.",
				restrictSingle: "단일 또는 이중 따옴표는 입력할 수 없습니다.",
				restrictDouble: "단일 또는 이중 따옴표는 입력할 수 없습니다.",
				specialCharacter: "공백과 '-'와'_' 이외의특수문자는 입력할 수 없습니다.",
				whitelist: "공백과 '-'와'_' 이외의특수문자는 입력할 수 없습니다.",
				contact: "올바른 연락처 형식으로 입력하세요.",
				blacklist: "잘못된 입력값 입니다."
			}
		}
	}
};


/**
 * 지정된 곳 외의 Enter 이벤트 방지.
 * 사용법
 * document.onkeydown = blockEnterKey;
 *
 * @return
 */
function blockEnterKey() {
    try {
        if (!e) var e = window.event;

        if (document.all) var key = e.keyCode;
        else var key = e.which;

        if( key == 13 ) {
            //var tag = e.srcElement ? e.srcElement.tagName : e.target.nodeName ;
            //var tagId = e.srcElement ? e.srcElement.id : "" ;
            return false;
        }
    } catch(e) { return true; }
}

function handlerNumeral(e) {

	var keyCode = ( window.event ) ? window.event.keyCode : e.which;

	if( 48 <= keyCode && keyCode <= 57 || 96 <= keyCode && keyCode <= 105 ||
    	 keyCode == 8 || keyCode == 9 || keyCode == 17 || keyCode == 18 ||
     	keyCode == 37 || keyCode == 39 || keyCode == 46 || keyCode == 110 || keyCode == 190)
     	return;
 	else
	{
		if( window.event )
			window.event.returnValue = false;
		else
		{
			e.preventDefault();
			e.stopPropagation();
		}
	}
}

/**
 * 비밀번호 유효성 검사(비밀번호 변경 시_닷컴화 규칙)
 * 1. 6자 이상, 15자 이하
 * 2. 영문 대/소문자, 숫자, 특수기호 중 2가지 이상 조합
 * 3. 동일문자 3회이상 반복 불가
 * 4. 키보드 상 연속문자열 4자 이상 사용 불가
 * 5. 사용자아이디와 연속 3문자 이상 중복 불가
 * 6. 연속된 숫자/문자 3자 이상 사용불가
 */
function validatePassword(password, userId) {
	//숫자/문자의 순서대로 3자 이상 사용금지
	var strights = ['012345678901', '987654321098', 'abcdefghijklmnopqrstuvwxyzab', 'zyxwvutsrqponmlkjihgfedcbazy'];
	//연속된 키보드 조합
	var keypads = [
		       		'`1234567890-=', 	'=-0987654321`', 	'~!@#$%^&*()_+', 	'+_)(*&^%$#@!~',
		       		'qwertyuiop[]\\', 	'\\][poiuytrewq', 	'QWERTYUIOP{}|',	'|}{POIUYTREWQ',
		       		'asdfghjkl;\'', 	'\';lkjhgfdsa', 	'ASDFGHJKL:"', 		'":LKJHGFDSA',
		       		'zxcvbnm,./', 		'/.,mnbvcxz', 		'ZXCVBNM<>?', 		'?><MNBVCXZ'
		       		];

	var getPattern = function(str, casesensitive) {
		//정규식 생성전에 예약어를 escape 시킨다.
		var reserves = ['\\', '^', '$', '.', '[', ']', '{', '}', '*', '+', '?', '(', ')', '|'];

		$.each(reserves, function(index, reserve){
			var pattern = new RegExp('\\' + reserve, 'g');
			if (pattern.test(str)) {
				str = str.replace(pattern, '\\' + reserve);
			}
		});
		var pattern = null;
		if (casesensitive == false) {
			pattern = new RegExp(str, 'i');
		} else {
			pattern = new RegExp(str);
		}
		return pattern;
	};

	// 1. 6자 이상, 15자 이하
	if (password.match(/^.{6,15}$/g) == null) {
		alert("패스워드는 6자리 이상 15자리 미만으로 입력하세요.");
		return false;
	}

	// 2. 영문 대/소문자, 숫자, 특수기호 중 2가지 이상 조합
	var valid_count = 0;
	if (password.match(/[a-z]/) != null) {
		valid_count++;
	}
	if (password.match(/[A-Z]/) != null) {
		valid_count++;
	}
	if (password.match(/[0-9]/) != null) {
		valid_count++;
	}
	if (password.match(/\W/) != null) {
		valid_count++;
	}
	if(valid_count < 2) {
		alert("패스워드는 영문대문자/영문소문자/숫자/특수기호중 2가지 이상을 혼합하여 입력하세요.");
		return false;
	}

	for (var i = 0 ; i < password.length ; i++) {
		if (password.charAt(i+1) != '' && password.charAt(i+2) != '') {
			//3. 동일문자 3회이상 반복 불가
			if (password.charCodeAt(i) == password.charCodeAt(i+1) && password.charCodeAt(i+1) == password.charCodeAt(i+2)) {
				alert("동일문자를 연속3회이상 반복 하실 수 없습니다.");
				return false;
			}
			var str = password.charAt(i)+''+password.charAt(i+1)+''+password.charAt(i+2);

			var pattern = getPattern(str, false);

			//6. 연속된 숫자/문자 3자 이상 사용불가
			for (var j = 0 ; j < strights.length ; j++) {
				if (pattern.exec(strights[j]) != null) {
					alert("연속된 알파벳/숫자 조합을 사용할 수 없습니다.");
					return false;
				}
			}

			//5. 사용자아이디와 연속 3문자 이상 중복 불가
			if (pattern.exec(userId) != null) {
				alert("아이디와 3자 이상 중복될 수 없습니다.");
				return false;
			}
		}
	}

	//4. 키보드 상 연속문자열 4자 이상 사용 불가
	for (var i = 0 ; i < password.length ; i++) {
		if (password.charAt(i+1) != '' && password.charAt(i+2) != '' && password.charAt(i+3) != '') {
			var str = password.charAt(i)+''+password.charAt(i+1)+''+password.charAt(i+2) +''+ password.charAt(i+3);

			var pattern = getPattern(str);

			for (var j = 0 ; j < keypads.length ; j++) {
				if (pattern.exec(keypads[j]) != null) {
					alert("연속된 키보드 조합을 사용할 수 없습니다.");
					return false;
				}
			}
		}
	}

	return true;
}