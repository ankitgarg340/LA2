package test;

import model.Album;
import model.LibraryModel;
import model.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;

public class LibraryModelTest {

    LibraryModel libraryModel;
    Song s1 = new Song("s1", "a", "album");
    Song s2 = new Song("s2", "a", "album1");
    Song s3 = new Song("s3", "a", "album1");
    Album a = new Album("album", "a", "g", "2010", List.of(s1));

    private void addGenreToLib(LibraryModel lib) {
        Song song1 = new Song("s1", "bob", "t");
        Song song2 = new Song("s2", "bob", "t");
        Song song3 = new Song("s3", "bob", "t");
        Song song4 = new Song("s4", "bob", "t");
        Song song5 = new Song("s5", "bob", "t");
        Song song6 = new Song("s6", "bob", "t");
        Song song7 = new Song("s7", "bob", "t");
        Song song8 = new Song("s8", "bob", "t");
        Song song9 = new Song("s9", "bob", "t");
        Song song10 = new Song("s10", "bob", "t");
        List<Song> songs1 = new ArrayList<>();
        songs1.add(song1);
        songs1.add(song2);
        songs1.add(song3);
        songs1.add(song4);
        songs1.add(song5);
        songs1.add(song6);
        songs1.add(song7);
        songs1.add(song8);
        songs1.add(song9);
        songs1.add(song10);
        Album aa = new Album("t", "bob", "POP", "2020", songs1);
        lib.addAlbum(aa);
    }

    @BeforeEach
    void setUp() {
        libraryModel = new LibraryModel();
        libraryModel.addAlbum(a);
        libraryModel.addSong(s1);
        libraryModel.addSong(s2);
        libraryModel.addSong(s3);

        libraryModel.createPlaylist("test");
        libraryModel.addSongToPlayList("test", s1);
        libraryModel.addSongToPlayList("test", s2);
        libraryModel.createPlaylist("empty");


        try {
            libraryModel.rateSong(s1, 3);
        } catch (IllegalArgumentException e) {
            fail();
        }
    }

    @Test
    public void testMakeCopy() {
        LibraryModel l2 = libraryModel.makeCopy();

        Song s4 = new Song("s4", "a", "b");

        assertFalse(l2.containSong(s1));
        assertFalse(l2.containSong(s2));
        assertFalse(l2.containSong(s4));

        List<String> test = new ArrayList<>();
        for (Song s : l2.getAllSongs()) {
            test.add(s.getTitle());
        }

        assertTrue(test.contains("s1"));
        assertTrue(test.contains("s2"));
        assertFalse(test.contains("s4"));
    }

    @Test
    public void testRemoveSong() {
        Song s4 = new Song("s4", "a", "b");
        assertFalse(libraryModel.containSong(s4));

        libraryModel.removeSong(s4);
        assertFalse(libraryModel.containSong(s4));

        assertTrue(libraryModel.containSong(s2));
        assertTrue(libraryModel.getSongsOfPlaylist("test").contains(s2));


        libraryModel.removeSong(s2);
        assertFalse(libraryModel.containSong(s2));


        assertFalse(libraryModel.getSongsOfPlaylist("test").contains(s2));
    }

    @Test
    public void testShuffleSongs() {
        Song s4 = new Song("s4", "a", "b");
        libraryModel.addSong(s4);

        Song s5 = new Song("s5", "a", "b");
        libraryModel.addSong(s5);

        Song s6 = new Song("s6", "a", "b");
        libraryModel.addSong(s6);

        Song s7 = new Song("s7", "a", "b");
        libraryModel.addSong(s4);

        List<Song> l1 = libraryModel.getAllSongs();

        libraryModel.shuffleSongs();

        List<Song> l2 = libraryModel.getAllSongs();

        assertFalse(l1.equals(l2));
    }

    @Test
    public void testPlaySong() {
        assertEquals(libraryModel.getSongPlaysCount(s1), 0);
        libraryModel.playSong(s1);

        assertEquals(libraryModel.getSongPlaysCount(s1), 1);
        libraryModel.playSong(s1);

        assertEquals(libraryModel.getSongPlaysCount(s1), 2);
        libraryModel.playSong(s1);

        assertEquals(libraryModel.getSongPlaysCount(s1), 3);

        assertEquals(libraryModel.getSongPlaysCount(s2), 0);
        assertEquals(libraryModel.getSongPlaysCount(s3), 0);

        Song s4 = new Song("s4", "a", "b");
        assertEquals(libraryModel.getSongPlaysCount(s4), -1);
    }

