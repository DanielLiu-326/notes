<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
    <title>My JSP 'index.jsp' starting page</title>
  </head>
  <body>
    没注册的用户(Bean)，请<a href="index/registerBean">注册</a>！<br>
    没注册的用户(含兴趣)，请<a href="${pageContext.request.contextPath }/index/register">注册</a>！<br>
    已注册的用户，去<a href="${pageContext.request.contextPath }/index/login">登录</a>！<br>
    已注册的用户，去<a href="${pageContext.request.contextPath }/index/multiUser">多用户修改</a>！
  </body>
</html>
