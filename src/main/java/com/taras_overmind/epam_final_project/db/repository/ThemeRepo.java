package com.taras_overmind.epam_final_project.db.repository;

import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.dao.ConnectionPool;
import com.taras_overmind.epam_final_project.db.dto.ThemeDTO;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThemeRepo {
    private static Logger LOG = Logger.getLogger(ConnectionPool.class.getName());

    public List<ThemeDTO> getAllThemes() {
        LOG.trace("Starting tracing ThemeRepo");
        List<ThemeDTO> themes = new ArrayList<>();
        ThemeDTO theme;

        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(Query.SELECT_ALL_THEMES)) {
                    connection.setAutoCommit(false);
                    statement.execute();
                    ResultSet resultSet = statement.getResultSet();
                    while (resultSet.next()) {
                        theme = new ThemeDTO(resultSet.getInt("id_theme"), resultSet.getString("name_theme"));
                        themes.add(theme);
                    }
                    resultSet.close();
                    connection.commit();
                } catch (SQLException ex) {
                    LOG.error(ex.getLocalizedMessage());
                    connection.rollback();
                }
            }
        } catch (SQLException ex) {
            LOG.info(ex.getLocalizedMessage());
        }

        return themes;
    }

    public ThemeDTO getThemeById(int id){
        LOG.trace("Start tracing ThemeRepo#getThemeById");
        ThemeDTO theme=null;
        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(Query.SELECT_THEME_BY_ID)) {
                    connection.setAutoCommit(false);
                    statement.setString(1, String.valueOf(id));
                    statement.execute();
                    ResultSet resultSet = statement.getResultSet();
                    if (resultSet.next()) {
                        theme = new ThemeDTO(resultSet.getInt("id_theme"), resultSet.getString("name_theme"));
                    }
                    resultSet.close();
                    connection.commit();
                } catch (SQLException e) {
                    LOG.error(e.getLocalizedMessage());
                    connection.rollback();
                }
            }
        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return theme;
    }
    public ThemeDTO getThemeByName(String name_theme){
        LOG.trace("Start tracing ThemeRepo#getThemeByName");
        ThemeDTO theme=null;
        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(Query.SELECT_THEME_BY_NAME)) {
                    connection.setAutoCommit(false);
                    statement.setString(1, name_theme);
                    statement.execute();
                    ResultSet resultSet = statement.getResultSet();
                    if (resultSet.next()) {
                        theme = new ThemeDTO(resultSet.getInt("id_theme"), resultSet.getString("name_theme"));
                    }
                    resultSet.close();
                    connection.commit();
                } catch (SQLException e) {
                    LOG.error(e.getLocalizedMessage());
                    connection.rollback();
                }
            }
        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return theme;
    }

    public ThemeDTO createTheme(String name_theme){
        LOG.trace("Start tracing ThemeRepo#createTheme");
        ThemeDTO theme = null;
        int id = -1;

        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(Query.CREATE_THEME, Statement.RETURN_GENERATED_KEYS)) {
                    connection.setAutoCommit(false);
                    statement.setString(1, name_theme);
                    statement.executeUpdate();
                    PreparedStatement stmt = connection.prepareStatement(Query.SELECT_LAST_THEME_ID);
                    stmt.execute();
                    ResultSet resultSet = stmt.getResultSet();
                    if (resultSet.next()) {
                        id = resultSet.getInt("max(id_theme)");
                    }
                    connection.commit();
                } catch (SQLException ex) {
                    LOG.error(ex.getLocalizedMessage());
                    connection.rollback();
                }
            }
        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        theme = new ThemeDTO(id, name_theme);
        return theme;
    }


}
