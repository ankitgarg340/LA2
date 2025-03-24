package model;

import java.util.Date;

public class SongInLibrary {
    private final Song song;
    private boolean isFavorite;
    private int rating;
    private Date lastPlayed;
    private int playCounter;

    public SongInLibrary(Song s) {
        song = s;
        isFavorite = false;
        rating = 0;
        playCounter = 0;
        lastPlayed = null;
    }

    public Song getSong() {
        return song;
    }

    public void rate(int r) {
        rating = r;
    }

    public void markFavorite() {
        isFavorite = true;
    }

    public void markUnFavorite() {
        isFavorite = false;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public int getRating() {
        return rating;
    }

    public void play() {
        playCounter++;
        lastPlayed = new Date();
    }

    public int getPlayCounter() {
        return playCounter;
    }

    public Date getLastPlayed() {
        if (lastPlayed != null) {
            return (Date) lastPlayed.clone();
        } else {
            return null;
        }
    }

    void setLastPlayed(Date d){
        lastPlayed = (Date) d.clone();
    }

    void setPlayCounter(int c){
        playCounter = c;
    }
}