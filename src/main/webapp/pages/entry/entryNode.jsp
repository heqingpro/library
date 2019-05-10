<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/source/themes/default/easyui.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/source/css/demo.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/source/functionJs/node.js"></script> --%>

<style type="text/css">
	li{
	list-style: none;
	text-align:left;
	}
	#appTree{
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
	var appTree;//内容提供商树
	var appTreeHide;//隐藏的内容提供商树
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
			onClick: appTreeOnClick			
		},
		view: {
			showIcon: false,
			fontCss : {color:"blue",fontWeight:"bold"}
		}
	};
		
	$(function(){
		loadAppTree();//初始化内容提供商树
	});
	//加载供应商树
	function loadAppTree(){
		$.ajax({
			url: '${pageContext.request.contextPath}/appController/queryAppList', 
			success: function(result){
			 	zApps=result.rows;
			 	if(zApps.length>0){
					$("#appTree").css("display","block");
					$("#appNoneTip").html("");
					appTree=$.fn.zTree.init($("#appTree"), settingApp, zApps);
					appTree.expandAll(true);
					appTreeHide=$.fn.zTree.init($("#appTreeHide"), settingApp, zApps);
				}else{
					$("#appNoneTip").html("<font color='red'><b>暂无内容提供商</b></font>");
					$("#appTree").css("display","none");
				}
	      	}
		});
	}
	
	//模糊查找内容提供商
	function searchAppByNameFuzzy(){
		var value = $("#appName").val();
		if(value!=null&&value!=""){
			var nodes = appTreeHide.getNodesByParamFuzzy("name", value, null);
			appTree=$.fn.zTree.init($("#appTree"), settingApp, nodes);
		}else{
			refreshAppTree();
		}
	}
	//内容提供商树点击事件
	function appTreeOnClick(event, treeId, treeNode) {
		var appId = treeNode.id;
		var appName = treeNode.name;
		$("#appId").val(treeNode.id);
		$("#appName").val(treeNode.name);
		//跳转到分类管理页面
		appName = encodeURI(encodeURI(appName));//中文编码，解码两次
		addTab("图书列表",'${pageContext.request.contextPath}/pages/entry/entryList.jsp?appId='+appId+"&appName="+appName);//+"&nodeType="+currentNode.nodeType
	};
	//--------------------------------不用通过分类树获取图书列表，直接加载一个供应商下的图书列表----------------------------
	//重新加载内容提供商树
	function refreshAppTree(){
		appTree=$.fn.zTree.init($("#appTree"), settingApp,appTreeHide.getNodes());
	}
	
</script>
<!-- 分类树显示 -->
<div>
    <div data-options="region:'west',title:'选择专区',split:true" style="width:200px;">
    	<div style="position:absolute;top:30px;height:500px;margin-top: 10px;margin-left: 10px;">
	  	 <span style="font-weight: bold">选择内容提供商：</span>
		 <!-- <input type="text"  id="appName"/>onkeyup="searchAppByNameFuzzy(this.value)"
		 <img onclick="searchAppByNameFuzzy();" src="source/images/search.gif" title="搜索"/>&nbsp;&nbsp;
		 <div id="appNoneTip" style="margin:10px"></div> -->
		 <input type="text" id="appId" style="display:none"/>
		 <ul id="appTree" class="ztree" style="height:500px;margin-top: 0px;margin-left: 68px;border:0px;display:none;"></ul>
		 <ul id="appTreeHide" class="ztree" style="margin-top: 0px;margin-left: 68px;border:0px;display:none;"></ul>
		</div> 
	</div>
</div>
