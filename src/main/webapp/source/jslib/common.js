var pageRefreshDataId = 0;
/**
 * @requires jQuery,EasyUI
 * 
 * panel关闭时回收内存，主要用于layout使用iframe嵌入网页时的内存泄漏问题
 */
$.fn.panel.defaults.onBeforeDestroy = function() {
	var frame = $('iframe', this);
	try {
		if (frame.length > 0) {
			for ( var i = 0; i < frame.length; i++) {
				frame[i].contentWindow.document.write('');
				frame[i].contentWindow.close();
			}
			frame.remove();
			if ($.browser.msie) {
				CollectGarbage();
			}
		}
	} catch (e) {
	}
};

/**
 * 使panel和datagrid在加载时提示
 * 
 * @requires jQuery,EasyUI
 * 
 */
$.fn.panel.defaults.loadingMessage = '加载中....';
$.fn.datagrid.defaults.loadMsg = '加载中....';

/**
 * @requires jQuery,EasyUI
 * 
 * 通用错误提示
 * 
 * 用于datagrid/treegrid/tree/combogrid/combobox/form加载数据出错时的操作
 */
var easyuiErrorFunction = function(XMLHttpRequest) {
	$.messager.progress('close');
	$.messager.alert('错误', XMLHttpRequest.responseText);
};
$.fn.datagrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.treegrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.tree.defaults.onLoadError = easyuiErrorFunction;
$.fn.combogrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combobox.defaults.onLoadError = easyuiErrorFunction;
$.fn.form.defaults.onLoadError = easyuiErrorFunction;

/**
 * @requires jQuery,EasyUI
 * 
 * 为datagrid、treegrid增加表头菜单，用于显示或隐藏列，注意：冻结列不在此菜单中
 */
var createGridHeaderContextMenu = function(e, field) {
	e.preventDefault();
	var grid = $(this);/* grid本身 */
	var headerContextMenu = this.headerContextMenu;/* grid上的列头菜单对象 */
	if (!headerContextMenu) {
		var tmenu = $('<div style="width:100px;"></div>').appendTo('body');
		var fields = grid.datagrid('getColumnFields');
		for ( var i = 0; i < fields.length; i++) {
			var fildOption = grid.datagrid('getColumnOption', fields[i]);
			if (!fildOption.hidden) {
				$('<div iconCls="icon-ok" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(tmenu);
			} else {
				$('<div iconCls="icon-empty" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(tmenu);
			}
		}
		headerContextMenu = this.headerContextMenu = tmenu.menu({
			onClick : function(item) {
				var field = $(item.target).attr('field');
				if (item.iconCls == 'icon-ok') {
					grid.datagrid('hideColumn', field);
					$(this).menu('setIcon', {
						target : item.target,
						iconCls : 'icon-empty'
					});
				} else {
					grid.datagrid('showColumn', field);
					$(this).menu('setIcon', {
						target : item.target,
						iconCls : 'icon-ok'
					});
				}
			}
		});
	}
	headerContextMenu.menu('show', {
		left : e.pageX,
		top : e.pageY
	});
};
$.fn.datagrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;
$.fn.treegrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;

/**
 * @requires jQuery,EasyUI
 * 
 * 扩展validatebox，添加验证两次密码功能
 */
$.extend($.fn.validatebox.defaults.rules, {
	eqPwd : {
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '密码不一致！'
	}
});

/**
 * @requires jQuery,EasyUI
 * 
 * 扩展tree，使其支持平滑数据格式
 */
$.fn.tree.defaults.loadFilter = function(data, parent) {
	var opt = $(this).data().tree.options;
	var idFiled, textFiled, parentField;
	if (opt.parentField) {
		idFiled = opt.idFiled || 'id';
		textFiled = opt.textFiled || 'text';
		parentField = opt.parentField;
		var i, l, treeData = [], tmpMap = [];
		for (i = 0, l = data.length; i < l; i++) {
			tmpMap[data[i][idFiled]] = data[i];
		}
		for (i = 0, l = data.length; i < l; i++) {
			if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
				if (!tmpMap[data[i][parentField]]['children'])
					tmpMap[data[i][parentField]]['children'] = [];
				data[i]['text'] = data[i][textFiled];
				tmpMap[data[i][parentField]]['children'].push(data[i]);
			} else {
				data[i]['text'] = data[i][textFiled];
				treeData.push(data[i]);
			}
		}
		return treeData;
	}
	return data;
};

/**
 * @requires jQuery,EasyUI
 * 
 * 扩展treegrid，使其支持平滑数据格式
 */
$.fn.treegrid.defaults.loadFilter = function(data, parentId) {
	var opt = $(this).data().treegrid.options;
	var idFiled, textFiled, parentField;
	if (opt.parentField) {
		idFiled = opt.idFiled || 'id';
		textFiled = opt.textFiled || 'text';
		parentField = opt.parentField;
		var i, l, treeData = [], tmpMap = [];
		for (i = 0, l = data.length; i < l; i++) {
			tmpMap[data[i][idFiled]] = data[i];
		}
		for (i = 0, l = data.length; i < l; i++) {
			if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
				if (!tmpMap[data[i][parentField]]['children'])
					tmpMap[data[i][parentField]]['children'] = [];
				data[i]['text'] = data[i][textFiled];
				tmpMap[data[i][parentField]]['children'].push(data[i]);
			} else {
				data[i]['text'] = data[i][textFiled];
				treeData.push(data[i]);
			}
		}
		return treeData;
	}
	return data;
};

