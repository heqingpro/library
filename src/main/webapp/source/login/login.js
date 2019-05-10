$(function(){
	if (top.location != self.location) {   
        top.location=self.location;   
    }
	$('#username').blur(checkUsername);
	$('#password').blur(checkPassword);
});

function checkUsername(){
	var name = $('#username').val();
	if(name == null || name == "" || name.trim() == ""){
		//提示错误
		$('#username-msg').html("用户名不能为空！");
		return false;
	} else {
		$('#username-msg').html("");
		return true;
	}
}

function checkPassword(){
	var password = $('#password').val();
	if(password == null || password == "" || password.trim() == ""){
		//提示错误
		$('#password-msg').html("密码亦不能为空！");
		return false;
	} else {
		$('#password-msg').html("");
		return true;
	}
}

function doLogin(){
	if(checkUsername() && checkPassword()){
		$('#user_login_form').submit();
	}
}