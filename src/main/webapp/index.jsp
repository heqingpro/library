<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>欢迎使用图书管理系统|Welcome to use the library management system</title>

<jsp:include page="inc.jsp"></jsp:include>
<script type="text/javascript">
	/*
	*添加选项卡方法
	*/
	function addTab(title,url){
		var tab=$('#tt').tabs('exists',title);
		if(tab){
			//若存在，则直接打开
			$('#tt').tabs('close',title);
		}
		$('#tt').tabs('add',{
			title:title,
			href:url,
			closable:true
		});
	}
	
	/*
	*根据title,选中Accordion对应的面板
	*/
	function selectAccordion(title){
		$('#accordionPanel').accordion('select',title);
	}
	/*
	*刷新时间
	*/
	function showTime(){
		var date=new Date();
		$('#nowTime').html();
		$('#nowTime').html(date.toLocaleString()+"&nbsp;&nbsp;");
	}
	/* setInterval(showTime,1000); */
	
	/*
	*检测浏览器窗口大小改变,来改变页面layout大小
	*/
	$(function(){
		$(window).resize(function(){
			$('#cc').layout('resize');
		});

		 $('#skin').combobox({  
			 onChange:function(skin,oldValue){  
				
		            if(skin == "metro"){
		    			$("#themesSkin").attr("href","${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.1/themes/metro/easyui.css");
		    		}
		    		if(skin == "default"){
		    			$("#themesSkin").attr("href","${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.1/themes/default/easyui.css");
		    		}
		    		if(skin == "black"){
		    			$("#themesSkin").attr("href","${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.1/themes/black/easyui.css");
		    		}
		    		if(skin == "gray"){
		    			$("#themesSkin").attr("href","${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.1/themes/gray/easyui.css");
		    		}
		    		if(skin == "bootstrap"){
		    			$("#themesSkin").attr("href","${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.1/themes/bootstrap/easyui.css");
		    		}
		        }
		    }); 

	});
	function logoutFun() {
		$.messager.confirm('确认', '确定要退出系统吗？', function(r) {
			if (r) {
				$.getJSON('${pageContext.request.contextPath}/sysUserController/logout', function(result) {
					location.replace('${pageContext.request.contextPath}/login.html');
				});
			}
		});
	}

	function userInfoFun() {
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/pages/admin/updateUserPwd.jsp',
			width : 560,
			height : 230,
			modal : true,
			title : '修改密码',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var d = $(this).closest('.window-body');
					if(passFlag == false)
						return false;
					if(passFormatFlag == false)
						return false;
					$.post('${pageContext.request.contextPath}/sysUserController/modifyPwd',
						$("#adminPwdUpdateForm").serializeArray(),function(r){
							if (r.success) {	
								d.dialog('destroy');
								$.messager.confirm('确认',  r.msg+"密码已修改,是否重新登录？", function(r) {
								if (r) {
									$.getJSON('${pageContext.request.contextPath}/sysUserController/logout', function(result) {
										location.replace('${pageContext.request.contextPath}/login.jsp');
									});
									}
								});
							}else{
								$.messager.alert('提示', '密码修改失败:'+r.msg);
							}
						}
					);
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			},
			onLoad : function() {
			}
		});
	}
</script>

<style type="text/css">

.banner {
	background: url(./source/images/banner.png) no-repeat;
	height:auto;
  	width:auto;
  	overflow: hidden;
  	background-size:cover;
}

