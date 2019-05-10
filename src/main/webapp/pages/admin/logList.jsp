<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<script type="text/javascript">
$(function() {
	$('#logDatagrid').datagrid({
		url : '${pageContext.request.contextPath}/sysUserController/querySystemLog',
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
		toolbar: '#logListToolBar',
		frozenColumns : [ [ {
			field : 'id',
			title : '编号',
			width : 20,
			checkbox : true
		}]],
		columns : [ [  
		{
			field : 'moduleName',
			title : '模块名称',
			width : 80,
			sortable : false
		}, {
			field : 'operatingFunction',
			title : '操作功能',
			width : 80,
			sortable : false
		}, {
			field : 'userName',
			title : '操作用户',
			width : 80,
			sortable : false
		}, {
			field : 'operatingDesc',
			title : '操作描述',
			width : 100,
			sortable : true
		}, {
			field: 'operatingDate',
			title: '操作时间',
			width: 80,
			sortable : true
		}, {
			field : 'action',
			title : '操作',
			width : 40,
			formatter : function(value, row, index) {
				if (row.id == '1') {
					return '系统用户';
				} else {
					 return formatString('<sec:authorize url="/logManage/deleteLog"><img onclick=\"log_jsgl_removeFun(\'{0}\');\" src=\"{1}\" title=\"删除日志\"/>&nbsp;</sec:authorize>',
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

function logRemoveAll() {
	var rows = $('#logDatagrid').datagrid('getChecked');
	var ids = [];
	for(var i=0;i<rows.length;i++){
		ids.push(rows[i].id);
	}
	if(rows.length > 0) {
		$.messager.confirm('确认', '您是否要删除当前选中的项目？', function(r) {
			if (r) {
			
				
				$.ajax({
					url:'${pageContext.request.contextPath}/sysUserController/deleteLog',
				data:{
					ids:ids.join(',')
				},
				dataType:'json',
				success:function(result){
					$('#logDatagrid').datagrid('reload');
					if(result.success){
						$.messager.alert('提示', '删除成功', 'info');
					}else{
						$.messager.alert('提示', '删除失败','error');
					}
				}
				});
			}
		});
	}else{
		$.messager.alert('提示', '请选择要删除的记录');
	}
}

function log_jsgl_removeFun(id){
	$('#logDatagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
	$('#logDatagrid').datagrid('checkRow', $('#logDatagrid').datagrid('getRowIndex', id));
	logRemoveAll();
}

function searchLogByUsername(){
	$("#logDatagrid").datagrid('getPager').pagination({pageNumber:1});
	var params = $('#logDatagrid').datagrid('options').queryParams;
	var userName = $("#username").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	params["userName"]=userName;
	params["startTime"]=startTime;
	params["endTime"]=endTime;
	$('#logDatagrid').datagrid('options').queryParams=params;
	$('#logDatagrid').datagrid('reload');
}

function downloadLogToExcel(){
	var form = $("<form>");   //定义一个form表单
    form.attr('style', 'display:none');   //在form表单中添加查询参数
    form.attr('target', '');
    form.attr('method', 'post');
    form.attr('action', '${pageContext.request.contextPath}/sysUserController/downloadLogToExcel');
    $('body').append(form);  //将表单放置在web中
    form.submit(); 
}
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'center',border:false">
		<table id="logDatagrid"></table>
	</div>
</div>
<div id="logListToolBar" style="height: 25px;" class="datagrid-toolbar">
	<div style="float: left;">
		<sec:authorize url="/logManage/deleteLog">
        	<a href="#" class="easyui-linkbutton" plain="true" icon="icon-remove" onclick="logRemoveAll()">批量删除</a>
		</sec:authorize>
		<sec:authorize url="/logManage/exportLog">
			<a href="#" class="easyui-linkbutton" plain="true" icon="icon-excel" onclick="downloadLogToExcel()">导出到excel表格</a>
		</sec:authorize>
	</div>
	<div style="float: right;">
		用户名：<input type="input" id="username" name="username"/>&nbsp;
		起止时间：<input id="startTime" name="startTime" type="text" onClick="WdatePicker()"/>&nbsp;&nbsp;至&nbsp;&nbsp;<input id="endTime" name="endTime" type="text" onClick="WdatePicker()"/>&nbsp;&nbsp;
		<img onclick="searchLogByUsername();" src="source/images/search.gif" title="搜索"/>&nbsp;&nbsp;
	</div>
</div>
</html>