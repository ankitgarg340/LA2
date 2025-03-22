package model;

public class User {
    private final String username;
    private final String password;
    private LibraryModel userLibrary;

    public User(String username, String passwordHash) {
        this.username = username;
        this.password = passwordHash;
    }

}
