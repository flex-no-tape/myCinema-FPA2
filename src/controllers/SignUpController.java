package controllers;

import dao.UserDAO;
import models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label feedbackLabel;

    @FXML
    public void handleReturnLoginSwitch() {
        System.out.println("Switching to login screen...");
        application.MyCinemaApp.setRoot("/views/LoginView.fxml");
    }

    @FXML
    public void handleSignUp(){
        System.out.println("Sign up button clicked!");
        //Registration logic
    }
}
