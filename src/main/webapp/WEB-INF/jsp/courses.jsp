<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="my" uri="/WEB-INF/tld/locale.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" uri="/WEB-INF/tld/tables.tld" %>

<html>

<head>
    <title><my:Locale value="page.courses.title"/></title>
    <link rel="stylesheet" type="text/css" href="../../styles/courses.css">
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon">
</head>

<body>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<div class="col-lg-9">
    <div class="panel panel-primary table-responsive">
        <div id="sortContainer">
            <form action="controller" method="post">
                <input type="hidden" name="command" value="courseCommand">
                <label>
                    <select class="sortSelect form-control" name="idTheme">
                        <option selected><my:Locale value="page.courses.all.themes"/></option>
                        <c:forEach items="${sessionScope.themes}" var="theme">
                            <option value="${theme.idTheme}"
                                    <c:if test="${not empty sessionScope.idTheme and sessionScope.idTheme==theme.idTheme}">
                                        selected
                                    </c:if>
                            >${theme.nameTheme}
                            </option>
                        </c:forEach>
                    </select>
                </label>

                <label>
                    <select class="sortSelect form-control" name="idLecturer">
                        <option selected><my:Locale value="page.courses.all.lecturers"/></option>
                        <c:forEach items="${sessionScope.lecturers}" var="lecturer">
                            <option value="${lecturer.id}"
                                    <c:if test="${not empty sessionScope.idLecturer and sessionScope.idLecturer==lecturer.id}">
                                        selected
                                    </c:if>
                            >${lecturer.surname} ${lecturer.name} ${lecturer.patronymic}
                            </option>
                        </c:forEach>
                    </select>
                </label>

                <button type="submit" class="btn btn-success"><my:Locale value="page.courses.choose"/></button>
                <table class="table table-bordered table-striped">
                    <tr>
                        <th class="info">
                            <button name="sort" class="sortRow" value="name_course"><my:Locale
                                    value="page.people.course.name"/></button>
                        </th>
                        <th class="info">
                            <button name="sort" class="sortRow" value="duration"><my:Locale
                                    value="page.people.course.duration"/></button>
                        </th>
                        <th class="info">
                            <button name="sort" class="sortRow" value="name_theme"><my:Locale
                                    value="page.student.theme"/></button>
                        </th>
                        <th  class="info">
                            <button name="sort" class="sortRow" value="surname"><my:Locale
                                    value="page.student.lecturer"/></button>
                        </th>
                        <th class="info">
                            <button name="sort" class="sortRow" value="name_status"><my:Locale
                                    value="page.courses.table.status"/></button>
                        </th>
                        <th class="info">
                            <button name="sort" class="sortRow" value="count"><my:Locale
                                    value="page.courses.table.count"/></button>
                        </th>
                    </tr>
<%--                    <t:courses/>--%>

                    <c:forEach items="${sessionScope.result}" var="row">
                        <tr>
                            <td>${row.courseName}</td>
                            <td>${row.duration}</td>
                            <td>${row.themeName}</td>
                            <td>${row.lecturerName}</td>
                            <td>${row.statusName}</td>
                            <td>${row.count}</td>
                        </tr>
                    </c:forEach>
                </table>
            </form>
        </div>
    </div>
</div>
</body>
</html>