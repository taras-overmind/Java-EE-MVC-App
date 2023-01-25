package com.taras_overmind.epam_final_project.command;

import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.RedirectResult;
import com.taras_overmind.epam_final_project.db.repository.CourseRepo;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCourseCommand extends Command{
    public static final Logger LOG = Logger.getLogger(DeleteCourseCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward) throws IOException, ServletException {
        LOG.trace("Start tracing DeleteCourseCommand");
        new CourseRepo().deleteCourseByIdCourse(Integer.parseInt(request.getParameter("id_course")));

        return new RedirectResult("?command=getCoursesCommand");

    }
}
