//单步排序
function editSortByOne(entryId,sortIndex,direction,nodeId,total){
	var destSortIndex=0;
	if(sortIndex==1&&direction==-1){
		msgShow('系统提示', '已经是第一位，不能上移！', 'warning');
		return;
	}
	if(sortIndex==total&&direction==1){
		msgShow('系统提示', '已经是最后一位，不能下移！', 'warning');
		return;
	}
	if (direction==-1) {//上移
		destSortIndex=parseInt(sortIndex)-1;
	}else if (direction==1) {//下移
		destSortIndex=parseInt(sortIndex)+1;
	}
	$.post("sortByEdit.action",{'entryId':entryId,'sortIndex':sortIndex,'destSortIndex':destSortIndex,"nodeId":nodeId},function(json){
	   	var resultCode=json.resultCode;
		if(resultCode=="sessionFail")
		{
			window.location.href = "../login.jsp";
		}else if(resultCode=="success")
		{
			$('#entryList').datagrid('reload');
		}else{
			msgShow('系统提示', '排序失败！', 'warning');
		}
	});
}
//置顶置底排序
function editSortFast(entryId,sortIndex,direction,nodeId,total){
	var destSortIndex=0;
	if(sortIndex==1&&direction==-1){
		msgShow('系统提示', '已经是第一位，不能置顶！', 'warning');
		return;
	}
	if(sortIndex==total&&direction==1){
		msgShow('系统提示', '已经是最后一位，不能置底！', 'warning');
		return;
	}
	if (direction==-1) {//置顶
		destSortIndex=1;
	}else if (direction==1) {//置底
		destSortIndex=total;
	}
	$.post("sortByEdit.action",{'entryId':entryId,'sortIndex':sortIndex,'destSortIndex':destSortIndex,"nodeId":nodeId},function(json){
	   	var resultCode=json.resultCode;
		if(resultCode=="sessionFail")
		{
			window.location.href = "../login.jsp";
		}else if(resultCode=="success")
		{
			$('#entryList').datagrid('reload');
		}else{
			msgShow('系统提示', '排序失败！', 'warning');
		}
	});
}
//填写方式改变排序
function jumpSort(entryId,sortIndex,nodeId,total){
	var destSortIndex=$("#sortValue"+entryId).val();
	if(destSortIndex==""){
		msgShow('系统提示', '位置不能为空！', 'warning');
		// $('#entryList').datagrid('reload');
		return;	
	}
	destSortIndex=parseInt(destSortIndex);
	if(destSortIndex<=0){
		msgShow('系统提示', '位置值必须大于0！', 'warning');
		 //$('#entryList').datagrid('reload');
		return;
	}
	if(destSortIndex>total){
		msgShow('系统提示', '没有该位置！', 'warning');
		// $('#entryList').datagrid('reload');
		return;
	}
 	$.post("sortByEdit.action",{'entryId':entryId,'sortIndex':sortIndex,'destSortIndex':destSortIndex,"nodeId":nodeId},function(json){
 	 	var resultCode=json.resultCode;
		if(resultCode=="success"){
			 $('#entryList').datagrid('reload');
		}else if(resultCode=="sessionFail")
		{
			window.location = "../login.jsp";
		}else{
			 msgShow('系统提示', '操作失败！', 'warning');
		}
	}); 
}