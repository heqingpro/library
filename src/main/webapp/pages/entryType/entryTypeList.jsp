<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!-- 系列管理 -->
<script type="text/javascript">
	var entryTypeDataGrid=$('#entryTypeDataGrid');
	$(function() {
		entryTypeDataGrid.datagrid({
			url : '${pageContext.request.contextPath}/entryTypeController/queryEntryTypeList',
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
			toolbar: '#entryTypeToolBar',
			columns : [ [
			{
				title : '编号',
				field : 'id',
				width : 50,
				checkbox : true
			}, {
				title : '系列名称',
				field : 'typeName',
				width : 100
			}, {
				title : '备注',
				field : 'remark',
				width : 100
				
			},{
				title : '创建时间',
				field : 'addTime',
				width : 100				
			},
			 {
				field : 'action',
				title : '操作',
				width : 150,
				formatter : function(value, row, index) {
						return formatString(
						'<sec:authorize url="/entryTypeManage/entryTypeDelete"><img onclick=\"entryTypeDelete(\'{0}\');\" src=\"{1}\" title=\"删除系列\"/></sec:authorize>',
						 row.id, '${pageContext.request.contextPath}/source/images/delete.png');
				}
			}
		] ]
	})});
	//系列新增
	function entryTypeAdd(){
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/pages/entryType/entryTypeEdit.jsp',
			width : 550,
			height : 350,
			modal : true,
			resizable:true,
			title : '新增系列',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var result = $('#entryType_form').form('validate');
						if(!result) {
						return false;
					}
					var d=$(this).closest('.window-body');	
					$('#entryType_form').form('submit',{
						 url:'${pageContext.request.contextPath}/entryTypeController/addEntryType',
						 success:function(result){
							 var r = JSON.parse(result);
							 if (r.success) {
								    entryTypeDataGrid.datagrid('reload');
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
	
	//系列编辑
	function entryTypeEdit(){
		var rows = entryTypeDataGrid.datagrid('getSelections');
		if (rows.length > 0) {
			if(rows.length>1){
				$.messager.alert('提示', '只能编辑单个系列');
			}else{
				$('<div/>').dialog({
					href : '${pageContext.request.contextPath}/pages/entryType/entryTypeEdit.jsp',
					width : 550,
					height :350,
					modal : true,
					resizable:true,
					title : '编辑系列',
					buttons : [ {
						text : '确定',
						iconCls : 'icon-ok',
						handler : function() {
							var result = $('#entryType_form').form('validate');
							if(!result) {
								return false;
							}
							var d = $(this).closest('.window-body');
							$('#entryType_form').form('submit',{
								 url:'${pageContext.request.contextPath}/entryTypeController/editEntryType',
								 success:function(result){
									 var r = JSON.parse(result);
									 if (r.success) {
										    entryTypeDataGrid.datagrid('reload');
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
							});			
						}
					} ],
					onClose : function() {
						$(this).dialog('destroy');
					},
					onLoad : function() {
						var entryTypeId=rows[0].id;
						var row = rows[0];
					/* 	$.ajax({
							url : '${pageContext.request.contextPath}/entryTypeController/getAppDetail?entryTypeId='+entryTypeId,
							data : {
							},
							dataType : 'json',
							success : function(r) { */
								$('#entryType_form').form('load',row);
								$("#id").val(entryTypeId);
						/* 	}
						}); */
						//清除所选择的内容提供商
						entryTypeDataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
					}
				});
			}
		} else {
			$.messager.alert('提示', '请选择要编辑的系列');
		}
	}


	//系列删除
	function entryTypeDelete(id){
		entryTypeDataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		entryTypeDataGrid.datagrid('checkRow', entryTypeDataGrid.datagrid('getRowIndex', id));
		entryTypeDeleteAll();
	}
	
	//系列批量删除
	function entryTypeDeleteAll(){
		var rows = entryTypeDataGrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				if(rows[i].entryTypeFlag==1){
					$.messager.alert('提示', '选择删除的系列中，['+rows[i].entryTypeName+']是系统回看系列，不可删除！','info');
					return;
				}
				if(rows[i].statusInfo=="已发布"){
					$.messager.alert('提示', '选择删除的系列中，['+rows[i].entryTypeName+']是已经发布的系列，不可删除！','info');
					return;
				}
			}
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			$.messager.confirm('确认', '您是否要删除当前选中的系列？', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/entryTypeController/deleteEntryType',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(result) {
							entryTypeDataGrid.datagrid('reload');
							if (result.success) {
								$.messager.alert('提示', '删除成功','info');
								entryTypeDataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
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
			$.messager.alert('提示', '请选择要删除的系列');
		}
	}
		
	//系列搜索
	function entryTypeSearch() {
		entryTypeDataGrid.datagrid('getPager').pagination({pageNumber:1});
		var params = entryTypeDataGrid.datagrid('options').queryParams;
		var searchAppName = $("#searchTypeName").val();
		
		if(searchAppName!=undefined){
			params["typeName"]=searchAppName;
		}
		entryTypeDataGrid.datagrid('options').queryParams=params;
		entryTypeDataGrid.datagrid('reload');
	}
	
</script>



<!-- 显示datagrid数据表格 -->
<table id="entryTypeDataGrid" style="display: none" singleSelect=true></table>
<!-- 显示datagrid数据表格中的toolbar -->
<div id="entryTypeToolBar" style="height: 25px;display: none">
	<div style="float:left;">
		<sec:authorize url="/entryTypeManage/entryTypeAdd">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="entryTypeAdd()">新增</a>
		</sec:authorize>
		<sec:authorize url="/entryTypeManage/entryTypeEdit">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="entryTypeEdit()">修改</a>
        </sec:authorize>
		<sec:authorize url="/entryTypeManage/entryTypeDelete">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="entryTypeDeleteAll()">删除</a>
		</sec:authorize>		
		
    </div>
	<div style="float:right;">
		系列名称：<input type="text" id="searchTypeName" name="searchTypeName"/>&nbsp;&nbsp;
	    <img onclick="entryTypeSearch();" src="source/images/search.gif" title="搜索"/>&nbsp;&nbsp;
    </div>
</div>
