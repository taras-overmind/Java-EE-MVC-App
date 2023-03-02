package com.taras_overmind.epam_final_project.command.admin;

import com.taras_overmind.epam_final_project.command.Command;
import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.ForwardResult;
import com.taras_overmind.epam_final_project.command.student.GetStudentPageCommand;
import com.taras_overmind.epam_final_project.db.dto.UserDTO;
import com.taras_overmind.epam_final_project.db.repository.UserRepo;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class GetUsersPageCommand extends Command {
    public static final Logger LOG = Logger.getLogger(GetStudentPageCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward)
            throws IOException, ServletException {
        LOG.trace("Start tracing GetUsersPageCommand");

        HttpSession session = request.getSession();
        List<UserDTO> list1 = new UserRepo().findUsers(true);
        List<UserDTO> list2 = new UserRepo().findUsers(false);

        session.setAttribute("result1", list1);
        session.setAttribute("result2", list2);


        return new ForwardResult("/WEB-INF/jsp/users.jsp");
    }
}
