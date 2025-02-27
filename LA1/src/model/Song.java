package model;

public class Song {
	private final String title;
	private final String artist;
	private final String albumName;
	
	public Song(String titleIn, String artistIn, String albumName) {
		title = titleIn;
		artist = artistIn;
		this.albumName = albumName;
	}

	public String getTitle(){
		return title;
	}
	
	public String toString() {
		String song = title + " by " + artist + " in album " + album.getTitle();
		return song;
	}
	
	public void printSong() {
		System.out.println("song title: " + title);
		System.out.println("artist: " + artist);
		System.out.println("album: " + album.getTitle());
		System.out.println();
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getArtist() {
		return artist;
	}
}
