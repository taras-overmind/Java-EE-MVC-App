package com.taras_overmind.epam_final_project.command;


import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.RedirectResult;
import com.taras_overmind.epam_final_project.db.dto.UserDTO;
import com.taras_overmind.epam_final_project.db.service.UserService;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serial;
import java.nio.charset.StandardCharsets;


public class RegisterCommand extends Command {


    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    private final UserService userService = new UserService();
    @Serial
    private static final long serialVersionUID = -7190245479634943129L;

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward) throws IOException, ServletException {
        LOG.trace("Start tracing RegisterCommand");

        HttpSession session = request.getSession();
        RedirectResult redirect;
        String username = "", password = "", lastName = "", firstName = "", middleName = "";
        int role = 0;

        if ((request.getParameter("username") != null) && (request.getParameter("password") != null) &&
                request.getParameter("lastName") != null && request.getParameter("firstName") != null &&
                request.getParameter("middleName") != null && request.getParameter("role") != null) {
            username = new String(request.getParameter("username").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            password = new String(request.getParameter("password").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            firstName = new String(request.getParameter("firstName").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            lastName = new String(request.getParameter("lastName").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            middleName = new String(request.getParameter("middleName").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            role = request.getParameter("role").equals("1") ? 1 : 2;
            System.out.println(role);
            System.out.println(
            );
            System.out.println();
        }

        if (userService.registerCheck(username, password) != null) {
            session.setAttribute("wrongData", userService.registerCheck(username, password));
            redirect = new RedirectResult("?command=getRegisterCommand");
        } else {
            userService.getUserRepo().createUser(username, password, role);
            if(role==1)
                session.setAttribute("registerSuccess", "You registered successfully");
            else
                session.setAttribute("registerSuccess", "You registered successfully. Wait for admin to confirm your role");
            redirect = new RedirectResult("?command=getLoginCommand");
        }

        return redirect;
    }
}