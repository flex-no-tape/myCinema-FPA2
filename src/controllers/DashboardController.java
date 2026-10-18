package controllers;

import dao.MovieDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Window;
import models.Movie;
import utils.ExportBookings;
import utils.UserSession;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXML;
import java.util.List;

public class DashboardController {

    @FXML private Label welcomeLabel;
    @FXML private TableView<Movie> movieTable;


    private MovieDAO movieDAO = new MovieDAO();

    @FXML
    public void initialize(){
        models.User currentUser = UserSession.getLoggedInUser();

        if(currentUser != null) {
            welcomeLabel.setText("Welcome to MyCinema, " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
        }else {
            welcomeLabel.setText("Welcome to MyCinema!");
        }

        TableColumn<Movie, String> titleColumn = new TableColumn<>("Title");
        TableColumn<Movie, String> genreColumn = new TableColumn<>("Genre");
        TableColumn<Movie, String> classificationColumn = new TableColumn<>("Classification");
        TableColumn<Movie, Integer> ratingColumn = new TableColumn<>("Rating");

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        classificationColumn.setCellValueFactory(new PropertyValueFactory<>("classification"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

        movieTable.getColumns().addAll(titleColumn, genreColumn, classificationColumn, ratingColumn);

        List<Movie> movieList = movieDAO.getMoviesList();
        ObservableList<Movie> observableMovies = FXCollections.observableArrayList(movieList);
        movieTable.setItems(observableMovies);


    }

    @FXML
    public void handleBookSelectedMovie(){
        Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();

        if(selectedMovie == null){
            System.out.println("No movie selected");
            return;
        }

        System.out.println("Entering booking form for: " + selectedMovie.getTitle());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BookingView.fxml"));
            Parent root = loader.load();

            BookingController bookingController = loader.getController();

            bookingController.initialize(selectedMovie);

            movieTable.getScene().setRoot(root);
        } catch(Exception e) {
            System.err.println("Error loading bookingscreen: " + e.getMessage());
        }
    }

    @FXML
    public void handleExportBookings(){
        Window currentWindow = welcomeLabel.getScene().getWindow();
        ExportBookings.exportBookingsToFile(currentWindow);
    }

    @FXML
    public void handleEditAccount(){
        System.out.println("Switching to edit account screen");
        application.MyCinemaApp.setRoot("/views/AccountView.fxml");
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
