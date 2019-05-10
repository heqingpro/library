
$(function(){
	var weekdayObj = [
						{"id":"mon","name":"周一"},
						{"id":"tue","name":"周二"},
						{"id":"wed","name":"周三"},
						{"id":"thu","name":"周四"},
						{"id":"fri","name":"周五"},
						{"id":"sat","name":"周六"},
						{"id":"sun","name":"周日"},
					];
	initTimeList(weekdayObj);
	initTimeTable(weekdayObj);

	/*$(".timeTable").on('click','td',function(e){
		e = e || window.event;
		console.log(e);
		$this = $(this);
		var index = $this.index();
		console.log(index);
		$this.addClass("select");
	});*/
	var selectIdArr = [];
	var temp = "";

	$(".weekdayList").on('click','input',function(){
		var $this = $(this);
		var id = $this.attr("id");
		var weekday = id.split("_")[1];
		// console.log($this.is(":checked"));
		var tdArr = $("#"+weekday).children("td");
		if(!$this.is(":checked")){
			$("#"+weekday).children("td").removeClass("select");

			$.each(tdArr, function( i, val) {

				var td_id = val.id;

				if( $.inArray(td_id, selectIdArr) > -1 ) {
					selectIdArr.splice( $.inArray(td_id, selectIdArr), 1 );
				}
			});

		}else{
			$("#"+weekday).children("td").addClass("select");
			$.each(tdArr, function( i, val) {

				var td_id = val.id;

				if( $.inArray(td_id, selectIdArr) == -1 ) {
					selectIdArr.push( td_id );
				}
			});
		}
		// console.log(selectIdArr);
		getTimeTable(selectIdArr);
	});

	$(".timeTable").on('click','td',function( event ) {
		//单击
		var _id = $(this).attr("id");
		var trIndex = $(this).closest('tr').index();
		console.log(trIndex);
		if( trIndex > 0) {

			if($.inArray(_id, selectIdArr) > -1){

				$(this).removeClass("select");
				selectIdArr.splice($.inArray(_id, selectIdArr), 1);

			}else{

				$(this).addClass("select");
				selectIdArr.push(_id);

			}
			
		}

		getTimeTable(selectIdArr);
	});

	$(".timeTable").on('mousedown','td',function( event ) {
		$(".timeTable").on({
			
			//按住鼠标拖动
			mousemove: function( e ) {
				var $this = $(this);
				var trIndex = $this.closest('tr').index();

				//除开第一行和第二行
				if( trIndex > 0 ) {

					var index = $this.index();
					var id = $this.attr("id");
					if( temp != id){

						if($.inArray(id, selectIdArr) > -1){

							$this.removeClass("select");
							selectIdArr.splice($.inArray(id, selectIdArr), 1);

						}else{

							$this.addClass("select");
							selectIdArr.push(id);

						}

					}

				}
				temp = id;
				
			},
			//松开鼠标
			mouseup: function( e ) {
				var $this = $(this);
				$(".timeTable").off('mousemove','td');
				// console.log('test');

				getTimeTable(selectIdArr);
			}

		},"td");

	});
})

/**
 * 初始化星期列表
 * @param  {[type]} weekday [日期对象]
 * @return {[type]}         [description]
 */
function initTimeList( weekday ) {
	var str = "";
	$(".weekdayList ul").html("");

	$.each( weekday, function( i, val ) {
		str += '<li><input type="checkbox" id="weekday_' + $(val).attr("id") + '" /><span>' + $(val).attr("name") + '</span></li>';
	});

	$(".weekdayList ul").html(str);
}

/**
 * 初始化星期列表
 * @param  {[type]} weekday [日期对象]
 * @return {[type]}         [description]
 */
function initTimeTable( weekday ) {
	var hour = 24;
	var str1 = "<tr>";
	for (var i = 0; i < hour; i++) {
		str1 += '<td>' + formatTime( i ) + '</td>';
		// str1 += '<td colspan="2" rowspan="2">' + formatTime( i ) + '</td>';
	};
	//str1 += "</tr><tr>";

	var weekdayList = ["sun","mon","tue","wed","thu","fri","sat"];
	for (var j = 0; j < 7; j++) {
		var str2 = '<tr id="' + weekdayList[j] + '">';
		for (var i = 0; i < hour ; i++) {
			str2 += '<td id="' + j+ "_" + i + '" ></td>';
		};
		str2 += '</tr>';

		str1 += str2;
	};

	$(".timeTable table").html( str1 );
}

