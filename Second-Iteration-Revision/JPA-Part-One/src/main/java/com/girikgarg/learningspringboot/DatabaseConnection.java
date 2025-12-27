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
            return DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        }
        catch (ClassNotFoundException | SQLException ex) {
            // handle exception
        }
        return null;
    }
}
