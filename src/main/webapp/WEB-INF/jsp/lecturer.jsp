<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="my" uri="/WEB-INF/tld/locale.tld" %>
<%@ taglib prefix="t" uri="/WEB-INF/tld/tables.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html lang="en">
<head>
    <title><my:Locale value="page.lecturer.title"/></title>
    <link rel="stylesheet" type="text/css" href="../../styles/courses.css">
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon">
</head>

<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="container-fluid bs-const">

    <div class="col-lg-9">
        <div class="row">

            <div class="table-responsive">
                <table class="table table-bordered table-striped">
                    <tr>
                        <th class="info">
                            <button name="sort" class="sortRow" value="name_course"><my:Locale
                                    value="page.people.course.name"/></button>
                        </th>
                        <th class="info">
                            <button name="sort" class="sortRow" value="surname">Student</button>
                        </th>
                        <th class="info">
                            <button name="sort" class="sortRow" value="mark"><my:Locale
                                    value="page.student.mark"/></button>
                        </th>

                    </tr>
<%--                    <t:lecturerJournal/>--%>
                    <c:forEach items="${sessionScope.result}" var="row">
                        <tr>
                            <form action="controller" method="post">
                                <input type="hidden" name="command" value="markStudentCommand">
                                <input type="hidden" name="id" value="${row.id}">
                                <input type="hidden" name="mark" value="new">
                                <td>${row.courseName}</td>
                                <td>${row.studentName} </td>
                                <td>
                                <input type="text" name="new_mark"
                                       placeholder=""
                                       required>
                                </td>
                                <td><button type="submit" class="btn btn-success">Send mark</button> </td>
                            </form>
                        </tr>
                    </c:forEach>

                </table>
            </div>
        </div>

    </div>
</div>
</body>
</html>