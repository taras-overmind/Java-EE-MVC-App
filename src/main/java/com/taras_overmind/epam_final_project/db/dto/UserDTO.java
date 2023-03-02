package com.taras_overmind.epam_final_project.db.dto;

public class UserDTO {
    private int id_user;
    private String name;
    private String name_state;
    public UserDTO(){}

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_state() {
        return name_state;
    }

    public void setName_state(String name_state) {
        this.name_state = name_state;
    }

    public UserDTO(int id_user, String name, String name_state) {
        this.id_user = id_user;
        this.name = name;
        this.name_state = name_state;
    }
}
