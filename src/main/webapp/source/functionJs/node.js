//预览节点下的图片
function previewImageOfNode(url){
/*	$('#previewImageDiag').dialog({    
	    closed: false,  
	    cache: false,    
	    modal: false   
	}); 
	$("#previewImage").attr("src","");
	$("#previewImage").attr("src",url);*/
	//window.parent.addTab("预览文件",url+"?random="+Math.random(),"icon icon-search");
	url+="?ran="+Math.random();
	window.open(url, "_blank", "scrollbars=yes,resizable=1,modal=false,alwaysRaised=yes,width=1280,height=720,top=50,left=120");
}
//预览节点下的文本
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
		//window.parent.addTab("预览文件",url,'icon icon-search');
 		msgShow('系统提示', "打开预览窗口之后请务必使用键盘的CTRL+F5刷新", 'warning',function(){window.open(url+"?ran="+Math.random(), "_blank", "scrollbars=yes,resizable=1,modal=false,alwaysRaised=yes,width=1280,height=720,top=50,left=120");});
 		//window.open(url+"?ran="+Math.random(), "_blank", "scrollbars=yes,resizable=1,modal=false,alwaysRaised=yes,width=1280,height=720,top=50,left=120");
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
	$('#replaceImageDiagTip').text("图片分辨率为"+width+"×"+height);
}
function closeReplaceImageDiag(){
	$('#replaceImageDiag').dialog('close'); 
}
//替换图片
function replaceImage(){
	$('#replaceForm').form('submit',{
		  method:'POST',
	      url:'replaceImageInWebPage.action',   
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
		    	 	msgShow('系统提示', msg, 'warning',function(){});
		     }else if(resultCode=="height_error"){
		    	 	msgShow('系统提示', msg, 'warning',function(){});
		     }else if(resultCode=="fileSize_error"){
		    	 	msgShow('系统提示', msg, 'warning',function(){});
		     }else{
	    		msgShow('系统提示', "替换失败！", 'warning');	  
	    	 }
	    }   
	 }); 
}
//打开修改文本对话框
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
//修改文本
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
//打开修改节点状态对话框
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
//关闭修改节点状态
function closeSwitchNodeStatusDiag(){
	 $('#switchStatusDiag').dialog('close'); 
}
//修改节点状态
function switchNodeStatus(){
	var nodeId=currentNode.id;
	var status= $("input:radio[name='isOnline'][checked='checked']").val();
	var messsage="";
	if(status==0){
		messsage="下线该分类，该分类将不会在电视上显示，是否继续?";
	}else{
		messsage="上线该分类，该分类将会在电视上显示，是否继续?";
	}
	$.messager.confirm("提示",messsage,function(r){
		if(r){
		var id=currentNode.id;
		$.post("switchNodeStatus.action",{'nodeId':nodeId,"status":status},function(json){
	    	 var resultCode=json.resultCode;
	    	 var msg=json.msg;
			if(resultCode=="success"){
				msgShow('系统提示', msg, 'warning');
				reloadTree();
				closeSwitchNodeStatusDiag();
			}else{
				 msgShow('系统提示', msg, 'warning');
			}
		});
		}
	});
}


/**
 * 剪切分类
 * 
 */
