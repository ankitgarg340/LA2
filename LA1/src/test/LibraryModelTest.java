package test;

import model.Album;
import model.LibraryModel;
import model.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryModelTest {

    LibraryModel libraryModel;
    Song s1 = new Song("s1", "a", "album");
    Song s2 = new Song("s2", "a", "album1");
    Song s3 = new Song("s3", "a", "album1");
    Album a = new Album("album", "a", "g", "2010", List.of(s1));

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
        assertEquals(s1, songs.get(0));
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
}
