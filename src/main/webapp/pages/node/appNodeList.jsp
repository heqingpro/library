<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/source/themes/default/easyui.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/source/css/demo.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/source/functionJs/node.js"></script> --%>

<!-- 分类管理 -->
<style type="text/css">
	li{
	list-style: none;
	text-align:left;
	}
	#appTreeOnNodePage{
	margin-left:10px;
	width:300px;
	height:600px;
	overflow-y:auto
	}
	#nodeTree{
	margin-left:10px;
	width:300px;
	height:600px;
	overflow-y:auto
	}
</style>
<script type="text/javascript">
	var nodeDatagrid=$('#nodeDatagrid');
	var appTreeOnNodePage;//内容提供商树
	var appTreeHideOnNodePage;//隐藏的内容提供商树
	var nodeTree;//分类树
	var zNodes =null;
	var zApps=null;
	var currentNode=null;//被选中的树分类对象
	
	var settingApp = {
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: appTreeOnClick,
			onRightClick: appTreeOnRightClick
		},
		view: {
			showIcon: false,
			fontCss : {color:"blue",fontWeight:"bold"}
		}
	};
	
	var setting = {
		    edit: {
					enable: true,
					showRemoveBtn: false,
					showRenameBtn: false,
					drag:{
						isCopy:false//拖动节点时不能复制节点
					}
				},
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "pId",
					rootPId: 0 //不设置rootId，则树分类的pId则为null
				}
	
			},
			view: {
				dblClickExpand: false,
				fontCss : setFontCss
			},
			callback: {
				onClick: nodeTreeOnClick,
				onRightClick: nodeTreeOnRightClick,
				beforeDrag: beforeDrag,
				beforeDrop: beforeDrop
			}
		};
		
	$(function(){
		loadAppTree();//初始化内容提供商树
		showNodeTree(0);//初始化分类树	
		//加载右键菜单
		initMM();
		
	});
	//加载内容提供商树
	function loadAppTree(){
		$.ajax({
			url: '${pageContext.request.contextPath}/appController/queryAppList', 
			success: function(result){
			 	zApps=result.rows;
			 	if(zApps.length>0){
					$("#appTreeOnNodePage").css("display","block");
					$("#appNoneTip").html("");
					appTreeOnNodePage=$.fn.zTree.init($("#appTreeOnNodePage"), settingApp, zApps);
					appTreeOnNodePage.expandAll(true);
					appTreeHideOnNodePage=$.fn.zTree.init($("#appTreeHideOnNodePage"), settingApp, zApps);
				}else{
					$("#appNoneTip").html("<font color='red'><b>暂无内容提供商</b></font>");
					$("#appTreeOnNodePage").css("display","none");
				}
	      	}
		});
	}
	function setFontCss(treeId, treeNode) {
		var styleObj = {
			"fontWeight":"",//字体大小
			"color":"",//字体颜色
			"backgroundColor":"",//背景颜色
			'textDecoration': ''//下划线或者删除线
		}
		if(treeNode.pId==0){
			styleObj.fontWeight="bold";
		}
		
		if(treeNode.isParent==false){//不管理的叶子节点是灰色
			styleObj.color="grey";
		}
		if(treeNode.isOnline==0){//下线的节点被下划线划掉
			styleObj.textDecoration="line-through";
		}else{
			styleObj.color="blue";
		}
		/* if(treeNode.nodeType!=null&&treeNode.nodeType!=0){//推荐位
			styleObj.backgroundColor="#FFCC00";
		} */
		if(treeNode.highlight==true){
			styleObj.backgroundColor="#A60000";
			styleObj.fontWeight="bold";
		}
		return {"font-weight":styleObj.fontWeight,"color":styleObj.color,"background-color":styleObj.backgroundColor,"text-decoration":styleObj.textDecoration};
	}
	//模糊查找内容提供商
	function searchAppByNameFuzzy(){
		var value = $("#appName").val();
		if(value!=null&&value!=""){
			var nodes = appTreeHideOnNodePage.getNodesByParamFuzzy("name", value, null);
			appTreeOnNodePage=$.fn.zTree.init($("#appTreeOnNodePage"), settingApp, nodes);
		}else{
			refreshAppTree();
		}
	}
	//内容提供商树左键点击事件
	function appTreeOnClick(event, treeId, treeNode) {
		var appId=treeNode.id;
		$("#appIdOnNodePage").val(treeNode.id);
		showNodeTree(appId);
	};
	
	//内容提供商树右键点击事件
	function appTreeOnRightClick(event, treeId, treeNode){
		event.preventDefault();//屏蔽系统右键事件
		if(treeNode!=null){//点击树分类
			//将当前点击的分类保存到全局变量
			currentNode=treeNode;
		
			console.log(currentNode.id);
			console.log(currentNode.name);
			$('#mm').menu('enableItem', $("#m-new"));
			$('#mm').menu('disableItem', $("#m-delete"));
			$('#mm').menu('disableItem', $("#m-edit"));
			$('#mm').menu('disableItem', $("#switchStatus"));
			
			$('#mm').menu('show', {
				  left: event.pageX,    
				  top: event.pageY
			}); 
		}
	}	
	
	//重新加载内容提供商树
	function refreshAppTree(){
		appTreeOnNodePage=$.fn.zTree.init($("#appTreeOnNodePage"), settingApp, appTreeHideOnNodePage.getNodes());
	}
	//切换内容提供商时，更新分类树内容
	function showNodeTree(appId){
		APPID = appId;
		if(appId==0){//没有选择内容提供商
			 showTip();
		}else{//选择某个分类
			$.ajax({
				url: '${pageContext.request.contextPath}/nodeController/queryNodeTree?appId='+appId, 
				//async:true,
				success: function(result){
					$("#nodeTree").css("display","block");
					$("#nodeTree").html("");
					$("#totalNodeCount").html(result.total);				
					zNodes=result.rows;
					nodeTree = $.fn.zTree.init($("#nodeTree"), setting, zNodes);
					nodeTree.expandAll(true);
			  	}
			});
		}
	};
	function showTip(){
		 $.fn.zTree.init($("#nodeTree"), setting, null);
		 var tip="<p><font color='red' font-size='24px'>暂无分类结构</font></p>";
		 $("#nodeTree").html(tip);
	}
	//分类树拖动拖动节点前
	function beforeDrag(treeId, treeNodes) {
		for (var i=0,l=treeNodes.length; i<l; i++) {
			if (treeNodes[i].drag === false) {
				return false;
			}
		}
		return true;
	}
	//分类树拖动节点释放鼠标前
	function beforeDrop(treeId, treeNodes, targetNode, moveType) {
		if(treeNodes.length>1){
			msgShow('系统提示', '一次只能拖动一个分类！', 'warning');
			return false;
		}
		var moveTypeMessage="";
		switch(moveType){
		case 'prev':
			moveTypeMessage="前面";
			break;
		case 'next':
			moveTypeMessage="后面";
			break;
		case 'inner':
			moveTypeMessage="里面";
			break;
		}
		var message="确认要把分类『"+treeNodes[0].name+"』拖到分类『"+targetNode.name+"』"+moveTypeMessage+"?";
		if(targetNode.pId==0||targetNode.pId==null&&moveType!="inner"){
			msgShow('系统提示', '不能拖动到根分类前面或者后面！', 'warning');
			return false;
		}
	 	 $.messager.confirm("提示",message,function(r){
				if(r){
					//return targetNode ? targetNode.drop !== false : true;
					var nodeIds="";//被拖动节点
					var targetNodeId=targetNode.id;//目标节点
					for(var i=0;i<treeNodes.length;i++){
						nodeIds+=treeNodes[i].id;
					}
					$.ajax({
						url:'${pageContext.request.contextPath}/nodeController/moveNode?nodeId='+nodeIds+"&targetNodeId="+targetNodeId+"&moveType="+moveType,
						success: function(result){
							reloadTree();
						}
					});
					 return true;
				}else{
					 reloadTree();
					 return false;
				}
			}); 
	 	
		return true; 
	}
	//分类树左键单击鼠标事件
	function nodeTreeOnClick(event, treeId, treeNode){
		//将当前点击的分类保存到全局变量
		currentNode=treeNode;
		var nodeTree = $.fn.zTree.getZTreeObj(treeId);
		nodeTree.expandNode(treeNode);
	}
	//分类树右键菜单
	function nodeTreeOnRightClick(event, treeId, treeNode){
		event.preventDefault();//屏蔽系统右键事件
		if(treeNode!=null){//点击树分类
			//将当前点击的分类保存到全局变量
			currentNode=treeNode;
			//上、下线处理,下线分类不可以添加子分类，修改，进入素材列表功能，只能删除或重新上线
			/* if(treeNode.pId==0||treeNode.pId==null&&(treeNode.isParent==true)){//根分类不能被删除,能进行导入导出操作
				$('#mm').menu('enableItem', $("#m-new"));
				$('#mm').menu('enableItem', $("#switchStatus"));
				$('#mm').menu('disableItem', $("#m-delete"));
				$('#mm').menu('disableItem', $("#m-edit"));
			}else if(treeNode.isParent==false&&treeNode.isManaged=="notManaged"){//灰色节点能被删除，不能进行其他操作
				$('#mm').menu('enableItem', $("#m-new"));
				$('#mm').menu('enableItem', $("#switchStatus"));
				$('#mm').menu('enableItem', $("#m-delete"));
				$('#mm').menu('enableItem', $("#m-edit"));
			}else if(treeNode.pId!=0&&treeNode.isParent==true){//非叶子分类且非根分类，不能被删除，不能导入节点
				$('#mm').menu('enableItem', $("#m-new"));
				$('#mm').menu('enableItem', $("#switchStatus"));
				$('#mm').menu('disableItem', $("#m-delete"));
				$('#mm').menu('enableItem', $("#m-edit"));
			}else{//叶子分类可以被删除
				$('#mm').menu('enableItem', $("#m-new"));
				$('#mm').menu('enableItem', $("#switchStatus"));
				$('#mm').menu('enableItem', $("#m-delete"));
				$('#mm').menu('enableItem', $("#m-edit"));
			} */
			
			$('#mm').menu('enableItem', $("#m-new"));
			$('#mm').menu('enableItem', $("#switchStatus"));
			$('#mm').menu('enableItem', $("#m-delete"));
			$('#mm').menu('enableItem', $("#m-edit"));
			
			if(treeNode.isOnline==0){
				$('#mm').menu('disableItem', $("#m-new"));
			}
			
			$('#mm').menu('show', { 
				  left: event.pageX,    
				  top: event.pageY
			}); 
		}
	}	
	//加载分类右键菜单
	function initMM(){		
		$('#mm').menu({    
			 onClick:function(item){
				 	 if(item.name=="new"){//添加分类
						 nodeAdd();
					 }else if(item.name=="edit"){//修改分类
						 nodeEdit();
					 }else if(item.name=="delete"){//删除分类
						 nodeDelete();
					 }else if(item.name=="import"){//导入分类树
						 openImportNodeTreeDiag();
					 }else if(item.name=="export"){//导出分类树
						 exportNodeTree();
					 }else if(item.name=="switchStatus"){//节点上下线
						 nodeSwitchStatus();
					 }else if(item.name="entryList"){
						 entryListForward();
					 }
					 
			}
		}); 
	} 
	//跳转到分类的素材管理页面
	function entryListForward(){
		//跳转到素材管理页面
		addTab("素材列表",'${pageContext.request.contextPath}/pages/entry/entryList.jsp?nodeId='+currentNode.id+"&nodeTypeName="+currentNode.nodeTypeName);
	}
	
	//分类新增
	function nodeAdd(){
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/pages/node/nodeEdit.jsp',
			width : 600,
			height : 400,
			modal : true,
			resizable:true,
			title : '新增分类',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var result = $('#node_form').form('validate');
					if(!result) {
						return false;
					}
					if(!enNameFlag)
						return false;
					if(!urlFlag)
						return false;
					var d=$(this).closest('.window-body');			
					$('#node_form').form('submit',{
						 url:'${pageContext.request.contextPath}/nodeController/addNode',
						 success:function(result){
							 var r = JSON.parse(result);
							 if (r.success) {
									d.dialog('destroy');
									$.messager.alert('提示', '添加成功');
									reloadTree();
							 }else{
									if(r.msg!=undefined){
										$.messager.alert('提示', r.msg);
									}else{
										toLogin();
									}										
								}
					    }   
					});
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			},
			onLoad : function() {
				$("#pId").val(currentNode.id);
				$("#pNameTr").css("display","table-row");
				$("#pName").val(currentNode.name);
				$("#nodeImage").val("");
			}
		});
	}
	//修改分类
	function nodeEdit(){
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/pages/node/nodeEdit.jsp',
			width : 600,
			height : 400,
			modal : true,
			resizable:true,
			title : '修改分类',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var result = $('#node_form').form('validate');
					if(!result) {
						return false;
					}
					if(!enNameFlag)
						return false;
					if(!urlFlag)
						return false;
					var d=$(this).closest('.window-body');			
					$('#node_form').form('submit',{
						 url:'${pageContext.request.contextPath}/nodeController/editNode',
						 dataType:'json',  //服务器返回的格式,可以是json
						 success:function(result){
							 var r = JSON.parse(result);
							 if (r.success) {
									d.dialog('destroy');
									$.messager.alert('提示', '修改成功');
									reloadTree();
							 }else{
									if(r.msg!=undefined){
										$.messager.alert('提示', r.msg);
									}else{
										toLogin();
									}										
								}
					    }   
					});
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			},
			onLoad : function() {
				//$('#node_form').form('load',currentNode);
				$("#id").val(currentNode.id);
				$("#pNameTr").css("display","none");
				$("#name").val(currentNode.name);
				$("#enName").val(currentNode.enName);
				/* if(currentNode.nodeType!=-1){
					$("#nodeType").combobox('setValue', currentNode.nodeType);
				} */
				var posterUrl = currentNode.posterUrl+"?t="+Math.random();
				var thumbnailUrl = currentNode.thumbnailUrl+"?t="+Math.random();
				if(currentNode.posterUrl!=null&&currentNode.posterUrl!=""){
					$("#previewImage").append("<img src='"+posterUrl+"' style='margin-top:5px;width: 150px;height: 120px;border:1px solid black'></img>");	
				}
				if(currentNode.thumbnailUrl!=null&&currentNode.thumbnailUrl!=""){
					$("#previewNodeThumbnail").append("<img src='"+thumbnailUrl+"' style='margin-top:5px;width: 150px;height: 120px;border:1px solid black'></img>");	
				}
				$("#linkUrl").val(currentNode.linkUrl);				
			}
		});
	}
	//分类上下线
	function nodeSwitchStatus(){
		var targetStatus="上线";
		var targetMsg="确定上线此分类吗？";
		if(currentNode.isOnline==1){
			targetStatus = "下线";
			targetMsg="注意：下线该分类分类将导致其子分类也不可用，您确定要下线吗？";
		}
		$.messager.confirm('确认', targetMsg, function(r) {
			if (r) {
				$.ajax({
					url : '${pageContext.request.contextPath}/nodeController/updateNodeStatus',
					data : {
						id : currentNode.id
					},
					dataType : 'json',
					success : function(result) {
						if (result.success) {
							$.messager.alert('提示', targetStatus+'成功','info');
							reloadTree();
						}else{
							if(result.msg!=undefined){
								$.messager.alert('提示', targetStatus+'失败','error');
							}else{
								toLogin();
							}
						}
					}
				});
			}
		});
	}
	//删除分类节点
	function nodeDelete(){
			$.messager.confirm('确认', '删除该分类，则该分类下的素材也会被删除，是否要删除当前分类？', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/nodeController/deleteNode',
						data : {
							id : currentNode.id
						},
						dataType : 'json',
						success : function(result) {
							if (result.success) {
								$.messager.alert('提示', '删除成功','info');
								reloadTree();
							}else{
								if(result.msg!=undefined){
									$.messager.alert('提示', result.msg,'error');
								}else{
									toLogin();
								}
							}
						}
					});
				}
			});
	}
	//加载分类树
	function reloadTree(){
		var appId = $("#appIdOnNodePage").val();
		showNodeTree(appId);
	}
	
