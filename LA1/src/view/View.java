package view;

import model.Album;
import model.LibraryModel;
import model.MusicStore;
import model.Playlist;
import model.Song;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class View {
    private final MusicStore musicStore;
    private final LibraryModel libraryModel;

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
            System.out.println("Could not load data from file with error msg - "+e.getMessage());
            System.exit(1);
        }
    }

    public void start() {
        while (true) {
            System.out.println("Where would you like to perform actions?");
            System.out.println("[1] - Music store");
            System.out.println("[2] - Library");
            printBackOrExitMessege();
            int command = getUserInput(1, 2);
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
            int command = getUserInput(1, 2);
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
            int command = getUserInput(1, 2);
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
            int command = getUserInput(1, searchResult.size());

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
            int command = getUserInput(1, 2);
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
                for(Song s : searchResult.get(i).getSongs()){
                    System.out.println("\t\t"+s.toString());
                }
            }
            printBackOrExitMessege();
            int command = getUserInput(1, searchResult.size());
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
            System.out.println("[5] - Modify/Delete an existing playlist");
            printBackOrExitMessege();
            int command = getUserInput(1, 5);

            if (command == 0) {
                break;
            } else if (command == 1) {
                searchSongsInLibrary();
            } else if (command == 2) {
                searchAlbumsInLibrary();
            } else if (command == 3) {
                searchPlaylists();
            } else if (command == 4) {
                int a = 0;
            } else if (command == 5) {
            	int a = 0;
            }
        }
    }
    
    private void searchSongsInLibrary() {
        while (true) {
            System.out.println("How would like to search for a song?");
            System.out.println("[1] - By title");
            System.out.println("[2] - By artist");
            printBackOrExitMessege();
            int command = getUserInput(1, 2);
            if (command == 0) {
                break;
            } else if (command == 1) {
                handleSongsSearchInLibrary(searchSongsInLibraryByTitle());
            } else if (command == 2) {
                handleSongsSearchInLibrary(searchSongsInLibrayByArtist());
            }
        }
    }
    
    private void searchPlaylists() {
        while (true) {
            System.out.println("Enter the title of the playlist: ");
            String input = scanner.nextLine();
            
            Playlist pl = libraryModel.getPlaylistFromName(input);
            if(pl != null) {
            	System.out.println(pl.getName());
            	System.out.println("\tSongs:");
            	for(Song s : pl.getSongs()) {
            		System.out.println("\t\t" + s.toString());
            	}
            } else {
            	System.out.println("No Playlist Found");
            	break;
            }
            break;
        }
        printBackOrExitMessege();
        getUserInput(0,0);
    }
    
    private void searchAlbumsInLibrary() {
        while (true) {
            System.out.println("How would like to search for an album");
            System.out.println("[1] - By title");
            System.out.println("[2] - By artist");
            printBackOrExitMessege();
            int command = getUserInput(1, 2);
            if (command == 0) {
                break;
            } else if (command == 1) {
                handleAlbumsSearchInLibrary(searchAlbumsInLibraryByTitle());
            } else if (command == 2) {
                handleAlbumsSearchInLibrary(searchAlbumsInLibraryByArtist());
            }
        }
    }
    
    private void handleAlbumsSearchInLibrary(List<Album> searchResult) {
        while (true) {
            if (searchResult.isEmpty()) {
                System.out.println("No albums were found");
                break;
            }
            System.out.println("Here are the albums for your search");

            for (int i = 0; i < searchResult.size(); i++) {
                System.out.println(searchResult.get(i).toString());
                System.out.println("\tSongs:");
                for(Song s : searchResult.get(i).getSongs()){
                    System.out.println("\t\t"+s.toString());
                }
            }
            break;
        }
        printBackOrExitMessege();
    }
    
    private void handleSongsSearchInLibrary(List<Song> searchResult) {
        while (true) {
            if (searchResult.isEmpty()) {
                System.out.println("No songs were found");
                break;
            }
            System.out.println("Here are the songs for your search");

            for (int i = 0; i < searchResult.size(); i++) {
                System.out.println(searchResult.get(i).toString());
            }
            break;
        }
        printBackOrExitMessege();
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

    private List<Song> searchSongsInLibrayByArtist() {
        System.out.println("Enter the artist of the song");
        String input = scanner.nextLine();
        return libraryModel.getSongsByArtist(input);
    }

    /**
     * Get command from the user and return the number of the command.
     * 0 means to go back
     * -1 means invalid command
     * any other number is the command
     *
     * @param min the min number of command
     * @param max the max number of command
     * @return a number representing the command from the user
     */
    private int getUserInput(int min, int max) {
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
            if (command < min || command > max) {
                printInputErrorMessage(min, max);
                return -1;
            }

            return command;
        } catch (NumberFormatException e) {
            printInputErrorMessage(min, max);
            return -1;
        }
    }

    private void printInputErrorMessage(int min, int max) {
        System.out.println("The input can only be a number between " + min + " - " + max + ", '"
                + EXIT_COMMAND + "' to exit, or '" + BACK_COMMAND + "' to go back a menu");
    }

    private void printBackOrExitMessege() {
        System.out.println("[" + BACK_COMMAND + "] - to go back");
        System.out.println("[" + EXIT_COMMAND + "] - to exit the program");
    }
}
