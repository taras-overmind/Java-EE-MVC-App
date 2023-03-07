package com.taras_overmind.epam_final_project;

import javax.servlet.http.HttpServletRequest;

public class Utils {
    public static String getCurrentURL(HttpServletRequest request){
        return request.getContextPath() + "?" + request.getParameter("url");
    }
}
