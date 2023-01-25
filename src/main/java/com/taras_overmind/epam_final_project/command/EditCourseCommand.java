package com.taras_overmind.epam_final_project.command;

import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.RedirectResult;
import com.taras_overmind.epam_final_project.db.repository.CourseRepo;
import com.taras_overmind.epam_final_project.db.repository.ThemeRepo;
import com.taras_overmind.epam_final_project.db.service.ThemeService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EditCourseCommand extends Command {

    private static final Logger LOG = Logger.getLogger(EditCourseCommand.class);
    private static final ThemeService themeService = new ThemeService();

    private static final CourseRepo courseRepo = new CourseRepo();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward)
            throws IOException, ServletException {
        LOG.trace("Start tracing EditCourseCommand");

        HttpSession session = request.getSession();
        String redirect = request.getContextPath()+"?"+request.getParameter("url");

        try {
            int id_lecturer = Integer.parseInt(request.getParameter("idLecturer"));
            int id_course = Integer.parseInt(request.getParameter("id_course"));
            int duration = Integer.parseInt(request.getParameter("duration"));
            int status = Integer.parseInt(request.getParameter("idStatus"));
            String themeName = request.getParameter("name_theme");
            String nameCourse = request.getParameter("name_course");
            int id_theme = themeService.themeCheck(themeName).getIdTheme();
            session.setAttribute("themes", new ThemeRepo().getAllThemes());
            if (duration < 0) {
                session.setAttribute("wrongData", "Duration is negative");
            } else {
                courseRepo.updateCourse(id_course, nameCourse, duration, id_theme, id_lecturer, status);
                redirect = "?command=getCoursesCommand";
            }
        } catch (NumberFormatException ex) {
            session.setAttribute("wrongData", "Wrong data");
        }
        return new RedirectResult(redirect);
    }

}
