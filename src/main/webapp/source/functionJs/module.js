//���ļ�����ҳ��
function openFileManageTab(moduleId){
	window.parent.addTab("�ļ�����","toFileManagePage.action?moduleId="+moduleId,"icon icon-file");
}
//Ԥ���ڵ��µ�ͼƬ
function previewImageOfModule(url){
/*	$('#previewImageDiag').dialog({    
	    closed: false,  
	    cache: false,    
	    modal: false   
	}); 
	$("#previewImage").attr("src","");
	$("#previewImage").attr("src",url);*/
	//window.parent.addTab("Ԥ���ļ�",url+"?random="+Math.random(),"icon icon-search");
	window.open(url+"?random="+Math.random(), "_blank", "scrollbars=yes,resizable=1,modal=false,alwaysRaised=yes,width=1280,height=720,top=50,left=120");
}
//Ԥ���ڵ��µ��ı�����
function previewTxtOfModule(fileName){
	$('#previewTxtDiag').dialog({    
	    closed: false,  
	    cache: false,    
	    modal: false   
	}); 
	$("#content").val("");
	$.get("getContentByFileNameInModule.action?moduleId="+moduleId+"&fileName="+fileName,function(json){
		var content=json.content;
		$("#content").val(content);
	});
	
}
//ɾ��ͼƬ
function delImage(imageId,entryId){
	 $.messager.confirm("��ʾ","ȷ��Ҫɾ����ͼƬô?",function(r){
			if(r){
			$.post("delImage.action",{'imageId':imageId},function(result){
				 var resultCode=result.resultCode;
		    	 var msg=result.msg;
				if(resultCode=="success"){
					 msgShow('ϵͳ��ʾ', "ɾ���ɹ���", 'warning',function(){showImages(entryId);});
				}else{
					 msgShow('ϵͳ��ʾ', msg, 'warning');
				}
			});
			}
		});
}
//���滻ͼƬ�Ի���
function openReplaceImageDiag(filePath,moduleId,imageUrl,width,height,size){
	$('#replaceImageDiag').dialog({  
	    closed: false,  
	    cache: false,  
	    modal: true  
	}); 
	$('#replaceForm').form('clear');
	$("#moduleId_replace").val(moduleId);
	$("#filePath_replace").val(filePath);
	$("#width_replace").val(width);
	$("#height_replace").val(height);
	$("#size_replace").val(size);
	$('#preview_single').html("");
	imageUrl+="?random="+Math.random();
 	$("#preview_single").append("<img src='"+imageUrl+"' style='margin-top:5px;border:1px solid black'></img>");
	$('#replaceImageDiagTip').text("ͼƬ�ֱ���Ϊ"+width+"��"+height);
}
function closeReplaceImageDiag(){
	$('#replaceImageDiag').dialog('close'); 
}
//�滻ͼƬ
function replaceImage(){
	$('#replaceForm').form('submit',{
		  method:'POST',
	      url:'replaceImageInModule.action',   
	      onSubmit: function(){
	    	  if($("#image").val()==null||$("#image").val()==""){
	    		  msgShow('ϵͳ��ʾ', "δѡ���κ�ͼƬ", 'warning');	 
	    		  return false;
	    	  }
	    	  if(info!=""){
    			  msgShow('ϵͳ��ʾ', info, 'warning');	 
    			  return false;
    		  }
	    	  return $(this).form('validate');   
	  	},
	     success:function(result){
	    	 var json = $.parseJSON(result);
	    	 var resultCode=json.resultCode;
	    	 var msg=json.msg;
	    	 if(resultCode=="replace_success"){
	    		closeReplaceImageDiag();
	    	 	msgShow('ϵͳ��ʾ', "�滻�ɹ���", 'warning',function(){$("#fileList").datagrid('reload');});
	    	 }else if(resultCode=="resolution_error"){
		    		//closeReplaceImageDiag();
		    	 	msgShow('ϵͳ��ʾ', msg, 'warning',function(){});
		     }else if(resultCode=="fileSize_error"){
		    		//closeReplaceImageDiag();
		    	 	msgShow('ϵͳ��ʾ', msg, 'warning',function(){});
		     }else{
	    		msgShow('ϵͳ��ʾ', "�滻ʧ�ܣ�", 'warning');	  
	    	 }
	    }   
	 }); 
}
//���޸��ı��Ի���
function openEditTxtDiag(fileName,moduleId){
	$('#editTxtDiag').dialog({  
	    closed: false,  
	    cache: false,  
	    modal: true  
	}); 
	$('#editTxtForm').form('clear');
	$("#moduleId_edit").val(moduleId);
	$("#fileName_edit").val(fileName);
	$("#content_edit").val("");
	$.get("getContentByFileNameInModule.action?moduleId="+moduleId+"&fileName="+fileName,function(json){
		var content=json.content;
		$("#content_edit").val(content);
	});
	
}
function closeEditTxtDiag(){
	$('#editTxtDiag').dialog('close'); 
}
//�޸��ı�
function editTxt(){
	$('#editTxtForm').form('submit',{
		  method:'POST',
	      url:'editTxtInModule.action',   
	      onSubmit: function(){
	    	  return $(this).form('validate');   
	  	},
	     success:function(result){
	    	 var json = $.parseJSON(result);
	    	 var resultCode=json.resultCode;
	    	 var msg=json.msg;
	    	 if(resultCode=="edit_success"){
	    		closeEditTxtDiag();
	    	 	msgShow('ϵͳ��ʾ', "�޸ĳɹ���", 'warning',function(){$("#fileList").datagrid('reload');});
	    	 }else{
	    		msgShow('ϵͳ��ʾ', "�޸�ʧ�ܣ�", 'warning');	  
	    	 }
	    }   
	 }); 
}

