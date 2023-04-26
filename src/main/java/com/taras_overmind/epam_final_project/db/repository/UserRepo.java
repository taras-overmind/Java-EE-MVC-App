package com.taras_overmind.epam_final_project.db.repository;

import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.ConnectionPool;
import com.taras_overmind.epam_final_project.db.Role;
import com.taras_overmind.epam_final_project.db.State;
import com.taras_overmind.epam_final_project.db.entity.UserEntity;

import com.taras_overmind.epam_final_project.db.dto.UserDTO;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserRepo {

    private static final Logger LOG = Logger.getLogger(ConnectionPool.class.getName());

    public UserEntity getUserByName(String username) {
        LOG.trace("Start tracing UserRepo#getUserByName");

        UserEntity userEntity = null;
        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(Query.SELECT_USER_BY_LOGIN)) {
                    connection.setAutoCommit(false);
                    statement.setString(1, username);
                    statement.execute();
                    ResultSet resultSet = statement.getResultSet();
                    if (resultSet.next()) {
                        userEntity = new UserEntity(resultSet.getInt("id_user"), resultSet.getString("login"),
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
        return userEntity;
    }

    public UserEntity createUser(String login, String password, Role role) {
        LOG.trace("Start tracing UserRepo#createUser");
        UserEntity user;
        int id = -1;

        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(Query.CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {
                    connection.setAutoCommit(false);
                    statement.setString(1, login);
                    statement.setString(2, password);
                    statement.setInt(3, role.getId_role());
                    statement.setInt(4,
                            role.equals(Role.STUDENT) ? State.UNLOCKED.getId_state() : State.LOCKED.getId_state());
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
        user = new UserEntity(id, login, password, role.getId_role(),
                role.equals(Role.STUDENT) ? State.UNLOCKED.getId_state() : State.LOCKED.getId_state());
        return user;
    }

    public void changeUserState(int id, State state) {
        LOG.trace("Start tracing UserRepo#changeUserState");
        try (Connection connection = ConnectionPool.getConnection()) {
            if ((connection != null)) {
                try (PreparedStatement statement = connection.prepareStatement(Query.CHANGE_STATE_USER,
                        Statement.RETURN_GENERATED_KEYS)) {
                    connection.setAutoCommit(false);
                    if(state.equals(State.LOCKED))
                        statement.setInt(1, State.UNLOCKED.getId_state());
                    else
                        statement.setInt(1, State.LOCKED.getId_state());
                    statement.setInt(2, id);
                    statement.executeUpdate();
                    connection.commit();
                } catch (SQLException e) {
                    LOG.error(e.getLocalizedMessage());
                    connection.rollback();
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage());
        }
    }

    public void registerUserOnCourse(int id, int idCourse) {
        LOG.trace("Start tracing UserRepo#registerUserOnCourse");
        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(Query.REGISTER_USER_ON_COURSE)) {
                    connection.setAutoCommit(false);
                    statement.setInt(1, new StudentRepo().findStudentByIdUser(id).getId());
                    statement.setInt(2, idCourse);
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

    public List<UserDTO> findUsers(boolean isStudent) {
        UserDTO userDTO;
        List<UserDTO> list = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                PreparedStatement statement;
                if (isStudent)
                    statement = connection.prepareStatement(Query.SELECT_STUDENTS_INFO);
                else
                    statement = connection.prepareStatement(Query.SELECT_LECTURERS_INFO);
                connection.setAutoCommit(false);
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    userDTO = new UserDTO();
                    userDTO.setId_user(resultSet.getInt("id_user"));
                    userDTO.setName(resultSet.getString("surname") + " " + resultSet.getString("name")
                            + " " + resultSet.getString("patronymic"));
                    userDTO.setName_state(resultSet.getString("name_state"));
                    list.add(userDTO);
                }
                resultSet.close();
            }
        } catch (SQLException ex) {
            LOG.info(ex.getLocalizedMessage());
        }
        return list;
    }
}
