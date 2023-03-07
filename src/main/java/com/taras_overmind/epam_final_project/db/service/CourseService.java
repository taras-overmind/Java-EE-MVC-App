package com.taras_overmind.epam_final_project.db.service;

import com.taras_overmind.epam_final_project.db.State;
import com.taras_overmind.epam_final_project.db.Status;
import com.taras_overmind.epam_final_project.db.dto.CourseDTO;
import com.taras_overmind.epam_final_project.db.repository.CourseRepo;

import java.util.List;

public class CourseService {
    private final CourseRepo courseRepo;

    public CourseService(CourseRepo courseRepo) {
        this.courseRepo = courseRepo;
    }

    public void createCourse(String name, int duration, int theme, int lecturer, Status status) {
        courseRepo.createCourse(name, duration, theme, lecturer, status);
    }


    public void updateCourse(int id_course, String name, int duration, int theme, int lecturer, int status) {
        courseRepo.updateCourse(id_course, name, duration, theme, lecturer, status);
    }

    public void deleteCourseByIdCourse(int id) {
        courseRepo.deleteCourseByIdCourse(id);
    }

    public List<CourseDTO> findUserCoursesByUserIdAndStatus(String status, int id_user) {
        return courseRepo.findUserCoursesByUserIdAndStatus(status, id_user);
    }

    public List<CourseDTO> findCoursesToRegisterByUserId(int id) {
        return courseRepo.findCoursesToRegisterByUserId(id);
    }

    public List<CourseDTO> findSortedCourses(Object sort, Object sorting, Object idLecturer, Object idTheme, String ending) {
        return courseRepo.findSortedCourses(sort, sorting, idLecturer, idTheme, ending);
    }

    public int getNumberOfRecords() {
        return courseRepo.getNumberOfRecords();
    }


}
