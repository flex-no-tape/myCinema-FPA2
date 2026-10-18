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

        String screeningsSQL = "INSERT INTO screenings (movie_id, cinema_name, screening_date, screening_time) VALUES " +
                "(1, 'Cinema 1', '2026-10-26', '10:00'), (1, 'Cinema 2', '2026-10-26', '13:30'), (1, 'IMAX', '2026-10-26', '18:00'), (1, 'Gold Class', '2026-10-27', '20:15'), (1, 'Cinema 3', '2026-10-27', '15:00'), " +
                "(2, 'Cinema 2', '2026-10-26', '10:30'), (2, 'Cinema 1', '2026-10-26', '14:15'), (2, 'Cinema 3', '2026-10-27', '09:00'), (2, 'Gold Class', '2026-10-27', '19:30'), (2, 'IMAX', '2026-10-28', '21:00'), " +
                "(3, 'IMAX', '2026-10-26', '11:00'), (3, 'Cinema 3', '2026-10-26', '16:45'), (3, 'Gold Class', '2026-10-27', '18:30'), (3, 'Cinema 1', '2026-10-28', '12:00'), (3, 'Cinema 2', '2026-10-28', '20:45'), " +
                "(4, 'Cinema 1', '2026-10-26', '12:30'), (4, 'Cinema 2', '2026-10-27', '15:30'), (4, 'Cinema 3', '2026-10-27', '21:15'), (4, 'IMAX', '2026-10-28', '19:00'), (4, 'Gold Class', '2026-10-29', '17:30'), " +
                "(5, 'Gold Class', '2026-10-26', '21:00'), (5, 'Cinema 3', '2026-10-27', '13:45'), (5, 'Cinema 1', '2026-10-28', '16:15'), (5, 'IMAX', '2026-10-29', '20:30'), (5, 'Cinema 2', '2026-10-29', '22:45'), " +
                "(6, 'IMAX', '2026-10-26', '14:00'), (6, 'Cinema 1', '2026-10-27', '11:30'), (6, 'Cinema 2', '2026-10-28', '18:45'), (6, 'Gold Class', '2026-10-29', '19:15'), (6, 'Cinema 3', '2026-10-30', '21:00'), " +
                "(7, 'Cinema 2', '2026-10-26', '09:30'), (7, 'Cinema 1', '2026-10-27', '10:15'), (7, 'Cinema 3', '2026-10-28', '14:30'), (7, 'Gold Class', '2026-10-29', '12:00'), (7, 'IMAX', '2026-10-30', '16:00'), " +
                "(8, 'Cinema 3', '2026-10-26', '22:00'), (8, 'Gold Class', '2026-10-27', '23:15'), (8, 'Cinema 1', '2026-10-28', '20:00'), (8, 'Cinema 2', '2026-10-29', '19:45'), (8, 'IMAX', '2026-10-30', '22:30'), " +
                "(9, 'IMAX', '2026-10-26', '16:30'), (9, 'Cinema 1', '2026-10-27', '18:15'), (9, 'Cinema 2', '2026-10-28', '21:30'), (9, 'Cinema 3', '2026-10-29', '15:45'), (9, 'Gold Class', '2026-10-30', '20:15'), " +
                "(10, 'Gold Class', '2026-10-26', '17:00'), (10, 'Cinema 2', '2026-10-27', '12:45'), (10, 'Cinema 3', '2026-10-28', '10:30'), (10, 'IMAX', '2026-10-29', '14:15'), (10, 'Cinema 1', '2026-10-30', '21:45')";


        try(Connection conn = DatabaseManager.getConnection();
            Statement stmt = conn.createStatement()){

            stmt.executeUpdate(moviesSQL);

        }catch (SQLException e){}
        try(Connection conn = DatabaseManager.getConnection();
            Statement stmt = conn.createStatement()){

            stmt.executeUpdate(screeningsSQL);

        }catch (SQLException e){}

    }
}
