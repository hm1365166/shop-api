<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>welcome files store</title>
    <meta name="description" content="particles.js is a lightweight JavaScript library for creating particles.">
    <meta name="author" content="Vincent Garreau"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" media="screen" href="/common/css/login_background.css">
    <link rel="stylesheet" media="screen" href="/common/css/middle.css">
</head>
<body class="div1" id="particles-js">
<div style="margin:0 auto;text-align:center">
    <div class="div2" style="color:white;">
            <form action="/_toLogin" method="post" style="height: 200px;width: 1000px">
                <td>Account：</td>
                <input type="text" name="userName"><br>
                <td>PassWord：</td>
                <input type="password" name="userPass"><br>
                <input type="submit" value="login...">
                <td>${rt.errorMessage}</td>
            </form>
    </div>
</div>
<!-- scripts -->
<script src="/common/js/login_background.js"></script>
<script src="/common/js/login_background_conf.js"></script>
</body>
</html>
