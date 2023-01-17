package com.taras_overmind.epam_final_project.db.repository;

import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.dao.ConnectionPool;
import com.taras_overmind.epam_final_project.db.dto.UserDTO;

import org.apache.log4j.Logger;

import java.sql.*;

public class UserRepo {

    private static Logger LOG = Logger.getLogger(ConnectionPool.class.getName());
    public UserDTO getUserByName(String username) {
        LOG.trace("Start tracing UserDAO#getUserByName");

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

}
