package db;

import model.LibraryModel;

public interface IDb {
    public LibraryModel getUserLibrary(String username, String password) throws IllegalArgumentException;
    public void createUser(String username, String password) throws IllegalArgumentException;
    public void updateUser(String username,String password, LibraryModel lib) throws IllegalArgumentException;
}
