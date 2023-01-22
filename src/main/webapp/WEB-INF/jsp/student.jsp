<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="my" uri="/WEB-INF/tld/locale.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<sql:setDataSource var="db" driver="com.mysql.cj.jdbc.Driver" url="jdbc:mysql://localhost:3306/courses" user="root"
                   password=""/>

<c:choose>
    <c:when test="${param.table=='1'}">
        <sql:query dataSource="${db}"
                   var="result">SELECT DISTINCT NAME_COURSE, DURATION, THEMES.NAME_THEME, LECTURERS.SURNAME, LECTURERS.NAME, LECTURERS.PATRONYMIC FROM STUDENTS INNER JOIN STUDENT_COURSE ON STUDENT_COURSE.ID_STUDENT=STUDENTS.ID INNER JOIN COURSES ON STUDENT_COURSE.ID_COURSE=COURSES.ID_COURSE INNER JOIN STATUSES ON STATUSES.ID_STATUS = COURSES.ID_STATUS INNER JOIN THEMES ON COURSES.ID_THEME = THEMES.ID_THEME INNER JOIN LECTURERS ON COURSES.ID_LECTURER = LECTURERS.ID INNER JOIN USERS ON USERS.ID_USER = STUDENTS.ID_USER WHERE USERS.id_user=${sessionScope.id} AND STATUSES.id_status = 1</sql:query>
    </c:when>
    <c:when test="${param.table=='2'}">
        <sql:query dataSource="${db}"
                   var="result">SELECT DISTINCT NAME_COURSE, DURATION, THEMES.NAME_THEME, LECTURERS.SURNAME, LECTURERS.NAME, LECTURERS.PATRONYMIC FROM STUDENTS INNER JOIN STUDENT_COURSE ON STUDENT_COURSE.ID_STUDENT=STUDENTS.ID INNER JOIN COURSES ON STUDENT_COURSE.ID_COURSE=COURSES.ID_COURSE INNER JOIN STATUSES ON STATUSES.ID_STATUS = COURSES.ID_STATUS INNER JOIN THEMES ON COURSES.ID_THEME = THEMES.ID_THEME INNER JOIN LECTURERS ON COURSES.ID_LECTURER = LECTURERS.ID INNER JOIN USERS ON USERS.ID_USER = STUDENTS.ID_USER WHERE USERS.id_user=${sessionScope.id} AND STATUSES.id_status = 2</sql:query>
    </c:when>
    <c:when test="${param.table=='4'}">
        <sql:query dataSource="${db}"
                   var="result">SELECT DISTINCT NAME_COURSE, DURATION, THEMES.NAME_THEME, LECTURERS.SURNAME, LECTURERS.NAME, LECTURERS.PATRONYMIC, MARK FROM STUDENTS INNER JOIN STUDENT_COURSE ON STUDENTS.ID = STUDENT_COURSE.ID_STUDENT INNER JOIN COURSES ON STUDENT_COURSE.ID_COURSE = COURSES.ID_COURSE INNER JOIN STATUSES ON COURSES.ID_STATUS = STATUSES.ID_STATUS INNER JOIN THEMES ON COURSES.ID_THEME = THEMES.ID_THEME INNER JOIN JOURNAL ON STUDENT_COURSE.ID_STUDENT_COURSE = JOURNAL.ID_STUDENT_COURSE INNER JOIN LECTURERS ON COURSES.ID_LECTURER = LECTURERS.ID INNER JOIN USERS ON STUDENTS.ID_USER = USERS.ID_USER WHERE STATUSES.ID_STATUS = 4 AND USERS.id_user=16</sql:query>
    </c:when>
    <c:otherwise>
        <sql:query dataSource="${db}"
                   var="result">SELECT DISTINCT NAME_COURSE, DURATION, THEMES.NAME_THEME, LECTURERS.SURNAME, LECTURERS.NAME, LECTURERS.PATRONYMIC FROM STUDENTS INNER JOIN STUDENT_COURSE ON STUDENT_COURSE.ID_STUDENT=STUDENTS.ID INNER JOIN COURSES ON STUDENT_COURSE.ID_COURSE=COURSES.ID_COURSE INNER JOIN STATUSES ON STATUSES.ID_STATUS = COURSES.ID_STATUS INNER JOIN THEMES ON COURSES.ID_THEME = THEMES.ID_THEME INNER JOIN LECTURERS ON COURSES.ID_LECTURER = LECTURERS.ID INNER JOIN USERS ON USERS.ID_USER = STUDENTS.ID_USER WHERE USERS.id_user=${sessionScope.id} AND STATUSES.id_status = 1</sql:query>
    </c:otherwise>
</c:choose>



<html lang="en">
<head>
    <c:import url="../jspf/head.jsp"/>
    <meta charset="UTF-8">
    <title><my:Locale value="page.student.title"/></title>
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon">
    <script src="../../bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../../styles/courses.css">