/**
 * @requires jQuery,EasyUI
 * 
 * 扩展combotree，使其支持平滑数据格式
 */
$.fn.combotree.defaults.loadFilter = $.fn.tree.defaults.loadFilter;

/**
 * @requires jQuery,EasyUI
 * 
 * 防止panel/window/dialog组件超出浏览器边界
 * @param left
 * @param top
 */
var easyuiPanelOnMove = function(left, top) {
	var l = left;
	var t = top;
	if (l < 1) {
		l = 1;
	}
	if (t < 1) {
		t = 1;
	}
	var width = parseInt($(this).parent().css('width')) + 14;
	var height = parseInt($(this).parent().css('height')) + 14;
	var right = l + width;
	var buttom = t + height;
	var browserWidth = $(window).width();
	var browserHeight = $(window).height();
	if (right > browserWidth) {
		l = browserWidth - width;
	}
	if (buttom > browserHeight) {
		t = browserHeight - height;
	}
	$(this).parent().css({/* 修正面板位置 */
		left : l,
		top : t
	});
};
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.panel.defaults.onMove = easyuiPanelOnMove;

/**
 * @requires jQuery,EasyUI,jQuery cookie plugin
 * 
 * 更换EasyUI主题的方法
 * 
 * @param themeName
 *            主题名称
 */
changeTheme = function(themeName) {
	var $easyuiTheme = $('#easyuiTheme');
	var url = $easyuiTheme.attr('href');
	var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
	$easyuiTheme.attr('href', href);

	var $iframe = $('iframe');
	if ($iframe.length > 0) {
		for ( var i = 0; i < $iframe.length; i++) {
			var ifr = $iframe[i];
			$(ifr).contents().find('#easyuiTheme').attr('href', href);
		}
	}

	$.cookie('easyuiThemeName', themeName, {
		expires : 7
	});
};

/**
 * @requires jQuery
 * 
 * 将form表单元素的值序列化成对象
 * 
 * @returns object
 */
serializeObject = function(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};

/**
 * 增加formatString功能
 * 
 * 使用方法：formatString('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
 * 
 * @returns 格式化后的字符串
 */
formatString = function(str) {
	for ( var i = 0; i < arguments.length - 1; i++) {
		str = str.replace("{" + i + "}", arguments[i + 1]);
	}
	return str;
};

/**
 * 接收一个以逗号分割的字符串，返回List，list里每一项都是一个字符串
 * 
 * @returns list
 */
