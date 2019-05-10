/**
 * @author limq
 * @param tabId
 *            表格Id
 * @param startCol
 *            列的开始下标(下标这里是从"00:00"开始,"00:00" 点是1)
 * @param n
 *            表示横向待合并单元格
 * @param rowNum
 *            所在的行
 * @return
 */
function doTab(tabObj, startCol, n, rowNum, adId, adName, startTime, endTime,
		repeatDay, adDealType, broastState) {
	if (n < 1)
		return;
	if (rowNum < 1)
		return;
	if (startCol > 144) {
		startCol = startCol - 144;
		rowNum = 4;
	}
	
	rowNum = rowNum - 1;
	startCol = startCol - 1;

	// 广告播发状态的颜色，默认值是空闲时的颜色
	var broastState_color = "#e7f3ff";
	
	if ("play" == broastState || "waitStopPlay" == broastState
			|| "failStopPlay" == broastState) {// 播发，待停，停播失败
		broastState_color = "#0b77da";

	} else if ("waitPlay" == broastState || "failPlay" == broastState) {// 待播，播发失败
		broastState_color = "#fe7004";

	} else if ("stopPlay" == broastState) {//停播
		broastState_color = "#fb4544";

	} 
	else {
		broastState_color = "#e7f3ff";
	}
	
	if ((startCol + n ) > 144 && rowNum ==1) {
		/**
		 * 上午
		 */
		var temN = n;

		n = 144 - startCol;
		var i = startCol;
		while (i < n + startCol - 1) { // 横向合并
			tabObj.rows[rowNum].deleteCell(startCol);
			i++;
		}
		

		// 修改合并后的cell的 colSpan 属性
		tabObj.rows[rowNum].cells[startCol].colSpan = n;

		// 清空合并后的cell里的内容
		tabObj.rows[rowNum].cells[startCol].innerText = "";
		tabObj.rows[rowNum].cells[startCol].innerHTML = "";

		
		// 修改合并后的cell的 width 属性7=6+1 其中的6表示每个单元格的宽度，1表示每个单元格的边框为1，所以每个单元格的总宽为7
		// 通过样式指定宽度
		tabObj.rows[rowNum].cells[startCol].style.width = (7 * n) + "px";
		// 通过样式指定背景颜色
		tabObj.rows[rowNum].cells[startCol].style.backgroundColor = broastState_color;
		// 指定cell文本的对齐方式
		tabObj.rows[rowNum].cells[startCol].align = "center";
		// 添加cell的标题
		tabObj.rows[rowNum].cells[startCol].title = adName + "\r\n" + "(" + adDealType + ")";
		// 添加cell的内容
		tabObj.rows[rowNum].cells[startCol].innerHTML = adName + "<br>" + "(" + adDealType + ")";
		

		tabObj.rows[rowNum].cells[startCol].onclick = function() {
			// 弹出添加窗口
			$("#showAdInfo").dialog( {
				height : 300,
				width : 400,
				modal : true
			});
			$("#adName").html(adName);
			$("#adDealType").html(adDealType);
			$("#adStartTime").html(startTime);
			$("#adEndTime").html(endTime);
			$("#repeatDay").html(repeatDay);
		};

		/**
		 * 下午
		 */

		n = startCol + temN - 144;
		startCol = 0;
		var i = startCol;
		rowNum = rowNum + 2;
		while (i < n + startCol - 1) { // 横向合并
			tabObj.rows[rowNum].deleteCell(startCol);
			i++;
		}

		
		
		// 修改合并后的cell的 colSpan 属性
		tabObj.rows[rowNum].cells[startCol].colSpan = n;

		// 清空合并后的cell里的内容
		tabObj.rows[rowNum].cells[startCol].innerText = "";
		tabObj.rows[rowNum].cells[startCol].innerHTML = "";

		// 修改合并后的cell的 width 属性7=6+1 其中的6表示每个单元格的宽度，1表示每个单元格的边框为1，所以每个单元格的总宽为7
		// 通过样式指定宽度
		tabObj.rows[rowNum].cells[startCol].style.width = (7 * n) + "px";
		// 通过样式指定背景颜色
		tabObj.rows[rowNum].cells[startCol].style.backgroundColor = broastState_color;
		// 指定cell文本的对齐方式
		tabObj.rows[rowNum].cells[startCol].align = "center";
		// 添加cell的标题
		tabObj.rows[rowNum].cells[startCol].title = adName + "\r\n" + "(" + adDealType + ")";
		// 添加cell的内容
		tabObj.rows[rowNum].cells[startCol].innerHTML = adName + "<br>" + "(" + adDealType + ")";

		tabObj.rows[rowNum].cells[startCol].onclick = function() {
			// 弹出添加窗口
			$("#showAdInfo").dialog( {
				height : 300,
				width : 400,
				modal : true
			});
			$("#adName").html(adName);
			$("#adDealType").html(adDealType);
			$("#adStartTime").html(startTime);
			$("#adEndTime").html(endTime);
			$("#repeatDay").html(repeatDay);
		};
	} else {
		
		var i = startCol;
		while (i < n + startCol - 1) { // 横向合并
			tabObj.rows[rowNum].deleteCell(startCol);
			i++;
		}


		// 修改合并后的cell的 colSpan 属性
		tabObj.rows[rowNum].cells[startCol].colSpan = n;

		// 清空合并后的cell里的内容
		tabObj.rows[rowNum].cells[startCol].innerText = "";
		tabObj.rows[rowNum].cells[startCol].innerHTML = "";

		// 修改合并后的cell的 width 属性7=6+1 其中的6表示每个单元格的宽度，1表示每个单元格的边框为1，所以每个单元格的总宽为7
		// 通过样式指定宽度
		tabObj.rows[rowNum].cells[startCol].style.width = (7 * n) + "px";
		// 通过样式指定背景颜色
		tabObj.rows[rowNum].cells[startCol].style.backgroundColor = broastState_color;
		// 指定cell文本的对齐方式
		tabObj.rows[rowNum].cells[startCol].align = "center";
		// 添加cell的标题
		tabObj.rows[rowNum].cells[startCol].title = adName + "\r\n" + "(" + adDealType + ")";
		// 添加cell的内容
		tabObj.rows[rowNum].cells[startCol].innerHTML = adName + "<br>" + "(" + adDealType + ")";

		tabObj.rows[rowNum].cells[startCol].onclick = function() {
			// 弹出添加窗口

			$("#showAdInfo").dialog( {
				height : 300,
				width : 400,
				modal : true
			});
			$("#adName").html(adName);
			$("#adDealType").html(adDealType);
			$("#adStartTime").html(startTime);
			$("#adEndTime").html(endTime);
			$("#repeatDay").html(repeatDay);
		};
	}

}
function areaMoredisplay(){
	$("span[id='areaDisNone']").css("display","inline");
	$("#areaMorebutton").css("display","none");
	$("#areaHideButton").css("display","inline");
}
function hideArea(){
	$("span[id='areaDisNone']").css("display","none");
	$("#areaMorebutton").css("display","inline");
	$("#areaHideButton").css("display","none");
}


function channelMoredisplay(){
	$("span[id='channelDisNone']").css("display","inline");
	$("#channelMorebutton").css("display","none");
	$("#channelHideButton").css("display","inline");
}
function hideChannel(){
	$("span[id='channelDisNone']").css("display","none");
	$("#channelMorebutton").css("display","inline");
	$("#channelHideButton").css("display","none");
}

