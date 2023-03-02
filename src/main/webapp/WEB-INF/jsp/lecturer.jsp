<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="lang" uri="/WEB-INF/tld/locale.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html lang="en">
<head>
    <title><lang:Locale value="page.lecturer.title"/></title>
    <link rel="stylesheet" type="text/css" href="../../styles/courses.css">
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon">
</head>

<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<c:if test="${not empty sessionScope.wrongData}">
    <div class="alertError">
        <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
        <strong>Error: </strong>
        <text>${sessionScope.wrongData} </text>
    </div>
    <c:remove var="wrongData" scope="session"/>
</c:if>

<div class="container-fluid bs-const">
    <div class="col-lg-2 info">
        <a class="nav-link" href="?command=getLecturerCommand&table=1"><br>Students without marks</a><br>
        <a class="nav-link" href="?command=getLecturerCommand&table=2"><br>Students with marks</a><br>
    </div>

    <div class="col-lg-9">
        <div class="row">
            <c:choose>
                <c:when test="${param.table=='2'}">
                    <div class="panel-heading"><br><br><br>You rated these students</div>
                </c:when>
                <c:otherwise>
                    <div class="panel-heading"><br><br><br>You didn't rate these students</div>
                </c:otherwise>
            </c:choose>

            <div class="table-responsive">

                <c:choose>
                    <c:when test="${param.table=='2'}">
                        <table class="table table-bordered table-striped">
                            <tr>
                                <th class="info">
                                    <button name="sort" class="sortRow" value="name_course"><lang:Locale
                                            value="page.people.course.name"/></button>
                                </th>
                                <th class="info">
                                    <button name="sort" class="sortRow" value="surname">Student</button>
                                </th>
                                <th class="info">
                                    <button name="sort" class="sortRow" value="mark"><lang:Locale
                                            value="page.student.mark"/></button>
                                </th>

                            </tr>
                            <c:forEach items="${sessionScope.result2}" var="row">
                                <tr>
                                    <form action="controller" method="post">
                                        <input type="hidden" name="command" value="markStudentCommand">
                                        <input type="hidden" name="id" value="${row.id}">
                                        <input type="hidden" name="mark" value="update">
                                        <td>${row.courseName}</td>
                                        <td>${row.studentName} </td>
                                        <td>
                                            <input type="text" name="new_mark"
                                                   placeholder="${row.mark}"
                                                   required>
                                        </td>
                                        <td>
                                            <button type="submit" class="btn btn-success">Update mark</button>
                                        </td>
                                    </form>
                                </tr>
                            </c:forEach>
                            <br>
                        </table>
                    </c:when>
                    <c:otherwise>

                        <table class="table table-bordered table-striped">
                            <tr>
                                <th class="info">
                                    <button name="sort" class="sortRow" value="name_course"><lang:Locale
                                            value="page.people.course.name"/></button>
                                </th>
                                <th class="info">
                                    <button name="sort" class="sortRow" value="surname">Student</button>
                                </th>
                                <th class="info">
                                    <button name="sort" class="sortRow" value="mark"><lang:Locale
                                            value="page.student.mark"/></button>
                                </th>

                            </tr>
                            <c:forEach items="${sessionScope.result1}" var="row">
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
                                        <td>
                                            <button type="submit" class="btn btn-success">Send mark</button>
                                        </td>
                                    </form>
                                </tr>
                            </c:forEach>
                            <br>
                        </table>
                    </c:otherwise>
                </c:choose>

            </div>
        </div>

    </div>
</div>
</body>
</html>