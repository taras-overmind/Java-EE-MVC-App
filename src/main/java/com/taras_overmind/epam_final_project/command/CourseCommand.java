package com.taras_overmind.epam_final_project.command;

import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.RedirectResult;
import org.apache.juli.logging.Log;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;


public class CourseCommand extends Command {

    public static final Logger LOG = Logger.getLogger(CourseCommand.class);

    private static final long serialVersionUID = 5908769609880924971L;

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String redirect)
            throws IOException, ServletException {
        LOG.trace("Starting trace CourseCommand");
        HttpSession session = request.getSession();
        String forward = "?command=getCoursesCommand";
        if (session.getAttribute("user") != null) {
            List<String> fields = Arrays.asList("id_course", "name_course", "duration", "name_theme",
                    "surname", "name", "patronymic", "count", "name_status");
//            String state = String.valueOf(session.getAttribute("state"));
            String sort = null;
            String idTheme;
            String idLecturer;
//            if (Integer.parseInt(state) == 0) {
//                request.setAttribute("errorMessage", Errors.ERR_LOCKED);
//                forward = Path.PAGE_ERROR_PAGE;
//            }
            if (request.getParameter("idTheme") != null) {
                idTheme = new String(request.getParameter("idTheme"));
                LOG.trace("Theme id: "+idTheme);
                if (idTheme.equals("All themes") || idTheme.equals("Всі теми")) {
                    idTheme = null;
                }
                session.setAttribute("idTheme", idTheme);
            }

            if (request.getParameter("idLecturer") != null) {
                idLecturer = new String(request.getParameter("idLecturer"));
                LOG.trace("Lecturer id: "+idLecturer);
                if (idLecturer.equals("All lecturers") || idLecturer.equals("Всі викладачі")) {
                    idLecturer = null;
                }
                session.setAttribute("idLecturer", idLecturer);
            }

            if ((session.getAttribute("sort") != null) && (request.getParameter("sort") != null)) {
                sort = String.valueOf(session.getAttribute("sort"));
                LOG.trace("Sorting by: "+sort);

            }

            if (request.getParameter("sort") != null) {
                if (request.getParameter("sort").equals(sort)) {
                    if (session.getAttribute("sorting") != null) {
                        if (session.getAttribute("sorting").equals("ASC")) {
                            session.setAttribute("sorting", "DESC");
                        }
                        else {
                            session.setAttribute("sorting", "ASC");
                        }
                        forward = "?command=getCoursesCommand";
                    }
                }
                else {
                    session.setAttribute("sorting", "DESC");
                    forward = "?command=getCoursesCommand";
                    for (String field : fields) {
                        if (field.equals(request.getParameter("sort"))) {
                            session.setAttribute("sort", request.getParameter("sort"));
                        }
                    }
                }
            }

        }
        else {
            LOG.trace("User not logged");
            forward = "?command=getLoginCommand";
        }
        return new RedirectResult(forward);
    }
}