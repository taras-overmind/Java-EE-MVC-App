package com.taras_overmind.epam_final_project;

public final class Path {

    public static final String PAGE_LOGIN = "/WEB-INF/jsp/login.jsp";
    public static final String PAGE_COURSES = "/WEB-INF/jsp/courses.jsp";
    public static final String PAGE_REGISTER = "/WEB-INF/jsp/register.jsp";
    public static final String PAGE_STUDENT = "/WEB-INF/jsp/student.jsp";
    public static final String PAGE_USERS = "/WEB-INF/jsp/users.jsp";
    public static final String PAGE_LECTURER = "/WEB-INF/jsp/lecturer.jsp";
    public static final String PAGE_COURSES_TO_REGISTER = "/WEB-INF/jsp/coursesToRegister.jsp";
    public static final String PAGE_CREATE_COURSE = "/WEB-INF/jsp/createCourse.jsp";
    public static final String PAGE_EDIT_COURSE = "/WEB-INF/jsp/editCourse.jsp";

    // commands
    public static final String LOGIN_COMMAND = "?command=getLoginCommand";
    public static final String COURSES = "?command=getCoursesCommand";
    public static final String CREATE_COURSE = "?command=getCreateCourseCommand";
    public static final String REGISTER = "?command=getRegisterCommand";
    public static final String STUDENT = "?command=getStudentCommand";
    public static final String LECTURER = "?command=getLecturerCommand&table=2";



}