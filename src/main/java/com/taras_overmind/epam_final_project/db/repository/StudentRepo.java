package com.taras_overmind.epam_final_project.db.repository;


import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.dao.ConnectionPool;
import com.taras_overmind.epam_final_project.db.dto.StudentDTO;
import org.apache.log4j.Logger;

import java.sql.*;

public class StudentRepo {
    private static Logger LOG = Logger.getLogger(ConnectionPool.class.getName());

    public StudentDTO createStudent(String lastName, String firstName, String middleName, int user_id) {

        LOG.trace("Start tracing StudentRepo#createStudent");
        StudentDTO student = null;
        int id = -1;

        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(Query.CREATE_STUDENT, Statement.RETURN_GENERATED_KEYS)) {
                    connection.setAutoCommit(false);
                    statement.setString(1, lastName);
                    statement.setString(2, firstName);
                    statement.setString(3, middleName);
                    statement.setInt(4, user_id);
                    statement.executeUpdate();
                    PreparedStatement stmt = connection.prepareStatement(Query.SELECT_LAST_STUDENT_ID);
                    stmt.execute();
                    ResultSet resultSet = stmt.getResultSet();
                    if (resultSet.next()) {
                        id = resultSet.getInt("max(id)");
                    }
                    connection.commit();
                } catch (SQLException ex) {
                    LOG.error(ex.getLocalizedMessage());
                    connection.rollback();
                }
            }
        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        student = new StudentDTO(id, lastName, firstName, middleName, user_id);
        return student;
    }
}
