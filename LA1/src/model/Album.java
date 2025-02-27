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

	
	public void printAlbum() {
		System.out.println("album title: " + title);
		System.out.println("artist: " + artist);
		System.out.println("genre: " + genre);
		System.out.println("year released: " + year);
		System.out.println("songs: ");
		for(Song s : songs) {
			System.out.println(s.getTitle());
		}
		System.out.println();
	}
	
	public ArrayList<Song> getSongs() {
		return new ArrayList<Song>(this.songs);
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getArtist() {
		return artist;
	}
}