    @Test
    public void testGetSongLastPlayDate() {

    }

    @Test
    public void testRemoveAlbum() {
        Song s4 = new Song("s4", "a", "b");

        assertTrue(libraryModel.containSong(s1));

        Album b = new Album("A", "A", "A", "A", List.of(s4));

        libraryModel.removeAlbum(a);
        libraryModel.removeAlbum(b);

        assertFalse(libraryModel.containSong(s1));
        assertTrue(libraryModel.containSong(s2));
        assertFalse(libraryModel.containSong(s4));

    }

    @Test
    public void testGetAllPlaylistNames() {
        assertTrue(libraryModel.getAllPlaylistsNames().contains("Most Recent Played Songs"));
        assertTrue(libraryModel.getAllPlaylistsNames().contains("Top Rated Songs"));
        assertFalse(libraryModel.getAllPlaylistsNames().contains("not in playlists"));
        assertFalse(libraryModel.getAllPlaylistsNames().contains("Genre: g"));
        assertFalse(libraryModel.getAllPlaylistsNames().contains("Genre: fail"));
    }

    @Test
    public void testGetSongSortedByTitle() {
        List<Song> testList = libraryModel.getSongsSortedByTitle();
        assertEquals(testList.get(0), s1);
        assertEquals(testList.get(1), s2);
        assertEquals(testList.get(2), s3);
    }

    @Test
    public void testGetSongSortedByArtist() {
        Song s4 = new Song("s4", "z", "album4");

        libraryModel.addSong(s4);

        List<Song> testList = libraryModel.getSongsSortedByArtist();
        assertEquals(testList.get(0), s1);
        assertEquals(testList.get(1), s2);
        assertEquals(testList.get(2), s3);
        assertEquals(testList.get(3), s4);

    }

    @Test
    public void testGetSongsSortedByRating() {
        libraryModel.rateSong(s3, 1);
        libraryModel.rateSong(s2, 2);
        libraryModel.rateSong(s1, 3);

        List<Song> testList = libraryModel.getSongsSortedByRating();

        assertEquals(testList.get(0), s3);
        assertEquals(testList.get(1), s2);
        assertEquals(testList.get(2), s1);

    }


    @Test
    public void testShufflePlaylists() {
        Song s4 = new Song("s4", "a", "album4");
        Song s5 = new Song("s5", "a", "album4");
        Song s6 = new Song("s6", "a", "album4");
        libraryModel.addSong(s4);
        libraryModel.addSong(s5);
        libraryModel.addSong(s6);
        libraryModel.addSongToPlayList("test", s4);
        libraryModel.addSongToPlayList("test", s5);
        libraryModel.addSongToPlayList("test", s6);


        List<Song> pre = libraryModel.getSongsOfPlaylist("test");
        assertEquals(pre.get(0), s1);
        assertEquals(pre.get(1), s2);

        libraryModel.shufflePlaylist("test");


        List<Song> post = libraryModel.getSongsOfPlaylist("test");
        assertFalse(pre.equals(post));
    }

    @Test
    public void testAddSong() {
        Song test = new Song("test", "a", "b");
        assertEquals(libraryModel.getAllSongs().size(), 3);
        libraryModel.addSong(s1);
        assertEquals(libraryModel.getAllSongs().size(), 3);
        libraryModel.addSong(test);
        assertEquals(libraryModel.getAllSongs().size(), 4);
        libraryModel.addSong(test);
        assertEquals(libraryModel.getAllSongs().size(), 4);

    }

    @Test
    public void testContainSong() {
        Song test = new Song("test", "a", "b");
        assertFalse(libraryModel.containSong(test));
        assertTrue(libraryModel.containSong(s1));
    }

