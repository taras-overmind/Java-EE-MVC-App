package com.taras_overmind.epam_final_project.db.dto;

public class CourseInfoDTO {
    private int courseId;
    private String courseName;
    private int duration;
    private String themeName;
    private String lecturerName;
    private String statusName;
    private int count;

    @Override
    public String toString() {
        return "CourseInfoDTO{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", duration=" + duration +
                ", themeName='" + themeName + '\'' +
                ", lecturerName='" + lecturerName + '\'' +
                ", statusName='" + statusName + '\'' +
                ", count=" + count +
                '}';
    }

    public CourseInfoDTO(int courseId, String courseName, int duration, String themeName, String lecturerName, String statusName, int count) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.duration = duration;
        this.themeName = themeName;
        this.lecturerName = lecturerName;
        this.statusName = statusName;
        this.count = count;
    }
    public CourseInfoDTO(){

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

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
