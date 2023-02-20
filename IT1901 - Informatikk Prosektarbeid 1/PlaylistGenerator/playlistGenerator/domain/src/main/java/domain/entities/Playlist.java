package domain.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an playlist.
 */
public class Playlist {
	private ArrayList<Song> playlist = new ArrayList<Song>();
	private String name;
	private double minrating;
	private double maxrating;
	private ArrayList<String> moods;

	/**
	 * Playlist constructor.
	 * 
	 * @param name      playlist name
	 * @param moods     ArrayList of moods for the playlist
	 * @param minrating double of minimum song rating
	 * @param maxrating double of maximum song rating
	 * @param allSongs  ArrayList of all songs from Song Manager.
	 * @throws IllegalArgumentException if some of the input is missing or invalid.
	 */
	public Playlist(String name, ArrayList<String> moods, double minrating, double maxrating, List<Song> allSongs)
			throws IllegalArgumentException {
		if (isValidInput(name, moods, minrating, maxrating)) {
			this.name = name;
			this.minrating = minrating;
			this.maxrating = maxrating;
			this.moods = new ArrayList<>(moods);
			this.playlist = findSongMatch(allSongs);
		}
	}

	/**
	 * Function to check if all values to base playlist on is valid, so it does not
	 * lack any values. Name cant be empty, Max rating must be higher than min,
	 * ArrayList of moods can not be empty.
	 * 
	 * @param name      Name of playlist
	 * @param moods     Moods of playlist
	 * @param minrating Minimum rating of songs that should be added to playlist
	 * @param maxrating Maximum rating of songs that should be added to playlist
	 * @return true if input is valid
	 * @throws IllegalArgumentException if it lacks any of the input
	 */
	private boolean isValidInput(String name, ArrayList<String> moods, double minrating, double maxrating) {
		if (name.equals("") || minrating > maxrating || moods.isEmpty()) {
			throw new IllegalArgumentException("Illegal input for playlist");
		} else {
			return true;
		}
	}

	/**
	 * Checks through all songs entered into the app and finds matches, adds them to
	 * playlist.
	 * 
	 * @return an array of songs matching the playlist demands
	 */
	public ArrayList<Song> findSongMatch(List<Song> allSongs) {
		for (Song song : allSongs) {
			double r = song.getAverageRating();
			ArrayList<String> songMoods = song.getMood();
			boolean shareMood = false;
			if (r >= this.minrating && r <= this.maxrating) {
				for (String moodS : songMoods) {
					for (String moodP : this.moods) {
						if (moodS.equals(moodP)) {
							shareMood = true;
						}
					}
				}
				if (shareMood) {
					this.playlist.add(song);
				}
			}
		}
		return new ArrayList<>(this.playlist);
	}

	/**
	 * Getter for playlist.
	 * 
	 * @return ArrayList of Song-objects
	 */
	public ArrayList<Song> getPlaylist() {
		return new ArrayList<>(this.playlist);
	}

	/**
	 * Getter for name of playlist.
	 * 
	 * @return the playlist name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Getter for moods of the playlist.
	 * 
	 * @return ArrayList of moods
	 */
	public ArrayList<String> getMoods() {
		return new ArrayList<>(this.moods);
	}

	/**
	 * Getter for the max rating of the playlist.
	 * 
	 * @return double with the Max rating value for songs in playlist
	 */
	public double getMaxRating() {
		return this.maxrating;
	}

	/**
	 * Getter for the min rating of the playlist.
	 * 
	 * @return double with min rating for songs in playlist
	 */
	public double getMinRating() {
		return this.minrating;
	}

	/**
	 * Takes in a string with the name of a song and returns the object if it is
	 * containd in the playlist.
	 * 
	 * @param name String with name of song
	 * @return Song object with that name
	 * @throws if there is no song with that name in the Playlist
	 */
	public Song getSong(String name) throws IllegalArgumentException {
		for (Song song : playlist) {
			if (song.getName().equals(name)) {
				return song;
			}
		}
		throw new IllegalArgumentException("Song not in playlist");
	}
}
