package model;

public class SongInLibrary {
	private final Song song;
	private boolean isFavorite;
	private int rating;

	public SongInLibrary(Song s) {
		song = s;
		isFavorite = false;
	}
}
