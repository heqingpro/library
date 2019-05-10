<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript">
	var execute = false;

	$(function() {

		$('#adminUserDatagrid').datagrid({
			url : '${pageContext.request.contextPath}/sysUserController/querySysUser',
			
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
			/* rownumbers: true, */
			striped : true,
			toolbar: '#userListToolBar',
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				width : 20,
				checkbox : true
			}]],
			columns : [ [  {
				field : 'userName',
				title : '帐号',
				width : 80,
				sortable : false
			}, {
				field : 'email',
				title : '电子邮箱',
				width : 80,
				sortable : false
			}, {
				field : 'phone',
				title : '联系电话',
				width : 80,
				sortable : false
			}, {
				field : 'createTime',
				title : '创建时间',
				width : 100,
				sortable : true
			}, {
				field: 'userTypeInfo',
				title: '用户类型',
				width: 60,
				sortable : true
			}, {
				field : 'roleIds',
				title : '所属角色ID',
				width : 80,
				hidden : true
			}, {
				field : 'roleNames',
				title : '所属角色',
				width : 120
			},{
				field : 'appNames',
				title : '所属专区',
				width : 120
			},
			{
				field : 'action',
				title : '操作',
				width : 50,
				formatter : function(value, row, index) {
					if (row.id == '1') {
						return '系统用户';
					} else {
						return formatString('<sec:authorize url="/userManage/editSysUser"><img onclick=\"adminUserEdit(\'{0}\');\" src=\"{1}\" title=\"编辑用户\"/>&nbsp;&nbsp;</sec:authorize>'+
						'<sec:authorize url="/userManage/resetPassword"><img onclick=\"admin_user_resetpwd(\'{2}\');\" src=\"{3}\" title=\"重置密码\"/>&nbsp;&nbsp;</sec:authorize>'+
						'<sec:authorize url="/userManage/deleteSysUser"><img onclick=\"adminUserRemove(\'{4}\');\" src=\"{5}\" title=\"删除用户\"/></sec:authorize>',
						 row.id, '${pageContext.request.contextPath}/source/images/edit.png', 
						 row.id, '${pageContext.request.contextPath}/source/images/key.png',
						 row.id, '${pageContext.request.contextPath}/source/images/delete.png');
					}
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
	
	

	function adminUserAdd() {
		$('#adminUserDatagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/pages/admin/userEdit.jsp',
			width : 560,
			height : 380,
			modal : true,
			resizable:true,
			title : '添加用户',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var result = $('#adminUserEditForm').form('validate');
					if(!result) {
						return false;
					}
					var d = $(this).closest('.window-body');
					if(passFlag == false)
						return false;
					if(passFormatFlag == false)
						return false;
					if(mailFlag == false)
						return false;
					if(nameFlag == false)
						return false;
					if(mailRepeatFlag == false)
						return false;
					if(phoneFlag == false)
						return false;
					if(nameFormatFlag == false)
						return false;
						
					if(execute == true){
						return;
					}
					execute = true;
					$.post('${pageContext.request.contextPath}/sysUserController/addSysUser',
					$("#adminUserEditForm").serializeArray(),function(r){
						if (r.success) {
								$('#adminUserDatagrid').datagrid('reload');
								d.dialog('destroy');
								$.messager.alert('提示', '添加成功');
						}else{
							if(r.msg!=undefined)
								$.messager.alert('提示', '添加失败');
							else
								toLogin();
						}
						execute = false;
					}	
					);	
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	function adminUserEdit(id) {
		$('#adminUserDatagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/pages/admin/userEdit.jsp',
			width : 560,
			height : 380,
			modal : true,
			resizable:true,
			title : '编辑用户',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var result = $('#adminUserEditForm').form('validate');
					if(!result) {
						return false;
					}
					var d = $(this).closest('.window-body');
					if(mailFlag == false)
						return false;
					if(nameFlag == false)
						return false;
					if(mailRepeatFlag == false)
						return false;
					if(phoneFlag == false)
						return false;
					if(nameFormatFlag == false)
						return false;
						
					if(execute == true){
						return;
					}
					execute = true;
					$.post('${pageContext.request.contextPath}/sysUserController/editSysUser',
							$("#adminUserEditForm").serializeArray(),function(r){
								if (r.success) {
									$('#adminUserDatagrid').datagrid('reload');
									d.dialog('destroy');
									$.messager.alert('提示', '编辑成功');
								}else{
									if(r.msg!=undefined)
										$.messager.alert('提示', '编辑失败');
									else
										toLogin();
								}
								execute = false;
							}
					);
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			},
			onLoad : function() {
				var index = $('#adminUserDatagrid').datagrid('getRowIndex', id);
				var rows = $('#adminUserDatagrid').datagrid('getRows');
				var o = rows[index];
				if(!(o.roleIds instanceof Array)){
					o.roleIds = stringToList(o.roleIds);
				}
				if(!(o.appIds instanceof Array)){
					o.appIds = stringToList(o.appIds);
				}
				
				$("#pass_tr").html("");
				$("#passagain_tr").html("");
				$('#adminUserEditForm').form('load', o);
				$("#user_name").attr("readonly","true");
				mailFlag = true;
				mailRepeatFlag = true;
				nameFlag = true;
				phoneFlag = true;
				nameFormatFlag = true;
				isUpdate = true;
			}
		});
	}
	
	function adminUserRemoveAll() {
		var rows = $('#adminUserDatagrid').datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			var currentUserId = '${session_user_id}';/*当前登录用户的ID*/
			var flag = false;
			for ( var i = 0; i < rows.length; i++) {
				if (currentUserId != rows[i].id) {
					ids.push(rows[i].id);
				} else {
					flag = true;
				}
			}
			$.messager.confirm('确认', '您是否要删除当前选中的项目？', function(r) {
				if (r) {
					if (flag) {
						$.messager.alert('提示', '不能删除当前用户');
						return;
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/sysUserController/deleteSysUser',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(result) {
							$('#adminUserDatagrid').datagrid('reload');
							if (result.success) {
								$.messager.alert('提示', '删除成功','info');
							}else{
								if(result.msg!=undefined)
									$.messager.alert('提示', '删除失败','error');
								else
									toLogin();
								
							}
						}
					});
				}
			});
		} else {
			$.messager.alert('提示', '请选择要删除的记录');
		}
	}
	
	function adminUserRemove(id) {
		$('#adminUserDatagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		$('#adminUserDatagrid').datagrid('checkRow', $('#adminUserDatagrid').datagrid('getRowIndex', id));
		adminUserRemoveAll();
	}
	
	function adminUserHasAppEdit() {
		var rows = $('#adminUserDatagrid').datagrid('getChecked');
		var ids = [];
		var sysUserFalg = false;
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				if(rows[i].id == "1"){
					sysUserFalg = true;
				}
				ids.push(rows[i].id);
			}
			if(sysUserFalg){
				$.messager.alert('提示', '系统用户不能编辑');
				return;
			}
			$('<div/>').dialog({
				href : '${pageContext.request.contextPath}/pages/admin/userEditApp.jsp',
				width : 360,
				height : 320,
				modal : true,
				resizable:true,
				title : '批量编辑用户授权内容提供商',
				buttons : [ {
					text : '确定',
					iconCls : 'icon-edit',
					handler : function() {
						if(execute == true){
							return;
						}
						execute = true;
						var d = $(this).closest('.window-body');
						$.post('${pageContext.request.contextPath}/sysUserController/editUserHasApp',
							$("#adminUserAppEditForm").serializeArray(),function(r){
								if (r.success) {
									$('#adminUserDatagrid').datagrid('reload');
									d.dialog('destroy');
									$.messager.alert('提示', '编辑成功');
								}else{
									if(r.msg!=undefined)
										$.messager.alert('提示', '编辑失败');
									else
										toLogin();
								}
								execute = false;
							}
						);
					}
				} ],
				onClose : function() {
					$(this).dialog('destroy');
				},
				onLoad : function() {
					$('#adminUserAppEditForm').form('load', {
						ids : ids
					});
				}
			});
		} else {
			$.messager.alert('提示', '请选择要编辑的用户');
		}
	}
	
	function adminUserEditRole() {
		var rows = $('#adminUserDatagrid').datagrid('getChecked');
		var ids = [];
		var sysUserFalg = false;
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				if(rows[i].id == "1"){
					sysUserFalg = true;
				}
				ids.push(rows[i].id);
			}
			if(sysUserFalg){
				$.messager.alert('提示', '系统用户不能编辑');
				return;
			}
			$('<div/>').dialog({
				href : '${pageContext.request.contextPath}/pages/admin/userEditRole.jsp',
				width : 360,
				height : 320,
				modal : true,
				resizable:true,
				title : '批量编辑用户角色',
				buttons : [ {
					text : '确定',
					iconCls : 'icon-edit',
					handler : function() {
						if(execute == true){
							return;
						}
						execute = true;
						var d = $(this).closest('.window-body');
						$.post('${pageContext.request.contextPath}/sysUserController/editUserRole',
							$("#adminUserRoleEditForm").serializeArray(),function(r){
								if (r.success) {
									$('#adminUserDatagrid').datagrid('reload');
									d.dialog('destroy');
									$.messager.alert('提示', '编辑成功');
								}else{
									if(r.msg!=undefined)
										$.messager.alert('提示', '编辑失败');
									else
										toLogin();
								}
								execute = false;
							}
						);
					}
				} ],
				onClose : function() {
					$(this).dialog('destroy');
				},
				onLoad : function() {
					$('#adminUserRoleEditForm').form('load', {
						ids : ids
					});
				}
			});
		} else {
			$.messager.alert('提示', '请选择要编辑的用户');
		}
	}
	
	function admin_user_resetpwd(id){

		$.messager.confirm('提示', '密码将被重置，您确认要重置密码吗？', function(result) {
			if (result) {
				$.ajax({
					url : '${pageContext.request.contextPath}/sysUserController/resetUserPwd',
					data : {
						id : id
					},
					dataType : 'json',
					success : function(result) {
						if (result.success) {
							$.messager.alert('提示', '密码重置成功','info');
						}else{
							if(r.msg!=undefined)
								$.messager.alert('提示', '密码重置失败','error');
							else
								toLogin();
						}
					}
				});
			}
		});

	}
	

	function searchUser() {
		//var params = $('#adminUserDatagrid').datagrid('options').queryParams; //取得 datagrid 的查询参数
		//params[name] = value; //设置查询参数
		//$('#adminUserDatagrid').datagrid('reload');
		$("#adminUserDatagrid").datagrid('getPager').pagination({pageNumber:1});
		var params = $('#adminUserDatagrid').datagrid('options').queryParams;
		var userName = $("#searchUserName").val();
		params["userName"]=userName;
		$('#adminUserDatagrid').datagrid('options').queryParams=params;
		$('#adminUserDatagrid').datagrid('reload');
	}
	
	
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'center',border:false">
		<table id="adminUserDatagrid"></table>
	</div>
