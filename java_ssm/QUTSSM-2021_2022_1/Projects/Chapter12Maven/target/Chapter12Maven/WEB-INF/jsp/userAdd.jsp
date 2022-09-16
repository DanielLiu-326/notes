<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath }/user/save">
    <fieldset>
        <legend>添加一个用户</legend>
        <p>
            <label>用户名:</label>
            <input name="userName" type="text" value="${user.userName}"/>
        </p>
        <p>
            <label>爱好:</label>
            <c:forEach items="${hobbys}" var="hobby" varStatus="status">
                <input type="checkbox" name="hobby" value="${hobby.key}"/>${hobby.value}
            </c:forEach>
        </p>
        <p>
            <label>朋友:</label>
            <input type="checkbox" name="friends" value="张三"/>张三
            <input type="checkbox" name="friends" value="李四"/>李四
            <input type="checkbox" name="friends" value="王五"/>王五
            <input type="checkbox" name="friends" value="赵六"/>赵六
        </p>
        <p>
            <label>职业:</label>
            <select name="carrer">请选择职业
                <c:forEach items="${carrers}" var="carrer" varStatus="status">
                    <option name="carrers" value="${carrer}"> ${carrer}</option>
                </c:forEach>
            </select>
        </p>
        <p>
            <label>户籍:</label>
            <select name="houseRegister">请选择户籍
                <c:forEach items="${houseRegisters}" var="house" varStatus="status">
                    <option name="houseRegisters" value="${house}"> ${house}</option>
                </c:forEach>
            </select>
        </p>
        <p>
            <label>个人描述:</label>
            <input type="textarea" name="remark" rows="5"/>
        </p>
        <p id="buttons">
            <input id="reset" type="reset">
            <input id="submit" type="submit" value="添加">
        </p>
    </fieldset>
</form>
</body>
</html>
