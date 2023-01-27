package com.taras_overmind.epam_final_project.db.repository;

import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.ConnectionPool;
import com.taras_overmind.epam_final_project.db.dto.CourseDTO;
import com.taras_overmind.epam_final_project.db.dto.CourseInfoDTO;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRepo {

    public static final Logger LOG = Logger.getLogger(CourseRepo.class);
    private int noOfRecords=0;

    public List<CourseDTO> getAllCourses() {
        LOG.trace("Starting tracing CourseRepo#getAllCourses");
        List<CourseDTO> courses = new ArrayList<>();
        CourseDTO course;

        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(Query.SELECT_ALL_COURSES)) {
                    connection.setAutoCommit(false);
                    statement.execute();
                    ResultSet resultSet = statement.getResultSet();
                    while (resultSet.next()) {
                        course = new CourseDTO(resultSet.getInt("id_course"),
                                resultSet.getString("name_course"), resultSet.getInt("duration"),
                                resultSet.getInt("id_theme"), resultSet.getInt("id_lecturer"),
                                resultSet.getInt("id_status"));
                        courses.add(course);
                    }
                    resultSet.close();
                    connection.commit();
                } catch (SQLException ex) {
                    LOG.error(ex.getLocalizedMessage());
                    connection.rollback();
                }
            }
        } catch (SQLException ex) {
            LOG.info(ex.getLocalizedMessage());
        }
        return courses;
    }

    public void createCourse(String name, int duration, int theme, int lecturer, int status) {
        LOG.trace("Starting tracing CourseRepo#createCourse");
        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(Query.CREATE_COURSE, Statement.RETURN_GENERATED_KEYS)) {
                    connection.setAutoCommit(false);
                    statement.setString(1, name);
                    statement.setInt(2, duration);
                    statement.setInt(3, theme);
                    statement.setInt(4, lecturer);
                    statement.setInt(5, status);
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

    public List<CourseInfoDTO> findUserCoursesByUserIdAndStatus(String status, int id_user) {
        LOG.trace("Start tracing CourseRepo#findUserCoursesByUserIdAndStatus");
        CourseInfoDTO courseInfoDTO;
        List<CourseInfoDTO> courses = new ArrayList<>();
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
                    courseInfoDTO = new CourseInfoDTO();
                    courseInfoDTO.setLecturerName(resultSet.getString("surname") + " " +
                            resultSet.getString("name") + " " + resultSet.getString("patronymic"));
                    courseInfoDTO.setDuration(resultSet.getInt("duration"));
                    courseInfoDTO.setCourseName(resultSet.getString("name_course"));
                    courseInfoDTO.setThemeName(resultSet.getString("name_theme"));
                    if (finished)
                        courseInfoDTO.setCount(resultSet.getInt("mark"));
                    courses.add(courseInfoDTO);
                }
                resultSet.close();
            }
        } catch (SQLException ex) {
            LOG.info(ex.getLocalizedMessage());
        }
        return courses;
    }

    public List<CourseInfoDTO> findCoursesToRegisterByUserId(int id) {
        LOG.trace("Start tracing CourseRepo#findCoursesToRegisterByUserId");
        CourseInfoDTO courseInfoDTO;
        List<CourseInfoDTO> courses = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(
                        Query.SELECT_COURSES_TO_REGISTER);

                connection.setAutoCommit(false);
                statement.setInt(1, id);
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    courseInfoDTO = new CourseInfoDTO();
                    courseInfoDTO.setCourseId(resultSet.getInt("id_course"));
                    courseInfoDTO.setLecturerName(resultSet.getString("surname") + " " +
                            resultSet.getString("name") + " " + resultSet.getString("patronymic"));
                    courseInfoDTO.setDuration(resultSet.getInt("duration"));
                    courseInfoDTO.setCourseName(resultSet.getString("name_course"));
                    courseInfoDTO.setThemeName(resultSet.getString("name_theme"));
                    courses.add(courseInfoDTO);
                }
                resultSet.close();
            }
        } catch (SQLException ex) {
            LOG.info(ex.getLocalizedMessage());
        }
        return courses;
    }

    public int getNoOfRecords() {
        return noOfRecords;
    }

    public List<CourseInfoDTO> findSortedCourses(Object sort, Object sorting, Object idLecturer, Object idTheme,
                                                 String ending) {

        CourseInfoDTO courseInfoDTO;
        List<CourseInfoDTO> courses = new ArrayList<>();
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
                    courseInfoDTO = new CourseInfoDTO();
                    courseInfoDTO.setCourseId(resultSet.getInt("id_course"));
                    courseInfoDTO.setCourseName(resultSet.getString("name_course"));
                    courseInfoDTO.setDuration(resultSet.getInt("duration"));
                    courseInfoDTO.setThemeName(resultSet.getString("name_theme"));
                    courseInfoDTO.setLecturerName(resultSet.getString("surname") + " " +
                            resultSet.getString("name") + " " + resultSet.getString("patronymic"));
                    courseInfoDTO.setStatusName(resultSet.getString("name_status"));
                    courseInfoDTO.setCount(resultSet.getInt("COUNT"));
                    courses.add(courseInfoDTO);
                }
                resultSet.close();
                resultSet = connection.createStatement().executeQuery("SELECT FOUND_ROWS()");
                if (resultSet.next())
                    noOfRecords = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            LOG.info(ex.getLocalizedMessage());
        }
        return courses;
    }

}
