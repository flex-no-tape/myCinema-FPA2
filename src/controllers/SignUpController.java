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

    //specialCharCheck method is used by password validation to check for special characters
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
    public void handleReturnLoginSwitch() {
        System.out.println("Switching to login screen...");
        application.MyCinemaApp.setRoot("/views/LoginView.fxml");
    }

    @FXML
public void handleSignUp(){
        System.out.println("Sign up button clicked");
        UserDAO newUserDAO = new UserDAO();

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();


        //ensures fields are not left empty
        if(firstName.trim().isEmpty() ||
                lastName.trim().isEmpty() ||
                username.trim().isEmpty() ||
                password.trim().isEmpty()){
            feedbackLabel.setText("Please enter all required information");
            return;
        }

        //ensures usernames are at least 3 characters
        if(username.trim().length() < 3){
            feedbackLabel.setText("Username must be at least 3 characters");
            return;
        }

        /*
        following conditional ensures passwords are valid:
        - Are at least 8 characters long
        - Contain at least 1 numerical digit
        - Contain at least 1 uppercase letter
        - Contain at least 1 special character
         */
        String specialChars = "!@#$%^&*()_-=+?";
        if(password.trim().length() < 8 ||
                !password.trim().matches(".*\\d.*") ||
                !password.trim().matches(".*[A-Z].*") ||
                !specialCharCheck(password.trim(), specialChars)){
            feedbackLabel.setText("Password must contain at least 1 number, uppercase letter and special character ( " + specialChars + " )");
            return;
        }

        //checks if there is whitespace within the fields
        if(!firstName.trim().matches("\\S+")){
            feedbackLabel.setText("Fields cannot have whitespace");
            return;
        }

        User newUser = new User(
                username.trim(),
                password.trim(),
                firstName.trim(),
                lastName.trim()
        );

        boolean registered = newUserDAO.registerUser(newUser);

        if(registered){
            application.MyCinemaApp.setRoot("/views/LoginView.fxml");
            System.out.println("Successfully signed up");
        }else{
            feedbackLabel.setText("Username taken, please choose something else");
        }

    }
}
