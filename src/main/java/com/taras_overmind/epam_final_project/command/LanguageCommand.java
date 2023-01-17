package com.taras_overmind.epam_final_project.command;


import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.RedirectResult;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@WebServlet(name = "ChangeLanguage")
public class LanguageCommand extends Command {


    private static final Logger LOG = Logger.getLogger(LanguageCommand.class);


    private static final long serialVersionUID = 5063715519941606153L;

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward) throws IOException, ServletException {
        LOG.trace("Start tracing LanguageCommand");
        HttpSession session = request.getSession();
        List<String> languages = Arrays.asList("en", "uk");
        String language = request.getParameter("language");
        boolean existLanguage = false;
        for (String lang : languages) {
            if (language.equals(lang)) {
                existLanguage = true;
                break;
            }
        }
        if (!existLanguage) {
            request.setAttribute("errorMessage", "Invalid value for language");
            throw new ServletException();
        } else {
            LOG.trace("Language is switched to "+language);
            session.setAttribute("language", language);
        }
        session.setAttribute("redirect", request.getParameter("redirect"));
        return new RedirectResult(request.getParameter("redirect"));
    //    return new RedirectResult(request.getContextPath());
    }
}
