package com.taras_overmind.epam_final_project.db.repository;


import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.ConnectionPool;
import com.taras_overmind.epam_final_project.db.entity.StudentEntity;
import org.apache.log4j.Logger;

import java.sql.*;

public class StudentRepo {
    private static final Logger LOG = Logger.getLogger(ConnectionPool.class.getName());

    public StudentEntity createStudent(String lastName, String firstName, String middleName, int user_id) {

        LOG.trace("Start tracing StudentRepo#createStudent");
        StudentEntity student = null;
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
        student = new StudentEntity(id, lastName, firstName, middleName, user_id);
        return student;
    }

    public StudentEntity findStudentByIdUser(int id) {
        LOG.trace("Start tracing MySQLStudentDAO#findStudentByIdUser");
        StudentEntity student = null;
        try (Connection connection = ConnectionPool.getConnection()){
            if (connection!=null){
                try(PreparedStatement statement = connection.prepareStatement(Query.SELECT_STUDENT_BY_ID_USER)) {
                    connection.setAutoCommit(false);
                    statement.setInt(1, id);
                    statement.execute();
                    ResultSet resultSet = statement.getResultSet();
                    if (resultSet.next()) {
                        student = new StudentEntity(resultSet.getInt("id"), resultSet.getString("surname"),
                                resultSet.getString("name"), resultSet.getString("patronymic"),
                                resultSet.getInt("id_user"));
                    }
                    connection.commit();
                }catch (SQLException ex){
                    LOG.error(ex.getLocalizedMessage());
                    connection.rollback();
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage());
        }
        return student;
    }
}
