package com.taras_overmind.epam_final_project.db.repository;

import com.taras_overmind.epam_final_project.db.ConnectionPool;
import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.dto.StatusDTO;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatusRepo {
    private static final Logger LOG = Logger.getLogger(ConnectionPool.class.getName());
    public List<StatusDTO> getAllStatuses() {
        LOG.trace("Starting tracing StatusRepo#getAllStatuses");
        List<StatusDTO> statuses = new ArrayList<>();
        StatusDTO status;

        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(Query.SELECT_ALL_STATUSES)) {
                    connection.setAutoCommit(false);
                    statement.execute();
                    ResultSet resultSet = statement.getResultSet();
                    while (resultSet.next()) {
                        status = new StatusDTO(resultSet.getInt("id_status"), resultSet.getString("name_status"));
                        statuses.add(status);
                    }
                    resultSet.close();
                    connection.commit();
                } catch (SQLException ex) {
                    LOG.error(ex.getLocalizedMessage());
                    connection.rollback();
                }
            }
        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return statuses;
    }
}
