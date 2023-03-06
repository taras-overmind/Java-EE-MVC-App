package com.taras_overmind.epam_final_project.context;

import com.taras_overmind.epam_final_project.db.repository.*;
import com.taras_overmind.epam_final_project.db.service.*;

import javax.servlet.http.HttpServletRequest;

public class AppContext {
    private final ThemeService themeService;
    private final CourseService courseService;
    private final JournalService journalService;
    private final LecturerService lecturerService;
    private final StatusService statusService;
    private final StudentCourseService studentCourseService;
    private final StudentService studentService;
    private final UserService userService;

    public AppContext() {
        CourseRepo courseRepo = new CourseRepo();
        JournalRepo journalRepo = new JournalRepo();
        LecturerRepo lecturerRepo = new LecturerRepo();
        StatusRepo statusRepo = new StatusRepo();
        StudentCourseRepo studentCourseRepo = new StudentCourseRepo();
        StudentRepo studentRepo = new StudentRepo();
        ThemeRepo themeRepo = new ThemeRepo();
        UserRepo userRepo = new UserRepo();

        this.courseService = new CourseService(courseRepo);
        this.journalService = new JournalService(journalRepo);
        this.lecturerService = new LecturerService(lecturerRepo);
        this.statusService = new StatusService(statusRepo);
        this.studentCourseService = new StudentCourseService(studentCourseRepo);
        this.studentService = new StudentService(studentRepo);
        this.themeService = new ThemeService(themeRepo);
        this.userService = new UserService(userRepo);
    }

    public ThemeService getThemeService() {
        return themeService;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public JournalService getJournalService() {
        return journalService;
    }

    public LecturerService getLecturerService() {
        return lecturerService;
    }

    public StatusService getStatusService() {
        return statusService;
    }

    public StudentCourseService getStudentCourseService() {
        return studentCourseService;
    }

    public StudentService getStudentService() {
        return studentService;
    }

    public UserService getUserService() {
        return userService;
    }

    public static AppContext getInstance(HttpServletRequest request){
        return (AppContext) request.getServletContext().getAttribute("app_context");
    }
}
