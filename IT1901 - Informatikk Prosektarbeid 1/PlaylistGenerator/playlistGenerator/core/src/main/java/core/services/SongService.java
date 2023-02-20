package core.services;

import static java.util.stream.Collectors.toMap;

import core.repository.SongRepository;
import core.repository.impl.SongRepositoryFileImpl;
import domain.entities.Song;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents manager that has control of all song objects.
 */
public class SongService {
	private List<Song> songs = new ArrayList<>();
	private SongRepository songRepository;

	private LinkedHashMap<Song, Double> ratingMap = new LinkedHashMap<>();

	/**
	 * Creates the song archive file NO intializes an array.
	 */
	public SongService() {
		this.songRepository = new SongRepositoryFileImpl();
		this.songRepository.setFilePath("springbootserver-songs.json");
		try {
			this.songs = songRepository.findAll();

		} catch (IOException ex) {
			System.out.println("Could not load from file.");
		}

	}

	/**
	 * Sets the repository and all songs array to be the same as the input
	 * SongService.
	 * 
	 * @param songService given SongService.
	 */
	public SongService(SongService songService) {
		this.songRepository = songService.songRepository;
		this.songs = songService.songs;
	}

	/**
	 * Sets the path of the repository to given url string.
	 * 
	 * @param url string.
	 */
	public SongService(String url) {
		this.songRepository = new SongRepositoryFileImpl();
		this.songRepository.setFilePath(url);
		try {
			this.songs = songRepository.findAll();

		} catch (IOException ex) {
			System.out.println("Could not load from file.");
			songRepository.saveAll(songs);
		}
	}

	/**
	 * Uses saveAll method in the songRepository to save all songs to file.
	 * 
	 * @throws IOException if not found.
	 */
	public void saveAllInFile() throws IOException {
		this.songRepository.saveAll(this.songs);
	}

	/**
	 * This method first checks if song already saved in repo. If not, the song will
	 * be added and saved to the songs list. If it exists, the ratings and mood of
	 * the song object will be updated. Also puts them into the ratings map.
	 * 
	 * @param songTitle  title of song.
	 * @param artist     artist of song.
	 * @param songRating rating of song.
	 * @param moods      moods of song, can be multiple.
	 */
	public void addSong(String songTitle, String artist, Double songRating, ArrayList<String> moods) {
		ArrayList<Double> rating = new ArrayList<>();
		rating.add(songRating);
		if (checkSong(songTitle, artist) == null) {
			Song song = new Song(songTitle, artist, rating, moods);
			this.songs.add(song);
		} else {
			Song existingSong = checkSong(songTitle, artist);
			existingSong.addRating(songRating);
			existingSong.addMoods(moods);
		}
		try {
			this.songRepository.saveAll(songs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a song object to the list of songs if there doesn't exist a song with
	 * the title and artist.
	 * 
	 * @param Song to add.
	 * @return boolean, true if added correctly.
	 */
	public boolean addSong(Song song) {
		if (checkSong(song.getName(), song.getArtist()) == null) {
			return this.songs.add(song);
		}
		return false;
	}

	/**
	 * This method gets all the songs.
	 * 
	 * @return ArrayList with all song objects created.
	 */
	public ArrayList<Song> getSongs() {
		return new ArrayList<>(this.songs);
	}

	/**
	 * This method gets a song from the list by checking the name.
	 * 
	 * @param name the name of the song which is to be found.
	 * @return Song a song object with that name.
	 * @throws IllegalArgumentException if the song is not in the list.
	 */

	public Song getSong(String name) throws IllegalArgumentException {
		for (Song song : songs) {
			if (song.getName().equals(name)) {
				return song;
			}
		}
		throw new IllegalArgumentException("No song with this name exists in the register");
	}

	/**
	 * This method checks if a song with the title and artist already exists in the
	 * app, if yes it returns the song object, if not it returns null.
	 * 
	 * @param songTitle title of song.
	 * @param artist    artist of song.
	 * @return Song object with the same title and artist if already a saved song,
	 *         null if not.
	 */
	public Song checkSong(String songTitle, String artist) throws IllegalArgumentException {
		for (Song song : songs) {
			if ((song.getName().compareTo(songTitle) == 0) && (song.getArtist().compareTo(artist) == 0)) {
				return song;
			}
		}
		return null;
	}

	/**
	 * Formats list of all songs in a way that can be displayed to the user.
	 * 
	 * @return String all song objects as strings with formatting.
	 */
	public String returnSongsAsStrings() {
		StringBuffer buf = new StringBuffer();
		for (Song song : this.songs) {
			buf.append("Name: " + song.getName() + ", Artist: " + song.getArtist() + ", Rating: "
					+ getRatingFormatted(song.getAverageRating()) + " Moods: ");
			for (String m : song.getMood()) {
				buf.append(m + " ");
			}
			buf.append("\n");
		}
		String s = buf.toString();
		return s;
	}

	/**
	 * This returns an encapsulated Map of songs with their ratings. The song is a
	 * key and the rating is its value.
	 * 
	 * @return linked hasmap of ratingmap.
	 */
	public LinkedHashMap<Song, Double> returnSongsByRating() {
		this.ratingMap = new LinkedHashMap<>();
		for (Song song : getSongs()) {
			this.ratingMap.put(song, song.getAverageRating());
		}
		return new LinkedHashMap<Song, Double>(this.ratingMap);
	}

	/**
	 * This method puts all songs and their respective order, and sorts it in
	 * descending order.
	 * 
	 * @return a linked hasmap of songs sorted by rating. Is a linked hashmap to be
	 *         able to sort.
	 */

	public LinkedHashMap<Song, Double> sortSongsByRating() {
		LinkedHashMap<Song, Double> sortedMap = returnSongsByRating().entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
		;
		// Saves a map which is sorted by decreasing order of value.
		return new LinkedHashMap<Song, Double>(sortedMap);
	}

	/**
	 * First makes a Collection of the songs that have been sorted by decreasing
	 * order of rating, then writes out all of the names + artist + rating out in a
	 * string On the Format: Number [number] Title: [song title], Artist: [artist],
	 * Rating: [avg. rating].
	 * 
	 * @return formated sting of top songs.
	 */
	public String returnSongSortRatingStringed() {
		Set<Song> songsSorted = sortSongsByRating().keySet();
		StringBuilder topRatedSong = new StringBuilder();
		int i = 1;
		for (Song song : songsSorted) {
			topRatedSong.append("Number ").append(String.valueOf(i)).append("\n Title: ").append(song.getName())
					.append(", Artist: ").append(song.getArtist()).append(", Rating: ")
					.append(String.valueOf(song.getAverageRating())).append("\n");
			i++;
		}
		return topRatedSong.toString();
	}

	/**
	 * This method returns a rating as a string.
	 * 
	 * @param avgRating gets the rating as a Double.
	 * @return the avgRating as String.
	 */
	public String getRatingFormatted(Double avgRating) {
		DecimalFormat df = new DecimalFormat("#.##");
		String formatted = df.format(avgRating);
		return formatted;
	}
}
