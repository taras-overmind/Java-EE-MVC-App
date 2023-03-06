package com.taras_overmind.epam_final_project.db.service;

import com.taras_overmind.epam_final_project.db.entity.LecturerEntity;
import com.taras_overmind.epam_final_project.db.repository.LecturerRepo;

import java.util.List;

public class LecturerService {
    private final LecturerRepo lecturerRepo;

    public LecturerService(LecturerRepo lecturerRepo) {
        this.lecturerRepo = lecturerRepo;
    }

    public LecturerEntity createLecturer(String lastName, String firstName, String middleName, int user_id) {
        return lecturerRepo.createLecturer(lastName, firstName, middleName, user_id);
    }

    public List<LecturerEntity> getAllLecturers() {
        return lecturerRepo.getAllLecturers();
    }

    public LecturerEntity getLecturerById(int id) {
        return lecturerRepo.getLecturerById(id);
    }
}
