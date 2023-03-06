package com.taras_overmind.epam_final_project.command.student;

import com.taras_overmind.epam_final_project.command.Command;
import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.RedirectResult;
import com.taras_overmind.epam_final_project.context.AppContext;
import com.taras_overmind.epam_final_project.db.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegisterOnCourseCommand extends Command {
    private static final Logger LOG = Logger.getLogger(RegisterOnCourseCommand.class);


    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward)
            throws IOException, ServletException {

        LOG.trace("Start tracing RegisterOnCourseCommand");
        UserService userService= AppContext.getInstance(request).getUserService();

        HttpSession session = request.getSession();
        RedirectResult redirect;
        redirect = new RedirectResult("?command=getStudentCommand");
        int id = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        int courseId = Integer.parseInt(request.getParameter("id_course"));
        userService.registerUserOnCourse(id, courseId);

        return redirect;
    }
}
