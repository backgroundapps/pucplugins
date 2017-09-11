package com.tecgraf.plugins.server.dao.conf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static Connection connection;

    public Connection getConnection() {
        try {

            if (connection != null && !connection.isClosed()) return connection;

            else {

                Class.forName ("oracle.jdbc.OracleDriver");
                this.connection = DriverManager.getConnection("jdbc:oracle:thin:system/xe@localhost:1521/XE");

                return this.connection;

            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
