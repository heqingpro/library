//Ԥ���ڵ��µ�ͼƬ
function previewImageOfNode(url){
/*	$('#previewImageDiag').dialog({    
	    closed: false,  
	    cache: false,    
	    modal: false   
	}); 
	$("#previewImage").attr("src","");
	$("#previewImage").attr("src",url);*/
	//window.parent.addTab("Ԥ���ļ�",url+"?random="+Math.random(),"icon icon-search");
	url+="?ran="+Math.random();
	window.open(url, "_blank", "scrollbars=yes,resizable=1,modal=false,alwaysRaised=yes,width=1280,height=720,top=50,left=120");
}
//Ԥ���ڵ��µ��ı�
function previewTxtOfNode(fileName){
	var nodeId=currentNode.id;
	/*$('#previewTxtDiag').dialog({    
	    closed: false,  
	    cache: false,    
	    modal: false   
	}); 
	$("#content").val("");
	$.get("getContentByFileName.action?nodeId="+nodeId+"&fileName="+fileName,function(json){
		var content=json.content;
		$("#content").val(content);
	});*/
	$.get("previewNode.action?",{"nodeId":nodeId,"fileName":fileName},function(data){
 		var url=data.url;
		//window.parent.addTab("Ԥ���ļ�",url,'icon icon-search');
 		msgShow('ϵͳ��ʾ', "��Ԥ������֮�������ʹ�ü��̵�CTRL+F5ˢ��", 'warning',function(){window.open(url+"?ran="+Math.random(), "_blank", "scrollbars=yes,resizable=1,modal=false,alwaysRaised=yes,width=1280,height=720,top=50,left=120");});
 		//window.open(url+"?ran="+Math.random(), "_blank", "scrollbars=yes,resizable=1,modal=false,alwaysRaised=yes,width=1280,height=720,top=50,left=120");
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
function openReplaceImageDiag(filePath,imageUrl,width,height,size){
	var nodeId=currentNode.id;
	$('#replaceImageDiag').dialog({  
	    closed: false,  
	    cache: false,  
	    modal: true  
	}); 
	$('#replaceForm').form('clear');
	$("#nodeId_replace").val(nodeId);
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
	      url:'replaceImageInWebPage.action',   
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
		    	 	msgShow('ϵͳ��ʾ', msg, 'warning',function(){});
		     }else if(resultCode=="height_error"){
		    	 	msgShow('ϵͳ��ʾ', msg, 'warning',function(){});
		     }else if(resultCode=="fileSize_error"){
		    	 	msgShow('ϵͳ��ʾ', msg, 'warning',function(){});
		     }else{
	    		msgShow('ϵͳ��ʾ', "�滻ʧ�ܣ�", 'warning');	  
	    	 }
	    }   
	 }); 
}
//���޸��ı��Ի���
function openEditTxtDiag(fileName){
	var nodeId=currentNode.id;
	$('#editTxtDiag').dialog({  
	    closed: false,  
	    cache: false,  
	    modal: true  
	}); 
	$('#editTxtForm').form('clear');
	$("#nodeId_edit").val(nodeId);
	$("#fileName_edit").val(fileName);
	$("#content_edit").val("");
	$.get("getContentByFileName.action?nodeId="+nodeId+"&fileName="+fileName,function(json){
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
	      url:'editTxtInWebPage.action',   
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
//���޸Ľڵ�״̬�Ի���
function openSwitchStatusDiag(){
	 $('#switchStatusForm').form('clear');
	 $("#nodeId_switch").val(currentNode.id);
	 $('#switchStatusDiag').dialog({  
		    closed: false,  
		    cache: false,  
		    modal: true  
		}); 
	 $("input:radio[name='isOnline'][value='"+currentNode.isOnline+"']").attr("checked","true");
}
//�ر��޸Ľڵ�״̬
function closeSwitchNodeStatusDiag(){
	 $('#switchStatusDiag').dialog('close'); 
}
//�޸Ľڵ�״̬
function switchNodeStatus(){
	var nodeId=currentNode.id;
	var status= $("input:radio[name='isOnline'][checked='checked']").val();
	var messsage="";
	if(status==0){
		messsage="���߸÷��࣬�÷��ཫ�����ڵ�������ʾ���Ƿ����?";
	}else{
		messsage="���߸÷��࣬�÷��ཫ���ڵ�������ʾ���Ƿ����?";
	}
	$.messager.confirm("��ʾ",messsage,function(r){
		if(r){
		var id=currentNode.id;
		$.post("switchNodeStatus.action",{'nodeId':nodeId,"status":status},function(json){
	    	 var resultCode=json.resultCode;
	    	 var msg=json.msg;
			if(resultCode=="success"){
				msgShow('ϵͳ��ʾ', msg, 'warning');
				reloadTree();
				closeSwitchNodeStatusDiag();
			}else{
				 msgShow('ϵͳ��ʾ', msg, 'warning');
			}
		});
		}
	});
}


/**
 * ���з���
 * 
 */
var setting_paste = {
		data: {
			simpleData: {
				enable: true,
				//idKey: "id",
				//pIdKey: "pId",
				rootPId: 0//������rootId�����������pId��Ϊnull
			}

		},
		view: {
			fontCss : setFontCss
		},
		callback: {
			onClick: nodeTreeOnClick_paste
		}
	};

	var settingApp_paste = {
			data: {
				simpleData: {
					enable: true
				}
			},
			view: {
				//fontCss : setFontCssArea
			},
			callback: {
				onClick: appTreeOnClick_paste
			}
		};
	function setFontCss(treeId, treeNode) {
		/* 	if(treeNode.isParent==true){
				return {color:"grey"};
			}else{ */	
			var styleObj = {
				"fontWeight":"",//�����С
				"color":"",//������ɫ
				"backgroundColor":"",//������ɫ
				'textDecoration': ''//�»��߻���ɾ����
			}
			
			if(treeNode.isParent==true||treeNode.isHome==1){
				styleObj.fontWeight="bold";
			}
			if(treeNode.hasWebPage==0){//û��ҳ��Ľڵ�
				styleObj.color="red";
			}
			if(treeNode.isManaged=="notManaged"&&treeNode.isParent==false){//�������Ҷ�ӽڵ��ǻ�ɫ
				styleObj.color="grey";
			}
			if(treeNode.isOnline=="0"){//���ߵĽڵ㱻�»��߻���
				styleObj.textDecoration="line-through";
			}
			if(treeNode.nodeType=="dynamic"){//��̬�ڵ�
				styleObj.backgroundColor="#FFCC00";
			}
			return {"font-weight":styleObj.fontWeight,"color":styleObj.color,"background-color":styleObj.backgroundColor,"text-decoration":styleObj.textDecoration};
		};
	//�����ṩ��������¼�
	function appTreeOnClick_paste(event, treeId, treeNode) {
		var appId=treeNode.id;
		editType=treeNode.editType;
		$("#appName_paste").val(treeNode.name);
		$("#appId_paste").val(treeNode.id);
		showNodeTree_paste(appId);
	};
	
	function nodeTreeOnClick_paste(event, treeId, treeNode){
		currentNode_target=treeNode;
		var nodeArray=[];
		var nodePath="";
		nodeArray=getParentNodeName(currentNode_target,nodeArray);
		while(nodeArray.length>0){
			nodePath+=nodeArray.pop().name+">";
		}
		nodePath=nodePath.substr(0,nodePath.length-1);
		$("#nodeName_paste").html(nodePath);
	}
	//��ø��ڵ�
	function getParentNodeName(currentNode_target,nodeArray){
		nodeArray.push(currentNode_target);
		var parentNode=currentNode_target.getParentNode();
		if(parentNode!=null){
			getParentNodeName(parentNode,nodeArray);
		}
		return nodeArray;
	}
	//�л������ṩ��ʱ�����·���������
	function showNodeTree_paste(appId){
		if(appId==0){//û��ѡ�����
			showTip_paste();
		}else{//ѡ��ĳ������
			$.ajax({
				url: "getNodeTree.action?appId="+appId, 
				//async:true,
				success: function(result){
					checkSession(result);
					zNodes=result;
					$.fn.zTree.init($("#treeDemo_paste"), setting_paste, zNodes);
					zTree = $.fn.zTree.getZTreeObj("treeDemo_paste");
					zTree.expandAll(true);
		      	}
			});
		}
	};	
	//ģ�����������ṩ��
	function searchAppByNameFuzzy_paste(value){
		if(value!=null&&value!=""){
			var nodes = appTreeHide.getNodesByParamFuzzy("enName", value, null);
			appTree=$.fn.zTree.init($("#appTree_paste"), settingApp_paste, nodes);
		}else{
			refreshAppTree_paste();
		}
	}
	//���¼��������ṩ����
	function refreshAppTree_paste(){
		appTree=$.fn.zTree.init($("#appTree_paste"), settingApp_paste,appTreeHide.getNodes());
	}
//�򿪼��з���Ի���
function openCutNodeDiag(){
	if(currentNode.nodeType=="dynamic"){
		 msgShow("ϵͳ��ʾ", "<span style='color:red'>��̬���಻������У���ͨ�������丸���������ж�̬���࣡<span>", 'warning');
		 return false;
	}
	$('#cutNodeDiag').dialog({    
	    title: '���з���',    
	    width: 800,    
	    height: 500,    
	    closed: false,    
	    cache: false,    
	    modal: true   
	}); 
	currentNode_target=null;
	$("#nodeName_paste").html("");
	initAppTree_paste();//��ʼ�������ṩ����
	showNodeTree_paste(0);//��ʼ��������
}
function initAppTree_paste(){
	$("#appName_paste").val("");
	$("#appId_paste").val("");
	$.ajax({
		url: "getAppsByCurrentUser.action", 
		success: function(result){
			checkSession(result);
			var zNodes=result;
			if(zNodes.length>0){
				$("#appTree_paste").css("display","block");
				$("#appNoneTip_paste").html("");
				appTree=$.fn.zTree.init($("#appTree_paste"), settingApp_paste, zNodes);
				appTree.expandAll(true);
				appTreeHide=$.fn.zTree.init($("#appTreeHide_paste"), settingApp_paste, zNodes);
			}else{
				$("#appNoneTip_paste").html("<font color='red'><b>���������ṩ��</b></font>");
				$("#appTree_paste").css("display","none");
			}
      	}
	});
	showTip_paste();
}
function showTip_paste(){
	 zNodes=null;
	 $.fn.zTree.init($("#treeDemo_paste"), setting_paste, zNodes);
	 var tip="<p><font color='red' font-size='24px'>���޷���ṹ</font></p>";
	 $("#treeDemo_paste").html(tip);
}
//�رռ��з���Ի���
function closeCutNodeDiag(){
	$('#cutNodeDiag').dialog('close'); 
}
//���з���
function pasteNode(){
	if(currentNode_target==null){
		 msgShow('ϵͳ��ʾ', '��ѡ��һ��Ŀ����࣡', 'warning');
		 return false;
	}
	if(currentNode_target.isManaged=="notManaged"&&currentNode_target.isParent==false&&currentNode_target.pId!=0){
		 msgShow('ϵͳ��ʾ', '��������з��ൽ����������', 'warning');
		 return false;
	}
	if(currentNode_target.isOnline=="0"){
		 msgShow('ϵͳ��ʾ', '��������з��ൽ���߷����', 'warning');
		 return false;
	}
	if(currentNode_target.clickable=="0"&&currentNode_target.pId!=0){
		msgShow('ϵͳ��ʾ', '��������з��ൽ���ɵ�������', 'warning');
		return false;
	}
	var nodeId=currentNode.id;
	var targetNodeId=currentNode_target.id;
	$.post("cutNode.action",{'nodeId':nodeId,'targetNodeId':targetNodeId},function(json){
		checkSession(json); 
		var resultCode=json.resultCode;
   	    var msg=json.msg;
		if(resultCode=="success"){
			 msgShow('ϵͳ��ʾ', "���гɹ���", 'warning',function(){closeCutNodeDiag();reloadTree()});
		}else{
			 msgShow('ϵͳ��ʾ', msg, 'warning');
		}
	});
}
//ʹ���������� �Ӹ�����ʾ���ܣ���Ҫ2��  
//1.��tree��setting ��view ����������� fontCss: getFontCss ����  
//2.��ztree�����Ϸ������һ���ı��򣬲����onkeyup�¼������¼����ù̶�����  changeColor(id,key,value��  
//  idָztree������id��һ��Ϊul��key��ָ��ztree�ڵ�����ݵ��ĸ�����Ϊ����������,value��ָ�����������ù���Ϊģ������  
var nodeList=new Array();
var treeId = 0;
function changeColor(id,key,value){  
    treeId = id;  
    updateNodes(false);
    if(value != ""){  
        var treeObj = $.fn.zTree.getZTreeObj(treeId);  
        nodeList = treeObj.getNodesByParamFuzzy(key, value);  
        if(nodeList && nodeList.length>0){  
            updateNodes(true);  
        }  
    }  
}  
function updateNodes(highlight) {  
    var treeObj = $.fn.zTree.getZTreeObj(treeId);  
    for( var i=0; i<nodeList.length;  i++) {  
        nodeList[i].highlight = highlight;  
        treeObj.updateNode(nodeList[i]);  
    }  
}  
