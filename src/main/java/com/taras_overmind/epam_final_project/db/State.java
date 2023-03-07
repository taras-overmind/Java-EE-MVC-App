package com.taras_overmind.epam_final_project.db;

public enum State {
    LOCKED(0), UNLOCKED(1);

    private final int id_state;

    State(int id_state) {
        this.id_state=id_state;
    }

    public int getId_state() {
        return id_state;
    }
}
