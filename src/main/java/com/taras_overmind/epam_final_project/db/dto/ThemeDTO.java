package com.taras_overmind.epam_final_project.db.dto;


import java.io.Serializable;


public class ThemeDTO implements Serializable {


    private static final long serialVersionUID = 6674765258602087731L;

    private int idTheme;
    private String nameTheme;

    public ThemeDTO(){

    }

    public ThemeDTO(int idTheme, String nameTheme) {
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
        return "ThemeDTO{" +
                "themeId=" + idTheme +
                ", themeName='" + nameTheme + '\'' +
                '}';
    }
}
