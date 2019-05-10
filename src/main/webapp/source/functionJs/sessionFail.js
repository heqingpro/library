function checkSession(json){
	if(json=="sessionFail")
	{
	    msgShow('系统提示', "登录超时,请重新登录!", 'warning',function(){ window.location = "login.jsp"});
	}
} 
