<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML >
<html>
<head>
<script type="text/javascript">
	function toLogin(){
		window.top.location.href="${pageContext.request.contextPath}/login.jsp";
	}
</script>
<body onload="toLogin();">
</body>
</html>
