//Model
public class Booking {
    private int bookingID;
    private String username;
    private int screeningID;
    private int numAdultTickets;
    private int numChildTickets;
    private String seatInfo;
    private String bookingNote;
    private String recordDate;

    public Booking(String username,
                   int screeningID,
                   int numAdultTickets,
                   int numChildTickets,
                   String seatInfo,
                   String bookingNote){
        this.username = username;
        this.screeningID = screeningID;
        this.numAdultTickets = numAdultTickets;
        this.numChildTickets = numChildTickets;
        this.seatInfo = seatInfo;
        this.bookingNote = bookingNote;
    }

    //overloaded constructor used by bookingDAO when returning booking history
    public Booking(int bookingID,
                   String username,
                   int screeningID,
                   int numAdultTickets,
                   int numChildTickets,
                   String seatInfo,
                   String bookingNote,
                   String recordDate){
        this.bookingID = bookingID;
        this.username = username;
        this.screeningID = screeningID;
        this.numAdultTickets = numAdultTickets;
        this.numChildTickets = numChildTickets;
        this.seatInfo = seatInfo;
        this.bookingNote = bookingNote;
        this.recordDate = recordDate;
    }


    //getters
    public int getBookingID(){return bookingID;}
    public String getUsername(){return username;}
    public int getBookingScreeningID(){return screeningID;}
    public int getNumAdultTickets() {return numAdultTickets;}
    public int getNumChildTickets() {return numChildTickets;}
    public String getSeatInfo() {return seatInfo;}
    public String getBookingNote() {return bookingNote;}
    public String getRecordDate(){return recordDate;}

    //setters
    public void setBookingID(int bookingID){this.bookingID = bookingID;}
    public void setUsername(String username){this.username = username;}
    public void setNumAdultTickets(int numAdultTickets) {this.numAdultTickets = numAdultTickets;}
    public void setNumChildTickets(int numChildTickets) {this.numChildTickets = numChildTickets;}
    public void setSeatInfo(String seatInfo) {this.seatInfo = seatInfo;}
    public void setBookingNote(String bookingNote) {this.bookingNote = bookingNote;}
    public void setRecordDate(String recordDate){this.recordDate = recordDate;}

}
