<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="my" uri="/WEB-INF/tld/locale.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<sql:setDataSource var="db" driver="com.mysql.cj.jdbc.Driver" url="jdbc:mysql://localhost:3306/courses" user="root"
                   password=""/>

<c:choose>
    <c:when test="${not empty sessionScope.sort}">
        <c:choose>
            <c:when test="${not empty sessionScope.idTheme and not empty sessionScope.idLecturer}">
                <sql:query dataSource="${db}"
                           var="result"> SELECT COURSES.ID_THEME, NAME_COURSE, DURATION, NAME_THEME, SURNAME, NAME, PATRONYMIC, NAME_STATUS, count(id_student_course) as COUNT FROM COURSES INNER JOIN THEMES ON THEMES.ID_THEME=COURSES.ID_THEME INNER JOIN LECTURERS ON LECTURERS.ID=COURSES.ID_LECTURER INNER JOIN STATUSES ON STATUSES.ID_STATUS=COURSES.ID_STATUS INNER JOIN STUDENT_COURSE ON COURSES.id_course = STUDENT_COURSE.id_course WHERE ID = ${sessionScope.idLecturer} AND COURSES.ID_THEME = ${sessionScope.idTheme} GROUP BY NAME_COURSE, DURATION, NAME_THEME, SURNAME, NAME, PATRONYMIC, NAME_STATUS, COURSES.ID_THEME ORDER BY ${sessionScope.sort} ${sessionScope.sorting} </sql:query>
            </c:when>
            <c:when test="${not empty sessionScope.idLecturer}">
                <sql:query dataSource="${db}"
                           var="result"> SELECT COURSES.ID_THEME, NAME_COURSE, DURATION, NAME_THEME, SURNAME, NAME, PATRONYMIC, NAME_STATUS, count(id_student_course) as COUNT FROM COURSES INNER JOIN THEMES ON THEMES.ID_THEME=COURSES.ID_THEME INNER JOIN LECTURERS ON LECTURERS.ID=COURSES.ID_LECTURER INNER JOIN STATUSES ON STATUSES.ID_STATUS=COURSES.ID_STATUS INNER JOIN STUDENT_COURSE ON COURSES.id_course = STUDENT_COURSE.id_course WHERE ID = ${sessionScope.idLecturer} GROUP BY NAME_COURSE, DURATION, NAME_THEME, SURNAME, NAME, PATRONYMIC, NAME_STATUS, COURSES.ID_THEME ORDER BY ${sessionScope.sort} ${sessionScope.sorting} </sql:query>
            </c:when>
            <c:when test="${not empty sessionScope.idTheme}">
                <sql:query dataSource="${db}"
                           var="result"> SELECT COURSES.ID_THEME, NAME_COURSE, DURATION, NAME_THEME, SURNAME, NAME, PATRONYMIC, NAME_STATUS, count(id_student_course) as COUNT FROM COURSES INNER JOIN THEMES ON THEMES.ID_THEME=COURSES.ID_THEME INNER JOIN LECTURERS ON LECTURERS.ID=COURSES.ID_LECTURER INNER JOIN STATUSES ON STATUSES.ID_STATUS=COURSES.ID_STATUS INNER JOIN STUDENT_COURSE ON COURSES.id_course = STUDENT_COURSE.id_course WHERE COURSES.id_theme = ${sessionScope.idTheme} GROUP BY NAME_COURSE, DURATION, NAME_THEME, SURNAME, NAME, PATRONYMIC, NAME_STATUS, COURSES.ID_THEME ORDER BY ${sessionScope.sort} ${sessionScope.sorting} </sql:query>
            </c:when>
            <c:otherwise>
                <sql:query dataSource="${db}"
                           var="result"> SELECT COURSES.ID_THEME, NAME_COURSE, DURATION, NAME_THEME, SURNAME, NAME, PATRONYMIC, NAME_STATUS, count(id_student_course) as COUNT FROM COURSES INNER JOIN THEMES ON THEMES.ID_THEME=COURSES.ID_THEME INNER JOIN LECTURERS ON LECTURERS.ID=COURSES.ID_LECTURER INNER JOIN STATUSES ON STATUSES.ID_STATUS=COURSES.ID_STATUS INNER JOIN STUDENT_COURSE ON COURSES.id_course = STUDENT_COURSE.id_course GROUP BY NAME_COURSE, DURATION, NAME_THEME, SURNAME, NAME, PATRONYMIC, NAME_STATUS, COURSES.ID_THEME ORDER BY ${sessionScope.sort} ${sessionScope.sorting} </sql:query>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${not empty sessionScope.idTheme and not empty sessionScope.idLecturer}">
                <sql:query dataSource="${db}"
                           var="result"> SELECT COURSES.ID_THEME, NAME_COURSE, DURATION, NAME_THEME, SURNAME, NAME, PATRONYMIC, NAME_STATUS, count(id_student_course) as COUNT FROM COURSES INNER JOIN THEMES ON THEMES.ID_THEME=COURSES.ID_THEME INNER JOIN LECTURERS ON LECTURERS.ID=COURSES.ID_LECTURER INNER JOIN STATUSES ON STATUSES.ID_STATUS=COURSES.ID_STATUS INNER JOIN STUDENT_COURSE ON COURSES.id_course = STUDENT_COURSE.id_course WHERE ID = ${sessionScope.idLecturer} AND COURSES.ID_THEME = ${sessionScope.idTheme} GROUP BY NAME_COURSE, DURATION, NAME_THEME, SURNAME, NAME, PATRONYMIC, NAME_STATUS, COURSES.ID_THEME</sql:query>
            </c:when>
            <c:when test="${not empty sessionScope.idLecturer}">
                <sql:query dataSource="${db}"
                           var="result"> SELECT COURSES.ID_THEME, NAME_COURSE, DURATION, NAME_THEME, SURNAME, NAME, PATRONYMIC, NAME_STATUS, count(id_student_course) as COUNT FROM COURSES INNER JOIN THEMES ON THEMES.ID_THEME=COURSES.ID_THEME INNER JOIN LECTURERS ON LECTURERS.ID=COURSES.ID_LECTURER INNER JOIN STATUSES ON STATUSES.ID_STATUS=COURSES.ID_STATUS INNER JOIN STUDENT_COURSE ON COURSES.id_course = STUDENT_COURSE.id_course WHERE ID = ${sessionScope.idLecturer} GROUP BY NAME_COURSE, DURATION, NAME_THEME, SURNAME, NAME, PATRONYMIC, NAME_STATUS, COURSES.ID_THEME </sql:query>
            </c:when>
            <c:when test="${not empty sessionScope.idTheme}">
                <sql:query dataSource="${db}"
                           var="result"> SELECT COURSES.ID_THEME, NAME_COURSE, DURATION, NAME_THEME, SURNAME, NAME, PATRONYMIC, NAME_STATUS, count(id_student_course) as COUNT FROM COURSES INNER JOIN THEMES ON THEMES.ID_THEME=COURSES.ID_THEME INNER JOIN LECTURERS ON LECTURERS.ID=COURSES.ID_LECTURER INNER JOIN STATUSES ON STATUSES.ID_STATUS=COURSES.ID_STATUS INNER JOIN STUDENT_COURSE ON COURSES.id_course = STUDENT_COURSE.id_course WHERE COURSES.id_theme = ${sessionScope.idTheme} GROUP BY NAME_COURSE, DURATION, NAME_THEME, SURNAME, NAME, PATRONYMIC, NAME_STATUS, COURSES.ID_THEME</sql:query>
            </c:when>
            <c:otherwise>
                <sql:query dataSource="${db}" var="result">
                    SELECT COURSES.ID_THEME, NAME_COURSE, DURATION, NAME_THEME, SURNAME, NAME, PATRONYMIC, NAME_STATUS, count(id_student_course) as COUNT
                    FROM COURSES
                    INNER JOIN THEMES ON THEMES.ID_THEME=COURSES.ID_THEME
                    INNER JOIN LECTURERS ON LECTURERS.ID=COURSES.ID_LECTURER
                    INNER JOIN STATUSES ON STATUSES.ID_STATUS=COURSES.ID_STATUS
                    INNER JOIN STUDENT_COURSE ON COURSES.id_course = STUDENT_COURSE.id_course
                    GROUP BY NAME_COURSE, DURATION, NAME_THEME, SURNAME, NAME, PATRONYMIC, NAME_STATUS, COURSES.ID_THEME
                </sql:query>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>

