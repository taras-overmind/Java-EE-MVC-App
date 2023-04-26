package com.taras_overmind.epam_final_project.db.repository;

import com.taras_overmind.epam_final_project.db.ConnectionPool;
import com.taras_overmind.epam_final_project.db.Query;
import org.junit.Test;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ConnectionPool.class)
public class JournalRepoTest {
    @Mock
    Connection connection;
    @Mock
    PreparedStatement statement;
    @Mock
    ResultSet resultSet;

    @InjectMocks
    JournalRepo journalRepo;

    @Test
    public void setMarkForStudentByStudentCourseId() throws SQLException {
        PowerMockito.mockStatic(ConnectionPool.class);

        int mark = 80;
        int id = 123;
        String status = "new";
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(ConnectionPool.getConnection()).thenReturn(connection);

        // Act
        journalRepo.setMarkForStudentByStudentCourseId(mark, id, status);

        // Assert
        verify(connection, times(1)).setAutoCommit(false);
        verify(statement, times(1)).execute();
        verify(connection, times(1)).commit();
        verify(connection, times(0)).rollback();
    }
}