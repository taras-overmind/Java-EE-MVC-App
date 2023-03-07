package com.taras_overmind.epam_final_project.db.repository;

import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.ConnectionPool;
import com.taras_overmind.epam_final_project.db.Status;
import com.taras_overmind.epam_final_project.db.dto.CourseDTO;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRepo {

    public static final Logger LOG = Logger.getLogger(CourseRepo.class);
    private int numberOfRecords =0;

    public void createCourse(String name, int duration, int theme, int lecturer, Status status) {
        LOG.trace("Starting tracing CourseRepo#createCourse");
        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(Query.CREATE_COURSE, Statement.RETURN_GENERATED_KEYS)) {
                    connection.setAutoCommit(false);
                    statement.setString(1, name);
                    statement.setInt(2, duration);
                    statement.setInt(3, theme);
                    statement.setInt(4, lecturer);
                    statement.setInt(5, status.getId_status());
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

    public void updateCourse(int id_course, String name, int duration, int theme, int lecturer, int status) {
        LOG.trace("Starting tracing CourseRepo#updateCourse");
        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(Query.UPDATE_COURSE)) {
                    connection.setAutoCommit(false);
                    statement.setString(1, name);
                    statement.setInt(2, duration);
                    statement.setInt(3, theme);
                    statement.setInt(4, lecturer);
                    statement.setInt(5, status);
                    statement.setInt(6, id_course);
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

    public void deleteCourseByIdCourse(int id) {
        LOG.trace("Starting tracing CourseRepo#deleteCourseByIdCourse");
        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(Query.DELETE_COURSE, Statement.RETURN_GENERATED_KEYS)) {
                    connection.setAutoCommit(false);
                    statement.setInt(1, id);
                    statement.executeUpdate();
                    connection.commit();
                }
                try (PreparedStatement statement = connection.prepareStatement(Query.DELETE_STUDENT_COURSE, Statement.RETURN_GENERATED_KEYS)) {
                    connection.setAutoCommit(false);
                    statement.setInt(1, id);
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

    public List<CourseDTO> findUserCoursesByUserIdAndStatus(String status, int id_user) {
        LOG.trace("Start tracing CourseRepo#findUserCoursesByUserIdAndStatus");
        CourseDTO courseDTO;
        List<CourseDTO> courses = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection()) {
            boolean finished = status.equals("4");
            if (connection != null) {
                PreparedStatement statement;
                if (!finished) {
                    statement = connection.prepareStatement(
                            Query.SELECT_INFO_ABOUT_COURSE_BY_USER_ID_AND_BY_COURSE_STATUS_ID);

                    statement.setString(2, status);
                } else {
                    statement = connection.prepareStatement(
                            Query.SELECT_FINISHED_COURSE_BY_USER_ID);
                }
                connection.setAutoCommit(false);
                statement.setInt(1, id_user);
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    courseDTO = new CourseDTO();
                    courseDTO.setLecturerName(resultSet.getString("surname") + " " +
                            resultSet.getString("name") + " " + resultSet.getString("patronymic"));
                    courseDTO.setDuration(resultSet.getInt("duration"));
                    courseDTO.setCourseName(resultSet.getString("name_course"));
                    courseDTO.setThemeName(resultSet.getString("name_theme"));
                    if (finished)
                        courseDTO.setCount(resultSet.getInt("mark"));
                    courses.add(courseDTO);
                }
                resultSet.close();
            }
        } catch (SQLException ex) {
            LOG.info(ex.getLocalizedMessage());
        }
        return courses;
    }

    public List<CourseDTO> findCoursesToRegisterByUserId(int id) {
        LOG.trace("Start tracing CourseRepo#findCoursesToRegisterByUserId");
        CourseDTO courseDTO;
        List<CourseDTO> courses = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(
                        Query.SELECT_COURSES_TO_REGISTER);

                connection.setAutoCommit(false);
                statement.setInt(1, id);
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    courseDTO = new CourseDTO();
                    courseDTO.setCourseId(resultSet.getInt("id_course"));
                    courseDTO.setLecturerName(resultSet.getString("surname") + " " +
                            resultSet.getString("name") + " " + resultSet.getString("patronymic"));
                    courseDTO.setDuration(resultSet.getInt("duration"));
                    courseDTO.setCourseName(resultSet.getString("name_course"));
                    courseDTO.setThemeName(resultSet.getString("name_theme"));
                    courses.add(courseDTO);
                }
                resultSet.close();
            }
        } catch (SQLException ex) {
            LOG.info(ex.getLocalizedMessage());
        }
        return courses;
    }


    public List<CourseDTO> findSortedCourses(Object sort, Object sorting, Object idLecturer, Object idTheme,
                                             String ending) {

        CourseDTO courseDTO;
        List<CourseDTO> courses = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                PreparedStatement statement;
                StringBuilder query;
                if (sort != null) {
                    if (idTheme != null && idLecturer != null) {
                        query = new StringBuilder(Query.SELECT_SORTED_COURSES_BY_LECTURER_AND_THEME);
                        query.append(" ").append(sort).append(" ").append(sorting);
                        query.append(ending);
                        statement = connection.prepareStatement(query.toString());
                        statement.setString(1, String.valueOf(idLecturer));
                        statement.setString(2, String.valueOf(idTheme));

                    } else if (idTheme != null) {
                        query = new StringBuilder(Query.SELECT_SORTED_COURSES_BY_THEME);
                        query.append(" ").append(sort).append(" ").append(sorting);
                        query.append(ending);
                        statement = connection.prepareStatement(query.toString());
                        statement.setString(1, String.valueOf(idTheme));

                    } else if (idLecturer != null) {
                        query = new StringBuilder(Query.SELECT_SORTED_COURSES_BY_LECTURER);
                        query.append(" ").append(sort).append(" ").append(sorting);
                        query.append(ending);
                        statement = connection.prepareStatement(query.toString());
                        statement.setString(1, String.valueOf(idLecturer));

                    } else {
                        query = new StringBuilder(Query.SELECT_SORTED_COURSES);
                        query.append(" ").append(sort).append(" ").append(sorting);
                        query.append(ending);
                        statement = connection.prepareStatement(query.toString());

                    }

                } else {
                    if (idTheme != null && idLecturer != null) {
                        query = new StringBuilder(Query.SELECT_COURSES_BY_LECTURER_AND_THEME);
                        query.append(ending);
                        statement = connection.prepareStatement(query.toString());
                        statement.setString(1, String.valueOf(idLecturer));
                        statement.setString(2, String.valueOf(idTheme));
                    } else if (idTheme != null) {
                        query = new StringBuilder(Query.SELECT_COURSES_BY_THEME);
                        query.append(ending);
                        statement = connection.prepareStatement(query.toString());
                        statement.setString(1, String.valueOf(idTheme));

                    } else if (idLecturer != null) {
                        query = new StringBuilder(Query.SELECT_COURSES_BY_LECTURER);
                        query.append(ending);
                        statement = connection.prepareStatement(query.toString());
                        statement.setString(1, String.valueOf(idLecturer));
                    } else {
                        query = new StringBuilder(Query.SELECT_COURSES);
                        query.append(ending);
                        statement = connection.prepareStatement(query.toString());
                    }
                }
                connection.setAutoCommit(false);
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    courseDTO = new CourseDTO();
                    courseDTO.setCourseId(resultSet.getInt("id_course"));
                    courseDTO.setCourseName(resultSet.getString("name_course"));
                    courseDTO.setDuration(resultSet.getInt("duration"));
                    courseDTO.setThemeName(resultSet.getString("name_theme"));
                    courseDTO.setLecturerName(resultSet.getString("surname") + " " +
                            resultSet.getString("name") + " " + resultSet.getString("patronymic"));
                    courseDTO.setStatusName(resultSet.getString("name_status"));
                    courseDTO.setCount(resultSet.getInt("COUNT"));
                    courses.add(courseDTO);
                }
                resultSet.close();
                resultSet = connection.createStatement().executeQuery("SELECT FOUND_ROWS()");
                if (resultSet.next())
                    numberOfRecords = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            LOG.info(ex.getLocalizedMessage());
        }
        return courses;
    }

    public int getNumberOfRecords() {
        return numberOfRecords;
    }

}
