///*日历控件定义-GooCalendar类*/
//InputID :要被绑定的INPUT对象的ID
//property  :JSON变量，Progress的详细参数设置
function GooCalendar(InputID,bugID,property,_days,_clos){ 
	this.$divId=property.divId;//日历控件所在DIV的ID
	this.$div=$("<div id='"+this.$divId+"' class='Calendar' style='display:none'></div>");
	this.$div.addClass("Calendar"); 
	$("#"+InputID).addClass("inputobj");
	$("#"+InputID).bind("keyup",function(e){
			if(e.keyCode!=37&&e.keyCode!=39)
				this.value=this.value.replace(/[^0-9|-]/,'');
	}); 
	$("#"+InputID).bind("focusout",function(e){
			var time = $(this).val();
			var reg=/(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)/i;
			if(!reg.test(time)&&$("#"+InputID).val()!="")$("#"+bugID).fadeIn(300);
			else $("#"+bugID).fadeOut(300);	
	});
	$("#"+bugID).addClass("bugobj"); 
	if(property.fixid)
		this.$fixid=property.fixid;
	temp="<div class='top'><b class='arrow'><div class='left'></div></b><b class='arrow' style='float:right;'><div class='right'></div></b></div>"+
		"<div class='week'></div><div class='day'></div>"+
		"<div class='bottom'><div class='cal_btn'>"+_days+"</div><div class='time'></div><div class='cal_btn' style='float:right'>"+_clos+"</div></div>";
	this.$div.append(temp);
	var now =new Date();
	this.$daysNum=[31,28,31,30,31,30,31,31,30,31,30,31];
	this.$date={
		year:now.getFullYear(),
		month:now.getMonth()+1,
		day:now.getDate(),
		hour:now.getHours(),
		minute:now.getMinutes(),
		second:now.getSeconds()
	};//用来保存当前所选的时间
	this.$days=this.$div.children(".day");
	this.$selectMonth=$("<b id='"+this.$divId+"Month' style='width:92px;'><span>aa</span><select style='display:none'></select></b>");
	this.$selectYear=$("<b id='"+this.$divId+"Year' style='width:36px;'><span>aa</span><select style='display:none'></select></b>");
	this.$div.children(".top").children("b:eq(0)").after(this.$selectYear).after(this.$selectMonth);
	this.$selectDay=null;
	this.$selectTime=null;
	//是否需要显示精确到秒的时间选择器
	this.$needTime=false;
	if(property.needTime){
		this.$needTime=property.needTime;
		this.$selectTime={
			hour:$("<input type='text' size='2' maxlength='2' title='hour' value='"+this.$date.hour+"' onkeyup=\"if(isNaN(this.value)||this.value<0||this.value>23) this.value='0'\"/>"),
			minute:$("<input type='text' size='2' maxlength='2' title='minute' value='"+this.$date.minute+"' onkeyup=\"if(isNaN(this.value)||this.value<0||this.value>59) this.value='0'\"/>"),
			second:$("<input type='text' size='2' maxlength='2' title='second' value='"+this.$date.second+"' onkeyup=\"if(isNaN(this.value)||this.value<0||this.value>59) this.value='0'\"/>")
		};
		this.$div.children(".bottom").children(".time").append(this.$selectTime.hour).append("：").append(this.$selectTime.minute).append("：").append(this.$selectTime.second);
	}
	//设定WEEK
	if(property.week)
		this.$week=property.week;
	else
		this.$week=['Su','Mo','Tu','We','Th','Fr','Sa'];//从左至右，周日，周一。。。。。。周六
	temp="";
	for(i=0;i<this.$week.length;++i){
		temp+="<div>"+this.$week[i]+"</div>";
	}
	this.$div.children("div:eq(1)").append(temp);
	
	//设定年份范围
	if(property.yearRange)
		this.$yearRange=property.yearRange;
	else
		this.$yearRange=[1970,2030];//数组第一个为开始年份，第二个为结束年份
	temp="";
	for(i=this.$yearRange[0];i<=this.$yearRange[1];++i){
		temp+="<option value='"+i+"'>"+i+"</option>";
	}
	this.$selectYear.children("select").html(temp);
	this.$selectYear.children("select").val(this.$date.year);
	this.$selectYear.children("span").html(this.$date.year);
	
	//设定月份格式
	if(property.month)
		this.$month=property.month;
	else
		this.$month=['January','February','March','April','May','June','July','August','September','October','November','December'];//数组顺序，从一月至十二月
	temp="";
	for(i=0;i<this.$month.length;++i){
		temp+="<option value='"+(i+1)+"'>"+this.$month[i]+"</option>";
	}
	this.$selectMonth.children("select").html(temp);
	this.$selectMonth.children("select").val(this.$date.month);
	this.$selectMonth.children("span").text(this.$month[this.$date.month-1]);
	//设定日期输出格式
	if(property.format)
		this.$format=property.format;
	else
		this.$format="yyyy-MM-dd hh:mm:ss";
	$("#"+InputID).attr("maxlength",this.$format.length)
	//根据传入的年，月，设定这个月内的所有日期
	this.initDatesByYM=function(year,month){
		this.$days.empty();
		first=new Date(year,month-1,1).getDay();
		if(first>0)
			var temp="<div style='width:"+(27*first)+"px;height:20px;float:left;'></div>";//占位而已
		else
			var temp="";
		var i;
		now=new Date();
		nowYear=now.getFullYear();nowMonth=now.getMonth();nowDate=now.getDate();
		for(i=1;i<=this.$daysNum[month-1];++i){
			temp+="<a href='#' class='";
			if(year==nowYear&&month==nowMonth+1&&i==nowDate)
				temp+="today";
			if(year==this.$date.year&&month==this.$date.month&&i==this.$date.day)
				temp+=" select";
			temp+="'>"+i+"</a>";
		}
		if(year%4==0&&month==2){//如果是闰年
			temp+="<a href='#' class='";
			if(year==nowYear&&month==nowMonth+1&&i==nowDate)
				temp+="today";
			if(year==this.$date.year&&month==this.$date.month&&i==this.$date.day)
				temp+=" select";
			temp+="'>"+i+"</a>";
		}
		this.$days.append(temp);
		this.$selectDay=this.$days.children(".select");
	};	


	//跳转至上一个月
	this.preMonth=function(){
		year=this.$selectYear.children("span").text();
		month=this.$selectMonth.children("select").val();
		if(month>1) month--; 
		else{
			month=12;
			year--; this.$selectYear.children("span").text(year);
			this.$selectYear.children("span").text(year);
			this.$selectYear.children("select").val(year);
		}
		this.$selectMonth.children("span").text(this.$month[month-1]);
		//this.$tempDate.month=month;
		this.$selectMonth.children("select").attr("value",month);
		this.initDatesByYM(year,month);
	};
	//跳转至下一个月
	this.nextMonth=function(){
		year=this.$selectYear.children("span").text();
		month=this.$selectMonth.children("select").val();
		if(month<12) month++;
		else{
			month=1;
			year++; this.$selectYear.children("span").text(year);
			this.$selectYear.children("span").text(year);
			this.$selectYear.children("select").val(year);
		}
		this.$selectMonth.children("span").text(this.$month[month-1]);
		this.$selectMonth.children("select").val(month);
		this.initDatesByYM(year,month);
	}; 
	//光标离开隐藏  判断点击的地方(或点击的父类...)是不是文本框和时间的div  不是就隐藏时间div
 $(document).bind("click",function(e){
		if(e.target!=null){
			if(e.target.id==property.divId||e.target.id==InputID)return;
			if(e.target.parentNode!=null){
  				if(e.target.parentNode.id==property.divId||e.target.parentNode.id==InputID)return;
				if(e.target.parentNode.parentNode!=null){
  					if(e.target.parentNode.parentNode.id==property.divId||e.target.parentNode.parentNode.id==InputID)return;
  					if(e.target.parentNode.parentNode.parentNode!=null){
  						if(e.target.parentNode.parentNode.parentNode.id==property.divId||e.target.parentNode.parentNode.parentNode.id==InputID)return;
  						if(e.target.parentNode.parentNode.parentNode.parentNode!=null)
							if(e.target.parentNode.parentNode.parentNode.parentNode.id==property.divId||e.target.parentNode.parentNode.parentNode.parentNode.id==InputID)return;
  					} 	
  				} 	
  			}		
		}
		var reg=/(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)/i;
		var time = $("#"+InputID).val();
		if(!reg.test(time)&&$("#"+InputID).val()!="")$("#"+bugID).fadeIn(300);
		else $("#"+bugID).fadeOut(300);	
		$("#"+property.divId).hide(300);
 	});

//设定当用户定击日期后的事件
	this.$days.bind("click",{inthis:this},function(e){  
		inthis=e.data.inthis; 
		var $clicked = $(e.target);
		if($clicked.is("a")){
			if ( e && e.preventDefault )
			//阻止默认浏览器动作(W3C)
			e.preventDefault();
			else
			//IE中阻止函数器默认动作的方式
			window.event.returnValue = false;
			inthis.$selectDay.removeClass("select");
			$clicked.addClass("select");
			inthis.$selectDay=$clicked;
			inthis.$date.year=inthis.$selectYear.children("select").val();
			inthis.$date.month=inthis.$selectMonth.children("select").val();
			inthis.$date.day=$clicked.text();
			if(inthis.$needTime){
				inthis.$date.hour=inthis.$selectTime.hour.val();
				inthis.$date.minute=inthis.$selectTime.minute.val();
				inthis.$date.second=inthis.$selectTime.second.val();
			}
			var lastDate=new Date(inthis.$date.year,inthis.$date.month-1,inthis.$date.day);
			if(inthis.$needTime){
				lastDate.setHours(inthis.$date.hour,inthis.$date.minute,inthis.$date.second);
			}
			if(!inthis.$fixid) inthis.$div.hide(300);
			$("#"+InputID).val(lastDate.format(inthis.$format)); 

			return false;
		}
	}); 
	this.initDatesByYM(this.$date.year,this.$date.month);
	this.$div.children(".top").children("b:eq(0)").bind("click",{inthis:this},
		function(e){inthis=e.data.inthis;inthis.preMonth();}
	);
	this.$div.children(".top").children("b:eq(3)").bind("click",{inthis:this},
		function(e){inthis=e.data.inthis;inthis.nextMonth();}
	); 
	this.$selectMonth.bind("mousedown",function(e){
		tmpThis=$(this).children("span");
		if(tmpThis.css("display")!='none'){
			tmpThis.hide(300);
			$(this).children("select").show(300);
			//$(this).children("select").focus();
			$(this).next().children("select").blur();
		}
	});
	this.$selectYear.bind("mousedown",function(e){
		tmpThis=$(this).children("span");
		if(tmpThis.css("display")!='none'){
			tmpThis.hide(300);
			$(this).children("select").show(300);
			$(this).prev().children("select").blur(); 
			//$(this).children("select").focus();
		}
	});
	this.$selectMonth.children("select").bind("blur",{span:this.$selectMonth.children("span"),inthis:this},
	function(e){
		tmpThis=$(this);
		inthis=e.data.inthis;
		if(e.data.span.text!=inthis.$month[tmpThis.val()-1]){
			e.data.span.text(inthis.$month[tmpThis.val()-1]);
			inthis.initDatesByYM(inthis.$selectYear.children("select").val(),tmpThis.val());
		}
		tmpThis.hide(300);
		e.data.span.show(300);
	});
	this.$selectMonth.children("select").bind("change",function(){this.blur()});
	
	this.$selectYear.children("select").bind("blur",{span:this.$selectYear.children("span"),inthis:this},
	function(e){
		tmpThis=$(this);
		inthis=e.data.inthis;
		if(e.data.span.text!=tmpThis.val()){
			e.data.span.text(tmpThis.val());
			inthis.initDatesByYM(tmpThis.val(),inthis.$selectMonth.children("select").val());
		}
		tmpThis.hide(300);
		e.data.span.show(300);
	});
	this.$selectYear.children("select").bind("change",function(){this.blur()});
	//返回本年本月界面
	this.gotoToday=function(dat){
		dat = dat.replace(/-/g,"/");
		if(dat!=null&&dat!="")
			now = new Date(dat);
		else
			now = new Date(); 
		year=now.getFullYear();month=now.getMonth()+1; 		day_ =	now.getDate();	
		this.$selectMonth.children("span").text(this.$month[month-1]);
		this.$selectMonth.children("select").val(month);
		this.$selectYear.children("span").text(year);
		this.$selectYear.children("select").val(year);
		this.initDatesByYM(year,month);
		this.$selectDay.removeClass("select");
		$("#"+this.$divId+" .day a").each(function(){
			 if($(this).text()==day_){
			 		$(this).addClass("select");
			 		return false;
			 } 	
		})
		if(this.$needTime){
			this.$selectTime.hour.val(now.getHours());
			this.$selectTime.minute.val(now.getMinutes());
			this.$selectTime.second.val(now.getSeconds());
		}
	};
	this.$div.children(".bottom").children("div:eq(0)").bind("click",{inthis:this},function(e){e.data.inthis.cancel();var lastDate=new Date();$("#"+InputID).val(lastDate.format(property.format));});//e.data.inthis.gotoToday("");
	//放弃本次的时间修改
	this.cancel=function(){
		this.$selectMonth.children("span").text(this.$month[this.$date.month-1]);
		this.$selectMonth.children("select").val(this.$date.month);
		this.$selectYear.children("span").text(this.$date.year);
		this.$selectYear.children("select").val(this.$date.year);
		this.initDatesByYM(this.$date.year,this.$date.month); 
		if(this.$needTime){
			this.$selectTime.hour.val(this.$date.hour);
			this.$selectTime.minute.val(this.$date.minute);
			this.$selectTime.second.val(this.$date.second);
		}
		if(!this.$fixid)this.$div.hide(300);
	}
	this.$div.children(".bottom").children("div:eq(2)").bind("click",{inthis:this},function(e){e.data.inthis.cancel();});
	
	//与INPUT联系上
	if(!this.$fixid){
		$("body").append(this.$div);//把渲染好的控件UI附加在BODY的最后部分
		$("#"+InputID).bind("mousedown",{div:this.$div,inthis:this},function(e){
			var locate=getElCoordinate(this);
			locate.top+=$(this).attr("offsetHeight"); 
			e.data.div.show(300); 
			var time = $("#"+InputID).val();
			e.data.div.css({top:locate.top+"px",left:locate.left+"px"});
			var reg=/(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)/i;
			if(!reg.test(time)&&$("#"+InputID).val()!="")$("#"+bugID).fadeIn(300);
			else $("#"+bugID).fadeOut(300);	
			if(reg.test(time))e.data.inthis.gotoToday(time);
		});
	}
	else
		$("#"+this.$fixid).append(this.$div.css("display","block"));
	document.getElementById(InputID).value="";
	//设定当前所选日期,用户直接传入年月日或者再加上小时：分秒最多6个数字的JSON,参数date为一JSON，由用户自行组装
	this.setDate=function(date){
		this.$date.year=date.year||this.$date.year;
		this.$date.month=date.month||this.$date.month;
		this.$date.day=date.day||this.$date.day;
		this.$date.hour=date.hour||this.$date.hour;
		this.$date.minute=date.minute||this.$date.minute;
		this.$date.second=date.second||this.$date.second;
		lastDate=new Date(this.$date.year,this.$date.month-1,this.$date.day);
		//刷新日历控件的显示
		this.$selectMonth.children("span").text(this.$month[this.$date.month-1]);
		this.$selectMonth.children("select").val(this.$date.month);
		this.$selectYear.children("span").text(this.$date.year);
		this.$selectYear.children("select").val(this.$date.year);
		this.initDatesByYM(this.$date.year,this.$date.month);
		
		if(this.$needTime){
			lastDate.setHours(this.$date.hour,this.$date.minute,this.$date.second);
			this.$selectTime.hour.val(this.$date.hour);
			this.$selectTime.minute.val(this.$date.minute);
			this.$selectTime.second.val(this.$date.second);
		}
		$("#"+InputID).val(lastDate.format(this.$format));
		//刷新日历控件的显示
		this.initDatesByYM(this.$date.year,this.$date.month);
	} 
}

