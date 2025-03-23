package model;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    protected final String name;
    protected final List<Song> songs;

    public Playlist(String name) {
        this.name = name;
        songs = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addSong(Song s) {
        // if a song is already in the playlist, don't add it again
        if (!songs.contains(s)) {
            songs.add(s);
        }
    }

    public void removeSong(Song s) {
        songs.remove(s);
    }

    public List<Song> getSongs() {
        return new ArrayList<>(songs);
    }
    
}
