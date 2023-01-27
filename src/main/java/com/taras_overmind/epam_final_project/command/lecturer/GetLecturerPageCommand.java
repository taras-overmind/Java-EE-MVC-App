package com.taras_overmind.epam_final_project.command.lecturer;

import com.taras_overmind.epam_final_project.command.Command;
import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.ForwardResult;
import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.ConnectionPool;
import com.taras_overmind.epam_final_project.db.dto.StudentCourseDTO;
import com.taras_overmind.epam_final_project.db.repository.StudentCourseRepo;
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
        LOG.trace("Start tracing GetLecturerPageCommand");

        HttpSession session = request.getSession();
        int id = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        List<StudentCourseDTO> list1 = new StudentCourseRepo().findStudentsByUserId(id, false);
        List<StudentCourseDTO> list2 = new StudentCourseRepo().findStudentsByUserId(id, true);


        session.setAttribute("result1", list1);
        session.setAttribute("result2", list2);


        return new ForwardResult("/WEB-INF/jsp/lecturer.jsp");

    }
}