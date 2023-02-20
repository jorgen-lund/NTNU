package core;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.repository.impl.PlayListRepositoryFileImpl;
import core.services.PlaylistService;
import core.services.SongService;
import domain.entities.Playlist;
import domain.entities.Song;

public class PlaylistServiceTest {
	private ArrayList<String> mood1 = new ArrayList<String>();
	private ArrayList<String> mood2 = new ArrayList<String>();
	private ArrayList<String> mood3 = new ArrayList<String>();
	private PlaylistService playlistService;
	private SongService songService;
	private Playlist p1;
	private Playlist p2;
	private PlayListRepositoryFileImpl playListRepositoryFileImpl = new PlayListRepositoryFileImpl();

	@BeforeEach
	public void setUp() {
		songService = new SongService("test-playlistservice-songs.json");
		playlistService = new PlaylistService("test-playlistservice-playlist.json");

		mood1.add("happy");
		mood1.add("party");
		mood2.add("happy");
		mood3.add("party");

		songService.addSong("s1", "a1", 2.0, mood1);
		songService.addSong("s2", "a2", 5.0, mood2);
		songService.addSong("s3", "a3", 3.0, mood2);
		songService.addSong("s4", "a4", 10.0, mood1);

		p1 = new Playlist("p1", mood1, 0.0, 10.0, songService.getSongs());
		p2 = new Playlist("p2", mood2, 0.0, 10.0, songService.getSongs());
		playlistService.addPlaylist("p1", mood1, 0.0, 10.0, songService.getSongs());

	}

	/**
	 * Checks that playlist 1 has been added, and that playlist 2 is not. Then adds,
	 * and checks that it now is contained.
	 */
	@Test
	public void testAddPlaylist() {
		playlistService.addPlaylist("p1", mood3, 0.0, 10.0, songService.getSongs());
		assertTrue(playlistService.containsPlaylist(p1.getName()));
		// assertFalse(playlistService.containsPlaylist(p2.getName()));
		playlistService.addPlaylist("p2", mood2, 0.0, 10.0, songService.getSongs());
		assertTrue(playlistService.containsPlaylist(p2.getName()));
	}

	/**
	 * @Throws IllegalArgumentException if playlist can't be found. Checks that the
	 *         first playlist added is the same we find with findPlaylist("p1").
	 */
	@Test
	public void testFindPlaylist() {
		Playlist p4 = new Playlist("pThrow", mood1, 0.0, 1.0, songService.getSongs());
		assertThrows(IllegalArgumentException.class, () -> {
			playlistService.findPlaylist("pThrow");
		});
		assertEquals(playlistService.getPlaylists().get(0).getName(), playlistService.findPlaylist("p1").getName());
	}

	/**
	 * Tests that sortInstances and getSongsByInstances work.
	 */
	@Test
	public void sortInstances() {
		// Checking that the first Song in the LinkedHashMap is "s1".
		Map.Entry<Song, Integer> mapEntry = playlistService.returnSongsByInstances().entrySet().iterator().next();
		assertEquals(mapEntry.getKey().getName(), p2.getSong("s1").getName());

		/*
		 * Checking that the value of this is 1 and not 0 (as in how many playlists it
		 * is in).
		 * assertNotEquals(0, mapEntry.getValue());
		 * assertEquals(1, mapEntry.getValue());
		 */

		// Adding a playlist where only "s4 meets the criteria". Then runs sortInstance.
		playlistService.addPlaylist("p3", mood2, 7.0, 10.0, songService.getSongs());
		Map.Entry<Song, Integer> mapEntrySorted = playlistService.sortInstances().entrySet().iterator().next();
		assertEquals(mapEntrySorted.getKey().getName(), p2.getSong("s4").getName());
	}

	/**
	 * Deletes the JSON file for playlist after each test. This way all
	 * the tests work properly every time, instead of just once. UPDATE: Does not
	 * seem to work anymore.
	 */
	@AfterEach
	public void cleanUp() {
		playListRepositoryFileImpl.deleteJsonPlaylistFile("test-playlistservice-playlist.json");
	}
}
