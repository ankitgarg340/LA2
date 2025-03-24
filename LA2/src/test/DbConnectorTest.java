package test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.*;
import org.junit.jupiter.api.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class DbConnectorTest {
    private static File dbFile;
    private static final String jsonTest = "{\"test\":{\"username\":\"test\",\"password\":\"nBt0lwRgc92OgUYcAA5nyQ\\u003d\\u003d\",\"userLibrary\":{\"songs\":[{\"song\":{\"title\":\"Cold Shoulder\",\"artist\":\"Adele\",\"albumName\":\"19\"},\"isFavorite\":false,\"rating\":0,\"lastPlayed\":\"Mar 23, 2025, 12:27:08 AM\",\"playCounter\":4},{\"song\":{\"title\":\"Traveling Man\",\"artist\":\"Dolly Parton\",\"albumName\":\"Coat of Many Colors\"},\"isFavorite\":false,\"rating\":0,\"playCounter\":0},{\"song\":{\"title\":\"The Cave\",\"artist\":\"Mumford \\u0026 Sons\",\"albumName\":\"Sigh No More\"},\"isFavorite\":false,\"rating\":0,\"lastPlayed\":\"Mar 23, 2025, 2:15:20 AM\",\"playCounter\":8},{\"song\":{\"title\":\"Fire\",\"artist\":\"The Heavy\",\"albumName\":\"Sons\"},\"isFavorite\":false,\"rating\":5,\"playCounter\":0},{\"song\":{\"title\":\"Jesus\",\"artist\":\"Amos Lee\",\"albumName\":\"Mission Bell\"},\"isFavorite\":true,\"rating\":4,\"playCounter\":0},{\"song\":{\"title\":\"Tapestry\",\"artist\":\"Carol King\",\"albumName\":\"Tapestry\"},\"isFavorite\":true,\"rating\":5,\"playCounter\":0},{\"song\":{\"title\":\"Daydreamer\",\"artist\":\"Adele\",\"albumName\":\"19\"},\"isFavorite\":false,\"rating\":0,\"playCounter\":0},{\"song\":{\"title\":\"Best for Last\",\"artist\":\"Adele\",\"albumName\":\"19\"},\"isFavorite\":false,\"rating\":0,\"playCounter\":0},{\"song\":{\"title\":\"Chasing Pavements\",\"artist\":\"Adele\",\"albumName\":\"19\"},\"isFavorite\":false,\"rating\":0,\"playCounter\":0},{\"song\":{\"title\":\"Crazy for You\",\"artist\":\"Adele\",\"albumName\":\"19\"},\"isFavorite\":false,\"rating\":0,\"playCounter\":0},{\"song\":{\"title\":\"Melt My Heart to Stone\",\"artist\":\"Adele\",\"albumName\":\"19\"},\"isFavorite\":false,\"rating\":0,\"playCounter\":0},{\"song\":{\"title\":\"First Love\",\"artist\":\"Adele\",\"albumName\":\"19\"},\"isFavorite\":false,\"rating\":0,\"playCounter\":0},{\"song\":{\"title\":\"Right as Rain\",\"artist\":\"Adele\",\"albumName\":\"19\"},\"isFavorite\":false,\"rating\":0,\"playCounter\":0},{\"song\":{\"title\":\"Make You Feel My Love\",\"artist\":\"Adele\",\"albumName\":\"19\"},\"isFavorite\":false,\"rating\":0,\"playCounter\":0},{\"song\":{\"title\":\"My Same\",\"artist\":\"Adele\",\"albumName\":\"19\"},\"isFavorite\":false,\"rating\":0,\"playCounter\":0},{\"song\":{\"title\":\"Tired\",\"artist\":\"Adele\",\"albumName\":\"19\"},\"isFavorite\":false,\"rating\":0,\"playCounter\":0},{\"song\":{\"title\":\"Hometown Glory\",\"artist\":\"Adele\",\"albumName\":\"19\"},\"isFavorite\":false,\"rating\":0,\"playCounter\":0}],\"albums\":[{\"title\":\"19\",\"artist\":\"Adele\",\"genre\":\"Pop\",\"year\":\"2008\",\"songs\":[{\"title\":\"Daydreamer\",\"artist\":\"Adele\",\"albumName\":\"19\"},{\"title\":\"Best for Last\",\"artist\":\"Adele\",\"albumName\":\"19\"},{\"title\":\"Chasing Pavements\",\"artist\":\"Adele\",\"albumName\":\"19\"},{\"title\":\"Cold Shoulder\",\"artist\":\"Adele\",\"albumName\":\"19\"},{\"title\":\"Crazy for You\",\"artist\":\"Adele\",\"albumName\":\"19\"},{\"title\":\"Melt My Heart to Stone\",\"artist\":\"Adele\",\"albumName\":\"19\"},{\"title\":\"First Love\",\"artist\":\"Adele\",\"albumName\":\"19\"},{\"title\":\"Right as Rain\",\"artist\":\"Adele\",\"albumName\":\"19\"},{\"title\":\"Make You Feel My Love\",\"artist\":\"Adele\",\"albumName\":\"19\"},{\"title\":\"My Same\",\"artist\":\"Adele\",\"albumName\":\"19\"},{\"title\":\"Tired\",\"artist\":\"Adele\",\"albumName\":\"19\"},{\"title\":\"Hometown Glory\",\"artist\":\"Adele\",\"albumName\":\"19\"}]},{\"title\":\"Coat of Many Colors\",\"artist\":\"Dolly Parton\",\"genre\":\"Traditional Country\",\"year\":\"1971\",\"songs\":[{\"title\":\"Coat of Many Colors\",\"artist\":\"Dolly Parton\",\"albumName\":\"Coat of Many Colors\"},{\"title\":\"Traveling Man\",\"artist\":\"Dolly Parton\",\"albumName\":\"Coat of Many Colors\"},{\"title\":\"My Blue Tears\",\"artist\":\"Dolly Parton\",\"albumName\":\"Coat of Many Colors\"},{\"title\":\"If I Lose My Mind\",\"artist\":\"Dolly Parton\",\"albumName\":\"Coat of Many Colors\"},{\"title\":\"The Mystery of the Mystery\",\"artist\":\"Dolly Parton\",\"albumName\":\"Coat of Many Colors\"},{\"title\":\"She Never Met a Man (She Didn\\u0027t Like)\",\"artist\":\"Dolly Parton\",\"albumName\":\"Coat of Many Colors\"},{\"title\":\"Early Morning Breeze\",\"artist\":\"Dolly Parton\",\"albumName\":\"Coat of Many Colors\"},{\"title\":\"The Way I See You\",\"artist\":\"Dolly Parton\",\"albumName\":\"Coat of Many Colors\"},{\"title\":\"Here I Am\",\"artist\":\"Dolly Parton\",\"albumName\":\"Coat of Many Colors\"},{\"title\":\"A Better Place to Live\",\"artist\":\"Dolly Parton\",\"albumName\":\"Coat of Many Colors\"}]},{\"title\":\"Sigh No More\",\"artist\":\"Mumford \\u0026 Sons\",\"genre\":\"Alternative\",\"year\":\"2009\",\"songs\":[{\"title\":\"Sigh No More\",\"artist\":\"Mumford \\u0026 Sons\",\"albumName\":\"Sigh No More\"},{\"title\":\"The Cave\",\"artist\":\"Mumford \\u0026 Sons\",\"albumName\":\"Sigh No More\"},{\"title\":\"Winter Winds\",\"artist\":\"Mumford \\u0026 Sons\",\"albumName\":\"Sigh No More\"},{\"title\":\"Roll Away Your Stone\",\"artist\":\"Mumford \\u0026 Sons\",\"albumName\":\"Sigh No More\"},{\"title\":\"White Blank Page\",\"artist\":\"Mumford \\u0026 Sons\",\"albumName\":\"Sigh No More\"},{\"title\":\"I Gave You All\",\"artist\":\"Mumford \\u0026 Sons\",\"albumName\":\"Sigh No More\"},{\"title\":\"Little Lion Man\",\"artist\":\"Mumford \\u0026 Sons\",\"albumName\":\"Sigh No More\"},{\"title\":\"Timshel\",\"artist\":\"Mumford \\u0026 Sons\",\"albumName\":\"Sigh No More\"},{\"title\":\"Thistle \\u0026 Weeds\",\"artist\":\"Mumford \\u0026 Sons\",\"albumName\":\"Sigh No More\"},{\"title\":\"Awake My Soul\",\"artist\":\"Mumford \\u0026 Sons\",\"albumName\":\"Sigh No More\"},{\"title\":\"Dust Bowl Dance\",\"artist\":\"Mumford \\u0026 Sons\",\"albumName\":\"Sigh No More\"},{\"title\":\"After the Storm\",\"artist\":\"Mumford \\u0026 Sons\",\"albumName\":\"Sigh No More\"}]},{\"title\":\"Sons\",\"artist\":\"The Heavy\",\"genre\":\"Rock\",\"year\":\"2019\",\"songs\":[{\"title\":\"Heavy for You\",\"artist\":\"The Heavy\",\"albumName\":\"Sons\"},{\"title\":\"The Thief\",\"artist\":\"The Heavy\",\"albumName\":\"Sons\"},{\"title\":\"Better as One\",\"artist\":\"The Heavy\",\"albumName\":\"Sons\"},{\"title\":\"Fire\",\"artist\":\"The Heavy\",\"albumName\":\"Sons\"},{\"title\":\"Fighting for the Same Thing\",\"artist\":\"The Heavy\",\"albumName\":\"Sons\"},{\"title\":\"Hurt Interlude\",\"artist\":\"The Heavy\",\"albumName\":\"Sons\"},{\"title\":\"Put the Hurt on Me\",\"artist\":\"The Heavy\",\"albumName\":\"Sons\"},{\"title\":\"Simple Things\",\"artist\":\"The Heavy\",\"albumName\":\"Sons\"},{\"title\":\"A Whole Lot of Love\",\"artist\":\"The Heavy\",\"albumName\":\"Sons\"},{\"title\":\"What Don\\u0027t Kill You\",\"artist\":\"The Heavy\",\"albumName\":\"Sons\"},{\"title\":\"Burn Bright\",\"artist\":\"The Heavy\",\"albumName\":\"Sons\"}]},{\"title\":\"Mission Bell\",\"artist\":\"Amos Lee\",\"genre\":\"Singer/Songwriter\",\"year\":\"2010\",\"songs\":[{\"title\":\"El Camino\",\"artist\":\"Amos Lee\",\"albumName\":\"Mission Bell\"},{\"title\":\"Windows Are Rolled Down\",\"artist\":\"Amos Lee\",\"albumName\":\"Mission Bell\"},{\"title\":\"Flower\",\"artist\":\"Amos Lee\",\"albumName\":\"Mission Bell\"},{\"title\":\"Stay With Me\",\"artist\":\"Amos Lee\",\"albumName\":\"Mission Bell\"},{\"title\":\"Out of the Cold\",\"artist\":\"Amos Lee\",\"albumName\":\"Mission Bell\"},{\"title\":\"Jesus\",\"artist\":\"Amos Lee\",\"albumName\":\"Mission Bell\"},{\"title\":\"Hello Again\",\"artist\":\"Amos Lee\",\"albumName\":\"Mission Bell\"},{\"title\":\"Cup of Sorrow\",\"artist\":\"Amos Lee\",\"albumName\":\"Mission Bell\"},{\"title\":\"Clear Blue Eyes (feat. Lucinda Williams)\",\"artist\":\"Amos Lee\",\"albumName\":\"Mission Bell\"},{\"title\":\"Behind Me Now\",\"artist\":\"Amos Lee\",\"albumName\":\"Mission Bell\"}]},{\"title\":\"Tapestry\",\"artist\":\"Carol King\",\"genre\":\"Rock\",\"year\":\"1971\",\"songs\":[{\"title\":\"I Feel The Earth Move\",\"artist\":\"Carol King\",\"albumName\":\"Tapestry\"},{\"title\":\"So Far Away\",\"artist\":\"Carol King\",\"albumName\":\"Tapestry\"},{\"title\":\"Home Again\",\"artist\":\"Carol King\",\"albumName\":\"Tapestry\"},{\"title\":\"Beautiful\",\"artist\":\"Carol King\",\"albumName\":\"Tapestry\"},{\"title\":\"Way Over Yonder\",\"artist\":\"Carol King\",\"albumName\":\"Tapestry\"},{\"title\":\"You\\u0027ve Got A Friend\",\"artist\":\"Carol King\",\"albumName\":\"Tapestry\"},{\"title\":\"Where You Lead\",\"artist\":\"Carol King\",\"albumName\":\"Tapestry\"},{\"title\":\"Will You Love Me Tomorrow?\",\"artist\":\"Carol King\",\"albumName\":\"Tapestry\"},{\"title\":\"Tapestry\",\"artist\":\"Carol King\",\"albumName\":\"Tapestry\"},{\"title\":\"(You Make Me Feel Like) A Natural Woman\",\"artist\":\"Carol King\",\"albumName\":\"Tapestry\"}]}],\"playlists\":[{\"name\":\"bla\",\"songs\":[{\"title\":\"Daydreamer\",\"artist\":\"Adele\",\"albumName\":\"19\"}]}]},\"salt\":\"U8JDBt/hgp7Tow/l4b1vxQ\\u003d\\u003d\"}}";

    private static File storeFile;
    private static final String storeText = """
            19,Adele
            Mission Bell,Amos Lee
            Sigh No More,Mumford & Sons
            Coat of Many Colors,Dolly Parton
            Tapestry,Carol King
            Sons,The Heavy""";

    private static File album1File;
    private static final String album1Test = """
            19,Adele,Pop,2008
            Daydreamer
            Best for Last
            Chasing Pavements
            Cold Shoulder
            Crazy for You
            Melt My Heart to Stone
            First Love
            Right as Rain
            Make You Feel My Love
            My Same
            Tired
            Hometown Glory""";

    private static File album2File;
    private static final String album2Test = """
            Coat of Many Colors,Dolly Parton,Traditional Country,1971
            Coat of Many Colors
            Traveling Man
            My Blue Tears
            If I Lose My Mind
            The Mystery of the Mystery
            She Never Met a Man (She Didn't Like)
            Early Morning Breeze
            The Way I See You
            Here I Am
            A Better Place to Live""";

    private static File album3File;
    private static final String album3Test = """
            Sigh No More,Mumford & Sons,Alternative,2009
            Sigh No More
            The Cave
            Winter Winds
            Roll Away Your Stone
            White Blank Page
            I Gave You All
            Little Lion Man
            Timshel
            Thistle & Weeds
            Awake My Soul
            Dust Bowl Dance
            After the Storm""";

    private static File album4File;
    private static final String album4Test = """
            Sons,The Heavy,Rock,2019
            Heavy for You
            The Thief
            Better as One
            Fire
            Fighting for the Same Thing
            Hurt Interlude
            Put the Hurt on Me
            Simple Things
            A Whole Lot of Love
            What Don't Kill You
            Burn Bright""";

    private static File album5File;
    private static final String album5Test = """
            Mission Bell,Amos Lee,Singer/Songwriter,2010
            El Camino
            Windows Are Rolled Down
            Flower
            Stay With Me
            Out of the Cold
            Jesus
            Hello Again
            Cup of Sorrow
            Clear Blue Eyes (feat. Lucinda Williams)
            Behind Me Now""";

    private static File album6File;
    private static final String album6Test = """
            Tapestry,Carol King,Rock,1971
            I Feel The Earth Move
            So Far Away
            Home Again
            Beautiful
            Way Over Yonder
            You've Got A Friend
            Where You Lead
            Will You Love Me Tomorrow?
            Tapestry
            (You Make Me Feel Like) A Natural Woman""";


    private static final String username = "test";
    private static final String password = "123";

    private static MusicStore ms;
    private static DbConnector dbc;

    @BeforeAll
    static void setUp() {
        try {
            storeFile = new File("store.txt");
            assertTrue(storeFile.createNewFile());
            BufferedWriter writer = new BufferedWriter(new FileWriter(storeFile, true));
            writer.write(storeText);
            writer.close();


            album1File = new File("19_Adele.txt");
            assertTrue(album1File.createNewFile());
            writer = new BufferedWriter(new FileWriter(album1File, true));
            writer.write(album1Test);
            writer.close();


            album2File = new File("Coat of Many Colors_Dolly Parton.txt");
            assertTrue(album2File.createNewFile());
            writer = new BufferedWriter(new FileWriter(album2File, true));
            writer.write(album2Test);
            writer.close();


            album3File = new File("Sigh No More_Mumford & Sons.txt");
            assertTrue(album3File.createNewFile());
            writer = new BufferedWriter(new FileWriter(album3File, true));
            writer.write(album3Test);
            writer.close();


            album4File = new File("Sons_The Heavy.txt");
            assertTrue(album4File.createNewFile());
            writer = new BufferedWriter(new FileWriter(album4File, true));
            writer.write(album4Test);
            writer.close();


            album5File = new File("Mission Bell_Amos Lee.txt");
            assertTrue(album5File.createNewFile());
            writer = new BufferedWriter(new FileWriter(album5File, true));
            writer.write(album5Test);
            writer.close();


            album6File = new File("Tapestry_Carol King.txt");
            assertTrue(album6File.createNewFile());
            writer = new BufferedWriter(new FileWriter(album6File, true));
            writer.write(album6Test);
            writer.close();

            ms = new MusicStore();
            try {
                ms.readFile(storeFile.getName());
            } catch (IOException e) {
                fail();
            }

        } catch (Exception e) {
            fail();
        }
    }

    @BeforeEach
    void beforeEach() {
        try {
            dbFile = new File("users.json");
            assertTrue(dbFile.createNewFile());
            BufferedWriter writer = new BufferedWriter(new FileWriter(dbFile, true));
            writer.write(jsonTest);
            writer.close();
            dbc = new DbConnector(ms);
        } catch (IOException e) {
            fail();
        }
    }

    @AfterEach
    void afterEach() {
        assertTrue(dbFile.delete());
    }

    @AfterAll
    static void afterTests() {
        assertTrue(storeFile.delete());
        assertTrue(album1File.delete());
        assertTrue(album2File.delete());
        assertTrue(album3File.delete());
        assertTrue(album4File.delete());
        assertTrue(album5File.delete());
        assertTrue(album6File.delete());
    }

    @Test
    public void testCreateUserNew() {
        String username1 = "aaa";
        String password1 = "ppp";
        assertDoesNotThrow(() -> dbc.createUser(username1, password1));
        assertDoesNotThrow(() -> dbc.login(username1, password1));

        LibraryModel lib = dbc.login(username1, password1);
        assertTrue(lib.getAllSongs().isEmpty());
        assertTrue(lib.getAllAlbums().isEmpty());
        assertTrue(lib.getUserPlaylistsNames().isEmpty());
    }

    @Test
    public void testCreateUserAlreadyExist() {
        assertThrows(IllegalArgumentException.class, () -> dbc.createUser(username, password));
    }

    @Test
    public void testLoginExist() {
        assertDoesNotThrow(() -> dbc.login(username, password));

        LibraryModel lib = dbc.login(username, password);
        Type usersType = new TypeToken<HashMap<String, User>>() {
        }.getType();
        Gson gson = new Gson();
        HashMap<String, User> usersByUsername = gson.fromJson(jsonTest, usersType);

        assertEquals(gson.toJson(usersByUsername.get(username).getUserLibrary(), LibraryModel.class),
                gson.toJson(lib, LibraryModel.class));
    }

    @Test
    public void testLoginNotExist() {
        String username1 = "aaa";
        String password1 = "ppp";

        assertThrows(IllegalArgumentException.class, () -> dbc.login(username1, password1));
    }

    @Test
    public void testLoginWrongPassword() {
        String password1 = "ppp";

        assertThrows(IllegalArgumentException.class, () -> dbc.login(username, password1));
    }


    @Test
    public void testUpdateUserExist() {
        LibraryModel nlib = new LibraryModel();
        assertDoesNotThrow(() -> dbc.updateUser(username, password, nlib));

        LibraryModel lib = dbc.login(username, password);
        assertTrue(lib.getAllSongs().isEmpty());
        assertTrue(lib.getAllAlbums().isEmpty());
        assertTrue(lib.getUserPlaylistsNames().isEmpty());

        Song s = ms.getAlbums().get(0).getSongs().get(0);
        nlib.addSong(s);
        dbc.updateUser(username, password, nlib);
        lib = dbc.login(username, password);
        assertEquals(s, lib.getAllSongs().get(0));
        assertEquals(1, lib.getAllSongs().size());
    }

    @Test
    public void testUpdateUserNotExist() {
        String username1 = "aaa";
        String password1 = "ppp";

        assertThrows(IllegalArgumentException.class, () -> dbc.updateUser(username1, password1, new LibraryModel()));
    }

    @Test
    public void testCtorJsonFileDoesntExist(){
        assertTrue(dbFile.delete());
        assertDoesNotThrow(() -> dbc = new DbConnector(ms));
    }
}
