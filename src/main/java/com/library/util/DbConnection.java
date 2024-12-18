package com.library.util;

import com.library.Exception.ConfigurationException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;



public class DbConnection {
    private static String dbUrl;
    private static String dbUser;
    private static String dbPassword;




    static{
        Properties props = new Properties();
        try {

            FileInputStream fis = new FileInputStream("config.properties");
            props.load(fis);

            // Initialize the variables
            dbUrl = props.getProperty("db.url");
            dbUser = props.getProperty("db.user");
            dbPassword = props.getProperty("db.password");
        } catch (FileNotFoundException e) {
            try {
                throw new ConfigurationException("Configuration file not found", e);
            } catch (ConfigurationException ex) {
                throw new RuntimeException(ex);
            }
        } catch (IOException e) {
            try {
                throw new ConfigurationException("Error loading configuration file", e);
            } catch (ConfigurationException ex) {
            }

        }

    }
    /**
     * Default constructor for DbConnection.
     * This constructor is not intended to be used directly.
     * DbConnection should be used through its static methods.
     */
    public DbConnection() throws FileNotFoundException {
        // This constructor is empty because DbConnection should only be used through its static methods.
        // It is not intended for direct instantiation. If invoked, an UnsupportedOperationException is thrown.
        throw new UnsupportedOperationException("DbConnection should be used through its static methods, not instantiated directly.");
    }

    public static Connection getConnection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);

        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Database connection error", e);
        }
    }

}