var setting_paste = {
		data: {
			simpleData: {
				enable: true,
				//idKey: "id",
				//pIdKey: "pId",
				rootPId: 0//不设置rootId，则树分类的pId则为null
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
				"fontWeight":"",//字体大小
				"color":"",//字体颜色
				"backgroundColor":"",//背景颜色
				'textDecoration': ''//下划线或者删除线
			}
			
			if(treeNode.isParent==true||treeNode.isHome==1){
				styleObj.fontWeight="bold";
			}
			if(treeNode.hasWebPage==0){//没有页面的节点
				styleObj.color="red";
			}
			if(treeNode.isManaged=="notManaged"&&treeNode.isParent==false){//不管理的叶子节点是灰色
				styleObj.color="grey";
			}
			if(treeNode.isOnline=="0"){//下线的节点被下划线划掉
				styleObj.textDecoration="line-through";
			}
			if(treeNode.nodeType=="dynamic"){//动态节点
				styleObj.backgroundColor="#FFCC00";
			}
			return {"font-weight":styleObj.fontWeight,"color":styleObj.color,"background-color":styleObj.backgroundColor,"text-decoration":styleObj.textDecoration};
		};
	//内容提供商树点击事件
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
	//获得父节点
	function getParentNodeName(currentNode_target,nodeArray){
		nodeArray.push(currentNode_target);
		var parentNode=currentNode_target.getParentNode();
		if(parentNode!=null){
			getParentNodeName(parentNode,nodeArray);
		}
		return nodeArray;
	}
	//切换内容提供商时，更新分类树内容
	function showNodeTree_paste(appId){
		if(appId==0){//没有选择分类
			showTip_paste();
		}else{//选择某个分类
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
	//模糊查找内容提供商
	function searchAppByNameFuzzy_paste(value){
		if(value!=null&&value!=""){
			var nodes = appTreeHide.getNodesByParamFuzzy("enName", value, null);
			appTree=$.fn.zTree.init($("#appTree_paste"), settingApp_paste, nodes);
		}else{
			refreshAppTree_paste();
		}
	}
	//重新加载内容提供商树
	function refreshAppTree_paste(){
		appTree=$.fn.zTree.init($("#appTree_paste"), settingApp_paste,appTreeHide.getNodes());
	}
//打开剪切分类对话框
function openCutNodeDiag(){
	if(currentNode.nodeType=="dynamic"){
		 msgShow("系统提示", "<span style='color:red'>动态分类不允许剪切，请通过剪切其父分类来剪切动态分类！<span>", 'warning');
		 return false;
	}
	$('#cutNodeDiag').dialog({    
	    title: '剪切分类',    
	    width: 800,    
	    height: 500,    
	    closed: false,    
	    cache: false,    
	    modal: true   
	}); 
	currentNode_target=null;
	$("#nodeName_paste").html("");
	initAppTree_paste();//初始化内容提供商树
	showNodeTree_paste(0);//初始化分类树
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
				$("#appNoneTip_paste").html("<font color='red'><b>暂无内容提供商</b></font>");
				$("#appTree_paste").css("display","none");
			}
      	}
	});
	showTip_paste();
}
function showTip_paste(){
	 zNodes=null;
	 $.fn.zTree.init($("#treeDemo_paste"), setting_paste, zNodes);
	 var tip="<p><font color='red' font-size='24px'>暂无分类结构</font></p>";
	 $("#treeDemo_paste").html(tip);
}
//关闭剪切分类对话框
function closeCutNodeDiag(){
	$('#cutNodeDiag').dialog('close'); 
}
//剪切分类
function pasteNode(){
	if(currentNode_target==null){
		 msgShow('系统提示', '请选择一个目标分类！', 'warning');
		 return false;
	}
	if(currentNode_target.isManaged=="notManaged"&&currentNode_target.isParent==false&&currentNode_target.pId!=0){
		 msgShow('系统提示', '不允许剪切分类到不管理分类里！', 'warning');
		 return false;
	}
	if(currentNode_target.isOnline=="0"){
		 msgShow('系统提示', '不允许剪切分类到下线分类里！', 'warning');
		 return false;
	}
	if(currentNode_target.clickable=="0"&&currentNode_target.pId!=0){
		msgShow('系统提示', '不允许剪切分类到不可点击分类里！', 'warning');
		return false;
	}
	var nodeId=currentNode.id;
	var targetNodeId=currentNode_target.id;
	$.post("cutNode.action",{'nodeId':nodeId,'targetNodeId':targetNodeId},function(json){
		checkSession(json); 
		var resultCode=json.resultCode;
   	    var msg=json.msg;
		if(resultCode=="success"){
			 msgShow('系统提示', "剪切成功！", 'warning',function(){closeCutNodeDiag();reloadTree()});
		}else{
			 msgShow('系统提示', msg, 'warning');
		}
	});
}
//使用搜索数据 加高亮显示功能，需要2步  
//1.在tree的setting 的view 设置里面加上 fontCss: getFontCss 设置  
//2.在ztree容器上方，添加一个文本框，并添加onkeyup事件，该事件调用固定方法  changeColor(id,key,value）  
//  id指ztree容器的id，一般为ul，key是指按ztree节点的数据的哪个属性为条件来过滤,value是指过滤条件，该过滤为模糊过滤  
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
