var isIE = (navigator.userAgent.indexOf("MSIE") > -1)?true:false;      
var isIE6 = (navigator.appVersion.indexOf("MSIE 6.0") > -1)?true:false;
var isIE7 = (navigator.appVersion.indexOf("MSIE 7.0") > -1)?true:false;
var isChrome = (navigator.userAgent.indexOf("Chrome") > -1)?true:false;
var isFireFox = (navigator.userAgent.indexOf("FireFox") > -1)?true:false;

var Util = {
	NextFunction : null,
	CheckBoxes : new Array(),
	DefaultKeyword : '',
	Event : null,
	Continue : function() {
		if(this.NextFunction != null)	eval(this.NextFunction);
	},
	CaptureEvent : function(e) {
		this.Event = e;
	},
	IsLoggedIn : function() {
		var res = $.ajax({ 
			url : '/Member/LoginCheck.jsp', 
			type : 'get',
			async: false
		}).responseText;
		
		if(res.trim() == 'true') return true;
		else return false;
	},
	GetValue : function(id) {
		var txt = document.getElementById(id);

		if(txt == null) txt = '';
		else txt = txt.value;
		txt = txt.trim();
		
		return txt;
	},
	GetNamedValue : function(name) {
		var arr = document.getElementsByName(name);
		var value = '';
		
		for(i=0;i<arr.length;i++){
			if(arr[i].checked) { 
				value = arr[i].value;
				break;
			}
		}
		return value;
	},
	IsNullOrEmpty : function(txt) {
		if(txt != null) txt = txt.trim();
		if(txt == null || txt == '') return true;
		else 
			return false;
	},
  CheckObject : function(id) {
      if(document.getElementById('CHK-' + id).checked) {
          this.CheckBoxes.push(id);
      }
      else {
          for(i=0;i<this.CheckBoxes.length;i++) {
              if(this.CheckBoxes[i] == id) { 
                  this.CheckBoxes.splice(i,1);
                  break;
              }
          }
      }
  },
  CheckObject2 : function(id) {
      if(document.getElementById('CHK2-' + id).checked) {
          this.CheckBoxes.push(id);
      }
      else {
          for(i=0;i<this.CheckBoxes.length;i++) {
              if(this.CheckBoxes[i] == id) { 
                  this.CheckBoxes.splice(i,1);
                  break;
              }
          }
      }
  },
  CheckAll : function(objForm) {
	  var objForm = obj.form;
	  //alert(document.getElementById('CheckAll').checked);
      if(document.getElementById('CheckAll').checked) { 
      		this.CheckBoxes = new Array();
          for(i=0; i<document.getElementByName('checkbox').length; i++) { 
              this.CheckBoxes.push(objForm.elements[i].value);
          }
      }
      else 
    	  this.CheckBoxes = new Array();
  },
  CheckAll2 : function(obj) {
  	var objForm = obj.form;

    if(obj.checked) { 
    		this.CheckBoxes = new Array();
        for(i=0; i<objForm.length; i++) { 
            if(objForm.elements[i].type == 'checkbox' && objForm.elements[i].name == 'checkbox' && objForm.elements[i] != obj) {
            		objForm.elements[i].checked = true;
                this.CheckBoxes.push(objForm.elements[i].value);
            }
        }
    }
    else 
        this.UnCheckAll();
  },
  CheckAll3 : function(obj) {
	  	var objForm = obj.form;

	    if(obj.checked) { 
	    		this.CheckBoxes = new Array();
	        for(i=0; i<objForm.length; i++) { 
	            if(objForm.elements[i].type == 'checkbox' && objForm.elements[i].name == 'checkbox2' && objForm.elements[i] != obj) {
	            		objForm.elements[i].checked = true;
	                this.CheckBoxes.push(objForm.elements[i].value);
	            }
	        }
	    }
	    else 
	        this.UnCheckAll();
	  },
	UnCheckAll : function() {
	    for(i=0;i<this.CheckBoxes.length;i++) {
	        document.getElementById('CHK2-' + this.CheckBoxes[i]).checked = false;
	    }
	    this.CheckBoxes = new Array();
	},
	GetURLWithParameter : function(url, tag, value) {
		url = url.replace(/\#$/,''); 
		url = url + '?';
		var u = url.split('?');
		
		u[1] = '&' + u[1];
		var rexp = new RegExp('[\\&]' + tag + '=[^\\&]*','gi');
		u[1] = u[1].replace(rexp,'');
		if(value != '')
			u[1] = u[1] + '&' + tag + '=' + value;
		u[1] = u[1].replace(/^\&+/g, '');
		
		if(u[1] == '') url = u[0];
		else
			url = u[0] + '?' + u[1];
		return url;
	},
	ChangeURLParameter : function(tag, value) {
		document.location.href = this.GetURLWithParameter(document.location.href, tag, value);
	},
	ChangeURLParameter1 : function(tag, value) {
		var url = this.GetURLWithParameter(document.location.href, 'page', '');
		document.location.href = this.GetURLWithParameter(url, tag, value);
	},	
	RefreshURLParameter : function(tag, value) {
		var url = Util.GetURLWithParameter(document.location.href.split('?')[0], tag, value);
		document.location.href = url;
	},
	ResizeWin : function(win,w,h) { // IE 전용
	  var winBody = win.document.body; 	 
	  var marginHeight = parseInt(winBody.topMargin) + parseInt(winBody.bottomMargin); 
	  var marginWidth = parseInt(winBody.leftMargin) + parseInt(winBody.rightMargin); 
	  var wid = winBody.scrollWidth + (winBody.offsetWidth - winBody.clientWidth) + marginWidth + w;
	  var hei = winBody.scrollHeight + (winBody.offsetHeight - winBody.clientHeight) + marginHeight + h;

	  win.resizeTo(wid, hei); 
	},
	ResizeWindow : function() { 
	  document.body.style.overflow = 'hidden';

	  var _width = document.body.offsetWidth + 15;
	  var _height = document.body.offsetHeight + 85;
	  
	  if(isIE) {
		  _width = document.body.scrollWidth + (document.body.offsetWidth - document.body.clientWidth) + 6;
		  //if(isIE7) _height = document.body.scrollHeight + (document.body.offsetHeight - document.body.clientHeight) + 76;
		  //else
			 	_height = document.body.scrollHeight + (document.body.offsetHeight - document.body.clientHeight) + 54;  
	  }
	  else if(isChrome) {
	  	_width -= 5;
	  	_height -= 25;
	  }

	  window.resizeTo(_width, _height);
	},	
	Search : function() { // 객체 아이디 값이 일치하는 경우만
		var keyword = $('#SearchKeyword').val();
		var type = $('#SearchType').val();
		
		var link = document.location.href;
		link = Util.GetURLWithParameter(link,'page',1);
		link = Util.GetURLWithParameter(link,'stype',type);
		link = Util.GetURLWithParameter(link,'keyword',keyword);
		
		document.location.href = link;
	},
	GetBytes : function(str) { // 문자열 바이트수 반환
		var _byte = 0;

		if(str.length != 0) {
			for (var i=0; i < str.length; i++) {
				var str2 = str.charAt(i);
		
	      if(escape(str2).length > 4) _byte += 2;
	      else _byte++;
			}
		}
		return _byte;
	},
	SetCookie : function(name, value, expiredays) {
		var now = new Date();
		now.setTime(now.getTime() + expiredays*1000*60*60*24);
		document.cookie = name + '=' + escape(value) + '; path=/; expires =' + now.toGMTString() + ';';
	},
	GetCookie : function(name) {
		var arr = document.cookie.split(';');
		
		for(var i=0;i<arr.length;i++) {
			var param = arr[i].split('=');
			
			if(param.length == 2) {
				if(param[0].trim() == name) return param[1].trim();
			}
		}
		
		return '';
	},
	SetDefaultInput : function(id, d) {
		var obj = $('#' + id);
		this.DefaultKeyword = d;
		
		if(obj.val() == '') obj.val(d);
		
		obj.bind('click', function(e) {
			if(obj.val() == d) obj.val('');
		});
		
		obj.bind('blur', function(e) {
			if(Util.IsNullOrEmpty(obj.val())) obj.val(d);
		});
	},
	Overlay : function(opct) {
		try {
			var div = document.getElementById('OVERLAY');
			var opct100 = opct;
			if(opct100 > 0) opct100 = parseFloat(opct100/100);
			var h = 0;
	
			if(div == null) {
				div = document.createElement('div');
				div.id = 'OVERLAY';
				div.style.position = 'absolute';
				div.style.backgroundColor = '#000000';
				div.style.zIndex = 999;
				div.style.top = '0px';
				div.style.left = '0px';
				div.style.width = '100%';
//alert(document.body.scrollHeight+','+window.innerHeight+','+document.body.offsetHeight+','+document.body.clientHeight);
				/*if(window.innerHeight) h = window.innerHeight;
				else {
					if(document.body.scrollHeight > document.body.offsetHeight)
						h = document.body.scrollHeight;
					else
						h = document.body.offsetHeight;
				}*/
				h= document.body.scrollHeight+$(document).scrollTop();
				if(isFireFox) div.style.MozOpacity = opct100;
				if(isIE) div.style.filter = 'alpha(opacity='+opct+')';
				div.style.opacity = opct100;
		  	document.body.appendChild(div);
		  	$('#'+div.id).css('height',h);
			}
		} catch(err) {
		}
	},
	Loading : function() {
		this.Overlay(0);

		var div2 = document.createElement("div");
		div2.id = 'PROCESS';
		div2.style.position = 'absolute';
		div2.style.zIndex = 9999;
		div2.style.top = ((document.body.scrollHeight + $(document).scrollTop())/2 - 100) + 'px';
		div2.style.left = '45%';
  		div2.innerHTML = '<img src="/Images/conversion.gif" alt="로딩" />';
  	
  	document.body.appendChild(div2);
	},
	Done : function() { 
		var div1 = document.getElementById('OVERLAY');
		var div2 = document.getElementById('PROCESS');
		
		if(div1 != null) document.body.removeChild(div1);
		if(div2 != null) document.body.removeChild(div2);
	},
	Sending : function() {
		this.Overlay(20);
		
		var div2 = document.createElement("div");
		div2.id = 'PROCESS';
		div2.style.position = 'absolute';
		div2.style.zIndex = 9999;
		div2.style.top = ((document.body.scrollHeight + $(document).scrollTop())/2 - 100) + 'px';
		div2.style.left = '45%';
  	div2.innerHTML = '<img src="/Images/sending.gif"/>';
  	
  	document.body.appendChild(div2);
	},
	Converting : function() {
		this.Overlay(20);
	
		var div2 = document.createElement("div");
		div2.id = 'PROCESS';
		div2.style.position = 'absolute';
		div2.style.zIndex = 9999;
		//div2.style.top = ((document.body.scrollHeight + document.body.scrollTop)/2 - 100) + 'px';
		div2.style.top = ((document.body.scrollHeight + $(document).scrollTop())/2 - 100) + 'px';
		div2.style.left = '45%';
  	div2.innerHTML = '<img src="/Images/conversion.gif"/>';

  	document.body.appendChild(div2);		
	},
	Saving : function() { 
		this.Overlay(1);
		
		var div2 = document.createElement("div");
		div2.id = 'PROCESS';
		div2.style.position = 'absolute';
		div2.style.zIndex = 9999;
		div2.style.top = ((document.body.scrollHeight + $(document).scrollTop())/2 - 100) + 'px';
		div2.style.left = '50%';
		div2.style.marginLeft = '-85px';
  	div2.innerHTML = '<img src="/Images/save.gif"/>';
  	
  	document.body.appendChild(div2);
	},
/* -- Util.Done(); 으로 변경요 */
	Sent : function() {
		var Overlay = document.getElementById('OVERLAY');
		var Sending = document.getElementById('PROCESS');
		
  	if(Overlay != null) document.body.removeChild(Overlay);
  	if(Sending != null) document.body.removeChild(Sending);		
	},
	Converted : function() { 
		var div1 = document.getElementById('OVERLAY');
		var div2 = document.getElementById('PROCESS');
		
		if(div1 != null) document.body.removeChild(div1);
		if(div2 != null) document.body.removeChild(div2);
	},
	Saved : function() {
		var div1 = document.getElementById('OVERLAY');
		var div2 = document.getElementById('PROCESS');
		
		if(div1 != null) document.body.removeChild(div1);
		if(div2 != null) document.body.removeChild(div2);
	},	
/* --  */
	HTMLEncode : function(html) {
		var div = document.createElement('div');
		div.innerText = html;
		return div.innerHTML;		
	}, 
	HTMLDecode : function(html) {
		var div = document.createElement('div');
		div.innerHTML = html;
		return div.innerText;
	},
	TextDecode : function(html) {
		html = html.replace('&lt;','<').replace('&gt;','>').replace('&amp','&');
		return html;
	},
	FormattedDate : function() { // yyyyMMddHHmmssSSS 형식으로 반환
		var now = new Date();
		var year = now.getFullYear();
		var month = (100 + now.getMonth() + 1).toString().substring(1);
		var day = (100 + now.getDate()).toString().substring(1);
		var hour = (100 + now.getHours()).toString().substring(1);
		var minute = (100 + now.getMinutes()).toString().substring(1);
		var second = (100 + now.getSeconds()).toString().substring(1);
		var millisecond = (1000 + now.getMilliseconds()).toString().substring(1);
		
		var fdate = year + month + day + hour + minute + second + millisecond;
		return(fdate);
	},
	CutStringByte : function(str,limit){
		var tmpStr = str;
		var byte_count = 0;
		var len = str.length;
		
		for(var i=0;i<len;i++) {
		 	byte_count += this.CharByte(str.charAt(i)); 
			if(byte_count > limit) break;

			tmpStr = str.substr(0,i+1);
		}

		return tmpStr;
	},
	CharByte : function(chr){
		if(escape(chr).length > 4) return 2;
		else return 1;
	},
	StringBytes : function(str) { // 문자열의 길이 바이트값으로 리턴
		var bytes = 0;

		for ( var i = 0; i < str.length; i++ ) {
			bytes += this.CharByte(str.charAt(i));
		}

		return bytes;
	},
	SetBytesLimit : function(obj,limit) {
		var content = obj.value;
		var len = Util.GetBytes(content);

		if(len > limit) {
			obj.blur();
			obj.value = this.CutStringByte(content,limit);
		 	this.SetBytesLimit(obj,limit);
			obj.focus();
		}
	},
	Insert2Textarea : function(obj,txt) {
		obj.focus();

		if(obj.value.length > 0) {
	    if(document.selection) { // IE
	    	document.selection.createRange().text = txt;
	 		} else if(obj.selectionStart) { 
				var start = obj.selectionStart;
				var end   = obj.selectionEnd;
	
				obj.value = obj.value.substr(0, start) + txt + obj.value.substr(end, obj.value.length);
	  	}
  	} else obj.value = txt;
	},
	CheckTextLen : function(obj, maxLen, outputId) {
		var len = this.StringBytes(obj.value.replace(/\r/g, ""));

		if(len > maxLen) {
			alert("입력값은 " + maxLen + " 바이트를 넘을 수 없습니다.");
			obj.value = this.CutStringByte(obj.value, maxLen);
			len = this.StringBytes(obj.value);
		}

		var obj2 = document.getElementById(outputId);
		obj2.innerHTML = "<Strong>" + len + "</Strong>/" + maxLen + " bytes";
	}
}


var CheckBox = {
	Values : new Array(),
	OnDrag : false,
	Form : null,
	Init : function(frm) {
		if(frm == null || frm.id == null) return;
		this.Form = frm;
		
		$('input[type=checkbox]').bind('mousedown', function() {
			CheckBox.OnDrag = true;
			$(this).attr('checked', !$(this).attr('checked'));
		});
		$('input[type=checkbox]').bind('mouseover', function() {
			if(CheckBox.OnDrag) {
				$(this).attr('checked', !$(this).attr('checked'));
			}
		});
		$('input[type=checkbox]').bind('click', function() {
			$(this).attr('checked', !$(this).attr('checked'));
		});
		
		$('#'+this.Form.id).bind('mousedown', function() {
			CheckBox.OnMouseDown();
		});
		
		$('body').bind('mouseup', function() {
			CheckBox.OnMouseUp();
		});				
	},
	OnMouseUp : function() {
		CheckBox.OnDrag = false;
		CheckBox.Values = new Array();
		Util.CheckBoxes = new Array();

		if(this.Form == null) return;

		$('input[type=checkbox][name=checkbox]:checked').each(function() {
			CheckBox.Values.push($(this).val());
			Util.CheckBoxes.push($(this).val());
		});
	},
	OnMouseDown : function() {
		this.OnDrag = true;
	},
	All : function(obj) {
		//if(this.Form == null) return;
		if(obj.checked) 
			$('input[type=checkbox]').attr('checked',true);
		else 
			$('input[type=checkbox]').attr('checked',false);
	}
}




function GoPage(n) {
	Util.ChangeURLParameter('page',n);
}


// String trim()
String.prototype.ltrim = function() {
	var re = /\s*((\S+\s*)*)/;
	return this.replace(re, "$1");
}

String.prototype.rtrim = function() {
	var re = /((\s*\S+)*)\s*/;
	return this.replace(re, "$1");
}

String.prototype.trim = function() {
	return this.ltrim().rtrim();
}

