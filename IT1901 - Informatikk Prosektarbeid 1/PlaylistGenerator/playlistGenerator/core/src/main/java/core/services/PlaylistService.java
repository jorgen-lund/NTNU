package core.services;

import static java.util.stream.Collectors.toMap;

import core.repository.impl.PlayListRepositoryFileImpl;
import domain.entities.Playlist;
import domain.entities.Song;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents manager that has control of all playlist objects.
 */
public class PlaylistService {
	private List<Playlist> allPlaylists = new ArrayList<>();
	private PlayListRepositoryFileImpl playListRepository;

	private LinkedHashMap<Song, Integer> instancesOfSong = new LinkedHashMap<>();

	/**
	 * Constructor for playlistmanager. Collects all playlists and manages them.
	 * 
	 * @throws IOException if not found.
	 */
	public PlaylistService() {
		this.playListRepository = new PlayListRepositoryFileImpl();
		this.playListRepository.setFilePath("springbootserver-playlists.json");
		try {
			this.allPlaylists = playListRepository.findAll();
		} catch (IOException ex) {
			System.out.println("Could not load from file.");
			playListRepository.saveAll(allPlaylists);
		}
	}

	/**
	 * Sets the playlist file and array of playlists to the input.
	 * 
	 * @param playlistService the playlist service.
	 */
	public PlaylistService(PlaylistService playlistService) {
		this.playListRepository = playlistService.playListRepository;
		this.allPlaylists = playlistService.allPlaylists;
	}

	/**
	 * Sets the playlist file path and sets it to the input url string.
	 * 
	 * @param url given url String.
	 */
	public PlaylistService(String url) {
		this.playListRepository = new PlayListRepositoryFileImpl();
		this.playListRepository.setFilePath(url);
		try {
			this.allPlaylists = playListRepository.findAll();
		} catch (IOException ex) {
			System.out.println("Could not load from file.");
			playListRepository.saveAll(allPlaylists);
		}
	}

	/**
	 * Takes in a parameters for a new playlist. The method constructs a new
	 * playlist and adds it to the list of all playlists. Also saves to file.
	 * 
	 * @param name      Name of the playlist.
	 * @param moods     Moods of the playlist, can be multiple.
	 * @param minrating Minimum rating of playlist.
	 * @param maxrating Maximum rating of playlist.
	 * @param allSongs  All songes saved.
	 */
	public void addPlaylist(String name, ArrayList<String> moods, double minrating, double maxrating,
			ArrayList<Song> allSongs) {
		Playlist playlist = new Playlist(name, moods, minrating, maxrating, allSongs);
		this.allPlaylists.add(playlist);
		this.playListRepository.saveAll(allPlaylists);
	}

	public void saveAllInFile() {
		this.playListRepository.saveAll(allPlaylists);
	}

