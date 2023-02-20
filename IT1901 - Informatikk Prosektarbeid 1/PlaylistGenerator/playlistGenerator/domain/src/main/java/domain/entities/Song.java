package domain.entities;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Represents a song.
 */
public class Song {

    private String name;
    private String artist;
    private ArrayList<Double> ratings;
    private ArrayList<String> moods;
    private String regex = "[a-zA-Z0-9 æøåÆØÅ,'\".?!:-]+";

    /**
     * Takes in the parameters for a new song object and checks that they are all.
     * valid.
     * 
     * @param name    Name of song
     * @param artist  Artist of song
     * @param ratings ArrayList of rating(s)
     * @param moods   Moods of song
     * @throws throws IllegalArgumentException if invalid input
     */
    public Song(String name, String artist, ArrayList<Double> ratings, ArrayList<String> moods) {
        if (isValidInputName(name) && isValidInputArtist(artist) && isValidInputMoods(moods)
                && isValidInputRating(ratings)) {
            this.name = name;
            this.artist = artist;
            this.ratings = new ArrayList<>(ratings);
            this.moods = new ArrayList<>(moods);
        }
    }

    /**
     * Checks if the input from the song-name field is valid.
     * 
     * @param name Song name
     * @return true if the input is valid
     * @throws throws IllegalArgumentException if the song name is null or empty.
     * @throws throws IllegalArgumentException if the length of the title is more
     *                than 171 letters
     * @throws throws IllegalArgumentException if the song-name does not match this
     *                regex: [a-zA-Z0-9 æøåÆØÅ,'\".?!:-]+
     */
    private boolean isValidInputName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is either empty or null");
        } else if (name.length() > 171) {
            throw new IllegalArgumentException("Longest allowed song Title: 171 characters. Spotify's longest.");
        } else if (!name.matches(regex)) {
            throw new IllegalArgumentException(
                    "Song-name can only contain letters, numbers and these characters: ,'\".?!:-");
        } else {
            return true;
        }
    }

    /**
     * Checks if the input from the artist field is valid.
     * 
     * @param artist Song artist
     * @return true if the input is valid
     * @throws throws IllegalArgumentException if artist is null or empty.
     * @throws throws IllegalArgumentException if artist length is bigger than 24.
     * @throws throws IllegalArgumentException if the artist name does not match
     *                this regex: "[a-zA-Z0-9 ,.?!-&:+æøåÆØÅ]+".
     */
    private boolean isValidInputArtist(String artist) {
        if (artist == null || artist.isEmpty()) {
            throw new IllegalArgumentException("Artist is either empty or null");
        } else if (artist.length() > 24) {
            throw new IllegalArgumentException("Artist name is too long. Maximum of 24 characters. Spotify's longest");
        } else if (!artist.matches(regex)) {
            throw new IllegalArgumentException("Artist can only contain these characters: ,'\".?!:- ");
        } else {
            return true;
        }
    }

    /**
     * Checks that rating is between 0-10.
     * 
     * @param ArrayList<Double> Song rating(s)
     * @return true if the input is valid.
     * @throws throws IllegalArgumentException if rating is less than 0 or bigger
     *                than 10.
     */
    private boolean isValidInputRating(ArrayList<Double> ratings) {
        for (Double rating : ratings) {
            if (rating < 0 || rating > 10) {
                throw new IllegalArgumentException("Rating must be from 0 to 10 only");
            }
        }
        return true;
    }

    /**
     * Checks that mood is not null.
     * 
     * @param rating Song rating.
     * @return true if mood is not null.
     * @throws throws IllegalArgumentException if mood is null.
     */
    private boolean isValidInputMoods(ArrayList<String> moods) {
        if (moods == null) {
            throw new IllegalArgumentException("moods can't be null");
        } else {
            return true;
        }
    }

    /**
     * Getter for song name.
     * 
     * @return a string with the name of song object.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for song artist.
     * 
     * @return a string with the name of the artist for the song object.
     */
    public String getArtist() {
        return this.artist;
    }

    /**
     * Getter for song rating.
     * 
     * @return an array that contains the ratings of the song object.
     */
    public ArrayList<Double> getRatings() {
        return new ArrayList<Double>(this.ratings);
    }

    /**
     * Getter for song moods.
     * 
     * @return an array of Doubles containg moods of the song object.
     */
    public ArrayList<String> getMood() {
        return new ArrayList<>(this.moods);
    }

    /**
     * Adds a new rating to the song object.
     * 
     * @param rating new rating for song.
     */

    public void addRating(Double rating) {
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be from 0 to 10 only");
        } else {
            this.ratings.add(rating);
        }
    }

    /**
     * Gets average rating of a song object.
     * 
     * @return average rating
     */

    public Double getAverageRating() {
        Double sum = 0.0;
        for (double rating : this.ratings) {
            sum += rating;
        }
        Double average = sum / this.ratings.size();
        return average;
    }

    /**
     * Updates mood for song.
     * 
     * @param moods new mood for song
     */

    public void addMoods(ArrayList<String> moods) {
        for (String mood : moods) {
            if (!this.moods.contains(mood)) {
                this.moods.add(mood);
            }
        }
    }

    /**
     * Formates rating for average rating.
     * 
     * @return a formated rating for average score. Rounds the two last digits after
     *         point
     */

    public String getFormattedRating() {
        DecimalFormat df = new DecimalFormat("#.##");
        String formatted = df.format(getAverageRating());
        return formatted;
    }
}
