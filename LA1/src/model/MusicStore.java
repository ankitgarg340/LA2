package model;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.String;

public class MusicStore {
	private final List<Album> albums;
	
	public MusicStore() {
		albums = new ArrayList<Album>();
	}
	
	public void addAlbum(Album a) {
		albums.add(a);
	}
	
	public void readFile(String file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while((line = br.readLine()) != null) {
				String[] albumLine = line.split(",");
				String albumFile = albumLine[0] + "_" + albumLine[1] + ".txt";
				BufferedReader brAlbum = new BufferedReader(new FileReader(albumFile));
				
				ArrayList<Song> songs = new ArrayList<Song>();
				
				String albumFileLine = brAlbum.readLine();
				String[] albumInfo = albumFileLine.split(",");

				Album album = new Album(albumInfo[0], albumInfo[1], albumInfo[2], albumInfo[3], songs);
				
				while((albumFileLine = brAlbum.readLine()) != null) {
					Song song = new Song(albumFileLine, albumInfo[1], album);
					songs.add(song);
				}
				this.addAlbum(album);
				brAlbum.close();
			}
			br.close();
			
		} catch (Exception IOEXception) {
			System.out.println("Error: File could not be opened "  + file);
			System.exit(1);
		}
		 
	}
	
	public ArrayList<Album> getAlbums() {
		return new ArrayList<Album>(this.albums);
	}
	
	public ArrayList<Song> getSongByTitle(String title) {
		ArrayList<Song> returnList = new ArrayList<Song>();
				
		for(Album a : this.getAlbums()) {
			for(Song s : a.getSongs()) {
				if(s.getTitle().equals(title)) {
					s.printSong();
					returnList.add(s);
				}
			}
		}
		
		return returnList;
	}
	
	public ArrayList<Song> getSongByArtist(String artist) {
		ArrayList<Song> returnList = new ArrayList<Song>();
		
		for(Album a : this.getAlbums()) {
			for(Song s : a.getSongs()) {
				if(s.getArtist().equals(artist)) {
					s.printSong();
					returnList.add(s);
				}
			}
		}
		
		return returnList;
	}
	
	public ArrayList<Album> getAlbumByTitle(String album) {
		ArrayList<Album> returnList = new ArrayList<Album>();
		
		for(Album a : this.getAlbums()) {
			if(a.getTitle().equals(album)) {
				a.printAlbum();
				returnList.add(a);
			}
		}
		
		return returnList;
	}
	
	public ArrayList<Album> getAlbumByArtist(String artist) {
		ArrayList<Album> returnList = new ArrayList<Album>();
		
		for(Album a : this.getAlbums()) {
			if(a.getArtist().equals(artist)) {
				a.printAlbum();
				returnList.add(a);
			}
		}
		
		return returnList;
		
	}
	
	public static void main(String args[]) {
		MusicStore ms = new MusicStore();
		ms.readFile("albums.txt");
		ms.getAlbumByArtist("Adele");
	}
	
}
