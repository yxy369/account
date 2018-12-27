<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="net.jpanda.mvntest.account.service.*,
		org.springframework.context.ApplicationContext,
		org.springframework.web.context.support.WebApplicationContextUtils" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<%
ApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(getServletContext());
AccountService accountService=(AccountService)context.getBean("accountService");
String captchaKey=accountService.generateCaptchaKey();
%>
<div class="text-field">
<h2>注册新账户</h2>
<form name="signup" action="signup" method="post">
	<label>账户ID:</label><input type="text" name="id"></input><br/>
	<label>Email:</label><input type="text" name="email"></input><br/>
	<label>显示名称:</label><input type="text" name="name"></input><br/>
	<label>密码:</label><input type="password" name="password"></input><br/>
	<label>确认密码:</label><input type="password" name="confirm_password"></input><br/>
	<label>验证码:</label><input type="text" name="captcha_value"></input><br/>
	<input type="hidden" name="captcha_key" value="<%=captchaKey %>"/>
	<img src="<% =request.getContextPath() %>/captcha_image?key=<% =captchaKey %>"/>
	</br>
	<button>确认并提交</button>
</form>
</div>
</body>
</html>