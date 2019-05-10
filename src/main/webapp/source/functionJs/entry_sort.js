//��������
function editSortByOne(entryId,sortIndex,direction,nodeId,total){
	var destSortIndex=0;
	if(sortIndex==1&&direction==-1){
		msgShow('ϵͳ��ʾ', '�Ѿ��ǵ�һλ���������ƣ�', 'warning');
		return;
	}
	if(sortIndex==total&&direction==1){
		msgShow('ϵͳ��ʾ', '�Ѿ������һλ���������ƣ�', 'warning');
		return;
	}
	if (direction==-1) {//����
		destSortIndex=parseInt(sortIndex)-1;
	}else if (direction==1) {//����
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
			msgShow('ϵͳ��ʾ', '����ʧ�ܣ�', 'warning');
		}
	});
}
//�ö��õ�����
function editSortFast(entryId,sortIndex,direction,nodeId,total){
	var destSortIndex=0;
	if(sortIndex==1&&direction==-1){
		msgShow('ϵͳ��ʾ', '�Ѿ��ǵ�һλ�������ö���', 'warning');
		return;
	}
	if(sortIndex==total&&direction==1){
		msgShow('ϵͳ��ʾ', '�Ѿ������һλ�������õף�', 'warning');
		return;
	}
	if (direction==-1) {//�ö�
		destSortIndex=1;
	}else if (direction==1) {//�õ�
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
			msgShow('ϵͳ��ʾ', '����ʧ�ܣ�', 'warning');
		}
	});
}
//��д��ʽ�ı�����
function jumpSort(entryId,sortIndex,nodeId,total){
	var destSortIndex=$("#sortValue"+entryId).val();
	if(destSortIndex==""){
		msgShow('ϵͳ��ʾ', 'λ�ò���Ϊ�գ�', 'warning');
		// $('#entryList').datagrid('reload');
		return;	
	}
	destSortIndex=parseInt(destSortIndex);
	if(destSortIndex<=0){
		msgShow('ϵͳ��ʾ', 'λ��ֵ�������0��', 'warning');
		 //$('#entryList').datagrid('reload');
		return;
	}
	if(destSortIndex>total){
		msgShow('ϵͳ��ʾ', 'û�и�λ�ã�', 'warning');
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
			 msgShow('ϵͳ��ʾ', '����ʧ�ܣ�', 'warning');
		}
	}); 
}