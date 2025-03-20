package test;

import model.Song;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SongTest {

    @Test
    public void testGetTitle() {
        String title = "s1";
        Song s1 = new Song(title, "a", "album");
        assertEquals(title, s1.getTitle());
    }

    @Test
    public void testGetArtist() {
        String artist = "a";
        Song s1 = new Song("s1", artist, "album");
        assertEquals(artist, s1.getArtist());
    }

    @Test
    public void testToString() {
        String title = "s1lalalalala";
        String artist = "aaaaaaaaa";
        String album = "aboboerjsdcidkljw";

        Song s1 = new Song(title, artist, album);
        assertTrue(s1.toString().contains(title));
        assertTrue(s1.toString().contains(artist));
        assertTrue(s1.toString().contains(album));
    }


}
