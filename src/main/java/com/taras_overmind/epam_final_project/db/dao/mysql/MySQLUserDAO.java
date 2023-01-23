package com.taras_overmind.epam_final_project.db.dao.mysql;


import org.apache.log4j.Logger;
import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.dao.ConnectionPool;
import com.taras_overmind.epam_final_project.db.dao.UserDAO;
import com.taras_overmind.epam_final_project.db.dto.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLUserDAO extends UserDTO implements UserDAO {

    public static final Logger LOG = Logger.getLogger(MySQLUserDAO.class);
    private static final long serialVersionUID = 5566685127381260993L;



    @Override
    public List<UserDTO> getAllUsers() {
        LOG.trace("Start tracing MySQLUserDAO#getAllUsers");
        List<UserDTO> users = new ArrayList<>();
        UserDTO user;

        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(Query.SELECT_ALL_USERS)) {
                    connection.setAutoCommit(false);
                    statement.execute();
                    ResultSet resultSet = statement.getResultSet();
                    while (resultSet.next()) {
                        user = new UserDTO(resultSet.getInt("id_user"), resultSet.getString("login"),
                                resultSet.getString("password"), resultSet.getString("email"),
                                resultSet.getInt("id_role"), resultSet.getInt("id_state"));
                        users.add(user);
                    }
                    resultSet.close();
                    connection.commit();
                } catch (SQLException ex) {
                    LOG.error(ex.getLocalizedMessage());
                    connection.rollback();
                }
            }
        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }

        return users;
    }



    @Override
    public void setNewPassword(int id, String password) {
        LOG.trace("Start tracing MySQLUserDAO#setNewPassword");
        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(Query.UPDATE_PASSWORD)) {
                    connection.setAutoCommit(false);
                    statement.setString(1, password);
                    statement.setInt(2, id);
                    statement.executeUpdate();
                    connection.commit();
                } catch (SQLException ex) {
                    LOG.error(ex.getLocalizedMessage());
                    connection.rollback();
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage());
        }
    }
}