package com.example.randommovie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class Movie {
    private String title;
    private String year;
    private String duration;
    private String genre;
    private String director;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getYear() {
        return year;
    }

    public String getDuration() {
        return duration;
    }

    public String getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }
}
