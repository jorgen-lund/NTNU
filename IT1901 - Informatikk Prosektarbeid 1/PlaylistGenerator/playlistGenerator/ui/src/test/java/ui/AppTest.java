package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class AppTest extends ApplicationTest {
    private TabController controller;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Tabs.fxml"));
        Parent parent = fxmlLoader.load();
        this.controller = fxmlLoader.getController();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Test
    public void controller_init_test() {
        assertNotNull(this.controller);
    }

    @Test
    public void addSong_Test() {
        clickOn("#SongTab");
        try {
            Thread.sleep(500);

        } catch (InterruptedException ie) {

        }
        clickOn("#Love");
        clickOn("#Sad");
        clickOn("#Chill");

        clickOn("#songTitle").write("title1");
        clickOn("#songArtist").write("artist1");

        clickOn("#addSong");

        TextField title = lookup("#songTitle").query();
        TextField artist = lookup("#songArtist").query();
        String text = null;

        assertEquals(text, title.getText());
        assertEquals(text, artist.getText());
    }

    @Test
    public void playlist_test() {
        clickOn("#PlaylistTab");
        try {
            Thread.sleep(500);

        } catch (InterruptedException ie) {

        }

        clickOn("#playlistName").write("playlistTest");

        clickOn("#Love");
        clickOn("#Sad");
        clickOn("#Chill");

        TextField title = lookup("#playlistName").query();

        clickOn("#createPlaylist");

        String text = "";

        assertEquals(text, title.getText());
    }

    @Test
    public void viewSongTest_test() {
        clickOn("#ViewerTab");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ie) {

        }
        clickOn("#viewAllSongs");

        TextArea view = (TextArea) lookup("#viewer").query();
        assertNotNull(view.getText());
    }

    @Test
    public void viewAllPlaylists_test() {
        clickOn("#ViewerTab");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ie) {

        }
        clickOn("#viewAllPlaylists");

        TextArea view = (TextArea) lookup("#viewer").query();
        assertNotNull(view.getText());
    }

    @Test
    public void viewPlaylist_test() {
        clickOn("#PlaylistTab");
        try {
            Thread.sleep(500);

        } catch (InterruptedException ie) {

        }

        clickOn("#playlistName").write("playlistTest");

        clickOn("#Love");
        clickOn("#Sad");
        clickOn("#Chill");

        clickOn("#createPlaylist");

        clickOn("#ViewerTab");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ie) {

        }
        clickOn("#playlistNameSearchField").write("playlistTest"); // Should exist
        clickOn("#viewPlaylist");

        TextArea view = (TextArea) lookup("#viewer").query();

        assertNotNull(view.getText());

    }

    @Test
    public void clear_test() {
        // test for song clear
        clickOn("#songTitle").write("title1");
        clickOn("#songArtist").write("artist1");

        clickOn("#clear");
        TextField songTitle = lookup("#songTitle").query();
        TextField songArtist = lookup("#songArtist").query();
        String songText = null;

        assertEquals(songText, songTitle.getText());
        assertEquals(songText, songArtist.getText());

        // test for playlist clear
        clickOn("#PlaylistTab");
        clickOn("#playlistName").write("playlistTest");

        clickOn("#clear");
        TextField playlistTitle = lookup("#playlistName").query();

        String playlistText = "";

        assertEquals(playlistText, playlistTitle.getText());

        // test for Viewer clear
        clickOn("#ViewerTab");
        clickOn("#playlistNameSearchField").write("playlistTest"); // Should exist

        clickOn("#clear");

        TextField playListNameSearchField = lookup("#playlistNameSearchField").query();

        String viewerText = null;

        assertEquals(viewerText, playListNameSearchField.getText());
    }

    @Test
    public void statsTest() {
        // add a song to make sure there is a song so the stats returns at least one
        // song.
        clickOn("#SongTab");
        try {
            Thread.sleep(500);

        } catch (InterruptedException ie) {

        }
        clickOn("#Love");
        clickOn("#Sad");
        clickOn("#Chill");

        clickOn("#songTitle").write("title1");
        clickOn("#songArtist").write("artist1");

        clickOn("#addSong");

        // test if stats work
        clickOn("#StatsTab");

        clickOn("#statsTabRefreshButton");

        String text = "";

        TextArea highestRatedViewer = lookup("#highestRatedViewer").query();
        TextArea mostAddedViewer = lookup("#mostAddedViewer").query();

        assertNotEquals(text, highestRatedViewer.getText());
        assertNotEquals(text, mostAddedViewer.getText());
    }

}
