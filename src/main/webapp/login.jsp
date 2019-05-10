<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML >
<html>
<head>
<title>欢迎登录图书管理系统|Welcome to login the library management system</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/source/images/favicon.ico">

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/source/login/reset.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/source/login/login.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/source/jslib/ztree/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/source/login/login.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/source/login/canvas-particle.js"></script>


<script type="text/javascript">
	window.onload = function() {
		var config = {
			vx : 4,
			vy : 4,
			height : 2,
			width : 2,
			count : 100,
			color : "121, 162, 185",
			stroke : "100, 200, 180",
			dist : 6000,
			e_dist : 20000,
			max_conn : 10
		}
		CanvasParticle(config);
	}
</script>
</head>

<body>
	<div class="page">
		<div class="loginwarrp">
			<div class="logo">欢迎登陆图书管理系统</div>
			<div class="login_form">
				<form id="user_login_form" name="Login" method="post"
					action="user_login">
					<li class="login-item"><span>用户名：</span>
						<input type="text" id="username" name="loginUserName" class="login_input">
						<span id="username-msg" class="error">${login_error}</span>
					</li>
					<li class="login-item">
						<span>密&emsp;码：</span>
						<input type="password" id="password" name="loginPassword" class="login_input">
						<span id="password-msg" class="error">${login_error}</span>
					</li>
					<li class="login-sub">
						<input onclick="doLogin()" type="button" name="Submit" value="登  录" style="margin-right:30px; cursor: pointer;" />
						<input type="reset" name="Reset" value="重  置" style="margin-left:30px; cursor: pointer;" />
					</li>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
