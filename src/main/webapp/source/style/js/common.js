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
	/* 加上登录凭证信息*/
	var session = null;
	if(!requestData)
	{
		requestData = {};
	}
	else if(requestData)
	{
		session = requestData.session;
	}
	
	if(!session)
	{
		requestData.session = $.cookie("session");
	}
		
	$.ajax({
		url : requestUrl,
		type : "POST",
		data : requestData,
		beforeSend:ajaxLoading,//发送请求前打开进度条 
		success : function(data) {
			ajaxLoadEnd();//任务执行成功，关闭进度条
			alert(data.responseCode);
			if (data && (data.responseCode == 200 || data.responseCode == "200")) {
				
				if(data.responseMsg&&data.responseMsg!="")
				{
					$.messager.alert('提示消息', data.responseMsg, "info");
				}
				else
				{
					$.messager.show({
						title : '提示消息',
						msg : '请求成功！',
						timeout : 3000,
						showType : 'slide'
					});
				}
				if (successHandler && successHandler != null)
				{
					var responseData = data.data;
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
					if (errorHandler && errorHandler != null)
						errorHandler();
				});
			}
		},
		error : function(xmlHttpRequest, textStatus, errorThrown) {
			ajaxLoadEnd();//任务执行成功，关闭进度条
			$.messager.alert('提示消息', 'Ajax请求失败，服务器异常异常：textStatus=' + textStatus + ", errorThrown=" + errorThrown, "error", function() {
				if (errorHandler && errorHandler != null)
					errorHandler();
			});
		},
		dataType : "json"
	});
}

/*
 * 通用dialog的form表单提交
 */
function dialog_form_submit(dialog, requestUrl, form, successHandler, errorHandler, title, beforeSubmit) {
	
	/*添加登录凭证*/
	/*requestUrl = requestUrl + "?session=" +$.cookie("session");*/
	
	dialog.show().dialog({
		title : title,
		modal : true,
		buttons : [{
			text : '确定',
			handler : function() {
				ajaxLoading();//发送请求前打开进度条 
				form.form("submit", {
					url : requestUrl,
					onSubmit : function(param) {
						
						/* 加上登录凭证信息*/
						var session = param.session;
						if(!session)
						{
							param.session = $.cookie("session");
						}
						
						var result = false;
						if (beforeSubmit || beforeSubmit != null) {
							result = beforeSubmit();
						} else {
							result = form.form("validate");
						}
						/*
						 关闭dialog防止用户表单重复提交
						 */
						if (result == true) {
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

/**
 * 改写jeasyui中默认etitor的combotree单选的问题
 */
jQuery.extend(jQuery.fn.datagrid.defaults.editors, {  
    combotree: {  
        init: function(container, options){  
            var editor = jQuery('<input type="text">').appendTo(container);  
            editor.combotree(options);  
            return editor;  
        },  
        destroy: function(target){  
            jQuery(target).combotree('destroy');  
        },  
        getValue: function(target){  
            var temp = jQuery(target).combotree('getValues');  
            //alert(temp);  
            return temp.join(',');  
        },  
        setValue: function(target, value){
        	var temp;
        	if(value&&value.indexOf(",")>=0)
        	{
        		temp = value.split(",");
        		jQuery(target).combotree('setValues', temp); 
        	}
        	else
        	{
        		temp = value;
        		jQuery(target).combotree('setValue', temp); 
        	}
        },  
        resize: function(target, width){  
            jQuery(target).combotree('resize', width);  
        }  
}  
});


/** 
 * @author 陈双源 
 *  
 * @requires jQuery 
 *  
 * 扩展datagrid的editor的datetimebox类型 
 */  
$.extend($.fn.datagrid.defaults.editors, {  
    datetimebox: {//datetimebox就是你要自定义editor的名称  
        init: function(container, options){  
            var editor = $('<input />').appendTo(container);  
            editor.editable = false;  
            editor.datetimebox(options);  
            return editor;  
        },  
        getValue: function(target){
        	//console.info("getValue",$(target).datetimebox('getValue'));
           var value = $(target).datetimebox('getValue');
           return value;
//        	var new_str = $(target).datetimebox('getValue').replace(/:/g,'-');  
//            new_str = new_str.replace(/ /g,'-');  
//            var arr = new_str.split("-");  
//            var datum = new Date(Date.UTC(arr[0],arr[1]-1,arr[2],arr[3]-8,arr[4],arr[5]));  
//            var timeStamp = datum.getTime();  
//            return timeStamp;  
       },  
        setValue: function(target, value){  
        //	console.info("setValue:"+value);
        	$(target).datetimebox('setValue',value);
          //  $(target).datetimebox('setValue',new Date(value).format("MM/dd/yyyy hh:mm:ss"));  
        },  
        resize: function(target, width){  
           $(target).datetimebox('resize',width);          
        },  
        destroy: function(target){  
            $(target).datetimebox('destroy');  
        }  
    }  
});  

//时间格式化  
Date.prototype.format = function(format){  
    /* 
     * eg:format="yyyy-MM-dd hh:mm:ss"; 
     */  
    if(!format){  
        format = "yyyy-MM-dd hh:mm:ss";  
    }  
  
    var o = {  
            "M+": this.getMonth() + 1, // month  
            "d+": this.getDate(), // day  
            "h+": this.getHours(), // hour  
            "m+": this.getMinutes(), // minute  
            "s+": this.getSeconds(), // second  
           "q+": Math.floor((this.getMonth() + 3) / 3), // quarter  
           "S": this.getMilliseconds()  
            // millisecond  
   };  
  
   if (/(y+)/.test(format)) {  
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
    }  
  
    for (var k in o) {  
        if (new RegExp("(" + k + ")").test(format)) {   
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" +o[k]).length));  
       }  
    }  
    return format;  
};  


function ajaxLoading(){ 
    $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body"); 
    $("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
 } 
 function ajaxLoadEnd(){ 
     $(".datagrid-mask").remove(); 
     $(".datagrid-mask-msg").remove();             
} 
