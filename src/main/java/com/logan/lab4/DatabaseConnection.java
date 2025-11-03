package com.logan.lab4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/Lab4";
    private static final String USER = "root";
    private static final String PASSWORD = "Mag1c_MySQL";

    public static Connection getConnection() {
        Connection connection = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e);
        }
        return connection;
    }

    public static void main(String[] args) {
        getConnection();
    }
}

