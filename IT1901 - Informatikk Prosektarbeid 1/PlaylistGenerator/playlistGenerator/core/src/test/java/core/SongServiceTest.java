package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map.Entry;
import core.repository.impl.SongRepositoryFileImpl;
import core.services.SongService;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.entities.Song;

public class SongServiceTest {
	private SongService ss = new SongService("test-songservice.json");
	private ArrayList<String> mood1 = new ArrayList<String>();
	private ArrayList<String> mood2 = new ArrayList<String>();
	SongRepositoryFileImpl srfi = new SongRepositoryFileImpl();

	/**
	 * Sets up before each test. Adding moods to the Mood Array.
	 * 
	 * @throws IllegalAccessException if illegal input is put into Song constructor.
	 */

	@BeforeEach
	public void setUp() throws IllegalAccessException {
		mood1.add("happy");
		mood1.add("sad");
		mood2.add("happy");

	}

	/**
	 * Tests addSong
	 * 
	 * @throws IllegalArgumentException if song does not exist. Tests that a song is
	 *                                  indeed added. Then tests that a song with
	 *                                  the same name and artist updates the
	 *                                  song-object rather than creating a new one.
	 */
	@Test
	public void testAddSong() {
		// Throws with invalid input.
		assertThrows(IllegalArgumentException.class, () -> {
			ss.getSong("invalid");
		});

		// Adding a song, and then after adding it again. Then making sure
		// that the added song is updated rather than created again.
		ss.addSong("title1", "artist1", 6.0, mood1);
		ss.addSong("title1", "artist1", 8.0, mood2);
		assertEquals(ss.getSong("title1").getAverageRating(), 7.0);
	}

	/**
	 * Tests sortSongsByRating, which is a HashMap consisting of <Song, Double>.
	 * Checks that the size is equal to the two elements added. Checks that the song
	 * added first shows up first, which is "title2". The last check checks that the
	 * song with the highest rating shows up first.
	 */
	@Test
	public void testSortSongsByRating() {
		ss.addSong("title2", "artist2", 6.0, mood1);
		ss.addSong("title3", "title3", 10.0, mood1);
		assertEquals(3, ss.returnSongsByRating().keySet().size());

		// Testing that we get the first object added, aka. title3, to show up first.
		Entry<Song, Double> mapEntry = ss.returnSongsByRating().entrySet().iterator().next();
		Song key = mapEntry.getKey();
		assertNotEquals(ss.getSong("title3").getName(), key.getName());
		assertEquals(ss.getSong("title1").getName(), key.getName());

		// Testing that the LinkedHashMap is now sorted, and we now get title3 first.
		Entry<Song, Double> mapEntry2 = ss.sortSongsByRating().entrySet().iterator().next();
		Song key1 = mapEntry2.getKey();
		assertNotEquals(ss.getSong("title2").getName(), key1.getName());
		assertEquals(ss.getSong("title3").getName(), key1.getName());
	}

	/**
	 * Deletes the JSON file for playlist after each test. This way all the tests
	 * work properly every time, instead of just once. UPDATE: Does not seem to work
	 * anymore.
	 */
	@AfterEach
	public void cleanUp() {
		srfi.deleteJsonSongFile("test-songservice.json");
	}
}