package utils;

import javafx.stage.FileChooser;
import javafx.stage.Window;
import models.Booking;
import dao.BookingDAO;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class ExportBookings {

    public static void exportBookingsToFile(Window window){
        BookingDAO bookingDAO = new BookingDAO();
        List<Booking> bookingsList = new ArrayList<>();
        bookingsList = bookingDAO.getBookings(UserSession.getLoggedInUser());

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export booking history");
        fileChooser.setInitialFileName("MyBookings.txt");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File fileToSave = fileChooser.showSaveDialog(window);

        if(fileToSave != null){
            try(PrintWriter write = new PrintWriter(fileToSave)){
                write.println("--- MyCinema Booking History ---");
                write.println("User:" + UserSession.getLoggedInUser().getUsername());
                write.println("--------------------------------");

                for(Booking booking : bookingsList){
                    write.println("Movie: " + booking.getScreening().getMovie().getTitle());
                    write.println("Cinema: " + booking.getScreening().getCinemaName());
                    write.println("Date: " + booking.getScreening().getDate() + " at " + booking.getScreening().getTime());
                    write.println("Adult Tickets: " + booking.getNumAdultTickets());
                    write.println("Child Tickets: " + booking.getNumChildTickets());
                    write.println("--------------------------------");
                }

            }catch (IOException e){
                System.err.println("Error saving exported bookings file" + e.getMessage());
            }

        }

    }
}
