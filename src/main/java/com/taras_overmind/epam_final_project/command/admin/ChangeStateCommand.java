package com.taras_overmind.epam_final_project.command.admin;

import com.taras_overmind.epam_final_project.Utils;
import com.taras_overmind.epam_final_project.command.Command;
import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.RedirectResult;
import com.taras_overmind.epam_final_project.context.AppContext;
import com.taras_overmind.epam_final_project.db.State;
import com.taras_overmind.epam_final_project.db.service.LecturerService;
import com.taras_overmind.epam_final_project.db.service.UserService;
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

        UserService userService = AppContext.getInstance(request).getUserService();
        LecturerService lecturerService = AppContext.getInstance(request).getLecturerService();

        int id = Integer.parseInt(request.getParameter("id"));
        String name_state = request.getParameter("name_state").toUpperCase();
        userService.changeUserState(id, State.valueOf(name_state));
        request.getSession().setAttribute("lecturers", lecturerService.getAllLecturers());

        String redirect = Utils.getCurrentURL(request);
        return new RedirectResult(redirect);
    }
}
