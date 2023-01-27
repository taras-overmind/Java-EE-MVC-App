package com.taras_overmind.epam_final_project.command.student;

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

public class GetStudentPageCommand extends Command {
    public static final Logger LOG = Logger.getLogger(GetStudentPageCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward) throws IOException, ServletException {
        LOG.trace("Start tracing GetStudentPageCommand");
        HttpSession session = request.getSession();
        String status = request.getParameter("table")==null?"1":request.getParameter("table");
        List<CourseInfoDTO> courses=new CourseRepo().
                findUserCoursesByUserIdAndStatus(status, Integer.parseInt(String.valueOf(session.getAttribute("id"))));

        session.setAttribute("result", courses);
        return new ForwardResult("/WEB-INF/jsp/student.jsp");

    }
}