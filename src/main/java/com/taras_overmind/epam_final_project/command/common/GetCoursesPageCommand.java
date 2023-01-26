package com.taras_overmind.epam_final_project.command.common;

import com.taras_overmind.epam_final_project.command.Command;
import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.ForwardResult;
import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.ConnectionPool;
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

public class GetCoursesPageCommand extends Command {
    public static final Logger LOG = Logger.getLogger(GetCoursesPageCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward) throws IOException, ServletException {
        LOG.trace("Start tracing GetCoursesPageCommand");

        HttpSession session = request.getSession();
        CourseInfoDTO courseInfoDTO;
        List<CourseInfoDTO> courses = new ArrayList<>();
        int page=1;
        int noOfRecords =0;
        final int recordsPerPage=5;
        LOG.trace(0);
        if (request.getParameter("page") != null ) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        String ending = " LIMIT " + recordsPerPage * (page - 1) + ", " + recordsPerPage;


        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                PreparedStatement statement;
                StringBuilder query;
                if (session.getAttribute("sort") != null) {
                    if (session.getAttribute("idTheme") != null && session.getAttribute("idLecturer") != null) {
                        query = new StringBuilder(Query.SELECT_SORTED_COURSES_BY_LECTURER_AND_THEME);
                        query.append(" ").append(session.getAttribute("sort")).append(" ").append(session.getAttribute("sorting"));
                        query.append(ending);
                        statement = connection.prepareStatement(query.toString());
                        statement.setString(1, String.valueOf(session.getAttribute("idLecturer")));
                        statement.setString(2, String.valueOf(session.getAttribute("idTheme")));

                    } else if (session.getAttribute("idTheme") != null) {
                        query = new StringBuilder(Query.SELECT_SORTED_COURSES_BY_THEME);
                        query.append(" ").append(session.getAttribute("sort")).append(" ").append(session.getAttribute("sorting"));
                        query.append(ending);
                        statement = connection.prepareStatement(query.toString());
                        statement.setString(1, String.valueOf(session.getAttribute("idTheme")));

                    } else if (session.getAttribute("idLecturer") != null) {
                        query = new StringBuilder(Query.SELECT_SORTED_COURSES_BY_LECTURER);
                        query.append(" ").append(session.getAttribute("sort")).append(" ").append(session.getAttribute("sorting"));
                        query.append(ending);
                        statement = connection.prepareStatement(query.toString());
                        statement.setString(1, String.valueOf(session.getAttribute("idLecturer")));

                    } else {
                        query = new StringBuilder(Query.SELECT_SORTED_COURSES);
                        query.append(" ").append(session.getAttribute("sort")).append(" ").append(session.getAttribute("sorting"));
                        query.append(ending);
                        statement = connection.prepareStatement(query.toString());

                    }

                } else {
                    if (session.getAttribute("idTheme") != null && session.getAttribute("idLecturer") != null) {
                        query= new StringBuilder(Query.SELECT_COURSES_BY_LECTURER_AND_THEME);
                        query.append(ending);
                        statement = connection.prepareStatement(query.toString());
                        statement.setString(1, String.valueOf(session.getAttribute("idLecturer")));
                        statement.setString(2, String.valueOf(session.getAttribute("idTheme")));
                    } else if (session.getAttribute("idTheme") != null) {
                        query= new StringBuilder(Query.SELECT_COURSES_BY_THEME);
                        query.append(ending);
                        statement = connection.prepareStatement(query.toString());
                        statement.setString(1, String.valueOf(session.getAttribute("idTheme")));

                    } else if (session.getAttribute("idLecturer") != null) {
                        query= new StringBuilder(Query.SELECT_COURSES_BY_LECTURER);
                        query.append(ending);
                        statement = connection.prepareStatement(query.toString());
                        statement.setString(1, String.valueOf(session.getAttribute("idLecturer")));
                    } else {
                        query= new StringBuilder(Query.SELECT_COURSES);
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
                resultSet=connection.createStatement().executeQuery("SELECT FOUND_ROWS()");
                if(resultSet.next())
                    noOfRecords= resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            LOG.info(ex.getLocalizedMessage());
        }
        session.setAttribute("result", courses);
        int noOfPages=(int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        session.setAttribute("noOfPages", noOfPages);
        session.setAttribute("currentPage", page);

        return new ForwardResult("/WEB-INF/jsp/courses.jsp");

    }
}