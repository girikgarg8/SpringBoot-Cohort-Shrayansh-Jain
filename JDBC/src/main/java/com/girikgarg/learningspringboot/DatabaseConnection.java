package com.girikgarg.learningspringboot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public Connection getConnection() {
        try {
            // Loading H2 Driver
            Class.forName("org.h2.Driver");

            // Establish connection with DB
            return DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
        }
        catch (ClassNotFoundException | SQLException e) {
            // handle exception
        }
        return null;
    }
}
