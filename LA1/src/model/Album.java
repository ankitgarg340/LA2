package model;
import java.util.List;
import java.util.ArrayList;

public class Album {
	private final String title;
	private final String artist;
	private final String genre;
	private final String year;
	private final List<Song> songs;
	
	public Album(String t, String a, String g, String y, ArrayList<Song> s) {
		title = t;
		artist = a;
		genre = g;
		year = y;
		songs = s;
	}

	public String toString() {
		return this.title + "," + this.artist + "," + this.genre + "," + this.year + "," + songs.size();
	}
}
