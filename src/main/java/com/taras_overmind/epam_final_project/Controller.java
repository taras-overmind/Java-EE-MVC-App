package com.taras_overmind.epam_final_project;

import com.taras_overmind.epam_final_project.command.Command;
import com.taras_overmind.epam_final_project.command.CommandContainer;
import com.taras_overmind.epam_final_project.command.commandResult.*;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "controller")
public class Controller extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(Controller.class);

    private static final Map<Class<?>, View> views = new HashMap<>();

    static {
        views.put(ForwardResult.class, (commandResult, request, response) -> {
            LOG.trace("Forward address --> " + commandResult.getResource());
            LOG.debug("Controller finished, now go to forward address --> " + commandResult.getResource());

            request.getRequestDispatcher(commandResult.getResource()).forward(request, response);

        });
        views.put(RedirectResult.class, (commandResult, request, response) -> {
            LOG.trace("Redirect address --> " + commandResult.getResource());
            LOG.debug("Controller finished, now redirect to address --> " + commandResult.getResource());

            response.sendRedirect(request.getContextPath() + commandResult.getResource());
        });

    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.trace("*****************GET*****************");
        process(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.trace("*****************POST*****************");
        process(request, response);
    }

    private void process(HttpServletRequest request,
                         HttpServletResponse response) throws IOException, ServletException {

        LOG.debug("Controller starts");

        String commandName = request.getParameter("command");
        LOG.trace("Request parameter: command --> " + commandName);

        Command command = CommandContainer.get(commandName);

        LOG.trace("Obtained command --> " + command);

        CommandResult commandResult = new ForwardResult("?command=goToErrorPage");
        try {
            commandResult = command.execute(request, response);
        } catch (Exception ex) {
            request.setAttribute("errorMessage", ex.getMessage());
        }

        views.get(commandResult.getClass()).render(commandResult, request, response);
//        request.getRequestDispatcher(commandResult.getResource()).forward(request, response);
    }
}