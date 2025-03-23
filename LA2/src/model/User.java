package model;

public class User {
    private final String username;
    private final String password;
    private LibraryModel userLibrary;
    private final String salt;

    public User(String username, String passwordHash, String salt) {
        this.username = username;
        this.password = passwordHash;
        this.salt = salt;

        userLibrary = new LibraryModel();
    }

    public void setUserLibrary(LibraryModel lib) {
        userLibrary = lib.makeCopy();
    }

    public LibraryModel getUserLibrary() {
        return userLibrary.makeCopy();
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }
}
