<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	var nameFlag = false;
	var sysUserEdit = "角色列表";
	var sysUserEditFlag = false;
	var flagCheck = false;
	function checkName(){		
		var id = $("#role_id").val();
		var role_name = $("#role_name").val();
		if(role_name==null||role_name==""){
			return;
		}
		$.post("${pageContext.request.contextPath}/sysUserController/checkRoleName",
			{id:id,name:role_name},function(result){
				if(result){
					nameFlag=true;
					$("#nameMsg").hide();
				}else{
					nameFlag=false;
					$("#nameMsg").show();
					}
				});
	}
	
	
</script>
<div align="center">
	<form id="adminRoleEditForm" method="post"  draggable="true">
		<table class="tableForm">
			<tr>
				<th>角色名称<input type="hidden" id ="role_id" name="id" /></th>
				<td>
					<input id="role_name" onkeyup="checkName();" onblur="checkName()" name="name" class="easyui-validatebox" data-options="required:true" />
					<div id="nameMsg" style="color:Red;display:none;" style="width: 50px;">*角色名称已使用</div>
				</td>
			</tr>
			<tr>
				<th>可访问资源</th>
				<td>
					<input id="role_resourceIds" name="resourceIds" class="easyui-combotree" 
					data-options="url:'${pageContext.request.contextPath}/sysUserController/queryPermissionItem',parentField : 'pid',lines : true,multiple:true"
					style="width: 300px;" />&nbsp;
					<img src="${pageContext.request.contextPath}/source/images/no.png" onclick="$('#role_resourceIds').combotree('clear');" />
				</td>
			</tr>
		</table>
	</form>
</div>