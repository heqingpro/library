
	
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
function openReplaceImageDiag(imageId,entryId){
	$('#replaceImageDiag').dialog({  
	    closed: false,  
	    cache: false,  
	    modal: true  
	}); 
	$('#replaceForm').form('clear');
	$("#imageId_replace").val(imageId);
	$("#entryId_replace").val(entryId);
	$('#preview_single').html("");
}
function closeReplaceImageDiag(){
	$('#replaceImageDiag').dialog('close'); 
}
//�滻ͼƬ
function replaceImage(entryId){
	$('#replaceForm').form('submit',{
		  method:'POST',
	      url:'replaceImage.action',   
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
	    	 if(resultCode=="success"){
	    		closeReplaceImageDiag();
	    	 	msgShow('ϵͳ��ʾ', "�滻�ɹ���", 'warning',function(){showImages(entryId);});
	    	 }else{
	    		msgShow('ϵͳ��ʾ', "�滻ʧ�ܣ�", 'warning');	  
	    	 }
	    }   
	 }); 
}

//Ԥ������ͼƬ
function setImagePreview_single(){
	info="";
	totalNum=0;
	var docObj=null;
	var imgObjPreviewTd=null;
	docObj=document.getElementById("image");
	imgObjPreviewTd=document.getElementById("preview_single");

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
			if(docObj.files[i].size>maxImageSize*1024){
				info+=docObj.files[i].name+",";	
			}
			totalNum++;
			//����£�ֱ����img����
			imgObjPreviewDiv.style.display = 'block';
			imgObjPreviewDiv.style.margin = '5px'; 
			imgObjPreviewDiv.style.border="1px solid #000";
			//imgObjPreviewDiv.style.float="left";
			imgObjPreview.style.width = '120px';
			imgObjPreview.style.height = '100px'; 
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
			if(docObj.files[i].size>maxImageSize*1024){
				info+=docObj.files[i].name+",";	
			}
			totalNum++;
			imageName.innerHTML=docObj.files[i].name;
			//����£�ֱ����img����
			imgObjPreviewDiv.style.display = 'block';
			imgObjPreviewDiv.style.margin = '5px'; 
			imgObjPreviewDiv.style.border="1px solid #000";
			//imgObjPreviewDiv.style.float="left";
			imgObjPreview.style.width = '120px';
			imgObjPreview.style.height = '100px'; 
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
		imgObjPreview.style.width = "120px";
		imgObjPreview.style.height = "100px";
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
//Ԥ����ĿͼƬ
function setImagePreview(operationType,_id1,_id2) {
	var docObj=null;
	var imgObjPreviewTd=null;
	docObj=document.getElementById(_id1);
	imgObjPreviewTd=document.getElementById(_id2);
	imgObjPreviewTd.innerHTML="";
	var imgObjPreview=document.createElement("img");
	imgObjPreviewTd.appendChild(imgObjPreview);
	//���ݻ�������
	if(docObj.files && docObj.files[0]){
			//alert("window.URL="+typeof(window.URL));
			if(window.URL!=""&&typeof(window.URL)!="undefined"){
				//����£�ֱ����img����
				imgObjPreview.style.display='block';
				imgObjPreview.style.width = '150px';
				imgObjPreview.style.height = '120px'; 
				imgObjPreview.style.marginTop = '5px'; 
				imgObjPreview.style.border = '1px solid #000'; 
				//imgObjPreview.src = docObj.files[0].getAsDataURL();
				//���7���ϰ汾�����������getAsDataURL()��ʽ��ȡ����Ҫһ�·�ʽ
				imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);	
			}else if(window.webkitURL!=""&&typeof(window.webkitURL)!="undefined"){
				//alert("window.webkitURL="+window.webkitURL);
				//����360�����
				imgObjPreview.style.display='block';
				imgObjPreview.style.width = '150px';
				imgObjPreview.style.height = '120px'; 
				imgObjPreview.style.marginTop = '5px'; 
				imgObjPreview.style.border = '1px solid #000'; 
				imgObjPreview.src=window.webkitURL.createObjectURL(docObj.files[0]);
			}
		}else{
			//alert("ie");
			//IE�£�ʹ���˾�
			docObj.select();
			var imgSrc = document.selection.createRange().text;
			//�������ó�ʼ��С
			imgObjPreview.style.display='block';
			imgObjPreview.style.width = "150px";
			imgObjPreview.style.height = "120px";
			imgObjPreview.style.marginTop = '5px'; 
			imgObjPreview.style.border = '1px solid #000'; 
			//ͼƬ�쳣�Ĳ�׽����ֹ�û��޸ĺ�׺��α��ͼƬ
			try{
				imgObjPreview.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
				imgObjPreview.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
			}
			catch(e)
			{
				alert("���ϴ���ͼƬ��ʽ����ȷ��������ѡ��!");
				imgObjPreview.style.display='none';
				document.selection.empty();
				return false;
			}
			return true;
		}
}
//Ԥ��
function setImagePreview(_id1,_id2) {
	var docObj=null;
	var imgObjPreviewTd=null;
	docObj=document.getElementById(_id1);
	imgObjPreviewTd=document.getElementById(_id2);
	imgObjPreviewTd.innerHTML="";
	var imgObjPreview=document.createElement("img");
	imgObjPreviewTd.appendChild(imgObjPreview);
	//���ݻ�������
	if(docObj.files && docObj.files[0]){
			//alert("window.URL="+typeof(window.URL));
			if(window.URL!=""&&typeof(window.URL)!="undefined"){
				//����£�ֱ����img����
				imgObjPreview.style.display='block';
				imgObjPreview.style.width = '150px';
				imgObjPreview.style.height = '120px'; 
				imgObjPreview.style.marginTop = '5px'; 
				imgObjPreview.style.border = '1px solid #000'; 
				//imgObjPreview.src = docObj.files[0].getAsDataURL();
				//���7���ϰ汾�����������getAsDataURL()��ʽ��ȡ����Ҫһ�·�ʽ
				imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);	
			}else if(window.webkitURL!=""&&typeof(window.webkitURL)!="undefined"){
				//alert("window.webkitURL="+window.webkitURL);
				//����360�����
				imgObjPreview.style.display='block';
				imgObjPreview.style.width = '150px';
				imgObjPreview.style.height = '120px'; 
				imgObjPreview.style.marginTop = '5px'; 
				imgObjPreview.style.border = '1px solid #000'; 
				imgObjPreview.src=window.webkitURL.createObjectURL(docObj.files[0]);
			}
		
		}else{
			//alert("ie");
			//IE�£�ʹ���˾�
			docObj.select();
			var imgSrc = document.selection.createRange().text;
			//�������ó�ʼ��С
			imgObjPreview.style.display='block';
			imgObjPreview.style.width = "150px";
			imgObjPreview.style.height = "120px";
			imgObjPreview.style.marginTop = '5px'; 
			imgObjPreview.style.border = '1px solid #000'; 
			//ͼƬ�쳣�Ĳ�׽����ֹ�û��޸ĺ�׺��α��ͼƬ
			try{
				imgObjPreview.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
				imgObjPreview.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
			}
			catch(e)
			{
				alert("���ϴ���ͼƬ��ʽ����ȷ��������ѡ��!");
				imgObjPreview.style.display='none';
				document.selection.empty();
				return false;
			}
			return true;
		}
}