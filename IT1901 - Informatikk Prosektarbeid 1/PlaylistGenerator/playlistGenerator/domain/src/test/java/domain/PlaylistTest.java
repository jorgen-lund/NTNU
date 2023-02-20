package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import domain.entities.Playlist;
import domain.entities.Song;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlaylistTest {
	private ArrayList<String> mood1 = new ArrayList<String>();
	private ArrayList<String> mood2 = new ArrayList<String>();
	private ArrayList<String> mood3 = new ArrayList<String>();
	private ArrayList<Song> allSongs = new ArrayList<Song>();

	private ArrayList<Double> rating1 = new ArrayList<Double>();
	private ArrayList<Double> rating2 = new ArrayList<Double>();
	private ArrayList<Double> rating3 = new ArrayList<Double>();
	private ArrayList<Double> rating4 = new ArrayList<Double>();

	private Song s1;
	private Song s2;
	private Song s3;
	private Song s4;

	@BeforeEach
	public void makeSongs() throws IllegalAccessException {

		// To not have many of the same entries each time, we delete songs in archive
		// and playlists made.
		mood1.add("happy");
		mood1.add("party");
		mood2.add("happy");
		mood3.add("party");
		// 7, 5, 8, 2
		rating1.add(7.0);
		rating2.add(5.0);
		rating3.add(8.0);
		rating4.add(2.0);

		s1 = new Song("s1", "a1", rating1, mood1);
		s2 = new Song("s2", "a2", rating2, mood2);
		s3 = new Song("s3", "a3", rating3, mood3);
		s4 = new Song("s4", "a4", rating4, mood1);

		this.allSongs.add(s1);
		this.allSongs.add(s2);
		this.allSongs.add(s3);
		this.allSongs.add(s4);
	}

	// Test to see if the songs set to containing happy are included, should be 1,
	// 2, 4, and not 3m
	@Test
	public void checkIfCorrectMood() throws IllegalArgumentException {
		Playlist pHappy = new Playlist("happy playlist", mood2, 0.0, 10.0, allSongs);

		// Assert that the playlist added all the songs with the desiered moood
		assertEquals(this.s1, pHappy.getSong(s1.getName()), "The playlist contains a song with correct mood");
		assertEquals(this.s2, pHappy.getSong(s2.getName()), "The playlist contains a song with correct mood");
		assertEquals(this.s4, pHappy.getSong(s4.getName()), "The playlist contains a song with correct mood");

		// Assert that the playlist does not contain songs that are not happy
		assertThrows(IllegalArgumentException.class, () -> {
			pHappy.getSong(s3.getName());
		});
	}

	// Test to see that only the songs containing happy with ratings 5 and over are
	// included. Should be 1, 2, and not 4
	@Test
	public void checkRatings() throws IllegalArgumentException {
		Playlist pHappyOver5 = new Playlist("happy playlist over 5", mood2, 5.0, 10.0, allSongs);

		// Asserts that the correct songs have been added to the correct playlist
		assertEquals(this.s1, pHappyOver5.getSong("s1"));
		assertEquals(this.s2, pHappyOver5.getSong("s2"));

		// Asserts that the incorrect songs are not in the playlist. s4 has right mood,
		// wrong rating
		// s3 is already checked by wrong mood
		assertThrows(IllegalArgumentException.class, () -> {
			pHappyOver5.getSong(s4.getName());
		});
	}

	// Test to see if thrown when max < min, name is empty or mood is empty
	@Test
	public void testConstructorValidation() throws IllegalArgumentException {
		assertThrows(IllegalArgumentException.class, () -> {
			new Playlist("p2", mood3, 10.0, 0.0, allSongs);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new Playlist("", mood3, 0.0, 10.0, allSongs);
		});
		ArrayList<String> empty = new ArrayList<>();
		assertThrows(IllegalArgumentException.class, () -> {
			new Playlist("p3", empty, 0.0, 10.0, allSongs);
		});
	}

	/**
	 * Test to see of getters for arrays returns new array
	 */

	@Test
	public void testGetArrays() {
		Playlist pHappy = new Playlist("happy playlist", mood2, 0.0, 10.0, allSongs);
		assertNotEquals(pHappy.getPlaylist(), pHappy);
	}
}