<html>

<head>
    <title><my:Locale value="page.courses.title"/></title>
    <link rel="stylesheet" type="text/css" href="../../styles/courses.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">
    <script src="../../bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css">
</head>

<body>
<div id="flags">
    <form action="controller" method="post">
        <input type="hidden" name="command" value="languageCommand">
        <input type="hidden" name="redirect" value="?command=getCoursesCommand">
        <input type="hidden" name="language" value="en">
        <input type="hidden" name=url value="${requestScope['javax.servlet.forward.query_string']}">
        <input type="image" src="../../img/us.png" alt="en">
    </form>
    <form action="controller" method="post">
        <input type="hidden" name="command" value="languageCommand">
        <input type="hidden" name="redirect" value="?command=getCoursesCommand">
        <input type="hidden" name="language" value="uk">
        <input type="hidden" name=url value="${requestScope['javax.servlet.forward.query_string']}">
        <input type="image" src="../../img/ua.png" alt="ua">
    </form>
</div>

<div class="col-lg-9">
    <div class="panel panel-primary table-responsive">
        <div class="panel-heading"><my:Locale value="page.student.table.title.progress"/></div>
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
                            >${lecturer.surname} ${lecturer.name} ${lecturer.patronymic}</option>
                            >
                        </c:forEach>
                    </select>
                </label>
                <button type="submit" class="btn btn-success"><my:Locale value="page.courses.choose"/></button>
                <c:choose>
                    <c:when test="${not empty result.rows}">
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
                                <th colspan="3" class="info">
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
                            <c:forEach items="${result.rows}" var="row">
                                <tr>
                                    <td>${row.name_course}</td>
                                    <td>${row.duration}</td>
                                    <td>${row.name_theme}</td>
                                    <td>${row.surname}</td>
                                    <td>${row.name}</td>
                                    <td>${row.patronymic}</td>
                                    <td>${row.name_status}</td>
                                    <td>${row.count}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <h3><my:Locale value="page.courses.error"/></h3>
                    </c:otherwise>
                </c:choose>
            </form>
        </div>
    </div>
</div>
</body>
</html>
