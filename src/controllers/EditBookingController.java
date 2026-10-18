package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Movie;
import models.Screening;
import models.Booking;
import utils.UserSession;
import dao.*;

import java.util.ArrayList;
import java.util.List;

public class EditBookingController {


    @FXML private Label bookingTitleLabel;
    @FXML private Label welcomeLabel;
    @FXML private Label feedbackLabel;
    @FXML private ComboBox<String> cinemaComboBox;
    @FXML private ComboBox<Screening> dateComboBox;


    private List<Screening> allScreenings;
    private Movie bookedMovie;
    private Booking originalBooking;
    private ScreeningDAO screeningDAO = new ScreeningDAO();
    private BookingDAO bookingDAO = new BookingDAO();

    @FXML
    public void initialize(Booking booking) {
        originalBooking = booking;
        bookedMovie = originalBooking.getScreening().getMovie();

        models.User currentUser = UserSession.getLoggedInUser();

        if(currentUser != null) {
            welcomeLabel.setText("Welcome to MyCinema, " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
        }else {
            welcomeLabel.setText("Welcome to MyCinema!");
        }

        bookingTitleLabel.setText("Edit booking for: " + bookedMovie.getTitle());

        allScreenings = screeningDAO.getScreeningsForMovie(bookedMovie);

        //list of unique cinemas
        List<String> uniqueCinemas = new ArrayList<>();
        for (Screening s: allScreenings){
            if(!uniqueCinemas.contains(s.getCinemaName())){
                uniqueCinemas.add(s.getCinemaName());
            }
        }

        cinemaComboBox.getItems().addAll(uniqueCinemas);

        //update the date dropdown box when cinema type is selected
        cinemaComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                updateDateDropdown(newValue);
            }
        });

    }

    @FXML
    private void updateDateDropdown(String selectedCinema){
        dateComboBox.getItems().clear();

        for(Screening s : allScreenings){
            if(s.getCinemaName().equals(selectedCinema)){
                dateComboBox.getItems().add(s);
            }
        }
    }


    @FXML
    public void handleBookingConfirmation() {
        Screening selectedScreening = dateComboBox.getValue();

        Booking booking = new Booking(
                utils.UserSession.getLoggedInUser(),
                selectedScreening,
                originalBooking.getNumAdultTickets(),
                originalBooking.getNumChildTickets(),
                originalBooking.getSeatInfo(),
                originalBooking.getBookingNote()
        );
        boolean success = bookingDAO.editBooking(booking);

        if(success) {
            feedbackLabel.setStyle("-fx-text-fill: green;");
            feedbackLabel.setText("Success! Booking confirmed.");
            switchToDashboard();
        }else{
            feedbackLabel.setStyle("-fx-text-fill: red;");
            feedbackLabel.setText("Database error. Please try again.");
        }
    }

    @FXML
    public void switchToDashboard(){
        System.out.println("Returning to dashboard...");
        application.MyCinemaApp.setRoot("/views/DashboardView.fxml");
    }

    @FXML
    public void handleLogOut(){
        System.out.println("Logging user out - Switching to login screen");
        UserSession.logOutUser();
        application.MyCinemaApp.setRoot("/views/LoginView.fxml");
    }
}
