package controllers;


import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.Movie;
import models.Screening;
import models.Booking;
import models.User;
import utils.UserSession;
import dao.*;

import java.util.ArrayList;
import java.util.List;

public class AccountController {

    @FXML private Label welcomeLabel;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private PasswordField oldPasswordField;
    @FXML private PasswordField newPasswordField;
    @FXML private Label feedbackLabel;
    @FXML private Label passwordFeedbackLabel;

    private boolean specialCharCheck (String str, String specialChars){
        boolean hasSpecialChar = false;

        for (int i = 0; i < str.length(); i++) {
            if (specialChars.contains(String.valueOf(str.charAt(i)))) {
                hasSpecialChar = true;
                break;
            }
        }
        return hasSpecialChar;
    }

    @FXML
    public void initialize() {
        models.User currentUser = UserSession.getLoggedInUser();

        if (currentUser != null) {
            welcomeLabel.setText("Welcome to MyCinema, " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
        } else {
            welcomeLabel.setText("Welcome to MyCinema!");
        }
    }

    @FXML public void handleNameChange() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        if(firstName.isEmpty() && lastName.isEmpty()){
            feedbackLabel.setStyle("-fx-text-fill: red;");
            feedbackLabel.setText("Please enter name to update");
            return;
        }
        if (firstName.isEmpty()){
            firstName = UserSession.getLoggedInUser().getFirstName();
        }
        if (lastName.isEmpty()){
            lastName = UserSession.getLoggedInUser().getLastName();
        }

        User updatedUser = new User(
                UserSession.getLoggedInUser().getUsername(),
                UserSession.getLoggedInUser().getPassword(),
                firstName,
                lastName
        );

        UserDAO userDAO = new UserDAO();
        boolean success = userDAO.updateUser(updatedUser);

        if (success){
            feedbackLabel.setStyle("-fx-text-fill: green;");
            feedbackLabel.setText("Name changed successfully");
            UserSession.setLoggedInUser(updatedUser);
        }
        else{
            feedbackLabel.setStyle("-fx-text-fill: red;");
            feedbackLabel.setText("Name change failed");
        }

    }

    @FXML public void handleUpdatePassword(){
        String oldPassword = oldPasswordField.getText().trim();
        String newPassword = newPasswordField.getText().trim();

        if(oldPassword.isEmpty() || newPassword.isEmpty()){
            passwordFeedbackLabel.setStyle("-fx-text-fill: red;");
            passwordFeedbackLabel.setText("Please enter old and new passwords");
            return;
        }

        String specialChars = "!@#$%^&*()_-=+?";
        if(newPassword.length() < 8 ||
                !newPassword.matches(".*\\d.*") ||
                !newPassword.matches(".*[A-Z].*") ||
                !specialCharCheck(newPassword, specialChars)){
            passwordFeedbackLabel.setText("Password must contain at least 1 number, uppercase letter and special character ( " + specialChars + " )");
            return;
        }

        //checks if there is whitespace within the new password
        if(!newPassword.matches("\\S+")){
            passwordFeedbackLabel.setStyle("-fx-text-fill: red;");
            passwordFeedbackLabel.setText("Password cannot have whitespace");
            return;
        }

        User upadatedUser = new User(
                UserSession.getLoggedInUser().getUsername(),
                newPassword,
                UserSession.getLoggedInUser().getFirstName(),
                UserSession.getLoggedInUser().getLastName()
        );

        UserDAO userDAO = new UserDAO();
        boolean success = userDAO.changePassword(upadatedUser, oldPassword);

        if (success){
            passwordFeedbackLabel.setStyle("-fx-text-fill: green;");
            passwordFeedbackLabel.setText("Password successfully updated!");
        } else {
            passwordFeedbackLabel.setStyle("-fx-text-fill: red;");
            passwordFeedbackLabel.setText("Error occurred updating password");
        }

    }

    @FXML
    public void switchToDashboard(){
        System.out.println("Returning to dashboard...");
        application.MyCinemaApp.setRoot("/views/DashboardView.fxml");
    }

    @FXML
    public void handleViewBookings(){
        System.out.println("Switching to booking history screen");
        application.MyCinemaApp.setRoot("/views/HistoryView.fxml");
    }

    @FXML
    public void handleLogOut(){
        System.out.println("Logging user out - Switching to login screen");
        UserSession.logOutUser();
        application.MyCinemaApp.setRoot("/views/LoginView.fxml");
    }
}
