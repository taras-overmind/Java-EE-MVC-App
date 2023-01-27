<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="my" uri="/WEB-INF/tld/locale.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title><my:Locale value="page.courses.title"/></title>
    <link rel="stylesheet" type="text/css" href="../../styles/courses.css">
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon">
</head>

<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="col-lg-10">
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
            </form>
            <table class="table table-bordered table-striped">
                <tr>
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="courseCommand">
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
                        <th class="info">
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
                    </form>

                    <c:forEach items="${sessionScope.result}" var="row">
                <tr>
                    <td>${row.courseName}</td>
                    <td>${row.duration}</td>
                    <td>${row.themeName}</td>
                    <td>${row.lecturerName}</td>
                    <td>${row.statusName}</td>
                    <td>${row.count}</td>
                    <td>
                        <form action="controller" method="get">
                            <input type="hidden" name="command" value="getEditCourseCommand">
                            <input type="hidden" name="id_course" value="${row.courseId}">
                            <input type="hidden" name="name_course" value="${row.courseName}">
                            <input type="hidden" name="duration" value="${row.duration}">
                            <input type="hidden" name="name_theme" value="${row.themeName}">
                            <input type="hidden" name="lecturerName" value="${row.lecturerName}">
                            <input type="hidden" name="name_status" value="${row.statusName}">
                            <button type="submit" class="ed_btn">Edit</button>
                        </form>
                    </td>
                    <td>
                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="deleteCourseCommand">
                            <input type="hidden" name="id_course" value="${row.courseId}">
                            <button type="submit" class="ed_btn">Delete</button>
                        </form>
                    </td>
                </tr>
                </c:forEach>
            </table>

        </div>
    </div>
    <%--For displaying Previous link except for the 1st page --%>


    <%--For displaying Page numbers.
    The when condition does not display a link for the current page--%>
    <table border="1" cellpadding="5" cellspacing="5">

        <tr>
            <c:if test="${sessionScope.currentPage != 1}">
                <td><a href="?command=getCoursesCommand&page=${sessionScope.currentPage - 1}">Previous</a></td>
            </c:if>
            <c:forEach begin="1" end="${sessionScope.noOfPages}" var="i">
                <c:choose>
                    <c:when test="${sessionScope.currentPage eq i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="?command=getCoursesCommand&page=${i}">${i}</a></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${sessionScope.currentPage lt sessionScope.noOfPages}">
                <td><a href="?command=getCoursesCommand&page=${sessionScope.currentPage + 1}">Next</a></td>
            </c:if>
        </tr>
    </table>

    <%--For displaying Next link --%>


</div>
</body>
</html>