package com.taras_overmind.epam_final_project.command.common;

import com.taras_overmind.epam_final_project.Path;
import com.taras_overmind.epam_final_project.command.Command;
import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.ForwardResult;
import com.taras_overmind.epam_final_project.context.AppContext;
import com.taras_overmind.epam_final_project.db.dto.CourseDTO;
import com.taras_overmind.epam_final_project.db.service.CourseService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.util.List;

public class GetCoursesPageCommand extends Command {
    public static final Logger LOG = Logger.getLogger(GetCoursesPageCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward) throws IOException, ServletException {
        LOG.trace("Start tracing GetCoursesPageCommand");

        CourseService courseService = AppContext.getInstance(request).getCourseService();
        HttpSession session = request.getSession();

        var idLecturer = session.getAttribute("idLecturer");
        var idTheme = session.getAttribute("idTheme");
        var sort = session.getAttribute("sort");
        var sorting = session.getAttribute("sorting");

        int current_page = 1;
        int noOfRecords;
        final int recordsPerPage = 5;

        if (request.getParameter("page") != null) {
            current_page = Integer.parseInt(request.getParameter("page"));
        }

        String ending = " LIMIT " + recordsPerPage * (current_page - 1) + ", " + recordsPerPage;

        List<CourseDTO> courses = courseService.findSortedCourses(sort, sorting, idLecturer, idTheme, ending);

        noOfRecords = courseService.getNumberOfRecords();
        int numberOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        request.setAttribute("result", courses);
        request.setAttribute("noOfPages", numberOfPages);
        request.setAttribute("currentPage", current_page);

        return new ForwardResult(Path.PAGE_COURSES);

    }
}