package com.taras_overmind.epam_final_project.db.service;

import com.taras_overmind.epam_final_project.db.ConnectionPool;
import com.taras_overmind.epam_final_project.db.repository.StudentRepo;
import org.apache.log4j.Logger;

public class StudentService {
    private static Logger LOG = Logger.getLogger(ConnectionPool.class.getName());
    private StudentRepo studentRepo = new StudentRepo();

    public StudentRepo getStudentRepo() {
        return studentRepo;
    }
}
