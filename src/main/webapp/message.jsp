<%--
  Created by IntelliJ IDEA.
  User: runner76rus
  Date: 11.03.2024
  Time: 14:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>message</title>
</head>
<body>
<h1>Создайте своё сообщение</h1>
<div>
    <form>
        <label for="topic">Тема</label>
        <input type="text" id="topic" name="topic" placeholder="Укажите тему сообщения для email отправлений">
        <label for="text">Сообщение</label>
        <input type="text" id="text" name="text" placeholder="Придумайте ваше сообщение">
        <input type="submit" placeholder="Отправить">
    </form>
</div>
</body>
</html>
