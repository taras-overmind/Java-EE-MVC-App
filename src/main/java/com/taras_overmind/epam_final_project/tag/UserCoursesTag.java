package com.taras_overmind.epam_final_project.tag;

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
        List<LecturerDTO> lecturers = new ArrayList<>();
        List<ThemeDTO> themes = new ArrayList<>();
        List<CourseDTO> courses = new ArrayList<>();
        List<String> marks = new ArrayList<>();
        LecturerDTO lecturer;
        CourseDTO course;
        ThemeDTO theme;
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
                while (resultSet.next()) {
                    lecturer = new LecturerDTO();
                    course = new CourseDTO();
                    theme = new ThemeDTO();
                    lecturer.setName(resultSet.getString("name"));
                    lecturer.setSurname(resultSet.getString("surname"));
                    lecturer.setPatronymic(resultSet.getString("patronymic"));
                    lecturers.add(lecturer);
                    course.setDuration(resultSet.getInt("duration"));
                    course.setCourseName(resultSet.getString("name_course"));
                    courses.add(course);
                    theme.setNameTheme(resultSet.getString("name_theme"));
                    themes.add(theme);
                    if (finished)
                        marks.add(resultSet.getString("mark"));
                }
                resultSet.close();
            }
        } catch (SQLException ex) {
            LOG.info(ex.getLocalizedMessage());
        }
        JspWriter out = pageContext.getOut();
        Iterator<LecturerDTO> lecturersIt = lecturers.iterator();
        Iterator<CourseDTO> coursesIt = courses.iterator();
        Iterator<ThemeDTO> themesIt = themes.iterator();
        Iterator<String> marksIt = marks.iterator();
        StringBuffer table = new StringBuffer();
        while (lecturersIt.hasNext()) {
            lecturer = lecturersIt.next();
            course = coursesIt.next();
            theme = themesIt.next();
            table.append("<tr><td>").append(course.getCourseName()).append("</td><td>").append(course.getDuration())
                    .append("</td><td>").append(theme.getNameTheme()).append("</td><td>").append(lecturer.getSurname())
                    .append(" ").append(lecturer.getName()).append(" ").append(lecturer.getPatronymic())
                    .append("</td>");
            if (finished)
                table.append("<td>").append(marksIt.next()).append("</td>");
            table.append("</tr>");
        }
        try {
            out.println(table);
        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage());
        }

        return EVAL_PAGE;
    }
}