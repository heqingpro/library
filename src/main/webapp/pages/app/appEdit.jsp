<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
var enNameFlag = true;
function checkEnName(){
	var enName = $("#enName").val();
	if(enName=="")
		return ;
	if( /[a-zA-Z0-9\s]+/.test(enName)){		
		$("#enNameErrorMsg").hide();
		enNameFlag = true;
	}else{
		enNameFlag = false;
		$("#enNameErrorMsg").show();
	}
}
/* function loadChannel(){
	$('#appChannelId').combotree({
		url : '${pageContext.request.contextPath}/appController/getChannelTreeData',
		idField : 'id',
		textField : 'text',
		parentField :'parentId',
		fitColumns : true,
		multiple:true,
		editable : false,
		formatter:function(node){
			if(node.id.indexOf("fre_")>=0){
				return node.text;
			}else{
					return node.text+'<input id="channelNo_'+node.id+'" class="easyui-numberbox" data-options="required:true,min:1,max:100" style="width:50px" value='+node.channelNo+'>';
				}
			}
	}); 

	$('#appChannelId').treegrid({
		fitColumns:true,
		width:400,
		height:260,
        fit:false,
        idField:'id',
        url:'${pageContext.request.contextPath}/appController/getChannelTreeData',
        method:'get',
        treeField:'text',
        parentField :'parentId',
        checkOnSelect : true,
		selectOnCheck : true,
		singleSelect : false,
    	columns:[[ {title:'',field:'id',checkbox:true},{title:'频道名称',field:'text'},
    		    {field:'channelNo',title:'频道号',width:150,formatter:function(value, row, index){
    				if(row.id.indexOf("fre_")<0){
    					return '<input id="channelNo_'+row.id+'" class="easyui-numberbox" data-options="required:true,min:1,max:100" style="width:50px" value='+row.channelNo+'>';
    				}
    				}}
	    		]]
});
}
function loadServer(){
	$('#appServerId').combogrid({
					multiple : true,
					nowrap : true,
					url : '${pageContext.request.contextPath}/remoteServerController/queryRemoteServerList',
					idField : 'id',
					textField : 'remoteIP',
					fitColumns : true,
					rownumbers : false,
					editable : false,
					checkOnSelect : true,
					selectOnCheck : true,
					columns : [ [
					{
						title : '编号',
						field : 'id',
						width : 50,
						checkbox : true
					},  
					{
						title : '服务器IP',
						field : 'remoteIP',
						width : 110
					}
					] ]
	}); 
} */


</script>
<div align="center" style="margin-top: 30px;">
	<form id="app_form" method="post" draggable="true">
		<input name="id" id="id" type="hidden"/>
		<table class="tableForm" id="tableForm">
			<tr>
				<th >名称：</th>
				<td><input id="appName" name="appName" onkeyup="queryShortName('enName',this.value)" class="easyui-validatebox" style="width: 300px;" data-options="required:'true'" /></td>
			</tr>
			<tr>
				<th >英文名：</th>
				<td><input id="enName" name="enName" class="easyui-validatebox" style="width: 300px;" data-options="required:'true'" onkeyup="checkEnName()"/></td>
				<div id="enNameErrorMsg" style="color:Red;display:none;">* 英文名格式不正确</div>
			</tr>
			<tr>
				<th >备注：</th>
				<td><input id="remark" name="remark" class="easyui-validatebox" style="width: 300px;" /></td>
			</tr>
			<!-- <tr id="tr_appChannelId">
				<th >提供商频道：</th>
				<td>
				<table id="appChannelId" style="display: none" ></table>
				</td>
			</tr>
			<tr id="tr_appServerId">
				<th >存储服务器：</th>
				<td><select id="appServerId" class="easyui-combogrid" name="appServerId" style="width: 300px;" data-options="required:'true'"/></td>
			</tr>
			<tr style="visibility: hidden;">
				<th >发布版本号：</th>
				<td><input id="releaseVersion" name="releaseVersion" class="easyui-validatebox" style="width: 300px;" data-options="required:'true'" /></td>
			</tr> -->
		</table>
	</form>
</div>