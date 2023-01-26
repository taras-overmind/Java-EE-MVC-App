package com.taras_overmind.epam_final_project.db.service;

import com.taras_overmind.epam_final_project.db.ConnectionPool;
import com.taras_overmind.epam_final_project.db.repository.LecturerRepo;
import org.apache.log4j.Logger;

public class LecturerService {
    private static final Logger LOG = Logger.getLogger(ConnectionPool.class.getName());
    private final LecturerRepo lecturerRepo = new LecturerRepo();

    public LecturerRepo getLecturerRepo() {
        return lecturerRepo;
    }
}
