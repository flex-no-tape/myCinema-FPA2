//Model
public class Screening {
    private int screening_id;
    private Movie movie;
    private String cinemaName;
    private String date;
    private String time;

    public Screening (Movie movie,
                      String cinemaName,
                      String date,
                      String time){
        this.movie = movie;
        this.cinemaName = cinemaName;
        this.date = date;
        this.time = time;
    }

    public Screening (int screening_id,
                      Movie movie,
                      String cinemaName,
                      String date,
                      String time){
        this.screening_id = screening_id;
        this.movie = movie;
        this.cinemaName = cinemaName;
        this.date = date;
        this.time = time;
    }

    public int getScreening_id(){return screening_id;}
    public Movie getMovie(){return movie;}
    public String getCinemaName(){return cinemaName;}
    public String getDate(){return date;}
    public String getTime(){return time;}
}

//SETTERS
