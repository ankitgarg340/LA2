package test;

import model.Song;
import model.SongInLibrary;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class SongInLibraryTest {
    Song s1 = new Song("s1", "a", "album");

    @Test
    public void testCtorUnfavorite() {
        SongInLibrary sil = new SongInLibrary(s1);
        assertFalse(sil.isFavorite());
    }

    @Test
    public void testRate5NoFavorite() {
        // The logic of making a song favorite should not be in this class
        SongInLibrary sil = new SongInLibrary(s1);
        sil.rate(5);
        assertFalse(sil.isFavorite());
    }

    @Test
    public void testMarkFavorite() {
        // The logic of making a song favorite should not be in this class
        SongInLibrary sil = new SongInLibrary(s1);
        sil.markFavorite();
        assertTrue(sil.isFavorite());
    }

    @Test
    public void testMarkFavoriteTwise() {
        // The logic of making a song favorite should not be in this class
        SongInLibrary sil = new SongInLibrary(s1);
        sil.markFavorite();
        assertTrue(sil.isFavorite());
        sil.markFavorite();
        assertTrue(sil.isFavorite());
    }

    @Test
    public void testMarkUnFavorite() {
        // The logic of making a song favorite should not be in this class
        SongInLibrary sil = new SongInLibrary(s1);
        sil.markUnFavorite();
        assertFalse(sil.isFavorite());
    }

    @Test
    public void testMarkUnFavoriteTwise() {
        // The logic of making a song favorite should not be in this class
        SongInLibrary sil = new SongInLibrary(s1);
        sil.markUnFavorite();
        assertFalse(sil.isFavorite());
        sil.markUnFavorite();
        assertFalse(sil.isFavorite());
    }

    @Test
    public void testMarkUnFavoriteAfterFavorite() {
        // The logic of making a song favorite should not be in this class
        SongInLibrary sil = new SongInLibrary(s1);
        sil.markFavorite();
        sil.markUnFavorite();
        assertFalse(sil.isFavorite());
    }

    @Test
    public void testGetRatingNoRating() {
        // The logic of making a song favorite should not be in this class
        SongInLibrary sil = new SongInLibrary(s1);
        assertEquals(0, sil.getRating());
    }

    @Test
    public void testGetRatingAfterRating() {
        // The logic of making a song favorite should not be in this class
        SongInLibrary sil = new SongInLibrary(s1);
        int rate = 4;
        sil.rate(rate);
        assertEquals(rate, sil.getRating());
    }

    @Test
    public void testGetSong() {
        SongInLibrary sil = new SongInLibrary(s1);
        assertEquals(s1, sil.getSong());
    }

    @Test
    public void testNeverPlay() {
        SongInLibrary sil = new SongInLibrary(s1);
        assertEquals(0, sil.getPlayCounter());
        assertNull(sil.getLastPlayed());
    }

    @Test
    public void testPlay() {
        SongInLibrary sil = new SongInLibrary(s1);
        assertEquals(0, sil.getPlayCounter());
        Date dBefore = new Date();
        sil.play();
        Date dAfter = new Date();
        assertEquals(1, sil.getPlayCounter());
        assertNotNull(sil.getLastPlayed());
        assertTrue(sil.getLastPlayed().getTime()>=dBefore.getTime());
        assertTrue(sil.getLastPlayed().getTime()<=dAfter.getTime());
    }
}
