//package com.taras_overmind.epam_final_project.tag;
//
//import com.taras_overmind.epam_final_project.db.dto.CourseInfoDTO;
//import com.taras_overmind.epam_final_project.db.repository.CourseRepo;
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
//public class StudentCoursesTag extends TagSupport {
//
//    public static final Logger LOG = Logger.getLogger(StudentCoursesTag.class);
//
//    private String status;
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    @Override
//    public int doStartTag() throws JspException {
//        LOG.trace("Tracing UserCoursesTag");
//        HttpSession session = pageContext.getSession();
//        List<CourseInfoDTO> courses;
//        courses=new CourseRepo().findCoursesByUserIdAndStatus(status, Integer.parseInt(String.valueOf(session.getAttribute("id"))));
//
//        session.setAttribute("result", courses);
//
//        return EVAL_PAGE;
//    }
//}