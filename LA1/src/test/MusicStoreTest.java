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
		Exception io = new Exception IOException();
		AssertEquals(ms.readFile(""), io);
	}
}
