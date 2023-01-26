package com.taras_overmind.epam_final_project.command.lecturer;

import com.taras_overmind.epam_final_project.command.Command;
import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.ForwardResult;
import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.ConnectionPool;
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
    public static final Logger LOG = Logger.getLogger(GetLecturerPageCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward) throws IOException, ServletException {
        LOG.trace("Start tracing GetLoginPageCommand");

        HttpSession session = request.getSession();
        StudentCourseDTO studentCourseDTO;
        List<StudentCourseDTO> list1 = new ArrayList<>();
        List<StudentCourseDTO> list2 = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                PreparedStatement statement1 = connection.prepareStatement(Query.SELECT_STUDENTS_WITHOUT_MARK);
                PreparedStatement statement2 = connection.prepareStatement(Query.SELECT_STUDENTS_WITH_MARK);
                statement1.setString(1, String.valueOf(session.getAttribute("id")));
                statement2.setString(1, String.valueOf(session.getAttribute("id")));
                connection.setAutoCommit(false);
                statement1.execute();
                statement2.execute();
                ResultSet resultSet = statement1.getResultSet();
                while (resultSet.next()) {
                    studentCourseDTO = new StudentCourseDTO();
                    studentCourseDTO.setStudentName(resultSet.getString("surname")+" "+resultSet.getString("name")
                            + " "+resultSet.getString("patronymic"));
                    studentCourseDTO.setCourseName(resultSet.getString("name_course"));
                    studentCourseDTO.setId(resultSet.getInt("id_student_course"));
                    list1.add(studentCourseDTO);
                }
                resultSet=statement2.getResultSet();
                while (resultSet.next()) {
                    studentCourseDTO = new StudentCourseDTO();
                    studentCourseDTO.setStudentName(resultSet.getString("surname")+" "+resultSet.getString("name")
                            + " "+resultSet.getString("patronymic"));
                    studentCourseDTO.setCourseName(resultSet.getString("name_course"));
                    studentCourseDTO.setId(resultSet.getInt("id_student_course"));
                    studentCourseDTO.setMark(resultSet.getInt("mark"));
                    list2.add(studentCourseDTO);
                }
                resultSet.close();
            }
        } catch (SQLException ex) {
            LOG.info(ex.getLocalizedMessage());
        }
        session.setAttribute("result1", list1);
        session.setAttribute("result2", list2);


        return new ForwardResult("/WEB-INF/jsp/lecturer.jsp");

    }
}