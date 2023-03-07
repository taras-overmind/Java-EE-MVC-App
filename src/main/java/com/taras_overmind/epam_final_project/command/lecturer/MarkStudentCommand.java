package com.taras_overmind.epam_final_project.command.lecturer;

import com.taras_overmind.epam_final_project.Path;
import com.taras_overmind.epam_final_project.command.Command;
import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.ForwardResult;

import com.taras_overmind.epam_final_project.context.AppContext;

import com.taras_overmind.epam_final_project.db.service.JournalService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;



public class MarkStudentCommand extends Command {

    private static final Logger LOG = Logger.getLogger(MarkStudentCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward)
            throws IOException, ServletException {

        LOG.trace("Start tracing MarkStudentCommand");
        JournalService journalService = AppContext.getInstance(request).getJournalService();

        String status = request.getParameter("mark");
        try {
            int mark = Integer.parseInt(request.getParameter("new_mark"));
            int id = Integer.parseInt(request.getParameter("id"));
            if (mark < 0 || mark > 100)
                throw new NumberFormatException();
            return journalService.setMarkForStudentByStudentCourseId(mark, id, status);
        } catch (NumberFormatException ex) {
            request.getSession().setAttribute("wrongData", "Enter a valid mark");
            return new ForwardResult(Path.PAGE_LECTURER);
        }
    }
}
