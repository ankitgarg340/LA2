package test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.io.IOException;
import model.*;

public class MusicStoreTest {
	@Test
	public void testConstructor() {
		MusicStore ms = new MusicStore();
		ArrayList<Album> newList = new ArrayList<Album>();
		assertEquals(ms.getAlbums().size(), newList.size());
	}
	
	@Test
	public void testAddAlbum() {
		MusicStore ms = new MusicStore();
		assertEquals(ms.getAlbums().size(), 0);
		
		Album a1 = new Album("", "","", "", new ArrayList<Song>());
		ms.addAlbum(a1);
		assertEquals(ms.getAlbums().size(), 1);
		
		Album a2 = new Album("", "", "", "", new ArrayList<Song>());
		ms.addAlbum(a2);
		assertEquals(ms.getAlbums().size(), 2);
	}
	
	@Test
	public void testReadFile() {
		MusicStore ms = new MusicStore();
		assertFalse(ms.readFile(""));
		
		assertTrue(ms.readFile("albums.txt"));
		
		assertEquals(ms.getAlbums().size(), 15);
	}
	
	@Test
	public void testGetAlbums() {
		MusicStore ms = new MusicStore();
		assertEquals(ms.getAlbums().size(), 0);
		
		ms.readFile("albums.txt");
		assertEquals(ms.getAlbums().size(), 15);
	}
	
	@Test
	public void testGetSongByTitle() {
		MusicStore ms = new MusicStore();
		ms.readFile("albums.txt");
		
		ArrayList<Song> test = ms.getSongByTitle("Daylight");
		assertEquals(test.size(), 1);
		assertEquals(test.get(0).getTitle(), "Daylight");
		
		test = ms.getSongByTitle("NONEXISTENT");
		assertEquals(test.size(), 0);
	}
	
	@Test
	public void testGetSongByArtist() {
		MusicStore ms = new MusicStore();
		ms.readFile("albums.txt");
		
		ArrayList<Song> test = ms.getSongByArtist("Adele");
		for(Song s : test) {
			assertEquals(s.getArtist(), "Adele");
		}
		
		test = ms.getSongByArtist("NONEXISTENT");
		assertEquals(test.size(), 0);
	}
	
	@Test
	public void testGetAlbumByTitle() {
		MusicStore ms = new MusicStore();
		ms.readFile("albums.txt");
		
		ArrayList<Album> test = ms.getAlbumByTitle("21");
		for(Album a : test) {
			assertEquals(a.getTitle(), "21");
		}
		
		test = ms.getAlbumByTitle("");
		assertEquals(test.size(), 0);
	}
	
	@Test
	public void testGetAlbumByArtist() {
		MusicStore ms = new MusicStore();
		ms.readFile("albums.txt");
		
		ArrayList<Album> test = ms.getAlbumByArtist("Adele");
		for(Album a : test) {
			assertEquals(a.getArtist(), "Adele");
		}
		
		test = ms.getAlbumByArtist("");
		assertEquals(test.size(), 0);
	}
}
