<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="lang" uri="/WEB-INF/tld/locale.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Update course</title>
    <link rel="stylesheet" type="text/css" href="../../styles/login.css">
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon">
</head>

<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
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
        <input type="hidden" name="command" value="editCourseCommand">
        <input type="hidden" name="id_course" value="${param.id_course}">
        <input type="hidden" name=url value="${requestScope['javax.servlet.forward.query_string']}">
        <div id="form-input">
            <label class="field_label" for="courseName">Course name: </label>
            <input id="courseName" type="text" name="name_course" value="${param.name_course}"
                   required>
        </div>
        <div class="form-input">
            <label class="field_label" for="duration">Duration: </label>
            <input id="duration" type="text" name="duration" value="${param.duration}"
                   required>
        </div>
        <div class="form-input">
            <label class="field_label" for="name_theme">Theme: </label>
            <input id="name_theme" type="text" name="name_theme" value="${param.name_theme}"
                   required>
        </div>
        <div class="form-input">
            <label class="field_label" for="lecturer">Lecturer: </label>
            <select id="lecturer" class="editSelect" name="idLecturer" required>
                <c:forEach items="${sessionScope.lecturers}" var="lecturer">
                    <option
                            <c:set var="name_lecturer"
                                   value="${lecturer.surname} ${lecturer.name} ${lecturer.patronymic}"/>
                            value="${lecturer.id}"
                            <c:if test="${param.lecturerName eq name_lecturer}">
                                selected
                            </c:if>
                    >${lecturer.surname} ${lecturer.name} ${lecturer.patronymic}

                    </option>
                </c:forEach>
            </select>

        </div>
        <div class="form-input">
            <label class="field_label" for="status">Status: </label>
            <select id="status" class="editSelect" name="idStatus" required>
                <c:forEach items="${sessionScope.statuses}" var="status">
                    <option
                            value="${status.id_status}"
                            <c:if test="${param.name_status eq status.name_status}">
                                selected
                            </c:if>
                    >${status.name_status}
                    </option>
                </c:forEach>
            </select>
        </div>


        <button type="submit" id="create">Edit course</button>
    </form>
</div>
</body>
</html>