<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript">
	var remoteServerDatagrid=$('#remoteServerDatagrid');
	$(function() {
		remoteServerDatagrid.datagrid({
			url : '${pageContext.request.contextPath}/remoteServerController/queryRemoteServerList',
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
			nowrap : false,
			singleSelect : false,
			toolbar: '#remoteServerToolBar',
			columns : [ [
			{
				title : '编号',
				field : 'id',
				width : 50,
				checkbox : true
			}, {
				title : '服务器名称',
				field : 'remoteServerName',
				width : 100
			},
			 {
				title : 'IP地址',
				field : 'remoteIP',
				width : 100
			}, 
			{
				title : '端口号',
				field : 'remotePort',
				width : 100
			},
			 {
				title : '用户名',
				field : 'userName',
				width : 100
			},
			{
				title : '密码',
				field : 'userPass',
				width : 100
			},
			 {
				field : 'action',
				title : '操作',
				width : 100,
				formatter : function(value, row, index) {
						return formatString(
						'<sec:authorize url="/remoteServerManage/remoteServerDetail"><img onclick=\"remoteServerDetail(\'{0}\');\" src=\"{1}\" title=\"服务器详情\"/>详情&nbsp;</sec:authorize>'+
						'<sec:authorize url="/remoteServerManage/remoteServerDelete"><img onclick=\"remoteServerDelete(\'{2}\');\" src=\"{3}\" title=\"删除服务器\"/>删除&nbsp;</sec:authorize>',
						 row.id, '${pageContext.request.contextPath}/source/images/detail.png', 
						 row.id, '${pageContext.request.contextPath}/source/images/delete.png')
				}
			}
		] ]
	})});
	
	//服务器新增
	function remoteServerAdd(){
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/pages/remoteServer/remoteServerEdit.jsp',
			width : 400,
			height : 280,
			modal : true,
			resizable:true,
			title : '服务器新增',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var result = $('#remoteServer_form').form('validate');
					if(!result) {
						return false;
					}
					var d=$(this).closest('.window-body');					
					$.post('${pageContext.request.contextPath}/remoteServerController/addRemoteServer',
							$("#remoteServer_form").serializeArray(),function(r){
								if (r.success) {
									remoteServerDatagrid.datagrid('reload');
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
	
	//服务器编辑
	function remoteServerEdit(){
		var rows = remoteServerDatagrid.datagrid('getSelections');
		if (rows.length > 0) {
			if(rows.length>1){
				$.messager.alert('提示', '只能编辑单个服务器');
			}else{
				$('<div/>').dialog({
					href : '${pageContext.request.contextPath}/pages/remoteServer/remoteServerEdit.jsp',
					width : 400,
					height : 280,
					modal : true,
					resizable:true,
					title : '服务器编辑',
					buttons : [ {
						text : '确定',
						iconCls : 'icon-ok',
						handler : function() {
							var result = $('#remoteServer_form').form('validate');
							if(!result) {
								return false;
							}
							var d=$(this).closest('.window-body');					
							$.post('${pageContext.request.contextPath}/remoteServerController/editRemoteServer',
									$("#remoteServer_form").serializeArray(),function(r){
										if (r.success) {
											remoteServerDatagrid.datagrid('reload');
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
						var row=rows[0];
						$('#remoteServer_form').form('load',row);
						remoteServerDatagrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
					}
				});
			}
		} else {
			$.messager.alert('提示', '请选择要编辑的服务器');
		}
	}
	
	//服务器详情
	function remoteServerDetail(id){
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/pages/remoteServer/remoteServerEdit.jsp',
			width : 650,
			height : 450,
			modal : true,
			resizable:true,
			title : '服务器详情',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var d=$(this).closest('.window-body');
					d.dialog('destroy');
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			},
			onLoad : function() {
				var index = remoteServerDatagrid.datagrid('getRowIndex', id);
				var rows = remoteServerDatagrid.datagrid('getRows');
				var row=rows[index];
				$('#remoteServer_form').form('load',row);
				
				$("#remoteServerName").attr("readonly","true");
				$("#remoteIP").attr("readonly","true");
				$("#remotePort").attr("readonly","true");
				$("#userName").attr("readonly","true");
				$("#userPass").attr("readonly","true");
			}
		});
		
	}
	
	//服务器删除
	function remoteServerDelete(id){
		remoteServerDatagrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		remoteServerDatagrid.datagrid('checkRow', remoteServerDatagrid.datagrid('getRowIndex', id));
		remoteServerDeleteAll();
	}
	
	//服务器批量删除
	function remoteServerDeleteAll(){
		var rows = remoteServerDatagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			$.messager.confirm('确认', '您是否要删除当前选中的服务器？', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/remoteServerController/deleteRemoteServer',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(result) {
							remoteServerDatagrid.datagrid('reload');
							if (result.success) {
								$.messager.alert('提示', '删除成功','info');
								remoteServerDatagrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
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
			$.messager.alert('提示', '请选择要删除的服务器');
		}
	}
	
	
	
	//服务器搜索
	function remoteServerSearch() {
		remoteServerDatagrid.datagrid('getPager').pagination({pageNumber:1});
		var params = remoteServerDatagrid.datagrid('options').queryParams;
		var searchRemoteServerName = $("#searchRemoteServerName").val();
		
		if(searchRemoteServerName!=undefined){
			params["remoteServerName"]=searchRemoteServerName;
		}
		remoteServerDatagrid.datagrid('options').queryParams=params;
		remoteServerDatagrid.datagrid('reload');
	}
	
</script>



<!-- 显示datagrid数据表格 -->
<table id="remoteServerDatagrid" style="display: none" singleSelect=true></table>
<!-- 显示datagrid数据表格中的toolbar -->
<div id="remoteServerToolBar" style="height: 20px;display: none">
	<div style="float:left;">
		<sec:authorize url="/remoteServerManage/remoteServerAdd">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="remoteServerAdd()">服务器新增</a>
		</sec:authorize>
		<sec:authorize url="/remoteServerManage/remoteServerEdit">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="remoteServerEdit()">服务器修改</a>
        </sec:authorize>
		<sec:authorize url="/remoteServerManage/remoteServerDelete">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="remoteServerDeleteAll()">服务器删除</a>
		</sec:authorize>
    </div>
	<div style="float:right;">
		服务器名称：<input type="input" id="searchRemoteServerName" name="searchRemoteServerName"/>&nbsp;&nbsp;
	    <img onclick="remoteServerSearch();" src="source/images/search.gif" title="搜索"/>&nbsp;&nbsp;
    </div>
</div>
