package ui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * Controller that connects the user interface with the application.
 */
public class TabController {
	@FXML
	Button songTab;
	@FXML
	Button playlistTab;
	@FXML
	Button viewerTab;
	@FXML
	Button songStatsTab;
	@FXML
	AnchorPane vistaHolder;
	@FXML
	private Button clear;

	@FXML
	SongTabController songTabController;
	@FXML
	PlaylistTabController playlistTabController;
	@FXML
	ViewerTabController viewerTabController;
	@FXML
	StatsTabController statsTabController;

	private Button previousTab;

	/**
	 * Initializes the tab controller. This will make the tabs at the top of the
	 * screen visible.
	 * cd
	 * 
	 * @throws IOException throws if unable to find filepath for json document
	 */

	@FXML
	public void initialize() throws IOException {
		System.out.println("javafx init");
		Node tabNode = (Node) FXMLLoader.load(getClass().getResource("SongTab.fxml"));
		vistaHolder.getChildren().setAll(tabNode);
	}

	/**
	 * Changes what tab is visible by taking in which button is clicked and showing
	 * the correct fxml.
	 * 
	 * @param event button click for each tab button.
	 */

	@FXML
	public void goToTab(ActionEvent event) {
		// Previous tab button (if any) gets it's lightblue color back.
		if (this.previousTab != null) {
			previousTab.setStyle("-fx-background-color: lightblue; -fx-border-color: black;");
		}
		Button source = (Button) event.getSource();
		String tabName = source.getId() + ".fxml";
		previousTab = source;
		// Highlighting the button selected to pink.
		source.setStyle("-fx-background-color: pink; -fx-border-color: black;");

		// here we load the tabName tab.
		try {
			Node tabNode = (Node) FXMLLoader.load(getClass().getResource(tabName));
			vistaHolder.getChildren().setAll(tabNode);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			System.out.println("Could not load new pane.");
		}
	}
}