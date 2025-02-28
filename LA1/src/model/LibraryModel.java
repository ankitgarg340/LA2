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
        if (!containSong(s)) {
            songs.add(new SongInLibrary(s));
        }
    }

    public boolean containSong(Song song) {
        return getSongInLibraryFromSong(song) != null;
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
        if (!getAllPlaylistsNames().contains(name)) {
            playlists.add(new Playlist(name));
        }
    }

    public List<Song> getAllSongs() {
        List<Song> returnSongs = new ArrayList<>();
        for (SongInLibrary s : songs) {
            returnSongs.add(s.getSong());
        }
        return returnSongs;
    }

    public List<Album> getAllAlbums() {
        return new ArrayList<>(albums);
    }

    public List<String> getAllPlaylistsNames() {
        List<String> returnPlaylistsNames = new ArrayList<>();
        for (Playlist p : playlists) {
            returnPlaylistsNames.add(p.getName());
        }
        return returnPlaylistsNames;
    }

    public void rateSong(Song s, int rating) throws IllegalAccessException {
        SongInLibrary sil = getSongInLibraryFromSong(s);
        if (sil != null) {
            if (rating < 1 || rating > 5) {
                throw new IllegalAccessException("bad rating");
            }
            sil.rate(rating);

            if (rating == 5) {
                sil.markFavorite();
            }
        }
    }

    public void markSongFavorite(Song s) {
        SongInLibrary sil = getSongInLibraryFromSong(s);
        if (sil != null) {
            sil.markFavorite();
        }
    }

    public void markSongUnFavorite(Song s) {
        SongInLibrary sil = getSongInLibraryFromSong(s);
        if (sil != null) {
            sil.markUnFavorite();
        }
    }

    public Playlist getPlaylistFromName(String name) {
        for (Playlist p : playlists) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    public List<Song> getSongsOfPlaylist(String playlistName) {
        return getPlaylistFromName(playlistName).getSongs();
    }

    public void addSongToPlayList(String playlistName, Song song) {
        if (containSong(song)) {
            Playlist playlist = getPlaylistFromName(playlistName);
            playlist.addSong(song);
        }
    }

    public void removeSongToPlayList(String playlistName, Song song) {
        if (containSong(song)) {
            Playlist playlist = getPlaylistFromName(playlistName);
            playlist.removeSong(song);
        }
    }

    public List<Song> getSongsByTitle(String title) {
        ArrayList<Song> returnList = new ArrayList<Song>();

        for (SongInLibrary sil : songs) {
            if (sil.getSong().getTitle().equals(title)) {
                returnList.add(sil.getSong());
            }
        }

        return returnList;
    }

    public List<Song> getSongsByArtist(String artist) {
        ArrayList<Song> returnList = new ArrayList<Song>();

        for (SongInLibrary sil : songs) {
            if (sil.getSong().getArtist().equals(artist)) {
                returnList.add(sil.getSong());
            }
        }

        return returnList;
    }

    public List<Song> getFavoriteSongs() {
        ArrayList<Song> returnList = new ArrayList<Song>();

        for (SongInLibrary sil : songs) {
            if (sil.isFavorite()) {
                returnList.add(sil.getSong());
            }
        }

        return returnList;
    }


    public List<Album> getAlbumsByTitle(String album) {
        ArrayList<Album> returnList = new ArrayList<Album>();

        for (Album a : albums) {
            if (a.getTitle().equals(album)) {
                returnList.add(a);
            }
        }

        return returnList;
    }

    public List<Album> getAlbumsByArtist(String artist) {
        ArrayList<Album> returnList = new ArrayList<Album>();

        for (Album a : albums) {
            if (a.getArtist().equals(artist)) {
                returnList.add(a);
            }
        }

        return returnList;
    }


    private SongInLibrary getSongInLibraryFromSong(Song s) {
        for (SongInLibrary sil : songs) {
            if (sil.getSong().equals(s)) {
                return sil;
            }
        }
        return null;
    }
}
