<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<script type="text/javascript">
	var appId = "<%=request.getParameter("appId")%>"; 
	var appName = "<%=request.getParameter("appName")%>"; 
	appName = decodeURI(appName,"utf-8");
	var entryDataGrid = $('#entryDataGrid');
	$(function() {
		entryDataGrid.datagrid({
			url : '${pageContext.request.contextPath}/entryController/queryEntryList?app_Id='+appId,
			fit : false,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'id',
			sortOrder : 'asc',
			checkOnSelect : true,
			selectOnCheck : true,
			nowrap : true,
			singleSelect : false,
			toolbar: '#entryToolBar',
			columns : [ [
			{
				title : '编号',
				field : 'id',
				width : 50,
				checkbox : true
			}, {
				title : '标题',
				field : 'title',
				width : 100
			}, {
				title : '标题简拼',
				field : 'shortName',
				width : 100
				
			},{
				title : '封面',
				field : 'coverImageUrl',
				width : 50,
				formatter : function(value, row, index) {
					 var img = "<img style=\"align:center\"   src='" + value + "' width='30px' height='30px'  onclick=\"preview(\'" + value + "\')\"/>";
			         return img; 
				}
			},{
				title : '年代',
				field : 'yearsId',
				width : 100,
				formatter : function(value, row, index) {
						if(row.yearsId == 1){
				         	return "古代";
						} else if(row.yearsId == 2){
							return "近现代";
						}else if(row.yearsId == 3){
							return "现当代";
						} 
					}
			},{
				title : '分类',
				field : 'nodeNames',
				width : 150
			},
			{
				title : '语种',
				field : 'langName',
				width : 100
			}
			,{
				title : '系列',
				field : 'entryTypeName',
				width : 100
			}
			,{
				title : '创建时间',
				field : 'addTime',
				width : 100				
			},
			{
				title : '修改时间',
				field : 'modifyTime',
				width : 100				
			},
			{
				title : '排序序号',
				field : 'rankNumber',
				width : 100
			},
			 {
				field : 'action',
				title : '操作',
				width : 150,
				formatter : function(value, row, index) {
					return formatString(
							'<sec:authorize url="/entryManage/entryDetail"><a onclick=\"entryDetail(\'{0}\');\"><img src=\"{1}\" title=\"图书详情\"/>&nbsp;&nbsp;</a></sec:authorize>'+
							'<sec:authorize url="/entryManage/changeEntryRankNumber"><a onclick=\"changeEntryRankNumber(\'{2}\',\'{3}\');\"><img src=\"{4}\" title=\"上升一位\"/>&nbsp;&nbsp;</a></sec:authorize>'+
							'<sec:authorize url="/entryManage/changeEntryRankNumber"><a onclick=\"changeEntryRankNumber(\'{5}\',\'{6}\');\"><img src=\"{7}\" title=\"下降一位\"/>&nbsp;&nbsp;</a></sec:authorize>'+
							'<sec:authorize url="/entryManage/entryDelete"><a onclick=\"entryDelete(\'{8}\');\"><img src=\"{9}\" title=\"删除图书\"/></a></sec:authorize>',
							 row.id, '${pageContext.request.contextPath}/source/images/detail.png',
							 row.id, -1, '${pageContext.request.contextPath}/source/images/up.png',
							 row.id, 1, '${pageContext.request.contextPath}/source/images/down.png',
							 row.id, '${pageContext.request.contextPath}/source/images/delete.png');
				}
			}
		] ]
	})});
	
	//调整排序
	function changeEntryRankNumber(id,operation) {
		$.ajax({
			url : '${pageContext.request.contextPath}/entryController/changeEntryRankNumber',
			data : {
				"id" : id,
				"operation" : operation
			},
			dataType : 'json',
			success : function(result) {
				if (result.success) {
					$.messager.alert('提示', '操作成功！','info');
					entryDataGrid.datagrid('reload');
					entryDataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
				}else{
					if(result.msg!=undefined){
						$.messager.alert('提示', '操作失败！','error');
					}else{
						toLogin();
					}
				}
			}
		});
	}
	
	//活动列表点击预览
	function preview(imgPath){
		$("#previewImg").attr("src",""); //清空值
		if(!imgPath || imgPath == ""){
			$.messager.alert("尚未上传图片", function(){});
			return;
		}
		$("#previewImg").attr("src",imgPath);
		$('#picPathVodDg').window('open');
				
	}
	
	//图书新增
	function entryAdd(){
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/pages/entry/entryEdit.jsp',
			width : 650,
			height : 550,
			modal : true,
			resizable:true,
			title : '新增图书',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var result = $('#entry_form').form('validate');
						if(!result) {
						return false;
					}
					var entryTypeId = $("#entryTypeId").combogrid('getValue');
					if(entryTypeId==null||entryTypeId=='null'){
						$("#entryTypeId").combogrid('setValue',-1);
					}
					var langId = $("#langId").combogrid('getValue');
					if(langId==null||langId=='null'){
						$("#langId").combogrid('setValue',-1);
					}
					var d=$(this).closest('.window-body');	
					$('#entry_form').form('submit',{
						 url:'${pageContext.request.contextPath}/entryController/addEntry',
						 success:function(result){
							 var r = JSON.parse(result);
							 if (r.success) {
								 entryDataGrid.datagrid('reload');	
								 d.dialog('destroy');
								 $.messager.alert('提示', '添加成功');								
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
				$("#app_Id").val(appId); //素材列表页面的套书系列
				//加载内容提供商
				$("#appName").val(appName);
				//加载套数系列
				loadEntryType();
				//加载语种
				loadAngles();
				//加载类型
				loadNodes();								
			}
		});
	}
	
	//根据类型显示套书系列
	function loadTagStyle(propType){
		//根据节目类型显示，隐藏套书系列
		if(propType==0){//普通分类
			$(".activity_type").css("display","none");//style.display("none");
			$(".bundle_type").css("display","none");
			$(".entry_type").css("display","none");
			$(".bundle_entry_type").css("display","none");
			$("#tr_propTypeId").css("display","none");//推荐位类型不显示
		}else if(propType==1){//单品
			$(".activity_type").css("display","none");//style.display("none");
			$(".bundle_type").css("display","none");
		}else if(propType==2){//剧集
			$(".activity_type").css("display","none");//style.display("none");
			$(".entry_type").css("display","none");
		}else if(propType==3){//专题
			$(".bundle_type").css("display","none");
			$(".entry_type").css("display","none");
			$(".bundle_entry_type").css("display","none");
		}
	}
	
	//图书编辑
	function entryEdit(){
		var rows = entryDataGrid.datagrid('getSelections');
		if (rows.length > 0) {
			if(rows.length>1){
				$.messager.alert('提示', '只能编辑单个图书');
			}else{
				$('<div/>').dialog({
					href : '${pageContext.request.contextPath}/pages/entry/entryEdit.jsp',
					width : 650,
					height :550,
					modal : true,
					resizable:true,
					title : '编辑图书',
					buttons : [ {
						text : '确定',
						iconCls : 'icon-ok',
						handler : function() {
							var result = $('#entry_form').form('validate');
							if(!result) {
								return false;
							}//
							var entryTypeId = $("#entryTypeId").combogrid('getValue');
							if(entryTypeId==null||entryTypeId=='null'){
								$("#entryTypeId").combogrid('setValue',-1);
							}
							var langId = $("#langId").combogrid('getValue');
							if(langId==null||langId=='null'){
								$("#langId").combogrid('setValue',-1);
							}
							var d = $(this).closest('.window-body');
							$('#entry_form').form('submit',{
								 url:'${pageContext.request.contextPath}/entryController/editEntry',
								 success:function(result){
									 var r = JSON.parse(result);
									 if (r.success) {
										 entryDataGrid.datagrid('reload');	
										 d.dialog('destroy');
										 $.messager.alert('提示', '修改成功');								
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
						//加载语种
						loadAngles();
						//加载系列
						loadEntryType();
						//加载分类
						loadNodes();	
						var entryId=rows[0].id;
						var row = rows[0];
					 	$.ajax({
							url : '${pageContext.request.contextPath}/entryController/getEntryDetail?id='+entryId,
							data : {
							},
							dataType : 'json',
							success : function(r) {
								//根据节目类型加载需要显示或隐藏的套书系列样式
								//loadTagStyle(r.propType);
								//图书套书系列
								var nodeIds = stringToList(r.nodeIds);
								row.nodeIds = nodeIds;
								row.langId = stringToList(r.langId+'');
								row.entryTypeId = stringToList(r.entryTypeId+'');
								$('#entry_form').form('load',row);//直接加载表单属性
								//其他单独需要加载的属性
								$("#id").val(entryId);
								$("#appName").val(r.appName);
								$("#app_Id").val(r.app_Id);
								if(r.yearsId!=null){
									$("#yearsId").combobox('setValue', r.yearsId);
								}
								//两个单选按钮选中
								$("input[name=isPrize]:eq("+r.isPrize+")").attr("checked",'checked');
								$("input[name=formatType]:eq("+r.formatType+")").attr("checked",'checked');
								//加载封面海报
								var coverImageUrl = r.coverImageUrl+"?t="+Math.random();
								if(r.coverImageUrl!=null&&r.coverImageUrl!=""){
									$("#previewCoverImage").append("<img src='"+coverImageUrl+"' style='margin-top:5px;width: 150px;height: 120px;border:1px solid black'></img>");
									$("#previewCoverImageTAG").attr("hidden",false);
								}
								var coverThumbNailUrl = r.coverThumbNailUrl+"?t="+Math.random();
								if(r.coverThumbNailUrl!=null&&r.coverThumbNailUrl!=""){
									$("#previewCoverThumbNail").append("<img src='"+coverThumbNailUrl+"' style='margin-top:5px;width: 150px;height: 120px;border:1px solid black'></img>");
									$("#previewCoverThumbNailTAG").attr("hidden",false);
								}
								//加载源文件
								var contentModel = r.contentModel;
								if(contentModel!=null){	
									$("#contentUrl").text(contentModel.filePath);
									$("#contentUrlLoadTAG").attr("hidden",false);
								}
								//加载源音频文件
								var audioModels = r.entryAudioModels;
								if(audioModels!=null&&audioModels.length>0){
									$("#audioUrl").text(audioModels[0].audioPath);
									$("#audioUrlLoadTAG").attr("hidden",false);
								}
							}
						}); 
						//清除所选择的内容提供商
						entryDataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
					}
				});
			}
		} else {
			$.messager.alert('提示', '请选择要编辑的图书');
		}
	}

	//图书删除
	function entryDelete(id){
		entryDataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		entryDataGrid.datagrid('checkRow', entryDataGrid.datagrid('getRowIndex', id));
		entryDeleteAll();
	}
	
	//图书批量删除
	function entryDeleteAll(){
		var rows = entryDataGrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				if(rows[i].entryFlag==1){
					$.messager.alert('提示', '选择删除的图书中，['+rows[i].entryName+']是系统回看图书，不可删除！','info');
					return;
				}
				if(rows[i].statusInfo=="已发布"){
					$.messager.alert('提示', '选择删除的图书中，['+rows[i].entryName+']是已经发布的图书，不可删除！','info');
					return;
				}
			}
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			$.messager.confirm('确认', '您是否要删除当前选中的图书？', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/entryController/deleteEntry',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(result) {
							entryDataGrid.datagrid('reload');
							if (result.success) {
								$.messager.alert('提示', '删除成功','info');
								entryDataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
							}else{
								if(result.msg!=undefined){
									$.messager.alert('提示', '删除失败','error');
								}else{
									toLogin();
								}
							}
						}
					});
				}
			});
		} else {
			$.messager.alert('提示', '请选择要删除的图书');
		}
	}
	
	//图书详情
	function entryDetail(id){
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/pages/entry/entryDetail.jsp',
			width : 750,
			height : 520,
			modal : true,
			resizable:true,
			title : '图书详情',
			buttons : [ {
				text : '关闭',
				iconCls : 'icon-ok',
				handler : function() {
					var d=$(this).closest('.window-body');
					d.dialog('destroy');
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			},
			onLoad : function() {
				var index = entryDataGrid.datagrid('getRowIndex', id);
				var rows = entryDataGrid.datagrid('getRows');
				var row=rows[index];
				loadDetail(row.id);
			}
		});
		
	}	
	
	//编辑相册信息
	function entryAlbumsEdit(id){
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/pages/entry/entryAlbums.jsp',
			width : 750,
			height : 420,
			modal : true,
			resizable:true,
			title : '详情图片',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var d=$(this).closest('.window-body');
					d.dialog('destroy');
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			},
			onLoad : function() {				
				var index = entryDataGrid.datagrid('getRowIndex', id);
				var rows = entryDataGrid.datagrid('getRows');
				var row=rows[index];
				loadAlbums(row.id);
			}
		});
		
	}	
	
	//图书搜索
	function entrySearch() {
		entryDataGrid.datagrid('getPager').pagination({pageNumber:1});
		var params = entryDataGrid.datagrid('options').queryParams;
		var searchEntryName = $("#searchEntryName").val();
		
		if(searchEntryName!=undefined){
			params["title"]=searchEntryName;
		}
		entryDataGrid.datagrid('options').queryParams=params;
		entryDataGrid.datagrid('reload');
	}
	
	//重建图书索引
	function recreateBookIndex(){
		$.messager.confirm('确认', '注意：重建索引会先删除所有已存在图书索引数据，而后的重建索引过程需要一定时间，确定重建吗？', function(r) {
			if (r) {
				$.ajax({
					url : '${pageContext.request.contextPath}/entryController/recreateBookIndex',
					dataType : 'json',
					success : function(result) {
						if (result.code == 0) {
							$.messager.alert('提示', '重建成功！', 'info');
						}else{
							$.messager.alert('提示', '重建失败！','error');
						}
					}
				});
			}
		});
	}
