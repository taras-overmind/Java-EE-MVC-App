package com.taras_overmind.epam_final_project.command;

import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.RedirectResult;
import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.dao.ConnectionPool;
import com.taras_overmind.epam_final_project.db.repository.StudentRepo;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterOnCourseCommand extends Command{
    private static final Logger LOG = Logger.getLogger(MarkStudentCommand.class);

    private StudentRepo studentRepo = new StudentRepo();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward)
            throws IOException, ServletException {

        LOG.trace("Start tracing RegisterOnCourseCommand");

        HttpSession session = request.getSession();
        RedirectResult redirect;
        redirect= new RedirectResult("?command=getStudentCommand");

            try (Connection connection = ConnectionPool.getConnection()) {
                int id = Integer.parseInt(String.valueOf(session.getAttribute("id")));
                int courseId = Integer.parseInt(request.getParameter("id_course"));
                if (connection != null) {
                    try (PreparedStatement statement = connection.prepareStatement(Query.REGISTER_USER_ON_COURSE)) {
                        connection.setAutoCommit(false);
                        statement.setInt(1, studentRepo.findStudentByIdUser(id).getId());
                        statement.setInt(2, courseId);
                        statement.execute();
                        connection.commit();
                    } catch (SQLException ex) {
                        LOG.error(ex.getLocalizedMessage());
                        connection.rollback();
                    }
                }
            } catch (SQLException | NumberFormatException ex) {
                LOG.error(ex.getMessage());
            }

        return redirect;
    }
}
