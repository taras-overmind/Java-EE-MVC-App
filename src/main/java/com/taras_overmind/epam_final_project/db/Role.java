package com.taras_overmind.epam_final_project.db;

import com.taras_overmind.epam_final_project.db.dto.UserDTO;


public enum Role {
    ADMIN, STUDENT, LECTURER;


    public static Role getRole(UserDTO user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
