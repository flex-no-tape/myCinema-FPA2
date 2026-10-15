package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MyCinemaApp extends Application{

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        //initiialise the db to ensure its created and setup correctly
        utils.DatabaseManager databaseManager = new utils.DatabaseManager();
        databaseManager.initialiseDB();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LoginView.fxml")); //Add url to view
        Parent root = loader.load();

        Scene scene = new Scene(root, 900, 600);

        primaryStage.setTitle("MyCinema - Booking System");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void setRoot(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(MyCinemaApp.class.getResource(fxmlPath));
            Parent root = loader.load();
            primaryStage.getScene().setRoot(root);
        } catch (Exception e) {
            System.err.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        launch(args);
    }
}
