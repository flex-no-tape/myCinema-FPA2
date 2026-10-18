package dao;

import models.User;
import utils.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public boolean registerUser(User user){
        String sql = "INSERT INTO users (username, password, first_name, last_name) VALUES (?, ?, ?, ?)";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getFirstName());
            pstmt.setString(4, user.getLastName());
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e){
            //Duplicate username will throw an exception
            System.err.println("dao.UserDAO: models.User Registration Failed: " + e.getMessage());
            return false;
        }
    }

    public User authenticateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    //If this condition is executed then there is a user with a matching password and username in the table
                    return new User(
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("first_name"),
                            rs.getString("last_name")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("dao.UserDAO: Login Error: " + e.getMessage());
        }
        return null;
    }

    public boolean updateUser(User user){
        String sql = "UPDATE users SET first_name = ?, last_name = ? WHERE username = ?";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getUsername());

            int rowsAffected = pstmt.executeUpdate();

            //returns true if user is found and is updated, false if no matching user is found to update
            return rowsAffected > 0;

        } catch(SQLException e){
            System.err.println("dao.UserDAO: Updating user failed: " + e.getMessage());
            return false;
        }
    }

    public boolean changePassword(User user, String oldPassword){
        String sql = "UPDATE users SET password = ? WHERE username = ? AND password = ?";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, oldPassword);

            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;
        } catch(SQLException e){
            System.err.println("dao.UserDAO: Updating password failed: " + e.getMessage());
            return false;
        }
    }


}
