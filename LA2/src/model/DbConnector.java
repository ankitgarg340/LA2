package model;

import db.DbJson;
import db.IDb;

import java.io.IOException;

public class DbConnector {
    private final IDb db;
    private final MusicStore musicStore;

    public DbConnector(MusicStore store) throws IOException {
        musicStore = store;
        db = new DbJson();
    }
    private LibraryModel getLibraryModelInfoFromStore(LibraryModel dbLib){
//        LibraryModel fromStoreLib = new LibraryModel();
//
//        return fromStoreLib;
        return dbLib;
    }

    public void createUser(String username, String password) throws IllegalArgumentException{
        db.createUser(username, password);
    }

    public void updateUser(String username, String password, LibraryModel lib) throws IllegalArgumentException{
        db.updateUser(username, password, lib);
    }

    public LibraryModel login(String username, String password) throws IllegalArgumentException{
        LibraryModel dbLib = db.getUserLibrary(username, password);

        return getLibraryModelInfoFromStore(dbLib);
    }
}
