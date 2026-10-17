package controllers;

import dao.MovieDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Movie;
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

    public void handleLogOut(){
        System.out.println("Logging user out - Switching to login screen");
        UserSession.logOutUser();
        application.MyCinemaApp.setRoot("/views/LoginView.fxml");
    }
}
