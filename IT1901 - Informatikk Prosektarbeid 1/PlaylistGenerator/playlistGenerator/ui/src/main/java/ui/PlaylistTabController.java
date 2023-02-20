package ui;

import java.net.URI;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Controller for the playlist tab.
 */
public class PlaylistTabController {
	@FXML
	private Button createPlaylist;
	@FXML
	private Slider minRating;
	@FXML
	private Slider maxRating;
	@FXML
	private TextField playlistName;
	@FXML
	private Button playlistMoodHappy;
	@FXML
	private Button playlistMoodLove;
	@FXML
	private Button playlistMoodRunning;
	@FXML
	private Button playlistMoodSensual;
	@FXML
	private Button playlistMoodParty;
	@FXML
	private Button playlistMoodAngry;
	@FXML
	private Button playlistMoodSad;
	@FXML
	private Button playlistMoodSleep;
	@FXML
	private Button playlistMoodChill;
	@FXML
	private Button clear;
	@FXML
	private Text playlistNameErrorTextbox;
	@FXML
	private Text playlistErrorTextbox;
	@FXML
	private Text playlistAddedTextbox;

	private ArrayList<Button> playlistMoodsSelected = new ArrayList<Button>();
	private ArrayList<String> playlistMoodsSelectedStrings = new ArrayList<String>();

	private static final URI URIEndpoint = URI.create("http://localhost:8080/springboot");
	private PlaylistServiceRemoteAccess playlistServiceRemoteAccess = new PlaylistServiceRemoteAccess(URIEndpoint);
	private SongServiceRemoteAccess songServiceRemoteAccess = new SongServiceRemoteAccess(URIEndpoint);

	/**
	 * Creates a list of buttons and list og strings with the moods specified for
	 * the Checks if the button is already pressed, if so it clears it. If not
	 * already selected and less than three moods are good then it adds the mood to
	 * the moods list.
	 * 
	 * @param event mouseclick
	 */
	@FXML
	public void addPlaylistMood(ActionEvent event) {
		Button source = (Button) event.getSource();
		if (this.playlistMoodsSelected.contains(source)) {
			playlistMoodsSelected.remove(source);
			playlistMoodsSelectedStrings.remove(source.getId());
			source.setStyle("-fx-background-color: pink; -fx-border-color: black;");
		} else if (this.playlistMoodsSelectedStrings.size() < 3) {
			playlistMoodsSelected.add(source);
			playlistMoodsSelectedStrings.add(source.getId());
			source.setStyle("-fx-background-color: red; -fx-border-color: black;");
		}
	}

	/**
	 * Creates a playlist when button is clicked from the input in ui. Will show
	 * user an error if there
	 * are any unsupported inputs.
	 */

	@FXML
	void createPlaylist() {
		try {
			playlistServiceRemoteAccess.addPlaylist(playlistName.getText(), playlistMoodsSelectedStrings,
					minRating.getValue(),
					maxRating.getValue(), songServiceRemoteAccess.getAllSongs());
			playlistAddedTextbox.setText("Playlist Successfully added");
			clearErrorMessages();
		} catch (IllegalArgumentException e) {
			clearSuccessfullyAddedMessage();
			playlistErrorTextbox.setText("Playlist not created. Needs name, mood(s) or max rating >= min rating");
		}
		clear();
	}

	/**
	 * Clears errormessage from the userinterface.
	 */

	private void clearErrorMessages() {
		playlistErrorTextbox.setText("");
	}

	/**
	 * Clears previous success message.
	 */

	private void clearSuccessfullyAddedMessage() {
		playlistAddedTextbox.setText("");
	}

	/**
	 * Clears the user interface.
	 */

	@FXML
	public void clear() {
		playlistName.setText("");
		minRating.setValue(0);
		maxRating.setValue(0);
		playlistMoodHappy.setStyle("-fx-background-color: pink; -fx-border-color: black;");
		playlistMoodLove.setStyle("-fx-background-color: pink; -fx-border-color: black;");
		playlistMoodRunning.setStyle("-fx-background-color: pink; -fx-border-color: black;");
		playlistMoodSad.setStyle("-fx-background-color: pink; -fx-border-color: black;");
		playlistMoodParty.setStyle("-fx-background-color: pink; -fx-border-color: black;");
		playlistMoodSensual.setStyle("-fx-background-color: pink; -fx-border-color: black;");
		playlistMoodAngry.setStyle("-fx-background-color: pink; -fx-border-color: black;");
		playlistMoodChill.setStyle("-fx-background-color: pink; -fx-border-color: black;");
		playlistMoodSleep.setStyle("-fx-background-color: pink; -fx-border-color: black;");
	}
}
