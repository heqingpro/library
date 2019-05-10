<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	$(function() {
		$('#adminUserAppCombogrid').combogrid({
			multiple : true,
			nowrap : false,
			url : '${pageContext.request.contextPath}/appController/queryAppList',
			panelWidth : 300,
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
				title : '专区名称',
				width : 100
			}
			] ]
		});
	});
</script>
<div align="center" draggable="true">
	<form id="adminUserAppEditForm" method="post" draggable="true" >
		<input name="ids" readonly="readonly" type="hidden" />
		<table class="tableForm">
			<tr>
				<th>内容提供商列表</th>
				<td><input id="adminUserAppCombogrid" name="appIds" style="width: 200px;" /></td>
				<td><img src="${pageContext.request.contextPath}/source/images/no.png" onclick="$('#adminUserAppCombogrid').combogrid('clear');" />
				</td>
			</tr>
		</table>
	</form>
</div>