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

            pstmt.setString(1, booking.getUsername());
            pstmt.setInt(2, booking.getBookingScreeningID()); // FIX ME - IMPLEMENT PASSWORD HASHING
            pstmt.setInt(3, booking.getNumAdultTickets());
            pstmt.setInt(4, booking.getNumChildTickets());
            pstmt.setString(5, booking.getSeatInfo());
            pstmt.setString(6, booking.getBookingNote());
            pstmt.setString(7, LocalDate.now().toString()); //using the java library local date to get the current date when booking for the record date. THis should not be updated upon editing booking

            pstmt.executeUpdate();
            return true;
        }catch(SQLException e){
            System.err.println("BookingDAO: Booking failed: " + e.getMessage());
            return false;
        }
    }

    public boolean editBooking(Booking booking){
        String sql = "UPDATE bookings SET screening_id = ?," +
                     "adult_tickets = ?, child_tickets = ?, seat_info = ?, note = ? WHERE username = ?";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, booking.getBookingScreeningID());
            pstmt.setInt(2, booking.getNumAdultTickets());
            pstmt.setInt(3, booking.getNumChildTickets());
            pstmt.setString(4, booking.getSeatInfo());
            pstmt.setString(5, booking.getBookingNote());
            pstmt.setString(6, booking.getUsername());


            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }catch(SQLException e){
            System.err.println("BookingDAO: Update to booking failed: " + e.getMessage());
            return false;
        }
    }

    public List<Booking> getBookings(User user){
        List<Booking> userBookings = new ArrayList<>();

        String sql = "SELECT * FROM bookings WHERE username = ? ORDER BY booking_id DESC";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, user.getUsername());

            try(ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    int bookingID = rs.getInt("booking_id");
                    int screeningID = rs.getInt("screening_id");
                    int numAdultTickets = rs.getInt("adult_tickets");
                    int numChildTickets = rs.getInt("child_tickets");
                    String seatInfo = rs.getString("seat_info");
                    String bookingNote = rs.getString("note");
                    String recordDate = rs.getString("record_date");


                    Booking booking = new Booking(bookingID, user.getUsername(), screeningID,
                                                  numAdultTickets, numChildTickets, seatInfo, bookingNote, recordDate);
                    userBookings.add(booking);
                }
            }
            return userBookings;
        }catch (SQLException e){
            System.err.println("BookingDAO: Error returning previous user bookings: " + e.getMessage());
            return null;
        }
    }

    public boolean deleteBooking(Booking booking){
        //Uses booking_id and username for confirmation about
        String sql = "DELETE FROM bookings WHERE booking_id = ? AND username = ?";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, booking.getBookingID());
            pstmt.setString(2, booking.getUsername());

           int rowsAffected = pstmt.executeUpdate();
           return rowsAffected > 0;

        }catch(SQLException e){
            System.err.println("BookingDAO: Failed to cancel booking: " + e.getMessage());
            return false;
        }
    }

}
