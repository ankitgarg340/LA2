package view;

import model.LibraryModel;
import model.MusicStore;

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
        musicStore.readFile(dataFile);
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
                int a = 0;
            } else if (command == 2) {
                int a = 0;
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
            int command = getUserInput(1, 4);

            if (command == 0) {
                break;
            } else if (command == 1) {
                int a = 0;
            } else if (command == 2) {
                int a = 0;
            } else if (command == 3) {
                int a = 0;
            } else if (command == 4) {
                int a = 0;
            }
        }
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
