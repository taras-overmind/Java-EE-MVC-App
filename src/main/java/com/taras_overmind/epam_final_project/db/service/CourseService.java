package com.taras_overmind.epam_final_project.db.service;

import com.taras_overmind.epam_final_project.db.State;
import com.taras_overmind.epam_final_project.db.Status;
import com.taras_overmind.epam_final_project.db.dto.CourseDTO;
import com.taras_overmind.epam_final_project.db.repository.CourseRepo;

import java.sql.SQLDataException;
import java.util.List;

public class CourseService {
    private final CourseRepo courseRepo;

    public CourseService(CourseRepo courseRepo) {
        this.courseRepo = courseRepo;
    }

    public void createCourse(String name, int duration, int theme, int lecturer, Status status) {
        if(duration<0){
            throw new IllegalArgumentException();
        }
        courseRepo.createCourse(name, duration, theme, lecturer, status);
    }


    public void updateCourse(int id_course, String name, int duration, int theme, int lecturer, int status) throws SQLDataException {
        if (duration < 0) {
            throw new NumberFormatException("Duration is negative");
        }
        courseRepo.updateCourse(id_course, name, duration, theme, lecturer, status);
    }

    public void deleteCourseByIdCourse(int id) {
        courseRepo.deleteCourseByIdCourse(id);
    }

    public List<CourseDTO> findUserCoursesByUserIdAndStatus(Status status, int id_user) {
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
