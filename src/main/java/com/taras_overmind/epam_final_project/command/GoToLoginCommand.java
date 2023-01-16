package com.taras_overmind.epam_final_project.command;

import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.ForwardResult;
import com.taras_overmind.epam_final_project.command.commandResult.RedirectResult;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToLoginCommand extends Command {
    public static final Logger LOG = Logger.getLogger(GoToLoginCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.trace("Start tracing GoToLoginCommand");
        return new ForwardResult("/WEB-INF/jsp/login.jsp");

    }
}