package com.taras_overmind.epam_final_project.command;

import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.ForwardResult;
import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.dao.ConnectionPool;
import com.taras_overmind.epam_final_project.db.dto.CourseInfoDTO;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetCoursesCommand extends Command {
    public static final Logger LOG = Logger.getLogger(GetCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward) throws IOException, ServletException {
        LOG.trace("Start tracing GetCommand");

        HttpSession session = request.getSession();
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

        return new ForwardResult("/WEB-INF/jsp/courses.jsp");

    }
}