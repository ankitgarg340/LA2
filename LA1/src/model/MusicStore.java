package model;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.String;

public class MusicStore {
    private final List<Album> albums;

    public MusicStore() {
        albums = new ArrayList<>();
    }

    public void readFile(String file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            String[] albumLine = line.split(",");
            String albumFile = albumLine[0] + "_" + albumLine[1] + ".txt";
            BufferedReader brAlbum = new BufferedReader(new FileReader(albumFile));

            ArrayList<Song> songs = new ArrayList<>();

            String albumFileLine = brAlbum.readLine();
            String[] albumInfo = albumFileLine.split(",");


            while ((albumFileLine = brAlbum.readLine()) != null) {
                Song song = new Song(albumFileLine, albumInfo[1], albumInfo[0]);
                songs.add(song);
            }

            Album album = new Album(albumInfo[0], albumInfo[1], albumInfo[2], albumInfo[3], songs);

            this.addAlbum(album);
            brAlbum.close();
        }
        br.close();
    }

    public List<Album> getAlbums() {
        return new ArrayList<>(this.albums);
    }


    public List<Song> getSongByTitle(String title) {
        ArrayList<Song> returnList = new ArrayList<>();

        for (Album a : this.getAlbums()) {
            for (Song s : a.getSongs()) {
                if (s.getTitle().equals(title)) {
                    returnList.add(s);
                }
            }
        }

        return returnList;
    }

    public List<Song> getSongByArtist(String artist) {
        ArrayList<Song> returnList = new ArrayList<>();

        for (Album a : this.getAlbums()) {
            for (Song s : a.getSongs()) {
                if (s.getArtist().equals(artist)) {
                    returnList.add(s);
                }
            }
        }

        return returnList;
    }

    public List<Album> getAlbumByTitle(String album) {
        ArrayList<Album> returnList = new ArrayList<>();

        for (Album a : this.getAlbums()) {
            if (a.getTitle().equals(album)) {
                returnList.add(a);
            }
        }

        return returnList;
    }

    public List<Album> getAlbumByArtist(String artist) {
        ArrayList<Album> returnList = new ArrayList<>();

        for (Album a : this.getAlbums()) {
            if (a.getArtist().equals(artist)) {
                returnList.add(a);
            }
        }

        return returnList;
    }

    private void addAlbum(Album a) {
        albums.add(a);
    }

}