stringToList = function(value) {
	if (value != undefined && value != '') {
		var values = [];
		var t = value.split(',');
		for ( var i = 0; i < t.length; i++) {
			values.push('' + t[i]);/* 避免他将ID当成数字 */
		}
		return values;
	} else {
		return [];
	}
};

/**
 * @requires jQuery
 * 
 * 改变jQuery的AJAX默认属性和方法
 */
$.ajaxSetup({
	type : 'POST',
	error : function(XMLHttpRequest, textStatus, errorThrown) {
		$.messager.progress('close');
		$.messager.alert('错误', XMLHttpRequest.responseText);
	}
});

$.ajaxSetup({
	cache : false
});

function print(obj) {
	var property = "";
	for ( var i in eval(obj)) {
		property += (i + ", ");
	}
	alert(property);
}

function getOs() {
	var bro = $.browser;
	var binfo = "";
	if (bro.msie) {
		binfo = "Microsoft Internet Explorer " + bro.version;
	}
	if (bro.mozilla) {
		binfo = "Mozilla Firefox " + bro.version;
	}
	if (bro.safari) {
		binfo = "Apple Safari " + bro.version;
	}
	if (bro.opera) {
		binfo = "Opera " + bro.version;
	}
	return binfo;
}

function getExplorer() {
	var explorer = window.navigator.userAgent ;
	var e;
	if (explorer.indexOf("MSIE") >= 0) {
		e = "ie";
	}
	//firefox 
	else if (explorer.indexOf("Firefox") >= 0) {
		e = "firefox";
	}
	//Chrome
	else if(explorer.indexOf("Chrome") >= 0){
		e = "chrome";
	}
	//Opera
	else if(explorer.indexOf("Opera") >= 0){
		e = "opera";
	}
	//Safari
	else if(explorer.indexOf("Safari") >= 0){
		e = "safari";
	}else
		e = "other";
	return e ;
}

/**
 * 产生min到max之间的随机数
 * @param min
 * @param max
 * @returns
 */
function getRandomNum(min,max){   
	var range = max - min;   
	var rand = Math.random();   
	return(min + Math.round(rand * range));   
}   

String.prototype.trim=function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

String.prototype.ltrim=function(){
    return this.replace(/(^\s*)/g,"");
}

String.prototype.rtrim=function(){
    return this.replace(/(\s*$)/g,"");
}

/**
 * 图片按比例放大缩小
 * @param {Object} maxWidth
 * @param {Object} maxHeight
 * @param {Object} objImg
 */
function AutoResizeImage(maxWidth,maxHeight,objImg){
	var img = new Image();
	img.src = objImg.src;
	var hRatio;
	var wRatio;
	var Ratio =1;
	var w = img.width;
	var h = img.height;
	wRatio = maxWidth / w;
	hRatio = maxHeight / h;
	if (maxWidth ==0 && maxHeight==0){
		Ratio = 1;
	}else if (maxWidth==0){
		if (hRatio<1) 
		Ratio = hRatio;
	}else if (maxHeight==0){
		if (wRatio<1) 
		Ratio = wRatio;
	}else if (wRatio<1 || hRatio<1){
		 if(wRatio<=hRatio)
			Ratio = wRatio;
		else
			Ratio = hRatio;
	}
	if (Ratio<1){
	w = w * Ratio;
	h = h * Ratio;
	}
	objImg.height = h;
	objImg.width = w;
	
}

function isInsalledIEVLC(){ 
    var vlcObj = null;
    var vlcInstalled= false;
    try {
        vlcObj = new ActiveXObject("VideoLAN.Vlcplugin.2"); 
        if( vlcObj != null ){ 
            vlcInstalled = true 
        }
    } catch (e) {
        vlcInstalled= false;
    }        
    return vlcInstalled;
} 
	
 function isInsalledOtherVLC(){ 
	var result = false;
	for (i = 0; i < navigator.plugins.length; i++) {
		if(navigator.plugins[i].name.indexOf("VLC") >= 0){
			result = true;	
			break;
		}else{
			result = false;
		}
	}
 	return result;
}

/*
 * 套书系列页中添加套书系列
 */
