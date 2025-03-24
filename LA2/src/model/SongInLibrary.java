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
        setPlayCounter(playCounter+1);
        setLastPlayed(new Date());
    }

    public int getPlayCounter() {
        return playCounter;
    }

    /**
     * Get the latest date of when this song was played. Null if this song was never played
     * @return Date or null
     */
    public Date getLastPlayed() {
        if (lastPlayed != null) {
            return (Date) lastPlayed.clone();
        } else {
            return null;
        }
    }


    // setLastPlayed and setPlayCounter exist within the package when we build the library model
    // from the db
    void setLastPlayed(Date d){
        lastPlayed = (Date) d.clone();
    }

    void setPlayCounter(int c){
        playCounter = c;
    }
}