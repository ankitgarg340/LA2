package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class User {
    private final String username;
    private final String password;
    private LibraryModel userLibrary;

    public User(String username, String passwordHash) {
        this.username = username;
        this.password = passwordHash;
    }

    public void setUserLibrary(LibraryModel lib){
        userLibrary = lib.makeCopy();
    }

    public LibraryModel getUserLibrary(){
        return userLibrary.makeCopy();
    }
}
