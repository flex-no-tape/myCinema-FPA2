package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;
import models.Booking;
import utils.ExportBookings;
import utils.UserSession;
import dao.*;

import java.util.List;


public class HistoryController {

    @FXML private Label welcomeLabel;
    @FXML private TableView<Booking> historyTable;
    @FXML private Label feedbackLabel;

    private BookingDAO bookingDAO = new BookingDAO();
    private ObservableList<Booking> observableBookings;

    @FXML
    public void initialize() {

        models.User currentUser = UserSession.getLoggedInUser();

        if(currentUser != null) {
            welcomeLabel.setText("Welcome to MyCinema, " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
        }else {
            welcomeLabel.setText("Welcome to MyCinema!");
        }

        TableColumn<Booking, String> titleCol = new TableColumn<>("Movie");
        titleCol.setCellValueFactory(cellData -> {
            Booking booking = cellData.getValue();

            String title = booking.getScreening().getMovie().getTitle();
            return new SimpleStringProperty(title);
        });

        TableColumn<Booking, String> cinemaCol = new TableColumn<>("Cinema");
        cinemaCol.setCellValueFactory(cellData -> {
            Booking booking = cellData.getValue();

            String cinema = booking.getScreening().getCinemaName();
            return new SimpleStringProperty(cinema);
        });


        TableColumn<Booking, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(cellData -> {
            Booking booking = cellData.getValue();

            String date = booking.getScreening().getDate();
            return new SimpleStringProperty(date);
        });

        TableColumn<Booking, String> timeCol = new TableColumn<>("Time");
        timeCol.setCellValueFactory(cellData -> {
            Booking booking = cellData.getValue();

            String time = booking.getScreening().getTime();
            return new SimpleStringProperty(time);
        });

        TableColumn<Booking, Integer> adultCol = new TableColumn<>("Adults");
        adultCol.setCellValueFactory(new PropertyValueFactory<>("numAdultTickets"));

        TableColumn<Booking, Integer> childCol = new TableColumn<>("Children");
        childCol.setCellValueFactory(new PropertyValueFactory<>("numChildTickets"));

        historyTable.getColumns().addAll(titleCol, cinemaCol, dateCol, timeCol, adultCol, childCol);


        loadTableData();
    }

    private void loadTableData() {
        List<Booking> userBookings = bookingDAO.getBookings(utils.UserSession.getLoggedInUser());
        observableBookings = FXCollections.observableArrayList(userBookings);
        historyTable.setItems(observableBookings);
    }

    @FXML
    public void handleEditBooking(){
        Booking selectedBooking = historyTable.getSelectionModel().getSelectedItem();
        if(selectedBooking == null){
            feedbackLabel.setStyle("-fx-text-fill: red;");
            feedbackLabel.setText("Please select a booking to edit");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditBookingView.fxml"));
            Parent root = loader.load();

            EditBookingController editBookingController = loader.getController();

            editBookingController.initialize(selectedBooking);

            historyTable.getScene().setRoot(root);
        } catch(Exception e) {
            System.err.println("Error loading bookingscreen: " + e.getMessage());
        }
    }

    @FXML
    public void handleEditAccount(){
        System.out.println("Switching to edit account screen");
        application.MyCinemaApp.setRoot("/views/AccountView.fxml");
    }

    @FXML
    public void handleCancelBooking(){
        Booking selectedBooking = historyTable.getSelectionModel().getSelectedItem();
        boolean success = bookingDAO.deleteBooking(selectedBooking);

        if(success){
            feedbackLabel.setStyle("-fx-text-fill: green;");
            feedbackLabel.setText("Booking successfully cancelled");
        }
    };

    @FXML
    public void handleExportBookings(){
        Window currentWindow = welcomeLabel.getScene().getWindow();
        ExportBookings.exportBookingsToFile(currentWindow);
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
