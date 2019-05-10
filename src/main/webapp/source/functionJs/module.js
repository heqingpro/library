//打开文件管理页面
function openFileManageTab(moduleId){
	window.parent.addTab("文件管理","toFileManagePage.action?moduleId="+moduleId,"icon icon-file");
}
//预览节点下的图片
function previewImageOfModule(url){
/*	$('#previewImageDiag').dialog({    
	    closed: false,  
	    cache: false,    
	    modal: false   
	}); 
	$("#previewImage").attr("src","");
	$("#previewImage").attr("src",url);*/
	//window.parent.addTab("预览文件",url+"?random="+Math.random(),"icon icon-search");
	window.open(url+"?random="+Math.random(), "_blank", "scrollbars=yes,resizable=1,modal=false,alwaysRaised=yes,width=1280,height=720,top=50,left=120");
}
//预览节点下的文本内容
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
//删除图片
function delImage(imageId,entryId){
	 $.messager.confirm("提示","确定要删除该图片么?",function(r){
			if(r){
			$.post("delImage.action",{'imageId':imageId},function(result){
				 var resultCode=result.resultCode;
		    	 var msg=result.msg;
				if(resultCode=="success"){
					 msgShow('系统提示', "删除成功！", 'warning',function(){showImages(entryId);});
				}else{
					 msgShow('系统提示', msg, 'warning');
				}
			});
			}
		});
}
//打开替换图片对话框
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
	$('#replaceImageDiagTip').text("图片分辨率为"+width+"×"+height);
}
function closeReplaceImageDiag(){
	$('#replaceImageDiag').dialog('close'); 
}
//替换图片
function replaceImage(){
	$('#replaceForm').form('submit',{
		  method:'POST',
	      url:'replaceImageInModule.action',   
	      onSubmit: function(){
	    	  if($("#image").val()==null||$("#image").val()==""){
	    		  msgShow('系统提示', "未选择任何图片", 'warning');	 
	    		  return false;
	    	  }
	    	  if(info!=""){
    			  msgShow('系统提示', info, 'warning');	 
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
	    	 	msgShow('系统提示', "替换成功！", 'warning',function(){$("#fileList").datagrid('reload');});
	    	 }else if(resultCode=="resolution_error"){
		    		//closeReplaceImageDiag();
		    	 	msgShow('系统提示', msg, 'warning',function(){});
		     }else if(resultCode=="fileSize_error"){
		    		//closeReplaceImageDiag();
		    	 	msgShow('系统提示', msg, 'warning',function(){});
		     }else{
	    		msgShow('系统提示', "替换失败！", 'warning');	  
	    	 }
	    }   
	 }); 
}
//打开修改文本对话框
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
//修改文本
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
	    	 	msgShow('系统提示', "修改成功！", 'warning',function(){$("#fileList").datagrid('reload');});
	    	 }else{
	    		msgShow('系统提示', "修改失败！", 'warning');	  
	    	 }
	    }   
	 }); 
}

//预览单张图片
function setImagePreview_single(_id1,_id2){
	info="";
	totalNum=0;
	var docObj=null;
	var imgObjPreviewTd=null;
	docObj=document.getElementById(_id1);
	imgObjPreviewTd=document.getElementById(_id2);
	
	//兼容火狐浏览器
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
			//火狐下，直接设img属性
			imgObjPreviewDiv.style.display = 'block';
			imgObjPreview.style.margin = '5px'; 
			imgObjPreview.style.border="1px solid #000";
			//imgObjPreviewDiv.style.float="left";
		/*	imgObjPreview.style.width = '400px';
			imgObjPreview.style.height = '250px'; */
			//imgObjPreview.src = docObj.files[0].getAsDataURL();
			//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
			imgObjPreview.src = window.URL.createObjectURL(docObj.files[i]);
			imgObjPreviewDiv.appendChild(imgObjPreview);
			imgObjPreviewDiv.appendChild(imageName);
		}
		
	}else if(window.webkitURL!=""&&typeof(window.webkitURL)!="undefined"){
		imgObjPreviewTd.innerHTML="";
		//兼容360浏览器
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
			//火狐下，直接设img属性
			imgObjPreviewDiv.style.display = 'block';
			imgObjPreview.style.margin = '5px'; 
			imgObjPreview.style.border="1px solid #000";
			//imgObjPreviewDiv.style.float="left";
			/*imgObjPreview.style.width = '400px';
			imgObjPreview.style.height = '250px'; */
			//imgObjPreview.src = docObj.files[0].getAsDataURL();
			//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
			imgObjPreview.src = window.URL.createObjectURL(docObj.files[i]);
			imgObjPreviewDiv.appendChild(imgObjPreview);
			imgObjPreviewDiv.appendChild(imageName);
		}
		
	}else{
		imgObjPreviewTd.innerHTML="";
		var imgObjPreview=document.createElement("img");
		imgObjPreviewTd.appendChild(imgObjPreview);
		//IE下，使用滤镜
		docObj.select();
		var imgSrc = document.selection.createRange().text;
		//必须设置初始大小
		imgObjPreview.style.display='block';
		imgObjPreview.style.width = "400px";
		imgObjPreview.style.height = "250px";
		imgObjPreview.style.margin = '5px'; 
		imgObjPreview.style.border="1px solid #000";
		//图片异常的捕捉，防止用户修改后缀来伪造图片
		try{
			imgObjPreview.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
			imgObjPreview.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
		}
		catch(e)
		{
		imgObjPreview.style.display = 'none';
		document.selection.empty();
		alert("您上传的图片格式不正确，请重新选择!");
		return false;
		}
		return true;
	}
	if(info!=""){
		info=info.substr(0,info.length-1);
		info="图片"+info+"超过大小限制！";
		msgShow('系统提示', info, 'warning');	  
	}
}
//下载模板
function downloadModule(moduleId){
	$.get("checkModule.action",{'moduleId':moduleId},function(json){
    	var resultCode=json.resultCode;
    	var msg=json.msg;
		if(resultCode=="module_exist"){
			window.location.href="downloadModule.action?moduleId="+moduleId;
		}else if(resultCode=="module_notExist"){
			 msgShow('系统提示', "部署包不存在", 'warning',function(){});
		}else{
			 msgShow('系统提示', msg, 'warning');
		}
	});
}