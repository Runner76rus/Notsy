<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>notsy</title>
</head>
<body>
<h1><%= "NOTSY" %>
</h1>
<h3>Система уведомлений</h3>
<br/>
<a href="${pageContext.request.contextPath}/message.jsp">Создать уведомление</a>
<a href="registration.jsp">Зарегистрироваться</a>
<a href="login.jsp">Войти</a>
</body>
</html>