package com.taras_overmind.epam_final_project.db.dao;


import com.taras_overmind.epam_final_project.db.dto.UserDTO ;

import java.util.List;


public interface UserDAO {


    public void lockUserById(int id, int state);


    public UserDTO findUserByLogin(String login);


    public UserDTO createUser(String login, String password);


    public List<UserDTO> getAllUsers();


    public void registerUserOnCourse(int id, int idCourse);


    public void setNewPassword(int id, String password);
}
