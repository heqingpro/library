<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function loadDetail(entryId){
				//$.messager.alert('提示', "loadWan mac:"+deviceKey,'info');
				$.ajax({
					url : '${pageContext.request.contextPath}/entryController/getEntryDetail?id='+entryId,
					data : {
					},
					dataType : 'json',
					success : function(r) {
						var bookId = r.id;
						var title  = r.title;//图书名称
						var shortName = r.shortName;//长度200限制
						var author = r.author;//作者
						var desc = r.desc;//简介
						var pubOrg = r.pubOrg;// 出版机构
						var editor = r.editor; //编者
						var originalAuthor = r.originalAuthor; //原著作者
						var yearsId = r.yearsId; //  年代id: 1古代 2近现代 3现当代
						var auditStatus = r.auditStatus;//状态   （分类状态：0未审核 1已审核）
						var uid = r.uid;//图书第三方id 
						var global_guid = r.global_guid;//图书唯一编码 
						var pageCount = r.pageCount; //页数
						var width = r.width;//宽度
						var height = r.height;//高度
						var formatType = r.formatType; //格式:1无声 2有声,3 txt（三秦）
						var coverImageUrl = r.coverImageUrl;//图书封面url
						var coverThumbNailUrl = r.coverThumbNailUrl;
						var content = r.content;//文本类图书的内容
						var editionType = r.editionType;//版本类型
						var sortValue = r.sortValue;//排序编号
						var isPrize = r.isPrize;//是否为获奖作品
						var langId = r.langId;//语种id
						var langName = r.langName;//素材绑定语种
						var appId = r.app_Id; //所属的内容提供商Id(appId这个单词获取不到参数值)
						var appName = r.appName; //所属的内容提供商名称
						var entryTypeId = r.entryTypeId;// 所属的图书套书信息
						var	entryTypeName = r.entryTypeName;// 所属的图书套书信息
						var nodeIds = r.entryTypeName; // 所属的分类id集合
						var nodeNames = r.nodeNames; //所属的分类名称集合
						var addTime = r.addTime;//添加时间
						var modifyTime = r.modifyTime; // 更新时间
						var operateUserName = r.operateUserName;//操作人名字
						if(yearsId==1){
							yearsId = '古代';
						}else if(yearsId==2){
							yearsId = '近现代';
						}else if(yearsId==3){
							yearsId = '现当代';						}
						if(formatType==1){
							formatType = '无声';
						}else if(formatType==2){
							formatType = '有声';
						}
						if(isPrize == 0){
							isPrize = '获奖'
						}else if(isPrize==1){
							isPrize = '未获奖';
						}else if(isPrize==-1){
							isPrize = '未知';
						}
						if(auditStatus==0){
							formatType = '未审核';
						}else if(auditStatus==1){
							formatType = '审核通过';
						}
						$("#bookId").text(bookId);
						$("#title").text(title);
						$("#shortName").text(shortName);
						$("#author").text(author);
						$("#pubOrg").text(pubOrg);
						$("#editor").text(editor);
						$("#originalAuthor").text(originalAuthor);
						$("#yearsId").text(yearsId);
						$("#auditStatus").text(auditStatus);
						$("#uid").text(uid);
						$("#global_guid").text(global_guid);
						$("#pageCount").text(pageCount);
						$("#width").text(width);
						$("#height").text(height);
						$("#formatType").text(formatType);
						if(coverImageUrl!=null&&coverImageUrl!=""){
							$("#coverImageUrl").attr("src",coverImageUrl);
							$("#coverImageUrlTAG").attr("hidden",false);
							$("#coverImage").attr("hidden",false);
						}
						if(coverThumbNailUrl!=null&&coverThumbNailUrl!=""){
							$("#coverThumbNailUrl").attr("src",coverThumbNailUrl);
							$("#coverThumbNailUrlTAG").attr("hidden",false);
							$("#coverThumbNail").attr("hidden",false);
						}
						$("#editionType").text(editionType);
						$("#sortValue").text(sortValue);
						$("#isPrize").text(isPrize);
						$("#langName").text(langName);
						$("#appName").text(appName);
						$("#entryTypeName").text(entryTypeName);
						$("#nodeNames").text(nodeNames);
						$("#addTime").text(addTime);
						$("#modifyTime").text(modifyTime);
						$("#operateUserName").text(operateUserName);
						/* if(desc.length>200){
							desc = desc.substring(0,200)+"……";
						} */
						$("#desc").text(desc);
						$("#browseCount").text(r.browseCount);
						$("#downloadCount").text(r.downloadCount);
						var contentModel = r.contentModel;
						if(contentModel!=null){	
							$("#contentUrl").text(contentModel.filePath);
							$("#contentUrlLoadTAG").attr("hidden",false);
						}
						var audioModels = r.entryAudioModels;
						if(audioModels!=null&&audioModels.length>0){
							$("#audioUrl").text(audioModels[0].audioPath);
							$("#audioUrlLoadTAG").attr("hidden",false);
						}
					}
				});
}
	