</head>
<body>
<div class="cover-container d-flex w-100 h-10 p-3 mx-auto flex-column">
    <header class="mb-15">
        <div>
            <nav class="nav nav-masthead justify-content-right float-md-end row">
                <div class="left-block col-6">
                    <a class="nav-link" href="/trip">Courses</a>
                    <a class="nav-link" href="/route">My Page</a>
                </div>
                <div class="right-block col-6">
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="languageCommand">
                        <input type="hidden" name="redirect" value="?command=getStudentCommand">
                        <input type="hidden" name="language" value="en">
                        <input type="hidden" name=url value="${requestScope['javax.servlet.forward.query_string']}">
                        <input type="image" src="../../img/us.png" alt="en">
                    </form>
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="languageCommand">
                        <input type="hidden" name="redirect" value="?command=getStudentCommand">
                        <input type="hidden" name="language" value="uk">
                        <input type="hidden" name=url value="${requestScope['javax.servlet.forward.query_string']}">
                        <input type="image" src="../../img/ua.png" alt="ua">
                    </form>
                </div>
            </nav>
        </div>
    </header>
</div>



<div class="container-fluid bs-const">
    <div class="col-lg-2 info">
        <div class="bs-example" data-example-id="simple-nav-stacked">
            <ul class="nav nav-pills nav-stacked nav-pills-stacked-example">
                <form method="post" action="controller">
                    <input type="hidden" name="command" value="loadInformationCommand">
                    <button type="submit" class="btn btn-success profile">Редактировать личную информацию</button>
                </form>
<%--                <form method="post" action="controller">--%>
<%--                    <input type="hidden" name="command" value="getStudentCommand">--%>
<%--                    <input type="hidden" name="table" value="1">--%>
<%--                    <button type="submit" class="btn btn-success profile"><my:Locale value="page.student.leftbar.notstartedcourse"/></button>--%>
<%--                </form>--%>
<%--                <form method="post" action="controller">--%>
<%--                    <input type="hidden" name="command" value="getStudentCommand">--%>
<%--                    <input type="hidden" name="table" value="2">--%>
<%--                    <button type="submit" class="btn btn-success profile"><my:Locale value="page.student.leftbar.begancourses"/></button>--%>
<%--                </form>--%>
<%--                <form method="post" action="controller">--%>
<%--                    <input type="hidden" name="command" value="getStudentCommand">--%>
<%--                    <input type="hidden" name="table" value="4">--%>
<%--                    <button type="submit" class="btn btn-success profile"><my:Locale value="page.student.table.title.progress"/></button>--%>
<%--                </form>--%>
                <a href="?command=getStudentCommand&table=1"><my:Locale value="page.student.leftbar.notstartedcourse"/></a>
                <a href="?command=getStudentCommand&table=2"><my:Locale value="page.student.leftbar.begancourses"/></a>
                <a href="?command=getStudentCommand&table=4"><my:Locale value="page.student.table.title.progress"/></a>
<%--                <li role="presentation">--%>
<%--                    <a href="#dontstartcourses"><my:Locale value="page.student.leftbar.notstartedcourse"/></a>--%>
<%--                </li>--%>
<%--                <li role="presentation">--%>
<%--                    <a href="#begancourses"><my:Locale value="page.student.leftbar.begancourses"/></a>--%>
<%--                </li>--%>
            </ul>
        </div>
    </div>
    <div class="col-lg-9">
        <a name="courses"></a>
        <div class="row">
            <div class="cont">
                <c:forEach items="${coursesForUser}" var="courseForUser">
                    <div class="col-lg-3">
                        <div class="thumbnail">

                            <div class="caption">
                                <form method="post">
                                    <input type="hidden" name="command" value="registerOnCourseCommand">
                                    <input type="hidden" name="idCourse" value="${courseForUser.idCourse}">
                                    <h3>${courseForUser.nameCourse}</h3>
                                    <p><my:Locale value="page.student.duration"/>: ${courseForUser.duration} <my:Locale
                                            value="page.student.week"/></p>
                                    <p>
                                        <a onclick="$(this).closest('form').submit()" href="#" class="btn btn-success"
                                           role="button"><my:Locale value="page.student.register"/></a>
                                    </p>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="row">
            <a name="dontstartcourses"></a>
            <div class="panel panel-primary">
                <div class="panel-heading"><my:Locale value="page.student.table.title.opened"/></div>
                <div class="table-responsive">
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
                            <c:if test="${param.table=='4'}">
                                <th  class="info">
                                    <button name="sort" class="sortRow" value="mark"><my:Locale
                                            value="page.student.mark"/></button>
                                </th>
                            </c:if>

                        </tr>
                        <c:forEach items="${result.rows}" var="row">
                            <tr>
                                <td>${row.name_course}</td>
                                <td>${row.duration}</td>
                                <td>${row.name_theme}</td>
                                <td>${row.surname} ${row.name} ${row.patronymic}</td>
                                <c:if test="${param.table=='4'}">
                                    <td>${row.mark}</td>
                                </c:if>

                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
        <div class="row">
            <a name="begancourses"></a>
            <div class="panel panel-primary">
                <div class="panel-heading"><my:Locale value="page.student.table.title.inprogress"/></div>
                <div class="table-responsive">
                    <t:table status="In progress"/>
                </div>
            </div>
        </div>
        <div class="row">
            <a name="progress"></a>
            <div class="panel panel-primary">
                <div class="panel-heading"><my:Locale value="page.student.table.title.progress"/>:</div>
                <div class="table-responsive">
                    <pr:progress/>
                </div>
            </div>
        </div>
    </div>
    <div id="copyright">
        <cr:copyright/>
    </div>
</div>
</body>
</html>