</div>
<div id="userListToolBar" style="height: 25px; padding:1px 2px 0 2px;" class="datagrid-toolbar">
	<div style="float:left;">
	<sec:authorize url="/userManage/addSysUser">
        <a href="javascript:void(0);" class="easyui-linkbutton" plain="true"  icon="icon-add" onclick="adminUserAdd()">添加用户</a>
	</sec:authorize>
	<sec:authorize url="/userManage/deleteSysUser">	
        <a href="javascript:void(0);" class="easyui-linkbutton" plain="true" icon="icon-remove" onclick="adminUserRemoveAll()">批量删除</a>
	</sec:authorize>
	<sec:authorize url="/userManage/setSysUserRole">	
        <a href="javascript:void(0);" class="easyui-linkbutton" plain="true" icon="icon-people" onclick="adminUserEditRole()">设置角色</a>
	</sec:authorize>
	<sec:authorize url="/userManage/editSysUserHasApp">
		<a href="javascript:void(0);" class="easyui-linkbutton" plain="true" icon="icon-factory" onclick="adminUserHasAppEdit()">关联内容提供商</a>
	</sec:authorize>
    </div>
    <div style="float:right;">
		<!--
		<input class="easyui-searchbox" data-options="prompt:'请输入查询内容',menu:'#userSearchMenu',searcher:searchUser" style="width:200px"></input>
		<div id="userSearchMenu" style="width:100px">
			<div data-options="name:'userName'">用户名</div>
		</div>
		-->
		用户名：<input type="input" id="searchUserName" name="searchUserName"/>&nbsp;&nbsp;<img onclick="searchUser();" src="source/images/search.gif" title="搜索"/>&nbsp;&nbsp;
    </div>
</div>