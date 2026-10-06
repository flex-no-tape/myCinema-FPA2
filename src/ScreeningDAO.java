import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//DAO
//This will fetch data from database of screening options for when user selects a movie from the dropdown
public class ScreeningDAO {
    public List<Screening> getScreeningsForMovie(Movie movie){
        String sql = "SELECT * FROM screenings WHERE movie_id = ?";
        List<Screening> screeningsList = new ArrayList<>();

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, movie.getMovieID());

            try(ResultSet rs = pstmt.executeQuery()){
                while(rs.next()) {
                    Screening screening = new Screening(
                            rs.getInt("screening_id"),
                            movie,
                            rs.getString("cinema_name"),
                            rs.getString("screening_date"),
                            rs.getString("screening_time")
                    );
                    screeningsList.add(screening);
                }
            }
            return screeningsList;
        }catch(SQLException e){
            System.err.println("ScreeningDAO: Failed to get screenings: " + e.getMessage());
            return null;
        }
    }
}
