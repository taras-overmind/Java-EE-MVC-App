<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="my" uri="/WEB-INF/tld/locale.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html lang="en">
<head>
    <title>UsersPage</title>
    <link rel="stylesheet" type="text/css" href="../../styles/courses.css">
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon">
</head>

<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="container-fluid bs-const">
    <div class="col-lg-2 info">
        <a href="?command=getUsersPageCommand&table=1"><br>Students</a><br>
        <a href="?command=getUsersPageCommand&table=2"><br>Lecturers</a><br>
    </div>

    <div class="col-lg-6">
        <div class="row">
            <div class="table-responsive">

                <c:choose>
                    <c:when test="${param.table=='2'}">
                        <table class="table table-bordered table-striped">
                            <tr>
                                <th class="info">
                                    <button name="sort" class="sortRow" value="surname">Lecturer</button>
                                </th>
                                <th class="info">
                                    <button name="sort" class="sortRow" value="mark">State</button>
                                </th>

                            </tr>
                            <c:forEach items="${sessionScope.result2}" var="row">
                                <tr>
                                    <form action="controller" method="post">
                                        <input type="hidden" name="command" value="changeStateCommand">
                                        <input type="hidden" name="id" value="${row.id_user}">
                                        <input type="hidden" name="name_state" value="${row.name_state}">
                                        <input type="hidden" name=url value="${requestScope['javax.servlet.forward.query_string']}">
                                        <td>${row.name}</td>
                                        <td>${row.name_state} </td>
                                        <td>
                                            <button type="submit" class="btn btn-success">Change state</button>
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
                                    <button name="sort" class="sortRow" value="surname">Student</button>
                                </th>
                                <th class="info">
                                    <button name="sort" class="sortRow" value="mark">State</button>
                                </th>

                            </tr>
                            <c:forEach items="${sessionScope.result1}" var="row">
                                <tr>
                                    <form action="controller" method="post">
                                        <input type="hidden" name="command" value="changeStateCommand">
                                        <input type="hidden" name="id" value="${row.id_user}">
                                        <input type="hidden" name="name_state" value="${row.name_state}">
                                        <input type="hidden" name=url value="${requestScope['javax.servlet.forward.query_string']}">
                                        <td>${row.name}</td>
                                        <td>${row.name_state} </td>
                                        <td>
                                            <button type="submit" class="btn btn-success">Change state</button>
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