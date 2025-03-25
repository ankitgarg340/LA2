package model;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.String;

//test change
public class MusicStore {
    private final List<Album> albums;

    public MusicStore() {
        albums = new ArrayList<>();
    }

    public void readFile(String file) throws IOException {
        readFile(new File(file));
    }

    /**
     * Read the albums, create their songs, and add the albums
     *
     * @param file all the albums to read
     * @throws IOException if could not read the albums file or any specific album file
     */
    public void readFile(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        // for every album in the file
        while ((line = br.readLine()) != null) {
            String[] albumLine = line.split(",");
            String albumFile = albumLine[0] + "_" + albumLine[1] + ".txt ";
            if (file.getParent() != null) {
                albumFile = file.getParent() + "\\" + albumFile;
            }
            BufferedReader brAlbum = new BufferedReader(new FileReader(albumFile));

            ArrayList<Song> songs = new ArrayList<>();

            String albumFileLine = brAlbum.readLine();
            String[] albumInfo = albumFileLine.split(",");

            // read all the songs of the album
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

    /**
     * Get the album of a song, null if an album doesn't exist for the song
     *
     * @param song a song we search for an album
     * @return an album or null
     */
    public Album getAlbumBySong(Song song) {
        for (Album a : this.getAlbums()) {
            if (a.getSongs().contains(song)) {
                return a;
            }
        }
        return null;
    }

    private void addAlbum(Album a) {
        albums.add(a);
    }
}
