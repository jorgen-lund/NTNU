package ui;

import java.io.IOException;
import java.net.URI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * Controller for the stats tab.
 */
public class StatsTabController {

    @FXML
    private Button statsTabRefreshButton;

    private static final URI URIEndpoint = URI.create("http://localhost:8080/springboot");
    private PlaylistServiceRemoteAccess playlistServiceRemoteAccess = new PlaylistServiceRemoteAccess(URIEndpoint);
    private SongServiceRemoteAccess songServiceRemoteAccess = new SongServiceRemoteAccess(URIEndpoint);

    @FXML
    private TextArea highestRatedViewer;

    @FXML
    private TextArea mostAddedViewer;

    /**
     * This methods sets the text fields in the tab to the top lists of the highest
     * rated and the most occuring song.
     * 
     * @throws IOException
     */
    public void refreshStats() throws IOException {
        String sortedSongsByRating = songServiceRemoteAccess.getSongStats();
        highestRatedViewer.setText(sortedSongsByRating);

        String sortedSongsByInstances = playlistServiceRemoteAccess.getPlaylistStats();
        mostAddedViewer.setText(sortedSongsByInstances);
    }
}
