	var intSMSMaxLength = 80; // 단문메시지 최대 바이트 스마트폰은 90 2G폰은 80 여기선 80으로고정함
	var arrRecieverList = Array();
	var arrFileList = Array();
	var blnDupPopup = true;
	var cntReceiver = 0;
	var cntFile = 0;
	var blnMMSLayer = true;
	var popWin = null;
	
	var receiveCnt = 0;

	// 받는 사람 직접 입력
	function addFormReceiver(frm) {
		if(frm != null) {
			strName = frm.AddReceiveName.value;
			strDefName = frm.AddReceiveName.defaultValue;
			strNumber = frm.AddReceiveNumber.value;
			strDefNumber = frm.AddReceiveNumber.defaultValue;
			if( strName == strDefName ) strName = "";
				strName = Util.CutStringByte(strName,50);
 
				strNumber = strNumber.split("-").join(""); 
			if(frm.MSGTYPE.value == 'FMS') {
				var strFaxNumberType = frm.FAX_NUMBER_TYPE.options[frm.FAX_NUMBER_TYPE.selectedIndex].value;
				var arrPrefixFax4 = new Array("0130","0501","0502","0503","0504","0505","0506","0507","0508","0509","1515");
				
				var strPrefix2 = strNumber.substr(0,2);
				var strPrefix3 = strNumber.substr(0,3);
				var strPrefix4 = strNumber.substr(0,4);
				
				if( strFaxNumberType == "2" ) {
					checkNationalFax(strNumber, "InternationalFax");
				} else if( (strFaxNumberType == "1" && !in_array(strPrefix3, arrPrefixPhone) && strPrefix2 != "02" && !in_array(strPrefix4, arrPrefixFax4)) || strNumber.length < 8 ) {
					alert("전화번호 형식이 맞지 않습니다.");
					frm.AddReceiveNumber.value = "";
					frm.AddReceiveNumber.focus();
				} else {
					if( addReceiver(strName, strNumber) ) {
						frm.AddReceiveName.value = "";
						frm.AddReceiveNumber.value = "";
						frm.AddSmartPhnYn.value = "";
						
					}
				}
			} else {
				if( !isPhone(strNumber, "MOBILE") && !isPhone(strNumber, "PHONE") && frm.MSGTYPE.value != 'SMS' && frm.MSGTYPE.value != 'LMS' ) {
					alert("전화번호 형식이 맞지 않습니다.");
					frm.AddReceiveNumber.value = "";
					frm.AddReceiveNumber.focus();
					return;
				} else {
					if( addReceiver(strName, strNumber) ) {
						frm.AddReceiveName.value = "";
						frm.AddReceiveNumber.value = "";
						frm.AddSmartPhnYn.value = "";
					}
				}
			}
		}
		return false;
	}

	function checkMessageLength(obj, intMaxLength, strMsgType, strOutText) {
		var intByteLength = getByteLength(obj.value.replace(/\r/g, ""));

		if ( intByteLength > intMaxLength) {
			alert("메시지 길이는 " + intMaxLength + " 바이트를 넘을 수 없습니다.");
			obj.value = cutStringByByte(obj.value, intMaxLength);
			intByteLength = getByteLength(obj.value);
		}

		if( strMsgType != "SMS" ) {
			objMessageLength = document.getElementById(strOutText);
			objMessageLength.innerHTML = "<Strong>" + intByteLength + "</Strong>/" + intMaxLength;
		} else {
			objMessageLength = document.getElementById(strOutText);
			//objMMSImage = document.getElementById("MMSImage");
			objMMSBackground = document.getElementById('CONTENT_BG');
			
			if ( 80 >= intByteLength ) {
				objMessageLength.innerHTML = "<Strong>" + intByteLength + "</Strong>/80";
				$('#mtype_sms').css({'display':'inline'});
				$('#mtype_lms').css({'display':'none'});
				$('#mtype_mms').css({'display':'none'});
				$('#MSGTYPE').val('SMS');
				if($('#MMSLayer').length > 0 && objMMSImage.src.indexOf('ico_mms_off.gif') < 0) {
					objMMSImage.src = "/images/mob.png";
					objMMSBackground.style.backgroundImage = $('#CONTENT_BG').css('background-image').replace('msgmms_bg.gif','msgsms_bg.gif');
					$('#MSGTYPE').val('SMS');

					if($('#MMSLayer').length > 0) {
						$('#MMSLayer').css({'display':'none'});
						blnMMSLayer = true;
					}
				}			
			} else {
				objMessageLength.innerHTML = "<b>" + intByteLength + "</b>/" + intMaxLength;
				$('#mtype_sms').css({'display':'none'});
				$('#mtype_lms').css({'display':'inline'});
				$('#mtype_mms').css({'display':'none'});
				$('#MSGTYPE').val('MMS');
				
				if($('#MMSLayer').length > 0 && objMMSImage.src.indexOf('ico_mms_on.gif') < 0) {
					objMMSImage.src = "/images/mob.png";
					objMMSBackground.style.backgroundImage = $('#CONTENT_BG').css('background-image').replace('msgsms_bg.gif','msgmms_bg.gif');
					$('#MSGTYPE').val('MMS');
					
					
					if(blnMMSLayer && $('#MMSLayer').length > 0 && $('#MMSLayer').css('display') == 'none') {
						var posBG = $('#CONTENT_BG').position();
						$('#MMSLayer').css({'display':'inline','left':(posBG.left+400)+'px','top':(posBG.top+110) + 'px'});
					}
				}
			}
		}
	}

	// 문자열의 길이 바이트값으로 리턴
	function getByteLength(strValue) {
		var strTemp = "";
		var intByteLength = 0;
		var intLength = strValue.length;

		for ( var i = 0; i < intLength ; ++i ) {
			strTemp = strValue.charAt(i);
			strTemp2 = strValue.charCodeAt(i);

			if ( escape(strTemp).length > 4 || ( strTemp2 >= 128 && strTemp2 <= 255 ) ) {
				intByteLength += 2;
			} else {
				++intByteLength;
			}
		}

		return intByteLength;

	}

	// 바이트 고려해서 문자열 자르기
	function cutStringByByte(strValue, intMaxLength) {

		var strReturn = "";
		var strTemp = "";
		var intByteLength = 0;
		var intLength = strValue.length;

		for ( var i = 0 ; i < intLength ; ++i ) {
			strTemp = strValue.charAt(i);

			if ( escape(strTemp).length > 4 ) {
				intByteLength += 2;
			} else {
				++intByteLength;
			}

			if ( intByteLength <= intMaxLength ) {
				strReturn += strTemp;
			} else {
				break;
			}
		}

		return strReturn;
	}

	function addFile(strFileName) {
		if(strFileName != '') {
			if( false == checkDupFile(strFileName) ) {
				alert("중복된 파일이 존재 합니다.");
				return;
			}
	
			arrFileList.push(Array(strFileName));
	
			if( cntFile == 0 ) {
				document.getElementById("FILE_LOCALPATH").value = strFileName;
			} else {
				document.getElementById("FILE_LOCALPATH").value = document.getElementById("FILE_LOCALPATH").value + "|" + strFileName;
			}
			cntFile++;
	
			var html = "";
			var strFileName2 = strFileName;
			if(strFileName2.indexOf('/TCSMSG/') > -1) {
				var tmp = strFileName2.replace(/^\/|\/$/gi,'').split('/');
				strFileName2 = tmp[tmp.length - 1];
			}

			html  = "<div id=\"SubFileContent" + cntFile + "\" name=\"SubReceiveContent\" style=\"width:100%;\">";
			html += "<input type=\"hidden\" name=\"FILE_INFO[]\" value=\"" + strFileName + "\">";
			html += "<div id=\"SubFileName" + cntFile + "\" style=\"float:left;padding:3px;width:90%;height:14px;overflow:hidden;word-break:break-all;\">" + strFileName2 + "</div>";
			html += "<div id=\"SubFileDel" + cntFile + "\" style=\"float:right;padding:3px;\"><img src=\"/Images/btn_delete.gif\" align=\"absmiddle\" border=\"0\" onclick=\"delFile(" + cntFile + ")\" style=\"cursor:pointer\"></div>";
			html += "</div>";
	
			document.getElementById("FILE_COUNT").value = parseInt(document.getElementById("FILE_COUNT").value) + 1;
			document.getElementById("divFileList").innerHTML += html;
	
			setFileCount();
	
			return true;
		}
		else {
			alert('파일이 존재하지 않습니다.');
			return;
		}
	}
	function sendCntChk() {
		var sendcount = $('#SEND_COUNT').val();
		var receivercount = $('input[name=receiverPhone]').length;
		if(receivercount > sendcount) {
			alert('한번에 전송할 수 있는 최대는 ' + sendcount + '명입니다.');
			return false;
		}
	}
		
	function addReceiver(strName, strNumber) {
		var sendcount = $('#SEND_COUNT').val();
		var receivercount = $('input[name=receiverPhone]').length;
		var smartPhnYn = $('input[name=AddSmartPhnYn]').val();
		
		//alert (smartPhnYn);
		
		if ( strNumber == "" ) {
			alert("받는사람 번호를 입력해주세요.");
			return;
		}
		
		strNumber = strNumber.replace(/-/gi,'');

		// VMS, SURVEY의 경우 동일 번호 착신 금지
		if(document.location.href.indexOf('VmsMessage') > -1 || document.location.href.indexOf('VmsSurvey') > -1) {
			var phone = $('#SELECT_SENDER').val();
			if(phone == 'DIRECT') phone = $('#SENDER').val();

			if(strNumber.replace(/\D/g,'') == phone.replace(/\D/g,'')) {
				alert('보내는 사람과 받는사람이 동일한 건이 있습니다.\n음성 메시지는 보내는 사람과 받는사람을 같은 번호로\n설정할 수 없습니다.');
				return;
			}
		}
		
		if(receivercount >= sendcount) {
			alert('한번에 전송할 수 있는 최대는 ' + sendcount + '명입니다.');
			return;
		}
		else if($('input[name=receiverPhone][value='+strNumber+']').length > 0) {
			//if ( blnDupPopup ) {
				alert("중복된 전화번호가 있습니다.");
				blnDupPopup = false;
			//}

			document.getElementById("AddReceiveNumber").focus();
			return;
		}

		cntReceiver++;

		var html = "";		
		
		html  = '<li id="SubReceiveContent' + cntReceiver + '" >';
		html += '<input type="text" id="SubReceiverName' + cntReceiver + '" class="input-sm name" value="' + strName + '">';
		html += '<input type="text" id="SubReceiverValue' + cntReceiver + '" class="input-sm number" value="' + OnlyNumPhoneNumValueScript(strNumber) + '">';
		html += '<button class="btn btn-link btn-xs" onclick="delReceiver(' + cntReceiver + ')" >삭제</button>';
		html += '<input type="hidden" name="receiverInfo" value="' + strName + '^' + strNumber + '|" />';
		html += '<input type="hidden" name="receiverPhone" value="' + strNumber + '" />';
		html += '<input type="hidden" name="smartPhnYn" value="' + smartPhnYn + '" />';
		html += '</li>';
		
		/*
		html  = '<li> <div id="SubReceiveContent' + cntReceiver + '" >';
		html += '<div id="SubReceiverName' + cntReceiver + '" class="input-sm name" >' + strName + '</div>';
		html += '<div id="SubReceiverValue' + cntReceiver + '" class="input-sm number" >' + OnlyNumPhoneNumValueScript(strNumber) + '</div>';
		html += '<div ><button class="btn btn-link btn-xs" onclick="delReceiver(' + cntReceiver + ')" style="cursor:pointer">삭제</button></div>';
		html += '<input type="hidden" name="receiverInfo" value="' + strName + '^' + strNumber + '|" />';
		html += '<input type="hidden" name="receiverPhone" value="' + strNumber + '" />';
		html += '</div> </li>';
		*/

		document.getElementById("divReceiverList").innerHTML += html;
		setReceiverCount();
		return true;
	}
	
	function addReceiver2(strName, strNumber, grpNo) {
		//delReceiverAll();
		var sendcount = $('#SEND_COUNT').val();
		var receivercount = $('input[name=receiverPhone]').val(); 
		var smartPhnYn = $('input[name=AddSmartPhnYn]').val();
		
		//alert (smartPhnYn);
		
		if ( strNumber == "" ) {
			alert("받는사람 번호를 입력해주세요.");
			return;
		}
		
//		strNumber = strNumber.replace(/-/gi,'');
//
//		// VMS, SURVEY의 경우 동일 번호 착신 금지
//		if(document.location.href.indexOf('VmsMessage') > -1 || document.location.href.indexOf('VmsSurvey') > -1) {
//			var phone = $('#SELECT_SENDER').val();
//			if(phone == 'DIRECT') phone = $('#SENDER').val();
//
//			if(strNumber.replace(/\D/g,'') == phone.replace(/\D/g,'')) {
//				alert('보내는 사람과 받는사람이 동일한 건이 있습니다.\n음성 메시지는 보내는 사람과 받는사람을 같은 번호로\n설정할 수 없습니다.');
//				return;
//			}
//		}

		cntReceiver++;

		var html = "";		
		
		html  = '<li id="SubReceiveContent' + cntReceiver + '" >';
		html += '<input type="text" id="SubReceiverName' + cntReceiver + '" class="input-sm name" value="' + strName + '">';
		html += '<input type="text" id="SubReceiverValue' + cntReceiver + '" class="input-sm number" value="' + OnlyNumPhoneNumValueScript(strNumber) + '">';
		html += '<button class="btn btn-link btn-xs" onclick="delReceiver(' + cntReceiver + ')" >삭제</button>';
		html += '<input type="hidden" name="receiverInfo" value="' + grpNo + '"/>';
		html += '<input type="hidden" name="receiverPhone" value="' + strNumber + '" />';
		html += '<input type="hidden" name="smartPhnYn" value="' + smartPhnYn + '" />';
		html += '</li>';
		
		document.getElementById("divReceiverList").innerHTML += html;
		//setReceiverCount();
		return true;
	}

	// 받는 사람 목록에서 중복 검사
	function checkDupNumber(strNumber) {
		strNumber = strNumber.replace(/-/g, "");

		for ( var i = 0 ; i < arrRecieverList.length ; ++i ) {
			if ( strNumber == arrRecieverList[i][1].replace(/-/g, "") ) {
				return false;
			}
		}

		return true;
	}

	// 파일 중복 검사
	function checkDupFile(strFileName) {
		for ( var i = 0 ; i < arrFileList.length ; ++i ) {
			if ( strFileName == arrFileList[i] ) {
				return false;
			}
		}
		return true;
	}

	//받는 사람 목록에서 제거
	function delReceiver(seq) {
		var obj = document.getElementById("SubReceiveContent" + seq);

		for(i=0;i<arrRecieverList.length;i++) {
				if(arrRecieverList[i][1].replace(/\-/gi,'') == document.getElementById('SubReceiverValue' + seq).innerHTML.replace(/\-/gi,'')) { 
						arrRecieverList.splice(i,1);
						break;
				}
		}
		
		obj.parentNode.removeChild(obj);
		setReceiverCount();
	}
	
	//받는 사람 목록에서 제거
	function delReceiver2(seq) {
		var obj = document.getElementById("SubReceiveContent" + seq);

		for(i=0;i<arrRecieverList.length;i++) {
				if(arrRecieverList[i][1].replace(/\-/gi,'') == document.getElementById('SubReceiverValue' + seq).innerHTML.replace(/\-/gi,'')) { 
						arrRecieverList.splice(i,1);
						break;
				}
		}
		
		obj.parentNode.removeChild(obj);
	}
	
	//받는 사람 목록에서 모두 제거
	function delReceiverAll() {
		arrRecieverList = new Array();
		document.getElementById("divReceiverList").innerHTML = '';
		
		setReceiverCount();
		receiveCnt = 0 ;
	}	

	// 파일 목록에서 제거
	function delFile(seq) {
		var obj = document.getElementById('SubFileContent' + seq);
		var file = document.getElementById('SubFileName' + seq).innerHTML;
		var localpath = '';

		for(i=0;i<arrFileList.length;i++) {
				if(arrFileList[i].toString().indexOf(file) > - 1) { 
						arrFileList.splice(i,1);
						//break;
				}
				else
					localpath = localpath + ((i==0)?'':'|') + arrFileList[i];
		}
		
		document.getElementById("FILE_LOCALPATH").value = localpath;
		obj.parentNode.removeChild(obj);

		setFileCount();
	}

	//받는 사람 목록 카운트 변경
	function setReceiverCount() {
		var obj = document.getElementsByName("receiverInfo");
		var obj2 = document.getElementById("spanReceiverCount");
		var obj3 = document.getElementById("RECEIVER_CNT");

		obj2.innerHTML = obj.length;
		obj3.value = obj.length;
	//	obj2 = obj.length;
	}
	
	//받는 사람 목록 카운트 변경
	function setReceiverCount2(seq) {
		var obj2 = document.getElementById("spanReceiverCount");
		var obj3 = document.getElementById("RECEIVER_CNT");
		
		var obj = document.getElementsByName("receiverPhone")[seq].value;
		var objBef = "";
				
		if( seq > 0){
			objBef = document.getElementsByName("receiverPhone")[seq-1].value;
			receiveCnt = receiveCnt + parseInt(obj);
		}
		else{
			receiveCnt = parseInt(obj);			
		}
		
		var receivercountTot =  receiveCnt;
		var sendcount =   $('#SEND_COUNT').val();

		//alert(receivercountTot);
		if(receivercountTot >= sendcount) {
			alert('한번에 전송할 수 있는 최대는 ' + sendcount + '명입니다.');
			delReceiver2(seq);
			return;
		}

		obj2.innerHTML = receiveCnt;
		obj3.value = receiveCnt;
		
		//alert(objBef +" " +obj + " " + receiveCnt);

	}
	
	//받는 사람 목록 카운트 변경
	function setReceiverCount3() {
		var obj2 = document.getElementById("spanReceiverCount");
		var obj3 = document.getElementById("RECEIVER_CNT");
		
		var obj = document.getElementsByName("receiverPhone") ;
		var objBef = "";		
		receiveCnt = 0;
		for(i=0;i<obj.length;i++) {
			objBef = document.getElementsByName("receiverPhone")[seq].value;
			receiveCnt = receiveCnt + parseInt(obj);	
		}
		
		var receivercountTot =  receiveCnt;
		var sendcount =   $('#SEND_COUNT').val();

		alert(receivercountTot);
		if(receivercountTot >= sendcount) {
			alert('한번에 전송할 수 있는 최대는 ' + sendcount + '명입니다.');
			delReceiver2(seq);
			return;
		}

		obj2.innerHTML = receiveCnt;
		obj3.value = receiveCnt;
		
		//alert(objBef +" " +obj + " " + receiveCnt);

	}

	//파일 수 변경
	function setFileCount() {
		var obj = document.getElementsByName("FILE_INFO[]");
		var obj2 = document.getElementById("spanFileCount");

		obj2.innerHTML = obj.length;

	//	obj2 = obj.length;
	}

	// 녹음 방법 탭 선택
	function RecodingMethod(n, w) {
		var strUrl = "http://" + document.location.host + "/Vms/RecordingMethod.jsp";
		if( document.location.host == "" || document.location.host == "" ) {
//				strUrl = "https://" + document.location.host + "/Vms/RecordingMethod.jsp";
		}

		$.ajax({ 
//				url : 'http://'+document.location.host+'/Vms/RecordingMethod.jsp', 
			url : strUrl, 
			type : 'get', 
			data : { tab : n, where : w },  
			success : function(res) {
				$('#RecordingMethod').html(res.trim());
			},
			error : function(res) {
				$('#RecordingMethod').html('');
			}
		});
	}

	function LoadMessage(frm, strOrder) {
		var strSeqno = frm["SEQNO"+strOrder].value.trim();
		var strMsgType = frm["MSGTYPE"+strOrder].value.trim();
		var strSubject = frm["SUBJECT"+strOrder].value.trim();
		var strContent = frm["CONTENT"+strOrder].value.rtrim();
		var strCreateDate = frm["CREATE_DATE"+strOrder].value.trim();
		var strAttach = frm["ATTACH"+strOrder].value.trim();
		var strScenarioId = frm["SCENARIO_ID"+strOrder].value.trim();
		var strFileSize = frm["FILESIZE"+strOrder].value.trim();
		var lngDuration = 0;
		var intPageCount = 0;

		if(strSubject == "") {
			strSubject = "제목없음";
		}

		switch(strMsgType) {
			case "SMS":
			case "LMS":
			case "PMS":
				window.opener.$("#CONTENT").val(Util.TextDecode(strContent));
				window.opener.$("#SUBJECT").val(strSubject);
				window.opener.Emoticon.ShowBytes();
				
				if(strAttach != '') { // 포토메시지
					window.opener.$('#CONVERT_FILE').val(strAttach);
					window.opener.$('#CONVERT_FILE_SIZE').val(strFileSize);
					window.opener.$('#CONVERT_CURRENT_PAGE').val(1);
					if( strScenarioId > 0 ) {
						window.opener.$('#CONTENTS_KEY').val(strScenarioId);
					}

					//window.opener.$('#MmsImage').html("<img src=\"/Client/ImageThumb.jsp?sid=COM&width=140&height=115&image=" + strAttach + "\" width=\"140\" height=\"115\" onclick=\"ImageUpload(" + (strScenarioId > 0 ? 2 : 1) + ")\" style=\"cursor:pointer;\">");
					window.opener.$('#MmsImage').html("<img src=\"/Client/ImageThumb.jsp?sid=COM&width=115&image=" + strAttach + "\" onclick=\"ImageUpload(" + (strScenarioId > 0 ? 2 : 1) + ")\" style=\"cursor:pointer;\" class=\"al_center\" />");
				}
			break;
			case "FMS":
				if(strAttach != "") {
					intPageCount = frm["PAGE_COUNT"+strOrder].value.trim();
				
					if(window.opener.FMS.Files.length < 1) {
						window.opener.$("#SUBJECT").val(strSubject);
						window.opener.document.frmFMS.CONVERT_FILE_SIZE.value = strFileSize;
						window.opener.document.frmFMS.CONVERT_CURRENT_PAGE.value = intPageCount;
					} else {
						window.opener.document.frmFMS.CONVERT_FILE_SIZE.value = 0;
						window.opener.document.frmFMS.CONVERT_CURRENT_PAGE.value = 0;
					}
					//opener.ttfAddFile("divFileList", strAttach);
					window.opener.FMS.AddFile(strAttach,strAttach.substring(strAttach.lastIndexOf('/')+1),intPageCount);
				} else {
					alert("파일이 존재하지 않습니다.");
				}
			break;
			case "VMS":
				lngDuration = frm["DURATION"+strOrder].value.trim();

				window.opener.$("#VoiceSubject").html(strSubject);
				window.opener.$("#VoiceDate").html(strCreateDate.substring(0,4) + "년 " + strCreateDate.substring(5,7) + "월 " + strCreateDate.substring(8,10) + "일");
				window.opener.$("#VoiceSeqNo").val(strSeqno);
				window.opener.$("#RECORD_TAB_FILE").val(strAttach);
				
				window.opener.document.frmVMS.CONVERT_FILE_SIZE.value = strFileSize;
				window.opener.document.frmVMS.CONVERT_DURATION.value = lngDuration;
			break;
			case "SURVEY":
				lngDuration = frm["DURATION"+strOrder].value.trim();
				
				window.opener.document.frmSURVEY.CONVERT_FILE_SIZE.value = strFileSize;
				window.opener.document.frmSURVEY.CONVERT_DURATION.value = lngDuration;

				window.opener.LoadSurvey(1,strSeqno);
			break;
		}

		window.close();
	}

	// 엑셀에서 전화번호 불러오기
	function ExcelLoader(type) {
		popWin = window.open('/Sms/PopExcelFile.jsp?type=' + type + '&stype=' + type,'POPUP','width=310,height=530,resizable=0,toolbars=no,menubar=no,status=no');
	}

	// 주소록에서 전화번호 불러오기
	function AddressBookLoader(type) {
		popWin = window.open('/Address/PopAddress.jsp?type=' + type + '&stype=' + type,'POPUP','width=530,height=556,resizable=0,toolbars=no,menubar=no,scrollbars= no,status=no');
	}

	// 내 모음함에서 불러오기
	function MessageBoxLoader() {
		var url = document.location.href;

		if(url.indexOf('VmsMessage.jsp') > -1) 
			popWin = window.open('/Result/PopMessageBox.jsp?stype=VMS','POPUP','width=392,height=483,resizable=no,scrollbars=yes');
		else if(url.indexOf('VmsMessage2.jsp') > -1) 
			popWin = window.open('/Result/PopMessageBox.jsp?stype=VMS','POPUP','width=392,height=483,resizable=no,scrollbars=yes');
		else if(url.indexOf('SmsMessage.jsp') > -1) 
	 		popWin = window.open('/Result/PopMessageBox.jsp?stype=SMS&size=9','POPUP','width=502,height=714,resizable=no,scrollbars=yes');
		else if(url.indexOf('SmsEmoticon.jsp') > -1) 
	 		popWin = window.open('/Result/PopMessageBox.jsp?stype=SMS&size=9','POPUP','width=502,height=714,resizable=no,scrollbars=yes');
		else if(url.indexOf('MmsMessage.jsp') > -1) 
			popWin = window.open('/Result/PopMessageBox.jsp?stype=PMS&size=6','POPUP','width=660,height=758,resizable=no,scrollbars=yes');
		else if(url.indexOf('PhotoMessage.jsp') > -1) 
			popWin = window.open('/Result/PopMessageBox.jsp?stype=PMS&size=6','POPUP','width=660,height=758,resizable=no,scrollbars=yes');
		else if(url.indexOf('FmsMessage.jsp') > -1) 
			popWin = window.open('/Result/PopMessageBox.jsp?stype=FMS','POPUP','width=592,height=483,resizable=no,scrollbars=yes');
		else if(url.indexOf('FmsMessage2.jsp') > -1) 
			popWin = window.open('/Result/PopMessageBox.jsp?stype=FMS','POPUP','width=392,height=483,resizable=no,scrollbars=yes');
		else if(url.indexOf('VmsSurvey.jsp') > -1) 
			popWin = window.open('/Result/PopMessageBox.jsp?stype=SURVEY','POPUP','width=392,height=483,resizable=no,scrollbars=yes');
		else
			alert('올바른 접근이 아닙니다');
	}

	// 메시지 회신번호 입력(selectbox)
	function SenderSelect(strSelectSender, frm) { 
		if( strSelectSender == "DIRECT" ) {
			frm.SELECT_SENDER.style.display = "none";
			frm.SENDER.style.display = "";
			frm.SENDER.value = "";
			if($('#divPhoto').length > 0 && $('#divPhoto').css('display') == 'none') {}
			else frm.SENDER.focus();
			if(frm.SELECT_SENDER.options.length > 1)
				document.getElementById("spanSelectBox").style.display = "";
		} else {
			frm.SELECT_SENDER.style.display = "block";
			frm.SENDER.style.display = "none";
			frm.SENDER.value = strSelectSender;
			if( frm.SELECT_SENDER.options[frm.SELECT_SENDER.selectedIndex].value == "DIRECT" ) {
				frm.SELECT_SENDER.options[0].selected = true;
			}
			document.getElementById("spanSelectBox").style.display = "none";
		}
	}

	// 그룹 주소록에서 전송
	function AddressGroupReceivers(type, gseqs) {
		var arr = gseqs.split(',');
		document.body.height = '1000px';
		Util.Converting();
		$('#Converting').css({'top':'330px'});
		
		if(arr.length > 0) {
			$.ajax({ 
				url : '/Address/divGroupReceiverList.jsp', 
				type : 'post', 
				data : { gseq : gseqs.split(','), stype : type.toLowerCase() }, 
				success : function(res) {
					if(res != null && res.trim() != '') {
						document.getElementById("divReceiverList").innerHTML += res;
						setReceiverCount();

						// 마지막 cntReceiver 설정
						var temp = $('li[name=SubReceiveContent]');
						if(temp != null && temp.length > 0) {
							var idx = temp[temp.length-1].id.replace('SubReceiveContent','');
							cntReceiver = idx;
						}
						
						// alert 실행안되서 임시방편;
						var mgroups = res.match(/\<script\>(.+)\<\/script\>/i);
						if(mgroups != null && mgroups.toString() != '') eval(mgroups[1]);

						Util.Converted();
					}
				},
				error : function() {
					alert('에러가 발생했습니다.');
					Util.Converted();
				}
			});
		} else alert('메시지를 발송할 그룹을 선택해 주십시오.');			
	}
	
	// 발송할 수 없는 문자 체크
	function CheckMessage() {
		var content = $('#CONTENT').val().replace(Util.DefaultKeyword,'');

		var res = $.ajax({ 
			url : '/Sms/CheckCharacters.jsp', 
			type : 'post',
			data : { msg : content },
			async : false
		}).responseText;
		
		if(res.trim() == 'true') return true;
		else {
			alert('지원되지 않는 문자가 포함되어 있습니다.\n수정 후 발송해주시기 바랍니다.\n\n수정할 문자 : ' + res);
			return false;
		}
	}
	
	// 발송할 인증여부 체크
	function CheckSendCount(cnt) {
		var res = $.ajax({ 
			url : '/Sms/CheckSendCount.jsp', 
			type : 'post',
			data : { sid : 'COM', count : cnt },
			async : false,
			timeout : 10000
		}).responseText;
		
		if(res != null) {
			res = res.trim();
			if(NumberCheck(res)) {
				res = parseInt(res);
			}	else {
				alert(res);
				return -999999;
			}
		} else {
			alert('오류가 발생하였습니다.');
			return -999999;
		}
		
		return res;
	}	
	
	// 전송전 미리보기
	function Preview2Send() { 
jQuery(document).ready(function() {
		try {
			var sendtype = Util.GetNamedValue('scheduleType');
			var type = document.getElementById('MSGTYPE').value;
			var ifrm = document.getElementById('PREVIEW');
			var frmtype = type;
			var file = '';
			var frm = '';
			var h = 416;
			if($('#CONVERT_FILE').length > 0) file = $('#CONVERT_FILE').val();

			if(type == 'MMS' && file != '') h = 506;
			else if(type == 'MMS') frmtype = 'SMS';

			if(sendtype == 'RESERVE') h = h + 26;
			else if(sendtype == 'REPEAT') h = h + 46;

			if(frmtype == 'MMS') frm = document.frmMMS;
			else if(frmtype == 'VMS') frm = document.frmVMS;
			else if(frmtype == 'SURVEY') frm = document.frmSURVEY;
			else if(frmtype == 'FMS') frm = document.frmFMS;
			else frm = document.frmSMS;
				
			//eval('frm = document.frm'+ frmtype);
			$('input[name=receiverInfo]').attr('disabled','disabled');
			$('input[name=receiverPhone]').attr('disabled','disabled');
	
			Util.Overlay(20);
			
			if(ifrm == null) {
//alert(window.innerHeight+','+$(document).scrollTop()+','+document.body.clientHeight);

				if(isIE && jQuery.browser.version < 9)
					ifrm = document.createElement('<iframe name="PREVIEW">');
				else ifrm = document.createElement('iframe');

				ifrm.id = 'PREVIEW';
				ifrm.name = 'PREVIEW';
				ifrm.frameBorder = 0;
				ifrm.marginWidth = 0;
				ifrm.marginHeight = 0;
				ifrm.scrolling = 'no';
				ifrm.style.position = 'absolute';
				ifrm.style.zIndex = 9999;
				//ifrm.style.top = ((document.body.scrollHeight + document.body.scrollTop)/2 - 100 - (h/2)) + 'px';
				ifrm.style.top = (document.body.clientHeight-h)/2 - 50 + $(document).scrollTop() + 'px';
				ifrm.style.left = '50%';
				ifrm.style.marginLeft = '-195px';
				ifrm.width = '392px';
				ifrm.height = h + 'px';

		  	document.body.appendChild(ifrm);
			}
	
			ifrm.height = h + 'px';
			ifrm.style.display = 'inline';
			frm.action = '/Sms/PreviewMessage.jsp';
			frm.target = 'PREVIEW';		
			frm.submit();
			
			$('input[name=receiverInfo]').attr('disabled','');
			$('input[name=receiverPhone]').attr('disabled','');			
		} catch(err) {
			return;
		}
});
	}	

	window.onunload = function() {
		if(popWin != null) popWin.close();
	}

	// 클릭투콜 발송
	function SendCTC(strReceiverInfo) {
		var frm = document.frmCTC;

		if( strReceiverInfo != "" ) {
			arrReceiverInfo = strReceiverInfo.split("|");

			if( isPhone(arrReceiverInfo[1], "MOBILE") == true ) {
				document.getElementsByName("receiverInfo")[0].value = strReceiverInfo;
				Util.SetCookie('SUBMIT',Math.floor(Math.random()*1000000),1);
				frm.target = "HiddenFrame";
				frm.submit();
				top.focus(); 
			} else {
				alert("전화번호 형식이 맞지 않습니다.");
				return;
			}
		}

/*		$.ajax({ 
			url : "/Sms/SmsProc.jsp", 
			type : "post", 
			data : { MSGTYPE : "VMS", SENDER : gstrDefaultNumber, REPLY_TYPE : "3", "RECEIVER_INFO[]" : escape(encodeURIComponent(strReceiveInfo)) }, 
			success : function(res) { 
				alert(res);
			},
			error : function() {
				alert('에러가 발생했습니다.');
			}
		});*/

/*		$.post("/Sms/SmsProc.jsp", {
				MSGTYPE : "VMS", SENDER : gstrDefaultNumber, REPLY_TYPE : "3", "RECEIVER_INFO[]" : strReceiveInfo
			},
			function(data) {
alert(data);
			},"json"
		);*/
		
	}
	
	// 개별메세지 타입 체크
	function Check2Replace() {
		try {
			var content = $('#CONTENT').val();
			var hasRplmt = (content.indexOf('[이름]') > -1 || content.indexOf('[개별정보1]') > -1 || content.indexOf('[개별정보2]') > -1 || content.indexOf('[회사명]') > -1 || content.indexOf('[부서]') > -1);
			$('#CONVERT_TYPE').val('FALSE');
	
			if(!hasRplmt) {
				return true;
			}	else if(hasRplmt) {
				var receivers =	$('input[name=receiverInfo\[\]]');
				var msgtype = $('#MSGTYPE').val();
				var sms = new Array();
				var mms = new Array();
				var nmsg = new Array();
				
				if((msgtype == 'MMS' && receivers.length > 300) || (msgtype == 'SMS' && receivers.length > 2000)) {
					alert('개별메시지는 한번에 단문 2000건, 장문(포토) 300건 이상\n 발송할 수 없습니다.');
					return false;
				} else {
					$('li[name=SubReceiveContent]').css({'background-color':'#ffffff'});
					
					if(msgtype != 'MMS' && hasRplmt) {
						for(i=0;i<receivers.length;i++) {
							var temp = receivers[i].value.split('\|');
							var cont = content.replace('\[이름\]',temp[0]);
							cont = cont.replace('\[개별정보1\]',temp[2]);
							cont = cont.replace('\[개별정보2\]',temp[3]);
							if(temp.length > 5) {
								cont = cont.replace('\[회사명\]',temp[4]);
								cont = cont.replace('\[부서\]',temp[5]);
							}

							if(Util.GetBytes(cont) > 80) {
								mms.push(new Array($(receivers[i]).parent().attr('id'),cont));
								$(receivers[i]).parent().css({'background-color':'#f9b8a4'});
							} else if(cont == '') {
								nmsg.push(new Array($(receivers[i]).parent().attr('id'),cont));
								$(receivers[i]).parent().css({'background-color':'#f9b8a4'});								
							}	else
								sms.push(new Array($(receivers[i]).parent().attr('id'),cont));
						}

						/*if(nmsg.length > 0 && mms.length > 0) {
							alert('80bytes가 넘는 메시지가 ' + mms.length + '건, 내용이 없는 메시지가 ' + nmsg.length + '건이 있어 발송할 수 없습니다!\n수신자 목록 중 붉은색 목록을 삭제하시거나\n메시지 내용을 수정해주세요!');
						} else if(nmsg.length > 0) {
							alert('내용이 없는 메시지가 ' + nmsg.length + '건이 있어 발송할 수 없습니다!\n수신자 목록 중 붉은색 목록을 삭제하시거나\n메시지 내용을 수정해주세요!');
						}	else */if(mms.length > 0) {
							alert('90bytes가 넘는 메시지가 ' + mms.length + '건이 있어 발송할 수 없습니다!\n수신자 목록 중 붉은색 목록을 삭제하시거나\n메시지 내용을 수정해주세요!');
						} else {
							$('#CONVERT_TYPE').val('TRUE');
							return true;
						}
					} else if(hasRplmt) {
						$('#CONVERT_TYPE').val('TRUE');
						return true;
					}
				}
			}
		} catch(ex) {
			return false;
		}
		return false;
	}

	// 개별메세지 선태버튼
	function SelectReplacement(status) {
		if(status != null) {
			if($('#Replacements').css('display') == 'none' && !status) return;
			else if($('#Replacements').css('display') != 'none' && status) return;
		}
	
		$('#Replacements').toggle();
		if($('#Replacements').css('display') == 'none') 
			document.getElementById('btnReplacement').src = document.getElementById('btnReplacement').src.replace('on','off');
		else
			document.getElementById('btnReplacement').src = document.getElementById('btnReplacement').src.replace('off','on');
	}
	
	// 개별메세지 항목 입력
	function AddReplacement(obj) {
		var content = $('#CONTENT').val().trim();
		if(content == '여기에 메시지를 입력하세요.') $('#CONTENT').val('');
    $('#CONTENT').focus();
    
    var cont = document.getElementById('CONTENT');
    var c = '[' + obj.innerHTML.replace(/&lt;/g,'<').replace(/&gt;/g,'>').replace(/&amp;/g,'&') + ']';

 		Util.Insert2Textarea(cont,c);
  	
    Emoticon.ShowBytes();
    $('#CONTENT').focus();
	$('#Replacements').css({'display':'none'});
    //$(obj).parent().css({'display':'none'});
    document.getElementById('btnReplacement').src = document.getElementById('btnReplacement').src.replace('on','off');
	}
	
		
	function wait(msecs)
	{
	var start = new Date().getTime();
	var cur = start;
	while(cur - start < msecs)
		{
			cur = new Date().getTime();
		}
	}
	
	function warning() {
		var str = '<div style="border:1px solid red;padding:4px;background-color:#ffbf35;color:#000000;text-align:center;">';
		if(!isIE) {
			str += '<strong>지금 사용중인 브라우저에서는 지원이 되지 않습니다. Internet Explorer를 이용해 주세요.</strong>';
		} else {
			str += '<a href="/ActiveX2/Setup/XroshotInstall2.exe"><strong>음성 메시지 발송을 위해서는 전용 프로그램이 필요합니다. 설치하시려면 여기를 [클릭] 하세요.</strong></a>';
		}
		
		str += '</div>';
		
		if($('#divActiveX').length > 0) {
			$('#divActiveX').html(str);
 
			if(objTtsPlayer == null || objVoiceUploader == null) $('#divActiveX').css('display','block');
		}
	}