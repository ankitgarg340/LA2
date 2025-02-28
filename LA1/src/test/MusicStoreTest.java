package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import model.*;

public class MusicStoreTest {
    private static File testFile;
    private static File album1File;
    private static File album2File;
    private static File album3File;
    private static MusicStore ms;

    @BeforeAll
    static void setUp() {
        testFile = new File("testfile.txt");
        album1File = new File("album1_artist1.txt");
        album2File = new File("album2_artist1.txt");
        album3File = new File("album1_artist2.txt");

        try {
            // create the albums text file
            assertTrue(testFile.createNewFile());
            BufferedWriter writer = new BufferedWriter(new FileWriter(testFile, true));
            writer.write("album1,artist1");
            writer.newLine();
            writer.write("album2,artist1");
            writer.newLine();
            writer.write("album1,artist2");
            writer.close();

            // create the album1 text file
            assertTrue(album1File.createNewFile());
            writer = new BufferedWriter(new FileWriter(album1File, true));
            writer.write("album1,artist1,Pop,2008");
            writer.newLine();
            writer.write("song1");
            writer.close();

            // create the album2 text file
            assertTrue(album2File.createNewFile());
            writer = new BufferedWriter(new FileWriter(album2File, true));
            writer.write("album2,artist1,Pop,2009");
            writer.newLine();
            writer.write("song2");
            writer.close();

            // create the album3 text file
            assertTrue(album3File.createNewFile());
            writer = new BufferedWriter(new FileWriter(album3File, true));
            writer.write("album1,artist2,Rock,2013");
            writer.newLine();
            writer.write("song1");
            writer.close();


            ms = new MusicStore();
            try {
                ms.readFile(testFile.getName());
            } catch (IOException e) {
                fail();
            }
        } catch (IOException e) {
            fail();
        }
    }

    @AfterAll
    static void afterTests() {
        assertTrue(testFile.delete());
        assertTrue(album1File.delete());
        assertTrue(album2File.delete());
        assertTrue(album3File.delete());
    }

    @Test
    public void testConstructor() {
        MusicStore m = new MusicStore();
        assertEquals(0, m.getAlbums().size());
    }

    @Test
    public void testReadFileError() {
        MusicStore m = new MusicStore();
        assertThrows(IOException.class, () -> m.readFile(""));
    }

    @Test
    public void testGetAlbums() {
        assertEquals(3, ms.getAlbums().size());
    }

    @Test
    public void testGetAlbumsSendCopy() {
        List<Album> returnAlbums = ms.getAlbums();
        returnAlbums.add(new Album("a","a", "a", "2", new ArrayList<>()));
        assertEquals(3, ms.getAlbums().size());
    }

    @Test
    public void testGetSongByTitleOneSong() {
        assertEquals(1, ms.getSongByTitle("song2").size());
    }

    @Test
    public void testGetSongByTitleTwoSong() {
        assertEquals(2, ms.getSongByTitle("song1").size());
    }

    @Test
    public void testGetSongByTitleNoSong() {
        assertEquals(0, ms.getSongByTitle("ttttttttttttttt").size());
    }

    @Test
    public void testGetSongByArtistOneSong() {
        assertEquals(1, ms.getSongByArtist("artist2").size());
    }

    @Test
    public void testGetSongByArtistTwoSong() {
        assertEquals(2, ms.getSongByArtist("artist1").size());
    }

    @Test
    public void testGetSongByArtistNoSong() {
        assertEquals(0, ms.getSongByArtist("ttttttttttttttt").size());
    }

    @Test
    public void testGetAlbumByTitleOneAlbum() {
        assertEquals(1, ms.getAlbumByTitle("album2").size());
    }

    @Test
    public void testGetAlbumByTitleTwoAlbum() {
        assertEquals(2, ms.getAlbumByTitle("album1").size());
    }

    @Test
    public void testGetAlbumByTitleNoAlbum() {
        assertEquals(0, ms.getAlbumByTitle("ttttttttttttttt").size());
    }

    @Test
    public void testGetAlbumByArtistOneAlbum() {
        assertEquals(1, ms.getAlbumByArtist("artist2").size());
    }

    @Test
    public void testGetAlbumByArtistTwoAlbum() {
        assertEquals(2, ms.getAlbumByArtist("artist1").size());
    }

    @Test
    public void testGetAlbumByArtistNoAlbum() {
        assertEquals(0, ms.getAlbumByArtist("ttttttttttttttt").size());
    }
}