/**
 * 获得最后结果
 * @param  {[type]} selectIdArr [存储单元格id数组]
 * @return {[type]}             [description]
 */
function getTimeTable( selectIdArr ) {
	console.log( selectIdArr );
	var weekdayNum = 7;		//一周7天
	var timeArr = [];	
	var timeResult = [];	//最后结果

	//定义二维数组
	for (var j = 0; j < weekdayNum; j++) {
		timeArr[j] = [];
	};
	var timeList = [];

	$.each(selectIdArr, function( i, val ) {
		var weekdayIndex = parseInt(val.split("_")[0]);		//星期
		var time = parseInt(val.split("_")[1]);		//时间
		
		/*timeList.push(time/2);
		timeList = sortArr(timeList);*/

		timeArr[weekdayIndex].push(time);
	});

	//从小到大排序
	for( var i = 0; i < timeArr.length; i++){
		timeArr[i] = sortArr(timeArr[i]);
	}
	// console.log(timeArr);

	
	for( var i = 0; i < timeArr.length; i++){
		if( timeArr[i].length > 0 ){
			var obj = {};
			obj.week = i;
			var timeStr = formatTimeResult(timeArr[i]);
			obj.time = timeStr;
			if( obj.week == 0 ) {
				obj.week = 7;
			}
			timeResult.push(obj);
		}
	}

	console.log(timeResult);

	/*if(timeResult.length > 0) {

		$(".showResult").html(JSON.stringify(timeResult));

	}else{
		$(".showResult").html("");

	}*/

	$("#lend_time").val(JSON.stringify(timeResult));

}

/**
 * 将存放时间点的数组格式化成时间段
 * @param  {[type]} arr [要格式化的数组]
 * @return {[type]}     [格式化之后的字符串]
 */
function formatTimeResult (arr) {
	console.log(arr);
	var startArr = [];
	var endArr = [];
	for( var i = 0; i < arr.length; i++) {

		var start = formatTime(arr[i]);
		var end = formatTime(arr[i]+1);
		
		startArr.push(start);
		endArr.push(end);
	}

	/*console.log(startArr);
	console.log(endArr);*/
	//开始时间和结束时间去重
	var startArrTemp = [];
	var endArrTemp = [];
	$.each(startArr, function( i, val ){
		if( $.inArray(val, endArr) == -1 ) {
			startArrTemp.push(val);
		}
	});
	$.each(endArr, function( i, val ){
		if( $.inArray(val, startArr) == -1 ) {
			//var hour = parseInt(val.split(":")[0]) - 1;
			//var min = "59";
			//var endTime = Math.floor(hour/10) > 0 ? hour + ":" + min : "0" + hour + ":" + min;
			val = val == "24:00" ? "23:59" : val;
			endArrTemp.push(val);
		}
	});
	/*console.log(startArrTemp);
	console.log(endArrTemp);*/
	str = "";
	for( var j = 0; j < startArrTemp.length; j++ ) {
		if( j == startArrTemp.length -1 ){
			str += startArrTemp[j] + "-" + endArrTemp[j];
		}else{
			str += startArrTemp[j] + "-" + endArrTemp[j] + ",";
		}
	}
	// console.log(str);
	return str;
}

/**
 * 格式化时间
 * @param  {[type]} hour [要格式化的时间]
 * @return {[type]}      [格式化后的时间]
 */
function formatTime( hour ) {
	if(hour - Math.floor(hour) > 0){
		hour = Math.floor(hour);
		if( Math.floor(hour/10) > 0 ) {
			//两位数不添0
			return hour + ":30";
		}else {
			return "0"+ hour + ":30";
		}

	}else{

		if( Math.floor(hour/10) > 0 ) {
			//两位数不添0
			return hour + ":00";
		}else {
			return "0"+ hour + ":00";
		}
		
	}

}

/**
 * 排序
 * @param  {[type]} arr [要排序的数组]
 * @return {[type]}     [排序后的借故偶]
 */
function sortArr( arr ) {
	for (var i = 0; i < arr.length; i++) {
		for (var j = i + 1; j < arr.length; j++) {
			if( arr[i] > arr[j] ){
				var temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		};
	};

	return arr;
}