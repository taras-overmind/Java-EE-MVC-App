package com.taras_overmind.epam_final_project.command;

import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.ForwardResult;
import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.dao.ConnectionPool;
import com.taras_overmind.epam_final_project.db.dto.CourseInfoDTO;
import com.taras_overmind.epam_final_project.db.dto.StudentCourseDTO;
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

public class GetLecturerPageCommand extends Command {
    public static final Logger LOG = Logger.getLogger(GetCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward) throws IOException, ServletException {
        LOG.trace("Start tracing GetLoginCommand");

        HttpSession session = request.getSession();
        StudentCourseDTO studentCourseDTO;
        List<StudentCourseDTO> list = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(Query.SELECT_STUDENTS_WITHOUT_MARK);
                statement.setString(1, String.valueOf(session.getAttribute("id")));
                connection.setAutoCommit(false);
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    studentCourseDTO = new StudentCourseDTO();
                    studentCourseDTO.setStudentName(resultSet.getString("surname")+" "+resultSet.getString("name")
                            + " "+resultSet.getString("patronymic"));
                    studentCourseDTO.setCourseName(resultSet.getString("name_course"));
                    studentCourseDTO.setId(resultSet.getInt("id_student_course"));
                    list.add(studentCourseDTO);
                }
                resultSet.close();
            }
        } catch (SQLException ex) {
            LOG.info(ex.getLocalizedMessage());
        }
        session.setAttribute("result", list);

        return new ForwardResult("/WEB-INF/jsp/lecturer.jsp");

    }
}