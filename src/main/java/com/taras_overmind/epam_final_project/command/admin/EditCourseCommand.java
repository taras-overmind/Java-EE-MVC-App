package com.taras_overmind.epam_final_project.command.admin;

import com.taras_overmind.epam_final_project.Path;
import com.taras_overmind.epam_final_project.Utils;
import com.taras_overmind.epam_final_project.command.Command;
import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.RedirectResult;
import com.taras_overmind.epam_final_project.context.AppContext;
import com.taras_overmind.epam_final_project.db.service.CourseService;
import com.taras_overmind.epam_final_project.db.service.ThemeService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLDataException;

public class EditCourseCommand extends Command {

    private static final Logger LOG = Logger.getLogger(EditCourseCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward)
            throws IOException, ServletException {
        LOG.trace("Start tracing EditCourseCommand");

        CourseService courseService = AppContext.getInstance(request).getCourseService();
        ThemeService themeService = AppContext.getInstance(request).getThemeService();
        HttpSession session = request.getSession();
        String redirect = Utils.getCurrentURL(request);

        try {
            int id_lecturer = Integer.parseInt(request.getParameter("idLecturer"));
            int id_course = Integer.parseInt(request.getParameter("id_course"));
            int duration = Integer.parseInt(request.getParameter("duration"));
            int status = Integer.parseInt(request.getParameter("idStatus"));
            String themeName = request.getParameter("name_theme");
            String nameCourse = request.getParameter("name_course");
            int id_theme = themeService.themeCheck(themeName).getIdTheme();

            session.setAttribute("themes", themeService.getAllThemes());

                redirect = Path.COURSES;
                courseService.updateCourse(id_course, nameCourse, duration, id_theme, id_lecturer, status);

        }catch (NumberFormatException | SQLDataException ex){
            session.setAttribute("wrongData", ex.getMessage());
        }
//        catch (IllegalArgumentException ex) {
//            session.setAttribute("wrongData", "Wrong data");
//        }
        return new RedirectResult(redirect);
    }

}
