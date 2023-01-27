
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="lang" uri="/WEB-INF/tld/locale.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title><lang:Locale value="page.login.title"/></title>
    <link rel="stylesheet" type="text/css" href="../../styles/login.css">
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon">
</head>

<body>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<c:if test="${not empty sessionScope.wrongData}">
    <div class="alertError">
        <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
        <strong>Помилка: </strong>
        <text>${sessionScope.wrongData} </text>
    </div>
    <c:remove var="wrongData" scope="session"/>
</c:if>
<div id="container">
    <form action="controller" method="post">
        <input type="hidden" name="command" value="registerCommand">
        <div id="form-input">
            <input type="text" name="username"
                   placeholder="<lang:Locale value="page.login.enter"/> <lang:Locale value="page.login.username"/>"
                   required>
        </div>
        <div class="form-input">
            <input type="password" name="password"
                   placeholder="<lang:Locale value="page.login.enter"/> <lang:Locale value="page.login.password"/>"
                   required>
        </div>
        <div class="form-input">
            <input type="text" name="lastName"
                   placeholder="<lang:Locale value="page.login.enter"/> last name"
                   required>
        </div>
        <div class="form-input">
            <input type="text" name="firstName"
                   placeholder="<lang:Locale value="page.login.enter"/> first name"
                   required>
        </div>
        <div class="form-input">
            <input type="text" name="middleName"
                   placeholder="<lang:Locale value="page.login.enter"/> middle name"
                   required>
        </div>
        <div>
            <input type="radio" id="contactChoice1"
                   name="role" value="1" checked="checked">
            <label for="contactChoice1">Student</label>

            <input type="radio" id="contactChoice2"
                   name="role" value="2">
            <label for="contactChoice2">Teacher</label>
        </div>

        <button type="submit" id="login">Register</button>
    </form>
    <form action="controller" method="get">
        <input type="hidden" name="command" value="getLoginCommand">
        <button type="submit" id="noAcc">I have an account</button>
    </form>
</div>
</body>
</html>