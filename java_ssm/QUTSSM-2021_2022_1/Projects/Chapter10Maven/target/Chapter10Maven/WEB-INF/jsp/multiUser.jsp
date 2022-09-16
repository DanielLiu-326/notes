<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>后台登录</title>
	<style type="text/css">
		table{
			text-align: center;
		}
		.textSize{
			width: 120px;
			height: 25px;
		}
		* {
			margin: 0px;
			padding: 0px;
		}
		body {
			font-family: Arial, Helvetica, sans-serif;
			font-size: 12px;
			margin: 10px 10px auto;
			background-image: url(${pageContext.request.contextPath }/images/bb.jpg);
		}
	</style>
	<script type="text/javascript">
		//确定按钮
		function gogo(){
			document.forms[0].submit();
		}
		//取消按钮
		function cancel(){
			document.forms[0].action = "";
		}
	</script>
</head>
<body>
<form action="${pageContext.request.contextPath }/user/multiUserEdit" method="get">
	<table>
		<tr>
			<td>选择</td>
			<td>用户名</td>
		</tr>
		<tr>
			<td><input name="userList[0].id" value="1" type="checkbox"/> </td>
			<td><input name="userList[0].username" value="tome" type="text" /></td>
		</tr>
		<tr>
			<td><input name="userList[1].id" value="1" type="checkbox"/> </td>
			<td><input name="userList[1].username" value="jack" type="text" /></td>
		</tr>
		<tr><td>------------------</td></tr>
		<tr>
			<td><input name="userMap['1'].id" value="1" type="checkbox"/> </td>
			<td><input name="userMap['1'].username" value="tome" type="text" /></td>
		</tr>
		<tr>
			<td><input name="userMap['2'].id" value="2" type="checkbox"/> </td>
			<td><input name="userMap['2'].username" value="jack" type="text" /></td>
		</tr>
	</table>
	<input type="submit" value="修改"/>
</form>
</body>
</html>
