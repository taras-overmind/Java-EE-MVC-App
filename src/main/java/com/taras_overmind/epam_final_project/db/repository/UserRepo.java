package com.taras_overmind.epam_final_project.db.repository;

import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.dao.ConnectionPool;
import com.taras_overmind.epam_final_project.db.dto.UserDTO;

import org.apache.log4j.Logger;

import java.sql.*;

public class UserRepo {

    private static Logger LOG = Logger.getLogger(ConnectionPool.class.getName());
    public UserDTO getUserByName(String username) {
        LOG.trace("Start tracing UserRepo#getUserByName");

        UserDTO userDTO = null;
        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(Query.SELECT_USER_BY_LOGIN)) {
                    connection.setAutoCommit(false);
                    statement.setString(1, username);
                    statement.execute();
                    ResultSet resultSet = statement.getResultSet();
                    if (resultSet.next()) {
                        userDTO = new UserDTO(resultSet.getInt("id_user"), resultSet.getString("login"),
                                resultSet.getString("password"),
                                resultSet.getString("email"),
                                resultSet.getInt("id_role"),
                                resultSet.getInt("id_state"));
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
        return userDTO;
    }
    public UserDTO createUser(String login, String password, int role) {
        LOG.trace("Start tracing UserRepo#createUser");
        UserDTO user = null;
        int id = -1;

        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(Query.CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {
                    connection.setAutoCommit(false);
                    statement.setString(1, login);
                    statement.setString(2, password);
                    statement.setInt(3, role);
                    statement.setInt(4, role==1?1:0);
                    statement.executeUpdate();
                    PreparedStatement stmt = connection.prepareStatement(Query.SELECT_LAST_USER_ID);
                    stmt.execute();
                    ResultSet resultSet = stmt.getResultSet();
                    if (resultSet.next()) {
                        id = resultSet.getInt("max(id_user)");
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
        user = new UserDTO(id, login, password, role, role==1?1:0);
        return user;
    }

}