	/**
	 * Validation of input. Checks that the inserted name is the same as the name in
	 * the playlist list.
	 * 
	 * @param name String of name of to check if playlist.
	 * @return true if playlist exists.
	 */
	public boolean containsPlaylist(String name) {
		for (Playlist playlist : allPlaylists) {
			if (playlist.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Uses the input String to find the corresponding playlist object.
	 * 
	 * @param playlistName in name of desired playlist.
	 * @return Playlist object with target name.
	 */
	public Playlist findPlaylist(String playlistName) {
		for (Playlist playlist : allPlaylists) {
			if (playlistName.equals(playlist.getName())) {
				return playlist;
			}
		}
		throw new IllegalArgumentException("There is no playlist with that name in the register");
	}

	/**
	 * Goes through all songs in the target playlists and writes them on a string
	 * format.
	 * On the format:
	 * Playlist Name: playlist name
	 * Name: name, Artist: artist, Rating: avg rating, Moods: [moods].
	 * 
	 * @return All the songs and attributes as a string.
	 * @param playlistName takes in the name of the playlist.
	 * @throws IllegalArgumentException if playlist name is set incorrectly.
	 */
	public String returnPlaylistString(String playlistName) throws IllegalArgumentException {
		StringBuffer buf = new StringBuffer();
		buf.append("Playlist Name: " + playlistName + "\n");
		for (Song song : findPlaylist(playlistName).getPlaylist()) {
			buf.append("Name: " + song.getName() + ", Artist: " + song.getArtist() + ", Rating: "
					+ song.getAverageRating() + ", Moods: ");
			for (String m : song.getMood()) {
				buf.append(m + " ");
			}
			buf.append("\n");
		}
		String s = buf.toString();
		return s;
	}

	/**
	 * Writes all the playlist names in string formatting.
	 * 
	 * @return String with the names of all created playlists.
	 */
	public String returnPlaylistNames() {
		StringBuffer buf = new StringBuffer();
		for (Playlist playlist : allPlaylists) {
			buf.append(playlist.getName() + "\n");
		}

		String s = buf.toString();
		return s;
	}

	/**
	 * Getter for playlist objects.
	 * 
	 * @return ArrayList with all playlists in the register.
	 */
	public ArrayList<Playlist> getPlaylists() {
		return new ArrayList<Playlist>(this.allPlaylists);
	}

	/**
	 * This methods finds out how many times a song is included in a playlist, and
	 * stores the song as key and times as value. Does not include songs that do not
	 * appear in a playlist.
	 * 
	 * @return returns a linked hasmap with the most added songs first.
	 */
	public LinkedHashMap<Song, Integer> returnSongsByInstances() {
		this.instancesOfSong.clear();
		for (Playlist playlist : getPlaylists()) {
			for (Song songPlaylist : playlist.getPlaylist()) {
				int increment = 1;
				int ceiling = instancesOfSong.entrySet().size();
				int counter = 0;
				for (Map.Entry<Song, Integer> entry : instancesOfSong.entrySet()) {
					counter++;
					if (songPlaylist.getName().equals(entry.getKey().getName())
							&& songPlaylist.getArtist().equals(entry.getKey().getArtist())) {
						increment += entry.getValue();
						this.instancesOfSong.put(entry.getKey(), increment);
						break;
					}
				}
				if (ceiling == counter) {
					this.instancesOfSong.put(songPlaylist, increment);
				}

			}
		}
		return new LinkedHashMap<Song, Integer>(instancesOfSong);
	}

	/**
	 * This method sorts the LinkedHashMap after the values of the map, in
	 * decreasing order.
	 * 
	 * @return LinkedHashMap.
	 */
	public LinkedHashMap<Song, Integer> sortInstances() {
		LinkedHashMap<Song, Integer> sortedMap = returnSongsByInstances().entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

		return new LinkedHashMap<Song, Integer>(sortedMap);
	}

	/**
	 * This method formats the sorted linked hasmap to a stringformat that can be
	 * showed to the user.
	 * 
	 * @return String of most added songs.
	 */
	public String returnSongSortInstanceStringedNew() {
		LinkedHashMap<Song, Integer> sortedInstances = sortInstances();
		StringBuilder mostInstancesSongs = new StringBuilder();
		for (Map.Entry<Song, Integer> entry : sortedInstances.entrySet()) {
			Song song = entry.getKey();
			mostInstancesSongs.append(String.valueOf(entry.getValue())).append(" times:").append("\n Title: ")
					.append(song.getName()).append(", Artist: ").append(song.getArtist()).append("\n");
		}
		return mostInstancesSongs.toString();
	}

	/**
	 * Adds a whole playlist to list containing all playlists. Check if a list
	 * already has a list with the name, if not it attempts to add the playlist. if
	 * successful, returns true.
	 * 
	 * @param playlist the added playlist.
	 * @return boolean.
	 */
	public boolean addPlaylist(Playlist playlist) {
		return this.allPlaylists.add(playlist);
	}
}
