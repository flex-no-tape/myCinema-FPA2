package utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PopulateDB {
    public static void populateDB(){

        String moviesSQL = "INSERT INTO movies (title, genre, classification, rating) VALUES" +
                            "('The Matrix', 'Sci-Fi', 'M', 9)," +
                            "('Toy Story', 'Animation', 'G', 8)," +
                            "('The Dark Knight', 'Action', 'M', 10)," +
                            "('Jurassic Park', 'Adventure', 'PG', 9)," +
                            "('Parasite', 'Thriller', 'MA15+', 9)," +
                            "('Avengers: Endgame', 'Action', 'M', 8)," +
                            "('Finding Nemo', 'Animation', 'G', 8)," +
                            "('The Shining', 'Horror', 'MA15+', 8)," +
                            "('Inception', 'Sci-Fi', 'M', 9)," +
                            "('Gladiator', 'Action', 'M', 8);";

        try(Connection conn = DatabaseManager.getConnection();
            Statement stmt = conn.createStatement()){

            stmt.executeUpdate(moviesSQL);
        }catch (SQLException e){

        }

    }
}