//Ԥ������ͼƬ
function setImagePreview_single(_id1,_id2){
	info="";
	totalNum=0;
	var docObj=null;
	var imgObjPreviewTd=null;
	docObj=document.getElementById(_id1);
	imgObjPreviewTd=document.getElementById(_id2);
	
	//���ݻ�������
	if(window.URL!=""&&typeof(window.URL)!="undefined")
	{
		imgObjPreviewTd.innerHTML="";
		for(var i=0;i<docObj.files.length;i++){
			var imgObjPreviewDiv=document.createElement("div");
			imgObjPreviewTd.appendChild(imgObjPreviewDiv);
			var imgObjPreview=document.createElement("img");
			var imageName=document.createElement("div");
			imageName.innerHTML=docObj.files[i].name;
			if(docObj.files[i].size>webPageImageMaxSize*1024){
				info+=docObj.files[i].name+",";	
			}
			totalNum++;
			//����£�ֱ����img����
			imgObjPreviewDiv.style.display = 'block';
			imgObjPreview.style.margin = '5px'; 
			imgObjPreview.style.border="1px solid #000";
			//imgObjPreviewDiv.style.float="left";
		/*	imgObjPreview.style.width = '400px';
			imgObjPreview.style.height = '250px'; */
			//imgObjPreview.src = docObj.files[0].getAsDataURL();
			//���7���ϰ汾�����������getAsDataURL()��ʽ��ȡ����Ҫһ�·�ʽ
			imgObjPreview.src = window.URL.createObjectURL(docObj.files[i]);
			imgObjPreviewDiv.appendChild(imgObjPreview);
			imgObjPreviewDiv.appendChild(imageName);
		}
		
	}else if(window.webkitURL!=""&&typeof(window.webkitURL)!="undefined"){
		imgObjPreviewTd.innerHTML="";
		//����360�����
		for(var i=0;i<docObj.files.length;i++){
			var imgObjPreviewDiv=document.createElement("div");
			imgObjPreviewTd.appendChild(imgObjPreviewDiv);
			var imgObjPreview=document.createElement("img");
			var imageName=document.createElement("div");
			if(docObj.files[i].size>webPageImageMaxSize*1024){
				info+=docObj.files[i].name+",";	
			}
			totalNum++;
			imageName.innerHTML=docObj.files[i].name;
			//����£�ֱ����img����
			imgObjPreviewDiv.style.display = 'block';
			imgObjPreview.style.margin = '5px'; 
			imgObjPreview.style.border="1px solid #000";
			//imgObjPreviewDiv.style.float="left";
			/*imgObjPreview.style.width = '400px';
			imgObjPreview.style.height = '250px'; */
			//imgObjPreview.src = docObj.files[0].getAsDataURL();
			//���7���ϰ汾�����������getAsDataURL()��ʽ��ȡ����Ҫһ�·�ʽ
			imgObjPreview.src = window.URL.createObjectURL(docObj.files[i]);
			imgObjPreviewDiv.appendChild(imgObjPreview);
			imgObjPreviewDiv.appendChild(imageName);
		}
		
	}else{
		imgObjPreviewTd.innerHTML="";
		var imgObjPreview=document.createElement("img");
		imgObjPreviewTd.appendChild(imgObjPreview);
		//IE�£�ʹ���˾�
		docObj.select();
		var imgSrc = document.selection.createRange().text;
		//�������ó�ʼ��С
		imgObjPreview.style.display='block';
		imgObjPreview.style.width = "400px";
		imgObjPreview.style.height = "250px";
		imgObjPreview.style.margin = '5px'; 
		imgObjPreview.style.border="1px solid #000";
		//ͼƬ�쳣�Ĳ�׽����ֹ�û��޸ĺ�׺��α��ͼƬ
		try{
			imgObjPreview.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
			imgObjPreview.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
		}
		catch(e)
		{
		imgObjPreview.style.display = 'none';
		document.selection.empty();
		alert("���ϴ���ͼƬ��ʽ����ȷ��������ѡ��!");
		return false;
		}
		return true;
	}
	if(info!=""){
		info=info.substr(0,info.length-1);
		info="ͼƬ"+info+"������С���ƣ�";
		msgShow('ϵͳ��ʾ', info, 'warning');	  
	}
}
//����ģ��
function downloadModule(moduleId){
	$.get("checkModule.action",{'moduleId':moduleId},function(json){
    	var resultCode=json.resultCode;
    	var msg=json.msg;
		if(resultCode=="module_exist"){
			window.location.href="downloadModule.action?moduleId="+moduleId;
		}else if(resultCode=="module_notExist"){
			 msgShow('ϵͳ��ʾ', "�����������", 'warning',function(){});
		}else{
			 msgShow('ϵͳ��ʾ', msg, 'warning');
		}
	});
}