function tabs_add_panel(tabs,title,href)
{
	if (!tabs.tabs("exists", title)) {
		tabs.tabs("add", {
			title : title,
			href : href,
			closable : true
		});
	} else {
		tabs.tabs("select", title);
	}
}


/*
 通用Ajax提交请求至后台
 */
function ajaxRequestBMS(requestUrl, requestData, successHandler, errorHandler) {
	if(!requestData){
		requestData = {};
	}
	$.ajax({
		url : requestUrl,
		type : "POST",
		data : requestData,
		beforeSend:ajaxLoadStart,//发送请求前打开进度条 
		success : function(data) {
			ajaxLoadEnd();//任务执行成功，关闭进度条
			if (data && (data.success)) {				
				if(data.msg&&data.msg!=""){
					$.messager.alert('提示消息', data.msg, "info");
				}else{
					$.messager.show({
						title : '提示消息',
						msg : '请求成功！',
						timeout : 3000,
						showType : 'slide'
					});
				}
				if (successHandler && successHandler != null){
					var responseData = data.data;
					if(responseData){
						successHandler(responseData);
					}else{
						successHandler();
					}
				}
			} else {
				$.messager.alert('提示消息', data.msg, "error", function() {
					if (errorHandler && errorHandler != null){
						errorHandler();
					}	
				});
			}
		},
		error : function(xmlHttpRequest, textStatus, errorThrown) {
			ajaxLoadEnd();//任务执行成功，关闭进度条
			$.messager.alert('提示消息', 'Ajax请求失败，服务器异常：textStatus=' + textStatus + ", errorThrown=" + errorThrown, "error", function() {
				if (errorHandler && errorHandler != null){
					errorHandler();
				}		
			});
		},
		dataType : "json"
	});
}

/*
 * 通用dialog的form表单提交
 */
function dialog_form_submit(dialog, requestUrl, form, successHandler, errorHandler, title, beforeSubmit) {
	dialog.show().dialog({
		title : title,
		modal : true,
		buttons : [{
			text : '确定',
			handler : function() {
				ajaxLoadStart();//发送请求前打开进度条 
				form.form("submit", {
					url : requestUrl,
					onSubmit : function(param) {
						var result = false;
						if (beforeSubmit || beforeSubmit != null) {
							result = beforeSubmit();
						} else {
							result = form.form("validate");
						}
						/*
						 关闭dialog防止用户表单重复提交
						 */
						if (result) {
							dialog.dialog("close");
						}
						return result;
					},
					success : function(data) {
						ajaxLoadEnd();//任务执行成功，关闭进度条
						try {
							data = JSON.parse(data);
							if (data.responseCode == "200") {
								
								if(data.responseMsg&&data.responseMsg!="")
								{
									$.messager.alert('提示消息', data.responseMsg, "warn");
								}
								else
								{
									$.messager.show({
										title : '提示消息',
										msg : '表单提交成功！',
										timeout : 3000,
										showType : 'slide'
									});
								}
								if (successHandler && successHandler != null)
								{
									var responseData = data.responseMsg;
									if(responseData)
									{
										successHandler(responseData);
									}
									else
									{
										successHandler();
									}
								}
							} else {
								$.messager.alert('提示消息', data.responseMsg, "error", function() {
									dialog.dialog("open");
									if (errorHandler && errorHandler != null)
										errorHandler(data);
								});
							}
						} catch (e) {
							$.messager.alert('提示消息', '表单提交失败，遇到异常:' + e, "error", function() {
								dialog.dialog("open");
								if (errorHandler && errorHandler != null)
									errorHandler();
							});
						}
					}
				});
			}
		}, {
			text : '重置',
			handler : function() {
				form.form("reset");
			}
		}]
	});
}
function ajaxLoadStart(){ 
    $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body"); 
    $("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
 } 
 function ajaxLoadEnd(){ 
     $(".datagrid-mask").remove(); 
     $(".datagrid-mask-msg").remove();             
} 
function toLogin(){
		window.top.location.href='login.jsp';
}