//将此类的构造函数加入至JQUERY对象中
jQuery.extend({
	createGooCalendar: function(InputId,bugId,property,type){
		var _week;
		var _month;
		var _days;
		var _clos;
		if(type=="Type"){
			_week=['Su','Mo','Tu','We','Th','Fr','Sa'];
			_month=['January','February','March','April','May','June','July','August','September','October','November','December'];
			_days="today";
			_clos="cancel";
		}else{
			_week=['七','一','二','三','四','五','六'];
			_month=['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'];
			_days="今天";
			_clos="取消";
		} 
		var property={
			divId:"demo"+new Date().getTime(),//日历控件最外层DIV的ID
			needTime:false,//是否需要显示精确到秒的时间选择器，即输出时间中是否需要精确到小时：分：秒 默认为FALSE可不填
			yearRange:[1970,2030],//可选年份的范围,数组第一个为开始年份，第二个为结束年份,如[1970,2030],可不填
			week:_week,//数组，设定了周日至周六的显示格式,可不填
			month:_month,
			//week:['Su','Mo','Tu','We','Th','Fr','Sa'],
			//month:['January','February','March','April','May','June','July','August','September','October','November','December'],//数组，设定了12个月份的显示格式,可不填
			format:"yyyy-MM-dd"
		   //yyyy-MM-dd hh:mm:ss//*设定日期的输出格式,可不填*/
		};
		return new GooCalendar(InputId,bugId,property,_days,_clos);
  }
}); 
