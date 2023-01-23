package com.taras_overmind.epam_final_project.tag;

import com.taras_overmind.epam_final_project.db.dto.CourseInfoDTO;
import org.apache.log4j.Logger;
import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.dao.ConnectionPool;
import com.taras_overmind.epam_final_project.db.dto.CourseDTO;
import com.taras_overmind.epam_final_project.db.dto.LecturerDTO;
import com.taras_overmind.epam_final_project.db.dto.ThemeDTO;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CoursesTag extends TagSupport {

    public static final Logger LOG = Logger.getLogger(CoursesTag.class);

    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int doStartTag() throws JspException {
        LOG.trace("Tracing CoursesTag");
        HttpSession session = pageContext.getSession();
        CourseInfoDTO courseInfoDTO;
        List<CourseInfoDTO> courses = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                PreparedStatement statement;

                if (session.getAttribute("sort") != null) {
                    StringBuilder query;
                    if (session.getAttribute("idTheme") != null && session.getAttribute("idLecturer") != null) {
                        query = new StringBuilder(Query.SELECT_SORTED_COURSES_BY_LECTURER_AND_THEME);
                        query.append(" ").append(session.getAttribute("sort")).append(" ").append(session.getAttribute("sorting"));
                        statement = connection.prepareStatement(query.toString());

                        statement.setString(1, String.valueOf(session.getAttribute("idLecturer")));
                        statement.setString(2, String.valueOf(session.getAttribute("idTheme")));
                    } else if (session.getAttribute("idTheme") != null) {
                        query = new StringBuilder(Query.SELECT_SORTED_COURSES_BY_THEME);
                        query.append(" ").append(session.getAttribute("sort")).append(" ").append(session.getAttribute("sorting"));
                        statement = connection.prepareStatement(query.toString());

                        statement.setString(1, String.valueOf(session.getAttribute("idTheme")));
                    } else if (session.getAttribute("idLecturer") != null) {
                        query = new StringBuilder(Query.SELECT_SORTED_COURSES_BY_LECTURER);

                        query.append(" ").append(session.getAttribute("sort")).append(" ").append(session.getAttribute("sorting"));
                        statement = connection.prepareStatement(query.toString());

                        statement.setString(1, String.valueOf(session.getAttribute("idLecturer")));
                    } else {
                        query = new StringBuilder(Query.SELECT_SORTED_COURSES);

                        query.append(" ").append(session.getAttribute("sort")).append(" ").append(session.getAttribute("sorting"));
                        statement = connection.prepareStatement(query.toString());
                    }

                } else {
                    if (session.getAttribute("idTheme") != null && session.getAttribute("idLecturer") != null) {
                        statement = connection.prepareStatement(
                                Query.SELECT_COURSES_BY_LECTURER_AND_THEME);
                        statement.setString(1, String.valueOf(session.getAttribute("idLecturer")));
                        statement.setString(2, String.valueOf(session.getAttribute("idTheme")));
                    } else if (session.getAttribute("idTheme") != null) {
                        statement = connection.prepareStatement(
                                Query.SELECT_COURSES_BY_THEME);
                        statement.setString(1, String.valueOf(session.getAttribute("idTheme")));

                    } else if (session.getAttribute("idLecturer") != null) {
                        statement = connection.prepareStatement(
                                Query.SELECT_COURSES_BY_LECTURER);
                        statement.setString(1, String.valueOf(session.getAttribute("idLecturer")));
                    } else {
                        statement = connection.prepareStatement(
                                Query.SELECT_COURSES);
                    }
                }
                connection.setAutoCommit(false);
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    courseInfoDTO = new CourseInfoDTO();
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
            }
        } catch (SQLException ex) {
            LOG.info(ex.getLocalizedMessage());
        }
        session.setAttribute("result", courses);

        return EVAL_PAGE;
    }
}