<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript">
	var appDataGrid=$('#appDataGrid');
	$(function() {
		appDataGrid.datagrid({
			url : '${pageContext.request.contextPath}/appController/queryAppList',
			fit : false,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'id',
			sortOrder : 'asc',
			checkOnSelect : true,
			selectOnCheck : true,
			nowrap : true,
			singleSelect : false,
			toolbar: '#appToolBar',
			columns : [ [
			{
				title : '编号',
				field : 'id',
				width : 50,
				checkbox : true
			}, {
				title : '提供商名称',
				field : 'appName',
				width : 100
			}, {
				title : '英文名称',
				field : 'enName',
				width : 100
			}, {
				title : '备注',
				field : 'remark',
				width : 100
				
			},
			 {
				field : 'action',
				title : '操作',
				width : 150,
				formatter : function(value, row, index) {
						return formatString(
						/* '<sec:authorize url="/appManage/appDetail"><img onclick=\"appDetail(\'{0}\');\" src=\"{1}\" title=\"提供商详情\"/>&nbsp;&nbsp;</sec:authorize>'+ */
						'<sec:authorize url="/appManage/appDelete"><img onclick=\"appDelete(\'{2}\');\" src=\"{3}\" title=\"删除提供商\"/></sec:authorize>',
						 row.id, '${pageContext.request.contextPath}/source/images/detail.png', 
						 row.id, '${pageContext.request.contextPath}/source/images/delete.png');
				}
			}
		] ]
	})});
	//提供商新增
	function appAdd(){
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/pages/app/appEdit.jsp',
			width : 550,
			height : 350,
			modal : true,			
			resizable:true,
			title : '新增提供商',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var result = $('#app_form').form('validate');
						if(!result) {
						return false;
					}
					var d=$(this).closest('.window-body');	
					$.post('${pageContext.request.contextPath}/appController/addApp', 
							$("#app_form").serializeArray(),function(r){
								if (r.success) {
									appDataGrid.datagrid('reload');
									d.dialog('destroy');
									$.messager.alert('提示', '添加成功');
								}else{
									if(r.msg!=undefined){
										$.messager.alert('提示', r.msg);
									}else{
										toLogin();
									}										
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
	
	//提供商编辑
	function appEdit(){
		var rows = appDataGrid.datagrid('getSelections');
		if (rows.length > 0) {
			if(rows.length>1){
				$.messager.alert('提示', '只能编辑单个提供商');
			}else{
				$('<div/>').dialog({
					href : '${pageContext.request.contextPath}/pages/app/appEdit.jsp',
					width : 550,
					height :350,
					modal : true,
					resizable:true,					
					title : '编辑提供商',
					buttons : [ {
						text : '确定',
						iconCls : 'icon-ok',
						handler : function() {
							var result = $('#app_form').form('validate');
							if(!result) {
								return false;
							}
							var d = $(this).closest('.window-body');
							$.post('${pageContext.request.contextPath}/appController/editApp',
									$("#app_form").serializeArray(),function(r){
										if (r.success) {
											appDataGrid.datagrid('reload');
											d.dialog('destroy');
											$.messager.alert('提示', '修改成功');
										}else{
											if(r.msg!=undefined){
												$.messager.alert('提示', r.msg);
											}else{
												toLogin();
											}										
										}
									}	
							);	
						}
					} ],
					onClose : function() {
						$(this).dialog('destroy');
					},
					onLoad : function() {
						var appId=rows[0].id;
						var row = rows[0];
						$.ajax({
							url : '${pageContext.request.contextPath}/appController/getAppDetail?appId='+appId,
							data : {
							},
							dataType : 'json',
							success : function(r) {
								$('#app_form').form('load',row);
								$("#id").val(appId);
							}
						});
						//清除所选择的提供商
						appDataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
					}
				});
			}
		} else {
			$.messager.alert('提示', '请选择要编辑的提供商');
		}
	}


	//提供商删除
	function appDelete(id){
		appDataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		appDataGrid.datagrid('checkRow', appDataGrid.datagrid('getRowIndex', id));
		appDeleteAll();
	}
	
	//提供商批量删除
	function appDeleteAll(){
		var rows = appDataGrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			/* for ( var i = 0; i < rows.length; i++) {
				if(rows[i].appFlag==1){
					$.messager.alert('提示', '选择删除的提供商中，['+rows[i].appName+']是系统回看提供商，不可删除！','info');
					return;
				}
				if(rows[i].statusInfo=="已发布"){
					$.messager.alert('提示', '选择删除的提供商中，['+rows[i].appName+']是已经发布的提供商，不可删除！','info');
					return;
				}
			} */
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			$.messager.confirm('确认', '您是否要删除当前选中的提供商？', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/appController/deleteApp',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(result) {
							appDataGrid.datagrid('reload');
							if (result.success) {
								$.messager.alert('提示', '删除成功','info');
								appDataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
							}else{
								if(result.msg!=undefined){
									$.messager.alert('提示', '删除失败','error');
								}else{
									toLogin();
								}
							}
						}
					});
				}
			});
		} else {
			$.messager.alert('提示', '请选择要删除的提供商');
		}
	}
	
	//提供商排序
	function appSort(){
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/pages/app/appSortEdit.jsp',
			width : 400,
			height : 400,
			modal : true,
			resizable:true,
			title : '配置提供商排序序号',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-edit',
				handler : function() {
					var result = $('#app_sort_form').form('validate');
					if(!result) {
						return false;
					}
					if(!numberFlag){
						return false;
					}	
					var d = $(this).closest('.window-body');
					$.post('${pageContext.request.contextPath}/appController/updateAppSortNumber',
					$("#app_sort_form").serializeArray(),function(result){
						if (result.success==true) {
							appDataGrid.datagrid("reload");
							d.dialog('destroy');
							$.messager.alert('提示','排序成功','info');
						}else if(result.msg!=undefined){
							$.messager.alert('提示',result.msg,'info');
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
	
	//提供商搜索
	function appSearch() {
		appDataGrid.datagrid('getPager').pagination({pageNumber:1});
		var params = appDataGrid.datagrid('options').queryParams;
		var searchAppName = $("#searchAppName").val();
		
		if(searchAppName!=undefined){
			params["appName"]=searchAppName;
		}
		appDataGrid.datagrid('options').queryParams=params;
		appDataGrid.datagrid('reload');
	}
	
</script>



<!-- 显示datagrid数据表格 -->
<table id="appDataGrid" style="display: none" singleSelect=true></table>
<!-- 显示datagrid数据表格中的toolbar -->
<div id="appToolBar" style="height: 25px;display: none; padding:1px 2px 0 2px;">
	<div style="float:left;">
		<sec:authorize url="/appManage/appAdd">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="appAdd()">新增</a>
		</sec:authorize>
		<sec:authorize url="/appManage/appEdit">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="appEdit()">修改</a>
        </sec:authorize>
		<sec:authorize url="/appManage/appDelete">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="appDeleteAll()">删除</a>
		</sec:authorize>
		<%-- <sec:authorize url="/appManage/appSort">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="appSort()">排序</a>
		</sec:authorize> --%>
		
    </div>
	<div style="float:right;">
		提供商名称：<input type="input" id="searchAppName" name="searchAppName"/>&nbsp;&nbsp;
	    <img onclick="appSearch();" src="source/images/search.gif" title="搜索"/>&nbsp;&nbsp;
    </div>
</div>
