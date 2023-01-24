package com.taras_overmind.epam_final_project.tag;

import com.taras_overmind.epam_final_project.db.dto.StudentCourseDTO;
import org.apache.log4j.Logger;
import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.dao.ConnectionPool;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

import javax.servlet.jsp.tagext.TagSupport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class LecturerTag extends TagSupport {

    public static final Logger LOG = Logger.getLogger(CoursesTag.class);

    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int doStartTag() throws JspException {
        LOG.trace("Tracing CoursesTag");
        HttpSession session = pageContext.getSession();
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

        return EVAL_PAGE;
    }
}