function previewVideo(videoId){
	if(getExplorer()!="Chrome"){//��ǰ��������ǹȸ������
		 msgShow('ϵͳ��ʾ', '��ǰ�������֧����ƵԤ�������л����ȸ������Ԥ��', 'warning');
		 return ;
	}
	var videoUrl=$(videoId).val();
	$('#win').window('open');
	$('#win').window('refresh', "videoIframe.action?videoUrl="+videoUrl);  
	//window.parent.addTab("Ԥ����Ƶ","video.action","icon icon-search");

}
function previewVideo_detail(videoId){
	if(getExplorer()!="Chrome"){//��ǰ��������ǹȸ������
		 msgShow('ϵͳ��ʾ', '��ǰ�������֧����ƵԤ�������л����ȸ������Ԥ��', 'warning');
		 return ;
	}
	var videoUrl=$(videoId).html();
	$('#win').window('open');
	$('#win').window('refresh', "videoIframe.action?videoUrl="+videoUrl);  
	//window.parent.addTab("Ԥ����Ƶ","video.action","icon icon-search");
	
}
function getExplorer() {
	var explorer = window.navigator.userAgent ;
	//ie 
	if (explorer.indexOf("MSIE") >= 0) {
	return ("ie");
	}
	//firefox 
	else if (explorer.indexOf("Firefox") >= 0) {
		return("Firefox");
	}
	//Chrome
	else if(explorer.indexOf("Chrome") >= 0){
		return("Chrome");
	}
	//Opera
	else if(explorer.indexOf("Opera") >= 0){
	return("Opera");
	}
	//Safari
	else if(explorer.indexOf("Safari") >= 0){
	return("Safari");
	}
}