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
        <strong></strong>
        <text>${sessionScope.wrongData} </text>
    </div>
    <c:remove var="wrongData" scope="session"/>
</c:if>
<c:if test="${not empty sessionScope.registerSuccess}">
    <div class="alertError">
        <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
        <strong></strong>
        <text>${sessionScope.registerSuccess} </text>
    </div>
    <c:remove var="registerSuccess" scope="session"/>
</c:if>
<div id="container">
    <%--    <img src="../../img/man.png">--%>
    <form action="controller" method="post">
        <input type="hidden" name="command" value="loginCommand">
        <div class="form-input">
            <input type="text" name="username"
                   placeholder="<lang:Locale value="page.login.enter"/> <lang:Locale value="page.login.username"/>"
                   required>
        </div>
        <div class="form-input">
            <input type="password" name="password"
                   placeholder="<lang:Locale value="page.login.enter"/> <lang:Locale value="page.login.password"/>"
                   required>
        </div>
        <button type="submit" id="login"><lang:Locale value="page.login.login"/></button>
    </form>
    <form action="controller" method="get">
        <input type="hidden" name="command" value="getRegisterCommand">      <!--TODO-->
        <button type="submit" id="noAcc"><lang:Locale value="page.login.registration"/></button>
    </form>
</div>
</body>
</html>