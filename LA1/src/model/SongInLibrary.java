package model;

public class SongInLibrary {
    private final Song song;
    private boolean isFavorite;
    private int rating;

    public SongInLibrary(Song s) {
        song = s;
        isFavorite = false;
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
}