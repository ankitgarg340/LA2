package test;

import model.Song;
import model.SongInLibrary;
import org.junit.jupiter.api.Test;

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
}
