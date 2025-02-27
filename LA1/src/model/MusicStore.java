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
					album.addSong(song);
				}
				this.addAlbum(album);
			}
			
		} catch (Exception IOEXception) {
			System.out.println("Error: File could not be opened "  + file);
			System.exit(1);
		
		}
		 
	}
	
	public ArrayList<Album> getAlbums() {
		return new ArrayList<Album>(this.albums);
	}
	
	public static void main(String[] args) {
		MusicStore ms = new MusicStore();
		ms.readFile("albums.txt");
		
		for(Album a : ms.getAlbums()) {
			System.out.println(a.toString());
		}
	}

}
