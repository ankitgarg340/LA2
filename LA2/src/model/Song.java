package model;

public class Song {
    private final String title;
    private final String artist;
    private final String albumName;

    public Song(String titleIn, String artistIn, String albumName) {
        title = titleIn;
        artist = artistIn;
        this.albumName = albumName;
    }

    public String getTitle() {
        return title;
    }

    public String toString() {
        return title + " by " + artist + " in album " + albumName;
    }

    public String getArtist() {
        return artist;
    }
}
