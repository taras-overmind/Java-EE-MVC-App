package com.taras_overmind.epam_final_project.db;

public enum Status {
    OPENED(1), IN_PROGRESS(2), CLOSED(3), FINISHED(4);
    private final int id_status;

    Status(int id_status) {
        this.id_status = id_status;
    }

    public int getId_status() {
        return id_status;
    }
}
