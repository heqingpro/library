<%@ page language="java" pageEncoding="UTF-8"%>
<!-- 系列-->
<script type="text/javascript">

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
<div align="center" style="margin-top: 30px;">
	<form id="entryType_form" method="post" draggable="true" enctype="multipart/form-data" >
		<input name="id" id="id" type="hidden"/>
		<table class="tableForm" id="tableForm">
			<tr>
				<th >系列名称：</th>
				<td><input id="typeName" name="typeName" class="easyui-validatebox" style="width: 300px;" data-options="required:'true'" /></td>
			</tr>
			<!-- 缩略图 -->
			<tr>
				 <th >logo图：</th>
				 <td>
					<input id="typeImage" class="easyui-validatebox" data-options="" name="typeImage" type="file" onchange="setImagePreview('typeImage','previewImage')" style="height: 27px" accept="image/gif, image/jpeg, image/png"/>
				 </td>
			</tr>
			<tr height="15px">
					<td></td>
					<td id="previewImage" style="width: 200px"></td>
			</tr>
			<tr>
				<th >备注：</th>
				<td><input id="remark" name="remark" class="easyui-validatebox" style="width: 300px;" /></td>
			</tr>
		
		</table>
	</form>
</div>