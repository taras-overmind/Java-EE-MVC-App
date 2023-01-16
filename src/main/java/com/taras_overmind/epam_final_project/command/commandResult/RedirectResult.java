package com.taras_overmind.epam_final_project.command.commandResult;

public class RedirectResult implements CommandResult {
    private final String redirectResource;

    public RedirectResult(String resource) {
        this.redirectResource = resource;
    }
    public String getResource() {
        return redirectResource;
    }
}