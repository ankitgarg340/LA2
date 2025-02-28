package test;

import model.Album;
import model.MusicStore;
import model.Song;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlbumTest {

    Song s1 = new Song("s1", "a", "album");
    Song s2 = new Song("s2", "a", "album");
    ;
    Song s3 = new Song("s3", "a", "album");
    ;

    @Test
    public void testGetTitle() {
        String title = "album";
        Album a = new Album(title, "a", "g", "2010", new ArrayList<Song>());
        assertEquals(title, a.getTitle());
    }

    @Test
    public void testGetArtist() {
        String artist = "a";
        Album a = new Album("t", artist, "g", "2010", new ArrayList<Song>());
        assertEquals(artist, a.getArtist());
    }

    @Test
    public void testGetSongsSameSongs() {
        List<Song> songs = new ArrayList<>();
        songs.add(s1);
        songs.add(s2);
        songs.add(s3);
        Album a = new Album("t", "a", "g", "2010", songs);
        assertEquals(songs.size(), a.getSongs().size());

        for (int i = 0; i < songs.size(); i++) {
            assertEquals(songs.get(i), a.getSongs().get(i));
        }
    }

    @Test
    public void testCopySongListInCtor() {
        List<Song> songs = new ArrayList<>();
        songs.add(s1);
        songs.add(s2);
        Album a = new Album("t", "a", "g", "2010", songs);
        songs.add(s3);
        assertEquals(songs.size(), a.getSongs().size() + 1);
        assertFalse(a.getSongs().contains(s3));
    }

    @Test
    public void testGetSongsReturnCopy() {
        List<Song> songs = new ArrayList<>();
        songs.add(s1);
        songs.add(s2);
        Album a = new Album("t", "a", "g", "2010", songs);
        a.getSongs().add(s3);

        assertEquals(2, a.getSongs().size());
        assertFalse(a.getSongs().contains(s3));
    }

    @Test
    public void testToString() {
        List<Song> songs = new ArrayList<>();
        songs.add(s1);
        songs.add(s2);
        String title = "abababababababa";
        String artist = "aaaaaaaaa";
        String genre = "ggggggggggggggg";
        String year = "20986334";
        Album a = new Album(title,artist,genre,year,songs);

        assertTrue(a.toString().contains(title));
        assertTrue(a.toString().contains(artist));
        assertTrue(a.toString().contains(genre));
        assertTrue(a.toString().contains(year));

        assertFalse(a.toString().contains(s1.getTitle()));
        assertFalse(a.toString().contains(s2.getTitle()));
    }
}
