package ui;

import java.io.IOException;
import java.net.URI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Controller for the viewer tab.
 */
public class ViewerTabController {
	@FXML
	private TextField playlistNameSearchField;
	@FXML
	private Button viewSongs;
	@FXML
	private Button viewPlaylist;
	@FXML
	private TextArea viewer;
	@FXML
	private Button viewAllPlaylists;
	@FXML
	private Button clear;
	@FXML
	private Text playlistNameSearchErrorTextbox;

	private static final URI URIEndpoint = URI.create("http://localhost:8080/springboot");
	private PlaylistServiceRemoteAccess playlistServiceRemoteAccess = new PlaylistServiceRemoteAccess(URIEndpoint);
	private SongServiceRemoteAccess songServiceRemoteAccess = new SongServiceRemoteAccess(URIEndpoint);

	/**
	 * Shows all the contents of all songs in the SongManager list.
	 * 
	 * @throws IOException if there are no songs to be shown
	 */

	@FXML
	public void viewSonglist() throws IOException {
		playlistNameSearchErrorTextbox.setText("");
		String allSongsAsStrings = songServiceRemoteAccess.getSongsStringed();

		if (allSongsAsStrings.equals("")) {
			playlistNameSearchErrorTextbox.setText("There are no songs to be shown");
		}
		viewer.setText(allSongsAsStrings);
	}

	/**
	 * Prints a spesific playlist when the user searches for one.
	 * 
	 * @throws IllegalArgumentException throws if there are no playlist with exact
	 *                                  name to be shown.
	 * @throws IOException
	 */
	@FXML
	public void viewPlaylist() throws IllegalArgumentException, IOException {
		playlistNameSearchErrorTextbox.setText("");
		try {
			viewer.setText(null);
			viewer.setText(playlistServiceRemoteAccess.getPlaylistString(playlistNameSearchField.getText()).toString());
		} catch (IllegalArgumentException e) {
			playlistNameSearchErrorTextbox.setText("No playlists are called this! :((");
		}
	}

	/**
	 * Lets the user view all playlists.
	 * 
	 * @throws IllegalArgumentException throws if there are no playlists to be shown
	 * @throws IOException
	 */
	@FXML
	public void viewPlaylists() throws IllegalArgumentException, IOException {
		playlistNameSearchErrorTextbox.setText("");
		String allPlaylistsAsString = playlistServiceRemoteAccess.getPlaylistNames();

		if (allPlaylistsAsString.equals("")) {
			playlistNameSearchErrorTextbox.setText("There are no playlist to be shown :((!!!!!!!!!!!");
		}
		viewer.setText(allPlaylistsAsString);
	}

	/**
	 * Clears the user interface.
	 */

	@FXML
	public void clear() {
		playlistNameSearchErrorTextbox.setText("");
		playlistNameSearchField.setText(null);
		viewer.setText(null);
	}
}