    @Test
    public void testAddAlbum() {
        List<Song> aSongs = new ArrayList<Song>();

        Song s1Test = new Song("s1t", "a", "test");
        Song s2Test = new Song("s2t", "a", "test");
        Song s3Test = new Song("s3t", "a", "test");

        aSongs.add(s1Test);
        aSongs.add(s2Test);
        aSongs.add(s3Test);

        Album test = new Album("a", "a", "a", "a", aSongs);
        assertFalse(libraryModel.containSong(s1Test));

        libraryModel.addSong(s1Test);
        assertTrue(libraryModel.containSong(s1Test));

        assertFalse(libraryModel.getAllAlbums().contains(test));

        libraryModel.addAlbum(test);
        assertTrue(libraryModel.getAllAlbums().contains(test));

        libraryModel.addAlbum(test);
        assertTrue(libraryModel.getAllAlbums().contains(test));

        assertTrue(libraryModel.containSong(s1Test));
        assertTrue(libraryModel.containSong(s2Test));

        assertEquals(libraryModel.getSongsByTitle("s1t").size(), 1);

        ArrayList<Song> aSongs2 = new ArrayList<Song>();
        aSongs2.add(s2);
        aSongs2.add(s3);

        Album test2 = new Album("a", "a", "a", "a", aSongs2);
        libraryModel.addAlbum(test2);

        assertEquals(libraryModel.getSongsByTitle("s2").size(), 1);
    }

    @Test
    public void testCreatePlaylist() {
        assertFalse(libraryModel.getUserPlaylistsNames().contains("new"));

        libraryModel.createPlaylist("new");
        assertTrue(libraryModel.getUserPlaylistsNames().contains("new"));

        libraryModel.createPlaylist("new");

        int count = 0;
        for (String pl : libraryModel.getUserPlaylistsNames()) {
            if (pl.equals("new")) {
                count++;
            }
        }

        assertEquals(count, 1);
    }

    @Test
    public void testGetAllPlaylistsNames() {
        assertEquals(libraryModel.getUserPlaylistsNames().size(), 2);
        assertTrue(libraryModel.getUserPlaylistsNames().contains("test"));
        assertTrue(libraryModel.getUserPlaylistsNames().contains("empty"));
        assertFalse(libraryModel.getUserPlaylistsNames().contains("new"));

        libraryModel.createPlaylist("new");
        assertEquals(libraryModel.getUserPlaylistsNames().size(), 3);
        assertTrue(libraryModel.getUserPlaylistsNames().contains("new"));

    }

    @Test
    public void testGetSongsOfPlaylist() {
        List<Song> songs = libraryModel.getSongsOfPlaylist("test");
        assertEquals(2, songs.size());
        assertEquals(s1, songs.get(0));
        assertEquals(s2, songs.get(1));
    }

    @Test
    public void testGetSongsOfPlaylistNoSongs() {
        List<Song> songs = libraryModel.getSongsOfPlaylist("empty");
        assertEquals(0, songs.size());
    }

    @Test
    public void testGetSongsOfPlaylistNoPlaylist() {
        List<Song> songs = libraryModel.getSongsOfPlaylist("aaaaaaaa");
        assertNull(songs);
    }


    @Test
    public void testAddSongToPlayList() {
        libraryModel.addSongToPlayList("test", s3);
        List<Song> songs = libraryModel.getSongsOfPlaylist("test");
        assertEquals(3, songs.size());
        assertEquals(s1, songs.get(0));
        assertEquals(s2, songs.get(1));
        assertEquals(s3, songs.get(2));
    }

    @Test
    public void testAddSongToPlayListAlreadyInPlaylist() {
        libraryModel.addSongToPlayList("test", s1);
        List<Song> songs = libraryModel.getSongsOfPlaylist("test");
        assertEquals(2, songs.size());
        assertEquals(s1, songs.get(0));
        assertEquals(s2, songs.get(1));
    }

