package com.taras_overmind.epam_final_project.db.dto;


import com.taras_overmind.epam_final_project.db.repository.LecturerRepo;
import com.taras_overmind.epam_final_project.db.repository.ThemeRepo;

import java.io.Serializable;


public class CourseDTO implements Serializable{


    private static final long serialVersionUID = -5527657822689332544L;

    private static final LecturerRepo lecturerRepo = new LecturerRepo();

    private static final ThemeRepo themeRepo = new ThemeRepo();

    private int courseId;
    private String courseName;
    private int duration;
    private int themeId;
    private int lecturerId;
    private int statusId;


    public CourseDTO() {
    }

    public CourseDTO(int courseId, String courseName, int duration, int themeId, int lecturerId, int statusId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.duration = duration;
        this.themeId = themeId;
        this.lecturerId = lecturerId;
        this.statusId = statusId;
    }

    public int getCourseId() {
        return courseId;
    }


    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }


    public String getCourseName() {
        return courseName;
    }


    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }


    public int getDuration() {
        return duration;
    }


    public void setDuration(int duration) {
        this.duration = duration;
    }


    public int getThemeId() {
        return themeId;
    }

    public ThemeDTO getTheme(){
        return themeRepo.getThemeById(themeId);
    }


    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }


    public int getLecturerId() {
        return lecturerId;
    }

    public LecturerDTO getLecturer(){
        return lecturerRepo.getLecturerById(lecturerId);
    }


    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
    }


    public int getStatusId() {
        return statusId;
    }


    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }


    @Override
    public String toString() {
        return "CourseDTO{" +
                "idCourse=" + courseId +
                ", nameCourse='" + courseName + '\'' +
                ", duration=" + duration +
                ", idTheme=" + themeId +
                ", idLecturer=" + lecturerId +
                ", idStatus=" + statusId +
                '}';
    }

}
