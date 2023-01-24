<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="my" uri="/WEB-INF/tld/locale.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Create course</title>
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
        <input type="hidden" name="command" value="createCourseCommand">
        <div id="form-input">
            <input type="text" name="name_course"
                   placeholder="<my:Locale value="page.login.enter"/> <my:Locale value="page.admin.create.namecourse"/>"
                   required>
        </div>
        <div class="form-input">
            <input type="text" name="duration"
                   placeholder="<my:Locale value="page.login.enter"/> <my:Locale value="page.admin.create.duration"/>"
                   required>
        </div>
        <div class="form-input">
            <input type="text" name="name_theme"
                   placeholder="<my:Locale value="page.login.enter"/> <my:Locale value="page.admin.create.nametheme"/>"
                   required>
        </div>
        <div class="form-input">
            <select class="lecturerSelect" name="idLecturer" required>
                <c:forEach items="${sessionScope.lecturers}" var="lecturer">
                    <option
                            value="${lecturer.id}">${lecturer.surname} ${lecturer.name} ${lecturer.patronymic}
                    </option>
                </c:forEach>
            </select>
        </div>


        <button type="submit" id="create">Create course</button>
    </form>
</div>
</body>
</html>