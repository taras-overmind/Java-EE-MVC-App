package com.taras_overmind.epam_final_project.db.service;

import com.taras_overmind.epam_final_project.db.entity.ThemeEntity;
import com.taras_overmind.epam_final_project.db.repository.ThemeRepo;
import org.apache.log4j.Logger;

import java.util.List;

public class ThemeService {

    private final ThemeRepo themeRepo;

    public ThemeService(ThemeRepo themeRepo) {
        this.themeRepo = themeRepo;
    }

    private static final Logger LOG = Logger.getLogger(ThemeService.class.getName());

    public ThemeEntity themeCheck(String name_theme) {
        LOG.trace("Start tracing ThemeService#themeCheck");
        int id_theme;
        ThemeEntity theme = themeRepo.getThemeByName(name_theme);
        if (theme != null) {
            id_theme = theme.getIdTheme();
            return new ThemeEntity(id_theme, name_theme);
        } else
            return themeRepo.createTheme(name_theme);
    }

    public List<ThemeEntity> getAllThemes() {
        return themeRepo.getAllThemes();
    }

    public ThemeEntity getThemeById(int id) {
        return themeRepo.getThemeById(id);
    }

    public ThemeEntity getThemeByName(String name_theme) {
        return themeRepo.getThemeByName(name_theme);
    }

    public ThemeEntity createTheme(String name_theme) {
        return themeRepo.createTheme(name_theme);
    }
}
