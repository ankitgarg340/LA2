package model;

public class Song {
	private final String title;
	private final String artist;
	private final Album album;
	
	public Song(String titleIn, String artistIn, Album albumIn) {
		title = titleIn;
		artist = artistIn;
		album = albumIn;
	}
}
