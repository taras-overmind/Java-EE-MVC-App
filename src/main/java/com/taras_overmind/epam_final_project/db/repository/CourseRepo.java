package com.taras_overmind.epam_final_project.db.repository;

import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.dao.ConnectionPool;
import com.taras_overmind.epam_final_project.db.dto.CourseDTO;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseRepo {

    public static final Logger LOG = Logger.getLogger(CourseRepo.class);
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

}
