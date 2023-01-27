<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="lang" uri="/WEB-INF/tld/locale.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html lang="en">
<head>
    <title><lang:Locale value="page.student.title"/></title>
    <link rel="stylesheet" type="text/css" href="../../styles/courses.css">
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon">
</head>

<body>
<%@ include file="/WEB-INF/jspf/header.jspf"%>

<div class="container-fluid bs-const">
    <div class="col-lg-2 info">
        <a class="nav-link" href="?command=getStudentCommand&table=1"><br><lang:Locale value="page.student.leftbar.notstartedcourse"/></a><br>
        <a class="nav-link" href="?command=getStudentCommand&table=2"><br><lang:Locale value="page.student.leftbar.begancourses"/></a><br>
        <a class="nav-link" href="?command=getStudentCommand&table=4"><br><lang:Locale value="page.student.table.title.progress"/></a>
    </div>
    <div class="col-lg-9">
        <div class="row">
            <c:choose>
                <c:when test="${param.table=='2'}">
                    <div class="panel-heading"><br><br><br><lang:Locale value="page.student.table.title.inprogress"/></div>
                </c:when>
                <c:when test="${param.table=='4'}">
                    <div class="panel-heading"><br><br><br><lang:Locale value="page.student.table.title.progress"/></div>
                </c:when>
                <c:otherwise>
                    <div class="panel-heading"><br><br><br><lang:Locale value="page.student.table.title.opened"/></div>
                </c:otherwise>
            </c:choose>
            <div class="table-responsive">
                <table class="table table-bordered table-striped">
                    <tr>
                        <th class="info">
                            <button name="sort" class="sortRow" value="name_course"><lang:Locale
                                    value="page.people.course.name"/></button>
                        </th>
                        <th class="info">
                            <button name="sort" class="sortRow" value="duration"><lang:Locale
                                    value="page.people.course.duration"/></button>
                        </th>
                        <th class="info">
                            <button name="sort" class="sortRow" value="name_theme"><lang:Locale
                                    value="page.student.theme"/></button>
                        </th>
                        <th class="info">
                            <button name="sort" class="sortRow" value="surname"><lang:Locale
                                    value="page.student.lecturer"/></button>
                        </th>
                        <c:if test="${param.table=='4'}">
                            <th class="info">
                                <button name="sort" class="sortRow" value="mark"><lang:Locale
                                        value="page.student.mark"/></button>
                            </th>
                        </c:if>

                    </tr>


                    <c:forEach items="${sessionScope.result}" var="row">
                        <tr>
                            <td>${row.courseName}</td>
                            <td>${row.duration}</td>
                            <td>${row.themeName}</td>
                            <td>${row.lecturerName} </td>
                            <c:if test="${param.table=='4'}">
                                <td>${row.count}</td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    <br>
                </table>
            </div>
        </div>

    </div>
</div>
</body>
</html>