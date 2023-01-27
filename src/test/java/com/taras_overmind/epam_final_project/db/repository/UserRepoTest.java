package com.taras_overmind.epam_final_project.db.repository;

//import org.junit.jupiter.api.Test;

import com.taras_overmind.epam_final_project.db.ConnectionPool;
import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.dto.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.*;
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
    PreparedStatement stmt;
    @Mock
    ResultSet resultSet;

    @InjectMocks
    UserRepo userRepo;

    @Test
    public void createUser() throws SQLException {
        PowerMockito.mockStatic(ConnectionPool.class);
        when(ConnectionPool.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(statement);
        when(connection.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.getResultSet()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("max(id_user)")).thenReturn(1);

        UserDTO userDTO = userRepo.createUser("test", "password", 2);

        assertEquals(1, userDTO.getIdUser());
        assertEquals("test", userDTO.getLogin());
        assertEquals("password", userDTO.getPassword());
        assertEquals(2, userDTO.getRoleId());
        assertEquals(0, userDTO.getStateId());

        verify(statement).setString(1, "test");
        verify(statement).setString(2, "password");
        verify(statement).setInt(3, 2);
        verify(statement).setInt(4, 0);
        verify(statement).executeUpdate();
        verify(stmt).execute();
        verify(connection).commit();

    }



}