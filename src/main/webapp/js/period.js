var Period = {
	Start : new Date(),	
	End : new Date(),	
	Now : new Date(),
	Form : function() {
		document.write(this.SelectBox(this.Start, 'start') + '&nbsp;&nbsp;~&nbsp;&nbsp;' + this.SelectBox(this.End, 'end'));
	},
	SelectBox : function(dat, prefix) {
		var month = dat.getMonth() + 1;
		var maxOfDay = this.DaysOfMonth(dat.getFullYear(), month);
	
		write = '<select class="form-control unstyled" name="' + prefix + 'Year" id="' + prefix + 'Year">';
		for(i = new Date().getFullYear()+1; i > 2007; i--)
			write += '<option value="' + i + '"' + ((i == dat.getFullYear())?" selected":"") + '>' + i + '</option>';
		write += '</select> 년&nbsp;&nbsp;';
	
		write += '<select class="form-control unstyled" name="' + prefix + 'Month" id="' + prefix + 'Month" onchange="Period.ReloadDate(\'' + prefix + 'Day\', document.getElementById(\'' + prefix + 'Year\').value, this.value)">';
	
		for(i=1; i < 13; i++)
			write += '<option value="' + i + '"' + ((i == month)?" selected":"") + '>' + i + '</option>';
		write += "</select> 월&nbsp;&nbsp;";
		
		write += '<select class="form-control unstyled" name="' + prefix + 'Day" id="' + prefix + 'Day">';
		for (i = 1; i <= maxOfDay; i++)
			write += '<option value="' + i + '"' + ((i == dat.getDate())?" selected":"") + '>' + i + '</option>';	
		write += "</select> 일";
			
		return write;
	},
	SelectBoxFormWrite : function(strElement) {
		document.write(this.SelectBoxChange(this.Start, strElement));
	},
	SelectBoxChange : function(dat, prefix) {
		var month = dat.getMonth() + 1;
		var maxOfDay = this.DaysOfMonth(dat.getFullYear(), month);
	
		write = '<select class="form-control unstyled" name="' + prefix + 'Year" id="' + prefix + 'Year">';
		for(i = this.Now.getFullYear(); i < this.Now.getFullYear()+2; i++)
			write += '<option value="' + i + '"' + ((i == dat.getFullYear())?" selected":"") + '>' + i + '</option>';
		write += '</select> 년&nbsp;&nbsp;';
	
		write += '<select class="form-control unstyled" name="' + prefix + 'Month" id="' + prefix + 'Month" onchange="Period.ReloadDate(\'' + prefix + 'Day\', document.getElementById(\'' + prefix + 'Year\').value, this.value)">';
	
		for(i=1; i < 13; i++) {
			if( i < 10 ) {
				write += '<option value="0' + i + '"' + ((i == month)?" selected":"") + '>0' + i + '</option>';
			} else {
				write += '<option value="' + i + '"' + ((i == month)?" selected":"") + '>' + i + '</option>';
			}
		}
		write += "</select> 월&nbsp;&nbsp;";
		
		write += '<select class="form-control unstyled" name="' + prefix + 'Day" id="' + prefix + 'Day">';
		for (i = 1; i <= maxOfDay; i++) {
			if( i < 10 ) {
				write += '<option value="0' + i + '"' + ((i == dat.getDate())?" selected":"") + '>0' + i + '</option>';	
			} else {
				write += '<option value="' + i + '"' + ((i == dat.getDate())?" selected":"") + '>' + i + '</option>';	
			}
		}
		write += "</select> 일";
			
		return write;
	},
	SelectBoxDayFormWrite : function(strElement) {
		document.write(this.SelectBoxDay(strElement));
	},
	SelectBoxDay : function(prefix) {
		write = '<select class="form-control unstyled" name="' + prefix + 'Day" id="' + prefix + 'Day">';
		for( i = 1; i < 32; i++ ) {
			if( i < 10 ) {
				write += '<option value="0' + i + '">0' + i + '일</option>';
			} else {
				write += '<option value="' + i + '">' + i + '일</option>';
			}
		}
		write += "</select>";

		return write;
	},
	SelectBoxWeekFormWrite : function(strElement) {
		document.write(this.SelectBoxDay(strElement));
	},
	SelectBoxWeek : function(prefix) {
		write = '<select class="form-control unstyled" name="' + prefix + 'Week" id="' + prefix + 'Week">';
			write += '<option value="일요일">일요일</option>';
			write += '<option value="월요일">월요일</option>';
			write += '<option value="화요일">화요일</option>';
			write += '<option value="수요일">수요일</option>';
			write += '<option value="목요일">목요일</option>';
			write += '<option value="금요일">금요일</option>';
			write += '<option value="토요일">토요일</option>';
		write += "</select>";

		return write;
	},
	SelectBoxTimeFormWrite : function(strElement) {
		document.write(this.SelectBoxTime(strElement));
	},
	SelectBoxTime : function(strElement) {
		var NowHour = this.Now.getHours();
		var NowMin = this.Now.getMinutes();
		var strSelected = "";
		var tmp = "";

		NowMin += 10;
		if( NowMin > 55 ) {
			NowHour += 1;
			NowMin = NowMin - 60;
		}

		// 시간
		strSelectBox = "<br><br><select name=\"" + strElement + "Hour\" id=\"" + strElement + "Hour\" class=\"form-control unstyled\">";
		for( i = 0; i < 24; i++ ) {
			if( i < 10 ) {
				tmp = "0" + i;
			} else {
				tmp = i;
			}

			if( NowHour == i ) {
				strSelected = " selected";
			} else {
				strSelected = "";
			}

			strSelectBox += "<option value=\"" + tmp + "\" " + strSelected + ">" + tmp + "</option>";
		}
		strSelectBox += "</select> 시&nbsp;&nbsp;";

		// 분
		strSelectBox += "<select name=\"" + strElement + "Min\" id=\"" + strElement + "Min\" class=\"form-control unstyled\">";
		for( i = 0; i <= 55; i++ ) {
			if( i % 5 == 0 || i == 0 ) {
				if( i < 10 ) {
					tmp = "0" + i;
				} else {
					tmp = i;
				}

				if( NowMin <= i && NowMin > i - 5 ) {
					strSelected = " selected";
				} else {
					strSelected = "";
				}

				strSelectBox += "<option value=\"" + tmp + "\" " + strSelected + ">" + tmp + "</option>";
			}
		}
		strSelectBox += "</select> 분";

		return strSelectBox;
	},
	ReloadDate : function(target, year, month) {
		var max = this.DaysOfMonth(year, month);
		var obj = document.getElementById(target);
		var len = obj.options.length;
		
		if(max < len) {
			for(i=max;i<len;i++) obj.remove(max);
		}
		else if(max > len) {		
			for(i=len+1; i <= max; i++) {
				var opt = document.createElement("option");
		
				opt.value	= new String(100 + i).substring(1);
				opt.text = opt.value;
	
				obj.options.add(opt);
			}
		}
	},
	SetPeriod : function(days) { // 끝나는 기간 기준
		var end = new Date();
		end.setFullYear(document.getElementById('end_year').value,document.getElementById('end_month').value-1,document.getElementById('end_date').value);
		end.setDate(end.getDate() - days);

		var year = document.getElementById('start_year');
		var month = document.getElementById('start_month');
		var date = document.getElementById('start_date');
		
		Period.ReloadDate('start_date', end.getFullYear(), end.getMonth()+1);

		year.value = end.getFullYear();
		month.value = end.getMonth()+1;
		date.value = end.getDate();
	},
	SetPeriod2 : function(days) { // 시작 기간 기준
		var start = new Date();
		start.setFullYear(document.getElementById('start_year').value,document.getElementById('start_month').value-1,document.getElementById('start_date').value);
		start.setDate(start.getDate() + days);

		var year = document.getElementById('end_year');
		var month = document.getElementById('end_month');
		var date = document.getElementById('end_date');
		
		Period.ReloadDate('end_date', start.getFullYear(), start.getMonth()+1);
		
		year.value = start.getFullYear();
		month.value = start.getMonth()+1;
		date.value = start.getDate();
	},	
	Search : function() {
		var begin = document.getElementById('start_year').value + new String(100 + parseInt(document.getElementById('start_month').value)).substring(1) + new String(100 + parseInt(document.getElementById('start_date').value)).substring(1);
		var end = document.getElementById('end_year').value + new String(100 + parseInt(document.getElementById('end_month').value)).substring(1) + new String(100 + parseInt(document.getElementById('end_date').value)).substring(1);
		
			var link = document.location.href;
			link = Util.GetURLWithParameter(link,'start',begin);
			link = Util.GetURLWithParameter(link,'end',end);
			link = Util.GetURLWithParameter(link,'page',1);
			
			document.location.href = link;
	},
	DaysOfMonth : function(year, month) {
		var maxOfDay;
		
		if(month == 1 || month == 3 || month == 5 ||  month == 7 || month == 8 ||  month == 10 || month == 12)
		{
			maxOfDay = 31;
		}
		else if(month == 4 || month == 6 ||   month == 9 || month == 11)
		{
			maxOfDay = 30;
		}
		else
		{
			if(((year%4 == 0)&&(year%100 != 0)) || (year%400 == 0))
			{
				maxOfDay = 29;
			}
			else
			{
				maxOfDay = 28;
			}
		}
		
		return maxOfDay;
	}
}