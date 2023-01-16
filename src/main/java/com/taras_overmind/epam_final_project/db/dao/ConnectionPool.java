package com.taras_overmind.epam_final_project.db.dao;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {

    private static final Logger LOG = Logger.getLogger(ConnectionPool.class);

    private ConnectionPool(){}



    public static synchronized Connection getConnection() {
        try {
            Context initCtx = new InitialContext();
            Context envContext = (Context) initCtx.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/courses");
            LOG.trace("Connection is successful");
            return ds.getConnection();
        } catch (NamingException ex) {
            LOG.error("Cannot find the data source");
            return null;
        } catch (SQLException ex) {
            LOG.error("Cannot get connection from data source");
            return null;
        }
    }

}
