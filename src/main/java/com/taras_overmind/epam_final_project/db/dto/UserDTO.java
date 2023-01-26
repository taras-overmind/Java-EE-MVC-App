package com.taras_overmind.epam_final_project.db.dto;


import java.io.Serial;
import java.io.Serializable;


public class UserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 8218802710491337849L;

    private int idUser;
    private String login;
    private String password;
    private String email;
    private int roleId = -1;
    private int stateId = 1;

    public UserDTO() {}

    public UserDTO(String login, String password, int roleId) {
        this.login = login;
        this.password = password;
        this.roleId = roleId;
    }

    public UserDTO(String login, String password, int roleId, int stateId) {
        this.login = login;
        this.password = password;
        this.roleId = roleId;
        this.stateId = stateId;
    }
    public UserDTO(int id, String login, String password, int roleId, int stateId) {
        this.idUser = id;
        this.login = login;
        this.password = password;
        this.roleId = roleId;
        this.stateId = stateId;
    }


    public UserDTO(int id, String login, String password, String email, int roleId, int stateId) {
        this.idUser = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.roleId = roleId;
        this.stateId = stateId;
    }

    public int getIdUser() {
        return idUser;
    }

void setIdUser(int idUser) {
        this.idUser = idUser;
    }


    public void setLogin(String login) {
        this.login = login;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public int getRoleId() {
        return roleId;
    }


    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public int getStateId() {
        return stateId;
    }

    public String getLogin(){return login;}


    public void setStateId(int stateId) {
        this.stateId = stateId;
    }


    @Override
    public String toString() {
        return "UserDTO{" +
                "idUser=" + idUser +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roleId=" + roleId +
                ", stateId=" + stateId +
                '}';
    }
}
