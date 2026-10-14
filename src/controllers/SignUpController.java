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
        dao.UserDAO newUserDAO = new UserDAO();

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(firstName.trim().isEmpty() ||
                lastName.trim().isEmpty() ||
                username.trim().isEmpty() ||
                password.trim().isEmpty()){
            feedbackLabel.setText("Please enter all required information");
            return;
        }

        //TODO: Validations

        User newUser = new User(
                firstName,
                lastName,
                username,
                password
        );

        boolean registered = newUserDAO.registerUser(newUser);
        //TODO
        //If this is true then you can return to the home page because account is registered
        //If false then an error occurred registering user

    }
}
