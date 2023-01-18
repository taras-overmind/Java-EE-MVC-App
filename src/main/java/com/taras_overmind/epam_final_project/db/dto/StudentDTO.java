package com.taras_overmind.epam_final_project.db.dto;



import java.io.Serializable;


public class StudentDTO implements Serializable {


    private static final long serialVersionUID = -5205879381501085376L;

    private int id;
    private String surname;
    private String name;
    private String patronymic;
    private int idUser;


    public StudentDTO() {
    }


    public StudentDTO(int id, String surname, String name, String middleName, int idUser) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = middleName;
        this.idUser = idUser;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getSurname() {
        return surname;
    }


    public void setSurname(String surname) {
        this.surname = surname;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getPatronymic() {
        return patronymic;
    }


    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }


    public int getIdUser() {
        return idUser;
    }


    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }


    @Override
    public String toString() {
        return "StudentDTO{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", idUser=" + idUser +
                '}';
    }
}