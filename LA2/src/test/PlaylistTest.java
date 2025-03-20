package test;

import model.Playlist;
import model.Song;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaylistTest {
    @Test
    public void testCtorEmptySongs() {
        Playlist p = new Playlist("pl");
        assertEquals(0, p.getSongs().size());
    }

    @Test
    public void testGetName() {
        String name = "pl";
        Playlist p = new Playlist(name);
        assertEquals(name, p.getName());
    }

    @Test
    public void testAddSong() {
        Playlist p = new Playlist("pl");
        Song s1 = new Song("s1", "a", "album");
        p.addSong(s1);
        assertEquals(1, p.getSongs().size());

        Song s2 = new Song("s2", "a", "album");
        Song s3 = new Song("s3", "a", "album");
        p.addSong(s2);
        p.addSong(s3);
        assertEquals(3, p.getSongs().size());
    }

    @Test
    public void testAddSongSame() {
        Playlist p = new Playlist("pl");
        Song s1 = new Song("s1", "a", "album");
        p.addSong(s1);
        assertEquals(1, p.getSongs().size());
        p.addSong(s1);
        assertEquals(1, p.getSongs().size());
    }

    @Test
    public void testRemoveSong() {
        Playlist p = new Playlist("pl");
        Song s1 = new Song("s1", "a", "album");
        p.addSong(s1);
        assertEquals(1, p.getSongs().size());
        p.removeSong(s1);
        assertEquals(0, p.getSongs().size());
    }

    @Test
    public void testRemoveSongNoInPlaylist() {
        Playlist p = new Playlist("pl");
        Song s1 = new Song("s1", "a", "album");
        Song s2 = new Song("s2", "a", "album");
        p.addSong(s1);
        assertEquals(1, p.getSongs().size());
        p.removeSong(s2);
        assertEquals(1, p.getSongs().size());
    }

    @Test
    public void testGetSongs() {
        Playlist p = new Playlist("pl");
        Song s1 = new Song("s1", "a", "album");
        Song s2 = new Song("s2", "a", "album");
        p.addSong(s1);
        p.addSong(s2);

        List<Song> songs = p.getSongs();
        assertEquals(2, songs.size());
        assertEquals(s1, songs.get(0));
        assertEquals(s2, songs.get(1));
    }

    @Test
    public void testGetSongsCopyList() {
        Playlist p = new Playlist("pl");
        Song s1 = new Song("s1", "a", "album");
        Song s2 = new Song("s2", "a", "album");
        p.addSong(s1);
        List<Song> songs = p.getSongs();
        songs.add(s2);
        assertEquals(1, p.getSongs().size());
        assertEquals(s1, p.getSongs().get(0));
    }
}
