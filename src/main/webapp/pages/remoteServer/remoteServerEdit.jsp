<%@ page language="java" pageEncoding="UTF-8"%>
<div align="center" style="margin-top: 10px;">
	<form id="remoteServer_form" method="post" draggable="true" >
		<input name="id" type="hidden"/>
		<table class="tableForm" id="tableForm">
			<tr>
				<th >服务器名称：</th>
				<td><input id="remoteServerName" name="remoteServerName" class="easyui-validatebox" data-options="required:'true'" /></td>
			</tr>
			<tr>
				<th >服务器IP：</th>
				<td><input id="remoteIP" name="remoteIP" class="easyui-validatebox" data-options="required:'true'" /></td>
			</tr>
			
			<tr>
				<th>服务器端口：</th>
				<td><input id="remotePort" name="remotePort" class="easyui-validatebox" data-options="required:'true'" /></td>
			</tr>
			
			<tr>
				<th>服务器用户名：</th>
				<td><input id="userName" name="userName"  class="easyui-validatebox" data-options="required:'true'" /></td>
			</tr>
			
			<tr>
				<th>服务器密码：</th>
				<td><input id="userPass" name="userPass"  class="easyui-validatebox" data-options="required:'true'" /></td>
			</tr>
		</table>
	</form>
</div>