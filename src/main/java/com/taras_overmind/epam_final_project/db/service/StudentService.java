package com.taras_overmind.epam_final_project.db.service;

import com.taras_overmind.epam_final_project.db.entity.StudentEntity;
import com.taras_overmind.epam_final_project.db.repository.StudentRepo;

public class StudentService {
    private final StudentRepo studentRepo;
    public StudentService(StudentRepo studentRepo)  {
        this.studentRepo = studentRepo;
    }

    public StudentEntity createStudent(String lastName, String firstName, String middleName, int user_id) {
        return studentRepo.createStudent(lastName, firstName, middleName, user_id);
    }

    public StudentEntity findStudentByIdUser(int id) {
        return studentRepo.findStudentByIdUser(id);
    }
}
