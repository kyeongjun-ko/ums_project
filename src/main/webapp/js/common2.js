	var LayerOn = '';
	var arrPrefixPhone = new Array("031", "032", "033", "041", "042", "043", "044", "051", "052", "053", "054", "055", "061", "062", "063", "064", "070", "030", "080");
	var arrPrefixPhone2 = new Array("031", "032", "033", "041", "042", "043", "044", "051", "052", "053", "054", "055", "061", "062", "063", "064", "070");
	var arrPrefixMobile = new Array("010", "011", "016", "017", "018", "019", "030", "070", "012", "013", "015");
	var arrPrefix4 = new Array("1515","1544","1566","1577","1588","1599","1600","1644","1666","1688","0130","0500","0501","0502","0503","0504","0505","0506","0507","0508","0509"); // 프리픽스4자리 참고

	var gstrSecureConn = (document.location.host != 'dev.xroshot.com:8080' ? 'https://' + document.location.host : '');
	var gstrSvr = (document.location.host != 'dev.xroshot.com:8080' ? 'http://' + document.location.host : '');

	/* 정규 표현식 */
	function ereg(exp,str) 
	{ 
		exp=new RegExp(exp); 
		exp=str.match(exp); 
		return exp; 
	} 

	/* 날짜 형식 확인 */
	function checkDate(mm,dd,yy)
	{
		dateFormat = new Date(yy,mm,dd);

		if( mm == 12 ) {
			strYear = dateFormat.getFullYear() - 1;
		} else {
			strYear = dateFormat.getFullYear();
		}

		if( dateFormat.getMonth() == 0 ) {
			strMonth = 12;
		} else {
			strMonth = dateFormat.getMonth();
		}
		strDay = dateFormat.getDate();

		if (yy == strYear && mm == strMonth && dd == strDay) {
			return true;
		} else {
			return false;
		}
	}

	/* 주민등록번호 형식 확인 */
	function checkSSN(ssn1,ssn2)
	{
		if (ssn1.length != 6 || ssn2.length != 7) return false;
		if (!ereg("^[0-9]{2}[0|1]{1}[0-9]{1}[0-3]{1}[0-9]{1}[1|2|3|4]{1}[0-9]{6}$", ssn1+ssn2)) return false;
		ssn = ssn1 + "" + ssn2
		juminyear = ( '2' >= ssn.substr(6,1)) ? "19" : "20";
	//alert(1);
		if (!checkDate(ssn.substr(2,2),ssn.substr(4,2),juminyear+""+ssn.substr(0,2))) return false;
	//alert(2);
		tmpjumin = 0;

		for(i=0;i<12;i++)
		{
			if(i>7) jumincheck=i-6;
			else jumincheck=i+2;
			jumincut = ssn.substr(i,1);
			tmpjumin = tmpjumin + jumincut * jumincheck;
		}
		jumintype = 11 - tmpjumin % 11;
	//alert(3);
		if(jumintype > 9) jumintype = jumintype - 10;
		if(jumintype != ssn.substr(ssn.length-1,1)) return false;
	//alert(4);
		return true;
	}

	/* 이메일 형식 체크 */
	function IsMail(addr)
	{
		return ereg("(^[_0-9a-zA-Z-]+(\.[_0-9a-zA-Z-]+)*@[0-9a-zA-Z-]+(\.[0-9a-zA-Z-]+)*$)",addr);
	}

	/* 비밀번호 형식 체크 */
	function PasswordCheck(pw) {
		if(pw == null) return false;
		else if(pw.length < 8) return false;
		else if(16 < pw.length) return false;
		else return true;
	}

	/* 030 비밀번호 형식 체크 */
	function Password030Check(pw) {
		if(pw == null) return false;
		else if(pw.length != 4) return false;
		else if(pw.match(/[^0-9]/gi) != null) return false;
		else return true;
	}

	/* 숫자 형식 체크 */
	function NumberCheck(val)
	{
		var Num = /^[0-9]/;
		j = 0;
		temp = "";

		for (i=0; i < val.length; i++) {
			temp = val.substring(i, i+1);

			if (!(Num.test(temp))) {
				j++; 
			}
		}

		if (j>0) {
			return false;
		} else {
			return true;
		}
	}

	/* 한글 형식 체크 */
	function StringCheck(str)
	{
		var i, j = 0;
		var Alpha = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
		var Digit = '1234567890<>?/\\+=-_)(*&^%$#@!.,;:\{\}[]\"\'\`\~';
		astr = Alpha + Digit;

		if (astr.length > 1) {
			for(i = 0; i < str.length; i++) {
				if(astr.indexOf(str.substring(i, i+1)) >= 0) {
					j++;
				}
			}
			if (j > 0) {
				return false;
			} else {
				return true;
			}
		}
	}

	/* 입력창 포커스 변경 */
	function InputFocus(srcInput, tarInput, len)
	{
		srcInput = srcInput+".value.length";
		tarInput = tarInput+".focus()";

		if (eval(srcInput) == len)
		{
			eval(tarInput);
		}
	}

	/* 전화번호 형식 체크 */
	function isPhone(num, tp) {
		/*if (tp != "NOT030PHONE") {
			if (tp == "MOBILE") tp = "HPHONE";		
			var res = $.ajax({ 
				url : '/Sms/IsPhone.jsp', 
				type : 'post', 
				data : { phone : num, type : tp },
				async : false
			}).responseText;
			
			if(res != null && res.trim() == 'true') return true;
			else return false;
		} else return !isPhone(num, '030');
			*/
	
		num = num.split("-").join("");
		pre = num.substr(0,3);

		if (tp == "MOBILE") {
			if (!in_array(pre, arrPrefixMobile)) return false;
		} else if (tp == "PHONE") {
			if (!in_array(pre, arrPrefixPhone) && pre.substr(0,2) != "02" && pre.substr(0,2) != "15" && pre.substr(0,2) != "16" && !in_array(num.substr(0,4), arrPrefix4)) return false;
		} else if (tp == "NOT030PHONE") {
			if (!in_array(pre, arrPrefixPhone2) && pre.substr(0,2) != "02" ) return false;
		} else if (tp == "030") {
			if (pre != "030") return false;
		} else {
			if (!in_array(pre, arrPrefixMobile) && !in_array(pre, arrPrefixPhone) && pre.substr(0,2) != "02" && pre.substr(0,2) != "15" && pre.substr(0,2) != "16" && !in_array(num.substr(0,4), arrPrefix4)) return false;
		}

		if( num.substr(0,4) == "1515" ) {
			numbody = num.substr(4);

			if (numbody.length != 7 && numbody.length != 10 && numbody.length != 11) return false;
		} else if( num.substr(0,5) != "03030" ) {
			if( pre.substr(0,2) == "02" ) {
				numbody = num.substr(2);
			} else if( pre.substr(0,2) == "15" || pre.substr(0,2) == "16" ) {
				numbody = num;
			} else if(num[0] == '0' && in_array(num.substr(0,4), arrPrefix4)) {
				numbody = num.substr(4);
			} else {
				numbody = num.substr(3);
			}
			
			if (numbody.length != 8 && numbody.length != 7) return false;
		} else {
			numbody = num.substr(5);
			if (numbody.length != 7 && numbody.length != 6) return false;
		}

		for (i=0;i < numbody.length; i++) {
			if ( parseInt( numbody.substr(i,1) ) + 0 != numbody.substr(i,1) ) return false;
		}		

		return true;
	}

	// 번호할당 확인
	function NumberAssign(strNumber) {
		arrCheckNum = new Array("1004", "2580", "1112", "1113", "1114", "1115", "1116", "1117", "1118", "1119", "1110");

		cntNumber = strNumber.length;
		InputNumber = strNumber.substring(cntNumber - 4, cntNumber);

		strReturnMsg = "";

		if( InputNumber.length == 4 ) {
			// 특수 목적 번호
			if( in_array(InputNumber, arrCheckNum) ) {
				strReturnMsg = "특수 목적 번호는 할당할 수 없습니다.";
				return strReturnMsg;
			}

			// 2자리 연속 번호/동일번호 4자리 포함( ex:1212, 2323, 1111, 2222... )
			/*tmpNumber = InputNumber.substring(0, 2);
			tmpSumNumber = tmpNumber + tmpNumber;

			if( InputNumber == tmpSumNumber ) {
				strReturnMsg = "두자리 이상 연속되는 숫자는 사용할 수 없습니다.";
				return strReturnMsg;
			}*/
			
			tmpNumber = InputNumber.substring(0, 1);
			tmpSumNumber = tmpNumber + tmpNumber + tmpNumber + tmpNumber;

			if( InputNumber == tmpSumNumber ) {
				strReturnMsg = "연속되는 숫자는 사용할 수 없습니다.";
				return strReturnMsg;
			}
			
			// 연속번호 4자리
			ini1 = InputNumber.substring(0, 1);
			ini2 = InputNumber.substring(3, 4);
			tmpRepeatNumber1 = "";
			tmpRepeatNumber2 = "";

			for( i = 0; i < 4; i++ ) {
				tmpRepeatNumber1 = tmpRepeatNumber1 + ( parseInt(ini1) + i );
				tmpRepeatNumber2 = ( parseInt(ini2) + i ) + tmpRepeatNumber2;
			}

			if( InputNumber == tmpRepeatNumber1 || InputNumber == tmpRepeatNumber2 ) {
				strReturnMsg = "연속되는 숫자는 사용할 수 없습니다.";
				return strReturnMsg;
			}
		} else {
			strReturnMsg = "4자리 번호를 입력하셔야 합니다.";
			return strReturnMsg;
		}

	}

	/* 배열 */
	function in_array(val, arr) {

		for(i=0; i< arr.length; i++) {
			if ( val == arr[i] ) return true;
		}
		return false;
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

	function setPhoneNumberByValue(val) {
		val = val.trim().replace(/\-/g,"");

		var str= val;
		var l = str.length;

		if( l > 2 ) {
			if( l > 7 ) {
				str=str.substr(0,(k=(str.substr(0,2)=='02')?2:3))+'-'+str.substr(k,l-4-k)+'-'+str.substr(l-4);
			}
			else {
				str=str.substr(0,3)+'-'+str.substr(3);
			}
		}
		return str;
	}

	function OnlyNumPhoneNumScript(obj,event) {
		if(event == null) return;
		
		var key = event.keyCode;
		var strCtrl = event.ctrlKey;
		var strShift = event.shiftKey;

		if(!(key==8||key==46||key==144||(!strShift&key>=48&&key<=57)||(!strShift&&key>=96&&key<=105)||key==109||key==189||(key>=33&&key<=40||key==16||key==17))){
			if( key==9 || key==13 || (strCtrl == true && (key == 67 || key == 86 )) ) {
				event.returnValue = true;
				return;
			} else {
				alert("숫자만 입력 가능합니다.");
				event.returnValue = false;
				return false;
			}
		} else {
			event.returnValue = true;
		}

		var	str	= obj.value.replace(/\D/g,"");
		var len = str.length;

		if( len > 2 && (key != 8 && key != 46) && !(key >= 33 && key <= 40)) {
			if( (len > 5 && str.substr(0,5) == "03030") || (str.substr(0,5) == "03030" && (len == 5) && (key != 105 && key != 57)) ) {
				if( len == 11 ) { 
					str=str.substr(0,k=4)+"-"+str.substr(k,len-3-k)+"-"+str.substr(len-3);
				} else if( len > 7 ) {
					str=str.substr(0,k=5)+"-"+str.substr(k,len-2-k)+"-"+str.substr(len-2);
				} else {
					str=str.substr(0,5)+"-"+str.substr(5);
				}
			} else if( len >= 4 && in_array(str.substr(0,4), arrPrefix4) ) {
				str=str.substr(0,4)+"-"+str.substr(4);
			} else if( str.substr(0,1) != "0" ) {
				str = str;
			} else if( len >= 8 && in_array(str.substr(0,4), arrPrefix4) ) {
				str=str.substr(0,(k=4))+"-"+str.substr(k,len-3-k)+"-"+str.substr(len-3);
			} else if( len >= 7 ) {
				str=str.substr(0,(k=(str.substr(0,2)=="02")?2:3))+"-"+str.substr(k,len-3-k)+"-"+str.substr(len-3);
			} else {
				if( str.substr(0,2)!= "15" && str.substr(0,2)!= "16" ) {
					str=str.substr(0,3)+"-"+str.substr(3);
				} else if( in_array(str.substr(0,4), arrPrefix4) ) {
					str=str.substr(0,4)+"-"+str.substr(4);
				}
			}

			if( (key==109 || key==189) && str.substr(substr.length-1, 1)=="-" ) {
				event.returnValue = false;
			}
			obj.value = str;
		}
	}

	function OnlyNumPhoneNumValueScript(val) {
		var	str	= val.replace(/\-/g,"");
		var len = str.length;

		if( len > 3 ) {
			/*if( len > 5 && str.substr(0,5) == "03030" && str.substr(5,1) != "9" ) {
				if( len > 8 ) {
					str=str.substr(0,k=5)+"-"+str.substr(k,len-4-k)+"-"+str.substr(len-4);
				} else {
					str=str.substr(0,5)+"-"+str.substr(5);
				}*/
			if( len > 5 && str.substr(0,5) == "03030") {
				if( len == 12 ) {
					str=str.substr(0,k=4)+"-"+str.substr(k,len-4-k)+"-"+str.substr(len-4);
				} else if( len > 9 ) {
					str=str.substr(0,k=5)+"-"+str.substr(k,len-4-k)+"-"+str.substr(len-4);
				} else {
					str=str.substr(0,5)+"-"+str.substr(5);
				}
			} else if( len > 5 && str.substr(0,5) == "00727" && len > 10 ) {
				str=str.substr(0,k=5)+"-"+str.substr(k,len);
//			} else if( len > 4 && len <= 8 && (str.substr(0,2) == "15" || str.substr(0,2) == "16" || str.substr(0,4) == "0502" || str.substr(0,4) == "0505" || str.substr(0,4) == "0504" || str.substr(0,4) == "0130") ) {
			} else if( len > 4 && len <= 8 && in_array(str.substr(0,4), arrPrefix4) ) {
				str=str.substr(0,4)+"-"+str.substr(4);
//			} else if( len > 8 && (str.substr(0,4) == "0502" || str.substr(0,4) == "0505" || str.substr(0,4) == "0504" || str.substr(0,4) == "0130")) {
			} else if( len > 8 && in_array(str.substr(0,4), arrPrefix4) ) {
				if( str.substr(0,4) == "1515" && (len == 14 || len == 15) ) {
					str=str.substr(0,(k=4))+"-"+str.substr(k);
				} else {
					str=str.substr(0,(k=4))+"-"+str.substr(k,len-4-k)+"-"+str.substr(len-4);
				}
			} else if( len > 7 ) {
				str=str.substr(0,(k=(str.substr(0,2)=="02")?2:3))+'-'+str.substr(k,len-4-k)+"-"+str.substr(len-4);
			} else {
				str=str.substr(0,3)+"-"+str.substr(3);
			}
		}

		return str;
	}

	function onlyNumValueCheck(val){
		var key = event.keyCode;
		var strCtrl = event.ctrlKey;

		if(!(key==8||key==46||key==144||(key>=48&&key<=57)||(key>=96&&key<=105)||key==109||key==189||(key>=33&&key<=40||key==16||key==17))){
			if( key==9 || key==13 || (strCtrl == true && (key == 67 || key == 86 )) ) {
				event.returnValue = true;
				return;
			} else {
				alert("숫자만 입력 가능합니다.");
				event.returnValue = false;
				return false;
			}
		} else {
			event.returnValue = true;
		}
	}

	function writeActiveXObject(str)
	{
		document.writeln(str);
	}

	function sendQuery(dbm, query, type ,  cb) {
		$.post("/aquery.jsp", {
			strQry : query,
			strDbm : dbm,
			strType : type,
			strRefresh : Math.round(Math.random() * 1000)
		}, function(data) {
			if (cb) eval(cb+"(data)");
		},"html");
	}
	
	// for client fax rec li
	function sendQueryMessageConfirm(strMsgId) {
		$.post("/aquery.jsp", {
			strMsgId : strMsgId,
			strType : "MESSAGECONFIRM",
			strRefresh : Math.round(Math.random() * 1000)
		}, function(data) {
				alert(data);
		},"html");
	}
	
	// for sms proc 
	function sendQueryUpdateRepeat(strJobId,strGrpId,strSeqno) {
		$.post("/aquery.jsp", {
			strJobId : strJobId,
			strGrpId : strGrpId,
			strSeqno : strSeqno,
			strType : "UPDATEREPEAT",
			strRefresh : Math.round(Math.random() * 1000)
		}, function(data) {
		},"html");		
	}

	function sendQueryDeleteRepeat(strSeqno) {
		$.post("/aquery.jsp", {
			strSeqno : strSeqno,
			strType : "DELETEREPEAT",
			strRefresh : Math.round(Math.random() * 1000)
		}, function(data) {
		},"html");		
	}	
	
	function sendQueryUpdateMsg(strJobId,strGrpId,strSeqno) {
		$.post("/aquery.jsp", {
			strJobId : strJobId,
			strGrpId : strGrpId,
			strSeqno : strSeqno,
			strType : "UPDATEMSG",
			strRefresh : Math.round(Math.random() * 1000)
		}, function(data) {
		},"html");		
	}	
	
	function sendQueryUpdateMsgReceive(strJobId, strGrpId, strSeqno, strOrderNum) {
		$.post("/aquery.jsp", {
			strJobId : strJobId,
			strGrpId : strGrpId,			
			strSeqno : strSeqno,
			strOrderNum : strOrderNum,
			strType : "UPDATEMSGRECEIVE",
			strRefresh : Math.round(Math.random() * 1000)
		}, function(data) {
		},"html");		
	}
	

	function getAuth(sp_id, eu_id, cb) {
		$.post("/aquery.jsp", {
			strType : "AUTH",
			strSpId : sp_id,
			strEuId : eu_id,
			strRefresh : Math.round(Math.random() * 1000)
		}, function(data) {
			if (cb) eval(cb+"(data)");
		},"html");
	}

	function procSendFail(msgid, result, cb) {
		$.post("/aquery.jsp", {
			strType : "FAIL",
			strMsgId : msgid,
			strResult : result,
			strRefresh : Math.round(Math.random() * 1000)
		}, function(data) {
			if (cb) eval(cb+"(data)");
		},"html");
	}

	function checkNationalFax(fax_number, cb) {
		$.post("/aquery.jsp", {
			strType : "NATION_FAX",
			strFaxNum : fax_number,
			strRefresh : Math.round(Math.random() * 1000)
		}, function(data) {
			if (cb) eval(cb+"(data)");
		},"html");
	}

	function CloseLayers() {
		try {
			//if(LayerOn == '') 
				//document.getElementById('famsiteCom').style.display = 'none';
		} catch(e) {}
	}

	function UserGuide(target) {
		var url = '/Intro/PopUseGuide3.jsp?menu=' + target;
		window.open(url, "pop_help", "width=700, height=600, scrollbars=yes, toolbars=no, resizable=no ");
	}

	// 2자리수 만들기
	function AddStringZero(strValue) {
		var strReturnValue = "";

		if( strValue < 10 ) {
			strReturnValue = "0" + strValue;
		} else {
			strReturnValue = strValue;
		}

		return strReturnValue;
	}
	
	// 사업자 등록번호 체크
	function IsBizCode(bizcode) {
		if(bizcode == null || bizcode.match(/\d{3}\-\d{2}\-\d{5}/) == null) return false;
		
		bizcode = bizcode.replace(/\-/g,'');
	  if(bizcode.length != 10) return false;
	  
	  var sum = 0;
	  sum += parseInt(bizcode.substring(0,1));
	  sum += parseInt(bizcode.substring(1,2)) * 3 % 10;
	  sum += parseInt(bizcode.substring(2,3)) * 7 % 10;
	  sum += parseInt(bizcode.substring(3,4)) * 1 % 10;
	  sum += parseInt(bizcode.substring(4,5)) * 3 % 10;
	  sum += parseInt(bizcode.substring(5,6)) * 7 % 10;
	  sum += parseInt(bizcode.substring(6,7)) * 1 % 10;
	  sum += parseInt(bizcode.substring(7,8)) * 3 % 10;
	  sum += Math.floor(parseInt(bizcode.substring(8,9)) * 5 / 10);
	  sum += parseInt(bizcode.substring(8,9)) * 5 % 10;
	  sum += parseInt(bizcode.substring(9,10));
	  
	  if (sum % 10 == 0) return true;
	 	else
		  return false; 
	}
	
	// 로그인 체크
	function IsLoggedIn() {
		$.ajax({
			url : '/validSession', 
			type : 'post',  
			dataType : 'json',
			success : function(res) {
				
				if(res.result) return "valid";
				else {
					alert('로그인해주세요.');
					return "invalid";
				}
			},
			error : function(res) {
				alert('에러가 발생했습니다.');
			}
		});
	}
	
	// 로그인 아이피 체크
	function CheckLoginIp() {
		var frm = document.getElementById('frmLogin');
		var frmCert = document.getElementById('frmLoginAuth');
		
		if(frm != null && frm.USERID.value != '' && frm.PASSWD.value != '') {
			$.ajax({
				url : '/Member/CheckLoginIp.jsp', 
				type : 'post', 
				data : $(frm).serialize(), 
				dataType : 'json',
				success : function(res) { 
					if(res != null) {
						if(res.result == 'true') {
							if(res.auth == 'true') {
								Util.NextFunction = 'loginAfterCert()';
								frmCert.action = '/Client/PopMobilians.jsp';
								frmCert.id.value = frm.USERID.value;
								if(res.auth_type == 1)
									var win = window.open('','CERT','width=292,height=389');
								else
									var win = window.open('','CERT','width=482,height=350');
								frmCert.submit();
							} else
								frm.submit();
						} else
							alert(res.message);
					}
				},
				error : function(res) {
					alert('에러가 발생했습니다.');
				}
			});
		}
	}
	
	// 로그인 아이피 보안 설정 팝업
	function popLoginIp() {
		var frm = document.getElementById('frmLogin');

		window.open('/Member/LoginIpPop.jsp?lv='+frm.IPLEVEL.value,'POPUP','width=540,height=566');
	}
	
	// 로그인 아이피 보안 단계 설정
	function setIpLevel(level) {
		if(level > 0) $('#ip_level_txt').attr('src','/Images/login_txt_3_'+level+'.gif');
		else if(level == 0) $('#ip_level_txt').attr('src','/Images/login_txt_3_off.gif');
		else return;
		
		var frm = document.getElementById('frmLogin');
		frm.IPLEVEL.value = level;
	}

	// 보안 인증후 로그인
	function loginAfterCert() {

		var frm = document.getElementById('frmLogin');
		if(frm != null) {
			var mobile_auth = $('#MOBILE_AUTH').val();
			var mobile_auth_no = $('#MOBILE_AUTH_NO').val();
			var ipin_ci = $('#IPIN_CI').val();

			if(mobile_auth=='Y') {
				//frm.MOBILE_AUTH.value = mobile_auth;
				//frm.MOBILE_AUTH_NO.value = mobile_auth_no;
				//frm.IPIN_CI.value = ipin_ci;
				
				frm.submit();
			}
		}
	}