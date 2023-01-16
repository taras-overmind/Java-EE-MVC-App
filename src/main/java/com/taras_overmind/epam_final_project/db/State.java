package com.taras_overmind.epam_final_project.db;


import com.taras_overmind.epam_final_project.db.dto.UserDTO ;

public enum State {
    UNLOCKED, LOCKED;


    public static Role getState(UserDTO user) {
        int stateId = user.getStateId();
        return Role.values()[stateId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
