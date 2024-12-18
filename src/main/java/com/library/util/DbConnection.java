package com.library.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// Custom exception for configuration-related errors
class ConfigurationException extends Exception {
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
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
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(new ConfigurationException("Error loading configuration file", e));

        }

    }
    public DbConnection() throws FileNotFoundException {
    }

    public static Connection getConnection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("bien connecte");
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);

        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Database connection error", e);
        }
    }

    public static void main(String[] args) throws SQLException {
      Connection connection=  DbConnection.getConnection();

    }
}
