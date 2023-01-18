package com.taras_overmind.epam_final_project.db.dto;


import java.io.Serializable;


public class ThemeDTO implements Serializable {


    private static final long serialVersionUID = 6674765258602087731L;

    private int themeId;
    private String themeName;

    public ThemeDTO(){

    }

    public ThemeDTO(int themeId, String themeName) {
        this.themeId = themeId;
        this.themeName = themeName;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    @Override
    public String toString() {
        return "ThemeDTO{" +
                "themeId=" + themeId +
                ", themeName='" + themeName + '\'' +
                '}';
    }
}
