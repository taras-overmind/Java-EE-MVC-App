package com.taras_overmind.epam_final_project.command.common;

import com.taras_overmind.epam_final_project.command.Command;
import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.ForwardResult;
import com.taras_overmind.epam_final_project.db.dto.CourseInfoDTO;
import com.taras_overmind.epam_final_project.db.repository.CourseRepo;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.util.List;

public class GetCoursesPageCommand extends Command {
    private CourseRepo courseRepo=new CourseRepo();
    public static final Logger LOG = Logger.getLogger(GetCoursesPageCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward) throws IOException, ServletException {
        LOG.trace("Start tracing GetCoursesPageCommand");

        HttpSession session = request.getSession();
        var idLecturer=session.getAttribute("idLecturer");
        var idTheme = session.getAttribute("idTheme");
        var sort = session.getAttribute("sort");
        var sorting = session.getAttribute("sorting");

        int page=1;
        int noOfRecords =0;
        final int recordsPerPage=5;
        if (request.getParameter("page") != null ) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        String ending = " LIMIT " + recordsPerPage * (page - 1) + ", " + recordsPerPage;

        List<CourseInfoDTO> courses;
        courses=courseRepo.findSortedCourses(sort, sorting, idLecturer, idTheme, ending);
        noOfRecords= courseRepo.getNoOfRecords();
        session.setAttribute("result", courses);
        int noOfPages=(int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        session.setAttribute("noOfPages", noOfPages);
        session.setAttribute("currentPage", page);

        return new ForwardResult("/WEB-INF/jsp/courses.jsp");

    }
}