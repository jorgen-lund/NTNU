package ui;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Controller for the song tab.
 */
public class SongTabController {
	@FXML
	private Button addSong;
	@FXML
	private TextField songTitle;
	@FXML
	private TextField songArtist;
	@FXML
	private Slider songRating;
	@FXML
	private Button songMoodHappy;
	@FXML
	private Button songMoodLove;
	@FXML
	private Button songMoodRunning;
	@FXML
	private Button songMoodSensual;
	@FXML
	private Button songMoodParty;
	@FXML
	private Button songMoodAngry;
	@FXML
	private Button songMoodSad;
	@FXML
	private Button songMoodSleep;
	@FXML
	private Button songMoodChill;
	@FXML
	private Button clear;
	@FXML
	private Text songNameErrorTextbox;
	@FXML
	private Text songArtistErrorTextbox;
	@FXML
	private Text songErrorTextbox;
	@FXML
	private Text songAddedTextbox;

	private ArrayList<Button> songMoodsSelected = new ArrayList<Button>();
	private ArrayList<String> songMoodsSelectedStrings = new ArrayList<String>();

	private static final URI URIEndpoint = URI.create("http://localhost:8080/springboot");
	private SongServiceRemoteAccess songServiceRemoteAccess = new SongServiceRemoteAccess(URIEndpoint);

	/**
	 * Initalizes the ui. Clears ui, then loads playlists and songs form json.
	 * 
	 * @throws IOException throws if unable to find filepath for json document
	 */
	@FXML
	public void initialize() throws IOException {
		this.songMoodsSelected.clear();
		this.songMoodsSelectedStrings.clear();
	}

	/**
	 * Creates a list of buttons Checks if the button is already presses, if so it
	 * clears it. If not already selected and less than three moods are good then it
	 * adds the mood to the moods list.
	 * 
	 * @param event Action selected button
	 */
	@FXML
	public void addSongMood(ActionEvent event) {
		Button source = (Button) event.getSource();
		if (this.songMoodsSelected.contains(source)) {
			songMoodsSelected.remove(source);
			songMoodsSelectedStrings.remove(source.getId());
			source.setStyle("-fx-background-color: pink; -fx-border-color: black;");
		} else if (this.songMoodsSelected.size() < 3) {
			songMoodsSelected.add(source);
			songMoodsSelectedStrings.add(source.getId());
			source.setStyle("-fx-background-color: red; -fx-border-color: black;");
		}
	}

	/**
	 * This inits a song object and adds it to the allSongs array in SongManager.
	 * 
	 * @throws IOException Throws exception if not able to find text in all fields
	 */
	@FXML
	public void addSong() throws IOException {
		try {
			songServiceRemoteAccess.addSong(songTitle.getText(), songArtist.getText(), songRating.getValue(),
					this.songMoodsSelectedStrings);
			songAddedTextbox.setText("Song successfully added");
			clearErrorMessages();
			clear();

		} catch (IllegalArgumentException e) {
			clearSuccessfullyAddedMessage();
			songErrorTextbox.setText(
					"Missing (or invalid) title, artist or mood. Only these symbols allowed: , ' \" . ? ! : -");
			System.out.println(e.getMessage());
		}
	}

	private void clearErrorMessages() {
		songErrorTextbox.setText("");
	}

	private void clearSuccessfullyAddedMessage() {
		songAddedTextbox.setText("");
	}

	/**
	 * Clears the user interface.
	 */
	@FXML
	public void clear() {
		songMoodsSelectedStrings.clear();
		songMoodsSelected.clear();
		songTitle.setText(null);
		songArtist.setText(null);
		songRating.setValue(0);
		songMoodHappy.setStyle("-fx-background-color: pink; -fx-border-color: black;");
		songMoodLove.setStyle("-fx-background-color: pink; -fx-border-color: black;");
		songMoodRunning.setStyle("-fx-background-color: pink; -fx-border-color: black;");
		songMoodSad.setStyle("-fx-background-color: pink; -fx-border-color: black;");
		songMoodParty.setStyle("-fx-background-color: pink; -fx-border-color: black;");
		songMoodSensual.setStyle("-fx-background-color: pink; -fx-border-color: black;");
		songMoodAngry.setStyle("-fx-background-color: pink; -fx-border-color: black;");
		songMoodChill.setStyle("-fx-background-color: pink; -fx-border-color: black;");
		songMoodSleep.setStyle("-fx-background-color: pink; -fx-border-color: black;");

	}
}
