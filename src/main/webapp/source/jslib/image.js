
//打开替换图片对话框
function openReplaceImageDiag(imageId,entryId){
	$('#replaceImageDiag').dialog({  
	    closed: false,  
	    cache: false,  
	    modal: true  
	}); 
	$('#replaceForm').form('clear');
	$("#imageId_replace").val(imageId);
	$("#entryId_replace").val(entryId);
	$("#id").val(entryId);
	$('#preview_single').html("");
}
function closeReplaceImageDiag(){
	$('#replaceImageDiag').dialog('close'); 
}


//预览单张图片
function setImagePreview_single(){
	info="";
	totalNum=0;
	var docObj=null;
	var imgObjPreviewTd=null;
	docObj=document.getElementById("image");
	imgObjPreviewTd=document.getElementById("preview_single");

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
			if(docObj.files[i].size>maxImageSize*1024){
				info+=docObj.files[i].name+",";	
			}
			totalNum++;
			//火狐下，直接设img属性
			imgObjPreviewDiv.style.display = 'block';
			imgObjPreviewDiv.style.margin = '5px'; 
			imgObjPreviewDiv.style.border="1px solid #000";
			//imgObjPreviewDiv.style.float="left";
			imgObjPreview.style.width = '120px';
			imgObjPreview.style.height = '100px'; 
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
			if(docObj.files[i].size>maxImageSize*1024){
				info+=docObj.files[i].name+",";	
			}
			totalNum++;
			imageName.innerHTML=docObj.files[i].name;
			//火狐下，直接设img属性
			imgObjPreviewDiv.style.display = 'block';
			imgObjPreviewDiv.style.margin = '5px'; 
			imgObjPreviewDiv.style.border="1px solid #000";
			//imgObjPreviewDiv.style.float="left";
			imgObjPreview.style.width = '120px';
			imgObjPreview.style.height = '100px'; 
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
		imgObjPreview.style.width = "120px";
		imgObjPreview.style.height = "100px";
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
		$.messager.alert('系统提示', info, 'warning');	  
	}
}
//预览条目图片
function setImagePreview(operationType,_id1,_id2) {
	var docObj=null;
	var imgObjPreviewTd=null;
	docObj=document.getElementById(_id1);
	imgObjPreviewTd=document.getElementById(_id2);
	imgObjPreviewTd.innerHTML="";
	var imgObjPreview=document.createElement("img");
	imgObjPreviewTd.appendChild(imgObjPreview);
	//兼容火狐浏览器
	if(docObj.files && docObj.files[0]){
			//alert("window.URL="+typeof(window.URL));
			if(window.URL!=""&&typeof(window.URL)!="undefined"){
				//火狐下，直接设img属性
				imgObjPreview.style.display='block';
				imgObjPreview.style.width = '150px';
				imgObjPreview.style.height = '120px'; 
				imgObjPreview.style.marginTop = '5px'; 
				imgObjPreview.style.border = '1px solid #000'; 
				//imgObjPreview.src = docObj.files[0].getAsDataURL();
				//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
				imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);	
			}else if(window.webkitURL!=""&&typeof(window.webkitURL)!="undefined"){
				//alert("window.webkitURL="+window.webkitURL);
				//兼容360浏览器
				imgObjPreview.style.display='block';
				imgObjPreview.style.width = '150px';
				imgObjPreview.style.height = '120px'; 
				imgObjPreview.style.marginTop = '5px'; 
				imgObjPreview.style.border = '1px solid #000'; 
				imgObjPreview.src=window.webkitURL.createObjectURL(docObj.files[0]);
			}
		}else{
			//alert("ie");
			//IE下，使用滤镜
			docObj.select();
			var imgSrc = document.selection.createRange().text;
			//必须设置初始大小
			imgObjPreview.style.display='block';
			imgObjPreview.style.width = "150px";
			imgObjPreview.style.height = "120px";
			imgObjPreview.style.marginTop = '5px'; 
			imgObjPreview.style.border = '1px solid #000'; 
			//图片异常的捕捉，防止用户修改后缀来伪造图片
			try{
				imgObjPreview.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
				imgObjPreview.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
			}
			catch(e)
			{
				alert("您上传的图片格式不正确，请重新选择!");
				imgObjPreview.style.display='none';
				document.selection.empty();
				return false;
			}
			return true;
		}
}




