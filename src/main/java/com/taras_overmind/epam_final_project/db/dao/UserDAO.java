package com.taras_overmind.epam_final_project.db.dao;


import com.taras_overmind.epam_final_project.db.dto.UserDTO ;

import java.util.List;


public interface UserDAO {




    public List<UserDTO> getAllUsers();




    public void setNewPassword(int id, String password);
}
