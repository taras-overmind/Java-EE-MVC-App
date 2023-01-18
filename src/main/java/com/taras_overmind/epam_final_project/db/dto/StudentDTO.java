package com.taras_overmind.epam_final_project.db.dto;



import java.io.Serializable;


public class StudentDTO implements Serializable {


    private static final long serialVersionUID = -5205879381501085376L;

    private int id;
    private String lastName;
    private String firstName;
    private String middleName;
    private int userId;


    public StudentDTO() {
    }


    public StudentDTO(int id, String lastName, String firstName, String middleName, int userId) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.userId = userId;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getMiddleName() {
        return middleName;
    }


    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }


    public int getUserId() {
        return userId;
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "StudentDTO{" +
                "id=" + id +
                ", surname='" + lastName + '\'' +
                ", name='" + firstName + '\'' +
                ", patronymic='" + middleName + '\'' +
                ", idUser=" + userId +
                '}';
    }
}