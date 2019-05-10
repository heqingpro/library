<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	var isUpdate = false;
	var passFlag = false;
	var passFormatFlag = false;
	var mailFlag = false;
	var mailRepeatFlag = false;
	var nameFlag = false;
	var phoneFlag = false;
	var nameFormatFlag = false;
	$(function() {
		$('#adminUserAppendCombogrid')
				.combogrid(
						{
							multiple : true,
							nowrap : false,
							url : '${pageContext.request.contextPath}/sysUserController/queryRole',
							panelWidth : 450,
							panelHeight : 200,
							idField : 'id',
							textField : 'name',
							pagination : false,
							fitColumns : true,
							rownumbers : true,
							editable : false,
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
	
	$('#adminUserAppsCombogrid').combogrid(
				{
					multiple : true,
					nowrap : false,
					url : '${pageContext.request.contextPath}/appController/queryAppList',
					panelWidth : 450,
					panelHeight : 200,
					idField : 'id',
					textField : 'name',
					pagination : false,
					fitColumns : true,
					rownumbers : true,
					editable : false,
					pageSize : 5,
					pageList : [ 5, 10 ],
					columns : [ [ {
						field : 'id',
						title : '编号',
						width : 150,
						hidden : true
					}, {
						field : 'name',
						title : '内容提供商名称',
						width : 150
					}] ]
	});
	function checkPassword(){
		var psw = $("#password").val();
		var psw_a = $("#password_again").val();
		if(psw == psw_a){
			passFlag = true;
			$("#passMsg").hide();
		}
		else{
			passFlag = false;
			$("#passMsg").show();	
		}
	}

	function checkPassFormat(){
		var psw = $("#password").val();
		//var reg = /^[a-zA-Z]\w{5,15}$/;
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

	function checkmail(){
		var mail = $("#mail_adr").val();
		var reg = /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		if(reg.test(mail)){
			mailFlag = true;
			$("#mailMsg").hide();
		}
		else{
			mailFlag = false;
			$("#mailMsg").show();
		}			
	}

	function checkMailRepeat(){
		if(isUpdate){
			return;
		}
		var mail_adr = $("#mail_adr").val();
		var id = $("#user_id").val();
		$.post("${pageContext.request.contextPath}/sysUserController/checkMail",
				{id:id, mail_adr:mail_adr},
				function(result){
					if(result){
						mailRepeatFlag = true;
						$("#mailMsg2").hide();
					}else{
						mailRepeatFlag = false;
						$("#mailMsg2").show();
					}
				})
	}
	function cleanMailMsg(){
		$("#mailMsg2").hide();
		}
	function checkUname(){
		if(isUpdate){
			return;
		}
		var id = $("#user_id").val();
		var user_name = $("#user_name").val();
		$.post("${pageContext.request.contextPath}/sysUserController/checkUname",
				{id:id,user_name:user_name},function(result){
					if(result){
						nameFlag=true;
						$("#nameMsg").hide();
					}else{
						nameFlag=false;
						$("#nameMsg").show();
						}
					});
	}
	function checkTelephone(){
		var psw = $("#telephone").val();
		var reg = /^(\(\d{3,4}\)|\d{3,4}-)?\d{7,8}$/;
		var reg1 = /^\d{11}$/;
		if(reg.test(psw)||reg1.test(psw)){		
			phoneFlag = true;
			$("#phoneMsg").hide();
		}
		else{
			phoneFlag = false;
			$("#phoneMsg").show();
		}
	}
	function checkNameFormat(){
		var uname = $("#user_name").val();
		var reg = /^[\u4e00-\u9fa5A-Za-z0-9]+$/;
		if(reg.test(uname)){
			nameFormatFlag = true;
			$("#nameFormatMsg").hide();
		}
		else{
			nameFormatFlag = false;
			$("#nameFormatMsg").show();
		}
	}
</script>
<div align="lift" style="margin-top: 10px;" >
	<form id="adminUserEditForm" method="post" draggable="true">
		<table class="tableForm">
			<tr>
				<th>用户名<input id="user_id" type="hidden" name="id" /></th>
				<td><input onkeyup="checkUname()" onblur="checkUname()" onchange="checkNameFormat()"  id="user_name" name="userName" class="easyui-validatebox" data-options="required:true" style="width: 150px;" />
				</td>
				<td><div id="nameMsg" style="color:Red;display:none;" style="width: 50px;">*用户名已注册</div>
				<div id="nameFormatMsg" style="color:Red;display:none;" style="width: 50px;">*用户名格式错误！必须由汉字、字母和数字组成！</div></td>
			</tr>
			<tr id="pass_tr">
				<th>密码</th>
				<td><input onkeyup="checkPassFormat()" id="password" name="password" type="password"
					 class="easyui-validatebox" data-options="required:true" style="width: 150px;" />
				</td>
				<td><div id="passFormatMsg" style="color:Red;display:none;">*密码格式不正确，长度在6-16之间，只能包含字母、数字和下划线！</div>
				</td>
			</tr>
			<tr id="passagain_tr">
				<th>确认密码</th>
				<td><input onkeyup="checkPassword()" onblur="checkPassword()"  id="password_again" name="password_againd" type="password"
					class="easyui-validatebox" data-options="required:true"
					style="width: 150px;" /></td>
				<td><div id="passMsg" style="color:Red;display:none;">*密码输入不一致</div>
				</td>
			</tr>
			<tr>
				<th>联系电话</th>
				<td><input onkeyup="checkTelephone()" id="telephone" name="phone" class="easyui-numberbox"
					data-options="required:true" style="width: 150px;" />
				</td>
				<td><div id="phoneMsg" style="color:Red;display:none;">*请输入正确的电话号码！固定电话格式为：（区号）号码，或：区号-号码！手机号码为11位数字！</div>
				</td>
			</tr>
			<tr>
				<th>电子邮箱</th>
				<td><input onblur="checkMailRepeat()" onchange="checkmail()" onkeyup="checkmail()" onkeydown="cleanMailMsg()" id="mail_adr" name="email" class="easyui-validatebox"
					data-options="required:true" style="width: 200px;" /></td>
				<td><div id="mailMsg" style="color:Red;display:none;" style="width: 50px;">*邮箱格式错误</div>
					<div id="mailMsg2" style="color:Red;display:none;" style="width: 50px;">*邮箱已注册</div>
				</td>
			</tr>
			<tr>
				<th style="width: 60px;">所属角色</th>
				<td><input id="adminUserAppendCombogrid" name="roleIds"
					data-options="required:true" style="width: 204px;" /></td>
			</tr>
			<tr>
				<th style="width: 60px;">授权内容提供商</th>
				<td><input id="adminUserAppsCombogrid" name="appIds"
					data-options="required:true" style="width: 204px;" /></td>
			</tr>
		</table>
	</form>
</div>
