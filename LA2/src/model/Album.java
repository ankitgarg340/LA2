package model;

import java.util.List;
import java.util.ArrayList;

public class Album {
    private final String title;
    private final String artist;
    private final String genre;
    private final String year;
    private final List<Song> songs;

    public Album(String title, String artist, String genre, String year, List<Song> songs) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.year = year;
        this.songs = new ArrayList<>(songs);
    }


    public List<Song> getSongs() {
        return new ArrayList<>(this.songs);
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String toString() {
        return title + " by " + artist + " of genre " + genre + " released in " + year;
    }

}
