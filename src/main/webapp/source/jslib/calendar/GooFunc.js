///*本系列框架中,一些用得上的小功能函数,一些UI必须使用到它们,用户也可以单独拿出来用*/

//获取一个DIV的绝对坐标的功能函数,即使是非绝对定位,一样能获取到
function getElCoordinate(dom) {
  var t = dom.offsetTop;
  var l = dom.offsetLeft;
  dom=dom.offsetParent;
  while (dom) {
    t += dom.offsetTop;
    l += dom.offsetLeft;
	dom=dom.offsetParent;
  }; return {
    top: t,
    left: l
  };
}
//兼容各种浏览器的,获取鼠标真实位置
function mousePosition(ev){
	if(!ev) ev=window.event;
    if(ev.pageX || ev.pageY){
        return {x:ev.pageX, y:ev.pageY};
    }
    return {
        x:ev.clientX + document.documentElement.scrollLeft - document.body.clientLeft,
        y:ev.clientY + document.documentElement.scrollTop  - document.body.clientTop
    };
}
//给DATE类添加一个格式化输出字串的方法
Date.prototype.format = function(format)   
{   
   var o = {   
      "M+" : this.getMonth()+1, //month  
      "d+" : this.getDate(),    //day  
      "h+" : this.getHours(),   //hour  
      "m+" : this.getMinutes(), //minute  
      "s+" : this.getSeconds(), //second  ‘
	  //quarter  
      "q+" : Math.floor((this.getMonth()+3)/3), 
      "S" : this.getMilliseconds() //millisecond  
   }   
   if(/(y+)/.test(format)) format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));   
    for(var k in o)if(new RegExp("("+ k +")").test(format))   
      format = format.replace(RegExp.$1,   
        RegExp.$1.length==1 ? o[k] :    
          ("00"+ o[k]).substr((""+ o[k]).length));   
    return format;   
 }   ///*本系列框架中,一些用得上的小功能函数,一些UI必须使用到它们,用户也可以单独拿出来用*/

//获取一个DIV的绝对坐标的功能函数,即使是非绝对定位,一样能获取到
function getElCoordinate(dom) {
  var t = dom.offsetTop;
  var l = dom.offsetLeft;
  dom=dom.offsetParent;
  while (dom) {
    t += dom.offsetTop;
    l += dom.offsetLeft;
	dom=dom.offsetParent;
  }; return {
    top: t,
    left: l
  };
}
//兼容各种浏览器的,获取鼠标真实位置
function mousePosition(ev){
	if(!ev) ev=window.event;
    if(ev.pageX || ev.pageY){
        return {x:ev.pageX, y:ev.pageY};
    }
    return {
        x:ev.clientX + document.documentElement.scrollLeft - document.body.clientLeft,
        y:ev.clientY + document.documentElement.scrollTop  - document.body.clientTop
    };
}
//给DATE类添加一个格式化输出字串的方法
Date.prototype.format = function(format)   
{   
   var o = {   
      "M+" : this.getMonth()+1, //month  
      "d+" : this.getDate(),    //day  
      "h+" : this.getHours(),   //hour  
      "m+" : this.getMinutes(), //minute  
      "s+" : this.getSeconds(), //second  ‘
	  //quarter  
      "q+" : Math.floor((this.getMonth()+3)/3), 
      "S" : this.getMilliseconds() //millisecond  
   }   
   if(/(y+)/.test(format)) format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));   
    for(var k in o)if(new RegExp("("+ k +")").test(format))   
      format = format.replace(RegExp.$1,   
        RegExp.$1.length==1 ? o[k] :    
          ("00"+ o[k]).substr((""+ o[k]).length));   
    return format;   
 }  
 
  function toTime() {
		var strDiv= '<div id="hour" class="hour_">'+ 
		'<table width="100%" height="100%" cellpadding="0" cellspacing="0">'+
		'<tr><td>01</td><td>02</td><td>03</td><td>04</td><td>05</td><td>06</td></tr>'+
		'<tr><td>07</td><td>08</td><td>09</td><td>10</td><td>11</td><td>12</td></tr>'+
		'<tr><td>13</td><td>14</td><td>15</td><td>16</td><td>17</td><td>18</td></tr>'+
		'<tr><td>19</td><td>20</td><td>21</td><td>22</td><td>23</td><td>24</td></tr>'+
		'</table>'+
		'</div>'+
		'<div id="minute" class="minute_">'+
		'<table width="100%" height="50%" cellpadding="0" cellspacing="0"> '+
		'<tr><td>00</td><td>05</td><td>10</td><td>15</td><td>20</td><td>25</td></tr>'+
		'<tr><td>30</td><td>35</td><td>40</td><td>45</td><td>50</td><td>55</td></tr>'+
		'</table>'+
		'</div>';
		$(strDiv).appendTo(document.body);
		$("#hour").addClass("hour_"); 
		$("#minute").addClass("minute_"); 
		$("#hour td,#minute td").mousemove(function(){ $(this).css("backgroundColor","#b0e3fe")});
		$("#hour td,#minute td").mouseout(function(){$(this).css("backgroundColor","#fff")});
		$("#hour td,#minute td").mousedown(function(){objText.value = $(this).text();});
			var objText=null;
			$(".hour").click(function(e){
				   objText = this;
				  	var locate=getElCoordinate(this);
						locate.top+=$(this).attr("offsetHeight");
						$("#hour").css({top:locate.top+"px",left:locate.left+"px"});
				   $("#hour").show(300);
			});
			$(".hour").focusout(function(){$("#hour").hide(300);});
			$(".minute").click(function(e){
			 	 	 objText = this;
		  		 var locate=getElCoordinate(this);
						locate.top+=$(this).attr("offsetHeight");
						$("#minute").css({top:locate.top+"px",left:locate.left+"px"});
			  	 $("#minute").show(300);
    	});
      $(".minute").focusout(function(){$("#minute").hide(300);});
 
    } 
 
 

