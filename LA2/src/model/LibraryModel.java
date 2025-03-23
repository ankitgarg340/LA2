package model;

import com.google.gson.Gson;

import java.util.*;

public class LibraryModel {
    private final List<SongInLibrary> songs;
    private final List<Album> albums;
    private final List<Playlist> playlists;
    private transient Playlist recentPlaylist;
    private transient Playlist frequentPlaylist;
    private transient Playlist faivoritePlaylist;
    private transient Playlist topRatedPlaylist;

    private transient final String RECENT_PLAYLIST_NAME = "Most Recent Played Songs";
    private transient final String FREQUENT_PLAYLIST_NAME = "Most Frequently Played Songs";
    private transient final String FAVORITE_PLAYLIST_NAME = "Favorite Songs";
    private transient final String TOP_RATED_PLAYLIST_NAME = "Top Rated Songs";

    public LibraryModel() {
        songs = new ArrayList<>();
        albums = new ArrayList<>();
        playlists = new ArrayList<>();
        recentPlaylist = new Playlist(RECENT_PLAYLIST_NAME);
        frequentPlaylist = new Playlist(FREQUENT_PLAYLIST_NAME);
        faivoritePlaylist = new Playlist(FAVORITE_PLAYLIST_NAME);
        topRatedPlaylist = new Playlist(TOP_RATED_PLAYLIST_NAME);
    }

    public void addSong(Song s) {
        if (!containSong(s)) {
            songs.add(new SongInLibrary(s));
        }
    }

    public boolean containSong(Song song) {
        return getSongInLibraryFromSong(song) != null;
    }

    /**
     * Add an album and all its songs to the library
     * @param album - album to add to the library
     */
    public void addAlbum(Album album) {
        addOnlyAlbum(album);

        // add the songs of the album
        for (Song song : album.getSongs()) {

            // skip song if it was added before
            if (!containSong(song)) {
                addSong(song);
            }
        }
    }

    /**
     * Add only the album to the library
     * @param album - album to add to the library
     */
    public void addOnlyAlbum(Album album){
        if (!albums.contains(album)) {
            albums.add(album);
        }

    }

