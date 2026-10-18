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

public class BookingController {

    @FXML private TextField seatInfoField;
    @FXML private TextField noteArea;
    @FXML private Label bookingTitleLabel;
    @FXML private Label welcomeLabel;
    @FXML private Label feedbackLabel;
    @FXML private ComboBox<String> cinemaComboBox;
    @FXML private ComboBox<Screening> dateComboBox;
    @FXML private ComboBox<Integer> adultTicketsBox;
    @FXML private ComboBox<Integer> childTicketsBox;

    private List<Screening> allScreenings;
    private Movie movieToBook;
    private ScreeningDAO screeningDAO = new ScreeningDAO();
    private BookingDAO bookingDAO = new BookingDAO();

    @FXML
    public void initialize(Movie selectedMovie) {

        models.User currentUser = UserSession.getLoggedInUser();

        if(currentUser != null) {
            welcomeLabel.setText("Welcome to MyCinema, " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
        }else {
            welcomeLabel.setText("Welcome to MyCinema!");
        }

        this.movieToBook = selectedMovie;

        bookingTitleLabel.setText("Make a booking for: " + movieToBook.getTitle());

        allScreenings = screeningDAO.getScreeningsForMovie(selectedMovie);

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

        //Number of tickets
        for (int i = 0; i <= 10; i++) {
            adultTicketsBox.getItems().add(i);
            childTicketsBox.getItems().add(i);
        }

        // Set a default selection so it doesn't stay blank
        adultTicketsBox.getSelectionModel().selectFirst();
        childTicketsBox.getSelectionModel().selectFirst();
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
        String seatInfo = seatInfoField.getText().trim();
        String note = noteArea.getText().trim();

        //empty required field check
        if (selectedScreening == null || seatInfo.isEmpty()) {
            feedbackLabel.setStyle("-fx-text-fill: red;");
            feedbackLabel.setText("Please select a date/time and enter seat info.");
            return;
        }

        //validation for word limit
        if (!note.isEmpty()) {
            String[] words = note.split("\\s+");
            if (words.length > 50) {
                feedbackLabel.setStyle("-fx-text-fill: red;");
                feedbackLabel.setText("Note cannot exceed 50 words. You have " + words.length + ".");
                return;
            }
        }

        //retrieve values of tickets
        int adultTickets = adultTicketsBox.getValue() != null ? adultTicketsBox.getValue() : 0;
        int childTickets = childTicketsBox.getValue() != null ? childTicketsBox.getValue() : 0;

        if (adultTickets == 0 && childTickets == 0) {
            feedbackLabel.setStyle("-fx-text-fill: red;");
            feedbackLabel.setText("You must book at least one ticket.");
            return;
        }

        Booking booking = new Booking(
                utils.UserSession.getLoggedInUser(),
                selectedScreening,
                adultTickets,
                childTickets,
                seatInfo,
                note
        );
        boolean success = bookingDAO.addBooking(booking);

        if (success) {
            feedbackLabel.setStyle("-fx-text-fill: green;");
            feedbackLabel.setText("Success! Booking confirmed.");
            switchToDashboard();
        } else {
            feedbackLabel.setStyle("-fx-text-fill: red;");
            feedbackLabel.setText("Database error. Please try again.");
        }
    }

    @FXML
    public void switchToDashboard(){
        System.out.println("Returning to dashboard...");
        application.MyCinemaApp.setRoot("/views/DashboardView.fxml"); //TODO: Decide on returning to home or not
    }

    @FXML
    public void handleLogOut(){
        System.out.println("Logging user out - Switching to login screen");
        UserSession.logOutUser();
        application.MyCinemaApp.setRoot("/views/LoginView.fxml");
    }
}
