package com.taras_overmind.epam_final_project.db;



import com.taras_overmind.epam_final_project.db.entity.UserEntity;


public enum Role {
    ADMIN(0), STUDENT(1), LECTURER(2);
    private final int id_role;

    Role(int id_role) {
        this.id_role = id_role;
    }
    //    public static Role getRole(UserEntity user) {
//        int roleId = user.getRoleId();
//        return Role.values()[roleId];
//    }

    public int getId_role() {
        return id_role;
    }

    public String getName() {
        return toString();
    }
}
