<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	var passFormatFlag = false;
	var passFlag = false;

	function checkPassFormat(){
		var psw = $("#newPwd").val();
		var reg = /^\w{6,16}$/;
		if(reg.test(psw)){
			passFormatFlag = true;
			$("#passFormatMsg").hide();
		}
		else{
			passFormatFlag = false;
			$("#passFormatMsg").show();
		}
	}
	
	function checkPassword(){
		var psw = $("#newPwd").val();
		var psw_a = $("#newPwd2").val();
		if(psw == psw_a){
			passFlag = true;
			$("#passMsg").hide();
		}
		else{
			passFlag = false;
			$("#passMsg").show();	
		}
	}
</script>
<div align="lift" style="margin-top: 10px;">
	<form id="adminPwdUpdateForm" method="post" draggable="true">
		<table class="tableForm">
			<!--<tr>
				<th></th>
				<td><input type="hidden" name="id" /></td>
			</tr>
			<tr>
				<th>用户名</th>
				<td><input name="userName" class="easyui-validatebox" readonly="true" data-options="required:true" style="width:200px;"/></td>
			</tr>!-->
			<tr>
				<th align="right" style="width:80px;">旧密码</th>
				<td><input id="oldPwd" name="oldPwd" class="easyui-validatebox" type="password" data-options="required:true" style="width:150px;" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<th align="right" style="width:80px;">新密码</th>
				<td><input id="newPwd" name="newPwd" onkeyup="checkPassFormat()" class="easyui-validatebox" type="password" data-options="required:true" style="width:150px;" /></td>
				<td><div id="passFormatMsg" style="color:Red;display:none;">*密码格式不正确，长度在6-16之间，只能包含字母、数字和下划线！</div>
				</td>
			</tr>
			<tr>
				<th align="right" style="width:80px;">确认密码</th>
				<td><input id="newPwd2" name="newPwd2" onkeyup="checkPassword()" onblur="checkPassword()"  class="easyui-validatebox" type="password" data-options="required:true" style="width:150px;" /></td>
				<td><div id="passMsg" style="color:Red;display:none;">*密码输入不一致</div>
				</td>
			</tr>
		</table>
	</form>
</div>