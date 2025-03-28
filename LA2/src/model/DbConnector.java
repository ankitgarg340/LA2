package model;

import db.DbJson;
import db.IDb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DbConnector {
    private static final String DEFAULT_FILE_PATH = "users.json";
    private final IDb db;
    private final MusicStore musicStore;

    public DbConnector(MusicStore store) throws IOException {
        this(store, DEFAULT_FILE_PATH);
    }

    public DbConnector(MusicStore store, String filepath) throws IOException {
        musicStore = store;
        db = new DbJson(filepath);
    }

    private LibraryModel getLibraryModelInfoFromStore(LibraryModel dbLib) {
        // creating a LibraryModel based on the LibraryModel from the db with the object
        // from the music store
        LibraryModel libWithStoreInfo = new LibraryModel();

        // add all the songs from the store
        for (Song s : dbLib.getAllSongs()) {
            Optional<Song> sInStore = musicStore.getSongByTitle(s.getTitle()).stream()
                    .filter(song -> song.getArtist().equals(s.getArtist()))
                    .findFirst();

            if (sInStore.isPresent()) {
                libWithStoreInfo.addSong(sInStore.get());

                // add rating
                int rating = dbLib.getSongRating(s);
                if (rating >= 1 && rating <= 5) {
                    libWithStoreInfo.rateSong(sInStore.get(), rating);
                }

                // edge case where a song is rated 5 but not favorite
                if (rating == 5 && !dbLib.getFavoriteSongs().contains(s)) {
                    libWithStoreInfo.markSongUnFavorite(sInStore.get());
                }

                // set plays tracking
                int playsCount = dbLib.getSongPlaysCount(s);
                if (playsCount > 0) {
                    Date d = dbLib.getSongLastPlayDate(s);
                    libWithStoreInfo.setSongPlayHistory(sInStore.get(), playsCount, d);
                }
            }
        }

        // add all the albums from the store
        for (Album a : dbLib.getAllAlbums()) {
            Optional<Album> aInStore = musicStore.getAlbumByTitle(a.getTitle()).stream()
                    .filter(album -> album.getArtist().equals(a.getArtist()))
                    .findFirst();
            if (aInStore.isPresent()) {
                List<Song> songsInAlbum = aInStore.get().getSongs();

                // Add the songs in the album that are not in the library to a list so when we add the
                // album to the library, we will remove those songs
                List<Song> songsToRemove = new ArrayList<>();
                for (Song s : songsInAlbum) {
                    if (!libWithStoreInfo.containSong(s)) {
                        songsToRemove.add(s);
                    }
                }
                libWithStoreInfo.addAlbum(aInStore.get());
                for (Song s : songsToRemove) {
                    libWithStoreInfo.removeSong(s);
                }
            }
        }

        // add all the playlists
        for (String playlistName : dbLib.getUserPlaylistsNames()) {
            libWithStoreInfo.createPlaylist(playlistName);
            for (Song s : dbLib.getSongsOfPlaylist(playlistName)) {
                Optional<Song> sInStore = musicStore.getSongByTitle(s.getTitle()).stream()
                        .filter(song -> song.getArtist().equals(s.getArtist()))
                        .findFirst();
                sInStore.ifPresent(song -> libWithStoreInfo.addSongToPlayList(playlistName, song));
            }
        }

        // set favorite songs
        for (Song s : dbLib.getFavoriteSongs()) {
            Optional<Song> sInStore = musicStore.getSongByTitle(s.getTitle()).stream()
                    .filter(song -> song.getArtist().equals(s.getArtist()))
                    .findFirst();
            sInStore.ifPresent(libWithStoreInfo::markSongFavorite);
        }
        libWithStoreInfo.initAutomaticPlaylists();
        return libWithStoreInfo;
    }

    public void createUser(String username, String password) throws IllegalArgumentException {
        db.createUser(username, password);
    }

    public void updateUser(String username, String password, LibraryModel lib) throws IllegalArgumentException {
        db.updateUser(username, password, lib.makeCopy());
    }

    public LibraryModel login(String username, String password) throws IllegalArgumentException {
        LibraryModel dbLib = db.getUserLibrary(username, password);

        return getLibraryModelInfoFromStore(dbLib);
    }
}
