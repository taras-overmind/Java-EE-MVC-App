package com.taras_overmind.epam_final_project.command;

import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.ForwardResult;
import com.taras_overmind.epam_final_project.command.commandResult.RedirectResult;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;


public class GetEditCourseCommand extends Command {
    public static final Logger LOG = Logger.getLogger(GetRegisterCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward) throws IOException, ServletException {
        LOG.trace("Start tracing GetEditCourseCommand");

        return new ForwardResult("/WEB-INF/jsp/editCourse.jsp");
//        return new RedirectResult("?command=getEditCourseCommand");

    }
}