</script>
<!-- 分类树显示 -->
<div>
    <div data-options="region:'west',title:'选择专区',split:true" style="width:25%;">
    	<div style="position:absolute;top:30px;height:500px;margin-top: 10px;margin-left: 10px;">
	  	 <span style="font-weight: bold">选择内容提供商(当前分类总个数为：<span id="totalNodeCount" style="color:red">0</span>)：</span>
		 <!-- <input type="text"  id="appName"/>onkeyup="searchAppByNameFuzzy(this.value)"
		 <img onclick="searchAppByNameFuzzy();" src="source/images/search.gif" title="搜索"/>&nbsp;&nbsp;
		 <div id="appNoneTip" style="margin:10px"></div> -->
		 <input type="text" id="appIdOnNodePage" style="display:none"/>
		 <ul id="appTreeOnNodePage" class="ztree" style="height:500px;margin-top: 0px;margin-left: 68px;border:0px;display:none;padding-left: 70px;"></ul>
		 <ul id="appTreeHideOnNodePage" class="ztree" style="margin-top: 0px;margin-left: 68px;border:0px;display:none;"></ul>
		</div> 
	</div>
    <div data-options="title:'分类树'" style="padding:5px; position: relative; width:50%; left: 25%;">
	 	<div style="font-weight: bold; width:100%;">
		 	<span style="padding-left: 100px;">搜索分类<b>(模糊搜索)</b>：<input type="text" id="dicKey" onkeyup="changeColor('nodeTree','name',this.value)"/></span>
		 	<span style="padding-left: 100px;">注意：<span style="color: blue;">蓝色字体</span>表示[已上线分类]， <span style="color: grey;text-decoration: line-through; ">灰色划线字体</span>表示[已下线分类]</span>
	 	</div>
   		 
	     <div style="float:left; padding-left: 140px;">
			<ul id="nodeTree" class="ztree" style="height:500px;margin-top: 0px;margin-left: 68px;border:0px;display:none;"></ul>
		 </div>
	</div>
	
