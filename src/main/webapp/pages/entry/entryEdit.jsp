<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var enNameFlag = true;
	var urlFlag = true;
	//必须绑定事件
	$(function () {
		  $('#propType').combobox({
		          onSelect: function (record) {
		        	  changeEntryFormStyle();
		         }
		     });
	});
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
		if(enName.indexOf("'")!=-1||enName.indexOf('"')!=-1){
			enNameFlag = false;
			$("#enNameErrorMsg2").show();
		}else{
			$("#enNameErrorMsg2").hide();
			enNameFlag = true;
		}
	}
	//不能包含英文单引号，双引号
	function checkTag(prop,errorTag){
		var title = $("#"+prop).val();
		if(title=="")
			return ;
		if(title.indexOf("'")!=-1||title.indexOf('"')!=-1){
			enNameFlag = false;
			$("#"+errorTag).show();
		}else{
			$("#"+errorTag).hide();
			enNameFlag = true;
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
	
	//预览
	function setImagePreview(_id1,_id2) {
		//$("#entryImage_tv").val("");
		//$("#entryImage_app").val("");
	
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
				/* if(_id1=='entryImage_tv'){
					$("#entryImage_tv").val(imgObjPreview.src);
				}
				if(_id1=='entryImage_app'){
					$("#entryImage_app").val(imgObjPreview.src);
				} */
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

	//加载图书系列
	function loadEntryType(){
		$('#entryTypeId').combogrid({
			multiple : false,
			nowrap : true,
			url : '${pageContext.request.contextPath}/entryTypeController/queryEntryTypeList?page=1&rows=1000',
			//url:"${pageContext.request.contextPath}/nodeController/queryNodeTree?appId=5",	
			idField : 'id',
			textField : 'typeName',
			fitColumns : true,
			rownumbers : false,
			editable : false,
			checkOnSelect : true,
			selectOnCheck : true,
			columns : [ [
			{
				title : '编号',
				field : 'id',
				width : 50,
				checkbox : true
			},  
			{
				title : '系列名',
				field : 'typeName',
				width : 110
			}
			] ],
			
		}); 
	}
	//加载图书语种
	function loadAngles(){
		$('#langId').combogrid({
			multiple : false,
			nowrap : true,
			url : '${pageContext.request.contextPath}/angleController/queryAngleList?page=1&rows=1000',
			idField : 'id',
			textField : 'name',
			fitColumns : true,
			rownumbers : false,
			editable : false,
			checkOnSelect : true,
			selectOnCheck : true,
			columns : [ [
			{
				title : '编号',
				field : 'id',
				width : 50,
				checkbox : true
			},  
			{
				title : '语种名',
				field : 'name',
				width : 110
			}
			] ],
			
		}); 
	}	
	//
	function loadNodes(){
		$('#nodeIds').combogrid({
			multiple : true,
			nowrap : true,
			url: '${pageContext.request.contextPath}/nodeController/queryNodeTree?page=1&rows=1000&subView=1&appId='+appId,
			idField : 'id',
			textField : 'name',
			fitColumns : true,
			rownumbers : false,
			editable : false,
			checkOnSelect : true,
			selectOnCheck : true,
			columns : [ [
			{
				title : '编号',
				field : 'id',
				width : 50,
				checkbox : true
			},  
			{
				title : '分类名',
				field : 'name',
				width : 110
			}
			] ],
			
		}); 
		 
	}
	//根据图书类型选择不同，修改显示的样式
	function changeEntryFormStyle(){
		var propType = $("#propType").combobox('getValue');
		if(propType==1){//单品
			$(".activity_type").css("display","none");
			$(".bundle_type").css("display","none");
			$(".entry_type").css("display","table-row");//表格中使用block格式会错
			$(".bundle_entry_type").css("display","table-row");//表格中使用block格式会错			
		}else if(propType==2){//剧集
			$(".activity_type").css("display","none");
			$(".entry_type").css("display","none");
			$(".bundle_type").css("display","table-row");
			$(".bundle_entry_type").css("display","table-row");//表格中使用block格式会错
		}else if(propType==3){//专题
			$(".activity_type").css("display","table-row");
			$(".bundle_type").css("display","none");
			$(".entry_type").css("display","none");
			$(".bundle_entry_type").css("display","none");
		}
	}
</script>
<div align="center" style="margin-top: 10px;top: 50px" >
	<form id="entry_form" method="post" draggable="true"  enctype="multipart/form-data" >
		<input id="id" name="id" type="hidden"/>
		<table class="tableForm" id="tableForm">
			<tr>
				<th>内容提供商：</th>
				<td>
					<input id="app_Id" name="app_Id" type="hidden"/>
					<input id="appName" name="appName" readonly="readonly"  class="easyui-validatebox" data-options="required:'true'" style="width:200px" />
				</td>
			</tr>
			<tr>
				<th>标题：</th>
				<td><input id="title" name="title" onkeyup="queryShortName('enName',this.value)"  class="easyui-validatebox" data-options="required:'true'" style="width:200px"  onkeyup="checkTag('title','titleErrorMsg')" onblur="checkTag('title','titleErrorMsg')"/>
				<div id="titleErrorMsg" style="color:Red;display:none;">* 不能包含英文单引号或双引号</div>
				</td>
				
			</tr>
			<tr>
				<th>英文简拼：</th>
				<td><input id="enName" name="shortName"  class="easyui-validatebox" data-options="required:'true'" style="width:200px"  onkeyup="checkEnName()" onblur="checkEnName()"/>
				<div id="enNameErrorMsg" style="color:Red;display:none;">* 英文简拼格式不正确</div>
				<div id="enNameErrorMsg2" style="color:Red;display:none;">* 不能包含英文单引号或双引号</div>
				</td>
			</tr>
			<tr>
				<th>作者：</th>
				<td><input id="author" name="author"  class="easyui-validatebox" data-options="required:'true'" style="width:200px" onkeyup="checkTag('author','authorErrorMsg')" onblur="checkTag('author','authorErrorMsg')"/>
				<div id="authorErrorMsg" style="color:Red;display:none;">* 不能包含英文单引号或双引号</div>
				</td>
			</tr>
		
			<tr>
				<th>出版机构：</th>
				<td><input id="pubOrg" name="pubOrg"  class="easyui-validatebox"  style="width:200px" /></td>
			</tr>
			<tr>
				<th>编者：</th>
				<td><input id="editor" name="editor"  class="easyui-validatebox"  style="width:200px" onkeyup="checkTag('editor','editorErrorMsg')" onblur="checkTag('editor','editorErrorMsg')"/>
				<div id="editorErrorMsg" style="color:Red;display:none;">* 不能包含英文单引号或双引号</div>
				</td>
			</tr>
			<tr>
				<th>原著作者：</th>
				<td><input id="originalAuthor" name="originalAuthor"  class="easyui-validatebox"  style="width:200px" onkeyup="checkTag('originalAuthor','originalAuthorErrorMsg')" onblur="checkTag('originalAuthor','originalAuthorErrorMsg')"/>
				<div id="originalAuthorErrorMsg" style="color:Red;display:none;">* 不能包含英文单引号或双引号</div>
				</td>
			</tr>
			<tr id="tr_yearsId">
				<th >年代：</th>
				<td><select id="yearsId" class="easyui-combobox" name="yearsId" style="width: 200px;" onselect="">
						<option style="width: 200px" value='1'>古代</option>
						<option style="width: 200px" value='2'>近现代</option>
						<option style="width: 200px" value='3'>现当代</option>	
					</select>
				</td>
			</tr>
			<tr id="tr_langIds">
				<th >语种：</th><!-- 单选 -->
				<td><select id="langId" class="easyui-combogrid" name="langId" style="width: 200px;" ></select></td>
			</tr>
			<tr id="tr_nodeIds">
				<th >分类：</th><!-- 单选 -->
				<td><select id="nodeIds"  class="easyui-combogrid" name="nodeIds" style="width: 200px;" ></select></td>
			</tr>
			<tr id="tr_typeIds">
				<th >套书系列：</th><!-- 单选  -->
				<td><select id="entryTypeId" class="easyui-combogrid" name="entryTypeId" style="width: 200px;">
						<option  value="0"  selected="selected">单行书</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>页数：</th>
				<td><input id="pageCount" name="pageCount" type="text" class="easyui-numberbox" value="0" data-options="min:0,precision:0" style="width:200px"></input></td>  
				<!-- <td><input id="pageCount" name="pageCount"  class="easyui-validatebox" data-options="validType:'Number'"  style="width:200px" /></td> -->
			</tr>
			<tr>
				<th>宽度：</th>
				<td><input id="width" name="width" type="text" class="easyui-numberbox" value="0" data-options="min:0,precision:0" style="width:200px"></input></td>
				<!-- <td><input id="width" name="width"  class="easyui-validatebox" data-options="validType:'Number'"  style="width:200px" /></td> -->
			</tr>
			<tr>
				<th>高度：</th>
				<td><input id="height" name="height" type="text" class="easyui-numberbox" value="0" data-options="min:0,precision:0" style="width:200px"></input></td>
				<!-- <td><input id="height" name="height"  class="easyui-validatebox" data-options="validType:'Number'"  style="width:200px" /></td> -->
			</tr>
			<tr> 
			<!-- <tr>
				<th>版本类型：</th>
				<td><input id="editionType" name="editionType"  class="easyui-validatebox"  style="width:200px" /></td>
			</tr> -->
			<tr>
				<th >是否获奖：</th>
				<td style="text-align:left">
		            <span>
		                <input type="radio" name="isPrize" value="0" checked="checked">否</input>
		                <input type="radio" name="isPrize" value="1">是</input>
		            </span>
	        	</td>
        	</tr>
			<tr>
				<th >封面海报：</th>
				 <td>
					<input id="coverImage" class="easyui-validatebox" data-options="" name="coverImage" type="file" onchange="setImagePreview('coverImage','previewCoverImage')" style="height: 27px" accept="image/gif, image/jpeg, image/png"/>
				</td>
			</tr>
			<tr height="15px">
					<td></td>
					<td id="previewCoverImage" style="width: 200px"></td>
					<td hidden="true" id="previewCoverImageTAG"><a href="javascript:window.downloadFile('previewCoverImage')" class="downloadcls">下载<a></td>
			</tr>
			<tr>
				<th >封面推荐大图：</th>
				 <td>
					<input id="coverThumbNail" class="easyui-validatebox" data-options="" name="coverThumbNail" type="file" onchange="setImagePreview('coverThumbNail','previewCoverThumbNail')" style="height: 27px" accept="image/gif, image/jpeg, image/png"/>
				</td>
			</tr>
			<tr height="15px">
					<td></td>
					<td id="previewCoverThumbNail" style="width: 200px"></td>
					<td hidden="true" id="previewCoverThumbNailTAG"><a href="javascript:window.downloadFile('previewCoverThumbNail')" class="downloadcls">下载<a></td>
			</tr>
			<tr>
				<th >格式：</th>
				<td style="text-align:left">
		            <span>
		                <input type="radio" name="formatType" value="1" checked="checked">无声</input>
		                <input type="radio" name="formatType" value="2">有声</input>
		                <input type="radio" name="formatType" value="3">电子书</input>
		            </span>
	        	</td>
        	</tr>
			<tr>
				<th>选择图书：</th>
				<td><input id="entryImportFile" type="file" name="entryImportFile" class="easyui-validatebox" accept="*"/>
				<div> *(后缀需为txt/epub/pdf/mp3/mp4/wmv)</div>
				<div id="contentUrlLoadTAG" hidden="true"><span style="color: red;">已上传</span><span id = "contentUrl"></span><a href="javascript:window.downloadFile($('#contentUrl').text())" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-download'">下载<a></div>
				</td>	
			</tr>
				<tr>
				<th>选择音频：</th>
				<td><input id="audioFile" type="file" name="audioFile" class="easyui-validatebox" accept="audio/*"/>
				<div> *(后缀需为mp3/mp4/wmv)</div>
				<div id="audioUrlLoadTAG" hidden="true"><span style="color: red;">已上传</span><span id="audioUrl"></span><a href="javascript:window.downloadFile($('#audioUrl').text())" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-download'">下载<a></div>
				</td>	
			</tr>
			<tr>
				<th>简介：</th>
				<td><textarea id="desc" name="desc" rows="10" cols="10" class="easyui-validatebox" style="width:300px"></textarea></td>
			</tr>
		</table>
	</form>
</div>

