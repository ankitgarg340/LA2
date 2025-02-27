package model;

import java.util.List;
import java.util.ArrayList;

public class LibraryModel {
    private final List<SongInLibrary> songs;
    private final List<Album> albums;
    private final List<Playlist> playlists;

    public LibraryModel() {
        songs = new ArrayList<>();
        albums = new ArrayList<>();
        playlists = new ArrayList<>();
    }

    public void addSong(Song s) {
        songs.add(new SongInLibrary(s));
    }

    public boolean containSong(Song song) {
        for (SongInLibrary sil : songs) {
            if (sil.getSong().getTitle().equals(song.getTitle())) {
                return true;
            }
        }
        return false;
    }

    public void addAlbum(Album album) {
        albums.add(album);
        for (Song song : album.getSongs()) {
            if (!containSong(song)) {
                addSong(song);
            }
        }
    }

    public void createPlaylist(String name) {
        playlists.add(new Playlist(name));
    }


}
