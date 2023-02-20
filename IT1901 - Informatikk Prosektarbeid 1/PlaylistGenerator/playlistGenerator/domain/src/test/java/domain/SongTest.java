package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import domain.entities.Song;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SongTest {
	private Song song;
	private ArrayList<String> moods = new ArrayList<String>();
	private ArrayList<Double> ratings = new ArrayList<Double>();


	@BeforeEach
	public void setup() throws IOException {
		moods.add("happy");
		moods.add("sad");
		ratings.add(7.0);
		song = new Song("name", "artist", ratings, moods);
	}

	// Testing that it returns the correct name
	@Test
	public void testGetName() {
		assertEquals("name", song.getName());
	}

	// Testing that it returns the correct artist
	@Test
	public void testGetArtist() {
		assertEquals("artist", song.getArtist());
	}

	// Testing that ratings contains the correct ratings
	@Test
	public void testGetRatings() {
		assertEquals(7.0, song.getRatings().get(0));
	}

	// Testing that it returns the correct rating
	@Test
	public void testGetAverageRating() {
		assertEquals(7.0, song.getAverageRating());
		song.addRating(5.0);
		assertEquals(6.0, song.getAverageRating());
		song.addRating(9.0);
		assertEquals(7.0, song.getAverageRating());
	}

	// Testing that it returns the correct moods and no duplicates.
	@Test
	public void testGetMoods() {
		ArrayList<String> mood = new ArrayList<String>();
		mood.add("happy");
		mood.add("sad");
		assertEquals(mood, song.getMood());
		mood.add("sad");
	}

	// Testing that song name is not empty or null
	@Test
	public void testConstructor_checkEmptyOrNullName() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Song("", "artist", new ArrayList<Double>((int) 7), moods);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new Song(null, "artist", new ArrayList<Double>((int) 7), moods);
		});
	}

	// Testing that name is not empty nor null
	@Test
	public void testConstructor_checkEmptyOrNullArtist() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Song("song", "", new ArrayList<Double>((int) 7), moods);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new Song("song", null, new ArrayList<Double>((int) 7), moods);
		});
	}

	// Tests that song length is not longer than 171.
	@Test
	public void testConstructor_checkNameLength() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Song(
					"To The Workers of The Rock River Valley Region, I have an idea concerning your predicament, and it involves an inner tube, bath mats, and 21 able-bodied men Sufjan Stevens1",
					"artist", new ArrayList<Double>((int) 7), moods);
		});

	}

	// Tests that artist length is not longer than 24
	@Test
	public void testConstructor_checkArtistLength() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Song("song", "Kenny babyface Edmonds1234", new ArrayList<Double>((int) 7.0), moods);
		});
	}

	// Tests that only valid characters are being used and that valid characters are
	// allowed for name.
	@Test
	public void testConstructor_checkValidCharactersName() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Song("{[(/#etc", "Artist", new ArrayList<Double>((int) 7), moods);
		});
		String name1 = new String("name\"'.?!:-,");
		Song song1 = new Song("name\"'.?!:-,", "artist", new ArrayList<Double>((int) 7.0), moods);
		assertEquals(name1, song1.getName());
	}

	// Tests that only valid characters are being used and that valid characters are
	// allowed for artist.
	@Test
	public void testConstructor_checkValidCharactersArtist() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Song("song", "{[(/#etc", new ArrayList<Double>((int) 7), moods);
		});
		String artist1 = new String("æÅartist\"'.?!:-,");
		Song song1 = new Song("song", "æÅartist\"'.?!:-,", new ArrayList<Double>((int) 7.0), moods);
		assertEquals(artist1, song1.getArtist());
	}

	// Test if valid rating
	@Test
	public void testConstructor_IsValidInputRating() {
		ArrayList<Double> negativeRating = new ArrayList<Double>();
		negativeRating.add(-1.0);
		ArrayList<Double> tooHighRating = new ArrayList<Double>();
		tooHighRating.add(11.0);

		assertThrows(IllegalArgumentException.class, () -> {
			new Song("song", "Artist", negativeRating, moods);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new Song("song", "Artist", tooHighRating, moods);
		});
	}

	// tests that moods is not null
	@Test
	public void testConstructor_IsValidInputMood() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Song("song", "artist", ratings, null);
		});
	}
}