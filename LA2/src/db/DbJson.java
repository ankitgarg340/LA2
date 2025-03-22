package db;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import model.LibraryModel;
import model.User;


public class DbJson implements IDb {
    private static final String FILE_PATH = "users.json";
    private Gson gson;

    private HashMap<String, User> usersByUsername;

    public DbJson() throws IOException {
        gson = new Gson();
        usersByUsername = new HashMap<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new IOException("Could not create db file");
            }
            saveUsers();
        } else {
            loadUsers();
        }

    }

    @Override
    public LibraryModel getUserLibrary(String username, String password) throws IllegalArgumentException {
        if (usersByUsername.containsKey(username)) {
            return usersByUsername.get(username).getUserLibrary();
        } else {
            throw new IllegalArgumentException("User not exist or wrong password");
        }
    }

    @Override
    public void createUser(String username, String password) throws IllegalArgumentException {
        if (!usersByUsername.containsKey(username)) {
            usersByUsername.put(username, new User(username, password));
            saveUsers();
        } else {
            throw new IllegalArgumentException("User already exist");
        }
    }

    @Override
    public void updateUser(String username, String password, LibraryModel lib) throws IllegalArgumentException {
        if (usersByUsername.containsKey(username)) {
            usersByUsername.get(username).setUserLibrary(lib);
            saveUsers();
        } else {
            throw new IllegalArgumentException("User not exist or wrong password");
        }
    }

    private void saveUsers() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(usersByUsername, writer);
        } catch (IOException e) {
        }
    }

    private void loadUsers() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type usersType = new TypeToken<HashMap<String, User>>() {}.getType();
            usersByUsername = gson.fromJson(reader, usersType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
