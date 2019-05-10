<%@ page language="java" pageEncoding="UTF-8"%>
<!-- 分类管理 -->
<script type="text/javascript">
	var enNameFlag = true;
	var urlFlag = true;
	
	function checkEnName(){
		var enName = $("#enName").val();
		if(enName=="")
			return ;
		if( /[a-zA-Z0-9\s]+/.test(enName)){		
			$("#enNameErrorMsg").hide();
			enNameFlag = true;
		}else{
			enNameFlag = false;
			$("#enNameErrorMsg").show();
		}
	}
	function checkUrl(){
		var linkUrl = $("#linkUrl").val();
		if(linkUrl=="")
			return ;
		if(/^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/.test(linkUrl)){
			$("#linkUrlErrorMsg").hide();
			urlFlag = true;
		}else{
			urlFlag = false;
			$("#linkUrlErrorMsg").show();
		}
	}
	
	function setImagePreview(_id1,_id2) {
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

</script>
<div align="center" style="margin-top: 10px;" >
	<form id="node_form" method="post" draggable="true"  enctype="multipart/form-data">
		<input name="pId" id="pId" type="hidden"/>
		<input name="id" id="id" type="hidden"/>
		
		<table class="tableForm" id="tableForm">
			<tr id="pNameTr">
				<th>父分类：</th>
				<td><input  name="pName" id="pName" readonly="readonly"/></td>
			</tr>
			<tr>
				<th>名称：</th>
				<td><input id="name" name="name" onkeyup="queryShortName('enName',this.value)"  class="easyui-validatebox" data-options="required:'true'" style="width:200px" /></td>
			</tr>
			<tr>
			<th>英文名称：</th>
				<td><input id="enName" name="enName"  class="easyui-validatebox" data-options="required:'true'" style="width:200px"  onkeyup="checkEnName()" onblur="checkEnName()"/>
				<div id="enNameErrorMsg" style="color:Red;display:none;">* 英文名格式不正确</div>
				</td>
			</tr>
			<!-- <tr id="tr_nodeTypeId">
				<th >类别：</th>推荐位
				<td><select id="nodeType" class="easyui-combobox" name="nodeType" style="width: 200px;" data-options="required:'true'">
						<option style="width: 200px" value='0' selected="selected">普通分类</option>
						<option style="width: 200px" value='1'>单品类</option>
						<option style="width: 200px" value='2'>剧集类</option>
						<option style="width: 200px" value='3'>专题类</option>					
					</select>
				</td>
			</tr> -->
			<!-- 海报 -->
			<tr>
				<th >logo海报：</th>
				 <td>
					<input id="nodeImage" class="easyui-validatebox" data-options="" name="nodeImage" type="file" onchange="setImagePreview('nodeImage','previewImage')" style="height: 27px" accept="image/gif, image/jpeg, image/png"/>
				</td>
			</tr>
			<tr height="15px">
					<td></td>
					<td id="previewImage" style="width: 200px"></td>
			</tr>
			<!-- 缩略图 -->
			<tr>
				 <th >缩略图：</th>
				 <td>
					<input id="nodeThumbnail" class="easyui-validatebox" data-options="" name="nodeThumbnail" type="file" onchange="setImagePreview('nodeThumbnail','previewNodeThumbnail')" style="height: 27px" accept="image/gif, image/jpeg, image/png"/>
				 </td>
			</tr>
			<tr height="15px">
					<td></td>
					<td id="previewNodeThumbnail" style="width: 200px"></td>
			</tr>
			
			<!-- 专题类 -->
			<!-- <tr>
				<th>外链接：</th>
				<td><input id="linkUrl" name="linkUrl"  class="easyui-validatebox" data-options="validType:'url'" style="width:350px"/>    onkeyup="checkUrl()" onblur="checkUrl()"
				<div id="linkUrlErrorMsg" style="color:Red;display:none;">* 外链接格式不正确</div>
				</td>
			</tr>	 -->		
			<!-- 单品类 -->
			<!-- <tr>
				<th>单集vodID：</th>
				<td><input id="vodId" name="vodId"  class="easyui-validatebox" data-options="required:'true'" style="width:200px" /></td>
			</tr> -->
			<!-- 剧集类 -->                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			<!-- <tr>
				<th>父级vodID：</th>
				<td><input id="fatherVodId" name="fatherVodId"  class="easyui-validatebox" data-options="required:'true'" style="width:200px" /></td>
			</tr> -->
			<!-- <tr>
				<th>播放间隔时间:</th>
				<td><input id="resumeDuration" name="resumeDuration"  class="easyui-validatebox" data-options="required:'true'" style="width:200px" /></td>
			</tr> -->
		</table>
	</form>
</div>
