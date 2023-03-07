package com.taras_overmind.epam_final_project.command.lecturer;

import com.taras_overmind.epam_final_project.Path;
import com.taras_overmind.epam_final_project.command.Command;
import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.ForwardResult;
import com.taras_overmind.epam_final_project.context.AppContext;
import com.taras_overmind.epam_final_project.db.dto.StudentCourseDTO;
import com.taras_overmind.epam_final_project.db.service.StudentCourseService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class GetLecturerPageCommand extends Command {
    public static final Logger LOG = Logger.getLogger(GetLecturerPageCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward) throws IOException, ServletException {
        LOG.trace("Start tracing GetLecturerPageCommand");

        StudentCourseService studentCourseService= AppContext.getInstance(request).getStudentCourseService();
        HttpSession session = request.getSession();
        int id = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        List<StudentCourseDTO> list1 = studentCourseService.findStudentsByUserId(id, false);
        List<StudentCourseDTO> list2 = studentCourseService.findStudentsByUserId(id, true);


        session.setAttribute("result1", list1);
        session.setAttribute("result2", list2);


        return new ForwardResult(Path.PAGE_LECTURER);

    }
}