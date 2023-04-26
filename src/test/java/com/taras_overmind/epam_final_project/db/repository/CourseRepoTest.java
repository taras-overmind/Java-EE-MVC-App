package com.taras_overmind.epam_final_project.db.repository;

import com.taras_overmind.epam_final_project.db.ConnectionPool;
import com.taras_overmind.epam_final_project.db.Status;
import com.taras_overmind.epam_final_project.db.entity.CourseEntity;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.sql.*;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ConnectionPool.class)
public class CourseRepoTest {

    @Mock
    Connection connection;
    @Mock
    PreparedStatement statement;
    @Mock
    ResultSet resultSet;

    @InjectMocks
    CourseRepo courseRepo;

    @Test
    public void findCourseById() throws SQLException {
        PowerMockito.mockStatic(ConnectionPool.class);
        int courseId = 1;
        CourseEntity expectedCourse = new CourseEntity();
        expectedCourse.setCourseId(courseId);
        expectedCourse.setLecturerId(2);
        expectedCourse.setDuration(3);
        expectedCourse.setCourseName("Test Course");
        expectedCourse.setThemeId(4);
        expectedCourse.setStatusId(5);
        when(ConnectionPool.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(statement);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.getResultSet()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id_course")).thenReturn(courseId);
        when(resultSet.getInt("id_lecturer")).thenReturn(2);
        when(resultSet.getInt("duration")).thenReturn(3);
        when(resultSet.getString("name_course")).thenReturn("Test Course");
        when(resultSet.getInt("id_theme")).thenReturn(4);
        when(resultSet.getInt("id_status")).thenReturn(5);

        CourseEntity actualCourse = courseRepo.findCourseById(courseId);

        assertEquals(expectedCourse, actualCourse);

    }

    @Test
    public void createCourse() throws SQLException {
        // Arrange
        PowerMockito.mockStatic(ConnectionPool.class);
        String name = "Test Course";
        int duration = 10;
        int theme = 1;
        int lecturer = 2;
        Status status = Status.OPENED;
        when(ConnectionPool.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(statement);

        // Act
        courseRepo.createCourse(name, duration, theme, lecturer, status);

        // Assert
        verify(connection).prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS));
        verify(statement).setString(eq(1), eq(name));
        verify(statement).setInt(eq(2), eq(duration));
        verify(statement).setInt(eq(3), eq(theme));
        verify(statement).setInt(eq(4), eq(lecturer));
        verify(statement).setInt(eq(5), eq(status.getId_status()));
        verify(statement).executeUpdate();
        verify(connection).commit();
        verify(connection, never()).rollback();
    }

    @Test
    public void updateCourse() throws SQLException {
        PowerMockito.mockStatic(ConnectionPool.class);
        // Arrange
        int courseId = 1;
        String name = "Test Course";
        int duration = 5;
        int theme = 1;
        int lecturer = 1;
        int status = 1;

        when(courseRepo.findCourseById(courseId)).thenReturn(null);

        // Act and Assert
        Assertions.assertThrows(SQLDataException.class, () -> {
            courseRepo.updateCourse(courseId, name, duration, theme, lecturer, status);
        });
    }

    @Test
    public void deleteCourseByIdCourse() {
    }

    @Test
    public void findUserCoursesByUserIdAndStatus() {
    }

    @Test
    public void findCoursesToRegisterByUserId() {
    }

    @Test
    public void findSortedCourses() {
    }

    @Test
    public void getNumberOfRecords() {
    }
}