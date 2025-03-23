package model;

public class RecentPlaylist extends Playlist {
    public RecentPlaylist() {
        super("Most Recent Played Songs");
    }

    @Override
    public void addSong(Song s) {
        songs.remove(s);
        songs.add(0, s);
        if (songs.size() > 10) {
            songs.remove(songs.size() - 1);
        }
    }

    @Override
    public void removeSong(Song s) {
        throw new IllegalCallerException("Can't call removeSong for most recent playlist");
    }
}
