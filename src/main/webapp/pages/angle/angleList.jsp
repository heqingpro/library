<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!--  语种管理-->
<script type="text/javascript">
	var angleDataGrid=$('#angleDataGrid');
	$(function() {
		angleDataGrid.datagrid({
			url : '${pageContext.request.contextPath}/angleController/queryAngleList',
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
			toolbar: '#angleToolBar',
			columns : [ [
			{
				title : '编号',
				field : 'id',
				width : 50,
				checkbox : true
			}, {
				title : '名称',
				field : 'name',
				width : 100
			}, {
				title : 'logo',
				field : 'imageUrl',
				width : 100,
				formatter : function(value, row, index) {
					 var img = "<img style=\"align:center\"   src='" + value + "' width='30px' height='30px' \"/>";
			         return img; 
				}
			},
			 {
				field : 'action',
				title : '操作',
				width : 150,
				formatter : function(value, row, index) {
						return formatString(
						'<sec:authorize url="/angleManage/angleDelete"><img onclick=\"angleDelete(\'{2}\');\" src=\"{3}\" title=\"删除语种\"/></sec:authorize>',
						 row.id, '${pageContext.request.contextPath}/source/images/detail.png', 
						 row.id, '${pageContext.request.contextPath}/source/images/delete.png');
				}
			}
		] ]
	})});
	//语种新增
	function angleAdd(){
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/pages/angle/angleEdit.jsp',
			width : 550,
			height : 350,
			modal : true,
			resizable:true,
			title : '新增语种',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var result = $('#angle_form').form('validate');
						if(!result) {
						return false;
					}
					var d=$(this).closest('.window-body');	
					$('#angle_form').form('submit',{
						 url:'${pageContext.request.contextPath}/angleController/addAngle',
						 success:function(result){
							 var r = JSON.parse(result);
							 if (r.success) {
								 	angleDataGrid.datagrid('reload');
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
	
	//语种编辑
	function angleEdit(){
		var rows = angleDataGrid.datagrid('getSelections');
		if (rows.length > 0) {
			if(rows.length>1){
				$.messager.alert('提示', '只能编辑单个语种');
			}else{
				$('<div/>').dialog({
					href : '${pageContext.request.contextPath}/pages/angle/angleEdit.jsp',
					width : 550,
					height :350,
					modal : true,
					resizable:true,
					title : '编辑语种',
					buttons : [ {
						text : '确定',
						iconCls : 'icon-ok',
						handler : function() {
							var result = $('#angle_form').form('validate');
							if(!result) {
								return false;
							}
							var d = $(this).closest('.window-body');
							$('#angle_form').form('submit',{
								 url:'${pageContext.request.contextPath}/angleController/editAngle',
								 success:function(result){
									 var r = JSON.parse(result);
									 if (r.success) {
										 	angleDataGrid.datagrid('reload');
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
						var angleId=rows[0].id;
						var row = rows[0];
						var imageUrl = row.imageUrl+"?t="+Math.random();
						if(row.imageUrl!=null&&row.imageUrl!=""){
							$("#previewImage").append("<img src='"+imageUrl+"' style='margin-top:5px;width: 150px;height: 120px;border:1px solid black'></img>");	
						}
						$('#angle_form').form('load',row);
						$("#id").val(angleId);
						//清除所选择的内容提供商
						angleDataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
					}
				});
			}
		} else {
			$.messager.alert('提示', '请选择要编辑的语种');
		}
	}


	//语种删除
	function angleDelete(id){
		angleDataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		angleDataGrid.datagrid('checkRow', angleDataGrid.datagrid('getRowIndex', id));
		angleDeleteAll();
	}
	
	//语种批量删除
	function angleDeleteAll(){
		var rows = angleDataGrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			$.messager.confirm('确认', '您是否要删除当前选中的语种？', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/angleController/deleteAngle',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(result) {
							angleDataGrid.datagrid('reload');
							if (result.success) {
								$.messager.alert('提示', '删除成功','info');
								angleDataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
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
			$.messager.alert('提示', '请选择要删除的语种');
		}
	}
	
	//语种搜索
	function angleSearch() {
		angleDataGrid.datagrid('getPager').pagination({pageNumber:1});
		var params = angleDataGrid.datagrid('options').queryParams;
		var searchAppName = $("#searchAppName").val();
		
		if(searchAppName!=undefined){
			params["angleName"]=searchAppName;
		}
		angleDataGrid.datagrid('options').queryParams=params;
		angleDataGrid.datagrid('reload');
	}
	
</script>



<!-- 显示datagrid数据表格 -->
<table id="angleDataGrid" style="display: none" singleSelect=true></table>
<!-- 显示datagrid数据表格中的toolbar -->
<div id="angleToolBar" style="height: 25px;display: none; padding:1px 2px 0 2px;">
	<div style="float:left;">
		<sec:authorize url="/angleManage/angleAdd">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="angleAdd()">新增</a>
		</sec:authorize>
		<sec:authorize url="/angleManage/angleEdit">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="angleEdit()">修改</a>
        </sec:authorize>
		<sec:authorize url="/angleManage/angleDelete">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="angleDeleteAll()">删除</a>
		</sec:authorize>
		<%-- <sec:authorize url="/angleManage/angleSort">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="angleSort()">排序</a>
		</sec:authorize> --%>
		
    </div>
	<div style="float:right;">
		语种名称：<input type="input" id="searchAppName" name="searchAppName"/>&nbsp;&nbsp;
	    <img onclick="angleSearch();" src="source/images/search.gif" title="搜索"/>&nbsp;&nbsp;
    </div>
</div>
