package view;
import model.Album;
import model.LibraryModel;
import model.MusicStore;

public class View {
    private final MusicStore musicStore;
    private final LibraryModel libraryModel;

    public View(String dataFile){
        musicStore = new MusicStore();
        libraryModel = new LibraryModel();

        musicStore.readFile(dataFile);
    }

    public void Start(){
        for(Album a : musicStore.getAlbums()) {
            System.out.println(a.toString());
        }
    }
}
