<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript">
	var id = "<%=request.getParameter("id")%>"; 	
	var entryDataGrid = $('#userEntryDatagrid');
	$(function() {
		entryDataGrid.datagrid({
			url : '${pageContext.request.contextPath}/appUserController/queryPersonalEntryList?id='+id,
			fit : false,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'id',
			sortOrder : 'asc',
			checkOnSelect : true,
			selectOnCheck : true,
			nowrap : true,
			singleSelect : false,
			toolbar: '#personal_entrys_toolbar',			
			columns : [ [
			{
				title : '编号',
				field : 'id',
				width : 50,
				checkbox : true
			},
			{   title:'收藏类型',
				field:'recordTypeName',
				width:150
			}/* ,
			{
				field : 'action',
				title : '操作',
				width : 150,
				formatter : function(value, row, index) {
						return formatString(
								 '<sec:authorize url="/entryManage/entryDelete"><img onclick=\"entryDelete(\'{0}\');\" src=\"{1}\" title=\"删除素材\"/>删除&nbsp;</sec:authorize>',
								 row.id, '${pageContext.request.contextPath}/source/images/delete.png');
				}
			} */
			] ]		
		});
		
		//下拉列表选项绑定事件
		$("#recordType").combobox({  
		       onSelect: function () {  
		    		var opValue = $("#recordType").combobox('getValue');
		    		alert(opValue);
		    		$("#userEntryDatagrid").datagrid('getPager').pagination({pageNumber:1});
		    		var params = $('#userEntryDatagrid').datagrid('options').queryParams;
		    		params["recordType"]=opValue;
		    		$('#userEntryDatagrid').datagrid('options').queryParams=params;
		    		$('#userEntryDatagrid').datagrid('reload');		    	
		       }  
		 });
	
	
	
		}
	
	);
	
	//素材搜索
	function entrySearch() {
		entryDataGrid.datagrid('getPager').pagination({pageNumber:1});
		var params = entryDataGrid.datagrid('options').queryParams;
		var searchEntryName = $("#searchEntryName").val();
				
		if(searchEntryName!=undefined){
			params["entryName"]=searchEntryName;
		}
		entryDataGrid.datagrid('options').queryParams=params;
		entryDataGrid.datagrid('reload');
	}
	
</script>




<!-- 显示treegrid数据表格 -->
<!-- <table id="treegrid_personal_entrys" style="display: none"></table> -->
<!-- 用户素材表格 -->
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'center',border:false">
		<table id="userEntryDatagrid"></table>
	</div>
</div>
<!-- 显示treegrid数据表格中的toolbar -->
<div id="personal_entrys_toolbar" style="height: 20px;display: none">
	<div style="float:left;">
  		 <select id="recordType" class="easyui-combobox" name="recordType" style="width: 200px;" data-options="required:'true'" >
				<option style="width: 200px" value='-1' selected="selected">全部</option>
				<option style="width: 200px" value='0' >我想做</option>
				<option style="width: 200px" value='1'>我会做</option>
				<option style="width: 200px" value='2'>我做过</option>
				<option style="width: 200px" value='3'>历史记录</option>
		</select>      	
    </div>
   <!--  <div style="float:right;">
		素材名称：<input type="text" id="searchEntryName" name="searchEntryName"/>&nbsp;&nbsp;
	    <img onclick="entrySearch();" src="source/images/search.gif" title="搜索"/>&nbsp;&nbsp;
    </div> -->
</div>
