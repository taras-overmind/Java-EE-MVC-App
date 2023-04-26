package com.taras_overmind.epam_final_project.command;


import com.taras_overmind.epam_final_project.command.admin.*;
import com.taras_overmind.epam_final_project.command.common.*;
import com.taras_overmind.epam_final_project.command.lecturer.GetLecturerPageCommand;
import com.taras_overmind.epam_final_project.command.lecturer.MarkStudentCommand;
import com.taras_overmind.epam_final_project.command.student.GetCoursesToRegisterCommand;
import com.taras_overmind.epam_final_project.command.student.GetStudentPageCommand;
import com.taras_overmind.epam_final_project.command.student.RegisterOnCourseCommand;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

public class CommandFactory {


    private static final Logger LOG = Logger.getLogger(CommandFactory.class);

    private static final Map<String, Command> commands = new TreeMap<>();

    static {
        //admin
        commands.put("changeStateCommand", new ChangeStateCommand());
        commands.put("createCourseCommand", new CreateCourseCommand());
        commands.put("deleteCourseCommand", new DeleteCourseCommand());
        commands.put("editCourseCommand", new EditCourseCommand());
        commands.put("getCreateCourseCommand", new GetCreateCoursePageCommand());
        commands.put("getEditCourseCommand", new GetEditCoursePageCommand());
        commands.put("getUsersPageCommand", new GetUsersPageCommand());

        //common
        commands.put("noCommand", new GetCoursesPageCommand());
        commands.put("getCoursesCommand", new GetCoursesPageCommand());
        commands.put("getLoginCommand", new GetLoginPageCommand());
        commands.put("getRegisterCommand", new GetRegisterPageCommand());
        commands.put("logOutCommand", new LogOutCommand());
        commands.put("registerCommand", new RegisterCommand());
        commands.put("courseCommand", new SortCoursesCommand());
        commands.put("languageCommand", new LanguageCommand());
        commands.put("loginCommand", new LoginCommand());

        //student
        commands.put("getCoursesToRegisterCommand", new GetCoursesToRegisterCommand());
        commands.put("getStudentCommand", new GetStudentPageCommand());
        commands.put("registerOnCourseCommand", new RegisterOnCourseCommand());

        //lecturer
        commands.put("getLecturerCommand", new GetLecturerPageCommand());
        commands.put("markStudentCommand", new MarkStudentCommand());


    }


    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            LOG.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }
}
