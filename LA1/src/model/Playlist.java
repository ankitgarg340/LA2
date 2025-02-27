package model;
import java.util.ArrayList;
import java.util.List;

public class Playlist {
	private final String name;
	private final List<Song> songs;

	public Playlist(String name){
		this.name = name;
		songs = new ArrayList<>();
	}
}
