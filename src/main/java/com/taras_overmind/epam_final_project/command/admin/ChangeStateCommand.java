package com.taras_overmind.epam_final_project.command.admin;

import com.taras_overmind.epam_final_project.command.Command;
import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.RedirectResult;
import com.taras_overmind.epam_final_project.db.repository.LecturerRepo;
import com.taras_overmind.epam_final_project.db.repository.UserRepo;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeStateCommand extends Command {
    public static final Logger LOG = Logger.getLogger(ChangeStateCommand.class);
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward) throws IOException, ServletException {
        LOG.trace("Start tracing ChangeStateCommand");
        String redirect = request.getContextPath()+"?"+request.getParameter("url");
        int id = Integer.parseInt(request.getParameter("id"));
        String name_state = request.getParameter("name_state");
        new UserRepo().changeUserState(id, name_state);
        request.getSession().setAttribute("lecturers", new LecturerRepo().getAllLecturers());
        return new RedirectResult(redirect);
    }
}
