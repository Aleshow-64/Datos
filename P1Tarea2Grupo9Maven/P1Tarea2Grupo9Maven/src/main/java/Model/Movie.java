package Model;

public class Movie {

    private String title;
    private String director;
    private int year;
    private String genre;
    private String synopsis;

    public Movie(String title, String director, int year, String genre, String synopsis) {
        this.title = title;
        this.director = director;
        this.year = year;
        this.genre = genre;
        this.synopsis = synopsis;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    @Override
    public String toString() {
        return this.title + " (" + this.year + ")";
    }
}
