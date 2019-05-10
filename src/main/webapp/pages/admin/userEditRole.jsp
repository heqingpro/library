<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	$(function() {
		$('#adminUserRoleCombogrid').combogrid({
			multiple : true,
			nowrap : false,
			url : '${pageContext.request.contextPath}/sysUserController/queryRole',
			panelWidth : 450,
			panelHeight : 200,
			idField : 'id',
			textField : 'name',
			pagination : true,
			fitColumns : true,
			rownumbers : true,
			editable : false,
			mode : 'remote',
			delay : 500,
			pageSize : 5,
			pageList : [ 5, 10 ],
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				hidden : true
			}, {
				field : 'name',
				title : '角色名称',
				width : 150
			}, {
				title : '可访问资源ID',
				field : 'resourceIds',
				width : 300,
				hidden : true
			}, {
				title : '可访问资源',
				field : 'resourceNames',
				width : 300
			} ] ]
		});
	});
</script>
<div align="center">
	<form id="adminUserRoleEditForm" method="post" draggable="true">
		<input name="ids" readonly="readonly" type="hidden" />
		<table class="tableForm">
			<tr>
				<th>所属角色</th>
				<td><input id="adminUserRoleCombogrid" name="roleIds" style="width: 200px;" /></td>
				<td><img src="${pageContext.request.contextPath}/source/images/no.png" onclick="$('#adminUserRoleCombogrid').combogrid('clear');" />
				</td>
			</tr>
		</table>
	</form>
</div>