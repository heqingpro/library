<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript">
	var execute = false;

	$(function() {
		$('#adminRoleDatagrid').datagrid({
			url : '${pageContext.request.contextPath}/sysUserController/queryRole',
			fit : false,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'id',
			sortOrder : 'asc',
			checkOnSelect : true,
			selectOnCheck : true,
			nowrap : false,
			toolbar: '#roleToolBar',
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				checkbox : true
			}]],
			columns : [ [/*  {
				title : '编号',
				field : 'id',
				width : 150,
				sortable : false,
				checkbox : true
			},  */{
				title : '角色名称',
				field : 'name',
				width : 70,
				sortable : false
			}, {
				title : '可访问资源',
				field : 'resourceNames',
				width : 480,
				formatter : function(value, row, index) {
					if (row.id == '1') {
						return '可访问系统所有资源';
					} else {
						return value;
					}
				}
			}, {
				field : 'action',
				title : '操作',
				width : 50,
				formatter : function(value, row, index) {
					if (row.id == '1') {
						return '系统角色';
					} else {
						return formatString('<sec:authorize url="/roleManage/editRole"><img onclick="adminRoleEdit(\'{0}\');" src="{1}" title=\"编辑\"/></sec:authorize>&nbsp;&nbsp;'+
						'<sec:authorize url="/roleManage/deleteRole"><img onclick="admin_jsgl_deleteFun(\'{2}\');" src="{3}" title=\"删除\"/></sec:authorize>',
						 row.id, '${pageContext.request.contextPath}/source/images/edit.png',
						  row.id, '${pageContext.request.contextPath}/source/images/delete.png');
					}
				}
			}
			] ]
		});
	});

	function adminRoleEdit(id) {
		$('#adminRoleDatagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/pages/admin/roleEdit.jsp',
			width : 500,
			height : 200,
			modal : true,
			resizable:true,
			title : '编辑角色',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var result = $('#adminRoleEditForm').form('validate');
					if(!result) {
						return false;
					}
					if(!nameFlag)
						return false;
					if(execute == true){
						return;
					}
					execute = true;
					var d = $(this).closest('.window-body');
					$.post('${pageContext.request.contextPath}/sysUserController/editRole',
					$("#adminRoleEditForm").serializeArray(),function(r){
						if (r.success) {
								$('#adminRoleDatagrid').datagrid('updateRow', {
									index : $('#adminRoleDatagrid').datagrid('getRowIndex', id),
									row : r.obj
								});
								d.dialog('destroy');
						}
						if(r.msg!=undefined)
							$.messager.alert('提示',r.msg,'info');
						else
							toLogin();
						execute = false;
						}
					);
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			},
			onLoad : function() {
				nameFlag = true;
				var index = $('#adminRoleDatagrid').datagrid('getRowIndex', id);
				var rows = $('#adminRoleDatagrid').datagrid('getRows');
				var o = rows[index];
				var s = rows[index].resourceIds;
				o.resourceIds = stringToList(rows[index].resourceIds);
				$('#adminRoleEditForm').form('load', o);
				o.resourceIds = s;
				
			}
		});
	}
	function adminRoleAppend() {
		$('#adminRoleDatagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/pages/admin/roleEdit.jsp',
			width : 500,
			height : 200,
			modal : true,
			resizable:true,
			title : '添加角色',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var result = $('#adminRoleEditForm').form('validate');
					if(!result) {
						return false;
					}
					if(!nameFlag)
						return false;
					if(execute == true){
						return;
					}
					execute = true;
					var d = $(this).closest('.window-body');
					$.post('${pageContext.request.contextPath}/sysUserController/addRole',
					$("#adminRoleEditForm").serializeArray(),function(r){
						if (r.success) {									
							$('#adminRoleDatagrid').datagrid('load');
							d.dialog('destroy');
						}
						if(r.msg!=undefined)
							$.messager.alert( '提示', r.msg,'info');
						else
							toLogin();
						execute = false;
					});
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}
	function adminRoleRemove() {
		var rows = $('#adminRoleDatagrid').datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			$.messager.confirm('确认', '您是否要删除当前选中的项目？', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/sysUserController/deleteRole',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(result) {
							$('#adminRoleDatagrid').datagrid('reload');
							$('#adminRoleDatagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
							$.messager.alert('提示', /*result.msg*/'执行成功','info');
						}
					});
				}
			});
		} else {
			$.messager.alert( '提示', '请勾选要删除的记录！','error');
		}
	}
	function admin_jsgl_deleteFun(id) {
		$('#adminRoleDatagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		$('#adminRoleDatagrid').datagrid('checkRow', $('#adminRoleDatagrid').datagrid('getRowIndex', id));
		adminRoleRemove();
	}
</script>
<table id="adminRoleDatagrid"></table>

<div id="roleToolBar" style="height: 25px;" class="datagrid-toolbar">
	<div style="float:left;">
		<sec:authorize url="/roleManage/addRole">
        <a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="adminRoleAppend()">添加角色</a>
		</sec:authorize>
		<sec:authorize url="/roleManage/deleteRole">
        <a href="#" class="easyui-linkbutton" plain="true" icon="icon-remove" onclick="adminRoleRemove()">批量删除</a>
		</sec:authorize>
    </div>
</div>