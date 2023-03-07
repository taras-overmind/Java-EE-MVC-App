package com.taras_overmind.epam_final_project.filter;

import com.taras_overmind.epam_final_project.db.Role;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class CommandAccessFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(CommandAccessFilter.class);

    private final Map<Role, List<String>> accessMap = new HashMap<>();
    private List<String> commons = new ArrayList<>();
//    private List<String> outOfControl = new ArrayList<>();

    public void destroy() {
        LOG.debug("CommandAccessFilter destructed");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        LOG.debug("Filter starts");

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;
        HttpSession session = httpReq.getSession();

        if (accessAllowed(request)) {
            LOG.debug("Filter finished");

            chain.doFilter(request, response);
        } else {
            String errorMessage = "You do not have permission to access the requested resource";
            session.setAttribute("wrongData", errorMessage);

            LOG.trace("Set the request attribute: errorMessage --> " + errorMessage);

            httpResp.sendRedirect(httpReq.getContextPath() + "?command=getLoginCommand");
        }


    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String commandName = request.getParameter("command");
        Role userRole;

        if (commandName == null || commandName.isEmpty()) {
            return false;
        }

        if (commons.contains(commandName)) {
            return true;
        }

        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            return false;
        }

        String role = String.valueOf(session.getAttribute("id_role"));
        userRole = switch (role) {
            case "0" -> Role.ADMIN;
            case "2" -> Role.LECTURER;
            case "1" -> Role.STUDENT;
            default -> null;
        };

        if (userRole == null)
            return false;

        return accessMap.get(userRole).contains(commandName);
    }

    public void init(FilterConfig fConfig) throws ServletException {
        LOG.debug("Filter initialization starts");

        // roles
        accessMap.put(Role.ADMIN, asList(fConfig.getInitParameter("admin")));
        accessMap.put(Role.LECTURER, asList(fConfig.getInitParameter("lecturer")));
        accessMap.put(Role.STUDENT, asList(fConfig.getInitParameter("student")));


        // commons
        commons = asList(fConfig.getInitParameter("common"));

        LOG.debug("Filter initialization finished");
    }

    private List<String> asList(String str) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list;
    }

}