package com.taras_overmind.epam_final_project.command;

import com.taras_overmind.epam_final_project.command.commandResult.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;


public abstract class Command implements Serializable {

    @Serial
    private static final long serialVersionUID = 2130509003029456001L;


    public abstract CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;


    @Override
    public final String toString() {
        return getClass().getSimpleName();
    }

}
