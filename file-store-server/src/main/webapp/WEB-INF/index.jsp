<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>用户登录</title>
        <meta name="description" content="particles.js is a lightweight JavaScript library for creating particles.">
        <meta name="author" content="Vincent Garreau" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <link rel="stylesheet" media="screen" href="../common/css/login_background.css">
    </head>
<body>

<form action="/_toLogin" method="post">
    <input type="text" name="userName">
    <input type="password" name="userPass">
    <input type="submit">
    <td>${rt.errorMessage}</td>
</form>
<div id="particles-js"></div>

<!-- scripts -->
<script src="../common/js/login_background.js"></script>
<script src="../common/js/login_background_conf.js"></script>
</body>
</html>
