package view;

import model.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class View {
    private final MusicStore musicStore;
    private final LibraryModel libraryModel;
    private DbConnector dbConnector;

    private final String BACK_COMMAND = "b";
    private final String EXIT_COMMAND = "x";
    private final Scanner scanner;

    public View(String dataFile) {
        musicStore = new MusicStore();
        libraryModel = new LibraryModel();

        scanner = new Scanner(System.in);
        try {
            musicStore.readFile(dataFile);
        } catch (IOException e) {
            System.out.println("Could not load data from file with error msg - " + e.getMessage());
            System.exit(1);
        }

        try {
            dbConnector = new DbConnector(musicStore);
        } catch (IOException e) {
            System.out.println("DB start error - " + e.getMessage());
            System.exit(1);
        }

        dbConnector.createUser("test", "1234");
    }

    public void start() {
        while (true) {
            System.out.println("Where would you like to perform actions?");
            System.out.println("[1] - Music store");
            System.out.println("[2] - Library");
            printBackOrExitMessege();
            int command = getUserInput(2);
            if (command == 0) {
                break;
            } else if (command == 1) {
                storeCommands();
            } else if (command == 2) {
                libraryCommands();
            }
        }
    }

    private void storeCommands() {
        while (true) {
            System.out.println("What would you like to do in the music store?");
            System.out.println("[1] - Search for a song");
            System.out.println("[2] - Search for an album");
            printBackOrExitMessege();
            int command = getUserInput(2);
            if (command == 0) {
                break;
            } else if (command == 1) {
                searchSongsInStore();
            } else if (command == 2) {
                searchAlbumInStore();
            }
        }
    }

    private void searchSongsInStore() {
        while (true) {
            System.out.println("How would like to search for a song");
            System.out.println("[1] - By title");
            System.out.println("[2] - By artist");
            printBackOrExitMessege();
            int command = getUserInput(2);
            if (command == 0) {
                break;
            } else if (command == 1) {
                handleSongsSearchInStore(searchSongsInStoreByTitle());
            } else if (command == 2) {
                handleSongsSearchInStore(searchSongsInStoreByArtist());
            }
        }
    }

    private List<Song> searchSongsInStoreByTitle() {
        System.out.println("Enter the title of the song");
        String input = scanner.nextLine();
        return musicStore.getSongByTitle(input);
    }

    private List<Song> searchSongsInStoreByArtist() {
        System.out.println("Enter the artist of the song");
        String input = scanner.nextLine();
        return musicStore.getSongByArtist(input);
    }

    /**
     * Get a list of songs from the store to allow the user to choose which one he would
     * like to add to the library
     *
     * @param searchResult list of songs
     */
    private void handleSongsSearchInStore(List<Song> searchResult) {
        while (true) {
            if (searchResult.isEmpty()) {
                System.out.println("No songs were found");
                break;
            }
            System.out.println("Here are the songs for your search");
            System.out.println("Which song would you like to like to add to your library?");

            for (int i = 0; i < searchResult.size(); i++) {
                System.out.println("[" + (i + 1) + "] - " + searchResult.get(i).toString());
            }
            printBackOrExitMessege();
            int command = getUserInput(searchResult.size());

            if (command == 0) {
                break;
            } else if (command >= 1 && command <= searchResult.size()) {
                Song selectedSong = searchResult.get(command - 1);
                libraryModel.addSong(selectedSong);
                System.out.println("The song " + selectedSong.toString() + " is in your library");
            }
        }
    }

    private void searchAlbumInStore() {
        while (true) {
            System.out.println("How would like to search for an album");
            System.out.println("[1] - By title");
            System.out.println("[2] - By artist");
            printBackOrExitMessege();
            int command = getUserInput(2);
            if (command == 0) {
                break;
            } else if (command == 1) {
                handleAlbumsSearchInStore(searchAlbumsInStoreByTitle());
            } else if (command == 2) {
                handleAlbumsSearchInStore(searchAlbumsInStoreByArtist());
            }
        }
    }

    private List<Album> searchAlbumsInStoreByTitle() {
        System.out.println("Enter the title of the album");
        String input = scanner.nextLine();
        return musicStore.getAlbumByTitle(input);
    }

    private List<Album> searchAlbumsInStoreByArtist() {
        System.out.println("Enter the artist of the album");
        String input = scanner.nextLine();
        return musicStore.getAlbumByArtist(input);
    }

    /**
     * Get a list of albums from the store to allow the user to choose which one he would
     * like to add to the library
     *
     * @param searchResult list of albums
     */
    private void handleAlbumsSearchInStore(List<Album> searchResult) {
        while (true) {
            if (searchResult.isEmpty()) {
                System.out.println("No albums were found");
                break;
            }
            System.out.println("Here are the albums for your search");
            System.out.println("Which album would you like to like to add to your library?");

            for (int i = 0; i < searchResult.size(); i++) {
                System.out.println("[" + (i + 1) + "] - " + searchResult.get(i).toString());
                System.out.println("\tSongs:");
                for (Song s : searchResult.get(i).getSongs()) {
                    System.out.println("\t\t" + s.toString());
                }
            }
            printBackOrExitMessege();
            int command = getUserInput(searchResult.size());
            if (command == 0) {
                break;
            } else if (command >= 1 && command <= searchResult.size()) {
                Album selectedAlbum = searchResult.get(command - 1);
                libraryModel.addAlbum(selectedAlbum);
                System.out.println("The album " + selectedAlbum.toString() + " is in your library");
            }
        }
    }


    private void libraryCommands() {
        while (true) {
            System.out.println("What would you like to do in your library?");
            System.out.println("[1] - Search for songs");
            System.out.println("[2] - Search for albums");
            System.out.println("[3] - Search for playlists");
            System.out.println("[4] - Create a playlist");
            printBackOrExitMessege();
            int command = getUserInput(4);

            if (command == 0) {
                break;
            } else if (command == 1) {
                searchSongsInLibrary();
            } else if (command == 2) {
                searchAlbumsInLibrary();
            } else if (command == 3) {
                searchPlaylists();
            } else if (command == 4) {
                createPlaylist();
            }
        }
    }

    private void searchSongsInLibrary() {
        while (true) {
            System.out.println("How would like to search for a song?");
            System.out.println("[1] - By title");
            System.out.println("[2] - By artist");
            System.out.println("[3] - Get all");
            System.out.println("[4] - Get favorites");
            printBackOrExitMessege();
            int command = getUserInput(4);
            if (command == 0) {
                break;
            } else if (command == 1) {
                handleSongsSearchInLibrary(searchSongsInLibraryByTitle());
            } else if (command == 2) {
                handleSongsSearchInLibrary(searchSongsInLibraryByArtist());
            } else if (command == 3) {
                handleSongsSearchInLibrary(libraryModel.getAllSongs());
            } else if (command == 4) {
                handleSongsSearchInLibrary(libraryModel.getFavoriteSongs());
            }
        }
    }

    private void searchPlaylists() {
        while (true) {
            System.out.println("How would like to search for playlists");
            System.out.println("[1] - By title");
            System.out.println("[2] - Get all");
            printBackOrExitMessege();
            int command = getUserInput(2);
            if (command == 0) {
                break;
            } else if (command == 1) {
                handlePlaylistSearchByNameInLibrary();
            } else if (command == 2) {
                handlePlaylistSearchInLibrary();
            }
        }
    }

    private void handlePlaylistSearchByNameInLibrary() {
        System.out.println("Enter the title of the playlist: ");
        String input = scanner.nextLine();

        // if no playlist exist for that name, don't go to the screen of actions on playlist
        if (!libraryModel.isPlaylistExist(input)) {
            System.out.println("Playlist doesn't exist with that name");
        } else {
            handlePlaylistAction(input);
        }
    }

    private void handlePlaylistSearchInLibrary() {
        while (true) {
            String selectedPlaylist = choosePlaylistFromAll();

            // if selectedPlaylist, meaning no playlist was selected
            if (selectedPlaylist.isEmpty()) {
                break;
            }
            handlePlaylistAction(selectedPlaylist);
        }
    }

    private void handlePlaylistAction(String playlist) {
        while (true) {
            System.out.println("How would like to do on the playlist " + playlist);
            System.out.println("[1] - View all songs");
            System.out.println("[2] - Remove a song");
            printBackOrExitMessege();
            int command = getUserInput(2);
            if (command == 0) {
                break;
            } else if (command == 1) {
                printSongsOfPlaylist(playlist);
            } else if (command == 2) {
                deleteSongFromPlaylist(playlist);
            }
        }
    }

    private void printSongsOfPlaylist(String playlist) {
        List<Song> songs = libraryModel.getSongsOfPlaylist(playlist);
        if (songs.isEmpty()) {
            System.out.println("Playlist is empty");
        }
        for (Song s : songs) {
            System.out.println(s.toString());
        }
    }

    private void deleteSongFromPlaylist(String playlist) {
        while (true) {
            List<Song> songs = libraryModel.getSongsOfPlaylist(playlist);
            if (songs.isEmpty()) {
                System.out.println("Playlist is empty");
                break;
            }
            System.out.println("Choose a song to remove from " + playlist);
            for (int i = 0; i < songs.size(); i++) {
                System.out.println("[" + (i + 1) + "] - " + songs.get(i).toString());
            }
            printBackOrExitMessege();
            int command = getUserInput(songs.size());
            if (command == 0) {
                break;
            } else if (command >= 1 && command <= songs.size()) {
                libraryModel.removeSongToPlayList(playlist, songs.get(command - 1));
                System.out.println("The song was removed from " + playlist);
            }
        }
    }

    private void searchAlbumsInLibrary() {
        while (true) {
            System.out.println("How would like to search for an album");
            System.out.println("[1] - By title");
            System.out.println("[2] - By artist");
            System.out.println("[3] - Get all");
            printBackOrExitMessege();
            int command = getUserInput(3);
            if (command == 0) {
                break;
            } else if (command == 1) {
                handleAlbumsSearchInLibrary(searchAlbumsInLibraryByTitle());
            } else if (command == 2) {
                handleAlbumsSearchInLibrary(searchAlbumsInLibraryByArtist());
            } else if (command == 3) {
                handleAlbumsSearchInLibrary(libraryModel.getAllAlbums());
            }
        }
    }

    private void handleAlbumsSearchInLibrary(List<Album> searchResult) {
        if (searchResult.isEmpty()) {
            System.out.println("No albums were found");
        } else {
            System.out.println("Here are the albums for your search");

            for (Album album : searchResult) {
                System.out.println(album.toString());
                System.out.println("\tSongs:");
                for (Song s : album.getSongs()) {
                    System.out.println("\t\t" + s.toString());
                }
            }
        }
    }

    private void handleSongsSearchInLibrary(List<Song> searchResult) {
        while (true) {
            if (searchResult.isEmpty()) {
                System.out.println("No songs were found");
                break;
            }
            System.out.println("Here are the songs for your search");
            System.out.println("Which song to rate/favorite/unfavorite/add to playlist?");

            for (int i = 0; i < searchResult.size(); i++) {
                System.out.println("[" + (i + 1) + "] - " + searchResult.get(i).toString());
            }

            printBackOrExitMessege();
            int command = getUserInput(searchResult.size());

            if (command == 0) {
                break;
            } else if (command >= 1 && command <= searchResult.size()) {
                Song selectedSong = searchResult.get(command - 1);
                actionOnSongInLibrary(selectedSong);
            }
        }
    }

    private List<Album> searchAlbumsInLibraryByTitle() {
        System.out.println("Enter the title of the album");
        String input = scanner.nextLine();
        return libraryModel.getAlbumsByTitle(input);
    }

    private List<Album> searchAlbumsInLibraryByArtist() {
        System.out.println("Enter the artist of the album");
        String input = scanner.nextLine();
        return libraryModel.getAlbumsByArtist(input);
    }

    private List<Song> searchSongsInLibraryByTitle() {
        System.out.println("Enter the title of the song");
        String input = scanner.nextLine();
        return libraryModel.getSongsByTitle(input);
    }

    private List<Song> searchSongsInLibraryByArtist() {
        System.out.println("Enter the artist of the song");
        String input = scanner.nextLine();
        return libraryModel.getSongsByArtist(input);
    }

    private void actionOnSongInLibrary(Song song) {
        while (true) {
            System.out.println("What would you like to do on song " + song.getTitle() + "?");
            System.out.println("[1] - Rate");
            System.out.println("[2] - View rating");
            System.out.println("[3] - Mark as favorite");
            System.out.println("[4] - Unmark as favorite");
            System.out.println("[5] - Add to a playlist");
            printBackOrExitMessege();
            int command = getUserInput(5);
            if (command == 0) {
                break;
            } else if (command == 1) {
                rateSong(song);
            } else if (command == 2) {
                int rating = libraryModel.getSongRating(song);
                if (rating == 0) {
                    System.out.println(song.getTitle() + " has no rating");
                } else {
                    System.out.println(song.getTitle() + " rating is " + rating);
                }
            } else if (command == 3) {
                libraryModel.markSongFavorite(song);
                System.out.println("Song " + song.getTitle() + " marked as favorite");
            } else if (command == 4) {
                libraryModel.markSongUnFavorite(song);
                System.out.println("Song " + song.getTitle() + " unmarked as favorite");
            } else if (command == 5) {
                handleAddSongToPlaylist(song);
            }
        }
    }

    private void rateSong(Song song) {
        System.out.println("What would you rate the song " + song.getTitle() + " from 1 to 5?");
        String input = scanner.nextLine();
        int rate;
        try {
            rate = Integer.parseInt(input);
            libraryModel.rateSong(song, rate);
        } catch (NumberFormatException e) {
            System.out.println("Rating must be a number");
            return;
        } catch (IllegalArgumentException e) {
            System.out.println("Rating must be a number from 1 to 5");
            return;
        }
        System.out.println("Rating " + input + " saved for " + song.getTitle());
    }

    private void handleAddSongToPlaylist(Song song) {
        while (true) {
            String selectedPlaylist = choosePlaylistFromAll();
            // if no playlist was selected
            if (selectedPlaylist.isEmpty()) {
                break;
            }
            libraryModel.addSongToPlayList(selectedPlaylist, song);
            System.out.println("The song " + song.getTitle() + " was added to the playlist " + selectedPlaylist);
        }
    }

    private void createPlaylist() {
        System.out.println("What is the name of the new playlist?");
        String input = scanner.nextLine();
        if (input.isEmpty()) {
            System.out.println("Playlist name can't be empty");
        } else if (libraryModel.isPlaylistExist(input)) {
            System.out.println("Playlist name " + input + " already exist");
        } else {
            libraryModel.createPlaylist(input);
            System.out.println("Playlist " + input + " was created");
        }
    }

    /**
     * Let the user to choose a playlist.
     * If no playlist was chosen, return empty string
     *
     * @return name of a playlist or empty string
     */
    private String choosePlaylistFromAll() {
        List<String> playlists = libraryModel.getAllPlaylistsNames();
        if (playlists.isEmpty()) {
            System.out.println("You have no playlists");
            return "";
        }
        System.out.println("Choose a playlist");
        for (int i = 0; i < playlists.size(); i++) {
            System.out.println("[" + (i + 1) + "] - " + playlists.get(i));
        }
        printBackOrExitMessege();
        int command = getUserInput(playlists.size());

        if (command == 0) {
            return "";
        } else if (command >= 1 && command <= playlists.size()) {
            return playlists.get(command - 1);
        }
        return "";
    }

    /**
     * Get command from the user and return the number of the command.
     * The input can only be number between 1 to max,
     * the value of EXIT_COMMAND which will exit the program
     * or BACK_COMMAND which will return 0
     * -1 means invalid command
     *
     * @param max the max number of command
     * @return a number representing the command from the user
     */
    private int getUserInput(int max) {
        String input = scanner.nextLine();

        // the user wants to exit the program
        if (input.compareTo(EXIT_COMMAND) == 0) {
            System.exit(0);
        }

        // the user wants to go back
        if (input.compareTo(BACK_COMMAND) == 0) {
            return 0;
        }
        int command;
        try {
            command = Integer.parseInt(input);
            if (command < 1 || command > max) {
                printInputErrorMessage(max);
                return -1;
            }

            return command;
        } catch (NumberFormatException e) {
            printInputErrorMessage(max);
            return -1;
        }
    }

    private void printInputErrorMessage(int max) {
        System.out.println("The input can only be a number between " + 1 + " - " + max + ", '"
                + EXIT_COMMAND + "' to exit, or '" + BACK_COMMAND + "' to go back a menu");
    }

    private void printBackOrExitMessege() {
        System.out.println("[" + BACK_COMMAND + "] - to go back");
        System.out.println("[" + EXIT_COMMAND + "] - to exit the program");
    }
}
