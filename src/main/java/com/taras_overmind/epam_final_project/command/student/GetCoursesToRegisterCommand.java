package com.taras_overmind.epam_final_project.command.student;

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

public class GetCoursesToRegisterCommand extends Command {
    public static final Logger LOG = Logger.getLogger(GetCoursesToRegisterCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward) throws IOException, ServletException {
        LOG.trace("Start tracing GetCoursesToRegisterCommand");
        CourseService courseService= AppContext.getInstance(request).getCourseService();
        HttpSession session = request.getSession();
        List<CourseDTO> courses = courseService
                .findCoursesToRegisterByUserId(Integer.parseInt(String.valueOf(session.getAttribute("id"))));
        session.setAttribute("result", courses);

        return new ForwardResult(Path.PAGE_COURSES_TO_REGISTER);

    }
}