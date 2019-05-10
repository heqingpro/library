<%@ page language="java" pageEncoding="UTF-8"%>
<style>
<!--
相册展示
-->
.entryAlbumList {
	list-style: none;
	padding:0;
	margin:0;
}
.entryAlbumList li {
	float: left; 
	width: 100px;
	height: 150px; 
	text-align: center;
	margin: 10px;
	padding: 0px;
}
 .entryAlbumList li img {
	float: left;
	width: 80px;
	height: 80px;
	text-align: center;
	border: 1px solid #CCC;
	margin: 5px;
} 
</style>
<!-- 替换图片 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/source/jslib/image.js" charset="utf-8"></script>
<script type="text/javascript">

	$("#albumImageMaxSize").text(Globals.albumImageMaxSize);
	$("#albumImageMaxNum").text(Globals.albumImageMaxNum);
	
	function loadAlbums(id){
		$("#id").val(id);
		//$.messager.alert('提示', "loadWan mac:"+deviceKey,'info');
		$.ajax({
			url : '${pageContext.request.contextPath}/entryController/getEntryAlbums?entryId='+id,
			data : {
			},
			dataType : 'json',
			success : function(r) {
				 var imageList_tv=r.entryImages_tv;
				 var imageList_app=r.entryImages_app;
				 $("#album_tv").html("");
				 $("#album_app").html("");
				 //加载电视端详情海报
				 //加载tv端详情海报
				 if(imageList_tv!=""&&imageList_tv!=null){
					    var divId = "album_tv";
						for(var i in imageList_tv){
							 if(imageList_tv[i]!=""&&imageList_tv[i]!=null){
								 var imageId=imageList_tv[i].id;
								 var imageName=imageList_tv[i].uniqueName;
								 var url=imageList_tv[i].imagePath+"?ran="+Math.random();
								 var div="<li id='imgDiv_"+divId+i+"' value="+imageId+"></li>";
							   
							     var img="<img src="+url+"></img>";
							     /* if(imageName.length>16){
							    	 imageName="<marquee>"+imageName+"</marquee>";
							     } */
								 var nameDiv='<div>'+imageName+'</div>';
								 var setCoverButton='<a href="javascript:setCover(\''+imageId+'\')" class="setCoverCls"><span style="color:blue">设置封面</span></a>';
								 var replaceButton='<a href="javascript:openReplaceImageDiag(\''+imageId+'\',\''+id+'\')" class="replaceCls"><span style="color:blue">替换图片</span></a>';
								 var delButton='<a href="javascript:delImage(\''+imageId+'\',\''+id+'\')" class="delCls"><span style="color:blue">删除</span></a>';
								 var downButton='<a href="javascript:window.downloadFile(\'imgDiv_'+divId+i+'\')" class="downloadcls">下载<a>';
								 var cover='<div style="position: relative; left: 10px; top: -70px; font-size: 30px;"><span style="color:blue">封面</span></div>';
								 $("#"+divId).append(div);
								 $("#imgDiv_"+divId+i).append(nameDiv);
								 $("#imgDiv_"+divId+i).append(img);
								 $("#imgDiv_"+divId+i).append(replaceButton);
								 $("#imgDiv_"+divId+i).append(delButton);
								 $("#imgDiv_"+divId+i).append(downButton);
								 $('.setCoverCls').linkbutton({text:'设置封面',plain:true,iconCls:'icon-edit'});  
								 $('.replaceCls').linkbutton({text:'替换图片',plain:true,iconCls:'icon-edit'});  
								 $('.delCls').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
								 /* $('.downloadcls').linkbutton({text:'下载',plain:true,iconCls:'icon-download'}); */
								}
						}
					}else{
						$("#album_tv").html("");
					}
				 	//加载内容提供商端详情图片
				 if(imageList_app!=""&&imageList_app!=null){
					    var divId = "album_app";
						for(var i in imageList_app){
							 if(imageList_app[i]!=""&&imageList_app[i]!=null){
								 var imageId=imageList_app[i].id;
								 var imageName=imageList_app[i].uniqueName;
								 var url=imageList_app[i].imagePath+"?ran="+Math.random();
								 var div="<li id='imgDiv_"+divId+i+"' value="+imageId+"></li>";
							   
							     var img="<img src="+url+"></img>";
							    /*  if(imageName.length>15){
							    	 imageName="<marquee>"+imageName+"</marquee>";
							     } */
								 var nameDiv='<div>'+imageName+'</div>';
								 var setCoverButton='<a href="javascript:setCover(\''+imageId+'\')" class="setCoverCls"><span style="color:blue">设置封面</span></a>';
								 var replaceButton='<a href="javascript:openReplaceImageDiag(\''+imageId+'\',\''+id+'\')" class="replaceCls"><span style="color:blue">替换图片</span></a>';
								 var delButton='<a href="javascript:delImage(\''+imageId+'\',\''+id+'\')" class="delCls"><span style="color:blue">删除</span></a>';
								 var downButton='<a href="javascript:window.downloadFile(\'imgDiv_'+divId+i+'\')" class="downloadcls">下载<a>';
								 var cover='<div style="position: relative; left: 10px; top: -70px; font-size: 30px;"><span style="color:blue">封面</span></div>';
								 $("#"+divId).append(div);
								 $("#imgDiv_"+divId+i).append(nameDiv);
								 $("#imgDiv_"+divId+i).append(img);
								 $("#imgDiv_"+divId+i).append(replaceButton);
								 $("#imgDiv_"+divId+i).append(delButton);
								 $("#imgDiv_"+divId+i).append(downButton);
								 $('.setCoverCls').linkbutton({text:'设置封面',plain:true,iconCls:'icon-edit'});  
								 $('.replaceCls').linkbutton({text:'替换图片',plain:true,iconCls:'icon-edit'});  
								 $('.delCls').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
								//$('.downloadcls').linkbutton({text:'下载',plain:true,iconCls:'icon-download'});
								}
						}
				 	}else{
				 		$("#album_app").html("");
				 	}
			}
		}); 
	}
	//修改多媒体信息 包括相册图集，视频地址，外链接地址
	function editEntryAlbums(deviceType){
		var d = $(this).closest('.window-body');
		$('#albumsDataForm').form('submit',{
		     url:'${pageContext.request.contextPath}/entryController/editEntryAlbums?deviceType='+deviceType,   
		     success:function(result){
		    	 var r = JSON.parse(result);
				 if (r.success) {
					 //d.dialog('destroy');
					 $.messager.alert('提示', '上传成功');		
					 loadAlbums($("#id").val());
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
	//替换图片
	function replaceImage(imageId,id){
		var d = $(this).closest('.window-body');
		$('#replaceForm').form('submit',{
			  method:'POST',
		      url:'${pageContext.request.contextPath}/entryController/replaceEntryImage?imageId='+imageId,
		      onSubmit: function(){
		    	  if($("#image").val()==null||$("#image").val()==""){
		    		  $.messager.alert('系统提示', "未选择任何图片", 'warning');	 
		    		  return false;
		    	  }	    	  
		  	},
		     success:function(result){
		    	 var r = JSON.parse(result);
				 if (r.success) {
					 d.dialog('destroy');
					 $('#replaceImageDiag').dialog('close'); 
					 $.messager.alert('提示', '替换成功');
					 loadAlbums(id);
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
	//删除图片
	function delImage(imageId,id){
		 $.messager.confirm("提示","确定要删除该图片么?",function(r){
				if(r){
					$.ajax({
						url : '${pageContext.request.contextPath}/entryController/delEntryImage',
						data : {
							imageId:imageId
						},
						dataType : 'json',
						success : function(r){
						 if (r.success) {
							 //d.dialog('destroy');
							 $.messager.alert('提示', '删除成功');
							 loadAlbums(id);
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
			});
	}
	//预览相册图片
	function setImagePreview_Content(operationType){
		info="";
		totalNum=0;
		var docObj=null;
		var imgObjPreviewTd=null;
		if(operationType=="tv"){
			docObj=document.getElementById("albumImages_tv");
			imgObjPreviewTd=document.getElementById("previewContent_tv");
		}else if(operationType=="app"){
			docObj=document.getElementById("albumImages_app");
			imgObjPreviewTd=document.getElementById("previewContent_app");
		}
		var divId="album"+"_"+operationType;
		$("#"+divId).html("");
		//兼容火狐浏览器
		if(window.URL!=""&&typeof(window.URL)!="undefined")
		{
			imgObjPreviewTd.innerHTML="";
			for(var i=0;i<docObj.files.length;i++){
				if(docObj.files[i].size>albumImageMaxSize*1024){
					info+=docObj.files[i].name+",";	
				}
				totalNum++;
				var imgObjPreview=document.createElement("img");
				//火狐下，直接设img属性
				imgObjPreview.style.display = 'block';
				imgObjPreview.style.width = '120px';
				imgObjPreview.style.height = '100px'; 
				imgObjPreview.style.margin = '5px'; 
				imgObjPreview.style.border="1px solid #000";
				//imgObjPreview.src = docObj.files[0].getAsDataURL();
				//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
				imgObjPreview.src = window.URL.createObjectURL(docObj.files[i]);
				imgObjPreview.style.float="left";
				imgObjPreviewTd.appendChild(imgObjPreview);
				//添加图片到可移动排序的li套书系列内
				/* var div="<li id='imgDiv_"+divId+i+"' value="+docObj.files[i].name+"></li>";
				var img="<img src="+window.URL.createObjectURL(docObj.files[i])+"></img>";
				$("#"+divId).append(div);
				$("#imgDiv_"+divId+i).append(img); */
			}
			
		}else if(window.webkitURL!=""&&typeof(window.webkitURL)!="undefined"){
			imgObjPreviewTd.innerHTML="";
			//兼容360浏览器
			for(var i=0;i<docObj.files.length;i++){
				var imgObjPreview=document.createElement("img");
				//火狐下，直接设img属性
				imgObjPreview.style.display = 'block';
				imgObjPreview.style.width = '120px';
				imgObjPreview.style.height = '100px'; 
				imgObjPreview.style.margin = '5px'; 
				imgObjPreview.style.border="1px solid #000";
				//imgObjPreview.src = docObj.files[0].getAsDataURL();
				//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
				imgObjPreview.src=window.webkitURL.createObjectURL(docObj.files[i]);
				imgObjPreview.style.float="left";
				imgObjPreviewTd.appendChild(imgObjPreview);
				//添加图片到可移动排序的li套书系列内
				/* var div="<li id='imgDiv_"+divId+i+"' value="+docObj.files[i].name+"></li>";
				var img="<img src="+window.webkitURL.createObjectURL(docObj.files[i])+"></img>";
				$("#"+divId).append(div);
				$("#imgDiv_"+divId+i).append(img); */
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
				//添加图片到可移动排序的li套书系列内
				/* var div="<li id='imgDiv_"+divId+i+"' value="+docObj.files[i].name+"></li>";
				var img="<img src="+imgSrc+"></img>";
				$("#"+divId).append(div);
				$("#imgDiv_"+divId+i).append(img); */
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
		//-------------add by fangg 为了增加排序功能----------
		//加载延迟
		//setTimeout('imageSortHandlerMM()', 1000); //延迟1秒
	}
	/* 
	 //相册图片拖动事件绑定鼠标移动事件
	 //setTimeout('imageMoveHandler()', 1000); //延迟1秒
	 setTimeout('imageSortHandler()', 1000); //延迟1秒 */

	//相册可排序
	function imageSortHandler(){
		$('#album').sortable().bind('sortupdate', function() {
			$('#msg').html('已变换位置').fadeIn(200).delay(1000).fadeOut(200);
		});
	}
	
	//编辑相册顺序
	function editAlbumSort(){
		var albumImageIds="";
		$("#album").find("li").each(function(){
			albumImageIds = albumImageIds+this.value+",";
		});
		
		$.getJSON("editAlbumSort.action",{'albumImageIds':albumImageIds},function(json){
			checkSession(json); 
			var resultCode=json.resultCode;
	    	 var msg=json.msg;
			if(resultCode=="success"){
				 msgShow('系统提示', "排序成功！", 'warning');
				 reloadDatagrid(nodeId);
			}else{
				 msgShow('系统提示', msg, 'warning');
			}
		});
	}
</script>
<div align="left" style="margin-top: 10px;top: 50px;">
	<form id="albumsDataForm" method="post" draggable="true" enctype="multipart/form-data">
		<input id="id" name="id" type="hidden"/>
		<table class="tableForm" id="tableForm" style="width: 100%">
			<tr id="albumImagesTip">
					<td colspan="2"><font color="blue">注意:相册图片大小不能超过<span id="albumImageMaxSize" style="color: red;"></span>K,格式只能是jpg,jpeg,png,gif,且图片张数不能超过<span id="albumImageMaxNum" style="color: red;"></span>张</font></td>
			</tr>
			<tr id="tr_albumImages_tv" >
					<th>TV端详情海报：</th>
					<td><input id="albumImages_tv" name="albumImages_tv" type="file" multiple="multiple" onchange="setImagePreview_Content('tv')" accept="image/gif, image/jpeg, image/png"/>
						<a  href="javascript:void(0)" class="easyui-linkbutton"
								data-options="plain:true,iconCls:'icon-add'"
								onclick="editEntryAlbums(0)">开始上传</a>
					</td>
					<td></td>
			</tr>
			<tr id="previewContentTr_tv">
					<td id="previewContent_tv" colspan="2"></td>
			</tr>
			<tr>
				<td colspan="5">
				<div>
					<hr></hr>
					<!-- 显示图片列表,这里增加图片可排序功能 by fangg -->
					<!-- <span><font color="green">提示：鼠标拖动图片对相册排序</font></span> -->
					<ul class="entryAlbumList" id="album_tv">	
					</ul>
					<div style="clear:both" id="msg"></div>
					<!-- <div><a class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:false" onclick="editAlbumSort()"><span>排序</span></a></div> -->
				</div>
				</td>
			
			</tr>
			<tr id="tr_albumImages_app" >
					<th>APP端详情海报：</th>
					<td><input id="albumImages_app" name="albumImages_app" type="file" multiple="multiple" onchange="setImagePreview_Content('app')" accept="image/gif, image/jpeg, image/png"/>
						<a  href="javascript:void(0)" class="easyui-linkbutton"
								data-options="plain:true,iconCls:'icon-add'"
								onclick="editEntryAlbums(1)">开始上传</a>
					</td>
					<td></td>
			</tr>
			<tr id="previewContentTr_app">
					<td id="previewContent_app" colspan="2"></td>
			</tr>
			<tr>
				<td colspan="5">
				<div>
					<hr></hr>
					<!-- 显示图片列表,这里增加图片可排序功能 by fangg -->
					<!-- <span><font color="green">提示：鼠标拖动图片对相册排序</font></span> -->
					<ul class="entryAlbumList" id="album_app">	
					</ul>
					<div style="clear:both" id="msg"></div>
					<!-- <div><a class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:false" onclick="editAlbumSort()"><span>排序</span></a></div> -->
				</div>
				</td>
			
			</tr>
		</table>		
	</form>
</div>
<!-- 替换图片start -->
<div id="replaceImageDiag" class="easyui-dialog" style="width:550px;height:400px;" closed=true
		data-options="title:'替换图片',modal:true,resizable:true,onClose:function(){},
			buttons:[{
				text:'上传',
				handler:function(){replaceImage($('#imageId_replace').val(),$('#entryId_replace').val());}
			},{
				text:'关闭',
				handler:function(){closeReplaceImageDiag();}
			}]">
			<div align="center">
			<form id="replaceForm" method="post" draggable="true" enctype="multipart/form-data">
				<table>
				<tr>
				<td style="display:none;"> 
				<input id="imageId_replace" type="text" name="imageId"/>
				<input id="entryId_replace" type="text" name="entryId"/>
		        </td>
				</tr>
		        <tr id="imagesTrSingle" >
					<td><input id="image" name="image" type="file" onchange="setImagePreview_single()"  accept="image/gif, image/jpeg,image/png" /></td>
				</tr>
				<tr>
					<td colspan="2"><font color="blue">注意:相册图片大小不能超过<span id="albumImageMaxSize" style="color: red;"></span>K,格式只能是jpg,jpeg,png,gif</font></td>
				</tr>
				</table>
				<div id="preview_single" style="padding:10px 30px;">
				</div>
			</form>
			</div>
</div>
<!-- 替换图片end -->