</div>

<!-- 分类右键菜单功能 -->
<div id="mm" class="easyui-menu" style="width:120px;">  
	<sec:authorize url="/nodeManage/addNode">
    <div data-options="iconCls:'icon-add',name:'new'" id="m-new">添加分类</div>   
      <div class="menu-sep"></div>  
     </sec:authorize> 
    <sec:authorize url="/nodeManage/editNode">
    <div data-options="iconCls:'icon-edit',name:'edit'" id="m-edit">修改分类</div>   
      <div class="menu-sep"></div>   
    </sec:authorize> 
    <sec:authorize url="/nodeManage/delNode">
    <div data-options="iconCls:'icon-remove',name:'delete'"  id="m-delete">删除分类</div>   
      <div class="menu-sep"></div> 
    </sec:authorize>
    <%-- <div>  
        <span>导入/导出</span>   
        <div style="width:150px;" id="m-imex-xml">   
          	<sec:authorize url="/nodeManage/importNodeTree">  
            <div data-options="iconCls:'icon-undo',name:'import'" id="m-im-xml"><b>导入分类文件</b></div>  
            </sec:authorize>   
            <sec:authorize url="/nodeManage/exportNodeTree"> 
            <div data-options="iconCls:'icon-redo',name:'export'" id="m-ex-xml">导出分类文件</div>  
            </sec:authorize>   
        </div>   
    </div>  --%>
    <div class="menu-sep"></div>   
    <sec:authorize url="/nodeManage/switchStatus">
    <div data-options="iconCls:'icon-tick',name:'switchStatus'"  id="switchStatus">上线/下线</div>   
    </sec:authorize> 
    <%-- <sec:authorize url="/nodeManage/entryList">
   	  <div data-options="iconCls:'icon-add',name:'entryList'" id="m-entryList" style="display: none;">图书列表</div>   
      <div class="menu-sep"></div>  
    </sec:authorize> --%>  
</div>  

