<%@ page language="java" pageEncoding="UTF-8"%>
<div align="center" style="margin-top: 10px;">
	<form id="user_userInfo_form" method="post" draggable="true">
		<input name="id" type="hidden" value="${session_user_id}" />
		<table class="tableForm">
			<tr>
				<th style="width: 75px;">当前用户名</th>
				<td><input readonly="readonly" value="${session_user_name}" />
				</td>
			</tr>
			<tr>
				<th>当前密码</th>
				<td><input name="password" type="password"
					class="easyui-validatebox"
					data-options="required:'true',missingMessage:'请填写当前密码'" /></td>
			</tr>
			<tr>
				<th>新密码</th>
				<td><input id="newPassword" name="newPassword" type="password"
					class="easyui-validatebox"
					data-options="required:'true',missingMessage:'请填写新密码'" /></td>
			</tr>
			<tr>
				<th>确认新密码</th>
				<td><input name="confirmPassword" type="password"
					class="easyui-validatebox"
					data-options="required:'true',missingMessage:'请填写确认新密码'" /></td>
			</tr>
		</table>
	</form>
</div>