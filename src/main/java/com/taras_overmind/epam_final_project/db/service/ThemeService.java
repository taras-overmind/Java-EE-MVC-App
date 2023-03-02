package com.taras_overmind.epam_final_project.db.service;

import com.taras_overmind.epam_final_project.db.entity.ThemeEntity;
import com.taras_overmind.epam_final_project.db.repository.ThemeRepo;
import org.apache.log4j.Logger;

public class ThemeService {

    private static final ThemeRepo themeRepo = new ThemeRepo();
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
}
