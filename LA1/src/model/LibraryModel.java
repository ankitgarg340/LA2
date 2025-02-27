package model;
import java.util.List;
import java.util.ArrayList;

import java.util.HashMap;

public class LibraryModel {
	private final List<SongInLibrary> songs;
	private final List<Album> albums;
	private final List<Playlist> playlists;
	
	public LibraryModel() {
		songs = new ArrayList<SongInLibrary>();
		albums = new ArrayList<Album>();
		playlists = new ArrayList<Playlist>();
	}
	
	public void addSong(SongInLibrary s) {
		songs.add(s);
	}
	
	public void addAlbum(Album a) {
		albums.add(a);
	}
	 
	public void addPlaylist(Playlist p) {
		playlists.add(p);
	}
	
	

}
