package com.taras_overmind.epam_final_project.db.service;

import com.taras_overmind.epam_final_project.db.dto.StudentCourseDTO;
import com.taras_overmind.epam_final_project.db.repository.StudentCourseRepo;

import java.util.List;

public class StudentCourseService {
    private final StudentCourseRepo studentCourseRepo;

    public StudentCourseService(StudentCourseRepo studentCourseRepo) {
        this.studentCourseRepo = studentCourseRepo;
    }

    public List<StudentCourseDTO> findStudentsByUserId(int id, boolean withMarks) {
        return studentCourseRepo.findStudentsByUserId(id, withMarks);
    }
}
