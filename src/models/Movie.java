package models;

//Model
public class Movie {
    private int movieID;
    private String movieTitle;
    private String genre;
    private String classification;
    private int rating;

    public Movie(String movieTitle,
                 String genre,
                 String classification,
                 int rating){
        this.movieTitle = movieTitle;
        this.genre = genre;
        this.classification = classification;
        this.rating = rating;
    }

    public Movie(int movieID,
                 String movieTitle,
                 String genre,
                 String classification,
                 int rating){
        this.movieID = movieID;
        this.movieTitle = movieTitle;
        this.genre = genre;
        this.classification = classification;
        this.rating = rating;
    }

    public int getMovieID(){return movieID;}
    public String getTitle(){return movieTitle;}
    public String getGenre(){return genre;}
    public String getClassification(){return classification;}
    public int getRating() {return rating;}
}
