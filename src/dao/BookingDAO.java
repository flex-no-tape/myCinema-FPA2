package dao;

import models.Booking;
import models.User;
import models.Movie;
import models.Screening;
import utils.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    //viewBookingHistory

    public boolean addBooking(Booking booking){
        String sql = "INSERT INTO bookings (username, screening_id, " +
                     "adult_tickets, child_tickets, seat_info, note, record_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, booking.getUser().getUsername());
            pstmt.setInt(2, booking.getScreening().getScreening_id());
            pstmt.setInt(3, booking.getNumAdultTickets());
            pstmt.setInt(4, booking.getNumChildTickets());
            pstmt.setString(5, booking.getSeatInfo());
            pstmt.setString(6, booking.getBookingNote());
            pstmt.setString(7, LocalDate.now().toString()); //using the java library local date to get the current date when booking for the record date.

            pstmt.executeUpdate();
            return true;
        }catch(SQLException e){
            System.err.println("dao.BookingDAO: models.Booking failed: " + e.getMessage());
            return false;
        }
    }

    public boolean editBooking(Booking booking){
        String sql = "UPDATE bookings SET screening_id = ?," +
                     "adult_tickets = ?, child_tickets = ?, seat_info = ?, note = ? WHERE booking_id = ?";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, booking.getScreening().getScreening_id());
            pstmt.setInt(2, booking.getNumAdultTickets());
            pstmt.setInt(3, booking.getNumChildTickets());
            pstmt.setString(4, booking.getSeatInfo());
            pstmt.setString(5, booking.getBookingNote());
            pstmt.setInt(6, booking.getBookingID());


            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }catch(SQLException e){
            System.err.println("dao.BookingDAO: Update to booking failed: " + e.getMessage());
            return false;
        }
    }

    public List<Booking> getBookings(User user){
        List<Booking> userBookings = new ArrayList<>();

        String sql = "SELECT b.booking_id, b.username, b.adult_tickets, b.child_tickets, " +
                "b.seat_info, b.note, b.record_date, " +
                "s.screening_id, s.cinema_name, s.screening_date, s.screening_time, " +
                "m.movie_id, m.title, m.genre, m.classification, m.rating " +
                "FROM bookings b " +
                "JOIN screenings s ON b.screening_id = s.screening_id " +
                "JOIN movies m ON s.movie_id = m.movie_id " +
                "WHERE b.username = ? " +
                "ORDER BY b.booking_id DESC";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, user.getUsername());

            try(ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    Movie movie = new Movie(
                        rs.getInt("movie_id"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getString("classification"),
                        rs.getInt("rating")
                    );

                    Screening screening = new Screening(
                        rs.getInt("screening_id"),
                        movie,
                        rs.getString("cinema_name"),
                        rs.getString("screening_date"),
                        rs.getString("screening_time")
                    );

                   Booking booking = new Booking(
                       rs.getInt("booking_id"),
                       user,
                       screening,
                       rs.getInt("adult_tickets"),
                       rs.getInt("child_tickets"),
                       rs.getString("seat_info"),
                       rs.getString("note"),
                       rs.getString("record_date")
                    );

                    userBookings.add(booking);
                }
            }
            return userBookings;
        }catch (SQLException e){
            System.err.println("dao.BookingDAO: Error returning previous user bookings: " + e.getMessage());
            return null;
        }
    }

    public boolean deleteBooking(Booking booking){
        //Uses booking_id and username for confirmation about
        String sql = "DELETE FROM bookings WHERE booking_id = ? AND username = ?";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, booking.getBookingID());
            pstmt.setString(2, booking.getUser().getUsername());

           int rowsAffected = pstmt.executeUpdate();
           return rowsAffected > 0;

        }catch(SQLException e){
            System.err.println("dao.BookingDAO: Failed to cancel booking: " + e.getMessage());
            return false;
        }
    }

}
