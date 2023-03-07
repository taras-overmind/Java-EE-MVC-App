package com.taras_overmind.epam_final_project.command.common;


import com.taras_overmind.epam_final_project.Path;
import com.taras_overmind.epam_final_project.command.Command;
import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.RedirectResult;
import com.taras_overmind.epam_final_project.context.AppContext;
import com.taras_overmind.epam_final_project.db.Role;
import com.taras_overmind.epam_final_project.db.service.LecturerService;
import com.taras_overmind.epam_final_project.db.service.StudentService;
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


    private static final Logger LOG = Logger.getLogger(RegisterCommand.class);

    @Serial
    private static final long serialVersionUID = -7190245479634943129L;

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward)
            throws IOException, ServletException {
        LOG.trace("Start tracing RegisterCommand");

        UserService userService = AppContext.getInstance(request).getUserService();
        StudentService studentService = AppContext.getInstance(request).getStudentService();
        LecturerService lecturerService = AppContext.getInstance(request).getLecturerService();

        HttpSession session = request.getSession();
        RedirectResult redirect = new RedirectResult(Path.REGISTER);
        String username, password, surname, name, patronymic;
        Role role;


        username = new String(request.getParameter("username").getBytes(StandardCharsets.ISO_8859_1),
                StandardCharsets.UTF_8);
        password = new String(request.getParameter("password").getBytes(StandardCharsets.ISO_8859_1),
                StandardCharsets.UTF_8);
        surname = request.getParameter("lastName");
        name = request.getParameter("firstName");
        patronymic = request.getParameter("middleName");
        role = Role.valueOf(request.getParameter("role"));


        if (userService.registerCheck(username, password) != null) {
            session.setAttribute("wrongData", userService.registerCheck(username, password));
        } else {
            var user = userService.createUser(username, password, role);
            if (role.equals(Role.STUDENT)) {
                session.setAttribute("registerSuccess", "You registered successfully");
                studentService.createStudent(surname, name, patronymic, user.getIdUser());
            }
            else {
                session.setAttribute("registerSuccess", "You registered successfully. Wait for admin to confirm your role");
                lecturerService.createLecturer(surname, name, patronymic, user.getIdUser());
            }
            redirect = new RedirectResult(Path.LOGIN_COMMAND);
        }

        return redirect;
    }
}