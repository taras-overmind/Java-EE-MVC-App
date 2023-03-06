package com.taras_overmind.epam_final_project.db.entity;


import java.io.Serializable;


public class CourseEntity implements Serializable{


    private static final long serialVersionUID = -5527657822689332544L;

    private int courseId;
    private String courseName;
    private int duration;
    private int themeId;
    private int lecturerId;
    private int statusId;


    public CourseEntity() {
    }

    public CourseEntity(int courseId, String courseName, int duration, int themeId, int lecturerId, int statusId) {
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

//    public ThemeEntity getTheme(){
//        return themeRepo.getThemeById(themeId);
//    }


    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }


    public int getLecturerId() {
        return lecturerId;
    }

//    public LecturerEntity getLecturer(){
//        return lecturerRepo.getLecturerById(lecturerId);
//    }


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
        return "CourseEntity{" +
                "idCourse=" + courseId +
                ", nameCourse='" + courseName + '\'' +
                ", duration=" + duration +
                ", idTheme=" + themeId +
                ", idLecturer=" + lecturerId +
                ", idStatus=" + statusId +
                '}';
    }

}
