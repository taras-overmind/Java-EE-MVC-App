package com.taras_overmind.epam_final_project.command.common;

import com.taras_overmind.epam_final_project.Path;
import com.taras_overmind.epam_final_project.command.Command;
import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.RedirectResult;
import com.taras_overmind.epam_final_project.context.AppContext;
import com.taras_overmind.epam_final_project.db.service.LecturerService;
import com.taras_overmind.epam_final_project.db.service.StatusService;
import com.taras_overmind.epam_final_project.db.service.ThemeService;
import com.taras_overmind.epam_final_project.db.service.UserService;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serial;
import java.nio.charset.StandardCharsets;


public class LoginCommand extends Command {


    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    @Serial
    private static final long serialVersionUID = -7190245479634943129L;

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward)
            throws IOException, ServletException {
        LOG.trace("Start tracing LoginCommand");

        UserService userService = AppContext.getInstance(request).getUserService();
        StatusService statusService = AppContext.getInstance(request).getStatusService();
        LecturerService lecturerService = AppContext.getInstance(request).getLecturerService();
        ThemeService themeService = AppContext.getInstance(request).getThemeService();

        HttpSession session = request.getSession();
        RedirectResult redirect=new RedirectResult(Path.LOGIN_COMMAND);
        String username = "", password = "";

        if ((request.getParameter("username") != null) && (request.getParameter("password") != null)) {
            username = new String(request.getParameter("username").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            password = new String(request.getParameter("password").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        }
        if (userService.loginCheck(username, password) != null) {
            session.setAttribute("wrongData", userService.loginCheck(username, password));
        } else {
            redirect = new RedirectResult(Path.COURSES);
            var user = userService.getUserByName(username);
            session.setAttribute("id", user.getIdUser());
            session.setAttribute("login", user.getLogin());
            session.setAttribute("password", user.getPassword());
            session.setAttribute("email", user.getEmail());
            session.setAttribute("id_state", user.getStateId());
            session.setAttribute("id_role", user.getRoleId());
            session.setAttribute("user", user);
        }

        session.setAttribute("statuses", statusService.getAllStatuses());
        session.setAttribute("themes", themeService.getAllThemes());
        session.setAttribute("lecturers", lecturerService.getAllLecturers());

        return redirect;
    }
}