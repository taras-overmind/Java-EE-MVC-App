package com.taras_overmind.epam_final_project.filter;

import org.apache.log4j.Logger;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;


@WebFilter(filterName = "EncodingFilter")
public class EncodingFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(EncodingFilter.class);
    private static final String DEFAULT_ENCODING = "UTF-8";
    private String encoding;

    public void destroy() {
        LOG.trace("=========================================================================");
        LOG.info("Filter destruction starts");
        LOG.info("Filter destruction finished");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        LOG.info("Filter starts");
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        resp.setCharacterEncoding(encoding);

        LOG.info("Filter finished");
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        LOG.info("Filter initialization starts");
        encoding = config.getInitParameter("encoding");
        if (encoding == null) {
            encoding = DEFAULT_ENCODING;
            LOG.info("Encoding from Filter Config --> " + encoding);
        }
        LOG.info("Filter initialization finished");
    }

}
