var AddressPop = null;

var AddressBook = {
	Flag : false,
	Checked : false,
	Names : new Array(),
	Phones : new Array(),
	Mobiles : new Array(),
	Faxes : new Array(),
	UserCount : 0,
	Init : function() {
		this.Flag = false;
		this.Checked = false;
		this.Names = new Array();
		this.Phones = new Array();
		this.Mobiles = new Array();
		this.Faxes = new Array();
		this.UserCount = 0;
	},
	AddArray : function(name, phone) {
		this.Names.push(name);
		this.phones.push(phone);
	},
	GetForm : function() {
		var frm = null;
		var seq = null;
		var gseq = null;

		if($('#frmAddress').length < 1) {
			frm = document.createElement("form");
			frm.method = 'POST';
			frm.id = 'frmAddress';
			frm.target = 'POPUP';
			frm.target = 'POPADDRESS';
			seq = document.createElement("input");
			seq.name = 'seq';
			seq.id = 'inpSeq';
			frm.appendChild(seq);
			gseq = document.createElement("input");
			gseq.name = 'gseq';
			gseq.id = 'inpGseq';
			frm.appendChild(gseq);
			document.body.appendChild(frm);
		} else {
			frm = document.getElementById('frmAddress');
		}
		
		return frm;
	},
	Save : function() { // 주소 저장_한명씩 추가
		var seqno = Util.GetValue('seqno');
		var name = Util.GetValue('name');
		var group = Util.GetValue('group');
		var cellphone1 = Util.GetValue('cellphone1').replace(/[-\.]/gi,'');
		var phone = Util.GetValue('phone').replace(/[-\.]/gi,'');
		var fax = Util.GetValue('fax');
		var officephone = Util.GetValue('officephone');
		var emailid = Util.GetValue('emailid');
		var emailprovider = Util.GetValue('emailprovider');
		var company = Util.GetValue('company');
		var department = Util.GetValue('department');
		var address1 = Util.GetValue('companyaddress1');
		var address2 = Util.GetValue('companyaddress2');
		var zipcode1 = Util.GetValue('zipcode1');
		var zipcode2 = Util.GetValue('zipcode2');
		var zipcode = '';
		var memo = Util.GetValue('memo');
		var info1 = $('#info1').val().trim();
		var info2 = $('#info2').val().trim();
		
		if(zipcode1 != '') zipcode = zipcode1 + '-' + zipcode2;
		
				
		if(Util.IsNullOrEmpty(name) && Util.IsNullOrEmpty(cellphone1) && Util.IsNullOrEmpty(phone) && Util.IsNullOrEmpty(fax)) {
			alert('기본정보를 입력해주세요.');
		}
		else if(seqno == 0 && Util.IsNullOrEmpty(group)) {
			alert('그룹을 선택해주세요');
		}
		else if(!Util.IsNullOrEmpty(cellphone1) && !isPhone(cellphone1, '')) {
			alert('문자 받는 번호가 올바르지 않습니다.');
		}
		else if(!Util.IsNullOrEmpty(phone) && !isPhone(phone, '')) {
			alert('음성 받을 번호가 올바르지 않습니다.');
		}
		else if(!Util.IsNullOrEmpty(fax) && (fax.match(/[^0-9\-]/g) != null)) {
			alert('팩스 받는 번호가 올바르지 않습니다.');
		}
		else if(address1.indexOf('$') > -1 || address2.indexOf('$') > -1) {
			alert('주소에 사용할 수 없는 문자가 있습니다.');
		}
		else if(Util.GetBytes(info1) > 20 || Util.GetBytes(info2) > 20) {
			alert('개별메시지용 정보는 최대 20Bytes까지 입력가능합니다.');
		}
		else {
			var email = '';
			var address = address1 + '$' + address2;
			if(!Util.IsNullOrEmpty(emailid) && !Util.IsNullOrEmpty(emailprovider)) email = emailid + '@' + emailprovider;
		  if(!Util.IsNullOrEmpty(email) && !IsMail(email)) { 
		  	alert('이메일이 올바르지 않습니다.');
		  	return;
			}
			if(Util.IsNullOrEmpty(name)) name = '이름없음';
			if(group == null || group == '') group = 0;

			cellphone1 = cellphone1.replace('-','');
			phone = phone.replace('-','');
			
			if(seqno == 0) {
				var res = $.ajax({ 
					url : '/Address/AddressDuplicated4MultiSave.jsp', 
					type : 'post',
					data : { sms : cellphone1, vms : phone, fms : fax }, 
					async : false
				}).responseText;

				if(res.trim() != '') {
					if(!confirm('중복번호가 있습니다.\n그래도 저장하시겠습니까?')) return;
				}				
			}
			
			var Arr = new Array(seqno, name, group, cellphone1, phone, fax, officephone, email, company, department, address, zipcode, memo, info1, info2);
			$.ajax({ 
				url : '/Address/SaveAddress.jsp', 
				type : 'post', 
				data : { params : Arr }, 
				success : function(res) { 
						if(res.trim() == 'true') {
							if(seqno > 0) alert('수정되었습니다.');
							else alert('입력되었습니다.');
							window.close();
							window.opener.location.reload();
						} else alert(res.trim());
				},
				error : function(res) {
					alert('에러가 발생했습니다.');
				}
			});
		}
		
		return;
	},
	MultiSave : function(n) { // 주소록 한꺼번에 추가
		if((AddressBook.Checked || this.CheckMultiSave())) {
			if(n < this.Names.length) {
				var group = $('#group').val().trim();
				var result = false;
				
				var Arr = new Array(0, this.Names[n], group, this.Mobiles[n], this.Phones[n], this.Faxes[n], '', '', '', '', '', '', '','','');
				
				$.ajax({ 
					url : '/Address/SaveAddress.jsp', 
					type : 'post', 
					data : { params : Arr }, 
					success : function(res) {
						if(res.trim() == 'true') {
							AddressBook.UserCount++;
							AddressBook.MultiSave(n+1);
						}
					},
					error : function(res) {
						result = false;
					}
				});
			} else {
				if(this.Names.length < 1) alert('등록할 주소를 차례로 입력해주세요.');
				else {
					alert(AddressBook.UserCount + '명의 주소가 입력되었습니다.');
					if(AddressBook.Flag) {
						AddressBook.InitMultiSave();
						window.opener.AddressBook.Flag = true;
					}
					else {
						window.close();
					}
					window.opener.location.reload();
					return;
				}
			}
		} else this.Init();
	},
	CheckMultiSave : function() { // 주소록 한꺼번에 추가(형식 & 중복 체크)
		var name = $('#name0');
		var mobile = $('#cellphone10');
		var phone = $('#phone0');
		var fax = $('#fax0');
		var group = $('#group');

		if(Util.IsNullOrEmpty(group.val())) {
			alert('그룹을 선택해주세요');
			return false;
		}
		else {		
			for(var i=1;name.length > 0;i++) {
				var _name = name.val().trim();
				var _mobile = mobile.val().replace(/\D/gi,'');
				var _phone = phone.val().replace(/\D/gi,'');
				var _fax = fax.val().replace(/\D/gi,'');
				
				if(Util.GetBytes(_name) > 128) {
					alert(i + '번째 이름을 한글64자, 영문128자 이내로 입력해주세요.');
					return false;
				} else if(!Util.IsNullOrEmpty(_mobile) && !isPhone(_mobile, '')) {
					alert(i + '번째 문자받는 번호가 올바르지 않습니다.');
					return false;
				}	else if(!Util.IsNullOrEmpty(_phone) && !isPhone(_phone, '')) {이
					alert(i + '번째 음성받는 번호가 올바르지 않습니다.');
					return false;
				}	else if(!Util.IsNullOrEmpty(_fax) && !isPhone(_fax, '')) {
					alert(i + '번째 팩스받는 번호가 올바르지 않습니다.');
					return false;
				}

				for(var j=0;j<this.Names.length;j++) {
					if(_mobile != '' || _phone != '' || _fax != '') {
						if(this.Mobiles[j] == _mobile && this.Phones[j] == _phone && this.Faxes[j] == _fax) {
							alert('입력하신 주소 중 중복되는 내용이 있습니다.');
							return false;
							break;
						}
					}
				}
				
				if(_name != '' || _mobile != '' || _phone != '' || _fax != '') {
					if(_name == '') _name = '이름없음';
					
					this.Names.push(_name);
					this.Mobiles.push(_mobile);
					this.Phones.push(_phone);
					this.Faxes.push(_fax);
				}
				
				var name = $('#name' + i);
				var mobile = $('#cellphone1' + i);
				var phone = $('#phone' + i);
				var fax = $('#fax' + i);
			}
			
			if(this.Names.length > 0) { // 기존 주소록에 중복된 번호가 있는지 체크
				var res = $.ajax({ 
					url : '/Address/AddressDuplicated4MultiSave.jsp', 
					type : 'post',
					data : { sms : AddressBook.Mobiles, vms : AddressBook.Phones, fms : AddressBook.Faxes }, 
					async : false
				}).responseText;				

				if(res.trim() == '') {
					AddressBook.Checked = true;
					return true;
				}
				else {
					//alert(res.trim() + '번째에 중복된 번호가 있습니다.');
					//return false;
					if(confirm(res.trim() + '번째에 중복번호가 있습니다.\n그래도 저장하시겠습니까?')) {
						AddressBook.Checked = true;
						return true;
					}
					else {
						return false;
					}
				}
			} else { 
				alert('입력할 주소가 없습니다.'); 
				return false; 
			}
		}
	},
	InitMultiSave : function() { // 주소록 한꺼번에 추가(초기화)
		var name = $('#name0');
		var cellphone1 = $('#cellphone10');
		var phone = $('#phone0');
		var fax = $('#fax0');
		var group = $('#group');
		group.value = '';

		for(var i=1;name.length > 0;i++) {
			name.val('');
			cellphone1.val('');
			phone.val('');
			fax.val('');
			
			var name = $('#name' + i);
			var cellphone1 = $('#cellphone1' + i);
			var phone = $('#phone' + i);
			var fax = $('#fax' + i);
		}

		AddressBook.Init();
	},
	Search : function() {
		var keyword = Util.GetValue('SearchKeyword');
		var type = $('#SearchType').val();
		if(keyword == Util.DefaultKeyword) keyword = '';
		
		var link = document.location.href;
		link = Util.GetURLWithParameter(link,'page',1);
		link = Util.GetURLWithParameter(link,'stype',type);
		link = Util.GetURLWithParameter(link,'keyword',keyword);
		
		document.location.href = link;
	},
	SelectGroup : function(gseq) {
		var link = document.location.href;
		link = Util.GetURLWithParameter(link,'page','');
		link = Util.GetURLWithParameter(link,'gseq',gseq);
		
		document.location.href = link;
	},
	SelectChar : function(char) {
		var link = document.location.href;
		link = Util.GetURLWithParameter(link,'page','');
		link = Util.GetURLWithParameter(link,'char',char);
		
		document.location.href = link;
	},
	Delete : function(gseq) { // 주소 삭제 NEW
		var seq = $('#SeqList').val().split(',');
		
		if(seq != '') {
			if(confirm('해당 주소를 삭제하시겠습니까?')) {
				$.ajax({ 
					url : '/Address/DeleteAddress.jsp', 
					type : 'post', 
					data : { seq : seq, gseq : gseq }, 
					success : function(res) { 
						if(res.trim() == 'true') {
							alert('삭제되었습니다');
							window.opener.document.location.reload();
							window.close();
						}
						else
							alert(res.trim());
					},
					error : function() {
						alert('에러가 발생했습니다.');
					}
				});
			}
		} else alert('삭제할 주소를 선택해주세요.');
	},
	AddGroup : function() { // 해당 주소에 그룹 추가(복사) NEW
		if(window.opener.Util) {
			if(window.opener.Util.CheckBoxes.length > 0) { // 주소
				if(Util.CheckBoxes.length > 0) { // 그룹
					var arr = window.opener.Util.CheckBoxes.join(',').split(',');

					$.ajax({ 
						url : '/Address/AddGroup2Address.jsp', 
						type : 'post', 
						data : { seq : arr, gseq : Util.CheckBoxes.join(',') }, 
						success : function(res) { 
							var cnt = res.trim();
							if(NumberCheck(cnt)) {
								if(cnt != arr.length) alert('중복된 번호가 포함된 ' + (Util.CheckBoxes.length * arr.length - cnt) + '건 제외\n ' + cnt + '개 복사가 완료되었습니다.'); 
								else alert(res.trim() + '개 복사가 완료되었습니다.'); 
								window.close();
								window.opener.document.location.reload();
							}
							else
								alert(res.trim());
						},
						error : function() {
							alert('에러가 발생했습니다.');
						}
					});
				} else alert('그룹을 선택해주세요.');
			} else alert('복사할 주소를 선택해주세요.');
		}
	},
	Move : function() { // 해당 주소에 그룹 이동 NEW
		if(window.opener.Util) {
			if(window.opener.Util.CheckBoxes.length > 0) { // 주소
				if($('input[name=radio]:checked').length > 0) { // 그룹
					var radio = $('input[name=radio]:checked');
					var arr = window.opener.Util.CheckBoxes.join(',').split(',');

					Util.Saving();
		
					$.ajax({ 
						url : '/Address/MoveAddress.jsp', 
						type : 'post', 
						data : { seq : arr, gseq : $('#GroupSeqNo').val(), gseq2 : radio[0].value }, 
						success : function(res) { 
							var cnt = res.trim();
							if(NumberCheck(cnt)) {
								if(cnt != arr.length) alert('중복된 번호가 포함된 ' + (arr.length - cnt) + '건 제외 ' + cnt + '개 이동이 완료되었습니다.'); 
								else alert(res.trim() + '개 이동이 완료되었습니다.'); 
								window.close();
								window.opener.document.location.reload();
							}
							else
								alert(res.trim());
								
							Util.Done();
						},
						error : function() {
							alert(res.trim());
							//alert('에러가 발생했습니다.');
							Util.Done();
						}
					});
				} else alert('그룹을 선택해주세요.');
			} else alert('이동할 주소를 선택해주세요.');
		}
	},
	SaveGroup : function() {
		var gseq = Util.GetValue('GroupSeqNo');
		var gorder = Util.GetValue('GroupNo');
		var gname = Util.GetValue('GroupName');

		if(!NumberCheck(gorder)) {
			alert('자연수를 입력해주세요.');
		}	else if(gname == '') {
			alert('그룹명을 입력해주세요');
		}	else {	
			$.ajax({ 
				url : '/Address/SaveAddressGroup.jsp', 
				type : 'post', 
				data : { gseq : gseq, name : gname, order : gorder }, 
				success : function(res) { 
					res = res.trim();
					
					if(res.indexOf('true') > -1) {
						alert('입력되었습니다.');
						window.close();
						
						// 외부주소록 등록의 경우
						if(window.opener.document.location.href.indexOf('AddressExternal') > -1) {
							gseq = res.trim().split('$')[1];
							window.opener.Util.ChangeURLParameter('gseq',gseq);
						}	else
							window.opener.location.reload();
					}	else
						alert(res);
				},
				error : function() {
					alert('에러가 발생했습니다.');
				}
			});
		}
	},
	DeleteGroup : function() {
		var isDeleteAll = false;

		if(CheckBox.Values.length < 1) {
			alert('삭제할 그룹을 선택해주세요');
		}
		else {
			if(confirm('그룹에 속한 주소록도 모두 삭제하시겠습니까?')) {
					isDeleteAll = true;				
			}
			
			if(confirm('한번 삭제한 주소록/그룹은 복구할 수 없습니다.\n정말 삭제하시겠습니까?')) {
				$.ajax({ 
					url : '/Address/DeleteAddressGroup.jsp', 
					type : 'post', 
					data : { gseqs : CheckBox.Values, flag : isDeleteAll }, 
					success : function(res) { 
						if(res.trim() == 'true') {
							alert('삭제되었습니다.');
							document.location.reload();
						}	else
							alert(res.trim());
					},
					error : function() {
						alert('에러가 발생했습니다.');
					}
				});
			}
		}
	},
	Deleter : function() { // 주소 삭제 NEW
		if(Util.CheckBoxes.length < 1) {
			alert('삭제할 주소를 선택해주세요');
		}
		else {
			var frm = this.GetForm();
			var seq = document.getElementById('inpSeq');
			var gseq = document.getElementById('inpGseq');
			AddressPop = window.open('','POPADDRESS','width=392,height=382');
			
			frm.action = '/Address/AddressDeleter.jsp';
			seq.value = Util.CheckBoxes.join(',');
			gseq.value = $('#GroupSeqNo').val();
			frm.submit();
		}
	},
	Copier : function() { // 주소 복사 NEW
		if(Util.CheckBoxes.length < 1) {
			alert('복사할 주소를 선택해주세요');
		}
		else {
			var frm = this.GetForm();
			var seq = document.getElementById('inpSeq');
			var gseq = document.getElementById('inpGseq');
			AddressPop = window.open('','POPADDRESS','width=392,height=371');
			
			frm.action = '/Address/AddressGroupUpdator.jsp?type=ADD';
			gseq.value = $('#GroupSeqNo').val();
			frm.submit();
		}		
	},
	Mover : function() { // 주소 복사 NEW
		if(Util.CheckBoxes.length < 1) {
			alert('이동할 주소를 선택해주세요');
		}
		else {
			var frm = this.GetForm();
			var seq = document.getElementById('inpSeq');
			var gseq = document.getElementById('inpGseq');
			AddressPop = window.open('','POPADDRESS','width=392,height=371');
			
			frm.action = '/Address/AddressGroupUpdator.jsp?type=MOVE';
			gseq.value = $('#GroupSeqNo').val();
			frm.submit();
		}		
	},	
	AddressSaver : function(gseq) {
		if(AddressPop != null) AddressPop.close();

		AddressPop = window.open('/Address/AddressSaver.jsp' + ((gseq>0)?"?gseq="+ gseq:""),'POPADDRESS','width=500,height=680,scrollbars=yes');
	},
	AddressEditor : function() {
		if(Util.CheckBoxes.length != 1) {
			alert('수정하실 한분을 선택해주세요');
		}
		else {
			this.ShowAddress(Util.CheckBoxes[0]);
		}
	},
	ShowAddress : function(seq) {
		if(AddressPop != null) AddressPop.close();
		AddressPop = window.open('/Address/AddressSaver.jsp?seq=' + seq,'POPADDRESS','width=500,height=680,scrollbars=yes');
	},
	GroupSaver : function() {
		if(AddressPop != null) AddressPop.close();
		AddressPop = window.open('AddressGroupSaver.jsp','POPADDRESS','width=430,height=260,scrollbars=yes');
	},
	GroupEditor : function() { 
		if(AddressPop != null) AddressPop.close();

		if(CheckBox.Values.length != 1) {
			alert('수정할 그룹을 하나만 선택해주세요.');
		}
		else {
			AddressPop = window.open('AddressGroupSaver.jsp?gseq=' + Util.CheckBoxes[0],'POPADDRESS','width=430,height=260,scrollbars=yes');
		}
	},
	Group2Excel : function() {
		var params = "";

		if(CheckBox.Values.length < 1) {
			alert('그룹을 선택해주세요');
		}
		else {		
			document.location.href = '/Address/AddressBook2Excel.jsp?gseq='+ CheckBox.Values.join('|');
		}
	},
	Pop : function(seq) {
		if(AddressPop != null) AddressPop.close();

		AddressPop = window.open('/Address/PopAddress.jsp?pop=1&gseq=' + seq,'POPADDRESS','width=0,height=0');
	},
	SelectTab : function(stype,tab) {
		var url = Util.GetURLWithParameter(document.location.href.split('?')[0], 'tab', tab);
		url = Util.GetURLWithParameter(url, 'type', stype);
		document.location.href = url;
	},
	Excel : function() {
		var params = document.location.href+'?';
		params = params.split('?')[1];
		var url = '/Address/AddressBook2Excel.jsp?'+ params;
		url = Util.GetURLWithParameter(url, 'gseq', $('#AddressGroup').val());
		
		document.location.href = url;	
	},

	Insert2SMS2 : function() { // 주소록 팝업에서 받는 사람입력
		//var stype = document.location.search.match(/MSGTYPE=([^\&]+)/gi);
		var stype1 = $('#MSGTYPE').val();
		var stype = window.opener.document.getElementById("MSGTYPE").value;	
		//alert(stype);
		//if(stype != null)	stype = stype[0].toString().replace('type=','');
		//else stype = 'sms';
		//if(stype !='email') { this.AddReceiverList();return; }
		var tab = $('#Tab').val();

		//alert(Util.CheckBoxes.length);
		if(Util.CheckBoxes.length < 1) {
		 if(tab == 1) alert('입력하실 그룹을 선택해주세요.');
		 else
			alert('입력하실 연락처를 선택해주세요.');		 	
		}
		else {			
			window.opener.delReceiverAll();
			window.opener.document.getElementById("RECEIVER_TYPE").value = 'A';

				for(i=0;i<Util.CheckBoxes.length;i++) {
					if(stype == 'email'){
						window.opener.Mail.AddEmail($('#NAME-' + Util.CheckBoxes[i]).val(), $('#SMS-' + Util.CheckBoxes[i]).val());
					}
					else if(stype == 'VMS'){
						window.opener.addReceiver($('#NAME-' + Util.CheckBoxes[i]).val(), $('#VMS-' + Util.CheckBoxes[i]).val());					
					}
					else if(stype == 'FMS'){
						window.opener.addReceiver($('#NAME-' + Util.CheckBoxes[i]).val(), $('#FMS-' + Util.CheckBoxes[i]).val());
					}
					else  if((stype == 'SMS' || stype == 'MMS') && stype1 != 'GRP'){
						window.opener.addReceiver($('#NAME-' + Util.CheckBoxes[i]).val(), $('#SMS-' + Util.CheckBoxes[i]).val());
						
					}else  if((stype == 'SMS' || stype == 'MMS') && stype1 == 'GRP'){
						window.opener.addReceiver2($('#NAME-' + Util.CheckBoxes[i]).val(), $('#GRPCNT-' + Util.CheckBoxes[i]).val(), $('#GRPNO-' + Util.CheckBoxes[i]).val());
						//alert(stype1);

						window.opener.document.getElementById("RECEIVER_TYPE").value = 'G';
						//window.opener.setReceiverCount3();
					}
				}
				//if(stype != 'GRP'){
					window.opener.setReceiverCount();
				//}
				window.close();
		}
	},
	Insert2SMS : function() { // 주소록 팝업에서 받는 사람입력
		var stype = document.location.search.match(/type=([^\&]+)/gi);
		if(stype != null)	stype = stype[0].toString().replace('type=','');
		else stype = 'sms';
		
		if(stype !='email') { this.AddReceiverList();return; }
		var tab = $('#Tab').val();
		alert(tab);
		alert(Util.CheckBoxes.length);
		
		if(Util.CheckBoxes.length < 1) {
		 if(tab == 1) alert('입력하실 그룹을 선택해주세요.');
		 else
			alert('입력하실 연락처를 선택해주세요.');		 	
		}
		else {
			if(tab == 1) {
				$.ajax({ 
					url : '/Address/XMLGroupAddresses.jsp', 
					type : 'get', 
					data : { gseq : Util.CheckBoxes }, 
					dataType : 'xml',
					success : function(xml) { 
						var group = $(xml).find('group');
						
						group.find('address').each(function() {
							if(document.location.href.indexOf('stype=email') > -1) {
								var email = $(this).find('email').text().trim();
								if(email != '')
									window.opener.Mail.AddEmail($(this).find('name').text().trim(), email);
							}
							/*else if(document.location.href.indexOf('stype=fms') > -1)
								window.opener.addReceiver($(this).find('name').text().trim(), $(this).find('fax').text().trim());
							else if(document.location.href.indexOf('stype=vms') > -1)
								window.opener.addReceiver($(this).find('name').text().trim(), $(this).find('phone').text().trim());
							else
								window.opener.addReceiver($(this).find('name').text().trim(), $(this).find('cellphone').text().trim());
								*/
						});
						window.close();
					},
					error : function() {
						alert('에러가 발생했습니다.');
					}
				});
			}	else {
				for(i=0;i<Util.CheckBoxes.length;i++) {
					if(stype == 'email')
						window.opener.Mail.AddEmail($('#NAME-' + Util.CheckBoxes[i]).val(), $('#NO-' + Util.CheckBoxes[i]).val());
					else
						window.opener.addReceiver($('#NAME-' + Util.CheckBoxes[i]).val(), $('#NO-' + Util.CheckBoxes[i]).val());
				}
				window.close();
			}
		}
	},
	AddReceiverList : function() {
		var tab = $('#Tab').val();
		var stype = document.location.search.match(/type=([^\&]+)/gi);
		if(stype != null)	stype = stype[0].toString().replace('type=','');
		else stype = 'sms';
		var sender = window.opener.$('#SELECT_SENDER').val().trim();
		if(sender == 'DIRECT') sender = window.opener.$('#SENDER').val();

		// 현재 입력되어 있는값 반환(중복체크용)
		var phone2 = new Array();
		window.opener.$('input[name=receiverPhone]').each(function(){
			phone2.push(this.value);
		});
		alert(tab);
		alert(Util.CheckBoxes.length);
		
		if(Util.CheckBoxes.length < 1) {
		 if(tab == 1) alert('입력하실 그룹을 선택해주세요.');
		 else
			alert('입력하실 연락처를 선택해주세요.');		 	
		}
		else {
			//window.opener.Util.Converting();
			if(tab == 1) { // 그룹 주소록
				$.ajax({ 
					url : '/Address/divGroupReceiverList.jsp', 
					type : 'post', 
					data : { gseq : Util.CheckBoxes, stype : stype, idx : window.opener.cntReceiver, phone2 : phone2, sender : sender }, 
					success : function(res) { 
						if(res != null && res.trim() != '') {
							window.opener.document.getElementById("divReceiverList").innerHTML += res;
							window.opener.setReceiverCount();
							
							// 마지막 cntReceiver 설정
							var temp = window.opener.$('li[name=SubReceiveContent]');
							if(temp != null && temp.length > 0) {					
								var idx = temp[temp.length-1].id.replace('SubReceiveContent','');
								window.opener.cntReceiver = idx;
							}

							// alert 실행안되서 임시방편;
							var mgroups = res.match(/\<script\>(.+)\<\/script\>/i);
							if(mgroups != null && mgroups.toString() != '') eval(mgroups[1]);
						}
						//window.close();
					},
					error : function() {
						alert('에러가 발생했습니다.');
					}
				});
			}
			else { // 일반 주소록
				this.Names = new Array();
				this.Phones = new Array();
				var info1 = new Array();
				var info2 = new Array();
				var company = new Array();
				var dept = new Array();
				
				for(i=0;i<Util.CheckBoxes.length;i++) {
					this.Names.push($('#NAME-' + Util.CheckBoxes[i]).val());
					this.Phones.push($('#NO-' + Util.CheckBoxes[i]).val());
					if($('#INFO1-' + Util.CheckBoxes[i]).length > 0) {
						info1.push($('#INFO1-' + Util.CheckBoxes[i]).val());
						info2.push($('#INFO2-' + Util.CheckBoxes[i]).val());
						company.push($('#COMPANY-' + Util.CheckBoxes[i]).val());
						dept.push($('#DEPT-' + Util.CheckBoxes[i]).val());
					} else {
						info1.push('');
						info2.push('');
						company.push('');
						dept.push('');
					}
				}
				
				$.ajax({ 
					url : '/Address/divReceiverList.jsp', 
					type : 'post', 
					data : { name : AddressBook.Names, phone : AddressBook.Phones, info1 : info1, info2 : info2, stype : stype, idx : window.opener.cntReceiver, phone2 : phone2, sender : sender, company : company, dept : dept }, 
					success : function(res) { 
						if(res != null && res.trim() != '') {
							//window.opener.Util.Converted();
							window.opener.document.getElementById("divReceiverList").innerHTML += res;
							window.opener.setReceiverCount();
														
							// 마지막 cntReceiver 설정
							var temp = window.opener.$('li[name=SubReceiveContent]');
							if(temp != null && temp.length > 0) {
								var idx = temp[temp.length-1].id.replace('SubReceiveContent','');
								window.opener.cntReceiver = idx;
							}
							
							// alert 실행안되서 임시방편;
							var mgroups = res.match(/\<script\>(.+)\<\/script\>/i);
							if(mgroups != null && mgroups.toString() != '') eval(mgroups[1]);							
						}
						//window.close();
					},
					error : function() {
						alert('에러가 발생했습니다.');
					}
				});
			}
		}
	},
	Excel2Receivers : function() {
		var stype = document.location.search.match(/type=([^\&]+)/gi);
		if(stype != null)	stype = stype[0].toString().replace('type=','');
		else stype = 'sms';
		
		// 현재 입력되어 있는값 반환(중복체크용)
		//var phone2 = new Array();
		//window.opener.$('input[name=RECEIVER_PHONE]').each(function(){
		//	phone2.push(this.value);
		//});
		alert(stype);
		$('#uploadAjaxForm').ajaxForm({
			url : '/ums/addrExcelUploadProcessAjax',
			type : 'post',
			dataType: "html",
			success : function(res) {
				alert(res);
				if(res != null) {
					window.opener.document.getElementById("divReceiverList").innerHTML += res;
					window.opener.setReceiverCount();
					alert(res);		
					// 마지막 cntReceiver 설정
					var temp = window.opener.$('li[name=SubReceiveContent]');
					if(temp != null && temp.length > 0) {
						var idx = temp[temp.length-1].id.replace('SubReceiveContent','');
						window.opener.cntReceiver = idx;
					}				
				}
				window.close();
			},
			error : function(res) {
				alert(res);
			}
		});
	},
	
	
	Send : function() { // 개인주소록 전송하기
		if(Util.CheckBoxes.length > 0) {
			var type = $('#SendType').val();
			if(type == 'FMS') url = '/Fms/FmsMessage.jsp';
			else if(type == 'PMS') url = '/Sms/MmsMessage.jsp';
			else if(type == 'VMS') url = '/Vms/VmsMessage.jsp';
			else if(type == 'SURVEY') url = '/Vms/VmsSurvey.jsp';
			else url = '/Sms/SmsMessage.jsp';

			url = gstrSvr + url;

			$.ajax({ 
				url : '/Address/Addresses2Send.jsp', 
				type : 'post', 
				data : { seq : Util.CheckBoxes.join(','), type : type }, 
				success : function(res) { 
					if(res != null && res.replace(/\<script\>.+\<\/script\>/gi,'').trim() != '') {
						// 번호 유무 체크를 이중으로하는걸 막기위해 html으로.........
						var sendForm = document.createElement("form");
						sendForm.action = url;
						sendForm.method = 'POST';
						var sendInput = document.createElement("input");
						sendInput.name = 'htmlreceivers';
						sendInput.value = res.trim();
						sendForm.appendChild(sendInput);
						document.body.appendChild(sendForm);
						sendForm.submit();
					} else alert('메시지가 발송될 번호가 등록되어 있지 않습니다.');
				},
				error : function(res) {
					alert(res);
				}
			});
		} else alert('메시지를 발송할 번호를 선택해 주십시오.');
	},
	GroupSend : function() { // 그룹주소록 전송하기		
		if(Util.CheckBoxes.length > 0) {
			var type = $('#SendType').val();
			if(type == 'FMS') url = '/Fms/FmsMessage.jsp';
			else if(type == 'PMS') url = '/Sms/MmsMessage.jsp';
			else if(type == 'VMS') url = '/Vms/VmsMessage.jsp';
			else if(type == 'SURVEY') url = '/Vms/VmsSurvey.jsp';
			else url = '/Sms/SmsMessage.jsp';

			url = gstrSvr + url;

			var sendForm = document.createElement("form");
			sendForm.action = url;
			sendForm.method = 'POST';
			var sendInput = document.createElement("input");
			sendInput.name = 'gseqs';
			sendInput.value = Util.CheckBoxes.join(',');
			sendForm.appendChild(sendInput);
			var sendType = document.createElement("input");
			sendType.name = 'type';
			sendType.value = type;
			sendForm.appendChild(sendType);
			document.body.appendChild(sendForm);
			sendForm.submit();
		} else alert('메시지를 발송할 그룹을 선택해 주십시오.');
	}
}


window.onunload = function() {
	try { 
		if(AddressPop != null && !AddressBook.Flag) AddressPop.close();
		else AddressBook.Flag = false;
	}catch(e) {
	}
}


function renderUploadList(data) {
    var addrExcelList;
    var usrNm = "";
    var hpNo = ""; 
    if(data.phoneNo != null) { hpNo = data.phoneNo; }
    if(data.addrName != null) { usrNm = data.addrName; } 
  
    validFailList = "<tr>" +
                  "<td scope='row'>" + escapeHtml(addrName) + "</td>" +
                  "<td scope='row'>" + escapeHtml(phoneNo) + "</td>" +
                  "</tr>";
    $("#addrExcelList").append(addrExcelList);
}