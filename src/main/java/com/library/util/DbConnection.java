package com.library.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    Properties props = new Properties();
    FileInputStream fis = new FileInputStream("config.properties");

//    private static final String URL = "jdbc:mysql://host.docker.internal:3307/library_db";
//    private static final String USER = "root";
//    private static final String PASSWORD = "";

    static{
        Properties props = new Properties();
        try {
            FileInputStream fis = new FileInputStream("config.properties");
            props.load(fis);

            URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public DbConnection() throws FileNotFoundException {
    }

    public static Connection getConnection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("bien connecte");
            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Database connection error", e);
        }
    }

    public static void main(String[] args) throws SQLException {
      Connection connection=  DbConnection.getConnection();

    }
}