    @Test
    public void testAddSongToPlayListSongNotInLibraryNoError() {
        try {
            libraryModel.addSongToPlayList("test", new Song("s", "a", "album"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testAddSongToPlayListPlaylistNotExistNoError() {
        try {
            libraryModel.addSongToPlayList("aaaaaaa", s1);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testRemoveSongToPlayList() {
        libraryModel.removeSongToPlayList("test", s1);
        List<Song> songs = libraryModel.getSongsOfPlaylist("test");
        assertEquals(1, songs.size());
        assertEquals(s2, songs.get(0));
    }

    @Test
    public void testRemoveSongToPlayListAlreadyNotInPlaylist() {
        libraryModel.removeSongToPlayList("test", s3);
        List<Song> songs = libraryModel.getSongsOfPlaylist("test");
        assertEquals(2, songs.size());
        assertEquals(s1, songs.get(0));
        assertEquals(s2, songs.get(1));
    }

    @Test
    public void testRemoveSongToPlayListSongNotInLibraryNoError() {
        try {
            libraryModel.removeSongToPlayList("test", new Song("s", "a", "album"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testRemoveSongToPlayListPlaylistNotExistNoError() {
        try {
            libraryModel.removeSongToPlayList("aaaaaaa", s1);
        } catch (Exception e) {
            fail();
        }
    }


    @Test
    public void testGetSongRatingSongNotExist() {
        assertEquals(-1, libraryModel.getSongRating(new Song("s", "a", "album")));
    }

    @Test
    public void testGetSongRatingSongNoRating() {
        assertEquals(0, libraryModel.getSongRating(s2));
    }

    @Test
    public void testGetSongRatingSong() {
        assertEquals(3, libraryModel.getSongRating(s1));
    }

    @Test
    public void testRateSongNotExist() {
        try {
            libraryModel.rateSong(new Song("s", "a", "album"), 3);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testRateSong() {
        try {
            libraryModel.rateSong(s2, 3);
            assertEquals(3, libraryModel.getSongRating(s2));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testRateSongRate5() {
        try {
            assertFalse(libraryModel.getFavoriteSongs().contains(s2));
            libraryModel.rateSong(s2, 5);
            assertEquals(5, libraryModel.getSongRating(s2));
            assertTrue(libraryModel.getFavoriteSongs().contains(s2));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testRateSongRateIllegal() {
        assertThrows(IllegalArgumentException.class, () -> libraryModel.rateSong(s2, 0));
        assertThrows(IllegalArgumentException.class, () -> libraryModel.rateSong(s2, 9));
        assertThrows(IllegalArgumentException.class, () -> libraryModel.rateSong(s2, -9));
    }

    @Test
    public void testGetAllSongs() {
        List<Song> expected = new ArrayList<Song>();
        expected.add(s1);
        expected.add(s2);
        expected.add(s3);

        List<Song> test = libraryModel.getAllSongs();
        for (int i = 0; i < 3; i++) {
            assertEquals(test.get(i), expected.get(i));
        }
    }

    @Test
    public void testGetAllAlbums() {
        List<Album> expected = new ArrayList<Album>();
        expected.add(a);

        assertEquals(libraryModel.getAllAlbums().get(0), expected.get(0));
    }

    @Test
    public void testMarkSongFavorite() {
        libraryModel.markSongFavorite(s1);
        libraryModel.markSongFavorite(s2);

        Song errSong = new Song("a", "a", "a");
        libraryModel.markSongFavorite(errSong);

        assertTrue(libraryModel.getFavoriteSongs().contains(s2));
        assertTrue(libraryModel.getFavoriteSongs().contains(s1));
        assertFalse(libraryModel.getFavoriteSongs().contains(errSong));
        assertFalse(libraryModel.getFavoriteSongs().contains(s3));
    }

    @Test
    public void testMarkSongUnFavorite() {
        libraryModel.markSongFavorite(s1);
        libraryModel.markSongFavorite(s2);

        Song errSong = new Song("a", "a", "a");

        assertTrue(libraryModel.getFavoriteSongs().contains(s2));
        assertTrue(libraryModel.getFavoriteSongs().contains(s1));
        assertFalse(libraryModel.getFavoriteSongs().contains(errSong));
        assertFalse(libraryModel.getFavoriteSongs().contains(s3));

        libraryModel.markSongUnFavorite(errSong);
        libraryModel.markSongUnFavorite(s3);
        libraryModel.markSongUnFavorite(s2);

        assertFalse(libraryModel.getFavoriteSongs().contains(s2));
        assertTrue(libraryModel.getFavoriteSongs().contains(s1));
        assertFalse(libraryModel.getFavoriteSongs().contains(errSong));
        assertFalse(libraryModel.getFavoriteSongs().contains(s3));
    }

    @Test
    public void testGetSongByTitle() {
        Song s4 = new Song("s1", "b", "b");
        libraryModel.addSong(s4);

        assertEquals(libraryModel.getSongsByTitle("s1").get(0), s1);
        assertEquals(libraryModel.getSongsByTitle("s1").size(), 2);
        assertEquals(libraryModel.getSongsByTitle("s1").get(1), s4);
        assertEquals(libraryModel.getSongsByTitle("s2").get(0), s2);

        assertTrue(libraryModel.getSongsByTitle("errSong").isEmpty());
    }

    @Test
    public void testGetSongsByArtist() {
        Song s4 = new Song("s4", "b", "b");
        Song s5 = new Song("s5", "b", "b");

        libraryModel.addSong(s4);
        libraryModel.addSong(s5);

        assertEquals(libraryModel.getSongsByArtist("b").size(), 2);
        assertEquals(libraryModel.getSongsByArtist("a").size(), 3);
        assertTrue(libraryModel.getSongsByArtist("c").isEmpty());

        assertTrue(libraryModel.getSongsByArtist("b").contains(s4));
        assertTrue(libraryModel.getSongsByArtist("b").contains(s5));
        assertFalse(libraryModel.getSongsByArtist("b").contains(s3));

        assertFalse(libraryModel.getSongsByArtist("a").contains(s4));
        assertTrue(libraryModel.getSongsByArtist("a").contains(s1));
    }

    @Test
    public void testGetAlbumsByTitle() {
        Album a1 = new Album("album", "a", "g", "2010", List.of(s1));
        Album b = new Album("albumB", "b", "g", "2010", List.of(s2));
        libraryModel.addAlbum(a1);
        libraryModel.addAlbum(b);

        assertTrue(libraryModel.getAlbumsByTitle("album").contains(a));
        assertTrue(libraryModel.getAlbumsByTitle("album").contains(a1));
        assertFalse(libraryModel.getAlbumsByTitle("album").contains(b));

        assertTrue(libraryModel.getAlbumsByTitle("albumB").contains(b));
        assertFalse(libraryModel.getAlbumsByTitle("albumB").contains(a));

        assertTrue(libraryModel.getAlbumsByTitle("Error").isEmpty());
    }

    @Test
    public void testGetAlbumsByArtist() {
        Album a1 = new Album("album", "a", "g", "2010", List.of(s1));
        Album b = new Album("albumB", "b", "g", "2010", List.of(s2));
        libraryModel.addAlbum(a1);
        libraryModel.addAlbum(b);

        assertTrue(libraryModel.getAlbumsByArtist("a").contains(a));
        assertTrue(libraryModel.getAlbumsByArtist("a").contains(a1));
        assertFalse(libraryModel.getAlbumsByArtist("a").contains(b));

        assertTrue(libraryModel.getAlbumsByArtist("b").contains(b));
        assertFalse(libraryModel.getAlbumsByArtist("b").contains(a));

        assertTrue(libraryModel.getAlbumsByArtist("Error").isEmpty());
    }

    @Test
    public void testIsPlaylistExist() {
        assertTrue(libraryModel.isPlaylistExist("empty"));
        assertTrue(libraryModel.isPlaylistExist("test"));
        assertFalse(libraryModel.isPlaylistExist("error"));
    }

    @Test
    public void testGetAllPlaylistsNamesGenre() {
        addGenreToLib(libraryModel);

        assertTrue(libraryModel.getAllPlaylistsNames().contains("Genre: POP"));
    }

    @Test
    public void testGetSongsSortedByTitleNoSongs() {
        LibraryModel l = new LibraryModel();
        assertTrue(l.getSongsSortedByTitle().isEmpty());
    }

    @Test
    public void testGetSongsSortedByArtistNoSongs() {
        LibraryModel l = new LibraryModel();
        assertTrue(l.getSongsSortedByArtist().isEmpty());
    }

    @Test
    public void testGetSongsSortedByRatingNoSongs() {
        LibraryModel l = new LibraryModel();
        assertTrue(l.getSongsSortedByRating().isEmpty());
    }

    @Test
    public void removeSongUpdateAutomaticPlaylist() {
        libraryModel.rateSong(s3, 5);
        assertTrue(libraryModel.getSongsOfPlaylist("Top Rated Songs").contains(s3));
        libraryModel.removeSong(s3);
        assertFalse(libraryModel.getSongsOfPlaylist("Top Rated Songs").contains(s3));
    }

    @Test
    public void removeSongRemoveGenreAutomaticPlaylist() {
        addGenreToLib(libraryModel);

        assertTrue(libraryModel.getAllPlaylistsNames().contains("Genre: POP"));
        libraryModel.removeSong(libraryModel.getSongsByTitle("s8").get(0));
        assertFalse(libraryModel.getAllPlaylistsNames().contains("Genre: POP"));
    }

    @Test
    public void removeAlbumSameName() {
        Song song1 = new Song("s1", "bob", "t");
        Song song2 = new Song("s2", "bob2", "t");
        List<Song> songs1 = new ArrayList<>();
        songs1.add(song1);
        List<Song> songs2 = new ArrayList<>();
        songs2.add(song2);
        Album aa1 = new Album("t", "bob", "POP", "2020", songs1);
        Album aa2 = new Album("t", "bob", "POP", "2020", songs2);
        libraryModel.addAlbum(aa1);
        libraryModel.addAlbum(aa2);
        libraryModel.removeAlbum(aa1);

        assertTrue(libraryModel.containSong(song2));
        assertFalse(libraryModel.containSong(song1));
    }

    @Test
    public void testIsPlaylistAutomatic() {
        assertTrue(libraryModel.isPlaylistAutomatic("Most Recent Played Songs"));
        assertTrue(libraryModel.isPlaylistAutomatic("Most Frequently Played Songs"));
        assertTrue(libraryModel.isPlaylistAutomatic("Favorite Songs"));
        assertTrue(libraryModel.isPlaylistAutomatic("Top Rated Songs"));
        assertFalse(libraryModel.isPlaylistAutomatic("test"));

        addGenreToLib(libraryModel);
        assertTrue(libraryModel.isPlaylistAutomatic("Genre: POP"));
    }

    @Test
    public void testPlaySongAddMostRecent() {
        libraryModel.playSong(s1);
        assertEquals(s1, libraryModel.getSongsOfPlaylist("Most Recent Played Songs").get(0));
    }

    @Test
    public void testPlaySongAddFrequent() {
        libraryModel.playSong(s1);
        assertTrue(libraryModel.getSongsOfPlaylist("Most Frequently Played Songs").contains(s1));
    }

    @Test
    public void testPlaySongNotAddFrequent() {
        Song song1 = new Song("s1", "bob", "t");
        Song song2 = new Song("s2", "bob", "t");
        Song song3 = new Song("s3", "bob", "t");
        Song song4 = new Song("s4", "bob", "t");
        Song song5 = new Song("s5", "bob", "t");
        Song song6 = new Song("s6", "bob", "t");
        Song song7 = new Song("s7", "bob", "t");
        Song song8 = new Song("s8", "bob", "t");
        Song song9 = new Song("s9", "bob", "t");
        Song song10 = new Song("s10", "bob", "t");
        List<Song> songs1 = new ArrayList<>();
        songs1.add(song1);
        songs1.add(song2);
        songs1.add(song3);
        songs1.add(song4);
        songs1.add(song5);
        songs1.add(song6);
        songs1.add(song7);
        songs1.add(song8);
        songs1.add(song9);
        songs1.add(song10);
        Album aa = new Album("t", "bob", "POP", "2020", songs1);
        libraryModel.addAlbum(aa);

        libraryModel.playSong(song1);
        libraryModel.playSong(song1);

        libraryModel.playSong(song2);
        libraryModel.playSong(song2);

        libraryModel.playSong(song3);
        libraryModel.playSong(song3);

        libraryModel.playSong(song4);
        libraryModel.playSong(song4);

        libraryModel.playSong(song5);
        libraryModel.playSong(song5);

        libraryModel.playSong(song6);
        libraryModel.playSong(song6);

        libraryModel.playSong(song7);
        libraryModel.playSong(song7);

        libraryModel.playSong(song8);
        libraryModel.playSong(song8);

        libraryModel.playSong(song9);
        libraryModel.playSong(song9);

        libraryModel.playSong(song10);
        libraryModel.playSong(song10);

        libraryModel.playSong(s1);

        assertFalse(libraryModel.getSongsOfPlaylist("Most Frequently Played Songs").contains(s1));
    }

    @Test
    public void testGetSongsOfPlaylistAutoNotNull(){
        assertNotNull(libraryModel.getSongsOfPlaylist("Most Recent Played Songs"));
        assertNotNull(libraryModel.getSongsOfPlaylist("Most Frequently Played Songs"));
        assertNotNull(libraryModel.getSongsOfPlaylist("Favorite Songs"));
        assertNotNull(libraryModel.getSongsOfPlaylist("Top Rated Songs"));

        assertNull(libraryModel.getSongsOfPlaylist("Genre: POP"));
        addGenreToLib(libraryModel);
        assertNotNull(libraryModel.getSongsOfPlaylist("Genre: POP"));
    }

}
