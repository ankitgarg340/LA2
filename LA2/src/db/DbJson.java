package db;

import java.io.*;
import java.lang.reflect.Type;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.HashMap;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import model.LibraryModel;
import model.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


public class DbJson implements IDb {
    private final Gson gson;
    private final String file_path;
    private HashMap<String, User> usersByUsername;

    public DbJson(String filepath) throws IOException {
        gson = new Gson();
        usersByUsername = new HashMap<>();
        file_path = filepath;
        File file = new File(filepath);
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
        User user = getUser(username, password);
        return user.getUserLibrary();
    }

    @Override
    public void createUser(String username, String password) throws IllegalArgumentException {
        if (!usersByUsername.containsKey(username)) {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            String saltStr = Base64.getEncoder().encodeToString(salt);
            String hashPass = hashPassword(password, saltStr);
            usersByUsername.put(username, new User(username, hashPass, saltStr));
            saveUsers();
        } else {
            throw new IllegalArgumentException("User already exist");
        }
    }

    @Override
    public void updateUser(String username, String password, LibraryModel lib) throws IllegalArgumentException {
        User user = getUser(username, password);
        user.setUserLibrary(lib);
        saveUsers();
    }

    private void saveUsers() {
        try (FileWriter writer = new FileWriter(file_path)) {
            gson.toJson(usersByUsername, writer);
        } catch (IOException e) {
            System.out.println("Failed saving db");
        }
    }

    private User getUser(String username, String password) throws IllegalArgumentException {
        if (usersByUsername.containsKey(username)) {
            User user = usersByUsername.get(username);
            String hashPass = hashPassword(password, user.getSalt());
            if (user.getPassword().equals(hashPass)) {
                return user;
            }
        }
        throw new IllegalArgumentException("User not exist or wrong password");
    }

    private void loadUsers() throws IOException {
        Reader reader = new FileReader(file_path);
        Type usersType = new TypeToken<HashMap<String, User>>() {
        }.getType();
        usersByUsername = gson.fromJson(reader, usersType);
        reader.close();
    }

    private String hashPassword(String password, String salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), Base64.getDecoder().decode(salt), 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return Base64.getEncoder().encodeToString(factory.generateSecret(spec).getEncoded());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
