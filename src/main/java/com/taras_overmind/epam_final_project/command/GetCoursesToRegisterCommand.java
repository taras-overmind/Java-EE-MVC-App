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

public class GetCoursesToRegisterCommand extends Command {
    public static final Logger LOG = Logger.getLogger(GetCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward) throws IOException, ServletException {
        LOG.trace("Start tracing GetCoursesToRegisterCommand");
        HttpSession session = request.getSession();
        CourseInfoDTO courseInfoDTO;
        List<CourseInfoDTO> courses = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(
                        Query.SELECT_COURSES_TO_REGISTER);

                connection.setAutoCommit(false);
                statement.setString(1, String.valueOf(session.getAttribute("id")));
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    courseInfoDTO = new CourseInfoDTO();
                    courseInfoDTO.setCourseId(resultSet.getInt("id_course"));
                    courseInfoDTO.setLecturerName(resultSet.getString("surname")+" "+
                            resultSet.getString("name")+" "+resultSet.getString("patronymic"));
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
        session.setAttribute("result", courses);

        return new ForwardResult("/WEB-INF/jsp/coursesToRegister.jsp");

    }
}