package com.taras_overmind.epam_final_project.db.repository;

import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.dao.ConnectionPool;
import com.taras_overmind.epam_final_project.db.dto.LecturerDTO;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LecturerRepo {
    private static Logger LOG = Logger.getLogger(ConnectionPool.class.getName());

    public LecturerDTO createLecturer(String lastName, String firstName, String middleName, int user_id) {

        LOG.trace("Start tracing LecturerRepo#createLecturer");
        LecturerDTO lecturer = null;
        int id = -1;

        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(Query.CREATE_LECTURER, Statement.RETURN_GENERATED_KEYS)) {
                    connection.setAutoCommit(false);
                    statement.setString(1, lastName);
                    statement.setString(2, firstName);
                    statement.setString(3, middleName);
                    statement.setInt(4, user_id);
                    statement.executeUpdate();
                    PreparedStatement stmt = connection.prepareStatement(Query.SELECT_LAST_LECTURER_ID);
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
        lecturer = new LecturerDTO(id, lastName, firstName, middleName, user_id);
        return lecturer;
    }

    public List<LecturerDTO> getAllLecturers() {
        LOG.trace("Starting tracing MySQLLecturerDAO#getAllLecturers");
        List<LecturerDTO> lecturers = new ArrayList<>();
        LecturerDTO lecturer;
        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(Query.SELECT_ALL_LECTURERS)) {
                    connection.setAutoCommit(false);
                    statement.execute();
                    ResultSet resultSet = statement.getResultSet();
                    while (resultSet.next()) {
                        lecturer = new LecturerDTO(resultSet.getInt("id"), resultSet.getString("surname"),
                                resultSet.getString("name"), resultSet.getString("patronymic"), resultSet.getInt("id_user"));
                        lecturers.add(lecturer);
                    }
                    resultSet.close();
                    connection.commit();
                } catch (SQLException ex) {
                    LOG.error(ex.getLocalizedMessage());
                    connection.rollback();
                }
            }
        } catch (SQLException ex) {
            LOG.info(ex.getLocalizedMessage());
        }
        return lecturers;
    }
}
