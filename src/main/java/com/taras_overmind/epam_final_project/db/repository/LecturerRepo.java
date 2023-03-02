package com.taras_overmind.epam_final_project.db.repository;

import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.ConnectionPool;
import com.taras_overmind.epam_final_project.db.entity.LecturerEntity;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LecturerRepo {
    private static final Logger LOG = Logger.getLogger(ConnectionPool.class.getName());

    public LecturerEntity createLecturer(String lastName, String firstName, String middleName, int user_id) {

        LOG.trace("Start tracing LecturerRepo#createLecturer");
        LecturerEntity lecturer = null;
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
        lecturer = new LecturerEntity(id, lastName, firstName, middleName, user_id);
        return lecturer;
    }

    public List<LecturerEntity> getAllLecturers() {
        LOG.trace("Starting tracing LecturerRepo#getAllLecturers");
        List<LecturerEntity> lecturers = new ArrayList<>();
        LecturerEntity lecturer;
        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(Query.SELECT_ALL_LECTURERS)) {
                    connection.setAutoCommit(false);
                    statement.execute();
                    ResultSet resultSet = statement.getResultSet();
                    while (resultSet.next()) {
                        lecturer = new LecturerEntity(resultSet.getInt("id"), resultSet.getString("surname"),
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

    public LecturerEntity getLecturerById(int id){
        LOG.trace("Start tracing LecturerRepo#getLecturerById");
        LecturerEntity lecturer=null;
        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(Query.SELECT_LECTURER_BY_ID)) {
                    connection.setAutoCommit(false);
                    statement.setString(1, String.valueOf(id));
                    statement.execute();
                    ResultSet resultSet = statement.getResultSet();
                    if (resultSet.next()) {
                        lecturer = new LecturerEntity(resultSet.getInt("id"), resultSet.getString("surname"),
                                resultSet.getString("name"),
                                resultSet.getString("patromynic"),
                                resultSet.getInt("id_user"));
                    }
                    resultSet.close();
                    connection.commit();
                } catch (SQLException e) {
                    LOG.error(e.getLocalizedMessage());
                    connection.rollback();
                }
            }
        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return lecturer;
    }
}
