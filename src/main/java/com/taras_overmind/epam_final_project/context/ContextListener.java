package com.taras_overmind.epam_final_project.context;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class ContextListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(ContextListener.class);

    public ContextListener() {}

    public void contextInitialized(ServletContextEvent event) {
        LOG.trace("Servlet context initialization starts");

        ServletContext servletContext = event.getServletContext();
        servletContext.setAttribute("app_context", new AppContext());
        initLog4J(servletContext);
        initCommandContainer();

        LOG.trace("Servlet context initialization finished");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        LOG.trace("Servlet context destroyed");
    }

    private void initCommandContainer() {
        try {
            Class.forName("com.taras_overmind.epam_final_project.command.CommandFactory");
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Cannot initialize Command Container");
        }
    }


    private void initLog4J(ServletContext servletContext) {
        LOG.trace("Log4J initialization started");

        try {
            PropertyConfigurator.configure(servletContext.getRealPath("WEB-INF/log4j.properties"));
            LOG.debug("Log4j has been initialized");
        } catch (Exception ex) {
            LOG.trace("Cannot configure Log4j");
            ex.printStackTrace();
        }

        LOG.trace("Log4J initialization finished");
    }

}