    public void createPlaylist(String name) {
        if (!getUserPlaylistsNames().contains(name) && !isPlaylistAutomatic(name)) {
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

    public List<String> getUserPlaylistsNames() {
        List<String> returnPlaylistsNames = new ArrayList<>();
        for (Playlist p : playlists) {
            returnPlaylistsNames.add(p.getName());
        }
        return returnPlaylistsNames;
    }

    public List<String> getAllPlaylistsNames() {
        List<String> returnPlaylistsNames = getUserPlaylistsNames();
        returnPlaylistsNames.add(recentPlaylist.getName());
        returnPlaylistsNames.add(frequentPlaylist.getName());
        returnPlaylistsNames.add(faivoritePlaylist.getName());
        returnPlaylistsNames.add(topRatedPlaylist.getName());
        return returnPlaylistsNames;
    }


    /**
     * Give a rating for a song from 1 to 5, if a song is not in the library, don't do anything
     *
     * @param s      a song
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
            initTopRatedPlaylist();
            if (rating == 5) {
                sil.markFavorite();
            }
            initFavoritePlaylist();
        }
    }

    public void markSongFavorite(Song s) {
        SongInLibrary sil = getSongInLibraryFromSong(s);
        if (sil != null) {
            sil.markFavorite();
            initFavoritePlaylist();
        }
    }

    public void markSongUnFavorite(Song s) {
        SongInLibrary sil = getSongInLibraryFromSong(s);
        if (sil != null) {
            sil.markUnFavorite();
            initFavoritePlaylist();
        }
    }

    private Playlist getPlaylistFromName(String name) {
        if (name.equals(RECENT_PLAYLIST_NAME)) {
            return recentPlaylist;
        }
        if (name.equals(FREQUENT_PLAYLIST_NAME)) {
            return frequentPlaylist;
        }
        if (name.equals(FAVORITE_PLAYLIST_NAME)) {
            return faivoritePlaylist;
        }
        if (name.equals(TOP_RATED_PLAYLIST_NAME)) {
            return topRatedPlaylist;
        }
        for (Playlist p : playlists) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Get the list of the songs in the playlist.
     *
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
     *
     * @param playlistName name of the playlist
     * @param song         song to add to playlist
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
     *
     * @param playlistName name of the playlist
     * @param song         song to remove from playlist
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
     *
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

    public LibraryModel makeCopy() {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(this), getClass());
    }

    public void removeSong(Song s) {
        SongInLibrary songInLibrary = getSongInLibraryFromSong(s);
        if (songInLibrary != null) {
            for (Playlist playlist : playlists) {
                playlist.removeSong(s);
            }
            songs.remove(songInLibrary);
        }
    }

    public void playSong(Song s) {
        SongInLibrary songInLibrary = getSongInLibraryFromSong(s);

        if (songInLibrary != null) {
            songInLibrary.play();
            initRecentPlaylist();
            initFrequentPlaylist();
        }
    }

    void setSongPlayHistory(Song s, int count, Date lastPlayed) {
        SongInLibrary songInLibrary = getSongInLibraryFromSong(s);
        if (songInLibrary != null) {
            songInLibrary.setLastPlayed(lastPlayed);
            songInLibrary.setPlayCounter(count);
        }
    }

    public int getSongPlaysCount(Song s) {
        SongInLibrary songInLibrary = getSongInLibraryFromSong(s);
        if (songInLibrary != null) {
            return songInLibrary.getPlayCounter();
        }
        return -1;
    }

    public Date getSongLastPlayDate(Song s) {
        SongInLibrary songInLibrary = getSongInLibraryFromSong(s);
        if (songInLibrary != null) {
            return songInLibrary.getLastPlayed();
        }
        return null;
    }

    private SongInLibrary getSongInLibraryFromSong(Song s) {
        for (SongInLibrary sil : songs) {
            if (sil.getSong().equals(s)) {
                return sil;
            }
        }
        return null;
    }

    public boolean isPlaylistAutomatic(String playlistName) {
        return playlistName.equals(RECENT_PLAYLIST_NAME) ||
                playlistName.equals(FREQUENT_PLAYLIST_NAME) ||
                playlistName.equals(FAVORITE_PLAYLIST_NAME) ||
                playlistName.equals(TOP_RATED_PLAYLIST_NAME);

    }

    public void initAutomaticPlaylists() {
        initRecentPlaylist();
        initFrequentPlaylist();
        initFavoritePlaylist();
        initTopRatedPlaylist();
    }

    private void initRecentPlaylist() {
        List<SongInLibrary> sorted_songs = songs.stream()
                .filter(item -> item.getLastPlayed() != null)
                .sorted(Comparator.comparing(SongInLibrary::getLastPlayed, Comparator.reverseOrder()))
                .limit(10)
                .toList();

        recentPlaylist = new Playlist(RECENT_PLAYLIST_NAME);

        for (SongInLibrary sortedSong : sorted_songs) {
            recentPlaylist.addSong(sortedSong.getSong());
        }
    }

    private void initFrequentPlaylist() {
        List<SongInLibrary> sorted_songs = songs.stream()
                .filter(item -> item.getPlayCounter() > 0)
                .sorted(Comparator.comparing(SongInLibrary::getPlayCounter, Comparator.reverseOrder()))
                .limit(10)
                .toList();

        frequentPlaylist = new Playlist(FREQUENT_PLAYLIST_NAME);

        for (SongInLibrary sortedSong : sorted_songs) {
            frequentPlaylist.addSong(sortedSong.getSong());
        }
    }

    private void initFavoritePlaylist() {
        List<SongInLibrary> fav_songs = songs.stream()
                .filter(item -> item.isFavorite() || item.getRating() == 5)
                .toList();

        faivoritePlaylist = new Playlist(FAVORITE_PLAYLIST_NAME);

        for (SongInLibrary sortedSong : fav_songs) {
            faivoritePlaylist.addSong(sortedSong.getSong());
        }
    }

    private void initTopRatedPlaylist() {
        List<SongInLibrary> top_songs = songs.stream()
                .filter(item -> item.getRating() == 4 || item.getRating() == 5)
                .toList();

        topRatedPlaylist = new Playlist(TOP_RATED_PLAYLIST_NAME);

        for (SongInLibrary sortedSong : top_songs) {
            topRatedPlaylist.addSong(sortedSong.getSong());
        }
    }

}
