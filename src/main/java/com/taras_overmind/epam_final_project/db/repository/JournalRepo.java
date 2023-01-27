package com.taras_overmind.epam_final_project.db.repository;

import com.taras_overmind.epam_final_project.command.commandResult.RedirectResult;
import com.taras_overmind.epam_final_project.db.ConnectionPool;
import com.taras_overmind.epam_final_project.db.Query;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JournalRepo {
    public static final Logger LOG = Logger.getLogger(JournalRepo.class.getName());

    public RedirectResult setMarkForStudentByStudentCourseId(int mark, int id, String status) {
        RedirectResult redirect = new RedirectResult("?command=getLecturerCommand&table=1");
        String query;
        if (status.equals("new"))
            query = Query.CREATE_MARK_FOR_STUDENT;
        else
            query = Query.UPDATE_MARK_FOR_STUDENT;

        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    connection.setAutoCommit(false);
                    if (status.equals("new")) {

                        statement.setInt(1, id);
                        statement.setInt(2, mark);
                    } else {
                        redirect = new RedirectResult("?command=getLecturerCommand&table=2");
                        statement.setInt(1, mark);
                        statement.setInt(2, id);
                    }
                    statement.execute();
                    connection.commit();
                } catch (SQLException ex) {
                    LOG.error(ex.getLocalizedMessage());
                    connection.rollback();
                }
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        }
        return redirect;

    }
}
