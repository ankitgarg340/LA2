package model;

public class User {
    private final String username;
    private final String password;
    private LibraryModel userLibrary;

    public User(String username, String passwordHash) {
        this.username = username;
        this.password = passwordHash;
        userLibrary = new LibraryModel();
    }

    public void setUserLibrary(LibraryModel lib){
        userLibrary = lib.makeCopy();
    }

    public LibraryModel getUserLibrary(){
        return userLibrary.makeCopy();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
