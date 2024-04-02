<%--
  Created by IntelliJ IDEA.
  User: runner76rus
  Date: 11.03.2024
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Регистрация</h1>
<div>
    <form action="${pageContext.request.contextPath}/user" method="post">
        <div>
            <label for="username">Отображаемое имя</label>
            <input type="text" id="username" name="username" placeholder="Ваш никнейм">
        </div>
        <div>
            <label for="password">Пароль</label>
            <input type="password" id="password" name="password">
        </div>

        <div>
            <label for="first-name">Имя</label>
            <input type="text" id="first-name" name="firstName" placeholder="Ваша имя">
        </div>
        <div>
            <label for="second-name">Фамилия</label>
            <input type="text" id="second-name" name="secondName" placeholder="Ваша фамилия">
        </div>
        <div>
            <label for="email">Email</label>
            <input type="email" id="email" name="email" placeholder="example@mail.ru">
        </div>
        <div>
            <label for="phone-number">Телефон</label>
            <input type="text" id="phone-number" name="phoneNumber" placeholder="+79995556633">
        </div>

        <input type="submit" placeholder="Зарегистрироваться">
    </form>
</div>
</body>
</html>
