package com.taras_overmind.epam_final_project.command;


import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {


    private static final Logger LOG = Logger.getLogger(CommandContainer.class);

    private static final Map<String, Command> commands = new TreeMap<>();

    static {
        commands.put("languageCommand", new LanguageCommand());
        commands.put("loginCommand", new LoginCommand());
        commands.put("getCommand", new GetCommand());
        commands.put("registerCommand", new RegisterCommand());
        commands.put("courseCommand", new CourseCommand());
        commands.put("markStudentCommand", new MarkStudentCommand());
      //TODO
    }


    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            LOG.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }
}
