<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript">
	var execute = false;

	$(function() {

		$('#appUserDatagrid').datagrid({
			url : '${pageContext.request.contextPath}/appUserController/queryAppUserList',
			fit : false,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'id',
			sortOrder : 'desc',
			checkOnSelect : true,
			selectOnCheck : true,
			nowrap : true,
			rownumbers: true,
			striped : true,
			toolbar: '#userListToolBar',
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				width : 20,
				checkbox : true
			}]],
			columns : [ [  {
				field : 'caId',
				title : 'CA卡号',
				width : 30,
				sortable : false
			},{
				field : 'userName',
				title : '帐号',
				width : 30,
				sortable : false
			},   {
				field : 'action',
				title : '操作',
				width : 50,
				formatter : function(value, row, index) {
						return formatString(
						'<sec:authorize url="/appUserManage/appUserEntryList"><img onclick=\"personalEntryList(\'{0}\');\" src=\"{1}\" title=\"个人数据中心\"/>收藏中心</sec:authorize>',
						 row.id, '${pageContext.request.contextPath}/source/images/bookmark.png');
				}
			} ] ],
			onRowContextMenu: function(e, index, row){
				e.preventDefault();
				$(this).datagrid('unselectAll');
				$(this).datagrid('selectRow', index);
				$('#userRightMenu').menu('show', {
					left: e.pageX,
					top: e.pageY
				});
			}
		});

	});

	//频道导入
	function appUserImport(){
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/pages/dataCollection/personalData/appUserImport.jsp',
			width : 500,
			height :150,
			modal : true,
			resizable:true,
			title : "导入用户",
			buttons : [ {
				text : '导入',
				iconCls : 'icon-add',
				handler : function() {
					var d = $(this).closest('.window-body');
					var result = $('#appUser_import_form').form('validate');
					if(!result) {
						return false;
					}
					var appUserImportFile = $("#appUserImportFile").val();
					if (appUserImportFile==undefined ||appUserImportFile==""){
						$.messager.alert('提示', '请上传excel格式的网络文件！','info');
						return;
					}
					//弹出后台处理提示框，用户不能操作，只能等待完成。
					ajaxLoadStart();
					var appUserImportUrl = "${pageContext.request.contextPath}/appUserController/importAppUser";
					$.ajaxFileUpload({  
						//处理文件上传操作的服务器端地址 
						url:appUserImportUrl,  
						secureuri:false,                           //是否启用安全提交,默认为false   
						fileElementId:['appUserImportFile'],   	   //文件选择框的id属性
						dialog:d,								   //导入文件时，防止重复提交，关闭对话框
						dataType:'json',                           //服务器返回的格式,可以是json或xml等  
						success:function(data, status){ //服务器响应成功时的处理函数 
							//关闭后台处理提示框。
							ajaxLoadEnd();
							if(data.success==false){
								$.messager.alert('提示', data.msg,'info');
							}
							else{
								$('#appUserDatagrid').datagrid('reload');
								$.messager.alert('提示', '导入成功','info');
							}
						},  
						error:function(data, status, e){ //服务器响应失败时的处理函数 
							ajaxLoadEnd();
							$.messager.alert('提示', '执行异常！','error');
						}  
					}); 
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			},
			onLoad : function() {
				
			}
		});
	}
	function personalEntryList(id){
		//跳转到素材管理页面
		addTab("个人素材列表",'${pageContext.request.contextPath}/pages/dataCollection/personalData/appUserEntryList.jsp?id='+id);
	}
	
	

	function searchUser() {
		//var params = $('#appUserDatagrid').datagrid('options').queryParams; //取得 datagrid 的查询参数
		//params[name] = value; //设置查询参数
		//$('#appUserDatagrid').datagrid('reload');
		$("#appUserDatagrid").datagrid('getPager').pagination({pageNumber:1});
		var params = $('#appUserDatagrid').datagrid('options').queryParams;
		var userName = $("#searchUserName").val();
		params["userName"]=userName;
		$('#appUserDatagrid').datagrid('options').queryParams=params;
		$('#appUserDatagrid').datagrid('reload');
	}
	
	
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'center',border:false">
		<table id="appUserDatagrid"></table>
	</div>
</div>
<div id="userListToolBar">
	<div style="float:right;">
		<!--
		<input class="easyui-searchbox" data-options="prompt:'请输入查询内容',menu:'#userSearchMenu',searcher:searchUser" style="width:200px"></input>
		<div id="userSearchMenu" style="width:100px">
			<div data-options="name:'userName'">用户名</div>
		</div>
		-->
		<sec:authorize url="/appUserManage/appUserImport">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="appUserImport()">用户导入</a>
		</sec:authorize>
		用户名：<input type="input" id="searchUserName" name="searchUserName"/>&nbsp;&nbsp;<img onclick="searchUser();" src="source/images/search.gif" title="搜索"/>&nbsp;&nbsp;
    </div>
</div>