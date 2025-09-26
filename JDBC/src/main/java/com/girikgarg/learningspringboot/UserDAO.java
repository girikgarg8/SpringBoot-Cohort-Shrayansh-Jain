package com.girikgarg.learningspringboot;

import java.sql.*;

public class UserDAO {
    public void createUserTable() {
        try {
            Connection connection = new DatabaseConnection().getConnection();
            Statement statementQuery = connection.createStatement();
            String sql = "CREATE TABLE USERS(USER_ID INT AUTO_INCREMENT PRIMARY KEY, USER_NAME VARCHAR(100), AGE INT)";
            statementQuery.executeUpdate(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            // close statement query and db connection
        }
    }

    public void createUser(String userName, int userAge) {
        try {
            Connection connection = new DatabaseConnection().getConnection();
            String sqlQuery = "INSERT INTO USERS(USER_NAME, AGE) VALUES(?,?)";
            PreparedStatement preparedStatsment = connection.prepareStatement(sqlQuery);
            preparedStatsment.setString(1, userName);
            preparedStatsment.setInt(2, userAge);
            preparedStatsment.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            // close prepared query and db connection
        }
    }

    public void readUsers() {
        try {
            Connection connection = new DatabaseConnection().getConnection();
            String sqlQuery = "SELECT * FROM USERS";
            PreparedStatement preparedQuery = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedQuery.executeQuery();

            while (resultSet.next()) {
                String userName = resultSet.getString("USER_NAME");
                int age = resultSet.getInt("AGE");
                System.out.println("User name is" + userName);
                System.out.println("Age is" + age);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            // clos prepared query and db connection
        }
    }
}
