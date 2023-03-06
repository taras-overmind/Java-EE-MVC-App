package com.taras_overmind.epam_final_project;

public final class Path {

    public static final String PAGE_LOGIN = "/WEB-INF/jsp/login.jsp";
    public static final String PAGE_COURSES = "/WEB-INF/jsp/courses.jsp";
    public static final String PAGE_REGISTER = "/WEB-INF/jsp/register.jsp";
    public static final String PAGE_STUDENT = "/WEB-INF/jsp/student.jsp";
    public static final String PAGE_USERS = "/WEB-INF/jsp/users.jsp";
    public static final String PAGE_LECTURER = "/WEB-INF/jsp/lecturer.jsp";
    public static final String PAGE_COURSES_TO_REGISTER = "/WEB-INF/jsp/courses.jsp";

    // commands
    public static final String LOGIN_COMMAND = "?command=getLoginCommand";
    public static final String COURSES_COMMAND = "?command=getLCoursesCommand";
    public static final String CREATE_COURSE = "?command=getCreateCourseCommand";

}