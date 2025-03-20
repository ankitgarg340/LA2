package test;

import model.Album;
import model.LibraryModel;
import model.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

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
    	assertFalse(libraryModel.getAllPlaylistsNames().contains("new"));
    	
    	libraryModel.createPlaylist("new");
    	assertTrue(libraryModel.getAllPlaylistsNames().contains("new"));
    	
    	libraryModel.createPlaylist("new");
    	
    	int count = 0;
    	for(String pl : libraryModel.getAllPlaylistsNames()) {
    		if(pl.equals("new")) {
    			count++;
    		}
    	}
    	
    	assertEquals(count, 1);
    }
    
    @Test
    public void testGetAllPlaylistsNames() {
    	assertEquals(libraryModel.getAllPlaylistsNames().size(), 2);
    	assertTrue(libraryModel.getAllPlaylistsNames().contains("test"));
    	assertTrue(libraryModel.getAllPlaylistsNames().contains("empty"));
    	assertFalse(libraryModel.getAllPlaylistsNames().contains("new"));
    	
    	libraryModel.createPlaylist("new");
    	assertEquals(libraryModel.getAllPlaylistsNames().size(), 3);
    	assertTrue(libraryModel.getAllPlaylistsNames().contains("new"));

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
    	for(int i = 0; i < 3; i++) {
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
    
}
