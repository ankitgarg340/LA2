package test;

import model.Album;
import model.LibraryModel;
import model.Song;
import model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {

    @Test
    public void testGetPassword(){
        String pass = "1234";
        String salt = "aaaa";
        String name = "abcd";
        User u = new User(name,pass,salt);

        assertEquals(pass, u.getPassword());
    }

    @Test
    public void testGetSalt(){
        String pass = "1234";
        String salt = "aaaa";
        String name = "abcd";
        User u = new User(name,pass,salt);

        assertEquals(salt, u.getSalt());
    }

    @Test
    public void testCtorEmptyLibrary(){
        String pass = "1234";
        String salt = "aaaa";
        String name = "abcd";
        User u = new User(name,pass,salt);
        LibraryModel lib = u.getUserLibrary();

        assertTrue(lib.getAllSongs().isEmpty());
        assertTrue(lib.getAllAlbums().isEmpty());
        assertTrue(lib.getUserPlaylistsNames().isEmpty());
    }

    @Test
    public void testSetUserLibrary(){
        String pass = "1234";
        String salt = "aaaa";
        String name = "abcd";
        User u = new User(name,pass,salt);
        LibraryModel lib = new LibraryModel();
        Song s1 = new Song("a","a1","aa");
        Song s2 = new Song("b","a1","aa");
        lib.addSong(s1);
        lib.addSong(s2);
        u.setUserLibrary(lib);
        LibraryModel ulib = u.getUserLibrary();

        assertNotEquals(ulib, lib);
        assertEquals(lib.getAllSongs().size(), ulib.getAllSongs().size());
        assertNotEquals(lib.getAllSongs().get(0), ulib.getAllSongs().get(0));
        assertEquals(lib.getAllSongs().get(0).getTitle(), ulib.getAllSongs().get(0).getTitle());
        assertNotEquals(lib.getAllSongs().get(1), ulib.getAllSongs().get(1));
        assertEquals(lib.getAllSongs().get(1).getTitle(), ulib.getAllSongs().get(1).getTitle());
    }
}
