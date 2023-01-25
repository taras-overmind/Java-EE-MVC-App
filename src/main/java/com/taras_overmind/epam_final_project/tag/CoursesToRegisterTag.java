//package com.taras_overmind.epam_final_project.tag;
//
//import com.taras_overmind.epam_final_project.db.dto.CourseInfoDTO;
//import org.apache.log4j.Logger;
//import com.taras_overmind.epam_final_project.db.Query;
//import com.taras_overmind.epam_final_project.db.ConnectionPool;
//import javax.servlet.http.HttpSession;
//import javax.servlet.jsp.JspException;
//import javax.servlet.jsp.tagext.TagSupport;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.*;
//
//public class CoursesToRegisterTag extends TagSupport {
//
//    public static final Logger LOG = Logger.getLogger(CoursesToRegisterTag.class);
//
//    private String status;
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    @Override
//    public int doStartTag() throws JspException {
//        LOG.trace("Tracing CoursesToRegisterTag");
//        HttpSession session = pageContext.getSession();
//        CourseInfoDTO courseInfoDTO;
//        List<CourseInfoDTO> courses = new ArrayList<>();
//
//        try (Connection connection = ConnectionPool.getConnection()) {
//            if (connection != null) {
//                PreparedStatement statement = connection.prepareStatement(
//                            Query.SELECT_COURSES_TO_REGISTER);
//
//                connection.setAutoCommit(false);
//                statement.setString(1, String.valueOf(session.getAttribute("id")));
//                statement.execute();
//                ResultSet resultSet = statement.getResultSet();
//                while (resultSet.next()) {
//                    courseInfoDTO = new CourseInfoDTO();
//                    courseInfoDTO.setCourseId(resultSet.getInt("id_course"));
//                    courseInfoDTO.setLecturerName(resultSet.getString("surname")+" "+
//                            resultSet.getString("name")+" "+resultSet.getString("patronymic"));
//                    courseInfoDTO.setDuration(resultSet.getInt("duration"));
//                    courseInfoDTO.setCourseName(resultSet.getString("name_course"));
//                    courseInfoDTO.setThemeName(resultSet.getString("name_theme"));
//                    courses.add(courseInfoDTO);
//                }
//                resultSet.close();
//            }
//        } catch (SQLException ex) {
//            LOG.info(ex.getLocalizedMessage());
//        }
//        session.setAttribute("result", courses);
//
//        return EVAL_PAGE;
//    }
//}