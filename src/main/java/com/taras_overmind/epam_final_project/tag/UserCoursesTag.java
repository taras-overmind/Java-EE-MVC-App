package com.taras_overmind.epam_final_project.tag;

import com.taras_overmind.epam_final_project.db.dto.CourseInfoDTO;
import org.apache.log4j.Logger;
import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.dao.ConnectionPool;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserCoursesTag extends TagSupport {

    public static final Logger LOG = Logger.getLogger(UserCoursesTag.class);

    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int doStartTag() throws JspException {
        LOG.trace("Tracing UserCoursesTag");
        HttpSession session = pageContext.getSession();
        CourseInfoDTO courseInfoDTO=null;
        List<CourseInfoDTO> courses = new ArrayList<>();
        boolean finished = status.equals("4");

        try (Connection connection = ConnectionPool.getConnection()) {
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
                statement.setString(1, String.valueOf(session.getAttribute("id")));
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                session.setAttribute("res", resultSet);
                while (resultSet.next()) {
                    courseInfoDTO = new CourseInfoDTO();
                    courseInfoDTO.setLecturerName(resultSet.getString("surname")+" "+
                            resultSet.getString("name")+" "+resultSet.getString("patronymic"));
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
        session.setAttribute("result", courses);

        return EVAL_PAGE;
    }
}