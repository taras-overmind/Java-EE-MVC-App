package com.taras_overmind.epam_final_project.command.commandResult;

public class ForwardResult implements CommandResult {
    private String forwardResource;

    public ForwardResult(String resource) {
        this.forwardResource = resource;
    }

    public String getResource() {
        return forwardResource;
    }
}