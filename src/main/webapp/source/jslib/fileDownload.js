
//--------------------------兼容各种浏览器下载文件，浏览器下载文件 start-----------------------------
window.downloadFile = function (tagId) {
	if(tagId.indexOf("http")==-1){
		var sUrl = $("#"+tagId).find("img").attr("src");	
	}else{
		var sUrl = tagId;
	}
	
    //iOS devices do not support downloading. We have to inform user about this.
    if (/(iP)/g.test(navigator.userAgent)) {
        alert('Your device does not support files downloading. Please try again in desktop browser.');
        return false;
    }

    //If in Chrome or Safari - download via virtual link click
    if (window.downloadFile.isChrome || window.downloadFile.isSafari) {
        //Creating new link node.
        var link = document.createElement('a');
        link.href = sUrl;

        if (link.download !== undefined) {
            //Set HTML5 download attribute. This will prevent file from opening if supported.
            var fileName = sUrl.substring(sUrl.lastIndexOf('/') + 1, sUrl.length);
            link.download = fileName;
        }

        //Dispatching click event.
        if (document.createEvent) {
            var e = document.createEvent('MouseEvents');
            e.initEvent('click', true, true);
            link.dispatchEvent(e);
            return true;
        }
    }

    // Force file download (whether supported by server).
    if (sUrl.indexOf('?') === -1) {
        sUrl += '?download';
    }

    window.open(sUrl, '_self');
    return true;
}

window.downloadFile.isChrome = navigator.userAgent.toLowerCase().indexOf('chrome') > -1;
window.downloadFile.isSafari = navigator.userAgent.toLowerCase().indexOf('safari') > -1;

//--------------------------浏览器下载文件 end-----------------------------

//function downloadImage(imageUrl,imageName){
//	$.post("downloadImage.action",{'imageUrl':imageUrl,"imageName":imageName},function(result){
//		var resultCode=result.resultCode;
//  	     var msg=result.msg;
//  	     if(resultCode=="success"){
//			location.href=result.msg;
//		 }else{
//			 msgShow('系统提示', msg, 'error');
//		 }
//	});
//}
