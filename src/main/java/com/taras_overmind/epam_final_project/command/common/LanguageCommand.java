package com.taras_overmind.epam_final_project.command.common;


import com.taras_overmind.epam_final_project.command.Command;
import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.RedirectResult;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serial;


public class LanguageCommand extends Command {


    private static final Logger LOG = Logger.getLogger(LanguageCommand.class);


    @Serial
    private static final long serialVersionUID = 5063715519941606153L;

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward) throws IOException, ServletException {
        LOG.trace("Start tracing LanguageCommand");
        HttpSession session = request.getSession();
        String language = request.getParameter("language");
        LOG.trace("Language is switched to " + language);
        session.setAttribute("language", language);
        return new RedirectResult(request.getContextPath()+"?"+request.getParameter("url"));
    }
}
