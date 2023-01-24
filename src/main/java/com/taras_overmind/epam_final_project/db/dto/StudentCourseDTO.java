package com.taras_overmind.epam_final_project.db.dto;

public class StudentCourseDTO {
    private int id;
    private String studentName;
    private String courseName;
    private int mark;

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public StudentCourseDTO(int id, String studentName, String courseName, int mark) {
        this.id = id;
        this.studentName = studentName;
        this.courseName = courseName;
        this.mark=mark;
    }
    public StudentCourseDTO(){}
}
