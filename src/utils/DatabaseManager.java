package utils;//DAO
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseManager {
    //Database URl relative to project root
    private static final String url = "jdbc:sqlite:MyCinema.db";

    //Attempts to establish and return connection to sqlite DB - if connection fails then an exception is thrown
    public static Connection getConnection() throws SQLException{
        System.out.println("utils.DatabaseManager: Initialising connection with Database");
        return DriverManager.getConnection(url);
    }

    public void initialiseDB(){

        //using try block to automatically close off resources
        try(Connection conn = getConnection();
            Statement stmt = conn.createStatement()){

            System.out.println("utils.DatabaseManager: Ensuring tables are created");
            //Create users table
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users ("
                    + "username TEXT PRIMARY KEY, "
                    + "password TEXT NOT NULL, "
                    + "first_name TEXT NOT NULL, "
                    + "last_name TEXT NOT NULL"
                    + ");";
            stmt.execute(createUsersTable);

            //Creates movie table - uses an autoincrementing integer as the primary key
            String createMoviesTable = "CREATE TABLE IF NOT EXISTS movies ("
                    + "movie_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "title TEXT UNIQUE NOT NULL, "
                    + "genre TEXT, "
                    + "classification TEXT, "
                    + "rating REAL"
                    + ");";
            stmt.execute(createMoviesTable);

            String createScreeningsTable = "CREATE TABLE IF NOT EXISTS screenings (" +
                    "screening_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "movie_id INT NOT NULL," +
                    "cinema_name TEXT NOT NULL," +
                    "screening_date TEXT NOT NULL," +
                    "screening_time TEXT NOT NULL," +
                    "FOREIGN KEY(movie_id) REFERENCES movies(movie_id)" +
                    ");";
            stmt.execute(createScreeningsTable);

            String createBookingsTable = "CREATE TABLE IF NOT EXISTS bookings ("
                    + "booking_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "screening_id INTEGER NOT NULL,"
                    + "username TEXT NOT NULL, "
                    + "adult_tickets INTEGER NOT NULL, "
                    + "child_tickets INTEGER NOT NULL, "
                    + "seat_info TEXT NOT NULL, "
                    + "note TEXT, " //validation of the 50 words max to happen in controller FIX ME - please ensure this is completed
                    + "record_date TEXT NOT NULL, "
                    + "FOREIGN KEY(username) REFERENCES users(username)," //references an actual user enforce integrity
                    + "FOREIGN KEY(screening_id) REFERENCES screenings (screening_id)"
                    + ");";
            stmt.execute(createBookingsTable);

        } catch (SQLException e) {
            System.err.println("utils.DatabaseManager: Error initializing the database: " + e.getMessage());
        }
    }
}
