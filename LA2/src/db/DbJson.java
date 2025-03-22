package db;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.LibraryModel;
import model.User;


public class DbJson implements IDb {
    private static final String FILE_PATH = "users.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private HashMap<String, User> usersByUsername;

    public DbJson() throws IOException {
        File file = new File(FILE_PATH);
        if(!file.exists()){
            file.createNewFile();
            usersByUsername = new HashMap<>();
        } else {
            FileReader reader = new FileReader(FILE_PATH);
            Type userListType = new TypeToken<HashMap<String, User>>() {}.getType();
            usersByUsername = gson.fromJson(reader, userListType);
        }

    }

    @Override
    public LibraryModel getUserLibrary(String username, String password) throws IllegalArgumentException {
        if(usersByUsername.containsKey(username)){
            return usersByUsername.get(username).getUserLibrary();
        }
        else{
            throw new IllegalArgumentException("User not exist or wrong password");
        }
    }

    @Override
    public void createUser(String username, String password) throws IllegalArgumentException {
        if(!usersByUsername.containsKey(username)){
            usersByUsername.put(username, new User(username, password));
        }
        else{
            throw new IllegalArgumentException("User already exist");
        }
    }

    @Override
    public void updateUser(String username, String password, LibraryModel lib) throws IllegalArgumentException {
        if(usersByUsername.containsKey(username)){
            usersByUsername.get(username).setUserLibrary(lib);
            saveUsers();
        }
        else{
            throw new IllegalArgumentException("User not exist or wrong password");
        }
    }

    private void saveUsers(){
        try {
            gson.toJson(usersByUsername, new FileWriter(FILE_PATH));
        } catch (IOException e) {
        }
    }
}
