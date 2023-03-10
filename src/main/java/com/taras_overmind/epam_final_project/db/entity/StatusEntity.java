package com.taras_overmind.epam_final_project.db.entity;

import java.io.Serializable;


public class StatusEntity implements Serializable {


    private static final long serialVersionUID = 8848503175074357512L;

    private int id_status;
    private String name_status;


    public StatusEntity(){

    }

    public StatusEntity(int id_status, String name_status) {
        this.id_status = id_status;
        this.name_status = name_status;
    }

    public int getId_status() {
        return id_status;
    }


    public void setId_status(int id_status) {
        this.id_status = id_status;
    }


    public String getName_status() {
        return name_status;
    }


    public void setName_status(String name_status) {
        this.name_status = name_status;
    }


    @Override
    public String toString() {
        return "StatusEntity{" +
                "idStatus=" + id_status +
                ", nameStatus='" + name_status + '\'' +
                '}';
    }
}
