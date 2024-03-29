package com.taras_overmind.epam_final_project.command.admin;

import com.taras_overmind.epam_final_project.Path;
import com.taras_overmind.epam_final_project.command.Command;
import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.RedirectResult;
import com.taras_overmind.epam_final_project.context.AppContext;
import com.taras_overmind.epam_final_project.db.Status;
import com.taras_overmind.epam_final_project.db.service.CourseService;
import com.taras_overmind.epam_final_project.db.service.ThemeService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CreateCourseCommand extends Command {

    private static final Logger LOG = Logger.getLogger(CreateCourseCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward)
            throws IOException, ServletException {
        LOG.trace("Start tracing CreateCourseCommand");

        ThemeService themeService = AppContext.getInstance(request).getThemeService();
        CourseService courseService = AppContext.getInstance(request).getCourseService();
        HttpSession session = request.getSession();
        String redirect = Path.CREATE_COURSE;

        try {
            int id_lecturer = Integer.parseInt(request.getParameter("idLecturer"));
            int duration = Integer.parseInt(request.getParameter("duration"));
            String themeName = request.getParameter("name_theme");
            String nameCourse = request.getParameter("name_course");

            int id_theme = themeService.themeCheck(themeName).getIdTheme();
            session.setAttribute("themes", themeService.getAllThemes());

            courseService.createCourse(nameCourse, duration, id_theme, id_lecturer, Status.OPENED);
            redirect = Path.COURSES;
        } catch (IllegalArgumentException ex) {
            session.setAttribute("wrongData", "Duration must be a positive number");
        }

        return new RedirectResult(redirect);
    }

}
