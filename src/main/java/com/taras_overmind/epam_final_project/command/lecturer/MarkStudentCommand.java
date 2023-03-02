package com.taras_overmind.epam_final_project.command.lecturer;

import com.taras_overmind.epam_final_project.command.Command;
import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.ForwardResult;
import com.taras_overmind.epam_final_project.command.commandResult.RedirectResult;
import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.ConnectionPool;
import com.taras_overmind.epam_final_project.db.repository.JournalRepo;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class MarkStudentCommand extends Command {

    private static final Logger LOG = Logger.getLogger(MarkStudentCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward)
            throws IOException, ServletException {

        LOG.trace("Start tracing MarkStudentCommand");

        String status = request.getParameter("mark");
        try {
            int mark = Integer.parseInt(request.getParameter("new_mark"));
            int id = Integer.parseInt(request.getParameter("id"));
            if(mark<0 || mark>100)
                throw new NumberFormatException();
            return new JournalRepo().setMarkForStudentByStudentCourseId(mark, id, status);
        } catch (NumberFormatException ex) {
            request.getSession().setAttribute("wrongData", "Enter a valid mark");
            return new ForwardResult("/WEB-INF/jsp/lecturer.jsp");
        }


    }
}
