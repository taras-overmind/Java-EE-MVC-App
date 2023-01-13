package com.taras_overmind.epam_final_project;

import com.taras_overmind.epam_final_project.command.Command;
import com.taras_overmind.epam_final_project.command.CommandContainer;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controller", value = "/controller")
public class Controller extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(Controller.class);


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

        String forward = "/WEB-INF/jsp/error_page.jsp";
        try {
            forward = command.execute(request, response);
        } catch (Exception ex) {
            request.setAttribute("errorMessage", ex.getMessage());
        }
        LOG.trace("Forward address --> " + forward);

        LOG.debug("Controller finished, now go to forward address --> " + forward);

        request.getRequestDispatcher(forward).forward(request, response);
    }
}