/* function loadBouquetDetail(channelName){
    for(var i=0;i<row-1;i++){
       var tr=$("<tr></tr>");
       tr.appendTo(table);
       for(var j=0;j<column;j++){
    	   if(channelNameList[count]==undefined){
    		  // var td=$("<td></td>"); 
    	   }else{
    		   var td=$("<td>"+channelNameList[count]+"</td>");
    	   }
          
          td.appendTo(tr);
          count++;
       }
    }
    //trend.appendTo(table);
    $("#entryDetail").append("</table>");
} */

</script>
<div align="left" style="margin-top: 10px;top: 50px;">
	<table class="tableForm" id="tableForm"  style="width: 100%;align-self: inherit;float: left;">
			<tr align="left"> 
				<th>内容提供商：</th>
				<td><span id="appName"></span></td>
			</tr>
			<tr> 
				<th>ID：</th>
				<td><span id="bookId"></span></td>
			</tr>
			<tr>
				<th >标题：</th>
				<td><span id="title"></span></td>
			</tr>
			<tr>
				<th >英文简拼：</th>
				<td><span id="shortName"  ></span></td>
			</tr>
			<tr>
				<th >封面海报：</th>
				<td><span id="coverImage" hidden="true"><img id="coverImageUrl" alt="" src="" width="100px" height="100px"></span></td>
				<td id="coverImageUrlTAG" hidden="true"><a href="javascript:window.downloadFile('coverImage')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-download'">下载<a></td>
			</tr>
			<tr>
				<th >缩略图：</th>
				<td><span id="coverThumbNail" hidden="true"><img id="coverThumbNailUrl" alt="" src="" width="100px" height="100px"></span></td>
				<td id="coverThumbNailUrlTAG" hidden="true"><a href="javascript:window.downloadFile('coverThumbNail')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-download'" >下载<a></td>
			</tr>
			<tr>
				<th>语种：</th>
				<td><span id="langName" ></span></td>
			</tr>
			<tr>
				<th >作者：</th>
				<td><span id="author"  ></span></td>
			</tr>
			<tr>
				<th >出版机构：</th>
				<td><span id="pubOrg"  ></span></td>
			</tr>
			<tr>
				<th >编者：</th>
				<td><span id="editor"  ></span></td>
			</tr>
			<tr>
				<th >审核状态：</th>
				<td><span id="auditStatus" ></span></td>
			</tr>
			<tr>
				<th >原著作者：</th>
				<td><span id="originalAuthor"  ></span></td>
			</tr>
			<tr>
				<th>年代：</th>
				<td><span id="yearsId" ></span></td>
			</tr>
			<tr>
				<th>图书编码：</th>
				<td><span id="global_guid" ></span></td>
			</tr>
			<tr>
				<th>页数：</th>
				<td><span id="pageCount" ></span></td>
			</tr>
			<tr>
				<th>高度：</th>
				<td><span id="height" ></span></td>
			</tr>
			<tr>
				<th>宽度：</th>
				<td><span id="width" ></span></td>
			</tr>
			<tr>
				<th>格式：</th>
				<td><span id="formatType" ></span></td>
			</tr>
			
			<tr>
				<th>编辑类型：</th>
				<td><span id="editionType" ></span></td>
			</tr>
			<tr>
				<th>图书编码：</th>
				<td><span id="global_guid" ></span></td>
			</tr>
			<tr>
				<th>获奖情况：</th>
				<td><span id="isPrize" ></span></td>
			</tr>
			<tr> 
				<th>系列：</th>
				<td><span id="entryTypeName"  ></span></td>
			</tr>
			<tr> 
				<th>分类：</th>
				<td><span id="nodeNames" ></span></td>
			</tr>
			<tr> 
				<th>图书地址：</th>
				<td><span id="contentUrl"></span></td>
				<td id="contentUrlLoadTAG" hidden="true"><a href="javascript:window.downloadFile($('#contentUrl').text())" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-download'">下载<a></td>
			</tr>
			<tr> 
				<th>音频地址：</th>
				<td><span id="audioUrl"></span></td> 
				<td id="audioUrlLoadTAG" hidden="true"><a href="javascript:window.downloadFile($('#audioUrl').text())" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-download'">下载<a></td>
			</tr>
			<tr> 
				<th>简介：</th>
				<td style="width: 300;height: 100"><span id="desc"></span></td>
			</tr>
			<tr> 
				<th>添加时间:</th>
				<td><span id="addTime" ></span></td>
			</tr>
			<tr> 
				<th>最新更新时间:</th>
				<td><span id="modifyTime" ></span></td>
			</tr>
			<tr> 
				<th>上传管理员:</th>
				<td><span id="operateUserName" ></span></td>
			</tr>
			<tr> 
				<th>浏览量:</th>
				<td><span id="browseCount" ></span></td>
			</tr>
			<tr> 
				<th>下载量:</th>
				<td><span id="downloadCount" ></span></td>
			</tr>
					
	</table>
</div>
<div align="center" style="margin-top: 10px;" id="entryDetail"></div>
