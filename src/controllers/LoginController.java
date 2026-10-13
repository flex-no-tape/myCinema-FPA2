package controllers;

import dao.UserDAO;
import models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label feedbackLabel;

    private UserDAO userDAO = new UserDAO();

    @FXML
    public void handleSignUpSwitch() {
        System.out.println("Switching to Sign Up screen...");
        application.MyCinemaApp.setRoot("/views/SignUpView.fxml");
    }
    @FXML
    public void handleLogin(){
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            feedbackLabel.setText("Please enter both username and password.");
            return;
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.authenticateUser(username, password);

        if(user != null){
            System.out.println("Successfully logged in for: " + user.getUsername());
            //application.MyCinemaApp.setRoot("/views/DashboardView.fxml");
            //switch screen
        }else{
            feedbackLabel.setText("Invalid username or password.");
        }

    }
}