</style>
</head>
<body style="border:none;visibility:visible;" onload="showTime();">
	
	<div id="cc" class="easyui-layout" style="width:100%;height:100%;">
		<!-- 页面顶部top及菜单栏 -->  
	   <div data-options="region:'north',border:false" class="banner" style="height:90px; padding:5px;"> 
	    <!-- <a href="#"><span class="northTitle">欢迎使用图书管理系统！</span></a> -->
	     	<span class="loginInfo" iconCls='icon-user' style="color: white; font-weight: bold;">
	     		您好，${session_user_name}&nbsp;！&nbsp;&nbsp;
				<a onclick="javascript:userInfoFun()" iconCls="icon-key"  class="easyui-linkbutton">修改密码</a>
	      		<a onclick="javascript:logoutFun()" class="easyui-linkbutton" iconCls="icon-redo">退出登录</a>
	     	</span>
	    </div>  
	  
		<!-- 左侧导航菜单 -->	    
	    <div region="west" title="菜单导航" style="width:180px;top:59px;border-bottom: none;border-right: none;" background="source/images/index_menu_button.jpg">
			<div class="easyui-accordion" fit="true" style="text-align: center;" id="accordionPanel" >
				 <sec:authorize url="/spManage">
					<div title="图书管理" id= menu_1>
						<ul style="text-align: left;padding-left: 15px;">
						<sec:authorize url="/spManage/appManage"><li style="padding:1px 0 1px 0"><a class="easyui-linkbutton" icon="icon-factory" plain="true" style="width:100%; text-align:left;" onmouseover="this.style.cursor='hand'" onclick="javascript:addTab('内容提供商管理','${pageContext.request.contextPath}/pages/app/appList.jsp');">内容提供商管理</a></li></sec:authorize>
						<sec:authorize url="/spManage/nodeManage"> <li style="padding:1px 0 1px 0"><a class="easyui-linkbutton" icon="icon-category" plain="true" style="width:100%; text-align:left;" onmouseover="this.style.cursor='hand'" onclick="javascript:addTab('分类管理','${pageContext.request.contextPath}/pages/node/appNodeList.jsp');">分类管理</a></li></sec:authorize>
						<sec:authorize url="/spManage/angleManage"> <li style="padding:1px 0 1px 0"><a class="easyui-linkbutton" icon="icon-language" plain="true" style="width:100%; text-align:left;" onmouseover="this.style.cursor='hand'" onclick="javascript:addTab('语种管理','${pageContext.request.contextPath}/pages/angle/angleList.jsp');">语种管理</a></li></sec:authorize>
						<sec:authorize url="/spManage/entryTypeManage"> <li style="padding:1px 0 1px 0"><a class="easyui-linkbutton" icon="icon-series" plain="true" style="width:100%; text-align:left;" onmouseover="this.style.cursor='hand'" onclick="javascript:addTab('系列管理','${pageContext.request.contextPath}/pages/entryType/entryTypeList.jsp');">系列管理</a></li></sec:authorize>
						<sec:authorize url="/spManage/entryManage"> <li style="padding:1px 0 1px 0"><a class="easyui-linkbutton" icon="icon-books" plain="true" style="width:100%; text-align:left;" onmouseover="this.style.cursor='hand'" onclick="javascript:addTab('书籍管理','${pageContext.request.contextPath}/pages/entry/entryNode.jsp');">书籍管理</a></li></sec:authorize>
						</ul>
					</div>
					
				 </sec:authorize>
				<%-- <sec:authorize url="/dataManage">
				<div title="统计管理" id="menu_2">
					<ul style="text-align: left;padding-left: 30px;" >
						<sec:authorize url="/dataManage/appUserManage"><li><a onmouseover="this.style.cursor='hand'"  onclick="javascript:addTab('专区用户管理','${pageContext.request.contextPath}/pages/dataCollection/personalData/personalList.jsp');">专区用户管理</a></li></sec:authorize>
					</ul>
					<ul style="text-align: left;padding-left: 30px;" >
						<sec:authorize url="/dataManage/appDataManage"><li><a onmouseover="this.style.cursor='hand'"  onclick="javascript:addTab('数据统计管理','${pageContext.request.contextPath}/pages/dataCollection/personalData/appDataList.jsp');">数据统计管理</a></li></sec:authorize>
					</ul>
				</div>
				</sec:authorize> --%>
				<%-- <sec:authorize url="/serverManage">
				<div title="服务器管理" id="menu_2">
					<ul style="text-align: left;padding-left: 30px;" >
						<sec:authorize url="/serverManage/remoteServerManage"><li><a onmouseover="this.style.cursor='hand'"  onclick="javascript:addTab('远程服务器管理','${pageContext.request.contextPath}/pages/remoteServer/remoteServerList.jsp');">远程服务器管理</a></li></sec:authorize>
					</ul>
				</div>
				</sec:authorize> --%>
				<sec:authorize url="/systemManage">
				<div title="系统管理" id="menu_4">
					<ul style="text-align: left;padding-left: 15px;" >
					  	<sec:authorize url="/systemManage/userManage"><li style="padding:1px 0 1px 0"><a class="easyui-linkbutton" icon="icon-client" plain="true" style="width:100%; text-align:left;" onmouseover="this.style.cursor='hand'" onclick="javascript:addTab('用户管理','${pageContext.request.contextPath}/pages/admin/userList.jsp');">用户管理</a></li></sec:authorize>
					   <sec:authorize url="/systemManage/roleManage"> <li style="padding:1px 0 1px 0"><a class="easyui-linkbutton" icon="icon-people" plain="true" style="width:100%; text-align:left;"onmouseover="this.style.cursor='hand'" onclick="javascript:addTab('角色管理','${pageContext.request.contextPath}/pages/admin/roleList.jsp');">角色管理</a></li></sec:authorize>
					   <sec:authorize url="/systemManage/logManage"> <li style="padding:1px 0 1px 0"><a class="easyui-linkbutton" icon="icon-book_open" plain="true" style="width:100%; text-align:left;"onmouseover="this.style.cursor='hand'" onclick="javascript:addTab('日志管理','${pageContext.request.contextPath}/pages/admin/logList.jsp');">日志管理</a></li></sec:authorize>
					</ul>
				</div>
				</sec:authorize>
			</div>	   
	    </div>
	  	<!-- 主显示区域选项卡界面 title="主显示区域"-->
	    <div region="center" style="border: none;padding-left: 3px">
	    	<div class="easyui-tabs" fit="true" id="tt" > 
	    		<!-- <div title="主页" style="padding-left: 10px">
	    			<h1 style="font-size: 18px;">欢迎使用图书管理系统！</h1>
	    		</div> -->
	    	</div>
	    </div>  
	    <!-- 页面底部信息 -->
		<!-- 页脚信息 -->
		<div data-options="region:'south',border:false" style="height:20px; padding:2px; align:center; text-align: center; vertical-align: middle;">
			<span id="sysVersion" style="vertical-align: middle; text-align: center;">Copyright © 1998-2018 深圳市茁壮网络股份有限公司 粤ICP备12069260 iPanel.TV Inc. All Rights Reserved</span>
			<!-- <span id="nowTime"></span> -->
		</div>

</div>

<!--主页-->
<script type="text/javascript">
window.onload = function() {
	var tab=$('#tt').tabs('exists',"主页");
		if(tab){
			//若存在，则直接打开
			$('#tt').tabs('close',"主页");
		}
		$('#tt').tabs('add',{
			title:"主页",
			href:"${pageContext.request.contextPath}/main.jsp",
			closable:false
		});
}
</script>
</body>
</html>