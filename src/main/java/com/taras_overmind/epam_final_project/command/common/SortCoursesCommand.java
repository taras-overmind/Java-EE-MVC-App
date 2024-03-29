package com.taras_overmind.epam_final_project.command.common;

import com.taras_overmind.epam_final_project.Path;
import com.taras_overmind.epam_final_project.command.Command;
import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.RedirectResult;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serial;
import java.util.Arrays;
import java.util.List;


public class SortCoursesCommand extends Command {

    public static final Logger LOG = Logger.getLogger(SortCoursesCommand.class);

    @Serial
    private static final long serialVersionUID = 5908769609880924971L;

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String redirect)
            throws IOException, ServletException {
        LOG.trace("Starting trace SortCoursesCommand");

        HttpSession session = request.getSession();
        String forward = Path.COURSES;
        String sort = null;
        String idTheme;
        String idLecturer;
        List<String> fields = Arrays.asList("id_course", "name_course", "duration", "name_theme",
                "surname", "name", "patronymic", "count", "name_status");
//            String state = String.valueOf(session.getAttribute("state"));
//            if (Integer.parseInt(state) == 0) {
//                request.setAttribute("errorMessage", Errors.ERR_LOCKED);
//                forward = Path.PAGE_ERROR_PAGE;
//            }
        if (request.getParameter("idTheme") != null) {
            idTheme = request.getParameter("idTheme");
            if (idTheme.equals("All themes") || idTheme.equals("Всі теми")) {
                idTheme = null;
            }
            session.setAttribute("idTheme", idTheme);

            LOG.trace("Theme id: " + idTheme);
        }

        if (request.getParameter("idLecturer") != null) {
            idLecturer = request.getParameter("idLecturer");
            if (idLecturer.equals("All lecturers") || idLecturer.equals("Всі викладачі")) {
                idLecturer = null;
            }
            session.setAttribute("idLecturer", idLecturer);

            LOG.trace("Lecturer id: " + idLecturer);
        }

        if ((session.getAttribute("sort") != null) && (request.getParameter("sort") != null)) {
            sort = String.valueOf(session.getAttribute("sort"));

            LOG.trace("Sorting by: " + sort);
        }

        if (request.getParameter("sort") != null) {
            if (request.getParameter("sort").equals(sort)) {
                if (session.getAttribute("sorting") != null) {
                    if (session.getAttribute("sorting").equals("ASC")) {
                        session.setAttribute("sorting", "DESC");
                    } else {
                        session.setAttribute("sorting", "ASC");
                    }
                }
            } else {
                session.setAttribute("sorting", "DESC");
                for (String field : fields) {
                    if (field.equals(request.getParameter("sort"))) {
                        session.setAttribute("sort", request.getParameter("sort"));
                    }
                }
            }
        }
        return new RedirectResult(forward);
    }
}