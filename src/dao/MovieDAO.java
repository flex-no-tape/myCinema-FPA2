package dao;

import models.Movie;
import utils.DatabaseManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//This DAO is for fetching list of movies to populate the dropdown in the booking form and for when seeing details of a screening
public class MovieDAO {

    public List<Movie> getMoviesList(){
        String sql = "SELECT * FROM movies";
        List<Movie> moviesList = new ArrayList<>();

        try(Connection conn = DatabaseManager.getConnection();
            Statement stmt = conn.createStatement()){
            try(ResultSet rs = stmt.executeQuery(sql)) {
                while(rs.next()){
                    Movie movie = new Movie(
                        rs.getInt("movie_id"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getString("classification"),
                        rs.getInt("rating")
                    );
                    moviesList.add(movie);
                }
            }
            return moviesList;
        }catch(SQLException e){
            System.err.println("dao.MovieDAO: Failed to get list of movies: " + e.getMessage());
            return null;
        }
    }
}