</script>

<!-- 显示datagrid数据表格 -->
<table id="entryDataGrid" style="display: none;" singleSelect=true></table>
<!-- 显示datagrid数据表格中的toolbar -->
<div id="entryToolBar" style="height: 25px;display: none; padding:1px 2px 0 2px;">
	<div style="float:left;">
		<sec:authorize url="/entryManage/entryAdd">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="entryAdd()">添加图书</a>
		</sec:authorize>
		<sec:authorize url="/entryManage/editEntry">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="entryEdit()">修改图书</a>
        </sec:authorize>
		<sec:authorize url="/entryManage/deleteEntry">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="entryDeleteAll()">批量删除</a>
		</sec:authorize>
		<sec:authorize url="/entryManage/recreateBookIndex">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'" onclick="recreateBookIndex()">重建索引</a>
		</sec:authorize>
   </div>
   <div style="float:right;">
		图书名称：<input type="text" id="searchEntryName" name="searchEntryName"/>&nbsp;&nbsp;
	    <img onclick="" src="source/images/search.gif" title="搜索"/>&nbsp;&nbsp;
   </div>
</div>

<!-- 图片浏览区域-->
<div id="picPathVodDg" closed="true" class="easyui-window" data-options="modal:true,title:'预览',iconCls:''" style="width: 550px; height: 550px; top:100px; padding: 5px;">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false"
			style="padding: 10px; background: #fff; border: 1px solid #ccc; top: 50px;">
			<div align="center"  >
				<img class="wrap_4" width="450px" height="350px"   src="" id="previewImg" >
				<!-- <a class="easyui-linkbutton" id="lastButton" href="javascript:void(0)"  onclick='previewLastImg()'>上一张</a> 
				<a class="easyui-linkbutton" id="nextButton" href="javascript:void(0)"  onclick='previewNextImg()'>下一张</a> -->
			</div>
		</div>
	</div>
</div>
