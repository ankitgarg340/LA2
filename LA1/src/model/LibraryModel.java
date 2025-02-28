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
        if (!albums.contains(album)) {
            albums.add(album);
        }
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

    /**
     * Give a rating for a song from 1 to 5, if a song is not in the library, don't do anything
     * @param s a song
     * @param rating the rating for the song
     * @throws IllegalArgumentException if rating < 1 || rating > 5
     */
    public void rateSong(Song s, int rating) throws IllegalArgumentException {
        SongInLibrary sil = getSongInLibraryFromSong(s);
        if (sil != null) {
            if (rating < 1 || rating > 5) {
                throw new IllegalArgumentException("bad rating");
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

    private Playlist getPlaylistFromName(String name) {
        for (Playlist p : playlists) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Get the list of the songs in the playlist.
     * @param playlistName name of the playlist
     * @return list of songs in the playlist, null if there is no playlist for the given name
     */
    public List<Song> getSongsOfPlaylist(String playlistName) {
        Playlist p = getPlaylistFromName(playlistName);
        if (p == null) {
            return null;
        } else {
            return p.getSongs();
        }
    }

    /**
     * Add a song for a playlist if the playlist exist and the song is in the library
     * @param playlistName name of the playlist
     * @param song song to add to playlist
     */
    public void addSongToPlayList(String playlistName, Song song) {
        if (containSong(song)) {
            Playlist playlist = getPlaylistFromName(playlistName);
            if (playlist != null) {
                playlist.addSong(song);
            }
        }
    }

    /**
     * Remove a song for a playlist if the playlist exist and the song is in the library
     * @param playlistName name of the playlist
     * @param song song to remove from playlist
     */
    public void removeSongToPlayList(String playlistName, Song song) {
        if (containSong(song)) {
            Playlist playlist = getPlaylistFromName(playlistName);
            if (playlist != null) {
                playlist.removeSong(song);
            }
        }
    }

    public List<Song> getSongsByTitle(String title) {
        ArrayList<Song> returnList = new ArrayList<>();

        for (SongInLibrary sil : songs) {
            if (sil.getSong().getTitle().equals(title)) {
                returnList.add(sil.getSong());
            }
        }

        return returnList;
    }

    public List<Song> getSongsByArtist(String artist) {
        ArrayList<Song> returnList = new ArrayList<>();

        for (SongInLibrary sil : songs) {
            if (sil.getSong().getArtist().equals(artist)) {
                returnList.add(sil.getSong());
            }
        }

        return returnList;
    }

    public List<Song> getFavoriteSongs() {
        ArrayList<Song> returnList = new ArrayList<>();

        for (SongInLibrary sil : songs) {
            if (sil.isFavorite()) {
                returnList.add(sil.getSong());
            }
        }

        return returnList;
    }


    public List<Album> getAlbumsByTitle(String album) {
        ArrayList<Album> returnList = new ArrayList<>();

        for (Album a : albums) {
            if (a.getTitle().equals(album)) {
                returnList.add(a);
            }
        }

        return returnList;
    }

    public List<Album> getAlbumsByArtist(String artist) {
        ArrayList<Album> returnList = new ArrayList<>();

        for (Album a : albums) {
            if (a.getArtist().equals(artist)) {
                returnList.add(a);
            }
        }

        return returnList;
    }

    public boolean isPlaylistExist(String name) {
        return getPlaylistFromName(name) != null;
    }

    /**
     * Return the rating of a song
     * @param song song to get its rating
     * @return the rating of a song, -1 if the song is not in the library
     */
    public int getSongRating(Song song) {
        SongInLibrary sil = getSongInLibraryFromSong(song);
        if (sil == null) {
            return -1;
        }
        return sil.getRating();
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
