package com.taras_overmind.epam_final_project.db.entity;


import java.io.Serializable;


public class ThemeEntity implements Serializable {


    private static final long serialVersionUID = 6674765258602087731L;

    private int idTheme;
    private String nameTheme;

    public ThemeEntity(){

    }

    public ThemeEntity(int idTheme, String nameTheme) {
        this.idTheme = idTheme;
        this.nameTheme = nameTheme;
    }

    public int getIdTheme() {
        return idTheme;
    }

    public void setIdTheme(int idTheme) {
        this.idTheme = idTheme;
    }

    public String getNameTheme() {
        return nameTheme;
    }

    public void setNameTheme(String nameTheme) {
        this.nameTheme = nameTheme;
    }

    @Override
    public String toString() {
        return "ThemeEntity{" +
                "themeId=" + idTheme +
                ", themeName='" + nameTheme + '\'' +
                '}';
    }
}
