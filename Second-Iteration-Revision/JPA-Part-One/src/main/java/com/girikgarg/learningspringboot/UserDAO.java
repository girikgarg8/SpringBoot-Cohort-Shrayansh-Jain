package com.girikgarg.learningspringboot;
import java.sql.*;

public class UserDAO {
    public void createUserTable() {
        try {
            Connection connection = new DatabaseConnection().getConnection();
            Statement statementQuery = connection.createStatement();
            String sql = "CREATE TABLE APP_USERS(USER_ID INT AUTO_INCREMENT PRIMARY KEY, USER_NAME VARCHAR(100), AGE INT)";
            statementQuery.executeUpdate(sql);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            // close statement query and db connection
        }
    }

    public void createUser(String userName, int userAge) {
        try {
            Connection connection = new DatabaseConnection().getConnection();
            String sqlQuery = "INSERT INTO APP_USERS(USER_NAME, AGE) VALUES(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, userName);
            preparedStatement.setInt(2, userAge);
            preparedStatement.executeUpdate();
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
            String sqlQuery = "SELECT * FROM APP_USERS";
            PreparedStatement preparedQuery = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedQuery.executeQuery();
            
            while (resultSet.next()) {
                String username = resultSet.getString("USER_NAME");
                int age = resultSet.getInt("AGE");
                System.out.println("User name is: "+ username);
                System.out.println("Age is: " + age);
            }
        }

        
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            // close prepared query and db connection
        }
    }


}
