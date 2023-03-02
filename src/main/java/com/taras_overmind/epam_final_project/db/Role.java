package com.taras_overmind.epam_final_project.db;



import com.taras_overmind.epam_final_project.db.entity.UserEntity;


public enum Role {
    ADMIN, STUDENT, LECTURER;


    public static Role getRole(UserEntity user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
