package com.taras_overmind.epam_final_project.db.repository;

//import org.junit.jupiter.api.Test;

import com.taras_overmind.epam_final_project.db.ConnectionPool;
import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.entity.UserEntity;
import com.taras_overmind.epam_final_project.db.dto.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ConnectionPool.class)
public class UserRepoTest {
    @Mock
    Connection connection;
    @Mock
    PreparedStatement statement;
    @Mock
    ResultSet resultSet;

    @InjectMocks
    UserRepo userRepo;


    @Test
    public void createUser() throws SQLException {
        PowerMockito.mockStatic(ConnectionPool.class);
        when(ConnectionPool.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(statement);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.getResultSet()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("max(id_user)")).thenReturn(1);

        UserEntity userEntity = userRepo.createUser("test", "password", 2);

        assertEquals(1, userEntity.getIdUser());
        assertEquals("test", userEntity.getLogin());
        assertEquals("password", userEntity.getPassword());
        assertEquals(2, userEntity.getRoleId());
        assertEquals(0, userEntity.getStateId());

        verify(statement).setString(1, "test");
        verify(statement).setString(2, "password");
        verify(statement).setInt(3, 2);
        verify(statement).setInt(4, 0);
        verify(statement).executeUpdate();
        verify(statement).execute();
        verify(connection).commit();

    }


    @Test
    public void getUserByName() throws SQLException{
        PowerMockito.mockStatic(ConnectionPool.class);
        when(ConnectionPool.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(Query.SELECT_USER_BY_LOGIN)).thenReturn(statement);
        when(statement.getResultSet()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id_user")).thenReturn(1);
        when(resultSet.getString("login")).thenReturn("testuser");
        when(resultSet.getString("password")).thenReturn("testpassword");
        when(resultSet.getString("email")).thenReturn("testuser@test.com");
        when(resultSet.getInt("id_role")).thenReturn(1);
        when(resultSet.getInt("id_state")).thenReturn(1);

        UserEntity userEntity = userRepo.getUserByName("testuser");

        assertEquals(1, userEntity.getIdUser());
        assertEquals("testuser", userEntity.getLogin());
        assertEquals("testpassword", userEntity.getPassword());
        assertEquals("testuser@test.com", userEntity.getEmail());
        assertEquals(1, userEntity.getRoleId());
        assertEquals(1, userEntity.getStateId());
    }


    @Test
    public void testChangeUserState_Locked() throws SQLException {
        PowerMockito.mockStatic(ConnectionPool.class);
        when(ConnectionPool.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(statement);
        userRepo.changeUserState(1, "locked");
        verify(statement).setInt(1, 1);
        verify(statement).setInt(2, 1);
        verify(statement).executeUpdate();
        verify(connection).commit();
    }

    @Test
    public void testChangeUserState_Unlocked() throws SQLException {
        PowerMockito.mockStatic(ConnectionPool.class);
        when(ConnectionPool.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(statement);
        userRepo.changeUserState(1, "unlocked");
        verify(statement).setInt(1, 0);
        verify(statement).setInt(2, 1);
        verify(statement).executeUpdate();
        verify(connection).commit();
    }

    @Test
    public void testChangeUserState_InvalidState() throws SQLException {
        PowerMockito.mockStatic(ConnectionPool.class);
        when(ConnectionPool.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(statement);
        userRepo.changeUserState(1, "invalid state");
        verify(connection).rollback();
    }


    @Test
    public void registerUserOnCourseTest_SQLException() throws SQLException {
        PowerMockito.mockStatic(ConnectionPool.class);
        when(ConnectionPool.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(Query.REGISTER_USER_ON_COURSE)).thenThrow(new SQLException());
        userRepo.registerUserOnCourse(1, 1);
        verify(connection).rollback();
    }

    @Test
    public void findUsers_shouldReturnCorrectUsers() throws SQLException {
        // Arrange
        PowerMockito.mockStatic(ConnectionPool.class);
        when(ConnectionPool.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(Query.SELECT_STUDENTS_INFO)).thenReturn(statement);
        when(statement.execute()).thenReturn(true);
        when(statement.getResultSet()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id_user")).thenReturn(1, 2);
        when(resultSet.getString("surname")).thenReturn("Smith", "Johnson");
        when(resultSet.getString("name")).thenReturn("John", "Jane");
        when(resultSet.getString("patronymic")).thenReturn("Doe", "Doe");
        when(resultSet.getString("name_state")).thenReturn("active", "active");

        // Act
        List<UserDTO> users = userRepo.findUsers(true);

        // Assert
        assertEquals(2, users.size());
        assertEquals(1, users.get(0).getId_user());
        assertEquals("Smith John Doe", users.get(0).getName());
        assertEquals("active", users.get(0).getName_state());
        assertEquals(2, users.get(1).getId_user());
        assertEquals("Johnson Jane Doe", users.get(1).getName());
        assertEquals("active", users.get(1).getName_state());
    }
}