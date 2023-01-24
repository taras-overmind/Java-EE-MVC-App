package com.taras_overmind.epam_final_project.command;

import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.RedirectResult;
import com.taras_overmind.epam_final_project.db.dto.LecturerDTO;
import com.taras_overmind.epam_final_project.db.dto.ThemeDTO;
import com.taras_overmind.epam_final_project.db.repository.LecturerRepo;
import com.taras_overmind.epam_final_project.db.repository.ThemeRepo;
import com.taras_overmind.epam_final_project.db.service.UserService;

import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serial;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class LoginCommand extends Command {


    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    private final UserService userService = new UserService();
    @Serial
    private static final long serialVersionUID = -7190245479634943129L;

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward) throws IOException, ServletException {
        LOG.trace("Start tracing LoginCommand");
        HttpSession session = request.getSession();
        RedirectResult redirect;
        String username = "", password = "";

        if ((request.getParameter("username") != null) && (request.getParameter("password") != null)) {
            username = new String(request.getParameter("username").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            password = new String(request.getParameter("password").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        }
        if (userService.loginCheck(username, password) != null) {
            session.setAttribute("wrongData", userService.loginCheck(username, password));
            redirect = new RedirectResult("?command=getLoginCommand");
        } else
            redirect = new RedirectResult("?command=getCoursesCommand");
        var user = userService.getUserRepo().getUserByName(username);
        session.setAttribute("id", user.getIdUser());
        session.setAttribute("login", user.getLogin());
        session.setAttribute("password", user.getPassword());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("id_state", user.getStateId());
        session.setAttribute("id_role", user.getRoleId());
        List<LecturerDTO> lecturers = new LecturerRepo().getAllLecturers();

        List<ThemeDTO> themes = new ThemeRepo().getAllThemes();
        session.setAttribute("themes", themes);
        session.setAttribute("lecturers", lecturers);
        session.setAttribute("user", user);


        return redirect;
    }
}