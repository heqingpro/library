

$(function() {/* 让页面加载的时候显示等待信息 */
	$.messager.progress({
		text : '加载中....',
		interval : 100
	});
});

$.parser.onComplete = function() {/* 页面easyui组件渲染完毕关闭等待信息 */
	window.setTimeout(function() {
		$.messager.progress('close');
	}, 500);
};

$.fn.panel.defaults.loadingMessage = '数据加载中，请稍候....';
$.fn.datagrid.defaults.loadMsg = '数据加载中，请稍候....';

var easyuiErrorFunction = function(XMLHttpRequest) {
	$.messager.progress('close');
	$.messager.alert('错误', XMLHttpRequest.responseText);
};

$.fn.datagrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.treegrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combogrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combobox.defaults.onLoadError = easyuiErrorFunction;
$.fn.form.defaults.onLoadError = easyuiErrorFunction;

var easyuiPanelOnMove = function(left, top) {
	if (left < 0) {
		$().window('move', {
			left : 1
		});
	}
	if (top < 0) {
		$().window('move', {
			top : 1
		});
	}
};

$.fn.panel.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;